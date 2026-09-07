/*******************************************************************************
 * Copyright (c) 2021, 2025 Primetals Technologies Austria GmbH
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractUpdateBlockFBNElementCommand extends Command
		implements ConnectionLayoutTagger, ScopedCommand {
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
	protected BlockFBNetworkElement newElement;

	/** The FBNetworkElement which should be updated */
	protected final BlockFBNetworkElement oldElement;
	/** The index where the fbNetwork element should be added */
	protected int oldIndex;

	protected final FBNetwork network;

	protected TypeEntry entry;

	protected AbstractUpdateBlockFBNElementCommand(final BlockFBNetworkElement oldElement) {
		this.oldElement = selectOldElement(oldElement);
		this.network = Objects.requireNonNull(this.oldElement.getFbNetwork(), "Element not in a network"); //$NON-NLS-1$
	}

	private static BlockFBNetworkElement selectOldElement(final BlockFBNetworkElement oldElement) {
		final BlockFBNetworkElement selectedElement = Objects.requireNonNull(oldElement);
		if (selectedElement.isMapped() && selectedElement.getMapping().getTo().equals(selectedElement)) {
			return selectedElement.getOpposite();
		}
		return selectedElement;
	}

	@Override
	public void execute() {
		Resource resource = null;
		List<ConnData> resourceConns = null;

		if (oldElement.isMapped()) {
			resource = oldElement.getResource();
			resourceConns = getResourceCons();
			unmapCmd = new UnmapCommand(oldElement);
			unmapCmd.execute();
		}

		createNewFB();

		checkGroup(oldElement, newElement); // needs to be done before anything is changed on the old element Bug
		// 579570
		oldIndex = network.getNetworkElements().indexOf(oldElement);
		network.getNetworkElements().add(oldIndex, newElement);

		// handle data, attributes, and connections of all pins
		oldElement.getInterface().getAllInterfaceElements().forEach(this::handlePin);

		reconnCmds.execute();

		// set Visible attribute after reconnect, to not hide connected In/Outputs
		// transfer attributes from type first (for new vars), then override them from
		// old instance
		final InterfaceList typeInterface = newElement.getTypeInterface();
		if (typeInterface != null) {
			transferVisibleAndVarConfigAttributes(typeInterface.getInputVars());
			transferVisibleAndVarConfigAttributes(typeInterface.getOutputVars());
			transferVisibleAndVarConfigAttributes(typeInterface.getInOutVars());
		}

		transferVisibleAndVarConfigAttributes(oldElement.getInterface().getInputVars());
		transferVisibleAndVarConfigAttributes(oldElement.getInterface().getOutputVars());
		transferVisibleAndVarConfigAttributes(oldElement.getInterface().getInOutVars());

		network.getNetworkElements().remove(oldElement);

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
		if (newElement instanceof final ConfigurableFB configFb) {
			if (oldElement instanceof final ConfigurableFB oldConfigFb) {
				configFb.setDataType(reloadDataType(oldConfigFb.getDataType()));
				configFb.updateConfiguration();
			} else {
				// transfer data from error marker
				handleConFBUpdateFromErrorMarker(configFb);
			}
		}
	}

	protected final DataType reloadDataType(final DataType dataType) {
		if (dataType == null) {
			return getAnyType();
		}

		if (dataType.getTypeEntry() != null) {
			// if we are a user defined type ensure to get the latest version from the file
			return (dataType.getTypeEntry().getType() instanceof final DataType dt) ? dt : getAnyType();

		}
		return dataType;
	}

	private DataType getAnyType() {
		return (getOldElement() instanceof StructManipulator) ? IecTypes.GenericTypes.ANY_STRUCT
				: IecTypes.GenericTypes.ANY;
	}

	private void handleConFBUpdateFromErrorMarker(final ConfigurableFB configFb) {
		if (configFb instanceof ConfigurableMoveFB) {
			final String dataTypeName = oldElement.getAttributeValue(LibraryElementTags.F_MOVE_CONFIG);
			if (dataTypeName != null) {
				configFb.loadConfiguration(LibraryElementTags.F_MOVE_CONFIG, dataTypeName);
				// make sure we don't copy the attribute into the attributeList
				oldElement.deleteAttribute(LibraryElementTags.F_MOVE_CONFIG);
			}
		} else {
			// we are a struct muxer
			final String dataTypeName = oldElement.getAttributeValue(LibraryElementTags.STRUCT_MANIPULATOR_CONFIG);
			if (dataTypeName != null) {
				configFb.loadConfiguration(LibraryElementTags.STRUCT_MANIPULATOR_CONFIG, dataTypeName);
				oldElement.deleteAttribute(LibraryElementTags.STRUCT_MANIPULATOR_CONFIG);
			}
		}
	}

	private void handleMemberAccessPins() {
		if (!Objects.equals(getOldElement().getTypeEntry(), getNewElement().getTypeEntry())) {
			// if the types are not equal checking member access pins does not make sense
			return;
		}

		final InterfaceList newInterface = getNewElement().getInterface();
		// for each member access pin create the according pin in the new element and
		// set it to visible, attributes for comments will be handled in the respective
		// update methods
		getOldElement().getInterface().getAllInterfaceElements() //
				.filter(ie -> ie.isVisible() && ie.isMemberAccessPin())
				.map(ie -> newInterface.getInterfaceElement(ie.getBlockRelativePath(), true))
				.filter(ie -> ie != null && ie.isMemberAccessPin()) //
				.forEach(ie -> ie.setVisible(true));
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
		InterfaceList typeInterface = newElement.getTypeInterface();
		if (newElement instanceof final AdapterFB adapterFB
				&& adapterFB.getTypeEntry() instanceof final AdapterTypeEntry adapterTypeEntry) {
			final AdapterType adpType = adapterTypeEntry.getType();
			typeInterface = (adapterFB.isPlug() ? adpType.getPlugType().getInterfaceList()
					: adpType.getInterfaceList());
		}
		if (typeInterface != null) {
			newElement.setInterface(typeInterface.instanceCopy());
		} else {
			newElement.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		}
	}

	private void transferVisibleAndVarConfigAttributes(final EList<VarDeclaration> varDeclList) {
		varDeclList.forEach(varDecl -> {
			if (newElement.getInterface().getInterfaceElement(varDecl) instanceof final VarDeclaration newDecl) {
				if ((newDecl.isIsInput() && newDecl.getInputConnections().isEmpty())
						|| (!newDecl.isIsInput() && newDecl.getOutputConnections().isEmpty())) {
					newDecl.setVisible(varDecl.isVisible());
				}
				if (newDecl.isInOutVar()) {
					newDecl.setVisible(varDecl.isVisible());
					newDecl.getInOutVarOpposite().setVisible(varDecl.getInOutVarOpposite().isVisible());
				}
				newDecl.setVarConfig(varDecl.isVarConfig());
			}
		});
	}

	private void recreateResourceConns(final List<ConnData> resourceConns) {
		final BlockFBNetworkElement orgMappedElement = (BlockFBNetworkElement) unmapCmd.getMappedFBNetworkElement();
		final BlockFBNetworkElement copiedMappedElement = newElement.getOpposite();
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

	private static IInterfaceElement findUpdatedInterfaceElement(final BlockFBNetworkElement newElement,
			final BlockFBNetworkElement oldElement, final IInterfaceElement oldInterface) {
		if ((oldInterface != null) && (oldInterface.getBlockFBNetworkElement() == oldElement)) {
			// origView is an interface of the original FB => find same interface on copied
			// FB
			return getNewInterfaceElement(oldInterface, newElement);
		}
		return oldInterface;
	}

	private static Stream<Connection> getAllConnections(final BlockFBNetworkElement element) {
		return element.getInterface().getAllInterfaceElements().flatMap(
				ifEl -> ifEl.isIsInput() ? ifEl.getInputConnections().stream() : ifEl.getOutputConnections().stream())
				.distinct(); // distinct filters duplicated connection objects originating from self loops

	}

	private List<ConnData> getResourceCons() {
		final List<ConnData> retVal = new ArrayList<>();
		final BlockFBNetworkElement resElement = oldElement.getOpposite();

		getAllConnections(resElement).forEach(conn -> {
			final IInterfaceElement source = conn.getSource();
			final IInterfaceElement dest = conn.getDestination();
			if (!source.getBlockFBNetworkElement().isMapped() || !dest.getBlockFBNetworkElement().isMapped()) {
				// one of both ends is a resourceFB therefore the connection needs to be
				// restored
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			} else if (((source.getBlockFBNetworkElement() == resElement)
					&& (dest.getBlockFBNetworkElement().getOpposite().getFbNetwork() != oldElement.getFbNetwork()))
					|| ((dest.getBlockFBNetworkElement() == resElement) && (source.getBlockFBNetworkElement()
							.getOpposite().getFbNetwork() != oldElement.getFbNetwork()))) {
				// one of both ends is a FB coming from a different fb network and therefore
				// this is also a resource specific connection
				retVal.add(new ConnData(conn.getSource(), conn.getDestination()));
			}
		});
		return retVal;
	}

	private void handlePin(final IInterfaceElement oldPin) {
		IInterfaceElement newPin = null;
		// check attributes
		if (!oldPin.getAttributes().isEmpty() && hasNonInternalAttr(oldPin)) {
			newPin = getNewInterfaceElement(oldPin, newElement);
			copyNonInternalAttributes(oldPin, newPin);
		}

		// comment
		if (!oldPin.getComment().isBlank()) {
			if (newPin == null) {
				newPin = getNewInterfaceElement(oldPin, newElement);
			}
			newPin.setComment(oldPin.getComment());
		}

		// values
		final String value = getPinValue(oldPin);
		if (value != null && !value.isBlank()) {
			if (newPin == null) {
				newPin = getNewInterfaceElement(oldPin, newElement);
			}
			setPinValue(newPin, value);
		}

		// check connections
		getConnectionsFromPin(oldPin).forEach(this::handleConnection);
	}

	private static boolean hasNonInternalAttr(final IInterfaceElement ie) {
		return ie.getAttributes().stream().anyMatch(Predicate.not(InternalAttributeDeclarations::isInternalAttribute));
	}

	private static void copyNonInternalAttributes(final ConfigurableObject source,
			final ConfigurableObject destination) {
		destination.getAttributes().addAll(EcoreUtil.copyAll(source.getAttributes().stream()
				.filter(attribute -> !InternalAttributeDeclarations.isInternalAttribute(attribute)).toList()));
	}

	private static String getPinValue(final IInterfaceElement pin) {
		final Value value = switch (pin) {
		case final VarDeclaration varDecl -> varDecl.getValue();
		case final ErrorMarkerInterface error -> error.getValue();
		case null, default -> null;
		};
		return (value != null) ? value.getValue() : null;
	}

	private static void setPinValue(final IInterfaceElement pin, final String newValue) {
		final Value value = switch (pin) {
		case final VarDeclaration varDecl -> {
			Value v = varDecl.getValue();
			if (v == null) {
				v = LibraryElementFactory.eINSTANCE.createValue();
				varDecl.setValue(v);
			}
			yield v;
		}
		case final ErrorMarkerInterface error -> {
			Value v = error.getValue();
			if (v == null) {
				v = LibraryElementFactory.eINSTANCE.createValue();
				error.setValue(v);
			}
			yield v;
		}
		case null, default -> {
			FordiacLogHelper.logWarning("Update FB wants to set a value to a non value holding interface element!"); //$NON-NLS-1$
			yield null;
		}
		};
		if (value != null) {
			value.setValue(newValue);
		}
	}

	private Stream<Connection> getConnectionsFromPin(final IInterfaceElement oldPin) {
		return oldPin.isIsInput() ? oldPin.getInputConnections().stream()
				// remove self loops from the input side to avoid handling a connection twice
				: oldPin.getOutputConnections().stream().filter(con -> con.getDestinationElement() != oldElement);
	}

	private static IInterfaceElement getNewInterfaceElement(final IInterfaceElement oldIE,
			final BlockFBNetworkElement newElement) {
		final List<String> blockRelativePath = (oldIE instanceof ErrorMarkerInterface)
				? Arrays.asList(oldIE.getName().split("\\.")) //$NON-NLS-1$
				: oldIE.getBlockRelativePath();
		IInterfaceElement updatedSelected = newElement.getInterface().getInterfaceElement(blockRelativePath, true);
		if (updatedSelected instanceof final VarDeclaration varDecl && varDecl.isInOutVar()
				&& updatedSelected.isIsInput() != oldIE.isIsInput()) {
			updatedSelected = varDecl.getInOutVarOpposite();
		}
		if (updatedSelected == null || updatedSelected.isIsInput() != oldIE.isIsInput()) {
			// check if we can get an error marker for the element with the full name
			updatedSelected = newElement.getInterface()
					.getInterfaceElement(List.of(oldIE.getRelativeName(oldIE.getBlockFBNetworkElement())));
			if (updatedSelected == null || updatedSelected.isIsInput() != oldIE.isIsInput()) {
				// create an error marker to serve as target
				updatedSelected = createMissingMarker(oldIE, newElement);
			}
		}
		if (updatedSelected instanceof VarDeclaration && updatedSelected.isMemberAccessPin()
				&& !updatedSelected.isVisible() && oldIE.isVisible()) {
			updatedSelected.setVisible(true);
		}
		return updatedSelected;
	}

	private static ErrorMarkerInterface createMissingMarker(final IInterfaceElement oldInterface,
			final BlockFBNetworkElement element) {
		return FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterface(oldInterface.getType(),
				oldInterface.getRelativeName(oldInterface.getBlockFBNetworkElement()), oldInterface.isIsInput(),
				element.getInterface());
	}

	private void handleConnection(final Connection connection) {
		var source = connection.getSource();
		var destination = connection.getDestination();

		// get or create pins for new element (source and/or destination)
		if (connection.getSourceElement() == oldElement) {
			source = getNewInterfaceElement(source, newElement);
		}
		if (connection.getDestinationElement() == oldElement) {
			destination = getNewInterfaceElement(destination, newElement);
		}

		// reconnect/replace connection, we can not use AbstractReconnectCommand as
		// source and destination could be our block
		replaceConnection(connection, source, destination);
	}

	private void replaceConnection(final Connection oldConn, final IInterfaceElement source,
			final IInterfaceElement dest) {
		reconnCmds.add(new DeleteConnectionCommand(oldConn, true));

		final FBNetwork fbn = oldConn.getFBNetwork();
		final AbstractConnectionCreateCommand cmd = createConnectionCreateCommand(fbn, source.getType());
		// when changing this list also update the list in
		// AbstractReconnectCommand::getCreateConnectionCommand
		cmd.setSource(source);
		cmd.setDestination(dest);
		cmd.setAttributes(oldConn.getAttributes());
		cmd.setArrangementConstraints(oldConn.getRoutingData());
		cmd.setElementIndex(fbn.getConnectionIndex(oldConn));
		reconnCmds.add(cmd);
	}

	private static AbstractConnectionCreateCommand createConnectionCreateCommand(final FBNetwork fbn,
			final DataType type) {
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
		handleMemberAccessPins();
		newElement.setName(oldElement.getName());
		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));
		if (newElement instanceof final TypedSubApp newTsa && oldElement instanceof final TypedSubApp oldTsa
				&& oldElement.getTypeEntry() == entry) {
			newTsa.getVarConfigParams().addAll(EcoreUtil.copyAll(oldTsa.getVarConfigParams()));
		}
		newElement.getAttributes().addAll(EcoreUtil.copyAll(oldElement.getAttributes()));
	}

	protected abstract BlockFBNetworkElement createCopiedFBEntry(final BlockFBNetworkElement srcElement);

	public BlockFBNetworkElement getOldElement() {
		return oldElement;
	}

	public BlockFBNetworkElement getNewElement() {
		return newElement;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(network);
	}
}
