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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeStructCommand extends AbstractUpdateFBNElementCommand {
	
	private final StructuredType newStruct;
	private final PaletteEntry entry;

	public ChangeStructCommand(final StructManipulator mux, final StructuredType newStruct) {
		super(mux);
		this.newStruct = newStruct;
		entry = mux.getPaletteEntry();
	}
	
	
	private void createNewMux() {
		if (oldElement instanceof Multiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (oldElement instanceof Demultiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		newElement.setPaletteEntry(entry);
		newElement.setInterface(EcoreUtil.copy(oldElement.getType().getInterfaceList()));
		newElement.setName(oldElement.getName());

		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));
		newElement.getAttributes().addAll(EcoreUtil.copyAll(oldElement.getAttributes()));
		newElement.deleteAttribute("VisibleChildren"); // TODO use constant
		((StructManipulator) newElement).setStructTypeElementsAtInterface(newStruct);
		createValues();
		replaceFBs(oldElement, newElement);
	}

	public StructManipulator getNewMux() {
		return (StructManipulator) newElement;
	}

	public StructManipulator getOldMux() {
		return (StructManipulator) oldElement;
	}


	
	@Override
	protected void handleApplicationConnections() {
		for (final Connection conn : getAllConnections(oldElement)) {
			doReconnect(conn, findUpdatedInterfaceElement(newElement, oldElement, conn.getSource()),
					findUpdatedInterfaceElement(newElement, oldElement, conn.getDestination()));
		}
	}
	
	@Override
	protected IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement copiedElement,
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
	
	@Override
	protected AbstractConnectionCreateCommand createConnCreateCMD(final IInterfaceElement refIE, final FBNetwork fbn) {
		AbstractConnectionCreateCommand retVal = null;
		if (refIE instanceof Event) {
			retVal = new EventConnectionCreateCommand(fbn);
		} else if (refIE instanceof VarDeclaration) {
			retVal = new DataConnectionCreateCommand(fbn);
		}
		return retVal;
	}

	@Override
	protected void reconnectConnections(Connection oldConn, IInterfaceElement source, IInterfaceElement dest,
			FBNetwork fbn) {
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


	@Override
	protected void handleExecute() {
		createNewMux();
		// Find connections which should be reconnected
		handleApplicationConnections();
	}

	@Override
	protected void handleRedo() {
		replaceFBs(oldElement, newElement);
	}

	@Override
	protected void handleUndo() {
		connCreateCmds.undo();
	}
}
