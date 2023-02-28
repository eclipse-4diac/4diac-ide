/*******************************************************************************
 * Copyright (c) 2015, 2022 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - allowed multiline selection in algorithm list
 *   Bianca Wiesmayr, Virendra Ashiwal -
 *   	- Created tableViewer as new WidgetFactory
 *   	- Shifted Grid heightHint and Width Hint to WidgetFactory.java
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *   Bianca Wiesmayr - improve element insertion
 *   Daniel Lindhuber - added copy and paste
 *   Alexander Lumplecker - allowed to reorder algorithms
 *   Alois Zoitl - turned AlgorithmList into a read only table
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.AlgorithmsLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class AlgorithmList {

	private static final String A_NAME = "Name"; //$NON-NLS-1$
	private static final String A_LANGUAGE = "Language"; //$NON-NLS-1$
	private static final String A_COMMENT = "Comment"; //$NON-NLS-1$

	private TableViewer algorithmViewer;
	private final Composite composite;

	private BasicFBType type;

	public AlgorithmList(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		composite = widgetFactory.createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		final GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		composite.setLayoutData(gridDataVersionViewer);

		createAlgorithmViewer(composite);
	}

	Composite getComposite() {
		return composite;
	}

	public void initialize(final BasicFBType type) {
		this.type = type;
	}

	private void createAlgorithmViewer(final Composite parent) {
		algorithmViewer = TableWidgetFactory.createTableViewer(parent);
		configureTableLayout(algorithmViewer);
		algorithmViewer.setColumnProperties(new String[] { A_NAME, A_LANGUAGE, A_COMMENT });
		algorithmViewer.setContentProvider(new ArrayContentProvider());
		algorithmViewer.setLabelProvider(new AlgorithmsLabelProvider());
	}

	private static void configureTableLayout(final TableViewer tableViewer) {
		final Table table = tableViewer.getTable();
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(Messages.AlgorithmList_ConfigureTableLayout_Name);
		final TableColumn column2 = new TableColumn(table, SWT.CENTER);
		column2.setText(Messages.AlgorithmList_ConfigureTableLayout_Language);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(Messages.AlgorithmList_ConfigureTableLayout_Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 50));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(3, 50));
		table.setLayout(layout);
	}


	public void refresh() {
		if (null != type) {
			algorithmViewer.setInput(type.getAlgorithm());
		}
	}

	public TableViewer getViewer() {
		return algorithmViewer;
	}

	public BasicFBType getType() {
		return type;
	}
}
