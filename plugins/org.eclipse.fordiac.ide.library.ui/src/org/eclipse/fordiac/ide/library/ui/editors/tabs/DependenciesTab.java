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
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class DependenciesTab {

	private Library library;
	private final TableControl dependencyTable;
	private final ManifestEditorPage<Exports> page;

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
		refresh();
	}

	public void refresh() {
		dependencyTable.getViewer().setInput(getDependencies());
		dependencyTable.setEnabled(library != null);
	}

	private void createColumns() {
		final TableViewerColumn symbolicNameColumn = dependencyTable.createColumn(Messages.ManifestEditor_SymbolicName,
				60);
		symbolicNameColumn
				.setLabelProvider(TableControl.createLabelProvider(Required.class, Required::getSymbolicName));
		symbolicNameColumn.setEditingSupport(TableControl.createEditingSupport(dependencyTable.getViewer(), page,
				Required.class, Required::getSymbolicName, this::createModifyNameOperation,
				new TextCellEditor(dependencyTable.getViewer().getTable())));

		final TableViewerColumn versionRangeColumn = dependencyTable.createColumn(Messages.ManifestEditor_VersionRange,
				40);
		versionRangeColumn.setLabelProvider(TableControl.createLabelProvider(Required.class, Required::getVersion));
		versionRangeColumn.setEditingSupport(TableControl.createEditingSupport(dependencyTable.getViewer(), page,
				Required.class, Required::getVersion, this::createModifyVersionOperation,
				new VersionRangeCellEditor(dependencyTable.getViewer().getTable())));
	}

	private void addDependency() {
		page.execute(new AddElementOperation<>("Add Dependency", getDependencies(), //$NON-NLS-1$
				ManifestHelper.createRequired("dependency", "1.0.0"), this::refresh)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void removeDependency() {
		final Object selection = dependencyTable.getViewer().getStructuredSelection().getFirstElement();

		if (selection instanceof final Required required) {
			page.execute(new RemoveElementOperation<>("Remove Dependency", getDependencies(), required, this::refresh)); //$NON-NLS-1$
		}
	}

	private EList<Required> getDependencies() {
		return library != null && library.getDependencies() != null ? library.getDependencies().getRequired()
				: ECollections.emptyEList();
	}

	private IUndoableOperation createModifyNameOperation(final Required dependency, final String value) {
		return new SetValueOperation<>("Set dependency name", dependency::getSymbolicName, dependency::setSymbolicName, //$NON-NLS-1$
				value, this::refresh);
	}

	private IUndoableOperation createModifyVersionOperation(final Required dependency, final String value) {
		return new SetValueOperation<>("Set dependency version", dependency::getVersion, dependency::setVersion, value, //$NON-NLS-1$
				this::refresh);
	}

}
