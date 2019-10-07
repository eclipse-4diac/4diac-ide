/*******************************************************************************
 * Copyright (c) 2015, 2016, 2018 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class TriggerEventHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		StructuredSelection selection = (StructuredSelection)HandlerUtil.getCurrentSelection(event);
		
		Event ev = getEvent(selection.getFirstElement());
		if(null != ev){
			MonitoringManager.getInstance().triggerEvent(ev);
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
				Event ev = getEvent(sel.getFirstElement());
				if((null != ev) && manager.containsPort(ev)) {
					needToAdd = true;
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

	private static Event getEvent(Object object) {
		IInterfaceElement ie = null;
		if(object instanceof InterfaceEditPart) {
			ie = ((InterfaceEditPart) object).getModel();
		} else if (object instanceof MonitoringEditPart){
			ie = ((MonitoringEditPart)object).getModel().getPort().getInterfaceElement();
		}
		if(ie instanceof Event) {
			return (Event)ie;
		}
		return null;
	}

}
