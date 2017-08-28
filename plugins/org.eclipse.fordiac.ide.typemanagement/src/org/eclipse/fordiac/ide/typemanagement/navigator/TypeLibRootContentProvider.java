/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class TypeLibRootContentProvider implements ITreeContentProvider {

	BaseWorkbenchContentProvider workbenchContentProvider = new BaseWorkbenchContentProvider(); 
	
	HashMap<AutomationSystem, TypeLibRootElement> typeLibElementStore = new HashMap< >();
	
	@Override
	public void dispose() {
		workbenchContentProvider.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof AutomationSystem){
			Object [] retVal = new Object[1];
			retVal[0] = getTypeLibRootElement((AutomationSystem)parentElement);
			return retVal;
		}else if(parentElement instanceof TypeLibRootElement){
			return workbenchContentProvider.getChildren(((TypeLibRootElement)parentElement).getSystem().getProject());
		}
		return null;
	}


	@Override
	public Object getParent(Object element) {
		if(element instanceof IResource && ((IResource)element).getParent() instanceof IProject){
			return getTypeLibRootElement((IProject)((IResource)element).getParent());
		}
		if(element instanceof TypeLibRootElement){
			return ((TypeLibRootElement)element).getSystem();
		}
		return null;
	}
	

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof TypeLibRootElement){
			return workbenchContentProvider.hasChildren(((TypeLibRootElement)element).getSystem().getProject());
		}
		return false;
	}

	private TypeLibRootElement getTypeLibRootElement(AutomationSystem system) {
		TypeLibRootElement retVal = typeLibElementStore.get(system);
		
		if( null == retVal){
			retVal = new TypeLibRootElement(system);
			typeLibElementStore.put(system, retVal);
		}
		return retVal;
	}
	
	private Object getTypeLibRootElement(IProject parent) {
		return getTypeLibRootElement(SystemManager.INSTANCE.getSystemForName(parent.getName()));
	}
	
}
