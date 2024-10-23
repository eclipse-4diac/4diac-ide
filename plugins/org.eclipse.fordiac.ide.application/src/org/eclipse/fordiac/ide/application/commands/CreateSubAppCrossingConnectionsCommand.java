/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - implemented special cases, handle existing pins gracefully
 *   Fabio Gandolfi
 *     - reuse connections if already exists between fb and subapp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class CreateSubAppCrossingConnectionsCommand extends Command implements ScopedCommand {

	private final IInterfaceElement source;
	private final IInterfaceElement destination;
	private final FBNetwork match;
	private final List<FBNetwork> sourceNetworks;
	private final List<FBNetwork> destinationNetworks;

	private final List<Command> commands = new ArrayList<>();

	protected CreateSubAppCrossingConnectionsCommand(final IInterfaceElement source,
			final IInterfaceElement destination, final List<FBNetwork> sourceNetworks,
			final List<FBNetwork> destinationNetworks, final FBNetwork match) {
		this.source = Objects.requireNonNull(source);
		this.destination = Objects.requireNonNull(destination);
		this.sourceNetworks = Objects.requireNonNull(sourceNetworks);
		this.destinationNetworks = Objects.requireNonNull(destinationNetworks);
		this.match = Objects.requireNonNull(match);
	}

	public static Command createProcessBorderCrossingConnection(final IInterfaceElement source,
			final IInterfaceElement destination) {
		Objects.requireNonNull(source);
		Objects.requireNonNull(destination);
		final List<FBNetwork> sourceNetworks = buildHierarchy(source);
		final List<FBNetwork> destinationNetworks = buildHierarchy(destination);
		final FBNetwork match = findMostSpecificMatch(source, destination, sourceNetworks, destinationNetworks);
		if (isSwapNeeded(source, destination, sourceNetworks, destinationNetworks)) {
			return new CreateSubAppCrossingConnectionsCommand(destination, source, destinationNetworks, sourceNetworks,
					match);
		}
		return new CreateSubAppCrossingConnectionsCommand(source, destination, sourceNetworks, destinationNetworks,
				match);
	}

	@Override
	public boolean canExecute() {
		// as not all checks of the createconnection command are valid we need to do our
		// own checks

		// equal types for source and dest
		if (!source.getClass().isAssignableFrom(destination.getClass())
				&& !destination.getClass().isAssignableFrom(source.getClass())) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		// source and dest check
		if (!LinkConstraints.isValidConnSource(source, sourceNetworks.get(0))
				|| !LinkConstraints.isValidConnDestination(destination, destinationNetworks.get(0))) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (source instanceof Event) {
			// the only event specific connection we currently have is duplicate connection
			// which makes no sense here so
			// we can return true
			return true;
		}

		if (source instanceof VarDeclaration) {
			return dataConnectionChecks();
		}

		if (source instanceof AdapterDeclaration) {
			return adapterConnectionChecks();
		}

		return false;
	}

	@Override
	public void execute() {
		final DataType commonType = resolveGenericDataType();

		final IInterfaceElement left = buildPath(source, sourceNetworks, destination, false,
				commonType != null ? commonType : source.getType());
		final IInterfaceElement right = buildPath(destination, destinationNetworks, source, true,
				commonType != null ? commonType : destination.getType());
		createConnection(match, left, right);
	}

	private DataType resolveGenericDataType() {
		if (IecTypes.GenericTypes.isAnyType(source.getType())
				&& !IecTypes.GenericTypes.isAnyType(destination.getType())) {
			return destination.getType();
		}

		if (!IecTypes.GenericTypes.isAnyType(source.getType())
				&& IecTypes.GenericTypes.isAnyType(destination.getType())) {
			return source.getType();
		}

		return null;
	}

	private static boolean isSwapNeeded(final IInterfaceElement source, final IInterfaceElement destination,
			final List<FBNetwork> sourceNetworks, final List<FBNetwork> destinationNetworks) {
		final boolean sourceIsInput = isInputElement(source, sourceNetworks);
		final boolean destinationIsInput = isInputElement(destination, destinationNetworks);
		return sourceIsInput && !destinationIsInput;
	}

	private static boolean isInputElement(final IInterfaceElement iel, final List<FBNetwork> networkList) {
		if (iel.getFBNetworkElement() instanceof final SubApp subapp) {
			final FBNetwork search = subapp.getSubAppNetwork();
			if (networkList.get(0) == search) {
				return !iel.isIsInput();
			}
		}
		return iel.isIsInput();
	}

	private static FBNetwork findMostSpecificMatch(final IInterfaceElement source, final IInterfaceElement destination,
			final List<FBNetwork> sourceNetworks, final List<FBNetwork> destinationNetworks) {
		final FBNetwork sourceSubAppNetwork = addSubAppNetworkToList(source, sourceNetworks);
		final FBNetwork destSubAppNetwork = addSubAppNetworkToList(destination, destinationNetworks);

		int sourceIndex = sourceNetworks.size() - 1;
		int destinationIndex = destinationNetworks.size() - 1;
		FBNetwork match = sourceNetworks.get(0);

		// breaks when the networks don't match anymore
		while ((sourceIndex >= 0) && (destinationIndex >= 0)
				&& (sourceNetworks.get(sourceIndex) == destinationNetworks.get(destinationIndex))) {
			match = sourceNetworks.get(sourceIndex);
			sourceIndex--;
			destinationIndex--;
		}

		checkIfSubAppNetworkIsNeeded(sourceNetworks, sourceSubAppNetwork, match);
		checkIfSubAppNetworkIsNeeded(destinationNetworks, destSubAppNetwork, match);
		return match;
	}

	private static FBNetwork addSubAppNetworkToList(final IInterfaceElement ie, final List<FBNetwork> networkList) {
		FBNetwork subAppNetwork = null;
		if (ie.getFBNetworkElement() instanceof final SubApp subApp && !subApp.isTyped()) {
			subAppNetwork = subApp.getSubAppNetwork();
			networkList.add(0, subAppNetwork);
		}
		return subAppNetwork;
	}

	private static void checkIfSubAppNetworkIsNeeded(final List<FBNetwork> networkList,
			final FBNetwork addedSubappNetwork, final FBNetwork match) {
		if ((addedSubappNetwork != null) && (match != networkList.get(0))) {
			networkList.remove(addedSubappNetwork);
		}
	}

	private static List<FBNetwork> buildHierarchy(final IInterfaceElement source) {
		final List<FBNetwork> list = new ArrayList<>();
		EObject current = source.eContainer();
		while (current != null) {
			if (current instanceof final FBNetwork currentFbNetwork) {
				list.add(currentFbNetwork);
			}
			if (current instanceof final SubAppType satype) {
				list.add(satype.getFBNetwork());
			}
			current = current.eContainer();
		}
		return list;
	}

	// build left or right path as seen from the matching network
	private IInterfaceElement buildPath(final IInterfaceElement element, final List<FBNetwork> networks,
			final IInterfaceElement oppositePin, final boolean isRightPath, final DataType commonType) {
		IInterfaceElement ie = element;
		FBNetwork network = networks.get(0);
		int i = 0;

		while (network != match) {
			final SubApp subapp = (SubApp) network.eContainer();

			final IInterfaceElement existingConnection = existingConnection(ie, subapp, oppositePin, isRightPath);

			if (existingConnection != null) {
				ie = existingConnection;
			} else {
				final IInterfaceElement createdPin = createInterfaceElement(isRightPath, ie, subapp, commonType);

				if (isRightPath) {
					createConnection(network, createdPin, ie);
				} else {
					createConnection(network, ie, createdPin);
				}

				ie = createdPin;
			}

			i++;
			network = networks.get(i);
		}
		return ie;
	}

	private IInterfaceElement existingConnection(final IInterfaceElement ie, final SubApp subapp,
			final IInterfaceElement oppositePin, final boolean isRightPath) {
		if (isRightPath) {
			final Optional<Connection> connection = ie.getInputConnections().stream()
					.filter(con -> con.getSourceElement().equals(subapp) && compatibleTypes(ie, con.getSource()))
					.findFirst();

			if (connection.isPresent()) {
				return connection.get().getSource();
			}

		} else {
			final Optional<Connection> connection = ie.getOutputConnections().stream().filter(
					con -> con.getDestinationElement().equals(subapp) && compatibleTypes(ie, con.getDestination()))
					.findFirst();

			if (connection.isPresent()) {
				return connection.get().getDestination();
			}
		}

		return existingSubAppPin(ie, oppositePin, isRightPath);
	}

	IInterfaceElement existingSubAppPin(final IInterfaceElement ie, final IInterfaceElement oppositePin,
			final boolean isRightPath) {
		final Optional<IInterfaceElement> subappPin;
		if (isRightPath) {

			if (ie.getFBNetworkElement() != null && ie.getFBNetworkElement().getOuterFBNetworkElement() != null) {

				if (ie instanceof Event) {
					subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getEventInputs()
							.stream().filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
							.map(IInterfaceElement.class::cast);
				} else if (ie instanceof final VarDeclaration varDecl && varDecl.isInOutVar()) {
					subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getInOutVars()
							.stream().filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
							.map(IInterfaceElement.class::cast);
				} else {
					subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getInputVars()
							.stream().filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
							.map(IInterfaceElement.class::cast);
				}

				if (subappPin.isPresent()) {
					createConnection(ie.getFBNetworkElement().getFbNetwork(), subappPin.get(), ie);
					return subappPin.get();
				}
			}

		} else if (ie.getFBNetworkElement() != null && ie.getFBNetworkElement().getOuterFBNetworkElement() != null) {

			if (ie instanceof Event) {
				subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getEventOutputs()
						.stream().filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
						.map(IInterfaceElement.class::cast);
			} else if (ie instanceof final VarDeclaration varDecl && varDecl.isInOutVar()) {
				subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getOutMappedInOutVars()
						.stream().filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
						.map(IInterfaceElement.class::cast);
			} else {
				subappPin = ie.getFBNetworkElement().getOuterFBNetworkElement().getInterface().getOutputVars().stream()
						.filter(pin -> isSourceTypeMatching(ie, pin, oppositePin, isRightPath)).findFirst()
						.map(IInterfaceElement.class::cast);
			}

			if (subappPin.isPresent()) {
				createConnection(ie.getFBNetworkElement().getFbNetwork(), subappPin.get(), ie);
				return subappPin.get();
			}
		}
		return null;
	}

	boolean isSourceTypeMatching(final IInterfaceElement ie, final IInterfaceElement subAppPin,
			final IInterfaceElement oppositePin, final boolean isRightPath) {
		if (isRightPath) {
			return !subAppPin.getInputConnections().stream()
					.filter(p -> getConnectionSourceFBInterfaceElements(p, isRightPath).contains(oppositePin)).findAny()
					.isEmpty();
		}
		return !subAppPin.getOutputConnections().stream()
				.filter(p -> getConnectionSourceFBInterfaceElements(p, isRightPath).contains(oppositePin)).findAny()
				.isEmpty();
	}

	List<IInterfaceElement> getConnectionSourceFBInterfaceElements(final Connection con, final boolean isRightPath) {

		final List<IInterfaceElement> list = new ArrayList<>();

		if (isRightPath) {
			if (!(con.getSourceElement() instanceof FB)) {
				con.getSource().getInputConnections()
						.forEach(s -> list.addAll(getConnectionSourceFBInterfaceElements(s, isRightPath)));
				return list;
			}
			list.add(con.getSource());
			return list;
		}

		if (!(con.getSourceElement() instanceof FB)) {
			con.getDestination().getOutputConnections()
					.forEach(s -> list.addAll(getConnectionSourceFBInterfaceElements(s, isRightPath)));
			return list;
		}
		list.add(con.getDestination());
		return list;
	}

	private IInterfaceElement createInterfaceElement(final boolean isRightPath, final IInterfaceElement ie,
			final SubApp subapp, final DataType commonType) {

		if (emptyPinAlreadyExists(subapp, ie, isRightPath)) {
			return subapp.getInterfaceElement(ie.getName());
		}

		final boolean isInOut = source instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar();

		final CreateSubAppInterfaceElementCommand pinCmd = new CreateSubAppInterfaceElementCommand(commonType,
				source.getName(), subapp.getInterface(), isRightPath, isInOut, ArraySizeHelper.getArraySize(source),
				-1);

		pinCmd.execute();
		commands.add(pinCmd);
		return pinCmd.getCreatedElement();
	}

	private boolean emptyPinAlreadyExists(final SubApp subapp, final IInterfaceElement ie, final boolean isRightPath) {
		final IInterfaceElement pin = subapp.getInterfaceElement(ie.getName());
		if ((pin != null) && pin.getInputConnections().isEmpty() && pin.getOutputConnections().isEmpty()
				&& compatibleTypes(pin)) {
			return (pin.isIsInput() && isRightPath) || (!pin.isIsInput() && !isRightPath);
		}
		return false;
	}

	private boolean compatibleTypes(final IInterfaceElement pin) {
		return compatibleTypes(source, pin);
	}

	private static boolean compatibleTypes(final IInterfaceElement pin1, final IInterfaceElement pin2) {
		if (pin1.getType() == null || pin2.getType() == null) {
			return false;
		}
		return LinkConstraints.typeCheck(pin1, pin2);
	}

	private void createConnection(final FBNetwork network, final IInterfaceElement connSource,
			final IInterfaceElement connDestination) {
		final AbstractConnectionCreateCommand connCmd = AbstractConnectionCreateCommand.createCommand(network,
				connSource, connDestination);
		connCmd.setSource(connSource);
		connCmd.setDestination(connDestination);
		connCmd.setVisible(connShouldBeVisible(network, connSource, connDestination));
		if (connCmd.canExecute()) {
			connCmd.execute();
			commands.add(connCmd);
		}
	}

	@Override
	public void undo() {
		// iterate backwards
		final ListIterator<Command> iterator = commands.listIterator(commands.size());
		while (iterator.hasPrevious()) {
			iterator.previous().undo();
		}
	}

	@Override
	public void redo() {
		commands.forEach(Command::redo);
	}

	private boolean dataConnectionChecks() {
		if (!LinkConstraints.hasAlreadyInputConnectionsCheck(source, destination, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, destination.getName()));
			return false;
		}

		if (!(source.getType() instanceof StructuredType && destination.getType() instanceof StructuredType)
				&& (!LinkConstraints.typeCheck(source, destination))) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.NA,
					(null != destination.getType()) ? destination.getType().getName() : FordiacMessages.NA));
			return false;

		}

		return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(destination);
	}

	private boolean adapterConnectionChecks() {
		if (!LinkConstraints.hasAlreadyInputConnectionsCheck(source, destination, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, destination.getName()));
			return false;
		}

		if (LinkConstraints.hasAlreadyOutputConnectionsCheck(source, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
			return false;
		}

		if (!LinkConstraints.adapaterTypeCompatibilityCheck(source, destination)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.ND,
					(null != destination.getType()) ? destination.getType().getName() : FordiacMessages.ND));
			return false;
		}

		return true;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		final Set<ScopedCommand> scopedCommands = commands.stream().filter(ScopedCommand.class::isInstance)
				.map(c -> (ScopedCommand) c).collect(Collectors.toSet());
		final Set<EObject> se = new HashSet<>();
		scopedCommands.forEach(c -> se.addAll(c.getAffectedObjects()));
		return se;

	}

	private static boolean connShouldBeVisible(final FBNetwork parentNW, final IInterfaceElement src,
			final IInterfaceElement dst) {
		final boolean srcHidden = (src.getFBNetworkElement() instanceof final SubApp srcSubapp)
				&& srcSubapp.isUnfolded() && parentNW == srcSubapp.getFbNetwork();
		final boolean dstHidden = (dst.getFBNetworkElement() instanceof final SubApp dstSubapp)
				&& dstSubapp.isUnfolded() && parentNW == dstSubapp.getFbNetwork();
		return !srcHidden && !dstHidden;
	}

}
