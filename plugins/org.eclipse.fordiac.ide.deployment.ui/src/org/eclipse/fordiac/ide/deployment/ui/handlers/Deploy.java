/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class Deploy extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Collection<EObject> selected = getDeployableObjects(HandlerUtil.getCurrentSelection(event));
		if(!selected.isEmpty()) {
			DeploymentCoordinator.INSTANCE.performDeployment(selected.toArray(new EObject[selected.size()]));
		}
		return null;
	}
	
	
	@Override
	public void setEnabled(Object evaluationContext){
		boolean needToAdd = false;
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);		
		if (selection instanceof ISelection) {
			needToAdd = !getDeployableObjects((ISelection) selection).isEmpty();
		}
		setBaseEnabled(needToAdd);
	}


	private static Collection<EObject> getDeployableObjects(ISelection selection){
		Set<EObject> retVal = new HashSet<>();
		if (selection instanceof StructuredSelection) {
			for (Object selectedObject : (( StructuredSelection)selection).toArray()) {
				if(selectedObject instanceof EObject) {
					addEObject((EObject)selectedObject, retVal);					
				} else if(selectedObject instanceof EditPart &&
						((EditPart)selectedObject).getModel() instanceof EObject) {
					addEObject((EObject)((EditPart)selectedObject).getModel(), retVal);
				}				
			}
		}		
		return retVal;
	}


	private static void addEObject(EObject object, Set<EObject> retVal) {
		if(object instanceof AutomationSystem){
			for(Device dev : ((AutomationSystem)object).getSystemConfiguration().getDevices()) {
				addDeviceContent(dev, retVal);
			}			
		}else if(object instanceof SystemConfiguration){
			for(Device dev : ((SystemConfiguration)object).getDevices()) {
				addDeviceContent(dev, retVal);
			}
		}else if(object instanceof Device){
			addDeviceContent((Device)object, retVal);			
		}else if(object instanceof Resource){
			retVal.add((Resource)object);
		}		
	}


	private static void addDeviceContent(Device dev, Set<EObject> retVal) {
		retVal.add(dev);
		for(Resource res : dev.getResource()) {
			retVal.add(res);
		}		
	}
	
}
