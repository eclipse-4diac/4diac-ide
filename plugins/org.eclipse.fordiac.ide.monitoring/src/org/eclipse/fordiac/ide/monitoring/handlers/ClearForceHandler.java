/*******************************************************************************
 * Copyright (c) 2015, 2016, 2018 fortiss GmbH, Johannes Kepler University 
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
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClearForceHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		VarDeclaration variable = ForceHandler.getVariable(selection.getFirstElement());
		if (null != variable) {
			MonitoringManager manager = MonitoringManager.getInstance();
			MonitoringBaseElement element = manager.getMonitoringElement(variable);
			if (element instanceof MonitoringElement) {
				manager.forceValue((MonitoringElement) element, ""); //$NON-NLS-1$
			}
		}

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		boolean needToAdd = false;
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);

		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			MonitoringManager manager = MonitoringManager.getInstance();

			if (1 == sel.size()) {
				// only allow to force a value if only one element is selected
				VarDeclaration variable = ForceHandler.getVariable(sel.getFirstElement());
				if (null != variable) {
					MonitoringBaseElement element = manager.getMonitoringElement(variable);
					needToAdd = (element instanceof MonitoringElement && ((MonitoringElement) element).isForce());
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

}
