/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

public class GoToElementHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection != null && selection.getFirstElement() instanceof final EObject rowObject) {
			final var root = EcoreUtil.getRootContainer(rowObject);
			final IEditorPart editor = OpenListenerManager.openEditor(root);
			gotoElement(rowObject, editor);
		}

		return null;
	}

	public static void gotoElement(final EObject rowObject, final IEditorPart editor) {
		final var file = ModelHelper.getFileFromContextChecked(rowObject);
		IMarker marker = null;
		try {
			marker = file.createMarker(FordiacErrorMarker.IEC61499_MARKER);
			marker.setAttribute(FordiacErrorMarker.TARGET_TYPE, EcoreUtil.getURI(rowObject.eClass()).toString());
			marker.setAttribute(FordiacErrorMarker.TARGET_URI, EcoreUtil.getURI(rowObject).toString());
			IDE.gotoMarker(editor, marker);
		} catch (final CoreException es) {
			FordiacLogHelper.logWarning(es.getMessage(), es);
		} finally {
			if (marker != null) {
				try {
					marker.delete();
				} catch (final CoreException es) {
					FordiacLogHelper.logWarning(es.getMessage(), es);
				}
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final var selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);

		setBaseEnabled(selection instanceof final IStructuredSelection structSel && structSel.size() == 1
				&& structSel.getFirstElement() instanceof EObject);
	}
}
