/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2013, 2015, 2016 Profactor GmbH, fortiss GmbH
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

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * The Class OpenSubApplicationEditorAction.
 */
public class OpenSubApplicationEditorAction extends OpenListener {
	
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

	/**
	 * Opens the editor for the specified Model or sets the focus to the editor if
	 * already opened.
	 */
	public void run() {
		
		SubApplicationEditorInput input = new SubApplicationEditorInput(subApp);

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			editor = activePage.openEditor(input, SubAppNetworkEditor.class.getName());
		} catch (PartInitException e) {
			editor = null;
			ApplicationPlugin
					.getDefault()
					.logError(
							Messages.OpenSubApplicationEditorAction_ERROR_OpenSubapplicationEditor,
							e);
		}

	}

	@Override
	public void run(IAction action) {
		run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof SubApp) {
				subApp = (SubApp) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public String getActionText() {
		return Messages.OpenSubApplicationEditorAction_Name;
	}

	@Override
	public Class<? extends I4DIACElement> getHandledClass() {
		return SubApp.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_SUBAPP_LISTENER_ID;
	}
}
