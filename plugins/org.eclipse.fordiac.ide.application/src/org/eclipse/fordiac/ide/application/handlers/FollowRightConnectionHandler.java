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
 *   				  jump to pin or parent, via right side
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class FollowRightConnectionHandler extends FollowConnectionHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		final IInterfaceElement originPin = ((InterfaceEditPart) selection.getFirstElement()).getModel();

		final InterfaceEditPart interfaceEditPart = (InterfaceEditPart) selection.getFirstElement();
		if (isEditorBorderPin(interfaceEditPart.getModel(), getFBNetwork(editor))
				&& !interfaceEditPart.getModel().isIsInput()) {
			gotoParent(event);
			return Status.OK_STATUS;
		}

		if (interfaceEditPart.isInput() && !isExpandedSubappPin(interfaceEditPart.getModel())) {
			HandlerHelper.selectElement(getInternalOppositePin(selection), viewer);
			return Status.OK_STATUS;
		}

		List<IInterfaceElement> opposites = getConnectionOposites(interfaceEditPart);
		final boolean stepMode = UIPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_TOGGLE_JUMP_STEP);
		if (!stepMode) {
			opposites = resolveTargetPins(opposites, viewer);
		}

		selectOpposites(event, viewer, originPin, opposites, editor);
		return Status.OK_STATUS;
	}

	@Override
	protected IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin) {
		final var eventOutputs = pin.getModel().getFBNetworkElement().getInterface().getEventOutputs();
		final var eventInputs = pin.getModel().getFBNetworkElement().getInterface().getEventInputs();

		if (eventOutputs.isEmpty()) {
			return getInternalOppositeVarPin(pin);
		}
		return calcInternalOppositePin(eventInputs, eventOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getFBNetworkElement().getInterface().getInputVars();
		final var varOutputs = pin.getModel().getFBNetworkElement().getInterface().getOutputVars();

		if (varOutputs.isEmpty()) {
			return getInternalOppositeVarInOutPin(pin);
		}
		return calcInternalOppositePin(varInputs, varOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarInOutPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getFBNetworkElement().getInterface().getInOutVars();
		final var varOutputs = pin.getModel().getFBNetworkElement().getInterface().getOutMappedInOutVars();

		if (varInputs.isEmpty()) {
			return getInternalOppositePlugOrSocketPin(pin);
		}
		return calcInternalOppositePin(varInputs, varOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositePlugOrSocketPin(final InterfaceEditPart pin) {
		final var sockets = pin.getModel().getFBNetworkElement().getInterface().getSockets();
		final var plugs = pin.getModel().getFBNetworkElement().getInterface().getPlugs();

		if (plugs.isEmpty()) {
			return getInternalOppositeEventPin(pin);
		}
		return calcInternalOppositePin(sockets, plugs, pin);
	}

	@Override
	protected boolean hasOpposites(final InterfaceEditPart pin) {
		if (pin.getModel().getFBNetworkElement() == null) {
			// we are at a type interface border in the type editor
			return false;
		}
		final InterfaceList il = (InterfaceList) pin.getModel().eContainer();
		return !(il.getEventOutputs().isEmpty() && il.getOutputVars().isEmpty() && il.getPlugs().isEmpty());
	}

	@Override
	protected boolean isLeft() {
		return false;
	}

	@Override
	protected EList<Connection> getConnectionList(final IInterfaceElement ie) {
		return ie.getOutputConnections();
	}
}