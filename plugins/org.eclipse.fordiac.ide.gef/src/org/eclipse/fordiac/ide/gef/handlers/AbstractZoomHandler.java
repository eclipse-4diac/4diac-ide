/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.editparts.AdvancedZoomManager;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractZoomHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		if (part != null) {
			final ZoomManager zoomManager = part.getAdapter(ZoomManager.class);
			if (zoomManager != null) {
				handleMouseLocation(part, zoomManager);
				Display.getDefault().syncExec(() -> performZoom(zoomManager));
			}

		}
		return Status.OK_STATUS;
	}

	protected abstract void performZoom(final ZoomManager zoomManager);

	protected static void handleMouseLocation(final IWorkbenchPart part, final ZoomManager zoomManager) {
		if (zoomManager instanceof AdvancedZoomManager) {
			final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				final Rectangle controlBounds = viewer.getControl().getBounds();
				Point cursorLocation = Display.getDefault().getCursorLocation();
				cursorLocation = viewer.getControl().getParent().toControl(cursorLocation);
				if (controlBounds.contains(cursorLocation)) {
					((AdvancedZoomManager) zoomManager).setLastMousePos(cursorLocation.x, cursorLocation.y);
				}
			}
		}
	}

}