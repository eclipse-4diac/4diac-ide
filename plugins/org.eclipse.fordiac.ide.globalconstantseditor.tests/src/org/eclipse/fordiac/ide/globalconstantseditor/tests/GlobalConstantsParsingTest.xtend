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
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.tests

import com.google.inject.Inject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(GlobalConstantsInjectorProvider)
class GlobalConstantsParsingTest {
	@Inject
	ParseHelper<STGlobalConstsSource> parseHelper

	@Test
	def void loadModel() {
		val result = parseHelper.parse('''
			GLOBALCONSTANTS TEST
				VAR_GLOBAL
					PI : LREAL := 3.14159265359;
				END_VAR
			END_GLOBALCONSTANTS
		''')
		Assertions.assertNotNull(result)
		val errors = result.eResource.errors
		Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
	}
}
