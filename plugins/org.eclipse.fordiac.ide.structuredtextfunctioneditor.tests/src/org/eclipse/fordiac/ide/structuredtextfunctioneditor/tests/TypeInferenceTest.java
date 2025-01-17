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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.inject.Inject;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionInjectorProvider.class)
class TypeInferenceTest {
	@Inject
	private ParseHelper<STFunctionSource> parseHelper;

	static Stream<Arguments> testSimpleAssignment() {
		return Stream.of(//
				arguments("INT", "17", "INT", "SINT"), // promote to expected
				arguments("INT", "128", "INT", "INT"), // matches expected
				arguments("INT", "32768", "DINT", "DINT"), // larger than expected
				// unsigned
				arguments("UINT", "17", "UINT", "SINT"), // promote and convert to unsigned
				arguments("UINT", "128", "UINT", "INT"), // convert to unsigned
				arguments("UINT", "32768", "UINT", "DINT"), // would be larger than expected
				arguments("UINT", "65536", "UDINT", "DINT"), // larger than expected
				// bit
				arguments("WORD", "17", "WORD", "SINT"), // promote and convert to bit
				arguments("WORD", "128", "WORD", "INT"), // convert to bit
				arguments("WORD", "32768", "WORD", "DINT"), // would be larger than expected
				arguments("WORD", "65536", "DWORD", "DINT") // larger than expected
		);
	}

	@ParameterizedTest(name = "{0} := {1};")
	@MethodSource
	void testSimpleAssignment(final String typeName, final String value, final String resultTypeName,
			final String declaredResultTypeName) throws Exception {
		final var result = parseHelper.parse("""
					FUNCTION test
					VAR_TEMP
						X : %s;
					END_VAR
					X := %s;
					END_FUNCTION
				""".formatted(typeName, value));
		final STAssignment assignment = (STAssignment) getFirstStatement(result);
		assertEquals(getTypeByName(resultTypeName), assignment.getRight().getResultType(), "result type");
		assertEquals(getTypeByName(declaredResultTypeName), assignment.getRight().getDeclaredResultType(),
				"declared result type");
	}

	static Stream<Arguments> testBinaryExpression() {
		return Stream.of(//
				arguments("INT", "+", "17", "4", "INT", "SINT", "INT", "SINT"), // promote to expected
				arguments("INT", "+", "128", "4", "INT", "INT", "INT", "INT"), // matches expected
				arguments("INT", "+", "32768", "4", "DINT", "DINT", "DINT", "DINT"), // larger than expected
				arguments("INT", "+", "17", "128", "INT", "INT", "INT", "SINT"), // 2nd matches expected
				arguments("INT", "+", "17", "32768", "DINT", "DINT", "INT", "SINT"), // 2nd larger than expected
				// unsigned
				arguments("UINT", "+", "17", "4", "UINT", "SINT", "UINT", "SINT"), // promote and convert to unsigned
				arguments("UINT", "+", "128", "4", "UINT", "INT", "UINT", "INT"), // convert to unsigned
				arguments("UINT", "+", "32768", "4", "UINT", "DINT", "UINT", "DINT"), // would be larger than expected
				arguments("UINT", "+", "65536", "4", "UDINT", "DINT", "UDINT", "DINT"), // larger than expected
				arguments("UINT", "+", "17", "128", "UINT", "INT", "UINT", "SINT"), // 2nd convert to unsigned
				arguments("UINT", "+", "17", "32768", "UINT", "DINT", "UINT", "SINT"), // 2nd would be larger
				arguments("UINT", "+", "17", "65536", "UDINT", "DINT", "UINT", "SINT"), // 2nd larger than expected
				// bit
				arguments("WORD", "AND", "17", "4", "WORD", "BYTE", "WORD", "SINT"), // promote and convert to bit
				arguments("WORD", "AND", "128", "4", "WORD", "WORD", "WORD", "INT"), // convert to bit
				arguments("WORD", "AND", "32768", "4", "WORD", "DWORD", "WORD", "DINT"), // would be larger
				arguments("WORD", "AND", "65536", "4", "DWORD", "DWORD", "DWORD", "DINT"), // larger than expected
				arguments("WORD", "AND", "17", "128", "WORD", "WORD", "WORD", "SINT"), // 2nd convert to bit
				arguments("WORD", "AND", "17", "32768", "WORD", "DWORD", "WORD", "SINT"), // 2nd would be larger
				arguments("WORD", "AND", "17", "65536", "DWORD", "DWORD", "WORD", "SINT") // 2nd larger than expected
		);
	}

