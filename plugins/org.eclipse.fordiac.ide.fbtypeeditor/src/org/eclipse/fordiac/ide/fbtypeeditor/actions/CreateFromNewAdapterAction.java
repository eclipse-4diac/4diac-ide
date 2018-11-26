/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizard;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizardPage;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;

public abstract class CreateFromNewAdapterAction extends WorkbenchPartAction {
	private FBType fbType;
	private PaletteEntry paletteEntry;

	protected FBType getFbType() {
		return fbType;
	}

	CreateFromNewAdapterAction(IWorkbenchPart part, FBType fbType) {
		super(part);
		setText("New Adapter ...");
		this.fbType = fbType;
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != fbType);
	}

	@Override
	public void run() {
		NewFBTypeWizard wizard = new NewFBTypeWizard(){
			@Override
			protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
				return new NewFBTypeWizardPage(getSelection()){
					@Override
					protected FileFilter createTemplatesFileFilter() {
						return new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								//only show adapter templates in template selection box 
								return pathname.getName().toUpperCase().endsWith(".ADP"); //$NON-NLS-1$
							}
						};
					}
				};
			}
		};
		
		
		WizardDialog dialog = new WizardDialog(getWorkbenchPart().getSite().getShell(), wizard);
		dialog.create();
		
		if(Window.OK == dialog.open()){
			//the type could be created new execute the command
			PaletteEntry entry = wizard.getPaletteEntry();
			if((null != entry) && (entry instanceof AdapterTypePaletteEntry)){
				AdapterTypePaletteEntry adpEntry = (AdapterTypePaletteEntry)entry;
				execute(getCreationCommand(adpEntry));
			}
		}
	}

	private IStructuredSelection getSelection() {
		if(null != paletteEntry){
			//if we have a paletteEntry we will use this to mark the place in the typelibrary 
			//to indicate the user where he might add the adapter
			return new StructuredSelection(paletteEntry.getFile().getParent());
		}
		return new StructuredSelection();
	}

	protected abstract Command getCreationCommand(AdapterTypePaletteEntry adpEntry);

	public void setPaletteEntry(PaletteEntry paletteEntry) {
		this.paletteEntry = paletteEntry;
	}

}
