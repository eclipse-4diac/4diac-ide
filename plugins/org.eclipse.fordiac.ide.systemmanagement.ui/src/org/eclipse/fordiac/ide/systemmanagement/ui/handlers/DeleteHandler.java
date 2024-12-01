/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Muddasir Shakil, Virendra Ashiwal, Alois Zoitl -
 *   	 migrated this code to handle the deletion of Applications, Resources
 *       and Devices
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteApplicationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeviceDeleteCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceDeleteCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteHandler extends AbstractHandler {
	private final Map<AutomationSystem, CompoundCommand> commandMap = new HashMap<>();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			for (final Object element : ((StructuredSelection) selection).toList()) {
				createDeleteCommand(element);
			}
			executeDeleteCommands();
		}
		return null;
	}

	// for each Automation System in the Map, we get the CompoundCommand of items to
	// delete and the CommandStack of the Automation System, and ask the
	// CommandStack to execute the command
	private void executeDeleteCommands() {
		commandMap.entrySet().forEach(entry -> entry.getKey().getCommandStack().execute(entry.getValue()));
		// we have executed all commands, clear the map
		commandMap.clear();
	}

	// check if the selected object is an Application, Resource or Device
	// and create a corresponding DeleteCommand for it, which is added to the Map
	private void createDeleteCommand(final Object element) {
		if (element instanceof Application) {
			enlistCommand(((Application) element).getAutomationSystem(),
					new DeleteApplicationCommand((Application) element));
		} else if (element instanceof Device) {
			enlistCommand(((Device) element).getAutomationSystem(), new DeviceDeleteCommand((Device) element));
		} else if (element instanceof Resource) {
			enlistCommand(((Resource) element).getAutomationSystem(), new ResourceDeleteCommand((Resource) element));
		}
	}

	private void enlistCommand(final AutomationSystem automationSystem, final Command cmd) {
		if (cmd.canExecute()) {
			getCommandList(automationSystem).add(cmd);
		}
	}

	private CompoundCommand getCommandList(final AutomationSystem automationSystem) {
		return commandMap.computeIfAbsent(automationSystem, sys -> new CompoundCommand());
	}

}
