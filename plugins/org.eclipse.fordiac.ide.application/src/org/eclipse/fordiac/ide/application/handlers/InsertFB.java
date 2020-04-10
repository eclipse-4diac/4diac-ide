/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class InsertFB extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			SelectionRequest request = new SelectionRequest();
			request.setLocation(getInsertPoint(viewer));
			request.setType(RequestConstants.REQ_OPEN);
			viewer.getRootEditPart().performRequest(request);
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME);
		GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		setBaseEnabled(null != viewer);
	}

	private static Point getInsertPoint(GraphicalViewer viewer) {
		if (viewer.getContextMenu() instanceof FBNetworkContextMenuProvider) {
			return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getPoint();
		}
		return new Point(0, 0);
	}

}
