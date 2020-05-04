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
public class ForteNgAtArrayAccessTest extends ForteNgArrayAtAccessXtend {

	private String sourceType;
	private String accessType;
	private String accessor;
	private int arrayStart;
	private int arrayStop;
	private int index;
	private String value;
	private boolean isValid;

	private static final String LWORD = "LWORD"; //$NON-NLS-1$
	private static final String DWORD = "DWORD"; //$NON-NLS-1$
	private static final String WORD = "WORD"; //$NON-NLS-1$
	private static final String BYTE = "BYTE"; //$NON-NLS-1$
	private static final String BOOL = "BOOL"; //$NON-NLS-1$

	private static final String BOOLACCESS_SHORT = ""; //$NON-NLS-1$
	private static final String BOOLACCESS = "%X"; //$NON-NLS-1$
	private static final String BYTEACCESS = "%B"; //$NON-NLS-1$
	private static final String WORDACCESS = "%W"; //$NON-NLS-1$
	private static final String DWORDACCESS = "%D"; //$NON-NLS-1$

	private static final String VALUE_TRUE = "true"; //$NON-NLS-1$
	private static final String VALUE_FALSE = "false"; //$NON-NLS-1$

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
	@Parameterized.Parameters
	public static Collection testCases() {
		return Arrays.asList(new Object[][] { //
				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 31, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 31, 32, VALUE_TRUE, INVALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 31, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 31, 32, VALUE_TRUE, INVALID_ACCESS }, //

				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 31, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS_SHORT, 0, 31, 32, VALUE_FALSE, INVALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 31, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ DWORD, BOOL, BOOLACCESS, 0, 31, 32, VALUE_FALSE, INVALID_ACCESS }, //

				{ DWORD, BYTE, BYTEACCESS, 0, 3, 0, VALUE_42, VALID_ACCESS }, //
				{ DWORD, BYTE, BYTEACCESS, 0, 1, 2, VALUE_42, INVALID_ACCESS }, //
				{ DWORD, BYTE, BYTEACCESS, 0, 3, 4, VALUE_42, INVALID_ACCESS }, //

				{ DWORD, WORD, WORDACCESS, 0, 1, 0, VALUE_42, VALID_ACCESS }, //
				{ DWORD, WORD, WORDACCESS, 0, 1, 2, VALUE_42, INVALID_ACCESS }, //

				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 63, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 63, 65, VALUE_TRUE, INVALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 63, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 1, 0, VALUE_TRUE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 63, 65, VALUE_TRUE, INVALID_ACCESS }, //

				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 63, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS_SHORT, 0, 63, 65, VALUE_FALSE, INVALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 63, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 1, 0, VALUE_FALSE, VALID_ACCESS }, //
				{ LWORD, BOOL, BOOLACCESS, 0, 63, 65, VALUE_FALSE, INVALID_ACCESS }, //

				{ LWORD, BYTE, BYTEACCESS, 0, 7, 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, BYTE, BYTEACCESS, 0, 1, 2, VALUE_42, INVALID_ACCESS }, //
				{ LWORD, BYTE, BYTEACCESS, 0, 7, 8, VALUE_42, INVALID_ACCESS }, //

				{ LWORD, WORD, WORDACCESS, 0, 3, 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, WORD, WORDACCESS, 0, 3, 4, VALUE_42, INVALID_ACCESS }, //

				{ LWORD, DWORD, DWORDACCESS, 0, 1, 0, VALUE_42, VALID_ACCESS }, //
				{ LWORD, DWORD, DWORDACCESS, 0, 1, 2, VALUE_42, INVALID_ACCESS }, //
		});
	}

	@Test
	public void testArrayAtAccess() {
		testLocatedArrayAtAccess(sourceType, accessType, arrayStart, arrayStop, accessor, index, value, isValid);
	}

}
