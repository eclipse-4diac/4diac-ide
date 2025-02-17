/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *               2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais - initial API and implementation and/or initial documentation.
 *   			  Mostly copied from SupAppWizard.
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.ui.providers.DialogSettingsProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.Wizard;

public abstract class AbstractSaveAsWizard extends Wizard {
	private String subappSection;
	protected SaveAsWizardPage newFilePage;

	protected AbstractSaveAsWizard(final String subAppSection) {
		this.subappSection = subAppSection;
		setupDiagramSettings();
	}

	protected AbstractSaveAsWizard() {
		/* used for the struct type save as wizard */
	}

	protected boolean perform() {
		newFilePage.saveWidgetValues();

		final IFile targetFile = getTargetTypeFile();
		if (targetFile.exists()) {
			return askOverwrite();
		}
		return true;
	}

	private void setupDiagramSettings() {
		final IDialogSettings settings = DialogSettingsProvider.getDialogSettings(getClass());
		if (null == settings.getSection(subappSection)) {
			// section does not exist create a section
			settings.addNewSection(subappSection);
		}
		setDialogSettings(settings);
	}

	@Override
	public abstract boolean performFinish();

	protected abstract boolean askOverwrite();

	protected abstract IFile getTargetTypeFile();
}
