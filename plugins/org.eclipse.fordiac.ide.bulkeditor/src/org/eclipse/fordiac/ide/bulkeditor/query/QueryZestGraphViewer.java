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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

public class QueryZestGraphViewer {
	private static final double ZOOM_STEP = 1.15;
	private static final double ZOOM_MIN = 0.2;
	private static final double ZOOM_MAX = 3.0;
	private static final int SCROLL_SPEED = 30;
	private double zoomLevel = 1.0;

	private final GraphViewer graphViewer;
	private final AdapterFactoryEditingDomain editingDomain;
	private final QueryLayoutAlgorithm layout = new QueryLayoutAlgorithm();
	private final QueryConnectionRouter connectionRouter = new QueryConnectionRouter();
	private final Set<EObject> collapsedNodes = new HashSet<>();

	private EPackage queryPackage;
	private EObject rootElement;
	private Runnable onSave;
	private Runnable onLoad;
	private IProject project;

	public QueryZestGraphViewer(final Composite parent, final AdapterFactoryEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
		graphViewer = createGraphViewer(parent);
	}

	public void setProject(final IProject project) {
		this.project = project;
	}

	public void setInput(final Object input) {
		graphViewer.setInput(input);
		applyLayout();
	}

	public void refresh() {
		graphViewer.refresh();
		applyLayout();
	}

	public GraphViewer getGraphViewer() {
		return graphViewer;
	}

	public void setSaveLoadCallbacks(final Runnable onSave, final Runnable onLoad) {
		this.onSave = onSave;
		this.onLoad = onLoad;
	}

	public void addContextMenu(final EPackage ePackage) {
		this.queryPackage = ePackage;

		final Menu menu = new Menu(graphViewer.getGraphControl());
		graphViewer.getGraphControl().setMenu(menu);
		menu.addListener(SWT.Show, _ -> rebuildContextMenu(menu));
	}

	private GraphViewer createGraphViewer(final Composite parent) {
		final GraphViewer viewer = new GraphViewer(parent, SWT.NONE);
		viewer.getGraphControl().getLightweightSystem().setEventDispatcher(new SWTEventDispatcher() {
			@Override
			public void dispatchMouseMoved(final MouseEvent me) {
				// disables node dragging
			}
		});
		viewer.setContentProvider(new QueryGraphContentProvider());
		viewer.setLabelProvider(new QueryGraphLabelProvider());
		addScrollAndZoom(viewer.getGraphControl());
		addDoubleClickToggle(viewer.getGraphControl());
		return viewer;
	}

	private void addScrollAndZoom(final Graph graph) {
		graph.addListener(SWT.MouseVerticalWheel, event -> {
			if ((event.stateMask & SWT.CTRL) != 0) {
				zoomLevel = event.count > 0 //
						? Math.min(ZOOM_MAX, zoomLevel * ZOOM_STEP)
						: Math.max(ZOOM_MIN, zoomLevel / ZOOM_STEP);
				graph.getRootLayer().setScale(zoomLevel);
			} else {
				final var vp = graph.getViewport();
				final var loc = vp.getViewLocation();
				final boolean horizontal = (event.stateMask & SWT.SHIFT) != 0;
				final int delta = event.count * SCROLL_SPEED;
				vp.setViewLocation(loc.x - (horizontal ? delta : 0), loc.y - (horizontal ? 0 : delta));
			}
		});
	}

	private void addDoubleClickToggle(final Graph graph) {
		graph.addListener(SWT.MouseDoubleClick, _ -> {
			final List<?> selection = graph.getSelection();
			if (selection.isEmpty() || !(selection.get(0) instanceof final GraphNode node)
					|| !(node.getData() instanceof final EObject eObj)) {
				return;
			}
			if (!QueryModelHelper.hasCollapsibleChildren(eObj)) {
				return;
			}
			if (!collapsedNodes.remove(eObj)) {
				collapsedNodes.add(eObj);
			}
			refresh();
		});
	}

	private void applyLayout() {
		layout.layout(graphViewer.getGraphControl());
		applyConnectionStyle();
	}

	private void applyConnectionStyle() {
		for (final Object obj : graphViewer.getGraphControl().getConnections()) {
			if (obj instanceof final GraphConnection gc
					&& gc.getConnectionFigure() instanceof final PolylineConnection polyline) {
				polyline.setConnectionRouter(connectionRouter);

				final PolygonDecoration arrow = new PolygonDecoration();
				arrow.setScale(6, 3);
				polyline.setTargetDecoration(arrow);
			}
		}
	}

