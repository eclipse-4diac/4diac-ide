/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians
 * 		- validation in partial bit access
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests

import com.google.inject.Inject
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.^extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(STFunctionInjectorProvider)
class STFunctionValidatorPartialAccessTest {

	@Inject extension ParseHelper<STFunctionSource> parseHelper
	@Inject extension ValidationTestHelper

	@BeforeAll
	def static void setup() {
		new DataTypeLibrary
	}

	@ParameterizedTest(name="Check valid index {0}")
	@MethodSource("generateValidByteIndices")
	def void testValidBytePartialAccess(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : BYTE;
			END_VAR
			x.«input» := 1;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static generateValidByteIndices() {
		val shortBitAccess = (0 .. 7).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		return shortBitAccess + longBitAccess
	}

	@ParameterizedTest(name="Check valid index {0}")
	@MethodSource("generateValidWordIndices")
	def void testValidWordPartialAccess(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : WORD;
			END_VAR
			x.«input» := true;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static generateValidWordIndices() {
		val shortBitAccess = (0 .. 15).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (0 .. 1).map["%B" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess
	}

	@ParameterizedTest(name="Check valid index {0}")
	@MethodSource("generateValidDWordIndices")
	def void testValidDWordPartialAccess(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : DWORD;
			END_VAR
			x.«input» := true;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static generateValidDWordIndices() {
		val shortBitAccess = (0 .. 31).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (0 .. 3).map["%B" + it.toString]
		val wordAccess = (0 .. 1).map["%W" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess + wordAccess
	}

	@ParameterizedTest(name="Check valid index {0}")
	@MethodSource("generateValidLWordIndices")
	def void testValidLWordPartialAccess(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : LWORD;
			END_VAR
			x.«input» := true;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static generateValidLWordIndices() {
		val shortBitAccess = (0 .. 63).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (0 .. 7).map["%B" + it.toString]
		val wordAccess = (0 .. 3).map["%W" + it.toString]
		val dwordAccess = (0 .. 1).map["%D" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess + wordAccess + dwordAccess
	}

	@ParameterizedTest
	@MethodSource("generateTypeNonAnyBitTypeNames")
	def void testNonAnyBitTypesCannotPerformPartialAccess(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : «input»;
			END_VAR
			x.1 := true;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INVALID_FOR_TYPE,
			"Partial access invalid for type " + input + ", which is not of ANY_BIT")
	}

	def static generateTypeNonAnyBitTypeNames() {
		return DataTypeLibrary.nonUserDefinedDataTypes.filter[!(it instanceof AnyBitType)].map[it.name]
	}

	@ParameterizedTest(name="Check invalid index {0}")
	@MethodSource("generateInvalidByteIndices")
	def void testInvalidBytePartialAccess(String input) {
		var exprString = "";
		try {
			Integer.parseInt(input)
			exprString = "%X" + input
		} catch (NumberFormatException e) {
			exprString = input
		}
		'''
			FUNCTION partialTest
			VAR
			x : BYTE;
			END_VAR
			x.«input» := 1;
			END_FUNCTION
		'''.parse.assertError(
			STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INDEX_OUT_OF_RANGE,
			"Partial access index " + exprString + " of out range"
		)
	}

	def static generateInvalidByteIndices() {
		val shortBitAccess = (8 .. 100).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (0 .. 100).map["%B" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess
	}

	@ParameterizedTest(name="Check invalid index {0}")
	@MethodSource("generateInvalidWordIndices")
	def void testInvalidWordPartialAccess(String input) {
		var exprString = "";
		try {
			Integer.parseInt(input)
			exprString = "%X" + input
		} catch (NumberFormatException e) {
			exprString = input
		}
		'''
			FUNCTION partialTest
			VAR
			x : WORD;
			END_VAR
			x.«input» := 1;
			END_FUNCTION
		'''.parse.assertError(
			STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INDEX_OUT_OF_RANGE,
			"Partial access index " + exprString + " of out range"
		)
	}

	def static generateInvalidWordIndices() {
		val shortBitAccess = (16 .. 100).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (2 .. 100).map["%B" + it.toString]
		val wordAccess = (0 .. 100).map["%W" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess + wordAccess
	}

	@ParameterizedTest(name="Check invalid index {0}")
	@MethodSource("generateInvalidDWordIndices")
	def void testInvalidDWordPartialAccess(String input) {
		var exprString = "";
		try {
			Integer.parseInt(input)
			exprString = "%X" + input
		} catch (NumberFormatException e) {
			exprString = input
		}
		'''
			FUNCTION partialTest
			VAR
			x : DWORD;
			END_VAR
			x.«input» := 1;
			END_FUNCTION
		'''.parse.assertError(
			STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INDEX_OUT_OF_RANGE,
			"Partial access index " + exprString + " of out range"
		)
	}

	def static generateInvalidDWordIndices() {
		val shortBitAccess = (32 .. 100).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (4 .. 100).map["%B" + it.toString]
		val wordAccess = (2 .. 100).map["%W" + it.toString]
		val dwordAccess = (0 .. 100).map["%D" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess + wordAccess + dwordAccess
	}

	@ParameterizedTest(name="Check invalid index {0}")
	@MethodSource("generateInvalidLWordIndices")
	def void testInvalidLWordPartialAccess(String input) {
		var exprString = "";
		try {
			Integer.parseInt(input)
			exprString = "%X" + input
		} catch (NumberFormatException e) {
			exprString = input
		}
		'''
			FUNCTION partialTest
			VAR
			x : LWORD;
			END_VAR
			x.«input» := 1;
			END_FUNCTION
		'''.parse.assertError(
			STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INDEX_OUT_OF_RANGE,
			"Partial access index " + exprString + " of out range"
		)
	}

	def static generateInvalidLWordIndices() {
		val shortBitAccess = (64 .. 100).map[it.toString]
		val longBitAccess = shortBitAccess.map["%X" + it]
		val byteAccess = (8 .. 100).map["%B" + it.toString]
		val wordAccess = (4 .. 100).map["%W" + it.toString]
		val dwordAccess = (2 .. 100).map["%D" + it.toString]
		val lwordAccess = (0 .. 100).map["%L" + it.toString]
		return shortBitAccess + longBitAccess + byteAccess + wordAccess + dwordAccess + lwordAccess
	}

	@ParameterizedTest(name="Check invalid access on non Variable expression with index {0}")
	@MethodSource("generateValidLWordIndices")
	def void testInvalidPartialAccessOnNonVariable(String input) {
		'''
			FUNCTION partialTest
			VAR
			x : LWORD;
			END_VAR
			x := (x OR x).«input»;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_INVALID_RECEIVER, "Receiving expression invalid for partial access")
	}

	@Test
	def void testValidPartialAccessOnArraySubscript() {
		'''
			FUNCTION partialTest
			VAR
			x : ARRAY [0..1] OF DWORD;
			END_VAR
			x[0].1 := NOT x[0].1;
			x[1].%b1 := NOT x[1].%b1;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@ParameterizedTest(name="Check valid access expression of type {0}")
	@MethodSource("generateValidAccessExpressionTypes")
	def void testValidPartialAccessExpressions(String input) {
		'''
			FUNCTION partialTest
			VAR
			accessor : «input»;
			accessed : LWORD;
			END_VAR
			accessed.(accessor);
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static generateValidAccessExpressionTypes() {
		return DataTypeLibrary.nonUserDefinedDataTypes.filter[it instanceof AnyIntType].map[it.name]
	}
	
	@ParameterizedTest(name="Check valid access expression of type {0}")
	@MethodSource("generateInvalidAccessExpressionTypes")
	def void testInvalidPartialAccessExpressions(String input) {
		'''
			FUNCTION partialTest
			VAR
			accessor : «input»;
			accessed : LWORD;
			END_VAR
			accessed.(accessor);
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STMultibitPartialExpression,
			STCoreValidator.BIT_ACCESS_EXPRESSION_NOT_OF_TYPE_ANY_INT, "Partial bit access expression is not resulting in an ANY_INT, but in " + input)
		
	}

	def static generateInvalidAccessExpressionTypes() {
		return DataTypeLibrary.nonUserDefinedDataTypes.filter[!(it instanceof AnyIntType)].map[it.name]
	}
}
