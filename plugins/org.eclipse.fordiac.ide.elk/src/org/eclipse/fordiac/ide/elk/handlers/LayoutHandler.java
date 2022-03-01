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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.fordiac.ide.elk.helpers.FordiacLayoutFactory;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		final List<Object> selection = getSelectedParts(event);

		// selection contains at least one element at this point, see getSelectedParts()
		if (selection.get(0) instanceof AbstractFBNetworkEditPart) {
			invokeLayout(workbenchPart, null);
		} else if (notLayoutable(selection)) {
			/* This ensures that a layout run is triggered for the whole editor even if one or more non layoutable
			 * blocks are selected. For this the layout run has to be triggered via the source menu. The context menu
			 * prohibits any layout runs with a non empty selection, which would need to be changed in order for
			 * separate unfolded subapp layouting to work properly. */
			invokeLayout(workbenchPart, null);
		} else {
			handleSelection(workbenchPart, selection);
		}

		return null;
	}

	private static boolean notLayoutable(List<Object> selection) {
		return selection.stream().noneMatch(LayoutHandler::isLayoutable);
	}

	private static List<Object> getSelectedParts(final ExecutionEvent event) {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (StructuredSelection.EMPTY.equals(selection)) {
			throw new IllegalStateException("Selection should at least contain the editors edit part."); //$NON-NLS-1$
		}
		return selection.toList();
	}

	private static void handleSelection(final IWorkbenchPart workbenchPart, final List<Object> selection) {
		selection.stream()
				.filter(LayoutHandler::isLayoutable)
				.forEach(diagramPart -> invokeLayout(workbenchPart, diagramPart));
	}

	/* Layouts the whole diagram if diagramPart is null */
	private static void invokeLayout(final IWorkbenchPart workbenchPart, final Object diagramPart) {
		DiagramLayoutEngine.invokeLayout(workbenchPart, diagramPart, FordiacLayoutFactory.createLayoutParams());
	}

	private static boolean isLayoutable(final Object object) {
		// TODO unfolded subapps should be layoutable (need to change project imports)
		return false;
	}

}