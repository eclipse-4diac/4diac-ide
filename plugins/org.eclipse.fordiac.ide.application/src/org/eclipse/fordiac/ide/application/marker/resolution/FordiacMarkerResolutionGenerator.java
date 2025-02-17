/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
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
 *   Martin Erich Jobst
 *    - add resolutions for configurable FBs
 *   Paul Stemmer
 *    - add resolutions for Attributes
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class FordiacMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {
		return switch (FordiacErrorMarker.getCode(marker)) {
		case LibraryElementValidator.ATTRIBUTE__VALIDATE_ATTRIBUTE_DECLARATION -> Stream.concat(
				Stream.of(new CreateAttributeMarkerResolution(marker), new ChangeAttributeMarkerResolution(marker)),
				BestFitAttributeMarkerResolution.createResolutions(marker)).toArray(IMarkerResolution[]::new);
		case LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE,
				LibraryElementValidator.CONFIGURABLE_FB__VALIDATE_DATA_TYPE ->
			Stream.concat(
					Stream.of(new CreateDataTypeMarkerResolution(marker), new ChangeDataTypeMarkerResolution(marker)),
					BestFitDataTypeMarkerResolution.createResolutions(marker)).toArray(IMarkerResolution[]::new);
		case LibraryElementValidator.TYPED_CONFIGUREABLE_OBJECT__VALIDATE_TYPE ->
			Stream.concat(Stream.of(new CreateMissingFBMarkerResolution(marker), new ChangeFBMarkerResolution(marker)),
					BestFitFBMarkerResolution.createResolutions(marker)).toArray(IMarkerResolution[]::new);
		default -> new IMarkerResolution[0];
		};
	}

	@Override
	public boolean hasResolutions(final IMarker marker) {
		final int code = FordiacErrorMarker.getCode(marker);
		return LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(FordiacErrorMarker.getSource(marker))
				&& (LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE == code
						|| LibraryElementValidator.TYPED_CONFIGUREABLE_OBJECT__VALIDATE_TYPE == code
						|| LibraryElementValidator.CONFIGURABLE_FB__VALIDATE_DATA_TYPE == code
						|| LibraryElementValidator.ATTRIBUTE__VALIDATE_ATTRIBUTE_DECLARATION == code);
	}
}
