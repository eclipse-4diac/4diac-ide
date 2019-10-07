/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils  
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.util.action.OpenListenerAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Helper class for reducing the effort to implement open listeners
 */
public abstract class OpenListener implements IOpenListener {

	private IEditorPart editor = null;

	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
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
		editor = EditorUtils.openEditor(input, editorId);
	}

}
