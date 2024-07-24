/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class RepairBrokenConnectionWizardPage extends WizardPage {
	private final TypeLibrary lib;
	private final DataType errorType;
	private StructuredType type;
	private String var;

	public RepairBrokenConnectionWizardPage(final String pageName, final TypeLibrary lib, final DataType errorType) {
		super(pageName);
		this.setTitle("Select Repair Connection");
		this.setDescription("Select a Structured Type and a compatiple Member Variable.");
		this.lib = lib;
		this.errorType = errorType;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridData containerData = new GridData();
		containerData.horizontalAlignment = GridData.FILL;
		containerData.grabExcessHorizontalSpace = true;
		container.setLayoutData(containerData);

		final GridLayout gridl = new GridLayout();
		gridl.numColumns = 2;
		gridl.marginWidth = 0;
		container.setLayout(gridl);

//		final Label structLabel = new Label(container, SWT.NONE);
//		structLabel.setText("SelectStruct");
		final Text currentType = new Text(container, SWT.AUTO_TEXT_DIRECTION);
		currentType.setText("ANY_STRUCT"); //$NON-NLS-1$
		currentType.setEditable(false);
		final Button structButton = new Button(container, SWT.NONE);
		structButton.setText("...");

		final Table varTable = new Table(container, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER);
		varTable.setLinesVisible(true);
		varTable.setHeaderVisible(true);
		final GridData tableData = new GridData();
		tableData.horizontalAlignment = GridData.FILL;
		tableData.grabExcessHorizontalSpace = true;
		tableData.grabExcessVerticalSpace = true;
		tableData.heightHint = 200;
		tableData.horizontalSpan = 2;
		varTable.setLayoutData(tableData);
		TableColumn column = new TableColumn(varTable, SWT.NONE);
		column.setText("Name");
		column = new TableColumn(varTable, SWT.NONE);
		column.setText("Type");

		structButton.addListener(SWT.Selection, event -> {
			final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
			final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(), instance);
			dialog.setInput(lib);

			if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode node
					&& !node.isDirectory() && node.getType() instanceof final StructuredType structType)) {
				type = structType;
				currentType.setText(node.getFullName());
				varTable.removeAll();
				type.getMemberVariables().forEach(var -> {
					if (var.getType().equals(errorType)) {
						final TableItem item = new TableItem(varTable, SWT.NONE);
						item.setText(0, var.getName());
						item.setText(1, var.getTypeName());
					}
					varTable.getColumn(0).pack();
					varTable.getColumn(1).pack();
				});
				varTable.update();
			}

		});

		varTable.addListener(SWT.Selection, event -> {
			var = varTable.getSelection()[0].getText(0);
			this.setPageComplete(true);
		});

		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return type != null && var != null;
	}

	public StructuredType getType() {
		return type;
	}

	public String getVar() {
		return var;
	}

}