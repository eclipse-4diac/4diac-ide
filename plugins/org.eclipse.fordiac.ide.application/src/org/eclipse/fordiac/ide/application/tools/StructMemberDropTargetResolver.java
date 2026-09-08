/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.tools;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;

final class StructMemberDropTargetResolver {

	static EObject resolve(final EditPart editPart) {
		for (EditPart current = editPart; current != null; current = current.getParent()) {
			if (current.getModel() instanceof final VarDeclaration pin) {
				return pin.getType() instanceof StructuredType && !pin.isArray() ? pin : null;
			}
			if (current.getModel() instanceof IInterfaceElement) {
				return null;
			}
			if (current instanceof final StructManipulatorEditPart manipulatorEditPart) {
				return manipulatorEditPart.getModel();
			}
		}
		return null;
	}

	private StructMemberDropTargetResolver() {
		throw new UnsupportedOperationException();
	}
}
