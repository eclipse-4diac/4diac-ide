/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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

import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.ui.editors.text.EditorsUI;

public class FordiacMarkerGraphicalAnnotationModel extends ResourceMarkerGraphicalAnnotationModel {

	private final Supplier<LibraryElement> libraryElementSupplier;

	public FordiacMarkerGraphicalAnnotationModel(final IResource resource,
			final Supplier<LibraryElement> libraryElementSupplier) {
		super(resource);
		this.libraryElementSupplier = Objects.requireNonNull(libraryElementSupplier);
		reload();
	}

	@Override
	protected GraphicalMarkerAnnotation createMarkerAnnotation(final IMarker marker) {
		final String type = EditorsUI.getAnnotationTypeLookup().getAnnotationType(marker);
		if (type != null) {
			final Object target = findTarget(marker);
			if (target != null) {
				return new GraphicalMarkerAnnotation(marker, type, target);
			}
		}
		return null;
	}

	protected Object findTarget(final IMarker marker) {
		try {
			return FordiacErrorMarker.getTargetRelative(marker, libraryElementSupplier.get());
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	protected boolean isTargetChanged(final IMarkerDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& !Objects.equals(delta.getAttribute(FordiacErrorMarker.TARGET_URI, null),
						delta.getMarker().getAttribute(FordiacErrorMarker.TARGET_URI, null));
	}
}
