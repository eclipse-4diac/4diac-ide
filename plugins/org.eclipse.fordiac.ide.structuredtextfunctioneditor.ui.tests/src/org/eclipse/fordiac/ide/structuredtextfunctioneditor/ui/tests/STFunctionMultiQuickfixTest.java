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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.ui.testing.AbstractMultiQuickfixTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionUiInjectorProvider.class)
class STFunctionMultiQuickfixTest extends AbstractMultiQuickfixTest {

	@Test
	void fixExitNotInLoopSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				EXIT;
				END_FUNCTION
				""", """
				FUNCTION TEST
				<0<EXIT;>0>
				END_FUNCTION
				-----
				0: message=EXIT statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixExitNotInLoopMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				EXIT;
				EXIT;
				END_FUNCTION
				""", """
				FUNCTION TEST
				<0<EXIT;>0>
				<1<EXIT;>1>
				END_FUNCTION
				-----
				0: message=EXIT statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				1: message=EXIT statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixContinueNotInLoopSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				CONTINUE;
				END_FUNCTION
				""", """
				FUNCTION TEST
				<0<CONTINUE;>0>
				END_FUNCTION
				-----
				0: message=CONTINUE statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixContinueNotInLoopMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				CONTINUE;
				CONTINUE;
				END_FUNCTION
				""", """
				FUNCTION TEST
				<0<CONTINUE;>0>
				<1<CONTINUE;>1>
				END_FUNCTION
				-----
				0: message=CONTINUE statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				1: message=CONTINUE statement is only valid inside a loop statement (FOR/WHILE/REPEAT)
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixTrailingUnderscoreSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR_ : DINT;
				END_VAR
				TESTVAR_ := 17;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					<0<TESTVAR_>0> : DINT;
				END_VAR
				TESTVAR_ := 17;
				END_FUNCTION
				-----
				0: message=Identifiers shall not have a trailing underscore ('_') character
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixTrailingUnderscoreMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR_ : DINT;
					TESTVAR2__ : DINT;
				END_VAR
				TESTVAR_ := 17;
				TESTVAR2__ := 4;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					<0<TESTVAR_>0> : DINT;
					<1<TESTVAR2__>1> : DINT;
				END_VAR
				TESTVAR_ := 17;
				TESTVAR2__ := 4;
				END_FUNCTION
				-----
				0: message=Identifiers shall not have a trailing underscore ('_') character
				1: message=Identifiers shall not have a trailing underscore ('_') character
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : DINT;
				END_VAR
				TESTVAR := 17;
				TESTVAR2 := 4;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixConsecutiveUnderscoreSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TEST__VAR : DINT;
				END_VAR
				TEST__VAR := 17;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					<0<TEST__VAR>0> : DINT;
				END_VAR
				TEST__VAR := 17;
				END_FUNCTION
				-----
				0: message=Identifiers shall not contain more than one consecutive underscore ('_') character
				""", """
				FUNCTION TEST
				VAR_TEMP
					TEST_VAR : DINT;
				END_VAR
				TEST_VAR := 17;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixConsecutiveUnderscoreMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TEST__VAR : DINT;
					TEST___VAR2 : DINT;
				END_VAR
				TEST__VAR := 17;
				TEST___VAR2 := 4;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					<0<TEST__VAR>0> : DINT;
					<1<TEST___VAR2>1> : DINT;
				END_VAR
				TEST__VAR := 17;
				TEST___VAR2 := 4;
				END_FUNCTION
				-----
				0: message=Identifiers shall not contain more than one consecutive underscore ('_') character
				1: message=Identifiers shall not contain more than one consecutive underscore ('_') character
				""", """
				FUNCTION TEST
				VAR_TEMP
					TEST_VAR : DINT;
					TEST_VAR2 : DINT;
				END_VAR
				TEST_VAR := 17;
				TEST_VAR2 := 4;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixVariableNameCasingSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				testVar := 17;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				<0<testVar>0> := 17;
				END_FUNCTION
				-----
				0: message=Variable names should be cased as declared
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixVariableNameCasingMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				testVar := 17;
				testVAR := 4;
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				<0<testVar>0> := 17;
				<1<testVAR>1> := 4;
				END_FUNCTION
				-----
				0: message=Variable names should be cased as declared
				1: message=Variable names should be cased as declared
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17;
				TESTVAR := 4;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryConversionSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := INT_TO_DINT(17);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := <0<INT_TO_DINT>0>(17);
				END_FUNCTION
				-----
				0: message=Unnecessary conversion from INT to DINT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryConversionMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := INT_TO_DINT(17);
				TESTVAR := SINT_TO_DINT(4);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := <0<INT_TO_DINT>0>(17);
				TESTVAR := <1<SINT_TO_DINT>1>(4);
				END_FUNCTION
				-----
				0: message=Unnecessary conversion from INT to DINT
				1: message=Unnecessary conversion from SINT to DINT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17;
				TESTVAR := 4;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryConversionNested() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := INT_TO_DINT(17 + SINT_TO_INT(4));
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := <0<INT_TO_DINT>0>(17 + <1<SINT_TO_INT>1>(4));
				END_FUNCTION
				-----
				0: message=Unnecessary conversion from INT to DINT
				1: message=Unnecessary conversion from SINT to INT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
				END_VAR
				TESTVAR := 17 + 4;
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryNarrowConversionSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := LINT_TO_INT(TESTVAR2);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := <0<LINT_TO_INT>0>(TESTVAR2);
				END_FUNCTION
				-----
				0: message=Unnecessary narrow conversion to INT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := LINT_TO_DINT(TESTVAR2);
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryNarrowConversionMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := LINT_TO_INT(TESTVAR2);
				TESTVAR := LINT_TO_SINT(TESTVAR2);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := <0<LINT_TO_INT>0>(TESTVAR2);
				TESTVAR := <1<LINT_TO_SINT>1>(TESTVAR2);
				END_FUNCTION
				-----
				0: message=Unnecessary narrow conversion to INT
				1: message=Unnecessary narrow conversion to SINT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : DINT;
					TESTVAR2 : LINT;
				END_VAR
				TESTVAR := LINT_TO_DINT(TESTVAR2);
				TESTVAR := LINT_TO_DINT(TESTVAR2);
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryWideConversionSingle() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := LINT_TO_SINT(TESTVAR2);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := <0<LINT_TO_SINT>0>(TESTVAR2);
				END_FUNCTION
				-----
				0: message=Unnecessary wide conversion from LINT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := INT_TO_SINT(TESTVAR2);
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixUnnecessaryWideConversionMulti() {
		testMultiQuickfix("""
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := LINT_TO_SINT(TESTVAR2);
				TESTVAR := DINT_TO_SINT(TESTVAR2);
				END_FUNCTION
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := <0<LINT_TO_SINT>0>(TESTVAR2);
				TESTVAR := <1<DINT_TO_SINT>1>(TESTVAR2);
				END_FUNCTION
				-----
				0: message=Unnecessary wide conversion from LINT
				1: message=Unnecessary wide conversion from DINT
				""", """
				FUNCTION TEST
				VAR_TEMP
					TESTVAR : SINT;
					TESTVAR2 : INT;
				END_VAR
				TESTVAR := INT_TO_SINT(TESTVAR2);
				TESTVAR := INT_TO_SINT(TESTVAR2);
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixRemoveImportSingle() {
		testMultiQuickfix("""
				IMPORT test1::type1;

				FUNCTION TEST
				END_FUNCTION
				""", """
				IMPORT <0<test1::type1>0>;

				FUNCTION TEST
				END_FUNCTION
				-----
				0: message=The import test1::type1 does not exist
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void fixRemoveImportMulti() {
		testMultiQuickfix("""
				IMPORT test1::type1;
				IMPORT test1::type2;

				FUNCTION TEST
				END_FUNCTION
				""", """
				IMPORT <0<test1::type1>0>;
				IMPORT <1<test1::type2>1>;

				FUNCTION TEST
				END_FUNCTION
				-----
				0: message=The import test1::type1 does not exist
				1: message=The import test1::type2 does not exist
				""", """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void organizeImportsSingle() {
		// create file
		final IFile file = dslFile("""
				IMPORT test1::*;

				FUNCTION TEST
				END_FUNCTION
				""");
		// create FB type to have a valid import
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());
		final SimpleFBType fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		PackageNameHelper.setFullTypeName(fbType, "test1::TestSimple");
		typeLibrary.addTypeEntry(new FBTypeEntryMock(fbType, typeLibrary, file.getProject().getFile("TestSimple.fbt")));
		// validate and get markers
		final IMarker[] markers = getMarkers(file);
		// assert markers
		assertContentsAndMarkers(file, markers, """
				IMPORT <0<test1::*>0>;

				FUNCTION TEST
				END_FUNCTION
				-----
				0: message=The use of wildcard imports is discouraged
				""");
		// apply quickfix
		applyQuickfixOnMultipleMarkers(markers);
		// assert result
		assertContentsAndMarkers(file, """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}

	@Test
	void organizeImportsMulti() {
		// create file
		final IFile file = dslFile("""
				IMPORT test1::*;
				IMPORT test2::*;

				FUNCTION TEST
				END_FUNCTION
				""");
		// create FB type to have a valid import
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());
		final SimpleFBType fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		PackageNameHelper.setFullTypeName(fbType, "test1::TestSimple");
		typeLibrary.addTypeEntry(new FBTypeEntryMock(fbType, typeLibrary, file.getProject().getFile("TestSimple.fbt")));
		// create second FB type to have a valid second import
		final SimpleFBType fbType2 = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		PackageNameHelper.setFullTypeName(fbType2, "test2::TestSimple2");
		typeLibrary
				.addTypeEntry(new FBTypeEntryMock(fbType2, typeLibrary, file.getProject().getFile("TestSimple2.fbt")));
		// validate and get markers
		final IMarker[] markers = getMarkers(file);
		// assert markers
		assertContentsAndMarkers(file, markers, """
				IMPORT <0<test1::*>0>;
				IMPORT <1<test2::*>1>;

				FUNCTION TEST
				END_FUNCTION
				-----
				0: message=The use of wildcard imports is discouraged
				1: message=The use of wildcard imports is discouraged
				""");
		// apply quickfix
		applyQuickfixOnMultipleMarkers(markers);
		// assert result
		assertContentsAndMarkers(file, """
				FUNCTION TEST
				END_FUNCTION
				-----
				(no markers found)
				""");
	}
}
