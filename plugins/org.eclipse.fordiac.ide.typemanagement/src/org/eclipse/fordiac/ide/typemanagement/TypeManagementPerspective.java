/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class TypeManagementPerspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "org.eclipse.fordiac.ide.typemanagement.perspective"; //$NON-NLS-1$
	public static final String TYPE_MANAGEMENT_LEFT_AREA_ID = "typemanagement.left"; //$NON-NLS-1$
	public static final String TYPE_MANAGEMENT_RIGHT_AREA_ID = "typemanagement.right"; //$NON-NLS-1$
	public static final String TYPE_MANAGEMENT_BOTTOM_AREA_ID = "typemanagement.bottom"; //$NON-NLS-1$


	@Override
	public void createInitialLayout(final IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);
		createLeftEntries(layout);
		createBottomEntries(layout);
		createRightEntries(layout);
		addPerspectiveShortcuts(layout);
	}

	private static void createLeftEntries(final IPageLayout layout) {
		final IFolderLayout left = layout.createFolder(TYPE_MANAGEMENT_LEFT_AREA_ID, IPageLayout.LEFT, 0.20f,
				layout.getEditorArea());
		left.addView("org.eclipse.fordiac.ide.typemanagement.navigator.view"); //$NON-NLS-1$
		left.addView(IPageLayout.ID_PROJECT_EXPLORER);
	}

	private static void createBottomEntries(final IPageLayout layout) {
		final IFolderLayout bottom = layout.createFolder(TYPE_MANAGEMENT_BOTTOM_AREA_ID, IPageLayout.BOTTOM, 0.82f,
				layout.getEditorArea());
		bottom.addView(IPageLayout.ID_PROP_SHEET);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
	}

	private static void createRightEntries(final IPageLayout layout) {
		final IFolderLayout right = layout.createFolder(TYPE_MANAGEMENT_RIGHT_AREA_ID, IPageLayout.RIGHT, 0.78f,
				layout.getEditorArea());
		right.addView(IPageLayout.ID_OUTLINE);
	}

	private static void addPerspectiveShortcuts(final IPageLayout layout) {
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);

		layout.addNewWizardShortcut("org.eclipse.fordiac.ide.systemmanagement.ui.wizard.New4diacProjectWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizard"); //$NON-NLS-1$
		layout.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective"); //$NON-NLS-1$
	}
}
