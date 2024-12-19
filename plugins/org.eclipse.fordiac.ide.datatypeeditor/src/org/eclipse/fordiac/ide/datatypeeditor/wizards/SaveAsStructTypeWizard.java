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
 *   Lukas Wais
 *     - initial API and implementation and/or initial documentation
 *
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.wizards.AbstractSaveAsWizard;
import org.eclipse.fordiac.ide.application.wizards.SaveAsWizardPage;
import org.eclipse.fordiac.ide.datatypeeditor.editors.DataTypeEditor;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class SaveAsStructTypeWizard extends AbstractSaveAsWizard {

	private final StructuredType structuredType;
	private final IProject project;
	private final String oldEditorName;
	private final DataTypeEditor dataTypeEditor;

	public SaveAsStructTypeWizard(final StructuredType structuredType, final DataTypeEditor dataTypeEditor) {
		setWindowTitle(Messages.SaveAsStructTypeWizard_WindowTitle);
		this.project = structuredType.getTypeEntry().getFile().getProject();
		this.structuredType = structuredType;
		this.dataTypeEditor = dataTypeEditor;
		oldEditorName = structuredType.getTypeEntry().getTypeName();
	}

	@Override
	public void addPages() {
		final StructuredSelection selection = new StructuredSelection(project); // select the current project
		newFilePage = SaveAsWizardPage.createSaveAsStructTypeWizardPage(Messages.SaveAsStructTypeWizard_WizardPageName,
				selection);
		newFilePage.setFileName(structuredType.getName());
		addPage(newFilePage);
	}

	@Override
	public boolean performFinish() {
		if (perform()) {
			if (TypeEntry.getTypeNameFromFile(getTargetTypeFile()).equalsIgnoreCase(oldEditorName)) {
				// for overwriting we save the current editor
				dataTypeEditor.doSave(new NullProgressMonitor());
			} else {
				final IFile targetFile = persistNewType();
				if (newFilePage.getOpenType()) {
					OpenStructMenu.openStructEditor(targetFile);
					closeOldEditor();
				}
			}
		}
		return true;
	}

	@Override
	protected boolean askOverwrite() {
		return MessageDialog.openConfirm(getShell(), Messages.ConvertToStructHandler_ErrorTitle,
				Messages.ConvertToStructHandler_ErrorMessage);
	}

	@Override
	protected IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath()
				+ File.separator + newFilePage.getFileName() + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT));
	}

	private void closeOldEditor() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				activePage.closeEditor(getCurrentOpenEditor().getEditor(false), false);
			}
		}
	}

	private IEditorReference getCurrentOpenEditor() {
		return Arrays.stream(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences())
				.filter(editor -> editor.getName()
						.equalsIgnoreCase(oldEditorName + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT))
				.findAny().orElseThrow();
	}

	private IFile persistNewType() {
		final IFile targetFile = getTargetTypeFile();
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(targetFile.getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				final String newName = TypeEntry.getTypeNameFromFile(targetFile);
				final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
				type.setName(structuredType.getName());
				type.setComment(structuredType.getComment());
				InterfaceListCopier.copyVarList(type.getMemberVariables(), structuredType.getMemberVariables(), true);
				TypeManagementPreferencesHelper.setupVersionInfo(type);
				type.setName(newName);
				createTypeEntry(targetFile).save(type, monitor);
			}
		};
		try {
			getContainer().run(true, true, operation);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		return targetFile;
	}

	private TypeEntry createTypeEntry(final IFile targetTypeFile) {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(project).createTypeEntry(targetTypeFile);
	}
}
