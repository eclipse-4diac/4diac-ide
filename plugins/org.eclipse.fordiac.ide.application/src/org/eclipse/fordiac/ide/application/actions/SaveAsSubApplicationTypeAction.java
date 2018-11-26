/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.wizards.SaveAsSubappWizard;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;

public class SaveAsSubApplicationTypeAction extends SelectionAction {
	

	/** The Constant ID. */
	public static final String ID = "SaveAsSubApplicationTypeAction"; //$NON-NLS-1$
	

	public SaveAsSubApplicationTypeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(Messages.SaveAsSubApplicationTypeAction_SaveAsSubApplicationTypeText);
	}

	@Override
	protected boolean calculateEnabled() {
		
		if(!getSelectedObjects().isEmpty()){
			for (Object selected : getSelectedObjects()) {
				if(selected instanceof EditPart){
					if( ((EditPart)selected).getModel() instanceof SubApp){
						if(null != ((SubApp)((EditPart)selected).getModel()).getPaletteEntry()){
							//a typed subapplication has been selected
							return false;
						}
					}else{
						// a non subapplication has been selected
						return false;
					}
				}else{
					return false;
				}
			}
			//we only have subapps selected
			return true;
		}		
		return false;
	}

	@Override
	public void run() {
		for (Object selected : getSelectedObjects()) {
			EditPart ep = (EditPart)selected;
			SubApp subApp = (SubApp)ep.getModel();
			
			if(!checkContainedSubApps(subApp)){
				showInformationDialog(subApp);
			}else{
				invokeSaveWizard(subApp);
			}			
		}
	}

	private void showInformationDialog(SubApp subApp) {
		//TODO consider to show the name of the subapplication in the error dialog
		MessageDialog.openError(getWorkbenchPart().getSite().getShell(), Messages.SaveAsSubApplicationTypeAction_UntypedSubappError,
				Messages.SaveAsSubApplicationTypeAction_UntypedSubappErrorDescription);		
	}
	
	private void invokeSaveWizard(SubApp subApp) {
		SaveAsSubappWizard wizard = new SaveAsSubappWizard(subApp);
		
		WizardDialog dialog = new WizardDialog(getWorkbenchPart().getSite().getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	private boolean checkContainedSubApps(SubApp subApp) {
		for (FBNetworkElement element : subApp.getSubAppNetwork().getNetworkElements()) {
			if ((element instanceof SubApp) && (null == element.getPaletteEntry())){
				//we have an untyped subapplication 
				return false;
			}
		}
		return true;
	}

}
