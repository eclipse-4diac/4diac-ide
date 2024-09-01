/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 *                          Johannes Kepler University
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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.actions.Open4DIACElementAction;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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

/** This class builds the context menu for the FBNetwork Editor. */
public class FBNetworkContextMenuProvider extends FordiacContextMenuProvider {

	private final TypeLibrary typeLib;
	private final DiagramEditorWithFlyoutPalette editor;
	private Point invocationLocation;

	/**
	 * Instantiates a new FB network context menu provider.
	 *
	 * @param viewer      the viewer
	 * @param registry    the registry
	 * @param zoomManager the zoom manager
	 */
	public FBNetworkContextMenuProvider(final DiagramEditorWithFlyoutPalette editor, final ActionRegistry registry,
			final ZoomManager zoomManager, final TypeLibrary typeLib) {
		super(editor.getViewer(), zoomManager, registry);
		this.typeLib = typeLib;
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
		final FigureCanvas viewerControl = (FigureCanvas) editor.getViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
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

		action = getRegistry().getAction(Open4DIACElementAction.ID);
		if ((action != null) && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}
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
		if (action instanceof final PasteEditPartsAction pasteAction) {
			pasteAction.setPastRefPosition(getTranslatedAndZoomedPoint());
		}
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);

		action = getRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
	}

	private boolean useChangeFBType;

	public void buildFBInsertMenu(final IMenuManager menu, final Point point, final boolean useChangeFBType) {
		invocationLocation = point;
		this.useChangeFBType = useChangeFBType;
		fillMenuForFolder(menu, typeLib.getProject());
	}

	private void fillMenuForFolder(final IMenuManager submenu, final IContainer container) {
		try {
			for (final IResource res : container.members()) {
				if (res instanceof final IFolder folder) {
					createSubMenu(submenu, folder);
				} else if (res instanceof final IFile file) {
					createFBMenuEntry(submenu, file);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private void createSubMenu(final IMenuManager parent, final IFolder res) {
		final MenuManager submenu = new MenuManager(res.getName());
		fillMenuForFolder(submenu, res);
		if (!submenu.isEmpty()) {
			submenu.setImageDescriptor(
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
			parent.add(submenu);
		}
	}

	private void createFBMenuEntry(final IMenuManager submenu, final IFile typeFile) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile);
		if ((entry instanceof FBTypeEntry) || (entry instanceof SubAppTypeEntry)) {
			final Action action = getActionForTypeEntry(entry);
			setActionIcon(action, entry);
			submenu.add(action);
		}
	}

	private Action getActionForTypeEntry(final TypeEntry entry) {
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

	private static void setActionIcon(final Action action, final TypeEntry entry) {
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

	private UpdateFBTypeAction createChangeFBTypeAction(final TypeEntry entry) {
		final UpdateFBTypeAction action = new UpdateFBTypeAction(editor, entry);
		getRegistry().registerAction(action);
		editor.getSelActions().add(action.getId());
		action.update();
		return action;
	}

	private FBNetworkElementInsertAction createFBInsertAction(final TypeEntry entry) {
		final FBNetworkElementInsertAction action = new FBNetworkElementInsertAction(editor, entry,
				((FBNetworkEditor) editor).getModel());
		getRegistry().registerAction(action);
		return action;
	}

	/**
	 * Allows SWTBot test to set the invocationLocation.
	 *
	 * Should not be used by other code!
	 *
	 * @param invocationLocation invocation location to be used for the next
	 *                           invocation of the menu
	 */
	public void setInvocationLocation(final Point invocationLocation) {
		this.invocationLocation = invocationLocation;
	}
}
