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

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

public class QualifiedNameReplacer implements ITextReplacer {

	private final ITextSegment region;
	private final String replacementText;

	public QualifiedNameReplacer(final ITextSegment region, final INamedElement element) {
		this.region = region;
		this.replacementText = element.getName();
	}

	@Override
	public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
		if (!region.getText().equals(replacementText) && region.getText().equalsIgnoreCase(replacementText)) {
			context.addReplacement(region.replaceWith(replacementText));
		}
		return context;
	}

	@Override
	public ITextSegment getRegion() {
		return region;
	}
}
