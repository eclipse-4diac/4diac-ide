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
 *   Melanie Winter - extracted to common super class
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractDirectEditManager<T> extends TextDirectEditManager {

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

	private final T element;

	protected AbstractDirectEditManager(final GraphicalEditPart source, final T element,
			final Label label, final ZoomManager zoomManager, final FigureCanvas fc, final Class clazz) {
		super(source, clazz, new LabelCellEditorLocator(label, zoomManager, fc));
		this.element = element;
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		if (null != element) {
			getCellEditor().setValue(element);
			final CCombo combo = getComboBox();
			combo.addModifyListener(e -> setDirty(true));
			final Text text = getText();
			text.addModifyListener(e -> setDirty(true));
		}

	}

	@Override
	public LabelCellEditorLocator getLocator() {
		return (LabelCellEditorLocator) super.getLocator();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractCombinedCellEditor<T> getCellEditor() {
		return (AbstractCombinedCellEditor<T>) super.getCellEditor();
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
