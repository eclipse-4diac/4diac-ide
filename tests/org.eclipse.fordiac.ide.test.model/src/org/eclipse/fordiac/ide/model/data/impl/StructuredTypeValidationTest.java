/*******************************************************************************
 * Copyright (c) 2026 Huzaifa Abdul Rehman
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 AND CC0-1.0
 *
 * AI Disclosure: This file was largely generated with OpenAI Codex.
 * The AI-generated portions are made available under CC0-1.0.
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.data.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.util.DataValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.impl.DataTypeEntryImpl;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class StructuredTypeValidationTest {

	@Test
	void reportsDirectCircularReference() {
		final StructuredType type = structure("Cycle"); //$NON-NLS-1$
		member(type, type);

		assertCircularReference(type, "Cycle -> Cycle"); //$NON-NLS-1$
	}

	@Test
	void reportsMutualCircularReferences() {
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		member(first, second);
		member(second, first);

		assertCircularReference(first, "CycleA -> CycleB -> CycleA"); //$NON-NLS-1$
		assertCircularReference(second, "CycleB -> CycleA -> CycleB"); //$NON-NLS-1$
	}

	@Test
	void reportsQualifiedCircularReferencePath() {
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		PackageNameHelper.setPackageName(first, "package"); //$NON-NLS-1$
		PackageNameHelper.setPackageName(second, "package"); //$NON-NLS-1$
		member(first, second);
		member(second, first);

		assertCircularReference(first, "package::CycleA -> package::CycleB -> package::CycleA"); //$NON-NLS-1$
	}

	@Test
	void reportsLongerCircularReference() {
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		final StructuredType third = structure("CycleC"); //$NON-NLS-1$
		member(first, second);
		member(second, third);
		member(third, first);

		assertCircularReference(first, "CycleA -> CycleB -> CycleC -> CycleA"); //$NON-NLS-1$
	}

	@Test
	void reportsCircularReferenceInNestedMemberType() {
		final StructuredType root = structure("Root"); //$NON-NLS-1$
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		member(root, first);
		member(first, second);
		member(second, first);

		assertCircularReference(root, "Root -> CycleA -> CycleB -> CycleA"); //$NON-NLS-1$
	}

	@Test
	void reportsCircularReferenceThroughDerivedType() {
		final StructuredType root = structure("Cycle"); //$NON-NLS-1$
		final DirectlyDerivedType alias = DataFactory.eINSTANCE.createDirectlyDerivedType();
		alias.setName("Alias"); //$NON-NLS-1$
		alias.setBaseType(root);
		member(root, alias);

		assertCircularReference(root, "Cycle -> Alias -> Cycle"); //$NON-NLS-1$
	}

	@Test
	void reportsCircularReferenceThroughArrayType() {
		final StructuredType root = structure("Cycle"); //$NON-NLS-1$
		final ArrayType array = DataFactory.eINSTANCE.createArrayType();
		array.setBaseType(root);
		member(root, array);

		assertCircularReference(root, "Cycle -> ArrayType -> Cycle"); //$NON-NLS-1$
	}

	@Test
	void reportsCircularReferenceInEditorCopy() {
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		final DataTypeEntryImpl entry = new DataTypeEntryImpl();
		entry.setType(first);
		new DataTypeEntryImpl().setType(second);
		member(first, second);
		member(second, first);

		final StructuredType copy = (StructuredType) entry.copyType();
		final BasicDiagnostic diagnostics = new BasicDiagnostic();
		assertFalse(copy.validateNoCircularReferences(diagnostics, Map.of()));
		assertCircularReference(copy, diagnostics, "CycleA -> CycleB -> CycleA"); //$NON-NLS-1$
	}

	@Test
	void acceptsSharedAcyclicMemberTypes() {
		final StructuredType root = structure("Root"); //$NON-NLS-1$
		final StructuredType first = structure("BranchA"); //$NON-NLS-1$
		final StructuredType second = structure("BranchB"); //$NON-NLS-1$
		final StructuredType leaf = structure("Leaf"); //$NON-NLS-1$
		member(root, first);
		member(root, second);
		member(first, leaf);
		member(second, leaf);

		assertEquals(Diagnostic.OK, Diagnostician.INSTANCE.validate(root).getSeverity());
	}

	@Test
	void acceptsTypeAfterCircularMemberIsRemoved() {
		final StructuredType first = structure("CycleA"); //$NON-NLS-1$
		final StructuredType second = structure("CycleB"); //$NON-NLS-1$
		member(first, second);
		member(second, first);
		assertCircularReference(first, "CycleA -> CycleB -> CycleA"); //$NON-NLS-1$

		second.getMemberVariables().clear();

		assertEquals(Diagnostic.OK, Diagnostician.INSTANCE.validate(first).getSeverity());
	}

	@Test
	void validatesWithoutDiagnosticCollector() {
		final StructuredType type = structure("Cycle"); //$NON-NLS-1$
		member(type, type);
		assertFalse(type.validateNoCircularReferences(null, Map.of()));

		type.getMemberVariables().clear();

		assertTrue(type.validateNoCircularReferences(null, Map.of()));
	}

	private static StructuredType structure(final String name) {
		final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
		type.setName(name);
		return type;
	}

	private static VarDeclaration member(final StructuredType owner, final DataType type) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		member.setName("member" + owner.getMemberVariables().size()); //$NON-NLS-1$
		member.setType(type);
		owner.getMemberVariables().add(member);
		return member;
	}

	private static void assertCircularReference(final StructuredType type, final String path) {
		assertCircularReference(type, Diagnostician.INSTANCE.validate(type), path);
	}

	private static void assertCircularReference(final StructuredType type, final Diagnostic diagnostics,
			final String path) {
		assertEquals(Diagnostic.ERROR, diagnostics.getSeverity());
		final Diagnostic diagnostic = diagnostics.getChildren().stream()
				.filter(candidate -> DataValidator.DIAGNOSTIC_SOURCE.equals(candidate.getSource())
						&& candidate.getCode() == DataValidator.STRUCTURED_TYPE__VALIDATE_NO_CIRCULAR_REFERENCES)
				.findFirst().orElseThrow();
		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		assertEquals("Structured type " + type.getName() + " has a circular member reference: " + path, //$NON-NLS-1$ //$NON-NLS-2$
				diagnostic.getMessage());
		assertTrue(diagnostic.getData().getFirst() == type);
		final Map<String, Object> attributes = FordiacMarkerHelper.getDiagnosticAttributes(diagnostic);
		assertEquals(EcoreUtil.getURI(type).toString(), attributes.get(FordiacErrorMarker.TARGET_URI));
		assertEquals(EcoreUtil.getURI(DataPackage.Literals.STRUCTURED_TYPE__MEMBER_VARIABLES).toString(),
				attributes.get(FordiacErrorMarker.TARGET_FEATURE));
	}
}
