/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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

package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
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
		private IInterfaceElement source;
		private IInterfaceElement dest;

		public ConnData(IInterfaceElement source, IInterfaceElement dest) {
			this.source = source;
			this.dest = dest;
		}
	}

	private StructuredType newStruct;
	private StructManipulator newMux;
	private StructManipulator oldMux;
	private PaletteEntry entry;
	private FBNetwork network;

	private CompoundCommand deleteConnCmds = new CompoundCommand();
	private CompoundCommand connCreateCmds = new CompoundCommand();
	private CompoundCommand resourceConnCreateCmds = new CompoundCommand();

	private MapToCommand mapCmd = null;
	private UnmapCommand unmapCmd = null;

	public ChangeStructCommand(StructManipulator mux, StructuredType struct) {
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
		List<ConnData> retVal = new ArrayList<>();
		FBNetworkElement resElement = oldMux.getOpposite();
		for (Connection conn : getAllConnections(resElement)) {
			IInterfaceElement source = conn.getSource();
			IInterfaceElement dest = conn.getDestination();
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

	private void recreateResourceConns(List<ConnData> resourceConns) {
		FBNetworkElement orgMappedElement = unmapCmd.getMappedFBNetworkElement();
		FBNetworkElement copiedMappedElement = newMux.getOpposite();
		for (ConnData connData : resourceConns) {
			IInterfaceElement source = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement,
					connData.source);
			IInterfaceElement dest = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement, connData.dest);
			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, copiedMappedElement.getFbNetwork());
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

	private static List<Connection> getAllConnections(FBNetworkElement element) {
		List<Connection> retVal = new ArrayList<>();
		for (IInterfaceElement ifEle : element.getInterface().getAllInterfaceElements()) {
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
		newMux.setX(oldMux.getX());
		newMux.setY(oldMux.getY());
		newMux.setStructType(newStruct);
		createValues();
		replaceFBs(oldMux, newMux);
	}

	private void createValues() {
		for (VarDeclaration var : newMux.getInterface().getInputVars()) {
			var.setValue(LibraryElementFactory.eINSTANCE.createValue());
			checkSourceParam(var);
		}
	}

	private void checkSourceParam(VarDeclaration var) {
		VarDeclaration srcVar = oldMux.getInterface().getVariable(var.getName());
		if ((null != srcVar) && (null != srcVar.getValue())) {
			var.getValue().setValue(srcVar.getValue().getValue());
		}
	}

	private void replaceFBs(FBNetworkElement oldElement, FBNetworkElement newElement) {
		network.getNetworkElements().remove(oldElement);
		network.getNetworkElements().add(newElement);
	}

	public StructManipulator getNewMux() {
		return newMux;
	}

	private void handleApplicationConnections() {
		for (Connection conn : getAllConnections(oldMux)) {
			doReconnect(conn, findUpdatedInterfaceElement(newMux, oldMux, conn.getSource()),
					findUpdatedInterfaceElement(newMux, oldMux, conn.getDestination()));
		}
	}

	private static List<Connection> getAllConnections(StructManipulator mux) {
		List<Connection> retVal = new ArrayList<>();
		for (IInterfaceElement ifEle : mux.getInterface().getAllInterfaceElements()) {
			if (ifEle.isIsInput()) {
				retVal.addAll(ifEle.getInputConnections());
			} else {
				retVal.addAll(ifEle.getOutputConnections());
			}
		}
		return retVal;
	}

	private static IInterfaceElement findUpdatedInterfaceElement(FBNetworkElement copiedElement,
			FBNetworkElement orgElement, IInterfaceElement orig) {
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

	private void doReconnect(Connection oldConn, IInterfaceElement source, IInterfaceElement dest) {
		// the connection may be already in our list if source and dest are on our FB
		if (!isInDeleteConnList(oldConn)) {
			FBNetwork fbn = oldConn.getFBNetwork();
			// we have to delete the old connection in all cases
			DeleteConnectionCommand cmd = new DeleteConnectionCommand(oldConn);
			cmd.execute();
			deleteConnCmds.add(cmd);

			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, fbn);
				if (null != dccc) {
					dccc.setSource(source);
					dccc.setDestination(dest);
					if (dccc.canExecute()) {
						dccc.setArrangementConstraints(oldConn.getDx1(), oldConn.getDx2(), oldConn.getDy());
						dccc.execute();
						connCreateCmds.add(dccc);
					}
				}
			}
		}
	}

	private boolean isInDeleteConnList(Connection conn) {
		for (Object cmd : deleteConnCmds.getCommands()) {
			if (((DeleteConnectionCommand) cmd).getConnectionView().equals(conn)) {
				return true;
			}
		}
		return false;
	}

	private static AbstractConnectionCreateCommand createConnCreateCMD(IInterfaceElement refIE, FBNetwork fbn) {
		AbstractConnectionCreateCommand retVal = null;
		if (refIE instanceof Event) {
			retVal = new EventConnectionCreateCommand(fbn);
		} else if (refIE instanceof VarDeclaration) {
			retVal = new DataConnectionCreateCommand(fbn);
		}
		return retVal;
	}
}
