/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExpandServiceSequenceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		// we check in the enablement that it is a structured selection therefore we can
		// easily cast here
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		for (final Object selected : selection.toList()) {
			if (selected instanceof final ServiceSequenceEditPart ep) {
				ep.toggleExpanded();
			} else if (selected instanceof ServiceSequence) {
				final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
				final ServiceSequenceEditPart ep = (ServiceSequenceEditPart) viewer.getEditPartForModel(selected);
				ep.toggleExpanded();
			}
		}
		return Status.OK_STATUS;
	}

}
