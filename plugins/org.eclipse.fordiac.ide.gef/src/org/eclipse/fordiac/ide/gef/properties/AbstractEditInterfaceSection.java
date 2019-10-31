/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * 				 2018, 2019 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
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
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
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
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceSection extends AbstractSection {
	private static final int TYPE_AND_COMMENT_COLUMN_WIDTH = 100;
	private static final int NAME_COLUMN_WIDTH = 200;
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String TYPE = "type"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private TableViewer inputsViewer;
	private TableViewer outputsViewer;

	protected enum InterfaceContentProviderType {
		EVENT, DATA, ADAPTER
	}

	protected abstract CreateInterfaceElementCommand newCreateCommand(boolean isInput);

	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);

	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean isInput,
			boolean moveUp);

	protected abstract String[] fillTypeCombo();

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
	}

	protected abstract IContentProvider getOutputsContentProvider();

	protected abstract IContentProvider getInputsContentProvider();

	private static void createTableLayout(Table table) {
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(NAME);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(TYPE);
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(COMMENT);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(NAME_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		table.setLayout(layout);
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
		createTableLayout(viewer.getTable());
		viewer.setColumnProperties(new String[] { NAME, TYPE, COMMENT });
		viewer.setCellModifier(new InterfaceCellModifier(viewer));
		viewer.setLabelProvider(new InterfaceLabelProvider());
		return viewer;
	}

	private void configureButtonList(AddDeleteReorderListWidget buttons, TableViewer viewer, boolean inputs) {
		buttons.bindToTableViewer(viewer, this, ref -> newCreateCommand(inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref),
				ref -> newOrderCommand((IInterfaceElement) ref, inputs, true),
				ref -> newOrderCommand((IInterfaceElement) ref, inputs, false));
	}

	private void setCellEditors(TableViewer viewer) {
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer.getTable()),
				ComboBoxWidgetFactory.createComboBoxCellEditor(viewer.getTable(), fillTypeCombo(), SWT.READ_ONLY),
				new TextCellEditor(viewer.getTable()) });
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
			if (inputElement instanceof SubApp || inputElement instanceof FBType) {
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

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			String result = null;
			if (element instanceof IInterfaceElement) {
				switch (columnIndex) {
				case 0:
					result = ((IInterfaceElement) element).getName();
					break;
				case 1:
					result = element instanceof Event ? "Event" : ((IInterfaceElement) element).getTypeName();
					break;
				case 2:
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
		if (getType() instanceof SubApp) {
			return ((SubApp) getType()).getFbNetwork().getApplication().getAutomationSystem().getPalette();
		} else if (getType() instanceof FBType) {
			return ((FBType) getType()).getPaletteEntry().getGroup().getPallete();
		}
		return null;
	}

	private class InterfaceCellModifier implements ICellModifier {
		private static final int TYPE_COLUMN_INDEX = 1;
		private TableViewer viewer;

		public InterfaceCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return !(TYPE.equals(property) && element instanceof IInterfaceElement
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
				return items.indexOf(type);
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
						cmd = newChangeTypeCommand((VarDeclaration) data,
								DataTypeLibrary.getInstance().getType(dataTypeName));
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

}
