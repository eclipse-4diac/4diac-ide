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

	private record SubConstraints(List<EObject> and, List<EObject> or) {
	}

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

		for (final EObject child : children) {
			computeSize(child);
		}

		final double[] size;
		if (children.isEmpty()) {
			size = new double[] { nodeSize[0], nodeSize[1] };
		} else if (QueryModelHelper.isOfType(eObj, QueryModelHelper.PLACE)) {
			size = computeHorizontalSize(nodeSize, children);
		} else if (QueryModelHelper.isConstraint(eObj)) {
			size = computeConstraintSize(nodeSize, children);
		} else {
			size = computeVerticalSize(nodeSize, children);
		}
		subtreeSizes.put(eObj, size);
		return size;
	}

	private double[] computeHorizontalSize(final double[] nodeSize, final List<EObject> children) {
		double childrenWidth = 0;
		double childrenMaxHeight = 0;
		for (int i = 0; i < children.size(); i++) {
			final double[] childSize = subtreeSizes.get(children.get(i));
			if (i > 0) {
				childrenWidth += hGap;
			}
			childrenWidth += childSize[0];
			childrenMaxHeight = Math.max(childrenMaxHeight, childSize[1]);
		}
		final double indent = nodeSize[0] / 2.0;
		return new double[] { Math.max(nodeSize[0], indent + childrenWidth), nodeSize[1] + vGap + childrenMaxHeight };
	}

	private double[] computeVerticalSize(final double[] nodeSize, final List<EObject> children) {
		double childrenHeight = 0;
		double childrenMaxWidth = 0;
		for (int i = 0; i < children.size(); i++) {
			final double[] childSize = subtreeSizes.get(children.get(i));
			if (i > 0) {
				childrenHeight += vGap;
			}
			childrenHeight += childSize[1];
			childrenMaxWidth = Math.max(childrenMaxWidth, childSize[0]);
		}
		final double indent = nodeSize[0] / 2.0 + childIndent;
		return new double[] { Math.max(nodeSize[0], indent + childrenMaxWidth), nodeSize[1] + vGap + childrenHeight };
	}

	private double[] computeConstraintSize(final double[] nodeSize, final List<EObject> children) {
		final SubConstraints subs = partitionSubConstraints(children);

		double rowWidth = nodeSize[0];
		double rowHeight = nodeSize[1];
		for (final EObject and : subs.and()) {
			final double[] childSize = subtreeSizes.get(and);
			rowWidth += hGap + childSize[0];
			rowHeight = Math.max(rowHeight, childSize[1]);
		}

		double orHeight = 0;
		double orMaxWidth = 0;
		for (int i = 0; i < subs.or().size(); i++) {
			final double[] childSize = subtreeSizes.get(subs.or().get(i));
			if (i > 0) {
				orHeight += vGap;
			}
			orHeight += childSize[1];
			orMaxWidth = Math.max(orMaxWidth, childSize[0]);
		}

		final double orIndent = nodeSize[0] / 2.0 + childIndent;
		final double width = Math.max(rowWidth, subs.or().isEmpty() ? 0 : orIndent + orMaxWidth);
		final double height = rowHeight + (subs.or().isEmpty() ? 0 : vGap + orHeight);
		return new double[] { width, height };
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

		if (QueryModelHelper.isOfType(eObj, QueryModelHelper.PLACE)) {
			positionHorizontal(children, x + nodeSize[0] / 2.0 + childIndent, y + nodeSize[1] + vGap);
		} else if (QueryModelHelper.isConstraint(eObj)) {
			positionConstraintChildren(children, x, y, nodeSize);
		} else {
			positionVertical(children, x + nodeSize[0] / 2.0 + childIndent, y + nodeSize[1] + vGap);
		}
	}

	private void positionHorizontal(final List<EObject> children, final double startX, final double startY) {
		double cx = startX;
		for (final EObject child : children) {
			position(child, cx, startY);
			cx += subtreeSizes.get(child)[0] + hGap;
		}
	}

	private void positionVertical(final List<EObject> children, final double startX, final double startY) {
		double cy = startY;
		for (final EObject child : children) {
			position(child, startX, cy);
			cy += subtreeSizes.get(child)[1] + vGap;
		}
	}

	private void positionConstraintChildren(final List<EObject> children, final double x, final double y,
			final double[] nodeSize) {
		final SubConstraints subs = partitionSubConstraints(children);

		// and-chain
		double cx = x + nodeSize[0] + hGap;
		double rowHeight = nodeSize[1];
		for (final EObject and : subs.and()) {
			position(and, cx, y);
			final double[] childSize = subtreeSizes.get(and);
			cx += childSize[0] + hGap;
			rowHeight = Math.max(rowHeight, childSize[1]);
		}

		// or-stack
		positionVertical(subs.or(), x, y + rowHeight + vGap);
	}

	private static SubConstraints partitionSubConstraints(final List<EObject> children) {
		final List<EObject> and = new ArrayList<>();
		final List<EObject> or = new ArrayList<>();
		for (final EObject child : children) {
			final EReference containment = child.eContainmentFeature();
			if (containment != null && QueryModelHelper.REF_AND_CONSTRAINTS.equals(containment.getName())) {
				and.add(child);
			} else {
				or.add(child);
			}
		}
		return new SubConstraints(and, or);
	}

	private List<EObject> getContainedChildren(final EObject eObj) {
		return QueryModelHelper.getChildNodes(eObj).stream().filter(nodeMap::containsKey).toList();
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