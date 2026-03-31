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
 *   Virendra Ashiwal
 *   	- moved calculateWithPos to the InterfaceEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.preferences.FBInterfaceEditorColors;
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

	private static final int WITH_BOX_HEIGHT = 8;
	private static final int WITH_BOX_WIDTH = 7;
	private static final int WITH_BOX_ARC_SIZE = 4; // arc size is the diameter

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
		connection.setForegroundColor(FBInterfaceEditorColors.getWithLineColor());
		return connection;
	}

	private static void updateConnection(final PolylineConnection connection) {
		final RoundedRectangleDecoration srcRectDec = new RoundedRectangleDecoration(WITH_BOX_WIDTH, WITH_BOX_HEIGHT,
				WITH_BOX_ARC_SIZE);
		srcRectDec.setBackgroundColor(FBInterfaceEditorColors.getWithBoxColor());
		connection.setTargetDecoration(srcRectDec);

		final RoundedRectangleDecoration targetRectDec = new RoundedRectangleDecoration(WITH_BOX_WIDTH, WITH_BOX_HEIGHT,
				WITH_BOX_ARC_SIZE);
		targetRectDec.setBackgroundColor(FBInterfaceEditorColors.getWithBoxColor());
		connection.setSourceDecoration(targetRectDec);
	}

	private static class RoundedRectangleDecoration extends Figure implements RotatableDecoration {

		private final int arcSize;

		public RoundedRectangleDecoration(final int width, final int height, final int arcSize) {
			this.arcSize = arcSize;
			setPreferredSize(width, height);
			setOpaque(true);
			setBackgroundColor(ColorConstants.black);
		}

		@Override
		protected void paintFigure(final Graphics g) {
			g.fillRoundRectangle(getBounds(), arcSize, arcSize);
		}

		@Override
		public void setLocation(final Point p) {
			final Dimension prefSize = getPreferredSize();
			setBounds(new Rectangle(p.x - prefSize.width / 2, p.y - prefSize.height / 2, prefSize.width,
					prefSize.height));
		}

		@Override
		public void setReferencePoint(final Point ref) {
			// Not needed for vertical connections, but required by interface
		}

	}

	@Override
	public void refresh() {
		super.refresh();
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
