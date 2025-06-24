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
			for (final ContractRule guarantee : comp.getGuarantees().values()) {
				switch (guarantee.getType()) {
				case REACTION:
				case AGE:
				case CAUSAL_REACTION:
				case CAUSAL_AGE:
					seenComponents.clear();
					resolveReaction(guarantee);
					break;
				default:
					break;
				}
			}
		}

		// check all connections
		for (final ContractComponent comp : components) {
			for (final CConnection conn : comp.getInputs()) {
				switch (conn.type()) {
				case CConnection.Type.NORMAL:
					checkNormalConnection(comp, conn);
					break;
				case CConnection.Type.FROM_OUTER:
					checkFromOuterConnection(comp, conn);
					break;
				case CConnection.Type.FROM_INNER:
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

		// check for assumption on input port
		final ContractComponent comp = reaction.getOwner();
		final ContractRule assumption = comp.getAssumptions().get(reaction.getInput());
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

				if (a != null && isResolver(a)) {
					if (resolved) {
						multipleResolveError(reaction);
					} else {
						resolveReactionWith(reaction, a);
					}
					resolved = true;
				}
			} else if (conn.type() == CConnection.Type.NORMAL) {
				// resolve reaction with guarantee from connected component
				final ContractRule g = conn.from().getGuarantees().get(conn.fromPort());

				if (g != null) {
					if (!isResolver(g)) {
						resolveReaction(g); // recursively resolve other reactions
					}
					if (isResolver(g)) {
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
	}

	private static boolean isResolver(final ContractRule rule) {
		return rule.getType() == SINGLE_EVENT || rule.getType() == REPETITION;
	}

	private static void resolveReactionWith(final ContractRule reaction, final ContractRule resolver) {
		if (resolver.getType() == SINGLE_EVENT) {
			resolveReactionSingle(reaction, resolver);
		} else if (resolver.getType() == REPETITION) {
			resolveReactionRepetition(reaction, resolver);
		}
	}

	private static void resolveReactionSingle(final ContractRule reaction, final ContractRule singleEvent) {
		reaction.setType(SINGLE_EVENT);
		reaction.setInterval(reaction.getInterval().add(singleEvent.getInterval()));
		reaction.setInput(null);
		// TODO: clock, "once" and "n out of m times" ignored for now
	}

	private static void resolveReactionRepetition(final ContractRule reaction, final ContractRule repetition) {
		final CInterval reactionInter = reaction.getInterval();
		reaction.setType(REPETITION);
		reaction.setInterval(repetition.getInterval());
		reaction.setOffset(reactionInter.add(repetition.getOffset()));
		reaction.setJitter(repetition.getJitter());
		reaction.setInput(null);
		// TODO: clock, "once" and "n out of m times" ignored for now
	}

	private void checkNormalConnection(final ContractComponent comp, final CConnection conn) {
		final ContractRule assumption = comp.getAssumptions().get(conn.toPort());
		final ContractRule guarantee = conn.from().getGuarantees().get(conn.fromPort());

		if (assumption != null && guarantee != null) {
			checkRules(assumption, guarantee);
		} else if (comp == conn.from()) {
			// special case where it actually is a direct inner connection
			// (input of component is connected to its output internally)
			checkDirectInnerConnection(comp, conn);
		}
	}

	private void checkDirectInnerConnection(final ContractComponent comp, final CConnection conn) {
		// input of component is internally directly connected to its output
		final ContractRule assumption = comp.getAssumptions().get(conn.fromPort());
		final ContractRule guarantee = conn.from().getGuarantees().get(conn.toPort());

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
			} else {
				unresolvedReactionInfo(stronger);
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
			} else {
				unresolvedReactionInfo(stronger);
			}
		} else {
			unresolvedReactionInfo(weaker);
		}
	}

	private boolean checkSingleEvents(final ContractRule weaker, final ContractRule stronger) {
		// TODO: clocks ignored for now
		if (!weaker.getInterval().contains(stronger.getInterval())) {
			system.error(
					Messages.ContractSingleEventMatchError.formatted(weaker.getOwner().getName(), weaker.getPortName(),
							weaker.getInterval(), stronger.getOwner().getName(), stronger.getInterval()),
					ContractIssue.Code.SINGLE_EVENT_MATCH);
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
							weaker.getPortName(), wO, stronger.getOwner().getName(), sO),
					ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		final CInterval wI = weaker.getInterval().add(weaker.getOffset()).addJitter(weaker.getJitter());
		final CInterval sI = stronger.getInterval().add(stronger.getOffset()).addJitter(stronger.getJitter());
		if (!wI.contains(sI)) {
			system.error(
					Messages.ContractRepetitionIntervalMatchError.formatted(weaker.getOwner().getName(),
							weaker.getPortName(), wI, stronger.getOwner().getName(), sI),
					ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		return true;
	}

	private void contractRuleTypeError(final ContractRule r1, final ContractRule r2) {
		system.error(Messages.ContractRuleTypeError.formatted(r1.getOwner().getName(), r1.getPortName(), r1.getType(),
				r2.getOwner().getName(), r2.getType()), ContractIssue.Code.TYPE_MATCH);
	}

	private void unresolvedReactionInfo(final ContractRule rule) {
		system.info(Messages.ContractUnresolvedReactionInfo.formatted(rule.getOwner().getName()),
				ContractIssue.Code.UNRESOLVED_REACTION);
	}

	private void multipleFulfillsError(final ContractRule rule) {
		system.error(Messages.ContractMultipleFulfillError.formatted(rule.getOwner().getName(), rule.getPortName()),
				ContractIssue.Code.MULTIPLE_FULFILL);
	}

	private void multipleResolveError(final ContractRule rule) {
		system.error(Messages.ContractMultipleResolveError.formatted(rule.getOwner().getName()),
				ContractIssue.Code.MULTIPLE_RESOLVE);
	}
}
