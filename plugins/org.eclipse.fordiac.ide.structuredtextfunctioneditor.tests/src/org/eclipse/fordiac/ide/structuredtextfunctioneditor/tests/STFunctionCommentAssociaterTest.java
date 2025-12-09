/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionInjectorProvider.class)
@SuppressWarnings({ "nls", "squid:S2479" })
class STFunctionCommentAssociaterTest {

	private static final Pattern SL_COMMENT_PATTERN = Pattern.compile("// (\\w+) (.*)\\R"); //$NON-NLS-1$
	private static final Pattern ML_COMMENT_PATTERN = Pattern.compile("/\\* (\\w+) (.*)\\*/", Pattern.DOTALL); //$NON-NLS-1$

	@Inject
	ParseHelper<STFunctionSource> parseHelper;

	@Inject
	STCoreCommentAssociater commentAssociater;

	@Test
	void emptyFunctionDeclarationSL() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				// BEFORE FUNCTION
				FUNCTION test // INNER FUNCTION
				// INNER FUNCTION
				END_FUNCTION // AFTER FUNCTION
				// AFTER FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void emptyFunctionDeclarationML() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				/* BEFORE FUNCTION
				 */
				FUNCTION test /* INNER FUNCTION
				 */
				/* INNER FUNCTION
				 */
				END_FUNCTION /* AFTER FUNCTION
				 */
				/* AFTER FUNCTION
				 */
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleFunctionDeclarationSL() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				// BEFORE FUNCTION
				FUNCTION test // BEFORE VAR_TEMP
				VAR_TEMP // BEFORE X
					X: DINT; // AFTER X
					// BEFORE Y
					Y: DINT; // AFTER Y
					// AFTER Y
				END_VAR // AFTER VAR_TEMP
				// AFTER VAR_TEMP
				END_FUNCTION // AFTER FUNCTION
				// AFTER FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleFunctionDeclarationML() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				/* BEFORE FUNCTION
				 */
				FUNCTION test /* BEFORE VAR_TEMP
				 */
				VAR_TEMP /* BEFORE X
				 */
					X: DINT; /* AFTER X */
					/* BEFORE Y */
					Y: DINT; /* AFTER Y */
					/* AFTER Y */
				END_VAR /* AFTER VAR_TEMP */
				END_FUNCTION /* AFTER FUNCTION */
				/* AFTER FUNCTION */
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleAssignmentsSL() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR // AFTER VAR_TEMP
				// BEFORE X
				X := 17; // AFTER X := 17;
				// BEFORE Y
				Y := 17; // AFTER Y := 17;
				// AFTER Y := 17;
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleAssignmentsML() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR /* AFTER VAR_TEMP */
				/* BEFORE X */
				X := 17; /* AFTER X := 17; */
				/* BEFORE Y */
				Y := 17; /* AFTER Y := 17; */
				/* AFTER Y := 17; */
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void emptyBlocksSL() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR // AFTER VAR_TEMP
				// BEFORE IF
				IF X < 0 THEN // BEFORE ELSIF
				ELSIF X > 0 THEN // AFTER ELSIF
					// BEFORE ELSE
				ELSE // AFTER ELSE
					// AFTER ELSE
				END_IF; // AFTER IF

				// BEFORE FOR
				FOR X := 0 TO 42 DO // INNER FOR
					// INNER FOR
				END_FOR; // AFTER FOR
				// AFTER FOR
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void emptyBlocksML() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR /* AFTER VAR_TEMP */
				/* BEFORE IF */
				IF X < 0 THEN /* BEFORE ELSIF */
				ELSIF X > 0 THEN /* AFTER ELSIF */
					/* BEFORE ELSE */
				ELSE /* AFTER ELSE */
					/* AFTER ELSE */
				END_IF; /* AFTER IF */

				/* BEFORE FOR */
				FOR X := 0 TO 42 DO /* INNER FOR */
					/* INNER FOR */
				END_FOR; /* AFTER FOR */
				/* AFTER FOR */
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleBlocksSL() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR // AFTER VAR_TEMP
				// BEFORE IF
				IF X < 0 THEN // BEFORE Y
					Y := 17;
				ELSIF X > 0 THEN // BEFORE Y
					Y := 4;
				ELSE // BEFORE Y
					Y := 21;
				END_IF; // AFTER IF

