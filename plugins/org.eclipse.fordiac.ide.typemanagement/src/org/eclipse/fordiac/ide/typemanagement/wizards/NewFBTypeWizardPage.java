/*******************************************************************************
 * Copyright (c) 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewFBTypeWizardPage extends WizardNewFileCreationPage {

	private Combo fbTypeCombo;
	private ArrayList<File> templates;
	private Button openTypeCheckbox;

	public NewFBTypeWizardPage(IStructuredSelection selection) {
		super("New type page", selection);
		this.setTitle("Create new Type"); 
        this.setDescription("Create a new type from a template type");
        this.setAllowExistingResources(true);  // needed for correct duplicate type check
	}

	@Override
	public void createControl(final Composite parent) {		
		super.createControl(parent);		
		Composite composite = (Composite)getControl();	
		openTypeCheckbox = new Button(composite, SWT.CHECK);
        openTypeCheckbox.setText("Open type for editing when done");
        openTypeCheckbox.setSelection(true);
		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	protected boolean validatePage() {
		String errorMessage = "";	
		if((null != templates) && (templates.size() == 0)){
			setErrorMessage("No type templates found! Please check the templates directory!");
			return false;
		}		
		if (getFileName().isEmpty()) {
			setErrorMessage("Empty Typename is not valid!");
			return false;
		}		
		//use super.getFileName here to get the type name without extension
		if ((errorMessage = IdentifierVerifyer.isValidIdentifierWithErrorMessage(super.getFileName())) != null) {
			setErrorMessage(errorMessage);
			return false;
		}
		setErrorMessage(null);
		return super.validatePage();
	}

	public String getType() {
		return fbTypeCombo.getText();
	}
	
	public File getTemplate() {
		return templates.get(fbTypeCombo.getSelectionIndex());
	}

    protected String getNewFileLabel() {
        return "Type name:"; 
    }
    
    public String getFileName() {
    	String retval = super.getFileName();	
    	if((null != fbTypeCombo) && (null != fbTypeCombo.getText())){
    		String splited[] = fbTypeCombo.getText().split("\\.");
    		if(splited.length == 2){
    			retval= retval + "." + splited[1];
    		}
    	}
    	return retval;
    }
    
    public boolean getOpenType(){
    	return openTypeCheckbox.getSelection();
    }
    
    protected void createAdvancedControls(Composite parent) {
    	createTemplateTypeSelection(parent);
    	super.createAdvancedControls(parent);
    }

	private void createTemplateTypeSelection(Composite parent) {
		Font font = parent.getFont();		
		Composite typeGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		typeGroup.setLayout(layout);
		typeGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		typeGroup.setFont(font);	
		Label fbTypeTypeLabel = new Label(typeGroup, SWT.NONE);
		fbTypeTypeLabel.setText("Type: ");
		fbTypeTypeLabel.setFont(font);	
		String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		File templateFolder = new File(templateFolderPath + File.separatorChar + "template");
		templates = new ArrayList<File>();
		FileFilter ff = createTemplatesFileFilter();
		if (templateFolder.isDirectory()) {
			File[] files = templateFolder.listFiles(ff);
			if(null != files){
				templates.addAll(Arrays.asList(files));
				Collections.sort(templates);
			}
		} 
		fbTypeCombo = new Combo(typeGroup, SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		fbTypeCombo.setLayoutData(data);
		fbTypeCombo.setFont(font);
		for (File file : templates) {
			fbTypeCombo.add(file.getName());
		}		
		fbTypeCombo.select(0);
		fbTypeCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setPageComplete(validatePage());
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});	
	}

	protected FileFilter createTemplatesFileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toUpperCase().endsWith(".FBT")
						|| pathname.getName().toUpperCase().endsWith(".ADP")
						|| pathname.getName().toUpperCase().endsWith(TypeLibrary.SUBAPP_TYPE_FILE_ENDING_WITH_DOT);
			}
		};
	}
}
