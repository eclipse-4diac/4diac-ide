/*******************************************************************************
 * Copyright (c) 2019, 2021 Johannes Kepler University Linz,
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

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class FordiacConnectionDragCreationTool extends ConnectionDragCreationTool {

	// Safety border around the canvas to ensure that during dragging connections
	// the canvas is not growing
	private static final Insets NEW_CONNECTION_CANVAS_BORDER = new Insets(1,
			1 + MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN + HideableConnection.BEND_POINT_BEVEL_SIZE, 1,
			1 + MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN + HideableConnection.BEND_POINT_BEVEL_SIZE);

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
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof final AdvancedScrollingGraphicalViewer advViewer) {
//			advViewer.checkScrollPositionDuringDragBounded(me,
//					new Point(MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN
//							+ HideableConnection.BEND_POINT_BEVEL_SIZE + ConnectionPreferenceValues.HANDLE_SIZE,
//							ConnectionPreferenceValues.HANDLE_SIZE));
//			CanvasHelper.bindToContentPane(me, advViewer, NEW_CONNECTION_CANVAS_BORDER);
		}
		super.mouseDrag(me, viewer);
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		if (((me.stateMask & SWT.MOD2) != 0)) {
			checkCurrentCommandforShiftMask();
		}
		super.mouseUp(me, viewer);
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

	private static void startHover() {
		UIPlugin.getDefault().getEMH().setHover(true);
	}

	private static void stopHover() {
		UIPlugin.getDefault().getEMH().setHover(false);
	}

	@Override
	protected boolean updateTargetUnderMouse() {
		if (isTargetLocked()) {
			return false;
		}
		final EditPartViewer currentViewer = getCurrentViewer();
		final EditPartViewer actualViewer = findViewer(getLocation().getSWTPoint(), currentViewer);
		if (actualViewer != currentViewer) {
			final Point actualLocation = convertCoordinates(getLocation().getSWTPoint(), currentViewer, actualViewer);
			EditPart editPart = actualViewer.findObjectAtExcluding(
					new org.eclipse.draw2d.geometry.Point(actualLocation), getExclusionSet(),
					getTargetingConditional());
			if (editPart != null) {
				editPart = editPart.getTargetEditPart(getTargetRequest());
			}
			final boolean changed = getTargetEditPart() != editPart;
			setTargetEditPart(editPart);
			return changed;
		}
		return super.updateTargetUnderMouse();
	}

	private static EditPartViewer findViewer(final Point point, final EditPartViewer viewer) {
		if (viewer == null) {
			return null;
		}
		if (viewer.getControl().getBounds().contains(point)) {
			return viewer;
		}
		final Point absolute = viewer.getControl().toDisplay(point);
		return Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows())
				.flatMap(window -> Stream.of(window.getPages())).flatMap(page -> Stream.of(page.getEditorReferences()))
				.map(ref -> ref.getEditor(false)).filter(Objects::nonNull)
				.<EditPartViewer>map(editor -> editor.getAdapter(GraphicalViewer.class)).filter(Objects::nonNull)
				.filter(candidate -> containsAbsolutePoint(candidate, absolute)).findAny().orElse(viewer);
	}

	private static boolean containsAbsolutePoint(final EditPartViewer viewer, final Point point) {
		final Control control = viewer.getControl();
		if (control.getParent() != null) {
			return control.getBounds().contains(control.getParent().toControl(point));
		}
		return control.getBounds().contains(point);
	}

	private static Point convertCoordinates(final Point point, final EditPartViewer from, final EditPartViewer to) {
		if (from == to) {
			return point;
		}
		return to.getControl().toControl(from.getControl().toDisplay(point.x, point.y));
	}
}