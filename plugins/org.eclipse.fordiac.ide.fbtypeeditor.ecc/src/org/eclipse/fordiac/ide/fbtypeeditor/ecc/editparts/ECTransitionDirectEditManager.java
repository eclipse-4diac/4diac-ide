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
		public Insets getInsets(IFigure figure) {
			return BORDER_INSETS;
		}

		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			// don't draw any border to make the direct editor smaller
		}
	};

	public static class ECTransitionCellEditorLocator implements CellEditorLocator {
		private Point refPoint = new Point(0, 0);
		private Label label;
		private ZoomManager zoomManager;
		private FigureCanvas fc;

		public ECTransitionCellEditorLocator(Label label, ZoomManager zoomManager, FigureCanvas fc) {
			this.label = label;
			this.zoomManager = zoomManager;
			this.fc = fc;
		}

		@Override
		public void relocate(CellEditor celleditor) {
			if (null != celleditor) {
				Control control = celleditor.getControl();
				updateRefPoint();
				Point pref = new Point(control.computeSize(SWT.DEFAULT, SWT.DEFAULT).x,
						control.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				control.setBounds(refPoint.x - pref.x / 2, refPoint.y - pref.y / 2, pref.x + 1, pref.y + 1);
			}
		}

		private void updateRefPoint() {
			final Point typeLabelTopLeft = label.getBounds().getCenter().scale(zoomManager.getZoom());
			final Point location = fc.getViewport().getViewLocation();
			refPoint = new Point(typeLabelTopLeft.x - location.x, typeLabelTopLeft.y - location.y);
		}

		public void setRefPoint(Point refPoint) {
			this.refPoint = refPoint;
		}

		public Point getRefPoint() {
			return refPoint;
		}
	}

	private ECTransition transition = null;

	public ECTransitionDirectEditManager(GraphicalEditPart source, ECTransition transition, Label label,
			ZoomManager zoomManager, FigureCanvas fc) {
		super(source, ECTransitionCellEditor.class, new ECTransitionCellEditorLocator(label, zoomManager, fc));
		this.transition = transition;
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		if (null != transition) {
			getCellEditor().setValue(transition);
			CCombo combo = getComboBox();
			combo.addModifyListener(e -> setDirty(true));
			Text text = getText();
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

	public void updateRefPosition(Point refPoint) {
		getLocator().setRefPoint(refPoint);
	}

	@Override
	protected IFigure getCellEditorFrame() {
		IFigure cellEditorFrame = super.getCellEditorFrame();
		cellEditorFrame.setBorder(BORDER);
		return cellEditorFrame;
	}

	public CCombo getComboBox() {
		if (getCellEditor() instanceof ECTransitionCellEditor) {
			return ((ECTransitionCellEditor) getCellEditor()).getCCombo();
		}
		return null;
	}

	public Text getText() {
		if (getCellEditor() instanceof ECTransitionCellEditor) {
			return ((ECTransitionCellEditor) getCellEditor()).getText();
		}
		return null;
	}
}
