/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians
 *   - basic fix to get plain VARINOUT FBs mapped correctly
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.dataimport.MappingTargetCreator;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jdt.annotation.NonNull;

public class MapToCommand extends Command implements ScopedCommand {
	protected final FBNetworkElement srcElement;
	private final MappingTarget resource;
	private UnmapCommand unmapFromExistingTarget;
	protected FBNetworkElement targetElement;
	private final Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
	private final CompoundCommand createdConnections = new CompoundCommand();
	private int elementIndex = -1;

	protected MapToCommand(final FBNetworkElement srcElement, final MappingTarget resource) {
		this.srcElement = srcElement;
		this.resource = resource;
	}

	@Override
	public boolean canExecute() {
		if (srcElement == null || resource == null) {
			return false;
		}
		if ((srcElement.isMapped()) && (srcElement.getOpposite().getFbNetwork().equals(getTargetFBNetwork()))) {
			ErrorMessenger.popUpErrorMessage(Messages.MapToCommand_STATUSMessage_AlreadyMapped);
			return false; // already mapped to this resource -> nothing to do -> mapping not possible!
		}
		return true;
	}

	/**
	 * Steps needed for the mapping command: 1. If already mapped create unmap
	 * command and execute it 2. Create FB in target FBNetwork (use FBnetwork create
	 * command) 3. Create Mapping entry 4. Determine list of connections that need
	 * to be created in target FBNetwork 5. Execute the create connection command
	 * for these
	 */
	@Override
	public void execute() {
		if (srcElement.isMapped()) {
			unmapFromExistingTarget = createUnmapCommand();
			unmapFromExistingTarget.execute();
		}

		targetElement = createTargetElement();

		mapping.setFrom(srcElement);
		mapping.setTo(targetElement);
		srcElement.setMapping(mapping);
		targetElement.setMapping(mapping);
		addMapping();

		checkConnections();

		createdConnections.execute();
	}

	protected UnmapCommand createUnmapCommand() {
		return new UnmapCommand(srcElement.getOpposite());
	}

	/**
	 * Steps 1. handle broken and unbroken connections 2. for each connection create
	 * command -> execute undo 3. for FBcreate command -> execute undo 4. remove
	 * mapping entry 5. if unmap command is not null -> execute undo
	 */
	@Override
	public void undo() {
		createdConnections.undo();

		srcElement.setMapping(null); // mapping should be removed first so that all notifiers checking for mapped
		// state will not invalidly afterwards use the resource
		targetElement.setMapping(null);
		removeMappedElements();
		getAutomationSystem().getMapping().remove(mapping);

		if (null != unmapFromExistingTarget) {
			unmapFromExistingTarget.undo();
		}
	}

	protected void addMappedElements() {
		getTargetFBNetwork().getNetworkElements().add(targetElement);
	}

	protected void removeMappedElements() {
		getTargetFBNetwork().getNetworkElements().remove(targetElement);
	}

	/**
	 * Steps 1. if unmap command is not null -> execute redo 2. for FBcreate command
	 * -> execute redo 3. readd mapping entry 3. for each connection create command
	 * -> execute redo 4. handle broken and unbroken connections
	 */
	@Override
	public void redo() {
		if (null != unmapFromExistingTarget) {
			unmapFromExistingTarget.redo();
		}
		addMappedElements();
		srcElement.setMapping(mapping);
		targetElement.setMapping(mapping);
		addMapping();
		createdConnections.redo();
	}

	protected FBNetworkElement createTargetElement() {
		final FBNetworkElement created = switch (srcElement) {
		case final ConfigurableFB confFB -> createConfigureableFB();
		case final FB fb -> createTargetFB();
		case final TypedSubApp typedSubapp -> createTargetTypedSubApp();
		case final UntypedSubApp untypedSubapp -> createTargetUntypedSubApp();
		default -> null;
		};
		if (created != null) {
			created.setName(srcElement.getName());
			MappingTargetCreator.transferFBParams(srcElement, created);
		}
		return created;
	}

	private FBNetworkElement createTargetFB() {
		final FBCreateCommand targetCreateFB = new FBCreateCommand((FBTypeEntry) srcElement.getTypeEntry(),
				getTargetFBNetwork(), srcElement.getPosition());
		targetCreateFB.execute();
		return targetCreateFB.getFB();
	}

