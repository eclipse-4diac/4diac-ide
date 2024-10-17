/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Anderen, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *               - reworked and harmonized source/target checking 551042
 *   Daniel Lindhuber - adjusted for unfolded subapps
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;

public abstract class AbstractConnectionCreateCommand extends Command implements ConnectionLayoutTagger, ScopedCommand {

	private final ConnectionRoutingData routingData;

	/** The parent. */
	private FBNetwork parent;

	/** The connection view. */
	private Connection connection;

	/** The source view. */
	private IInterfaceElement source;

	/** The destination view. */
	private IInterfaceElement destination;

	/**
	 * flag to indicate if during execution of this command a mirrored connection in
	 * the opposite element (e.g., resrouce for app) should be created.
	 *
	 * This flag is here so that the command can be reused also for creating the
	 * mirrored connection.
	 */
	private boolean performMappingCheck;

	private AbstractConnectionCreateCommand mirroredConnection;

	private boolean visible = true;

	private int elementIndex = -1;

	protected AbstractConnectionCreateCommand(final FBNetwork parent) {
		// initialize values
		this.parent = parent;
		this.performMappingCheck = true;
		routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
	}

	public void setArrangementConstraints(final ConnectionRoutingData routingData) {
		this.routingData.setDx1(routingData.getDx1());
		this.routingData.setDx2(routingData.getDx1());
		this.routingData.setDy(routingData.getDy());
	}

	public void setSource(final IInterfaceElement source) {
		this.source = source;
	}

	public IInterfaceElement getSource() {
		return source;
	}

	public void setDestination(final IInterfaceElement target) {
		this.destination = target;
	}

	public IInterfaceElement getDestination() {
		return destination;
	}

	public void setParent(final FBNetwork parent) {
		this.parent = parent;
	}

	public FBNetwork getParent() {
		return parent;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public boolean canExecute() {
		return parent != null && source != null && destination != null && source != destination && canExecuteConType();
	}

	@Override
	public void execute() {
		checkSourceAndTarget();

		connection = createConnectionElement();
		connection.setSource(source);
		connection.setDestination(destination);
		connection.setRoutingData(routingData);

		parent.addConnectionWithIndex(connection, elementIndex);
		// visible needs to be setup after the connection is added to correctly update
		// ui
		connection.setVisible(visible);

		if (performMappingCheck) {
			mirroredConnection = checkAndCreateMirroredConnection();
			if (null != mirroredConnection) {
				mirroredConnection.execute();
			}
		}
	}

	@Override
	public void undo() {
		if (null != mirroredConnection) {
			mirroredConnection.undo();
		}

		connection.setSource(null);
		connection.setDestination(null);
		parent.removeConnection(connection);
	}

	@Override
	public void redo() {
		connection.setSource(source);
		connection.setDestination(destination);
		parent.addConnectionWithIndex(connection, elementIndex);

		if (null != mirroredConnection) {
			mirroredConnection.redo();
		}
	}

	private void checkSourceAndTarget() {
		if (LinkConstraints.isSwapNeeded(source, parent)) {
			// our src is an input we have to swap source and target
			final IInterfaceElement buf = destination;
			destination = source;
			source = buf;
		}
	}

	protected abstract Connection createConnectionElement();

	/**
	 * Check if the mapping of source and target require mirrored connection to be
	 * created and setup a connection create command accordingly. The execute, undo,
	 * and redo will be invoked by AbstractCreateCommand when required.
	 *
	 * @return a connectioncreatecommand if a mirrord connection should be created,
	 *         null otherwise
	 */
	private AbstractConnectionCreateCommand checkAndCreateMirroredConnection() {
		if (null != source.getFBNetworkElement() && null != destination.getFBNetworkElement()) {
			final FBNetworkElement opSource = source.getFBNetworkElement().getOpposite();
			final FBNetworkElement opDestination = destination.getFBNetworkElement().getOpposite();

			if (opSource != null && opDestination != null) {
				IInterfaceElement opSrcIE = opSource.getInterfaceElement(source.getName());
				if (opSrcIE instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& varDeclaration.isIsInput()) {
					opSrcIE = varDeclaration.getInOutVarOpposite();
				}

				IInterfaceElement opDstIE = opDestination.getInterfaceElement(destination.getName());
				if (opDstIE instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& !varDeclaration.isIsInput()) {
					opDstIE = varDeclaration.getInOutVarOpposite();
				}

				if (requiresOppositeConnection(opSource, opDestination, opSrcIE, opDstIE)) {
					final AbstractConnectionCreateCommand cmd = createMirroredConnectionCommand(
							opSource.getFbNetwork());
					// as this is the command for the mirrored connection we don't want again to
					// check
					cmd.setPerformMappingCheck(false);
					cmd.setSource(opSrcIE);
					cmd.setDestination(opDstIE);
					cmd.setVisible(visible);
					return cmd;
				}
			}
		}
		return null;
	}

	private boolean requiresOppositeConnection(final FBNetworkElement opSource, final FBNetworkElement opDestination,
			final IInterfaceElement opSrcIE, final IInterfaceElement opDstIE) {
		if (opSrcIE == null || opDstIE == null) {
			return false;
		}

		if (opSource.getFbNetwork() != opDestination.getFbNetwork()) {
			return false;
		}

		if (opSource == opDestination && opSource instanceof SubApp
				&& ((SubApp) getSource().getFBNetworkElement()).getSubAppNetwork() == getParent()) {
			// we have a connection inside of the subapp, currently we don't need to create
			// this connection in the
			// resource
			return false;
		}

		return !LinkConstraints.duplicateConnection(opSrcIE, opDstIE);
	}

	/**
	 * Create a connection command for creating a mirrored connection for the
	 * connection type
	 *
	 * @param fbNetwork the fbn network for the mirrord connection
	 * @return the command for the connection must not be null
	 */
	protected abstract AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork);

