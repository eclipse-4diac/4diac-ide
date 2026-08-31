/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.properties;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.export.utils.AdditionalSourceDirectories;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

final class AdditionalSourceDirectoriesSection {

	private final List<IPath> directories = new ArrayList<>();
	private final Runnable changeListener;
	private final Composite container;
	private final org.eclipse.swt.widgets.List directoryList;
	private final Button addExistingButton;
	private final Button newButton;
	private final Button removeButton;

	private Optional<IFolder> outputFolder = Optional.empty();

	AdditionalSourceDirectoriesSection(final Composite parent, final Runnable changeListener) {
		this.changeListener = changeListener;
		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);

		final Label title = new Label(container, SWT.NONE);
		title.setText(Messages.TypeExport_AdditionalSourceDirectories);

		final Label description = new Label(container, SWT.WRAP);
		description.setText(Messages.TypeExport_AdditionalSourceDirectoriesDescription);
		GridDataFactory.fillDefaults().grab(true, false).hint(600, SWT.DEFAULT).applyTo(description);

		final Composite editor = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(editor);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);

		directoryList = new org.eclipse.swt.widgets.List(editor, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 100).applyTo(directoryList);
		directoryList.addListener(SWT.Selection, event -> updateButtons());

		final Composite buttons = new Composite(editor, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(buttons);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).applyTo(buttons);

		addExistingButton = createButton(buttons, Messages.TypeExport_AddExistingSourceDirectory,
				this::addExistingDirectories);
		newButton = createButton(buttons, Messages.TypeExport_NewSourceDirectory, this::createDirectory);
		removeButton = createButton(buttons, Messages.TypeExport_RemoveSourceDirectory,
				this::removeSelectedDirectories);
	}

	void setOutputFolder(final Optional<IFolder> outputFolder) {
		this.outputFolder = outputFolder;
		updateButtons();
	}

	void setDirectories(final Collection<IPath> newDirectories) {
		directories.clear();
		directories.addAll(newDirectories);
		refreshList();
	}

	List<IPath> getDirectories() {
		return List.copyOf(directories);
	}

	void setEditorEnabled(final boolean enable) {
		container.setEnabled(enable);
		updateButtons();
	}

	private void addExistingDirectories() {
		final IFolder currentOutputFolder = getRefreshedOutputFolder();
		if (currentOutputFolder == null) {
			return;
		}
		if (!currentOutputFolder.exists()) {
			MessageDialog.openInformation(container.getShell(), Messages.TypeExport_AdditionalSourceDirectories,
					Messages.TypeExport_OutputFolderMissing);
			return;
		}

		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(container.getShell(),
				new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setInput(currentOutputFolder);
		dialog.setAllowMultiple(true);
		dialog.setTitle(Messages.TypeExport_SelectSourceDirectories);
		dialog.setMessage(Messages.TypeExport_SelectSourceDirectoriesMessage);
		dialog.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				return element instanceof final IFolder folder && isSelectable(folder);
			}
		});

		if (dialog.open() != Window.OK) {
			return;
		}
		for (final Object result : dialog.getResult()) {
			if (result instanceof final IFolder folder) {
				addDirectory(folder);
			}
		}
		directoriesChanged();
	}

	private void createDirectory() {
		final IFolder currentOutputFolder = getRefreshedOutputFolder();
		if (currentOutputFolder != null) {
			createDirectory(currentOutputFolder);
		}
	}

	private void createDirectory(final IFolder currentOutputFolder) {
		final InputDialog dialog = new InputDialog(container.getShell(), Messages.TypeExport_AdditionalSourceDirectories,
				MessageFormat.format(Messages.TypeExport_NewSourceDirectoryMessage,
						currentOutputFolder.getProjectRelativePath().toPortableString()),
				"", value -> validateDirectoryName(currentOutputFolder, value)); //$NON-NLS-1$
		if (dialog.open() != Window.OK) {
			return;
		}

		final IFolder directory = currentOutputFolder.getFolder(new Path(dialog.getValue()));
		try {
			createFolder(directory);
			addDirectory(directory);
			directoriesChanged();
		} catch (final CoreException e) {
			showError(e);
		}
	}

	private String validateDirectoryName(final IFolder parent, final String name) {
		final IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(name, IResource.FOLDER);
		if (!nameStatus.isOK()) {
			return nameStatus.getMessage();
		}
		if (!AdditionalSourceDirectories.isValidRelativeDirectory(java.nio.file.Path.of(name))) {
			return Messages.TypeExport_InvalidSourceDirectories;
		}
		if (name.startsWith(".")) { //$NON-NLS-1$
			return Messages.TypeExport_HiddenSourceDirectory;
		}
		if (parent.findMember(name) != null) {
			return MessageFormat.format(Messages.TypeExport_SourceDirectoryExists, name);
		}
		return null;
	}

	private void removeSelectedDirectories() {
		final int[] selection = directoryList.getSelectionIndices();
		for (int i = selection.length - 1; i >= 0; i--) {
			directories.remove(selection[i]);
		}
		directoriesChanged();
	}

	private void addDirectory(final IFolder directory) {
		final IPath path = directory.getProjectRelativePath();
		if (!directories.contains(path)) {
			directories.add(path);
		}
	}

	private void directoriesChanged() {
		refreshList();
		changeListener.run();
	}

	private void refreshList() {
		directoryList.setItems(directories.stream().map(IPath::toPortableString).toArray(String[]::new));
		updateButtons();
	}

	private void updateButtons() {
		newButton.setEnabled(outputFolder.isPresent());
		addExistingButton.setEnabled(outputFolder.isPresent());
		removeButton.setEnabled(directoryList.getSelectionCount() > 0);
	}

	private IFolder getRefreshedOutputFolder() {
		final IFolder folder = outputFolder.orElse(null);
		return folder != null && refresh(folder) ? folder : null;
	}

	private boolean refresh(final IFolder folder) {
		try {
			folder.refreshLocal(IResource.DEPTH_INFINITE, null);
			return true;
		} catch (final CoreException e) {
			showError(e);
			return false;
		}
	}

	private void showError(final CoreException exception) {
		MessageDialog.openError(container.getShell(), Messages.TypeExport_AdditionalSourceDirectories,
				exception.getLocalizedMessage());
	}

	private static Button createButton(final Composite parent, final String text, final Runnable action) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(button);
		button.addListener(SWT.Selection, event -> action.run());
		return button;
	}

	private static boolean isSelectable(final IFolder folder) {
		return !folder.isVirtual() && !folder.isLinked(IResource.CHECK_ANCESTORS)
				&& !folder.getName().startsWith("."); //$NON-NLS-1$
	}

	private static void createFolder(final IFolder folder) throws CoreException {
		if (folder.exists()) {
			return;
		}
		if (folder.getParent() instanceof final IFolder parent) {
			createFolder(parent);
		}
		folder.create(IResource.FORCE, true, null);
	}

}
