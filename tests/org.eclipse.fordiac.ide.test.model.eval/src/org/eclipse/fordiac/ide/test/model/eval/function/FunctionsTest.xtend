/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock
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
		val method = SampleFunctions.findMethodFromDataTypes("SIMPLE", ElementaryTypes.INT)
		method.assertNotNull
		// wrong argument type
		NoSuchMethodException.assertThrows[SampleFunctions.findMethodFromDataTypes("SIMPLE", ElementaryTypes.STRING)]
		// return type
		ElementaryTypes.INT.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("SIMPLE", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("SIMPLE", ElementaryTypes.INT))
		// expected parameter type
		ElementaryTypes.INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.SINT, emptyList, 0))
		// invoke
		17.toIntValue.assertEquals(SampleFunctions.invoke("SIMPLE", 17.toIntValue))
	}

	@Test
	def void testGenericParam() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_PARAM", ElementaryTypes.INT)
		method.assertNotNull
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
		// expected parameter type
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY_MAGNITUDE.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		// invoke
		17.toIntValue.assertEquals(SampleFunctions.invoke("GENERIC_PARAM", 17.toLIntValue))
	}

	@Test
	def void testGenericReturn() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_RETURN", ElementaryTypes.INT)
		method.assertNotNull
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
		// expected parameter type
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY_MAGNITUDE.assertEquals(
			method.inferExpectedParameterTypeFromDataType(GenericTypes.ANY, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.DINT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		// invoke
		17.toLIntValue.assertEquals(SampleFunctions.invoke("GENERIC_RETURN", 17.toLIntValue))
	}

	@Test
	def void testMultiGenericParam() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("MULTI_GENERIC_PARAM", ElementaryTypes.INT,
			ElementaryTypes.INT)
		method.assertNotNull
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
		// expected parameter type
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 1))
		GenericTypes.ANY_MAGNITUDE.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		GenericTypes.ANY_MAGNITUDE.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 1))
		// invoke
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toIntValue, 4.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toLIntValue, 4.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("MULTI_GENERIC_PARAM", 17.toIntValue, 4.toLIntValue))
	}

	@Test
	def void testVarargs() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("VARARGS")
		method.assertNotNull
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
		// expected parameter type
		ElementaryTypes.INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		ElementaryTypes.INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 1))
		ElementaryTypes.INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 2))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 1))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 2))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 1))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 2))
		// invoke
		SampleFunctions.invoke("VARARGS").assertNull
		17.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue))
		21.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue, 4.toIntValue))
		42.toIntValue.assertEquals(SampleFunctions.invoke("VARARGS", 17.toIntValue, 4.toIntValue, 21.toIntValue))
	}

	@Test
	def void testVarargsGeneric() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("VARARGS_GENERIC")
		method.assertNotNull
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
		#[GenericTypes.ANY_MAGNITUDE].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC"))
		#[ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT))
		#[ElementaryTypes.LINT, ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT,
				ElementaryTypes.INT))
		#[ElementaryTypes.LINT, ElementaryTypes.LINT, ElementaryTypes.LINT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("VARARGS_GENERIC", ElementaryTypes.LINT,
				ElementaryTypes.INT, ElementaryTypes.SINT))
		// expected parameter type
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 1))
		GenericTypes.ANY_MAGNITUDE.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 2))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(null, #[null, ElementaryTypes.INT, ElementaryTypes.INT], 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(null, #[ElementaryTypes.INT, null, ElementaryTypes.INT], 1))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(null, #[ElementaryTypes.INT, ElementaryTypes.INT, null], 2))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 1))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 2))
		ElementaryTypes.DINT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		ElementaryTypes.DINT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 1))
		ElementaryTypes.DINT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 2))
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
		val method = SampleFunctions.findMethodFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT)
		method.assertNotNull
		// return type
		assertNull(SampleFunctions.inferReturnTypeFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("SIMPLE_OUTPUT", ElementaryTypes.INT))
		// expected parameter type
		ElementaryTypes.INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.SINT, emptyList, 0))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT)
		SampleFunctions.invoke("SIMPLE_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericOutput() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT)
		method.assertNotNull
		// return type
		assertNull(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT))
		// parameter types
		#[ElementaryTypes.INT].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_OUTPUT", ElementaryTypes.INT))
		// expected parameter type
		GenericTypes.ANY_INT.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY_INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT)
		SampleFunctions.invoke("GENERIC_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericType() {
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", GenericTypes.ANY)
		method.assertNotNull
		// return type
		GenericTypes.ANY.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", GenericTypes.ANY))
		// parameter types
		#[GenericTypes.ANY].assertIterableEquals(
			SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", GenericTypes.ANY))
		// expected parameter type
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(GenericTypes.ANY, emptyList, 0))
		ElementaryTypes.INT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.INT, emptyList, 0))
		ElementaryTypes.DINT.assertEquals(
			method.inferExpectedParameterTypeFromDataType(ElementaryTypes.DINT, emptyList, 0))
		// invoke
		val arg = newVariable("A", ElementaryTypes.INT, "21")
		SampleFunctions.invoke("GENERIC_OUTPUT", arg)
		21.toIntValue.assertEquals(arg.value)
	}

	@Test
	def void testGenericTypeArray() {
		val arrayType = newArrayType(ElementaryTypes.INT, newSubrange(0, 1))
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", arrayType)
		method.assertNotNull
		// return type
		arrayType.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", arrayType))
		// parameter types
		#[arrayType].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", arrayType))
		// expected parameter type
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(GenericTypes.ANY, emptyList, 0))
		arrayType.assertEquals(method.inferExpectedParameterTypeFromDataType(arrayType, emptyList, 0))
		// invoke
		val arg = newVariable("A", arrayType, "[17, 4]")
		val result = SampleFunctions.invoke("GENERIC_TYPE", arg.value) as ArrayValue
		17.toIntValue.assertEquals(result.get(0).value)
		4.toIntValue.assertEquals(result.get(1).value)
	}

	@Test
	def void testGenericTypeStruct() {
		val structType = DataFactory.eINSTANCE.createStructuredType => [
			name = "GenericTypeStruct"
			memberVariables.addAll(#[
				LibraryElementFactory.eINSTANCE.createVarDeclaration => [name = "a" type = ElementaryTypes.INT],
				LibraryElementFactory.eINSTANCE.createVarDeclaration => [name = "b" type = ElementaryTypes.INT]
			])
		]
		typeLib.addTypeEntry(new DataTypeEntryMock(structType, typeLib, null))
		val structResource = new ResourceImpl
		structResource.contents.add(structType)
		// lookup
		val method = SampleFunctions.findMethodFromDataTypes("GENERIC_TYPE", structType)
		method.assertNotNull
		// return type
		structType.assertEquals(SampleFunctions.inferReturnTypeFromDataTypes("GENERIC_TYPE", structType))
		// parameter types
		#[structType].assertIterableEquals(SampleFunctions.inferParameterTypesFromDataTypes("GENERIC_TYPE", structType))
		// expected parameter type
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(null, emptyList, 0))
		GenericTypes.ANY.assertEquals(method.inferExpectedParameterTypeFromDataType(GenericTypes.ANY, emptyList, 0))
		structType.assertEquals(method.inferExpectedParameterTypeFromDataType(structType, emptyList, 0))
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
			(ValueOperations.add(a, b) as AnyMagnitudeValue).toIntValue
		}

		def static IntValue VARARGS(IntValue... a) { a.reduce[p1, p2|ValueOperations.add(p1, p2)  as IntValue] }

		def static <T extends AnyMagnitudeValue> T VARARGS_GENERIC(T... a) { a.reduce[p1, p2|ValueOperations.add(p1, p2) as T] }

		def static void SIMPLE_OUTPUT(Variable<IntValue> a) { a.value = 21.wrapValue(a.type) }

		def static <T extends AnyIntValue> void GENERIC_OUTPUT(Variable<T> a) { a.value = 21.wrapValue(a.type) }

		def static <T extends AnyValue> T GENERIC_TYPE(T a) { a }
	}
}
