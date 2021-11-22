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
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class FollowRightConnectionHandler extends FollowConnectionHandler {

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
				&& !((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).getModel().isIsInput()) {
			gotoParentHandler.execute(event);
			return Status.OK_STATUS;
		}


		if (((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).isInput()) {
			selectElement(getInternalOppositePin(selection), viewer);
			return Status.OK_STATUS;
		}

		if (!opposites.isEmpty()) {
			if (opposites.size() == 1) {
				selectElement(opposites.get(0), viewer);
			} else {
				showOppositeSelectionDialog(opposites, event, viewer);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin) {

		final EList<?> eventInputs = pin.getModel().getFBNetworkElement().getInterface().getEventInputs();
		final EList<?> eventOutputs = pin.getModel().getFBNetworkElement().getInterface().getEventOutputs();

		if (eventOutputs.isEmpty()) {
			return null;
		}

		if (eventOutputs.indexOf(pin.getModel()) == -1) {
			return (IInterfaceElement) eventOutputs.get(0);
		}

		if ((eventOutputs.size() - 1) < eventInputs
				.indexOf(pin.getModel())) {
			return (IInterfaceElement) eventOutputs.get(eventOutputs.size() - 1);
		}
		return (IInterfaceElement) eventOutputs.get(eventInputs.indexOf(pin.getModel()));
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin) {

		final EList<?> varInputs = pin.getModel().getFBNetworkElement().getInterface().getInputVars();
		final EList<?> varOutputs = pin.getModel().getFBNetworkElement().getInterface().getOutputVars();

		if (varOutputs.isEmpty()) {
			return null;
		}

		if (varOutputs.indexOf(pin.getModel()) == -1) {
			return (IInterfaceElement) varOutputs.get(0);
		}

		if ((varOutputs.size() - 1) < varInputs.indexOf(pin.getModel())) {
			return (IInterfaceElement) varOutputs.get(varOutputs.size() - 1);
		}
		return (IInterfaceElement) varOutputs.get(varInputs.indexOf(pin.getModel()));
	}
}