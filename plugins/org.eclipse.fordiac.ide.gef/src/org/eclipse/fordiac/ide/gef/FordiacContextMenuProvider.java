/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2015 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;

/**
 * The Class ZoomUndoRedoContextMenuProvider.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FordiacContextMenuProvider extends ContextMenuProvider {

	public static final String GROUP_NAVIGATE = "org.fordiac.ide.group.navigate";

	private final ZoomManager zoomManager;
	private final ActionRegistry registry;

	/**
	 * Instantiates a new zoom undo redo context menu provider.
	 *
	 * @param viewer      the viewer
	 * @param zoomManager the zoom manager
	 * @param registry    the registry
	 */
	public FordiacContextMenuProvider(final EditPartViewer viewer, final ZoomManager zoomManager,
			final ActionRegistry registry) {
		super(viewer);
		this.zoomManager = zoomManager;
		this.registry = registry;
	}

	public ActionRegistry getRegistry() {
		return registry;
	}

	protected ZoomManager getZoomManager() {
		return zoomManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(final IMenuManager menu) {
		addActionGroups(menu);
		MenuManager alignSubMenu = createAlignSubmenu();
		if (!alignSubMenu.isEmpty()) {
			menu.appendToGroup(IWorkbenchActionConstants.GROUP_REORGANIZE, alignSubMenu);
		}
	}

	private MenuManager createAlignSubmenu() {
		MenuManager submenu = new MenuManager("&Align");

		IAction action;
		action = registry.getAction(GEFActionConstants.ALIGN_LEFT);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}

		action = registry.getAction(GEFActionConstants.ALIGN_CENTER);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}

		action = registry.getAction(GEFActionConstants.ALIGN_RIGHT);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}

		submenu.add(new Separator());

		action = registry.getAction(GEFActionConstants.ALIGN_TOP);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}

		action = registry.getAction(GEFActionConstants.ALIGN_MIDDLE);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}

		action = registry.getAction(GEFActionConstants.ALIGN_BOTTOM);
		if ((null != action) && action.isEnabled()) {
			submenu.add(action);
		}
		return submenu;
	}

	private static void addActionGroups(final IMenuManager menu) {
		menu.add(new Separator(GEFActionConstants.GROUP_EDIT));
		menu.add(new Separator(GROUP_NAVIGATE));
		menu.add(new Separator(GEFActionConstants.GROUP_COPY));
		menu.add(new Separator(GEFActionConstants.GROUP_VIEW));
		menu.add(new Separator(GEFActionConstants.GROUP_FIND));
		menu.add(new Separator(IWorkbenchActionConstants.GROUP_ADD));
		menu.add(new Separator(GEFActionConstants.GROUP_SAVE));
		menu.add(new Separator(GEFActionConstants.GROUP_REST));
		menu.add(new Separator(IWorkbenchActionConstants.GROUP_REORGANIZE));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
}
