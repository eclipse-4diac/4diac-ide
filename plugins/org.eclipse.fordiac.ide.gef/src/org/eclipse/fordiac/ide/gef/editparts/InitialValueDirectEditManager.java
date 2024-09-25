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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class InitialValueDirectEditManager extends TextDirectEditManager {
	private final VarDeclaration varDeclaration;
	private final String initialValue;

	public InitialValueDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator,
			final VarDeclaration varDeclaration, final String initialValue) {
		super(source, locator);
		this.varDeclaration = varDeclaration;
		this.initialValue = initialValue != null ? initialValue
				: InitialValueHelper.getInitialOrDefaultValue(varDeclaration);
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		if (varDeclaration.getType() instanceof StructuredType || varDeclaration.isArray()) {
			return new InitialValueStructuredCellEditor(composite, varDeclaration);
		}
		return new InitialValueCellEditor(composite, varDeclaration);
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		getCellEditor().setValue(initialValue);
	}
}
