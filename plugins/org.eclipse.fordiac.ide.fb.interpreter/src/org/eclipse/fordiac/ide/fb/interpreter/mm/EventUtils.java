/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class EventUtils {

	public static String getFullName(final Event event) {
		if (isContainedInAdapter(event)) {
			return getContainingAdapterDecl(event).getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}

	public static boolean isContainedInAdapter(final Event event) {
		return event.eContainer() != null && (event.eContainer().eContainer() instanceof AdapterDeclaration
				|| event.eContainer().eContainer() instanceof AdapterFB
				|| event.eContainer().eContainer() instanceof AdapterType);
	}

	public static AdapterDeclaration getContainingAdapterDecl(final Event event) {
		if (!isContainedInAdapter(event)) {
			return null;
		}
		if (event.eContainer().eContainer() instanceof final AdapterFB afb) {
			return afb.getAdapterDecl();
		}
		return (AdapterDeclaration) event.eContainer().eContainer();
	}

	public static AdapterType getContainingAdapterType(final Event event) {
		if (event.eContainer().eContainer() instanceof final AdapterType atype) {
			return atype;
		}
		return null;
	}

	public static boolean compareEventNames(final Event toCompare, final String name) {
		return getFullName(toCompare).equals(name);
	}

	/**
	 * Searches the interface of the FB type for an event of the provided name
	 *
	 * @param fbType    any FB type definition
	 * @param eventName e.g. "REQ" for event pin or "PLUG.REQ" for event contained
	 *                  in adapter
	 * @return
	 */
	public static Event findEventInInterface(final FBType fbType, final String eventName) {
		Event foundEvent = null;
		if (eventName.contains(".")) { //$NON-NLS-1$
			// event is part of an adapter
			final String[] path = eventName.split("\\."); //$NON-NLS-1$
			if (path.length == 2) {
				final AdapterDeclaration adapter = fbType.getInterfaceList().getAdapter(path[0]);
				foundEvent = adapter.getAdapterFB().getInterface().getEvent(path[1]);
			}
		} else { // event should be in the list of events
			foundEvent = fbType.getInterfaceList().getEvent(eventName);
		}
		return foundEvent;
	}

	private EventUtils() {
		throw new UnsupportedOperationException();
	}

	public static boolean isInput(final Event event) {
		if (isContainedInAdapter(event)) {
			return !event.isIsInput(); // adapter outputs are FB inputs!
		}
		return event.isIsInput();
	}
}
