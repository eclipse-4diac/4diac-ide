/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.phrase;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElement;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElementProcessor;

@SuppressWarnings("restriction")
public class FbtMacroPatternElement extends PatternBasedElement {

	private final LibraryElement target;

	public FbtMacroPatternElement(final LibraryElement target) {
		this.target = target;
	}

	@Override
	protected String getPattern(final int groupOffset) {
		return "fbt:([^\\[]*)\\[(.*?)\\]"; //$NON-NLS-1$
	}

	@Override
	protected int getPatternGroupCount() {
		return 2;
	}

	@Override
	protected PatternBasedElementProcessor newProcessor() {
		return new FbtMacroProcessor(target);
	}

}
