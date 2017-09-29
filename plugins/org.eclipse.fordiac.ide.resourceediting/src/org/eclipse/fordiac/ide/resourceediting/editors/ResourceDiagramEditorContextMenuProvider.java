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

import org.eclipse.fordiac.ide.application.editors.UIFBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;

/**
 * The Class ResourceDiagramEditorContextMenuProvider.
 */
public class ResourceDiagramEditorContextMenuProvider extends UIFBNetworkContextMenuProvider {

	public ResourceDiagramEditorContextMenuProvider(final ResourceDiagramEditor editor, 
			final ActionRegistry registry, final ZoomManager zoomManager, Palette palette) {
		super(editor, registry, zoomManager, palette);
		//Nothing that we need to do here
	}

	@Override
	protected IMenuManager createHWMappingMenu() {
		//for now we don't want to map anything from the resource. In the future we may want to allow
		//mapping FBs from resources to applications then some code should be added here.
		return null;
	}
}
