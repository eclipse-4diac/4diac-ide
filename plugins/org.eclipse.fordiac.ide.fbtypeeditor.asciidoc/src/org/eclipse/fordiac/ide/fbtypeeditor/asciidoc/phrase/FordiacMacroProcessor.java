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
 *   Andrea Zoitl - Renamed class and updated to support class
 *                  FordiacMacroPatternElement
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.phrase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.mylyn.wikitext.parser.Attributes;
import org.eclipse.mylyn.wikitext.parser.DocumentBuilder.BlockType;
import org.eclipse.mylyn.wikitext.parser.DocumentBuilder.SpanType;
import org.eclipse.mylyn.wikitext.parser.LinkAttributes;
import org.eclipse.mylyn.wikitext.parser.markup.PatternBasedElementProcessor;

public class FordiacMacroProcessor extends PatternBasedElementProcessor {

	public static final String ATP_TYPE_ENTRY_URI = "atp-entry://"; //$NON-NLS-1$
	public static final String FBT_TYPE_ENTRY_URI = "fbt-entry://"; //$NON-NLS-1$
	public static final String GCF_TYPE_ENTRY_URI = "gcf-entry://"; //$NON-NLS-1$

	private static final String THIS = "THIS"; //$NON-NLS-1$
	private static final String INTERFACE = "interface"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String TYPE = "type"; //$NON-NLS-1$
	private static final String PACKAGE = "package"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$
	private static final String URL = "url"; //$NON-NLS-1$
	private static final String ELEMENT = "element"; //$NON-NLS-1$

	private final LibraryElement target;

	public FordiacMacroProcessor(final LibraryElement target) {
		this.target = target;
	}

	String getMacroType() {
		return group(1);
	}

	String getQualifiedName() {
		return group(2);
	}

	String getElementAccess() {
		return group(3);
	}

	String getMacroParams() {
		return group(4);
	}

