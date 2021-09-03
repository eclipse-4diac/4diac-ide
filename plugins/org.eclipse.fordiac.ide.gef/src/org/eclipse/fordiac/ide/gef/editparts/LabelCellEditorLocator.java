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
 *   Melanie Winter -  extracted from ECTransitionDirectEditManager
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public class LabelCellEditorLocator implements CellEditorLocator {
	private Point refPoint = new Point(0, 0);
	private final Label label;
	private final ZoomManager zoomManager;
	private final FigureCanvas fc;

	public LabelCellEditorLocator(final Label label, final ZoomManager zoomManager, final FigureCanvas fc) {
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