/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

class CommentField {
	private final IInterfaceElement referencedElement;

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	/**
	 * Helper object to display comment of an in/output.
	 *
	 * @param referencedElement the referenced element
	 */
	CommentField(final IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
	}

	public String getLabel() {
		return getReferencedElement().getComment();
	}

}
