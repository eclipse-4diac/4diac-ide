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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@SuppressWarnings("static-method")
class DataTypeLibraryTest {

	static DataTypeLibrary dataTypeLibrary;

	@BeforeAll
	static void setup() {
		dataTypeLibrary = new DataTypeLibrary(null);
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
		assertInstanceOf(ErrorDataType.class, dataTypeLibrary.getType("STRING[-1]")); //$NON-NLS-1$
		assertInstanceOf(ErrorDataType.class, dataTypeLibrary.getType("STRING[ABC]")); //$NON-NLS-1$
		assertInstanceOf(ErrorDataType.class, dataTypeLibrary.getType("WSTRING[ABC]")); //$NON-NLS-1$
		assertInstanceOf(ErrorDataType.class, dataTypeLibrary.getType("NO_STRING[17]")); //$NON-NLS-1$
		assertInstanceOf(ErrorDataType.class, dataTypeLibrary.getType("DINT[17]")); //$NON-NLS-1$
	}

	@Test
	@Timeout(10)
	void testConcurrentModifyLookup() throws Throwable {
		final DataTypeLibrary library = new DataTypeLibrary(null);
		final String name = "ConcurrentModifyLookup"; //$NON-NLS-1$
		final StructuredType type = createStructuredType(name);
		final DataTypeEntryMock entry = new DataTypeEntryMock(type, null, null);
		assertInstanceOf(ErrorDataType.class, library.getType(name));

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		final ScheduledFuture<?> lookupFuture = executor.scheduleWithFixedDelay(() -> library.getType(name), 0, 1,
				TimeUnit.NANOSECONDS);

		try {
			for (int attempt = 0; attempt < 1000; attempt++) {
				library.addTypeEntry(entry);
				assertSame(type, library.getType(name));

				library.removeTypeEntry(entry);
				assertInstanceOf(ErrorDataType.class, library.getType(name));
			}
		} finally {
			executor.shutdownNow();
		}

		assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Timed out waiting for executor to terminate"); //$NON-NLS-1$
		if (lookupFuture.state() == Future.State.FAILED) {
			throw lookupFuture.exceptionNow();
		}
	}

	private static StructuredType createStructuredType(final String name) {
		final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
		type.setName(name);
		return type;
	}
}
