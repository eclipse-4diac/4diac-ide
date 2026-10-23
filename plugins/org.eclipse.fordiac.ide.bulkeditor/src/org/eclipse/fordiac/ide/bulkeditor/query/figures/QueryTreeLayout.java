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
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.gef.figures.ModuloFreeformFigure;

/**
 * Places the query nodes as tree: the children of a place side by side, AND
 * constraints right of their constraint and all other children below their
 * parent. The placeholders of the query are placed in a grid above the tree,
 * which fills the width of the drawing area around the tree.
 */
public class QueryTreeLayout extends AbstractLayout {

	private static final double HORIZONTAL_GAP = 60;
	private static final double VERTICAL_GAP = 25;
	private static final double CHILD_INDENT = 60;
	private static final double PADDING = 25;
	private static final double PLACEHOLDER_GAP = 25;
	// the drawing area of the 4diac root edit part (ModuloFreeformFigure) is a
	// multiple of 400 wide, but at least 1200
	private static final double DRAWING_AREA_UNIT = 400;
	private static final double DRAWING_AREA_MIN_UNITS = 3;

	private record Size(double width, double height) {
	}

	private final Map<EObject, IFigure> nodeFigures = new HashMap<>();
	private final Map<EObject, Size> subtreeSizes = new HashMap<>();

	@Override
	public void layout(final IFigure container) {
		nodeFigures.clear();
		subtreeSizes.clear();
		for (final IFigure child : container.getChildren()) {
			if (child instanceof final QueryNodeFigure node) {
				nodeFigures.put(node.getElement(), node);
			}
		}
		final EObject root = findRoot();
		if (root != null) {
			final List<EObject> placeholders = getPlaceholders(root);
			placeholders.forEach(this::computeSubtreeSize);
			final double treeRight = PADDING + computeSubtreeSize(root).width();
			final double gridWidth = getDrawingAreaWidth(container, treeRight) - 2 * PADDING;
			final double treeY = positionPlaceholders(placeholders, gridWidth, PADDING, PADDING);
			position(root, PADDING, treeY);
		}
	}

	@Override
	protected Dimension calculatePreferredSize(final IFigure container, final int wHint, final int hHint) {
		final Rectangle extent = new Rectangle();
		container.getChildren().forEach(child -> extent.union(child.getBounds()));
		return extent.getSize();
	}

	private EObject findRoot() {
		return nodeFigures.keySet().stream().filter(element -> !nodeFigures.containsKey(element.eContainer()))
				.findFirst().orElse(null);
	}

	private Size computeSubtreeSize(final EObject element) {
		final Size nodeSize = getNodeSize(element);
		final List<EObject> children = getLaidOutChildren(element);
		children.forEach(this::computeSubtreeSize);

		final Size size;
		if (children.isEmpty()) {
			size = nodeSize;
		} else if (QueryModelHelper.isPlace(element)) {
			size = computeHorizontalSize(nodeSize, children);
		} else if (QueryModelHelper.isConstraint(element)) {
			size = computeConstraintSize(nodeSize, children);
		} else {
			size = computeVerticalSize(nodeSize, children);
		}
		subtreeSizes.put(element, size);
		return size;
	}

	private Size computeHorizontalSize(final Size nodeSize, final List<EObject> children) {
		final double childrenWidth = getTotalWidth(children) + HORIZONTAL_GAP * (children.size() - 1);
		return new Size(getPlaceChildrenOffset(nodeSize) + childrenWidth,
				nodeSize.height() + VERTICAL_GAP + getMaxHeight(children));
	}

	private static double getPlaceChildrenOffset(final Size placeSize) {
		return placeSize.width() + HORIZONTAL_GAP;
	}

	private Size computeVerticalSize(final Size nodeSize, final List<EObject> children) {
		final double childrenHeight = getTotalHeight(children) + VERTICAL_GAP * (children.size() - 1);
		return new Size(Math.max(nodeSize.width(), nodeSize.width() / 2 + CHILD_INDENT + getMaxWidth(children)),
				nodeSize.height() + VERTICAL_GAP + childrenHeight);
	}

	private Size computeConstraintSize(final Size nodeSize, final List<EObject> children) {
		final List<EObject> andConstraints = getAndConstraints(children);
		final List<EObject> orConstraints = getOrConstraints(children);
		final double rowWidth = nodeSize.width() + HORIZONTAL_GAP * andConstraints.size()
				+ getTotalWidth(andConstraints);
		final double rowHeight = Math.max(nodeSize.height(), getMaxHeight(andConstraints));
		if (orConstraints.isEmpty()) {
			return new Size(rowWidth, rowHeight);
		}
		final double orHeight = getTotalHeight(orConstraints) + VERTICAL_GAP * (orConstraints.size() - 1);
		return new Size(Math.max(rowWidth, nodeSize.width() / 2 + CHILD_INDENT + getMaxWidth(orConstraints)),
				rowHeight + VERTICAL_GAP + orHeight);
	}

