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

import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.library.Attribute;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorPage;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AttributeTab {

	private final TableControl attributeTable;
	private final ManifestEditorPage<Exports> page;

	private Library library;

	public AttributeTab(final Composite parent, final FormToolkit toolkit, final ManifestEditorPage<Exports> page) {
		this.page = page;

		attributeTable = new TableControl(parent, toolkit, ""); //$NON-NLS-1$
		attributeTable.setAddHandler(this::addAttribute);
		attributeTable.setRemoveHandler(this::removeAttribute);

		createColumns();
	}

	public Control getControl() {
		return attributeTable.getControl();
	}

	public void setInput(final Library library) {
		this.library = library;
		attributeTable.setInput(getAttributes());
		attributeTable.setEnabled(library != null);
	}

	public void refresh() {
		attributeTable.getViewer().refresh();
	}

	private void createColumns() {
		createColumn(Messages.ManifestEditor_Name, 30, Attribute::getName,
				(attribute, value) -> new SetValueOperation<>("Set attribute name", //$NON-NLS-1$
						attribute::getName, attribute::setName, value));

		createColumn(Messages.ManifestEditor_Type, 20, Attribute::getType,
				(attribute, value) -> new SetValueOperation<>("Set attribute type", //$NON-NLS-1$
						attribute::getType, attribute::setType, value));

		createColumn(Messages.ManifestEditor_Value, 20, Attribute::getValue,
				(attribute, value) -> new SetValueOperation<>("Set attribute value", //$NON-NLS-1$
						attribute::getValue, attribute::setValue, value));

		createColumn(Messages.ManifestEditor_Comment, 40, Attribute::getComment,
				(attribute, value) -> new SetValueOperation<>("Set attribute comment", //$NON-NLS-1$
						attribute::getComment, attribute::setComment, value));
	}

	private void createColumn(final String title, final int weight, final Function<Attribute, String> getter,
			final BiFunction<Attribute, String, IUndoableOperation> operationFactory) {

		final var column = attributeTable.createColumn(title, weight);

		column.setLabelProvider(TableControl.createLabelProvider(Attribute.class, getter,
				attribute -> ManifestEditorPage.targets(page.getManifestEditor().getValidationResult(), attribute)));

		column.setEditingSupport(TableControl.createEditingSupport(attributeTable.getViewer(), page, Attribute.class,
				getter, operationFactory, new TextCellEditor(attributeTable.getViewer().getTable())));
	}

	private EList<Attribute> getAttributes() {
		return library != null ? library.getAttribute() : ECollections.emptyEList();
	}

	private void addAttribute() {
		if (library == null) {
			return;
		}

		page.execute(new AddElementOperation<>("Add attribute", //$NON-NLS-1$
				getAttributes(), ManifestHelper.createAttribute("attribute1"))); //$NON-NLS-1$
	}

	private void removeAttribute() {
		final Object selected = attributeTable.getViewer().getStructuredSelection().getFirstElement();

		if (selected instanceof final Attribute attribute) {
			page.execute(new RemoveElementOperation<>("Remove attribute", //$NON-NLS-1$
					getAttributes(), attribute));
		}
	}
}
