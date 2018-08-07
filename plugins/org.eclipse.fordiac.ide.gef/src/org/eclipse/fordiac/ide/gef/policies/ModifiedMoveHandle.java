/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GbmH, TU Wien ACIN, 
 * 				 2018 Johannes Kepler University 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.SimpleEtchedBorder;
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
	private Insets insets = new Insets(2);
	private int arc = 20;

	/**
	 * Instantiates a new modified move handle.
	 * 
	 * @param owner the owner
	 * @param insets the insets
	 * @param arc the arc
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
		setBorder(SimpleEtchedBorder.singleton);
		setBorder(new SchemeBorder() {

			@Override
			public void paint(IFigure figure, Graphics g, Insets insets) {
				Rectangle rect = getPaintRectangle(figure, insets);

				IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
				RGB color = PreferenceConverter.getColor(pf,
						DiagramPreferences.SELECTION_COLOR);
				Color rgb = new Color(null, color);
				Color highlight = FigureUtilities.lighter(rgb);

				paintEtchedBorder(g, rect, rgb, highlight);
				highlight.dispose();
				rgb.dispose();
				
			}

			@Override
			public Insets getInsets(IFigure figure) {
				return insets;
			}

			/**
			 * Paints a border with an etching effect, having a shadow of Color
			 * <i>shadow</i> and highlight of Color <i>highlight</i>.
			 * 
			 * @param g
			 *          the graphics object
			 * @param r
			 *          the bounds of the border
			 * @param shadow
			 *          the shadow color
			 * @param highlight
			 *          the highlight color
			 * @since 2.0
			 */
			private void paintEtchedBorder(Graphics g, Rectangle r, Color shadow,
					Color highlight) {
				int x = r.x, y = r.y, w = r.width, h = r.height;

				g.setLineStyle(Graphics.LINE_SOLID);
				g.setLineWidth(1);
				g.setXORMode(false);

				w -= 2;
				h -= 2;

				Rectangle rect = new Rectangle(x, y, w, h);
				g.setForegroundColor(shadow);

				g.drawRoundRectangle(rect, arc, arc);
			}

		});
		setCursor(Cursors.SIZEALL);
	}
}
