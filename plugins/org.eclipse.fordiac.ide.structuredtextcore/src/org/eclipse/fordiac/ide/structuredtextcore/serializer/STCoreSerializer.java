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
package org.eclipse.fordiac.ide.structuredtextcore.serializer;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting2.FormatterPreferences;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.preferences.TypedPreferenceValues;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.impl.Serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
public class STCoreSerializer extends Serializer {

	@Inject(optional = true)
	private Provider<IFormatter2> formatter2Provider;

	@Inject
	private Provider<FormatterRequest> formatterRequestProvider;

	@Inject(optional = true)
	@FormatterPreferences
	private IPreferenceValuesProvider preferencesProvider;

	@Override
	protected void serialize(final EObject obj, final Appendable appendable, final SaveOptions options)
			throws IOException {
		final ITextRegionAccess regionAccess = serializeToRegions(obj);
		final FormatterRequest request = formatterRequestProvider.get();
		request.setFormatUndefinedHiddenRegionsOnly(!options.isFormatting());
		request.setTextRegionAccess(regionAccess);

		if (preferencesProvider != null) {
			final IPreferenceValues preferenceValues = preferencesProvider
					.getPreferenceValues(regionAccess.getResource());
			request.setPreferences(TypedPreferenceValues.castOrWrap(preferenceValues));
		}

		final IFormatter2 formatter2 = formatter2Provider.get();
		final List<ITextReplacement> replacements = formatter2.format(request);
		regionAccess.getRewriter().renderToAppendable(replacements, appendable);
	}

	@Override
	protected int calculateReplaceRegionLength(final ICompositeNode node, final String text) {
		if (hiddenNodeFollows(node) && hasTrailingWhitespace(text)) {
			return node.getTotalLength() + getFollowingNode(node).getTotalLength();
		}
		return node.getTotalLength();
	}

	protected static boolean hasTrailingWhitespace(final String text) {
		return !text.isEmpty() && Character.isWhitespace(text.codePointBefore(text.length()));
	}
}
