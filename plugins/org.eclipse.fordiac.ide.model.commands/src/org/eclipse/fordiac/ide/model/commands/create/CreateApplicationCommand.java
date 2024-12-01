/*******************************************************************************
 * Copyright (c) 2011, 2024 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 *                          Johannes Keppler University Linz,
 *                          Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/**
 * The Class NewAppCommand.
 */
public class CreateApplicationCommand extends CreationCommand {

	private final AutomationSystem system;
	private final String appName;

	private Application application;

	public CreateApplicationCommand(final AutomationSystem system, final String appName) {
		this.system = system;
		this.appName = (appName != null) ? appName : "App"; //$NON-NLS-1$
	}

	@Override
	public boolean canExecute() {
		return system != null && appName != null;
	}

	@Override
	public void execute() {
		application = LibraryElementFactory.eINSTANCE.createApplication();

		final FBNetwork network = LibraryElementFactory.eINSTANCE.createFBNetwork();
		application.setFBNetwork(network);

		system.getApplication().add(application);
		application.setName(NameRepository.createUniqueName(application, appName));

	}

	@Override
	public void redo() {
		system.getApplication().add(application);
	}

	@Override
	public void undo() {
		system.getApplication().remove(application);
	}

	@Override
	public Application getCreatedElement() {
		return application;
	}

}
