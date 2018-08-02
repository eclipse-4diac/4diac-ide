/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.actions;

import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.Messages;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * The Class OpenResourceEditorAction.
 */
public class OpenResourceEditorAction extends OpenListener {
	
	private static final String OPEN_RES_EDITOR_LISTENER_ID = "org.eclipse.fordiac.ide.resourceediting.actions.OpenResourceEditorAction"; //$NON-NLS-1$

	/** The res. */
	private Resource res;

	@Override
	public void run(final IAction action) {
		if(null != res){
			ResourceEditorInput input = new ResourceEditorInput(res);			
			openEditor(input, ResourceDiagramEditor.class.getName());	
		}
	}

	@Override
	public void selectionChanged(final IAction action,
			final ISelection selection) {
		res = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof Resource) {
				res = (Resource) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public String getActionText() {
		return Messages.OpenResourceEditorAction_Name;
	}

	@Override
	public Class<? extends I4DIACElement> getHandledClass() {
		return Resource.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_RES_EDITOR_LISTENER_ID;
	}

}
