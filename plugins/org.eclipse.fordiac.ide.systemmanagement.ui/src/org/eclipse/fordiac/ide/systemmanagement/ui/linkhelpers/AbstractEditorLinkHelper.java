/*******************************************************************************
 * Copyright (c) 2015, 2018 fortiss GmbH, Johannes Kepler University
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

import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

public abstract class AbstractEditorLinkHelper implements ILinkHelper {

	protected IEditorPart activateEditor(IWorkbenchPage aPage, IEditorInput editorInput) {
		IEditorPart editor = aPage.findEditor(editorInput);
		if (null != editor){
			aPage.bringToTop(editor);
		}
		return editor;
	}
	
	/**helper method for selecting objects inside the main editors 
	 * 
	 * This can be when they are single clicked in the system explorer and link with editor 
	 * is activated, or double clicked when opening an elemetns base editor.
	 *
	 * 
	 * @param editor    the editor that was chosen for the element, may be null
	 * @param selObject the object to be selected, may be null
	 */
	public static void selectObject(IEditorPart editor, Object selObject) {
		if ((editor instanceof FBNetworkEditor) && (selObject instanceof FB)){
			((FBNetworkEditor)editor).selectFB((FB)selObject);
			return;
		}
		if((editor instanceof SystemConfigurationEditor) && 
				((selObject instanceof Device) || selObject instanceof Segment)){
			((SystemConfigurationEditor)editor).selectElement((TypedConfigureableObject)selObject);
			return;
		}
	}

}