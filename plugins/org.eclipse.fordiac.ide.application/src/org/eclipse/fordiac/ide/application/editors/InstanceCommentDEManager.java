/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.InstanceCommentEditPart;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

public class InstanceCommentDEManager extends TextDirectEditManager {

	protected final class InstanceCommentCellEditorLocator extends FigureCellEditorLocator {
		protected InstanceCommentCellEditorLocator(final IFigure figure) {
			super(figure);
		}

		@Override
		public void relocate(final CellEditor celleditor) {
			if (null != celleditor) {
				final Control control = celleditor.getControl();
				final Rectangle rect = getFigure().getClientArea();
				getFigure().translateToAbsolute(rect);
				final IFigure shadow = getCellEditorFrame();
				rect.shrink(shadow.getInsets());
				if (control instanceof Scrollable) {
					final org.eclipse.swt.graphics.Rectangle trim = ((Scrollable) control).computeTrim(0, 0, 0, 0);
					rect.translate(trim.x, trim.y);
					rect.width += trim.width;
					rect.height += trim.height;
				}
				control.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}

	public InstanceCommentDEManager(final InstanceCommentEditPart source) {
		super(source, null);
		setLocator(new InstanceCommentCellEditorLocator(source.getFigure()));
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		getCellEditor().setValue(getEditPart().getModel().getInstanceComment());
	}

	@Override
	protected InstanceCommentEditPart getEditPart() {
		return (InstanceCommentEditPart) super.getEditPart();
	}
}