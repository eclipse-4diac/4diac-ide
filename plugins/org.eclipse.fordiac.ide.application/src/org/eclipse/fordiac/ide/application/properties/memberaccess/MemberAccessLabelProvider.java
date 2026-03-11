/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties.memberaccess;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MemberAccessLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof final MemberAccessTreeNode memAccessTreeNode) {

			switch (columnIndex) {
			case 0:
				return memAccessTreeNode.getName();
			case 1:
				return memAccessTreeNode.getFullTypeName();
			case 2:
				return memAccessTreeNode.getComment();
			default:
				break;
			}
		}
		return element.toString();
	}

}
