/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class Open4DIACElementActionProvider extends CommonActionProvider {

	private Open4DIACElementAction openAction;

	private ICommonViewerWorkbenchSite viewSite = null;

	private boolean contribute = false;

	@Override
	public void init(final ICommonActionExtensionSite aConfig) {
		if (aConfig.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			viewSite = (ICommonViewerWorkbenchSite) aConfig.getViewSite();
			openAction = new Open4DIACElementAction(viewSite.getPart());
			contribute = true;
		}
	}

	@Override
	public void fillContextMenu(final IMenuManager aMenu) {
		if (!contribute || getContext().getSelection().isEmpty()) {
			return;
		}

		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		openAction.selectionChanged(selection);
		if (openAction.isEnabled()) {
			aMenu.insertAfter(ICommonMenuConstants.GROUP_OPEN, openAction);
		}

		addOpenWithMenu(aMenu);
	}

	@Override
	public void fillActionBars(final IActionBars theActionBars) {
		if (!contribute) {
			return;
		}
		final IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if (selection.size() == 1) {
			openAction.selectionChanged(selection);
			theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
		}

	}

	private void addOpenWithMenu(final IMenuManager aMenu) {
		final IStructuredSelection ss = (IStructuredSelection) getContext().getSelection();

		if (ss == null || ss.size() != 1) {
			return;
		}

		final Object obj = ss.getFirstElement();
		final List<IOpenListener> listeners = OpenListenerManager.INSTANCE.getOpenListener((EObject) obj);

		if (!listeners.isEmpty()) {
			// Create a menu flyout.
			final IMenuManager submenu = new MenuManager(Messages.OpenEditorProvider_OpenWithMenu_label,
					ICommonMenuConstants.GROUP_OPEN_WITH);
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_TOP));

			for (final IOpenListener openListener : listeners) {
				submenu.add(openListener.getOpenListenerAction());
			}
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_ADDITIONS));

			// Add the submenu.
			if (submenu.getItems().length > 2 && submenu.isEnabled()) {
				aMenu.appendToGroup(ICommonMenuConstants.GROUP_OPEN_WITH, submenu);
			}
		}
	}
}
