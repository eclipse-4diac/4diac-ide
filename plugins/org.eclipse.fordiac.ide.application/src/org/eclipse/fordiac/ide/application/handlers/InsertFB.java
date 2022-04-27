/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - reworked execution to also handle group and subapp creation
 *                 requests
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class InsertFB extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			final SelectionRequest request = createSelectionRequest(viewer);
			getTargetEditPart(viewer).performRequest(request);
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME);
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		setBaseEnabled(null != viewer);
	}

	private static Point getInsertPoint(final GraphicalViewer viewer) {
		if (viewer.getContextMenu() instanceof FBNetworkContextMenuProvider) {
			return new Point(((FBNetworkContextMenuProvider) viewer.getContextMenu()).getPoint());
		}
		return new Point(0, 0);
	}

	private static EditPart getTargetEditPart(final GraphicalViewer viewer) {
		return (EditPart) viewer.getSelectedEditParts().stream().filter(FBNetworkEditPart.class::isInstance).findAny()
				.orElse(viewer.getRootEditPart());
	}

	private static SelectionRequest createSelectionRequest(final GraphicalViewer viewer) {
		final SelectionRequest request = new SelectionRequest();
		request.setLocation(getInsertPoint(viewer));
		request.setType(RequestConstants.REQ_OPEN);
		return request;
	}
}


