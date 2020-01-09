/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Waldemar Eisenmenger,
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed issues in pop-up menu with sub-app types
 *               - show icons for folders, and FB
 *               - fixed issue in submenu generation when fb types are in the
 *                 root folder
 *               - reworked fb insert action to not reuse commands
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.actions.FBNetworkElementInsertAction;
import org.eclipse.fordiac.ide.application.actions.MapAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * This class builds the context menu for the FBNetwork Editor.
 */
public class UIFBNetworkContextMenuProvider extends ZoomUndoRedoContextMenuProvider {

	private Palette palette;
	private DiagramEditorWithFlyoutPalette editor;
	private Point pt;
	private ZoomManager zoomManager;

	/**
	 * Instantiates a new uIFB network context menu provider.
	 *
	 * @param viewer      the viewer
	 * @param registry    the registry
	 * @param zoomManager the zoom manager
	 */
	public UIFBNetworkContextMenuProvider(final DiagramEditorWithFlyoutPalette editor, final ActionRegistry registry,
			final ZoomManager zoomManager, Palette palette) {
		super(editor.getViewer(), zoomManager, registry);
		this.palette = palette;
		this.editor = editor;
		this.zoomManager = zoomManager;

		getViewer().getControl().addMenuDetectListener(e -> {
			pt = getViewer().getControl().toControl(e.x, e.y);
		});
	}

	public org.eclipse.draw2d.geometry.Point getPoint() {
		FigureCanvas viewerControl = (FigureCanvas) editor.getViewer().getControl();
		org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
		return new org.eclipse.draw2d.geometry.Point(pt.x + location.x, pt.y + location.y)
				.scale(1.0 / zoomManager.getZoom());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(final IMenuManager menu) {
		if (getViewer().getEditDomain().getActiveTool() instanceof AdvancedPanningSelectionTool) {
			AdvancedPanningSelectionTool st = (AdvancedPanningSelectionTool) getViewer().getEditDomain()
					.getActiveTool();
			if (!st.isMoved()) { // pan executed
				EditPart currentPart = getViewer().findObjectAt(st.getLocation());
				EditPart selected = st.getTargetEditPart();
				if ((selected != null) && (selected.getSelected() == EditPart.SELECTED_NONE)
						&& (selected instanceof AbstractViewEditPart)) {
					getViewer().select(currentPart);
				}

				if (selected == null) {
					getViewer().select(currentPart);
				}

			}
		}

		super.buildContextMenu(menu);
		IAction action;

		action = getRegistry().getAction(ActionFactory.SELECT_ALL.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = getRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.COPY.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.CUT.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.PASTE.getId());
		if (action instanceof PasteEditPartsAction) {
			((PasteEditPartsAction) action).setPastRefPosition(getPoint());
		}
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(GEFActionConstants.DIRECT_EDIT);
		if ((action != null) && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		createMappingMenuEntries(menu);

		menu.appendToGroup(GEFActionConstants.GROUP_REST, new Separator());

		action = getRegistry().getAction(UpdateFBTypeAction.ID);
		if ((action != null) && action.isEnabled()) {
			menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);
		}

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		createFBMenus(menu);

	}

	@SuppressWarnings("rawtypes")
	private void createFBMenus(final IMenuManager menu) {
		useChangeFBType = false;
		String text = Messages.UIFBNetworkContextMenuProvider_InsertFB;
		List eps = editor.getViewer().getSelectedEditParts();
		for (Object ep : eps) {
			if ((ep instanceof FBEditPart) || (ep instanceof SubAppForFBNetworkEditPart)) {
				text = Messages.UIFBNetworkContextMenuProvider_ChangeType;
				useChangeFBType = true;
				break;
			}
		}
		MenuManager submenu = new MenuManager(text);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);
		fillMenuForPaletteGroup(submenu, palette.getRootGroup().getSubGroups());
		addFBMenuEntries(palette.getRootGroup(), submenu);
	}

	public void buildFBInsertMenu(final IMenuManager menu, Point point) {
		pt = point;
		useChangeFBType = false;
		fillMenuForPaletteGroup(menu, palette.getRootGroup().getSubGroups());
		addFBMenuEntries(palette.getRootGroup(), menu);
	}

	private boolean useChangeFBType;

	private void fillMenuForPaletteGroup(IMenuManager insertTypeEntry, EList<PaletteGroup> subGroups) {
		// TODO sort groups alphabetically

		for (PaletteGroup group : subGroups) {
			MenuManager submenu = createSubMenu(group);
			addFBMenuEntries(group, submenu);
			if (!submenu.isEmpty()) {
				insertTypeEntry.add(submenu);
			}
		}
	}

	private void addFBMenuEntries(PaletteGroup group, IMenuManager submenu) {
		for (PaletteEntry entry : group.getEntries()) {

			if ((entry instanceof FBTypePaletteEntry) || (entry instanceof SubApplicationTypePaletteEntry)) {
				Action action = getActionForPaletteEntry(entry);
				if (null != action) {
					setActionIcon(action, entry);
					submenu.add(action);
				}
			}
		}
	}

	private MenuManager createSubMenu(PaletteGroup group) {
		MenuManager submenu = new MenuManager(group.getLabel());
		fillMenuForPaletteGroup(submenu, group.getSubGroups());
		submenu.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
		return submenu;
	}

