/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - create command now has enhanced guess, added columns
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CustomTextCellEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public abstract class AbstractEditInterfaceDataSection extends AbstractEditInterfaceSection {

	protected static final String ARRAY_SIZE = "arraysize"; //$NON-NLS-1$
	protected static final String INITIAL_VALUE = "initialvalue"; //$NON-NLS-1$
	protected static final int ARRAYSIZE_WIDTH = 100;
	protected static final int INITIALVALUE_WIDTH = 100;

	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new InterfaceContentProvider(false, InterfaceContentProviderType.DATA);
	}

	@Override
	protected IContentProvider getInputsContentProvider() {
		return new InterfaceContentProvider(true, InterfaceContentProviderType.DATA);
	}

	@Override
	protected String[] fillTypeCombo() {
		List<String> list = new ArrayList<>();
		for (DataType dataType : getDataTypeLib().getDataTypesSorted()) {
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}

	protected DataType getLastUsedDataType(InterfaceList interfaceList, boolean isInput,
			IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		EList<VarDeclaration> dataList = getDataList(interfaceList, isInput);
		if (!dataList.isEmpty()) {
			return dataList.get(dataList.size() - 1).getType();
		}
		return getDataTypeLib().getType("bool");//$NON-NLS-1$ // bool is default
	}

	@Override
	protected int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput) {
		if (null != interfaceElement) {
			InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getDataList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<VarDeclaration> getDataList(InterfaceList interfaceList, boolean isInput) {
		return isInput ? interfaceList.getInputVars() : interfaceList.getOutputVars();
	}

	@Override
	public void addEntry(Object entry, int index, CompoundCommand cmd) {
		// can not use instanceof since AdapterImplementation is derived from
		// VarDeclaration and this would break the addEntry method in the adapter
		// section
		if (entry.getClass().equals(VarDeclarationImpl.class)) {
			cmd.add(newInsertCommand((IInterfaceElement) entry, getIsInputsViewer(), index));
		}
	}

	@Override
	protected TableLayout createTableLayout(Table table) {
		TableLayout layout = super.createTableLayout(table);
		TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.InitialValue);
		TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText(FordiacMessages.ArraySize);

		layout.addColumnData(new ColumnPixelData(INITIALVALUE_WIDTH));
		layout.addColumnData(new ColumnPixelData(ARRAYSIZE_WIDTH));

		return layout;
	}

	@Override
	protected InterfaceLabelProvider getLabelProvider() {
		return new DataInterfaceLabelProvider();
	}

	@Override
	protected InterfaceCellModifier getCellModifier(TableViewer viewer) {
		return new DataInterfaceCellModifier(viewer);
	}

	@Override
	protected void setCellEditors(TableViewer viewer) {
		super.setCellEditors(viewer);
		CellEditor[] nameTypeCommentEditors = viewer.getCellEditors();
		viewer.setCellEditors(
				new CellEditor[] { nameTypeCommentEditors[0], nameTypeCommentEditors[1], nameTypeCommentEditors[2],
						new CustomTextCellEditor(viewer.getTable()), new CustomTextCellEditor(viewer.getTable()) });
	}

	@Override
	protected String[] getColumnProperties() {
		String[] nameTypeComment = super.getColumnProperties();
		return new String[] { nameTypeComment[0], nameTypeComment[1], nameTypeComment[2], INITIAL_VALUE, ARRAY_SIZE };
	}

	protected static class DataInterfaceLabelProvider extends InterfaceLabelProvider implements ITableColorProvider {
		protected static final int INITIALVALUE_COLUMN = 3;
		protected static final int ARRAYSIZE_COLUMN = 4;

		@Override
		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
			case INITIALVALUE_COLUMN:
				if (((VarDeclaration) element).getValue() == null) {
					return ""; //$NON-NLS-1$
				}
				return ((VarDeclaration) element).getValue().getValue();
			case ARRAYSIZE_COLUMN:
				int arraySize = ((VarDeclaration) element).getArraySize();
				return (arraySize <= 0) ? "" : String.valueOf(arraySize); //$NON-NLS-1$
			default:
				return super.getColumnText(element, columnIndex);
			}
		}

		@Override
		public Color getBackground(Object element, int columnIndex) {
			return null;
		}

		@Override
		public Color getForeground(Object element, int columnIndex) {
			return null;
		}
	}

	protected class DataInterfaceCellModifier extends InterfaceCellModifier {

		public DataInterfaceCellModifier(TableViewer viewer) {
			super(viewer);
		}

		@Override
		public boolean canModify(Object element, String property) {
			if (INITIAL_VALUE.equals(property)) {
				return true;
			}
			if (ARRAY_SIZE.equals(property)) {
				return true;
			}
			return super.canModify(element, property);
		}

		@Override
		public Object getValue(Object element, String property) {
			switch (property) {
			case ARRAY_SIZE:
				int arraySize = ((VarDeclaration) element).getArraySize();
				return (arraySize <= 0) ? "" : String.valueOf(arraySize); //$NON-NLS-1$
			case INITIAL_VALUE:
				if (((VarDeclaration) element).getValue() == null) {
					return ""; //$NON-NLS-1$
				}
				return ((VarDeclaration) element).getValue().getValue();
			default:
				return super.getValue(element, property);
			}
		}

		@Override
		public void modify(Object element, String property, Object value) {
			TableItem tableItem = (TableItem) element;
			Object data = tableItem.getData();
			Command cmd = null;

			switch (property) {
			case ARRAY_SIZE:
				cmd = new ChangeArraySizeCommand((VarDeclaration) data, (String) value);
				break;
			case INITIAL_VALUE:
				cmd = new ChangeInitialValueCommand((VarDeclaration) data, (String) value);
				break;
			default:
				super.modify(element, property, value);
			}

			if (null != cmd) {
				executeCommand(cmd);
				viewer.refresh(data);
			}
		}
	}
}
