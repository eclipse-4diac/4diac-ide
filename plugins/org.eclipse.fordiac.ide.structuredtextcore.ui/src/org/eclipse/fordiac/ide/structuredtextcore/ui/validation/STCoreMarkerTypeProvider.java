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

import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.xtext.ui.validation.LanguageAwareMarkerTypeProvider;
import org.eclipse.xtext.validation.Issue;

public class STCoreMarkerTypeProvider extends LanguageAwareMarkerTypeProvider {

	@Override
	public String getMarkerType(final Issue issue) {
		if (ValidationUtil.isModelValidationIssue(issue)) {
			return FordiacErrorMarker.VALIDATION_MARKER;
		}
		return super.getMarkerType(issue);
	}
}
