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
public class ForteNgAtArrayDeclarationTest extends ForteNgArrayAtDeclarationXtend {

	private String sourceType;
	private String accessType;
	private int arrayStart;
	private int arrayStop;
	private boolean isValid;

	private static final String LWORD = "LWORD"; //$NON-NLS-1$
	private static final String DWORD = "DWORD"; //$NON-NLS-1$
	private static final String WORD = "WORD"; //$NON-NLS-1$
	private static final String BYTE = "BYTE"; //$NON-NLS-1$
	private static final String BOOL = "BOOL"; //$NON-NLS-1$

	public ForteNgAtArrayDeclarationTest(String sourceType, String accessType, int arrayStart, int arrayStop,
			boolean isValid) {
		super();
		this.sourceType = sourceType;
		this.accessType = accessType;
		this.arrayStart = arrayStart;
		this.arrayStop = arrayStop;
		this.isValid = isValid;
	}

	@SuppressWarnings("rawtypes")
	@Parameterized.Parameters
	public static Collection testCases() {
		return Arrays.asList(new Object[][] { //
				{ LWORD, DWORD, 0, 1, VALID_DECLARATION }, //
				{ LWORD, WORD, 0, 3, VALID_DECLARATION }, //
				{ LWORD, BYTE, 0, 7, VALID_DECLARATION }, //
				{ LWORD, BOOL, 0, 63, VALID_DECLARATION }, //
				{ LWORD, DWORD, 0, 2, INVALID_DECLARATION }, //
				{ LWORD, WORD, 0, 4, INVALID_DECLARATION }, //
				{ LWORD, BYTE, 0, 8, INVALID_DECLARATION }, //
				{ LWORD, BOOL, 0, 64, INVALID_DECLARATION }, //
				{ DWORD, WORD, 0, 1, VALID_DECLARATION }, //
				{ DWORD, BYTE, 0, 3, VALID_DECLARATION }, //
				{ DWORD, BOOL, 0, 31, VALID_DECLARATION }, //
				{ DWORD, WORD, 0, 2, INVALID_DECLARATION }, //
				{ DWORD, BYTE, 0, 4, INVALID_DECLARATION }, //
				{ DWORD, BOOL, 0, 32, INVALID_DECLARATION }, //
				{ WORD, BYTE, 0, 1, VALID_DECLARATION }, //
				{ WORD, BOOL, 0, 15, VALID_DECLARATION }, //
				{ WORD, BYTE, 0, 2, INVALID_DECLARATION }, //
				{ WORD, BOOL, 0, 16, INVALID_DECLARATION }, //
				{ BYTE, BOOL, 0, 7, VALID_DECLARATION }, //
				{ BYTE, BOOL, 0, 8, INVALID_DECLARATION }, //
				{ "LINT", BOOL, 0, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ "DINT", BOOL, 0, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ "INT", BOOL, 0, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ "SINT", BOOL, 0, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ "REAL", BOOL, 0, 8, INVALID_DECLARATION }, //$NON-NLS-1$
		});
	}

	@Test
	public void testArrayAtDeclaration() {
		testLocatedArrayDeclaration(sourceType, accessType, arrayStart, arrayStop, isValid);
	}

}
