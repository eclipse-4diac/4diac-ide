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
 *   Franz Höpfinger - test for TypeHash import exclusion
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.search.st;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.CrossReferenceMatcher;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "static-method" })
class AttributeSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testReferenceSearch() {
		final SimpleFBType type = createSimpleFBType("Test");
		final VarDeclaration varDeclarationConst = createVarDeclaration("INTERNALCONST1", ElementaryTypes.DINT, false,
				"21");
		final VarDeclaration varDeclarationConst2 = createVarDeclaration("INTERNALCONST2", ElementaryTypes.DINT, false,
				"42");
		type.getInternalVars().add(varDeclarationConst);
		type.getInternalVars().add(varDeclarationConst2);
		type.getAttributes().add(createAttribute("TestAttribute", ElementaryTypes.DINT, "INTERNALCONST2 * 2"));
		assertNoMatch(type.getAttributes().getFirst(), new CrossReferenceMatcher(varDeclarationConst));
		assertMatch(type.getAttributes().getFirst(), new CrossReferenceMatcher(varDeclarationConst2), 0, 0, 14);
	}

	@Test
	void testTypeHashAttributeExcludedFromImportedNamespaces() {
		final Attribute attribute = createDeclaredAttribute(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_FULL_NAME,
				TypeLibraryTags.TYPE_HASH_ATTRIBUTE_NAME, "eclipse4diac::core");
		final var namespaces = ISearchFactory.createSearchSupport(attribute, Attribute.class).getImportedNamespaces();
		assertFalse(namespaces.contains("eclipse4diac::core::TypeHash"),
				"TypeHash must not be reported as an imported namespace");
	}

	@Test
	void testOtherAttributeIncludedInImportedNamespaces() {
		final Attribute attribute = createDeclaredAttribute("some::package::OtherAttribute", "OtherAttribute",
				"some::package");
		final var namespaces = ISearchFactory.createSearchSupport(attribute, Attribute.class).getImportedNamespaces();
		assertTrue(namespaces.contains("some::package::OtherAttribute"),
				"a non-TypeHash attribute declaration must still be reported as an imported namespace");
	}

	private static Attribute createDeclaredAttribute(final String attributeName, final String declarationName,
			final String packageName) {
		final AttributeDeclaration declaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		declaration.setName(declarationName);
		final var compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();
		compilerInfo.setPackageName(packageName);
		declaration.setCompilerInfo(compilerInfo);
		final Attribute attribute = createAttribute(attributeName, ElementaryTypes.STRING, "''");
		attribute.setAttributeDeclaration(declaration);
		return attribute;
	}
}
