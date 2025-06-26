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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;
import org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.utils.ContractspecResourceProvider;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

public class ContractSystem {

	private final List<ContractComponent> components;
	private final List<Clock> clocks;
	private final List<CausalFuncDecl> causalFuncDecls;
	private final List<ContractIssue> issues;
	private final Map<SubApp, ContractComponent> processedSubApps;
	private final Set<ContractComponent> seenComponents;

	public ContractSystem() {
		components = new ArrayList<>();
		clocks = new ArrayList<>();
		causalFuncDecls = new ArrayList<>();
		issues = new ArrayList<>();
		processedSubApps = new HashMap<>();
		seenComponents = new HashSet<>();
	}

	/**
	 * Initialize the contract system from a set of SubApps. Connections between
	 * them and other nested SubApps will be automatically added.
	 *
	 * @param subapps the set of SubApps for initialization
	 */
	public void gatherContracts(final Set<SubApp> subapps) {
		for (final SubApp subapp : subapps) {
			gatherContracts(subapp);
		}
	}

	private void gatherContracts(final SubApp subapp) {
		// don't gather twice, which can happen if a nested SubApp was also selected
		if (processedSubApps.containsKey(subapp)) {
			return;
		}

		final ContractComponent comp = createContractComponent(subapp);
		components.add(comp);
		processedSubApps.put(subapp, comp);

		// find connections to other already existing components
		final InterfaceList inter = subapp.getInterface();
		for (final Event e : inter.getEventOutputs()) {
			checkOutputConnections(e, comp, false);
			checkInputConnections(e, comp, true);
		}
		for (final Event e : inter.getEventInputs()) {
			checkInputConnections(e, comp, false);
			checkOutputConnections(e, comp, true);
		}

		// recursively gather nested SubApps of given SubApp
		for (final FBNetworkElement element : subapp.getSubAppNetwork().getNetworkElements()) {
			if (element instanceof final SubApp sapp
					&& sapp.getAttribute(ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME) != null) {
				gatherContracts(sapp);
			}
		}
	}

	private void checkOutputConnections(final Event e, final ContractComponent comp, final boolean inPort) {
		for (final Connection c : e.getOutputConnections()) {
			final ContractComponent neighbour = c.getDestinationElement() instanceof final SubApp sapp
					? processedSubApps.get(sapp)
					: null;
			if (neighbour == comp) {
				// special case to avoid 2 connections with self loops - only done when checking
				// output connections, otherwise self loops don't get created at all
				continue;
			}
			if (neighbour != null) {
				final CConnection.Type type;
				if (!inPort && !c.getDestination().isIsInput()) {
					type = CConnection.Type.FROM_INNER;
				} else if (inPort && c.getDestination().isIsInput()) {
					type = CConnection.Type.FROM_OUTER;
				} else {
					type = CConnection.Type.NORMAL;
				}
				neighbour.addInput(comp, e.getName(), c.getDestination().getName(), type);
			}
		}
	}

	private void checkInputConnections(final Event e, final ContractComponent comp, final boolean outPort) {
		for (final Connection c : e.getInputConnections()) {
			final ContractComponent neighbour = c.getSourceElement() instanceof final SubApp sapp
					? processedSubApps.get(sapp)
					: null;
			if (neighbour != null) {
				final CConnection.Type type;
				if (outPort && !c.getSource().isIsInput()) {
					type = CConnection.Type.FROM_INNER;
				} else if (!outPort && c.getSource().isIsInput()) {
					type = CConnection.Type.FROM_OUTER;
				} else {
					type = CConnection.Type.NORMAL;
				}
				comp.addInput(neighbour, c.getSource().getName(), e.getName(), type);
			}
		}
	}

	private ContractComponent createContractComponent(final SubApp subapp) {
		final ContractComponent comp = new ContractComponent(subapp.getQualifiedName());
		final ContractspecResourceProvider provider = new ContractspecResourceProvider(subapp);
		final XtextResource res = provider.createResource();
		final String contract = subapp.getAttributeValue(ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME);
		addContractRules(contract, res, comp);
		return comp;
	}

	/**
	 * Can be used to add custom defined contract components that are not part of a
	 * SubApp. Useful e.g. for testing.
	 *
	 * @param comp     the component that should be added
	 * @param contract the contract for the component
	 * @param iPrts    input port names for the component or null if it has none
	 * @param oPrts    output port names for the component or null if it has none
	 */
	public void addComponent(final ContractComponent comp, final String contract, final List<String> iPrts,
			final List<String> oPrts) {
		final ContractspecResourceProvider provider = new ContractspecResourceProvider(iPrts, oPrts);
		final XtextResource res = provider.createResource();
		addContractRules(contract, res, comp);
		components.add(comp);
	}

