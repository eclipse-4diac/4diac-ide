/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IFilter;

public class DataInterfaceFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof InterfaceEditPart) {
			if (((InterfaceEditPart) toTest).getCastedModel() instanceof AdapterDeclaration
					|| ((InterfaceEditPart) toTest).getCastedModel() instanceof Event) {
				return false;
			}
			return true;
		}
		if (toTest instanceof TypeEditPart) {
			if (((TypeEditPart) toTest).getCastedModel() instanceof AdapterDeclaration
					|| ((TypeEditPart) toTest).getCastedModel() instanceof Event) {
				return false;
			}
			return true;
		}
		if (toTest instanceof CommentEditPart) {
			if (((CommentEditPart) toTest).getCastedModel() instanceof AdapterDeclaration
					|| ((CommentEditPart) toTest).getCastedModel() instanceof Event) {
				return false;
			}
			return true;
		}
		if (!(toTest instanceof AdapterDeclaration || toTest instanceof Event) && toTest instanceof VarDeclaration) {
			return true;
		}
		return false;
	}
}
