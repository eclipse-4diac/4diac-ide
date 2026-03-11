/*******************************************************************************
 * Copyright (c) 2017, 2025 fortiss GmbH, Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker - extracted Code from OpenConnectionOppositeResource
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public final class ConnectionsHelper {

	public static EList<Connection> getConnections(final IInterfaceElement oppositeIE) {
		final IInterfaceElement fbOppostiteIE = oppositeIE.getBlockFBNetworkElement().getOpposite().getInterface()
				.getInterfaceElement(oppositeIE);

		if (null != fbOppostiteIE) {
			return (fbOppostiteIE.isIsInput()) ? fbOppostiteIE.getInputConnections()
					: fbOppostiteIE.getOutputConnections();
		}
		return ECollections.emptyEList();
	}

	public static IInterfaceElement getOppositeInterfaceElement(final IInterfaceElement ie,
			final Connection connection) {
		final IInterfaceElement fbOppostiteIE = ie.getBlockFBNetworkElement().getOpposite().getInterface()
				.getInterfaceElement(ie);

		if (null != fbOppostiteIE) {
			final IInterfaceElement connectionOpposite = (fbOppostiteIE.isIsInput()) ? connection.getSource()
					: connection.getDestination();

			if ((null != connectionOpposite) && connectionOpposite.getBlockFBNetworkElement().isMapped()) {
				final BlockFBNetworkElement mappedOppositeElement = connectionOpposite.getBlockFBNetworkElement()
						.getOpposite();
				return mappedOppositeElement.getInterface().getInterfaceElement(connectionOpposite);
			}

		}
		return null;
	}

	public static Connection getOppositeConnection(final Connection connection) {
		if (null != connection) {
			final IInterfaceElement source = connection.getSource();
			final IInterfaceElement dest = connection.getDestination();

			if (null != source && null != source.getBlockFBNetworkElement() && null != dest
					&& null != dest.getBlockFBNetworkElement()) {
				final BlockFBNetworkElement opSource = source.getBlockFBNetworkElement().getOpposite();
				final BlockFBNetworkElement opDestination = dest.getBlockFBNetworkElement().getOpposite();
				if (null != opSource && null != opDestination
						&& opSource.getFbNetwork() == opDestination.getFbNetwork()) {
					final IInterfaceElement opSourceIE = opSource.getInterface().getInterfaceElement(source);
					final IInterfaceElement opDestIE = opDestination.getInterface().getInterfaceElement(dest);
					if (opSourceIE != null && opDestIE != null) {
						// if we didn't find source or destination at the opposite site we should search
						// the connection
						return findConnection(opSourceIE, opDestIE);
					}
				}
			}
		}
		return null;
	}

	private static Connection findConnection(final IInterfaceElement source, final IInterfaceElement destination) {
		for (final Connection con : source.getOutputConnections()) {
			if (con.getDestination() == destination) {
				return con;
			}
		}
		return null;
	}

	private ConnectionsHelper() {
		throw new UnsupportedOperationException("Helper class ConnectionsHelper should not be instantiated!"); //$NON-NLS-1$
	}
}
