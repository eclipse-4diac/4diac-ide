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
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.phrase.FbtMacroPatternElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.mylyn.wikitext.parser.markup.AbstractMarkupLanguage.PatternBasedSyntax;
import org.eclipse.mylyn.wikitext.parser.markup.MarkupLanguageConfiguration;

public class FordiacAsciiDocLanguageConfiguration extends MarkupLanguageConfiguration {

	LibraryElement libraryElement;

	public FordiacAsciiDocLanguageConfiguration() {
		// needed for the clone implementation
	}

	public FordiacAsciiDocLanguageConfiguration(final LibraryElement libraryElement) {
		this.libraryElement = libraryElement;
	}

	@Override
	public void addPhraseModifierExtensions(final PatternBasedSyntax phraseModifierSyntax) {
		phraseModifierSyntax.add(new FbtMacroPatternElement(libraryElement));
	}

	@Override
	public MarkupLanguageConfiguration clone() {
		final FordiacAsciiDocLanguageConfiguration clone = (FordiacAsciiDocLanguageConfiguration) super.clone();
		clone.libraryElement = this.libraryElement;
		return clone;
	}

}
