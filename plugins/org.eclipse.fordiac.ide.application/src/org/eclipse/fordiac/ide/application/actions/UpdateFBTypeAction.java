/*******************************************************************************
 * Copyright (c) 2012 - 2014, 2016 AIT, Profactor GmbH, fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Filip Andren, Gerhard Ebenhofer, Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked update fb type to accept also supapps  
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * UpdateFBTypeAction triggers an update of the type for an FB instance
 * 
 * @author Filip Andr�n (Filip.Andren@ait.ac.at)
 */
public class UpdateFBTypeAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "UpdateFBTypeAction"; //$NON-NLS-1$
	
	/** FB type */
	private PaletteEntry entry;

	public UpdateFBTypeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Update Type");
		entry = null;
	}

	public UpdateFBTypeAction(IWorkbenchPart part, PaletteEntry entry) {
		super(part);
		setId(entry.getFile().getFullPath().toString().concat("_").concat(ID)); //$NON-NLS-1$
		setText(entry.getLabel());
		this.entry = entry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {	
		for(Object selected : getSelectedObjects()){		
			if (selected instanceof AbstractFBNElementEditPart) {
				return null != ((AbstractFBNElementEditPart) selected).getModel().getType();
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand updateCmd = new CompoundCommand();
		for(Object obj : getSelectedObjects()){
			if (obj instanceof AbstractFBNElementEditPart) {
				UpdateFBTypeCommand cmd = new UpdateFBTypeCommand(((AbstractFBNElementEditPart) obj).getModel(), entry);
				if (cmd.canExecute()) {
					updateCmd.add(cmd);
				}
			}
		}
		execute(updateCmd);
	}

}
