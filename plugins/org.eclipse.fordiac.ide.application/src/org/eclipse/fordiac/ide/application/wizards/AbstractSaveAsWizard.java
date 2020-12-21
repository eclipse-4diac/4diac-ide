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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.Wizard;

public abstract class AbstractSaveAsWizard extends Wizard {
	private final String subappSection;
	SaveAsWizardPage newFilePage;

	public AbstractSaveAsWizard(String subAppSection) {
		this.subappSection = subAppSection;
		setupDiagramSettings();
	}

	public IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath()
				+ File.separator + newFilePage.getFileName() + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT));
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
		final IDialogSettings settings = Activator.getDefault().getDialogSettings();

		if (null == settings.getSection(subappSection)) {
			// section does not exist create a section
			settings.addNewSection(subappSection);
		}
		setDialogSettings(settings);
	}

	@Override
	public abstract boolean performFinish();

	protected abstract boolean askOverwrite();
}
