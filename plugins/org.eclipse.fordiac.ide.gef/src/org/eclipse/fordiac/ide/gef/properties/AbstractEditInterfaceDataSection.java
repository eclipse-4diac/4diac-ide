/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
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
 *   Daniel Lindhuber - added addEntry method & type search field
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeDropdown;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CustomTextCellEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public abstract class AbstractEditInterfaceDataSection extends AbstractEditInterfaceSection {

	protected static final String ARRAY_SIZE = "arraysize"; //$NON-NLS-1$
	protected static final String INITIAL_VALUE = "initialvalue"; //$NON-NLS-1$
	protected static final int ARRAYSIZE_WIDTH = 100;
	protected static final int INITIALVALUE_WIDTH = 100;
	private DataTypeDropdown typeDropdown;

	@Override
	protected CellEditor createTypeCellEditor(final TableViewer viewer) {
		typeDropdown = new DataTypeDropdown(() -> getDataTypeLib().getDataTypesSorted(), viewer);
		return typeDropdown;
	}

	@Override
	protected Object getTypeValue(final Object element, final TableViewer viewer, final int TYPE_COLUMN_INDEX) {
		final VarDeclaration variable = (VarDeclaration) element;
		return variable.getTypeName();
	}

	@Override
	protected Command createChangeDataTypeCommand(final VarDeclaration data, final Object value, final TableViewer viewer) {
		if (value instanceof String) {
			final DataType dataType = typeDropdown.getType((String) value);
			return (dataType == null) ? null : newChangeTypeCommand(data, dataType);
		}
		return null;
	}

	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new DataInterfaceContentProvider(false);
	}

	@Override
	protected IContentProvider getInputsContentProvider() {
		return new DataInterfaceContentProvider(true);
	}

	@Override
	protected String[] fillTypeCombo() {
		final List<String> list = new ArrayList<>();
		for (final DataType dataType : getDataTypeLib().getDataTypesSorted()) {
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}

	protected DataType getLastUsedDataType(final InterfaceList interfaceList, final boolean isInput,
			final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		final EList<VarDeclaration> dataList = getDataList(interfaceList, isInput);
		if (!dataList.isEmpty()) {
			return dataList.get(dataList.size() - 1).getType();
		}
		return IecTypes.ElementaryTypes.BOOL; // bool is default
	}

	@Override
	protected int getInsertingIndex(final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getDataList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<VarDeclaration> getDataList(final InterfaceList interfaceList, final boolean isInput) {
		return isInput ? interfaceList.getInputVars() : interfaceList.getOutputVars();
	}

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		if ((entry instanceof VarDeclaration) && !(entry instanceof AdapterDeclaration)) {
			cmd.add(newInsertCommand((IInterfaceElement) entry, isInputsViewer(), index));
		}
	}

	@Override
	protected TableLayout createTableLayout(final Table table) {
		final TableLayout layout = super.createTableLayout(table);
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.InitialValue);
		final TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText(FordiacMessages.ArraySize);

		layout.addColumnData(new ColumnPixelData(INITIALVALUE_WIDTH));
		layout.addColumnData(new ColumnPixelData(ARRAYSIZE_WIDTH));

		return layout;
	}

	@Override
	protected LabelProvider getLabelProvider() {
		return new DataLabelProvider();
	}

	@Override
	protected InterfaceCellModifier getCellModifier(final TableViewer viewer) {
		return new DataInterfaceCellModifier(viewer);
	}

	@Override
	protected void setCellEditors(final TableViewer viewer) {
		super.setCellEditors(viewer);
		final CellEditor[] nameTypeCommentEditors = viewer.getCellEditors();
		viewer.setCellEditors(
				new CellEditor[] { nameTypeCommentEditors[0], nameTypeCommentEditors[1], nameTypeCommentEditors[2],
						new CustomTextCellEditor(viewer.getTable()), new CustomTextCellEditor(viewer.getTable()) });
	}

	@Override
	protected String[] getColumnProperties() {
		final String[] nameTypeComment = super.getColumnProperties();
		return new String[] { nameTypeComment[0], nameTypeComment[1], nameTypeComment[2], INITIAL_VALUE, ARRAY_SIZE };
	}

	protected class DataInterfaceCellModifier extends InterfaceCellModifier {

		public DataInterfaceCellModifier(final TableViewer viewer) {
			super(viewer);
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			if (INITIAL_VALUE.equals(property)) {
				return true;
			}
			if (ARRAY_SIZE.equals(property)) {
				return true;
			}
			return super.canModify(element, property);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case ARRAY_SIZE:
				final int arraySize = ((VarDeclaration) element).getArraySize();
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
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Object data = tableItem.getData();
			Command cmd = null;

			switch (property) {
			case ARRAY_SIZE:
				cmd = new ChangeArraySizeCommand((VarDeclaration) data, (String) value);
				break;
			case INITIAL_VALUE:
				cmd = new ChangeValueCommand((VarDeclaration) data, (String) value);
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

	protected static class DataInterfaceContentProvider extends InterfaceContentProvider {
		public DataInterfaceContentProvider(final boolean inputs) {
			super(inputs);
		}

		@Override
		protected Object[] getInputs(final Object inputElement) {
			final InterfaceList interfaceList = getInterfaceListFromInput(inputElement);
			if (null != interfaceList) {
				return interfaceList.getInputVars().toArray();
			}
			return new Object[0];
		}

		@Override
		protected Object[] getOutputs(final Object inputElement) {
			final InterfaceList interfaceList = getInterfaceListFromInput(inputElement);
			if (null != interfaceList) {
				return interfaceList.getOutputVars().toArray();
			}
			return new Object[0];
		}
	}
}
