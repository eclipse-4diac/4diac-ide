/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial commit of contract specification editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.Messages;
import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncName;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.EventList;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Port;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class ContractSpecValidator extends AbstractContractSpecValidator {
	private static final int INPUT = 1;
	private static final int OUTPUT = 0;

	public static final String EMPTY_INTERVAL = "emptyInterval"; //$NON-NLS-1$
	public static final String SPECIAL_EMPTY_INTERVAL = "specialEmptyInterval"; //$NON-NLS-1$
	public static final String DEGENERATE_INTERVAL = "degenerateInterval"; //$NON-NLS-1$
	public static final String PORT_NOT_INPUT = "portNotInput"; //$NON-NLS-1$
	public static final String PORT_NOT_OUTPUT = "portNotOutput"; //$NON-NLS-1$

	@Check
	public void checkInterval(final Interval interval) {
		if (interval.getTime() != null) {
			return; // was a single value -> no check needed
		}

		if (interval.getLbValue() > interval.getUbValue()) { // e.g. [10, 5]
			warning(Messages.EmptyIntervalWarning, ContractSpecPackage.Literals.INTERVAL__LB_VALUE, EMPTY_INTERVAL);
		} else if (interval.getLbValue() == interval.getUbValue()) {
			if (interval.getLBound().equals("]") || interval.getUBound().equals("[")) { //$NON-NLS-1$ //$NON-NLS-2$
				warning(Messages.EmptyIntervalWarning, ContractSpecPackage.Literals.INTERVAL__LB_VALUE,
						SPECIAL_EMPTY_INTERVAL); // e.g. ]10, 10] or [10, 10[ or ]10, 10[
			} else {
				warning(Messages.DegenerateIntervalWarning, ContractSpecPackage.Literals.INTERVAL__LB_VALUE,
						DEGENERATE_INTERVAL); // e.g. [10, 10]
			}
		}
	}

	@Check
	public void checkSingleEvent(final SingleEvent singleEvent) {
		checkPortsOfSameType(singleEvent.getEvents(), ContractSpecPackage.Literals.SINGLE_EVENT__EVENTS);
	}

	@Check
	public void checkRepetition(final Repetition repetition) {
		checkPortsOfSameType(repetition.getEvents(), ContractSpecPackage.Literals.REPETITION__EVENTS);
	}

	@Check
	public void checkReaction(final Reaction reaction) {
		checkPortsOfType(reaction.getInput(), INPUT, ContractSpecPackage.Literals.REACTION__INPUT);
		checkPortsOfType(reaction.getOutput(), OUTPUT, ContractSpecPackage.Literals.REACTION__OUTPUT);
	}

	@Check
	public void checkCausalReaction(final CausalReaction causalReaction) {
		checkPortsOfType(causalReaction.getInput(), INPUT, ContractSpecPackage.Literals.CAUSAL_REACTION__INPUT);
		checkPortsOfType(causalReaction.getOutput(), OUTPUT, ContractSpecPackage.Literals.CAUSAL_REACTION__OUTPUT);
	}

	@Check
	public void checkAge(final Age age) {
		checkPortsOfType(age.getOutput(), OUTPUT, ContractSpecPackage.Literals.AGE__OUTPUT);
		checkPortsOfType(age.getInput(), INPUT, ContractSpecPackage.Literals.AGE__INPUT);
	}

	@Check
	public void checkCausalAge(final CausalAge causalAge) {
		checkPortsOfType(causalAge.getOutput(), OUTPUT, ContractSpecPackage.Literals.CAUSAL_AGE__OUTPUT);
		checkPortsOfType(causalAge.getInput(), INPUT, ContractSpecPackage.Literals.CAUSAL_AGE__INPUT);
	}

	@Check
	public void checkCausalFuncDecl(final CausalFuncDecl causalFuncDecl) {
		if (causalFuncDecl.getFuncName() == CausalFuncName.REACTION) {
			checkPortOfType(causalFuncDecl.getPort1(), INPUT, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__PORT1);
			checkPortOfType(causalFuncDecl.getPort2(), OUTPUT, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__PORT2);
		} else {
			checkPortOfType(causalFuncDecl.getPort1(), OUTPUT, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__PORT1);
			checkPortOfType(causalFuncDecl.getPort2(), INPUT, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__PORT2);
		}
	}

	private void checkPortsOfSameType(final EventList list, final EStructuralFeature feature) {
		if (list != null && list.getEvents() != null && !list.getEvents().isEmpty()) {
			final int firstType = list.getEvents().get(0).getPort().getIsInput();
			checkPortsOfType(list.getEvents(), firstType, feature);
		}
	}

	private void checkPortsOfType(final EventExpr expr, final int type, final EStructuralFeature feature) {
		if (expr.getEvent() != null) {
			checkPortOfType(expr.getEvent().getPort(), type, feature);
		} else if (expr.getEvents() != null) {
			checkPortsOfType(expr.getEvents().getEvents(), type, feature);
		}
	}

	private void checkPortsOfType(final EList<EventSpec> list, final int type, final EStructuralFeature feature) {
		if (list != null) {
			for (final EventSpec es : list) {
				checkPortOfType(es.getPort(), type, feature);
			}
		}
	}

	private void checkPortsOfType(final EventSpec es, final int type, final EStructuralFeature feature) {
		if (es != null) {
			checkPortOfType(es.getPort(), type, feature);
		}
	}

	private void checkPortOfType(final Port port, final int type, final EStructuralFeature feature) {
		if (port != null && port.getIsInput() != type) {
			if (type == INPUT) {
				final String s = String.format(Messages.InputPortExpectedError, port.getName());
				error(s, feature, PORT_NOT_INPUT);
			} else {
				final String s = String.format(Messages.OutputPortExpectedError, port.getName());
				error(s, feature, PORT_NOT_OUTPUT);
			}
		}
	}
}
