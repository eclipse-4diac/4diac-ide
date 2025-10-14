/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.util.StringTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StringTransformTest {

	static Stream<Arguments> createStringTransformerTestCases() {
		return Stream.of(Arguments.of("Test:=\"Hello   World!  // Not a comment\";", //$NON-NLS-1$
				"Test:=\"Hello   World!  // Not a comment\";"), //$NON-NLS-1$
				Arguments.of("Test:=\"Hello   World! $\" \";", "Test:=\"Hello   World! $\" \";"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("Test:='Hello   World!  // Not a comment';", "Test:='Hello   World!  // Not a comment';"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("Test:='Hello   World! $\' ';", "Test:='Hello   World! $' ';"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("(* This is a multi-line \n \n comment *)", ""), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("/* This is a multi-line \n \n comment */", ""), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("// This is a single-line   ", ""), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("// This is a single-line  \ntest", "test"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						// This is a comment
						x :=  10;
						""", //$NON-NLS-1$
						"x:=10;"), //$NON-NLS-1$
				Arguments.of("""
						(*
						 Multi-line comment
						*)
						s := "Hello   World!";
						""", "s:=\"Hello   World!\";"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						/*
						 Multi-line comment
						*/
						s := "Hello   World!";
						""", "s:=\"Hello   World!\";"), //$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("testVar1 AND \t \t varTest2 \t THEN", "testVar1 AND varTest2 THEN"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("testVar1_ AND \t \t _varTest2 \t THEN", "testVar1_ AND _varTest2 THEN"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("testVar AND \t \t varTest2", "testVar AND varTest2"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("whitespace at the end \n", "whitespace at the end"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("whitespace and ( test", "whitespace and(test"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("whitespace and / test", "whitespace and/test"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("whitespace and ( at end (", "whitespace and(at end("), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("whitespace and / at end /", "whitespace and/at end/"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("   whitespace at beginning", "whitespace at beginning"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("// single line comment with \r test", "test"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						test unclosed multine comment
						(*
								Multi-line comment
								*
						""", "test unclosed multine comment"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						test unclosed multine comment
						(*
								Multi-line comment

						""", "test unclosed multine comment"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						test unclosed multine comment
						/*
								Multi-line comment
								*
						""", "test unclosed multine comment"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("""
						test unclosed multine comment
						/*
								Multi-line comment

						""", "test unclosed multine comment"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("Test unclosed string \"Hello   World! ", "Test unclosed string\"Hello   World! "), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("Test unclosed string 'Hello   World! ", "Test unclosed string'Hello   World! "), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("\"Hello   World!  // Not a comment\";", "\"Hello   World!  // Not a comment\";"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("'Hello   World!  // Not a comment';", "'Hello   World!  // Not a comment';"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("  Test  :=  'Hello   World! $\' '  ;  ", "Test:='Hello   World! $' ';"), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("testVar + varTest2", "testVar+varTest2") //$NON-NLS-1$ //$NON-NLS-2$

		);
	}

	@ParameterizedTest(name = "{index}: {0}->{1}")
	@MethodSource("createStringTransformerTestCases")
	@SuppressWarnings({ "squid:S5803", "squid:S5960", "static-method" })
	void transformStringName(final String input, final String expectedOutput) {
		final StringTransformer transf = new StringTransformer();
		assertEquals(expectedOutput, transf.transform(input));
	}

}
