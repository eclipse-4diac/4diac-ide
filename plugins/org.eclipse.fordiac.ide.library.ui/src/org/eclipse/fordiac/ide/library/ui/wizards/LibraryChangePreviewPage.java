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

import java.util.List;

import org.eclipse.fordiac.ide.library.LibraryChange;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class LibraryChangePreviewPage extends WizardPage {

	private TableViewer tableViewer;
	private List<LibraryChange> input = List.of();

	protected LibraryChangePreviewPage(final String pageName) {
		super(pageName);
		setTitle(Messages.ManageLibraryWizard_PreviewPage_Titel);
		setDescription(Messages.ManageLibraryWizard_PreviewPage_Description);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TableColumnLayout layout = new TableColumnLayout();
		root.setLayout(layout);

		tableViewer = new TableViewer(root, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		createColumn(layout);

		tableViewer.setInput(input);

		setControl(root);
		setPageComplete(true);
	}

	public void setInput(final List<LibraryChange> changes) {
		input = changes != null ? changes : List.of();

		if (tableViewer != null && !tableViewer.getTable().isDisposed()) {
			tableViewer.setInput(input);
		}
	}

	private void createColumn(final TableColumnLayout layout) {
		final TableViewerColumn nameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		nameColumn.getColumn().setText(Messages.ManageLibraryWizard_Change);
		nameColumn.setLabelProvider(new LibraryChangeLabelProvider(LibraryChange::getDescription, false));
		layout.setColumnData(nameColumn.getColumn(), new ColumnWeightData(100));
	}

}