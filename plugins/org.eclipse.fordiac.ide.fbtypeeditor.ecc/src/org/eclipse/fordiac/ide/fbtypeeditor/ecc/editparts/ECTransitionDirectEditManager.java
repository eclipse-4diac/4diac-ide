/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class ECTransitionDirectEditManager extends TextDirectEditManager {

	private static final Insets BORDER_INSETS = new Insets(0, 0, 0, 0);
	private static final AbstractBorder BORDER = new AbstractBorder() {

		@Override
		public Insets getInsets(final IFigure figure) {
			return BORDER_INSETS;
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
			// don't draw any border to make the direct editor smaller
		}
	};

	public static class ECTransitionCellEditorLocator implements CellEditorLocator {
		private Point refPoint = new Point(0, 0);
		private final Label label;
		private final ZoomManager zoomManager;
		private final FigureCanvas fc;

		public ECTransitionCellEditorLocator(final Label label, final ZoomManager zoomManager, final FigureCanvas fc) {
			this.label = label;
			this.zoomManager = zoomManager;
			this.fc = fc;
		}

		@Override
		public void relocate(final CellEditor celleditor) {
			if (null != celleditor) {
				final Control control = celleditor.getControl();
				updateRefPoint();
				final Point pref = new Point(control.computeSize(SWT.DEFAULT, SWT.DEFAULT).x,
						control.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				control.setBounds(refPoint.x - pref.x / 2, refPoint.y - pref.y / 2, pref.x + 1, pref.y + 1);
			}
		}

		private void updateRefPoint() {
			final Point typeLabelTopLeft = label.getBounds().getCenter().scale(zoomManager.getZoom());
			final Point location = fc.getViewport().getViewLocation();
			refPoint = new Point(typeLabelTopLeft.x - location.x, typeLabelTopLeft.y - location.y);
		}

		public void setRefPoint(final Point refPoint) {
			this.refPoint = refPoint;
		}

		public Point getRefPoint() {
			return refPoint;
		}
	}

	private ECTransition transition = null;

	public ECTransitionDirectEditManager(final GraphicalEditPart source, final ECTransition transition, final Label label,
			final ZoomManager zoomManager, final FigureCanvas fc) {
		super(source, ECTransitionCellEditor.class, new ECTransitionCellEditorLocator(label, zoomManager, fc));
		this.transition = transition;
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		if (null != transition) {
			getCellEditor().setValue(transition);
			final CCombo combo = getComboBox();
			combo.addModifyListener(e -> setDirty(true));
			final Text text = getText();
			text.addModifyListener(e -> setDirty(true));
		}

	}

	@Override
	public ECTransitionCellEditorLocator getLocator() {
		return (ECTransitionCellEditorLocator) super.getLocator();
	}

	@Override
	protected ECTransitionCellEditor getCellEditor() {
		return (ECTransitionCellEditor) super.getCellEditor();
	}

	public void updateRefPosition(final Point refPoint) {
		getLocator().setRefPoint(refPoint);
	}

	@Override
	protected IFigure getCellEditorFrame() {
		final IFigure cellEditorFrame = super.getCellEditorFrame();
		cellEditorFrame.setBorder(BORDER);
		return cellEditorFrame;
	}

	public CCombo getComboBox() {
		if (getCellEditor() != null) {
			return getCellEditor().getCCombo();
		}
		return null;
	}

	public Text getText() {
		if (getCellEditor() != null) {
			return getCellEditor().getText();
		}
		return null;
	}
}
