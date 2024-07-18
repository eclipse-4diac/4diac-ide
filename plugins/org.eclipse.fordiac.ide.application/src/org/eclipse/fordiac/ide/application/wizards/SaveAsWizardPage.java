/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *               2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais - initial API and implementation and/or initial documentation.
 *   			  Mostly copied from SaveAsStructWizard and SaveAsSubappWizard.
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.util.Optional;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class SaveAsWizardPage extends WizardNewFileCreationPage {

	private static final String STORE_OPEN_TYPE_ID = "SUBAPP_SECTION.STORE_OPEN_TYPE_ID"; //$NON-NLS-1$
	private static final String STORE_REPLACE_SOURCE_ID = "SUBAPP_SECTION.STORE_REPLACE_SOURCE_ID"; //$NON-NLS-1$

	private boolean openType = true;

	private Composite advancedComposite;
	private int advancedCompositeHeight = -1;

	private Button replaceSourceSubapp;
	private final String fileLabel;
	private final String checkBoxText;
	private final String replaceSourceText;

	private SaveAsWizardPage(final String pageName, final IStructuredSelection selection, final String title,
			final String description, final String fileLabel, final String checkBoxText,
			final String replaceSourceText) {
		super(pageName, selection);
		setTitle(title);
		setDescription(description);
		setAllowExistingResources(true); // needed for correct duplicate type
		this.fileLabel = fileLabel;
		this.checkBoxText = checkBoxText;
		this.replaceSourceText = replaceSourceText;
	}

	public boolean getOpenType() {
		return openType;
	}

	public boolean getReplaceSource() {
		return replaceSourceSubapp.getSelection();
	}

	@Override
	protected String getNewFileLabel() {
		return fileLabel;
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		restoreWidgetValues();
	}

	private void createPackageNameContainer(final Composite parent) {
		final Composite packageContainer = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(packageContainer);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(packageContainer);

		final Label enterPackageNameLabel = new Label(packageContainer, SWT.NONE);
		enterPackageNameLabel.setText("Enter package name: ");

		final Text packageText = new Text(packageContainer, SWT.BORDER);
		packageText.setEnabled(true);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).hint(250, SWT.DEFAULT)
				.applyTo(packageText);
	}

	@Override
	protected void createAdvancedControls(final Composite parent) {
		createPackageNameContainer(parent);
		if (replaceSourceText != null) {
			createReplaceSourceEntry(parent);
		}
		super.createAdvancedControls(parent);
	}

	public void saveWidgetValues() {
		final IDialogSettings settings = getDialogSettings();
		if (null != settings) {
			settings.put(STORE_OPEN_TYPE_ID, openType);
			settings.put(STORE_REPLACE_SOURCE_ID, replaceSourceSubapp.getSelection());
		}
	}

	private void restoreWidgetValues() {
		final IDialogSettings settings = getDialogSettings();
		if (null != settings) {
			openType = settings.getBoolean(STORE_OPEN_TYPE_ID);
			replaceSourceSubapp.setSelection(settings.getBoolean(STORE_REPLACE_SOURCE_ID));
		}
	}

	@Override
	protected boolean validatePage() {
		// use super.getFileName here to get the type name without extension
		final Optional<String> errorMessage = IdentifierVerifier.verifyIdentifier(super.getFileName());
		if (errorMessage.isPresent()) {
			setErrorMessage(errorMessage.get());
			return false;
		}

		return super.validatePage();
	}

	@Override
	protected void handleAdvancedButtonSelect() {
		final Shell shell = getShell();
		final Point shellSize = shell.getSize();
		final Composite composite = (Composite) getControl();

		if (null != advancedComposite) {
			advancedComposite.dispose();
			advancedComposite = null;
			shell.setSize(shellSize.x, shellSize.y - advancedCompositeHeight);
		} else {
			advancedComposite = createAdvancedGroup(composite);
			if (-1 == advancedCompositeHeight) {
				final Point groupSize = advancedComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				advancedCompositeHeight = groupSize.y;
			}
			shell.setSize(shellSize.x, shellSize.y + advancedCompositeHeight);
		}
		super.handleAdvancedButtonSelect();
	}

	private void createReplaceSourceEntry(final Composite parent) {
		replaceSourceSubapp = new Button(parent, SWT.CHECK);
		replaceSourceSubapp.setText(replaceSourceText);
	}

	private Composite createAdvancedGroup(final Composite parent) {
		final Font font = parent.getFont();
		initializeDialogUnits(parent);
		// top level group
		final Composite groupComposite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		groupComposite.setLayout(layout);
		groupComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.FILL_HORIZONTAL));
		groupComposite.setFont(font);

		final Button openTypeCheckbox = new Button(groupComposite, SWT.CHECK);
		openTypeCheckbox.setText(checkBoxText);
		openTypeCheckbox.setSelection(openType);
		openTypeCheckbox.addListener(SWT.Selection, ev -> openType = openTypeCheckbox.getSelection());

		return groupComposite;
	}

	public static SaveAsWizardPage createSaveAsStructWizardPage(final String pageName,
			final IStructuredSelection selection) {
		return new SaveAsWizardPage(pageName, selection, Messages.SaveAsStructWizardPage_WizardPageTitle,
				Messages.SaveAsStructWizardPage_WizardPageDescription, Messages.SaveAsStructWizardPage_TypeName,
				Messages.SaveAsSubApplicationTypeAction_WizardPageOpenType,
				Messages.SaveAsStructWizardPage_ConvertSourceElements);
	}

	public static SaveAsWizardPage createSaveAsSubAppWizardPage(final String pageName,
			final IStructuredSelection selection) {
		return new SaveAsWizardPage(pageName, selection, Messages.SaveAsSubApplicationTypeAction_WizardPageTitle,
				Messages.SaveAsSubApplicationTypeAction_WizardPageDescription,
				Messages.SaveAsSubApplicationTypeAction_WizardPageNameLabel,
				Messages.SaveAsSubApplicationTypeAction_WizardPageOpenType,
				Messages.SaveAsSubappHandler_ReplaceDialogText);
	}

	public static SaveAsWizardPage createSaveAsStructTypeWizardPage(final String pageName,
			final IStructuredSelection selection) {
		return new SaveAsWizardPage(pageName, selection, Messages.SaveAsWizardPage_SaveAsStructType_WizardPageTitle,
				Messages.SaveAsWizardPage_SaveAsStructType_Description,
				Messages.SaveAsWizardPage_SaveAsStructType_PageName,
				Messages.SaveAsSubApplicationTypeAction_WizardPageOpenType, null);
	}
}
