/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 2013, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The Class UnmapAction.
 */
public class UnmapAction extends SelectionAction implements
		IObjectActionDelegate {

	/** The Constant ID. */
	public static final String ID = "Unmap"; //$NON-NLS-1$

	/**
	 * Instantiates a new unmap action.
	 * 
	 * @param part the part
	 */
	public UnmapAction(final IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(Messages.UnmapAction_Unmap_Label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CommandStack stack = getCommandStack();
		CompoundCommand cmd = new CompoundCommand();
		cmd.setLabel(Messages.UnmapAction_Unmap_Label);
		for (FBNetworkElement element : selectedNetworkElements) {
			Command unmapCmd = new UnmapCommand(element);
			if (unmapCmd.canExecute()) {
				cmd.add(unmapCmd);
			} 
		}
		if (stack != null) {
			stack.execute(cmd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		// not used

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(final IAction action) {
		run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(final IAction action,
			final ISelection selection) {
		// not used
	}

	/** The selected f bs. */
	protected final List<FBNetworkElement> selectedNetworkElements = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		selectedNetworkElements.clear();

		for (Object selected : getSelectedObjects()) {
			if (selected instanceof AbstractFBNElementEditPart) {
				AbstractFBNElementEditPart ep = (AbstractFBNElementEditPart) selected;
				checkSelectedModelElement(ep.getModel());
			}
		}
		return (selectedNetworkElements.size() > 0);
	}

	protected void checkSelectedModelElement(FBNetworkElement model) {
		if(model.isMapped()){
			selectedNetworkElements.add(model);
		}
		//TODO model refactoring - check in else part if elements of the subapp are mapped to different entities 
	}
}
