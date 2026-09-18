/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.tools.FordiacConnectionDragCreationTool;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui.AddStructMemberRefactoringUI;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

public final class StructMemberConnectionDragCreationTool extends FordiacConnectionDragCreationTool {

	@Override
	protected boolean handleCreateConnection() {
		final Command connectionCommand = getCommand();
		if (connectionCommand == null || !connectionCommand.canExecute()) {
			openStructMemberRefactoring();
		}
		return super.handleCreateConnection();
	}

	private void openStructMemberRefactoring() {
		final EditPart sourceEditPart = getTargetRequest().getSourceEditPart();
		if (!(sourceEditPart instanceof final InterfaceEditPart interfaceEditPart) || !interfaceEditPart.isConnectable()
				|| !(sourceEditPart.getModel() instanceof final VarDeclaration connectionPin)
				|| getCurrentViewer() == null) {
			return;
		}

		final EditPart editPartUnderMouse = getCurrentViewer().findObjectAt(getLocation());
		final EObject target = StructMemberDropTargetResolver.resolve(editPartUnderMouse);
		if (target != null) {
			AddStructMemberRefactoringUI.openAsync(getCurrentViewer().getControl().getShell(), connectionPin, target,
					(source, destination) -> hasCommonFBNetwork(source, destination)
							? CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, destination)
							: null);
		}
	}

	private static boolean hasCommonFBNetwork(final IInterfaceElement source, final IInterfaceElement destination) {
		final List<FBNetwork> sourceNetworks = CreateSubAppCrossingConnectionsCommand.buildHierarchy(source);
		final List<FBNetwork> destinationNetworks = CreateSubAppCrossingConnectionsCommand.buildHierarchy(destination);
		return sourceNetworks.stream().anyMatch(destinationNetworks::contains);
	}
}
