/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.providers;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof final Attribute attr) {
			switch (columnIndex) {
			case 0:
				return attr.getName();
			case 1:
				return attr.getType().getName();
			case 2:
				return attr.getValue();
			case 3:
				return attr.getComment() != null ? ((Attribute) element).getComment() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}