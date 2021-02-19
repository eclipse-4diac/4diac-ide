/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017, 2019 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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
 *               - moved replace source subapp to an wizard option
 *   Lukas Wais  - Adaption to work with new super class
 *   Lukas Wais,
 *   Michael Oberlehner - Refactored code for better readability
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
import org.eclipse.fordiac.ide.application.commands.CommandUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.dataexport.AbstractBlockTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
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
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class SaveAsSubappWizard extends AbstractSaveAsWizard {

	private static final String SUBAPP_SECTION = "SUBAPP_SECTION"; //$NON-NLS-1$

	private final SubApp subApp;

	public SaveAsSubappWizard(final SubApp subApp, final String windowTitle) {
		super(SUBAPP_SECTION);
		setWindowTitle(windowTitle);
		this.subApp = subApp;
	}

	@Override
	public void addPages() {
		final IProject project = getSystem().getSystemFile().getProject();
		final StructuredSelection selection = new StructuredSelection(project); // select the current project
		newFilePage = SaveAsWizardPage
				.createSaveAsSubAppWizardPage(Messages.SaveAsSubApplicationTypeAction_WizardPageName, selection);
		newFilePage.setFileName(subApp.getName());
		addPage(newFilePage);
	}

	private AutomationSystem getSystem() {
		return subApp.getSubAppNetwork().getAutomationSystem();
	}

	@Override
	public boolean performFinish() {
		if (perform()) {
			final File[] fileList = getFilesFromTemplateFolder();
			if (null == fileList) {
				return false;
			}
			createSubAppTemplateCopy(fileList);
			createSupApplication();
		}
		return true;
	}

	private void createSupApplication() {
		final PaletteEntry entry = getPaletteEntry();
		final LibraryElement type = entry.getType();
		type.setName(TypeLibrary.getTypeNameFromFile(entry.getFile()));

		TypeManagementPreferencesHelper.setupIdentification(type);
		TypeManagementPreferencesHelper.setupVersionInfo(type);
		performTypeSetup((SubAppType) type);
		AbstractBlockTypeExporter.saveType(entry);
		entry.setType(type);

		if (newFilePage.getOpenType()) {
			openTypeEditor(entry);
		}

		if (newFilePage.getReplaceSource()) {
			replaceWithType(entry);
		}
	}

	private boolean createSubAppTemplateCopy(File[] fileList) {
		if (null != fileList) {
			for (final File file : fileList) {
				final String fileName = file.getName().toUpperCase();
				if (fileName.endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING)) {
					final IFile targetTypeFile = getTargetTypeFile();
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

	private static File[] getFilesFromTemplateFolder() {
		final String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		final File templateFolder = new File(templateFolderPath + File.separatorChar + "template"); //$NON-NLS-1$
		return templateFolder.listFiles();
	}

	private PaletteEntry getPaletteEntry() {
		final IFile targetTypeFile = getTargetTypeFile();
		PaletteEntry newEntry = TypeLibrary.getPaletteEntryForFile(targetTypeFile);
		if (null == newEntry) {
			// refresh the palette and retry to fetch the entry
			TypeLibrary.refreshTypeLib(targetTypeFile);
			newEntry = TypeLibrary.getPaletteEntryForFile(targetTypeFile);
		}

		return newEntry;
	}

	private static void openTypeEditor(final PaletteEntry entry) {
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(entry.getFile().getName());
		EditorUtils.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
	}

	private void replaceWithType(final PaletteEntry entry) {
		CommandUtil.closeOpenedSubApp(subApp.getSubAppNetwork());
		getSystem().getCommandStack().execute(new UpdateFBTypeCommand(subApp, entry));
	}

	private void performTypeSetup(final SubAppType type) {
		performInterfaceSetup(type);
		type.setFBNetwork(FBNetworkHelper.copyFBNetWork(subApp.getSubAppNetwork(), type.getInterfaceList()));
	}

	private void performInterfaceSetup(final SubAppType type) {
		// replace interface list with newly generated
		final InterfaceList interfaceList = EcoreUtil.copy(subApp.getInterface());
		type.setInterfaceList(interfaceList);
	}

	@Override
	protected boolean askOverwrite() {
		return MessageDialog.openConfirm(getShell(), Messages.SaveAsSubApplicationTypeAction_WizardOverrideTitle,
				Messages.SaveAsSubApplicationTypeAction_WizardOverrideMessage);
	}

	@Override
	public IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath()
				+ File.separator + newFilePage.getFileName() + TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT));
	}

}
