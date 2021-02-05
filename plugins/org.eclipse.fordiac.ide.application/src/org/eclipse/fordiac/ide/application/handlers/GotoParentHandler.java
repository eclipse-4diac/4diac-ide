/*******************************************************************************
 * Copyright (c) 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added check if subapp interface is selected and mark that in
                   parent
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import static org.eclipse.fordiac.ide.gef.handlers.BreadcrumbUtil.getViewer;
import static org.eclipse.fordiac.ide.gef.handlers.BreadcrumbUtil.openEditor;
import static org.eclipse.fordiac.ide.gef.handlers.BreadcrumbUtil.selectElement;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;
public class GotoParentHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
		final EObject model = fbnetwork.eContainer().eContainer().eContainer();

		final IEditorPart newEditor = openEditor(model);
		if (null != newEditor) {
			handleSelection(newEditor, fbnetwork, getViewer(editor).getSelection());
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (null != editor) {
			final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
			setBaseEnabled((null != fbnetwork) && fbnetwork.isSubApplicationNetwork());
		}
	}

	private static void handleSelection(IEditorPart newEditor, FBNetwork model, ISelection selection) {
		final IInterfaceElement selIElement = getSelectedSubappInterfaceElement(selection);

		if ((null != selIElement) && (((SubApp) selIElement.getFBNetworkElement()).getSubAppNetwork().equals(model))) {
			selectElement(selIElement, getViewer(newEditor));
		} else {
			selectElement(model.eContainer(), getViewer(newEditor));
		}
	}

	/**
	 * check if the current selection is a single subapp interface element
	 */
	private static IInterfaceElement getSelectedSubappInterfaceElement(ISelection selection) {
		if ((selection instanceof StructuredSelection) && (((StructuredSelection) selection).size() == 1)) {
			// only one element is selected
			final Object selObj = ((StructuredSelection) selection).getFirstElement();
			if ((selObj instanceof EditPart) && (((EditPart) selObj).getModel() instanceof IInterfaceElement)) {
				final IInterfaceElement elem = (IInterfaceElement) ((EditPart) selObj).getModel();
				if (elem.getFBNetworkElement() instanceof SubApp) {
					return elem;
				}
			}
		}
		return null;
	}

}