	private FBNetworkElement createConfigureableFB() {
		final ConfigurableFB configureableFB = (ConfigurableFB) createTargetFB();
		configureableFB.setDataType(((ConfigurableFB) srcElement).getDataType());
		configureableFB.updateConfiguration();
		return configureableFB;
	}

	private FBNetworkElement createTargetTypedSubApp() {
		final CreateSubAppInstanceCommand cmd = new CreateSubAppInstanceCommand(
				(SubAppTypeEntry) srcElement.getTypeEntry(), getTargetFBNetwork(), srcElement.getPosition());
		cmd.execute();
		return cmd.getSubApp();
	}

	private FBNetworkElement createTargetUntypedSubApp() {
		final SubApp element = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		element.setPosition(EcoreUtil.copy(srcElement.getPosition()));
		element.setInterface(srcElement.getInterface().copy());
		getTargetFBNetwork().getNetworkElements().add(element);
		return element;
	}

	private AutomationSystem getAutomationSystem() {
		return srcElement.getFbNetwork().getApplication().getAutomationSystem();
	}

	protected void checkConnections() {

		final var validInterfaceElements = srcElement.getInterface().getAllInterfaceElements().stream()
				.filter(element -> !(element instanceof ErrorMarkerInterface)).toList(); // Error marker do not get
																							// mapped
		for (final IInterfaceElement interfaceElement : validInterfaceElements) {
			if (interfaceElement.isIsInput()) {
				checkInputConnections(interfaceElement);
			} else {
				checkOutputConnections(interfaceElement);
			}
		}
	}

	private void checkInputConnections(@NonNull final IInterfaceElement interfaceElement) {

		// Filter error markers
		final var inputConnections = interfaceElement.getInputConnections().stream()
				.filter(connection -> !(connection.getSource() instanceof ErrorMarkerInterface));

		inputConnections.forEach(connection -> {
			final Resource res = connection.getSourceElement().getResource();
			if (resource.equals(res)) {
				// we need to create a connection in the target resource
				// connections to error markers will not get mapped
				IInterfaceElement destination = targetElement.getInterfaceElement(interfaceElement.getName());
				if (destination instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& !varDeclaration.isIsInput()) {
					destination = varDeclaration.getInOutVarOpposite();
				}

				final var sourceElement = connection.getSourceElement().getOpposite();
				var source = sourceElement.getInterfaceElement(connection.getSource().getName());
				if (source instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& varDeclaration.isIsInput()) {
					source = varDeclaration.getInOutVarOpposite();
				}
				addConnectionCreateCommand(source, destination, connection.isVisible());
			}
		});
	}

	private void checkOutputConnections(@NonNull final IInterfaceElement interfaceElement) {

		// filter out self-connections (handled by input connections) and error markers
		final var outputConnections = interfaceElement.getOutputConnections().stream()
				.filter(connection -> !isSelfConnection(connection))
				.filter(connection -> !(connection.getDestination() instanceof ErrorMarkerInterface));

		outputConnections.forEach(connection -> {
			final Resource res = connection.getDestinationElement().getResource();
			if (resource.equals(res)) {
				// we need to create a connection in the target resource
				// connections to error markers will not get mapped
				final var destinationElement = connection.getDestinationElement();
				IInterfaceElement destination = destinationElement.getOpposite()
						.getInterfaceElement(connection.getDestination().getName());
				if (destination instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& !varDeclaration.isIsInput()) {
					destination = varDeclaration.getInOutVarOpposite();
				}

				var source = targetElement.getInterfaceElement(interfaceElement.getName());
				if (source instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& varDeclaration.isIsInput()) {
					source = varDeclaration.getInOutVarOpposite();
				}
				addConnectionCreateCommand(source, destination, connection.isVisible());
				if ((destination instanceof AdapterDeclaration) || (destination instanceof VarDeclaration)) {
					checkForDeleteConnections(destination);
				}
			}
		});
	}

	private static boolean isSelfConnection(@NonNull final Connection connection) {
		return connection.getSourceElement() == connection.getDestinationElement();
	}

	private void addConnectionCreateCommand(final IInterfaceElement source, final IInterfaceElement destination,
			final boolean visible) {
		final AbstractConnectionCreateCommand cmd = getConnectionCreateCmd(source);
		if (null != cmd) {
			cmd.setSource(source);
			cmd.setDestination(destination);
			cmd.setVisible(visible);
			// as we are creating the connection as part of the mapping command we don't
			// need to perform a mapping check
			cmd.setPerformMappingCheck(false);
			createdConnections.add(cmd);
		}
	}

