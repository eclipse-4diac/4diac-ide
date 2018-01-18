/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;


/** Removes all selected watches
 *
 */
public class RemoveAllWatchesHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if (selection instanceof StructuredSelection) {
			MonitoringManager manager = MonitoringManager.getInstance();
			Set<IInterfaceElement> foundElements = getSelectedWatchedelements(manager, (StructuredSelection) selection);
			for (IInterfaceElement ifElement : foundElements) {
				removeMonitoringElement(manager, ifElement);
			}
			refreshEditor();
		}		
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext){
		boolean needToAdd = false;
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		
		if (selection instanceof StructuredSelection) {
			needToAdd = !getSelectedWatchedelements(MonitoringManager.getInstance(), (StructuredSelection) selection).isEmpty();
		}
		setBaseEnabled(needToAdd);
	}

	@SuppressWarnings("rawtypes")
	private static Set<IInterfaceElement> getSelectedWatchedelements(MonitoringManager manager, StructuredSelection selection) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			Object selectedObject = iterator.next();

			if (selectedObject instanceof FBEditPart) {
				foundElements.addAll(getWatchedIfElementsForFB(manager, ((FBEditPart)selectedObject).getModel()));
			} else if (selectedObject instanceof FBNetworkEditPart) {
				for (FBNetworkElement fbnElement : ((FBNetworkEditPart)selectedObject).getModel().getNetworkElements()) {
					foundElements.addAll(getWatchedIfElementsForFB(manager, fbnElement));
				}
			}else if (selectedObject instanceof InterfaceEditPart) {
				if(manager.containsPort( ((InterfaceEditPart)selectedObject).getModel())) {
					foundElements.add(((InterfaceEditPart)selectedObject).getModel());
				}
			}else if (selectedObject instanceof MonitoringEditPart){
				IInterfaceElement ie = ((MonitoringEditPart)selectedObject).getModel().getPort().getInterfaceElement();
				if(manager.containsPort(ie)) {
					foundElements.add(ie);
				}
			}
		}	
		return foundElements;
	}

	static private Set<IInterfaceElement> getWatchedIfElementsForFB(MonitoringManager manager,
			FBNetworkElement model) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		for (IInterfaceElement element : model.getInterface().getAllInterfaceElements()) {
			if(manager.containsPort(element)) {
				foundElements.add(element);
			}
		}	
		return foundElements;
	}
	
	static private void removeMonitoringElement(MonitoringManager manager, IInterfaceElement port) {	
		MonitoringBaseElement element = manager.getMonitoringElement(port);

		if (element instanceof MonitoringAdapterElement) {
			for (MonitoringElement child : ((MonitoringAdapterElement)element).getElements()) {
				manager.removeMonitoringElement(child);
			}
		}
		manager.removeMonitoringElement(element);
	}
}
