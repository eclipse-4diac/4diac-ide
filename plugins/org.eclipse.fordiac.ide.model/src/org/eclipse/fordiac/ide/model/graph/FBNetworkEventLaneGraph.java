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
 * A lane graph of event connections in an FB network.
 */
public final class FBNetworkEventLaneGraph
		extends FBNetworkLaneGraph<FBNetworkEventLaneGraph.EventLaneNode, FBNetworkEventLaneGraph.EventLane> {

	public class EventLane
			extends FBNetworkLaneGraph<FBNetworkEventLaneGraph.EventLaneNode, FBNetworkEventLaneGraph.EventLane>.Lane {
		// empty
	}

	public class EventLaneNode extends
			FBNetworkLaneGraph<FBNetworkEventLaneGraph.EventLaneNode, FBNetworkEventLaneGraph.EventLane>.LaneNode {

		public EventLaneNode(final BlockFBNetworkElement element) {
			super(element);
		}
	}

	public FBNetworkEventLaneGraph(final FBNetwork network) {
		super(network, null);
	}

	protected FBNetworkEventLaneGraph(final FBNetwork network, final EventLaneNode parent) {
		super(network, parent);
	}

	@Override
	protected boolean isRelevant(final Connection connection) {
		return connection instanceof EventConnection;
	}

	@Override
	protected EventLane createLane() {
		return new EventLane();
	}

	@Override
	protected EventLaneNode createNode(final BlockFBNetworkElement element) {
		return new EventLaneNode(element);
	}

	@Override
	protected FBNetworkGraph<EventLaneNode> createSubgraph(final FBNetwork network, final EventLaneNode parent) {
		return new FBNetworkEventLaneGraph(network, parent);
	}
}
