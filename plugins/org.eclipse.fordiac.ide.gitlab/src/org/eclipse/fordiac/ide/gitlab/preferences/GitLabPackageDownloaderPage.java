/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class GitLabPackageDownloaderPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public GitLabPackageDownloaderPage() {
		super(GRID);
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Messages.GitLab_PreferenceId));
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		final StringFieldEditor gitLabUrl = new StringFieldEditor(Messages.GitLab_Url, Messages.GitLab_Url_Title,
				getFieldEditorParent());
		final StringFieldEditor gitLabToken = new StringFieldEditor(Messages.GitLab_Token, Messages.GitLab_Token_Title,
				getFieldEditorParent()) {
			@Override
			protected void doFillIntoGrid(final Composite parent, final int numColumns) {
				super.doFillIntoGrid(parent, numColumns);

				getTextControl().setEchoChar('*');
			}
		};
		addField(gitLabUrl);
		addField(gitLabToken);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// No initialization for the Preference Page needed
	}

}