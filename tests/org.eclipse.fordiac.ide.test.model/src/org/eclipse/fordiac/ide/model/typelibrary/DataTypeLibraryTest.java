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
package org.eclipse.fordiac.ide.model.typelibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class DataTypeLibraryTest {

	static DataTypeLibrary dataTypeLibrary;

	@BeforeAll
	static void setup() {
		dataTypeLibrary = new DataTypeLibrary();
	}

	@Test
	void testElementaryTypes() {
		for (final DataType type : ElementaryTypes.getAllElementaryType()) {
			assertEquals(type, dataTypeLibrary.getType(type.getName()));
		}
	}

	@Test
	void testGenericTypes() {
		for (final DataType type : GenericTypes.getAllGenericTypes()) {
			assertEquals(type, dataTypeLibrary.getType(type.getName()));
		}
	}

	@Test
	void testStringMaxLength() {
		final DataType type = dataTypeLibrary.getType("STRING[20]"); //$NON-NLS-1$
		assertInstanceOf(StringType.class, type);
		assertEquals(20, ((AnyStringType) type).getMaxLength());

		assertSame(type, dataTypeLibrary.getType(type.getName()));
		assertSame(type, dataTypeLibrary.getTypeIfExists(type.getName()));
	}

	@Test
	void testWStringMaxLength() {
		final DataType type = dataTypeLibrary.getType("WSTRING[20]"); //$NON-NLS-1$
		assertInstanceOf(WstringType.class, type);
		assertEquals(20, ((AnyStringType) type).getMaxLength());

		assertSame(type, dataTypeLibrary.getType(type.getName()));
		assertSame(type, dataTypeLibrary.getTypeIfExists(type.getName()));
	}

	@Test
	void testInvalidMaxLength() {
		assertInstanceOf(ErrorMarkerDataType.class, dataTypeLibrary.getType("STRING[-1]")); //$NON-NLS-1$
		assertInstanceOf(ErrorMarkerDataType.class, dataTypeLibrary.getType("STRING[ABC]")); //$NON-NLS-1$
		assertInstanceOf(ErrorMarkerDataType.class, dataTypeLibrary.getType("WSTRING[ABC]")); //$NON-NLS-1$
		assertInstanceOf(ErrorMarkerDataType.class, dataTypeLibrary.getType("NO_STRING[17]")); //$NON-NLS-1$
		assertInstanceOf(ErrorMarkerDataType.class, dataTypeLibrary.getType("DINT[17]")); //$NON-NLS-1$
	}
}
