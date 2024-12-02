/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
		case FordiacNature.MISSING_EXPORT_BUILDER ->
			new IMarkerResolution[] { new MissingExportBuilderMarkerResolution() };
		default -> new IMarkerResolution[0];
		};
	}

	@Override
	public boolean hasResolutions(final IMarker marker) {
		final int code = FordiacErrorMarker.getCode(marker);
		return FordiacNature.class.getName().equals(FordiacErrorMarker.getSource(marker))
				&& FordiacNature.MISSING_EXPORT_BUILDER == code;
	}
}