	@ParameterizedTest(name = "{0} := {2} {1} {3};")
	@MethodSource
	void testBinaryExpression(final String typeName, final String operator, final String value1, final String value2,
			final String exprResultTypeName, final String exprDeclaredResultTypeName, final String leftResultTypeName,
			final String leftDeclaredResultTypeName) throws Exception {
		final var result = parseHelper.parse("""
					FUNCTION test
					VAR_TEMP
						X : %s;
					END_VAR
					X := %s %s %s;
					END_FUNCTION
				""".formatted(typeName, value1, operator, value2));
		final STAssignment assignment = (STAssignment) getFirstStatement(result);
		final STBinaryExpression expression = (STBinaryExpression) assignment.getRight();
		assertEquals(getTypeByName(exprResultTypeName), expression.getResultType(), "expr result type");
		assertEquals(getTypeByName(exprDeclaredResultTypeName), expression.getDeclaredResultType(),
				"expr declared result type");

		final STExpression left = expression.getLeft();
		assertEquals(getTypeByName(leftResultTypeName), left.getResultType(), "left result type");
		assertEquals(getTypeByName(leftDeclaredResultTypeName), left.getDeclaredResultType(),
				"left declared result type");
	}

	static Stream<Arguments> testStandardFunctionCall() {
		return Stream.of(//
				arguments("INT", "ADD", "17", "4", "INT", "SINT", "INT", "SINT"), // promote to expected
				arguments("INT", "ADD", "128", "4", "INT", "INT", "INT", "INT"), // matches expected
				arguments("INT", "ADD", "32768", "4", "DINT", "DINT", "DINT", "DINT"), // larger than expected
				arguments("INT", "ADD", "17", "128", "INT", "INT", "INT", "SINT"), // 2nd matches expected
				arguments("INT", "ADD", "17", "32768", "DINT", "DINT", "INT", "SINT"), // 2nd larger than expected
				// unsigned
				arguments("UINT", "ADD", "17", "4", "UINT", "SINT", "UINT", "SINT"), // promote and convert to unsigned
				arguments("UINT", "ADD", "128", "4", "UINT", "INT", "UINT", "INT"), // convert to unsigned
				arguments("UINT", "ADD", "32768", "4", "UINT", "DINT", "UINT", "DINT"), // would be larger than expected
				arguments("UINT", "ADD", "65536", "4", "UDINT", "DINT", "UDINT", "DINT"), // larger than expected
				arguments("UINT", "ADD", "17", "128", "UINT", "INT", "UINT", "SINT"), // 2nd convert to unsigned
				arguments("UINT", "ADD", "17", "32768", "UINT", "DINT", "UINT", "SINT"), // 2nd would be larger
				arguments("UINT", "ADD", "17", "65536", "UDINT", "DINT", "UINT", "SINT"), // 2nd larger than expected
				// bit
				arguments("WORD", "AND", "17", "4", "WORD", "ANY_BIT", "WORD", "SINT"), // promote and convert to bit
				arguments("WORD", "AND", "128", "4", "WORD", "ANY_BIT", "WORD", "INT"), // convert to bit
				arguments("WORD", "AND", "32768", "4", "WORD", "ANY_BIT", "WORD", "DINT"), // would be larger
				arguments("WORD", "AND", "65536", "4", "DWORD", "ANY_BIT", "DWORD", "DINT"), // larger than expected
				arguments("WORD", "AND", "17", "128", "WORD", "ANY_BIT", "WORD", "SINT"), // 2nd convert to bit
				arguments("WORD", "AND", "17", "32768", "WORD", "ANY_BIT", "WORD", "SINT"), // 2nd would be larger
				arguments("WORD", "AND", "17", "65536", "DWORD", "ANY_BIT", "WORD", "SINT") // 2nd larger than expected
		);
	}

	@ParameterizedTest(name = "{0} := {1}({2}, {3});")
	@MethodSource
	void testStandardFunctionCall(final String typeName, final String function, final String value1,
			final String value2, final String callResultTypeName, final String callDeclaredResultTypeName,
			final String argumentResultTypeName, final String argumentDeclaredResultTypeName) throws Exception {
		final var result = parseHelper.parse("""
					FUNCTION test
					VAR_TEMP
						X : %s;
					END_VAR
					X := %s(%s, %s);
					END_FUNCTION
				""".formatted(typeName, function, value1, value2));
		final STAssignment assignment = (STAssignment) getFirstStatement(result);
		final STFeatureExpression call = (STFeatureExpression) assignment.getRight();
		assertEquals(getTypeByName(callResultTypeName), call.getResultType(), "call result type");
		assertEquals(getTypeByName(callDeclaredResultTypeName), call.getDeclaredResultType(),
				"call declared result type");

		final STCallArgument argument = call.getParameters().getFirst();
		assertEquals(getTypeByName(argumentResultTypeName), argument.getResultType(), "arg result type");
		assertEquals(getTypeByName(argumentDeclaredResultTypeName), argument.getDeclaredResultType(),
				"arg declared result type");
	}

