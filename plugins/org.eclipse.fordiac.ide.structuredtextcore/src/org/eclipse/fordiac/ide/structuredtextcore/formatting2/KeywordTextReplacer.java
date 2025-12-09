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
package org.eclipse.fordiac.ide.structuredtextcore.formatting2;

import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

public class KeywordTextReplacer implements ITextReplacer {

	private final ITextSegment region;
	private final String replacementText;

	public KeywordTextReplacer(final ITextSegment region, final String replacementText) {
		this.region = region;
		this.replacementText = replacementText;
	}

	@Override
	public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
		if (!region.getText().equals(replacementText)) {
			context.addReplacement(region.replaceWith(replacementText));
		}
		return context;
	}

	@Override
	public ITextSegment getRegion() {
		return region;
	}
}
