/*******************************************************************************
 * Copyright (c) 2012 - 2014, 2016 AIT, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Filip Andren, Gerhard Ebenhofer, Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.Iterator;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.gef.EditPart;
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
		setText("Update FB Type");
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
		for (Iterator<?> iterator = getSelectedObjects().iterator(); iterator
				.hasNext();) {
			Object selected = iterator.next();
			if (selected instanceof FBEditPart) {
				return true;
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
		for (Iterator<?> iterator = getSelectedObjects().iterator(); iterator
				.hasNext();) {
			EditPart ep = (EditPart) iterator.next();
			if (ep instanceof FBEditPart) {
				UpdateFBTypeCommand cmd;
				if (entry == null) {
					cmd = new UpdateFBTypeCommand(((FBEditPart) ep).getModel());
				} else {
					cmd = new UpdateFBTypeCommand(((FBEditPart) ep).getModel(), entry);
				}
				if (cmd.canExecute()) {
					updateCmd.add(cmd);
				}
			}
		}
		execute(updateCmd);
	}

}
