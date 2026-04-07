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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public class LibraryValidation {

	// TODO move parts that do not depend on workspace to EMF validator
	public static IStatus validateVersions(final Manifest manifest, final IProject project)
			throws OperationCanceledException {
		final List<ErrorMarkerBuilder> errorMarkers = new ArrayList<>();
		final var dependencies = manifest.getDependencies();

		if (dependencies != null) {
			errorMarkers.addAll(dependencies.getRequired().stream()
					.filter(r -> !VersionComparator.isValidRange(r.getVersion()))
					.map(r -> LibraryMarkerFactory.createInvalidVersionMarker(r.getSymbolicName(), r.getVersion()))
					.toList());
		}

		final var product = manifest.getProduct();
		if (product != null && product.getVersionInfo() != null
				&& !VersionComparator.isValidRange(product.getVersionInfo().getVersion())) {
			errorMarkers.add(LibraryMarkerFactory.createInvalidVersionMarker(product.getSymbolicName(),
					product.getVersionInfo().getVersion()));
		}

		FordiacMarkerHelper.updateMarkers(project.getFile(LibraryManager.MANIFEST), FordiacErrorMarker.LIBRARY_MARKER,
				errorMarkers, true);

		return errorMarkers.isEmpty() ? Status.OK_STATUS : Status.CANCEL_STATUS;
	}

	private LibraryValidation() {
		throw new UnsupportedOperationException();
	}

}
