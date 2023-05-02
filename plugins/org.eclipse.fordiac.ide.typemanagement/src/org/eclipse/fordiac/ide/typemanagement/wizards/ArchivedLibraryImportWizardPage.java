/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ArchivedLibraryImportWizardPage extends WizardPage {
	
	private Composite container;

	protected ArchivedLibraryImportWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
    	container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        
        createLabel("Test label");
        
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
	}
	
	private Label createLabel(String labelText) {
    	Label label = new Label(container, SWT.NONE);
        label.setText(labelText);
    	return label;
    }

}
