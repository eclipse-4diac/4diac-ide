/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria
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
package org.eclipse.fordiac.ide.application.tools;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.tools.ScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;

public class FBNScrollingConnectionEndpointTracker extends ScrollingConnectionEndpointTracker {

	private static final int MOUSE_LEFT = 1;

	public FBNScrollingConnectionEndpointTracker(final org.eclipse.gef.ConnectionEditPart cep) {
		super(cep);
	}

	private ConnectionRoutingData originalRoutingData;

	@Override
	protected boolean handleButtonDown(final int button) {
		if (button == MOUSE_LEFT) {
			// only check selection on left mouse click
			performSelection();
		}
		return super.handleButtonDown(button);
	}

	@Override
	protected boolean handleDoubleClick(final int button) {
		if (button == 1) {
			performOpen();
		}
		return true;
	}

	/**
	 * This selection updater is based on perform selection from
	 * {@link org.eclipse.gef.tools.SelectEditPartTracker}
	 */
	protected void performSelection() {
		final EditPartViewer viewer = getCurrentViewer();
		final List<? extends EditPart> selectedObjects = viewer.getSelectedEditParts();

		if (getCurrentInput().isModKeyDown(SWT.MOD1) && !getCurrentInput().isModKeyDown(SWT.MOD3)) {
			if (selectedObjects.contains(getConnectionEditPart())) {
				viewer.deselect(getConnectionEditPart());
			} else {
				viewer.appendSelection(getConnectionEditPart());
			}
		} else if (getCurrentInput().isShiftKeyDown()) {
			viewer.appendSelection(getConnectionEditPart());
		} else {
			viewer.select(getConnectionEditPart());
		}
	}

	@Override
	protected boolean handleDragStarted() {
		final Connection conn = get4diacConnection();
		if (conn != null) {
			originalRoutingData = EcoreUtil.copy(conn.getRoutingData());
		}
		return super.handleDragStarted();
	}

	@Override
	protected void executeCurrentCommand() {
		if (shouldRestoreRoutingData()) {
			get4diacConnection().setRoutingData(originalRoutingData);
		}
		super.executeCurrentCommand();
	}

	@Override
	protected Insets getCanvasBorder() {
		final org.eclipse.fordiac.ide.model.libraryElement.Connection conn = get4diacConnection();
		if (null != conn) {
			conn.getRoutingData().setNeedsValidation(true);
			if (conn.getRoutingData().is3SegementData()
					&& RequestConstants.REQ_RECONNECT_SOURCE.equals(getCommandName())) {
				// if we have a 3 segment connection and we are dragging the destination we need
				// to take the first segment into account for the border
				final Insets adjustedBorder = new Insets(super.getCanvasBorder());
				adjustedBorder.right += toScreen(conn.getRoutingData().getDx1());
				return adjustedBorder;
			}
			if (conn.getRoutingData().is5SegementData()) {
				return get5SegmentCanvasBorder(conn.getRoutingData());
			}
		}

		return super.getCanvasBorder();
	}

	private Insets get5SegmentCanvasBorder(final ConnectionRoutingData routingData) {
		final Insets adjustedBorder = new Insets(super.getCanvasBorder());
		if (RequestConstants.REQ_RECONNECT_SOURCE.equals(getCommandName())) {
			adjustedBorder.right += toScreen(routingData.getDx1());
			if (routingData.getDy() < 0) {
				adjustedBorder.top -= toScreen(routingData.getDy());
			}
		}
		if (RequestConstants.REQ_RECONNECT_TARGET.equals(getCommandName())) {
			adjustedBorder.left += toScreen(routingData.getDx2());
		}
		return adjustedBorder;
	}

	private boolean shouldRestoreRoutingData() {
		final Connection con = get4diacConnection();
		if (con != null) {
			final Command curCommand = getCurrentCommand();
			if ((curCommand == null) || !curCommand.canExecute()) {
				return true;
			}
			if (curCommand instanceof final AbstractReconnectConnectionCommand cmd) {
				return con.getSource().equals(cmd.getNewSource())
						&& con.getDestination().equals(cmd.getNewDestination());
			}
		}
		return false;
	}

	protected Connection get4diacConnection() {
		if (getConnectionEditPart()
				.getModel() instanceof final org.eclipse.fordiac.ide.model.libraryElement.Connection conn) {
			return conn;
		}
		return null;
	}

	private void performOpen() {
		final SelectionRequest request = new SelectionRequest();
		request.setLocation(getLocation());
		request.setType(RequestConstants.REQ_OPEN);
		getConnectionEditPart().performRequest(request);
	}

	private static int toScreen(final double val) {
		return CoordinateConverter.INSTANCE.iec61499ToScreen(val);
	}

}