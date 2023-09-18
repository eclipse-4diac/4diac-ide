/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/** An Action which opens the <code>ApplicationEditor</code> for the specified Model. */
public class OpenApplicationEditorAction extends AbstractOpenSystemElementListener {

	private static final String OPEN_APP_LISTENER_ID = "org.eclipse.fordiac.ide.application.actions.OpenApplicationEditorAction"; //$NON-NLS-1$

	/** The app. */
	private Application app;

	@Override
	public void run(final IAction action) {
		final IFile file = app.getAutomationSystem().getTypeEntry().getFile();
		openInSystemEditor(file, app);
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structuredSel)
				&& (structuredSel.getFirstElement() instanceof final Application firstElement)) {
			app = firstElement;
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return Application.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_APP_LISTENER_ID;
	}

}
