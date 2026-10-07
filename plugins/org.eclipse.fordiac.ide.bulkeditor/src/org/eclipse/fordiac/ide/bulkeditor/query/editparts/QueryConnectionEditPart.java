/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

/** Edit part of a containment connection, labeled AND/OR for constraints. */
public class QueryConnectionEditPart extends AbstractConnectionEditPart {

	@Override
	public QueryConnection getModel() {
		return (QueryConnection) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final PolylineConnection connection = new PolylineConnection();
		connection.setForegroundColor(ColorConstants.lightGray);

		final PolygonDecoration arrow = new PolygonDecoration();
		arrow.setScale(6, 3);
		connection.setTargetDecoration(arrow);

		final String label = getModel().getLabel();
		if (label != null) {
			connection.add(new Label(label), new MidpointLocator(connection, 0));
		}
		return connection;
	}

	@Override
	protected void createEditPolicies() {
		// the connections show the containment of the query and can not be edited
	}

	@Override
	public boolean isSelectable() {
		return false;
	}
}
