/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *                			Martin Erich Jobst
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
 *   Martin Melik Merkumians - modernize Java, preserves VarConfig and Visible attributes at type update
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBManagement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractUpdateFBNElementCommand extends Command implements ConnectionLayoutTagger, ScopedCommand {
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
	/** The index where the fbNetwork element should be added */
	protected int oldIndex;

	protected FBNetwork network;

	protected TypeEntry entry;

	protected AbstractUpdateFBNElementCommand(final FBNetworkElement oldElement) {
		this.oldElement = Objects.requireNonNull(oldElement);
		this.network = Objects.requireNonNull(this.oldElement.getFbNetwork(), "Element not in a network"); //$NON-NLS-1$
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
			oldIndex = network.getNetworkElements().indexOf(oldElement);
			network.getNetworkElements().add(oldIndex, newElement);
		}

		handleErrorMarker();
		// Find connectionless pins which should be saved
		handleParameters();

		// Find connections which should be reconnected
		handleConnections();
		reconnCmds.execute();

		// set Visible attribute after reconnect, to not hide connected In/Outputs
		// transfer attributes from type first (for new vars), then override them from
		// old instance
		if (newElement.getType() != null) {
			transferVisibleAndVarConfigAttributes(newElement.getType().getInterfaceList().getInputVars());
			transferVisibleAndVarConfigAttributes(newElement.getType().getInterfaceList().getOutputVars());
		}
		transferVisibleAndVarConfigAttributes(oldElement.getInterface().getInputVars());
		transferVisibleAndVarConfigAttributes(oldElement.getInterface().getOutputVars());

		if (network != null) {
			network.getNetworkElements().remove(oldElement);
		}

		newElement.setName(oldElement.getName());

		// Map FB
		if (resource != null) {
			mapCmd = MapToCommand.createMapToCommand(newElement, resource);
			if (mapCmd instanceof final MapToCommand mapToCommand) {
				mapToCommand.setElementIndex(unmapCmd.getElementIndex());
			}

			if (mapCmd.canExecute()) {
				mapCmd.execute();
				recreateResourceConns(resourceConns);
			}
		}
	}

	protected void handleConfigurableFB() {
		// for the configurable fb we have to transfer the data type
		if (newElement instanceof final ConfigurableFB configFb
				&& oldElement instanceof final ConfigurableFB oldConfigFb) {
			configFb.setDataType(oldConfigFb.getDataType());

			if (configFb instanceof final Demultiplexer newDemux && oldConfigFb instanceof final Demultiplexer oldDemux
					&& oldDemux.isIsConfigured()) {
				newDemux.loadConfiguration(LibraryElementTags.DEMUX_VISIBLE_CHILDREN,
						ConfigurableFBManagement.buildVisibleChildrenString(oldDemux.getMemberVars()));
			} else {
				configFb.updateConfiguration();
			}
		}
	}

	@Override
	public void redo() {
		if (unmapCmd != null) {
			unmapCmd.redo();
		}
		checkGroup(oldElement, newElement);

		network.getNetworkElements().add(oldIndex, newElement);
		reconnCmds.redo();
		network.getNetworkElements().remove(oldElement);

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

		network.getNetworkElements().add(oldIndex, oldElement);
		reconnCmds.undo();
		network.getNetworkElements().remove(newElement);

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

	protected void setInterface() {
		newElement.setInterface(newElement.getType().getInterfaceList().copy());
	}

	private void transferVisibleAndVarConfigAttributes(final EList<VarDeclaration> varDeclList) {
		varDeclList.forEach(varDecl -> {
			if (newElement.getInterfaceElement(varDecl.getName()) instanceof final VarDeclaration newDecl) {
				if ((newDecl.isIsInput() && newDecl.getInputConnections().isEmpty())
						|| (!newDecl.isIsInput() && newDecl.getOutputConnections().isEmpty())) {
					newDecl.setVisible(varDecl.isVisible());
				}
				newDecl.setVarConfig(varDecl.isVarConfig());
			}
		});
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

	protected static IInterfaceElement findUpdatedInterfaceElement(final FBNetworkElement newElement,
			final FBNetworkElement oldElement, final IInterfaceElement oldInterface) {
		if ((oldInterface != null) && (oldInterface.getFBNetworkElement() == oldElement)) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			return updateSelectedInterface(oldInterface, newElement);
		}
		return oldInterface;
	}

	protected static List<Connection> getAllConnections(final FBNetworkElement element) {
		return element.getInterface().getAllInterfaceElements().stream().map((final IInterfaceElement ifEle) -> {
			if (ifEle.isIsInput()) {
				return ifEle.getInputConnections();
			}
			return ifEle.getOutputConnections();
		}).flatMap(List::stream).toList();

	}

	protected void createValues() {
		newElement.getInterface().getInputVars().stream().forEach(inVar -> {
			inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			checkSourceParam(inVar);
		});
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
		if ((oldElement instanceof ErrorMarkerFBNElement) && (newElement instanceof ErrorMarkerFBNElement)) {
			copyErrorMarkerRef();
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
				if (newElement
						.getInterfaceElement(erroMarker.getName()) instanceof final VarDeclaration varDeclaration) {
					final Value value = LibraryElementFactory.eINSTANCE.createValue();
					value.setValue(erroMarker.getValue().getValue());
					varDeclaration.setValue(value);
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

	private void copyErrorMarkerRef() {
		final FBNetworkElement repairedElement = ((ErrorMarkerFBNElement) oldElement).getRepairedElement();
		if (repairedElement != null) {
			((ErrorMarkerFBNElement) newElement).setRepairedElement(repairedElement);
		}
	}

	private static ErrorMarkerInterface createMissingMarker(final IInterfaceElement oldInterface,
			final FBNetworkElement element) {
		final ErrorMarkerInterface interfaceElement = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterface(
				oldInterface.getType(), oldInterface.getName(), oldInterface.isIsInput(), element.getInterface());

		if (oldInterface instanceof final VarDeclaration oldVarDecl && oldVarDecl.getValue() != null
				&& !oldVarDecl.getValue().getValue().isBlank()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(oldVarDecl.getValue().getValue());
			interfaceElement.setValue(value);
		}

		return interfaceElement;
	}

	private static IInterfaceElement updateSelectedInterface(final IInterfaceElement oldInterface,
			final FBNetworkElement newElement) {
		IInterfaceElement updatedSelected = oldInterface.isIsInput() ? newElement.getInput(oldInterface.getName())
				: newElement.getOutput(oldInterface.getName());
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
			cmd.setElementIndex(fbn.getConnectionIndex(oldConn));
			reconnCmds.add(cmd);
		}
	}

	protected boolean isConnectionInList(final Connection conn) {
		for (final Object cmd : reconnCmds.getCommands()) {
			if ((cmd instanceof final DeleteConnectionCommand deleteConnectionCommand)
					&& deleteConnectionCommand.getConnection().equals(conn)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isConnectionToBeCreated(final IInterfaceElement source, final IInterfaceElement dest) {
		for (final Object cmd : reconnCmds.getCommands()) {
			if ((cmd instanceof final AbstractConnectionCreateCommand abstractConnectionCreateCommand)
					&& (abstractConnectionCreateCommand.getSource() == source)
					&& (abstractConnectionCreateCommand.getDestination() == dest)) {
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

	protected void createNewFB() {
		newElement = createCopiedFBEntry(oldElement);
		setInterface();
		handleConfigurableFB();
		newElement.setName(oldElement.getName());
		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));
		copyAttributes();
		createValues();
		transferInstanceComments();
	}

	private void copyAttributes() {
		newElement.getAttributes().addAll(EcoreUtil.copyAll(oldElement.getAttributes()));
	}

	protected abstract FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement);

	public FBNetworkElement getOldElement() {
		return oldElement;
	}

	public FBNetworkElement getNewElement() {
		return newElement;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(network);
	}
}