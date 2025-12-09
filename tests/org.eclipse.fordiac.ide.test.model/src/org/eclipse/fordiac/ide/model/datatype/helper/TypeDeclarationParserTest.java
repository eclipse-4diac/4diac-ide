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
package org.eclipse.fordiac.ide.model.datatype.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class TypeDeclarationParserTest {

	@Test
	void testLegacyTypeDeclaration() {
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..0] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "1")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..16] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "17")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..16] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "  17 ")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0..1")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0..17")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0..17+4")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0..VAR")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseLegacyTypeDeclaration(ElementaryTypes.INT, "0..FUNC(1+2)")); //$NON-NLS-1$
	}

	@Test
	void testSimpleTypeDeclaration() {
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "1")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "17")); //$NON-NLS-1$

		assertTypeNameEquals("ARRAY [0..1] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..1")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..17] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..17")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [-10..10] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "-10..10")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..17] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "  0  .. 17  ")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..1, -10..10] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..1,-10..10")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..17, 21..42] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..17,21..42")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [0..17, 21..42] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "  0  .. 17  ,  21  .. 42  ")); //$NON-NLS-1$

		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..17+4")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..VAR")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..FUNC(1, 2)")); //$NON-NLS-1$

		assertTypeNameEquals("ARRAY [*, *] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..17+4, 1..VAR")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*, 0..1] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "0..VAR,0..1")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*, 4..17] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT,
						"0..FUNC(1, \"abc))(([{$\"$$$00\"), 4..17")); //$NON-NLS-1$

		assertTypeNameEquals("ARRAY [*] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "*")); //$NON-NLS-1$
		assertTypeNameEquals("ARRAY [*, *] OF INT", //$NON-NLS-1$
				TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "*,*")); //$NON-NLS-1$

		assertNull(TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "(")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, ")")); //$NON-NLS-1$
		assertNull(TypeDeclarationParser.parseSimpleTypeDeclaration(ElementaryTypes.INT, "(()))")); //$NON-NLS-1$
	}

	@Test
	void testCheckSimpleTypeDeclaration() {
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration(null));
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("1")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("17")); //$NON-NLS-1$

		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("0..1")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("0..17")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("-10..10")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("  0  .. 17  ")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("0..1,-10..10")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("0..17,21..42")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isSimpleTypeDeclaration("  0  .. 17  ,  21  .. 42  ")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..17+4")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..VAR")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..FUNC(1, 2)")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..17+4, 1..VAR")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..VAR,0..1")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("0..FUNC(1, \"abc))(([{$\"$$$00\"), 4..17")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("*")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("*,*")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("(")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration(")")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isSimpleTypeDeclaration("(()))")); //$NON-NLS-1$
	}

	@Test
	void testCheckVariableArrayBounds() {
		assertFalse(TypeDeclarationParser.isVariableArrayBounds(null));
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("17")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("17, 4")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("17 + 4 * 2, 42")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isVariableArrayBounds("0..17")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("-10..10")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("0..17,21..42")); //$NON-NLS-1$

		assertFalse(TypeDeclarationParser.isVariableArrayBounds("0..17*4")); //$NON-NLS-1$
		assertFalse(TypeDeclarationParser.isVariableArrayBounds("0..17*4, 1..VAR")); //$NON-NLS-1$

		assertTrue(TypeDeclarationParser.isVariableArrayBounds("*")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isVariableArrayBounds("*,*")); //$NON-NLS-1$
		assertTrue(TypeDeclarationParser.isVariableArrayBounds("0..17, *")); //$NON-NLS-1$
	}

	protected void assertTypeNameEquals(final String expected, final DataType type) {
		assertNotNull(type);
		assertEquals(expected, type.getName());
	}
}
