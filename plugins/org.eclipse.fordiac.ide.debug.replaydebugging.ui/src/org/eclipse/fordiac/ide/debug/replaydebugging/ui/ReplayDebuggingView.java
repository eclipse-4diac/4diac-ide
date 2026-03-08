/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import org.eclipse.draw2d.zoom.AbstractZoomManager;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.IReplayNavigatorRegistrationListener;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigatorManager;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.action.MoveDown;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.action.MoveOneEventBackwards;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.action.MoveOneEventForward;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.action.MoveUp;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.TimelineEditPartFactory;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Session;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * @brief View for replay debugging.
 *
 *        The view listen to replay navigators being registered or unregistered
 *        and creates or removes the corresponding model for them. It also
 *        listen to keyboard events to navigate through the timelines.
 */
public class ReplayDebuggingView extends ViewPart implements IReplayNavigatorRegistrationListener {

	private GraphicalViewer viewer;
	private final Session session = new Session();

	@Override
	public void createPartControl(final Composite parent) {

		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);

		final var root = new ScalableRootEditPart();
		root.getZoomManager().setZoomLevels(new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0 });
		root.getZoomManager().setZoomAnimationStyle(AbstractZoomManager.ANIMATE_ZOOM_IN_OUT);

		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new TimelineEditPartFactory());

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);

		viewer.setEditDomain(new DefaultEditDomain(null));

		final KeyHandler keyHandler = new KeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, 0), new MoveOneEventForward(viewer));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, 0), new MoveOneEventBackwards(viewer));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, 0), new MoveUp(viewer));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, 0), new MoveDown(viewer));

		viewer.setKeyHandler(keyHandler);

		ReplayNavigatorManager.getDefault().addListener(this);
		parent.addDisposeListener(e -> ReplayNavigatorManager.getDefault().removeListener(this));
		viewer.setContents(session);

	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void replayNavigatorRegistered(final ReplayNavigator navigator) {
		session.addReplayNavigator(navigator);
	}

	@Override
	public void replayNavigatorUnregistered(final ReplayNavigator navigator) {
		session.removeReplayNavigator(navigator);
	}
}
