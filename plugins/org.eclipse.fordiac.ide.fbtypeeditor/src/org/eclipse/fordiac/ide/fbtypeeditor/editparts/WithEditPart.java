/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class WithEditPart extends AbstractConnectionEditPart {

	public With getCastedModel() {
		return (With) getModel();
	}

	private boolean isInput() {
		final With with = getCastedModel();
		if (null != with) {
			final Event event = (Event) with.eContainer();
			if (null != event) {
				return event.isIsInput();
			}
		}
		return false;
	}

	private int calculateWithPos() {
		int pos = 1;
		final With with = getCastedModel();
		final Event event = (Event) with.eContainer();
		final InterfaceList interfaceList = (InterfaceList) event.eContainer();
		if (null != interfaceList) {
			pos += ((isInput()) ? interfaceList.getEventInputs() : interfaceList.getEventOutputs()).indexOf(event);
		}
		return pos;
	}

	@Override
	protected void createEditPolicies() {
		// // Selection handle edit policy.
		// // Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		// // Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteWithCommand(getCastedModel());
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		final PolylineConnection connection = (PolylineConnection) super.createFigure();
		updateConnection(connection);
		return connection;
	}

	private void updateConnection(final PolylineConnection connection) {
		final int h = 15;
		final float scale = 0.2f;
		final PointList rect = new PointList();
		final int withPos = calculateWithPos();
		rect.addPoint(-h, -h);
		rect.addPoint(-h, h);
		rect.addPoint(h, h);
		rect.addPoint(h, -h);
		rect.addPoint(-h, -h);
		rect.addPoint(0, -h);
		if (isInput()) {
			rect.addPoint(0, -h - 45);
			rect.addPoint(0, +h + 45 * withPos);
		} else {
			rect.addPoint(0, -h - 45 * withPos);
			rect.addPoint(0, +h + 45);
		}
		rect.addPoint(0, -h);
		final PointList targetRect = new PointList();
		targetRect.addPoint(-h, -h);
		targetRect.addPoint(-h, h);
		targetRect.addPoint(h, h);
		targetRect.addPoint(h, -h);
		targetRect.addPoint(-h, -h);
		targetRect.addPoint(0, -h);
		if (isInput()) {
			targetRect.addPoint(0, -h - 45 * withPos);
			targetRect.addPoint(0, +h + 45);
		} else {
			targetRect.addPoint(0, -h - 45);
			targetRect.addPoint(0, +h + 45 * withPos);
		}
		targetRect.addPoint(0, -h);
		final PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(targetRect.getCopy());
		rectDec.setScale(scale, scale);
		rectDec.setFill(false);
		connection.setTargetDecoration(rectDec);
		final PolygonDecoration rectDec2 = new PolygonDecoration();
		rectDec2.setTemplate(rect.getCopy());
		rectDec2.setScale(scale, scale);
		rectDec2.setFill(false);
		connection.setSourceDecoration(rectDec2);
	}

	public void updateWithPos() {
		if (null != getCastedModel().eContainer()) {
			// if the container is null our model got already removed from it. We don't need
			// to perform updates.
			updateConnection((PolylineConnection) getFigure());
			refreshSourceAnchor();
			refreshTargetAnchor();
		}
	}

	public WithEditPart() {
		super();
	}
}
