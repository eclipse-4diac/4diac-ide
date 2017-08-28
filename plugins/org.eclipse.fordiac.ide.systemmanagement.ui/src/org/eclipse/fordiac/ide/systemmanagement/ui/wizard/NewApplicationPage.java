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

import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
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
		if (!SystemManager.isValidAppName(text, getSelectedSystem())) {
			return Messages.NewApplicationPage_ErrorMessageInvalidAppName;
		}
		return null;
	}

	public Boolean getOpenApplication() {
		return openApplicationCheckbox.getSelection();
	}
}
