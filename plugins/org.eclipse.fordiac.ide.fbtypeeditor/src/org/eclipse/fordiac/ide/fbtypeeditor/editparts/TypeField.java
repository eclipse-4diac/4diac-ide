/*******************************************************************************
 * Copyright (c) 2011, 2023 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class TypeField {
	private final IInterfaceElement referencedElement;

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	public TypeField(final IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
	}

	public String getLabel() {
		if (referencedElement instanceof final VarDeclaration varDecl) {
			return DataLabelProvider.getDataTypeText(varDecl);
		}
		return getReferencedElement().getTypeName();
	}

}
