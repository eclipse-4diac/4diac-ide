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
package org.eclipse.fordiac.ide.library.ui.editors.tabs;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorPage;
import org.eclipse.fordiac.ide.library.ui.editors.VersionRangeCellEditor;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public final class DependenciesTab {

	private final ManifestEditorPage<Exports> page;
	private final TableControl dependencyTable;

	private Library library;

	public DependenciesTab(final Composite parent, final FormToolkit toolkit, final ManifestEditorPage<Exports> page) {

		this.page = page;

		dependencyTable = new TableControl(parent, toolkit, ""); //$NON-NLS-1$
		dependencyTable.setAddHandler(this::addDependency);
		dependencyTable.setRemoveHandler(this::removeDependency);

		createColumns();
	}

	public Control getControl() {
		return dependencyTable.getControl();
	}

	public void setInput(final Library library) {
		this.library = library;
		dependencyTable.getViewer().setInput(getDependencies());
		dependencyTable.setEnabled(library != null);
	}

	public void refresh() {
		dependencyTable.getViewer().refresh();
	}

	private void createColumns() {
		final TableViewerColumn nameColumn = dependencyTable.createColumn(Messages.ManifestEditor_SymbolicName, 60);

		nameColumn.setLabelProvider(
				TableControl.createLabelProvider(Required.class, Required::getSymbolicName, this::hasError));

		nameColumn.setEditingSupport(TableControl.createEditingSupport(dependencyTable.getViewer(), page,
				Required.class, Required::getSymbolicName, this::createModifyNameOperation,
				new TextCellEditor(dependencyTable.getViewer().getTable())));

		final TableViewerColumn versionColumn = dependencyTable.createColumn(Messages.ManifestEditor_VersionRange, 40);

		versionColumn.setLabelProvider(
				TableControl.createLabelProvider(Required.class, Required::getVersion, this::hasError));

		versionColumn.setEditingSupport(TableControl.createEditingSupport(dependencyTable.getViewer(), page,
				Required.class, Required::getVersion, this::createModifyVersionOperation,
				new VersionRangeCellEditor(dependencyTable.getViewer().getTable())));
	}

	private boolean hasError(final Required required) {
		return ManifestEditorPage.targets(page.getManifestEditor().getValidationResult(), required);
	}

	private void addDependency() {
		if (library == null || library.getDependencies() == null) {
			return;
		}

		final Required required = ManifestHelper.createRequired("dependency", "1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$

		page.execute(new AddElementOperation<>("Add Dependency", getDependencies(), required)); //$NON-NLS-1$

		dependencyTable.getViewer().setSelection(new StructuredSelection(required), true);
	}

	private void removeDependency() {
		final Object selection = dependencyTable.getViewer().getStructuredSelection().getFirstElement();

		if (selection instanceof final Required required) {
			page.execute(new RemoveElementOperation<>("Remove Dependency", //$NON-NLS-1$
					getDependencies(), required));
		}
	}

	private EList<Required> getDependencies() {
		return library != null && library.getDependencies() != null ? library.getDependencies().getRequired()
				: ECollections.emptyEList();
	}

	private IUndoableOperation createModifyNameOperation(final Required dependency, final String value) {

		return new SetValueOperation<>("Set dependency name", //$NON-NLS-1$
				dependency::getSymbolicName, dependency::setSymbolicName, value);
	}

	private IUndoableOperation createModifyVersionOperation(final Required dependency, final String value) {

		return new SetValueOperation<>("Set dependency version", //$NON-NLS-1$
				dependency::getVersion, dependency::setVersion, value);
	}
}
