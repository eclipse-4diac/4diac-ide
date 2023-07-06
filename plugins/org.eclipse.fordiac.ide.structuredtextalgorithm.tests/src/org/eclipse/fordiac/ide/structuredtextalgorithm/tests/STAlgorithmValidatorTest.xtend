/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies GmbH
 *                          Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - linking diagnostics
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.tests

import com.google.inject.Inject
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmValidator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(STAlgorithmInjectorProvider)
class STAlgorithmValidatorTest {
	@Inject extension ParseHelper<STAlgorithmSource> parseHelper
	@Inject extension ValidationTestHelper

	@BeforeAll
	def static void setup() {
		new DataTypeLibrary
	}

	@Test
	def void testDuplicateAlgorithmNameIsForbidden() {
		'''
			ALGORITHM REQ
			END_ALGORITHM
			ALGORITHM REQ
			END_ALGORITHM
		'''.parse.assertError(STAlgorithmPackage.eINSTANCE.STAlgorithm,
			STAlgorithmValidator.DUPLICATE_METHOD_OR_ALGORITHM_NAME, "Duplicate METHOD or ALGORITHM named 'REQ'")
	}

	@Test
	def void testDuplicateMethodNameIsForbidden() {
		'''
			METHOD REQ
			END_METHOD
			METHOD REQ
			END_METHOD
		'''.parse.assertError(STAlgorithmPackage.eINSTANCE.STMethod,
			STAlgorithmValidator.DUPLICATE_METHOD_OR_ALGORITHM_NAME, "Duplicate METHOD or ALGORITHM named 'REQ'")
	}

	@Test
	def void testDuplicateAlgorithmOrMethodNameIsForbidden() {
		'''
			ALGORITHM REQ
			END_ALGORITHM
			METHOD REQ
			END_METHOD
		'''.parse => [
			assertError(STAlgorithmPackage.eINSTANCE.STAlgorithm,
				STAlgorithmValidator.DUPLICATE_METHOD_OR_ALGORITHM_NAME, "Duplicate METHOD or ALGORITHM named 'REQ'")
			assertError(STAlgorithmPackage.eINSTANCE.STMethod, STAlgorithmValidator.DUPLICATE_METHOD_OR_ALGORITHM_NAME,
				"Duplicate METHOD or ALGORITHM named 'REQ'")
		]
	}

	@Test
	def void testDuplicateVariableNameIsForbiddenInMethod_0() {
		'''
			METHOD hubert
			VAR_INPUT
				bol1 : BOOL;
				bol1 : BOOL;
				bol1 : BOOL;
			END_VAR
			END_METHOD
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.DUPLICATE_VARIABLE_NAME,
			"Variable with duplicate name bol1")
	}

	@Test
	def void testDuplicateVariableNameIsForbiddenInMethod_1() {
		'''
			METHOD hubert
			VAR_INPUT
				bol1 : BOOL;
			END_VAR
			VAR_INPUT
				bol1 : BOOL;
			END_VAR
			VAR_TEMP
				bol1 : BOOL;
			END_VAR
			END_METHOD
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.DUPLICATE_VARIABLE_NAME,
			"Variable with duplicate name bol1")
	}

	@Test
	def void testDuplicateVariableNameIsForbiddenInAlgorithm() {
		'''
			ALGORITHM hubert
			VAR_TEMP
				bol1 : BOOL;
				bol1 : BOOL;
				bol1 : BOOL;
			END_VAR
			END_ALGORITHM
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.DUPLICATE_VARIABLE_NAME,
			"Variable with duplicate name bol1")
	}

	@Test
	def void testReturnTypeLinkingDiagnostic() {
		'''
			METHOD test : FLOAT
			END_FUNCTION
		'''.parse.assertError(STAlgorithmPackage.eINSTANCE.STMethod, Diagnostic.LINKING_DIAGNOSTIC,
			"The data type FLOAT is undefined")
	}
}
