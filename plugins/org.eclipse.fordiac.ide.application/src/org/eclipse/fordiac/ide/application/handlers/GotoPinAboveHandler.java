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
 *   Fabio Gandolfi - implemented Handler for Goto Pin Above command/shortcut
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class GotoPinAboveHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);

		if (getFollowingPin(selection) != null) {
			selectElement(getFollowingPin(selection), viewer);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);

		setBaseEnabled(editor != null && getFollowingPin(selection) != null);
	}

	private static IInterfaceElement getFollowingPin(final ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if ((structuredSelection.size() == 1)
					&& (structuredSelection.getFirstElement() instanceof InterfaceEditPart)) {
				// only if only one element is selected

				final InterfaceEditPart pin = (InterfaceEditPart) structuredSelection.getFirstElement();
				final IInterfaceElement ie = pin.getModel();

				if (pin.isInput()) {
					return getFollowingInputPin(pin, ie);
				}
				return getFollowingOutputPin(pin, ie);
			}
		}
		return null;
	}

	private static IInterfaceElement getFollowingInputPin(final InterfaceEditPart pin,
			final IInterfaceElement ie) {
		final List<Object> inputList = new ArrayList<>();
		inputList.addAll(ie.getFBNetworkElement().getInterface().getEventInputs());
		inputList.addAll(ie.getFBNetworkElement().getInterface().getInputVars());
		inputList.addAll(ie.getFBNetworkElement().getInterface().getSockets());

		if (inputList.size() == 1) {
			return null;
		}

		if (inputList.indexOf(pin.getModel()) == 0) {
			return (IInterfaceElement) inputList.get(inputList.size() - 1);
		}
		return (IInterfaceElement) inputList.get(inputList.indexOf(pin.getModel()) - 1);
	}

	private static IInterfaceElement getFollowingOutputPin(final InterfaceEditPart pin,
			final IInterfaceElement ie) {
		final List<Object> outputList = new ArrayList<>();
		outputList.addAll(ie.getFBNetworkElement().getInterface().getEventOutputs());
		outputList.addAll(ie.getFBNetworkElement().getInterface().getOutputVars());
		outputList.addAll(ie.getFBNetworkElement().getInterface().getPlugs());

		if (outputList.size() == 1) {
			return null;
		}

		if (outputList.indexOf(pin.getModel()) == 0) {
			return (IInterfaceElement) outputList.get(outputList.size() - 1);
		}
		return (IInterfaceElement) outputList.get(outputList.indexOf(pin.getModel()) - 1);
	}

	private static void selectElement(final Object element, final GraphicalViewer viewer) {
		final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(element);
		if (null != editPart) {
			if (viewer instanceof AdvancedScrollingGraphicalViewer) {
				((AdvancedScrollingGraphicalViewer) viewer).selectAndRevealEditPart(editPart);
			} else {
				viewer.select(editPart);
				viewer.reveal(editPart);
			}
		}
	}
}
