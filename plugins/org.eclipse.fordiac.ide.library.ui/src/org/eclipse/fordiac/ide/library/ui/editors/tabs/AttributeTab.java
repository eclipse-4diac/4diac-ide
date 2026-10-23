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
import org.eclipse.fordiac.ide.library.model.library.Attribute;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorPage;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.viewers.TableViewerColumn;
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
		refresh();
	}

	public void refresh() {
		attributeTable.setInput(library != null ? library.getAttribute() : List.of());
		attributeTable.setEnabled(library != null);
	}

	private void createColumns() {
		final TableViewerColumn nameColumn = attributeTable.createColumn(Messages.ManifestEditor_Name, 30);
		nameColumn.setLabelProvider(TableControl.createLabelProvider(Attribute.class, Attribute::getName));
		nameColumn.setEditingSupport(
				TableControl.createEditingSupport(attributeTable.getViewer(), page, Attribute.class, Attribute::getName,
						this::createModifyNameOperation, new TextCellEditor(attributeTable.getViewer().getTable())));

		final TableViewerColumn typeColumn = attributeTable.createColumn(Messages.ManifestEditor_Type, 20);
		typeColumn.setLabelProvider(TableControl.createLabelProvider(Attribute.class, Attribute::getType));
		typeColumn.setEditingSupport(
				TableControl.createEditingSupport(attributeTable.getViewer(), page, Attribute.class, Attribute::getType,
						this::createModifyTypeOperation, new TextCellEditor(attributeTable.getViewer().getTable())));

		final TableViewerColumn valueColumn = attributeTable.createColumn(Messages.ManifestEditor_Value, 20);
		valueColumn.setLabelProvider(TableControl.createLabelProvider(Attribute.class, Attribute::getValue));
		valueColumn.setEditingSupport(TableControl.createEditingSupport(attributeTable.getViewer(), page,
				Attribute.class, Attribute::getValue, this::createModifyValueOperation,
				new TextCellEditor(attributeTable.getViewer().getTable())));

		final TableViewerColumn commentColumn = attributeTable.createColumn(Messages.ManifestEditor_Comment, 40);
		commentColumn.setLabelProvider(TableControl.createLabelProvider(Attribute.class, Attribute::getComment));
		commentColumn.setEditingSupport(TableControl.createEditingSupport(attributeTable.getViewer(), page,
				Attribute.class, Attribute::getComment, this::createModifyCommentOperation,
				new TextCellEditor(attributeTable.getViewer().getTable())));
	}

	private EList<Attribute> getAttributes() {
		return library != null ? library.getAttribute() : ECollections.emptyEList();
	}

	private void addAttribute() {
		page.execute(new AddElementOperation<>("Add attribute", //$NON-NLS-1$
				getAttributes(), ManifestHelper.createAttribute("attribute1"), //$NON-NLS-1$
				this::refresh));
	}

	private void removeAttribute() {
		final Object selected = attributeTable.getViewer().getStructuredSelection().getFirstElement();

		if (selected instanceof final Attribute attribute) {
			page.execute(new RemoveElementOperation<>("Remove attribute", //$NON-NLS-1$
					getAttributes(), attribute, this::refresh));
		}
	}

	private IUndoableOperation createModifyNameOperation(final Attribute attribute, final String value) {
		return new SetValueOperation<>("Set attribute name", attribute::getName, attribute::setName, value, //$NON-NLS-1$
				this::refresh);
	}

	private IUndoableOperation createModifyTypeOperation(final Attribute attribute, final String value) {
		return new SetValueOperation<>("Set attribute type", attribute::getType, attribute::setType, value, //$NON-NLS-1$
				this::refresh);
	}

	private IUndoableOperation createModifyValueOperation(final Attribute attribute, final String value) {
		return new SetValueOperation<>("Set attribute value", attribute::getValue, attribute::setValue, value, //$NON-NLS-1$
				this::refresh);
	}

	private IUndoableOperation createModifyCommentOperation(final Attribute attribute, final String value) {
		return new SetValueOperation<>("Set attribute comment", attribute::getComment, attribute::setComment, value, //$NON-NLS-1$
				this::refresh);
	}
}
