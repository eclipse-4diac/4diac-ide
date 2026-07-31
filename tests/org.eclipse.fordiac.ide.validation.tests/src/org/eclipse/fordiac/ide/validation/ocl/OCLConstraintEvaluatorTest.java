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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.ocl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.ocl.ecore.OCL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OCLConstraintEvaluatorTest {

	private OCLTestFile testFile;
	private OCL ocl;
	private List<OCLConstraintDefinition> definitions;
	private List<OCLConstraintEvaluator.ConstraintError> errors;
	private OCLConstraintEvaluator evaluator;
	private BaseFBType context;

	@BeforeEach
	void setUp() throws CoreException, java.io.IOException {
		testFile = OCLTestFile.load("OCLConstraintEvaluatorTest", "RichDiagnostics.ocl"); //$NON-NLS-1$ //$NON-NLS-2$
		ocl = testFile.ocl();
		definitions = testFile.parseDefinitions();
		errors = new ArrayList<>();
		evaluator = new OCLConstraintEvaluator(ocl, errors::add);
		context = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		context.setName("invalid"); //$NON-NLS-1$
	}

	@AfterEach
	void tearDown() throws CoreException {
		testFile.close();
	}

	@Test
	void evaluatesRichDiagnosticOnceAndTargetsSelf() {
		final OCLDiagnostic diagnostic = evaluator.evaluate(context, getDefinition("RichError")).orElseThrow(); //$NON-NLS-1$

		assertEquals("Invalid BaseFBType: invalid", diagnostic.message()); //$NON-NLS-1$
		assertEquals(IMarker.SEVERITY_ERROR, diagnostic.severity());
		assertSame(context, diagnostic.markerTarget());
		assertTrue(errors.isEmpty());
	}

	@Test
	void createsNoDiagnosticForValidStatus() {
		context.setName("valid"); //$NON-NLS-1$

		assertTrue(evaluator.evaluate(context, getDefinition("RichError")).isEmpty()); //$NON-NLS-1$
		assertTrue(errors.isEmpty());
	}

	@Test
	void supportsContextAliasAndWarningSeverity() {
		final OCLDiagnostic diagnostic = evaluator.evaluate(context, getDefinition("RichWarningContext")) //$NON-NLS-1$
				.orElseThrow();

		assertEquals(IMarker.SEVERITY_WARNING, diagnostic.severity());
		assertSame(context, diagnostic.markerTarget());
	}

	@Test
	void suppressesMarkerForZeroSeverity() {
		assertTrue(evaluator.evaluate(context, getDefinition("RichOkSeverity")).isEmpty()); //$NON-NLS-1$
	}

	@Test
	void disablesInvalidTupleDiagnosticAfterFirstFailure() {
		final OCLConstraintDefinition definition = getDefinition("InvalidMarkerTarget"); //$NON-NLS-1$

		assertTrue(evaluator.evaluate(context, definition).isEmpty());
		assertTrue(evaluator.evaluate(context, definition).isEmpty());
		assertEquals(1, errors.size());
		assertSame(definition, errors.get(0).definition());
	}

	@Test
	void preservesLegacyConstraintDiagnostics() {
		final OCLConstraintDefinition legacyDefinition = definitions.stream()
				.filter(definition -> !definition.hasTupleDiagnostic()).findFirst().orElseThrow();

		final OCLDiagnostic diagnostic = evaluator.evaluate(context, legacyDefinition).orElseThrow();

		assertEquals("Self-cycles must be gated by events.", diagnostic.message()); //$NON-NLS-1$
		assertEquals(IMarker.SEVERITY_ERROR, diagnostic.severity());
		assertSame(context, diagnostic.markerTarget());
		assertFalse(legacyDefinition.hasTupleDiagnostic());
	}

	private OCLConstraintDefinition getDefinition(final String name) {
		return definitions.stream().filter(definition -> name.equals(definition.constraint().getName()))
				.findFirst().orElseThrow();
	}
}