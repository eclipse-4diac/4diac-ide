/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class GoToHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		if (sel != null) {
			final Object obj = sel.getFirstElement();
			if (obj instanceof final FBNetworkElement fbnElement) {
				showFBInEditor(fbnElement);
			} else if (obj instanceof final Application app) {
				showApplication(app);
			}
		}
		return Status.OK_STATUS;
	}

	private static void showApplication(final Application obj) {
		final IEditorPart editor = HandlerHelper.openEditor(obj);
		HandlerHelper.selectElement(obj, editor);
	}

	private static void showFBInEditor(final FBNetworkElement el) {
		EObject toView = el.eContainer().eContainer();
		// For unfolded subapps find the next parent that is not expanded as refElement
		while (toView instanceof final SubApp subApp && subApp.isUnfolded()) {
			toView = subApp.eContainer().eContainer();
		}
		final IEditorPart editor = HandlerHelper.openEditor(toView);
		HandlerHelper.selectElement(el, editor);
	}

}
