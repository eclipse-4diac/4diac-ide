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
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.tests

import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import org.junit.jupiter.api.^extension.ExtendWith
import com.google.inject.Inject
import org.eclipse.xtext.testing.formatter.FormatterTestHelper
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(STAlgorithmInjectorProvider)
class STAlgorithmFormattingTest {

	@Inject extension FormatterTestHelper

	@Test
	def void testAlgorithm() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN 
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''

			expectation = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR
				langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void testMethod() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN 
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR
				langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
		]
	}

}
