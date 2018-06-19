/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University
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

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers.AbstractEditorLinkHelper;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class Open4DIACElementAction extends BaseSelectionListenerAction {
	
	public static final String ID = Activator.PLUGIN_ID + ".OpenAction";//$NON-NLS-1$

	public Open4DIACElementAction(IWorkbenchPart part) {
		super(Messages.OpenEditorAction_text);
		setId(ID);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		boolean retval = true;
		Iterator element = getStructuredSelection().iterator();
		while(element.hasNext() && (retval)) {
			Object obj = element.next();
			if((obj instanceof Device) || (obj instanceof SystemConfiguration) || (obj instanceof Application) ||
					(obj instanceof SubApp) || obj instanceof Resource){
				continue;
			}else if(obj instanceof FB){
				//if we have an Fb check if it is in a subapp or application
				retval = isFBInAppOrSubApp((FB)obj); 
						
			}else{
				retval = false;
			}
		}
		return retval;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		Iterator element = getStructuredSelection().iterator();
		while(element.hasNext()) {
			Object obj = element.next();
			Object refObject = null;
			
			if(obj instanceof FB){
				//if an FB is selected we need to open the according root node and use FB for selecting
				refObject = obj;
				obj = getFBRootNode((FB)obj);
			}else if (obj instanceof Device){
				refObject = obj;
				obj = ((Device)obj).getSystemConfiguration();				
			}

			IEditorPart editor = OpenListenerManager.openEditor((I4DIACElement) obj);
			AbstractEditorLinkHelper.selectObject(editor,refObject);
		}
	}

	private static boolean isFBInAppOrSubApp(FB fb) {
		EObject rootNode = getFBRootNode(fb);
		return ((rootNode instanceof Application) || (rootNode instanceof SubApp));
	}

	private static EObject getFBRootNode(FB fb) {
		EObject fbCont = fb.eContainer();
		EObject rootNode = null;
		if(fbCont instanceof FBNetwork){
			rootNode = ((FBNetwork)fbCont).eContainer();
		}
		return rootNode;
	}


	
	
}
