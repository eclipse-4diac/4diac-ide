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
package org.eclipse.fordiac.ide.application.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.application.commands.TargetLabelReconnectCommand;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.tools.FordiacConnectionDragCreationTool;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class TargetInterfaceSelectionPolicy extends ModifiedNonResizeableEditPolicy {
	private Connection line;

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> list = new ArrayList<>(super.createSelectionHandles());
		if (line != null) {
			list.add(new ConnectionHandle(getHost(), line));
		}
		return list;
	}

	@Override
	protected void showSelection() {
		if (line == null) {
			addConnectionLine();
		}
		super.showSelection();
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();

		if (line != null) {
			deleteLine();
		}
	}

	@Override
	public Command getCommand(final Request request) {
		if (REQ_CONNECTION_START.equals(request.getType())) {
			return getConnectionCreateCommand((CreateConnectionRequest) request);
		}

		return super.getCommand(request);
	}

	protected final Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final TargetLabelReconnectCommand cmd = new TargetLabelReconnectCommand(null,
				getHost().getModel().getRefElement());
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	public TargetInterfaceElementEditPart getHost() {
		return (TargetInterfaceElementEditPart) super.getHost();
	}

	private void addConnectionLine() {
		final Connection newCon = new PolylineConnection();
		newCon.setConnectionRouter(((ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER)).getConnectionRouter());

		if (getHost().getParent() instanceof final InterfaceEditPart iep && !iep.isInput()) {
			if (iep.getTargetConnections().isEmpty() || iep.getChildren().stream()
					.filter(TargetInterfaceElementEditPart.class::isInstance).limit(2).count() < 2) {
				// we don't want a connection if pin has no connection or only 1 TargetLabel
				return;
			}

			newCon.setTargetAnchor(new FixedAnchor(getHostFigure(), true));
			if (iep.getTargetConnections().getFirst()
					.getSource() instanceof final InterfaceEditPart connectionSourceIEP) {
				newCon.setSourceAnchor(connectionSourceIEP.getSourceConnectionAnchor(new Request()));
			}
		}

		line = newCon;
		addFeedback(line);
	}

	private void deleteLine() {
		removeFeedback(line);
		line = null;
	}

	private class ConnectionHandle extends SquareHandle {
		public ConnectionHandle(final GraphicalEditPart host, final Connection connectionLine) {
			super(host, new ConnectionEndpointLocator(connectionLine, false));
		}

		@Override
		protected DragTracker createDragTracker() {
			return new FordiacConnectionDragCreationTool() {
				@Override
				protected boolean updateTargetUnderMouse() {
					if (isInState(STATE_INITIAL)) {
						// we are the first target under mouse
						setTargetEditPart(getHost());
						return true;
					}

					return super.updateTargetUnderMouse();
				}
			};
		}
	}
}
