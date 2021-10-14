/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class InternalFBLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final int NAME_COL_INDEX = 0;
	private static final int TYPE_COL_INDEX = 1;
	private static final int COMMENT_COL_INDEX = 2;

	@Override
	public String getColumnText(Object object, int columnIndex) {
		if (object instanceof FB) {
			FB internalFB = ((FB) object);
			switch (columnIndex) {
			case NAME_COL_INDEX:
				return internalFB.getName();
			case TYPE_COL_INDEX:
				return internalFB.getTypeName();
			case COMMENT_COL_INDEX:
				return internalFB.getComment() != null ? internalFB.getComment() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return object.toString();
	}

	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		// Not needed until now
		return null;
	}

}
