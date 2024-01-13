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
 *   Alois Zoitl    - extracted common Goto Pin Above/Below functions into
 *                    abstract base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractGoToPinHandler extends AbstractHandler {

	protected static final int ABOVE = -1;
	protected static final int BELOW = 1;

	final int direction;

	protected AbstractGoToPinHandler(final int direction) {
		this.direction = direction;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		final IInterfaceElement followingPin = getFollowingPin(selection);
		if (followingPin != null) {
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
			HandlerHelper.selectElement(followingPin, viewer);
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

	private IInterfaceElement getFollowingPin(final ISelection selection) {
		final IInterfaceElement ie = getSelectedInterfaceElement(selection);
		if (ie != null) {
			final List<IInterfaceElement> interfaceList = (ie.isIsInput()) ? getAllInputs(ie) : getAllOutputs(ie);
			return getFollowingPin(ie, interfaceList);
		}
		return null;
	}

	private static IInterfaceElement getSelectedInterfaceElement(final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structSel && !selection.isEmpty())
				&& ((structSel.size() == 1) && (structSel.getFirstElement() instanceof final EditPart ep))) {
			// only if only one element is selected
			if (ep.getModel() instanceof final IInterfaceElement ie) {
				return ie;
			}
		}
		return null;
	}

	protected static List<IInterfaceElement> getAllInputs(final IInterfaceElement ie) {
		final List<IInterfaceElement> inputList = new ArrayList<>();
		final InterfaceList interfaceList = (InterfaceList) ie.eContainer();
		inputList.addAll(interfaceList.getEventInputs().stream().filter(IInterfaceElement::isVisible).toList());
		inputList.addAll(interfaceList.getInputVars().stream().filter(IInterfaceElement::isVisible).toList());
		inputList.addAll(interfaceList.getInOutVars().stream().filter(IInterfaceElement::isVisible).toList());
		inputList.addAll(interfaceList.getSockets().stream().filter(IInterfaceElement::isVisible).toList());
		return inputList;
	}

	protected static List<IInterfaceElement> getAllOutputs(final IInterfaceElement ie) {
		final List<IInterfaceElement> outputList = new ArrayList<>();
		final InterfaceList interfaceList = (InterfaceList) ie.eContainer();
		outputList.addAll(interfaceList.getEventOutputs().stream().filter(IInterfaceElement::isVisible).toList());
		outputList.addAll(interfaceList.getOutputVars().stream().filter(IInterfaceElement::isVisible).toList());
		outputList.addAll(interfaceList.getOutMappedInOutVars().stream().filter(IInterfaceElement::isVisible).toList());
		outputList.addAll(interfaceList.getPlugs().stream().filter(IInterfaceElement::isVisible).toList());
		return outputList;
	}

	private IInterfaceElement getFollowingPin(final IInterfaceElement ie, final List<IInterfaceElement> interfaceList) {
		if (interfaceList.size() == 1) {
			return null;
		}
		int newIndex = interfaceList.indexOf(ie) + direction;
		if (newIndex < 0) {
			newIndex = interfaceList.size() - 1;
		} else if (newIndex >= interfaceList.size()) {
			newIndex = 0;
		}
		return interfaceList.get(newIndex);
	}

}
