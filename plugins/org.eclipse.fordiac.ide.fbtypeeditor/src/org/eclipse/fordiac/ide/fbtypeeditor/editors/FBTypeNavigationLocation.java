/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *   Martin Erich Jobst - fix handling of sub-locations
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.fordiac.ide.model.ui.editors.GraphicalViewerNavigationLocationData;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.NavigationLocation;

public final class FBTypeNavigationLocation extends NavigationLocation {

	final IEditorPart activeEditor;
	final INavigationLocation subNavigationLocation;
	final GraphicalViewerNavigationLocationData graphicalViewerLocationData;

	protected FBTypeNavigationLocation(final FBTypeEditor editorPart) {
		super(editorPart);
		activeEditor = editorPart.getActiveEditor();
		subNavigationLocation = createSubNavigationLocation(activeEditor);
		if (subNavigationLocation == null) {
			// if we don't have a sub navigation location check if it is graphical viewer
			// and capture this data
			graphicalViewerLocationData = createGraphicalViewerLocationData(activeEditor);
		} else {
			graphicalViewerLocationData = null;
		}
	}

	private static INavigationLocation createSubNavigationLocation(final IEditorPart activeEditor) {
		if (activeEditor instanceof INavigationLocationProvider) {
			return ((INavigationLocationProvider) activeEditor).createNavigationLocation();
		}
		return null;
	}

	private static GraphicalViewerNavigationLocationData createGraphicalViewerLocationData(
			final IEditorPart activeEditor) {
		final GraphicalViewer viewer = activeEditor.getAdapter(GraphicalViewer.class);
		return (viewer != null) ? new GraphicalViewerNavigationLocationData(viewer) : null;
	}

	@Override
	protected FBTypeEditor getEditorPart() {
		final IEditorPart part = super.getEditorPart();
		// part may be null
		return (part instanceof FBTypeEditor) ? (FBTypeEditor) part : null;
	}

	@Override
	public String getText() {
		final FBTypeEditor typeEditor = getEditorPart();
		if (typeEditor != null) {
			if (subNavigationLocation != null) {
				return subNavigationLocation.getText();
			}
			return typeEditor.getTitle() + "." + activeEditor.getTitle(); //$NON-NLS-1$
		}
		return super.getText();
	}

	@Override
	public void saveState(final IMemento memento) {
		// currently we are not saving the state
	}

	@Override
	public void restoreState(final IMemento memento) {
		// currently we are not restoring the state here
	}

	@Override
	public void restoreLocation() {
		final FBTypeEditor part = getEditorPart();
		if (part != null) {
			part.setActiveEditor(activeEditor);
			if (subNavigationLocation != null) {
				subNavigationLocation.restoreLocation();
			} else if (graphicalViewerLocationData != null) {
				restoreGraphicalViewerData(part);
			}
		}
	}

	private void restoreGraphicalViewerData(final FBTypeEditor part) {
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			graphicalViewerLocationData.restoreGraphicalViewerData(viewer);
		}
	}

	@Override
	public boolean mergeInto(final INavigationLocation currentLocation) {
		if (currentLocation instanceof final FBTypeNavigationLocation currentFBTypeLocation) {
			final FBTypeEditor typeEditor = getEditorPart();
			if ((typeEditor != null) && (typeEditor.equals(currentFBTypeLocation.getEditorPart()))
					&& (activeEditor.equals(currentFBTypeLocation.activeEditor))) {
				if (subNavigationLocation != null) {
					return subNavigationLocation.mergeInto(currentFBTypeLocation.subNavigationLocation);
				}
				if (graphicalViewerLocationData != null) {
					return graphicalViewerLocationData.equals(currentFBTypeLocation.graphicalViewerLocationData);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		if (subNavigationLocation != null) {
			subNavigationLocation.update();
		}
	}

	@Override
	public void releaseState() {
		super.releaseState();
		if (subNavigationLocation != null) {
			subNavigationLocation.releaseState();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (subNavigationLocation != null) {
			subNavigationLocation.dispose();
		}
	}
}
