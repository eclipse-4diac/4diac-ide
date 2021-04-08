/*******************************************************************************
 * Copyright (c) 2015, 2016, 2018 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Application and Subapplication linking need to be performed in one class as
 * it has the same trigger classes and FB selection would not work otherwise.
 *
 */
public class ApplicationSubAppEditorLinkHelper extends AbstractEditorLinkHelper {

	@Override
	public IStructuredSelection findSelection(final IEditorInput anInput) {
		if (anInput instanceof ApplicationEditorInput) {
			final ApplicationEditorInput appInput = (ApplicationEditorInput) anInput;
			return new StructuredSelection(appInput.getContent());
		} else if (anInput instanceof SubApplicationEditorInput) {
			final SubApplicationEditorInput subAppInput = (SubApplicationEditorInput) anInput;
			return new StructuredSelection(subAppInput.getSubApp());
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(final IWorkbenchPage aPage, final IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}

		if (aSelection.getFirstElement() instanceof Application) {
			performEditorSelect(aPage, new ApplicationEditorInput((Application) aSelection.getFirstElement()), null);
		}
		if ((aSelection.getFirstElement() instanceof SubApp)
				&& (((SubApp) aSelection.getFirstElement()).isTyped())) {
			// we have an untyped subapp
			handleUntypedSubApp(aPage, (SubApp) aSelection.getFirstElement());
		} else if (aSelection.getFirstElement() instanceof FBNetworkElement) {
			final FBNetworkElement refElement = (FBNetworkElement) aSelection.getFirstElement();
			performFBNElementSelection(aPage, refElement);
		}
	}

	private static void performEditorSelect(final IWorkbenchPage aPage, final IEditorInput editorInput,
			final FBNetworkElement refElement) {
		final IEditorPart editor = activateEditor(aPage, editorInput);
		HandlerHelper.selectElement(refElement, editor);
	}

	/**
	 * For untyped suapps first try to bring the subapp's editor to the front. If
	 * the editor is not open look for the editor the subapp is contained in.
	 */
	private static void handleUntypedSubApp(final IWorkbenchPage aPage, final SubApp subApp) {
		final IEditorPart editor = activateEditor(aPage, generateSubAppEditorInput(subApp));
		if (null == editor) {
			// we could not find the editor for this untyped subapp lets try if we can
			// select it in its containing editor
			performFBNElementSelection(aPage, subApp);
		}
	}

	private static SubApplicationEditorInput generateSubAppEditorInput(final SubApp subApp) {
		return new SubApplicationEditorInput(subApp);
	}

	/**
	 * A typed subapp or fb has been selected find the containing element open its
	 * editor and select it in this editor
	 */
	private static void performFBNElementSelection(final IWorkbenchPage aPage, final FBNetworkElement refElement) {
		final EObject fbCont = refElement.eContainer();
		if (fbCont instanceof FBNetwork) {
			final EObject obj = ((FBNetwork) fbCont).eContainer();
			if (obj instanceof Application) {
				performEditorSelect(aPage, new ApplicationEditorInput((Application) obj), refElement);
			} else if (obj instanceof SubApp) {
				performEditorSelect(aPage, generateSubAppEditorInput((SubApp) obj), refElement);
			}
		}
	}

}
