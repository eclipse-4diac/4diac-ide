/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.model.WithPinProperty;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;

public class WithEndPointEditPart extends AbstractConnectableEditPart {

	private FigureFollower figFollower;

	@Override
	public void activate() {
		super.activate();
		figFollower = new FigureFollower(this, getModel().getPin());

	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (figFollower != null) {
			figFollower.unhookFromRefFigure();
			figFollower = null;
		}
	}

	@Override
	protected IFigure createFigure() {
		final IFigure fig = new Figure() {
			@Override
			protected void paintFigure(final Graphics graphics) {
				super.paintFigure(graphics);
				final Rectangle bounds = getBounds();
				final int y = bounds.y + bounds.height / 2;
				graphics.setLineWidth(2);
				graphics.drawLine(bounds.x, y, bounds.x + bounds.width, y);
			}
		};
		updateLineColor(fig);
		fig.setPreferredSize(new Dimension(-1, 2));
		return fig;
	}

	@Override
	public WithPinProperty getModel() {
		return (WithPinProperty) super.getModel();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (figFollower != null) {
			figFollower.refresh();
		}
	}

	private void updateLineColor(final IFigure fig) {
		final IInterfaceElement pin = getModel().getPin();
		fig.setForegroundColor(switch (pin) {
		case final Event ev -> UIPreferenceConstants.getEventConnectorColor();
		case final AdapterDeclaration adp -> UIPreferenceConstants.getAdapterConnectorColor();
		default -> PreferenceGetter.getDataColor(pin.getType().getName());
		});
	}

}
