/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, 
 * 				 2018 - 2019 Johannes Kepler University Linz (JKU) 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - allowed multiline selection in algorithm list
 *   Bianca Wiesmayr, Virendra Ashiwal - 
 *   	- Created tableViewer as new WidgetFactory
 *   	- Shifted Grid heightHint and Width Hint to WidgetFactory.java
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.AlgorithmsLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class AlgorithmList implements CommandExecutor {

	private class AlgorithmViewerCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case A_NAME:
				return ((Algorithm) element).getName();
			case A_LANGUAGE:
				return (element instanceof STAlgorithm) ? 1 : 0;
			default:
				return ((Algorithm) element).getComment();
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			Algorithm data = (Algorithm) tableItem.getData();
			Command cmd = null;
			if (A_NAME.equals(property)) {
				cmd = new ChangeNameCommand(data, value.toString());
			} else if (A_LANGUAGE.equals(property)) {
				cmd = new ChangeAlgorithmTypeCommand(type, data, AbstractECSection.getLanguages().get((int) value));
			} else {
				cmd = new ChangeCommentCommand(data, value.toString());
			}
			if ((null != commandStack)) {
				executeCommand(cmd);
				algorithmViewer.refresh(data);
				if (cmd instanceof ChangeAlgorithmTypeCommand) {
					data = ((ChangeAlgorithmTypeCommand) cmd).getNewAlgorithm();
				}
				if (null != data) {
					algorithmViewer.setSelection(new StructuredSelection(data));
				}
				refresh();
			}
		}
	}

	private static final String A_NAME = "Name"; //$NON-NLS-1$
	private static final String A_LANGUAGE = "Language"; //$NON-NLS-1$
	private static final String A_COMMENT = "Comment"; //$NON-NLS-1$

	private TableViewer algorithmViewer;
	private Composite composite;

	TableViewer getAlgorithmViewer() {
		return algorithmViewer;
	}

	private BasicFBType type;

	private CommandStack commandStack;

	public AlgorithmList(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		composite = widgetFactory.createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		composite.setLayoutData(gridDataVersionViewer);
		AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(composite, widgetFactory);

		createAlgorithmViewer(composite);

		buttons.bindToTableViewer(algorithmViewer, ev -> {
			CreateAlgorithmCommand cmd = new CreateAlgorithmCommand(type);
			executeCommand(cmd);
			algorithmViewer.refresh();
			if (null != cmd.getNewAlgorithm()) {
				algorithmViewer.setSelection(new StructuredSelection(cmd.getNewAlgorithm()), true);
			}
		}, AddDeleteWidget.getSelectionListener(algorithmViewer, this,
				ref -> new DeleteAlgorithmCommand(type, (Algorithm) ref)));
	}

	Composite getComposite() {
		return composite;
	}

	public void initialize(BasicFBType type, CommandStack commandStack) {
		this.type = type;
		this.commandStack = commandStack;
	}

	private void createAlgorithmViewer(Composite parent) {
		algorithmViewer = TableWidgetFactory.createTableViewer(parent);
		configureTableLayout(algorithmViewer);
		algorithmViewer.setCellEditors(createAlgorithmCellEditors(algorithmViewer.getTable()));
		algorithmViewer.setColumnProperties(new String[] { A_NAME, A_LANGUAGE, A_COMMENT });
		algorithmViewer.setContentProvider(new ArrayContentProvider());
		algorithmViewer.setLabelProvider(new AlgorithmsLabelProvider());
		algorithmViewer.setCellModifier(new AlgorithmViewerCellModifier());
	}

	private static void configureTableLayout(TableViewer tableViewer) {
		Table table = tableViewer.getTable();
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText("Name");
		TableColumn column2 = new TableColumn(table, SWT.CENTER);
		column2.setText("Language");
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText("Comment");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 50));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(3, 50));
		table.setLayout(layout);
	}

	@Override
	public void executeCommand(Command cmd) {
		if (null != type && commandStack != null) {
			commandStack.execute(cmd);
		}
	}

	private static CellEditor[] createAlgorithmCellEditors(final Table table) {
		TextCellEditor algorithmNameEditor = new TextCellEditor(table);
		((Text) algorithmNameEditor.getControl()).addVerifyListener(new IdentifierVerifyListener());
		return new CellEditor[] { algorithmNameEditor,
				new ComboBoxCellEditor(table, AbstractECSection.getLanguages().toArray(new String[0]), SWT.READ_ONLY),
				new TextCellEditor(table) };
	}

	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			algorithmViewer.setInput(type.getAlgorithm());
		}
		commandStack = commandStackBuffer;
	}

}
