/*******************************************************************************
 * Copyright (c) 2007, 2024 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     David Green - initial API and implementation
 *     Alexander Fedorov (ArSysOp) - ongoing support
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
