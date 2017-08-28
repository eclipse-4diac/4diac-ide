/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ForceHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		StructuredSelection selection = (StructuredSelection)HandlerUtil.getCurrentSelection(event);
		InterfaceEditPart editPart = (InterfaceEditPart) (selection).getFirstElement();
		if (!editPart.isEvent()) {
			MonitoringManager manager = MonitoringManager.getInstance();
			MonitoringBaseElement element = manager.getMonitoringElement(editPart.getModel());
			if (element != null && element instanceof MonitoringElement) {
				MonitoringElement monitoringElement = (MonitoringElement)element;

				InputDialog input = new InputDialog(Display.getDefault().getActiveShell(), "Force Value", "Value",
						monitoringElement.isForce() ? monitoringElement.getForceValue() : "", //$NON-NLS-1$
						null);
				int ret = input.open();
				if (ret == org.eclipse.jface.window.Window.OK) {
					manager.forceValue(monitoringElement, input.getValue());
				}
			}
		}
		return null;
	}
	
	@Override
	public void setEnabled(Object evaluationContext){
		boolean needToAdd = false;
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			MonitoringManager manager = MonitoringManager.getInstance();
			
			if(1 == sel.size()){
				//only allow to force a value if only one element is selected
				if(sel.getFirstElement() instanceof InterfaceEditPart){
					InterfaceEditPart editPart = (InterfaceEditPart) sel.getFirstElement();					
					MonitoringBaseElement element = manager.getMonitoringElement(editPart.getModel());
					needToAdd = ((element instanceof MonitoringElement) && editPart.isVariable());
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

}
