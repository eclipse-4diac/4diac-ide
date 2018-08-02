/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.DistributedSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class SystemContentProvider extends AdapterFactoryContentProvider implements DistributedSystemListener  {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());
	
	private BaseWorkbenchContentProvider workbenchContentProvider = new BaseWorkbenchContentProvider();
	
	public SystemContentProvider() {
		super(systemAdapterFactory);
		SystemManager.INSTANCE.addWorkspaceListener(this);
	}


	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// this content provider is only requried on the lowest level of the tree
		if((null == parentElement) || (parentElement instanceof IWorkspaceRoot)){
			return getProjects();
		}
		if(parentElement instanceof IResource){
			return workbenchContentProvider.getChildren(parentElement);
		}		
		return super.getChildren(parentElement);
	}


	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject){
			return (null != SystemManager.INSTANCE.getSystemForName(((IProject)element).getName()));
		}
		return super.hasChildren(element);
	}
	
		
	private static List<AdapterFactory> createFactoryList(){
		ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new SystemElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}
	
	private static Object[] getProjects() {
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		IProject[] projects = myWorkspaceRoot.getProjects();
		
		ArrayList<Object> list = new ArrayList<>();
		
		for (IProject project : projects) {
			if(!project.getName().equals(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME)){
				AutomationSystem system = SystemManager.INSTANCE.getSystemForName(project.getName());
				if(null != system){
					list.add(system);
				}else{
					list.add(project);
				}
			}					
		}			
		return (list.isEmpty()) ? null : list.toArray();
	}


	@Override
	public void distributedSystemWorkspaceChanged() {		
		if(null != viewer && null != viewer.getControl() && null != viewer.getControl().getDisplay()){
			viewer.getControl().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					viewer.refresh();		
				}
			});		
		}
	}

}
