/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class InitialValueVariableDirectEditManager extends InitialValueDirectEditManager {

	private final VarDeclaration varDeclaration;

	public InitialValueVariableDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator,
			final VarDeclaration varDeclaration, final String initialValue) {
		super(source, locator, varDeclaration, initialValue);
		this.varDeclaration = varDeclaration;
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		if (varDeclaration.getType() instanceof StructuredType || varDeclaration.isArray()) {
			return new InitialValueVariableStructuredCellEditor(composite, varDeclaration);
		}
		return super.createCellEditorOn(composite);
	}
}
