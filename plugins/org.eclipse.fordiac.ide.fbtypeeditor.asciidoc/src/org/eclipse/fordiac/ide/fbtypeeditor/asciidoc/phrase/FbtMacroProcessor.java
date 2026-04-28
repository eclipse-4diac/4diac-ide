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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.mylyn.wikitext.parser.Attributes;
import org.eclipse.mylyn.wikitext.parser.DocumentBuilder.BlockType;
import org.eclipse.mylyn.wikitext.parser.DocumentBuilder.SpanType;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElementProcessor;

@SuppressWarnings("restriction")
public class FbtMacroProcessor extends PatternBasedElementProcessor {

	private static final String THIS = "THIS"; //$NON-NLS-1$
	private static final String INTERFACE = "interface"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String TYPE = "type"; //$NON-NLS-1$
	private static final String PACKAGE = "package"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private final LibraryElement target;

	public FbtMacroProcessor(final LibraryElement target) {
		this.target = target;
	}

	String getIdentifier() {
		return group(1);
	}

	String getMacroParams() {
		return group(2);
	}

	@Override
	public void emit() {
		final EObject targetElement = getTargetElement();
		if (targetElement == null) {
			emitTargetError();
			return;
		}

		switch (getMacroParams()) {
		case INTERFACE -> emitInterface(targetElement);
		case NAME -> emitTargetName(targetElement);
		case TYPE -> emitTargetType(targetElement);
		case PACKAGE -> emitTargetPackage(targetElement);
		case COMMENT -> emitTargetComment(targetElement);
		default -> emitWrongParamError();
		}

	}

	private void emitInterface(final EObject targetElement) {
		emitError("interface not yet implemented"); //$NON-NLS-1$
	}

	private void emitTargetName(final EObject targetElement) {
		if (!(targetElement instanceof final INamedElement namedEl)) {
			emitError("Target element has no name"); //$NON-NLS-1$
			return;
		}
		emitTextElement(namedEl.getName());
	}

	private void emitTargetType(final EObject targetElement) {
		if (targetElement instanceof final ITypedElement typedEl) {
			emitTextElement(typedEl.getFullTypeName());
			return;
		}
		if (targetElement instanceof final LibraryElement libEl) {
			emitTextElement(libEl.getTypeEntry().getFullTypeName());
			return;
		}
		emitError("Target element has no type"); //$NON-NLS-1$
	}

	private void emitTargetPackage(final EObject targetElement) {
		if (!(targetElement instanceof final LibraryElement libEl)) {
			emitError("Target element has no comment"); //$NON-NLS-1$
			return;
		}
		emitTextElement(libEl.getTypeEntry().getPackageName());
	}

	private void emitTargetComment(final EObject targetElement) {
		if (!(targetElement instanceof final INamedElement namedEl)) {
			emitError("Target element has no comment"); //$NON-NLS-1$
			return;
		}
		emitTextElement(namedEl.getComment());
	}

	private EObject getTargetElement() {
		final String identifier = getIdentifier();
		final int childSep = identifier.indexOf('.');

		final LibraryElement libElement = getLibElement(getLibElementName(identifier, childSep));

		if (childSep == -1) {
			return libElement;
		}

		if (libElement instanceof final FBType fbType) {
			return fbType.findByQualifiedName(identifier.substring(childSep + 1, identifier.length())).findFirst()
					.orElse(null);
		}
		return null;
	}

	private static String getLibElementName(final String identifier, final int childSep) {
		if (childSep == -1) {
			return identifier;
		}
		return identifier.substring(0, childSep);
	}

	private LibraryElement getLibElement(final String libElementName) {
		if (THIS.equals(libElementName)) {
			return target;
		}
		final TypeEntry typeEntry = target.getTypeLibrary().find(libElementName);
		return (typeEntry != null) ? typeEntry.getType() : null;
	}

	private void emitTargetError() {
		emitError("Couldn't find fbt macro target element: " + getIdentifier()); //$NON-NLS-1$
	}

	private void emitWrongParamError() {
		emitError("Wrong fbt macro parameter: " + getMacroParams()); //$NON-NLS-1$
	}

	private void emitError(final String errorMsg) {
		builder.beginBlock(BlockType.WARNING, new Attributes());
		builder.characters(errorMsg);
		builder.endBlock();
	}

	private void emitTextElement(final String text) {
		builder.beginSpan(SpanType.CODE, new Attributes());
		builder.characters(text);
		builder.endSpan();
	}

}
