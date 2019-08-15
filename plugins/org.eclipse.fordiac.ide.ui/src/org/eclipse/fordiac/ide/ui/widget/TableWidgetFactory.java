/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - initial implementation
 * Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - added style parameter to the
 * 				 createtableViewer method
 * Alois Zoitl - changed editor activation behavior to double click and added tab
 *               through cells behavior
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
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
