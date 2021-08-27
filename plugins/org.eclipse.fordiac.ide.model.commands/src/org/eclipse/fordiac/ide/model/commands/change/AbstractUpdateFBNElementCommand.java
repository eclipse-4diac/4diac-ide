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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractUpdateFBNElementCommand extends Command{
	// Helper data class for storing connection data of resource connection as the
	// connections are lost during the unmapping process
	protected static class ConnData {
		private final IInterfaceElement source;
		private final IInterfaceElement dest;

		public ConnData(final IInterfaceElement source, final IInterfaceElement dest) {
			this.source = source;
			this.dest = dest;
		}
	}


	protected final CompoundCommand deleteConnCmds = new CompoundCommand();
	protected final CompoundCommand connCreateCmds = new CompoundCommand();
	protected final CompoundCommand resourceConnCreateCmds = new CompoundCommand();

	protected MapToCommand mapCmd = null;
	protected UnmapCommand unmapCmd = null;

	/** The updated version of the FBNetworkElement */
	protected FBNetworkElement newElement;
	/** The FBNetworkElement which should be updated */
	protected FBNetworkElement oldElement;
	protected FBNetwork network;

	protected AbstractUpdateFBNElementCommand(final FBNetworkElement oldElement) {
		this.oldElement = oldElement;
		this.network = this.oldElement.getFbNetwork();
	}


	@Override
	public void execute() {
		Resource resource = null;
		List<ConnData> resourceConns = null;

		if (oldElement.isMapped()) {
			if (network.equals(oldElement.getResource().getFBNetwork())) {
				oldElement = oldElement.getOpposite();
				network = oldElement.getFbNetwork();
			}
			resource = oldElement.getResource();
			resourceConns = getResourceCons();
			unmapCmd = new UnmapCommand(oldElement);
			unmapCmd.execute();
		}

		handleExecute();

		// Map FB
		if (resource != null) {
			mapCmd = new MapToCommand(newElement, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
			}
		}
	}

	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}
		deleteConnCmds.redo();

		handleRedo();

		connCreateCmds.redo();

		if (mapCmd != null) {
			mapCmd.redo();
			resourceConnCreateCmds.redo();
		}
	}

	@Override
	public void undo() {
		if (mapCmd != null) {
			resourceConnCreateCmds.undo();
			mapCmd.undo();
		}

		handleUndo();

		replaceFBs(newElement, oldElement);
		deleteConnCmds.undo();

		if (unmapCmd != null) {
			unmapCmd.undo();
		}
	}


	protected void recreateResourceConns(final List<ConnData> resourceConns) {
		final FBNetworkElement orgMappedElement = unmapCmd.getMappedFBNetworkElement();
		final FBNetworkElement copiedMappedElement = newElement.getOpposite();
		for (final ConnData connData : resourceConns) {
			final IInterfaceElement source = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement,
					connData.source);
			final IInterfaceElement dest = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement, connData.dest);
			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, copiedMappedElement.getFbNetwork());
				if (null != dccc) {
					dccc.setSource(source);
					dccc.setDestination(dest);
					if (dccc.canExecute()) {
						dccc.execute();
						resourceConnCreateCmds.add(dccc);
					}
				}
			}
		}
	}

	protected void doReconnect(final Connection oldConn, final IInterfaceElement source, final IInterfaceElement dest) {
		// the connection may be already in our list if source and dest are on our FB
		if (!isInDeleteConnList(oldConn)) {
			final FBNetwork fbn = oldConn.getFBNetwork();
			// we have to delete the old connection in all cases
			final DeleteConnectionCommand cmd = new DeleteConnectionCommand(oldConn);
			cmd.execute();
			deleteConnCmds.add(cmd);

			if ((source != null) && (dest != null)) {
				reconnectConnections(oldConn, source, dest, fbn);
			}
		}
	}

	private boolean isInDeleteConnList(final Connection conn) {
		for (final Object cmd : deleteConnCmds.getCommands()) {
			if (((DeleteConnectionCommand) cmd).getConnectionView().equals(conn)) {
				return true;
			}
		}
		return false;
	}

	protected static List<Connection> getAllConnections(final FBNetworkElement element) {
		final List<Connection> connections = new ArrayList<>();
		for (final IInterfaceElement ifEle : element.getInterface().getAllInterfaceElements()) {
			if (ifEle.isIsInput()) {
				connections.addAll(ifEle.getInputConnections());
			} else {
				connections.addAll(ifEle.getOutputConnections());
			}
		}
		return connections;
	}

	protected void createValues() {
		for (final VarDeclaration inVar : newElement.getInterface().getInputVars()) {
			inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			checkSourceParam(inVar);
		}
	}

	private void checkSourceParam(final VarDeclaration variable) {
		final VarDeclaration srcVar = oldElement.getInterface().getVariable(variable.getName());
		if ((null != srcVar) && (null != srcVar.getValue())) {
			variable.getValue().setValue(srcVar.getValue().getValue());
		}
	}

	protected void replaceFBs(final FBNetworkElement removeElement, final FBNetworkElement addElement) {
		network.getNetworkElements().remove(removeElement);
		network.getNetworkElements().add(addElement);
	}

	protected List<ConnData> getResourceCons() {
		final List<ConnData> retVal = new ArrayList<>();
		final FBNetworkElement resElement = oldElement.getOpposite();
		for (final Connection conn : getAllConnections(resElement)) {
			final IInterfaceElement source = conn.getSource();
			final IInterfaceElement dest = conn.getDestination();
			if (!source.getFBNetworkElement().isMapped() || !dest.getFBNetworkElement().isMapped()) {
				// one of both ends is a resourceFB therefore the connection needs to be
				// restored
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			} else if (((source.getFBNetworkElement() == resElement)
					&& (dest.getFBNetworkElement().getOpposite().getFbNetwork() != oldElement.getFbNetwork()))
					|| ((dest.getFBNetworkElement() == resElement)
							&& (source.getFBNetworkElement().getOpposite().getFbNetwork() != oldElement.getFbNetwork()))) {
				// one of both ends is a FB coming from a different fb network and therefore
				// this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}

		}
		return retVal;
	}


	protected abstract void handleApplicationConnections();

	protected abstract IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement newElement, final FBNetworkElement oldElement, final IInterfaceElement oldInterface);

	protected abstract AbstractConnectionCreateCommand createConnCreateCMD(final IInterfaceElement refIE, final FBNetwork fbn);

	protected abstract void reconnectConnections(final Connection oldConn, final IInterfaceElement source, final IInterfaceElement dest, final FBNetwork fbn);


	protected abstract void handleExecute();

	protected abstract void handleRedo();

	protected abstract void handleUndo();
}
