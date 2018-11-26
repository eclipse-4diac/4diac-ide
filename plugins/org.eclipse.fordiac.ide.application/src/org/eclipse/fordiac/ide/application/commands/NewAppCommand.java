/*******************************************************************************
 * Copyright (c) 2011, 2015 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

/**
 * The Class NewAppCommand.
 */
public class NewAppCommand extends AbstractOperation {

	private org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system;
	private String appName;

	private String comment;
	private Application application;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;

	}

	public NewAppCommand(
			org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system,
			String appName, String comment) {
		super(Messages.NewApplicationCommand_LABEL_NewApplication);
		this.system = system;
		this.appName = appName;
		this.comment = comment;
	}

	public Application getApplication() {
		return application;
	}

	/**
	 * checks whether all required information for creating a new App are set.
	 * 
	 * @return true, if can execute
	 */
	@Override
	public boolean canExecute() {
		return system != null && appName != null && comment != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) {
		if (system != null) {
			application = creatApplicationElement();
			application.setName(appName);
			application.setComment(comment);
			
			FBNetwork network = LibraryElementFactory.eINSTANCE
					.createFBNetwork();
			application.setFBNetwork(network);

			system.getApplication().add(application);
			
//TODO model refactoring - think if system should be saved here or if editor should be shown dirty
			SystemManager.INSTANCE.saveSystem(system); 
			return Status.OK_STATUS;
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "", null); //$NON-NLS-1$

	}

	/**
	 * Redo.
	 * 
	 * @see NewAppCommand#execute()
	 */
	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) {
		if (system != null) {
			system.getApplication().add(application);
//TODO model refactoring - think if system should be saved here or if editor should be shown dirty
			SystemManager.INSTANCE.saveSystem(system); // to save the	
			return Status.OK_STATUS;
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "", null); //$NON-NLS-1$
	}

	/**
	 * undo of FBCreateCommand.
	 */
	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) {
		if (system != null) {
			system.getApplication().remove(application);

//TODO model refactoring - think if system should be saved here or if editor should be shown dirty
			SystemManager.INSTANCE.saveSystem(system);			
			return Status.OK_STATUS;
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "", null); //$NON-NLS-1$

	}
	
	protected Application creatApplicationElement() {
		return LibraryElementFactory.eINSTANCE.createApplication();
	}
}