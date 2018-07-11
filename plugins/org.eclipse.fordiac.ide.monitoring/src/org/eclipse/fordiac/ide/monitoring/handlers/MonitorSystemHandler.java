/*******************************************************************************
 * Copyright (c) 2013 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoredSystems;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

public class MonitorSystemHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
		boolean oldValue = HandlerUtil.toggleCommandState(command);
		// use the old value and perform the operation
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof TreeSelection) {
			if (((TreeSelection) selection).getFirstElement() instanceof AutomationSystem) {
				AutomationSystem system = (AutomationSystem) ((TreeSelection) selection)
						.getFirstElement();

				if (oldValue) {
					// we are monitoring disable it now
					MonitoringManager.getInstance().disableSystem(system);					
				} else {
					MonitoringManager.getInstance().enableSystem(system);
					
					//TODO think if this should be asked for to the user. Similar to switching to the debug perspective in java development.
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					try {
						window.getWorkbench().showPerspective("org.eclipse.fordiac.ide.monitoring.MonitoringPerspective", window); //$NON-NLS-1$
					} catch (WorkbenchException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}
				}
				MonitoredSystems.refreshSystemTree();
			}
		}
		return null;
	}
}
