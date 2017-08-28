/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteSystem extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof TreeSelection) {			
			for (Object selElement : ((TreeSelection)selection).toList()) {
				if(selElement instanceof AutomationSystem){
					IProject project = ((AutomationSystem)selElement).getProject();
					runDeleteAction(project);					
				}
			}
		}
		return null;
	}

	private static void runDeleteAction(IProject project) {
		DeleteResourceAction action = new DeleteResourceAction(new IShellProvider() {			
			@Override
			public Shell getShell() {
				return Display.getDefault().getActiveShell();
			}
		});
		action.selectionChanged(new StructuredSelection(project));
		action.run();
	}
}
