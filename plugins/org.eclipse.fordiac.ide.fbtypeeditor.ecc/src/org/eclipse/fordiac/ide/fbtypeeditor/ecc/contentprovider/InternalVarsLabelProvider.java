/*******************************************************************************
 * Copyright (c) 2008, 2013, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
				return ((VarDeclaration) element).getVarInitialization() != null && ((VarDeclaration) element).getVarInitialization().getInitialValue() !=  null ? ((VarDeclaration) element).getVarInitialization().getInitialValue() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}
