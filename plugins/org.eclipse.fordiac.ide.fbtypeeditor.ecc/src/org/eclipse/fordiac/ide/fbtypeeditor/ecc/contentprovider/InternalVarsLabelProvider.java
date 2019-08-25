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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The Class InternalVarsLabelProvider.
 */
public class InternalVarsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
	 *      int)
	 */
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
	 *      int)
	 */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {

		if (element instanceof VarDeclaration) {
			switch (columnIndex) {
			case 0:
				return ((VarDeclaration) element).getName();
			case 1:
				return ((VarDeclaration) element).getType().getName();
			case 2:
				return ((VarDeclaration) element).getComment();
			case 3:
				return ((VarDeclaration) element).getArraySize() > 0 ? Integer.toString(((VarDeclaration) element).getArraySize()) : ""; //$NON-NLS-1$
			case 4:
				return ((VarDeclaration) element).getValue() != null && ((VarDeclaration) element).getValue().getValue() !=  null ? ((VarDeclaration) element).getValue().getValue() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}
