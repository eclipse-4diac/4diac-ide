/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr - extract Table creation, improve insertion
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *   Daniel Lindhuber - added copy and paste
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.InternalVarsLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
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

public class InternalVarsSection extends ECCSection implements I4diacTableUtil {
	private static final String IV_NAME = "NAME"; //$NON-NLS-1$
	private static final String IV_TYPE = "TYPE"; //$NON-NLS-1$
	private static final String IV_ARRAY = "ARRAY_SIZE"; //$NON-NLS-1$
	private static final String IV_INIT = "INITIAL_VALUE"; //$NON-NLS-1$
	private static final String IV_COMMENT = "COMMENT"; //$NON-NLS-1$

	private TableViewer internalVarsViewer;
	private ComboBoxCellEditor typeDropDown;
	private DataTypeLibrary dataLib;
	private String[] dataTypes;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalVarsControls(parent);
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}

	public void createInternalVarsControls(final Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(composite, getWidgetFactory());

		internalVarsViewer = TableWidgetFactory.createTableViewer(composite);
		configureTableLayout(internalVarsViewer.getTable());

		internalVarsViewer.setColumnProperties(new String[] { IV_NAME, IV_TYPE, IV_ARRAY, IV_INIT, IV_COMMENT });
		internalVarsViewer.setContentProvider(new ArrayContentProvider());
		internalVarsViewer.setLabelProvider(new InternalVarsLabelProvider());
		internalVarsViewer.setCellModifier(new InternalVarsCellModifier());

		buttons.bindToTableViewer(internalVarsViewer, this,
				ref -> new CreateInternalVariableCommand(getType(), getInsertionIndex(), getName(), getDataType()),
				ref -> new DeleteInternalVariableCommand(getType(), (VarDeclaration) ref));
	}

	private DataType getDataType() {
		return (null != getLastSelectedVariable()) ? getLastSelectedVariable().getType() : null;
	}

	private String getName() {
		return (null != getLastSelectedVariable()) ? getLastSelectedVariable().getName() : null;
	}

	private int getInsertionIndex() {
		VarDeclaration alg = getLastSelectedVariable();
		if (null == alg) {
			return getType().getInternalVars().size();
		}
		return getType().getInternalVars().indexOf(alg) + 1;
	}

	private VarDeclaration getLastSelectedVariable() {
		IStructuredSelection selection = internalVarsViewer.getStructuredSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return (VarDeclaration) selection.toList().get(selection.toList().size() - 1);
	}

	private static void configureTableLayout(final Table table) {
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(Messages.InternalVarsSection_ConfigureTableLayout_Name);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(Messages.InternalVarsSection_ConfigureTableLayout_Type);
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(Messages.InternalVarsSection_ConfigureTableLayout_ArraySize);
		TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(Messages.InternalVarsSection_ConfigureTableLayout_InitialValue);
		TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText(Messages.InternalVarsSection_ConfigureTableLayout_Comment);
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
		dataLib = getType().getTypeLibrary().getDataTypeLibrary();
		dataTypes = dataLib.getDataTypesSorted().stream().map(DataType::getName).collect(Collectors.toList())
				.toArray(new String[0]);
		internalVarsViewer.setCellEditors(createCellEditors(internalVarsViewer.getTable()));
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
				cmd = new ChangeTypeCommand(data, dataLib.getType(dataTypes[(int) value]));
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

	@Override
	public TableViewer getViewer() {
		return internalVarsViewer;
	}

	public Object getEntry(int index) {
		return getType().getInternalVars().get(index);
	}

	@Override
	public void addEntry(Object entry, int index) {
		if (entry instanceof VarDeclaration) {
			VarDeclaration varEntry = (VarDeclaration) entry;
			Command cmd = new CreateInternalVariableCommand(getType(), index, varEntry.getName(), varEntry.getType());
			executeCommand(cmd);
			getViewer().refresh();
		}
	}

	@Override
	public Object removeEntry(int index) {
		VarDeclaration entry = (VarDeclaration) getEntry(index);
		Command cmd = new DeleteInternalVariableCommand(getType(), entry);
		executeCommand(cmd);
		getViewer().refresh();
		return entry;
	}
}
