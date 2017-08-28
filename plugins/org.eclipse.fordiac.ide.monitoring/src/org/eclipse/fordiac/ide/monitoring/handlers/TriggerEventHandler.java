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
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class TriggerEventHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		StructuredSelection selection = (StructuredSelection)HandlerUtil.getCurrentSelection(event);
		
		if ((selection).getFirstElement() instanceof InterfaceEditPart) {
			InterfaceEditPart editPart = (InterfaceEditPart) (selection).getFirstElement();
			MonitoringManager.getInstance().triggerEvent(editPart.getModel());
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
				//only allow trigger event if only one element is selected
				if(sel.getFirstElement() instanceof InterfaceEditPart){
					InterfaceEditPart editPart = (InterfaceEditPart) sel.getFirstElement();
					if(manager.containsPort(editPart.getModel())
							&& editPart.isEvent()){
						needToAdd = true;
					}
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

}
