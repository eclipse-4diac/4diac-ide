/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.widgets;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeMemberVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StructViewingComposite extends Composite implements CommandExecutor, I4diacTableUtil {
	private static final String NAME = "NAME"; //$NON-NLS-1$
	private static final String TYPE = "DATATYPE"; //$NON-NLS-1$
	private static final String INIT = "INITIAL_VALUE"; //$NON-NLS-1$
	private static final String COMMENT = "COMMENT"; //$NON-NLS-1$
	private static final String ARRAY = "ARRAY_SIZE"; //$NON-NLS-1$

	private TableViewer structViewer;
	private ComboBoxCellEditor typeDropDown;
	private final DataTypeLibrary dataTypeLibrary;
	private String[] dataTypes;
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
		TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showLabel(parent);

		fillDataTypeArray();

		AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(parent, widgetFactory);

		showTable(parent);

		buttons.bindToTableViewer(structViewer, this,
				ref -> new CreateMemberVariableCommand(getType(), getInsertionIndex(), getVarName(), getDataType(),
						dataTypeLibrary),
				ref -> new DeleteMemberVariableCommand(getType(), (VarDeclaration) ref),
				ref -> new ChangeMemberVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, true),
				ref -> new ChangeMemberVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref,
						false));

		part.getSite().setSelectionProvider(this);

		createContextMenu(structViewer);
	}

	private void fillDataTypeArray() {
		List<DataType> dataTypeList = dataTypeLibrary.getDataTypesSorted();

		dataTypes = dataTypeList.stream().filter(Objects::nonNull)
				.filter(dtp -> !dataType.getName().contentEquals(dtp.getName())) // prevent infinite loop
				.map(DataType::getName)
				.toArray(String[]::new);
	}

	private void showLabel(Composite parent) {
		final Label label = new Label(parent, SWT.CENTER);
		label.setText("STRUCT Editor");
	}

	private void showTable(Composite parent) {
		structViewer = TableWidgetFactory.createPropertyTableViewer(parent);
		configureTableLayout(structViewer.getTable());

		structViewer.setCellEditors(createCellEditors(structViewer.getTable()));
		structViewer.setColumnProperties(new String[] { NAME, TYPE, INIT, COMMENT, ARRAY });
		structViewer.setContentProvider(new ArrayContentProvider());
		structViewer.setLabelProvider(new StructVarsLabelProvider());
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
		VarDeclaration alg = getLastSelectedVariable();
		if (null == alg) {
			return getType().getMemberVariables().size();
		}
		return getType().getMemberVariables().indexOf(alg) + 1;
	}

	private VarDeclaration getLastSelectedVariable() {
		IStructuredSelection selection = structViewer.getStructuredSelection();
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
		typeDropDown = ComboBoxWidgetFactory.createComboBoxCellEditor(table, dataTypes, SWT.READ_ONLY);
		CCombo combobox = (CCombo) typeDropDown.getControl();
		combobox.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				fillDataTypeArray();
				typeDropDown.setItems(dataTypes);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return new CellEditor[] { new TextCellEditor(table), typeDropDown, new TextCellEditor(table),
				new TextCellEditor(table), new TextCellEditor(table) };
	}

	private void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText("Name");
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText("Type");
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText("Initial Value");
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText("Comment");
		final TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText("Array Size");
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
			case TYPE: // return index of selected element in array
				return Arrays.asList(typeDropDown.getItems()).indexOf(var.getType().getName());
			case COMMENT:
				return var.getComment();
			case INIT:
				return (var.getValue() == null) ? "" : var.getValue().getValue(); //$NON-NLS-1$
			case ARRAY:
				return Integer.toString(var.getArraySize());
			default:
				return "Could not load";
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
				cmd = new ChangeTypeCommand(data, dataTypeLibrary.getType(dataTypes[(int) value]));
				break;
			case COMMENT:
				cmd = new ChangeCommentCommand(data, value.toString());
				break;
			case INIT:
				cmd = new ChangeInitialValueCommand(data, value.toString());
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

	private static final class StructVarsLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof VarDeclaration) {
				final VarDeclaration varDecl = ((VarDeclaration) element);
				switch (columnIndex) {
				case 0:
					return varDecl.getName();
				case 1:
					return varDecl.getType().getName();
				case 2:
					return varDecl.getValue() == null ? "" : varDecl.getValue().getValue();
				case 3:
					return varDecl.getComment();
				case 4:
					return (varDecl.getArraySize() > 0) ? Integer.toString(varDecl.getArraySize()) : ""; //$NON-NLS-1$
				default:
					break;
				}
			}
			return element.toString();
		}
	}

	@Override
	public TableViewer getViewer() {
		return structViewer;
	}

	@Override
	public void addEntry(Object entry, int index, CompoundCommand cmd) {
		if (entry instanceof VarDeclaration) {
			VarDeclaration varEntry = (VarDeclaration) entry;
			cmd.add(new InsertVariableCommand(((StructuredType) dataType).getMemberVariables(), varEntry, index));
		}
	}

	@Override
	public void executeCompoundCommand(CompoundCommand cmd) {
		executeCommand(cmd);
	}

	@Override
	public Object removeEntry(int index, CompoundCommand cmd) {
		VarDeclaration entry = (VarDeclaration) getEntry(index);
		cmd.add(new DeleteMemberVariableCommand(getType(), entry));
		return entry;
	}

	public Object getEntry(int index) {
		return getType().getMemberVariables().get(index);
	}

	private void createContextMenu(TableViewer viewer) {
		Control ctrl = viewer.getControl();
		Menu openEditorMenu = new Menu(viewer.getTable());
		MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredType sel = getSelectedStructuredType(viewer);
				if (sel != null) {
					openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				openItem.setEnabled(getSelectedStructuredType(viewer) != null
						&& !getSelectedStructuredType(viewer).getName().contentEquals("ANY_STRUCT")); //$NON-NLS-1$
			}

			@Override
			public void menuHidden(MenuEvent e) {
			}
		});
		ctrl.setMenu(openEditorMenu);
	}

	private StructuredType getSelectedStructuredType(TableViewer viewer) {
		TableItem[] selected = viewer.getTable().getSelection();
		if (selected[0].getData() instanceof VarDeclaration) {
			VarDeclaration varDecl = (VarDeclaration) selected[0].getData();
			if (varDecl.getType() instanceof StructuredType) {
				return (StructuredType) varDecl.getType();
			}
		}
		return null;
	}

	private static void openStructEditor(IFile file) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {

				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
