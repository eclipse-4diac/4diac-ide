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

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
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
	private final int dX1;
	private final int dX2;
	private final int dY;

	public ConnectionReference(Connection con) {
		this.source = con.getSource();
		this.destination = con.getDestination();
		dX1 = con.getDx1();
		dX2 = con.getDx2();
		dY = con.getDy();
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

	public int getDx1() {
		return dX1;
	}

	public int getDx2() {
		return dX2;
	}

	public int getDy() {
		return dY;
	}
}