	/**
	 * Perform any connection type (i.e. event, data, or adapter con) specific
	 * checks
	 *
	 * @return true if the two pins can be connected false otherwise
	 */
	protected abstract boolean canExecuteConType();

	public void setPerformMappingCheck(final boolean performMappingCheck) {
		this.performMappingCheck = performMappingCheck;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
		if (null != mirroredConnection) {
			mirroredConnection.setVisible(visible);
		}
	}

	public static AbstractConnectionCreateCommand createCommand(final FBNetwork network,
			final IInterfaceElement connSrc, final IInterfaceElement connDest) {
		if (shouldStructDataConnCreationBeUsed(connSrc, connDest)
				|| shouldStructDataConnCreationBeUsed(connDest, connSrc)) {
			return new StructDataConnectionCreateCommand(network);
		}
		return createCommand(connSrc, network);
	}

	/**
	 * Check if the given pin is the struct defining pin of a struct manipulator.
	 *
	 * For a Demultiplexer it means that it is the single input. For a Multiplexer
	 * it means that it is the single output.
	 *
	 * @param pin the pin to check
	 * @return true if it is a struct defining pin of a struct manipulator.
	 */
	public static boolean isStructManipulatorDefPin(final IInterfaceElement pin) {
		if (!(pin instanceof VarDeclaration)) {
			return false;
		}
		final FBNetworkElement fbNE = pin.getFBNetworkElement();

		return ((fbNE instanceof Demultiplexer && pin.isIsInput())
				|| (fbNE instanceof Multiplexer && !pin.isIsInput()));
	}

	public static boolean shouldStructDataConnCreationBeUsed(final IInterfaceElement pin,
			final IInterfaceElement other) {
		return isStructManipulatorDefPin(pin) && other != null && other.getType() instanceof StructuredType;
	}

	private static AbstractConnectionCreateCommand createCommand(final IInterfaceElement ie, final FBNetwork network) {
		if (ie instanceof Event) {
			return new EventConnectionCreateCommand(network);
		}
		if (ie instanceof VarDeclaration) {
			return new DataConnectionCreateCommand(network);
		}
		return new AdapterConnectionCreateCommand(network);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Stream.of(parent, connection, source, destination).filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableSet());
	}

	public void setElementIndex(final int elementIndex) {
		this.elementIndex = elementIndex;
	}
}
