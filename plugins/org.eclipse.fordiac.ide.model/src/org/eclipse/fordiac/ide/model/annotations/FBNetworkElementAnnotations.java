/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *   			   - initial API and implementation and/or initial documentation
 *   Monika Wenger - extracted the model helper methods into this annotations
 *                   class
 *                 - introduced IEC 61499 attribute support into the model
 *   Alois Zoitl   - reworked model helper functions for better mapping and
 *                   sub-app support
 *   			   - extracted from annotations class and extended with group
 *   			     functions
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class FBNetworkElementAnnotations {

	public static final InterfaceList EMPTY_INTERFACE_LIST = LibraryElementFactory.eINSTANCE.createInterfaceList();

	public static Resource getResource(final FBNetworkElement fbne) {
		if (fbne instanceof CommunicationChannel) {
			return null;
		}
		if (null != fbne.getFbNetwork()) {
			final EObject container = fbne.getFbNetwork().eContainer();
			if (container instanceof final Resource res) {
				return res;
			}
			if ((container instanceof SubApp) || (container instanceof CFBInstance)) {
				// if we are in a subapp or CFBInstance look recursively for a resource
				return getResource((FBNetworkElement) container);
			}
		}
		if (fbne.isMapped()) {
			// get the Resource of the mapped FB
			final FBNetworkElement to = fbne.getMapping().getTo();
			if (to != null) {
				return to.getResource();
			}
		}
		return null;
	}

	public static IInterfaceElement getInterfaceElement(final FBNetworkElement fbne, final String name) {
		if (fbne.getInterface() != null) {
			return fbne.getInterface().getInterfaceElement(name);
		}
		return null;
	}

	public static IInterfaceElement getInput(final FBNetworkElement fbne, final String name) {
		if (fbne.getInterface() != null) {
			return fbne.getInterface().getInput(name);
		}
		return null;
	}

	public static IInterfaceElement getOutput(final FBNetworkElement fbne, final String name) {
		if (fbne.getInterface() != null) {
			return fbne.getInterface().getOutput(name);
		}
		return null;
	}

	public static FBNetworkElement getOpposite(final FBNetworkElement fbne) {
		// try to find the other corresponding mapped entity if this FBNetworkElement is
		// mapped
		if (fbne.isMapped()) {
			return (fbne == fbne.getMapping().getFrom()) ? fbne.getMapping().getTo() : fbne.getMapping().getFrom();
		}
		return null;
	}

	public static FBNetwork getFbNetwork(final FBNetworkElement fbne) {
		// an FB should always be put in an fbNetwork this is at the same time also a
		// null check
		return (fbne.eContainer() instanceof final FBNetwork fbn) ? fbn : null;
	}

	public static void checkConnections(final FBNetworkElement fbne) {
		fbne.getInterface().getAllInterfaceElements().forEach(element -> {
			element.getInputConnections().forEach(Connection::checkIfConnectionBroken);
			element.getOutputConnections().forEach(Connection::checkIfConnectionBroken);
		});
	}

	public static boolean isMapped(final FBNetworkElement fbne) {
		return null != fbne.getMapping();
	}

	public static boolean isInGroup(final FBNetworkElement fbne) {
		return fbne.getGroup() != null;
	}

	private FBNetworkElementAnnotations() {
		throw new UnsupportedOperationException(
				"The utility class FBNetworkElementAnnotations should not be instatiated"); //$NON-NLS-1$
	}

}
