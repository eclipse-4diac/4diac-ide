/*******************************************************************************
 * Copyright (c) 2008, 2009, 2010, 2015 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**
 * The Class DeploymentPerspective.
 */
public class DeviceSimulatorPerspective implements IPerspectiveFactory {

	/** The factory. */
	private IPageLayout factory;

	/**
	 * Instantiates a new deployment perspective.
	 */
	public DeviceSimulatorPerspective() {
		// empty constructor
	}

	@Override
	public void createInitialLayout(final IPageLayout factory) {
		this.factory = factory;
		factory.setEditorAreaVisible(false);
		addViews();
		addActionSets();
		addNewWizardShortcuts();
		addPerspectiveShortcuts();
		addViewShortcuts();
	}

	/**
	 * Adds the views.
	 */
	private void addViews() {
		IFolderLayout top = factory.createFolder("top", //$NON-NLS-1$
				IPageLayout.TOP, 1f, factory.getEditorArea());
		top.addView("org.eclipse.fordiac.ide.runtime.views.RuntimeLauncherView"); //$NON-NLS-1$

		IFolderLayout bottomLeft = factory.createFolder("bottomLeft", //$NON-NLS-1$
				IPageLayout.BOTTOM, 0.50f, "top"); //$NON-NLS-1$
		bottomLeft.addView("org.eclipse.fordiac.ide.runtime.views.RuntimesView"); //$NON-NLS-1$

		IFolderLayout bottomRight = factory.createFolder("bottomRight", //$NON-NLS-1$
				IPageLayout.RIGHT, 0.35f, "bottomLeft"); //$NON-NLS-1$
		bottomRight.addView(IConsoleConstants.ID_CONSOLE_VIEW);
	}

	/**
	 * Adds the action sets.
	 */
	private void addActionSets() {
		// currently not used
	}

	/**
	 * Adds the perspective shortcuts.
	 */
	private void addPerspectiveShortcuts() {
	}

	/**
	 * Adds the new wizard shortcuts.
	 */
	private void addNewWizardShortcuts() {
	}

	/**
	 * Adds the view shortcuts.
	 */
	private void addViewShortcuts() {
		// currently not used
	}

}
