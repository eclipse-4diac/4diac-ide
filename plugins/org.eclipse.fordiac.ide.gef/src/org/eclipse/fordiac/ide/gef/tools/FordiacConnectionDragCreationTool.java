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

import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;

public class FordiacConnectionDragCreationTool extends ConnectionDragCreationTool {

	public FordiacConnectionDragCreationTool() {
		super();
		setDefaultCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_CROSS));
		setDisabledCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_NO));
	}

	@Override
	public void mouseDrag(MouseEvent me, EditPartViewer viewer) {
		super.mouseDrag(me, viewer);
		if (isActive() && viewer instanceof AdvancedScrollingGraphicalViewer) {
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
		}
	}

}