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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - moved open type and replace soure with type into advanced
 *                 section
 *   Bianca Wiesmayr - copied SaveAsSubappWizardPage and adapted it for structs
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

// TODO extract Strings to messages.properties
public class SaveAsStructWizardPage extends WizardNewFileCreationPage {

	private static final String STORE_OPEN_TYPE_ID = "SUBAPP_SECTION.STORE_OPEN_TYPE_ID"; //$NON-NLS-1$
	private static final String STORE_REPLACE_SOURCE_ID = "SUBAPP_SECTION.STORE_REPLACE_SOURCE_ID"; //$NON-NLS-1$

	private boolean openType = true;
	private boolean replaceSource = true;

	private Composite advancedComposite;
	private int advancedCompositeHeight = -1;

	public SaveAsStructWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle("Save element(s) as Structured Type");
		setDescription(
				"Store new Type in library. Specify desired path to create missing folders automatically");
		setAllowExistingResources(true); // needed for correct duplicate type check
	}

	public boolean getOpenType() {
		return openType;
	}

	public boolean getReplaceSource() {
		return replaceSource;
	}

	@Override
	protected String getNewFileLabel() {
		return "Type name:";
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		restoreWidgetValues();
	}

	public void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (null != settings) {
			settings.put(STORE_OPEN_TYPE_ID, openType);
			settings.put(STORE_REPLACE_SOURCE_ID, replaceSource);
		}
	}

	private void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (null != settings) {
			openType = settings.getBoolean(STORE_OPEN_TYPE_ID);
			replaceSource = settings.getBoolean(STORE_REPLACE_SOURCE_ID);
		}
	}

	@Override
	protected void handleAdvancedButtonSelect() {
		Shell shell = getShell();
		Point shellSize = shell.getSize();
		Composite composite = (Composite) getControl();

		if (null != advancedComposite) {
			advancedComposite.dispose();
			advancedComposite = null;
			shell.setSize(shellSize.x, shellSize.y - advancedCompositeHeight);
		} else {
			advancedComposite = createAdvancedGroup(composite);
			if (-1 == advancedCompositeHeight) {
				Point groupSize = advancedComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				advancedCompositeHeight = groupSize.y;
			}
			shell.setSize(shellSize.x, shellSize.y + advancedCompositeHeight);
		}
		super.handleAdvancedButtonSelect();
	}

	private Composite createAdvancedGroup(Composite parent) {
		Font font = parent.getFont();
		initializeDialogUnits(parent);
		// top level group
		Composite groupComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		groupComposite.setLayout(layout);
		groupComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.FILL_HORIZONTAL));
		groupComposite.setFont(font);

		Button openTypeCheckbox = new Button(groupComposite, SWT.CHECK);
		openTypeCheckbox.setText(Messages.SaveAsSubApplicationTypeAction_WizardPageOpenType);
		openTypeCheckbox.setSelection(openType);
		openTypeCheckbox.addListener(SWT.Selection, ev -> openType = openTypeCheckbox.getSelection());

		Button replaceSourceSubapp = new Button(parent, SWT.CHECK);
		replaceSourceSubapp.setText("Convert source element(s)");
		replaceSourceSubapp.setSelection(replaceSource);
		replaceSourceSubapp.addListener(SWT.Selection, ev -> replaceSource = replaceSourceSubapp.getSelection());

		return groupComposite;
	}

}
