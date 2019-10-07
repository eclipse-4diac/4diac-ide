/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added mulitline selection and code cleanup.
 *   Bianca Wiesmayr - extract Table creation
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.Arrays;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.InternalVarsLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InternalVarsSection extends ECCSection {
	private static final String IV_NAME = "NAME"; //$NON-NLS-1$
	private static final String IV_TYPE = "TYPE"; //$NON-NLS-1$
	private static final String IV_ARRAY = "ARRAY_SIZE"; //$NON-NLS-1$
	private static final String IV_INIT = "INITIAL_VALUE"; //$NON-NLS-1$
	private static final String IV_COMMENT = "COMMENT"; //$NON-NLS-1$

	private TableViewer internalVarsViewer;
	private ComboBoxCellEditor typeDropDown;
	private String[] dataTypes = new String[DataTypeLibrary.getInstance().getDataTypesSorted().size()];

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalVarsControls(parent);
	}

	public void createInternalVarsControls(final Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(composite, getWidgetFactory());

		internalVarsViewer = TableWidgetFactory.createTableViewer(composite);
		configureTableLayout(internalVarsViewer.getTable());

		for (int i = 0; i < DataTypeLibrary.getInstance().getDataTypesSorted().size(); i++) {
			dataTypes[i] = ((DataType) DataTypeLibrary.getInstance().getDataTypesSorted().toArray()[i]).getName();
		}

		internalVarsViewer.setCellEditors(createCellEditors(internalVarsViewer.getTable()));
		internalVarsViewer.setColumnProperties(new String[] { IV_NAME, IV_TYPE, IV_ARRAY, IV_INIT, IV_COMMENT });
		internalVarsViewer.setContentProvider(new ArrayContentProvider());
		internalVarsViewer.setLabelProvider(new InternalVarsLabelProvider());
		internalVarsViewer.setCellModifier(new InternalVarsCellModifier());

		buttons.bindToTableViewer(internalVarsViewer, this, ref -> new CreateInternalVariableCommand(getType()),
				ref -> new DeleteInternalVariableCommand(getType(), (VarDeclaration) ref));
	}

	private static void configureTableLayout(final Table table) {
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText("Name");
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText("Type");
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText("Array Size");
		TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText("Initial Value");
		TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText("Comment");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 30));
		layout.addColumnData(new ColumnWeightData(2, 30));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(3, 50));
		table.setLayout(layout);
	}

	private CellEditor[] createCellEditors(final Table table) {
		TextCellEditor varNameEditor = new TextCellEditor(table);
		((Text) varNameEditor.getControl()).addVerifyListener(new IdentifierVerifyListener());
		typeDropDown = ComboBoxWidgetFactory.createComboBoxCellEditor(table, dataTypes, SWT.READ_ONLY);
		return new CellEditor[] { varNameEditor, typeDropDown, new TextCellEditor(table), new TextCellEditor(table),
				new TextCellEditor(table) };
	}

	@Override
	protected void setInputCode() {
		internalVarsViewer.setCellModifier(null);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			internalVarsViewer.setInput(getType().getInternalVars());
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {
		// for now nothing to be done here
	}

	private final class InternalVarsCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			VarDeclaration var = (VarDeclaration) element;
			switch (property) {
			case IV_NAME:
				return var.getName();
			case IV_TYPE: // return index of selected element in array
				return Arrays.asList(typeDropDown.getItems()).indexOf(var.getType().getName());
			case IV_COMMENT:
				return var.getComment();
			case IV_ARRAY:
				return Integer.toString(var.getArraySize());
			default:
				return var.getValue().getValue();
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			VarDeclaration data = (VarDeclaration) tableItem.getData();
			Command cmd = null;
			switch (property) {
			case IV_NAME:
				cmd = new ChangeNameCommand(data, value.toString());
				break;
			case IV_TYPE:
				cmd = new ChangeTypeCommand(data, DataTypeLibrary.getInstance().getType(dataTypes[(int) value]));
				break;
			case IV_COMMENT:
				cmd = new ChangeCommentCommand(data, value.toString());
				break;
			case IV_ARRAY:
				cmd = new ChangeArraySizeCommand(data, value.toString());
				break;
			default:
				cmd = new ChangeInitialValueCommand(data, value.toString());
				break;
			}

			executeCommand(cmd);
			internalVarsViewer.refresh(data);
		}
	}

}
