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
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemoveAllWatchesHandler extends AbstractMonitoringHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			MonitoringManager manager = MonitoringManager.getInstance();
		
			for (Iterator iter = sel.iterator(); iter.hasNext();) {
				Object selectedObject = iter.next();
				if (selectedObject instanceof FBEditPart) {
					FBEditPart editPart = (FBEditPart) selectedObject;
					for (IInterfaceElement element : editPart.getModel().getInterface().getAllInterfaceElements()) {
						if(manager.containsPort(element)) {
							RemoveWatchHandler.removeMonitoringElement(manager, element);
						}
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
				Object selectedObject = iterator.next();

				if (selectedObject instanceof FBEditPart) {
					FBEditPart editPart = (FBEditPart) selectedObject;
					for (IInterfaceElement element : editPart.getModel().getInterface().getAllInterfaceElements()) {
						if(manager.containsPort(element)) {
							needToAdd = true;
							break;
						}
					}					
				}	
			}
		}
		setBaseEnabled(needToAdd);
	}
}
