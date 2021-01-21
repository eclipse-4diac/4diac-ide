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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.gef.handlers.FordiacHandler;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class GotoParentHandler extends FordiacHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
		final EObject model = fbnetwork.eContainer().eContainer().eContainer();

		final FBNetworkEditor newEditor = (FBNetworkEditor) EditorUtils.openEditor(getEditorInput(model), getEditorId(model));
		if (null != newEditor) {
			handleSelection(newEditor, fbnetwork, getViewer(editor).getSelection());
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_ID_NAME);
		setBaseEnabled(SubAppNetworkEditor.class.getName().equals(selection));
	}

	private static IEditorInput getEditorInput(EObject model) {
		if (model instanceof SubApp) {
			return new SubApplicationEditorInput((SubApp) model);
		}
		if (model instanceof Application) {
			return new ApplicationEditorInput((Application) model);
		}
		return null;
	}

	private static String getEditorId(EObject model) {
		if (model instanceof SubApp) {
			return SubAppNetworkEditor.class.getName();
		}
		if (model instanceof Application) {
			return FBNetworkEditor.class.getName();
		}
		return null;
	}

	private static void handleSelection(FBNetworkEditor newEditor, FBNetwork model, ISelection selection) {
		final IInterfaceElement selIElement = getSelectedSubappInterfaceElement(selection);

		if ((null != selIElement) && (((SubApp) selIElement.getFBNetworkElement()).getSubAppNetwork().equals(model))) {
			newEditor.selectElement(selIElement);
		} else {
			newEditor.selectElement(model.eContainer());
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
