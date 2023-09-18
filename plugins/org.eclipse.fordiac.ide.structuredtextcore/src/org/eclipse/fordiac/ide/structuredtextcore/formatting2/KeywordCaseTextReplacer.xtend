/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *   Martin Jobst - avoid replacement if contents are identical
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.formatting2

import org.eclipse.xtext.formatting2.internal.AbstractTextReplacer
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment
import org.eclipse.xtext.formatting2.ITextReplacerContext

class KeywordCaseTextReplacer extends AbstractTextReplacer {

	final String replacementText

	new(IFormattableDocument document, ITextSegment region) {
		this(document, region, region.text.toUpperCase)
	}

	new(IFormattableDocument document, ITextSegment region, String replacementText) {
		super(document, region)
		this.replacementText = replacementText
	}

	override createReplacements(ITextReplacerContext context) {
		if(region.text != replacementText) {
			context.addReplacement(region.replaceWith(replacementText))
		}
		return context
	}

}
