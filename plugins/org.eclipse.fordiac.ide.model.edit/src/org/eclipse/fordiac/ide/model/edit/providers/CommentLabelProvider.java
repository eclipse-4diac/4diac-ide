/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin & Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CommentLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(final Object element) {
		if (element instanceof VarDeclaration) {
			final VarDeclaration varDec = (VarDeclaration) element;
			if (hasComment(element)) {
				return (varDec).getComment();
			}
			return getTypeComment(varDec);
		}
		return super.getText(element);
	}

	@Override
	public Color getForeground(final Object element) {
		if (hasComment(element)) {
			return super.getForeground(element);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	private static boolean hasComment(final Object element) {
		return (((VarDeclaration) element).getComment() != null && !((VarDeclaration) element).getComment().isBlank());
	}

	private static String getTypeComment(final VarDeclaration varDec) {
		if (varDec.eContainer() instanceof InterfaceList) {
			final FBNetworkElement fbn = (FBNetworkElement) varDec.eContainer().eContainer();
			if (fbn != null && fbn.getType() != null) {
				final IInterfaceElement interfaceElement = fbn.getType().getInterfaceList()
						.getInterfaceElement(varDec.getName());
				if (interfaceElement != null) {
					return interfaceElement.getComment() != null ? interfaceElement.getComment() : ""; //$NON-NLS-1$
				}
			}
		}
		return "";
	}
}
