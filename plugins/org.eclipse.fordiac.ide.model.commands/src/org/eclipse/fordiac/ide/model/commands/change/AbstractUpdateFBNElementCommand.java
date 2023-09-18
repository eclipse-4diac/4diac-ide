/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *               2022 - 2023 Martin Erich Jobst
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
 *   Martin Jobst - fix data type compatibility handling
 *                - refactor and clean up
 *                - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractUpdateFBNElementCommand extends Command implements ConnectionLayoutTagger {
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
	protected Command mapCmd = null;
	protected UnmapCommand unmapCmd = null;

	/** The updated version of the FBNetworkElement */
	protected FBNetworkElement newElement;

	/** The FBNetworkElement which should be updated */
	protected FBNetworkElement oldElement;
	protected FBNetwork network;

	protected final CompoundCommand createMarkersCmds = new CompoundCommand();
	protected final CompoundCommand deleteMarkersCmds = new CompoundCommand();
	protected TypeEntry entry;

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

		createNewFB();
		checkGroup(oldElement, newElement); // needs to be done before anything is changed on the old element Bug
		// 579570
		if (network != null) {
			network.getNetworkElements().add(newElement);
		}

		handleErrorMarker();

		// deletion has to be done before old element is removed
		deleteMarkersCmds.execute();

		// Find connectionless pins which should be saved
		handleParameters();

		// Find connections which should be reconnected
		handleConnections();
		reconnCmds.execute();

		if (network != null) {
			network.getNetworkElements().remove(oldElement);
		}

		newElement.setName(oldElement.getName());

		// Map FB
		if (resource != null) {
			mapCmd = MapToCommand.createMapToCommand(newElement, resource);
			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
			}
		}

		// creation has to be done after new element is inserted
		createMarkersCmds.execute();
	}

	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}
		checkGroup(oldElement, newElement);

		// deletion has to be done before old element is removed
		deleteMarkersCmds.redo();

		network.getNetworkElements().add(newElement);
		reconnCmds.redo();
		network.getNetworkElements().remove(oldElement);

		// creation has to be done after new element is inserted
		createMarkersCmds.redo();

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

		// the deletion has to be done before the new element is removed
		createMarkersCmds.undo();

		network.getNetworkElements().add(oldElement);
		reconnCmds.undo();
		network.getNetworkElements().remove(newElement);

		// the creation has to be done after the old element was inserted
		deleteMarkersCmds.undo();

		checkGroup(newElement, oldElement);

		if (unmapCmd != null) {
			unmapCmd.undo();
		}
	}

	private static void checkGroup(final FBNetworkElement oldElem, final FBNetworkElement newElem) {
		if (oldElem.isInGroup()) {
			newElem.setGroup(oldElem.getGroup());
			oldElem.setGroup(null);
		}
	}

	public void setInterface() {
		newElement.setInterface(newElement.getType().getInterfaceList().copy());
		if (newElement instanceof Multiplexer) {
			((Multiplexer) newElement).setStructTypeElementsAtInterface((StructuredType) ((FBTypeEntry) entry).getType()
					.getInterfaceList().getOutputVars().get(0).getType());
		}
		if (newElement instanceof Demultiplexer) {
			((Demultiplexer) newElement).setStructTypeElementsAtInterface((StructuredType) ((FBTypeEntry) entry)
					.getType().getInterfaceList().getInputVars().get(0).getType());
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
				final AbstractConnectionCreateCommand dccc = createConnectionCreateCommand(
						copiedMappedElement.getFbNetwork(), source.getType());
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
		if ((oldInterface != null) && (oldInterface.getFBNetworkElement() == oldElement)) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			IInterfaceElement interfaceElement = updateSelectedInterface(oldInterface, newElement);

			if (!interfaceElement.getType().isAssignableFrom(oldInterface.getType())) {
				final String errorMessage = MessageFormat.format(Messages.UpdateFBTypeCommand_type_mismatch,
						oldInterface.getFullTypeName(), interfaceElement.getFullTypeName());

				final List<ErrorMarkerBuilder> errorMarkerBuilders = new ArrayList<>();
				interfaceElement = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterfaceElement(
						newElement.getInterface(), oldInterface, errorMessage, errorMarkerBuilders);
				errorMarkerBuilders.stream().map(FordiacMarkerCommandHelper::newCreateMarkersCommand)
						.forEachOrdered(createMarkersCmds::add);
			}

			return interfaceElement;
		}
		return oldInterface;
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

	protected void transferInstanceComments() {
		oldElement.getInterface().getAllInterfaceElements().stream().filter(ie -> !ie.getComment().isBlank())
				.forEach(ie -> {
					final IInterfaceElement newIE = newElement.getInterfaceElement(ie.getName());
					if (newIE != null) {
						newIE.setComment(ie.getComment());
					}
				});
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
			final String errorMessage = MessageFormat.format("Type: ({0}) could not be loaded for FB: {1}", //$NON-NLS-1$
					entry.getFullTypeName(), newElement.getName());
			((ErrorMarkerRef) newElement).setErrorMessage(errorMessage);
			createMarkersCmds.add(FordiacMarkerCommandHelper.newCreateMarkersCommand(
					ErrorMarkerBuilder.createErrorMarkerBuilder(errorMessage).setTarget(newElement)));
		}

		if ((oldElement instanceof ErrorMarkerFBNElement) && (newElement instanceof ErrorMarkerFBNElement)) {
			copyErrorMarkerRef();
		}

		if (onlyOldElementIsErrorMarker()) {
			deleteMarkersCmds.add(
					FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(oldElement)));
		}

		for (final IInterfaceElement element : oldElement.getInterface().getAllInterfaceElements()) {
			deleteMarkersCmds
					.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(element)));
		}

		for (final VarDeclaration input : oldElement.getInterface().getInputVars()) {
			deleteMarkersCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(
					FordiacMarkerHelper.findMarkers(input.getValue(), FordiacErrorMarker.INITIAL_VALUE_MARKER)));
			deleteMarkersCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(
					FordiacMarkerHelper.findMarkers(input.getArraySize(), FordiacErrorMarker.TYPE_DECLARATION_MARKER)));
		}

		for (final VarDeclaration input : newElement.getInterface().getInputVars()) {
			if (input.isArray()) {
				final String errorMessage = VariableOperations.validateType(input);
				input.getArraySize().setErrorMessage(errorMessage);
				if (!errorMessage.isBlank()) {
					createMarkersCmds.add(FordiacMarkerCommandHelper.newCreateMarkersCommand(ErrorMarkerBuilder
							.createErrorMarkerBuilder(errorMessage).setType(FordiacErrorMarker.TYPE_DECLARATION_MARKER)
							.setTarget(input.getArraySize())));
				}
			}
			if (hasValue(input.getValue())) {
				final String errorMessage = VariableOperations.validateValue(input);
				input.getValue().setErrorMessage(errorMessage);
				if (!errorMessage.isBlank()) {
					createMarkersCmds.add(FordiacMarkerCommandHelper
							.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(errorMessage)
									.setType(FordiacErrorMarker.INITIAL_VALUE_MARKER).setTarget(input.getValue())));
				}
			}
		}
	}

	// Ensure that connectionless pins with a value are saved as well
	protected void handleParameters() {
		for (final VarDeclaration input : oldElement.getInterface().getInputVars()) {
			// No outside connections to a pin in oldElement and it has an initial value
			if (input.getInputConnections().isEmpty() && hasValue(input.getValue())) {
				updateSelectedInterface(input, newElement);
			}
		}

		for (final VarDeclaration output : oldElement.getInterface().getOutputVars()) {
			if (output.getOutputConnections().isEmpty() && hasValue(output.getValue())) {
				updateSelectedInterface(output, newElement);
			}
		}
		checkErrorMarkerPinParameters();
	}

	private void checkErrorMarkerPinParameters() {
		for (final ErrorMarkerInterface erroMarker : oldElement.getInterface().getErrorMarker()) {
			if (hasValue(erroMarker.getValue())) {
				final IInterfaceElement updatedSelected = newElement.getInterfaceElement(erroMarker.getName());
				if (updatedSelected instanceof VarDeclaration) {
					final Value value = LibraryElementFactory.eINSTANCE.createValue();
					value.setValue(erroMarker.getValue().getValue());
					((VarDeclaration) updatedSelected).setValue(value);
					if (erroMarker.isIsInput() && erroMarker.getInputConnections().isEmpty()) {
						// remove errormarker because value was set to pin and no connection needs to be
						// copied
						reconnCmds.add(new DeleteErrorMarkerCommand(erroMarker, oldElement));
					}
				} else if ((erroMarker.isIsInput() && erroMarker.getInputConnections().isEmpty())
						|| (!erroMarker.isIsInput() && erroMarker.getOutputConnections().isEmpty())) {
					// unconnected error pin create a new error pin
					updateSelectedInterface(erroMarker, newElement);
				}
			}
		}
	}

	private static boolean hasValue(final Value value) {
		return (value != null) && (value.getValue() != null) && !value.getValue().isBlank();
	}

	private boolean onlyNewElementIsErrorMarker() {
		return (!(oldElement instanceof ErrorMarkerFBNElement)) && (newElement instanceof ErrorMarkerFBNElement);
	}

	private boolean onlyOldElementIsErrorMarker() {
		return (oldElement instanceof ErrorMarkerFBNElement) && !(newElement instanceof ErrorMarkerFBNElement);
	}

	private void copyErrorMarkerRef() {
		final String errorMessage = ((ErrorMarkerFBNElement) oldElement).getErrorMessage();
		final FBNetworkElement repairedElement = ((ErrorMarkerFBNElement) oldElement).getRepairedElement();
		((ErrorMarkerFBNElement) newElement).setErrorMessage(errorMessage);
		if (repairedElement != null) {
			((ErrorMarkerFBNElement) newElement).setRepairedElement(repairedElement);
		}
	}

	private ErrorMarkerInterface createMissingMarker(final IInterfaceElement oldInterface,
			final FBNetworkElement element) {
		final List<ErrorMarkerBuilder> errorMarkerBuilders = new ArrayList<>();
		final ErrorMarkerInterface errorMarkerInterface = FordiacErrorMarkerInterfaceHelper
				.createErrorMarkerInterfaceElement(element.getInterface(), oldInterface,
						MessageFormat.format(Messages.UpdateFBTypeCommand_Pin_not_found, oldInterface.getName()),
						errorMarkerBuilders);
		errorMarkerBuilders.stream().map(FordiacMarkerCommandHelper::newCreateMarkersCommand)
				.forEachOrdered(createMarkersCmds::add);
		return errorMarkerInterface;
	}

	private IInterfaceElement updateSelectedInterface(final IInterfaceElement oldInterface,
			final FBNetworkElement newElement) {
		IInterfaceElement updatedSelected = newElement.getInterfaceElement(oldInterface.getName());
		if ((updatedSelected == null) || (updatedSelected.isIsInput() != oldInterface.isIsInput())) {
			updatedSelected = createMissingMarker(oldInterface, newElement);
		}
		return updatedSelected;
	}

	protected void handleConnections() {
		getAllConnections(oldElement).forEach(this::handleConnection);
	}

	private void handleConnection(final Connection connection) {
		var source = connection.getSource();
		var destination = connection.getDestination();

		// get or create pins for new element (source and/or destination)
		if (connection.getSourceElement() == oldElement) {
			source = updateSelectedInterface(source, newElement);
		}
		if (connection.getDestinationElement() == oldElement) {
			destination = updateSelectedInterface(destination, newElement);
		}
		// check type compatibility
		if (!LinkConstraints.typeCheck(source, destination)) {
			// create wrong type error marker
			// if connected with itself, prefer marker in destination
			final String errorMessage = MessageFormat.format(Messages.UpdateFBTypeCommand_type_mismatch,
					source.getFullTypeName(), destination.getFullTypeName());
			final List<ErrorMarkerBuilder> errorMarkerBuilders = new ArrayList<>();
			if (connection.getDestinationElement() == oldElement) {
				destination = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterfaceElement(
						newElement.getInterface(), destination, errorMessage, errorMarkerBuilders);
			} else {
				source = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterfaceElement(newElement.getInterface(),
						source, errorMessage, errorMarkerBuilders);
			}
			errorMarkerBuilders.stream().map(FordiacMarkerCommandHelper::newCreateMarkersCommand)
					.forEachOrdered(createMarkersCmds::add);
		}

		// set repaired endpoints
		if (source instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) source).setRepairedEndpoint(destination);
		}
		if (destination instanceof ErrorMarkerInterface) {
			((ErrorMarkerInterface) destination).setRepairedEndpoint(source);
		}

		// reconnect/replace connection
		replaceConnection(connection, source, destination);
	}

	protected void replaceConnection(final Connection oldConn, final IInterfaceElement source,
			final IInterfaceElement dest) {
		if (!isConnectionInList(oldConn)) {
			reconnCmds.add(new DeleteConnectionCommand(oldConn, true));
		}
		if (!isConnectionToBeCreated(source, dest)) {
			final FBNetwork fbn = oldConn.getFBNetwork();
			final AbstractConnectionCreateCommand cmd = createConnectionCreateCommand(fbn, source.getType());
			cmd.setSource(source);
			cmd.setDestination(dest);
			cmd.setVisible(oldConn.isVisible());
			cmd.setArrangementConstraints(oldConn.getRoutingData());
			reconnCmds.add(cmd);
		}
	}

	protected boolean isConnectionInList(final Connection conn) {
		for (final Object cmd : reconnCmds.getCommands()) {
			if ((cmd instanceof DeleteConnectionCommand)
					&& ((DeleteConnectionCommand) cmd).getConnection().equals(conn)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isConnectionToBeCreated(final IInterfaceElement source, final IInterfaceElement dest) {
		for (final Object cmd : reconnCmds.getCommands()) {
			if ((cmd instanceof AbstractConnectionCreateCommand)
					&& (((AbstractConnectionCreateCommand) cmd).getSource() == source)
					&& (((AbstractConnectionCreateCommand) cmd).getDestination() == dest)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("static-method")
	protected AbstractConnectionCreateCommand createConnectionCreateCommand(final FBNetwork fbn, final DataType type) {
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