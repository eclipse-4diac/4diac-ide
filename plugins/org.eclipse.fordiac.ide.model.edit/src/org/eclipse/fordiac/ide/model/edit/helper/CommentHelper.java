/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.helper;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class CommentHelper {

	public static boolean hasComment(final INamedElement element) {
		return element != null && element.getComment() != null && !element.getComment().isBlank();
	}

	public static String getInstanceComment(final INamedElement element) {
		if (hasComment(element)) {
			return element.getComment();
		}
		if (element instanceof final IInterfaceElement interfaceElement) {
			return getTypeComment(interfaceElement);
		}
		return ""; //$NON-NLS-1$
	}

	public static String getTypeComment(final IInterfaceElement interfaceElement) {
		if (interfaceElement != null) {
			final FBNetworkElement fbn = interfaceElement.getFBNetworkElement();
			if (fbn instanceof final StructManipulator structManipulator && structManipulator.getStructType() != null) {
				final VarDeclaration structMember = structManipulator.getStructType().getMemberVariables().stream()
						.filter(member -> interfaceElement.getName().equals(member.getName())).findFirst().orElse(null);
				if (structMember != null && structMember.getComment() != null) {
					return structMember.getComment();
				}
			}
			if (fbn != null && fbn.getType() != null) {
				final IInterfaceElement typeElement = fbn.getType().getInterfaceList()
						.getInterfaceElement(interfaceElement.getName());
				if (typeElement != null && typeElement.getComment() != null) {
					return typeElement.getComment();
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	private CommentHelper() {
		throw new UnsupportedOperationException();
	}
}
