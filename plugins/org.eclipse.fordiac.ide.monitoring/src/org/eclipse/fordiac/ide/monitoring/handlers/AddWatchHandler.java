/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddWatchHandler extends AbstractMonitoringHandler {
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		MonitoringManager manager = MonitoringManager.getInstance();
		if (selection instanceof StructuredSelection) {
			StructuredSelection sel = (StructuredSelection) selection;
			for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				if (obj instanceof InterfaceEditPart) {
					InterfaceEditPart editPart = (InterfaceEditPart) obj;				
					if (!manager.containsPort(editPart.getModel())) {
						PortElement port = MonitoringManagerUtils.createPortElement(editPart);
						createMonitoringElement(manager, port);
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
				if ((obj instanceof InterfaceEditPart) && 
						!(obj instanceof MonitoringAdapterInterfaceEditPart)){
					InterfaceEditPart editPart = (InterfaceEditPart) obj;
					if(MonitoringManagerUtils.canBeMonitored(editPart)
								&& !manager.containsPort(editPart.getModel())) {
						needToAdd = true;
						break; // can return from loop because one is enough to enable the action
					}
				}
			}
		}
		setBaseEnabled(needToAdd);
	}
	
	protected MonitoringBaseElement createMonitoringElement(MonitoringManager manager, PortElement port) {
		MonitoringBaseElement element;
		if (port instanceof AdapterPortElement) {
			element = MonitoringFactory.eINSTANCE.createMonitoringAdapterElement();
		}
		else {
			element = MonitoringFactory.eINSTANCE.createMonitoringElement();
		}
		element.setPort(port);
		manager.addMonitoringElement(element);
		if (port instanceof AdapterPortElement) {
			MonitoringAdapterElement adpaterElement = (MonitoringAdapterElement)element;
			createMonitoringElementsForAdapterInterface(manager, adpaterElement);
		}
		return element;
	}

	private void createMonitoringElementsForAdapterInterface(MonitoringManager manager, MonitoringAdapterElement adpaterElement) {
		createMonitoredAdpaterFBView(adpaterElement);
		refreshEditor();	
		PortElement port = adpaterElement.getPort();
		List<MonitoringElement> childElements = adpaterElement.getElements();
			InterfaceList interfaceList =  adpaterElement.getMonitoredAdapterFB().getInterface();
		List<PortElement> ports = ((AdapterPortElement)port).getPorts();
		MonitoringFactory monitoringFactory = MonitoringFactory.eINSTANCE;
		ArrayList<IInterfaceElement> ios = new ArrayList<>();
		ios.addAll(interfaceList.getEventInputs());
		ios.addAll(interfaceList.getEventOutputs());
		ios.addAll(interfaceList.getInputVars());
		ios.addAll(interfaceList.getOutputVars());
		for (IInterfaceElement io : ios) {
			PortElement newPort = monitoringFactory.createPortElement();
			newPort.setDevice(port.getDevice());
			newPort.setFb(port.getFb());
			newPort.setInterfaceElement(io);
			newPort.setResource(port.getResource());
			newPort.setSystem(port.getSystem());
			ports.add(newPort);
			childElements.add((MonitoringElement)createMonitoringElement(manager, newPort));
		}
	}

	private static void createMonitoredAdpaterFBView(MonitoringAdapterElement adpaterElement) {
		AdapterFB fb = LibraryElementFactory.eINSTANCE.createAdapterFB();
		IInterfaceElement interfaceElement = adpaterElement.getPort().getInterfaceElement();
		Palette palette = interfaceElement.getFBNetworkElement().getFbNetwork().getAutomationSystem().getPalette();
		List<PaletteEntry> types = palette.getTypeEntries(((AdapterDeclaration)interfaceElement).getType().getName());
		fb.setPaletteEntry(types.get(0));	
		fb.setAdapterDecl((AdapterDeclaration)interfaceElement);
		fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());	
		createMonitoredAdapterInterface(fb);		
		adpaterElement.setMonitoredAdapterFB(fb);
	}
	
	private static void createMonitoredAdapterInterface(AdapterFB fb) {
		InterfaceList interfaceList = fb.getInterface();
		for (Event event : fb.getType().getInterfaceList().getEventInputs()) {
			interfaceList.getEventInputs().add(EcoreUtil.copy(event));
		}
		for (Event event : fb.getType().getInterfaceList().getEventOutputs()) {
			interfaceList.getEventOutputs().add(EcoreUtil.copy(event));
		}
		for (VarDeclaration var : fb.getType().getInterfaceList().getInputVars()) {
			interfaceList.getInputVars().add(EcoreUtil.copy(var));
		}
		for (VarDeclaration var : fb.getType().getInterfaceList().getOutputVars()) {
			interfaceList.getOutputVars().add(EcoreUtil.copy(var));
		}
		//currently IEC 61499 does not allow adapters in adapters. 
		//If this changes here also plugs and sockets need to be added
	}	
}
