/*******************************************************************************
 * Copyright (c) 2012 - 2017 AIT, fortiss GmbH, Profactor GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked update fb type to accept also supapps
 *   Alois Zoitl - fixed issues in maintaining FB parameters
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdateFBTypeCommand extends Command {

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

	/** The FBNetworkElement which should be updated */
	private FBNetworkElement oldElement;

	/** The updated version of the FBNetworkElement */
	private FBNetworkElement newElement;

	/** If not null this entry should be used for the type of the updated FB */
	private PaletteEntry entry;

	private FBNetwork network;

	private final CompoundCommand deleteConnCmds = new CompoundCommand();
	private final CompoundCommand connCreateCmds = new CompoundCommand();
	private final CompoundCommand resourceConnCreateCmds = new CompoundCommand();

	private MapToCommand mapCmd = null;
	private UnmapCommand unmapCmd = null;

	private ErrorMarkerBuilder errorMarkerFB;
	private final List<ErrorMarkerBuilder> errorPins;

	public UpdateFBTypeCommand(final FBNetworkElement fbnElement, final PaletteEntry entry) {
		this.oldElement = fbnElement;
		network = fbnElement.getFbNetwork();
		if ((entry instanceof FBTypePaletteEntry) || (entry instanceof SubApplicationTypePaletteEntry)) {
			this.entry = entry;
		} else {
			this.entry = fbnElement.getPaletteEntry();
		}
		errorPins = new ArrayList<>();
	}

	@Override
	public boolean canExecute() {
		return (null != entry) && (null != oldElement) && (null != network);
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute() */
	@Override
	public void execute() {
		Resource resource = null;
		List<ConnData> resourceConns = null; // buffer for the resource specific connections from the mapped fb that
		// needs to be recreated
		if (oldElement.isMapped()) {
			if (network.equals(oldElement.getResource().getFBNetwork())) {
				// this is the resource fb we need to use the opposite for a correct update
				oldElement = oldElement.getOpposite();
				network = oldElement.getFbNetwork();
			}
			resource = oldElement.getResource();
			resourceConns = getResourceCons();
			unmapCmd = new UnmapCommand(oldElement);
			unmapCmd.execute();
		}

		// Create new FB
		copyFB();

		network.getNetworkElements().add(newElement);
		// Find connections which should be reconnected

		handleErrorMarker();

		handleApplicationConnections();
		network.getNetworkElements().remove(oldElement);

		// Change name
		newElement.setName(oldElement.getName());

		// Map FB
		if (resource != null) {
			mapCmd = new MapToCommand(newElement, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
			}
		}
	}

	private void handleErrorMarker() {
		if ((!(oldElement instanceof ErrorMarkerFBNElement)) && newElement instanceof ErrorMarkerFBNElement) {
			final String errorMessage = MessageFormat.format("Type File: {0} could not be loaded for FB", //$NON-NLS-1$
					entry.getFile() != null ? entry.getFile().getFullPath() : "null type"); //$NON-NLS-1$
			errorMarkerFB = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement, 0);
			errorMarkerFB.setErrorMarkerRef((ErrorMarkerRef) newElement);
			((ErrorMarkerRef) newElement).setErrorMessage(errorMessage);
			FordiacMarkerHelper.createMarkerInFile(errorMarkerFB);
		}
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo() */
	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}

		deleteConnCmds.redo();
		network.getNetworkElements().remove(oldElement);
		handleErrorMarker();
		network.getNetworkElements().add(newElement);
		errorPins.forEach(FordiacMarkerHelper::createMarkerInFile);
		connCreateCmds.redo();

		if (mapCmd != null) {
			mapCmd.redo();
			// after redoing the mapping for the new FB recreate the resource cons
			resourceConnCreateCmds.redo();
		}
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo() */
	@Override
	public void undo() {
		if (mapCmd != null) {
			// before removing the copied FB remove the newly created resource connections
			resourceConnCreateCmds.undo();
			mapCmd.undo();
		}
		errorPins.stream().map(ErrorMarkerBuilder::getErrorMarkerRef).forEach(FordiacMarkerHelper::deleteErrorMarker);
		connCreateCmds.undo();
		if (errorMarkerFB != null && newElement instanceof ErrorMarkerRef) {
			FordiacMarkerHelper.deleteErrorMarker((ErrorMarkerRef) newElement);
		}
		replaceFBs(newElement, oldElement);
		deleteConnCmds.undo();

		if (unmapCmd != null) {
			unmapCmd.undo();
		}
	}

	protected void setEntry(final PaletteEntry entry) {
		this.entry = entry;
	}

	protected PaletteEntry getEntry() {
		return entry;
	}

	private void handleApplicationConnections() {
		for (final Connection connection : getAllConnections(oldElement)) {

			final IInterfaceElement source = findUpdatedInterfaceElement(newElement, oldElement,
					connection.getSource());

			final IInterfaceElement dest = findUpdatedInterfaceElement(newElement, oldElement,
					connection.getDestination());

			if (source instanceof ErrorMarkerInterface) {
				((ErrorMarkerInterface) source).setRepairedEndpoint(dest);
			}

			if (dest instanceof ErrorMarkerInterface) {
				((ErrorMarkerInterface) dest).setRepairedEndpoint(source);
			}

			doReconnect(connection, source, dest);

		}
	}

	private static List<Connection> getAllConnections(final FBNetworkElement element) {
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

	private IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement newElement,
			final FBNetworkElement oldElement, final IInterfaceElement oldInterface) {
		if (oldInterface != null && oldInterface.getFBNetworkElement() == oldElement) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			final IInterfaceElement interfaceElement = newElement.getInterfaceElement(oldInterface.getName());

			if (interfaceElement == null) {
				return createErrorMarker(newElement, oldInterface,
						"Pin " + oldInterface.getName() + " not found after Type update"); //$NON-NLS-1$ //$NON-NLS-2$
			}

			if (!oldInterface.getType().isCompatibleWith(interfaceElement.getType())) {
				final String errorMessage = oldInterface.getTypeName()
						+ "from previous type is not compatible with new Type: " + interfaceElement.getTypeName(); //$NON-NLS-1$
				return createErrorMarker(newElement, oldInterface, "wrongType: " + oldInterface.getName(), //$NON-NLS-1$
						errorMessage);
			}


		}
		return oldInterface;
	}


	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement,
			final IInterfaceElement oldInterface, final String errorMessage) {
		return createErrorMarker(newElement, oldInterface, oldInterface.getName(), errorMessage);
	}

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement,
			final IInterfaceElement oldInterface, final String name, final String errorMessage) {
		IInterfaceElement interfaceElement;
		interfaceElement = ConnectionHelper.createErrorMarkerInterface(oldInterface.getType(), name,
				oldInterface.isIsInput(), newElement.getInterface());
		final ErrorMarkerBuilder createErrorMarker = FordiacMarkerHelper
				.createErrorMarker(errorMessage, newElement, 0);
		createErrorMarker.setErrorMarkerRef((ErrorMarkerRef) interfaceElement);
		FordiacMarkerHelper.createMarkerInFile(createErrorMarker);
		errorPins.add(createErrorMarker);
		return interfaceElement;
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
					dccc.setArrangementConstraints(oldConn.getRoutingData());
					dccc.execute();
					connCreateCmds.add(dccc);
				}
			}
		}
	}

	private static AbstractConnectionCreateCommand createConnCreateCMD(IInterfaceElement interfaceElement,
			final FBNetwork fbn) {
		if (interfaceElement instanceof ErrorMarkerInterface) {
			interfaceElement = ((ErrorMarkerInterface) interfaceElement).getRepairedEndpoint();
		}
		if (interfaceElement instanceof Event) {
			return new EventConnectionCreateCommand(fbn);
		}
		if (interfaceElement instanceof AdapterDeclaration) {
			return new AdapterConnectionCreateCommand(fbn);
		}
		if (interfaceElement instanceof VarDeclaration) {
			return new DataConnectionCreateCommand(fbn);
		}
		return null;

	}

	private boolean isInDeleteConnList(final Connection conn) {
		for (final Object cmd : deleteConnCmds.getCommands()) {
			if (((DeleteConnectionCommand) cmd).getConnectionView().equals(conn)) {
				return true;
			}
		}
		return false;
	}

	private void replaceFBs(final FBNetworkElement remElement, final FBNetworkElement addElement) {
		network.getNetworkElements().remove(remElement);
		network.getNetworkElements().add(addElement);
	}

	private void copyFB() {
		newElement = createCopiedFBEntry(oldElement);

		newElement.setInterface(newElement.getType().getInterfaceList().copy());

		newElement.setName(oldElement.getName());
		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));

		createValues();

	}

	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		FBNetworkElement copy;

		if (entry instanceof SubApplicationTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createSubApp();
		} else if (entry instanceof AdapterTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createAdapterFB();
			((AdapterFB) copy).setAdapterDecl(((AdapterFB) srcElement).getAdapterDecl());
		} else if (entry.getType() instanceof CompositeFBType) {
			copy = LibraryElementFactory.eINSTANCE.createCFBInstance();
		}else if(oldElement instanceof ErrorMarkerFBNElement && entry instanceof FBTypePaletteEntry){
			copy = createErrorTypeFb();
		} else if (entry.getFile() == null || !entry.getFile().exists()) {
			copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		}
		else {
			copy = LibraryElementFactory.eINSTANCE.createFB();
		}


		copy.setPaletteEntry(entry);
		return copy;
	}

	public FBNetworkElement createErrorTypeFb() {
		FBNetworkElement copy;
		final TypeLibrary typeLibrary = oldElement.getPaletteEntry().getTypeLibrary();
		FBTypePaletteEntry fbTypeEntry = typeLibrary.getErrorTypeLib()
				.getFBTypeEntry(oldElement.getType().getName());
		if (fbTypeEntry == null) {
			fbTypeEntry = typeLibrary.getBlockTypeLib().getFBTypeEntry(oldElement.getType().getName());
		}

		if (fbTypeEntry != null && fbTypeEntry.getFile() != null) {
			copy = LibraryElementFactory.eINSTANCE.createFB();
			copy.setPaletteEntry(fbTypeEntry);
			return copy;
		}
		copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		return copy;
	}

	private void createValues() {
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

	private List<ConnData> getResourceCons() {
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
					|| ((dest.getFBNetworkElement() == resElement) && (source.getFBNetworkElement().getOpposite()
							.getFbNetwork() != oldElement.getFbNetwork()))) {
				// one of both ends is a FB coming from a different fb network and therefore
				// this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}

		}
		return retVal;
	}

	private void recreateResourceConns(final List<ConnData> resourceConns) {
		final FBNetworkElement orgMappedElement = unmapCmd.getMappedFBNetworkElement();
		final FBNetworkElement copiedMappedElement = newElement.getOpposite();
		for (final ConnData connData : resourceConns) {
			final IInterfaceElement source = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement,
					connData.source);
			final IInterfaceElement dest = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement,
					connData.dest);
			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source,
						copiedMappedElement.getFbNetwork());
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

}
