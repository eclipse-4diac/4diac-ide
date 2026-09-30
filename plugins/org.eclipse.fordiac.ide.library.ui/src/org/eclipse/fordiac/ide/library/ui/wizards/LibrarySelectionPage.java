/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class LibrarySelectionPage extends WizardPage {

	private static final int SYMBOLIC_NAME_COLUMN_WIDTH = 300;
	private static final int NAME_COLUMN_WIDTH = 300;

	private enum TypeSelection {
		ALL_TYPES, INCLUDE_EXCLUDE_PATTERNS
	}

	private final Collection<Library> libraries;

	private Text outputDirectoryText;
	private TableViewer viewer;
	private Button exportAllTypesButton;

	public LibrarySelectionPage(final Collection<Library> libraries) {
		super(""); //$NON-NLS-1$
		this.libraries = libraries;
		setTitle(Messages.LibraryExporter_Title);
		setDescription(Messages.LibraryExporter_Description);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createOutputDirectoryEditor(composite);
		createLibraryViewer(composite);
		createTypeSelection(composite);

		setControl(composite);
		updatePageComplete();
	}

	public String getOutputDirectory() {
		return outputDirectoryText.getText();
	}

	public Library getSelectedLibrary() {
		final IStructuredSelection selection = viewer.getStructuredSelection();
		if (selection.getFirstElement() instanceof final Library library) {
			return library;
		}
		return null;
	}

	public TypeSelection getTypeSelection() {
		return exportAllTypesButton.getSelection() ? TypeSelection.ALL_TYPES : TypeSelection.INCLUDE_EXCLUDE_PATTERNS;
	}

	private void createOutputDirectoryEditor(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.LibraryExporter_OutputDirectory + ":"); //$NON-NLS-1$

		outputDirectoryText = new Text(composite, SWT.BORDER);
		outputDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		outputDirectoryText.addModifyListener(_ -> updatePageComplete());

		final Button browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(MessageFormat.format("{0}...", Messages.LibraryExporter_Browse)); //$NON-NLS-1$
		browseButton.addListener(SWT.Selection, _ -> browseOutputDirectory());
	}

	private void browseOutputDirectory() {
		final DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setText(Messages.LibraryExporter_BrowseText);
		dialog.setMessage(Messages.LibraryExporter_BrowseMessage);

		if (!outputDirectoryText.getText().isBlank()) {
			dialog.setFilterPath(outputDirectoryText.getText());
		}

		final String directory = dialog.open();
		if (directory != null) {
			outputDirectoryText.setText(directory);
		}
	}

	private void createLibraryViewer(final Composite parent) {
		viewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSymbolicNameColumn();
		createNameColumn();

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(libraries);

		viewer.addSelectionChangedListener(_ -> updatePageComplete());
	}

	private void createSymbolicNameColumn() {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(Messages.LibraryPage_SymbolicName);
		column.getColumn().setWidth(SYMBOLIC_NAME_COLUMN_WIDTH);

		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Library lib) {
					return Objects.toString(lib.getSymbolicName(), ""); //$NON-NLS-1$
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private void createNameColumn() {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(Messages.LibraryPage_Name);
		column.getColumn().setWidth(NAME_COLUMN_WIDTH);

		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Library lib) {
					return Objects.toString(lib.getName(), ""); //$NON-NLS-1$
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private void createTypeSelection(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.LibraryExporter_TypeSelection);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		exportAllTypesButton = new Button(group, SWT.RADIO);
		exportAllTypesButton.setText(Messages.LibraryExporter_ExportAllTypes);
		exportAllTypesButton.setSelection(true);

		final Button usePatternsButton = new Button(group, SWT.RADIO);
		usePatternsButton.setText(Messages.LibraryExporter_UseIncludeExclude);
	}

	private void updatePageComplete() {
		if (outputDirectoryText == null || viewer == null) {
			setPageComplete(false);
			return;
		}

		setPageComplete(!outputDirectoryText.getText().isBlank() && !viewer.getSelection().isEmpty());
	}
}
