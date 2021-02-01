/*******************************************************************************
 * Copyright (c) 2021 Primemetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class OpenCompositeInstanceViewerOpenListener extends AbstractOpenSystemElementListener {

	private static final String OPEN_COMPOSITE_LISTENER_ID = "org.eclipse.fordiac.ide.fbtypeeditor.network.actions.OpenCompositeInstanceViewerOpenListener"; //$NON-NLS-1$

	private FB compositeFBType;

	/** Constructor of the Action.
	 *
	 * @param uiSubAppNetwork the UISubAppNetwork */
	public OpenCompositeInstanceViewerOpenListener(final FB compositeFBType) {
		this.compositeFBType = compositeFBType;
	}

	/** Consturctor. */
	public OpenCompositeInstanceViewerOpenListener() {
		// empty constructor for OpenListener
	}

	/** Opens the editor for the specified Model or sets the focus to the editor if already opened. */
	public void run() {
		openInSystemEditor(compositeFBType.getFbNetwork().getAutomationSystem().getSystemFile(), compositeFBType);
	}

	@Override
	public void run(final IAction action) {
		run();
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof FB) {
				compositeFBType = (FB) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return FB.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_COMPOSITE_LISTENER_ID;
	}
}