	private void addContractRules(final String contract, final XtextResource resource,
			final ContractComponent component) {
		if (contract == null) {
			return; // SubApp has no contract
		}
		try {
			resource.load(new ByteArrayInputStream(contract.getBytes(StandardCharsets.UTF_8)), null);
		} catch (final IOException e) {
			error(Messages.ContractModelLoadError.formatted(component.getName()), ContractIssue.Code.MODEL_LOADING);
			return;
		}

		final IResourceValidator validator = resource.getResourceServiceProvider().getResourceValidator();
		final List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		if (!issues.isEmpty()) {
			for (final Issue i : issues) {
				final String msg = component.getName() + ": " + i.getMessage(); //$NON-NLS-1$
				issue(msg, ContractIssue.Code.SYNTAX_OR_SEMANTIC, i.getSeverity());
			}
			return;
		}

		if (resource.getContents().isEmpty()) {
			return; // empty contract
		}
		if (!(resource.getContents().get(0) instanceof final ModelImpl model)) {
			error(Messages.ContractModelLoadError.formatted(component.getName()), ContractIssue.Code.MODEL_LOADING);
			return;
		}

		final ContractRuleBuilder ruleBuilder = new ContractRuleBuilder(this, component);
		for (final var timespec : model.getTimeSpec()) {
			switch (timespec) {
			case final SingleEvent se:
				ruleBuilder.addSingleEvent(se);
				break;
			case final Repetition re:
				ruleBuilder.addRepetition(re);
				break;
			case final Reaction re:
				ruleBuilder.addReaction(re);
				break;
			case final Age age:
				ruleBuilder.addAge(age);
				break;
			case final CausalReaction cRe:
				ruleBuilder.addCausalReaction(cRe);
				break;
			case final CausalAge cAge:
				ruleBuilder.addCausalAge(cAge);
				break;
			case final CausalFuncDecl funcDecl:
				causalFuncDecls.add(funcDecl);
				break;
			case final ClockDefinition clock:
				clocks.add(new Clock(clock));
				break;
			default: // can't really happen if no new rules added
				warning(Messages.ContractUnkownRuleWarning.formatted(component.getName()),
						ContractIssue.Code.UNKOWN_RULE);
				break;
			}
		}
	}

	/**
	 * Checks the contract system for possible violations. Should be called after
	 * the system has been initialized via <code>gatherContracts()</code> or
	 * <code>addComponent()</code>. The found issues are then stored and can be
	 * accessed via <code>getIssues()</code>.
	 */
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
			error(Messages.ContractSingleEventMatchError.formatted(weaker.getOwner().getName(), getPortName(weaker),
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
			error(Messages.ContractRepetitionOffsetMatchError.formatted(weaker.getOwner().getName(),
					getPortName(weaker), wO, stronger.getOwner().getName(), sO), ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		final CInterval wI = weaker.getInterval().add(weaker.getOffset()).addJitter(weaker.getJitter());
		final CInterval sI = stronger.getInterval().add(stronger.getOffset()).addJitter(stronger.getJitter());
		if (!wI.contains(sI)) {
			error(Messages.ContractRepetitionIntervalMatchError.formatted(weaker.getOwner().getName(),
					getPortName(weaker), wI, stronger.getOwner().getName(), sI), ContractIssue.Code.REPETITION_MATCH);
			return false;
		}
		return true;
	}

	private void contractRuleTypeError(final ContractRule r1, final ContractRule r2) {
		error(Messages.ContractRuleTypeError.formatted(r1.getOwner().getName(), getPortName(r1), r1.getType(),
				r2.getOwner().getName(), r2.getType()), ContractIssue.Code.TYPE_MATCH);
	}

	private void unresolvedReactionInfo(final ContractRule rule) {
		info(Messages.ContractUnresolvedReactionInfo.formatted(rule.getOwner().getName()),
				ContractIssue.Code.UNRESOLVED_REACTION);
	}

	private void multipleFulfillsError(final ContractRule rule) {
		error(Messages.ContractMultipleFulfillError.formatted(rule.getOwner().getName(), getPortName(rule)),
				ContractIssue.Code.MULTIPLE_FULFILL);
	}

	private void multipleResolveError(final ContractRule rule) {
		error(Messages.ContractMultipleResolveError.formatted(rule.getOwner().getName()),
				ContractIssue.Code.MULTIPLE_RESOLVE);
	}

	private static String getPortName(final ContractRule rule) {
		return rule.isAssumption() ? rule.getInput() : rule.getOutput();
	}

	/**
	 * Get a list of all issues that occurred when checking the system by using
	 * <code>checkSystem()</code>.
	 *
	 * @return the list of issues
	 */
	public List<ContractIssue> getIssues() {
		return issues;
	}

	public int getNComponents() {
		return components.size();
	}

	private void error(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.ERROR);
	}

	private void warning(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.WARNING);
	}

	private void info(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.INFO);
	}

	private void issue(final String message, final ContractIssue.Code code, final Severity severity) {
		addIssue(new ContractIssue(message, code, severity));
	}

	public void addIssue(final ContractIssue issue) {
		issues.add(issue);
	}
}
