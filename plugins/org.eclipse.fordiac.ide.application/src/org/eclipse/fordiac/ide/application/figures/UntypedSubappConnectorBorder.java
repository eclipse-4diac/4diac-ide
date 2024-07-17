/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.application.figures;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class UntypedSubappConnectorBorder extends ConnectorBorder {

	private boolean secondPaint;

	public UntypedSubappConnectorBorder(final IInterfaceElement editPartModelOject) {
		super(editPartModelOject);
	}

	private SubApp getSubapp() {
		return (SubApp) getEditPartModelOject().getFBNetworkElement();
	}

	@Override
	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		if (!getSubapp().isUnfolded() || !allOutsideConnectionsAreHidden()) {
			// only draw outside if there is a visible connection
			super.paint(figure, graphics, insets);
		}
		if (getSubapp().isUnfolded()) {
			secondPaint = true;
			super.paint(figure, graphics, insets);
			secondPaint = false;
			if (!isInput() && getEditPartModelOject().getOutputConnections().stream()
					.filter(Predicate.not(Connection::isVisible)).count() > 1) {
				// draw a vertical line to show which target pins belong together
				final Rectangle where = getPaintRectangle(figure, insets);
				graphics.setLineWidth(2);
				graphics.drawLine(where.x + 1, where.y + 3, where.x + 1, where.y + where.height - 3);
			}
		}
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		if (getSubapp().isUnfolded()) {
			final int lrMargin = getLRMargin();
			if (allOutsideConnectionsAreHidden()) {
				if (isInput()) {
					return new Insets(0, 0, 0, lrMargin);
				}
				return new Insets(0, lrMargin, 0, 0);
			}
			return new Insets(0, lrMargin, 0, lrMargin);
		}
		return super.getInsets(figure);
	}

	@Override
	public boolean isInput() {
		final boolean input = super.isInput();
		return (secondPaint) ? !input : input;
	}

	private boolean allOutsideConnectionsAreHidden() {
		final List<Connection> conList = isInput() ? getEditPartModelOject().getInputConnections()
				: getEditPartModelOject().getOutputConnections();
		return !conList.isEmpty() && conList.stream().allMatch(Predicate.not(Connection::isVisible));
	}

}
