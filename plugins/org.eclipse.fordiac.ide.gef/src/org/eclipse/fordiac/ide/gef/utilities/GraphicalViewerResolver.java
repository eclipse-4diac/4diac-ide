/*******************************************************************************
 * Copyright (c) 2026 HR Agrartechnik
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Moritz Ortmeier - initial API and implementation, resolving the viewer of
 *                     the visible page of multi-page and breadcrumb editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * Resolves the {@link GraphicalViewer} that is actually visible for a given
 * editor part, recursing into multi-page (FormEditor) and breadcrumb
 * (AbstractBreadCrumbEditor, e.g. Subapp navigation) editors so callers get
 * the currently displayed FB Network rather than an outer container editor
 * that has no viewer of its own.
 */
public final class GraphicalViewerResolver {

	private GraphicalViewerResolver() {
		throw new UnsupportedOperationException();
	}

	public static GraphicalViewer resolveActiveViewer(final IEditorPart editor) {
		if (editor == null) {
			return null;
		}
		if (editor instanceof final FormEditor formEditor) {
			final IEditorPart activePage = formEditor.getActiveEditor();
			if (activePage != null) {
				return resolveActiveViewer(activePage);
			}
		}
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			return viewer;
		}
		if (editor instanceof final AbstractBreadCrumbEditor breadcrumb) {
			return resolveActiveViewer(breadcrumb.getActiveEditor());
		}
		return null;
	}
}
