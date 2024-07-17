/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RenameElementRefactoringWizardPage extends UserInputWizardPage {

	private final RenameElementRefactoringProcessor processor;

	private Text nameText;

	public RenameElementRefactoringWizardPage(final RenameElementRefactoringProcessor processor) {
		super(processor.getProcessorName());
		this.processor = processor;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		initializeDialogUnits(composite);
		createNameField(composite);

		setPageComplete(false);
		setControl(composite);
	}

	protected void createNameField(final Composite parent) {
		final Label nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText(Messages.RenameElementRefactoringWizardPage_Name);
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(Objects.requireNonNullElse(processor.getNewName(), "")); //$NON-NLS-1$
		nameText.addModifyListener(e -> {
			processor.setNewName(nameText.getText());
			validatePage();
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(nameText);
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			nameText.setFocus();
		}
		super.setVisible(visible);
	}

	protected void validatePage() {
		try {
			setPageComplete(processor.checkInitialConditions(new NullProgressMonitor()));
		} catch (OperationCanceledException | CoreException e) {
			setPageComplete(RefactoringStatus.createFatalErrorStatus(e.getMessage()));
		}
	}
}
