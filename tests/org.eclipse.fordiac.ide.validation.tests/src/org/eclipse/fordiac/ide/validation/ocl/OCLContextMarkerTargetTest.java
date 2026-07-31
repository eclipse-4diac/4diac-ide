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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.ocl.ecore.OCL;
import org.junit.jupiter.api.Test;

class OCLContextMarkerTargetTest {

	@Test
	void allInstancesDoesNotChangeTheContextMarkerTarget() throws CoreException, IOException {
		final StructuredType context = DataFactory.eINSTANCE.createStructuredType();
		final ConfigurableMoveFB referencedInstance = LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		referencedInstance.setDataType(context);
		try (OCLTestFile testFile = OCLTestFile.load(
				"OCLContextMarkerTargetTest", "StructuredTypeContext.ocl")) { //$NON-NLS-1$ //$NON-NLS-2$
			final OCL ocl = testFile.ocl();
			OCLValidationSession.setValidationExtent(ocl, List.of(context, referencedInstance));
			final OCLConstraintDefinition definition = testFile.parseDefinitions().get(0);
			final OCLConstraintEvaluator evaluator = new OCLConstraintEvaluator(ocl,
					error -> fail(error.message()));

			assertTrue(definition.appliesTo(context.eClass()));
			final OCLDiagnostic diagnostic = evaluator.evaluate(context, definition).orElseThrow();

			assertEquals("Structured type is used by a ConfigurableFB", diagnostic.message()); //$NON-NLS-1$
			assertSame(context, diagnostic.markerTarget());
		}
	}
}
