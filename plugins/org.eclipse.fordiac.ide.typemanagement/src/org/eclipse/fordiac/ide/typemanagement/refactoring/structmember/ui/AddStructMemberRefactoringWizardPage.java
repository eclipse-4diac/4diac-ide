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
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberConfiguration;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberRefactoring;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

final class AddStructMemberRefactoringWizardPage extends UserInputWizardPage {
	private final AddStructMemberRefactoring refactoring;
	private final List<String> memberNames;

	private Text nameText;
	private Text commentText;
	private Text typeText;
	private Combo positionCombo;

	AddStructMemberRefactoringWizardPage(final AddStructMemberRefactoring refactoring) {
		super(Messages.AddStructMemberWizardPage_Title);
		this.refactoring = refactoring;
		memberNames = refactoring.getMemberNames();
		setTitle(refactoring.getName());
		setDescription(Messages.AddStructMemberWizardPage_Description);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(composite);
		initializeDialogUnits(composite);

		createNameField(composite);
		createCommentField(composite);
		createTypeField(composite);
		createPositionField(composite);

		setControl(composite);
		updateConfiguration();
	}

	private void createNameField(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.AddStructMemberWizardPage_Name);
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(refactoring.getConfiguration().memberName());
		nameText.addModifyListener(_ -> updateConfiguration());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(nameText);
	}

	private void createCommentField(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.AddStructMemberWizardPage_Comment);
		commentText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		commentText.setText(refactoring.getConfiguration().comment());
		commentText.addModifyListener(_ -> updateConfiguration());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.hint(SWT.DEFAULT, 3 * commentText.getLineHeight()).applyTo(commentText);
	}

	private void createTypeField(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.AddStructMemberWizardPage_Type);
		typeText = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		typeText.setText(refactoring.getConfiguration().memberTypeName());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(typeText);

		final Button selectTypeButton = new Button(parent, SWT.PUSH);
		selectTypeButton.setText(Messages.AddStructMemberWizardPage_SelectType);
		selectTypeButton.setEnabled(refactoring.isTypeSelectionRequired());
		selectTypeButton.addListener(SWT.Selection, _ -> selectType());
	}

	private void selectType() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(),
				DataTypeSelectionTreeContentProvider.INSTANCE, new DataTypeTreeSelectionDialog.TreeNodeLabelProvider(),
				candidate -> candidate instanceof final DataType dataType && refactoring.isCompatibleMemberType(dataType));
		dialog.setTitle(Messages.AddStructMemberWizardPage_SelectTypeTitle);
		dialog.setMessage(Messages.AddStructMemberWizardPage_SelectTypeMessage);
		dialog.setInput(refactoring.getTypeLibrary());
		if (dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory() && node.getType() instanceof DataType) {
			typeText.setText(node.getFullName());
			updateConfiguration();
		}
	}

	private void createPositionField(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.AddStructMemberWizardPage_Position);
		positionCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		positionCombo.add(Messages.AddStructMemberWizardPage_Append);
		memberNames.stream().map(name -> MessageFormat.format(Messages.AddStructMemberWizardPage_Before, name))
				.forEach(positionCombo::add);
		positionCombo.select(0);
		positionCombo.addListener(SWT.Selection, _ -> updateConfiguration());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(positionCombo);
	}

	private void updateConfiguration() {
		if (nameText == null || commentText == null || typeText == null || positionCombo == null) {
			return;
		}
		final String insertBefore = positionCombo.getSelectionIndex() > 0
				? memberNames.get(positionCombo.getSelectionIndex() - 1)
				: null;
		final AddStructMemberConfiguration current = refactoring.getConfiguration();
		setPageComplete(refactoring.setConfiguration(new AddStructMemberConfiguration(nameText.getText(),
				commentText.getText(), typeText.getText(), current.arraySize(), insertBefore)));
		setTitle(refactoring.getName());
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			nameText.setFocus();
			nameText.selectAll();
		}
		super.setVisible(visible);
	}
}
