/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
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
package org.eclipse.fordiac.ide.monitoring.actions;

import java.util.Iterator;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.MonitoredSystems;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;


public class SwitchMonitoringModeAction implements
		IWorkbenchWindowPulldownDelegate2 {

	private boolean selectAll = true;

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	@Override
	public Menu getMenu(Control parent) {
		Menu menu = new Menu(parent);
		MonitoredSystems.createMenuEntriesForSystems(menu);
		return menu;
	}	
	
	@Override
	public void dispose() {
		//currently nothing to do here
	}

	@Override
	public void init(IWorkbenchWindow window) {
		//currently nothing to do here
	}

	@Override
	public void run(IAction action) {
		if (selectAll) {
			for (Iterator<AutomationSystem> iterator = SystemManager.INSTANCE.getSystems()
					.iterator(); iterator.hasNext();) {
				AutomationSystem system = iterator.next();
				MonitoringManager.getInstance().enableSystem(system);
			}
			selectAll = false;
		} else {
			for (Iterator<AutomationSystem> iterator = SystemManager.INSTANCE.getSystems()
					.iterator(); iterator.hasNext();) {
				AutomationSystem system = iterator.next();
				MonitoringManager.getInstance().disableSystem(system);
			}
			selectAll = true;
		}
		MonitoredSystems.refreshSystemTree();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		//currently nothing to do here
	}
	
}
