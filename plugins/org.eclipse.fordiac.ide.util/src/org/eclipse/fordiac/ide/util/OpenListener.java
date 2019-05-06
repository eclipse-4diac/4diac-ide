/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2017 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.util;

import org.eclipse.fordiac.ide.util.action.OpenListenerAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/** Helperclass for reducing the effort to implement open listeners
 */
public abstract class OpenListener implements IOpenListener {

	private IEditorPart editor = null;
	
	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		// nothing to do
	}	
	
	@Override
	public IEditorPart getOpenedEditor() {
		return editor;
	}
	
	@Override
	public final Action getOpenListenerAction() {
		return new OpenListenerAction(this);
	}
	
	protected void openEditor(IEditorInput input, String editorId) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			editor = activePage.openEditor(input, editorId);
		} catch (PartInitException e) {
			editor = null;
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}


	
}