	static Stream<Arguments> testStandardFunctionCallWithBinaryExpression() {
		return Stream.of(//
				// promote to expected
				arguments("INT", "ADD", "+", "17", "4", "21", //
						"INT", "SINT", "INT", "SINT", //
						"INT", "SINT", "INT", "SINT"),
				// matches expected
				arguments("INT", "ADD", "+", "128", "4", "21", //
						"INT", "INT", "INT", "INT", //
						"INT", "INT", "INT", "INT"),
				// larger than expected
				arguments("INT", "ADD", "+", "32768", "4", "21", //
						"DINT", "DINT", "DINT", "DINT", //
						"DINT", "DINT", "DINT", "DINT"),
				// 2nd matches expected
				arguments("INT", "ADD", "+", "17", "128", "21", //
						"INT", "INT", "INT", "INT", //
						"INT", "INT", "INT", "SINT"),
				// 2nd larger than expected
				arguments("INT", "ADD", "+", "17", "32768", "21", //
						"DINT", "DINT", "DINT", "DINT", //
						"DINT", "DINT", "INT", "SINT"),

				// unsigned
				// promote and convert to unsigned
				arguments("UINT", "ADD", "+", "17", "4", "21", //
						"UINT", "SINT", "UINT", "SINT", //
						"UINT", "SINT", "UINT", "SINT"),
				// convert to unsigned
				arguments("UINT", "ADD", "+", "128", "4", "21", //
						"UINT", "INT", "UINT", "INT", //
						"UINT", "INT", "UINT", "INT"),
				// would be larger than expected
				arguments("UINT", "ADD", "+", "32768", "4", "21", //
						"UINT", "DINT", "UINT", "DINT", //
						"UINT", "DINT", "UINT", "DINT"),
				// larger than expected
				arguments("UINT", "ADD", "+", "65536", "4", "21", //
						"UDINT", "DINT", "UDINT", "DINT", //
						"UDINT", "DINT", "UDINT", "DINT"),
				// 2nd convert to unsigned
				arguments("UINT", "ADD", "+", "17", "128", "21", //
						"UINT", "INT", "UINT", "INT", //
						"UINT", "INT", "UINT", "SINT"),
				// 2nd would be larger
				arguments("UINT", "ADD", "+", "17", "32768", "21", //
						"UINT", "DINT", "UINT", "DINT", //
						"UINT", "DINT", "UINT", "SINT"),
				// 2nd larger than expected
				arguments("UINT", "ADD", "+", "17", "65536", "21", //
						"UDINT", "DINT", "UDINT", "DINT", //
						"UDINT", "DINT", "UINT", "SINT"),

				// bit
				// promote and convert to bit
				arguments("WORD", "AND", "AND", "17", "4", "21", //
						"WORD", "ANY_BIT", "WORD", "BYTE", //
						"WORD", "BYTE", "WORD", "SINT"),
				// convert to bit
				arguments("WORD", "AND", "AND", "128", "4", "21", //
						"WORD", "ANY_BIT", "WORD", "WORD", //
						"WORD", "WORD", "WORD", "INT"),
				// would be larger
				arguments("WORD", "AND", "AND", "32768", "4", "21", //
						"WORD", "ANY_BIT", "WORD", "DWORD", //
						"WORD", "DWORD", "WORD", "DINT"),
				// larger than expected
				arguments("WORD", "AND", "AND", "65536", "4", "21", //
						"DWORD", "ANY_BIT", "DWORD", "DWORD", //
						"DWORD", "DWORD", "DWORD", "DINT"),
				// 2nd convert to bit
				arguments("WORD", "AND", "AND", "17", "128", "21", //
						"WORD", "ANY_BIT", "WORD", "WORD", //
						"WORD", "WORD", "WORD", "SINT"),
				// 2nd would be larger
				arguments("WORD", "AND", "AND", "17", "32768", "21", //
						"WORD", "ANY_BIT", "WORD", "DWORD", //
						"WORD", "DWORD", "WORD", "SINT"),
				// 2nd larger than expected
				arguments("WORD", "AND", "AND", "17", "65536", "21", //
						"DWORD", "ANY_BIT", "DWORD", "DWORD", //
						"DWORD", "DWORD", "WORD", "SINT"));
	}

