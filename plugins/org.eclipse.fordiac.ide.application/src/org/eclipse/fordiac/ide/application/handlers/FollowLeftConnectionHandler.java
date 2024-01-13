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
 *   Fabio Gandolfi - created this class to handle follow connections,
 *   				  jump to pin or parent, via left side
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class FollowLeftConnectionHandler extends FollowConnectionHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final List<IInterfaceElement> opposites = getConnectionOposites(selection, getFBNetwork(editor));
		final GotoParentHandler gotoParentHandler = new GotoParentHandler();

		if (isInsideSubappOrViewer(
				((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).getModel(),
				getFBNetwork(editor))
				&& ((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).getModel().isIsInput()) {
			gotoParentHandler.execute(event);
			return Status.OK_STATUS;
		}

		if (!((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).isInput()) {
			HandlerHelper.selectElement(getInternalOppositePin(selection), viewer);
			return Status.OK_STATUS;
		}

		if (!opposites.isEmpty()) {
			if (opposites.size() == 1) {
				HandlerHelper.selectElement(opposites.get(0), viewer);
			} else {
				showOppositeSelectionDialog(opposites, event, viewer);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin) {
		final var eventOutputs = pin.getModel().getFBNetworkElement().getInterface().getEventOutputs();
		final var eventInputs = pin.getModel().getFBNetworkElement().getInterface().getEventInputs();

		if (eventInputs.isEmpty()) {
			return getInternalOppositeVarPin(pin);
		}
		return calcInternalOppositePin(eventOutputs, eventInputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getFBNetworkElement().getInterface().getInputVars();
		final var varOutputs = pin.getModel().getFBNetworkElement().getInterface().getOutputVars();

		if (varInputs.isEmpty()) {
			return getInternalOppositeVarInOutPin(pin);
		}
		return calcInternalOppositePin(varOutputs, varInputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarInOutPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getFBNetworkElement().getInterface().getInOutVars();
		final var varOutputs = pin.getModel().getFBNetworkElement().getInterface().getOutMappedInOutVars();

		if (varInputs.isEmpty()) {
			return getInternalOppositePlugOrSocketPin(pin);
		}
		return calcInternalOppositePin(varOutputs, varInputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositePlugOrSocketPin(final InterfaceEditPart pin) {
		final var sockets = pin.getModel().getFBNetworkElement().getInterface().getSockets();
		final var plugs = pin.getModel().getFBNetworkElement().getInterface().getPlugs();

		if (sockets.isEmpty()) {
			return getInternalOppositeEventPin(pin);
		}
		return calcInternalOppositePin(plugs, sockets, pin);
	}

	@Override
	protected boolean hasOpposites(final InterfaceEditPart pin) {
		return !(pin.getModel().getFBNetworkElement().getInterface().getEventInputs().isEmpty()
				&& pin.getModel().getFBNetworkElement().getInterface().getInputVars().isEmpty()
				&& pin.getModel().getFBNetworkElement().getInterface().getSockets().isEmpty());
	}
}