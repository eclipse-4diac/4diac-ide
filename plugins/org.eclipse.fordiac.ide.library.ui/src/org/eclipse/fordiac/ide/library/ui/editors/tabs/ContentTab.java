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

import java.util.List;
import java.util.function.BiFunction;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorLibraryPage;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ContentTab {

	private final TableControl includesTable;
	private final TableControl excludesTable;
	private final Composite control;
	private final ManifestEditorLibraryPage page;

	private Library library;

	private static final String EXAMPLE_PATTERN = "some::example::pattern::*"; //$NON-NLS-1$

	public ContentTab(final Composite parent, final FormToolkit toolkit, final ManifestEditorLibraryPage page) {
		this.page = page;

		control = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(control);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(control);

		this.includesTable = new TableControl(control, toolkit, Messages.ManifestEditor_Includes);
		includesTable.setAddHandler(() -> addPattern(getIncludes(), EXAMPLE_PATTERN, "Add include")); //$NON-NLS-1$
		includesTable.setRemoveHandler(() -> removePattern(includesTable, getIncludes(), "Remove include")); //$NON-NLS-1$

		this.excludesTable = new TableControl(control, toolkit, Messages.ManifestEditor_Excludes);
		excludesTable.setAddHandler(() -> addPattern(getExcludes(), EXAMPLE_PATTERN, "Add exclude")); //$NON-NLS-1$
		excludesTable.setRemoveHandler(() -> removePattern(includesTable, getExcludes(), "Remove exclude")); //$NON-NLS-1$

		createColumns();
	}

	private void createColumns() {
		final var includeCol = includesTable.createColumn(Messages.ManifestEditor_Pattern, 100);
		includeCol.setLabelProvider(TableControl.createLabelProvider(LibraryElement.class, LibraryElement::getValue));

		includeCol.setEditingSupport(TableControl.createEditingSupport(includesTable.getViewer(), page,
				LibraryElement.class, LibraryElement::getValue, getSetOperationFactory(),
				new TextCellEditor(includesTable.getViewer().getTable())));

		final var excludeCol = excludesTable.createColumn(Messages.ManifestEditor_Pattern, 100);
		excludeCol.setLabelProvider(TableControl.createLabelProvider(LibraryElement.class, LibraryElement::getValue));

		excludeCol.setEditingSupport(TableControl.createEditingSupport(excludesTable.getViewer(), page,
				LibraryElement.class, LibraryElement::getValue, getSetOperationFactory(),
				new TextCellEditor(excludesTable.getViewer().getTable())));

	}

	public Control getControl() {
		return control;
	}

	public void setInput(final Library library) {
		this.library = library;
		refresh();
	}

	public void refresh() {
		excludesTable.setInput(library != null ? library.getExcludes().getLibraryElement() : List.of());
		excludesTable.setEnabled(library != null);

		includesTable.setInput(library != null ? library.getIncludes().getLibraryElement() : List.of());
		includesTable.setEnabled(library != null);
	}

	private EList<LibraryElement> getIncludes() {
		return library != null ? library.getIncludes().getLibraryElement() : ECollections.emptyEList();
	}

	private EList<LibraryElement> getExcludes() {
		return library != null ? library.getExcludes().getLibraryElement() : ECollections.emptyEList();
	}

	private void addPattern(final EList<LibraryElement> elements, final String value, final String label) {
		page.execute(new AddElementOperation<>(label, elements, ManifestHelper.createPattern(value), this::refresh));
	}

	private void removePattern(final TableControl table, final List<LibraryElement> elements, final String label) {
		final Object selected = table.getViewer().getStructuredSelection().getFirstElement();
		if (selected instanceof final LibraryElement element) {
			page.execute(new RemoveElementOperation<>(label, elements, element, this::refresh));
		}
	}

	private BiFunction<LibraryElement, String, IUndoableOperation> getSetOperationFactory() {
		return (libraryElement, value) -> new SetValueOperation<>("Set pattern", libraryElement::getValue, //$NON-NLS-1$
				libraryElement::setValue, value, () -> includesTable.getViewer().refresh(libraryElement));
	}

}