	@ParameterizedTest(name = "{0} := {1}({3} {2} {4}, {5});")
	@MethodSource
	void testStandardFunctionCallWithBinaryExpression(final String typeName, final String function,
			final String operator, final String value1, final String value2, final String value3,
			final String callResultTypeName, final String callDeclaredResultTypeName,
			final String argumentResultTypeName, final String argumentDeclaredResultTypeName,
			final String exprResultTypeName, final String exprDeclaredResultTypeName, final String leftResultTypeName,
			final String leftDeclaredResultTypeName) throws Exception {
		final var result = parseHelper.parse("""
					FUNCTION test
					VAR_TEMP
						X : %s;
					END_VAR
					X := %s(%s %s %s, %s);
					END_FUNCTION
				""".formatted(typeName, function, value1, operator, value2, value3));
		final STAssignment assignment = (STAssignment) getFirstStatement(result);
		final STFeatureExpression call = (STFeatureExpression) assignment.getRight();
		assertEquals(getTypeByName(callResultTypeName), call.getResultType(), "call result type");
		assertEquals(getTypeByName(callDeclaredResultTypeName), call.getDeclaredResultType(),
				"call declared result type");

		final STCallArgument argument = call.getParameters().getFirst();
		assertEquals(getTypeByName(argumentResultTypeName), argument.getResultType(), "arg result type");
		assertEquals(getTypeByName(argumentDeclaredResultTypeName), argument.getDeclaredResultType(),
				"arg declared result type");

		final STBinaryExpression expression = (STBinaryExpression) argument.getArgument();
		assertEquals(getTypeByName(exprResultTypeName), expression.getResultType(), "expr result type");
		assertEquals(getTypeByName(exprDeclaredResultTypeName), expression.getDeclaredResultType(),
				"expr declared result type");

		final STExpression left = expression.getLeft();
		assertEquals(getTypeByName(leftResultTypeName), left.getResultType(), "left result type");
		assertEquals(getTypeByName(leftDeclaredResultTypeName), left.getDeclaredResultType(),
				"left declared result type");
	}

	@Test
	void testNestedBinaryExpression() throws Exception {
		final var result = parseHelper.parse("""
					FUNCTION test
					VAR_TEMP
						X : BOOL;
						Y : DWORD;
					END_VAR
					X := X AND ((Y AND 16#0000_1000) <> 0);
					END_IF;
					END_FUNCTION
				""");
		final STAssignment assignment = (STAssignment) getFirstStatement(result);
		final STBinaryExpression outer = (STBinaryExpression) assignment.getRight();
		assertEquals(ElementaryTypes.BOOL, outer.getResultType(), "outer result type");
		assertEquals(ElementaryTypes.BOOL, outer.getDeclaredResultType(), "outer declared result type");

		final STBinaryExpression inner = (STBinaryExpression) outer.getRight();
		assertEquals(ElementaryTypes.BOOL, inner.getResultType(), "inner result type");
		assertEquals(ElementaryTypes.BOOL, inner.getDeclaredResultType(), "inner declared result type");

		final STExpression innerRight = inner.getRight();
		assertEquals(ElementaryTypes.DWORD, innerRight.getResultType(), "inner right result type");
		assertEquals(ElementaryTypes.SINT, innerRight.getDeclaredResultType(), "inner right declared result type");

		final STBinaryExpression innermost = (STBinaryExpression) inner.getLeft();
		assertEquals(ElementaryTypes.DWORD, innermost.getResultType(), "innermost result type");
		assertEquals(ElementaryTypes.DWORD, innermost.getDeclaredResultType(), "innermost declared result type");

		final STExpression innermostRight = innermost.getRight();
		assertEquals(ElementaryTypes.WORD, innermostRight.getResultType(), "innermost right result type");
		assertEquals(ElementaryTypes.INT, innermostRight.getDeclaredResultType(),
				"innermost right declared result type");
	}

	private static STStatement getFirstStatement(final STFunctionSource source) {
		return source.getFunctions().getFirst().getCode().getFirst();
	}

	private static DataType getTypeByName(final String typeName) {
		return Stream.of(ElementaryTypes.getAllElementaryType(), GenericTypes.getAllGenericTypes())
				.flatMap(Collection::stream).filter(type -> typeName.equals(type.getName())).findFirst().orElseThrow();
	}
}
