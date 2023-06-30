/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval.function

import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.eval.function.Functions
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.value.AnyIntValue
import org.eclipse.fordiac.ide.model.eval.value.AnyMagnitudeValue
import org.eclipse.fordiac.ide.model.eval.value.AnyValue
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue
import org.eclipse.fordiac.ide.model.eval.value.IntValue
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.model.typelibrary.testmocks.DataTypeEntryMock
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import static org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.*
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.SIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.junit.jupiter.api.Assertions.*

class FunctionsTest {
	protected static TypeLibrary typeLib

	@BeforeAll
	def static void setupXtext() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null)
		GlobalConstantsStandaloneSetup.doSetup
		STFunctionStandaloneSetup.doSetup
		STAlgorithmStandaloneSetup.doSetup
		StructuredTextEvaluatorFactory.register
	}

	@Test
	def void testNotFound() {
		NoSuchMethodException.assertThrows[SampleFunctions.findMethodFromDataTypes("ILLEGAL_NAME", ElementaryTypes.INT)]
	}

	@Test
	def void testSimple() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("SIMPLE", ElementaryTypes.INT).assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows[SampleFunctions.findMethodFromDataTypes("SIMPLE", ElementaryTypes.STRING)]
		// return type
		ElementaryTypes.INT.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("SIMPLE", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("SIMPLE", ElementaryTypes.INT))
		// invoke
		17.toIntValue.assertEquals(SampleFunctions.invoke("SIMPLE", 17.toIntValue))
	}

	@Test
	def void testGenericParam() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_PARAM", ElementaryTypes.INT).assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows [
			SampleFunctions.findMethodFromDataTypes("GENERIC_PARAM", ElementaryTypes.STRING)
		]
		// return type
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_PARAM", ElementaryTypes.LINT))
		// parameter types
		#[ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_PARAM", ElementaryTypes.LINT))
		// invoke
		17.toIntValue.assertEquals(SampleFunctions.invoke("GENERIC_PARAM", 17.toLIntValue))
	}

	@Test
	def void testGenericReturn() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_RETURN", ElementaryTypes.INT).assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows [
			SampleFunctions.findMethodFromDataTypes("GENERIC_RETURN", ElementaryTypes.STRING)
		]
		// return type
		ElementaryTypes.LINT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_RETURN", ElementaryTypes.LINT))
		// parameter types
		#[ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_RETURN", ElementaryTypes.LINT))
		// invoke
		17.toLIntValue.assertEquals(SampleFunctions.invoke("GENERIC_RETURN", 17.toLIntValue))
	}

	@Test
	def void testMultiGenericParam() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT, ElementaryTypes.INT).
			assertNotNull
		SampleFunctions.findMethodFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.LINT, ElementaryTypes.INT).
			assertNotNull
		SampleFunctions.findMethodFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT, ElementaryTypes.LINT).
			assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows [
			SampleFunctions.findMethodFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.STRING,
				ElementaryTypes.STRING)
		]
		// return type
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT,
				ElementaryTypes.INT))
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.LINT,
				ElementaryTypes.INT))
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT,
				ElementaryTypes.LINT))
		// parameter types
		#[ElementaryTypes.INT, ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT,
				ElementaryTypes.INT))
		#[ElementaryTypes.LINT, ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.LINT,
				ElementaryTypes.INT))
		#[ElementaryTypes.INT, ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT,
				ElementaryTypes.LINT))
		// invoke
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toIntValue, 4.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toLIntValue, 4.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toIntValue, 4.toLIntValue))
	}

	@Test
	def void testVarargs() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("VARARGS").assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS", ElementaryTypes.INT).assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT).assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT,
			ElementaryTypes.INT).assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows[SampleFunctions.findMethodFromDataTypes("VARARGS", ElementaryTypes.STRING)]
		// return type
		ElementaryTypes.INT.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("VARARGS"))
		ElementaryTypes.INT.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("VARARGS", ElementaryTypes.INT))
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT))
		ElementaryTypes.INT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT,
				ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("VARARGS"))
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS", ElementaryTypes.INT))
		#[ElementaryTypes.INT, ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT))
		#[ElementaryTypes.INT, ElementaryTypes.INT, ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS", ElementaryTypes.INT, ElementaryTypes.INT,
				ElementaryTypes.INT))
		// invoke
		SampleFunctions.invoke("VARARGS").assertNull
		17.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue, 4.toIntValue))
		42.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue, 4.toIntValue, 21.toIntValue))
	}

	@Test
	def void testVarargsGeneric() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC").assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT).assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT, ElementaryTypes.INT).
			assertNotNull
		SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT, ElementaryTypes.INT,
			ElementaryTypes.SINT).assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows [
			SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC", ElementaryTypes.STRING)
		]
		// return type
		GenericTypes.ANY_MAGNITUDE.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("VARARGS_GENERIC"))
		ElementaryTypes.LINT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT))
		ElementaryTypes.LINT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT, ElementaryTypes.INT))
		ElementaryTypes.LINT.assertEquals(
			SampleFunctions.inferReturnTypeFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT, ElementaryTypes.INT,
				ElementaryTypes.SINT))
		// parameter types
		#[GenericTypes.ANY_MAGNITUDE].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC"))
		#[ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT))
		#[ElementaryTypes.LINT, ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT,
				ElementaryTypes.INT))
		#[ElementaryTypes.LINT, ElementaryTypes.LINT, ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT,
				ElementaryTypes.INT, ElementaryTypes.SINT))
		// invoke
		SampleFunctions.invoke("VARARGS_GENERIC").assertNull
		17.toLIntValue.assertEquals(SampleFunctions.invoke("VARARGS_GENERIC", 17.toLIntValue))
		21.toLIntValue.assertEquals(SampleFunctions.invoke("VARARGS_GENERIC", 17.toLIntValue, 4.toIntValue))
		42.toLIntValue.assertEquals(
			SampleFunctions.invoke("VARARGS_GENERIC", 17.toLIntValue, 4.toIntValue, 21.toSIntValue))
	}

	@Test
	def void testSimpleOutput() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT).assertNotNull
		// return type
		assertNull(SampleFunctions.inferReturnTypeFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT)
		SampleFunctions.invoke("SIMPLE_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericOutput() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT).assertNotNull
		// return type
		assertNull(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT)
		SampleFunctions.invoke("GENERIC_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericType() {
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", GenericTypes.ANY).assertNotNull
		// return type
		GenericTypes.ANY.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", GenericTypes.ANY))
		// parameter types
		#[GenericTypes.ANY].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", GenericTypes.ANY))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT, "21")
		SampleFunctions.invoke("GENERIC_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericTypeArray() {
		val arrayType = newArrayType(ElementaryTypes.INT, newSubrange(0, 1))
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", arrayType).assertNotNull
		// return type
		arrayType.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", arrayType))
		// parameter types
		#[arrayType].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", arrayType))
		// invoke
		val arg = newVariable("A", arrayType, "[17, 4]")
		val result = SampleFunctions.invoke("GENERIC_TYPE", arg.value) as ArrayValue
		17.toIntValue.assertEquals(result.get(0).value)
		4.toIntValue.assertEquals(result.get(1).value)
	}

	@Test
	def void testGenericTypeStruct() {
		val structType = DataFactory.eINSTANCE.createStructuredType => [
			memberVariables.addAll(#[
				LibraryElementFactory.eINSTANCE.createVarDeclaration => [name = "a" type = ElementaryTypes.INT],
				LibraryElementFactory.eINSTANCE.createVarDeclaration => [name = "b" type = ElementaryTypes.INT]
			])
		]
		typeLib.addTypeEntry(new DataTypeEntryMock(structType, typeLib, null))
		val structResource = new ResourceImpl
		structResource.contents.add(structType)
		// lookup
		SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", structType).assertNotNull
		// return type
		structType.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", structType))
		// parameter types
		#[structType].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", structType))
		// invoke
		val arg = newVariable("A", structType, "(a := 17, b := 4)")
		val result = SampleFunctions.invoke("GENERIC_TYPE", arg.value) as StructValue
		17.toIntValue.assertEquals(result.get("a").value)
		4.toIntValue.assertEquals(result.get("b").value)
	}

	static interface SampleFunctions extends Functions {
		def static IntValue SIMPLE(IntValue a) { a }

		def static <T extends AnyMagnitudeValue> IntValue GENERIC_PARAM(T a) { a.toIntValue }

		def static <T extends AnyMagnitudeValue> T GENERIC_RETURN(T a) { a }

		def static <T extends AnyMagnitudeValue, U extends AnyMagnitudeValue> IntValue MULTI_GENERIC_PARAM(T a, U b) {
			((a + b) as AnyMagnitudeValue).toIntValue
		}

		def static IntValue VARARGS(IntValue... a) { a.reduce[p1, p2|(p1 + p2) as IntValue] }

		def static <T extends AnyMagnitudeValue> T VARARGS_GENERIC(T... a) { a.reduce[p1, p2|(p1 + p2) as T] }

		def static void SIMPLE_OUTPUT(Variable<IntValue> a) { a.value = 21.wrapValue(a.type) }

		def static <T extends AnyIntValue> void GENERIC_OUTPUT(Variable<T> a) { a.value = 21.wrapValue(a.type) }

		def static <T extends AnyValue> T GENERIC_TYPE(T a) { a }
	}
}
