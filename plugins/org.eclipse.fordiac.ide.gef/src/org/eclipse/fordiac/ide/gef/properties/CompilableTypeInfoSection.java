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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.provider.CompilerContentProvider;
import org.eclipse.fordiac.ide.gef.provider.CompilerLabelProvider;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/** Properties tab which shows the FB type information of the selected FB */
public abstract class CompilableTypeInfoSection extends TypeInfoSection {

	private static final String LANGUAGE_OTHER = "Other"; //$NON-NLS-1$
	private static final String LANGUAGE_C = "C"; //$NON-NLS-1$
	private static final String LANGUAGE_CPP = "Cpp"; //$NON-NLS-1$
	private static final String LANGUAGE_JAVA = "Java"; //$NON-NLS-1$
	private TableViewer compilerViewer;

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
			return switch (property) {
			case COMPILER_LANGUAGE -> getLanguageIndex(((Compiler) element).getLanguage().getName());
			case COMPILER_PRODUCT -> ((Compiler) element).getProduct();
			case COMPILER_VENDOR -> ((Compiler) element).getVendor();
			default -> ((Compiler) element).getVersion();
			};
		}

		private Object getLanguageIndex(final String language) {
			return switch (language) {
			case LANGUAGE_JAVA -> Integer.valueOf(LANGUAGE_JAVA_INDEX);
			case LANGUAGE_CPP -> Integer.valueOf(LANGUAGE_CPP_INDEX);
			case LANGUAGE_C -> Integer.valueOf(LANGUAGE_C_INDEX);
			default -> Integer.valueOf(LANGUAGE_OTHER_INDEX);
			};
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Compiler data = (Compiler) tableItem.getData();
			final Command cmd = getModificationCommand(property, value, data);
			if (null != cmd) {
				executeCommand(cmd);
				compilerViewer.refresh(data);
			}
		}

		private Command getModificationCommand(final String property, final Object value, final Compiler data) {
			Command cmd = null;
			switch (property) {
			case COMPILER_LANGUAGE:
				switch (((Integer) value).intValue()) {
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

	protected CompilableTypeInfoSection() {
		// nothing to be done here
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createCompilerInfoGroup(getRightComposite());

	}

	private void createCompilerInfoGroup(final Composite parent) {
		final Group compilerInfoGroup = getWidgetFactory().createGroup(parent, FordiacMessages.CompilerInfo);
		compilerInfoGroup.setLayout(new GridLayout(1, false));
		compilerInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite compositeBottom = getWidgetFactory().createComposite(compilerInfoGroup);
		compositeBottom.setLayout(new GridLayout(2, false));
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(compositeBottom, getWidgetFactory());

		compilerViewer = TableWidgetFactory.createPropertyTableViewer(compositeBottom);
		final Table table = compilerViewer.getTable();
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
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Language);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Vendor);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Product);
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.Version);
		final TableLayout layout = new TableLayout(true);
		layout.addColumnData(new ColumnWeightData(25, 80));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 80));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setLayout(layout);
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		if (null == commandStack) { // disable all field
			compilerViewer.setCellModifier(null);
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if ((getType() != null) && (null != getType().getCompilerInfo())) {
			compilerViewer.setInput(type);
		}
		commandStack = commandStackBuffer;
	}

	private final Adapter typeInfoAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		super.addContentAdapter();
		if (getType() != null && getType().getCompilerInfo() != null) {
			getType().getCompilerInfo().eAdapters().add(typeInfoAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		super.removeContentAdapter();
		if (getType() != null && getType().getCompilerInfo() != null) {
			getType().getCompilerInfo().eAdapters().remove(typeInfoAdapter);
		}
	}
}
