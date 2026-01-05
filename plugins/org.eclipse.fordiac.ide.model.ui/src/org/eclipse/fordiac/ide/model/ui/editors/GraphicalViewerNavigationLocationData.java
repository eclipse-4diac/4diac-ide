/*******************************************************************************
 * Copyright (c) 2021, 2024, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *   Sebastian Hollersbacher - Added selected Element of viewer
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Objects;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

public class GraphicalViewerNavigationLocationData {
	private final double zoom;
	private final Point location;
	private final IEditorPart editor;
	private final String selectedElementQN;

	public GraphicalViewerNavigationLocationData(final IEditorPart editor, final GraphicalViewer viewer) {
		this.editor = editor;
		zoom = getCurrentZoom(viewer);
		location = getViewerLocation(viewer);
		selectedElementQN = viewer.getSelection() instanceof final StructuredSelection structuredSelection
				&& structuredSelection.getFirstElement() instanceof final EditPart ep
				&& ep.getModel() instanceof final INamedElement ie ? ie.getQualifiedName() : null;
	}

	public void restoreGraphicalViewerData(final EditPartViewer viewer) {
		if (viewer.getRootEditPart() instanceof final ScalableFreeformRootEditPart rootEP) {
			rootEP.getZoomManager().setZoom(zoom);
		}

		// we have to wait to set the scroll position until the editor is drawn and the
		// canvas is setup
		if ((viewer.getControl() instanceof final FigureCanvas canvas) && !canvas.isDisposed()) {
			Display.getDefault().syncExec(() -> {
				if (!canvas.isDisposed()) {
					viewer.flush();
					canvas.scrollTo(location.x, location.y);

					if (selectedElementQN != null) {
						final LibraryElement root = editor.getAdapter(LibraryElement.class);
						root.findByQualifiedName(selectedElementQN).map(viewer::getEditPartForModel)
								.filter(Objects::nonNull).forEach(viewer::select);
					}
				}
			});
		}

	}

	public String getSelectedElementQN() {
		return selectedElementQN;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, Double.valueOf(zoom), selectedElementQN);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && this.getClass() == obj.getClass()) {
			final GraphicalViewerNavigationLocationData other = (GraphicalViewerNavigationLocationData) obj;
			return location.equals(other.location) && (0 == Double.compare(zoom, other.zoom)
					&& Objects.equals(selectedElementQN, other.selectedElementQN));
		}
		return false;
	}

	private static double getCurrentZoom(final GraphicalViewer viewer) {
		if (viewer.getRootEditPart() instanceof final ScalableFreeformRootEditPart rootEP) {
			return rootEP.getZoomManager().getZoom();
		}
		return 1.0;
	}

	private static Point getViewerLocation(final GraphicalViewer viewer) {
		if (viewer.getControl() instanceof final FigureCanvas canvas) {
			return canvas.getViewport().getViewLocation();
		}
		return new Point(0, 0);
	}

}