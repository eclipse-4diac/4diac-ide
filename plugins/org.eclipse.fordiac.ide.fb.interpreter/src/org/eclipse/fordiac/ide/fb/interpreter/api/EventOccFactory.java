/*******************************************************************************
 * Copyright (c) 2022, 2025 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation+
 *   Bianca Wiesmayr - extended for adapters
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public final class EventOccFactory {

	public static EventOccurrence createFrom(final Event event, final FBRuntimeAbstract runtime) {
		final EventOccurrence createdEo = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		createdEo.setEvent(event);
		createdEo.setActive(true);
		createdEo.setIgnored(false);
		if (null != event) {
			createdEo.setParentFB(getParentFbOfEvent(event));
		}
		createdEo.setFbRuntime(runtime);
		return createdEo;
	}

	private static BlockFBNetworkElement getParentFbOfEvent(final Event event) {
		final var aDecl = InterfacePinUtils.getContainingAdapterDecl(event);
		if (aDecl != null) {
			// event was contained in an adapter
			return aDecl.getBlockFBNetworkElement();
		}
		return event.getBlockFBNetworkElement();
	}

	public static EventOccurrence createFrom(final Event event) {
		return createFrom(event, null);
	}

	public static List<EventOccurrence> createFrom(final List<Event> events, final FBRuntimeAbstract initialRuntime) {
		if (null == events || events.isEmpty()) {
			return Collections.emptyList();
		}
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
