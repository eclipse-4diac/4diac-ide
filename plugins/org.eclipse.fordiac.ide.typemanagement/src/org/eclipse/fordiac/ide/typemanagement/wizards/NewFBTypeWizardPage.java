/*******************************************************************************
 * Copyright (c) 2010 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH, 
 * 							 Johannes Kepler University          
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked type selection to a type list with description  
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewFBTypeWizardPage extends WizardNewFileCreationPage {
	private static final Pattern NAME_PATTERN = Pattern.compile("Name=\"\\w+\""); //$NON-NLS-1$
	private static final Pattern COMMENT_PATTERN = Pattern.compile("Comment=\"[\\w\\s]+\""); //$NON-NLS-1$
	
	private Button openTypeCheckbox;
	private int openTypeParentHeight = -1;
	private boolean openType = true;
	private TableViewer templateTable;
	
	private class TemplateInfo{
		File  templateFile;
		String templateName;
		String templateDescription;
		
		public TemplateInfo(File templateFile, String templateName, String templateDescription) {
			this.templateFile = templateFile;
			this.templateName = templateName;
			this.templateDescription = templateDescription;
		}
	}
	
	private TemplateInfo[] templateList;

	
	private class TypeTemplatesLabelProvider extends LabelProvider implements ITableLabelProvider{

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof TemplateInfo) {
				switch (columnIndex) {
				case 0:
					return ((TemplateInfo) element).templateName;
				case 1:
					return ((TemplateInfo) element).templateDescription;				
				default:
					break;
				}
			}
			return "";
		}
	}

	public NewFBTypeWizardPage(IStructuredSelection selection) {
		super("New type page", selection);
		this.setTitle("Create new Type"); 
        this.setDescription("Create a new type from a template type");
        this.setAllowExistingResources(true);  // needed for correct duplicate type check
        loadTypeTemplates(); 
	}

	@Override
	public void createControl(final Composite parent) {	
		super.createControl(parent);		
		Composite composite = (Composite)getControl();	
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	@Override
	protected boolean validatePage() {
		if((null == templateList) || (0 == templateList.length)){
			setErrorMessage("No type templates found! Please check the templates directory!");
			return false;
		}		
		if (super.getFileName().isEmpty()) {
			setErrorMessage("Empty Typename is not valid!");
			return false;
		}		
		
		//use super.getFileName here to get the type name without extension
		String errorMessage = IdentifierVerifyer.isValidIdentifierWithErrorMessage(super.getFileName());	
		if (null != errorMessage) {
			setErrorMessage(errorMessage);
			return false;
		}
		
		if (null == getTemplate()) {
			setErrorMessage("No type selected!");
			return false;
		}		

		setErrorMessage(null);
		return super.validatePage();
	}
	
	public File getTemplate() {
		if(templateTable.getSelection() instanceof StructuredSelection) {
			Object selection = templateTable.getStructuredSelection().getFirstElement();
			return (selection instanceof TemplateInfo) ? ((TemplateInfo)selection).templateFile : null;
		}
		return null;
	}

	@Override
    protected String getNewFileLabel() {
        return "Type name:"; 
    }
	
    @Override
    public String getFileName() {
    	String retval = super.getFileName();	
    	if(null != getTemplate()){
    		String splited[] = getTemplate().getName().split("\\."); //$NON-NLS-1$
    		if(splited.length == 2){
    			retval= retval + "." + splited[1]; //$NON-NLS-1$
    		}
    	}
    	return retval;
    }
    
    public boolean getOpenType(){
    	return openType;
    }
    
    @Override
	protected void createAdvancedControls(Composite parent) {
    	createTemplateTypeSelection(parent);
    	super.createAdvancedControls(parent);
    }

	private void createTemplateTypeSelection(Composite parent) {
		Label fbTypeTypeLabel = new Label(parent, SWT.NONE);
		fbTypeTypeLabel.setText("Select type: ");
		fbTypeTypeLabel.setFont(parent.getFont());	
		
		templateTable = new TableViewer(parent, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridDataVersionViewer.heightHint = 150;
		templateTable.getControl().setLayoutData(gridDataVersionViewer);
		
		Table table = templateTable.getTable();
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		
		TableColumn tc = new TableColumn(templateTable.getTable(), SWT.LEFT);
	    tc.setText("Name");
	    
	    tc = new TableColumn(templateTable.getTable(), SWT.LEFT);
	    tc.setText("Description");
	    
	    TableLayout tabLayout = new TableLayout();
	    tabLayout.addColumnData(new ColumnWeightData(1, 30));
	    tabLayout.addColumnData(new ColumnWeightData(2, 70));
		table.setLayout(tabLayout);
		
	    templateTable.setContentProvider(new ArrayContentProvider());
	    templateTable.setLabelProvider(new TypeTemplatesLabelProvider());
	    
	    templateTable.setInput(templateList);
	    
	    templateTable.addSelectionChangedListener(ev -> handleEvent(null));	
	}

	private void loadTypeTemplates() {
		String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		File templateFolder = new File(templateFolderPath + File.separatorChar + "template");
		FileFilter ff = createTemplatesFileFilter();
		if (templateFolder.isDirectory()) {
			File[] files = templateFolder.listFiles(ff);
			if(null != files){
				Arrays.sort(files);
				templateList = new TemplateInfo[files.length];
				for(int i = 0; i < files.length; i++){ 
					templateList[i] =  createTemplateFileInfo(files[i]); 
				}
			}
		}
	}

	@SuppressWarnings("static-method")  //this method is need to allow sub-classes to override it with specific filters
	protected FileFilter createTemplatesFileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toUpperCase().endsWith(".FBT") //$NON-NLS-1$
						|| pathname.getName().toUpperCase().endsWith(".ADP") //$NON-NLS-1$
						|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT);
			}
		};
	}
	
	private TemplateInfo createTemplateFileInfo(File f) {
		Scanner scanner;
		String name = f.getName();
		String description = ""; //$NON-NLS-1$
		try {
			// we need a new scanner 
			scanner = new Scanner(f);
			name = scanner.findWithinHorizon(NAME_PATTERN, 0); 
			name = name.substring(6, name.length() - 1);
			scanner.close();
			//we need a new scanner as name and comment may be in arbitrary order
			scanner = new Scanner(f);
			description = scanner.findWithinHorizon(COMMENT_PATTERN, 0); 
			description = description.substring(9, description.length() - 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 		
		return new TemplateInfo(f, name, description);
	}

	
	@Override
	protected void handleAdvancedButtonSelect() {
		Shell shell = getShell();
		Point shellSize = shell.getSize();
		Composite composite = (Composite) getControl();

		if (openTypeCheckbox != null) {
			openTypeCheckbox.dispose();
			openTypeCheckbox = null;
			shell.setSize(shellSize.x, shellSize.y - openTypeParentHeight);
		} else {
			openTypeCheckbox = createOpenTypeGroup(composite);
			if (openTypeParentHeight == -1) {
				Point groupSize = openTypeCheckbox.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				openTypeParentHeight = groupSize.y;
			}
			shell.setSize(shellSize.x, shellSize.y + openTypeParentHeight);
		}
		super.handleAdvancedButtonSelect();
	}

	private Button createOpenTypeGroup(Composite parent) {
		openTypeCheckbox = new Button(parent, SWT.CHECK);
        openTypeCheckbox.setText("Open type for editing when done");
        openTypeCheckbox.setSelection(true);setPageComplete(validatePage());
        openTypeCheckbox.addListener( SWT.Selection, ev -> openType = openTypeCheckbox.getSelection() );
        return openTypeCheckbox;
	}
	
}
