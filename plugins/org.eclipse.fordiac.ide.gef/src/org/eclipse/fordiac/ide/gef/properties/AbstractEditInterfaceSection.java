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
 *   Bianca Wiesmayr - extract table creation, cleanup, new read-only derived sections
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *               - cleaned command stack handling for property sections
 *   Daniel Lindhuber - added copy/paste and the context menu
 *   				  - made typedropdown methods overrideable
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.edit.providers.InterfaceElementLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
	private static final String NAME_COL = "name"; //$NON-NLS-1$
	private static final String TYPE_COL = "type"; //$NON-NLS-1$
	private static final String COMMENT_COL = "comment"; //$NON-NLS-1$

	private TableViewer inputsViewer;
	private TableViewer outputsViewer;
	private boolean isInputsViewer;
	protected boolean createButtons = true;

	protected abstract CreateInterfaceElementCommand newCreateCommand(IInterfaceElement selection, boolean isInput);

	protected abstract CreateInterfaceElementCommand newInsertCommand(IInterfaceElement selection, boolean isInput,
			int index);

	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);

	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp);

	protected abstract String[] fillTypeCombo();

	@Override
	protected abstract INamedElement getInputType(Object input);

	@SuppressWarnings("static-method") // this method allows sub-classes to provide own change type commands, e.g.,
	// subapps
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return new ChangeDataTypeCommand(data, newType);
	}

	public TableViewer getInputsViewer() {
		return inputsViewer;
	}

	public TableViewer getOutputsViewer() {
		return outputsViewer;
	}

	public boolean isInputsViewer() {
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

		inputsViewer.addDoubleClickListener(ElementSelector::jumpToPinFromDoubleClickEvent);
		outputsViewer.addDoubleClickListener(ElementSelector::jumpToPinFromDoubleClickEvent);

		createContextMenu(getInputsViewer());
		createContextMenu(getOutputsViewer());
	}

	private void setFocusListeners() {
		getOutputsViewer().getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				isInputsViewer = false;
			}

			@Override
			public void focusLost(final FocusEvent e) {
				// Nothing to do
			}

		});
		getInputsViewer().getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				isInputsViewer = true;
			}

			@Override
			public void focusLost(final FocusEvent e) {
				// Nothing to do
			}

		});

	}

	protected abstract IContentProvider getOutputsContentProvider();

	protected abstract IContentProvider getInputsContentProvider();

	protected TableLayout createTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Name);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Type);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(NAME_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_AND_COMMENT_COLUMN_WIDTH));
		return layout;
	}

	private void createInputEdit(final Composite parent) {
		final Group inputsGroup = getWidgetFactory().createGroup(parent, "Inputs"); //$NON-NLS-1$
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (createButtons) {
			final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
			buttons.createControls(inputsGroup, getWidgetFactory());
			inputsViewer = createTypeTableView(inputsGroup);
			configureButtonList(buttons, inputsViewer, true);
		} else {
			inputsViewer = createTypeTableView(inputsGroup);
		}
	}

	private void createOutputEdit(final Composite parent) {
		final Group outputsGroup = getWidgetFactory().createGroup(parent, "Outputs"); //$NON-NLS-1$
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (createButtons) {
			final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
			buttons.createControls(outputsGroup, getWidgetFactory());
			outputsViewer = createTypeTableView(outputsGroup);
			configureButtonList(buttons, outputsViewer, false);
		} else {
			outputsViewer = createTypeTableView(outputsGroup);

		}
	}

	private TableViewer createTypeTableView(final Group parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setColumnProperties(getColumnProperties());
		viewer.setCellModifier(getCellModifier(viewer));
		viewer.setLabelProvider(getLabelProvider());
		return viewer;
	}

	protected String[] getColumnProperties() {
		return new String[] { NAME_COL, TYPE_COL, COMMENT_COL };
	}

	protected LabelProvider getLabelProvider() {
		return new InterfaceElementLabelProvider();
	}

	protected InterfaceCellModifier getCellModifier(final TableViewer viewer) {
		return new InterfaceCellModifier(viewer);
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final TableViewer viewer, final boolean inputs) {
		buttons.bindToTableViewer(viewer, this, ref -> newCreateCommand((IInterfaceElement) ref, inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	// can be overridden by subclasses to use a different type dropdown
	protected CellEditor createTypeCellEditor(final TableViewer viewer) {
		return ComboBoxWidgetFactory.createComboBoxCellEditor(viewer.getTable(), fillTypeCombo(), SWT.READ_ONLY);
	}

	// subclasses need to override this method if they use a different type dropdown
	@SuppressWarnings("static-method")
	protected Object getTypeValue(final Object element, final TableViewer viewer, final int TYPE_COLUMN_INDEX) {
		final String type = ((IInterfaceElement) element).getTypeName();
		final List<String> items = Arrays
				.asList(((ComboBoxCellEditor) viewer.getCellEditors()[TYPE_COLUMN_INDEX]).getItems());
		return Integer.valueOf(items.indexOf(type));
	}

	// subclasses need to override this method if they use a different type dropdown
	protected Command createChangeDataTypeCommand(final VarDeclaration data, final Object value, final TableViewer viewer) {
		final String dataTypeName = ((ComboBoxCellEditor) viewer.getCellEditors()[1]).getItems()[((Integer) value).intValue()];
		return newChangeTypeCommand(data, getDataTypeLib().getType(dataTypeName));
	}

	protected void setCellEditors(final TableViewer viewer) {
		viewer.setCellEditors(new CellEditor[] { new CustomTextCellEditor(viewer.getTable()),
				createTypeCellEditor(viewer), new CustomTextCellEditor(viewer.getTable()) });
	}

	@Override
	protected void setInputCode() {
		// nothing to be done here
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
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			inputsViewer.setInput(getType());
			outputsViewer.setInput(getType());
		}
		commandStack = commandStackBuffer;
	}

	protected abstract static class InterfaceContentProvider implements IStructuredContentProvider {
		private final boolean inputs;

		protected InterfaceContentProvider(final boolean inputs) {
			this.inputs = inputs;
		}

		protected abstract Object[] getInputs(Object inputElement);

		protected abstract Object[] getOutputs(Object inputElement);

		@Override
		public Object[] getElements(final Object inputElement) {
			if ((inputElement instanceof FBNetworkElement) || (inputElement instanceof FBType)) {
				if (inputs) {
					return getInputs(inputElement);
				}
				return getOutputs(inputElement);
			}
			return new Object[0];
		}

		static InterfaceList getInterfaceListFromInput(final Object inputElement) {
			InterfaceList interfaceList = null;
			if (inputElement instanceof FBNetworkElement) {
				interfaceList = ((FBNetworkElement) inputElement).getInterface();
			} else if (inputElement instanceof FBType) {
				interfaceList = ((FBType) inputElement).getInterfaceList();
			}
			return interfaceList;
		}
	}

	protected class InterfaceCellModifier implements ICellModifier {
		private static final int TYPE_COLUMN_INDEX = 1;
		protected TableViewer viewer;

		public InterfaceCellModifier(final TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return !(TYPE_COL.equals(property) && (element instanceof IInterfaceElement)
					&& (!((IInterfaceElement) element).getInputConnections().isEmpty()
							|| !((IInterfaceElement) element).getOutputConnections().isEmpty()));
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case NAME_COL:
				return ((INamedElement) element).getName();
			case TYPE_COL:
				return getTypeValue(element, viewer, TYPE_COLUMN_INDEX);
			case COMMENT_COL:
				return ((INamedElement) element).getComment() != null ? ((INamedElement) element).getComment() : ""; //$NON-NLS-1$
			default:
				return null;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Object data = tableItem.getData();
			Command cmd = null;

			switch (property) {
			case NAME_COL:
				cmd = new ChangeSubAppIENameCommand((IInterfaceElement) data, value.toString());
				break;
			case COMMENT_COL:
				cmd = new ChangeCommentCommand((INamedElement) data, value.toString());
				break;
			case TYPE_COL:
				if (data instanceof AdapterDeclaration) {
					final String dataTypeName = ((ComboBoxCellEditor) viewer.getCellEditors()[1]).getItems()[((Integer) value).intValue()];
					final DataType newType = getPalette().getAdapterTypeEntry(dataTypeName).getType();
					cmd = newChangeTypeCommand((VarDeclaration) data, newType);
				} else {
					if (data instanceof VarDeclaration) {
						cmd = createChangeDataTypeCommand((VarDeclaration) data, value, viewer);
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

	protected int getInsertingIndex(final IInterfaceElement interfaceElement,
			final EList<? extends IInterfaceElement> interfaceList) {
		return interfaceList.indexOf(interfaceElement) + 1;
	}

	protected abstract int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput);

	protected String getCreationName(final IInterfaceElement interfaceElement) {
		return (null != interfaceElement) ? interfaceElement.getName() : null;
	}

	@Override
	public TableViewer getViewer() {
		return isInputsViewer() ? getInputsViewer() : getOutputsViewer();
	}

	@Override
	public Object removeEntry(final int index, final CompoundCommand cmd) {
		final IInterfaceElement entry = getEntry(index);
		cmd.add(newDeleteCommand(entry));
		return entry;
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		getViewer().refresh();
	}

	private IInterfaceElement getEntry(final int index) {
		final Object obj = getViewer().getElementAt(index);
		return (IInterfaceElement) obj;
	}

	private static void createContextMenu(final TableViewer viewer) {
		OpenStructMenu.addTo(viewer);
	}
}
