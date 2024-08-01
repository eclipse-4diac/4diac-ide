/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public abstract class AbstractErrorMarkerResolution extends WorkbenchMarkerResolution {

	private final String label;
	protected final IMarker marker;

	protected AbstractErrorMarkerResolution(final String label, final IMarker marker) {
		this.label = label;
		this.marker = marker;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return getLabel();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IMarker[] findOtherMarkers(final IMarker[] markers) {
		return Stream.of(markers)
				.filter(other -> LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(FordiacErrorMarker.getSource(other))
						&& FordiacErrorMarker.getCode(marker) == FordiacErrorMarker.getCode(other)
						&& Arrays.equals(FordiacErrorMarker.getData(other), FordiacErrorMarker.getData(marker)))
				.toArray(IMarker[]::new);
	}

	public TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(marker.getResource().getProject());
	}
}