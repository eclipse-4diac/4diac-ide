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
 * A label provider that can be used to display data in columns
 */
public class DataLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final int NAME_COL_INDEX = 0;
	private static final int TYPE_COL_INDEX = 1;
	private static final int COMMENT_COL_INDEX = 2;
	private static final int INITIALVALUE_COL_INDEX = 3;
	private static final int ARRAYSIZE_COL_INDEX = 4;

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof VarDeclaration) {
			VarDeclaration varDecl = ((VarDeclaration) element);
			switch (columnIndex) {
			case NAME_COL_INDEX:
				return varDecl.getName();
			case TYPE_COL_INDEX:
				return varDecl.getType().getName();
			case COMMENT_COL_INDEX:
				return varDecl.getComment();
			case INITIALVALUE_COL_INDEX:
				return (varDecl.getValue() != null) ? varDecl.getValue().getValue() : ""; //$NON-NLS-1$
			case ARRAYSIZE_COL_INDEX:
				return (varDecl.getArraySize() > 0) ? Integer.toString(varDecl.getArraySize()) : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}
