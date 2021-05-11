/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *               - implemented first version of gotoMarker for FB markers
 *               - extracted breadcrumb based editor to model.ui
 *   Alois Zoitl, Bianca Wiesmayr, Michael Oberlehner, Lukas Wais, Daniel Lindhuber
 *     - initial implementation of breadcrumb navigation location
 *   Michael Oberlehner, Alois Zoitl
 *               - implemented save and restore state
 *   Alois Zoitl - extracted to new file, added graphical editor information
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Objects;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.NavigationLocation;

public class BreadcrumbNavigationLocation extends NavigationLocation {

	private static class GraphicalViewerData {
		public double zoom = 1.0;
		public Point location = new Point(0, 0);

		@Override
		public int hashCode() {
			return Objects.hash(location, Double.valueOf(zoom));
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof GraphicalViewerData)) {
				return false;
			}
			final GraphicalViewerData other = (GraphicalViewerData) obj;
			return location.equals(other.location) && (0 == Double.compare(zoom, other.zoom));
		}
	}

	private final Object model;
	private GraphicalViewerData viewerData = null;
	private final AdapterFactoryContentProvider contentProvider;
	private final AdapterFactoryLabelProvider labelProvider;

	protected BreadcrumbNavigationLocation(final AbstractBreadCrumbEditor editorPart, final Object model) {
		super(editorPart);
		this.model = model;
		contentProvider = editorPart.getBreadcrumb().getContentProvider();
		labelProvider = editorPart.getBreadcrumb().getLabelProvider();
		final GraphicalViewer viewer = editorPart.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			captureGraphicalViewerData(viewer);
		}
	}

	@Override
	public String getText() {
		final var sb = new StringBuilder();
		generateItemPath(sb, model, contentProvider, labelProvider);
		return sb.substring(1);
	}

	public static void generateItemPath(final StringBuilder sb, final Object model,
			final AdapterFactoryContentProvider adapterFactoryContentProvider,
			final AdapterFactoryLabelProvider adapterFactoryLabelProvider) {
		if (model == null) {
			return;
		}
		generateItemPath(sb, adapterFactoryContentProvider.getParent(model), adapterFactoryContentProvider,
				adapterFactoryLabelProvider);
		sb.append("."); //$NON-NLS-1$
		sb.append(adapterFactoryLabelProvider.getText(model));
	}

	private Object getModel() {
		return model;
	}

	@Override
	protected AbstractBreadCrumbEditor getEditorPart() {
		return (AbstractBreadCrumbEditor) super.getEditorPart();
	}

	@Override
	public void saveState(final IMemento memento) {
		// currently we are not saveing the state
	}

	@Override
	public void restoreState(final IMemento memento) {
		// currently we are not restoring the state here
	}

	@Override
	public void restoreLocation() {
		final IEditorPart part= getEditorPart();
		if (part instanceof AbstractBreadCrumbEditor) {
			final AbstractBreadCrumbEditor editor= getEditorPart();
			editor.getBreadcrumb().setInput(model);
			final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
			if ((viewer != null) && (viewerData != null)) {
				restoreGraphicalViewerData(viewer);
			}
		}
	}

	@Override
	public boolean mergeInto(final INavigationLocation currentLocation) {
		if (currentLocation instanceof BreadcrumbNavigationLocation) {
			final BreadcrumbNavigationLocation currentBreadCrumbLocation = ((BreadcrumbNavigationLocation) currentLocation);
			if (viewerData != null) {
				return this.model == currentBreadCrumbLocation.getModel()
						&& viewerData.equals(currentBreadCrumbLocation.viewerData);
			}
			return this.model == currentBreadCrumbLocation.getModel();
		}
		return false;
	}

	@Override
	public void update() {
		// currently not supported
	}

	private void captureGraphicalViewerData(final GraphicalViewer viewer) {
		viewerData = new GraphicalViewerData();
		if (viewer.getRootEditPart() instanceof ScalableFreeformRootEditPart) {
			viewerData.zoom = ((ScalableFreeformRootEditPart) viewer.getRootEditPart()).getZoomManager().getZoom();
		}

		if (viewer.getControl() instanceof FigureCanvas) {
			final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
			viewerData.location = canvas.getViewport().getViewLocation();
		}
	}

	private void restoreGraphicalViewerData(final GraphicalViewer viewer) {
		if (viewer.getRootEditPart() instanceof ScalableFreeformRootEditPart) {
			((ScalableFreeformRootEditPart) viewer.getRootEditPart()).getZoomManager().setZoom(viewerData.zoom);
		}

		if (viewer.getControl() instanceof FigureCanvas) {
			final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
			final int xLocation = viewerData.location.x;
			final int yLocation = viewerData.location.y;
			// we have to wait to set the scroll position until the editor is drawn and the canvas is setup
			Display.getDefault().asyncExec(() -> canvas.scrollTo(xLocation, yLocation));
		}

	}

}