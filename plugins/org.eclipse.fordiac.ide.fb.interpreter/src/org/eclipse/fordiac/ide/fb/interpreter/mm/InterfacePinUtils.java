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
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class InterfacePinUtils {

	public static String getFullName(final IInterfaceElement pin) {
		if (isContainedInAdapter(pin)) {
			return getContainingAdapterDecl(pin).getName() + "." + pin.getName(); //$NON-NLS-1$
		}
		return pin.getName();
	}

	public static boolean isContainedInAdapter(final IInterfaceElement event) {
		return event.eContainer() != null && (event.eContainer().eContainer() instanceof AdapterDeclaration
				|| event.eContainer().eContainer() instanceof AdapterFB
				|| event.eContainer().eContainer() instanceof AdapterType);
	}

	public static AdapterDeclaration getContainingAdapterDecl(final IInterfaceElement event) {
		if (!isContainedInAdapter(event)) {
			return null;
		}
		if (event.eContainer().eContainer() instanceof final AdapterFB afb) {
			return afb.getAdapterDecl();
		}
		return (AdapterDeclaration) event.eContainer().eContainer();
	}

	public static IInterfaceElement getContainedPin(final AdapterDeclaration aDecl, final String pinName) {
		return aDecl.getAdapterFB().getInterface().getInterfaceElement(pinName);
	}

	public static AdapterType getContainingAdapterType(final IInterfaceElement event) {
		return switch (event.eContainer().eContainer()) {
		case final AdapterType atype -> atype;
		default -> null;
		};
	}

	public static boolean compareEventNames(final Event toCompare, final String name) {
		return getFullName(toCompare).equals(name);
	}

	/**
	 * Searches the interface of the FB type for an event of the provided name
	 *
	 * @param fbType any FB type definition
	 * @param iel    e.g. "REQ" for event pin or "PLUG.REQ" for event contained in
	 *               adapter
	 * @return
	 */
	public static Event findEventInInterface(final FBType fbType, final String eventName) {
		return switch (findPinInInterface(fbType, eventName)) {
		case final Event event -> event;
		default -> null;
		};
	}

	public static Event findEventInInterface(final FBType fbType, final Event source) {
		return switch (findPinInInterface(fbType, source)) {
		case final Event event -> event;
		default -> null;
		};
	}

	public static VarDeclaration findVariableInInterface(final FBType fbType, final String varName) {
		return switch (findPinInInterface(fbType, varName)) {
		case final VarDeclaration varDecl -> varDecl;
		default -> null;
		};
	}

	public static VarDeclaration findVariableInInterface(final FBType fbType, final VarDeclaration source) {
		return switch (findPinInInterface(fbType, source)) {
		case final VarDeclaration varDecl -> varDecl;
		default -> null;
		};
	}

	private static IInterfaceElement findPinInInterface(final FBType fbType, final String pinName) {
		IInterfaceElement foundEvent = fbType.getInterfaceList().getInterfaceElement(pinName);
		if (foundEvent == null && pinName.contains(".")) { //$NON-NLS-1$
			// event is part of an adapter
			final String[] path = pinName.split("\\."); //$NON-NLS-1$
			if (path.length == 2) {
				final AdapterDeclaration adapter = fbType.getInterfaceList().getAdapter(path[0]);
				foundEvent = adapter.getAdapterFB().getInterface().getEvent(path[1]);
			}
		}
		return foundEvent;
	}

	public static IInterfaceElement findPinInInterface(final FBType fbType, final IInterfaceElement iel) {
		IInterfaceElement foundPin = fbType.getInterfaceList().getInterfaceElement(iel.getName());
		if (foundPin == null && isContainedInAdapter(iel)) {
			// pin is part of an adapter
			final String sourceAdapter = getContainingAdapterDecl(iel).getName();
			final AdapterDeclaration adapter = fbType.getInterfaceList().getAdapter(sourceAdapter);
			foundPin = adapter.getAdapterFB().getInterface().getInterfaceElement(iel.getName());
		}
		return foundPin;
	}

	public static IInterfaceElement findPinInInterface(final BlockFBNetworkElement fb, final IInterfaceElement iel) {
		IInterfaceElement foundPin = fb.getInterface().getInterfaceElement(iel.getName());
		if (foundPin == null && isContainedInAdapter(iel)) {
			// pin is part of an adapter
			final String sourceAdapter = getContainingAdapterDecl(iel).getName();
			final AdapterDeclaration adapter = fb.getInterface().getAdapter(sourceAdapter);
			foundPin = adapter.getAdapterFB().getInterface().getInterfaceElement(iel.getName());
		}
		return foundPin;
	}

	public static VarDeclaration findVariableInInterface(final BlockFBNetworkElement instance,
			final VarDeclaration source) {
		return switch (findPinInInterface(instance, source)) {
		case final VarDeclaration varDecl -> varDecl;
		default -> null;
		};
	}

	public static Event findEventInInterface(final BlockFBNetworkElement instance, final Event source) {
		return switch (findPinInInterface(instance, source)) {
		case final Event event -> event;
		default -> null;
		};
	}

	public static boolean isInput(final IInterfaceElement pin) {
		if (isContainedInAdapter(pin)) {
			return !pin.isIsInput(); // adapter outputs are FB inputs!
		}
		return pin.isIsInput();
	}

	public static boolean isCompositeTypeInterfacePin(final IInterfaceElement pin) {
		return pin.getInterfaceList().eContainer() instanceof CompositeFBType;
	}

	private InterfacePinUtils() {
		throw new UnsupportedOperationException();
	}
}
