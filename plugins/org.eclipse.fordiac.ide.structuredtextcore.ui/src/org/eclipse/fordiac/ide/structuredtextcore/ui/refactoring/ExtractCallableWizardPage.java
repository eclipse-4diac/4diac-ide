/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExtractCallableWizardPage extends UserInputWizardPage {

	private final ExtractCallableRefactoring refactoring;

	private Text nameText;

	public ExtractCallableWizardPage(final ExtractCallableRefactoring refactoring) {
		super(refactoring.getName());
		this.refactoring = refactoring;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		initializeDialogUnits(composite);
		createNameField(composite);

		setControl(composite);
	}

	protected void createNameField(final Composite parent) {
		final Label nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText(Messages.ExtractCallableWizardPage_NameField);
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(refactoring.getCallableName());
		nameText.addModifyListener(e -> {
			refactoring.setCallableName(nameText.getText());
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
			setPageComplete(refactoring.checkAllConditions(new NullProgressMonitor()));
		} catch (OperationCanceledException | CoreException e) {
			setPageComplete(RefactoringStatus.createFatalErrorStatus(e.getMessage()));
		}
	}
}
