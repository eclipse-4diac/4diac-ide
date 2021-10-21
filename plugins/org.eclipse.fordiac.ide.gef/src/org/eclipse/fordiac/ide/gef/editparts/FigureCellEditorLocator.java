/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University, Linz
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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

public class FigureCellEditorLocator implements CellEditorLocator {

	private final Figure figure;

	/**
	 * Instantiates a new name cell editor locator.
	 *
	 * @param label the label
	 */
	public FigureCellEditorLocator(final Figure figure) {
		this.figure = figure;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.
	 * CellEditor)
	 */
	@Override
	public void relocate(final CellEditor celleditor) {
		if (null != celleditor) {
			final Control control = celleditor.getControl();
			final Rectangle rect = figure.getClientArea();
			figure.translateToAbsolute(rect);
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
