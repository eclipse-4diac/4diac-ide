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
 *   Franz Höpfinger - grow the editor control for fixed-size sibling widgets
 *                      (e.g. a struct/array value editor's dialog button)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

public class FigureCellEditorLocator implements CellEditorLocator {

	private final IFigure figure;

	/**
	 * Instantiates a new name cell editor locator.
	 *
	 * @param label the label
	 */
	public FigureCellEditorLocator(final IFigure figure) {
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
			// a composite editor control may pack extra fixed-size controls next to its
			// main text control (e.g. a struct/array value editor's "..." dialog button),
			// which need room beyond what the figure itself reserves
			if (control instanceof final Composite composite) {
				final int extraWidth = computeFixedChromeWidth(composite);
				if (extraWidth > 0) {
					if (figure instanceof final Label label && label.getLabelAlignment() == PositionConstants.RIGHT) {
						rect.x -= extraWidth;
					}
					rect.width += extraWidth;
				}
			}
			control.setBounds(rect.x, rect.y, rect.width, rect.height);
		}
	}

	/**
	 * Computes the width taken up by a composite's fixed-size children (those not
	 * grabbing excess horizontal space) plus the layout's spacing and margins, i.e.
	 * the room needed beyond the composite's main, freely growing/shrinking control.
	 */
	private static int computeFixedChromeWidth(final Composite composite) {
		if (!(composite.getLayout() instanceof final GridLayout layout)) {
			return 0;
		}
		final Control[] children = composite.getChildren();
		int width = 2 * layout.marginWidth + Math.max(0, children.length - 1) * layout.horizontalSpacing;
		for (final Control child : children) {
			if (!(child.getLayoutData() instanceof final GridData data) || !data.grabExcessHorizontalSpace) {
				width += child.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			}
		}
		return width;
	}

	protected IFigure getFigure() {
		return figure;
	}

}
