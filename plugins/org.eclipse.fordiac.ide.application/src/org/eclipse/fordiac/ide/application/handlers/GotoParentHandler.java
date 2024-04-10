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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
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
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor != null) {
			final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
			final FBNetworkElement element = editor.getAdapter(FBNetworkElement.class);

			if (null != element) {
				// get parent (can be application, subapp, cfbinstance, cfbtype, subapptype)
				final EObject parent = getFBNetworkContainer(element);
				if (parent != null) {
					final IEditorPart newEditor = openEditor(parent);

					if (null != newEditor) {
						handleSelection(newEditor, element, selection);
					}
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

	/**
	 * Find the correct parent for an object to be used as the element for the
	 * parent editor.
	 *
	 * This method searches for a non unfolded subapp fbnetwork container.
	 *
	 * @param object the object which should be shown in its parent.
	 * @return the parent container (i.e., Application, CFB/SubappType, or Untyped
	 *         Subapp
	 */
	private static EObject getFBNetworkContainer(EObject object) {
		while (object != null) {
			object = object.eContainer();
			if (object instanceof FBNetwork
					&& !(object.eContainer() instanceof final SubApp subApp && subApp.isUnfolded())) {
				return object.eContainer();
			}
		}
		return null;
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

	/**
	 * check if the current selection is a single subapp interface element of our
	 * fbnetwork element
	 */
	private static IInterfaceElement getSelectedSubappInterfaceElement(final ISelection selection,
			final FBNetworkElement element) {
		// only one element is selected
		if (((selection instanceof final StructuredSelection structSel) && (structSel.size() == 1))
				&& ((structSel.getFirstElement() instanceof final EditPart ep)
						&& (ep.getModel() instanceof final IInterfaceElement ie))
				&& (element.equals(ie.getFBNetworkElement()))) {
			return ie;
		}
		return null;
	}
}
