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
 *   Michael Oberlehner - initial API and implementation and/or initial
 *                        documentation
 *   Alois Zoitl        - added support for cfb viewers in compiste and subapp
 *                        types
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class OpenCompositeInstanceViewerOpenListener extends AbstractOpenSystemElementListener {

	private static final String OPEN_COMPOSITE_LISTENER_ID = "org.eclipse.fordiac.ide.fbtypeeditor.network.actions.OpenCompositeInstanceViewerOpenListener"; //$NON-NLS-1$

	private FB compositeFBInstance;

	public OpenCompositeInstanceViewerOpenListener() {
		// empty constructor for OpenListener
	}

	@Override
	public void run(final IAction action) {
		final EObject root = EcoreUtil.getRootContainer(compositeFBInstance);
		if (root instanceof final AutomationSystem as) {
			openInSystemEditor(as.getTypeEntry().getFile(), compositeFBInstance);
		} else if (root instanceof final SubAppType subAppType) {
			openInSubappTypeEditor(subAppType, compositeFBInstance);
		} else if (root instanceof final CompositeFBType cfbType) {
			openInFBTypeEditor(cfbType, compositeFBInstance);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof final IStructuredSelection structuredSel
				&& structuredSel.getFirstElement() instanceof final FB firstEl) {
			compositeFBInstance = firstEl;
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
