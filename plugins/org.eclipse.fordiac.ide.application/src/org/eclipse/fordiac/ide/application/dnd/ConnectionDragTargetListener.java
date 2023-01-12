/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.dnd;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class ConnectionDragTargetListener extends AbstractTransferDropTargetListener {

	public ConnectionDragTargetListener(final EditPartViewer viewer) {
		super(viewer, ConnSourceTransfer.getInstance());
		setEnablementDeterminedByCommand(true);
	}

	@Override
	protected Request createTargetRequest() {
		final CreateConnectionRequest req = new CreateConnectionRequest();
		req.setType(RequestConstants.REQ_CONNECTION_START);
		final InterfaceEditPart source = ConnSourceTransfer.getInstance().getObject();
		req.setSourceEditPart(source);
		req.setStartCommand(source.getCommand(req));
		req.setType(RequestConstants.REQ_CONNECTION_END);
		return req;
	}

	@Override
	protected CreateConnectionRequest getTargetRequest() {
		return (CreateConnectionRequest) super.getTargetRequest();
	}

	@Override
	protected void updateTargetRequest() {
		getTargetRequest().setLocation(getDropLocation());
	}

}
