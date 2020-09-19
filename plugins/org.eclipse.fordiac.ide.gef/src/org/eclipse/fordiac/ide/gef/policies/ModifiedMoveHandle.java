/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GbmH, TU Wien ACIN,
 * 				 2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - changed color handling to not create colors during painting,
 *                 cleaned-up painting code
 *               - improved selection feedback with the default selection color,
 *                 thicker selection border line, and a transparent filled area.
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * The Class ModifiedMoveHandle.
 */
public class ModifiedMoveHandle extends MoveHandle {

	public static final int SELECTION_FILL_ALPHA = 50;
	private static final int SELECTION_BORDER_WIDTH = 2;
	private static Color selectionColor = null;

	public static Color getSelectionColor() {
		if (null == selectionColor) {
			Display display = Display.getCurrent();
			selectionColor = display.getSystemColor(SWT.COLOR_LIST_SELECTION);
		}
		return selectionColor;
	}

	private final Insets insets;
	private final int arc;

	/**
	 * Instantiates a new modified move handle.
	 *
	 * @param owner  the owner
	 * @param insets the insets
	 * @param arc    the arc
	 */
	public ModifiedMoveHandle(GraphicalEditPart owner, Insets insets, int arc) {
		super(owner);
		this.insets = insets;
		this.arc = arc;
	}

	/**
	 * Initializes the handle. Sets the {@link DragTracker} and DragCursor.
	 */
	@Override
	protected void initialize() {
		setOpaque(false);
		setBorder(new AbstractBorder() {
			@Override
			public void paint(IFigure figure, Graphics g, Insets insets) {
				Rectangle rect = getPaintRectangle(figure, insets);
				g.setLineStyle(Graphics.LINE_SOLID);
				g.setLineWidth(SELECTION_BORDER_WIDTH);
				g.setXORMode(false);
				g.setForegroundColor(getSelectionColor());
				g.setBackgroundColor(getSelectionColor());
				rect = rect.getShrinked(1, 1);
				g.drawRoundRectangle(rect, arc, arc);
				g.setAlpha(SELECTION_FILL_ALPHA);
				g.fillRoundRectangle(rect, arc, arc);
			}

			@Override
			public Insets getInsets(IFigure figure) {
				return insets;
			}
		});
		setCursor(Cursors.SIZEALL);
	}
}
