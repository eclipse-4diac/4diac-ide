/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/**
 * Helper class for storing all relevant connection information.
 *
 * This is needed for paste as it can be that the source is deleted in the
 * meantime (e.g., for when using cut)
 */
public class ConnectionReference {

	private final IInterfaceElement source;
	private final IInterfaceElement destination;
	private final ConnectionRoutingData routingData;
	private final boolean visible;

	public ConnectionReference(final Connection con) {
		this.source = con.getSource();
		this.destination = con.getDestination();
		routingData = EcoreUtil.copy(con.getRoutingData());
		visible = con.isVisible();
	}

	public IInterfaceElement getSource() {
		return source;
	}

	public IInterfaceElement getDestination() {
		return destination;
	}

	public FBNetworkElement getSourceElement() {
		return source.getFBNetworkElement();
	}

	public FBNetworkElement getDestinationElement() {
		return destination.getFBNetworkElement();
	}

	public ConnectionRoutingData getRoutingData() {
		return routingData;
	}

	public boolean isVisible() {
		return visible;
	}
}
