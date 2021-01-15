/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2013, 2015, 2016 Profactor GmbH, fortiss GmbH
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * The Class OpenSubApplicationEditorAction.
 */
public class OpenSubApplicationEditorAction extends AbstractOpenSystemElementListener {

	private static final String OPEN_SUBAPP_LISTENER_ID = "org.eclipse.fordiac.ide.application.actions.OpenSubApplicationEditorAction"; //$NON-NLS-1$

	/** The uiSubAppNetwork. */
	private SubApp subApp;

	/**
	 * Constructor of the Action.
	 *
	 * @param uiSubAppNetwork the UISubAppNetwork
	 */
	public OpenSubApplicationEditorAction(final SubApp subApp) {
		this.subApp = subApp;
	}

	/**
	 * Consturctor.
	 */
	public OpenSubApplicationEditorAction() {
		// empty constructor for OpenListener
	}

	/** Opens the editor for the specified Model or sets the focus to the editor if already opened. */
	public void run() {
		openInSystemEditor(subApp.getFbNetwork().getAutomationSystem().getSystemFile(), subApp);
	}

	@Override
	public void run(final IAction action) {
		run();
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof SubApp) {
				subApp = (SubApp) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return SubApp.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_SUBAPP_LISTENER_ID;
	}
}
