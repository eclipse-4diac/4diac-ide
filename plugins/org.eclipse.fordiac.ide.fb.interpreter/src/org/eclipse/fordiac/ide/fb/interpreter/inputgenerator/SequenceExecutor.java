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
package org.eclipse.fordiac.ide.fb.interpreter.inputgenerator;

import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public final class SequenceExecutor {

	public static EList<Transaction> executeRandomSequence(final FBType inputfb, final int count) {
		return executeRandomSequence(inputfb, count, false);
	}

	public static EList<Transaction> executeRandomSequence(final FBType inputfb, final int count,
			final boolean needRandomData) {
		final List<Event> events = InputGenerator.getRandomEventsSequence(inputfb, count);
		if (events.isEmpty()) {
			return ECollections.emptyEList();
		}
		final List<FBTransaction> trans = TransactionFactory.createFrom(events, RuntimeFactory.createFrom(inputfb),
				needRandomData);
		final EventManager eventManager = EventManagerFactory.createFrom(trans);
		EventManagerUtils.process(eventManager);
		return eventManager.getTransactions();
	}

	private SequenceExecutor() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
