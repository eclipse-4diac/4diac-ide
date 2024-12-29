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
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.HideConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
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

	private static boolean checkVisibilityOfSelection(final List<?> selection) {
		final Set<Boolean> visibility = new HashSet<>();
		for (final Object obj : selection) {
			if (obj instanceof final ConnectionEditPart conep) {
				visibility.add(Boolean.valueOf(!conep.getFigure().isHidden()));
			} else if (obj instanceof final AbstractFBNElementEditPart fbEP) {
				for (final IInterfaceElement element : fbEP.getModel().getInterface().getAllInterfaceElements()) {
					if (element.isIsInput() && !element.getInputConnections().isEmpty()) {
						element.getInputConnections()
								.forEach(conn -> visibility.add(Boolean.valueOf(conn.isVisible())));
					}
					if (!element.isIsInput() && !element.getOutputConnections().isEmpty()) {
						element.getOutputConnections()
								.forEach(conn -> visibility.add(Boolean.valueOf(conn.isVisible())));
					}
				}
			} else if (obj instanceof final InterfaceEditPart iep) {
				visibility.add(Boolean.valueOf(checkPinVisibility(iep.getModel())));
			}
		}

		if (visibility.size() == 2) {
			return true;
		}

		return visibility.contains(Boolean.TRUE);
	}

	private static void toggleConnections(final CompoundCommand commands, final IStructuredSelection selection,
			final boolean isVisible) {
		for (final Object obj : selection.toList()) {
			if (obj instanceof final ConnectionEditPart conEP) {
				addHideCommand(commands, conEP.getModel(), !isVisible);
			} else if (obj instanceof final AbstractFBNElementEditPart fbEP) {
				fbEP.getModel().getInterface().getAllInterfaceElements()
						.forEach(pin -> togglePinConnections(commands, pin, isVisible));
			} else if (obj instanceof final TargetInterfaceElementEditPart iep) {
				togglePinConnections(commands, iep.getModel().getHost(), isVisible);
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
		setBaseEnabled(selection != null && validEditor(editor) && selectionContainsConnectionsOrFbs(selection));
	}

	private static boolean selectionContainsConnectionsOrFbs(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel && !selection.isEmpty()) {
			return structSel.toList().stream().anyMatch(ep -> isConnection(ep) || hasConnection(ep));
		}
		return false;
	}

	private static boolean hasConnection(final Object ep) {
		if (ep instanceof final AbstractFBNElementEditPart fbEP) {
			for (final IInterfaceElement ie : fbEP.getModel().getInterface().getAllInterfaceElements()) {
				if (hasConnection(ie)) {
					return true;
				}
			}
		}

		return ((ep instanceof final TargetInterfaceElementEditPart tiep) && (hasConnection(tiep.getModel().getHost()))
				|| (ep instanceof final InterfaceEditPart iep) && (hasConnection(iep.getModel())));
	}

	private static boolean hasConnection(final IInterfaceElement ie) {
		return (ie.isIsInput() && !ie.getInputConnections().isEmpty()
				|| !ie.isIsInput() && !ie.getOutputConnections().isEmpty());
	}

	private static boolean isConnection(final Object ep) {
		return ep instanceof final ConnectionEditPart cep && cep.getFigure() != null;
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				final boolean isVisible = checkVisibilityOfSelection(viewer.getSelectedEditParts());
				if (viewer.getSelectedEditParts().size() == 1) {
					setElementText(element, isVisible,
							viewer.getSelectedEditParts().get(0) instanceof ConnectionEditPart,
							viewer.getSelectedEditParts().get(0) instanceof TargetInterfaceElementEditPart);
				}
			}
		}
	}

	private static void setElementText(final UIElement element, final boolean isVisible, final boolean isSingular,
			final boolean isTargetInterface) {
		if (isVisible) {
			element.setText(isSingular ? Messages.ToggleConnections_Singular_Hide : Messages.ToggleConnections_Hide);
		} else if (isTargetInterface) {
			element.setText(Messages.ToggleConnections_Target_Show);
		} else {
			element.setText(isSingular ? Messages.ToggleConnections_Singular_Show : Messages.ToggleConnections_Show);
		}
	}

	private static boolean validEditor(final IEditorPart editor) {
		// only if the editor can adapt to an FBNetwork it is a valid editor, otherwise
		// it is only a viewer
		return editor != null && editor.getAdapter(FBNetwork.class) != null;
	}

}
