/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * An Action which opens the <code>ApplicationEditor</code> for the specified
 * Model.
 */
public class OpenApplicationEditorAction extends OpenListener {
	
	private static final String OPEN_APP_LISTENER_ID = "org.eclipse.fordiac.ide.application.actions.OpenApplicationEditorAction"; //$NON-NLS-1$

	/** The app. */
	private Application app;

	@Override
	public void run(final IAction action) {
		ApplicationEditorInput input = new ApplicationEditorInput(app);
		openEditor(input, FBNetworkEditor.class.getName());
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof Application) {
				app = (Application) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public String getActionText() {
		return Messages.OpenApplicationEditorAction_Name;
	}

	@Override
	public Class<? extends I4DIACElement> getHandledClass() {
		return Application.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_APP_LISTENER_ID;
	}

}
