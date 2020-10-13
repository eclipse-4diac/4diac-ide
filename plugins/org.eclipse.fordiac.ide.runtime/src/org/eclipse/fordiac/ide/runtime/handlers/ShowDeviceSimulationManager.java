/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.handlers;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.runtime.Activator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WorkbenchWindow;

public class ShowDeviceSimulationManager extends AbstractHandler {
	private static final String DEVICE_SIMULATOR_PERSPECTIVE_ID = "org.eclipse.fordiac.ide.runtime.perspectives.DeviceSimulatorPerspective"; //$NON-NLS-1$

	private static IWorkbenchWindow win = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		showSimulationManagerWindow();
		return Status.OK_STATUS;
	}

	private static synchronized IWorkbenchWindow showSimulationManagerWindow() {
		if (null == win) {
			win = createSimulationManagerWindow();
		} else {
			// if we already have a window raise it at give it the focus
			win.getShell().setMinimized(false);
			win.getShell().forceActive();
			win.getShell().setFocus();
		}
		return win;
	}

	private static IWorkbenchWindow createSimulationManagerWindow() {
		try {

			IWorkbenchWindow newWin = PlatformUI.getWorkbench().openWorkbenchWindow(DEVICE_SIMULATOR_PERSPECTIVE_ID,
					ResourcesPlugin.getWorkspace().getRoot());
			newWin.getShell().addDisposeListener(e -> win = null);
			removeBars(newWin);
			return newWin;
		} catch (WorkbenchException e) {
			Activator.getDefault().logError("Could not create simulation manager workbench window", e); //$NON-NLS-1$
		}
		return null;
	}

	@SuppressWarnings("restriction") // we can only remove the different bars by accessing eclipse internal api
	private static void removeBars(IWorkbenchWindow newWin) {
		if (newWin instanceof WorkbenchWindow) {
			WorkbenchWindow wbWindow = (WorkbenchWindow) newWin;
			Arrays.stream(wbWindow.getMenuBarManager().getItems()).forEach(item -> item.setVisible(false));
			wbWindow.getMenuManager().setVisible(false);
			wbWindow.setCoolBarVisible(false);
			wbWindow.setPerspectiveBarVisible(false);
		}
	}

}
