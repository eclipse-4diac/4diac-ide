/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.ui.widgets.PackageSelectionProposalProvider;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class ChangePackageNameRefactoringWizardPage extends UserInputWizardPage {

	private final ChangePackageNameRefactoringProcessor processor;

	private Text packageNameText;

	public ChangePackageNameRefactoringWizardPage(final ChangePackageNameRefactoringProcessor processor) {
		super(processor.getProcessorName());
		this.processor = processor;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		initializeDialogUnits(composite);
		createPackageNameField(composite);

		validatePage();
		setControl(composite);
	}

	private void createPackageNameField(final Composite parent) {
		final Label packageNameLabel = new Label(parent, SWT.NONE);
		packageNameLabel.setText(Messages.ChangePackageNameRefactoringWizardPage_Name);
		packageNameText = new Text(parent, SWT.BORDER);
		packageNameText.setText(Objects.requireNonNullElse(processor.getNewPackageName(), "")); //$NON-NLS-1$
		packageNameText.addModifyListener(e -> {
			processor.setNewPackageName(packageNameText.getText());
			validatePage();
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(packageNameText);

		final ContentAssistCommandAdapter packageNameProposalAdapter = new ContentAssistCommandAdapter(packageNameText,
				new TextContentAdapter(), new PackageSelectionProposalProvider(processor::getTypeLibrary), null, null,
				true);
		packageNameProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			packageNameText.setFocus();
		}
		super.setVisible(visible);
	}

	private void validatePage() {
		try {
			final NullProgressMonitor monitor = new NullProgressMonitor();
			final RefactoringStatus status = processor.checkInitialConditions(monitor);
			status.merge(processor.checkFinalConditions(monitor, new CheckConditionsContext()));
			setPageComplete(status);
		} catch (OperationCanceledException | CoreException e) {
			setPageComplete(RefactoringStatus.createFatalErrorStatus(e.getMessage()));
		}
	}
}
