/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;

public class ResourceEditorLinkHelper extends AbstractEditorLinkHelper  {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		if(anInput instanceof ResourceEditorInput){
			ResourceEditorInput resInput = (ResourceEditorInput)anInput;
			return new StructuredSelection(resInput.getContent());
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage,
			IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()){
			return;
		}
		
		if (aSelection.getFirstElement() instanceof Resource) {
			IEditorInput resourceInput = new ResourceEditorInput((Resource)aSelection.getFirstElement());
			activateEditor(aPage, resourceInput);	
		}
	}

}
