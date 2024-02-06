/*******************************************************************************
 * Copyright (c) 2011, 2015 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.commands;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * The Class NewAppCommand.
 */
public class NewAppCommand extends AbstractOperation {

	private final AutomationSystem system;
	private final String appName;

	private final String comment;
	private Application application;

	public NewAppCommand(final AutomationSystem system, final String appName, final String comment) {
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
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) {
		if (system != null) {
			application = creatApplicationElement();
			application.setName(appName);
			application.setComment(comment);

			final FBNetwork network = LibraryElementFactory.eINSTANCE.createFBNetwork();
			application.setFBNetwork(network);

			system.getApplication().add(application);

			doSave(monitor);
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
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) {
		if (system != null) {
			system.getApplication().add(application);
			doSave(monitor);
			return Status.OK_STATUS;
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "", null); //$NON-NLS-1$
	}

	/**
	 * undo of FBCreateCommand.
	 */
	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) {
		if (system != null) {
			system.getApplication().remove(application);
			doSave(monitor);
			return Status.OK_STATUS;
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "", null); //$NON-NLS-1$

	}

	private void doSave(final IProgressMonitor monitor) {
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(
				system.getTypeEntry().getFile().getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				system.getTypeEntry().save(system, monitor);
			}
		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}

	private static Application creatApplicationElement() {
		return LibraryElementFactory.eINSTANCE.createApplication();
	}
}
