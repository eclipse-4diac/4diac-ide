/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/** The Class OpenGroupEditorAction. */
public class OpenGroupEditorAction extends AbstractOpenSystemElementListener {

	private static final String OPEN_GROUP_LISTENER_ID = "org.eclipse.fordiac.ide.application.actions.OpenGroupEditorAction"; //$NON-NLS-1$

	/** The uiGroupAppNetwork. */
	private Group group;

	public OpenGroupEditorAction() {
		// empty constructor for OpenListener
	}

	@Override
	public void run(final IAction action) {
		final EObject root = EcoreUtil.getRootContainer(group);
		if (root instanceof final AutomationSystem automationSystem) {
			openInSystemEditor(automationSystem.getTypeEntry().getFile(), group);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structuredSel)
				&& (structuredSel.getFirstElement() instanceof final Group firstElement)) {
			group = firstElement;
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return Group.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_GROUP_LISTENER_ID;
	}

}