	private void checkForDeleteConnections(final IInterfaceElement destination) {
		// TODO model refactoring - determine also connection in the target resource
		// which maybe have to be deleted
	}

	private AbstractConnectionCreateCommand getConnectionCreateCmd(final IInterfaceElement interfaceElement) {
		if (interfaceElement instanceof Event) {
			return new EventConnectionCreateCommand(getTargetFBNetwork());
		}
		if (interfaceElement instanceof AdapterDeclaration) {
			return new AdapterConnectionCreateCommand(getTargetFBNetwork());
		}
		if (interfaceElement instanceof VarDeclaration) {
			return new DataConnectionCreateCommand(getTargetFBNetwork());
		}
		return null;
	}

	protected FBNetwork getTargetFBNetwork() {
		return ((Resource) resource).getFBNetwork();
	}

	// This code is here to serve as template for handling the connections to be
	// deleted
	/*
	 * public void oldExecute() { boolean deletedConnections = false;
	 *
	 * uiResourceEditor.getResourceElement().getFBNetwork().getMappedFBs().add(
	 * mappedFBView.getFb());
	 *
	 * for (InterfaceElementView interfaceElement : fbView.getInterfaceElements()) {
	 * for (ConnectionView connectionView : interfaceElement.getInConnections()) {
	 * if (connectionView.getSource().eContainer() instanceof FBView) { FBView
	 * sourceFBView = ((FBView)
	 * connectionView.getSource().eContainer()).getMappedFB(); if (sourceFBView !=
	 * null && sourceFBView.getFb().getResource()
	 * .equals(uiResourceEditor.getResourceElement().getFBNetwork())) {
	 * ConnectionView newConnection = UiFactory.eINSTANCE.createConnectionView();
	 * newConnection.setConnectionElement(connectionView.getConnectionElement());
	 * newConnection.setDestination(connectionView.getDestination().
	 * getMappedInterfaceElement());
	 * newConnection.setSource(connectionView.getSource().getMappedInterfaceElement(
	 * )); uiResourceEditor.getConnections().add(newConnection);
	 * ConnectionUtil.addConnectionToResource(newConnection.getConnectionElement(),
	 * uiResourceEditor.getResourceElement());
	 * connectionView.getConnectionElement().setBrokenConnection(false);
	 * System.out.println("notBroken: " + connectionView);
	 *
	 * for (ConnectionView temp :
	 * connectionView.getSource().getMappedInterfaceElement() .getOutConnections())
	 * { System.out.println( "Is Resource Connection " +
	 * temp.getConnectionElement().isResourceConnection()); if
	 * (temp.getConnectionElement().isResourceConnection()) {
	 * DeleteConnectionCommand deleteCMD = new DeleteConnectionCommand(temp);
	 * deleteCMD.execute(); deletedConnections = true; } } } else {
	 * System.out.println("isBroken: " + connectionView);
	 * connectionView.getConnectionElement().setBrokenConnection(true); // nothing
	 * to do } } } } if (deletedConnections) { MessageBox informUser = new
	 * MessageBox(Display.getDefault().getActiveShell());
	 * informUser.setText("Warning"); informUser.setMessage(
	 * "Remapping required deletion of Connections added within the Resource - please check your network"
	 * ); informUser.open(); // TODO check whether markers could be used! } }
	 */

	public static Command createMapToCommand(final FBNetworkElement srcElement, final MappingTarget resource) {
		if (resource instanceof Resource) {
			if (srcElement instanceof final Group group) {
				final CompoundCommand cmd = new CompoundCommand();
				group.getGroupElements().forEach(el -> cmd.add(new MapToCommand(el, resource)));
				return cmd;
			}
			return new MapToCommand(srcElement, resource);
		}

		if (resource instanceof final CommunicationMappingTarget communicationMappingTarget) {
			return new MapCommunicationCommand(srcElement, communicationMappingTarget);
		}
		return null;
	}

	private void addMapping() {
		if (elementIndex == -1) {
			getAutomationSystem().getMapping().add(mapping);
		} else {
			getAutomationSystem().getMapping().add(elementIndex, mapping);
		}
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (srcElement != null && resource != null) {
			return Set.of(srcElement, resource);
		}
		return Set.of();
	}

	public void setElementIndex(final int elementIndex) {
		this.elementIndex = elementIndex;
	}
}
