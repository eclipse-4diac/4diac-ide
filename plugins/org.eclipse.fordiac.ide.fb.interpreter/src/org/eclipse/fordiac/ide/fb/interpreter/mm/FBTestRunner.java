/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmenda, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek
 *   	 -cleanup and us factory methods
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public final class FBTestRunner {

	public static Optional<String> runFBTest(final FBType fb, final ServiceSequence seq) {
		return runFBTest(fb, seq, null);
	}

	public static Optional<String> runFBTest(final FBType fb, final ServiceSequence seq, final String startStateName) {
		if (seq.getServiceTransaction().isEmpty()) {
			return Optional.empty();
		}
		final FBRuntimeAbstract rt = RuntimeFactory.createFrom(fb);
		RuntimeFactory.setStartState(rt, startStateName);
		final List<FBTransaction> transaction = TransactionFactory.createFrom(fb, seq, rt);
		final EventManager eventManager = EventManagerFactory.createFrom(transaction);
		EventManagerUtils.process(eventManager);
		return checkResults(seq, eventManager);
	}

	public static Optional<String> checkResults(final ServiceSequence seq, final EventManager eventManager) {
		final EList<ServiceTransaction> expectedResults = seq.getServiceTransaction();
		final EList<Transaction> results = eventManager.getTransactions();

		if (expectedResults.size() != results.size()) { // correct test data
			return Optional.of("test data is incorrect: expected number of elements: " + expectedResults.size() //$NON-NLS-1$
					+ ", received number of elements: " + results.size());  //$NON-NLS-1$
		}

		for (int i = 0; i < expectedResults.size(); i++) {
			final FBTransaction result = (FBTransaction) results.get(i);
			final ServiceTransaction expectedResult = expectedResults.get(i);
			final Optional<String> errorMsg = checkTransaction(result, expectedResult);
			if (errorMsg.isPresent()) {
				return errorMsg;
			}
		}
		return Optional.empty();
	}

	private static Optional<String> checkTransaction(final FBTransaction result,
			final ServiceTransaction expectedResult) {
		// input event was correctly generated
		if (!result.getInputEventOccurrence().getEvent().getName()
				.equals(expectedResult.getInputPrimitive().getEvent())) {
			return Optional.of(
					"Input event " + expectedResult.getInputPrimitive().getEvent() + " was not generated correctly"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// no unwanted output event occurrences
		final long outputEvents = expectedResult.getOutputPrimitive().stream().filter(
				p -> !p.getInterface().getName().toLowerCase().contains(ServiceSequenceUtils.INTERNAL_INTERFACE))
				.count();
		if (outputEvents != result.getOutputEventOccurrences().size()) {
			return Optional.of("Unwanted output event occurrence"); //$NON-NLS-1$
		}

		// check all output primitives
		for (int j = 0; j < outputEvents; j++) {
			final OutputPrimitive p = expectedResult.getOutputPrimitive().get(j);
			final Optional<String> errorMsg = checkOutputPrimitive(result, j, p);
			if (errorMsg.isPresent()) {
				return errorMsg;
			}
		}

		return Optional.empty();
	}

	private static Optional<String> checkOutputPrimitive(final FBTransaction result, final int j,
			final OutputPrimitive p) {
		if (!p.getInterface().getName().toLowerCase().contains(ServiceSequenceUtils.INTERNAL_INTERFACE)) {
			// generated output event is correct
			final String nameGeneratedEvent = result.getOutputEventOccurrences().get(j).getEvent().getName();
			if (!p.getEvent().equals(nameGeneratedEvent)) {
				return Optional.of("Generated output event " + nameGeneratedEvent + " is incorrect"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			// the associated data is correct
			final Optional<String> errorMsg = processParameters(p.getParameters(), result);
			if (!errorMsg.isEmpty()) {
				return Optional
						.of("Parameter values of " + p.getParameters() + " do not match the data: " + errorMsg.get()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return Optional.empty();
	}

	private static Optional<String> processParameters(final String parameters, final FBTransaction result) {
		if ((parameters == null) || parameters.isBlank()) {
			return Optional.empty();
		}
		final int length = result.getOutputEventOccurrences().size();
		final FBRuntimeAbstract captured = result.getOutputEventOccurrences().get(length - 1).getFbRuntime();
		final var parameterList = ServiceSequenceUtils.splitAndCleanList(parameters, ";"); //$NON-NLS-1$
		final SequenceMatcher sm = new SequenceMatcher(getFBType(captured));

		for (final String assumption : parameterList) {
			final Optional<String> errorMsg = sm.matchVariable(assumption, false);
			if (errorMsg.isPresent()) {
				return errorMsg;
			}
		}
		return Optional.empty();
	}

	private static FBType getFBType(final FBRuntimeAbstract captured) {
		final EObject type = captured.getModel();
		if (type instanceof FBType) {
			return (FBType) type;
		}
		return null;
	}

	private FBTestRunner() {
		throw new UnsupportedOperationException();
	}
}
