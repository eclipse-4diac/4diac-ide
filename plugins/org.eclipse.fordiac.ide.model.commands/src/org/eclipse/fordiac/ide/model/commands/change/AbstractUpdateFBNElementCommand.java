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
 *   Michael Oberlehner - added Error Marker Handling
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
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractUpdateFBNElementCommand extends Command {
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

	protected final CompoundCommand reconnCmds = new CompoundCommand();
	protected final CompoundCommand resourceConnCreateCmds = new CompoundCommand();

	protected MapToCommand mapCmd = null;
	protected UnmapCommand unmapCmd = null;

	/** The updated version of the FBNetworkElement */
	protected FBNetworkElement newElement;

	/** The FBNetworkElement which should be updated */
	protected FBNetworkElement oldElement;
	protected FBNetwork network;

	protected final List<ErrorMarkerBuilder> errorPins;
	protected ErrorMarkerBuilder errorMarkerBuilder;
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

		// deletion has to be done before old element is removed
		if (errorMarkerBuilder != null && onlyOldElementIsErrorMarker()) {
			ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) oldElement);
		}

		network.getNetworkElements().add(newElement);
		reconnCmds.redo();
		network.getNetworkElements().remove(oldElement);

		errorPins.forEach(ErrorMarkerBuilder::createMarkerInFile);

		// creation has to be done after new element is inserted
		if (errorMarkerBuilder != null && onlyNewElementIsErrorMarker()) {
			errorMarkerBuilder.createMarkerInFile();
		}

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

		errorPins.stream().map(ErrorMarkerBuilder::getErrorMarkerRef).forEach(ErrorMarkerBuilder::deleteErrorMarker);

		// the deletion has to be done before the new element is removed
		if (errorMarkerBuilder != null && onlyNewElementIsErrorMarker()) {
			ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) newElement);
		}

		network.getNetworkElements().add(oldElement);
		reconnCmds.undo();
		network.getNetworkElements().remove(newElement);

		// the creation has to be done after the old element was inserted
		if (errorMarkerBuilder != null && onlyOldElementIsErrorMarker()) {
			errorMarkerBuilder.createMarkerInFile();
		}

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
			final IInterfaceElement dest = findUpdatedInterfaceElement(copiedMappedElement, orgMappedElement,
					connData.dest);
			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source,
						copiedMappedElement.getFbNetwork());
				dccc.setSource(source);
				dccc.setDestination(dest);
				if (dccc.canExecute()) {
					dccc.execute();
					resourceConnCreateCmds.add(dccc);
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
		for (final Object cmd : reconnCmds.getCommands()) {
			if (cmd instanceof DeleteConnectionCommand
					&& ((DeleteConnectionCommand) cmd).getConnectionView().equals(conn)) {
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
					|| ((dest.getFBNetworkElement() == resElement) && (source.getFBNetworkElement().getOpposite()
							.getFbNetwork() != oldElement.getFbNetwork()))) {
				// one of both ends is a FB coming from a different fb network and therefore
				// this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}
		}
		return retVal;
	}

	private void handleErrorMarker() {
		if (onlyNewElementIsErrorMarker()) {
			final String errorMessage = MessageFormat.format("Type File: {0} could not be loaded for FB", //$NON-NLS-1$
					entry.getFile() != null ? entry.getFile().getFullPath() : "null type"); //$NON-NLS-1$
			errorMarkerBuilder = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement, 0);
			errorMarkerBuilder.setErrorMarkerRef((ErrorMarkerRef) newElement);
			((ErrorMarkerRef) newElement).setErrorMessage(errorMessage);
			errorMarkerBuilder.createMarkerInFile();
			moveEntryToErrorLib();
		}

		if (oldElement instanceof ErrorMarkerFBNElement && newElement instanceof ErrorMarkerFBNElement) {
			copyErrorMarkerRef();
		}

		if (onlyOldElementIsErrorMarker()) {
			errorMarkerBuilder = ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) oldElement);
		}

	}

	private boolean onlyNewElementIsErrorMarker() {
		return (!(oldElement instanceof ErrorMarkerFBNElement)) && newElement instanceof ErrorMarkerFBNElement;
	}

	private boolean onlyOldElementIsErrorMarker() {
		return oldElement instanceof ErrorMarkerFBNElement && !(newElement instanceof ErrorMarkerFBNElement);
	}

	private void copyErrorMarkerRef() {
		final long id = ((ErrorMarkerFBNElement) oldElement).getFileMarkerId();
		final String errorMessage = ((ErrorMarkerFBNElement) oldElement).getErrorMessage();
		final FBNetworkElement repairedElement = ((ErrorMarkerFBNElement) oldElement).getRepairedElement();
		((ErrorMarkerFBNElement) newElement).setFileMarkerId(id);
		((ErrorMarkerFBNElement) newElement).setErrorMessage(errorMessage);
		if (repairedElement != null) {
			((ErrorMarkerFBNElement) newElement).setRepairedElement(repairedElement);
		}
	}

	private void moveEntryToErrorLib() {
		final TypeLibrary typeLibrary = oldElement.getTypeLibrary();
		typeLibrary.removePaletteEntry(entry);
		typeLibrary.getErrorTypeLib().addPaletteEntry(entry);

	}

	private IInterfaceElement createErrorMarker(final FBNetworkElement newElement, final IInterfaceElement oldInterface,
			final String errorMessage) {
		final boolean markerExists = newElement.getInterface().getErrorMarker().stream()
				.anyMatch(e -> e.getName().equals(oldInterface.getName()) && e.isIsInput() == oldInterface.isIsInput());

		IInterfaceElement interfaceElement;
		interfaceElement = ConnectionHelper.createErrorMarkerInterface(oldInterface.getType(), oldInterface.getName(),
				oldInterface.isIsInput(), newElement.getInterface());

		if (!markerExists) {
			final ErrorMarkerBuilder createErrorMarker = FordiacMarkerHelper.createErrorMarker(errorMessage, newElement,
					0);
			createErrorMarker.setErrorMarkerRef((ErrorMarkerRef) interfaceElement);
			createErrorMarker.createMarkerInFile();
			errorPins.add(createErrorMarker);
		}
		return interfaceElement;
	}

	private ErrorMarkerInterface createWrongTypeMarker(final IInterfaceElement oldInterface,
			final IInterfaceElement newInterface, final FBNetworkElement element) {
		final String errorMessage = MessageFormat.format(Messages.UpdateFBTypeCommand_type_mismatch,
				oldInterface.getTypeName(), newInterface.getTypeName());
		final ErrorMarkerInterface createErrorMarker = (ErrorMarkerInterface) createErrorMarker(element, oldInterface,
				errorMessage);
		createErrorMarker.setErrorMessage(Messages.UpdateFBTypeCommand_wrong_type);
		return createErrorMarker;
	}

	private ErrorMarkerInterface creatMissingMarker(final IInterfaceElement oldInterface,
			final FBNetworkElement element) {
		return (ErrorMarkerInterface) createErrorMarker(element, oldInterface,
				MessageFormat.format(Messages.UpdateFBTypeCommand_Pin_not_found, oldInterface.getName()));
	}

	private IInterfaceElement updateSelectedInterface(final IInterfaceElement oldInterface,
			final FBNetworkElement newElement) {
		IInterfaceElement updatedSelected = newElement.getInterfaceElement(oldInterface.getName());
		if (updatedSelected == null) {
			updatedSelected = creatMissingMarker(oldInterface, newElement);
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

			if (onSelectedFB != null && onOtherFB != null) {
				updatedSelected = updateSelectedInterface(onSelectedFB, newElement);
				if (onSelectedFB.getFBNetworkElement() == onOtherFB.getFBNetworkElement()) {
					updatedOther = updateSelectedInterface(onOtherFB, newElement);
				} else {
					updatedOther = ((InterfaceList) onOtherFB.eContainer()).getInterfaceElement(onOtherFB.getName());
				}

				handleReconnect(connection, onSelectedFB, onOtherFB, updatedSelected, updatedOther);
			}
		}
	}

	private void handleReconnect(final Connection connection, final IInterfaceElement selected,
			final IInterfaceElement other, IInterfaceElement updatedSelected, IInterfaceElement updatedOther) {
		if (!updatedSelected.getType().isCompatibleWith(updatedOther.getType())) {
			// connection not compatible
			if (other instanceof ErrorMarkerInterface && other.getType().isCompatibleWith(updatedSelected.getType())) {
				updatedOther = other;
			} else {
				updatedSelected = createWrongTypeMarker(selected, updatedSelected, newElement);
			}
		}

		if (selected.isIsInput() != updatedSelected.isIsInput()) {
			updatedSelected = creatMissingMarker(selected, newElement);
		}
		if (other.isIsInput() != updatedOther.isIsInput()) {
			updatedOther = other;
		}

		if (updatedOther instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedOther).setRepairedEndpoint(updatedSelected);
		}
		if (updatedSelected instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) updatedSelected).setRepairedEndpoint(updatedOther);
		}

		// reconnect/replace connection
		if (updatedOther instanceof ErrorMarkerInterface
				&& updatedOther.getFBNetworkElement() != updatedSelected.getFBNetworkElement()) {
			reconnectConnection(connection, updatedSelected, connection.getSourceElement() == oldElement,
					connection.getFBNetwork());
		} else {
			if (connection.getSourceElement() == oldElement) {
				replaceConnection(connection, updatedSelected, updatedOther);
			} else {
				replaceConnection(connection, updatedOther, updatedSelected);
			}
		}
	}

	protected void replaceConnection(final Connection oldConn, final IInterfaceElement source,
			final IInterfaceElement dest) {
		// the connection may be already in our list if source and dest are on our FB
		if (!isInDeleteConnList(oldConn)) {
			final FBNetwork fbn = oldConn.getFBNetwork();
			// we have to delete the old connection in all cases
			final DeleteConnectionCommand cmd = new DeleteConnectionCommand(oldConn);
			cmd.execute();
			reconnCmds.add(cmd);

			if ((source != null) && (dest != null)) {
				// if source or dest is null it means that an interface element is not available
				// any more
				final AbstractConnectionCreateCommand dccc = createConnCreateCMD(source, fbn);
				dccc.setSource(source);
				dccc.setDestination(dest);
				dccc.setArrangementConstraints(oldConn.getRoutingData());
				if (dccc.canExecute()) {
					dccc.execute();
					reconnCmds.add(dccc);
				}
			}
		}
	}

	protected void reconnectConnection(final Connection oldConn, final IInterfaceElement selected,
			final boolean isSourceReconnect, final FBNetwork fbn) {
		final AbstractReconnectConnectionCommand reconnConnCmd = createReconnCMD(oldConn, selected,
				isSourceReconnect, fbn);
		if (reconnConnCmd.canExecute()) {
			reconnConnCmd.execute();
			reconnCmds.add(reconnConnCmd);
		}
	}

	protected AbstractReconnectConnectionCommand createReconnCMD(final Connection connection,
			final IInterfaceElement interfaceElement, final boolean isSourceReconnect, final FBNetwork fbn) {
		final DataType type = interfaceElement.getType();
		if (type instanceof EventType) {
			return new ReconnectEventConnectionCommand(connection, isSourceReconnect, interfaceElement, fbn);
		}
		if (type instanceof AdapterType) {
			return new ReconnectAdapterConnectionCommand(connection, isSourceReconnect, interfaceElement, fbn);
		}
		return new ReconnectDataConnectionCommand(connection, isSourceReconnect, interfaceElement, fbn);
	}

	protected static AbstractConnectionCreateCommand createConnCreateCMD(final IInterfaceElement interfaceElement,
			final FBNetwork fbn) {
		final DataType type = interfaceElement.getType();
		if (type instanceof EventType) {
			return new EventConnectionCreateCommand(fbn);
		}
		if (type instanceof AdapterType) {
			return new AdapterConnectionCreateCommand(fbn);
		}
		return new DataConnectionCreateCommand(fbn);
	}

	protected abstract void createNewFB();
}