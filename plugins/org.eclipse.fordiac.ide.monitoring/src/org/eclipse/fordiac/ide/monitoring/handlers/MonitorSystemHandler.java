/*******************************************************************************
 * Copyright (c) 2013 - 2018, 2020 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed automatic switch to monitoring perspective
 *   Muddasir Shakil - Monitor System in  Debug Menu
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoredSystems;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;

public class MonitorSystemHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		if ((selection instanceof TreeSelection)) {
			final AutomationSystem system = SystemManager.INSTANCE
					.getSystem((IFile) ((TreeSelection) selection).getFirstElement());
			if (!MonitoringManager.getInstance().isSystemMonitored(system)) {
				MonitoringManager.getInstance().enableSystem(system);
			} else {
				MonitoringManager.getInstance().disableSystem(system);
			}
			HandlerUtil.toggleCommandState(event.getCommand());
			MonitoredSystems.refreshSystemTree();
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		super.setEnabled(evaluationContext);
		final ICommandService service = PlatformUI.getWorkbench().getService(ICommandService.class);
		final Command command = service.getCommand("org.eclipse.fordiac.ide.monitoring.MonitorSystem"); //$NON-NLS-1$
		try {
			HandlerUtil.toggleCommandState(command);
		} catch (final ExecutionException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		setBaseEnabled(true);
	}

}
