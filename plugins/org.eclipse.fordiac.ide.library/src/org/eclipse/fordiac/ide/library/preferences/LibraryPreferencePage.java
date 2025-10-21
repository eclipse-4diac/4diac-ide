/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.preferences;

import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.ui.preferences.FordiacPropertyPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

public class LibraryPreferencePage extends FordiacPropertyPreferencePage {

	public LibraryPreferencePage() {
		super(GRID, LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID);
	}

	@Override
	protected String getPreferencePageID() {
		return "org.eclipse.fordiac.ide.library.preferences.LibraryPreferences"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageID() {
		return "org.eclipse.fordiac.ide.library.properties.LibraryPreferences"; //$NON-NLS-1$
	}

	@Override
	protected void createFieldEditors() {
		final Group load = new Group(getFieldEditorParent(), SWT.NONE);
		load.setText(Messages.PreferenceLoadingGroup);

		addField(new BooleanFieldEditor(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES,
				Messages.PreferenceForceLoad, load));

		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		load.setLayout(gridLayout);
		load.setLayoutData(gridData);
	}

}
