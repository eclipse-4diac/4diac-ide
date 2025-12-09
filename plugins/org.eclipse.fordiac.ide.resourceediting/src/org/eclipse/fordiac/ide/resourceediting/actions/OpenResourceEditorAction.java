/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.resourceediting.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * The Class OpenResourceEditorAction.
 */
public class OpenResourceEditorAction extends AbstractOpenSystemElementListener {

	private static final String OPEN_RES_EDITOR_LISTENER_ID = "org.eclipse.fordiac.ide.resourceediting.actions.OpenResourceEditorAction"; //$NON-NLS-1$

	/** The res. */
	private Resource res;

	@Override
	public void run(final IAction action) {
		if (null != res) {
			openInSystemEditor(res.getAutomationSystem().getTypeEntry().getFile(), res);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		res = null;
		if (selection instanceof final IStructuredSelection structuredSel
				&& structuredSel.getFirstElement() instanceof final Resource firstSel) {
			res = firstSel;
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return Resource.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_RES_EDITOR_LISTENER_ID;
	}

}
