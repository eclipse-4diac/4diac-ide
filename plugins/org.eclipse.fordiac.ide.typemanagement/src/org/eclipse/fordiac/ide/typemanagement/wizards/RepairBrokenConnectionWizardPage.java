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

import java.util.Objects;

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

/**
 * This WizardPage provides the selection of an existing Struct and one of its
 * Variables in order to repair an
 * {@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface
 * ErrorMarkerInterface}.
 */
public class RepairBrokenConnectionWizardPage extends WizardPage {
	private final TypeLibrary lib;
	private final DataType errorType;
	private StructuredType type;
	private String targetVar;

	/**
	 * Constructs a new instance
	 *
	 * @param lib       TypeLibrary of which the Struct can be selected from
	 * @param errorType DataType of the
	 *                  {@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface
	 *                  ErrorMarkerInterface}, needed for filtering the Struct
	 *                  Variables by Type
	 */
	public RepairBrokenConnectionWizardPage(final TypeLibrary lib, final DataType errorType) {
		super(Messages.RepairBrokenConnectionWizardPage_Title);
		this.setDescription(Messages.RepairBrokenConnectionWizardPage_Description);
		this.lib = Objects.requireNonNull(lib);
		this.errorType = Objects.requireNonNull(errorType);
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

		final Text currentType = new Text(container, SWT.AUTO_TEXT_DIRECTION);
		currentType.setText("ANY_STRUCT"); //$NON-NLS-1$
		currentType.setEditable(false);
		final Button structButton = new Button(container, SWT.NONE);
		structButton.setText(Messages.RepairBrokenConnectionWizardPage_Dots);

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
		column.setText(Messages.RepairBrokenConnectionWizardPage_Name);
		column = new TableColumn(varTable, SWT.NONE);
		column.setText(Messages.RepairBrokenConnectionWizardPage_Type);

		structButton.addListener(SWT.Selection, event -> {
			final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
			final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(), instance);
			dialog.setInput(lib);

			if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode node
					&& !node.isDirectory() && node.getType() instanceof final StructuredType structType)) {
				type = structType;
				currentType.setText(node.getFullName());
				varTable.removeAll();
				type.getMemberVariables().forEach(varDec -> {
					if (varDec.getType().equals(errorType)) {
						final TableItem item = new TableItem(varTable, SWT.NONE);
						item.setText(0, varDec.getName());
						item.setText(1, varDec.getTypeName());
					}
					varTable.getColumn(0).pack();
					varTable.getColumn(1).pack();
				});
				varTable.update();
			}

		});

		varTable.addListener(SWT.Selection, event -> {
			final TableItem[] sel = varTable.getSelection();
			if (sel.length != 0 && sel[0] != null) {
				targetVar = sel[0].getText(0);
				this.setPageComplete(true);
			}
		});

		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return type != null && targetVar != null;
	}

	public StructuredType getType() {
		return type;
	}

	public String getVar() {
		return targetVar;
	}

}