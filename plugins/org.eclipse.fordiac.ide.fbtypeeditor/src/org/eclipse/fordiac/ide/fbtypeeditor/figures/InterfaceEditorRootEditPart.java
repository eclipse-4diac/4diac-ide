/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - inherited FBInterface editor from the common diagram editor
 *                 to reduce code duplication and more common look and feel
 *               - extracted from FBInterfaceEditor and extended with own
 *                 connection layer
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.figures;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.ui.IWorkbenchPartSite;

public class InterfaceEditorRootEditPart extends ZoomScalableFreeformRootEditPart {
	public InterfaceEditorRootEditPart(final IWorkbenchPartSite site, final ActionRegistry actionRegistry) {
		super(site, actionRegistry);
	}

	@Override
	protected AbstractFreeformFigure createDrawingAreaContainer() {
		return new MinSpaceFreeformFigure();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure rootFigure = super.createFigure();
		final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
		if (grid != null) {
			// it does not make sense to have a grid in the interface layer so hide it
			grid.setVisible(false);
		}
		return rootFigure;
	}

	@Override
	protected LayeredPane createPrintableLayers() {
		final FreeformLayeredPane layeredPane = new FreeformLayeredPane();
		layeredPane.add(new FreeformLayer(), PRIMARY_LAYER);
		layeredPane.add(new OrderedConnectionLayer(), CONNECTION_LAYER);
		return layeredPane;
	}

	@Override
	protected void refreshGridLayer() {
		// empty to be sure that grid will not be drawn
	}

	private static class OrderedConnectionLayer extends ConnectionLayer {

		private boolean needsSorting = true;

		@Override
		protected void paintChildren(final Graphics graphics) {
			if (needsSorting) {
				ensureChildrenSorted();
				needsSorting = false;
			}
			super.paintChildren(graphics);
		}

		@Override
		public void add(final IFigure figure, final Object constraint, final int index) {
			super.add(figure, constraint, index);
			needsSorting = true;
		}

		@Override
		public void remove(final IFigure child) {
			super.remove(child);
			needsSorting = true;
		}

		private void ensureChildrenSorted() {
			@SuppressWarnings("unchecked")
			final List<IFigure> children = (List<IFigure>) getChildren();

			Collections.sort(children, (f1, f2) -> Integer.compare(f2.getBounds().bottom(), f1.getBounds().bottom()));
		}
	}
}