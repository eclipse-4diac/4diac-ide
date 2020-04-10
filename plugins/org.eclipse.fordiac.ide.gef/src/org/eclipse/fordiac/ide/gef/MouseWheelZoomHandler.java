/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
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
package org.eclipse.fordiac.ide.gef;

import org.eclipse.fordiac.ide.gef.editparts.AdvancedZoomManager;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.widgets.Event;

public enum MouseWheelZoomHandler implements MouseWheelHandler {
	SINGLETON;

	@Override
	public void handleMouseWheel(Event event, EditPartViewer viewer) {
		ZoomManager zoomManager = (ZoomManager) viewer.getProperty(ZoomManager.class.toString());
		if (null != zoomManager) {
			if (zoomManager instanceof AdvancedZoomManager) {
				((AdvancedZoomManager) zoomManager).setLastMousePos(event.x, event.y);
			}

			if (event.count > 0) {
				zoomManager.zoomIn();
			} else {
				zoomManager.zoomOut();
			}
			event.doit = false;
		}

	}

}
