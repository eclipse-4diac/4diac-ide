/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				 			Johannes Kepler University Linz
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
 *   Virendra Ashiwal
 *   	- moved calculateWithPos to the InterfaceEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class WithEditPart extends AbstractConnectionEditPart {

	private static final int WITH_BOX_SIZE = 4;
	private static final float WITH_SCALE = 1f;
	private static final int SCALED_WITH_DISTANCE = (int) WithAnchor.WITH_DISTANCE;

	public With getCastedModel() {
		return (With) getModel();
	}

	protected boolean isInput() {
		final With with = getCastedModel();
		if (null != with) {
			final Event event = (Event) with.eContainer();
			if (null != event) {
				return event.isIsInput();
			}
		}
		return false;
	}

	@Override
	protected void createEditPolicies() {
		// // Selection handle edit policy.
		// // Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		// // Allows the removal of the connection model element
		if (isInterfaceEditable()) {
			installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
				@Override
				protected Command getDeleteCommand(final GroupRequest request) {
					return new DeleteWithCommand(getCastedModel());
				}
			});
		}
	}

	@Override
	protected IFigure createFigure() {
		final PolylineConnection connection = (PolylineConnection) super.createFigure();
		updateConnection(connection);
		return connection;
	}

	private void updateConnection(final PolylineConnection connection) {

		final int withPos = InterfaceEditPart.calculateWithPos(getCastedModel(), isInput());

		final PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(createPointList(withPos, false));
		rectDec.setScale(WITH_SCALE, WITH_SCALE);
		rectDec.setFill(false);
		connection.setTargetDecoration(rectDec);

		final PolygonDecoration rectDec2 = new PolygonDecoration();
		rectDec2.setTemplate(createPointList(withPos, true));
		rectDec2.setScale(WITH_SCALE, WITH_SCALE);
		rectDec2.setFill(false);
		connection.setSourceDecoration(rectDec2);
	}

	private PointList createPointList(final int withPos, final boolean top) {
		final PointList rect = new PointList(9);
		rect.addPoint(-WITH_BOX_SIZE, -WITH_BOX_SIZE);
		rect.addPoint(-WITH_BOX_SIZE, WITH_BOX_SIZE);
		rect.addPoint(WITH_BOX_SIZE, WITH_BOX_SIZE);
		rect.addPoint(WITH_BOX_SIZE, -WITH_BOX_SIZE);
		rect.addPoint(-WITH_BOX_SIZE, -WITH_BOX_SIZE);
		rect.addPoint(0, -WITH_BOX_SIZE);
		if (isInput()) {
			if (top) {
				addRightAlignedLine(withPos, rect);
			} else {
				addLeftAlignedLine(withPos, rect);
			}
		} else if (top) {
			addLeftAlignedLine(withPos, rect);
		} else {
			addRightAlignedLine(withPos, rect);
		}
		rect.addPoint(0, -WITH_BOX_SIZE);
		return rect;
	}

	private static void addLeftAlignedLine(final int withPos, final PointList rect) {
		rect.addPoint(0, -SCALED_WITH_DISTANCE * withPos);
		rect.addPoint(0, (int) (SCALED_WITH_DISTANCE * 0.8));
	}

	private static void addRightAlignedLine(final int withPos, final PointList rect) {
		rect.addPoint(0, (int) (-SCALED_WITH_DISTANCE * 0.8));
		rect.addPoint(0, SCALED_WITH_DISTANCE * withPos);
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

	public boolean isInterfaceEditable() {
		return !(EcoreUtil.getRootContainer(getCastedModel()) instanceof FunctionFBType);
	}
}
