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

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The Class AlgorithmsLabelProvider.
 */
public class AlgorithmsLabelProvider extends LabelProvider implements ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.
	 * Object, int)
	 */
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
	 * int)
	 */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {

		if (element instanceof Algorithm) {
			switch (columnIndex) {
			case 0:
				return ((Algorithm) element).getName();
			case 1:
				if (element instanceof STAlgorithm) {
					return "ST"; //$NON-NLS-1$
				} else if (element instanceof OtherAlgorithm) {
					return ((OtherAlgorithm) element).getLanguage();
				}
				break;
			case 2:
				return ((Algorithm) element).getComment();
			default:
				break;
			}
		}
		return element.toString();
	}
}
