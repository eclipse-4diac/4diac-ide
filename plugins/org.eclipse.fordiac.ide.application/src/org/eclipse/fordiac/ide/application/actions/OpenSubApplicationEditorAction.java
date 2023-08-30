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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/** The Class OpenSubApplicationEditorAction. */
public class OpenSubApplicationEditorAction extends AbstractOpenSystemElementListener {

	private static final String OPEN_SUBAPP_LISTENER_ID = "org.eclipse.fordiac.ide.application.actions.OpenSubApplicationEditorAction"; //$NON-NLS-1$

	/** The uiSubAppNetwork. */
	private SubApp subApp;

	public OpenSubApplicationEditorAction() {
		// empty constructor for OpenListener
	}

	@Override
	public void run(final IAction action) {
		final EObject root = EcoreUtil.getRootContainer(subApp);
		if (root instanceof final AutomationSystem automationSystem) {
			openInSystemEditor(automationSystem.getTypeEntry().getFile(), subApp);
		} else if (root instanceof final SubAppType subAppType) {
			openInSubappTypeEditor(subAppType, subApp);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structuredSel)
				&& (structuredSel.getFirstElement() instanceof final SubApp firstElement)) {
			subApp = firstElement;
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
