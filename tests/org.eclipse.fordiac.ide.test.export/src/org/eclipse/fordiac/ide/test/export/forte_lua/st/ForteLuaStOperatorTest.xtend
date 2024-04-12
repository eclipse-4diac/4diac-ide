/*******************************************************************************
 * Copyright (c) 2022 Patrick Aigner
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Patrick Aigner - Initial Tests
 *******************************************************************************/
 package org.eclipse.fordiac.ide.test.export.forte_lua.st

import org.eclipse.fordiac.ide.export.forte_lua.st.StructuredTextSupportFactory
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteLuaStOperatorTest extends ExporterTestBasicFBTypeBase {
	static String var_a = "ENV.st_lv_a"
	static String var_b = "ENV.st_lv_b"
	static String var_c = "ENV.st_lv_c"

	@BeforeAll
	def static void setup() {
		new DataTypeLibrary()
		GlobalConstantsStandaloneSetup.doSetup()
		STFunctionStandaloneSetup.doSetup()
		STAlgorithmStandaloneSetup.doSetup()
		StructuredTextSupportFactory.register()
	}

	@Test
	def void testAdd() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : INT;
			  END_VAR
			  a := 1;
			  a := a + 1;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1
			«var_a» = («var_a» + 1)
		'''.toString(), result.toString())
	}

	@Test
	def void testSub() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : INT;
			  END_VAR
			  a := 1;
			  a := a - 1;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1
			«var_a» = («var_a» - 1)
		'''.toString(), result.toString())
	}

	@Test
	def void testMul() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : INT;
			  END_VAR
			  a := 1;
			  a := a * 1;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1
			«var_a» = («var_a» * 1)
		'''.toString(), result.toString())
	}

	@Test
	def void testDivInt() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : INT;
			  END_VAR
			  a := 1;
			  a := a / 1;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1
			«var_a» = math.floor(«var_a» / 1)
		'''.toString(), result.toString())
	}

	@Test
	def void testDivReal() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			  END_VAR
			  a := 1.0;
			  a := a / 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1.0
			«var_a» = («var_a» / 1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testMod() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : INT;
			  END_VAR
			  a := 1;
			  a := a MOD 1;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1
			«var_a» = («var_a» % 1)
		'''.toString(), result.toString())
	}

	@Test
	def void testPower() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			  END_VAR
			  a := 1.0;
			  a := a ** 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			
			«var_a» = 1.0
			«var_a» = («var_a»^1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testGt() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			    b : BOOL;
			  END_VAR
			  a := 1.0;
			  b := a > 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			«var_b» = false
			
			«var_a» = 1.0
			«var_b» = («var_a» > 1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testLe() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			    b : BOOL;
			  END_VAR
			  a := 1.0;
			  b := a <= 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			«var_b» = false
			
			«var_a» = 1.0
			«var_b» = («var_a» <= 1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testEq() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			    b : BOOL;
			  END_VAR
			  a := 1.0;
			  b := a = 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			«var_b» = false
			
			«var_a» = 1.0
			«var_b» = («var_a» == 1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testNe() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			    b : BOOL;
			  END_VAR
			  a := 1.0;
			  b := a <> 1.0;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			«var_b» = false
			
			«var_a» = 1.0
			«var_b» = («var_a» ~= 1.0)
		'''.toString(), result.toString())
	}

	@Test
	def void testAnd() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : BOOL;
			  END_VAR
			  a := TRUE;
			  a := a AND FALSE;
			  a := a & FALSE;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = false
			
			«var_a» = true
			«var_a» = («var_a» and false)
			«var_a» = («var_a» and false)
		'''.toString(), result.toString())
	}

	@Test
	def void testOr() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : BOOL;
			  END_VAR
			  a := TRUE;
			  a := a OR FALSE;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = false
			
			«var_a» = true
			«var_a» = («var_a» or false)
		'''.toString(), result.toString())
	}

	@Test
	def void testXor() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : BOOL;
			  END_VAR
			  a := TRUE;
			  a := a XOR FALSE;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = false
			
			«var_a» = true
			«var_a» = («var_a» ~= false)
		'''.toString(), result.toString())
	}

	@Test
	def void testOrAnd() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : BOOL;
			    b : BOOL;
			  END_VAR
			  a := TRUE;
			  b := FALSE;
			  a := a OR a AND b;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = false
			«var_b» = false
			
			«var_a» = true
			«var_b» = false
			«var_a» = («var_a» or («var_a» and «var_b»))
		'''.toString(), result.toString())
	}

	@Test
	def void testOrAndBrackets() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : BOOL;
			    b : BOOL;
			  END_VAR
			  a := TRUE;
			  b := FALSE;
			  a := (a OR a) AND b;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = false
			«var_b» = false
			
			«var_a» = true
			«var_b» = false
			«var_a» = ((«var_a» or «var_a») and «var_b»)
		'''.toString(), result.toString())
	}

	@Test
	def void testCalc() {
		val algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		algorithm.name = "TEST_ALGORITHM"
		algorithm.text = '''
			ALGORITHM TEST_ALGORITHM
			  VAR_TEMP
			    a : REAL;
			    b : INT;
			    c : INT;
			  END_VAR
			  a := 1.1;
			  b := 5;
			  c := 2;
			  a := a * (b / -c);
			  a := a * b / +c;
			  a := a * b + c;
			END_ALGORITHM
		'''
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", algorithm)
		val result = lang.generate(emptyMap)
		assertEquals('''
			local ENV = {}
			«var_a» = 0
			«var_b» = 0
			«var_c» = 0
			
			«var_a» = 1.1
			«var_b» = 5
			«var_c» = 2
			«var_a» = («var_a» * math.floor(«var_b» / -«var_c»))
			«var_a» = ((«var_a» * «var_b») / «var_c»)
			«var_a» = ((«var_a» * «var_b») + «var_c»)
		'''.toString(), result.toString())
	}
}
