/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GbmH, TU Wien ACIN,
 * 				 2018, 2019 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The Class ModifiedMoveHandle.
 */
public class ModifiedMoveHandle extends MoveHandle {

	private static Color borderColor = null;

	private static Color getBorderColor() {
		if (null == borderColor) {
			IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			RGB color = PreferenceConverter.getColor(pf, DiagramPreferences.SELECTION_COLOR);
			borderColor = new Color(null, color);
		}
		return borderColor;
	}

	private Insets insets = new Insets(2);
	private int arc = 20;

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
	 * Instantiates a new modified move handle.
	 *
	 * @param owner the owner
	 */
	public ModifiedMoveHandle(GraphicalEditPart owner) {
		super(owner);

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
				g.setLineWidth(1);
				g.setXORMode(false);
				g.setForegroundColor(getBorderColor());
				g.drawRoundRectangle(rect.getResized(-1, -1), arc, arc);
			}

			@Override
			public Insets getInsets(IFigure figure) {
				return insets;
			}
		});
		setCursor(Cursors.SIZEALL);
	}
}
