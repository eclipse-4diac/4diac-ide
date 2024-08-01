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

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class FordiacMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {
		return switch (FordiacErrorMarker.getCode(marker)) {
		case LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE -> new IMarkerResolution[] {
				new ChangeDataTypeMarkerResolution(marker), new CreateDataTypeMarkerResolution(marker) };
		case LibraryElementValidator.TYPED_CONFIGUREABLE_OBJECT__VALIDATE_TYPE -> new IMarkerResolution[] {
				new CreateMissingFBMarkerResolution(marker), new ChangeFBMarkerResolution(marker) };
		default -> new IMarkerResolution[0];
		};
	}

	@Override
	public boolean hasResolutions(final IMarker marker) {
		return LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(FordiacErrorMarker.getSource(marker))
				&& (LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE == FordiacErrorMarker.getCode(marker)
						|| LibraryElementValidator.TYPED_CONFIGUREABLE_OBJECT__VALIDATE_TYPE == FordiacErrorMarker
								.getCode(marker));
	}
}
