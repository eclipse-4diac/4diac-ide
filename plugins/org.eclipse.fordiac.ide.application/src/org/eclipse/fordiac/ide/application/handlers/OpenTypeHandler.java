/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final EObject object = getSelectedObject(sel);

		if (object != null) {
			openTypeEditor(object);
		}

		return null;
	}

	private static void openTypeEditor(final EObject object) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				try {
					OpenListenerManager.openEditor(object);
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);

		setBaseEnabled(getSelectedObject(sel) != null);
	}

	private static EObject getSelectedObject(final ISelection sel) {
		if ((sel instanceof final IStructuredSelection structSel) && !sel.isEmpty() && (structSel.size() == 1)) {
			Object obj = structSel.getFirstElement();

			if (obj instanceof final EditPart ep) {
				obj = ep.getModel();
			}

			final EObject eObject = switch (obj) {
			case final FBNetwork fbn -> getEObjectForOpening(fbn);
			case final FBNetworkElement fbnEl -> fbnEl.getType();
			case final IInterfaceElement ie -> ie.getType();
			default -> null;
			};

			if ((eObject != null)) {
				return eObject;
			}
		}

		return null;
	}

	private static EObject getEObjectForOpening(final FBNetwork fbn) {

		if (fbn.eContainer() instanceof final UntypedSubApp usa) {
			EObject element = usa;

			final List<String> subAppNames = new ArrayList<>();

			while (element != null) {
				if (element instanceof final INamedElement ine) {
					subAppNames.add(ine.getName());
				}

				if (element.eContainer() instanceof final TypedSubApp tsa) {
					subAppNames.add(tsa.getName());

					return getMatchingSubAppFromType(subAppNames.reversed(), tsa.getType());
				}

				element = element.eContainer();
			}
		}

		if (fbn.eContainer() instanceof final TypedSubApp tsa) {
			return tsa.getType();
		}

		return null;
	}

	private static SubApp getMatchingSubAppFromType(final List<String> subAppNames, final SubAppType type) {
		FBNetwork fb = type.getFBNetwork();
		SubApp lastSubapp = null;

		for (final String subAppName : subAppNames) {
			final SubApp sa = fb.getSubAppNamed(subAppName);

			if (sa != null) {
				lastSubapp = sa;
				fb = sa.getSubAppNetwork();
			}
		}

		return lastSubapp;
	}

}