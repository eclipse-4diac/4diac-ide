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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.helper;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public final class InitialValueHelper {

	private InitialValueHelper() {
		throw new UnsupportedOperationException("Helper class InitialValueHelper should not be instantiated!"); //$NON-NLS-1$
	}

	public static String getInitalOrDefaultValue(final Object element) {
		if (element instanceof VarDeclaration) {
			final VarDeclaration varDec = (VarDeclaration) element;
			if (hasInitalValue(element)) {
				return varDec.getValue().getValue();
			}
			return getDefaultValue(element);
		}
		return "";
	}

	public static String getDefaultValue(final Object element) {
		if (element instanceof VarDeclaration) {
			final VarDeclaration varDec = (VarDeclaration) element;

			try {
				return (IecTypes.GenericTypes.isAnyType(varDec.getType())) ? "" //$NON-NLS-1$
						: VariableOperations.newVariable(varDec).getValue().toString();
			} catch (final IllegalArgumentException exc) {
				// we are only logging it and jump to default value below
				FordiacLogHelper.logWarning("could not aquire VarDec default value", exc); //$NON-NLS-1$
			}
		}
		return "";
	}

	public static Color getForegroundColor(final Object element) {
		if (element instanceof VarDeclaration && !hasInitalValue(element)) {

			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	public static boolean hasInitalValue(final Object element) {
		return (((VarDeclaration) element).getValue() != null
				&& !((VarDeclaration) element).getValue().getValue().isEmpty());
	}

}
