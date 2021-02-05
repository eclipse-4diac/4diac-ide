/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2018 Johannes Kepler University
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
import org.eclipse.fordiac.ide.gef.handlers.BreadcrumbUtil;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class FocusOnPredecessor extends AbstractHandler {
	private static final int HALF_TRANSPERENT = 50;
	private static final int NON_TRANSPARENT = 255;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Set<ConfigurableObject> elementToHighlight = new HashSet<>();
		getPredecessorFBNetworkElements(getSelectedFBElement(event), elementToHighlight);

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Map<?, ?> map = BreadcrumbUtil.getViewer(editor).getEditPartRegistry();
		for (final Entry<?, ?> entry : map.entrySet()) {
			final Object obj = entry.getKey();
			final Object editPartAsObject = entry.getValue();
			final int transparency = (elementToHighlight.contains(obj)) ? NON_TRANSPARENT : HALF_TRANSPERENT;
			if (editPartAsObject instanceof AbstractViewEditPart) {
				((AbstractViewEditPart) editPartAsObject).setTransparency(transparency);
			} else if (editPartAsObject instanceof ConnectionEditPart) {
				((ConnectionEditPart) editPartAsObject).setTransparency(transparency);
			}
		}
		return null;
	}

	private static void getPredecessorFBNetworkElements(FBNetworkElement element,
			Set<ConfigurableObject> elementToHighlight) {
		if (null != element) {
			elementToHighlight.add(element);
			for (final VarDeclaration inVar : element.getInterface().getInputVars()) {
				for (final Connection con : inVar.getInputConnections()) {
					final IInterfaceElement source = con.getSource();
					if (source != null) {
						final FBNetworkElement sourceElement = source.getFBNetworkElement();
						if (null != sourceElement) {
							elementToHighlight.add(con);
							if (!elementToHighlight.contains(sourceElement)) {
								getPredecessorFBNetworkElements(sourceElement, elementToHighlight);
							}
						}
					}
				}
			}
		}
	}

	private static FBNetworkElement getSelectedFBElement(ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			Object selObj = ((StructuredSelection) selection).getFirstElement();
			if (selObj instanceof EditPart) {
				selObj = ((EditPart) selObj).getModel();
			}
			if (selObj instanceof FBNetworkElement) {
				return (FBNetworkElement) selObj;
			}
		}
		return null;
	}

}
