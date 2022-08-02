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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class EventOccFactory {

	public static EventOccurrence createFrom(final Event event, final FBRuntimeAbstract runtime) {
		final EventOccurrence createdEo = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		createdEo.setEvent(event);
		createdEo.setActive(true);
		createdEo.setIgnored(false);
		createdEo.setParentFB(event.getFBNetworkElement());
		createdEo.setFbRuntime(runtime);
		return createdEo;
	}

	/** @param events
	 * @return */
	public static List<EventOccurrence> createFrom(final List<Event> events, final FBRuntimeAbstract initialRuntime) {
		final List<EventOccurrence> createdEOs = new ArrayList<>();
		for (final Event e : events) {
			createdEOs.add(createFrom(e, null));
		}
		createdEOs.get(0).setFbRuntime(initialRuntime);
		return createdEOs;
	}

	private EventOccFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}

}
