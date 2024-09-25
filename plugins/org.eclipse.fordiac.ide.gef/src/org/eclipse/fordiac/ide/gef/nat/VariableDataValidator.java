/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.data.validate.DataValidator;

public class VariableDataValidator extends DataValidator {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	private final IRowDataProvider<? extends Variable<?>> dataProvider;

	public VariableDataValidator(final IRowDataProvider<? extends Variable<?>> dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public boolean validate(final int columnIndex, final int rowIndex, final Object newValue) {
		return dataProvider.getRowObject(rowIndex).validateValue(Objects.toString(newValue, NULL_DEFAULT));
	}
}
