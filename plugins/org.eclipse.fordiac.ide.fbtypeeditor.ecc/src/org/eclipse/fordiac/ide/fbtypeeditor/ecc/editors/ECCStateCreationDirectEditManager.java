/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class ECCStateCreationDirectEditManager extends DirectEditManager {

	private static final String TEMPLATE_NAME = "State"; //$NON-NLS-1$
	private static final int MINIMUM_WIDTH = 60;

	private static class StateCreationCellEditorLocator implements CellEditorLocator {

		private final Point refPoint;

		public StateCreationCellEditorLocator(final Point refPoint) {
			this.refPoint = refPoint.getCopy();
		}

		@Override
		public void relocate(final CellEditor cellEditor) {
			if (cellEditor != null) {
				final Control control = cellEditor.getControl();
				final org.eclipse.swt.graphics.Point pref = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				control.setBounds(refPoint.x, refPoint.y, Math.max(pref.x, MINIMUM_WIDTH), pref.y);
			}
		}
	}

	public ECCStateCreationDirectEditManager(final GraphicalEditPart source, final Point refPoint) {
		super(source, null, new StateCreationCellEditorLocator(refPoint));
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		return new TextCellEditor(composite);
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().setValue(TEMPLATE_NAME);
		if (getCellEditor().getControl() instanceof final Text text) {
			text.selectAll();
		}
	}
}
