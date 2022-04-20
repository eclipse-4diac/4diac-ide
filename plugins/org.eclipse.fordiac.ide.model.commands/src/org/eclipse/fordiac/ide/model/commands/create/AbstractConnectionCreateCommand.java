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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public abstract class AbstractConnectionCreateCommand extends Command {

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

	protected AbstractConnectionCreateCommand(final FBNetwork parent) {
		super();
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

	protected FBNetwork getParent() {
		return parent;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public boolean canExecute() {
		if ((getSource() == null) || (getDestination() == null)) {
			return false;
		}
		if (getSource() == getDestination()) {
			return false;
		}

		// ensure the right parent
		checkParent();

		if (checkUnfoldedSubAppConnections()) {
			return false;
		}

		return canExecuteConType();
	}

	private boolean checkUnfoldedSubAppConnections() {
		// returns false for typed subapps & cfbs
		if (getSource().getFBNetworkElement() == null
				|| getDestination().getFBNetworkElement() == null) {
			return false;
		}
		// prevents connections across unfolded subapp borders
		if (getSource().getFBNetworkElement().getFbNetwork() != getDestination().getFBNetworkElement().getFbNetwork()) {
			EObject srcContainer = null;
			EObject destContainer = null;
			if (getSource().eContainer().eContainer() instanceof SubApp) {
				srcContainer = getSource().eContainer().eContainer();
			}
			if (getDestination().eContainer().eContainer() instanceof SubApp) {
				destContainer = getDestination().eContainer().eContainer();
			}
			if ((srcContainer == null) && (destContainer == null)) {
				return true;
			}
			if ((destContainer == null)
					&& (srcContainer != getDestination().eContainer().eContainer().eContainer().eContainer())) {
				return true;
			}
			if ((srcContainer == null)
					&& (destContainer != getSource().eContainer().eContainer().eContainer().eContainer())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute() {
		checkParent();
		checkSourceAndTarget();

		connection = createConnectionElement();
		connection.setSource(source);
		connection.setDestination(destination);
		connection.setRoutingData(routingData);
		connection.setVisible(visible);

		parent.addConnection(connection);

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
		parent.addConnection(connection);

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

	private void checkParent() {
		final FBNetworkElement srcElement = getSource().getFBNetworkElement();
		final FBNetworkElement dstElement = getDestination().getFBNetworkElement();

		if ((srcElement != null) && (dstElement != null)
				&& ((srcElement instanceof SubApp) || (dstElement instanceof SubApp))) {
			// we only need to check the parent if both ends are subapps
			final FBNetwork srcNetwork = srcElement.getFbNetwork();
			final FBNetwork dstNetwork = dstElement.getFbNetwork();

			if (srcNetwork != dstNetwork) {
				// we have a connection from an interface element to an internal element
				if ((srcElement instanceof SubApp) && (((SubApp) srcElement).getSubAppNetwork() == dstNetwork)) {
					// the destination subapp is contained in the source subapp
					parent = dstNetwork;
				} else if ((dstElement instanceof SubApp) && (((SubApp) dstElement).getSubAppNetwork() == srcNetwork)) {
					// the source subapp is contained in the destination subapp
					parent = srcNetwork;
				}
			}
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
			if (null != opSource && null != opDestination
					&& opSource.getFbNetwork() == opDestination.getFbNetwork()) {
				final AbstractConnectionCreateCommand cmd = createMirroredConnectionCommand(opSource.getFbNetwork());
				cmd.setPerformMappingCheck(false); // as this is the command for the mirrored connection we don't want
				// again to check
				cmd.setSource(opSource.getInterfaceElement(source.getName()));
				cmd.setDestination(opDestination.getInterfaceElement(destination.getName()));
				cmd.setVisible(visible);
				return (cmd.canExecute()) ? cmd : null;
			}
		}
		return null;
	}

	/**
	 * Create a connection command for creating a mirrored connection for the
	 * connection type
	 *
	 * @param fbNetwork the fbn network for the mirrord connection
	 * @return the command for the connection must not be null
	 */
	protected abstract AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork);

	/** Perform any connection type (i.e. event, data, or adapter con) specific checks
	 *
	 * @return true if the two pins can be connected false otherwise */
	protected abstract boolean canExecuteConType();

	private void setPerformMappingCheck(final boolean performMappingCheck) {
		this.performMappingCheck = performMappingCheck;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
		if (null != mirroredConnection) {
			mirroredConnection.setVisible(visible);
		}
	}

}
