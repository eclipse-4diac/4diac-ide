/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University
 * 
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.gef.EditPart;
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
			
			if(selectedObject instanceof EditPart) { 
				if (selectedObject instanceof MonitoringEditPart){
					IInterfaceElement ie = ((MonitoringEditPart)selectedObject).getModel().getPort().getInterfaceElement();
					if(manager.containsPort(ie)) {
						foundElements.add(ie);
					}
				}else if (((EditPart)selectedObject).getModel() instanceof EObject) {
						foundElements.addAll(getWatchedelementsForLibrayElement(manager, (EObject)((EditPart)selectedObject).getModel()));
				}
			} else if (selectedObject instanceof EObject) {
				foundElements.addAll(getWatchedelementsForLibrayElement(manager, (EObject)selectedObject));
			}
		}	
		return foundElements;
	}

	private static Set<IInterfaceElement> getWatchedelementsForLibrayElement(MonitoringManager manager, EObject element) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		if (element instanceof FBNetworkElement) {
			foundElements.addAll(getWatchedIfElementsForFB(manager, (FBNetworkElement)element));
		} else if (element instanceof FBNetwork) {
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, (FBNetwork)element));
		}else if (element instanceof IInterfaceElement) {
			if(manager.containsPort( (IInterfaceElement)element)) {
				foundElements.add((IInterfaceElement)element);
			}
		} else if (element instanceof AutomationSystem) {
			foundElements.addAll( getWatchedElementsFromSystem(manager, (AutomationSystem)element));
		} else if (element instanceof Application) {
			foundElements.addAll( getWatchedElementsFromFBNetwork(manager, ((Application)element).getFBNetwork()));
		}
		return foundElements;				
	}

	private static Set<IInterfaceElement>  getWatchedElementsFromFBNetwork(MonitoringManager manager, FBNetwork fbNetwork) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		for (FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			foundElements.addAll(getWatchedIfElementsForFB(manager, fbnElement));
		}
		return foundElements;
	}

	private static Set<IInterfaceElement> getWatchedIfElementsForFB(MonitoringManager manager,
			FBNetworkElement model) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		for (IInterfaceElement element : model.getInterface().getAllInterfaceElements()) {
			if(manager.containsPort(element)) {
				foundElements.add(element);
			}
		}	
		return foundElements;
	}
	
	private static Collection<? extends IInterfaceElement> getWatchedElementsFromSystem(MonitoringManager manager,
			AutomationSystem system) {
		Set<IInterfaceElement> foundElements = new HashSet<>();
		for (Application application : system.getApplication()) {
			foundElements.addAll( getWatchedElementsFromFBNetwork(manager, application.getFBNetwork()));
		}
		return foundElements;
	}
	
	private static void removeMonitoringElement(MonitoringManager manager, IInterfaceElement port) {	
		MonitoringBaseElement element = manager.getMonitoringElement(port);

		if (element instanceof MonitoringAdapterElement) {
			for (MonitoringElement child : ((MonitoringAdapterElement)element).getElements()) {
				manager.removeMonitoringElement(child);
			}
		}
		manager.removeMonitoringElement(element);
	}

}
