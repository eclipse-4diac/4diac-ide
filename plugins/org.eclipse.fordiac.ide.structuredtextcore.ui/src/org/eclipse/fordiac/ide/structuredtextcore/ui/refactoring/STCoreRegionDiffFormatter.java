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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.xtext.formatting2.FormatterPreferences;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccessDiff;
import org.eclipse.xtext.ide.serializer.impl.RegionDiffFormatter;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.preferences.TypedPreferenceValues;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreRegionDiffFormatter extends RegionDiffFormatter {

	@Inject
	@FormatterPreferences
	private IPreferenceValuesProvider preferencesProvider;

	@Override
	protected FormatterRequest createFormatterRequest(final ITextRegionAccess rewritten) {
		final FormatterRequest request = super.createFormatterRequest(rewritten);
		initializePreferences(rewritten, request);
		return request;
	}

	@Override
	protected FormatterRequest createFormatterRequest(final ITextRegionAccessDiff rewritten) {
		final FormatterRequest request = super.createFormatterRequest(rewritten);
		initializePreferences(rewritten, request);
		return request;
	}

	protected void initializePreferences(final ITextRegionAccess rewritten, final FormatterRequest request) {
		final IPreferenceValues preferenceValues = preferencesProvider.getPreferenceValues(rewritten.getResource());
		request.setPreferences(TypedPreferenceValues.castOrWrap(preferenceValues));
	}
}
