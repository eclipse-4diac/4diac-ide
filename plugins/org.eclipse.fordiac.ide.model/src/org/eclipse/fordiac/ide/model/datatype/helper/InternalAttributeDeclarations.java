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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public final class InternalAttributeDeclarations {

	private static final String ATTRIBUTE_DECLARATION_LIB_URI = "internal_attribute_declarations.lib"; //$NON-NLS-1$
	private static final Resource ATTRIBUTE_DECLARATION_RES = new ResourceImpl(
			URI.createURI(ATTRIBUTE_DECLARATION_LIB_URI));

	private static boolean addAttribbuteDeclarationToResource(final AttributeDeclaration decl) {
		return ATTRIBUTE_DECLARATION_RES.getContents().add(decl);
	}

	public static final AttributeDeclaration VAR_CONFIG = createAttributeDeclaration(LibraryElementTags.VAR_CONFIG,
			ElementaryTypes.BOOL);
	public static final AttributeDeclaration VISIBLE = createAttributeDeclaration(LibraryElementTags.ELEMENT_VISIBLE,
			ElementaryTypes.BOOL);
	public static final AttributeDeclaration UNFOLDED = createAttributeDeclaration(
			LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE, ElementaryTypes.BOOL);

	private static AttributeDeclaration createAttributeDeclaration(final String name, final DataType type) {
		final AttributeDeclaration declaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		declaration.setName(name);

		final DirectlyDerivedType directlyDerivedType = DataFactory.eINSTANCE.createDirectlyDerivedType();
		directlyDerivedType.setBaseType(type);
		declaration.setType(directlyDerivedType);

		addAttribbuteDeclarationToResource(declaration);
		return declaration;
	}

	public static AttributeDeclaration getInternalAttributeByName(final String name) {
		return getAllInternalAttributes().stream().filter(decl -> decl.getName().equals(name)).findFirst().orElse(null);
	}

	public static List<AttributeDeclaration> getAllInternalAttributes() {
		return List.of(VAR_CONFIG, VISIBLE, UNFOLDED);
	}

	public static boolean isInternalAttribue(final AttributeDeclaration declaration) {
		if (declaration == null) {
			return false;
		}
		return getAllInternalAttributes().contains(declaration);
	}

	private InternalAttributeDeclarations() {
		throw new UnsupportedOperationException();
	}
}
