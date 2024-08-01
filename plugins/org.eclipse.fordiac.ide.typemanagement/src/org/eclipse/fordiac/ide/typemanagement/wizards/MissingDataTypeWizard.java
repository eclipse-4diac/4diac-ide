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
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.typemanagement.util.ErrorMarkerResolver;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class MissingDataTypeWizard extends AbstractMissingTypeWizard {
	private static final String PAGE_NAME = "DataTypeRepairPage"; //$NON-NLS-1$

	public MissingDataTypeWizard(final EObject type) {
		super(type);
	}

	@Override
	protected AbstractRepairOperationPage createPage() {
		return new DataTypeRepairOperationPage(PAGE_NAME);
	}

	@Override
	protected void handleChangeType() {
		final var typeChoice = result;
		if (errorType instanceof final IInterfaceElement interfaceElement
				&& interfaceElement.getType().getTypeEntry() instanceof final ErrorDataTypeEntry errorEntry) {
			final DataTypeInstanceSearch search = new DataTypeInstanceSearch(errorEntry,
					getTypeLibrary(interfaceElement));
			final List<? extends EObject> res = search.performSearch();
			for (final var r : res) {
				if (r instanceof final IInterfaceElement iE && typeChoice instanceof final DataType d) {
					AbstractLiveSearchContext.executeAndSave(ChangeDataTypeCommand.forDataType(iE, d), iE,
							new NullProgressMonitor());
				}

				if (r instanceof final StructManipulator fb && typeChoice instanceof final DataType d) {
					AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, d), fb,
							new NullProgressMonitor());
				} else if (r instanceof final ConfigurableFB fb && typeChoice instanceof final DataType d) {
					AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, d), fb,
							new NullProgressMonitor());
				}
			}
		}
	}

	@Override
	protected void handleCreateMissing() {
		if (errorType instanceof final VarDeclaration varDeclaration) {
			ErrorMarkerResolver.repairMissingStructuredDataType(varDeclaration);
		}
	}

	private class DataTypeRepairOperationPage extends AbstractRepairOperationPage {
		protected DataTypeRepairOperationPage(final String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(final Composite parent) {
			final Composite container = new Composite(parent, SWT.NONE);
			container.setLayout(new GridLayout());

			final Button changeTypeButton = new Button(container, SWT.RADIO);
			final Group group = new Group(container, SWT.NONE);
			group.setEnabled(true);
			group.setLayout(new GridLayout(2, false));
			changeTypeButton.setText(FordiacMessages.Repair_Dialog_ChangeDataType);
			changeTypeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					if (changeTypeButton.getSelection()) {
						choice = Choices.CHANGE_TYPE;
						result = IecTypes.GenericTypes.ANY_STRUCT;
					}
				}
			});

			final Text currentType = new Text(group, SWT.AUTO_TEXT_DIRECTION);
			currentType.setText("ANY_STRUCT"); //$NON-NLS-1$
			currentType.setEditable(false);

			final Button openDialogButton = new Button(group, SWT.PUSH);

			openDialogButton.setText("..."); //$NON-NLS-1$
			openDialogButton.addListener(SWT.Selection, e -> {
				final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(),
						DataTypeSelectionTreeContentProvider.INSTANCE);
				dialog.setInput(getTypeLibrary(errorType));
				if ((dialog.open() == Window.OK)
						&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
					result = node.getType();
					currentType.setText(node.getFullName());

				}
			});

			final Button creatTypeButton = new Button(container, SWT.RADIO);
			creatTypeButton.setText(FordiacMessages.Repair_Dialog_New_DataType);
			creatTypeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					if (creatTypeButton.getSelection()) {
						choice = Choices.CREATE_MISSING_TYPE;
					}
				}
			});

			this.setControl(container);
			this.setTitle(FordiacMessages.Repair_Dialog_ChangeDataType);
			getShell().pack();
			this.setPageComplete(true);
		}
	}
}
