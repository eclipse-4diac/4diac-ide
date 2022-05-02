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
 *   Ulzii Jargalsaikhan
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.Test
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator

@ExtendWith(InjectionExtension)
@InjectWith(STFunctionInjectorProvider)
class STFunctionValidatorTest {

	@Inject extension ParseHelper<STFunctionSource> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testWrongCasedIdentifierWarning() {
		'''
		FUNCTION hubert
		VAR
		    bol1 : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		IF bol1 THEN
			bol1 := TRUE;
		ELSIF bol2 THEN
			bol1 := TRUE;
		ELSE
			bol1 := FALSE;
			bOl1 := FALSE;
		END_IF;
			bOl1 := 1;
		END_FUNCTION'''.parse.assertIssuesInvalidNameWarning()
	}

	@Test
	def void testConsecutiveUnderscoreErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    bo__l1 : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	}
	
	@Test
	def void testTrailingUnderscoreErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    bol1_ : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	}

	def private assertIssuesInvalidNameWarning(STFunctionSource source) {
		source.assertWarning(STCorePackage.Literals.ST_FEATURE_EXPRESSION, STFunctionValidator.WRONG_NAME_CASE)
	}
}
