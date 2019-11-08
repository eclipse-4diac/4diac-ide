/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017, 2019 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Jose Cabral - Set version and description using general function
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *   			 - fixed double connection creation issue
 *               - extracted fbnetwork copying code into helper class for re-use
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class SaveAsSubappWizard extends Wizard {

	private final SubApp subApp;

	private SaveAsSubappWizardPage newFilePage;

	public SaveAsSubappWizard(SubApp subApp) {
		setWindowTitle(Messages.SaveAsSubApplicationTypeAction_WizardTitle);
		this.subApp = subApp;
	}

	@Override
	public void addPages() {
		IProject project = subApp.getSubAppNetwork().getAutomationSystem().getProject();
		StructuredSelection selection = new StructuredSelection(project); // select the current project
		newFilePage = new SaveAsSubappWizardPage(Messages.SaveAsSubApplicationTypeAction_WizardPageName, selection);
		newFilePage.setFileName(subApp.getName());
		addPage(newFilePage);
	}

	@Override
	public boolean performFinish() {
		boolean perform = true;

		IFile targetFile = getTargetTypeFile();
		if (targetFile.exists()) {
			perform = askOverwrite();
		}

		if (perform) {
			if (createSubAppTemplateCopy()) { // copy the subapp template so that we don't need to write code for any
												// basic type information stuff (e.g., version, coments etc.)
				PaletteEntry entry = getPalletEntry();
				LibraryElement type = entry.getType();
				type.setName(TypeLibrary.getTypeNameFromFile(entry.getFile()));

				TypeManagementPreferencesHelper.setupIdentification(type);
				TypeManagementPreferencesHelper.setupVersionInfo(type);
				performTypeSetup((SubAppType) type);
				AbstractTypeExporter.saveType(entry);
				entry.setType(type);

				if (newFilePage.getOpenType()) {
					openTypeEditor(entry);
				}

				return true;
			}
			return false;
		}

		return true;
	}

	private boolean askOverwrite() {
		return MessageDialog.openConfirm(getShell(), Messages.SaveAsSubApplicationTypeAction_WizardOverrideTitle,
				Messages.SaveAsSubApplicationTypeAction_WizardOverrideMessage);
	}

	private boolean createSubAppTemplateCopy() {
		String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		File templateFolder = new File(templateFolderPath + File.separatorChar + "template"); //$NON-NLS-1$
		File[] fileList = templateFolder.listFiles();
		if (null != fileList) {
			for (File file : fileList) {
				String fileName = file.getName().toUpperCase();
				if (fileName.endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING)) {
					IFile targetTypeFile = getTargetTypeFile();
					try {
						ImportUtils.copyFile(file, targetTypeFile);
						return true;
					} catch (IOException | CoreException e) {
						ApplicationPlugin.getDefault().logError(e.getMessage(), e);
					}
				}
			}
		}
		return false;
	}

	private IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath()
				+ File.separator + newFilePage.getFileName() + TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT));
	}

	private PaletteEntry getPalletEntry() {
		Palette palette = subApp.getSubAppNetwork().getAutomationSystem().getPalette();
		IFile targetTypeFile = getTargetTypeFile();
		PaletteEntry entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);

		if (null == entry) {
			// refresh the palette and retry to fetch the entry
			TypeLibrary.refreshPalette(palette);
			entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);
		}

		return entry;
	}

	private static void openTypeEditor(PaletteEntry entry) {
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(entry.getFile().getName());
		EditorUtils.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
	}

	private void performTypeSetup(SubAppType type) {
		performInterfaceSetup(type);
		type.setFBNetwork(FBNetworkHelper.copyFBNetWork(subApp.getSubAppNetwork(), type.getInterfaceList()));
	}

	private void performInterfaceSetup(SubAppType type) {
		// replace interface list with newly generated
		InterfaceList interfaceList = EcoreUtil.copy(subApp.getInterface());
		type.setInterfaceList(interfaceList);
	}
}
