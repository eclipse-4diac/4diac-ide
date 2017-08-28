/*******************************************************************************
 * Copyright (c) 208 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.commands.DeleteApplicationCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;


public class DeleteApplicationHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof TreeSelection) {
			if(((TreeSelection) selection).getFirstElement() instanceof Application) {
				Application application = (Application) ((TreeSelection) selection).getFirstElement();
				runDeleteAction(application);
			}
		}
		return null;
	}

	private static void runDeleteAction(Application application) {
		DeleteApplicationCommand cmd = new DeleteApplicationCommand(application);
		AutomationSystem system = application.getAutomationSystem();
		
		org.eclipse.gef.commands.CommandStack commandStack = SystemManager.INSTANCE.getCommandStack(system);
			
		commandStack.execute(cmd);
	}

	
}
