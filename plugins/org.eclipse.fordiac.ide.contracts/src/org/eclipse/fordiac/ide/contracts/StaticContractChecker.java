/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import static org.eclipse.fordiac.ide.contracts.ContractRule.Type.REPETITION;
import static org.eclipse.fordiac.ide.contracts.ContractRule.Type.SINGLE_EVENT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main class for performing the static check. Tries to find contradictions
 * between connected rules without concrete event occurrences.
 */
public class StaticContractChecker {

	private final ContractSystem system;
	private final List<ContractComponent> components;
	private final Set<ContractComponent> seenComponents;

	public StaticContractChecker(final ContractSystem system, final List<ContractComponent> components) {
		this.system = system;
		this.components = components;
		seenComponents = new HashSet<>();
	}

	public void checkSystem() {
		// try to resolve all reactions
		for (final ContractComponent comp : components) {
			// iterate in reverse, so we can remove from reaction list
			for (int i = comp.getReactions().size() - 1; i >= 0; i--) {
				seenComponents.clear();
				resolveReaction(comp.getReactions().get(i));
			}
			if (!comp.getReactions().isEmpty()) {
				unresolvedReactionInfo(comp);
			}
		}

		// check all connections
		for (final ContractComponent comp : components) {
			for (final CConnection conn : comp.getInputs()) {
				switch (conn.type()) {
				case NORMAL:
					checkNormalConnection(comp, conn);
					break;
				case FROM_OUTER:
					checkFromOuterConnection(comp, conn);
					break;
				case FROM_INNER:
					checkFromInnerConnection(comp, conn);
					break;
				default:
					break;
				}
			}
		}
	}

	private void resolveReaction(final ContractRule reaction) {
		// avoid loops when resolving
		if (!seenComponents.add(reaction.getOwner())) {
			return;
		}

		// TODO: tmp issue until support added
		if (reaction.getInputs().size() > 1 || reaction.getOutputs().size() > 1) {
			system.warning("Reactions with multiple inputs/outputs not supported yet.", ContractIssue.Code.UNKOWN); //$NON-NLS-1$
			return;
		}

		// check for assumption on input port
		final ContractComponent comp = reaction.getOwner();
		final ContractRule assumption = comp.getAssumptions().get(reaction.getInputs().getFirst());
		if (assumption != null) {
			resolveReactionWith(reaction, assumption);
			return;
		}

		// no assumption found, search for connected components
		boolean resolved = false;
		for (final CConnection conn : comp.getInputs()) {
			if (conn.type() == CConnection.Type.FROM_OUTER) {
				// resolve with assumption from outer component
				final ContractRule a = conn.from().getAssumptions().get(conn.fromPort());

				if (a != null) {
					if (resolved) {
						multipleResolveError(reaction);
					} else {
						resolveReactionWith(reaction, a);
					}
					resolved = true;
				}
			} else if (conn.type() == CConnection.Type.NORMAL) {
				// resolve reaction with guarantee from connected component
				ContractRule g = conn.from().getGuarantees().get(conn.fromPort());
				if (g == null) {
					// recursively resolve other reactions first
					tryRecursiveResolve(conn.from(), conn.fromPort());
				}

				g = conn.from().getGuarantees().get(conn.fromPort());
				if (g != null) {
					if (resolved) {
						multipleResolveError(reaction);
					} else {
						resolveReactionWith(reaction, g);
					}
					resolved = true;
				}
			}
		}
	}

	private void tryRecursiveResolve(final ContractComponent comp, final String port) {
		for (final ContractRule reaction : comp.getReactions()) {
			for (final String output : reaction.getOutputs()) {
				if (port.equals(output)) {
					resolveReaction(reaction);
					return;
				}
			}
		}
	}

	private void resolveReactionWith(final ContractRule reaction, final ContractRule resolver) {
		if (resolver.getType() == SINGLE_EVENT) {
			resolveReactionSingle(reaction, resolver);
		} else if (resolver.getType() == REPETITION) {
			resolveReactionRepetition(reaction, resolver);
		}
	}

	private void resolveReactionSingle(final ContractRule reaction, final ContractRule singleEvent) {
		final CInterval interval = reaction.getInterval().add(singleEvent.getInterval());

		for (final String output : reaction.getOutputs()) {
			final ContractRule rule = new ContractRule(output, interval);
			reaction.getOwner().addRule(rule, system);
		}
		reaction.getOwner().getReactions().remove(reaction);
		// TODO: clock and "n out of m times" ignored for now
	}

	private void resolveReactionRepetition(final ContractRule reaction, final ContractRule repetition) {
		final CInterval interval = repetition.getInterval();
		final CInterval offset = reaction.getInterval().add(repetition.getOffset());

		for (final String output : reaction.getOutputs()) {
			final ContractRule rule = new ContractRule(output, interval, offset, repetition.getJitter());
			reaction.getOwner().addRule(rule, system);
		}
		reaction.getOwner().getReactions().remove(reaction);
		// TODO: clock and "n out of m times" ignored for now
	}

