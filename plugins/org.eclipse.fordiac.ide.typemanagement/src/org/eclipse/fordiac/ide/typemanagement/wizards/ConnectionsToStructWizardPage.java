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

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring;
import org.eclipse.jface.window.Window;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This WizardPage
 */
public class ConnectionsToStructWizardPage extends UserInputWizardPage {

	private Text structNameText;
	private Text sourceNameText;
	private Text destinationNameText;
	private Button existingStructButton;
	private Button conflictButton;

	StructuredType structType;

	public ConnectionsToStructWizardPage() {
		super(Messages.ConnectionsToStructWizardPage_Title);
		this.setDescription(Messages.ConnectionsToStructWizardPage_Description);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		setErrorMessage(null);
		setMessage(null);

		final GridData containerData = new GridData();
		containerData.horizontalAlignment = GridData.FILL;
		containerData.grabExcessHorizontalSpace = true;
		container.setLayoutData(containerData);

		final GridLayout gridl = new GridLayout();
		gridl.numColumns = 3;
		container.setLayout(gridl);

		final Button newStructButton = new Button(container, SWT.RADIO);
		newStructButton.setText(Messages.ConnectionsToStructWizardPage_NewType);
		final Label structNameLabel = new Label(container, NONE);
		structNameLabel.setText(Messages.ConnectionsToStructWizardPage_Name + ":"); //$NON-NLS-1$
		structNameText = new Text(container, SWT.SINGLE | SWT.BORDER);

		existingStructButton = new Button(container, SWT.RADIO);
		existingStructButton.setText(Messages.ConnectionsToStructWizardPage_ExistingStruct);
		final Button structButton = new Button(container, SWT.NONE);
		structButton.setText("&..."); //$NON-NLS-1$
		final Text existingStructText = new Text(container, SWT.SINGLE | SWT.BORDER);
		existingStructText.setText(Messages.ConnectionsToStructWizardPage_AnyStruct);

		final Label sourceNameLabel = new Label(container, NONE);
		sourceNameLabel.setText(Messages.ConnectionsToStructWizardPage_OutName + ":"); //$NON-NLS-1$
		sourceNameText = new Text(container, SWT.SINGLE | SWT.BORDER);

		final Label destinationNameLabel = new Label(container, NONE);
		destinationNameLabel.setText(Messages.ConnectionsToStructWizardPage_InName + ":"); //$NON-NLS-1$
		destinationNameText = new Text(container, SWT.BORDER);

		final GridData textGridData = new GridData();
		textGridData.horizontalAlignment = GridData.FILL;
		textGridData.grabExcessHorizontalSpace = true;
		structNameText.setLayoutData(textGridData);
		existingStructText.setLayoutData(textGridData);
		final GridData textGridDataSpan = new GridData();
		textGridDataSpan.horizontalAlignment = GridData.FILL;
		textGridDataSpan.grabExcessHorizontalSpace = true;
		textGridDataSpan.horizontalSpan = 2;
		sourceNameText.setLayoutData(textGridDataSpan);
		destinationNameText.setLayoutData(textGridDataSpan);

		conflictButton = new Button(container, SWT.CHECK);
		conflictButton.setText(Messages.ConnectionsToStructWizardPage_SolveConflicts);
		conflictButton.setSelection(true);
		final GridData checkboxGridData = new GridData();
		checkboxGridData.horizontalSpan = 2;
		conflictButton.setLayoutData(checkboxGridData);

		structNameText.addModifyListener(e -> handleInputChanged());
		sourceNameText.addModifyListener(e -> handleInputChanged());
		destinationNameText.addModifyListener(e -> handleInputChanged());
		conflictButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> handleInputChanged()));
		existingStructButton.addListener(SWT.Selection, event -> {
			if (existingStructButton.getSelection()) {
				structNameText.setEnabled(false);
				structButton.setEnabled(true);
			} else {
				structNameText.setEnabled(true);
				structButton.setEnabled(false);
			}
			handleInputChanged();
		});
		structButton.addListener(SWT.Selection, event -> {
			final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
			final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(), instance);
			dialog.setInput(getConnectionsToStructRefactoring().getTypeLibrary());

			if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode node
					&& !node.isDirectory() && node.getType() instanceof final StructuredType selType)) {
				structType = selType;
				existingStructText.setText(selType.getName());
				handleInputChanged();
			}

		});

		structNameText.setFocus();
		newStructButton.setSelection(true);
		structButton.setEnabled(false);
		existingStructText.setEnabled(false);
	}

	private void handleInputChanged() {
		final RefactoringStatus status = new RefactoringStatus();
		final ConnectionsToStructRefactoring refactoring = getConnectionsToStructRefactoring();
		final URI structURI;

		if (existingStructButton.getSelection()) {
			structURI = structType != null ? EcoreUtil.getURI(structType) : null;
		} else {
			if (IdentifierVerifier.verifyIdentifier(structNameText.getText()).isPresent()) {
				status.merge(RefactoringStatus
						.createFatalErrorStatus(Messages.ConnectionsToStructWizardPage_InvalidStructName));
			}
			structURI = URI.createPlatformResourceURI(
					getConnectionsToStructRefactoring().getTypeLibrary().getProject().getFullPath() + File.separator
							+ TypeLibraryTags.TYPE_LIB_FOLDER_NAME + File.separator + structNameText.getText() + ".dtp", //$NON-NLS-1$
					true);
		}

		status.merge(refactoring.setUserConfig(structURI, sourceNameText.getText(), destinationNameText.getText(),
				conflictButton.getSelection()));

		setPageComplete(!status.hasError());
		final int severity = status.getSeverity();
		final String message = status.getMessageMatchingSeverity(severity);
		if (severity >= RefactoringStatus.INFO) {
			setMessage(message, severity);
		} else {
			setMessage("", NONE); //$NON-NLS-1$
		}
	}

	private ConnectionsToStructRefactoring getConnectionsToStructRefactoring() {
		return (ConnectionsToStructRefactoring) getRefactoring();
	}

}