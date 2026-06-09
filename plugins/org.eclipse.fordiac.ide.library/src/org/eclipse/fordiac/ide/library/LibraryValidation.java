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

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public class LibraryValidation {

	public static boolean validate(final EObject element, final IProject project) {
		final Diagnostic validationResult = Diagnostician.INSTANCE.validate(element);
		final List<ErrorMarkerBuilder> markerList = validationResult.getChildren().stream()
				.filter(d -> Objects.equals(d.getSource(), LibraryValidator.DIAGNOSTIC_SOURCE))
				.map(LibraryMarkerFactory::forDiagnostic).toList();

		FordiacMarkerHelper.updateMarkers(project.getFile(LibraryManager.MANIFEST), FordiacErrorMarker.LIBRARY_MARKER,
				markerList, true);

		return markerList.stream().noneMatch(b -> b.getSeverity() >= IMarker.SEVERITY_ERROR);
	}

	private LibraryValidation() {
		throw new UnsupportedOperationException();
	}

}
