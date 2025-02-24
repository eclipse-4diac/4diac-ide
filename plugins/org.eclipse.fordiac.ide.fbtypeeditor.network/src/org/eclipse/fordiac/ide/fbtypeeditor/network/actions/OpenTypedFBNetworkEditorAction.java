/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.network.CompositeNetworkEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.network.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListener;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;

public class OpenTypedFBNetworkEditorAction extends OpenListener {

	private static final String OPEN_TYPED_NETWORK_LISTENER_ID = "org.eclipse.fordiac.ide.fbtypeeditor.network.OpenTypedFBNetworkEditorAction"; //$NON-NLS-1$

	private FBNetwork fbNetworkToOpen;

	@Override
	public void run(final IAction action) {
		final EObject root = EcoreUtil.getRootContainer(fbNetworkToOpen);

		switch (root) {
		case final SubAppType subApp -> openInSubappTypeEditor(subApp, fbNetworkToOpen.eContainer());
		case final FBType fbType -> openCFBNetworkEditor(fbType);
		default -> {
			/* nothing to do in default case */ }
		}
	}

	private void openCFBNetworkEditor(final FBType fbType) {
		openInFBTypeEditor(fbType, fbNetworkToOpen.eContainer());
		if (getOpenedEditor() instanceof final FormEditor formEditor) {
			final IEditorInput input = formEditor.getActiveEditor().getEditorInput();
			for (final IEditorPart subEditor : formEditor.findEditors(input)) {
				if (subEditor instanceof final CompositeNetworkEditor cne) {
					cne.revealEditor();
				}
			}
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structuredSel)
				&& (structuredSel.getFirstElement() instanceof final FBNetwork fbn)) {
			fbNetworkToOpen = fbn;
		}
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return FBNetwork.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_TYPED_NETWORK_LISTENER_ID;
	}

	@Override
	public String getActionText() {
		return Messages.OpenTypedFBNetwork_ActionText;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return FordiacImage.ICON_FB_NETWORK.getImageDescriptor();
	}

}
