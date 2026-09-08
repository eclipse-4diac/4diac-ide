/*******************************************************************************
 * Copyright (c) 2021, 2025 Primetals Technologies Austria GmbH
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
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
		final boolean stepMode = Platform.getPreferencesService().getBoolean(
				UIPreferenceConstants.FORDIAC_UI_PREFERENCES_ID, UIPreferenceConstants.P_TOGGLE_JUMP_STEP, false, null);

		final InterfaceEditPart interfaceEditPart = (InterfaceEditPart) selection.getFirstElement();
		final IInterfaceElement originPin = interfaceEditPart.getModel();

		editor.getSite().getPage().getNavigationHistory().markLocation(editor);

		// Jump-mode, jump over Struct
		if (!stepMode && isStructManipulatorMember(originPin) && originPin.isIsInput()) {
			selectOpposites(event, viewer, originPin, jumpOverStruct((VarDeclaration) originPin, true), editor);
			return null;
		}

		// Go out of Editor (EditorBorderPin)
		if (isEditorBorderPin(interfaceEditPart.getModel(), getFBNetwork(editor))
				&& !interfaceEditPart.getModel().isIsInput()) {
			gotoParent(event);
			return null;
		}

		// Switch between in/out on FB
		if (interfaceEditPart.isInput() && !isExpandedSubappPin(interfaceEditPart.getModel())) {
			HandlerHelper.selectElement(getInternalOppositePin(interfaceEditPart), viewer);
			return null;
		}

		final List<IInterfaceElement> opposites = getNextFollowPins(originPin, stepMode, true);
		selectOpposites(event, viewer, originPin, opposites, editor);
		return null;
	}

	@Override
	protected IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin) {
		final var eventOutputs = pin.getModel().getBlockFBNetworkElement().getInterface().getEventOutputs();
		final var eventInputs = pin.getModel().getBlockFBNetworkElement().getInterface().getEventInputs();

		if (eventOutputs.isEmpty()) {
			return getInternalOppositeVarPin(pin);
		}
		return calcInternalOppositePin(eventInputs, eventOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getBlockFBNetworkElement().getInterface().getInputVars();
		final var varOutputs = pin.getModel().getBlockFBNetworkElement().getInterface().getOutputVars();

		if (varOutputs.isEmpty()) {
			return getInternalOppositeVarInOutPin(pin);
		}
		return calcInternalOppositePin(varInputs, varOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarInOutPin(final InterfaceEditPart pin) {
		final var varInputs = pin.getModel().getBlockFBNetworkElement().getInterface().getInOutVars();
		final var varOutputs = pin.getModel().getBlockFBNetworkElement().getInterface().getOutMappedInOutVars();

		if (varInputs.isEmpty()) {
			return getInternalOppositePlugOrSocketPin(pin);
		}
		return calcInternalOppositePin(varInputs, varOutputs, pin);
	}

	@Override
	protected IInterfaceElement getInternalOppositePlugOrSocketPin(final InterfaceEditPart pin) {
		final var sockets = pin.getModel().getBlockFBNetworkElement().getInterface().getSockets();
		final var plugs = pin.getModel().getBlockFBNetworkElement().getInterface().getPlugs();

		if (plugs.isEmpty()) {
			return getInternalOppositeEventPin(pin);
		}
		return calcInternalOppositePin(sockets, plugs, pin);
	}

	@Override
	protected boolean hasOpposites(final InterfaceEditPart pin) {
		if (pin.getModel().getBlockFBNetworkElement() == null) {
			// we are at a type interface border in the type editor
			return false;
		}
		final InterfaceList il = pin.getModel().getInterfaceList();
		return !(il.getEventOutputs().isEmpty() && il.getOutputVars().isEmpty() && il.getPlugs().isEmpty()
				&& il.getInOutVars().isEmpty());
	}

	@Override
	protected EList<Connection> getConnectionList(final IInterfaceElement ie) {
		return ie.getOutputConnections();
	}
}