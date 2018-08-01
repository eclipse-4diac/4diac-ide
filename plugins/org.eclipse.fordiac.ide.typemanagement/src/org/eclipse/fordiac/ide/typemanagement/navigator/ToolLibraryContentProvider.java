/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ToolLibraryContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		//currently nothing todo here
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		//currently nothing todo here
	}

	@Override
	public Object[] getElements(Object inputElement) {

		if((null == inputElement) || (inputElement instanceof IWorkspaceRoot)){
			// this content provider is only requried on the lowest level of the tree
			IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
	
			IFolder toolLibFolder = TypeLibrary.getToolLibFolder();
			
			IProject[] projects = myWorkspaceRoot.getProjects();
	
			Object[] retval = new Object[projects.length];
			
			//tool library should be first
			retval[0] = toolLibFolder;
			
			for(int i = 0, outputRunner = 1; i < projects.length; i++){
				if(!projects[i].getName().equals(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME)){
					retval[outputRunner] = projects[i];
					outputRunner++;
				}
			}
			
			return retval;
		}
		else{
			if(inputElement instanceof IContainer){
				try {
					return ((IContainer)inputElement).members();
				} catch (CoreException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}			
		}
		
		return null;
	}

	

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
