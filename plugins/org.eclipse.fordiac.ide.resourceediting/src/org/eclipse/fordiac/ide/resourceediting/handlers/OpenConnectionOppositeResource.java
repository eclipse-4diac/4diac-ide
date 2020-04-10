/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.handlers;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editparts.VirtualInOutputEditPart;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenConnectionOppositeResource extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof VirtualInOutputEditPart) {
				IInterfaceElement oppositeMappedIE = getOppositeInterfaceelement((VirtualInOutputEditPart) first);
				if (null != oppositeMappedIE) {
					openResource(oppositeMappedIE);
				}
			}
		}
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		IEvaluationContext ctx = (IEvaluationContext) evaluationContext;
		Object obj = ctx.getDefaultVariable();

		if (obj instanceof List) {
			List<?> list = (List<?>) obj;
			if (list.size() > 0) {
				obj = list.get(0);
			}
		}
		if (obj instanceof VirtualInOutputEditPart) {
			setBaseEnabled(null != getOppositeInterfaceelement((VirtualInOutputEditPart) obj));
		} else {
			setBaseEnabled(false);
		}
	}

	private IInterfaceElement getOppositeInterfaceelement(VirtualInOutputEditPart vIOEditPart) {
		IInterfaceElement ie = vIOEditPart.getModel().getReferencedInterfaceElement();
		IInterfaceElement fbOppostiteIE = ie.getFBNetworkElement().getOpposite().getInterfaceElement(ie.getName());
		if (null != fbOppostiteIE) {
			IInterfaceElement connectionOpposite = getConnectionOpposite(fbOppostiteIE);
			if (null != connectionOpposite && connectionOpposite.getFBNetworkElement().isMapped()) {
				FBNetworkElement mappedOppositeElement = connectionOpposite.getFBNetworkElement().getOpposite();
				return mappedOppositeElement.getInterfaceElement(connectionOpposite.getName());
			}

		}
		return null;
	}

	private IInterfaceElement getConnectionOpposite(IInterfaceElement fbOppostiteIE) {
		EList<Connection> connections = (fbOppostiteIE.isIsInput()) ? fbOppostiteIE.getInputConnections()
				: fbOppostiteIE.getOutputConnections();
		if (connections.size() >= 1) {
			return (fbOppostiteIE.isIsInput()) ? connections.get(0).getSource() : connections.get(0).getDestination();
		}
		return null;
	}

	private void openResource(IInterfaceElement oppositeMappedIE) {
		Resource res = oppositeMappedIE.getFBNetworkElement().getResource();

		if (null != res) {
			IEditorPart editor = OpenListenerManager.openEditor(res);
			if (editor instanceof ResourceDiagramEditor) {
				GraphicalViewer viewer = ((ResourceDiagramEditor) editor).getViewer();
				if (viewer != null) {
					Map<?, ?> map = viewer.getEditPartRegistry();
					Object ieToSelect = map.get(oppositeMappedIE);
					if (ieToSelect instanceof EditPart) {
						viewer.setSelection(new StructuredSelection(ieToSelect));
						viewer.reveal((EditPart) ieToSelect);
					}
				}
			}
		}
	}

}
