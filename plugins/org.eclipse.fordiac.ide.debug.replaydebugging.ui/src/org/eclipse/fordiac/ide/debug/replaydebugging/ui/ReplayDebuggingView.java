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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.TimelineEditPartFactory;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Session;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.contexts.IContextService;
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

	private static String menuId = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.ReplayDebuggingView"; //$NON-NLS-1$

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

		setMenu();

		getSite().getService(IContextService.class)
				.activateContext("org.eclipse.fordiac.ide.debug.replaydebugging.context"); //$NON-NLS-1$

		// Wire undo/redo to the workbench
		final UndoAction undoAction = new UndoAction(this);
		final RedoAction redoAction = new RedoAction(this);

		final IActionBars bars = getViewSite().getActionBars();
		bars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
		bars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);

		viewer.getEditDomain().getCommandStack().addCommandStackEventListener(e -> {
			undoAction.update();
			redoAction.update();
			bars.updateActionBars();
		});

		ReplayNavigatorManager.getDefault().addListener(this);
		SelectionService.getDefault().install(getSite().getPage());
		parent.addDisposeListener(e -> {
			ReplayNavigatorManager.getDefault().removeListener(this);
			SelectionService.getDefault().uninstall(getSite().getPage());
		});
		viewer.setContents(session);

	}

	private void setMenu() {

		// Register context menu — links the viewer's selection to Eclipse's menu
		// framework
		final MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);

		final Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		// This ID must match the locationURI in plugin.xml (without "popup:")
		getSite().registerContextMenu(menuId, menuManager, viewer);

		// This makes the viewer's selection available to handlers via HandlerUtil
		getSite().setSelectionProvider(viewer);
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

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == GraphicalViewer.class) {
			return adapter.cast(viewer);
		}
		if (adapter == CommandStack.class) {
			return adapter.cast(viewer.getEditDomain().getCommandStack());
		}
		return super.getAdapter(adapter);
	}
}
