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
 *   Andrea Zoitl - Renamed class from FbtMacroPatternElement to
 *                  FordiacMacroPatternElement
 *                - Expanded macro to support not only fbt but also fct, dtp,
 *                  gcf, atp, adp, sub, sys
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.phrase;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElement;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElementProcessor;

public class FordiacMacroPatternElement extends PatternBasedElement {

	private final LibraryElement target;

	// --- GRAMMAR VARIABLES ---
	private static final String LETTER = "[a-zA-Z]"; //$NON-NLS-1$
	private static final String DIGIT = "[0-9]"; //$NON-NLS-1$

	// Identifier = (Letter | ( "_" (Letter | Digit) ) ) ("_"? (Letter | Digit))*,
	private static final String IDENTIFIER = "(?:" + LETTER + "|(?:_(?:" + LETTER + "|" + DIGIT + ")))(?:_?(?:" + LETTER //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			+ "|" + DIGIT + "))*"; //$NON-NLS-1$ //$NON-NLS-2$

	// Package = Identifier ("::" Identifier)*,
	private static final String PACKAGE = "(?:" + IDENTIFIER + "(?:::" + IDENTIFIER + ")*)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	// QualifiedName = (( Package "::" Identifier) | Identifier),
	private static final String QUALIFIED_NAME = "(?:(?:" + PACKAGE + "::" + IDENTIFIER + ")|" + IDENTIFIER + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	// Types = ("fbt" | "fct" | "dtp" | "gcf" | "atp" | "adp" | "sub" | "sys"),
	private static final String TYPES = "(?:fbt|fct|dtp|gcf|atp|adp|sub|sys)"; //$NON-NLS-1$

	// SubAccess = ("." Identifier "[]"?)+,
	private static final String SUB_ACCESS = "(?:\\." + IDENTIFIER + "(?:\\[\\])?)+"; //$NON-NLS-1$ //$NON-NLS-2$

	// BitAccess = "." ("%" ( "x" | "b" | "w" | "d" | "l" ))? Digit+,
	private static final String BIT_ACCESS = "(?:\\.(?:%(?:[xbwdl]))?" + DIGIT + "+)"; //$NON-NLS-1$ //$NON-NLS-2$

	// ElementAccess = SubAccess BitAccess?,
	private static final String ELEMENT_ACCESS = "(?:" + SUB_ACCESS + BIT_ACCESS + "?)"; //$NON-NLS-1$ //$NON-NLS-2$

	// InformationIdentifier = ("name" | "type" | "comment" | "url" | "element" |
	// "interface"),
	private static final String INFORMATION_IDENTIFIER = "(?:name|type|comment|url|element|interface)"; //$NON-NLS-1$

	public FordiacMacroPatternElement(final LibraryElement target) {
		this.target = target;
	}

	@Override
	protected String getPattern(final int groupOffset) {
		// Note: We wrap each of the 4 groups in standard capture parentheses (...) to
		// isolate the sub-matches
		// Macro = Types ":" QualifiedName ElementAccess? "[" InformationIdentifier "]",
		return "(" + TYPES + "):(" + QUALIFIED_NAME + ")(" + ELEMENT_ACCESS + ")?\\[(" + INFORMATION_IDENTIFIER //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ ")\\]"; //$NON-NLS-1$
	}

	@Override
	protected int getPatternGroupCount() {
		return 4; // 4 capture groups (Types, QualifiedName, ElementAccess, InformationIdentifier)
	}

	@Override
	protected PatternBasedElementProcessor newProcessor() {
		return new FordiacMacroProcessor(target);
	}

}
