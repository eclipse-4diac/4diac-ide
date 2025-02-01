/*******************************************************************************
 * Copyright (c) 2019, 2025 Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;

public class FordiacConnectionDragCreationTool extends ConnectionDragCreationTool {

	EditPartViewer initialViewer;

	public FordiacConnectionDragCreationTool() {
		setDefaultCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_CROSS));
		setDisabledCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_NO));
	}

	@Override
	public void deactivate() {
		stopHover();
		super.deactivate();
	}

	@Override
	protected boolean handleMove() {
		// overwritten to disable viewer check
		if (isInState(STATE_CONNECTION_STARTED | STATE_INITIAL | STATE_ACCESSIBLE_DRAG_IN_PROGRESS)) {
			updateTargetRequest();
			updateTargetUnderMouse();
			showSourceFeedback();
			showTargetFeedback();
			setCurrentCommand(getCommand());
		}
		return true;
	}

	@Override
	public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
		// store the viewer to be used as the viewer for mouse cursor operations
		initialViewer = viewer;
		super.mouseDown(me, viewer);
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		if (((me.stateMask & SWT.MOD2) != 0)) {
			checkCurrentCommandforShiftMask();
		}
		super.mouseUp(me, viewer);
		initialViewer = null;
	}

	@Override
	protected void setCursor(final Cursor cursor) {
		if (isInState(STATE_CONNECTION_STARTED | STATE_DRAG_IN_PROGRESS) && initialViewer != null
				&& getCurrentViewer() != null && initialViewer != getCurrentViewer()) {
			initialViewer.setCursor(cursor);
		} else {
			super.setCursor(cursor);
		}

	}

	@Override
	protected void showSourceFeedback() {
		if (differentTargetViewer()) {
			final Point location = getLocation();
			final Point converted = new Point(initialViewer.getControl()
					.toControl(getCurrentViewer().getControl().toDisplay(location.x, location.y)));
			getTargetRequest().setLocation(converted);
		}
		super.showSourceFeedback();
		if (differentTargetViewer()) {
			getTargetRequest().setLocation(getLocation());
		}
	}

	private boolean differentTargetViewer() {
		return initialViewer != null && initialViewer != getCurrentViewer();
	}

	private void checkCurrentCommandforShiftMask() {
		final Command curCmd = getCurrentCommand();
		if (curCmd instanceof final AbstractConnectionCreateCommand conCreateCmd) {
			conCreateCmd.setVisible(false);
		}
	}

	@Override
	protected void setCurrentCommand(final Command c) {
		if (null == getCurrentCommand() && null != c) {
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

	protected void setInitialViewer(final EditPartViewer initialViewer) {
		this.initialViewer = initialViewer;
	}

	private static void startHover() {
		UIPlugin.getDefault().getEMH().setHover(true);
	}

	private static void stopHover() {
		UIPlugin.getDefault().getEMH().setHover(false);
	}

}