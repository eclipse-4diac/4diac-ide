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
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

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

}