/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.commands.FlattenSubAppCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class FlattenSubApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor)HandlerUtil.getActiveEditor(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Object[] list = getObjectArrayFromSelection(selection);
		if (null != list) {
			for (Object currentElement : list) {
				SubApp subApp = getSubApp(currentElement);
				if(null != subApp){
					FlattenSubAppCommand cmd = new FlattenSubAppCommand(subApp);
					editor.getCommandStack().execute(cmd);	
				}
			}
		}
		return null;
	}

	private SubApp getSubApp(Object currentElement) {
		if(currentElement instanceof SubApp){
			return (SubApp) currentElement;
		} else if (currentElement instanceof SubAppForFBNetworkEditPart){
			return ((SubAppForFBNetworkEditPart)currentElement).getModel();
		}		
		return null;
	}

	protected Object[] getObjectArrayFromSelection(ISelection selection){
		Object[] list = null;
		if(selection instanceof StructuredSelection) {
			list = ((StructuredSelection) selection).toArray();
		}
		return list;
	}
}
