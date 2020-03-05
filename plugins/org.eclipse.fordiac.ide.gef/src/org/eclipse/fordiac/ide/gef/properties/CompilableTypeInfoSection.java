/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
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
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on
 *   	activation, code clean-up
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.gef.provider.CompilerContentProvider;
import org.eclipse.fordiac.ide.gef.provider.CompilerLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerInfoClassdefCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerInfoHeaderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerLanguageCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerProductCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerVendorCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerVersionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewCompilerCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteCompilerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Properties tab which shows the FB type information of the selected FB
 *
 */
public abstract class CompilableTypeInfoSection extends TypeInfoSection {

	private static final String LANGUAGE_OTHER = "Other"; //$NON-NLS-1$
	private static final String LANGUAGE_C = "C"; //$NON-NLS-1$
	private static final String LANGUAGE_CPP = "Cpp"; //$NON-NLS-1$
	private static final String LANGUAGE_JAVA = "Java"; //$NON-NLS-1$
	private TableViewer compilerViewer;
	private Text headerText;
	private Text classdefText;

	private static final String COMPILER_VERSION = "compiler_version"; //$NON-NLS-1$
	private static final String COMPILER_LANGUAGE = "language"; //$NON-NLS-1$
	private static final String COMPILER_VENDOR = "vendor"; //$NON-NLS-1$
	private static final String COMPILER_PRODUCT = "product"; //$NON-NLS-1$
	private static final String[] VALUE_SET = new String[] { LANGUAGE_JAVA, LANGUAGE_CPP, LANGUAGE_C, LANGUAGE_OTHER };

	private class CompilerCellModifier implements ICellModifier {

		private static final int LANGUAGE_JAVA_INDEX = 0;
		private static final int LANGUAGE_CPP_INDEX = 1;
		private static final int LANGUAGE_C_INDEX = 2;
		private static final int LANGUAGE_OTHER_INDEX = 3;

		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case COMPILER_LANGUAGE:
				return getLanguageIndex(((Compiler) element).getLanguage().getName());
			case COMPILER_PRODUCT:
				return ((Compiler) element).getProduct();
			case COMPILER_VENDOR:
				return ((Compiler) element).getVendor();
			default:
				return ((Compiler) element).getVersion();
			}
		}

		private Object getLanguageIndex(String language) {
			switch (language) {
			case LANGUAGE_JAVA:
				return LANGUAGE_JAVA_INDEX;
			case LANGUAGE_CPP:
				return LANGUAGE_CPP_INDEX;
			case LANGUAGE_C:
				return LANGUAGE_C_INDEX;
			default:
				return LANGUAGE_OTHER_INDEX;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			Compiler data = (Compiler) tableItem.getData();
			Command cmd = getModificationCommand(property, value, data);
			if (null != cmd) {
				executeCommand(cmd);
				compilerViewer.refresh(data);
			}
		}

		private Command getModificationCommand(final String property, final Object value, Compiler data) {
			Command cmd = null;
			switch (property) {
			case COMPILER_LANGUAGE:
				switch ((Integer) value) {
				case LANGUAGE_JAVA_INDEX:
					cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_JAVA));
					break;
				case LANGUAGE_CPP_INDEX:
					cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_CPP));
					break;
				case LANGUAGE_C_INDEX:
					cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_C));
					break;
				case LANGUAGE_OTHER_INDEX:
					cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_OTHER));
					break;
				default:
					break;
				}
				break;
			case COMPILER_PRODUCT:
				cmd = new ChangeCompilerProductCommand(data, value.toString());
				break;
			case COMPILER_VENDOR:
				cmd = new ChangeCompilerVendorCommand(data, value.toString());
				break;
			default:
				cmd = new ChangeCompilerVersionCommand(data, value.toString());
				break;
			}
			return cmd;
		}
	}

	public CompilableTypeInfoSection() {
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createCompilerInfoGroup(getRightComposite());
	}

	private void createCompilerInfoGroup(Composite parent) {
		Group compilerInfoGroup = getWidgetFactory().createGroup(parent, FordiacMessages.CompilerInfo);
		compilerInfoGroup.setLayout(new GridLayout(1, false));
		compilerInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Composite composite = getWidgetFactory().createComposite(compilerInfoGroup, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.Header + ":"); //$NON-NLS-1$
		headerText = createGroupText(composite, true);
		headerText.addModifyListener(
				e -> executeCommand(new ChangeCompilerInfoHeaderCommand((FBType) type, headerText.getText())));
		getWidgetFactory().createCLabel(composite, FordiacMessages.Classdef + ":"); //$NON-NLS-1$
		classdefText = createGroupText(composite, true);
		classdefText.addModifyListener(
				e -> executeCommand(new ChangeCompilerInfoClassdefCommand((FBType) type, classdefText.getText())));
		Composite compositeBottom = getWidgetFactory().createComposite(compilerInfoGroup);
		compositeBottom.setLayout(new GridLayout(2, false));
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(compositeBottom, getWidgetFactory());

		compilerViewer = TableWidgetFactory.createPropertyTableViewer(compositeBottom);
		Table table = compilerViewer.getTable();
		configureTableLayout(table);
		compilerViewer.setContentProvider(new CompilerContentProvider());
		compilerViewer.setLabelProvider(new CompilerLabelProvider());
		compilerViewer
				.setCellEditors(new CellEditor[] { ComboBoxWidgetFactory.createComboBoxCellEditor(table, VALUE_SET),
						new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		compilerViewer.setColumnProperties(
				new String[] { COMPILER_LANGUAGE, COMPILER_VENDOR, COMPILER_PRODUCT, COMPILER_VERSION });

		buttons.bindToTableViewer(compilerViewer, this, ref -> new AddNewCompilerCommand((FBType) type),
				ref -> new DeleteCompilerCommand(((FBType) type).getCompilerInfo(), (Compiler) ref));

		compilerViewer.setCellModifier(new CompilerCellModifier());
	}

	private static void configureTableLayout(final Table table) {
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Language);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Vendor);
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Product);
		TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.Version);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(25, 80));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 80));
		table.setLayout(layout);
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		commandStack = getCommandStack(part, null);
		if (null == commandStack) { // disable all field
			headerText.setEnabled(false);
			classdefText.setEnabled(false);
			compilerViewer.setCellModifier(null);
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if ((type instanceof FBType) && (null != ((FBType) type).getCompilerInfo())) {
			headerText.setText(null != ((FBType) type).getCompilerInfo().getHeader()
					? ((FBType) type).getCompilerInfo().getHeader()
					: ""); //$NON-NLS-1$
			classdefText.setText(null != ((FBType) type).getCompilerInfo().getClassdef()
					? ((FBType) type).getCompilerInfo().getClassdef()
					: ""); //$NON-NLS-1$
			compilerViewer.setInput(type);
		}
		commandStack = commandStackBuffer;
	}
}
