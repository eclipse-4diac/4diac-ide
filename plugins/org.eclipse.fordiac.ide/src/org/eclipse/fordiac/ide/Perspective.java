/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * The Class Perspective.
 */
public class Perspective implements IPerspectiveFactory {
	
	/** The Constant PERSPECTIVE_ID. */
	public static final String PERSPECTIVE_ID = "org.eclipse.fordiac.ide.SystemPerspective"; //$NON-NLS-1$

	private IPageLayout factory;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	@Override
	public void createInitialLayout(final IPageLayout layout) {
		this.factory = layout;

		layout.setEditorAreaVisible(true);
		layout.setFixed(false);

		IFolderLayout left = layout.createFolder(
				"left", IPageLayout.LEFT, 0.20f, layout.getEditorArea()); //$NON-NLS-1$
		left.addView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer");  //$NON-NLS-1$

		IFolderLayout bottomLeft = layout.createFolder(
				"bottomLeft", IPageLayout.BOTTOM, 0.7f, "left"); //$NON-NLS-1$	//$NON-NLS-2$
		bottomLeft.addView(IPageLayout.ID_OUTLINE);

		IFolderLayout bottom = layout.createFolder(
				"bottom", IPageLayout.BOTTOM, 0.78f, layout.getEditorArea()); //$NON-NLS-1$
		bottom.addView(IPageLayout.ID_PROP_SHEET);
		bottom.addView("org.eclipse.fordiac.ide.systemconfiguration.virtualDNS"); //$NON-NLS-1$
		
		left.addView("org.eclipse.fordiac.ide.typemanagement.navigator.view"); //$NON-NLS-1$

		addPerspectiveShortcuts();
		
	}

	private void addPerspectiveShortcuts() {
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		factory.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.NewSystemWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.NewApplicationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizard"); //$NON-NLS-1$
		factory.addPerspectiveShortcut(PERSPECTIVE_ID);
		factory.addPerspectiveShortcut("org.eclipse.fordiac.ide.deployment.ui.perspectives.DeploymentPerspective"); //$NON-NLS-1$
	}
}
