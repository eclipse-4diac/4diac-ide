/*******************************************************************************
 * Copyright (c) 2013, 2015, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.State;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class MonitorSystemStateProvider extends State {

	@Override
	public Object getValue() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (null != wb) {
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			if (null != win) {
				IWorkbenchPage page = win.getActivePage();
				if (null != page) {
					ISelection selection = page.getSelection();
					if ((selection instanceof TreeSelection)
							&& (((TreeSelection) selection).getFirstElement() instanceof AutomationSystem)) {
						AutomationSystem system = (AutomationSystem) ((TreeSelection) selection).getFirstElement();
						return Boolean.valueOf(MonitoringManager.getInstance().monitoringForSystemEnabled(system));
					}
				}
			}
		}
		return Boolean.FALSE;
	}

	public MonitorSystemStateProvider() {
	}

}
