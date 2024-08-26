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

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.NavigationLocation;

public class BreadcrumbNavigationLocation extends NavigationLocation {

	// we need to explicitly store the breadcrumb editor as getEditorPart my provide
	// the wrong editor when we are inside
	// a type editor
	private final AbstractBreadCrumbEditor breadCrumbEditor;
	private final Object model;
	private GraphicalViewerNavigationLocationData viewerData = null;
	private final AdapterFactoryContentProvider contentProvider;
	private final AdapterFactoryLabelProvider labelProvider;

	protected BreadcrumbNavigationLocation(final AbstractBreadCrumbEditor editorPart, final Object model) {
		super(editorPart);
		this.breadCrumbEditor = editorPart;
		this.model = model;
		contentProvider = editorPart.getBreadcrumb().getContentProvider();
		labelProvider = editorPart.getBreadcrumb().getLabelProvider();
		final GraphicalViewer viewer = editorPart.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewerData = new GraphicalViewerNavigationLocationData(viewer);
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
		if (model == null || (model instanceof org.eclipse.emf.ecore.resource.Resource)) {
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
	public void saveState(final IMemento memento) {
		// currently we are not saveing the state
	}

	@Override
	public void restoreState(final IMemento memento) {
		// currently we are not restoring the state here
	}

	@Override
	public void restoreLocation() {
		if (breadCrumbEditor.getBreadcrumb().getActiveItem().getModel().equals(model)) {
			return;
		}

		breadCrumbEditor.getBreadcrumb().setInput(model);
		final GraphicalViewer viewer = breadCrumbEditor.getAdapter(GraphicalViewer.class);
		if ((viewer != null) && (viewerData != null)) {
			viewerData.restoreGraphicalViewerData(viewer);
		}
	}

	@Override
	public boolean mergeInto(final INavigationLocation currentLocation) {
		if ((currentLocation instanceof final BreadcrumbNavigationLocation loc) && (this.model == loc.getModel())) {
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