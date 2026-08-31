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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

/**
 * A graph for an FB network
 */
public abstract class FBNetworkGraph<N extends FBNetworkGraph<N>.Node> {

	/**
	 * A node representing an FB network element
	 */
	public abstract class Node {
		private final BlockFBNetworkElement element;

		private FBNetworkGraph<N> subgraph;

		protected Node(final BlockFBNetworkElement element) {
			this.element = Objects.requireNonNull(element, "element must not be null"); //$NON-NLS-1$
		}

		/**
		 * Get the element
		 *
		 * @return the element
		 */
		public BlockFBNetworkElement getElement() {
			return element;
		}

		/**
		 * Get the containing graph
		 *
		 * @return the containing graph
		 */
		public FBNetworkGraph<N> getGraph() {
			return FBNetworkGraph.this;
		}

		/**
		 * Get the sub-graph of this node if it is loaded
		 *
		 * @return the sub-graph or null if it is not loaded
		 */
		public FBNetworkGraph<N> getSubgraph() {
			return subgraph;
		}

		/**
		 * Load a sub-graph on-demand for this node if it has sub-elements
		 *
		 * @return the sub-graph or null if this node has no sub-elements
		 * @see #hasSubElements()
		 */
		@SuppressWarnings("unchecked")
		public FBNetworkGraph<N> loadSubgraph() {
			if (subgraph == null && element instanceof final UntypedSubApp subApp
					&& !subApp.getSubAppNetwork().getNetworkElements().isEmpty()) {
				subgraph = Objects.requireNonNull(createSubgraph(subApp.getSubAppNetwork(), (N) this));
			}
			return subgraph;
		}

		/**
		 * Unload a sub-graph
		 */
		public void unloadSubgraph() {
			subgraph = null;
		}

		/**
		 * Get if this node has sub-elements
		 *
		 * @return true if it has sub-elements, false otherwise
		 * @implNote A node has sub-elements if the element is an {@link UntypedSubApp}
		 *           with a non-empty network by default.
		 */
		public boolean hasSubElements() {
			return element instanceof final UntypedSubApp subApp
					&& !subApp.getSubAppNetwork().getNetworkElements().isEmpty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return element.hashCode();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Node other = (Node) obj;
			return Objects.equals(element, other.element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return String.format("%s [element=%s]", getClass().getSimpleName(), element.getQualifiedName()); //$NON-NLS-1$
		}
	}

	private final FBNetwork network;
	private final N parent;
	private final int depth;

	private final SequencedMap<BlockFBNetworkElement, N> nodes;

	/**
	 * Create a new FB network graph
	 *
	 * @param network the network
	 * @param parent  the parent graph or null if none
	 */
	protected FBNetworkGraph(final FBNetwork network, final N parent) {
		this.network = Objects.requireNonNull(network, "network is null"); //$NON-NLS-1$
		this.parent = parent;
		depth = parent != null ? parent.getGraph().getDepth() + 1 : 0;
		nodes = network.getBlockFBNetworkElements().map(this::createNode).collect(toNodeMap());
	}

	private static <N extends FBNetworkGraph<N>.Node> Collector<N, ?, SequencedMap<BlockFBNetworkElement, N>> toNodeMap() {
		return Collectors.collectingAndThen(
				Collectors.toMap(N::getElement, Objects::requireNonNull, (a, _) -> a, LinkedHashMap::new),
				Collections::unmodifiableSequencedMap);
	}

	/**
	 * Get the network of this graph
	 *
	 * @return the network
	 */
	public FBNetwork getNetwork() {
		return network;
	}

	/**
	 * Get the parent graph
	 *
	 * @return the parent graph or null if none
	 */
	public N getParent() {
		return parent;
	}

	/**
	 * Get the nesting depth of this graph
	 *
	 * @return the nesting depth or 0 if there is no parent
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Get the elements of this graph
	 *
	 * @return a set of elements in the order of the FB network
	 */
	public SequencedSet<BlockFBNetworkElement> getElements() {
		return nodes.sequencedKeySet();
	}

	/**
	 * Get the nodes of this graph
	 *
	 * @return a set of nodes in the order of the FB network
	 */
	public SequencedCollection<N> getNodes() {
		return nodes.sequencedValues();
	}

	/**
	 * Get the sorted nodes of this graph
	 *
	 * @return a set of nodes sorted depending on the graph
	 * @implNote This uses the same sorting as {@link #getNodes()} by default and
	 *           can be overridden to provide a sorting specific to the graph.
	 */
	public SequencedCollection<N> getSortedNodes() {
		return getNodes();
	}

	/**
	 * Get whether the graph contains the given element
	 *
	 * @param element an element
	 * @return true if the graph contains the element, false otherwise
	 */
	public boolean containsElement(final BlockFBNetworkElement element) {
		return nodes.containsKey(element);
	}

	/**
	 * Get the node for the provided element
	 *
	 * @param element an element
	 * @return the node for the element or null if the element is not contained in
	 *         this graph
	 */
	public N getNode(final BlockFBNetworkElement element) {
		return nodes.get(element);
	}

	/**
	 * Create a new node for the provided element
	 *
	 * @param element an element
	 * @return the created node, must not be null
	 */
	protected abstract N createNode(BlockFBNetworkElement element);

	/**
	 * Create a new sub-graph for the provided network and parent
	 *
	 * @param network a network
	 * @param parent  the parent graph
	 * @return the created sub-graph, must not be null
	 */
	protected abstract FBNetworkGraph<N> createSubgraph(FBNetwork network, N parent);
}
