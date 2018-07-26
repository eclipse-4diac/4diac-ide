/*******************************************************************************
 * Copyright (c) 2013 - 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed automatic switch to monitoring perspective   
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.MonitoredSystems;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class MonitorSystemHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if ((selection instanceof TreeSelection) && (((TreeSelection) selection).getFirstElement() instanceof AutomationSystem)) {
			AutomationSystem system = (AutomationSystem) ((TreeSelection) selection).getFirstElement();
			if (HandlerUtil.toggleCommandState(command)) {
				// we are monitoring disable it now
				MonitoringManager.getInstance().disableSystem(system);					
			} else {
				MonitoringManager.getInstance().enableSystem(system);
			}
			MonitoredSystems.refreshSystemTree();
		}
		return null;
	}
}
