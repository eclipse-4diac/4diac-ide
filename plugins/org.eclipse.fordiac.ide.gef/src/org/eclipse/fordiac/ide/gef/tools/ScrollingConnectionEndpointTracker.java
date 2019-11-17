/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.swt.events.MouseEvent;

public class ScrollingConnectionEndpointTracker extends ConnectionEndpointTracker {

	public ScrollingConnectionEndpointTracker(ConnectionEditPart cep) {
		super(cep);
	}

	@Override
	public void mouseDrag(MouseEvent me, EditPartViewer viewer) {
		if (isActive() && viewer instanceof AdvancedScrollingGraphicalViewer) {
			Point oldViewPort = ((AdvancedScrollingGraphicalViewer) viewer).getViewLocation();
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
			Dimension delta = oldViewPort.getDifference(((AdvancedScrollingGraphicalViewer) viewer).getViewLocation());
			// Compensate the moved scrolling in the start position for correct dropping of
			// moved parts
			setStartLocation(getStartLocation().getTranslated(delta));
		}
		super.mouseDrag(me, viewer);
	}
}
