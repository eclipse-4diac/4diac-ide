/*******************************************************************************
 * Copyright (c) 2008, 2010, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class NewApplicationPage extends NewElementPage {
	
	private Button openApplicationCheckbox;
	
	

	protected NewApplicationPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		
		Composite container = (Composite) getControl();
		openApplicationCheckbox = new Button(container, SWT.CHECK);
		openApplicationCheckbox.setText(Messages.NewApplicationPage_OpenApplicationForEditing);
		openApplicationCheckbox.setSelection(true);
		
	}
	
	@Override
	public String validateElementName(String text) {
		if (!isValidAppName(text, getSelectedSystem())) {
			return Messages.NewApplicationPage_ErrorMessageInvalidAppName;
		}
		return null;
	}

	public boolean getOpenApplication() {
		return openApplicationCheckbox.getSelection();
	}
	
	/**
	 * Checks if is valid app name.
	 * 
	 * @param appNameProposal the proposed new name for the application
	 * @param selectedSystem  the selected system
	 * 
	 * @return true, if is valid app name
	 */
	private static boolean isValidAppName(final String appNameProposal, final AutomationSystem selectedSystem) {
		if(!IdentifierVerifyer.isValidIdentifier(appNameProposal)){
			return false;
		}
		for (Application app : selectedSystem.getApplication()){
			if (appNameProposal.equalsIgnoreCase(app.getName())) {
				return false;
			}
		}
		return true;
	}
}
