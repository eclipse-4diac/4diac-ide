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

import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public final class EventManagerFactory {
	public static EventManager createFrom(final List<? extends Transaction> trans) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions().addAll(trans);
		return eventManager;
	}

	public static EventManager createFrom(final List<Event> events, final FBType fbtype) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions().addAll(TransactionFactory.createFrom(events, RuntimeFactory.createFrom(fbtype)));
		return eventManager;
	}

	public static EventManager createFrom(final List<Event> events, final FBNetwork network) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions()
		.addAll(TransactionFactory.createFrom(events, RuntimeFactory.createFrom(network)));
		return eventManager;
	}

	public static EventManager createFrom(final Event event, final FBNetwork network) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions().add(TransactionFactory.createFrom(event, RuntimeFactory.createFrom(network)));
		return eventManager;
	}

	private EventManagerFactory() {
		throw new UnsupportedOperationException();
	}
}
