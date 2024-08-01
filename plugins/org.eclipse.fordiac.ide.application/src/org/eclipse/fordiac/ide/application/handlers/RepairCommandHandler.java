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
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.wizards.MissingDataTypeWizard;
import org.eclipse.fordiac.ide.typemanagement.wizards.MissingFBTypeWizard;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.views.markers.MarkerItem;

public class RepairCommandHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		EObject eObject = getEObjectFromProblemViewSelection(selection);

		if (eObject == null) {
			eObject = getEObjectFromEditorSelection(selection);
		}

		repair(eObject);
		return null;
	}

	private static void repair(final EObject eObject) {
		if (eObject instanceof final VarDeclaration varDecl && varDecl.getType() instanceof ErrorMarkerDataType) {
			final MissingDataTypeWizard wizard = new MissingDataTypeWizard(varDecl);
			wizard.openWizard();
		}

		if (eObject instanceof final ErrorMarkerFBNElement fbnElement) {
			final MissingFBTypeWizard wizard = new MissingFBTypeWizard(fbnElement);
			wizard.openWizard();
		}
	}

	public static EObject getEObjectFromEditorSelection(final IStructuredSelection sel) {
		Object model = sel.getFirstElement();

		if (model instanceof final EditPart editPart) {
			model = editPart.getModel();
		}

		return getEObjectFromEditor(model);
	}

	public static EObject getEObjectFromEditor(final Object model) {
		if (model instanceof final ArraySize arraySize
				&& arraySize.eContainer() instanceof final VarDeclaration varDecl) {
			return getEObjectFromEditor(varDecl);
		}

		if (model instanceof final VarDeclaration varDecl && varDecl.getType() instanceof ErrorMarkerDataType) {
			return varDecl;
		}

		// missing pin
		if (model instanceof final ErrorMarkerInterface ie) {
			return ie;
		}

		// missing block
		if (model instanceof final ErrorMarkerFBNElement errorBlock) {
			return errorBlock;
		}
		return null;
	}

	private static EObject getEObjectFromProblemViewSelection(final IStructuredSelection sel) {
		final Object firstElement = sel.getFirstElement();

		// selection from problem view
		if (firstElement instanceof final MarkerItem item) {
			EObject eObj = getEObjectFromMarkerItem(item);
			// this should already be validated by the property tester therefore we want to
			// know early if this fails.

			if (eObj instanceof final ArraySize arraySize
					&& arraySize.eContainer() instanceof final VarDeclaration varDecl) {
				eObj = varDecl;
			}

			Assert.isNotNull(eObj);

			return eObj;
		}

		return null;
	}

	public static EObject getEObjectFromMarkerItem(final MarkerItem item) {
		final IMarker marker = item.getMarker();
		try {
			final EObject target = FordiacErrorMarker.getTarget(marker);
			if (target != null) {
				return target;
			}

		} catch (IllegalArgumentException | CoreException e) {
			return null;
		}
		return null;
	}
}
