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
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider that can be used to display data in columns
 */
public class InterfaceElementLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {
	public static final int NAME_COL_INDEX = 0;
	public static final int TYPE_COL_INDEX = 1;
	public static final int COMMENT_COL_INDEX = 2;

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof IInterfaceElement) {
			IInterfaceElement interfaceElement = ((IInterfaceElement) element);
			switch (columnIndex) {
			case NAME_COL_INDEX:
				return interfaceElement.getName();
			case TYPE_COL_INDEX:
				return element instanceof Event ? FordiacMessages.Event : ((IInterfaceElement) element).getTypeName();
			case COMMENT_COL_INDEX:
				return ((IInterfaceElement) element).getComment() != null ? ((IInterfaceElement) element).getComment()
						: ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}
}
