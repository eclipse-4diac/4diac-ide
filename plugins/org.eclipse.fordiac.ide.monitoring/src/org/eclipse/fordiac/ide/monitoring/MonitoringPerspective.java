/*******************************************************************************
 * Copyright (c) 2012, 2015 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MonitoringPerspective implements IPerspectiveFactory {

	private IPageLayout factory;

	@Override
	public void createInitialLayout(final IPageLayout layout) {
		this.factory = layout;

		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.20f, layout.getEditorArea()); //$NON-NLS-1$
		left.addView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$

		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.7f, "left"); //$NON-NLS-1$ //$NON-NLS-2$
		bottomLeft.addView(IPageLayout.ID_OUTLINE);

		IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, //$NON-NLS-1$
				0.5f, "top"); //$NON-NLS-1$

		topRight.addView("org.eclipse.fordiac.ide.monitoring.views.WatchesView"); //$NON-NLS-1$

		addPerspectiveShortcuts();
	}

	private void addPerspectiveShortcuts() {
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		factory.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);

		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.New4diacProjectWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.NewSystemWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.NewApplicationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.fbtypeeditor.wizard.NewFBTypeWizard"); //$NON-NLS-1$
	}

}
