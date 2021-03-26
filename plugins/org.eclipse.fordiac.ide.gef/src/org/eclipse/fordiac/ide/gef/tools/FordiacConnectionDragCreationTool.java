/*******************************************************************************
 * Copyright (c) 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - keep connection draging within canvas bounds
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
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
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof AdvancedScrollingGraphicalViewer) {
			((AdvancedScrollingGraphicalViewer) viewer)
			.checkScrollPositionDuringDragBounded(me,
					new Point(
							MoveableRouter.MIN_CONNECTION_FB_DISTANCE + HideableConnection.BEND_POINT_BEVEL_SIZE
							+ ConnectionPreferenceValues.HANDLE_SIZE,
							ConnectionPreferenceValues.HANDLE_SIZE));
		}
		super.mouseDrag(me, viewer);
	}

	@Override
	protected void setCurrentCommand(final Command c) {
		if(null == getCurrentCommand() && null != c) {
			// Hover started
			startHover();
		} else if (null != getCurrentCommand() && null != c && c != getCurrentCommand()) {
			// Hover changed
			stopHover();
			startHover();
		} else if (null != getCurrentCommand() && null == c) {
			// Hover stopped
			stopHover();
		}
		super.setCurrentCommand(c);
	}

	private static void startHover() {
		UIPlugin.getDefault().getEMH().setHover(true);
	}

	private static void stopHover() {
		UIPlugin.getDefault().getEMH().setHover(false);
	}

}