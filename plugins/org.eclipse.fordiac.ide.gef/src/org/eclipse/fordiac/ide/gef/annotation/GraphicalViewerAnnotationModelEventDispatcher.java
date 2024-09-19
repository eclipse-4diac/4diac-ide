/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.widgets.Display;

public class GraphicalViewerAnnotationModelEventDispatcher implements GraphicalAnnotationModelListener {

	private final GraphicalViewer viewer;

	public GraphicalViewerAnnotationModelEventDispatcher(final GraphicalViewer viewer) {
		Objects.requireNonNull(viewer);
		this.viewer = viewer;
	}

	@Override
	public void modelChanged(final GraphicalAnnotationModelEvent event) {
		Display.getDefault().execute(() -> dispatch(event));
	}

	protected void dispatch(final GraphicalAnnotationModelEvent event) {
		Stream.of(event.getAdded(), event.getRemoved(), event.getChanged()).flatMap(Collection::stream)
				.map(GraphicalAnnotation::getTarget).distinct().forEachOrdered(target -> dispatch(target, event));
	}

	protected void dispatch(final Object target, final GraphicalAnnotationModelEvent event) {
		final AnnotableGraphicalEditPart editPart = findEditPart(target);
		if (editPart != null) {
			editPart.updateAnnotations(event);
		}
	}

	protected AnnotableGraphicalEditPart findEditPart(final Object target) {
		if ((target != null)
				&& (viewer.getEditPartForModel(target) instanceof final AnnotableGraphicalEditPart editPart)) {
			return editPart;
		}
		return null;
	}

	public GraphicalViewer getViewer() {
		return viewer;
	}
}
