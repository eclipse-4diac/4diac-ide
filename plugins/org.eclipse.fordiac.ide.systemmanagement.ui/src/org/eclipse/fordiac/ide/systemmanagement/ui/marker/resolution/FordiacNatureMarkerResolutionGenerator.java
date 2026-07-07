/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - add OCL validation builder resolution
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.marker.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.systemmanagement.nature.FordiacNature;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class FordiacNatureMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {
		return switch (FordiacErrorMarker.getCode(marker)) {
		case FordiacNature.MISSING_EXPORT_BUILDER -> //
			new IMarkerResolution[] { new MissingExportBuilderMarkerResolution() };
		case FordiacNature.MISSING_LIBRARY_BUILDER -> //
			new IMarkerResolution[] { new MissingLibraryBuilderMarkerResolution() };
		case FordiacNature.MISSING_OCL_VALIDATION_BUILDER -> //
			getOCLValidationBuilderResolutions(marker);
		case FordiacNature.WRONG_BUILDER_ORDER -> //
			new IMarkerResolution[] { new WrongBuilderOrderMarkerResolution() };
		default -> new IMarkerResolution[0];
		};
	}

	@Override
	public boolean hasResolutions(final IMarker marker) {
		final int code = FordiacErrorMarker.getCode(marker);
		return FordiacNature.class.getName().equals(FordiacErrorMarker.getSource(marker))
				&& (FordiacNature.MISSING_EXPORT_BUILDER == code || FordiacNature.MISSING_LIBRARY_BUILDER == code
						|| hasOCLValidationBuilderResolution(marker, code)
						|| FordiacNature.WRONG_BUILDER_ORDER == code);
	}

	private static IMarkerResolution[] getOCLValidationBuilderResolutions(final IMarker marker) {
		if (isOCLValidationBuilderEnabled(marker)) {
			return new IMarkerResolution[] { new MissingOCLValidationBuilderMarkerResolution() };
		}
		return new IMarkerResolution[0];
	}

	private static boolean hasOCLValidationBuilderResolution(final IMarker marker, final int code) {
		return FordiacNature.MISSING_OCL_VALIDATION_BUILDER == code && isOCLValidationBuilderEnabled(marker);
	}

	private static boolean isOCLValidationBuilderEnabled(final IMarker marker) {
		return FordiacNature.isOCLValidationBuilderEnabled(marker.getResource().getProject());
	}
}
