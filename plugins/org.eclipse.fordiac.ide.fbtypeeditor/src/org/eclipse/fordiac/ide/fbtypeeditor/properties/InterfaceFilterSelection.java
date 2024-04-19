/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 				 2020        Johannes Kepler University Linz
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
 *
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentTypeField;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeField;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;

final class InterfaceFilterSelection {

	static IInterfaceElement getSelectableInterfaceElementOfType(final Object object) {
		Object ie = object;
		if (ie instanceof final InterfaceEditPart iep) {
			System.out.println("HAHA");
		}

		if (ie instanceof EditPart) {
			if (ie instanceof InterfaceEditPart) {
				System.out.println("HEUREKA IEP");
			}

			ie = ((EditPart) ie).getModel();

			if (ie instanceof final SubApp subapp) {
				System.out.println("HEUREKA");
			}
		}

		if (ie instanceof TypeField) {
			return ((TypeField) ie).getReferencedElement();
		}
		if (ie instanceof CommentTypeField) {
			return ((CommentTypeField) ie).getReferencedElement();
		}

		return isInterfaceElementOfType(ie) ? (IInterfaceElement) ie : null;
	}

	private static boolean isInterfaceElementOfType(final Object ie) {
		return (ie instanceof IInterfaceElement)
				&& (((IInterfaceElement) ie).eContainer().eContainer() instanceof FBType);
	}

	private InterfaceFilterSelection() {
		// should not be instantiated
	}

}
