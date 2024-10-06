/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmInitialValueEditedResourceProvider;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class InitialValueCellEditor extends XtextStyledTextCellEditor {
	private final IRowDataProvider<? extends ITypedElement> dataProvider;

	public InitialValueCellEditor(final IRowDataProvider<? extends ITypedElement> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public InitialValueCellEditor(final IRowDataProvider<? extends ITypedElement> dataProvider,
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
		return new STAlgorithmInitialValueEditedResourceProvider(getRowObject());
	}

	protected ITypedElement getRowObject() {
		return dataProvider.getRowObject(getRowIndex());
	}
}
