/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class TransactionFactory {

	public static Transaction createFrom(final EventOccurrence inputEO) {
		final FBTransaction createdTr = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		createdTr.setInputEventOccurrence(inputEO);
		return null;
	}

	public static List<Transaction> createFrom(final List<EventOccurrence> inputEOs) {
		final int size = inputEOs.size();
		final List<Transaction> trans = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			trans.add(createFrom(inputEOs.get(i)));
		}
		return trans;
	}

	public static Transaction createFrom(final Event inputEvent, final FBRuntimeAbstract runtime) {
		return createFrom(EventOccFactory.createFrom(inputEvent, runtime));
	}

	public static List<Transaction> createFrom(final List<Event> inputEvents, final FBRuntimeAbstract initialRuntime) {
		return createFrom(EventOccFactory.createFrom(inputEvents, initialRuntime));
	}

	private TransactionFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
