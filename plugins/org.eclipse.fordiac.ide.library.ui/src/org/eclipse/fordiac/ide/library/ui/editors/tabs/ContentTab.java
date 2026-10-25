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

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorLibraryPage;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorPage;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ContentTab {

	private static final String EXAMPLE_PATTERN = "some::example::pattern::*"; //$NON-NLS-1$

	private final ManifestEditorLibraryPage page;
	private final Composite control;
	private final TableControl includesTable;
	private final TableControl excludesTable;

	private Library library;

	public ContentTab(final Composite parent, final FormToolkit toolkit, final ManifestEditorLibraryPage page) {
		this.page = page;

		control = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(control);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(control);

		includesTable = createPatternTable(toolkit, Messages.ManifestEditor_Includes, this::getIncludes, "Add include", //$NON-NLS-1$
				"Remove include"); //$NON-NLS-1$

		excludesTable = createPatternTable(toolkit, Messages.ManifestEditor_Excludes, this::getExcludes, "Add exclude", //$NON-NLS-1$
				"Remove exclude"); //$NON-NLS-1$
	}

	private TableControl createPatternTable(final FormToolkit toolkit, final String title,
			final java.util.function.Supplier<EList<LibraryElement>> elements, final String addLabel,
			final String removeLabel) {

		final TableControl table = new TableControl(control, toolkit, title);

		table.setAddHandler(() -> addPattern(elements.get(), addLabel));
		table.setRemoveHandler(() -> removePattern(table, elements.get(), removeLabel));

		createPatternColumn(table);

		return table;
	}

	private void createPatternColumn(final TableControl table) {
		final var column = table.createColumn(Messages.ManifestEditor_Pattern, 100);

		column.setLabelProvider(TableControl.createLabelProvider(LibraryElement.class, LibraryElement::getValue,
				element -> ManifestEditorPage.targets(page.getManifestEditor().getValidationResult(), element)));

		column.setEditingSupport(TableControl.createEditingSupport(table.getViewer(), page, LibraryElement.class,
				LibraryElement::getValue, ContentTab::createSetOperation,
				new TextCellEditor(table.getViewer().getTable())));
	}

	public Control getControl() {
		return control;
	}

	public void setInput(final Library library) {
		this.library = library;

		includesTable.setInput(getIncludes());
		excludesTable.setInput(getExcludes());

		includesTable.setEnabled(library != null);
		excludesTable.setEnabled(library != null);
	}

	public void refresh() {
		includesTable.getViewer().refresh();
		excludesTable.getViewer().refresh();
	}

	private EList<LibraryElement> getIncludes() {
		return library != null && library.getIncludes() != null ? library.getIncludes().getLibraryElement()
				: ECollections.emptyEList();
	}

	private EList<LibraryElement> getExcludes() {
		return library != null && library.getExcludes() != null ? library.getExcludes().getLibraryElement()
				: ECollections.emptyEList();
	}

	private void addPattern(final EList<LibraryElement> elements, final String label) {
		if (library == null) {
			return;
		}

		page.execute(new AddElementOperation<>(label, elements, ManifestHelper.createPattern(EXAMPLE_PATTERN)));
	}

	private void removePattern(final TableControl table, final List<LibraryElement> elements, final String label) {
		final Object selection = table.getViewer().getStructuredSelection().getFirstElement();

		if (selection instanceof final LibraryElement element) {
			page.execute(new RemoveElementOperation<>(label, elements, element));
		}
	}

	private static IUndoableOperation createSetOperation(final LibraryElement element, final String value) {
		return new SetValueOperation<>("Set pattern", //$NON-NLS-1$
				element::getValue, element::setValue, value);
	}
}
