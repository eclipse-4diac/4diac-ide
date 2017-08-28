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

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemoveWatchHandler extends AbstractMonitoringHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			MonitoringManager manager = MonitoringManager.getInstance();

			for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				
				if (obj instanceof InterfaceEditPart) {
					InterfaceEditPart editPart = (InterfaceEditPart) obj;
					if (manager.containsPort(editPart.getModel())) {
						removeMonitoringElement(manager, editPart.getModel());
					}
				}
			}
			refreshEditor();
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setEnabled(Object evaluationContext){
		boolean needToAdd = false;
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			MonitoringManager manager = MonitoringManager.getInstance();

			for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				if (obj instanceof InterfaceEditPart) {
					InterfaceEditPart editPart = (InterfaceEditPart) obj;
					if (manager.containsPort(editPart.getModel()) && 
							!(editPart instanceof MonitoringAdapterInterfaceEditPart)) {
						needToAdd = true;
						break; // can return from loop because one is enough to enable the action
					}
				}
			}
		}
		setBaseEnabled(needToAdd);
	}
	
	static protected void removeMonitoringElement(MonitoringManager manager, IInterfaceElement port) {	
		MonitoringBaseElement element = manager.getMonitoringElement(port);

		if (element instanceof MonitoringAdapterElement) {
			for (MonitoringElement child : ((MonitoringAdapterElement)element).getElements()) {
				manager.removeMonitoringElement(child);
			}
		}
		manager.removeMonitoringElement(element);
	}
}
