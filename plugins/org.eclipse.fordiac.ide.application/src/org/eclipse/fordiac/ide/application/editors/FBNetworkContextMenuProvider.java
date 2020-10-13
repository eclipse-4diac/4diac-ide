/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 *               2019 - 2020 Johannes Kepler University
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
 *   Bianca Wiesmayr - clean-up and reorganize
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.fordiac.ide.application.actions.FBNetworkElementInsertAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * This class builds the context menu for the FBNetwork Editor.
 */
public class FBNetworkContextMenuProvider extends FordiacContextMenuProvider {

	private Palette palette;
	private DiagramEditorWithFlyoutPalette editor;
	private Point invocationLocation;

	/**
	 * Instantiates a new FB network context menu provider.
	 *
	 * @param viewer      the viewer
	 * @param registry    the registry
	 * @param zoomManager the zoom manager
	 */
	public FBNetworkContextMenuProvider(final DiagramEditorWithFlyoutPalette editor, final ActionRegistry registry,
			final ZoomManager zoomManager, Palette palette) {
		super(editor.getViewer(), zoomManager, registry);
		this.palette = palette;
		this.editor = editor;

		editor.getViewer().getControl()
				.addMenuDetectListener(e -> invocationLocation = getViewer().getControl().toControl(e.x, e.y));

	}

	/**
	 * Retrieve the location of the context menu relative to the view canvas with
	 * scroll and zoom correction.
	 *
	 * @return the zoom and scroll corrected position where the pop-up menu was
	 *         invoked (i.e. where the right click happened)
	 */
	public org.eclipse.draw2d.geometry.Point getTranslatedAndZoomedPoint() {
		FigureCanvas viewerControl = (FigureCanvas) editor.getViewer().getControl();
		org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
		return new org.eclipse.draw2d.geometry.Point(invocationLocation.x + location.x,
				invocationLocation.y + location.y).scale(1.0 / getZoomManager().getZoom());
	}

	public Point getPoint() {
		return invocationLocation;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(final IMenuManager menu) {
		super.buildContextMenu(menu);
		IAction action;

		action = getRegistry().getAction(GEFActionConstants.DIRECT_EDIT);
		if ((action != null) && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		menu.appendToGroup(GEFActionConstants.GROUP_REST, new Separator());

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		action = getRegistry().getAction(ActionFactory.CUT.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.COPY.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.PASTE.getId());
		if (action instanceof PasteEditPartsAction) {
			((PasteEditPartsAction) action).setPastRefPosition(getTranslatedAndZoomedPoint());
		}
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
	}

	private boolean useChangeFBType;

	public void buildFBInsertMenu(final IMenuManager menu, Point point, boolean useChangeFBType) {
		invocationLocation = point;
		this.useChangeFBType = useChangeFBType;
		fillMenuForFolder(menu, palette.getProject());
	}

	private void fillMenuForFolder(IMenuManager submenu, IContainer container) {
		try {
			for (IResource res : container.members()) {
				if (res instanceof IFolder) {
					createSubMenu(submenu, (IFolder) res);
				} else if (res instanceof IFile) {
					createFBMenuEntry(submenu, (IFile) res);
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private void createSubMenu(IMenuManager parent, IFolder res) {
		MenuManager submenu = new MenuManager(res.getName());
		fillMenuForFolder(submenu, res);
		if (!submenu.isEmpty()) {
			submenu.setImageDescriptor(
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
			parent.add(submenu);
		}
	}

	private void createFBMenuEntry(IMenuManager submenu, IFile typeFile) {
		PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(typeFile);
		if ((entry instanceof FBTypePaletteEntry) || (entry instanceof SubApplicationTypePaletteEntry)) {
			Action action = getActionForPaletteEntry(entry);
			setActionIcon(action, entry);
			submenu.add(action);
		}
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
			image = FordiacImage.ICON_SUB_APP_TYPE.getImageDescriptor();
		} else if (entry.getType() instanceof BasicFBType) {
			image = FordiacImage.ICON_BASIC_FB.getImageDescriptor();
		} else if (entry.getType() instanceof SimpleFBType) {
			image = FordiacImage.ICON_SIMPLE_FB.getImageDescriptor();
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

}
