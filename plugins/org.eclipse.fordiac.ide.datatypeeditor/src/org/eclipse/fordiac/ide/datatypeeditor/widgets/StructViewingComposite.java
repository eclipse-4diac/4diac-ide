/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Alexander Lumplecker
 *     - changed ChangeMemberVariableOrderCommand to ChangeVariableOrderCommand
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.widgets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.datatypeeditor.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeDropdown;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StructViewingComposite extends Composite implements CommandExecutor, I4diacTableUtil {
	private static final String NAME = "NAME"; //$NON-NLS-1$
	private static final String TYPE = "DATATYPE"; //$NON-NLS-1$
	private static final String INIT = "INITIAL_VALUE"; //$NON-NLS-1$
	private static final String COMMENT = "COMMENT"; //$NON-NLS-1$
	private static final String ARRAY = "ARRAY_SIZE"; //$NON-NLS-1$

	private TableViewer structViewer;
	private DataTypeDropdown typeDropDown;
	private final DataTypeLibrary dataTypeLibrary;
	private final CommandStack cmdStack;
	private final IWorkbenchPart part;

	private final DataType dataType;

	public StructViewingComposite(Composite parent, int style, CommandStack cmdStack, DataType dataType,
			DataTypeLibrary dataTypeLibrary, IWorkbenchPart part) {
		super(parent, style);
		this.cmdStack = cmdStack;
		this.dataType = dataType;
		this.dataTypeLibrary = dataTypeLibrary;
		this.part = part;
	}

	public void createPartControl(Composite parent) {
		final TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showLabel(parent);

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(parent, widgetFactory);

		showTable(parent);

		buttons.bindToTableViewer(structViewer, this,
				ref -> new CreateMemberVariableCommand(getType(), getInsertionIndex(), getVarName(), getDataType(),
						dataTypeLibrary),
				ref -> new DeleteMemberVariableCommand(getType(), (VarDeclaration) ref),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, true),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, false));

		part.getSite().setSelectionProvider(this);

		createContextMenu(structViewer);
	}

	private static void showLabel(Composite parent) {
		final Label label = new Label(parent, SWT.CENTER);
		label.setText(Messages.StructViewingComposite_Headline);
	}

	private void showTable(Composite parent) {
		structViewer = TableWidgetFactory.createPropertyTableViewer(parent);
		configureTableLayout(structViewer.getTable());

		structViewer.setCellEditors(createCellEditors(structViewer.getTable()));
		structViewer.setColumnProperties(new String[] { NAME, TYPE, COMMENT, INIT, ARRAY });
		structViewer.setContentProvider(new ArrayContentProvider());
		structViewer.setLabelProvider(new DataLabelProvider());
		structViewer.setCellModifier(new StructCellModifier());

		structViewer.setInput(((StructuredType) dataType).getMemberVariables());
	}

	private DataType getDataType() {
		return (null != getLastSelectedVariable()) ? getLastSelectedVariable().getType() : null;
	}

	private String getVarName() {
		return (null != getLastSelectedVariable()) ? getLastSelectedVariable().getName() : null;
	}

	private int getInsertionIndex() {
		final VarDeclaration alg = getLastSelectedVariable();
		if (null == alg) {
			return getType().getMemberVariables().size();
		}
		return getType().getMemberVariables().indexOf(alg) + 1;
	}

	private VarDeclaration getLastSelectedVariable() {
		final IStructuredSelection selection = structViewer.getStructuredSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return (VarDeclaration) selection.toList().get(selection.toList().size() - 1);
	}

	private StructuredType getType() {
		if (dataType instanceof StructuredType) {
			return (StructuredType) dataType;
		}
		return null;
	}

	private CellEditor[] createCellEditors(final Table table) {
		typeDropDown = new DataTypeDropdown(dataTypeLibrary, structViewer) {
			@Override
			protected List<DataType> getDataTypesSorted() {
				return super.getDataTypesSorted().stream()
						.filter(Objects::nonNull)
						.filter(type -> !type.getName().equals(StructViewingComposite.this.getType().getName()))
						.collect(Collectors.toList());
			}
		};
		return new CellEditor[] { new TextCellEditor(table), typeDropDown, new TextCellEditor(table),
				new TextCellEditor(table), new TextCellEditor(table) };
	}

	private static void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Name);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Type);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.InitialValue);
		final TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText(FordiacMessages.ArraySize);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(3, 30));
		layout.addColumnData(new ColumnWeightData(2, 30));
		layout.addColumnData(new ColumnWeightData(2, 20));
		layout.addColumnData(new ColumnWeightData(5, 50));
		layout.addColumnData(new ColumnWeightData(2, 20));
		table.setLayout(layout);
	}

	@Override
	public void executeCommand(Command cmd) {
		cmdStack.execute(cmd);
	}

	private final class StructCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, String property) {
			final VarDeclaration var = (VarDeclaration) element;
			switch (property) {
			case NAME:
				return var.getName();
			case TYPE:
				return var.getTypeName();
			case COMMENT:
				return var.getComment();
			case INIT:
				return (var.getValue() == null) ? "" : var.getValue().getValue(); //$NON-NLS-1$
			case ARRAY:
				return (var.getArraySize() > 0) ? Integer.toString(var.getArraySize()) : ""; //$NON-NLS-1$
			default:
				return "Could not load"; //$NON-NLS-1$
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final VarDeclaration data = (VarDeclaration) tableItem.getData();
			Command cmd = null;
			switch (property) {
			case NAME:
				cmd = new ChangeNameCommand(data, value.toString());
				break;
			case TYPE:
				final DataType type = typeDropDown.getType((String) value);
				if (type == null) {
					return;
				}
				cmd = new ChangeTypeCommand(data, type);
				break;
			case COMMENT:
				cmd = new ChangeCommentCommand(data, value.toString());
				break;
			case INIT:
				cmd = new ChangeValueCommand(data, value.toString());
				break;
			case ARRAY:
				cmd = new ChangeArraySizeCommand(data, value.toString());
				break;
			default:
				break;
			}
			executeCommand(cmd);
			structViewer.refresh(data);
		}
	}

	@Override
	public TableViewer getViewer() {
		return structViewer;
	}

	@Override
	public void addEntry(Object entry, int index, CompoundCommand cmd) {
		if (entry instanceof VarDeclaration) {
			final VarDeclaration varEntry = (VarDeclaration) entry;
			cmd.add(new InsertVariableCommand(((StructuredType) dataType).getMemberVariables(), varEntry, index));
		}
	}

	@Override
	public void executeCompoundCommand(CompoundCommand cmd) {
		executeCommand(cmd);
	}

	@Override
	public Object removeEntry(int index, CompoundCommand cmd) {
		final VarDeclaration entry = (VarDeclaration) getEntry(index);
		cmd.add(new DeleteMemberVariableCommand(getType(), entry));
		return entry;
	}

	public Object getEntry(int index) {
		return getType().getMemberVariables().get(index);
	}

	private static void createContextMenu(TableViewer viewer) {
		OpenStructMenu.addTo(viewer);
	}
}
