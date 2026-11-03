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
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.library.export.LibraryExporter.TypeSelectionResult;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LibraryExportPreviewPage extends WizardPage {

	private TypeSelectionResult types = new TypeSelectionResult(List.of(), List.of());

	private TableViewer viewer;
	private Label exportedTypes;
	private Label unusedTypes;

	public LibraryExportPreviewPage() {
		super("libraryExportPreview"); //$NON-NLS-1$
		setTitle(Messages.LibraryExporter_Preview_Title);
		setDescription(Messages.LibraryExporter_Preview_Description);
	}

	public void setInput(final TypeSelectionResult types) {
		this.types = types;
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.setInput(types.included());
			exportedTypes.setText(MessageFormat.format(Messages.LibraryExporter_IncludedTypes,
					Integer.valueOf(types.getIncludedCount())));
			unusedTypes.setText(MessageFormat.format(Messages.LibraryExporter_UnusedTypes,
					Integer.valueOf(types.getExcludedCount())));

			((Composite) getControl()).layout(true, true);
		}
	}

	public TypeSelectionResult getTypes() {
		return types;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		exportedTypes = new Label(composite, SWT.NONE);
		exportedTypes.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

		unusedTypes = new Label(composite, SWT.NONE);
		unusedTypes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Composite tableComposite = new Composite(composite, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TableColumnLayout columnLayout = new TableColumnLayout();
		tableComposite.setLayout(columnLayout);

		viewer = new TableViewer(tableComposite,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);

		createPackageNameColumn(columnLayout);
		createTypeNameColumn(columnLayout);

		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(List.of());

		setControl(composite);
		setInput(types);
	}

	private void createTypeNameColumn(final TableColumnLayout layout) {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(Messages.LibraryExporter_TypeName);

		layout.setColumnData(column.getColumn(), new ColumnWeightData(25, 150));

		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final TypeEntry typeEntry ? Objects.toString(typeEntry.getTypeName(), "") //$NON-NLS-1$
						: ""; //$NON-NLS-1$
			}
		});
	}

	private void createPackageNameColumn(final TableColumnLayout layout) {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(Messages.LibraryExporter_PackageName);

		layout.setColumnData(column.getColumn(), new ColumnWeightData(75, 150));

		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final TypeEntry typeEntry ? Objects.toString(typeEntry.getPackageName(), "") //$NON-NLS-1$
						: ""; //$NON-NLS-1$
			}
		});
	}

}
