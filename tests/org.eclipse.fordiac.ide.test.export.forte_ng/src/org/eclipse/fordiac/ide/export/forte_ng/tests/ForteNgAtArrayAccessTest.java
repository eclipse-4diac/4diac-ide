/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.forte_ng.tests;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

@RunWith(Parameterized.class)
public class ForteNgAtArrayAccessTest extends ForteNgArrayAtAccessXtend implements DatatypeConstants {

	private String sourceType;
	private String accessType;
	private String accessor;
	private int arrayStart;
	private int arrayStop;
	private int index;
	private String value;
	private boolean isValid;

	private static final String BOOLACCESS_SHORT = ""; //$NON-NLS-1$
	private static final String BOOLACCESS = "%X"; //$NON-NLS-1$
	private static final String BYTEACCESS = "%B"; //$NON-NLS-1$
	private static final String WORDACCESS = "%W"; //$NON-NLS-1$
	private static final String DWORDACCESS = "%D"; //$NON-NLS-1$

	private static final String VALUE_TRUE = "true"; //$NON-NLS-1$
	private static final String VALUE_FALSE = "false"; //$NON-NLS-1$

	static int getSize(String type) {
		return DatatypeConstants.getSize(type);
	}

	static int indexStop(String sourceType, String accessType) {
		return DatatypeConstants.indexStop(sourceType, accessType);
	}

	private static final String VALUE_42 = "42"; //$NON-NLS-1$

	public ForteNgAtArrayAccessTest(String sourceType, String accessType, String accessor, int arrayStart,
			int arrayStop, int index, String value, boolean isValid) {
		super();
		this.sourceType = sourceType;
		this.accessType = accessType;
		this.accessor = accessor;
		this.arrayStart = arrayStart;
		this.arrayStop = arrayStop;
		this.index = index;
		this.value = value;
		this.isValid = isValid;
	}

	@SuppressWarnings("rawtypes")
	@Parameterized.Parameters(name = "{index}: {0}.{2}{5}={6}")
	public static Collection testCases() {
		return Arrays.asList(new Object[][] { //
				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1,
						VALUE_TRUE, INVALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1, VALUE_TRUE,
						INVALID_ACCESS }, //

				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1,
						VALUE_FALSE, INVALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1, VALUE_FALSE,
						INVALID_ACCESS }, //

				{ DWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(DWORD, BYTE), 0, VALUE_42, VALID_ACCESS }, //
				{ DWORD, BYTE, BYTEACCESS, INDEX_START, 1, 2, VALUE_42, INVALID_ACCESS }, //
				{ DWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(DWORD, BYTE), indexStop(DWORD, BYTE) + 1, VALUE_42,
						INVALID_ACCESS }, //

				{ DWORD, WORD, WORDACCESS, INDEX_START, indexStop(DWORD, WORD), 0, VALUE_42, VALID_ACCESS }, //
				{ DWORD, WORD, WORDACCESS, INDEX_START, indexStop(DWORD, WORD), 2, VALUE_42, INVALID_ACCESS }, //

				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1,
						VALUE_TRUE, INVALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1, VALUE_TRUE,
						INVALID_ACCESS }, //

				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1,
						VALUE_FALSE, INVALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1, VALUE_FALSE,
						INVALID_ACCESS }, //

				{ LWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(LWORD, BYTE), 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, BYTE, BYTEACCESS, INDEX_START, 1, 2, VALUE_42, INVALID_ACCESS }, //
				{ LWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(LWORD, BYTE), indexStop(LWORD, BYTE) + 1, VALUE_42,
						INVALID_ACCESS }, //

				{ LWORD, WORD, WORDACCESS, INDEX_START, indexStop(LWORD, WORD), 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, WORD, WORDACCESS, INDEX_START, indexStop(LWORD, WORD), indexStop(LWORD, WORD) + 1, VALUE_42,
						INVALID_ACCESS }, //

				{ LWORD, DWORD, DWORDACCESS, INDEX_START, indexStop(LWORD, DWORD), 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, DWORD, DWORDACCESS, INDEX_START, indexStop(LWORD, DWORD), indexStop(LWORD, DWORD) + 1,
						VALUE_42, INVALID_ACCESS }, //
		});
	}

	@Test
	public void testArrayAtAccess() {
		testLocatedArrayAtAccess(sourceType, accessType, arrayStart, arrayStop, accessor, index, value, isValid);
	}

}
