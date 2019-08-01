/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 	
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - initial implementation
 * Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - added style parameter to the createtableViewer method
 * 
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.TableViewer;
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

	public static TableViewer createPropertyTableViewer(final Composite parent, int style) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);
		
		gridData.minimumHeight = 80;
		gridData.heightHint = 4;
		gridData.widthHint = 400;
		
		return tableViewer;
	}

	private static TableViewer createGenericTableViewer(GridData gridData,
			final Composite parent, int style) {
		TableViewer tableViewer = new TableViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | style);
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
