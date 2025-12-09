/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * The Class NewAppCommand.
 */
public class DeleteApplicationCommand extends Command {

	private final Application application;
	private AutomationSystem system;

	// compound command to store all unmapp commands for FBNetwork elements to be
	// unmapped
	private final CompoundCommand unmappApplicationElements = new CompoundCommand();

	public DeleteApplicationCommand(final Application application) {
		super("Delete Application");
		this.application = application;
		if (null != application) {
			this.system = application.getAutomationSystem();
		}
	}

	@Override
	public boolean canExecute() {
		return ((null != application) && (null != system));
	}

	@Override
	public void execute() {
		getUnmappCommands();
		unmappApplicationElements.execute();
		system.getApplication().remove(application);
	}

	@Override
	public void redo() {
		unmappApplicationElements.redo();
		system.getApplication().remove(application);
	}

	@Override
	public void undo() {
		system.getApplication().add(application);
		unmappApplicationElements.undo();
	}

	private void getUnmappCommands() {
		application.getFBNetwork().getNetworkElements().stream().forEach(element -> {
			final Mapping mapping = element.getMapping();
			if (null != mapping) {
				unmappApplicationElements.add(new UnmapCommand(element));
			}
			// TODO model refactoring -if not mapped and subapp check if the internals are
			// mapped and perform this recursivle on the elements
		});
	}

}
