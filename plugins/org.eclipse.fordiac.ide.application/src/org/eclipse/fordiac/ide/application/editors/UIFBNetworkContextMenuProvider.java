/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Waldemar Eisenmenger,
 *   Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.actions.FBInsertAction;
import org.eclipse.fordiac.ide.application.actions.MapAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.SaveAsSubApplicationTypeAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.utilities.FBTypeTemplateCreationFactory;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.util.AdvancedPanningSelectionTool;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * This class builds the context menu for the FBNetwork Editor.
 */
public class UIFBNetworkContextMenuProvider extends ZoomUndoRedoContextMenuProvider {

	Palette palette;
	DiagramEditorWithFlyoutPalette editor;
	Point pt;

	/**
	 * Instantiates a new uIFB network context menu provider.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param registry
	 *            the registry
	 * @param zoomManager
	 *            the zoom manager
	 */
	public UIFBNetworkContextMenuProvider(final DiagramEditorWithFlyoutPalette editor,
			final ActionRegistry registry, final ZoomManager zoomManager, Palette palette) {
		super(editor.getViewer(), zoomManager, registry);
		this.palette = palette;
		this.editor = editor;

		getViewer().getControl().addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				pt = getViewer().getControl().toControl(e.x, e.y);			  
			}
		});
	}

	public Point getPoint() {
		return pt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(final IMenuManager menu) {

		if (getViewer().getEditDomain().getActiveTool() instanceof AdvancedPanningSelectionTool) {
			AdvancedPanningSelectionTool st = (AdvancedPanningSelectionTool) getViewer()
					.getEditDomain().getActiveTool();
			if (!st.isMoved()) { // pan executed
				EditPart currentPart = getViewer().findObjectAt(
						st.getLocation());
				EditPart selected = st.getTargetEditPart();
				if (selected != null
						&& selected.getSelected() == EditPart.SELECTED_NONE
						&& selected instanceof AbstractViewEditPart) {
					getViewer().select(currentPart);
				}

				if (selected == null) {
					getViewer().select(currentPart);
				}

			}
		}

		super.buildContextMenu(menu);
		IAction action;

		action = registry.getAction(ActionFactory.SELECT_ALL.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = registry.getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = registry.getAction(ActionFactory.COPY.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = registry.getAction(ActionFactory.PASTE.getId());
		if(action instanceof PasteEditPartsAction){
			((PasteEditPartsAction)action).setPastRefPosition(pt);
		}
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = registry.getAction(GEFActionConstants.DIRECT_EDIT);
		if (action != null && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		action = registry.getAction(SaveAsSubApplicationTypeAction.ID);
		if ((action != null) && (action.isEnabled())) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}

		
		createMappingMenuEntries(menu);

		menu.appendToGroup(GEFActionConstants.GROUP_REST, new Separator()); 
				
		action = registry.getAction(UpdateFBTypeAction.ID);
		if (action != null && action.isEnabled()) {
			menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);
		}

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		createFBMenus(menu);		

	}



	@SuppressWarnings("rawtypes")
	private void createFBMenus(final IMenuManager menu){	
		useChangeFBType = false;
		String text = "Insert FB";
		List eps = editor.getViewer().getSelectedEditParts();			
		for (Object ep : eps) {
			if (ep instanceof FBEditPart) {
				text = "Change FB Type";
				useChangeFBType = true;
				break;
			}
		}
		MenuManager submenu = new MenuManager(text);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);
		fillMenuForPalletteGroup(submenu, palette.getRootGroup().getSubGroups());
	}

	private boolean useChangeFBType = false;

	private void fillMenuForPalletteGroup(MenuManager insertTypeEntry, EList<PaletteGroup> subGroups) {

		MenuManager submenu;
		Action action;

		//TODO sort groups alphabetically

		for (PaletteGroup group : subGroups) {
			submenu = new MenuManager(group.getLabel());
			fillMenuForPalletteGroup(submenu, group.getSubGroups());

			for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry : group.getEntries()) {

				if(entry instanceof FBTypePaletteEntry){
					if (useChangeFBType) {
						action = (Action) registry.getAction(entry.getFile()
								.getFullPath().toString().concat("_") //$NON-NLS-1$
								.concat(UpdateFBTypeAction.ID));
					} else {
						action = (Action) registry.getAction(entry.getFile().getFullPath().toString());
					}
					if(null == action){
						if (useChangeFBType) {
							action = createChangeFBTypeAction(entry);
						} else {
							action = createFBInsertAction(entry);
							((FBInsertAction) action).updateCreatePosition(pt);
						}
					}
					submenu.add(action);
				}
			}
			if(!submenu.isEmpty()){
				insertTypeEntry.add(submenu);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private UpdateFBTypeAction createChangeFBTypeAction(PaletteEntry entry) {
		UpdateFBTypeAction action = new UpdateFBTypeAction(editor, entry);
		registry.registerAction(action);
		editor.getSelActions().add(action.getId());
		action.update();
		return action;
	}

	private FBInsertAction createFBInsertAction(PaletteEntry entry) {
		FBInsertAction action = null;
		CreateRequest request = new CreateRequest();
		request.setFactory(new FBTypeTemplateCreationFactory(entry));

		org.eclipse.swt.graphics.Point location = Display.getCurrent().getCursorLocation();
		location = getViewer().getControl().toControl(location.x, location.y);
		request.setLocation(new org.eclipse.draw2d.geometry.Point(location));

		Command cmd = getViewer().getContents().getCommand(request);

		if(cmd instanceof FBCreateCommand){
			action = new FBInsertAction(editor, (FBCreateCommand)cmd);
			registry.registerAction(action);
		}

		return action;
	}

	protected void createMappingMenuEntries(final IMenuManager menu) {
		IAction action;
		menu.appendToGroup(GEFActionConstants.GROUP_REST, new Separator());
		
		IMenuManager mappingMenuEntry = createHWMappingMenu();
		if(null != mappingMenuEntry) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, mappingMenuEntry);
		}
		
		action = registry.getAction(UnmapAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}
		
		action = registry.getAction(UnmapAllAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}
	}

	@SuppressWarnings("unchecked")
	protected IMenuManager createHWMappingMenu() {
		MenuManager submenu = new MenuManager(Messages.UIFBNetworkContextMenuProvider_LABEL_HardwareMapping);
		GEFActionConstants.addStandardActionGroups(submenu);

		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();		

		if(isFBorSubAppSelected(selection) && activeEditor instanceof FBNetworkEditor){		
			FBNetworkEditor editor = (FBNetworkEditor) activeEditor;
			List<Device> devices = editor.getSystem().getSystemConfiguration().getDevices();

			for (Device device : devices){
				MenuManager devmenu = new MenuManager(device.getName() == null ? "Device" : device.getName()); //$NON-NLS-1$
				GEFActionConstants.addStandardActionGroups(devmenu);

				List<Resource> resources = device.getResource();
				for (Resource res : resources) {
					if (!res.isDeviceTypeResource()) {
						IAction action = getMapAction(activeEditor, res);
						if (action != null) {
							editor.getSelActions().add(action.getId());
							if (action.isEnabled()) {
								action.setChecked(checkIsCurrentlyMappedTo(res));
								devmenu.appendToGroup(GEFActionConstants.GROUP_REST, action);
							}
						}
					}
				}
				submenu.appendToGroup(GEFActionConstants.GROUP_REST, devmenu);
			}
		}
		return submenu;
	}

	private boolean isFBorSubAppSelected(ISelection selection) {
		if(selection instanceof StructuredSelection){
			for(Object element : ((IStructuredSelection)selection).toArray()){
				if (element instanceof FBEditPart || element instanceof SubAppForFBNetworkEditPart){
					return true;
				}				
			}
		}
		return false;
	}

	protected IAction getMapAction(IEditorPart activeEditor, Resource res) {
		if (res != null) {
			IAction action;
			action = new MapAction(activeEditor,res);
			return action;
		} 
		return null;  
	}

	/**
	 * Check is currently mapped to.
	 * 
	 * @param res
	 *            the res
	 * 
	 * @return true, if successful
	 */
	private boolean checkIsCurrentlyMappedTo(final Resource res) {
		// multiple fbs/subapps selected -> selection is not uniquely mapped to a single resource
		if (getViewer().getSelectedEditParts().size() != 1) {
			return false;
		} else {
			if (getViewer().getSelectedEditParts().get(0) instanceof FBEditPart) {
				FBEditPart fbep = (FBEditPart) getViewer().getSelectedEditParts().get(0);
				if(fbep.getModel().isMapped()){
					return res == fbep.getModel().getResource(); 
				}
			}
		}
		return false;
	}

}
