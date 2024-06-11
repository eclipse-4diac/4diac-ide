/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added connection re-connection across editor boundaries
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.dnd;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.handlers.AdvancedGraphicalViewerKeyHandler;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.dnd.AbstractTransferDragSourceListener;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceEvent;

public class CustomDragSourceListener extends AbstractTransferDragSourceListener {

	private Request lastReq = null;

	public CustomDragSourceListener(final EditPartViewer viewer) {
		super(viewer, CustomSourceTransfer.getInstance());
		Assert.isTrue(viewer.getKeyHandler() instanceof AdvancedGraphicalViewerKeyHandler);
	}

	@Override
	public void dragStart(final DragSourceEvent event) {
		final Request req = createRequest(new Point(event.x, event.y));

		if (!isAltKeyPressed() || req == null) {
			event.doit = false;
			lastReq = null;
			return;
		}
		lastReq = req;
		CustomSourceTransfer.getInstance().setObject(req);
	}

	@Override
	public void dragSetData(final DragSourceEvent event) {
		event.data = lastReq;
	}

	@Override
	public void dragFinished(final DragSourceEvent event) {
		super.dragFinished(event);
		CustomSourceTransfer.getInstance().setObject(null);
		lastReq = null;
	}

	private boolean isAltKeyPressed() {
		final AdvancedGraphicalViewerKeyHandler keyHandler = (AdvancedGraphicalViewerKeyHandler) getViewer()
				.getKeyHandler();
		return keyHandler.getCurrentKeyCode() == SWT.ALT;
	}

	private Request createRequest(final Point point) {
		final List<? extends EditPart> selectedEditParts = getViewer().getSelectedEditParts();
		if (selectedEditParts.size() == 1 && selectedEditParts.get(0) instanceof final InterfaceEditPart iep) {
			return createConnectionCreationRequest(iep);
		}
		if (selectedEditParts.size() == 1 && selectedEditParts.get(0).getModel() instanceof FBNetworkElement) {
			return createChangeBoundsRequest(selectedEditParts);
		}
		final List<ConnectionEditPart> connections = selectedEditParts.stream()
				.filter(ConnectionEditPart.class::isInstance).map(ep -> (ConnectionEditPart) ep).toList();
		if (!connections.isEmpty()) {
			return createReconnectRequest(connections, point);
		}
		return null;
	}

	private static Request createConnectionCreationRequest(final InterfaceEditPart iep) {
		final CreateConnectionRequest req = new CreateConnectionRequest();
		req.setType(RequestConstants.REQ_CONNECTION_START);
		req.setSourceEditPart(iep);
		req.setStartCommand(iep.getCommand(req));
		req.setType(RequestConstants.REQ_CONNECTION_END);
		return req;
	}

	private static Request createReconnectRequest(final List<ConnectionEditPart> connections, final Point point) {
		final ConnectionEditPart first = connections.get(0);
		final String reconnectType = getReconnectType(first, point);
		if (reconnectType != null) {
			// only create a ReconnectRequest when the drag was from a valid connection
			// end point handle
			final ReconnectRequest req = new ReconnectRequest(reconnectType);
			req.setConnectionEditPart(first);
			if (connections.size() > 1) {
				// we are reconnecting a fanned connection
				req.getExtendedData().put(CustomSourceTransfer.CONNECTIONS_LIST, connections);
			}
			final EditPart target = (req.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) ? first.getSource()
					: first.getTarget();
			req.setTargetEditPart(target);
			return req;
		}
		return null;
	}

	private static Request createChangeBoundsRequest(final List<? extends EditPart> selectedEditParts) {
		final ChangeBoundsRequest request = new ChangeBoundsRequest();
		request.setEditParts(selectedEditParts);
		request.setType(RequestConstants.REQ_ADD);
		return request;
	}

	private static String getReconnectType(final ConnectionEditPart first, final Point point) {
		final ConnectionEndpointHandle connectionHandle = getConnectionHandle(first, point);
		if (connectionHandle != null) {
			if (connectionHandle.getEndPoint() == ConnectionLocator.SOURCE) {
				return RequestConstants.REQ_RECONNECT_SOURCE;
			}
			return RequestConstants.REQ_RECONNECT_TARGET;
		}

		return null;
	}

	private static ConnectionEndpointHandle getConnectionHandle(final ConnectionEditPart first, final Point point) {
		final List<? extends IFigure> handles = LayerManager.Helper.find(first).getLayer(LayerConstants.HANDLE_LAYER)
				.getChildren();
		for (final IFigure handle : handles) {
			final Point pointCopy = point.getCopy();
			handle.translateToRelative(pointCopy);
			if (handle.getBounds().contains(pointCopy) && handle instanceof final ConnectionEndpointHandle conHandle) {
				return conHandle;
			}
		}
		return null;
	}

}
