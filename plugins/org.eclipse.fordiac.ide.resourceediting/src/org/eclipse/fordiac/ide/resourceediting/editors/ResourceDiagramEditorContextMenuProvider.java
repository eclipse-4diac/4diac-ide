/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class ResourceDiagramEditorContextMenuProvider.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class ResourceDiagramEditorContextMenuProvider extends
		ZoomUndoRedoContextMenuProvider {

	/**
	 * Instantiates a new resource diagram editor context menu provider.
	 * 
	 * @param viewer the viewer
	 * @param zoomManager the zoom manager
	 * @param registry the registry
	 */
	public ResourceDiagramEditorContextMenuProvider(
			final EditPartViewer viewer, final ZoomManager zoomManager,
			final ActionRegistry registry) {
		super(viewer, zoomManager, registry);
	}

	@Override
	public void buildContextMenu(final IMenuManager menu) {
		super.buildContextMenu(menu);

		IAction action;
		action = registry.getAction(UnmapAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}
		
		action = registry.getAction(UnmapAllAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}
		
		action = registry.getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
		
		action = registry.getAction(GEFActionConstants.DIRECT_EDIT);
		if (action != null && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}
	}
}
