/*******************************************************************************
 * Copyright (c) 2013 - 2015 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network;

import org.eclipse.fordiac.ide.application.editors.UIFBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;

public class CFBNetworkcontextMenuProvider extends
		UIFBNetworkContextMenuProvider {

	public CFBNetworkcontextMenuProvider(DiagramEditorWithFlyoutPalette editor,
			ActionRegistry registry, ZoomManager zoomManager, Palette palette) {
		super(editor, registry, zoomManager, palette);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {		
		super.buildContextMenu(menu);		
		InterfaceContextMenuProvider.buildInterfaceEditEntries(menu, getRegistry());
	}
	
	

}
