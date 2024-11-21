/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.EnumSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange.ChangeState;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ReconnectPinChange extends ConfigurableChange<FBNetworkElement> {

	private final String newName;
	private final String oldName;

	public ReconnectPinChange(final URI elementURI, final Class<FBNetworkElement> elementClass, final String newName,
			final String oldName) {
		super("Handle connection of : " + oldName, elementURI, elementClass);
		this.newName = newName;
		this.oldName = oldName;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {
		return new ReconnectPinByName(oldName, newName, element, getState());

	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.RECONNECT, ChangeState.NO_CHANGE, ChangeState.DELETE);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.RECONNECT);
	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {
		// No special initialization required
	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return super.isValid(element, pm);
	}

}

class ReconnectPinByName extends Command {

	final String oldName;
	final String newName;
	final FBNetworkElement element;
	final CompoundCommand cmds = new CompoundCommand();
	private final Set<ChangeState> state;

	public ReconnectPinByName(final String oldName, final String newName, final FBNetworkElement fbNeworkElement,
			final Set<ChangeState> state) {
		this.oldName = oldName;
		this.newName = newName;
		this.element = fbNeworkElement;
		this.state = state;
	}

	@Override
	public boolean canExecute() {
		return state.contains(ChangeState.RECONNECT) || state.contains(ChangeState.DELETE);
	}

	@Override
	public void execute() {
		final IInterfaceElement interfaceElement = element.getInterfaceElement(newName);
		final IInterfaceElement oldinterfaceElement = element.getInterfaceElement(oldName);
		if (oldinterfaceElement instanceof final ErrorMarkerInterface errorMarkerInterface) {
			final EList<Connection> inputConnections = getConnection(errorMarkerInterface);

			if (state.contains(ChangeState.RECONNECT)) {
				reconnect(interfaceElement, errorMarkerInterface, inputConnections);
			} else if (state.contains(ChangeState.DELETE)) {
				deleteConnection(inputConnections);
			}

			cmds.execute();
		}

	}

	private void deleteConnection(final EList<Connection> inputConnections) {
		for (final Connection c : inputConnections) {
			cmds.add(new DeleteConnectionCommand(c));
		}
	}

	private void reconnect(final IInterfaceElement interfaceElement, final ErrorMarkerInterface errorMarkerInterface,
			final EList<Connection> inputConnections) {
		for (final Connection c : inputConnections) {
			reconnect(c, interfaceElement, errorMarkerInterface, cmds);
		}
	}

	private void reconnect(final Connection c, final IInterfaceElement interfaceElement,
			final ErrorMarkerInterface errorMarkerInterface, final CompoundCommand cmds) {
		if (c instanceof final DataConnection dc) {
			final ReconnectDataConnectionCommand cmd = new ReconnectDataConnectionCommand(dc,
					!errorMarkerInterface.isIsInput(), interfaceElement, element.getFbNetwork());
			cmds.add(cmd);
		}

		if (c instanceof final EventConnection dc) {
			final ReconnectEventConnectionCommand cmd = new ReconnectEventConnectionCommand(dc,
					!errorMarkerInterface.isIsInput(), interfaceElement, element.getFbNetwork());
			cmds.add(cmd);
		}
	}

	private static EList<Connection> getConnection(final IInterfaceElement errorMarkerInterface) {
		return errorMarkerInterface.isIsInput() ? errorMarkerInterface.getInputConnections()
				: errorMarkerInterface.getOutputConnections();
	}

	@Override
	public boolean canUndo() {
		return cmds.canUndo();
	}

	@Override
	public boolean canRedo() {
		return cmds.canRedo();
	}

	@Override
	public void undo() {
		cmds.undo();
	}

	@Override
	public void redo() {
		cmds.redo();
	}

}