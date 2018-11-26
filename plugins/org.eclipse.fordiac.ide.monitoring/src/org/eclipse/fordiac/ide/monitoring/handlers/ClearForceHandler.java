/*******************************************************************************
 * Copyright (c) 2015, 2016, 2018 fortiss GmbH, Johannes Kepler University 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		StructuredSelection selection = (StructuredSelection)HandlerUtil.getCurrentSelection(event);
		VarDeclaration var = ForceHandler.getVariable(selection.getFirstElement());				
		if(null != var){		
			MonitoringManager manager = MonitoringManager.getInstance();
			MonitoringBaseElement element = manager.getMonitoringElement(var);
			if (element instanceof MonitoringElement) {
				manager.forceValue((MonitoringElement)element, ""); //$NON-NLS-1$
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
				VarDeclaration var = ForceHandler.getVariable(sel.getFirstElement());				
				if(null != var){
					MonitoringBaseElement element = manager.getMonitoringElement(var);
					needToAdd = (element instanceof MonitoringElement &&  ((MonitoringElement)element).isForce());
				}
			}
		}
		setBaseEnabled(needToAdd);
	}
	
}
