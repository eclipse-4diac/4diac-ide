/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.graph;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

/**
 * A topology graph of event connections in an FB network.
 */
public final class FBNetworkEventTopologyGraph
		extends FBNetworkTopologyGraph<FBNetworkEventTopologyGraph.EventTopologyNode> {

	public class EventTopologyNode
			extends FBNetworkTopologyGraph<FBNetworkEventTopologyGraph.EventTopologyNode>.TopologyNode {

		public EventTopologyNode(final BlockFBNetworkElement element) {
			super(element);
		}
	}

	public FBNetworkEventTopologyGraph(final FBNetwork network) {
		super(network, null);
	}

	protected FBNetworkEventTopologyGraph(final FBNetwork network, final EventTopologyNode parent) {
		super(network, parent);
	}

	@Override
	protected boolean isRelevant(final Connection connection) {
		return connection instanceof EventConnection;
	}

	@Override
	protected EventTopologyNode createNode(final BlockFBNetworkElement element) {
		return new EventTopologyNode(element);
	}

	@Override
	protected FBNetworkGraph<EventTopologyNode> createSubgraph(final FBNetwork network,
			final EventTopologyNode parent) {
		return new FBNetworkEventTopologyGraph(network, parent);
	}
}
