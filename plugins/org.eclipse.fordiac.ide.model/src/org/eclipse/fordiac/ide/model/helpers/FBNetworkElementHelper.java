/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;

public final class FBNetworkElementHelper {

	public static boolean isContainedInTypedInstance(final EObject element) {
		EObject obj = element;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if ((obj instanceof final SubApp subApp && subApp.isTyped()) || (obj instanceof CFBInstance)) {
				return true;
			}
		}
		return false;
	}

	public static SubApp getContainerSubappOfFB(final FB fb) {
		if (fb.isNestedInSubApp() && fb.getOuterFBNetworkElement() instanceof final SubApp subapp
				&& !subapp.isTyped()) {
			return subapp;
		}
		return null;
	}

	public static SubApp getUntypedContainerSubappOfTypedSubapp(final SubApp typedSubapp) {
		if (typedSubapp.isTyped() && typedSubapp.isNestedInSubApp()) {
			if (typedSubapp.eContainer() instanceof final SubApp subapp && !subapp.isTyped()) {
				return subapp;
			}
			if (typedSubapp.eContainer().eContainer() instanceof final SubApp subapp && !subapp.isTyped()) {
				return subapp;
			}
		}
		return null;
	}

	public static TypedSubApp getContainerTypedSubApp(final EObject element) {
		EObject obj = element;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof final TypedSubApp subApp && !subApp.isContainedInTypedInstance()) {
				return subApp;
			}
		}
		return null;
	}

	public static boolean isContainedInTypedSubApp(final EObject element) {
		EObject obj = element;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof final SubApp subApp && subApp.isTyped()) {
				return true;
			}
		}
		return false;
	}

	private FBNetworkElementHelper() {
		throw new UnsupportedOperationException();
	}
}
