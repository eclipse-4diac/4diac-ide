/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - fix positioning of fbnetwork
 *   Lukas Wais - implemented unique naming for FBs
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.QualNameAffectedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class FlattenSubAppCommand extends Command implements QualNameAffectedCommand {
	private final SubApp subapp;
	private final FBNetwork parentNetwork;
	private final List<FBNetworkElement> elements = new ArrayList<>();
	private final List<FBNetworkElement> elementsToMove = new ArrayList<>();
	private final List<EventConnection> transferEventConnections = new ArrayList<>();
	private final List<DataConnection> transferDataConnections = new ArrayList<>();
	private final List<AdapterConnection> transferAdapterConnections = new ArrayList<>();
	private final CompoundCommand deleteCommands = new CompoundCommand();
	private final CompoundCommand createCommands = new CompoundCommand();
	private final CompoundCommand mapCommands = new CompoundCommand();
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private final Position fbnetworkPosInSubapp;
	private boolean select = true;
	private final Map<INamedElement, String> oldQualNames = new HashMap<>();
	private final Map<INamedElement, String> newQualNames = new HashMap<>();

	public FlattenSubAppCommand(final SubApp subapp) {
		super(Messages.FlattenSubAppCommand_LABEL_FlattenSubAppCommand);
		this.subapp = Objects.requireNonNull(subapp);
		parentNetwork = Objects.requireNonNull(subapp.getFbNetwork());
		fbnetworkPosInSubapp = FBNetworkHelper
				.getTopLeftCornerOfFBNetwork(subapp.getSubAppNetwork().getNetworkElements());
	}

	public FlattenSubAppCommand(final SubApp subapp, final boolean select) {
		this(subapp);
		this.select = select;
	}

	@Override
	public void execute() {
		elements.addAll(subapp.getSubAppNetwork().getNetworkElements());
		elements.forEach(e -> oldQualNames.put(e, e.getQualifiedName()));
		elementsToMove.addAll(elements.stream().filter(el -> !el.isInGroup()).toList());
		// add elements to parent
		FBNetworkHelper.moveFBNetworkByOffset(elementsToMove, -getOriginalPositionX(), -getOriginalPositionY());

		checkConnections();
		createMapCommands();

		deleteCommands.add(new DeleteFBNetworkElementCommand(subapp));
		deleteCommands.execute();

		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parentNetwork.getNetworkElements().addAll(elements);

		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parentNetwork.getEventConnections().addAll(transferEventConnections);

		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parentNetwork.getDataConnections().addAll(transferDataConnections);

		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parentNetwork.getAdapterConnections().addAll(transferAdapterConnections);

		createCommands.execute();
		mapCommands.execute();

		// check unique names
		for (final FBNetworkElement fbNetworkElement : elements) {
			ensureUniqueName(fbNetworkElement);
		}

		if (select) {
			ElementSelector.selectViewObjects(elementsToMove);
		}
		elements.forEach(e -> newQualNames.put(e, e.getQualifiedName()));
	}

	private void ensureUniqueName(final FBNetworkElement element) {
		// ensure unique name in new network
		if (!NameRepository.isValidName(element, element.getName())) {
			final String uniqueName = NameRepository.createUniqueName(element, element.getName());
			final ChangeNameCommand uniqueNameCmd = ChangeNameCommand.forName(element, uniqueName);
			uniqueNameCmd.execute();
			setUniqueName.add(uniqueNameCmd);
		}
	}

	@Override
	public void redo() {
		deleteCommands.redo();
		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parentNetwork.getNetworkElements().addAll(elements);

		FBNetworkHelper.moveFBNetworkByOffset(elementsToMove, -getOriginalPositionX(), -getOriginalPositionY());

		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parentNetwork.getEventConnections().addAll(transferEventConnections);

		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parentNetwork.getDataConnections().addAll(transferDataConnections);

		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parentNetwork.getAdapterConnections().addAll(transferAdapterConnections);

		createCommands.redo();
		setUniqueName.redo();
		mapCommands.redo();

		if (select) {
			ElementSelector.selectViewObjects(elementsToMove);
		}
	}

	@Override
	public void undo() {
		mapCommands.undo();
		createCommands.undo();
		parentNetwork.getNetworkElements().removeAll(elements);
		subapp.getSubAppNetwork().getNetworkElements().addAll(elements);
		FBNetworkHelper.moveFBNetworkByOffset(elementsToMove, getOriginalPositionX(), getOriginalPositionY());

		parentNetwork.getEventConnections().removeAll(transferEventConnections);
		subapp.getSubAppNetwork().getEventConnections().addAll(transferEventConnections);

		parentNetwork.getDataConnections().removeAll(transferDataConnections);
		subapp.getSubAppNetwork().getDataConnections().addAll(transferDataConnections);

		parentNetwork.getAdapterConnections().removeAll(transferAdapterConnections);
		subapp.getSubAppNetwork().getAdapterConnections().addAll(transferAdapterConnections);

		setUniqueName.undo();
		deleteCommands.undo();

		if (select) {
			ElementSelector.selectViewObjects(Arrays.asList(subapp));
		}
	}

	private void checkConnections() {
		checkConnectionList(subapp.getSubAppNetwork().getEventConnections(), transferEventConnections);
		checkConnectionList(subapp.getSubAppNetwork().getDataConnections(), transferDataConnections);
		checkConnectionList(subapp.getSubAppNetwork().getAdapterConnections(), transferAdapterConnections);

	}

	private double getOriginalPositionX() {
		return -subapp.getPosition().getX() + fbnetworkPosInSubapp.getX();
	}

	private double getOriginalPositionY() {
		return -subapp.getPosition().getY() + fbnetworkPosInSubapp.getY();
	}

	private void createMapCommands() {
		if (subapp.isMapped()) {
			for (final FBNetworkElement fbNetworkElement : elements) {
				mapCommands.add(MapToCommand.createMapToCommand(fbNetworkElement, subapp.getResource()));
			}
		}
	}

	private <T extends Connection> void checkConnectionList(final List<T> connectionList,
			final List<T> transferConnectionList) {
		for (final T connection : connectionList) {
			if ((connection.getSourceElement() != subapp) && (connection.getDestinationElement() != subapp)) {
				// it is an internal connection transfer it
				transferConnectionList.add(connection);
			} else {
				deleteCommands.add(new DeleteConnectionCommand(connection));
				createCommandsFrom(connection);
			}
		}
	}

	private <T extends Connection> void createCommandsFrom(final T connection) {
		if ((connection.getSourceElement() == subapp) && (connection.getDestinationElement() == subapp)) {
			for (final Connection inboundConn : connection.getSource().getInputConnections()) {
				for (final Connection outboundConn : connection.getDestination().getOutputConnections()) {
					createCommands.add(createConnCreateCmd(inboundConn.getSource(), outboundConn.getDestination()));
				}
			}
		} else if (connection.getSourceElement() == subapp) {
			// for each connection coming into the sub app create one connection in the
			// outer fb network
			for (final Connection inboundConn : connection.getSource().getInputConnections()) {
				createCommands.add(createConnCreateCmd(inboundConn.getSource(), connection.getDestination()));
			}
		} else if (connection.getDestinationElement() == subapp) {
			// for each connection going from the subapp outputs create one connection in
			// the other fb network
			for (final Connection outboundConn : connection.getDestination().getOutputConnections()) {
				createCommands.add(createConnCreateCmd(connection.getSource(), outboundConn.getDestination()));
			}

		}
	}

	private AbstractConnectionCreateCommand createConnCreateCmd(final IInterfaceElement source,
			final IInterfaceElement destination) {
		AbstractConnectionCreateCommand cmd = null;
		if (source instanceof Event) {
			cmd = new EventConnectionCreateCommand(parentNetwork);
		} else if (source instanceof AdapterDeclaration) {
			cmd = new AdapterConnectionCreateCommand(parentNetwork);
		} else if (source instanceof VarDeclaration) {
			cmd = new DataConnectionCreateCommand(parentNetwork);
		}

		if (null != cmd) {
			cmd.setSource(source);
			cmd.setDestination(destination);
		}
		return cmd;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(parentNetwork);
	}

	@Override
	public String getOldQualName(final INamedElement element) {
		return oldQualNames.get(element);
	}

	@Override
	public String getNewQualName(final INamedElement element) {
		return newQualNames.get(element);
	}

	@Override
	public List<INamedElement> getChangedElements() {
		return new ArrayList<>(elements);
	}
}
