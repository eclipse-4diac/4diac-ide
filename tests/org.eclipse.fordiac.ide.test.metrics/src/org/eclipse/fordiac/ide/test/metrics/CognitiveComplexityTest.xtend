/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.metrics

import org.eclipse.fordiac.ide.test.metrics.AbstractCognitiveComplexityTest
import org.junit.jupiter.api.Test

class CognitiveComplexityTest extends AbstractCognitiveComplexityTest {
	@Test
	def a() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		INVALID                                            ''',
		-1.0)
	}
	
	@Test
	def b() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		EXIT;                     // 0   | 1   | 0   | 1''',
		1.0)
	}

	@Test
	def c() {
		verifyCognitiveComplexity( /* n   | c   | a   | s */'''
		IF                         // 0   | 1   | 0   | 1
			TRUE AND FALSE THEN    // 0   | 1   | 0   | 1
			EXIT;                  // 1   | 1   | -   | 1
		END_IF;                    // 0   | 0   | 0   | 0''',
		3.0)
	}

	@Test
	def d() {
		verifyCognitiveComplexity(        /* n   | c   | a   | s */'''
		IF                                // 0   | 1   | 0   | 1
			TRUE AND FALSE AND FALSE THEN // 0   | 1   | 0   | 1
			EXIT;                         // 1   | 1   | -   | 1
		END_IF;                           // 0   | 0   | 0   | 0''',
		3.0)
	}

	@Test
	def e() {
		verifyCognitiveComplexity(        /* n   | c   | a   | s */'''
		IF                                // 0   | 1   | 0   | 1
			TRUE AND FALSE OR FALSE THEN  // 0   | 2   | 0   | 2
			EXIT;                         // 1   | 1   | -   | 1
		END_IF;                           // 0   | 0   | 0   | 0''',
		4.0)
	}

	@Test
	def f() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		VAR I : DINT; END_VAR;    // 0   | 0   | 0   | 0
		FOR I:=0 TO 5 DO          // 0   | 1   | 0   | 1
			CONTINUE;             // 1   | 1   | -   | 1
		END_FOR;                  // 0   | 0   | 0   | 0''',
		2.0)
	}

	@Test
	def g() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		WHILE TRUE DO             // 0   | 1   | 0   | 1
			CONTINUE;             // 1   | 1   | -   | 1
		END_WHILE;                // 0   | 0   | 0   | 0''',
		2.0)
	}

	@Test
	def h() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		REPEAT                    // 0   | 1   | 0   | 1
			CONTINUE;             // 1   | 1   | -   | 1
		UNTIL TRUE END_REPEAT;    // 0   | 0   | 0   | 0''',
		2.0)
	}
	
	@Test
	def i() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		REPEAT                    // 0   | 1   | 0   | 1
			REPEAT                // 1   | 1   | 1   | 2
				CONTINUE;         // 2   | 1   | -   | 1
			UNTIL TRUE END_REPEAT;// 0   | 0   | 0   | 0
		UNTIL TRUE END_REPEAT;    // 0   | 0   | 0   | 0''',
		4.0)
	}

	@Test
	def j() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		VAR I : DINT; END_VAR;    // 0   | 0   | 0   | 0
		CASE I OF                 // 0   | 1   | 0   | 1
			1: EXIT;              // 1   | 1   | -   | 1
		ELSE                      // 0   | 0   | 0   | 0
			CONTINUE;             // 1   | 1   | -   | 1
		END_CASE;                 // 0   | 0   | 0   | 0''',
		3.0)
	}

	@Test
	def k() {
		verifyCognitiveComplexity(/* n   | c   | a   | s */'''
		VAR I : DINT; END_VAR;    // 0   | 0   | 0   | 0
		FOR I:=0 TO 10 DO         // 0   | 1   | 0   | 1
			IF TRUE               // 1   | 1   | 1   | 2
				AND TRUE          // 1   | 1   | 0   | 1
				AND TRUE          // 1   | 0   | 0   | 0
				OR TRUE THEN      // 0   | 1   | 0   | 1
					EXIT;         // 2   | 1   | 0   | 1
			END_IF;               // 0   | 0   | 0   | 0
		END_FOR;                  // 0   | 0   | 0   | 0''',
		6.0)
	}
}