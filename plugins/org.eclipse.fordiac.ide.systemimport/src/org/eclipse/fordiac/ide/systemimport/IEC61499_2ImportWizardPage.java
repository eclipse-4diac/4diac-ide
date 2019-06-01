/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral, Monika Wenger, 
 *   Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemimport;

import java.io.File;

import org.eclipse.fordiac.ide.ui.DirectoryChooserControl;
import org.eclipse.fordiac.ide.ui.FileChooserControl;
import org.eclipse.fordiac.ide.ui.IDirectoryChanged;
import org.eclipse.fordiac.ide.ui.IFileChanged;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The Class IEC61499_2ImportWizardPage.
 * 
 * @author gebenh, eisenmenger
 */
public class IEC61499_2ImportWizardPage extends WizardPage {

	private DirectoryChooserControl dcc;

	private FileChooserControl fcc;

	private boolean includeDefaultLibrary = false;

	public boolean isIncludeDefaultLibrary() {
		return includeDefaultLibrary;
	}

	protected IEC61499_2ImportWizardPage(String pageName) {
		super(pageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());

		initializeDialogUnits(parent);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createSysFileSourceGroup(composite);
		createLibDirectoryGroup(composite);

		setPageComplete(validatePage());
		setControl(composite);

	}

	private void createLibDirectoryGroup(Composite composite) {
		GridData stretch = new GridData();
		stretch.grabExcessHorizontalSpace = true;
		stretch.horizontalAlignment = SWT.FILL;
		{
			Label typeDirectory = new Label(composite, SWT.NONE);
			typeDirectory.setText("Specify the directory with the required FBTypes (e.g. the src directory of FBDK)");

			dcc = new DirectoryChooserControl(composite, SWT.NONE, "Types directory");
			dcc.setLayoutData(stretch);
			dcc.addDirectoryChangedListener(new IDirectoryChanged() {

				@Override
				public void directoryChanged(String newDirectory) {
					setPageComplete(validatePage());

				}
			});

		}

	}

	private void createSysFileSourceGroup(Composite composite) {
		GridData strechFcc = new GridData();
		strechFcc.grabExcessHorizontalSpace = true;
		strechFcc.horizontalAlignment = SWT.FILL;
		{
			Label systemFile = new Label(composite, SWT.NONE);
			systemFile.setText("Select a *.sys file");

			fcc = new FileChooserControl(composite, SWT.NONE, "System", new String[] { "System" },
					new String[] { "*.sys" }); //$NON-NLS-1$
			fcc.setLayoutData(strechFcc);
			fcc.addFileChangedListener(new IFileChanged() {

				@Override
				public void fileChanged(String newFile) {
					setPageComplete(validatePage());
				}
			});
		}
	}

	protected boolean validatePage() {
		if (fcc.getFile() == null || fcc.getFile().equals("")) { //$NON-NLS-1$
			setErrorMessage("No File choosen!");
			return false;
		}
		if (dcc.getDirectory() == null || dcc.getDirectory().equals("")) {
			setErrorMessage("No type directory choosen!");
			return false;
		}
		if (!new File(fcc.getFile()).exists()) {
			setErrorMessage("The selected File does not exist!");
			return false;
		}
		
		setErrorMessage(null);
		return true;
	}

	public File getSelectedSystemFile() {
		return new File(fcc.getFile());
	}
	
	public String getProjectName(){
		String[] val1 = fcc.getFile().split(File.separatorChar=='\\' ? "\\\\" : File.separator); //$NON-NLS-1$
		String[] val2 = val1[val1.length - 1].split("\\."); //$NON-NLS-1$
		
		return val2[0];
	}
}