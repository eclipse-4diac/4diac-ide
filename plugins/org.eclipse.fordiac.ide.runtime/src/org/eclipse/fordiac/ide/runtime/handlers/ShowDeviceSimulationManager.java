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
 *               - changed to use E4 api for creating the simulated device
 *                 manager window
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.PlatformUI;

public class ShowDeviceSimulationManager extends AbstractHandler {
	private static final String DEVICE_SIMULATOR_WINDOW_ID = "org.eclipse.fordiac.ide.runtime.trimmedwindow.simulateddevicesmanager"; //$NON-NLS-1$

	private static MWindow simulatedDevMgrWin = null;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		showSimulationManagerWindow();
		return Status.OK_STATUS;
	}

	public static IEclipseContext getContext() {
		final IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
		return (null == context) ? null : context.getActiveLeaf();
	}

	private static synchronized void showSimulationManagerWindow() {
		final IEclipseContext context = getContext();
		if (null != context) {
			if (null == simulatedDevMgrWin || null == simulatedDevMgrWin.getWidget()) {
				// the window is not open
				simulatedDevMgrWin = createSimulationManagerWindow(context);
			}
			if (null != simulatedDevMgrWin) {
				simulatedDevMgrWin.setVisible(true);
			}
		}
	}

	private static MWindow createSimulationManagerWindow(final IEclipseContext context) {
		final EModelService modelService = context.get(EModelService.class);
		final MApplication app = context.get(MApplication.class);
		final MWindow mainWin = app.getChildren().get(0);
		final MWindow newWin = (MWindow) modelService.cloneSnippet(app, DEVICE_SIMULATOR_WINDOW_ID, mainWin);
		mainWin.getWindows().add(newWin);
		return newWin;
	}

}