				// BEFORE FOR
				FOR X := 0 TO 42 DO // BEFORE Y
					Y := Y * 2; // AFTER Y :=
				END_FOR; // AFTER FOR

				// BEFORE X
				X := 17; // AFTER X := 17;
				// AFTER X := 17;
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void simpleBlocksML() throws Exception {
		final STFunctionSource source = parseHelper.parse("""
				FUNCTION test
				VAR_TEMP
					X: DINT;
					Y: DINT;
				END_VAR /* AFTER VAR_TEMP */
				/* BEFORE IF */
				IF X < 0 THEN /* BEFORE Y */
					Y := 17;
				ELSIF X > 0 THEN /* BEFORE Y */
					Y := 4;
				ELSE /* BEFORE Y */
					Y := 21;
				END_IF; /* AFTER IF */

				/* BEFORE FOR */
				FOR X := 0 TO 42 DO /* BEFORE Y */
					Y := Y * 2; /* AFTER Y := */
				END_FOR; /* AFTER FOR */

				/* BEFORE X */
				X := 17; /* AFTER X := 17; */
				/* AFTER X := 17; */
				END_FUNCTION
				""");
		assertNoErrors(source);
		assertComments(source);
	}

	@Test
	void complexML() throws Exception {
		final STFunctionSource source = parseHelper
				.parse("""
						FUNCTION /* INNER FUNCTION */ test
						VAR_TEMP
							X: /* INNER X */ DINT;
							Y: /* INNER Y */ DINT;
						END_VAR /* AFTER VAR_TEMP */
						/* BEFORE IF */
						IF /* BEFORE X */ X /* AFTER X */ < /* BEFORE 0 */ 0 /* AFTER 0 */ THEN /* BEFORE Y */
							Y := 17;
						ELSIF /* BEFORE X */ X /* AFTER X */ > /* BEFORE 0 */ 0 /* AFTER 0 */ THEN /* BEFORE Y */
							Y := 4;
						ELSE /* BEFORE Y */
							Y := 21;
						END_IF; /* AFTER IF */

						/* BEFORE FOR */
						FOR /* BEFORE X */ X /* AFTER X */ := /* BEFORE 0 */ 0 /* AFTER 0 */ TO /* BEFORE 42 */ 42 /* AFTER 42 */ DO /* BEFORE Y */
							Y := Y * 2; /* AFTER Y := */
						END_FOR; /* AFTER FOR */

						/* BEFORE X */
						/* BEFORE X */ X /* AFTER X */ := /* BEFORE 17 */ 17 /* AFTER 17 */; /* AFTER X := */
						/* AFTER X := */
						END_FUNCTION
						""");
		assertNoErrors(source);
		assertComments(source);
	}

	private void assertComments(final STFunctionSource source) {
		final var comments = commentAssociater.associateComments(source);
		for (final var comment : comments) {
			assertComment(comment);
		}
	}

	private static void assertComment(final STComment comment) {
		final var matcher = (comment.getText().startsWith("//") ? SL_COMMENT_PATTERN : ML_COMMENT_PATTERN) //$NON-NLS-1$
				.matcher(comment.getText());
		assertTrue(matcher.matches(), "Non matching comment: " + comment.getText()); //$NON-NLS-1$
		final STCommentPosition expectedCommentPosition = STCommentPosition.valueOf(matcher.group(1));
		assertEquals(expectedCommentPosition, comment.getPosition(), "Position in " + comment.getText()); //$NON-NLS-1$
		final String actualCommentContext = NodeModelUtils
				.getTokenText(NodeModelUtils.findActualNodeFor(comment.getContext()));
		final String expectedCommentContext = matcher.group(2).trim();
		assertTrue(actualCommentContext.startsWith(expectedCommentContext),
				String.format("Context mismatch in \"%s\": expected to start with: <%s> but was: <%s>", //$NON-NLS-1$
						comment.getText(), expectedCommentContext, actualCommentContext));
	}

	private static void assertNoErrors(final STFunctionSource source) {
		assertNotNull(source);
		final var errors = source.eResource().getErrors();
		assertTrue(errors.isEmpty(),
				"Unexpected errors: " + errors.stream().map(Object::toString).collect(Collectors.joining(", "))); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
