/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.gef.editparts.InitialValueCellEditor;
import org.eclipse.fordiac.ide.gef.editparts.InitialValueStructuredCellEditor;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class WatchValueDirectEditManager extends TextDirectEditManager {
	private final IVarDeclarationWatch watch;

	public WatchValueDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator,
			final IVarDeclarationWatch watch) {
		super(source, locator);
		this.watch = watch;
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		final VarDeclaration varDeclaration = watch.getWatchedElement();
		if (varDeclaration.getType() instanceof StructuredType || varDeclaration.isArray()) {
			return new InitialValueStructuredCellEditor(composite, varDeclaration);
		}
		return new InitialValueCellEditor(composite, varDeclaration);
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		getCellEditor().setValue(watch.getInternalVariable().toString());
	}
}