	private Action getActionForPaletteEntry(PaletteEntry entry) {
		Action action;
		if (useChangeFBType) {
			action = (Action) getRegistry().getAction(entry.getFile().getFullPath().toString().concat("_") //$NON-NLS-1$
					.concat(UpdateFBTypeAction.ID));
		} else {
			action = (Action) getRegistry().getAction(entry.getFile().getFullPath().toString());
		}
		if (null == action) {
			if (useChangeFBType) {
				action = createChangeFBTypeAction(entry);
			} else {
				action = createFBInsertAction(entry);
			}
		}
		return action;
	}

	private static void setActionIcon(Action action, PaletteEntry entry) {
		ImageDescriptor image = null;
		if (entry.getType() instanceof SubAppType) {
			image = FordiacImage.ICON_SUB_APP.getImageDescriptor();
		} else if (entry.getType() instanceof BasicFBType) {
			image = FordiacImage.ICON_BASIC_FB.getImageDescriptor();
		} else if (entry.getType() instanceof CompositeFBType) {
			image = FordiacImage.ICON_COMPOSITE_FB.getImageDescriptor();
		} else {
			image = FordiacImage.ICON_SIFB.getImageDescriptor();
		}
		action.setImageDescriptor(image);
	}

	@SuppressWarnings("unchecked")
	private UpdateFBTypeAction createChangeFBTypeAction(PaletteEntry entry) {
		UpdateFBTypeAction action = new UpdateFBTypeAction(editor, entry);
		getRegistry().registerAction(action);
		editor.getSelActions().add(action.getId());
		action.update();
		return action;
	}

	private FBNetworkElementInsertAction createFBInsertAction(PaletteEntry entry) {
		FBNetworkElementInsertAction action = new FBNetworkElementInsertAction(editor, entry,
				((FBNetworkEditor) editor).getModel());
		getRegistry().registerAction(action);
		return action;
	}

	protected void createMappingMenuEntries(final IMenuManager menu) {
		IAction action;
		menu.appendToGroup(GEFActionConstants.GROUP_REST, new Separator());

		IMenuManager mappingMenuEntry = createHWMappingMenu();
		if (null != mappingMenuEntry) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, mappingMenuEntry);
		}

		action = getRegistry().getAction(UnmapAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}

		action = getRegistry().getAction(UnmapAllAction.ID);
		if (action != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_REST, action);
		}
	}

	protected IMenuManager createHWMappingMenu() {
		MenuManager submenu = new MenuManager(Messages.UIFBNetworkContextMenuProvider_LABEL_HardwareMapping);
		GEFActionConstants.addStandardActionGroups(submenu);

		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();

		if (isFBorSubAppSelected(selection) && (activeEditor instanceof FBNetworkEditor)) {
			FBNetworkEditor fbEditor = (FBNetworkEditor) activeEditor;
			List<Device> devices = fbEditor.getSystem().getSystemConfiguration().getDevices();

			for (Device device : devices) {
				MenuManager devMenu = createDeviceMenuEntry(device);

				for (Resource res : device.getResource()) {
					IAction action = createResourceMappingEntry(fbEditor, res);
					if (null != action) {
						devMenu.appendToGroup(GEFActionConstants.GROUP_REST, action);
					}
				}
				submenu.appendToGroup(GEFActionConstants.GROUP_REST, devMenu);
			}
		}
		return submenu;
	}

	private static MenuManager createDeviceMenuEntry(Device device) {
		MenuManager devMenu = new MenuManager(device.getName() == null ? "Device" : device.getName()); //$NON-NLS-1$
		devMenu.setImageDescriptor(FordiacImage.ICON_DEVICE.getImageDescriptor());
		GEFActionConstants.addStandardActionGroups(devMenu);
		return devMenu;
	}

	@SuppressWarnings("unchecked")
	private IAction createResourceMappingEntry(FBNetworkEditor fbEditor, Resource res) {
		if (!res.isDeviceTypeResource()) {
			IAction action = getMapAction(fbEditor, res);
			if (action != null) {
				fbEditor.getSelActions().add(action.getId());
				if (action.isEnabled()) {
					action.setChecked(checkIsCurrentlyMappedTo(res));
					return action;
				}
			}
		}
		return null;
	}

	private static boolean isFBorSubAppSelected(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toArray()) {
				if ((element instanceof FBEditPart) || (element instanceof SubAppForFBNetworkEditPart)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("static-method") // currently needed to be overrideable by SubAppNetworkEditor
	protected IAction getMapAction(IEditorPart activeEditor, Resource res) {
		if (res != null) {
			IAction action;
			action = new MapAction(activeEditor, res);
			action.setImageDescriptor(FordiacImage.ICON_RESOURCE.getImageDescriptor());
			return action;
		}
		return null;
	}

	/**
	 * Check is currently mapped to.
	 *
	 * @param res the res
	 *
	 * @return true, if successful
	 */
	private boolean checkIsCurrentlyMappedTo(final Resource res) {
		// multiple fbs/subapps selected -> selection is not uniquely mapped to a single
		// resource
		if (getViewer().getSelectedEditParts().size() != 1) {
			return false;
		} else {
			if (getViewer().getSelectedEditParts().get(0) instanceof FBEditPart) {
				FBEditPart fbep = (FBEditPart) getViewer().getSelectedEditParts().get(0);
				if (fbep.getModel().isMapped()) {
					return res == fbep.getModel().getResource();
				}
			}
		}
		return false;
	}

}
