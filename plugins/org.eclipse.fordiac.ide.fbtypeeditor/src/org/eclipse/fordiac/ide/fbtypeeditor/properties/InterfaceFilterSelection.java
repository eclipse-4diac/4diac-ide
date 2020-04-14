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

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;

final class InterfaceFilterSelection {
	static Object getSelectableObject(Object object) {
		if (object instanceof InterfaceEditPart) {
			return ((InterfaceEditPart) object).getCastedModel();
		}
		if (object instanceof TypeEditPart) {
			return ((TypeEditPart) object).getCastedModel();
		}
		if (object instanceof CommentEditPart) {
			return ((CommentEditPart) object).getCastedModel();
		}
		return object;
	}

	private InterfaceFilterSelection() {
		// should not be instantiated
	}

}
