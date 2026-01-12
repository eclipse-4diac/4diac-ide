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

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.NavigationLocation;

public class BreadcrumbNavigationLocation extends NavigationLocation {

	private final String breadcrumbPath;
	private GraphicalViewerNavigationLocationData viewerData = null;

	protected BreadcrumbNavigationLocation(final AbstractBreadCrumbEditor editorPart) {
		super(editorPart);
		this.breadcrumbPath = editorPart.getBreadcrumb().serializePath();

		final GraphicalViewer viewer = editorPart.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewerData = new GraphicalViewerNavigationLocationData(viewer);
		}
	}

	@Override
	public String getText() {
		final String breadCrumbPath = generateItemPath(breadcrumbPath).substring(1);
		if (viewerData != null && viewerData.getSelectedElementQN() != null) {
			return breadCrumbPath.split("\\.")[0] + '.' + viewerData.getSelectedElementQN(); //$NON-NLS-1$
		}
		return breadCrumbPath;
	}

	public static String generateItemPath(final String breadcrumbPath) {
		return breadcrumbPath.replace('/', '.');
	}

	private String getBreadCrumbPath() {
		return breadcrumbPath;
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
		if (getEditorPart() instanceof final AbstractBreadCrumbEditor breadcrumbEditor) {
			breadcrumbEditor.getBreadcrumb().validateAndOpenPath(breadcrumbPath,
					breadcrumbEditor.getAdapter(LibraryElement.class));

			final GraphicalViewer viewer = breadcrumbEditor.getAdapter(GraphicalViewer.class);
			if ((viewer != null) && (viewerData != null)) {
				viewerData.restoreGraphicalViewerData(breadcrumbEditor, viewer);
			}
		}
	}

	@Override
	public boolean mergeInto(final INavigationLocation currentLocation) {
		if (currentLocation instanceof final BreadcrumbNavigationLocation loc
				&& this.breadcrumbPath.equals(loc.getBreadCrumbPath())) {
			if (viewerData != null) {
				return viewerData.equals(loc.viewerData);
			}
			return true;
		}
		return false;
	}

	@Override
	public void update() {
		// currently not supported
	}

}