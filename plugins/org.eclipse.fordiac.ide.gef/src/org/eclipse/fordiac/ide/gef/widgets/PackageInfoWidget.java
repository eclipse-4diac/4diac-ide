/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.gef.provider.PackageContentProvider;
import org.eclipse.fordiac.ide.gef.provider.PackageLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeImportNamespaceCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangePackageNameCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteImportCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class PackageInfoWidget extends TypeInfoWidget {

	private TableViewer packageViewer;
	private Text nameText;
	private AddDeleteWidget buttons;
	private Table table;
	Composite composite;

	private static final String IMPORTED_NAMESPACE = "imported namespace"; //$NON-NLS-1$

	public PackageInfoWidget(final FormToolkit widgetFactory) {
		super(widgetFactory);
	}

	@Override
	public void createControls(final Composite leftComposite, final Composite rightComposite) {
		super.createControls(leftComposite, rightComposite);
		createPackageInfoGroup(rightComposite);
	}

	private void createPackageInfoGroup(final Composite parent) {
		final Group packageGroup = createGroup(parent, FordiacMessages.Package);
		packageGroup.setLayout(new GridLayout(1, false));
		packageGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		composite = getWidgetFactory().createComposite(packageGroup, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		getWidgetFactory().createLabel(composite, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangePackageNameCommand(getType(), nameText.getText()));
			}
		});

		final Label importsLabel = new Label(packageGroup, SWT.NONE);
		importsLabel.setText(FordiacMessages.Imports + ":"); //$NON-NLS-1$
		importsLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_TRANSPARENT));

		final Composite compositeBottom = getWidgetFactory().createComposite(packageGroup);
		compositeBottom.setLayout(new GridLayout(2, false));
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		buttons = new AddDeleteWidget();
		buttons.createControls(compositeBottom, getWidgetFactory());

		packageViewer = TableWidgetFactory.createPropertyTableViewer(compositeBottom);
		table = packageViewer.getTable();
		configureTableLayout(table);
		packageViewer.setContentProvider(new PackageContentProvider());
		packageViewer.setLabelProvider(new PackageLabelProvider());
		packageViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table) });
		packageViewer.setColumnProperties(new String[] { IMPORTED_NAMESPACE });

		buttons.bindToTableViewer(packageViewer, this, ref -> new AddNewImportCommand(getType()),
				ref -> new DeleteImportCommand(getType().getCompilerInfo(), (Import) ref));

		packageViewer.setCellModifier(new ImportsCellModifier());
	}

	private static void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Name);

		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(25, 200));
		table.setLayout(layout);
	}

	@Override
	public void refresh() {
		super.refresh();
		final Consumer<Command> commandExecutorBuffer = getCommandExecutor();
		setCommandExecutor(null);
		if ((getType() != null)) {
			nameText.setEditable(!isReadonly());
			nameText.setEnabled(!isReadonly());
			buttons.setButtonEnablement(!isReadonly());
			buttons.setCreateButtonEnablement(!isReadonly());
			table.setEnabled(!isReadonly());

			if (null != getType().getCompilerInfo()) {
				final CompilerInfo compilerInfo = getType().getCompilerInfo();
				nameText.setText(null != compilerInfo.getPackageName() ? compilerInfo.getPackageName() : ""); //$NON-NLS-1$
			}
			packageViewer.setInput(getType());
		}
		setCommandExecutor(commandExecutorBuffer);

	}

	@Override
	public void setEnabled(final boolean enablement) {
		super.setEnabled(enablement);
		nameText.setEnabled(enablement);
		buttons.setVisible(enablement);
		table.setEnabled(enablement);
		packageViewer.setCellModifier(null);
	}

	private boolean isReadonly() {
		return getType() instanceof FunctionFBType || getType() instanceof GlobalConstants;
	}

	private class ImportsCellModifier implements ICellModifier {

		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (property.equals(IMPORTED_NAMESPACE)) {
				return ((Import) element).getImportedNamespace();
			}
			return null;
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Import data = (Import) tableItem.getData();
			final Command cmd = getModificationCommand(property, value, data);
			if (null != cmd) {
				executeCommand(cmd);
				packageViewer.refresh(data);
			}
		}

		private Command getModificationCommand(final String property, final Object value, final Import data) {
			Command cmd = null;
			if (property.equals(IMPORTED_NAMESPACE)) {
				cmd = new ChangeImportNamespaceCommand(data, value.toString());
			}
			return cmd;
		}
	}

}
