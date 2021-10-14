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
 *   Muddasir Shakil
 *     - Fixed getting Automation System
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.State;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class MonitorSystemStateProvider extends State {

	@Override
	public Object getValue() {
		final IWorkbench wb = PlatformUI.getWorkbench();
		if (null != wb) {
			final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			if (null != win) {
				final IWorkbenchPage page = win.getActivePage();
				if (null != page) {
					final ISelection selection = page.getSelection();
					if ((selection instanceof TreeSelection)
							&& SystemManager.isSystemFile(((TreeSelection) selection).getFirstElement())) {
						final AutomationSystem system = SystemManager.INSTANCE
								.getSystem((IFile) ((TreeSelection) selection).getFirstElement());
						return Boolean.valueOf(MonitoringManager.getInstance().isSystemMonitored(system));
					}
				}
			}
		}
		return Boolean.FALSE;
	}

}
