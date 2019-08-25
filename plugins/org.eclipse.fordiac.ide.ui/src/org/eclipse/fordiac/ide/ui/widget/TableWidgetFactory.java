/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - initial implementation
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal
 *     - added style parameter to the createtableViewer method
 *   Alois Zoitl
 *     - changed editor activation behavior to double click and added tab through
 *       cells behavior
 *     - extracted helper for ComboCellEditors that unfold on activation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public final class TableWidgetFactory {

	public static TableViewer createTableViewer(final Composite parent) {
		return createTableViewer(parent, 0);
	}

	public static TableViewer createTableViewer(final Composite parent, int style) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.heightHint = 150;
		gridData.widthHint = 80;

		return tableViewer;
	}

	public static TableViewer createPropertyTableViewer(final Composite parent) {
		return createPropertyTableViewer(parent, 0);
	}

	public static TableViewer createPropertyTableViewer(final Composite parent, int style) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.minimumHeight = 80;
		gridData.heightHint = 4;
		gridData.widthHint = 400;

		return tableViewer;
	}

	/**
	 * Create a Combobox Cell editor for a table
	 *
	 * This helper method will apply certain style options so that on activation the
	 * combo box is always unfolded making the usage more intuitive and quicker.
	 *
	 * @param table the table this cell editor will be used in
	 * @param items the items shown in the combo box
	 * @param style the style of the combobox cell editor
	 * @return the newly created combobox cell editor
	 */
	public static ComboBoxCellEditor createComboBoxCellEditor(Table table, String[] items, int style) {
		ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(table, items, style);
		cellEditor.setActivationStyle(
				ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION | ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);
		return cellEditor;
	}

	public static ComboBoxCellEditor createComboBoxCellEditor(Table table, String[] items) {
		return createComboBoxCellEditor(table, items, SWT.NONE);
	}

	private static TableViewer createGenericTableViewer(GridData gridData, final Composite parent, int style) {
		TableViewer tableViewer = new TableViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | style);

		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};

		TableViewerEditor.create(tableViewer, actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getControl().setLayoutData(gridData);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		return tableViewer;
	}

	private TableWidgetFactory() {
		throw new UnsupportedOperationException("Widget Factory should not be instantiated"); //$NON-NLS-1$
	}
}
