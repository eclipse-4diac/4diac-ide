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
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.validation.Issue;

public class STCoreMarkerCreator extends MarkerCreator {

	@Override
	public void createMarker(final Issue issue, final IResource resource, final String markerType)
			throws CoreException {
		if (FordiacErrorMarker.isModelMarkerType(markerType)) {
			ErrorMarkerBuilder.createErrorMarkerBuilder(issue.getMessage()).setType(markerType)
					.setSeverity(ValidationUtil.getMarkerSeverity(issue))
					.setCode(ValidationUtil.getModelMarkerCode(issue))
					.setSource(ValidationUtil.getModelMarkerSource(issue))
					.addAdditionalAttributes(ValidationUtil.getModelMarkerAttributes(issue)).createMarker(resource);
		} else {
			super.createMarker(issue, resource, markerType);
		}
	}
}
