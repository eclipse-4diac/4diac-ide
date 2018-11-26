/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateFromNewAdapterAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateInputEventAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateInputVariableAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateNewPlugAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateNewSocketAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateOutputEventAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateOutputVariableAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreatePlugAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateSocketAction;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;


public class InterfaceContextMenuProvider extends
		ZoomUndoRedoContextMenuProvider {

	private static final String CREATE_PLUG = "Create Plug";
	private static final String CREATE_SOCKET = "Create Socket";
	
	public InterfaceContextMenuProvider(EditPartViewer viewer,
			ZoomManager zoomManager, ActionRegistry registry) {
		super(viewer, zoomManager, registry);
	}
	
	@Override
	public void buildContextMenu(IMenuManager menu) {
		super.buildContextMenu(menu);

		IAction action = registry.getAction(ActionFactory.DELETE
				.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
		
		buildInterfaceEditEntries(menu, registry);
	}
	
	public static void buildInterfaceEditEntries(IMenuManager menu, ActionRegistry registry) {
		IAction action;
		
		action = registry.getAction(CreateInputEventAction.ID);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);
		
		IWorkbenchPart part = ((CreateInputEventAction)action).getWorkbenchPart();
		FBType fbType = ((CreateInputEventAction)action).getFbType();
		
		action = registry.getAction(CreateOutputEventAction.ID);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);
		
		MenuManager submenu = new MenuManager("Create Data Input");
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);
		
		for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
			action = registry.getAction(CreateInputVariableAction.getID(dataType.getName()));
			if(null == action){
				action = new CreateInputVariableAction(part, fbType, dataType);
				registry.registerAction(action);
			}
			submenu.add(action);
		}
		
		submenu = new MenuManager("Create Data Output");
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);
		
		for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
			action = registry.getAction(CreateOutputVariableAction.getID(dataType.getName()));
			if(null == action){
				action = new CreateOutputVariableAction(part, fbType, dataType);
				registry.registerAction(action);
			}
			submenu.add(action);
		} 
		if(!(fbType instanceof AdapterFBType)) {
			buildAdapterMenuEntries(menu, registry, part, fbType);
		}
	}

	private static void buildAdapterMenuEntries(IMenuManager menu,
			ActionRegistry registry, IWorkbenchPart part, FBType fbType) {
		
		if (((EditorPart)part).getEditorInput() instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) ((EditorPart)part).getEditorInput();
			Palette palette = untypedInput.getPaletteEntry().getGroup().getPallete();
			if(null != palette){			
				MenuManager socketEntry = new MenuManager(CREATE_SOCKET);		
				menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, socketEntry);
				
				MenuManager plugEntry = new MenuManager(CREATE_PLUG);		
				menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, plugEntry);
				
				Action action = (Action) registry.getAction(CreateNewPlugAction.ID);
				if(null != action){
					((CreateFromNewAdapterAction)action).setPaletteEntry(untypedInput.getPaletteEntry());
				}
				plugEntry.add(action);
				
				action = (Action) registry.getAction(CreateNewSocketAction.ID);
				if(null != action){
					((CreateFromNewAdapterAction)action).setPaletteEntry(untypedInput.getPaletteEntry());
				}
				socketEntry.add(action);
				
				fillMenuForPalletteGroup(socketEntry, plugEntry, registry, part, fbType, palette.getRootGroup().getSubGroups());
			}
		}		
	}

	private static void fillMenuForPalletteGroup(MenuManager socketEntry,
			MenuManager plugEntry, ActionRegistry registry,
			IWorkbenchPart part, FBType fbType, EList<PaletteGroup> subGroups) {
		MenuManager socketSubmenu;
		MenuManager plugSubmenu;
		Action action;
		
		//TODO sort groups alphabetically
		
		for (PaletteGroup group : subGroups) {
			socketSubmenu = new MenuManager(group.getLabel());
			plugSubmenu = new MenuManager(group.getLabel());
			
			fillMenuForPalletteGroup(socketSubmenu, plugSubmenu, registry, part, fbType, group.getSubGroups());

			for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry : group.getEntries()) {
				if(entry instanceof AdapterTypePaletteEntry){
					//add socket entry
					action = (Action) registry.getAction(CreateSocketAction.getID((AdapterTypePaletteEntry)entry));
					if(null == action){
						action = new CreateSocketAction(part, fbType, (AdapterTypePaletteEntry)entry);
					}
					socketSubmenu.add(action);
					
					//added plug entry
					action = (Action) registry.getAction(CreatePlugAction.getID((AdapterTypePaletteEntry)entry));
					if(null == action){
						action = new CreatePlugAction(part, fbType, (AdapterTypePaletteEntry)entry);
					}
					plugSubmenu.add(action);
				}
			}
			
			if(!socketSubmenu.isEmpty()){
				socketEntry.add(socketSubmenu);
				plugEntry.add(plugSubmenu);
			}			
		}		
		
	}

	public static void createInterfaceEditingActions(IWorkbenchPart workBenchPart, ActionRegistry registry, FBType fbType){
		IAction action;

		action = new CreateInputEventAction(workBenchPart, fbType);
		registry.registerAction(action);
		
		action = new CreateOutputEventAction(workBenchPart, fbType);
		registry.registerAction(action);
		
		if(!(fbType instanceof AdapterFBType)) {
			action = new CreateNewPlugAction(workBenchPart, fbType);
			registry.registerAction(action);
			
			action = new CreateNewSocketAction(workBenchPart, fbType);
			registry.registerAction(action);
		}
	}

}
