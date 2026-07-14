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
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.LinkedLibrary;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider;
import org.eclipse.fordiac.ide.library.provider.OfflineLibraryProvider;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;

public class ManifestEditorDependencyPage extends FormPage {

	private final ILibraryProvider offlineLibraryProvider = new OfflineLibraryProvider();
	private Manifest manifest;

	/**
	 * Maps symbolic names of linked libraries to Version Strings
	 */
	private final Map<String, String> linkedLibVersions = new HashMap<>();

	public ManifestEditorDependencyPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		final ScrolledForm form = managedForm.getForm();

		final Composite root = form.getBody();
		root.setLayout(new GridLayout(1, false));

		final Composite treeContainer = new Composite(root, SWT.NONE);
		treeContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TreeColumnLayout columnLayout = new TreeColumnLayout();
		treeContainer.setLayout(columnLayout);

		final TreeViewer treeViewer = new TreeViewer(treeContainer,
				SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		configureColumns(columnLayout, treeViewer);

		// do not compute dependencies
		offlineLibraryProvider.refresh(new NullProgressMonitor(), false);

		collectLinkedLibraryVersions();

		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof final Collection<?> list) {
					return !list.isEmpty();
				}
				if (element instanceof final LibContainer container) {
					return !container.children().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(final Object element) {
				return null;
			}

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof final Collection<?> list) {
					return list.toArray();
				}
				if (inputElement instanceof final LibContainer container) {
					return container.children().toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final Collection<?> list) {
					return list.toArray();
				}
				if (parentElement instanceof final LibContainer desc) {
					return desc.children().toArray();
				}
				return new Object[0];
			}
		});

		treeViewer.setInput(createViewerInput());
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.expandAll();

		root.layout();
		form.getBody().layout();
	}

	private List<LibContainer> createViewerInput() {
		if (getEditorInput() instanceof final FileEditorInput input) {
			manifest = ManifestHelper.getManifest(input.getFile());
			return List.of(new LibContainer("Required", manifest.getDependencies().getRequired())); //$NON-NLS-1$
		}
		return List.of(new LibContainer("Required", Collections.emptyList())); //$NON-NLS-1$
	}

	private void collectLinkedLibraryVersions() {
		if (getEditorInput() instanceof final FileEditorInput input) {
			final IFile file = input.getFile();
			if (file == null) {
				return;
			}
			final IProject project = file.getProject();
			try {
				LinkedLibrary.getAll(project, null).forEach(
						lib -> linkedLibVersions.putIfAbsent(lib.getSymbolicName(), lib.getVersion().toString()));
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	private String getUsedVersion(final String symbolicName) {
		return linkedLibVersions.getOrDefault(symbolicName, ""); //$NON-NLS-1$
	}

	private void configureColumns(final TreeColumnLayout layout, final TreeViewer treeViewer) {

		// symbolic name column
		final TreeViewerColumn symbolicNameColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		symbolicNameColumn.getColumn().setText(Messages.ManifestEditor_Column_SymbolicName);
		symbolicNameColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final LibContainer container) {
					cell.setText(container.name());
				}
				if (cell.getElement() instanceof final Required req) {
					cell.setText(req.getSymbolicName());
				}
			}
		});

		// version range column
		final TreeViewerColumn versionRangeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		versionRangeColumn.getColumn().setText(Messages.ManifestEditor_Column_VersionRange);
		versionRangeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final Required req) {
					cell.setText(req.getVersion());
				}
			}
		});
		versionRangeColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof final Required req) {
					req.setVersion(value.toString());
					getViewer().update(element, null);
					if (ManifestEditorDependencyPage.this.getEditor() instanceof final ManifestEditor editor) {
						editor.setDirty(true);
					}
				}
			}

			@Override
			protected Object getValue(final Object element) {
				if (element instanceof final Required req) {
					return req.getVersion();
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new TextCellEditor(treeViewer.getTree());
			}

			@Override
			protected boolean canEdit(final Object element) {
				return true;
			}
		});

		// active version column
		final TreeViewerColumn activeVersionColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		activeVersionColumn.getColumn().setText(Messages.ManifestEditor_Column_Used);
		activeVersionColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final Required req) {
					cell.setText(getUsedVersion(req.getSymbolicName()));
				}
			}
		});

		// latest version in range column
		final TreeViewerColumn latestInRangeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		latestInRangeColumn.getColumn().setText(Messages.ManifestEditor_Column_LatestInRange);
		latestInRangeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final Required req) {
					final org.osgi.framework.VersionRange range = VersionComparator.parseVersionRange(req.getVersion());
					cell.setText(offlineLibraryProvider.getLatest(req.getSymbolicName(), range)
							.map(library -> library.version().toString()).orElse("")); //$NON-NLS-1$
				}
			}
		});

		// latest overall version column
		final TreeViewerColumn latestColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		latestColumn.getColumn().setText(Messages.ManifestEditor_Column_Latest);
		latestColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final Required req) {
					cell.setText(offlineLibraryProvider.getLatest(req.getSymbolicName())
							.map(lib -> lib.version().toString()).orElse("")); //$NON-NLS-1$
				}
			}
		});

		// define column width ratios
		layout.setColumnData(symbolicNameColumn.getColumn(), new ColumnWeightData(40));
		layout.setColumnData(versionRangeColumn.getColumn(), new ColumnWeightData(30));
		layout.setColumnData(activeVersionColumn.getColumn(), new ColumnWeightData(20));
		layout.setColumnData(latestInRangeColumn.getColumn(), new ColumnWeightData(20));
		layout.setColumnData(latestColumn.getColumn(), new ColumnWeightData(20));
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		super.doSave(monitor);
		ManifestHelper.saveManifest(manifest);
	}

	record LibContainer(String name, List<Required> children) {
		LibContainer(final String name) {
			this(name, new ArrayList<>());
		}
	}
}
