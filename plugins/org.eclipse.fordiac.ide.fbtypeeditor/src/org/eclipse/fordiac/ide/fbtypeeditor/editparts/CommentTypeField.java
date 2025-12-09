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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class CommentTypeField {
	private final IInterfaceElement referencedElement;
	private final CommentField commentField;
	private final CommentTypeSeparator separator;
	private final TypeField typeField;

	static class CommentTypeSeparator {
		@SuppressWarnings("static-method")
		String getLabel() {
			return "    -    "; //$NON-NLS-1$
		}
	}

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	/**
	 * Helper object to display type and comment of an in/output.
	 *
	 * @param referencedElement the referenced element
	 */
	public CommentTypeField(final IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
		this.commentField = new CommentField(referencedElement);
		this.separator = new CommentTypeSeparator();
		this.typeField = new TypeField(referencedElement);
	}

	@SuppressWarnings("rawtypes")
	public List getChildren() {
		final List<Object> children = new ArrayList<>();
		if (getReferencedElement().isIsInput()) {
			children.add(commentField);
			children.add(separator);
			children.add(typeField);
		} else {
			children.add(typeField);
			children.add(separator);
			children.add(commentField);
		}
		return children;
	}
}
