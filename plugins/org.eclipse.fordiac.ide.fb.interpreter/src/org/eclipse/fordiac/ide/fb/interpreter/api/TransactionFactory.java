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
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class TransactionFactory {

	public static FBTransaction createFrom(final EventOccurrence inputEO) {
		final FBTransaction createdTr = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		createdTr.setInputEventOccurrence(inputEO);
		return createdTr;
	}

	public static List<FBTransaction> createFrom(final List<EventOccurrence> inputEOs) {
		final int size = inputEOs.size();
		final List<FBTransaction> trans = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			trans.add(createFrom(inputEOs.get(i)));
		}
		return trans;
	}

	public static FBTransaction createFrom(final Event inputEvent, final FBRuntimeAbstract runtime) {
		return createFrom(EventOccFactory.createFrom(inputEvent, runtime));
	}

	public static List<FBTransaction> createFrom(final List<Event> inputEvents,
			final FBRuntimeAbstract initialRuntime) {
		return createFrom(EventOccFactory.createFrom(inputEvents, initialRuntime));
	}

	public static FBTransaction createFrom(final FBType fb, final ServiceTransaction st) {
		final String inputEvent = st.getInputPrimitive().getEvent();
		if (inputEvent != null) {
			final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
			if (eventPin == null) {
				throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
			}
			final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
			eventOccurrence.setEvent(eventPin);
			final FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
			transaction.setInputEventOccurrence(eventOccurrence);
			// process parameter and set variables
			final String inputParameters = st.getInputPrimitive().getParameters();
			final var paramList = ServiceSequenceUtils.getParametersFromString(inputParameters);
			for (final List<String> parameter : paramList) {
				if (parameter.size() == 2) {
					VariableUtils.setVariable(fb, parameter.get(0), parameter.get(1));
				}
			}
			return transaction;
		}
		return null;
	}

	public static Collection<Transaction> createFrom(final BasicFBType fb, final ServiceSequence seq,
			final BasicFBTypeRuntime runtime) {
		final List<Transaction> transactions = new ArrayList<>();
		for (final ServiceTransaction st : seq.getServiceTransaction()) {
			final FBTransaction t = createFrom(fb, st);
			if (t != null) {
				transactions.add(t);
			}
		}
		// The first transaction has a copy of the BasicFBTypeRuntime
		final Copier copier = new Copier();
		final BasicFBTypeRuntime copyBasicFBTypeRuntime = (BasicFBTypeRuntime) copier.copy(runtime);
		copier.copyReferences();
		transactions.get(0).getInputEventOccurrence().setFbRuntime(copyBasicFBTypeRuntime);
		return transactions;
	}

	private TransactionFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
