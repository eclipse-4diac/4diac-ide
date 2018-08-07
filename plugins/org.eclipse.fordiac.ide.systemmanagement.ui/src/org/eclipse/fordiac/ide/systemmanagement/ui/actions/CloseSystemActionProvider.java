/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class CloseSystemActionProvider extends CommonActionProvider {
	
	private CloseResourceAction closeProjectAction;
	
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		
		IShellProvider sp = new IShellProvider() {
			@Override
			public Shell getShell() {
				return getActionSite().getViewSite().getShell();
			}
		};
		closeProjectAction = new CloseResourceAction(sp);
	}
	
	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
		updateActionBars();
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		IProject project = getSelectedProject();
		if(null != project){
			closeProjectAction.selectionChanged(new StructuredSelection(project));
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, closeProjectAction);
		}
	}
	
	@Override
	public void updateActionBars() {
		IProject project = getSelectedProject();
		if(null != project){		
			closeProjectAction.selectionChanged(new StructuredSelection(project));
		}
	}
	
	private IProject getSelectedProject(){
		IProject project = null;
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if(selection.getFirstElement() instanceof AutomationSystem){
			project = ((AutomationSystem)selection.getFirstElement()).getProject();
		}
		return project;
	}
}
