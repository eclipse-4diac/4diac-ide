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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public abstract class AbstractConnectionCreateCommand extends Command {

	private int connDx1;

	private int connDx2;

	private int connDy;

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

	public AbstractConnectionCreateCommand(FBNetwork parent) {
		super();
		// initialize values
		this.connDx1 = 0;
		this.connDx2 = 0;
		this.connDy = 0;
		this.parent = parent;
		this.performMappingCheck = true;
	}

	public void setArrangementConstraints(int dx1, int dx2, int dy) {
		this.connDx1 = dx1;
		this.connDx2 = dx2;
		this.connDy = dy;
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

	@Override
	public boolean canExecute() {
		if ((getSource() == null) || (getDestination() == null)) {
			return false;
		}
		if (getSource() == getDestination()) {
			return false;
		}
		if (!getInterfaceType().isInstance(getSource())) {
			return false;
		}
		if (!getInterfaceType().isInstance(getDestination())) {
			return false;
		}
		if (checkUnfoldedSubAppConnections()) {
			return false;
		}

		return true;
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
		connection.setDx1(connDx1);
		connection.setDx2(connDx2);
		connection.setDy(connDy);

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
			IInterfaceElement buf = destination;
			destination = source;
			source = buf;
		}
	}

	private void checkParent() {
		// input pin to output pin inside subapp
		if ((getSource().eContainer().eContainer() instanceof SubApp)
				&& (getDestination().eContainer().eContainer() instanceof SubApp)
				&& getSource().isIsInput() && !getDestination().isIsInput()) {
			parent = ((SubApp) getSource().eContainer().eContainer()).getSubAppNetwork();
		}
		// input pin to fb inside unfolded subapp
		if ((getSource().eContainer().eContainer() instanceof SubApp)
				&& (getDestination().eContainer().eContainer().eContainer().eContainer() instanceof SubApp)
				&& getSource().isIsInput()) {
			parent = getDestination().getFBNetworkElement().getFbNetwork();
		}
		// output pin to fb inside unfolded subapp (drawn in reverse)
		if ((getSource().eContainer().eContainer() instanceof SubApp)
				&& (getDestination().eContainer().eContainer().eContainer().eContainer() instanceof SubApp)
				&& !getSource().isIsInput()) {
			parent = getDestination().getFBNetworkElement().getFbNetwork();
		}
		// fb to output pin inside unfolded subapp
		if ((getDestination().eContainer().eContainer() instanceof SubApp)
				&& (getSource().eContainer().eContainer().eContainer().eContainer() instanceof SubApp)
				&& !getSource().isIsInput()) {
			parent = getSource().getFBNetworkElement().getFbNetwork();
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
			FBNetworkElement opSource = source.getFBNetworkElement().getOpposite();
			FBNetworkElement opDestination = destination.getFBNetworkElement().getOpposite();
			if (null != opSource && null != opDestination
					&& opSource.getFbNetwork() == opDestination.getFbNetwork()) {
				AbstractConnectionCreateCommand cmd = createMirroredConnectionCommand(opSource.getFbNetwork());
				cmd.setPerformMappingCheck(false); // as this is the command for the mirrored connection we don't want
													// again to check
				cmd.setSource(opSource.getInterfaceElement(source.getName()));
				cmd.setDestination(opDestination.getInterfaceElement(destination.getName()));
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

	/**
	 * Get the class this connection type should be allowed to be connectable
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Class getInterfaceType();

	private void setPerformMappingCheck(boolean performMappingCheck) {
		this.performMappingCheck = performMappingCheck;
	}

}
