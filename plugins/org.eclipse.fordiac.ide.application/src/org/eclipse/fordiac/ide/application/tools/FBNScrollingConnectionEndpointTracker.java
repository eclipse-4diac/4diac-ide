/*******************************************************************************
 * Copyright (c) 2021, 2025 Primetals Technologies Austria GmbH
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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.tools.InlineConnectionCreationTool;
import org.eclipse.fordiac.ide.gef.tools.ScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;

public class FBNScrollingConnectionEndpointTracker extends ScrollingConnectionEndpointTracker {

	EditPartViewer initialViewer;

	private static final int MOUSE_LEFT = 1;

	public FBNScrollingConnectionEndpointTracker(final org.eclipse.gef.ConnectionEditPart cep) {
		super(cep);
	}

	private ConnectionRoutingData originalRoutingData;

	@Override
	public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
		initialViewer = viewer;
		super.mouseDown(me, viewer);
	}

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
	protected void showSourceFeedback() {
		if (differentTargetViewer()) {
			final Point location = getLocation();
			final Point converted = new Point(initialViewer.getControl()
					.toControl(getCurrentViewer().getControl().toDisplay(location.x, location.y)));
			((ReconnectRequest) getTargetRequest()).setLocation(converted);
		}
		super.showSourceFeedback();
		if (differentTargetViewer()) {
			((ReconnectRequest) getTargetRequest()).setLocation(getLocation());
		}
	}

	@Override
	protected ConnectionDragCreationTool createConnectionCreationTool(final EditPart target) {
		return InlineConnectionCreationTool.createInlineConnCreationTool(target, getDomain(),
				differentTargetViewer() ? initialViewer : getCurrentViewer(), getLocation());
	}

	private boolean differentTargetViewer() {
		return initialViewer != null && initialViewer != getCurrentViewer();
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

}