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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
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

	public static EventManager createEventManager(final FBType fbType, final List<Event> events, final boolean random,
			final String startStateName) {
		if (fbType.getService() == null) {
			fbType.setService(ServiceFactory.createDefaultServiceModel());
		}
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset.createResource(
				URI.createURI(fbType.getTypeEntry().getURI().trimFileExtension().toString() + ".opsem")); //$NON-NLS-1$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);

		final List<EventOccurrence> createEos = EventOccFactory.createFrom(events,
				RuntimeFactory.createFrom(fbType, startStateName));

		final List<FBTransaction> transactions = TransactionFactory.createFrom(createEos, random);
		eventManager.getTransactions().addAll(transactions);
		return eventManager;
	}

	private EventManagerFactory() {
		throw new UnsupportedOperationException();
	}
}
