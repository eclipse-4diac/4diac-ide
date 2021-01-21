/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Muddasir Shakil - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - updated for breadcrumb editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.actions.MapAction;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class MapToContributionItem extends ContributionItem {

	private final IMenuListener mapToListener = this::createDeviceMenu;

	public MapToContributionItem() {
		super();
	}

	public MapToContributionItem(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		if (getParent() instanceof MenuManager) {
			((IMenuManager) getParent()).addMenuListener(mapToListener);
			((IMenuManager) getParent()).setRemoveAllWhenShown(true);
		}
		createDeviceMenu(((IMenuManager) getParent()));

	}


	@Override
	public boolean isEnabled() {
		final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		final FBNetwork fbnetwork = activeEditor.getAdapter(FBNetwork.class);
		return ((fbnetwork != null) && !(fbnetwork.isSubApplicationNetwork() || fbnetwork.isCFBTypeNetwork()));
	}

	private void createDeviceMenu(IMenuManager maptoMenu) {
		final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		final FBNetwork fbnetwork = activeEditor.getAdapter(FBNetwork.class);

		if (isFBorSubAppSelected(selection) && (null != fbnetwork)) {
			maptoMenu.removeAll();
			final List<Device> devices = fbnetwork.getAutomationSystem().getSystemConfiguration().getDevices();
			if (!devices.isEmpty()) {
				for (final Device device : devices) {
					createDeviceMenuEntry(maptoMenu, device);
				}
			}else {
				createEmptyMenuEntry(maptoMenu, Messages.MapToContributionItem_No_Device);
			}
		}else {
			createEmptyMenuEntry(maptoMenu, Messages.MapToContributionItem_No_FB_Or_SubApp_Selected);
		}
	}

	private boolean isFBorSubAppSelected(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			for (final Object element : ((IStructuredSelection) selection).toArray()) {
				if ((element instanceof AbstractFBNElementEditPart)
						|| (element instanceof SubAppForFBNetworkEditPart)) {
					return true;
				}
			}
		}
		return false;
	}

	private void createDeviceMenuEntry(IMenuManager maptoMenu, Device device) {
		final MenuManager deviceMenu = new MenuManager();
		deviceMenu.setMenuText(device.getName() == null ? FordiacMessages.Device : device.getName());
		deviceMenu.setImageDescriptor(FordiacImage.ICON_DEVICE.getImageDescriptor());
		maptoMenu.add(deviceMenu);
		deviceMenu.fill(((MenuManager) maptoMenu).getMenu(), -1);
		deviceMenu.setRemoveAllWhenShown(true);
		createResourceMenu(deviceMenu, device);
	}

	private void createResourceMenu(MenuManager parentMenuManager, Device device) {
		final IMenuListener listener = new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				final List<Resource> resources = device.getResource();
				final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				for (final Resource resource : resources) {
					final IAction action = new MapAction(activeEditor, resource);
					action.setText(resource.getName() == null ? FordiacMessages.Resource : resource.getName());
					action.setImageDescriptor(FordiacImage.ICON_RESOURCE.getImageDescriptor());
					parentMenuManager.add(action);
				}
			}
		};
		parentMenuManager.addMenuListener(listener);
	}

	private void createEmptyMenuEntry(IMenuManager maptoMenu, String message) {
		final ContributionItem emptyMenu = new ContributionItem() {
			@Override
			public void fill(Menu menu, int index) {
				final MenuItem item = (index == -1) ? new MenuItem(menu, SWT.None) : new MenuItem(menu, SWT.None, index);
				item.setText(message);
				item.setEnabled(false);
			}
		};
		maptoMenu.add(emptyMenu);
		emptyMenu.fill(((MenuManager) maptoMenu).getMenu(),-1);
	}
}
