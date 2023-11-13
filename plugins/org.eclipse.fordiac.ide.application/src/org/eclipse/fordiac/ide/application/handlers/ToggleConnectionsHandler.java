/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH,
 * 				 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *   Prankur Agarwal - update the toggle implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.HideConnectionCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class ToggleConnectionsHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final IStructuredSelection structSel) {
			final CompoundCommand commands = new CompoundCommand();
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack stack = editor.getAdapter(CommandStack.class);
			final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);

			final boolean isVisible = checkVisibilityOfSelection(structSel.toList());
			toggleConnections(commands, (IStructuredSelection) selection, isVisible);

			if (null != stack) {
				stack.execute(commands);
			}
			if (null != viewer) {
				((GraphicalEditPart) viewer.getRootEditPart()).getFigure().invalidateTree();
			}
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private static boolean checkVisibilityOfSelection(final List<? extends EditPart> selection) {
		final Set<Boolean> visibility = new HashSet<>();
		for (final EditPart obj : selection) {
			if (obj instanceof final ConnectionEditPart conep) {
				visibility.add(!conep.getFigure().isHidden());
			} else if (obj instanceof final AbstractFBNElementEditPart fbEP) {
				for (final IInterfaceElement element : fbEP.getModel().getInterface().getAllInterfaceElements()) {
					if (element.isIsInput() && !element.getInputConnections().isEmpty()) {
						element.getInputConnections().forEach(conn -> visibility.add(conn.isVisible()));
					}
					if (!element.isIsInput() && !element.getOutputConnections().isEmpty()) {
						element.getOutputConnections().forEach(conn -> visibility.add(conn.isVisible()));
					}
				}
			} else if (obj instanceof final InterfaceEditPart iep) {
				visibility.add(checkPinVisibility(iep.getModel()));
			}
		}

		if (visibility.size() == 2) {
			return true;
		}

		return visibility.contains(true);
	}

	private static void toggleConnections(final CompoundCommand commands, final IStructuredSelection selection,
			final boolean isVisible) {
		for (final Object obj : selection.toList()) {
			if (obj instanceof final ConnectionEditPart conEP) {
				addHideCommand(commands, conEP.getModel(), !isVisible);
			} else if (obj instanceof final AbstractFBNElementEditPart fbEP) {
				fbEP.getModel().getInterface().getAllInterfaceElements()
						.forEach(pin -> togglePinConnections(commands, pin, isVisible));
			} else if (obj instanceof final InterfaceEditPart iep) {
				togglePinConnections(commands, iep.getModel(), isVisible);
			}
		}
	}

	private static boolean checkPinVisibility(final IInterfaceElement iel) {
		if (iel.isIsInput()) {
			if (iel.getInputConnections().isEmpty()) {
				return true;
			}
			return iel.getInputConnections().stream().allMatch(Connection::isVisible);
		}
		if (iel.getOutputConnections().isEmpty()) {
			return true;
		}
		return iel.getOutputConnections().stream().allMatch(Connection::isVisible);
	}

	private static void togglePinConnections(final CompoundCommand commands, final IInterfaceElement iel,
			final boolean isVisible) {
		if (iel.isIsInput()) {
			iel.getInputConnections().forEach(conn -> {
				if (conn.isVisible() == isVisible) {
					addHideCommand(commands, conn, !isVisible);
				}
			});
		} else {
			iel.getOutputConnections().forEach(conn -> {
				if (conn.isVisible() == isVisible) {
					addHideCommand(commands, conn, !isVisible);
				}
			});
		}
	}

	private static void addHideCommand(final CompoundCommand commands, final Connection conn, final boolean hide) {
		final Command hideConCmd = new HideConnectionCommand(conn, hide);
		if (hideConCmd.canExecute()) {
			commands.add(hideConCmd);
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(selection != null && editor != null && selectionContainsConnectionsOrFbs(selection));
	}

	private static boolean selectionContainsConnectionsOrFbs(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel && !selection.isEmpty()) {
			return structSel.toList().stream().anyMatch(ep -> isConnection(ep) || hasConnection(ep));
		}
		return false;
	}

	private static boolean hasConnection(final Object ep) {
		if (ep instanceof final AbstractFBNElementEditPart fbEP) {
			if (isInViewer(fbEP.getModel())) {
				return false;
			}
			for (final IInterfaceElement ie : fbEP.getModel().getInterface().getAllInterfaceElements()) {
				if (hasConnection(ie)) {
					return true;
				}
			}
		}
		return ((ep instanceof final InterfaceEditPart iep) && (hasConnection(iep.getModel())));
	}

	private static boolean hasConnection(final IInterfaceElement ie) {
		if (ie.getFBNetworkElement() != null && isInViewer(ie.getFBNetworkElement())) {
			return false;
		}
		return (ie.isIsInput() && !ie.getInputConnections().isEmpty()
				|| !ie.isIsInput() && !ie.getOutputConnections().isEmpty());
	}

	private static boolean isConnection(final Object ep) {
		return ep instanceof final ConnectionEditPart cep && cep.getFigure() != null && !isInViewer(cep.getModel());
	}

	private static boolean isInViewer(final Connection model) {
		if (model.getFBNetwork().eContainer() instanceof final FBNetworkElement fbnEl) {
			return isInViewer(fbnEl);
		}
		return false;
	}

	private static boolean isInViewer(final FBNetworkElement fbnEl) {
		if (((fbnEl instanceof final SubApp subApp) && (subApp.isTyped())) || (fbnEl instanceof CFBInstance)) {
			return true;
		}
		return FBNetworkElementHelper.isContainedInTypedInstance(fbnEl);
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				final boolean isVisible = checkVisibilityOfSelection(viewer.getSelectedEditParts());
				setElementText(element, isVisible, viewer.getSelectedEditParts().size() == 1
						&& viewer.getSelectedEditParts().get(0) instanceof ConnectionEditPart);
			}
		}
	}

	private static void setElementText(final UIElement element, final boolean isVisible, final boolean isSingular) {
		if (isVisible) {
			element.setText(isSingular ? Messages.ToggleConnections_Singular_Hide : Messages.ToggleConnections_Hide);
		} else {
			element.setText(isSingular ? Messages.ToggleConnections_Singular_Show : Messages.ToggleConnections_Show);
		}
	}

}
