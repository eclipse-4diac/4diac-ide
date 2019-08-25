/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;


public class SaveAsSubappWizardPage extends WizardNewFileCreationPage {

	private Button openTypeCheckbox;
	
	public SaveAsSubappWizardPage(String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
		setTitle(Messages.SaveAsSubApplicationTypeAction_WizardPageTitel); 
		setDescription(Messages.SaveAsSubApplicationTypeAction_WizardPageDescription);
		setAllowExistingResources(true);  // needed for correct duplicate type check
	}
	
	@Override
	public void createControl(final Composite parent) {
		
		super.createControl(parent);
		
		Composite composite = (Composite)getControl();
		
		openTypeCheckbox = new Button(composite, SWT.CHECK);
        openTypeCheckbox.setText(Messages.SaveAsSubApplicationTypeAction_WizardPageOpenType);
        openTypeCheckbox.setSelection(true);
	}
	
	public boolean getOpenType(){
    	return openTypeCheckbox.getSelection();
    }
	
	@Override
	protected String getNewFileLabel() {
        return Messages.SaveAsSubApplicationTypeAction_WizardPageNameLabel; 
    }

}