	private void rebuildContextMenu(final Menu menu) {
		for (final MenuItem item : menu.getItems()) {
			item.dispose();
		}

		final List<?> selection = graphViewer.getGraphControl().getSelection();
		if (selection.isEmpty() || !(selection.get(0) instanceof final GraphNode node)
				|| !(node.getData() instanceof final EObject selected)) {
			populateBackgroundMenu(menu);
			return;
		}

		final Runnable afterChange = this::refresh;
		QueryModelHelper.populateAddChildMenuItems(menu, selected, editingDomain, queryPackage, afterChange);
		QueryModelHelper.populateFieldConstraintRemoval(menu, selected, editingDomain, afterChange);
		QueryModelHelper.populateRemoveMenuItem(menu, selected, editingDomain, afterChange);
	}

	@SuppressWarnings("unused")
	private void populateBackgroundMenu(final Menu menu) {
		if (rootElement != null && queryPackage != null) {
			QueryModelHelper.populateAddChildMenuItems(menu, rootElement, editingDomain, queryPackage, this::refresh);
		}
		if (onSave == null && onLoad == null) {
			return;
		}
		if (menu.getItemCount() > 0) {
			new MenuItem(menu, SWT.SEPARATOR);
		}
		if (onSave != null) {
			addSimpleMenuItem(menu, "Save", onSave); //$NON-NLS-1$
		}
		if (onLoad != null) {
			addSimpleMenuItem(menu, "Load", onLoad); //$NON-NLS-1$
		}
	}

	private static void addSimpleMenuItem(final Menu menu, final String text, final Runnable action) {
		final MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		item.addListener(SWT.Selection, _ -> action.run());
	}

	private class QueryGraphContentProvider implements IGraphEntityRelationshipContentProvider {
		private EObject root;

		@Override
		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
			root = switch (newInput) {
			case final EObject eObj -> eObj;
			case final Resource res when !res.getContents().isEmpty() -> res.getContents().get(0);
			case null, default -> null;
			};
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			if (root == null) {
				return new Object[0];
			}
			final List<EObject> all = new ArrayList<>();
			collectAll(root, all);
			return all.toArray();
		}

		@Override
		public Object[] getRelationships(final Object source, final Object dest) {
			if (source instanceof final EObject srcObj && dest instanceof final EObject destObj
					&& destObj.eContainer() == srcObj) {
				return new Object[] { new EntityConnectionData(source, dest) };
			}
			return new Object[0];
		}

		private void collectAll(final EObject eObj, final List<EObject> result) {
			result.add(eObj);

			if (collapsedNodes.contains(eObj)) {
				return;
			}

			final boolean isConstraint = QueryModelHelper.isConstraint(eObj);
			for (final EReference ref : eObj.eClass().getEAllContainments()) {
				final Object val = eObj.eGet(ref);
				if (val instanceof final EObject child) {
					if (isConstraint && QueryModelHelper.isOfType(child, QueryModelHelper.FIELD_CONSTRAINT)) {
						continue; // rendered inline in the constraint figure
					}
					collectAll(child, result);
				} else if (val instanceof final List<?> children) {
					for (final Object c : children) {
						if (c instanceof final EObject child) {
							collectAll(child, result);
						}
					}
				}
			}
		}
	}

	private class QueryGraphLabelProvider extends LabelProvider implements IFigureProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof EntityConnectionData) {
				return null;
			}
			if (element instanceof final EObject eObj) {
				return eObj.eClass().getName();
			}
			return String.valueOf(element);
		}

		@Override
		public IFigure getFigure(final Object element) {
			if (element instanceof final EObject eObj) {
				final IFigure node = createNodeFigure(eObj);
				node.setSize(node.getPreferredSize());
				return node;
			}
			return null;
		}

		private IFigure createNodeFigure(final EObject eObj) {
			if (QueryModelHelper.isConstraint(eObj)) {
				return new QueryConstraintNodeFigure(eObj, graphViewer.getGraphControl());
			}
			if (QueryModelHelper.isInstance(eObj)) {
				return new QueryOccurrenceNodeFigure(eObj);
			}
			if (QueryModelHelper.isPlaceholder(eObj)) {
				return new QueryPlaceholderNodeFigure(eObj, graphViewer.getGraphControl());
			}
			if (QueryModelHelper.isAttributeDeclaration(eObj)) {
				return new QueryAttributeDeclarationNodeFigure(eObj, graphViewer.getGraphControl(), project);
			}
			return new QueryNodeFigure(eObj);
		}
	}
}
