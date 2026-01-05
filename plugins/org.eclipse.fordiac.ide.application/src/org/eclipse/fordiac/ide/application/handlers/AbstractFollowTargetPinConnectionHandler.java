/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractFollowTargetPinConnectionHandler extends FollowConnectionHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		editor.getSite().getPage().getNavigationHistory().markLocation(editor);

		final TargetInterfaceElementEditPart targetIEEditPart = (TargetInterfaceElementEditPart) selection
				.getFirstElement();

		final boolean stepMode = Platform.getPreferencesService().getBoolean(
				UIPreferenceConstants.FORDIAC_UI_PREFERENCES_ID, UIPreferenceConstants.P_TOGGLE_JUMP_STEP, false, null);

		if (useTargetRefElement(targetIEEditPart) && !stepMode) {
			// Jump to target-pin reference
			selectInterfaceElement(targetIEEditPart.getModel().getRefElement(), editor);
		} else {
			// Select next pin (Step mode)
			selectOpposites(event, viewer, null,
					getConnectionOpposites((IInterfaceElement) targetIEEditPart.getParent().getModel()), editor);
		}

		return Status.OK_STATUS;
	}

	protected abstract boolean useTargetRefElement(final TargetInterfaceElementEditPart targetIEEditPart);

	@Override
	protected boolean isValidSelectedElement(final IStructuredSelection structSel) {
		return structSel.getFirstElement() instanceof TargetInterfaceElementEditPart;
	}

}