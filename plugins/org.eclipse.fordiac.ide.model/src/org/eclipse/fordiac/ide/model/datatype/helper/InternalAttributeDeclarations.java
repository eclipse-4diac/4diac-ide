/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.datatype.helper;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.AttributeInheritMode;
import org.eclipse.fordiac.ide.model.AttributeTarget;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class InternalAttributeDeclarations {

	private static final String ATTRIBUTE_DECLARATION_LIB_URI = "internal_attribute_declarations.lib"; //$NON-NLS-1$
	private static final Resource ATTRIBUTE_DECLARATION_RES = new ResourceImpl(
			URI.createURI(ATTRIBUTE_DECLARATION_LIB_URI));

	private static boolean addAttribbuteDeclarationToResource(final AttributeDeclaration decl) {
		return ATTRIBUTE_DECLARATION_RES.getContents().add(decl);
	}

	public static final AttributeDeclaration INHERIT = createAttributeDeclaration(LibraryElementTags.INHERIT_ATTRIBUTE,
			ElementaryTypes.STRING);

	public static final AttributeDeclaration VAR_CONFIG = createAttributeDeclaration(LibraryElementTags.VAR_CONFIG,
			ElementaryTypes.BOOL, AttributeInheritMode.COPY);
	public static final AttributeDeclaration VISIBLE = createAttributeDeclaration(LibraryElementTags.ELEMENT_VISIBLE,
			ElementaryTypes.BOOL, AttributeInheritMode.COPY);
	public static final AttributeDeclaration INOUT_VISIBLE_OUT = createAttributeDeclaration(
			LibraryElementTags.ELEMENT_INOUTVISIBLEOUT, ElementaryTypes.BOOL, AttributeInheritMode.COPY);

	public static final AttributeDeclaration UNFOLDED = createAttributeDeclaration(
			LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE, ElementaryTypes.BOOL);
	public static final AttributeDeclaration RETAIN = createAttributeDeclaration(LibraryElementTags.RETAIN_ATTRIBUTE,
			ElementaryTypes.USINT);
	public static final AttributeDeclaration TARGET = createTargetAttributeDeclaration();
	public static final AttributeDeclaration NEGATED = createAttributeDeclaration(LibraryElementTags.CONNECTION_NEGATED,
			ElementaryTypes.BOOL);

	private static final List<AttributeDeclaration> allAttributes = List.of(VAR_CONFIG, VISIBLE, INOUT_VISIBLE_OUT,
			UNFOLDED, RETAIN, TARGET, NEGATED, INHERIT);

	private static final Set<AttributeDeclaration> HASH_RELEVANT_ATRTRIBUTES = Set.of(RETAIN, NEGATED);

	private static AttributeDeclaration createAttributeDeclaration(final String name, final DataType type) {
		final AttributeDeclaration declaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		declaration.setName(name);

		final DirectlyDerivedType directlyDerivedType = DataFactory.eINSTANCE.createDirectlyDerivedType();
		directlyDerivedType.setBaseType(type);
		declaration.setType(directlyDerivedType);

		addAttribbuteDeclarationToResource(declaration);
		return declaration;
	}

	private static AttributeDeclaration createAttributeDeclaration(final String name, final DataType type,
			final AttributeInheritMode inheritMode) {
		final AttributeDeclaration declaration = createAttributeDeclaration(name, type);
		declaration.setAttribute(INHERIT, inheritMode.name(), null);
		return declaration;
	}

	private static AttributeDeclaration createTargetAttributeDeclaration() {
		final AttributeDeclaration declaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		declaration.setName(LibraryElementTags.TARGET_ATTRIBUTE_DEFINITION);

		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();

		for (final AttributeTarget target : AttributeTarget.values()) {
			structType.getMemberVariables().add(createTargetMember(target));
		}
		declaration.setType(structType);

		addAttribbuteDeclarationToResource(declaration);
		return declaration;
	}

	private static VarDeclaration createTargetMember(final AttributeTarget target) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		member.setName(target.name());
		member.setType(ElementaryTypes.BOOL);
		final Value val = LibraryElementFactory.eINSTANCE.createValue();
		val.setValue("TRUE"); //$NON-NLS-1$
		member.setValue(val);
		return member;
	}

	public static AttributeDeclaration getInternalAttributeByName(final String name) {
		return getAllInternalAttributes().stream().filter(decl -> decl.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
	}

	public static List<AttributeDeclaration> getAllInternalAttributes() {
		return allAttributes;
	}

	public static boolean isInternalAttribute(final AttributeDeclaration declaration) {
		if (declaration == null) {
			return false;
		}
		return getAllInternalAttributes().contains(declaration);
	}

	public static boolean isInternalAttribute(final Attribute attribute) {
		return getInternalAttributeByName(attribute.getName()) != null;
	}

	public static boolean isHashRelevantAttribute(final AttributeDeclaration declaration) {
		if (declaration == null) {
			return false;
		}
		return HASH_RELEVANT_ATRTRIBUTES.contains(declaration);
	}

	private InternalAttributeDeclarations() {
		throw new UnsupportedOperationException();
	}
}
