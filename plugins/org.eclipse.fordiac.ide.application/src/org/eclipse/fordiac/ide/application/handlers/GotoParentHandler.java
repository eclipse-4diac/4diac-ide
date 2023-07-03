/*******************************************************************************
 * Copyright (c) 2019 - 2020 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
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
 *                  parent
 *   Lukas Wais  - implemented functionality for instance viewer
 *   Alois Zoitl, Michael Genser,
 *   Lukas Wais  - implemented new added adapters for FBNetworkelement and clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.getViewer;
import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.openEditor;
import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.selectElement;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class GotoParentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor != null) {
			final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
			final FBNetworkElement element = editor.getAdapter(FBNetworkElement.class);

			if (null != element) {
				// get parent (can be application, subapp, cfbinstance, cfbtype, subapptype)
				final EObject model = element.eContainer().eContainer();
				final IEditorPart newEditor = openEditor(model);

				if (null != newEditor) {
					handleSelection(newEditor, element, selection);
				}
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (null != editor) {
			final FBNetworkElement element = editor.getAdapter(FBNetworkElement.class);
			setBaseEnabled(null != element);
		}
	}

	private static void handleSelection(final IEditorPart newEditor, final FBNetworkElement element,
			final ISelection selection) {
		final IInterfaceElement selIElement = getSelectedSubappInterfaceElement(selection, element);

		if (null != selIElement) {
			selectElement(selIElement, getViewer(newEditor));
		} else {
			selectElement(element, getViewer(newEditor));
		}
	}

	/** check if the current selection is a single subapp interface element of our fbnetwork element */
	private static IInterfaceElement getSelectedSubappInterfaceElement(final ISelection selection,
			final FBNetworkElement element) {
		if ((selection instanceof StructuredSelection) && (((StructuredSelection) selection).size() == 1)) {
			// only one element is selected
			final Object selObj = ((StructuredSelection) selection).getFirstElement();
			if ((selObj instanceof EditPart) && (((EditPart) selObj).getModel() instanceof IInterfaceElement)) {
				final IInterfaceElement ie = (IInterfaceElement) ((EditPart) selObj).getModel();
				if (element.equals(ie.getFBNetworkElement())) {
					return ie;
				}
			}
		}
		return null;
	}
}
