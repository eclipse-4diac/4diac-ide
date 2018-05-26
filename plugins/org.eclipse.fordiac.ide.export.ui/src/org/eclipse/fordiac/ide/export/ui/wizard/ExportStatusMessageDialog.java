/*******************************************************************************
 * Copyright (c) 2012, 2014,2016 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ExportStatusMessageDialog extends ErrorDialog {

	private final List<String> warnings;
	private final List<String> errors;

	private StyledText text;
	private String newLine = "";
		
	public ExportStatusMessageDialog(Shell parentShell, final List<String> warnings, final List<String> errors) {
		super(parentShell, "4DIAC Type Export Errors", "During Type export the following issues have been identified:", 
				new Status(IStatus.INFO, "pluginid", errors.size() + " errors and " + warnings.size() + " warnings!"), 
				IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
		 
		this.warnings = warnings;
		this.errors = errors;		
	}

	@Override
	protected Control createMessageArea(Composite parent) {
		Control retval = super.createMessageArea(parent);
		
		new Label(parent, SWT.NONE);  //simple placeholder label
		
		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout());
		GridData fillBoth = new GridData();
		fillBoth.grabExcessHorizontalSpace = true;
		fillBoth.grabExcessVerticalSpace = true;
		fillBoth.horizontalAlignment = GridData.FILL;
		fillBoth.verticalAlignment = GridData.FILL;

		main.setLayoutData(fillBoth);

		GridData fillText = new GridData();
		fillText.grabExcessHorizontalSpace = true;
		fillText.grabExcessVerticalSpace = true;
		fillText.horizontalAlignment = GridData.FILL;
		fillText.verticalAlignment = GridData.FILL;

		text = new StyledText(main, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		text.setLayoutData(fillText);
		
		printMessages();
		
		return retval;
	}

	/**
	 * writes all the messages ( warnings and errors) in the textfield.
	 */
	public void printMessages() {

		text.setText(""); //$NON-NLS-1$
		int count = 0;
		StyleRange style1 = new StyleRange();

		if(!warnings.isEmpty()){
			String warning = "Warnings \n";
			text.append(warning);
			style1.start = count;
			style1.length = warning.length();
			style1.fontStyle = SWT.BOLD;
			text.setStyleRange(style1);
	
			count += warning.length();
			count += addLines(warnings);
			text.append(newLine);
			count += newLine.length();
		}
		
		if(!errors.isEmpty()){
			String error = "Errors \n";
			text.append(error);
			style1 = new StyleRange();
			style1.start = count;
			style1.length = error.length();
			style1.fontStyle = SWT.BOLD;
			text.setStyleRange(style1);
			addLines(errors);
		}
	}

	private int addLines(List<String> messages) {
		int count = 0;
		
		for (String string : messages) {
			if(null != string){
				count += string.length();
				text.append(string);
				newLine = "\n"; //$NON-NLS-1$
				text.append(newLine);
				count += newLine.length();
			}
		}
		return count;
	}
}
