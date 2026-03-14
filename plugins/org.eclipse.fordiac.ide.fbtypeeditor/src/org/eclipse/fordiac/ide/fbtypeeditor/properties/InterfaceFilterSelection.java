/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Virendra Ashiwal
 *     - extracted as common code from class EventInterfaceFilter, AdapterInterfaceFilter
 *       and DataInterfaceFilter
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.model.PinProperty;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;

final class InterfaceFilterSelection {

	static IInterfaceElement getSelectableInterfaceElementOfType(final Object object) {
		Object ie = object;
		if (ie instanceof final EditPart ep) {
			ie = ep.getModel();
		}

		if (ie instanceof final PinProperty pinProp) {
			return pinProp.getPin();
		}

		return isInterfaceElementOfType(ie) ? (IInterfaceElement) ie : null;
	}

	private static boolean isInterfaceElementOfType(final Object ie) {
		return (ie instanceof final IInterfaceElement ifEl) && (ifEl.eContainer().eContainer() instanceof FBType);
	}

	private InterfaceFilterSelection() {
		// should not be instantiated
	}

}
