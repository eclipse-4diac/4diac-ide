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
 *    Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class InitialValueLabelProvider extends InterfaceElementLabelProvider {
	public static final int INITIALVALUE_COL_INDEX = 3;

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof VarDeclaration) {
			if (columnIndex == INITIALVALUE_COL_INDEX) {

				return InitialValueHelper.getInitialOrDefaultValue(element);
			}
			return super.getColumnText(element, columnIndex);
		}
		return element.toString();
	}

	@Override
	public Color getForeground(final Object element, final int columnIndex) {
		if (columnIndex == INITIALVALUE_COL_INDEX && !hasInitalValue(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return super.getForeground(element, columnIndex);
	}

	private static boolean hasInitalValue(final Object element) {
		return (((VarDeclaration) element).getValue() != null
				&& !((VarDeclaration) element).getValue().getValue().isEmpty());
	}
}
