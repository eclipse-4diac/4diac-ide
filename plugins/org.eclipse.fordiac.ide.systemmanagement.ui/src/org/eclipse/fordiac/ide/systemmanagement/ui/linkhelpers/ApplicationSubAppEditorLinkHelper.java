/*******************************************************************************
 * Copyright (c) 2015, 2016, 2018 fortiss GmbH, Johannes Kepler University
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;


/** Application and Subapplication linking need to be performed in one class as it has the same trigger classes
 * and FB selection would not work otherwise.
 * 
 */
public class ApplicationSubAppEditorLinkHelper extends AbstractEditorLinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {		
		if(anInput instanceof ApplicationEditorInput){
			ApplicationEditorInput appInput = (ApplicationEditorInput)anInput;
			return new StructuredSelection(appInput.getApplication());
		}else if (anInput instanceof SubApplicationEditorInput){
			SubApplicationEditorInput subAppInput = (SubApplicationEditorInput)anInput;
			return new StructuredSelection(subAppInput.getSubApp());
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()){
			return;
		}
		
		if (aSelection.getFirstElement() instanceof Application) {
			performEditorSelect(aPage, new ApplicationEditorInput((Application)aSelection.getFirstElement()), null);
		} else if (aSelection.getFirstElement() instanceof SubApp){
			performEditorSelect(aPage, generateSubAppEditorInput((SubApp)aSelection.getFirstElement()), null);
		} else if(aSelection.getFirstElement() instanceof FB){
			FB refFB = (FB)aSelection.getFirstElement();
			EObject fbCont = refFB.eContainer();
			if(fbCont instanceof FBNetwork){
				EObject obj = ((FBNetwork)fbCont).eContainer();
				if(obj instanceof Application){
					performEditorSelect(aPage, new ApplicationEditorInput((Application)obj), refFB);
				}else if(obj instanceof SubApp){
					performEditorSelect(aPage, generateSubAppEditorInput((SubApp)obj), refFB);					
				}
			}
		}		
	}

	private void performEditorSelect(IWorkbenchPage aPage, IEditorInput editorInput, FB refFB) {
		IEditorPart editor = activateEditor(aPage, editorInput);
		selectObject(editor, refFB);
	}
	
	private static SubApplicationEditorInput generateSubAppEditorInput(SubApp subApp){
		return new SubApplicationEditorInput(subApp);
	}

}
