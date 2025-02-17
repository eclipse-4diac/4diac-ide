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

import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionUiInjectorProvider.class)
class STFunctionQuickfixTest extends AbstractQuickfixTest {
	@Test
	void fixNonCompatibleTypes() {
		testQuickfixesOn("""
				FUNCTION TEST
				VAR_TEMP
					X : INT;
					Y : DINT;
				END_VAR
				X := Y;
				END_FUNCTION
				""", STCoreValidator.NON_COMPATIBLE_TYPES,
				new Quickfix("Add explicit typecast", "Add typecast from DINT to INT", """
						FUNCTION TEST
						VAR_TEMP
							X : INT;
							Y : DINT;
						END_VAR
						X := DINT_TO_INT(Y);
						END_FUNCTION
								"""));
	}

	@Test
	void fixUnnecessaryLiteralConversion() {
		testQuickfixesOn("""
				FUNCTION TEST
				VAR_TEMP
					X : INT;
				END_VAR
				X := DINT_TO_INT(1234);
				END_FUNCTION
				""", STCoreValidator.UNNECESSARY_LITERAL_CONVERSION,
				new Quickfix("Remove literal conversion", "Replace with result '1234'", """
						FUNCTION TEST
						VAR_TEMP
							X : INT;
						END_VAR
						X := 1234;
						END_FUNCTION
								"""));
	}

	@Test
	void fixTruncatingLiteralConversion() {
		testQuickfixesOn("""
				FUNCTION TEST
				VAR_TEMP
					X : INT;
				END_VAR
				X := DINT_TO_INT(12345678);
				END_FUNCTION
				""", STCoreValidator.TRUNCATING_LITERAL_CONVERSION,
				new Quickfix("Remove literal conversion", "Replace with result '24910'", """
						FUNCTION TEST
						VAR_TEMP
							X : INT;
						END_VAR
						X := 24910;
						END_FUNCTION
								"""));
	}

	@Test
	void fixTruncatingUnsignedLiteralConversion() {
		testQuickfixesOn("""
				FUNCTION TEST
				VAR_TEMP
					X : INT;
				END_VAR
				X := UINT_TO_INT(16#8000);
				END_FUNCTION
				""", STCoreValidator.TRUNCATING_LITERAL_CONVERSION,
				new Quickfix("Remove literal conversion", "Replace with result '-32768'", """
						FUNCTION TEST
						VAR_TEMP
							X : INT;
						END_VAR
						X := -32768;
						END_FUNCTION
								"""));
	}

	@Test
	void fixMissingVariable() {
		testQuickfixesOn("""
				FUNCTION TEST
				X := 17; // test comment
				END_FUNCTION

				""", Diagnostic.LINKING_DIAGNOSTIC,
				// INPUT
				new Quickfix("Create missing INPUT variable", "Create missing INPUT variable", """
						FUNCTION TEST
						VAR_INPUT
							X : SINT;
						END_VAR

						X := 17; // test comment
						END_FUNCTION

						"""),
				// IN_OUT
				new Quickfix("Create missing IN_OUT variable", "Create missing IN_OUT variable", """
						FUNCTION TEST
						VAR_IN_OUT
							X : SINT;
						END_VAR

						X := 17; // test comment
						END_FUNCTION

						"""),
				// OUTPUT
				new Quickfix("Create missing OUTPUT variable", "Create missing OUTPUT variable", """
						FUNCTION TEST
						VAR_OUTPUT
							X : SINT;
						END_VAR

						X := 17; // test comment
						END_FUNCTION

						"""),
				// TEMP
				new Quickfix("Create missing TEMP variable", "Create missing TEMP variable", """
						FUNCTION TEST
						VAR_TEMP
							X : SINT;
						END_VAR

						X := 17; // test comment
						END_FUNCTION

						"""));
	}

	@Test
	void fixMissingVariableExpression() {
		testQuickfixesOn("""
				FUNCTION TEST
				X := DINT#17 = 4; // test comment
				END_FUNCTION

				""", Diagnostic.LINKING_DIAGNOSTIC,
				// INPUT
				new Quickfix("Create missing INPUT variable", "Create missing INPUT variable", """
						FUNCTION TEST
						VAR_INPUT
							X : BOOL;
						END_VAR

						X := DINT#17 = 4; // test comment
						END_FUNCTION

						"""),
				// IN_OUT
				new Quickfix("Create missing IN_OUT variable", "Create missing IN_OUT variable", """
						FUNCTION TEST
						VAR_IN_OUT
							X : BOOL;
						END_VAR

						X := DINT#17 = 4; // test comment
						END_FUNCTION

						"""),
				// OUTPUT
				new Quickfix("Create missing OUTPUT variable", "Create missing OUTPUT variable", """
						FUNCTION TEST
						VAR_OUTPUT
							X : BOOL;
						END_VAR

						X := DINT#17 = 4; // test comment
						END_FUNCTION

						"""),
				// TEMP
				new Quickfix("Create missing TEMP variable", "Create missing TEMP variable", """
						FUNCTION TEST
						VAR_TEMP
							X : BOOL;
						END_VAR

						X := DINT#17 = 4; // test comment
						END_FUNCTION

						"""));
	}
}
