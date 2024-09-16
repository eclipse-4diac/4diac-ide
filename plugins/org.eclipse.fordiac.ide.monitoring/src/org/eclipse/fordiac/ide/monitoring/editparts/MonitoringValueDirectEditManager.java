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
package org.eclipse.fordiac.ide.monitoring.editparts;

import org.eclipse.fordiac.ide.gef.editparts.InitialValueCellEditor;
import org.eclipse.fordiac.ide.gef.editparts.InitialValueStructuredCellEditor;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class MonitoringValueDirectEditManager extends TextDirectEditManager {
	private final VarDeclaration varDeclaration;
	private final MonitoringElement monitoringElement;

	public MonitoringValueDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator,
			final VarDeclaration varDeclaration, final MonitoringElement monitoringElement) {
		super(source, locator);
		this.varDeclaration = varDeclaration;
		this.monitoringElement = monitoringElement;
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
		final String currentValue = monitoringElement.getCurrentValue();
		if (currentValue != null) {
			getCellEditor().setValue(currentValue);
		}
	}
}