	// Reconstructs the full identifier path for error messages
	String getIdentifier() {
		final String qName = getQualifiedName();
		final String access = getElementAccess();
		return (access != null) ? qName + access : qName;
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
		case URL -> emitTargetUrl(targetElement);
		case ELEMENT -> emitTargetElement(targetElement);
		default -> emitWrongParamError();
		}
	}

	private void emitInterface(final EObject targetElement) {
		if (!(targetElement instanceof final FBType fbType)) {
			emitError("Target element does not have an interface!"); //$NON-NLS-1$
			return;
		}
		emitLinkedTextElement("Interface of " + fbType.getName(), targetElement); //$NON-NLS-1$
	}

	private void emitTargetName(final EObject targetElement) {
		if (!(targetElement instanceof final INamedElement namedEl)) {
			emitError("Target element has no name!"); //$NON-NLS-1$
			return;
		}
		emitLinkedTextElement(namedEl.getName(), targetElement);
	}

	private void emitTargetType(final EObject targetElement) {
		if (targetElement instanceof final ITypedElement typedEl) {
			if (typedEl.getType() instanceof AnyDerivedType) {
				emitLinkedTextElement(typedEl.getFullTypeName(), typedEl.getType());
			} else {
				emitTextElement(typedEl.getFullTypeName());
			}
			return;
		}
		if (targetElement instanceof final LibraryElement libEl) {
			emitLinkedTextElement(libEl.getTypeEntry().getFullTypeName(), targetElement);
			return;
		}
		emitError("Target element has no type"); //$NON-NLS-1$
	}

	private void emitTargetPackage(final EObject targetElement) {
		if (!(targetElement instanceof final LibraryElement libEl)) {
			emitError("Target element has no package"); //$NON-NLS-1$
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

	private void emitTargetUrl(final EObject targetElement) {
		emitTextElement(createTargetHref(targetElement));
	}

	private void emitTargetElement(final EObject targetElement) {
		if (targetElement instanceof final INamedElement namedEl) {
			emitTextElement(namedEl.getName());
		} else {
			emitTextElement(targetElement.toString());
		}
	}

	private EObject getTargetElement() {
		final LibraryElement libElement = getLibElement(getQualifiedName());

		final String elementAccess = getElementAccess();
		if (elementAccess == null || elementAccess.isEmpty()) {
			return libElement;
		}

		if (libElement instanceof final FBType fbType) {
			// Strip the leading '.' to resolve the path
			String subPath = elementAccess.startsWith(".") ? elementAccess.substring(1) : elementAccess; //$NON-NLS-1$

			// Clean off array subscripts '[]' and bit access indicators (e.g., '.%x1')
			subPath = subPath.replace("\\[\\]", "").replaceAll("%[xbwdl]?\\d+", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			return fbType.findByQualifiedName(subPath).findFirst().orElse(null);
		}
		return null;
	}

	private LibraryElement getLibElement(final String libElementName) {
		// Use case-insensitive check for THIS
		if (THIS.equalsIgnoreCase(libElementName)) {
			// Only return target if the active editor's type matches the macro prefix
			if (isMatchingTarget(getMacroType(), target)) {
				return target;
			}
			return null;
		}

		final MacroType type = MacroType.fromString(getMacroType());
		if (type == null) {
			return null;
		}
		return type.getLibraryElementFromTypeLibrary(target.getTypeLibrary(), libElementName);
	}

	/**
	 * Type-safe macro verification using standard model element instances.
	 */
	private static boolean isMatchingTarget(final String macroType, final LibraryElement target) {
		if (target == null) {
			return false;
		}
		final MacroType type = MacroType.fromString(macroType);
		return type != null && type.matches(target); // Delegated directly to Enum
	}

	private void emitTargetError() {
		emitError("Couldn't find " + getMacroType() + " macro target element: " + getIdentifier()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void emitWrongParamError() {
		emitError("Wrong " + getMacroType() + " macro parameter: " + getMacroParams()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void emitError(final String errorMsg) {
		builder.beginBlock(BlockType.WARNING, new Attributes());
		builder.characters(errorMsg);
		builder.endBlock();
	}

	private void emitLinkedTextElement(final String text, final EObject target) {
		final LinkAttributes linkAttributes = new LinkAttributes();
		linkAttributes.setHref(createTargetHref(target));
		builder.beginSpan(SpanType.LINK, linkAttributes);
		emitTextElement(text);
		builder.endSpan();
	}

	protected void emitTextElement(final String text) {
		builder.beginSpan(SpanType.CODE, new Attributes());
		builder.characters(text);
		builder.endSpan();
	}

	private static String createTargetHref(final EObject target) {
		return FBT_TYPE_ENTRY_URI + EcoreUtil.getURI(target).toString();
	}

	public enum MacroType {
		ADP("adp") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof AdapterType;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib, final String name) {
				return fromTypeEntry(typeLib.getAdapterTypeEntry(name));
			}
		},
		ATP("atp") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof AttributeDeclaration;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getAttributeTypeEntry(libElementName));
			}
		},
		DTP("dtp") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof DataType;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return typeLib.getDataTypeLibrary().getTypeIfExists(libElementName);
			}
		},
		FBT("fbt") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof FBType;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getFBTypeEntry(libElementName));
			}
		},
		FCT("fct") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof FunctionFBType;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getFBTypeEntry(libElementName));
			}
		},
		GCF("gcf") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof GlobalConstants;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getGlobalConstantsEntry(libElementName));
			}
		},
		SUB("sub") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof SubAppType;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getSubAppTypeEntry(libElementName));
			}
		},
		SYS("sys") { //$NON-NLS-1$
			@Override
			public boolean matches(final LibraryElement target) {
				return target instanceof SystemConfiguration;
			}

			@Override
			public LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
					final String libElementName) {
				return fromTypeEntry(typeLib.getSystemEntry(libElementName));
			}
		};

		private final String prefix;

		public String getPrefix() {
			return prefix;
		}

		MacroType(final String prefix) {
			this.prefix = prefix;
		}

		private static LibraryElement fromTypeEntry(final TypeEntry typeEntry) {
			return (typeEntry != null) ? typeEntry.getType() : null;
		}

		/** Safely resolves a string prefix to its corresponding enum value. */
		public static MacroType fromString(final String prefix) {
			for (final MacroType type : values()) {
				if (type.prefix.equalsIgnoreCase(prefix)) {
					return type;
				}
			}
			return null;
		}

		/** Checks if the target matches this specific macro type. */
		public abstract boolean matches(final LibraryElement target);

		/** Resolves the library element for this macro type. */
		public abstract LibraryElement getLibraryElementFromTypeLibrary(final TypeLibrary typeLib,
				final String libElementName);
	}

}
