/*******************************************************************************
 * Copyright (c) 2008, 2013, 2015 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.provider;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The Class InternalVarsLabelProvider.
 */
public class InternalVarsLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof VarDeclaration) {
			VarDeclaration varDecl = ((VarDeclaration) element);
			switch (columnIndex) {
			case 0:
				return varDecl.getName();
			case 1:
				return varDecl.getType().getName();
			case 2:
				return (varDecl.getArraySize() > 0) ? Integer.toString(varDecl.getArraySize()) : ""; //$NON-NLS-1$
			case 3:
				return (varDecl.getValue() != null) ? varDecl.getValue().getValue() : ""; //$NON-NLS-1$
			case 4:
				return varDecl.getComment();
			default:
				break;
			}
		}
		return element.toString();
	}
}
