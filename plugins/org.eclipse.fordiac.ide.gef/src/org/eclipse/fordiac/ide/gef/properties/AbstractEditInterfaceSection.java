/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * 				 2018, 2019, 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl - initial implementation
 *   Alois Zoitl - moved group buttons to the top left, multi-line selection in lists,
 *               code clean-up
 *   Bianca Wiesmayr - extract table creation
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *               - cleaned command stack handling for property sections
 *   Daniel Lindhuber - added copy/paste and the context menu
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.CustomTextCellEditor;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceSection extends AbstractSection implements I4diacTableUtil {
	private static final int TYPE_AND_COMMENT_COLUMN_WIDTH = 100;
	private static final int NAME_COLUMN_WIDTH = 200;
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String TYPE = "type"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private TableViewer inputsViewer;
	private TableViewer outputsViewer;
	public boolean isInputsViewer;

	protected enum InterfaceContentProviderType {
		EVENT, DATA, ADAPTER
	}

	protected abstract CreateInterfaceElementCommand newCreateCommand(IInterfaceElement selection, boolean isInput);

	protected abstract InsertInterfaceElementCommand newInsertCommand(IInterfaceElement selection, boolean isInput,
			int index);

	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);

	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp);

	protected abstract String[] fillTypeCombo();

	protected abstract TypeLibrary getTypeLibrary();

	@Override
	protected abstract INamedElement getInputType(Object input);

	@SuppressWarnings("static-method") // this method allows sub-classes to provide own change type commands, e.g.,
	// subapps
	protected ChangeTypeCommand newChangeTypeCommand(VarDeclaration data, DataType newType) {
		return new ChangeTypeCommand(data, newType);
	}

	public TableViewer getInputsViewer() {
		return inputsViewer;
	}

	public TableViewer getOutputsViewer() {
		return outputsViewer;
	}

	public boolean getIsInputsViewer() {
		return isInputsViewer;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createInputEdit(parent);
		createOutputEdit(parent);

		inputsViewer.setContentProvider(getInputsContentProvider());
		outputsViewer.setContentProvider(getOutputsContentProvider());

		setFocusListeners();
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);

		createContextMenu(getInputsViewer());
		createContextMenu(getOutputsViewer());
	}

	private void setFocusListeners() {
		getOutputsViewer().getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				isInputsViewer = false;
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

		});
		getInputsViewer().getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				isInputsViewer = true;
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

		});

	}

	protected abstract IContentProvider getOutputsContentProvider();

	protected abstract IContentProvider getInputsContentProvider();

	protected TableLayout createTableLayout(Table table) {
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Name);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Type);
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(NAME_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		return layout;
	}

	private void createInputEdit(Composite parent) {
		Group inputsGroup = getWidgetFactory().createGroup(parent, "Inputs"); //$NON-NLS-1$
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(inputsGroup, getWidgetFactory());
		inputsViewer = createTypeTableView(inputsGroup);
		configureButtonList(buttons, inputsViewer, true);
	}

	private TableViewer createTypeTableView(Group parent) {
		TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setColumnProperties(getColumnProperties());
		viewer.setCellModifier(getCellModifier(viewer));
		viewer.setLabelProvider(getLabelProvider());
		return viewer;
	}

	protected String[] getColumnProperties() {
		return new String[] { NAME, TYPE, COMMENT };
	}

	protected InterfaceLabelProvider getLabelProvider() {
		return new InterfaceLabelProvider();
	}

	protected InterfaceCellModifier getCellModifier(TableViewer viewer) {
		return new InterfaceCellModifier(viewer);
	}

	private void configureButtonList(AddDeleteReorderListWidget buttons, TableViewer viewer, boolean inputs) {
		buttons.bindToTableViewer(viewer, this, ref -> newCreateCommand((IInterfaceElement) ref, inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	protected void setCellEditors(TableViewer viewer) {
		viewer.setCellEditors(new CellEditor[] { new CustomTextCellEditor(viewer.getTable()),
				ComboBoxWidgetFactory.createComboBoxCellEditor(viewer.getTable(), fillTypeCombo(), SWT.READ_ONLY),
				new CustomTextCellEditor(viewer.getTable()) });
	}

	private void createOutputEdit(Composite parent) {
		Group outputsGroup = getWidgetFactory().createGroup(parent, "Outputs"); //$NON-NLS-1$
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(outputsGroup, getWidgetFactory());
		outputsViewer = createTypeTableView(outputsGroup);
		configureButtonList(buttons, outputsViewer, false);
	}

	@Override
	protected void setInputCode() {
	}

	@Override
	protected void setInputInit() {
		// only now the types are correctly set so that the type lists for the combo
		// boxes can be created correctly
		setCellEditors(inputsViewer);
		setCellEditors(outputsViewer);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			inputsViewer.setInput(getType());
			outputsViewer.setInput(getType());
		}
		commandStack = commandStackBuffer;
	}

	protected static class InterfaceContentProvider implements IStructuredContentProvider {
		private boolean inputs;
		private InterfaceContentProviderType type;

		public InterfaceContentProvider(boolean inputs, InterfaceContentProviderType type) {
			this.inputs = inputs;
			this.type = type;
		}

		private Object[] getInputs(Object inputElement) {
			InterfaceList interfaceList = getInterfaceListFromInput(inputElement);

			if (null != interfaceList) {
				switch (type) {
				case EVENT:
					return interfaceList.getEventInputs().toArray();
				case ADAPTER:
					return interfaceList.getSockets().toArray();
				case DATA:
					return interfaceList.getInputVars().toArray();
				default:
					break;
				}
			}
			return new Object[0];
		}

		private Object[] getOutputs(Object inputElement) {
			InterfaceList interfaceList = getInterfaceListFromInput(inputElement);

			if (null != interfaceList) {
				switch (type) {
				case EVENT:
					return interfaceList.getEventOutputs().toArray();
				case ADAPTER:
					return interfaceList.getPlugs().toArray();
				case DATA:
					return interfaceList.getOutputVars().toArray();
				default:
					break;
				}
			}
			return new Object[0];
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			if ((inputElement instanceof SubApp) || (inputElement instanceof FBType)) {
				if (inputs) {
					return getInputs(inputElement);
				} else {
					return getOutputs(inputElement);
				}
			}
			return new Object[0];
		}

		private static InterfaceList getInterfaceListFromInput(Object inputElement) {
			InterfaceList interfaceList = null;
			if (inputElement instanceof SubApp) {
				interfaceList = ((SubApp) inputElement).getInterface();
			} else if (inputElement instanceof FBType) {
				interfaceList = ((FBType) inputElement).getInterfaceList();
			}
			return interfaceList;
		}
	}

	protected static class InterfaceLabelProvider extends LabelProvider implements ITableLabelProvider {

		private static final int NAME_COLUMN = 0;
		private static final int TYPE_COLUMN = 1;
		private static final int COMMENT_COLUMN = 2;

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			String result = null;
			if (element instanceof IInterfaceElement) {
				switch (columnIndex) {
				case NAME_COLUMN:
					result = ((IInterfaceElement) element).getName();
					break;
				case TYPE_COLUMN:
					result = element instanceof Event ? FordiacMessages.Event
							: ((IInterfaceElement) element).getTypeName();
					break;
				case COMMENT_COLUMN:
					result = ((IInterfaceElement) element).getComment() != null
					? ((IInterfaceElement) element).getComment()
							: ""; //$NON-NLS-1$
					break;
				}
			} else {
				result = element.toString();
			}
			return result;
		}
	}

	protected Palette getPalette() {
		return getTypeLibrary().getBlockTypeLib();
	}

	protected DataTypeLibrary getDataTypeLib() {
		return getTypeLibrary().getDataTypeLibrary();
	}

	protected class InterfaceCellModifier implements ICellModifier {
		private static final int TYPE_COLUMN_INDEX = 1;
		protected TableViewer viewer;

		public InterfaceCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return !(TYPE.equals(property) && (element instanceof IInterfaceElement)
					&& (!((IInterfaceElement) element).getInputConnections().isEmpty()
							|| !((IInterfaceElement) element).getOutputConnections().isEmpty()));
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case NAME:
				return ((INamedElement) element).getName();
			case TYPE:
				String type = ((IInterfaceElement) element).getTypeName();
				List<String> items = Arrays
						.asList(((ComboBoxCellEditor) viewer.getCellEditors()[TYPE_COLUMN_INDEX]).getItems());
				return Integer.valueOf(items.indexOf(type));
			case COMMENT:
				return ((INamedElement) element).getComment() != null ? ((INamedElement) element).getComment() : ""; //$NON-NLS-1$
			default:
				return null;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			Object data = tableItem.getData();
			Command cmd = null;

			switch (property) {
			case NAME:
				cmd = new ChangeSubAppIENameCommand((IInterfaceElement) data, value.toString());
				break;
			case COMMENT:
				cmd = new ChangeCommentCommand((INamedElement) data, value.toString());
				break;
			case TYPE:
				String dataTypeName = ((ComboBoxCellEditor) viewer.getCellEditors()[1]).getItems()[(int) value];
				if (data instanceof AdapterDeclaration) {
					DataType newType = getPalette().getAdapterTypeEntry(dataTypeName).getType();
					cmd = newChangeTypeCommand((VarDeclaration) data, newType);
				} else {
					if (data instanceof VarDeclaration) {
						cmd = newChangeTypeCommand((VarDeclaration) data, getDataTypeLib().getType(dataTypeName));
					}
				}
				break;
			default:
				break;
			}

			if (null != cmd) {
				executeCommand(cmd);
				viewer.refresh(data);
			}
		}
	}

	protected int getInsertingIndex(IInterfaceElement interfaceElement, EList interfaceList) {
		return interfaceList.indexOf(interfaceElement) + 1;
	}

	protected abstract int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput);

	protected String getCreationName(IInterfaceElement interfaceElement) {
		return (null != interfaceElement) ? interfaceElement.getName() : null;
	}

	@Override
	public TableViewer getViewer() {
		return getIsInputsViewer() ? getInputsViewer() : getOutputsViewer();
	}

	@Override
	public Object removeEntry(int index, CompoundCommand cmd) {
		IInterfaceElement entry = getEntry(index);
		cmd.add(newDeleteCommand(entry));
		return entry;
	}

	@Override
	public void executeCompoundCommand(CompoundCommand cmd) {
		executeCommand(cmd);
		getViewer().refresh();
	}

	private IInterfaceElement getEntry(int index) {
		Object obj = getViewer().getElementAt(index);
		return (IInterfaceElement) obj;
	}

	private static void createContextMenu(TableViewer viewer) {
		OpenStructMenu.addTo(viewer);
	}
}
