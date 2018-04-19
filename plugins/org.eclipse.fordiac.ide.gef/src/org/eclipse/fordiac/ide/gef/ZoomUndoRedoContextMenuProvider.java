/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class ZoomUndoRedoContextMenuProvider.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ZoomUndoRedoContextMenuProvider extends ContextMenuProvider {

	private final ZoomManager zoomManager;
	protected ActionRegistry registry;

	/**
	 * Instantiates a new zoom undo redo context menu provider.
	 * 
	 * @param viewer the viewer
	 * @param zoomManager the zoom manager
	 * @param registry the registry
	 */
	public ZoomUndoRedoContextMenuProvider(final EditPartViewer viewer,
			final ZoomManager zoomManager, final ActionRegistry registry) {
		super(viewer);
		this.zoomManager = zoomManager;
		this.registry = registry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(final IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, new GraphZoomInAction(zoomManager));
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, new GraphZoomOutAction(zoomManager));
		
		IAction action;
		action = registry.getAction(ActionFactory.UNDO.getId());	
		
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
		action = registry.getAction(ActionFactory.REDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
		
		// Alignment Actions
		MenuManager submenu = new MenuManager("&Align");

		action = registry.getAction(GEFActionConstants.ALIGN_LEFT);
		if (action != null && action.isEnabled())
			submenu.add(action);

		action = registry.getAction(GEFActionConstants.ALIGN_CENTER);
		if (action != null && action.isEnabled())
			submenu.add(action);

		action = registry.getAction(GEFActionConstants.ALIGN_RIGHT);
		if (action != null && action.isEnabled())
			submenu.add(action);

		submenu.add(new Separator());

		action = registry.getAction(GEFActionConstants.ALIGN_TOP);
		if (action != null && action.isEnabled())
			submenu.add(action);

		action = registry.getAction(GEFActionConstants.ALIGN_MIDDLE);
		if (action != null && action.isEnabled())
			submenu.add(action);

		action = registry.getAction(GEFActionConstants.ALIGN_BOTTOM);
		if (action != null && action.isEnabled())
			submenu.add(action);

		if (!submenu.isEmpty()){
			menu.appendToGroup(GEFActionConstants.GROUP_REST, submenu);
		}
	}

	private final class GraphZoomInAction extends ZoomInAction {
		public GraphZoomInAction(final ZoomManager zoomManager) {
			super(zoomManager);
		}

		@Override
		public boolean isEnabled() {
			return zoomManager.canZoomIn();
		}
	}

	private final class GraphZoomOutAction extends ZoomOutAction {
		public GraphZoomOutAction(final ZoomManager zoomManager) {
			super(zoomManager);
		}

		@Override
		public boolean isEnabled() {
			return zoomManager.canZoomOut();
		}
	}
}
