/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

public class QueryLayoutAlgorithm {

	private final int hGap;
	private final int vGap;
	private final int childIndent;
	private final int padding;

	private Map<EObject, GraphNode> nodeMap;
	private final Map<EObject, double[]> subtreeSizes = new HashMap<>();

	public QueryLayoutAlgorithm() {
		this(60, 25, 60, 25);
	}

	public QueryLayoutAlgorithm(final int hGap, final int vGap, final int childIndent, final int padding) {
		this.hGap = hGap;
		this.vGap = vGap;
		this.childIndent = childIndent;
		this.padding = padding;
	}

	public void layout(final Graph graph) {
		nodeMap = buildNodeMap(graph);
		if (nodeMap.isEmpty()) {
			return;
		}

		final EObject root = findRoot();
		if (root == null) {
			return;
		}

		subtreeSizes.clear();
		// compute subtree sizes
		computeSize(root);
		// place nodes
		position(root, padding, padding);
	}

	private static Map<EObject, GraphNode> buildNodeMap(final Graph graph) {
		final Map<EObject, GraphNode> map = new HashMap<>();
		for (final Object obj : graph.getNodes()) {
			if (obj instanceof final GraphNode gn && gn.getData() instanceof final EObject eObj) {
				map.put(eObj, gn);
			}
		}
		return map;
	}

	private EObject findRoot() {
		for (final EObject eObj : nodeMap.keySet()) {
			if (eObj.eContainer() == null || !nodeMap.containsKey(eObj.eContainer())) {
				return eObj;
			}
		}
		return null;
	}

	private double[] computeSize(final EObject eObj) {
		final double[] nodeSize = getNodeSize(eObj);
		final List<EObject> children = getContainedChildren(eObj);

		if (children.isEmpty()) {
			final double[] size = { nodeSize[0], nodeSize[1] };
			subtreeSizes.put(eObj, size);
			return size;
		}

		final List<double[]> childSizes = new ArrayList<>();
		for (final EObject child : children) {
			childSizes.add(computeSize(child));
		}

		final double totalWidth;
		final double totalHeight;

		if (QueryModelHelper.isOfType(eObj, QueryModelHelper.PLACE)) {
			double childrenWidth = 0;
			double childrenMaxHeight = 0;
			for (int i = 0; i < childSizes.size(); i++) {
				if (i > 0) {
					childrenWidth += hGap;
				}
				childrenWidth += childSizes.get(i)[0];
				childrenMaxHeight = Math.max(childrenMaxHeight, childSizes.get(i)[1]);
			}
			final double indent = nodeSize[0] / 2.0;
			totalWidth = Math.max(nodeSize[0], indent + childrenWidth);
			totalHeight = nodeSize[1] + vGap + childrenMaxHeight;
		} else {
			double childrenHeight = 0;
			double childrenMaxWidth = 0;
			for (int i = 0; i < childSizes.size(); i++) {
				if (i > 0) {
					childrenHeight += vGap;
				}
				childrenHeight += childSizes.get(i)[1];
				childrenMaxWidth = Math.max(childrenMaxWidth, childSizes.get(i)[0]);
			}
			final double indent = nodeSize[0] / 2.0 + childIndent;
			totalWidth = Math.max(nodeSize[0], indent + childrenMaxWidth);
			totalHeight = nodeSize[1] + vGap + childrenHeight;
		}

		final double[] size = { totalWidth, totalHeight };
		subtreeSizes.put(eObj, size);
		return size;
	}

	private void position(final EObject eObj, final double x, final double y) {
		final GraphNode node = nodeMap.get(eObj);
		if (node == null) {
			return;
		}

		node.setLocation(x, y);

		final double[] nodeSize = getNodeSize(eObj);
		final List<EObject> children = getContainedChildren(eObj);
		if (children.isEmpty()) {
			return;
		}
		final double childStartX = x + nodeSize[0] / 2.0 + childIndent;
		final double childStartY = y + nodeSize[1] + vGap;

		if (QueryModelHelper.isOfType(eObj, QueryModelHelper.PLACE)) {
			// horizontal layout of nodes (For children of Place)
			double cx = childStartX;
			for (final EObject child : children) {
				position(child, cx, childStartY);
				final double[] childSize = subtreeSizes.get(child);
				cx += childSize[0] + hGap;
			}
		} else {
			double cy = childStartY;
			for (final EObject child : children) {
				position(child, childStartX, cy);
				final double[] childSize = subtreeSizes.get(child);
				cy += childSize[1] + vGap;
			}
		}
	}

	private List<EObject> getContainedChildren(final EObject eObj) {
		final List<EObject> children = new ArrayList<>();
		for (final EReference ref : eObj.eClass().getEAllContainments()) {
			final Object val = eObj.eGet(ref);
			if (val instanceof final EObject child) {
				if (nodeMap.containsKey(child)) {
					children.add(child);
				}
			} else if (val instanceof final List<?> list) {
				for (final Object c : list) {
					if (c instanceof final EObject child && nodeMap.containsKey(child)) {
						children.add(child);
					}
				}
			}
		}
		return children;
	}

	private double[] getNodeSize(final EObject eObj) {
		final GraphNode node = nodeMap.get(eObj);
		if (node != null) {
			final var size = node.getSize();
			if (size.width > 1 && size.height > 1) {
				return new double[] { size.width, size.height };
			}
			final var prefSize = node.getNodeFigure().getPreferredSize();
			return new double[] { prefSize.width, prefSize.height };
		}
		return new double[] { 80, 30 };
	}
}