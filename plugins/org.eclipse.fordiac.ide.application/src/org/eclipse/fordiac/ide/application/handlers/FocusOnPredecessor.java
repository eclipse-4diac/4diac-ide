/*******************************************************************************
 * Copyright (c) 2011, 2025 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - migrated Focus on predecessor into an handler
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class FocusOnPredecessor extends AbstractHandler {
	private static final int HALF_TRANSPERENT = 50;
	private static final int NON_TRANSPARENT = 255;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Set<ConfigurableObject> elementToHighlight = new HashSet<>();
		getPredecessorFBNetworkElements(getSelectedFBElement(event), elementToHighlight);

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Map<?, ?> map = HandlerHelper.getViewer(editor).getEditPartRegistry();
		for (final Entry<?, ?> entry : map.entrySet()) {
			final Object obj = entry.getKey();
			final Object editPartAsObject = entry.getValue();
			final int transparency = (elementToHighlight.contains(obj)) ? NON_TRANSPARENT : HALF_TRANSPERENT;
			if (editPartAsObject instanceof final AbstractViewEditPart aViewAP) {
				aViewAP.setTransparency(transparency);
			} else if (editPartAsObject instanceof final ConnectionEditPart connEP) {
				connEP.setTransparency(transparency);
			}
		}
		return null;
	}

	private static void getPredecessorFBNetworkElements(final BlockFBNetworkElement element,
			final Set<ConfigurableObject> elementToHighlight) {
		if (null == element) {
			return;
		}
		elementToHighlight.add(element);
		for (final VarDeclaration inVar : element.getInterface().getInputVars()) {
			for (final Connection con : inVar.getInputConnections()) {
				final IInterfaceElement source = con.getSource();
				if (source != null && source.getBlockFBNetworkElement() != null) {
					final BlockFBNetworkElement sourceElement = source.getBlockFBNetworkElement();
					elementToHighlight.add(con);
					if (!elementToHighlight.contains(sourceElement)) {
						getPredecessorFBNetworkElements(sourceElement, elementToHighlight);
					}
				}
			}
		}
	}

	private static BlockFBNetworkElement getSelectedFBElement(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final StructuredSelection structSel) {
			Object selObj = structSel.getFirstElement();
			if (selObj instanceof final EditPart ep) {
				selObj = ep.getModel();
			}
			if (selObj instanceof final BlockFBNetworkElement fbne) {
				return fbne;
			}
		}
		return null;
	}

}