	private void checkNormalConnection(final ContractComponent comp, final CConnection conn) {
		if (comp == conn.from()) {
			// special case where it actually is a direct inner connection
			// (input of component is connected to its output internally)
			checkDirectInnerConnection(comp, conn);
		}
		final ContractRule assumption = comp.getAssumptions().get(conn.toPort());
		final ContractRule guarantee = conn.from().getGuarantees().get(conn.fromPort());

		if (assumption != null && guarantee != null) {
			checkRules(assumption, guarantee);
		}
	}

	private void checkDirectInnerConnection(final ContractComponent comp, final CConnection conn) {
		// input of component is internally directly connected to its output
		final ContractRule assumption = comp.getAssumptions().get(conn.fromPort());
		final ContractRule guarantee = comp.getGuarantees().get(conn.toPort());

		if (assumption != null && guarantee != null) {
			checkRules(guarantee, assumption);
		}
	}

	private void checkFromOuterConnection(final ContractComponent comp, final CConnection conn) {
		final ContractRule assumptionInner = comp.getAssumptions().get(conn.toPort());
		final ContractRule assumptionOuter = conn.from().getAssumptions().get(conn.fromPort());

		if (assumptionInner != null && assumptionOuter != null) {
			checkRules(assumptionInner, assumptionOuter);
		}
	}

	private void checkFromInnerConnection(final ContractComponent comp, final CConnection conn) {
		final ContractRule guaranteeOuter = comp.getGuarantees().get(conn.toPort());
		final ContractRule guaranteeInner = conn.from().getGuarantees().get(conn.fromPort());

		if (guaranteeOuter != null && guaranteeInner != null) {
			checkRules(guaranteeOuter, guaranteeInner);
		}
	}

	private void checkRules(final ContractRule weaker, final ContractRule stronger) {
		if (weaker.getType() == SINGLE_EVENT) {
			if (stronger.getType() == SINGLE_EVENT) {
				if (checkSingleEvents(weaker, stronger)) {
					if (weaker.isFulFilled()) {
						multipleFulfillsError(weaker);
					}
					weaker.setFulFilled(true);
				}
			} else if (stronger.getType() == REPETITION) {
				contractRuleTypeError(weaker, stronger);
			}
		} else if (weaker.getType() == REPETITION) {
			if (stronger.getType() == REPETITION) {
				if (checkRepetitions(weaker, stronger)) {
					if (weaker.isFulFilled()) {
						multipleFulfillsError(weaker);
					}
					weaker.setFulFilled(true);
				}
			} else if (stronger.getType() == SINGLE_EVENT) {
				contractRuleTypeError(weaker, stronger);
			}
		}
	}

	private boolean checkSingleEvents(final ContractRule weaker, final ContractRule stronger) {
		// TODO: clocks ignored for now
		if (!weaker.getInterval().contains(stronger.getInterval())) {
			system.error(Messages.ContractSingleEventMatchError.formatted(weaker.getOwner().getName(),
					weaker.getSinglePort(), weaker.getInterval(), stronger.getOwner().getName(),
					stronger.getInterval()), ContractIssue.Code.SINGLE_EVENT_MATCH);
			return false;
		}
		return true;
	}

	private boolean checkRepetitions(final ContractRule weaker, final ContractRule stronger) {
		// TODO: clocks ignored for now
		final CInterval wO = weaker.getOffset().addJitter(weaker.getJitter());
		final CInterval sO = stronger.getOffset().addJitter(stronger.getJitter());
		if (!wO.contains(sO)) {
			system.error(
					Messages.ContractRepetitionOffsetMatchError.formatted(weaker.getOwner().getName(),
							weaker.getSinglePort(), wO, stronger.getOwner().getName(), sO),
					ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		final CInterval wI = weaker.getInterval().add(weaker.getOffset()).addJitter(weaker.getJitter());
		final CInterval sI = stronger.getInterval().add(stronger.getOffset()).addJitter(stronger.getJitter());
		if (!wI.contains(sI)) {
			system.error(
					Messages.ContractRepetitionIntervalMatchError.formatted(weaker.getOwner().getName(),
							weaker.getSinglePort(), wI, stronger.getOwner().getName(), sI),
					ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		return true;
	}

	private void contractRuleTypeError(final ContractRule r1, final ContractRule r2) {
		system.error(Messages.ContractRuleTypeError.formatted(r1.getOwner().getName(), r1.getSinglePort(), r1.getType(),
				r2.getOwner().getName(), r2.getType()), ContractIssue.Code.TYPE_MATCH);
	}

	private void unresolvedReactionInfo(final ContractComponent comp) {
		system.info(Messages.ContractUnresolvedReactionInfo.formatted(comp.getName()),
				ContractIssue.Code.UNRESOLVED_REACTION);
	}

	private void multipleFulfillsError(final ContractRule rule) {
		system.error(Messages.ContractMultipleFulfillError.formatted(rule.getOwner().getName(), rule.getSinglePort()),
				ContractIssue.Code.MULTIPLE_FULFILL);
	}

	private void multipleResolveError(final ContractRule rule) {
		system.error(Messages.ContractMultipleResolveError.formatted(rule.getOwner().getName()),
				ContractIssue.Code.MULTIPLE_RESOLVE);
	}
}
