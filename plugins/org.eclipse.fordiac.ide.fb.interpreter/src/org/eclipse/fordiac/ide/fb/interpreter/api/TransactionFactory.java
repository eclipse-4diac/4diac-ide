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
 *     - extracted code from AbstractInterpreterTest, added factory methods
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Trace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class TransactionFactory {

	public static FBTransaction createFrom(final EventOccurrence inputEO) {
		return createFrom(inputEO, false);
	}

	private static FBTransaction createFrom(final EventOccurrence inputEO, final boolean addRandomData) {
		final FBTransaction createdTr = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		createdTr.setInputEventOccurrence(inputEO);
		if (addRandomData && (null != inputEO)) {
			createdTr.getInputVariables().addAll(InputGenerator.getRandomData(inputEO.getEvent()));
		}
		return createdTr;
	}

	public static List<FBTransaction> createFrom(final List<EventOccurrence> inputEOs) {
		return createFrom(inputEOs, false);
	}

	public static List<FBTransaction> createFrom(final List<EventOccurrence> inputEOs, final boolean addRandomData) {
		final List<FBTransaction> transactions = new ArrayList<>();
		for (final EventOccurrence eo : inputEOs) {
			transactions.add(createFrom(eo, addRandomData));
		}
		return transactions;
	}

	public static FBTransaction createFrom(final Event inputEvent, final FBRuntimeAbstract runtime) {
		return createFrom(inputEvent, runtime, false);
	}

	public static FBTransaction createFrom(final Event inputEvent, final FBRuntimeAbstract runtime,
			final boolean addRandomData) {
		return createFrom(EventOccFactory.createFrom(inputEvent, runtime), addRandomData);
	}

	public static List<FBTransaction> createFrom(final List<Event> inputEvents,
			final FBRuntimeAbstract initialRuntime) {
		return createFrom(EventOccFactory.createFrom(inputEvents, initialRuntime));
	}

	public static FBTransaction createFrom(final FBType fb, final ServiceTransaction st) {
		if ((st == null) || (st.getInputPrimitive() == null)) {
			throw new IllegalArgumentException("TransactionFactory could not access ServiceTransaction: invalid"); //$NON-NLS-1$
		}
		final String inputEvent = st.getInputPrimitive().getEvent();
		if (inputEvent != null) {
			final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
			if (eventPin == null) {
				throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
			}
			final FBTransaction transaction = createFrom(eventPin, null);
			// process parameter and set variables
			final String inputParameters = st.getInputPrimitive().getParameters();
			final var paramList = ServiceSequenceUtils.getParametersFromString(inputParameters);
			for (final List<String> parameter : paramList) {
				if (parameter.size() == 2) {
					final IInterfaceElement el = EcoreUtil
							.copy(fb.getInterfaceList().getInterfaceElement(parameter.get(0).strip()));

					VariableUtils.setVariable((VarDeclaration) el, parameter.get(1));
					transaction.getInputVariables().add((VarDeclaration) el);
				}
			}
			return transaction;
		}
		return null;
	}

	public static List<FBTransaction> createFrom(final FBType fb, final ServiceSequence seq,
			final FBRuntimeAbstract runtimecopy) {
		if (seq.getServiceTransaction().isEmpty()) {
			return Collections.emptyList();
		}
		final List<FBTransaction> transactions = new ArrayList<>();
		for (final ServiceTransaction st : seq.getServiceTransaction()) {
			final FBTransaction t = createFrom(fb, st);
			if (t != null) {
				transactions.add(t);
			}
		}
		if (!transactions.isEmpty()) {
			transactions.get(0).getInputEventOccurrence().setFbRuntime(runtimecopy);
		}
		return transactions;

	}

	public static List<FBTransaction> createFrom(final List<Event> inputEvents, final FBRuntimeAbstract initialRuntime,
			final boolean addRandomData) {
		return createFrom(EventOccFactory.createFrom(inputEvents, initialRuntime), addRandomData);
	}

	public static ServiceTransaction addTransaction(final ServiceSequence seq,
			final org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder fbtrans) {
		final ServiceTransaction transaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		seq.getServiceTransaction().add(transaction);
		if (fbtrans.getInputEventName() != null) {
			final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
			inputPrimitive.setEvent(fbtrans.getInputEventName());
			transaction.setInputPrimitive(inputPrimitive);
		}

		if (!fbtrans.getOutputEventNames().isEmpty()) {
			for (final String event : fbtrans.getOutputEventNames()) {
				final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
				outputPrimitive.setEvent(event);
				outputPrimitive.setInterface(((Service) seq.eContainer()).getLeftInterface());
				outputPrimitive.setParameters(""); //$NON-NLS-1$
				for (final String parameter : fbtrans.getOutputParameters()) {
					outputPrimitive.setParameters(outputPrimitive.getParameters() + parameter + ";"); //$NON-NLS-1$
				}
				transaction.getOutputPrimitive().add(outputPrimitive);
			}
		}
		return transaction;
	}

	public static void setStartState(final Transaction transaction, final String startStateName) {
		if (transaction.getInputEventOccurrence() != null) {
			RuntimeFactory.setStartState(transaction.getInputEventOccurrence().getFbRuntime(), startStateName);
		}
	}

	public static Transaction addTraceInfoTo(final Transaction transaction) {
		final BasicEList<Transaction> asList = new BasicEList<>();
		asList.add(transaction);
		return addTraceInfoTo(asList).get(0);
	}

	private static void createBasicFbTrace(final Transaction transaction) {
		final Trace trace = OperationalSemanticsFactory.eINSTANCE.createEccTrace();
		((FBTransaction) transaction).setTrace(trace);
	}

	public static List<Transaction> addTraceInfoTo(final EList<Transaction> transactions) {
		if (!transactions.isEmpty()) {
			final FBRuntimeAbstract fbRuntime = transactions.get(0).getInputEventOccurrence().getFbRuntime();
			if (fbRuntime != null) {
				final EObject executedModel = fbRuntime.getModel();
				if (!(executedModel instanceof BasicFBType) || !(transactions.get(0) instanceof FBTransaction)) {
					throw new UnsupportedOperationException("not implemented"); //$NON-NLS-1$
				}
				transactions.forEach(TransactionFactory::createBasicFbTrace);
			}
		}
		return transactions;
	}

	private TransactionFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
