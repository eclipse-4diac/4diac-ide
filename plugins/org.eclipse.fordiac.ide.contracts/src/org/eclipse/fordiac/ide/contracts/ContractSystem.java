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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncName;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.CausalRelation;
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;
import org.eclipse.fordiac.ide.contractSpec.TimeSpec;
import org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl;
import org.eclipse.fordiac.ide.contracts.ContractRule.Type;
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

/**
 * Represents a network of ContractComponents, which can be constructed from a
 * set of SubApps. Static/Dynamic checks are then performed on this network.
 * Also contains a list of errors which have occurred during checking.
 */
public class ContractSystem {

	private final List<ContractComponent> components;
	private final List<Clock> clocks;
	private final List<ContractIssue> issues;
	private final Map<SubApp, ContractComponent> processedSubApps;

	public ContractSystem() {
		components = new ArrayList<>();
		clocks = new ArrayList<>();
		issues = new ArrayList<>();
		processedSubApps = new HashMap<>();
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

		addContractRules(model.getTimeSpec(), component);
	}

	private void addContractRules(final List<TimeSpec> timeSpecs, final ContractComponent component) {
		final List<CausalFuncDecl> causalFuncDecls = new ArrayList<>();
		final ContractRuleBuilder ruleBuilder = new ContractRuleBuilder(this, component);
		for (final var timespec : timeSpecs) {
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
			default: // can't happen unless new rules are added to the language
				warning(Messages.ContractUnkownRuleWarning.formatted(component.getName()),
						ContractIssue.Code.UNKOWN_RULE);
				break;
			}
		}

		// find associated causal relations for rules
		for (final ContractRule reaction : component.getReactions()) {
			associateCausalFuncDecl(reaction, causalFuncDecls);
		}
	}

	private static void associateCausalFuncDecl(final ContractRule reaction, final List<CausalFuncDecl> funcDecls) {
		if (reaction.getType() == Type.CAUSAL_AGE) {
			for (final CausalFuncDecl funcDecl : funcDecls) {
				if (funcDecl.getFuncName() == CausalFuncName.AGE
						&& funcDecl.getPort1().getName().equals(reaction.getOutputs().getFirst())
						&& funcDecl.getPort2().getName().equals(reaction.getInputs().getFirst())) {
					reaction.setCausalRelation(funcDecl.getRelation());
					return;
				}
			}
		} else if (reaction.getType() == Type.CAUSAL_REACTION) {
			for (final CausalFuncDecl funcDecl : funcDecls) {
				if (funcDecl.getFuncName() == CausalFuncName.REACTION
						&& funcDecl.getPort1().getName().equals(reaction.getInputs().getFirst())
						&& funcDecl.getPort2().getName().equals(reaction.getOutputs().getFirst())) {
					reaction.setCausalRelation(funcDecl.getRelation());
					return;
				}
			}
		}
		// no fitting causal function declaration found, set relation to default (FIFO)
		reaction.setCausalRelation(CausalRelation.FIFO);
	}

	/**
	 * Checks the contract system for possible violations statically. Should be
	 * called after the system has been initialized via
	 * <code>gatherContracts()</code> or <code>addComponent()</code>. The found
	 * issues are then stored and can be accessed via <code>getIssues()</code>.
	 */
	public void performStaticCheck() {
		final StaticContractChecker checker = new StaticContractChecker(this, components);
		checker.checkSystem();
	}

	/**
	 * Checks if a given list of event occurrences violates any of the contracts.
	 * Should be called after the system has been initialized via
	 * <code>gatherContracts()</code> or <code>addComponent()</code>. The found
	 * issues are then stored and can be accessed via <code>getIssues()</code>.
	 *
	 * @param eventOccurences the list of (recorded) event occurrences
	 * @return a data structure with more information about the result
	 */
	public DynamicCheckResult performDynamicCheck(final List<EventOccurrence> eventOccurences) {
		final DynamicContractChecker checker = new DynamicContractChecker(this, components, eventOccurences);
		return checker.checkSystem();
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

	public void error(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.ERROR);
	}

	public void warning(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.WARNING);
	}

	public void info(final String message, final ContractIssue.Code code) {
		issue(message, code, Severity.INFO);
	}

	public void issue(final String message, final ContractIssue.Code code, final Severity severity) {
		addIssue(new ContractIssue(message, code, severity));
	}

	public void addIssue(final ContractIssue issue) {
		issues.add(issue);
	}
}
