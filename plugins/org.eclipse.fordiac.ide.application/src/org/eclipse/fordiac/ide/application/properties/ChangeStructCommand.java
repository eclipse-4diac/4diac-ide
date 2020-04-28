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
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeStructCommand extends Command {

	private StructuredType newStruct;
	private Multiplexer newMux;
	private Multiplexer oldMux;
	private PaletteEntry entry;
	private FBNetwork network;

	private CompoundCommand deleteConnCmds = new CompoundCommand();
	private CompoundCommand connCreateCmds = new CompoundCommand();

	public ChangeStructCommand(Multiplexer mux, StructuredType struct) {
		this.oldMux = mux;
		newStruct = struct;
		entry = mux.getPaletteEntry();
		network = mux.getFbNetwork();
	}

	@Override
	public void execute() {
		createNewMux();
		// Find connections which should be reconnected
		handleApplicationConnections();

	}

	private void createNewMux() {
		newMux = LibraryElementFactory.eINSTANCE.createMultiplexer();
		newMux.setInterface(EcoreUtil.copy(oldMux.getType().getInterfaceList()));
		newMux.setName(oldMux.getName());
		newMux.setX(oldMux.getX());
		newMux.setY(oldMux.getY());
		newMux.setStructType(newStruct);
		newMux.setPaletteEntry(entry);
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

	@Override
	public void undo() {
		connCreateCmds.undo();
		replaceFBs(newMux, oldMux);
		deleteConnCmds.undo();
	}

	@Override
	public void redo() {
		deleteConnCmds.redo();
		replaceFBs(oldMux, newMux);
		connCreateCmds.redo();
	}

	private void handleApplicationConnections() {
		for (Connection conn : getAllConnections(oldMux)) {
			doReconnect(conn, findUpdatedInterfaceElement(newMux, oldMux, conn.getSource()),
					findUpdatedInterfaceElement(newMux, oldMux, conn.getDestination()));
		}
	}

	private static List<Connection> getAllConnections(Multiplexer mux) {
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
