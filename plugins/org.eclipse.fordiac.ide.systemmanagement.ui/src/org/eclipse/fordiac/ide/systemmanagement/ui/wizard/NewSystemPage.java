/*******************************************************************************
 * Copyright (c) 2014  fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewSystemPage extends WizardNewProjectCreationPage {

	private static final String APPLICATION_NAME_PREFIX = "App"; //$NON-NLS-1$

	private boolean importDefaultPalette = true;
	private boolean openApplication = true;

	private Button advancedButton;
	
	private Text applicationName;

	/**
	 * Height of the "advanced" linked resource group. Set when the advanced
	 * group is first made visible.
	 */
	private int linkedResourceGroupHeight = -1;

	/** Container for the advanced section in the creation wizard
	 * 
	 */
	private Composite advancedGroupContainer;
	
	private Composite advancedGroupParent;
	
	// flag indicating if the user changed the initial application name if yes do not update the name any more
	private boolean appNameManuallyChanged = false;
	
	private boolean blockListeners = false;
	
	private Listener applicationNameModifyListener = e -> {
		if(!blockListeners) {
			appNameManuallyChanged = true;
			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};

	/**
	 * Creates a new project creation wizard page.
	 * 
	 * @param pageName
	 *            the name of this page
	 */
	public NewSystemPage(String pageName) {
		super(pageName);
		setPageComplete(false);
	}


	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		createApplicationNameGroup((Composite) getControl());
		
		createAdvancedControls( (Composite) getControl());
		
		Composite composite = (Composite) getControl();
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		setControl(composite);
		Dialog.applyDialogFont(composite);
	}
	
	private void createApplicationNameGroup(Composite parent) {
		Composite applicationNameGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        applicationNameGroup.setLayout(layout);
        applicationNameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label applicationLabel = new Label(applicationNameGroup, SWT.NONE);
        applicationLabel.setText(Messages.NewSystemWizard_InitialApplicationName);
        applicationLabel.setFont(parent.getFont());

        // new project name entry field
        applicationName = new Text(applicationNameGroup, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        applicationName.setLayoutData(data);
        applicationName.setFont(parent.getFont());
        applicationName.addListener(SWT.Modify, applicationNameModifyListener);
	}


	@Override
	protected boolean validatePage() {
		if (!IdentifierVerifyer.isValidIdentifier(getProjectName())) {
			setErrorMessage(Messages.SystemNameNotValid);
			return false;
		}	
		if(!SystemManager.isUniqueSystemName(getProjectName())) {
			setErrorMessage(Messages.SystemNameAlreadyUsed);
			return false;
		}
		if(!appNameManuallyChanged) {
			blockListeners = true;
			applicationName.setText(getProjectName() + APPLICATION_NAME_PREFIX );
			blockListeners = false;
		} 
		if(!IdentifierVerifyer.isValidIdentifier(getInitialApplicationName())){
			return false;
		}
		return super.validatePage();
	}

	public String getInitialApplicationName() {
		return applicationName.getText();
	}
	
	public boolean getOpenApplication() {
		return openApplication;
	}


	private void createAdvancedControls(Composite parent) {
		advancedGroupParent = new Composite(parent, SWT.NONE);
		advancedGroupParent.setFont(parent.getFont());
		advancedGroupParent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		advancedGroupParent.setLayout(layout);

		advancedButton = new Button(advancedGroupParent, SWT.PUSH);
		advancedButton.setFont(advancedGroupParent.getFont());
		advancedButton.setText(Messages.NewSystemWizard_ShowAdvanced);
		GridData data = setButtonLayoutData(advancedButton);
		data.horizontalAlignment = GridData.BEGINNING;
		advancedButton.setLayoutData(data);
		advancedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAdvancedButtonSelect();
			}
		});
	}


	/**
	 * Shows/hides the advanced option widgets.
	 */
	protected void handleAdvancedButtonSelect() {
		if (advancedGroupContainer != null) {
			advancedGroupContainer.dispose();
			advancedGroupContainer = null;
			advancedButton.setText(Messages.NewSystemWizard_ShowAdvanced);
		} else {
			createAdvancedGroup();
			if (linkedResourceGroupHeight == -1) {
				Point groupSize = advancedGroupContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT,
						true);
				linkedResourceGroupHeight = groupSize.y;
			}
			advancedButton.setText(Messages.NewSystemWizard_HideAdvanced);
		}
		Composite compo = (Composite) getControl();
		compo.layout();
	}

	private void createAdvancedGroup() {
		advancedGroupContainer = new Composite(advancedGroupParent, SWT.NONE);
		advancedGroupContainer.setLayout(new GridLayout());
		advancedGroupContainer.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));		

		Button importDefaultPaletteSB = new Button(advancedGroupContainer, SWT.CHECK);
		importDefaultPaletteSB.setSelection(importDefaultPalette);
		importDefaultPaletteSB.setText(Messages.PaletteManagementPage_LABEL_DefaultTypeLibrary);
		importDefaultPaletteSB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				importDefaultPalette = true;
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				importDefaultPalette = false;
			}
		});

		Button openApplicationCheckbox = new Button(advancedGroupContainer, SWT.CHECK);
		openApplicationCheckbox.setText(Messages.NewApplicationPage_OpenApplicationForEditing);
		openApplicationCheckbox.setSelection(openApplication);
		openApplicationCheckbox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				openApplication = true;
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				openApplication = false;
			}
		});
	}


	public boolean importDefaultPalette() {
		return importDefaultPalette;
	}

}
