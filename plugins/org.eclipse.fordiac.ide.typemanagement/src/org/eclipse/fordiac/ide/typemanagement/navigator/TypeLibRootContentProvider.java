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
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class TypeLibRootContentProvider implements ITreeContentProvider, IResourceChangeListener {

	private BaseWorkbenchContentProvider workbenchContentProvider = new BaseWorkbenchContentProvider(); 
	
	private Map<AutomationSystem, TypeLibRootElement> typeLibElementStore = new HashMap< >();
	
	private Viewer viewer;
	
	public TypeLibRootContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void dispose() {
		workbenchContentProvider.dispose();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
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

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if(null != viewer) {
			//TODO: for performance reason and to avoid to main change listeners going active it may be interesting to check here if the change is a 
			//      change in the rout folder of a project. 			
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if(!viewer.getControl().isDisposed()){
						viewer.refresh();
					}						
				}
			});
		}
	}
	
}
