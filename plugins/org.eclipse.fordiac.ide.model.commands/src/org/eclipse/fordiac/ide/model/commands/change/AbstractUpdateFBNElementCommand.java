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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
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

	protected final List<ErrorMarkerBuilder> errorPins;
	protected ErrorMarkerBuilder errorMarkerFB;
	protected PaletteEntry entry;

	protected AbstractUpdateFBNElementCommand(final FBNetworkElement oldElement) {
		this.oldElement = oldElement;
		this.network = this.oldElement.getFbNetwork();
		errorPins = new ArrayList<>();
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

		createNewFB();

		network.getNetworkElements().add(newElement);

		handleErrorMarker();

		// Find connections which should be reconnected
		handleApplicationConnections();

		network.getNetworkElements().remove(oldElement);

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

	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}
		deleteConnCmds.redo();

		network.getNetworkElements().remove(oldElement);
		network.getNetworkElements().add(newElement);
		errorPins.forEach(FordiacMarkerHelper::createMarkerInFile);

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

		errorPins.stream().map(ErrorMarkerBuilder::getErrorMarkerRef).forEach(FordiacMarkerHelper::deleteErrorMarker);
		connCreateCmds.undo();
		if (errorMarkerFB != null && newElement instanceof ErrorMarkerRef) {
			FordiacMarkerHelper.deleteErrorMarker((ErrorMarkerRef) newElement);
		}

		network.getNetworkElements().remove(newElement);
		network.getNetworkElements().add(oldElement);

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

	protected IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement newElement,
			final FBNetworkElement oldElement, final IInterfaceElement oldInterface) {
		if (oldInterface != null && oldInterface.getFBNetworkElement() == oldElement) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			final IInterfaceElement interfaceElement = updateSelectedInterface(oldInterface, newElement);

			if (!oldInterface.getType().isCompatibleWith(interfaceElement.getType())) {
				return createWrongTypeMarker(oldInterface, interfaceElement, newElement);
			}

			return interfaceElement;
		}
		return oldInterface;
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

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement, final IInterfaceElement oldInterface,
			final String errorMessage) {
		return createErrorMarker(newElement, oldInterface, oldInterface.getName(), errorMessage);
	}

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement, final IInterfaceElement oldInterface,
			final String name, final String errorMessage) {
		final boolean markerExists = newElement.getInterface().getErrorMarker().stream()
				.anyMatch(e -> e.getName().equals(name));

		IInterfaceElement interfaceElement;
		interfaceElement = ConnectionHelper.createErrorMarkerInterface(oldInterface.getType(), name,
				oldInterface.isIsInput(), newElement.getInterface());

		if (!markerExists) {
			final ErrorMarkerBuilder createErrorMarker = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement,
					0);
			createErrorMarker.setErrorMarkerRef((ErrorMarkerRef) interfaceElement);
			FordiacMarkerHelper.createMarkerInFile(createErrorMarker);
			errorPins.add(createErrorMarker);
		}
		return interfaceElement;
	}

	private ErrorMarkerInterface createWrongTypeMarker(final IInterfaceElement oldInterface, final IInterfaceElement newInterface,
			final FBNetworkElement element) {
		final String errorMessage = MessageFormat.format(Messages.UpdateFBTypeCommand_type_mismatch,
				oldInterface.getTypeName(), newInterface.getTypeName());
		final ErrorMarkerInterface createErrorMarker = (ErrorMarkerInterface) createErrorMarker(element, oldInterface,
				oldInterface.getName(), errorMessage);
		createErrorMarker.setErrorMessage(Messages.UpdateFBTypeCommand_wrong_type);
		return createErrorMarker;
	}

	private IInterfaceElement updateSelectedInterface(final IInterfaceElement oldInterface, final FBNetworkElement newElement) {
		IInterfaceElement updatedSelected = newElement.getInterfaceElement(oldInterface.getName());
		if (updatedSelected == null) {
			updatedSelected = createErrorMarker(newElement, oldInterface,
					MessageFormat.format(Messages.UpdateFBTypeCommand_Pin_not_found, oldInterface.getName()));
		}
		return updatedSelected;
	}

	protected void handleApplicationConnections() {
		for (final Connection connection : getAllConnections(oldElement)) {
			IInterfaceElement onSelectedFB;
			IInterfaceElement onOtherFB;
			IInterfaceElement updatedSelected;
			IInterfaceElement updatedOther;

			if (connection.getSourceElement() == oldElement) {
				// OUPUT
				onSelectedFB = connection.getSource();
				onOtherFB = connection.getDestination();
			} else {
				// INPUT
				onSelectedFB = connection.getDestination();
				onOtherFB = connection.getSource();
			}

			updatedSelected = updateSelectedInterface(onSelectedFB, newElement);
			updatedOther = onOtherFB.getFBNetworkElement().getInterfaceElement(onOtherFB.getName());

			handleReconnect(connection, onSelectedFB, onOtherFB, updatedSelected, updatedOther);
		}
	}

	private void handleReconnect(final Connection connection, final IInterfaceElement selected, final IInterfaceElement other,
			IInterfaceElement updatedSelected, IInterfaceElement updatedOther) {
		if (!updatedSelected.getType().isCompatibleWith(updatedOther.getType())) {
			// connection not compatible
			if (other instanceof ErrorMarkerInterface && other.getType().isCompatibleWith(updatedSelected.getType())) {
				updatedOther = other;
			} else {
				updatedSelected = createWrongTypeMarker(selected, updatedSelected, newElement);
			}
		}

		if (updatedOther instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedOther).setRepairedEndpoint(updatedSelected);
		}
		if (updatedSelected instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedSelected).setRepairedEndpoint(updatedOther);
		}

		if (updatedOther instanceof ErrorMarkerInterface) {
			reconnectConnections(connection, updatedSelected, updatedOther, connection.getFBNetwork());
			updatedOther = null;
		}

		reconnect(connection, updatedSelected, updatedOther);
	}

	private void reconnect(final Connection connection, final IInterfaceElement updatedSelected,
			final IInterfaceElement updatedOther) {
		if (connection.getSourceElement() == oldElement) {
			doReconnect(connection, updatedSelected, updatedOther);
		} else {
			doReconnect(connection, updatedOther, updatedSelected);
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

	protected void reconnectConnections(final Connection oldConn, final IInterfaceElement source,
			final IInterfaceElement dest, FBNetwork fbn) {
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

	protected AbstractConnectionCreateCommand createConnCreateCMD(IInterfaceElement interfaceElement,
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
		if (interfaceElement instanceof ErrorMarkerInterface) {
			return new DataConnectionCreateCommand(fbn);
		}
		return null;
	}

	protected abstract void createNewFB();
}