/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

public class OpenTypeHandler extends AbstractHandler {

	ElementListSelectionDialog dialog;
	private final Set<TypeEntry> entries = new HashSet<>();
	private final List<IContainer> projects = new ArrayList<>();
	private int selectedProject = 0;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		clear();
		collectProjects(ResourcesPlugin.getWorkspace().getRoot());
		processContainer(ResourcesPlugin.getWorkspace().getRoot());
		dialog = createDialog();
		dialog.setElements(entries.toArray());
		dialog.open();

		final Object result = dialog.getFirstResult();
		if (result instanceof final TypeEntry typeEntry) {
			openEditor(typeEntry.getFile());
		}
		return null;
	}

	private void clear() {
		dialog = null;
		entries.clear();
		projects.clear();
		selectedProject = 0;
	}

	private void changeDialog() {
		final String searchString = dialog.getFilter();
		dialog.close();
		entries.clear();
		processContainer(projects.get(selectedProject));
		dialog = createDialog();
		dialog.setElements(entries.toArray());
		dialog.setFilter(searchString);
		dialog.open();
	}

	private ElementListSelectionDialog createDialog() {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final ElementListSelectionDialog selDialog = new ElementListSelectionDialog(shell,
				new ResultListLabelProvider()) {

			@Override
			protected Control createDialogArea(final Composite parent) {
				final Composite container = (Composite) super.createDialogArea(parent);
				final ComboViewer viewer = new ComboViewer(container);
				viewer.getCombo().setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
				viewer.setLabelProvider(new LabelProvider() {

					@Override
					public String getText(final Object element) {
						if (element instanceof final IProject project) {
							return project.getName();
						}
						return "All Projects";
					}

				});
				viewer.getCombo().addListener(SWT.Selection, e -> {
					selectedProject = viewer.getCombo().getSelectionIndex();
					changeDialog();
				});
				viewer.add(projects.toArray());
				viewer.getCombo().select(selectedProject);
				return container;
			}

		};
		setDialogSettings(selDialog);
		return selDialog;
	}

	private static void setDialogSettings(final ElementListSelectionDialog dialog) {
		dialog.setMatchEmptyString(false);
		dialog.setMultipleSelection(false);
		dialog.setEmptyListMessage(Messages.OpenTypeHandler_NO_FILES_IN_WORKSPACE);
		dialog.setEmptySelectionMessage(Messages.OpenTypeHandler_NO_FILES_SELECTED);
		dialog.setTitle(Messages.OpenTypeHandler_OPEN_TYPE_TITLE);
		dialog.setStatusLineAboveButtons(true);
	}

	private void collectProjects(final IContainer container) {
		// add "All Projects" entry to dropdown
		projects.add(0, container);
		IResource[] members;
		try {
			members = container.members();
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			members = new IResource[0];
		}
		for (final IResource member : members) {
			if (member instanceof final IProject project && !project.isOpen()) {
				continue;
			}
			projects.add((IContainer) member);
		}
	}

	private void processContainer(final IContainer container) {
		IResource[] members;
		try {
			members = container.members();
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			members = new IResource[0];
		}
		for (final IResource member : members) {
			if (member instanceof final IProject project && !project.isOpen()) {
				continue;
			}
			if (member instanceof final IContainer cont) {
				processContainer(cont);
			} else if (member instanceof final IFile file
					&& !TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension())) {
				entries.add(TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) member));
			}
		}

	}

	private static void openEditor(final IFile file) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
						.getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (final PartInitException e1) {
					FordiacLogHelper.logError(e1.getMessage(), e1);
					final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(shell, Messages.OpenTypeHandler_OPEN_TYPE_ERROR_TITLE,
							Messages.OpenTypeHandler_EDITOR_OPEN_ERROR_MESSAGE);
				}
			}
		}
	}

}
