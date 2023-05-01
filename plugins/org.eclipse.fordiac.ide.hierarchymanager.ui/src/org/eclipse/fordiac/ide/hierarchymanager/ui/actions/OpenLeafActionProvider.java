/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.actions;

import org.eclipse.fordiac.ide.hierarchymanager.view.PlantHierarchyView;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class OpenLeafActionProvider extends CommonActionProvider {

	private OpenLeafAction openAction;
	private boolean contribute = false;

	@Override
	public void init(final ICommonActionExtensionSite aConfig) {
		if ((aConfig.getViewSite() instanceof final ICommonViewerWorkbenchSite commonViewSite)
				&& (commonViewSite.getPart() instanceof final PlantHierarchyView phView)) {
			openAction = new OpenLeafAction(phView);
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

}
