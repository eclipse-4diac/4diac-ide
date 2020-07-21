/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ResetInterfacesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);

		if (viewer != null) {
			Set<InterfaceEditPart> interfaces = getEditParts(viewer);
			if (interfaces.isEmpty()) {
				return null;
			}
			EditorWithInterfaceEditPart parent = getParent(interfaces.iterator().next());
			if (parent instanceof UISubAppNetworkEditPart) {
				for (InterfaceEditPart part : interfaces) {
					((UISubAppNetworkEditPart) parent).removeChildVisualInterfaceElement(part);
					((UISubAppNetworkEditPart) parent).addChildVisualInterfaceElement(part);
				}
			} else if (parent instanceof CompositeNetworkEditPart) {
				for (InterfaceEditPart part : interfaces) {
					((CompositeNetworkEditPart) parent).removeChildVisualInterfaceElement(part);
					((CompositeNetworkEditPart) parent).addChildVisualInterfaceElement(part);
				}
			}
		}

		return null;
	}

	private EditorWithInterfaceEditPart getParent(InterfaceEditPart part) {
		if (part.getParent() instanceof EditorWithInterfaceEditPart) {
			return (EditorWithInterfaceEditPart) part.getParent();
		}
		return null;
	}

	private Set<InterfaceEditPart> getEditParts(GraphicalViewer viewer) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> editPartSet = viewer.getEditPartRegistry();
		Set<InterfaceEditPart> interfaces = new HashSet<>();
		for (Object part : editPartSet.values()) {
			if (part instanceof CompositeInternalInterfaceEditPart || part instanceof SubAppInternalInterfaceEditPart) {
				InterfaceEditPart elem = (InterfaceEditPart) part;
				if (!elem.getSourceConnections().isEmpty() || !elem.getTargetConnections().isEmpty()) {
					interfaces.add(elem);
				}
			}
		}
		return interfaces;
	}

}