	private void position(final EObject element, final double x, final double y) {
		final IFigure figure = nodeFigures.get(element);
		figure.setBounds(new Rectangle(new PrecisionPoint(x, y), figure.getPreferredSize()));

		final Size nodeSize = getNodeSize(element);
		final List<EObject> children = getLaidOutChildren(element);
		if (QueryModelHelper.isPlace(element)) {
			positionHorizontal(children, x + getPlaceChildrenOffset(nodeSize), y + nodeSize.height() + VERTICAL_GAP);
		} else if (QueryModelHelper.isConstraint(element)) {
			positionConstraintChildren(children, x, y, nodeSize);
		} else {
			positionVertical(children, x + nodeSize.width() / 2 + CHILD_INDENT, y + nodeSize.height() + VERTICAL_GAP);
		}
	}

	private void positionHorizontal(final List<EObject> children, final double startX, final double startY) {
		double x = startX;
		for (final EObject child : children) {
			position(child, x, startY);
			x += subtreeSizes.get(child).width() + HORIZONTAL_GAP;
		}
	}

	private void positionVertical(final List<EObject> children, final double startX, final double startY) {
		double y = startY;
		for (final EObject child : children) {
			position(child, startX, y);
			y += subtreeSizes.get(child).height() + VERTICAL_GAP;
		}
	}

	/**
	 * Places the placeholders in a grid with columns of the same width that fill
	 * the given width.
	 *
	 * @return the y coordinate below the grid
	 */
	private double positionPlaceholders(final List<EObject> placeholders, final double width, final double startX,
			final double startY) {
		if (placeholders.isEmpty()) {
			return startY;
		}
		final double minColumnWidth = getMaxWidth(placeholders);
		final int columns = (int) Math.max(1,
				Math.floor((width + PLACEHOLDER_GAP) / (minColumnWidth + PLACEHOLDER_GAP)));
		final double columnWidth = Math.max(minColumnWidth, (width - PLACEHOLDER_GAP * (columns - 1)) / columns);
		double y = startY;
		for (int rowStart = 0; rowStart < placeholders.size(); rowStart += columns) {
			final List<EObject> row = placeholders.subList(rowStart, Math.min(rowStart + columns, placeholders.size()));
			for (int column = 0; column < row.size(); column++) {
				final IFigure figure = nodeFigures.get(row.get(column));
				final double x = startX + column * (columnWidth + PLACEHOLDER_GAP);
				figure.setBounds(new PrecisionRectangle(x, y, columnWidth, figure.getPreferredSize().height()));
			}
			y += getMaxHeight(row) + PLACEHOLDER_GAP;
		}
		return y;
	}

	private void positionConstraintChildren(final List<EObject> children, final double x, final double y,
			final Size nodeSize) {
		final List<EObject> andConstraints = getAndConstraints(children);
		positionHorizontal(andConstraints, x + nodeSize.width() + HORIZONTAL_GAP, y);
		final double rowHeight = Math.max(nodeSize.height(), getMaxHeight(andConstraints));
		positionVertical(getOrConstraints(children), x, y + rowHeight + VERTICAL_GAP);
	}

	private List<EObject> getLaidOutChildren(final EObject element) {
		// the placeholders are laid out in rows above the tree
		return QueryModelHelper.getChildNodes(element).stream().filter(nodeFigures::containsKey)
				.filter(child -> !QueryModelHelper.isPlaceholder(child)).toList();
	}

	/** @return the width of the drawing area around a tree ending at treeRight */
	private static double getDrawingAreaWidth(final IFigure container, final double treeRight) {
		for (IFigure figure = container.getParent(); figure != null; figure = figure.getParent()) {
			if (figure instanceof final ModuloFreeformFigure drawingArea) {
				// computed like the extent of the drawing area from the extent of the container
				final double contentsRight = treeRight + container.getInsets().right - drawingArea.getInsets().right;
				return Math.max(DRAWING_AREA_MIN_UNITS, Math.ceil(contentsRight / DRAWING_AREA_UNIT))
						* DRAWING_AREA_UNIT;
			}
		}
		return treeRight + PADDING;
	}

	private List<EObject> getPlaceholders(final EObject root) {
		return QueryModelHelper.getChildNodes(root).stream().filter(nodeFigures::containsKey)
				.filter(QueryModelHelper::isPlaceholder).toList();
	}

	private Size getNodeSize(final EObject element) {
		final Dimension size = nodeFigures.get(element).getPreferredSize();
		return new Size(size.width(), size.height());
	}

	private double getTotalWidth(final List<EObject> elements) {
		return elements.stream().mapToDouble(element -> subtreeSizes.get(element).width()).sum();
	}

	private double getTotalHeight(final List<EObject> elements) {
		return elements.stream().mapToDouble(element -> subtreeSizes.get(element).height()).sum();
	}

	private double getMaxWidth(final List<EObject> elements) {
		return elements.stream().mapToDouble(element -> subtreeSizes.get(element).width()).max().orElse(0);
	}

	private double getMaxHeight(final List<EObject> elements) {
		return elements.stream().mapToDouble(element -> subtreeSizes.get(element).height()).max().orElse(0);
	}

	private static List<EObject> getAndConstraints(final List<EObject> constraints) {
		return constraints.stream().filter(QueryTreeLayout::isAndConstraint).toList();
	}

	private static List<EObject> getOrConstraints(final List<EObject> constraints) {
		return constraints.stream().filter(constraint -> !isAndConstraint(constraint)).toList();
	}

	private static boolean isAndConstraint(final EObject constraint) {
		final EReference containment = constraint.eContainmentFeature();
		return containment != null && QueryModelHelper.REF_AND_CONSTRAINTS.equals(containment.getName());
	}
}
