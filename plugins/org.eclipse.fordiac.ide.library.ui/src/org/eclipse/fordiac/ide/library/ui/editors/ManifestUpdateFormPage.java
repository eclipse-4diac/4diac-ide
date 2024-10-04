/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;

public class ManifestUpdateFormPage extends FormPage {
	private IFile manifestFile;
	private final List<Required> libraries;
	private TableViewer tableViewer;

	public ManifestUpdateFormPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
		libraries = new ArrayList<>();
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		final ScrolledForm form = managedForm.getForm();

		form.setText("Update dependencies"); //$NON-NLS-1$

		final Composite composite = form.getBody();

		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		manifestFile = ((FileEditorInput) getEditorInput()).getFile();
		updateInput();

		tableViewer = new TableViewer(composite);
		configureTableViewer(tableViewer);
		tableViewer.setInput(libraries);
		GridDataFactory.swtDefaults().grab(true, true).applyTo(composite);

		composite.layout();
		form.getBody().layout();
	}

	private void configureTableViewer(final TableViewer viewer) {
		viewer.setContentProvider(new ArrayContentProvider());

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());

		final TableViewerColumn symNameColumn = new TableViewerColumn(viewer, SWT.LEAD);
		symNameColumn.getColumn().setText("Symbolic Name"); //$NON-NLS-1$
		symNameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Required) element).getSymbolicName();
			}
		});

		final TableViewerColumn versionColumn = new TableViewerColumn(viewer, SWT.LEAD);
		versionColumn.getColumn().setText("Version"); //$NON-NLS-1$
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Required) element).getVersion();
			}
		});

		final TableViewerColumn buttonColumn = new TableViewerColumn(viewer, SWT.LEAD);
		buttonColumn.getColumn().setText("Update"); //$NON-NLS-1$
		buttonColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public void update(final ViewerCell cell) {
				final Button button = new Button((Composite) cell.getControl(), SWT.NONE);
				final TableItem item = (TableItem) cell.getItem();
				button.setText("Update"); //$NON-NLS-1$

				button.addSelectionListener(new SelectionListener() {
					private final TableItem tItem = item;

					@Override
					public void widgetSelected(final SelectionEvent e) {
						final Required req = (Required) tItem.getData();
						LibraryManager.INSTANCE.updateLibrary(manifestFile.getProject(), req.getSymbolicName(),
								req.getVersion());
					}

					@Override
					public void widgetDefaultSelected(final SelectionEvent e) {
						// do nothing
					}
				});

				final TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(button, item, cell.getColumnIndex());
				editor.layout();
			}
		});
	}

	@SuppressWarnings("static-method")
	private TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(100));
		layout.addColumnData(new ColumnPixelData(100));
		return layout;
	}

	private void updateInput() {
		libraries.clear();
		final Manifest manifest = ManifestHelper.getManifest(manifestFile);
		if (manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().forEach(libraries::add);
		}
	}

	@Override
	public void setActive(final boolean active) {
		if (active) {
			updateInput();
			tableViewer.refresh();
		}
		super.setActive(active);
	}
}
