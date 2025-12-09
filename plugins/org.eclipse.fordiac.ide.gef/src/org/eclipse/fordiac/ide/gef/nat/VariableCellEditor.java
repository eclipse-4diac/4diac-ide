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

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmVariableEditedResourceProvider;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class VariableCellEditor extends XtextStyledTextCellEditor {
	private final IRowDataProvider<? extends Variable<?>> dataProvider;

	public VariableCellEditor(final IRowDataProvider<? extends Variable<?>> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public VariableCellEditor(final IRowDataProvider<? extends Variable<?>> dataProvider,
			final boolean moveSelectionOnEnter) {
		super(moveSelectionOnEnter);
		this.dataProvider = dataProvider;
	}

	@Override
	protected StyledText createStyledText(final Composite parent, final int style) {
		return super.createStyledText(parent, style | SWT.SINGLE);
	}

	@Override
	protected IEditedResourceProvider createEditedResourceProvider() {
		return new STAlgorithmVariableEditedResourceProvider(dataProvider.getRowObject(getRowIndex()));
	}
}
