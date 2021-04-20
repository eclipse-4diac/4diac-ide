/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 - 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - adapted ChangeTypeCommand for multiplexer use, sets struct
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeStructCommand extends Command {
	// Helper data class for storing connection data of resource connection as the
	// connections are lost during the unmapping process
	private static class ConnData {
		private final IInterfaceElement source;
		private final IInterfaceElement dest;

		public ConnData(final IInterfaceElement source, final IInterfaceElement dest) {
			this.source = source;
			this.dest = dest;
		}
	}

	private final StructuredType newStruct;
	private StructManipulator newMux;
	private StructManipulator oldMux;
	private final PaletteEntry entry;
	private FBNetwork network;

	private final CompoundCommand deleteConnCmds = new CompoundCommand();
	private final CompoundCommand connCreateCmds = new CompoundCommand();
	private final CompoundCommand resourceConnCreateCmds = new CompoundCommand();

	private MapToCommand mapCmd = null;
	private UnmapCommand unmapCmd = null;

	public ChangeStructCommand(final StructManipulator mux, final StructuredType struct) {
		this.oldMux = mux;
		newStruct = struct;
		entry = mux.getPaletteEntry();
		network = mux.getFbNetwork();
	}

	@Override
	public void execute() {
		Resource resource = null;
		List<ConnData> resourceConns = null;

		if (oldMux.isMapped()) {
			if (network.equals(oldMux.getResource().getFBNetwork())) {
				// this struct manipulator is in the resource
				oldMux = (StructManipulator) oldMux.getOpposite();
				network = oldMux.getFbNetwork();
			}
			resource = oldMux.getResource();
			resourceConns = getResourceConns();
			unmapCmd = new UnmapCommand(oldMux);
			unmapCmd.execute();
		}
		createNewMux();
		// Find connections which should be reconnected
		handleApplicationConnections();

		// Map FB
		if (resource != null) {
			mapCmd = new MapToCommand(newMux, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
			}
		}
	}

	@Override
	public void undo() {
		if (mapCmd != null) {
			resourceConnCreateCmds.undo();
			mapCmd.undo();
		}
		connCreateCmds.undo();
		replaceFBs(newMux, oldMux);
		deleteConnCmds.undo();

		if (unmapCmd != null) {
			unmapCmd.undo();
		}
	}

	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}
		deleteConnCmds.redo();
		replaceFBs(oldMux, newMux);
		connCreateCmds.redo();

		if (mapCmd != null) {
			mapCmd.redo();
			resourceConnCreateCmds.redo();
		}
	}

	private List<ConnData> getResourceConns() {
		final List<ConnData> retVal = new ArrayList<>();
		final FBNetworkElement resElement = oldMux.getOpposite();
		for (final Connection conn : getAllConnections(resElement)) {
			final IInterfaceElement source = conn.getSource();
			final IInterfaceElement dest = conn.getDestination();
			if (!source.getFBNetworkElement().isMapped() || !dest.getFBNetworkElement().isMapped()) {
				// one of both ends is a resourceFB therefore the connection needs to be
				// restored
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			} else if (((source.getFBNetworkElement() == resElement)
					&& (dest.getFBNetworkElement().getOpposite().getFbNetwork() != oldMux.getFbNetwork()))
					|| ((dest.getFBNetworkElement() == resElement)
							&& (source.getFBNetworkElement().getOpposite().getFbNetwork() != oldMux.getFbNetwork()))) {
				// one of both ends is a FB coming from a different fb network and therefore
				// this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}

		}
		return retVal;
	}

	private void recreateResourceConns(final List<ConnData> resourceConns) {
		final FBNetworkElement orgMappedElement = unmapCmd.getMappedFBNetworkElement();
		final FBNetworkElement copiedMappedElement = newMux.getOpposite();
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

	private static List<Connection> getAllConnections(final FBNetworkElement element) {
		final List<Connection> retVal = new ArrayList<>();
		for (final IInterfaceElement ifEle : element.getInterface().getAllInterfaceElements()) {
			if (ifEle.isIsInput()) {
				retVal.addAll(ifEle.getInputConnections());
			} else {
				retVal.addAll(ifEle.getOutputConnections());
			}
		}
		return retVal;
	}

	private void createNewMux() {
		if (oldMux instanceof Multiplexer) {
			newMux = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (oldMux instanceof Demultiplexer) {
			newMux = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		newMux.setPaletteEntry(entry);
		newMux.setInterface(EcoreUtil.copy(oldMux.getType().getInterfaceList()));
		newMux.setName(oldMux.getName());

		newMux.setPosition(EcoreUtil.copy(oldMux.getPosition()));
		newMux.getAttributes().addAll(EcoreUtil.copyAll(oldMux.getAttributes()));
		newMux.deleteAttribute("VisibleChildren"); // TODO use constant
		newMux.setStructTypeElementsAtInterface(newStruct);
		createValues();
		replaceFBs(oldMux, newMux);
	}

	private void createValues() {
		for (final VarDeclaration inVar : newMux.getInterface().getInputVars()) {
			inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			checkSourceParam(inVar);
		}
	}

	private void checkSourceParam(final VarDeclaration variable) {
		final VarDeclaration srcVar = oldMux.getInterface().getVariable(variable.getName());
		if ((null != srcVar) && (null != srcVar.getValue())) {
			variable.getValue().setValue(srcVar.getValue().getValue());
		}
	}

	private void replaceFBs(final FBNetworkElement oldElement, final FBNetworkElement newElement) {
		network.getNetworkElements().remove(oldElement);
		network.getNetworkElements().add(newElement);
	}

	public StructManipulator getNewMux() {
		return newMux;
	}

	private void handleApplicationConnections() {
		for (final Connection conn : getAllConnections(oldMux)) {
			doReconnect(conn, findUpdatedInterfaceElement(newMux, oldMux, conn.getSource()),
					findUpdatedInterfaceElement(newMux, oldMux, conn.getDestination()));
		}
	}

	private static List<Connection> getAllConnections(final StructManipulator mux) {
		final List<Connection> retVal = new ArrayList<>();
		for (final IInterfaceElement ifEle : mux.getInterface().getAllInterfaceElements()) {
			if (ifEle.isIsInput()) {
				retVal.addAll(ifEle.getInputConnections());
			} else {
				retVal.addAll(ifEle.getOutputConnections());
			}
		}
		return retVal;
	}

	private static IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement copiedElement,
			final FBNetworkElement orgElement, final IInterfaceElement orig) {
		if (orig == null) {
			// this happens when a connection has already been reconnected,
			// i.e., a connection from an output to an input of the same FB
			return null;
		}
		IInterfaceElement retval = orig;
		if (orig.getFBNetworkElement() == orgElement) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			retval = copiedElement.getInterfaceElement(orig.getName());
		}
		return retval;
	}

	private void doReconnect(final Connection oldConn, final IInterfaceElement source, final IInterfaceElement dest) {
		// the connection may be already in our list if source and dest are on our FB
		if (!isInDeleteConnList(oldConn)) {
			final FBNetwork fbn = oldConn.getFBNetwork();
			// we have to delete the old connection in all cases
			final DeleteConnectionCommand cmd = new DeleteConnectionCommand(oldConn);
			cmd.execute();
			deleteConnCmds.add(cmd);

			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, fbn);
				if (null != dccc) {
					dccc.setSource(source);
					dccc.setDestination(dest);
					if (dccc.canExecute()) {
						dccc.setArrangementConstraints(oldConn.getRoutingData());
						dccc.execute();
						connCreateCmds.add(dccc);
					}
				}
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

	private static AbstractConnectionCreateCommand createConnCreateCMD(final IInterfaceElement refIE, final FBNetwork fbn) {
		AbstractConnectionCreateCommand retVal = null;
		if (refIE instanceof Event) {
			retVal = new EventConnectionCreateCommand(fbn);
		} else if (refIE instanceof VarDeclaration) {
			retVal = new DataConnectionCreateCommand(fbn);
		}
		return retVal;
	}
}
