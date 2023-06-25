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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.internal.ui.refactoring.RefactoringUIMessages;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RenameTypeRefactoringInputPage extends UserInputWizardPage {

	private Text newName;
	private final Refactoring refactoring;

	public RenameTypeRefactoringInputPage(final String name, final Refactoring refactoring) {
		super(name);
		this.refactoring = refactoring;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		final Label label = new Label(composite, SWT.NONE);
		label.setText(RefactoringUIMessages.RenameResourceWizard_name_field_label);
		label.setLayoutData(new GridData());

		newName = createNameField(composite);
		final String typeName = getRenameTypeRefactoring().getTypeEntry().getTypeName();
		final String fileExtension = getRenameTypeRefactoring().getTypeEntry().getFile().getFileExtension();

		newName.setText(typeName);
		newName.addModifyListener(event -> handleInputChanged());
		newName.setFocus();
		newName.selectAll();

		handleInputChanged();
	}

	private Text createNameField(final Composite result) {
		final Text field = new Text(result, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return field;
	}

	void handleInputChanged() {
		final RefactoringStatus status = new RefactoringStatus();
		status.merge(getRenameTypeRefactoring().setAndValidateTypeName(newName.getText()));
		setPageComplete(!status.hasError());

		final int severity = status.getSeverity();
		final String message = status.getMessageMatchingSeverity(severity);
		if (severity >= RefactoringStatus.INFO) {
			setMessage(message, severity);
		} else {
			setMessage("", NONE); //$NON-NLS-1$
		}
	}

	private RenameTypeRefactoring getRenameTypeRefactoring() {
		return (RenameTypeRefactoring) refactoring;
	}

}