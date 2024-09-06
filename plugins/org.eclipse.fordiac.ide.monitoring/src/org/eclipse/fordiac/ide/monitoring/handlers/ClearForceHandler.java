/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClearForceHandler extends AbstractMonitoringHandler {

	@Override
	protected void doExecute(final ExecutionEvent event, final StructuredSelection structSel) {
		processHandler(ForceHandler.getVariable(structSel.getFirstElement()));
	}

	public static void processHandler(final VarDeclaration variable) {
		if (null != variable) {
			final MonitoringManager manager = MonitoringManager.getInstance();
			final MonitoringBaseElement element = manager.getMonitoringElement(variable);
			if (element instanceof final MonitoringElement me) {
				manager.forceValue(me, ""); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		boolean needToAdd = false;
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);

		if (selection instanceof final StructuredSelection sel) {
			final MonitoringManager manager = MonitoringManager.getInstance();

			if (1 == sel.size()) {
				// only allow to force a value if only one element is selected
				final VarDeclaration variable = ForceHandler.getVariable(sel.getFirstElement());
				if (null != variable) {
					final MonitoringBaseElement element = manager.getMonitoringElement(variable);
					needToAdd = (element instanceof final MonitoringElement me && me.isForce());
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

}
