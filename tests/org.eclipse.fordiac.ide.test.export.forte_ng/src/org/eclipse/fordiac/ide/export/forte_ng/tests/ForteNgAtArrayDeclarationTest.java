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
public class ForteNgAtArrayDeclarationTest extends ForteNgArrayAtDeclarationXtend implements DatatypeConstants {

	private String sourceType;
	private String accessType;
	private int arrayStart;
	private int arrayStop;
	private boolean isValid;

	static int getSize(String type) {
		return DatatypeConstants.getSize(type);
	}

	static int indexStop(String sourceType, String accessType) {
		return DatatypeConstants.indexStop(sourceType, accessType);
	}

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
	@Parameterized.Parameters(name = "{index}: {0}->{1}[{2}..{3}]")
	public static Collection testCases() {
		return Arrays.asList(new Object[][] { //
				{ LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD), VALID_DECLARATION }, //
				{ LWORD, WORD, INDEX_START, indexStop(LWORD, WORD), VALID_DECLARATION }, //
				{ LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE), VALID_DECLARATION }, //
				{ LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL), VALID_DECLARATION }, //
				{ LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD) + 1, INVALID_DECLARATION }, //
				{ LWORD, WORD, INDEX_START, indexStop(LWORD, WORD) + 1, INVALID_DECLARATION }, //
				{ LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE) + 1, INVALID_DECLARATION }, //
				{ LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL) + 1, INVALID_DECLARATION }, //
				{ DWORD, WORD, INDEX_START, indexStop(DWORD, WORD), VALID_DECLARATION }, //
				{ DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE), VALID_DECLARATION }, //
				{ DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL), VALID_DECLARATION }, //
				{ DWORD, WORD, INDEX_START, indexStop(DWORD, WORD) + 1, INVALID_DECLARATION }, //
				{ DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE) + 1, INVALID_DECLARATION }, //
				{ DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL) + 1, INVALID_DECLARATION }, //
				{ WORD, BYTE, INDEX_START, indexStop(WORD, BYTE), VALID_DECLARATION }, //
				{ WORD, BOOL, INDEX_START, indexStop(WORD, BOOL), VALID_DECLARATION }, //
				{ WORD, BYTE, INDEX_START, indexStop(WORD, BYTE) + 1, INVALID_DECLARATION }, //
				{ WORD, BOOL, INDEX_START, indexStop(WORD, BOOL) + 1, INVALID_DECLARATION }, //
				{ BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL), VALID_DECLARATION }, //
				{ BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL) + 1, INVALID_DECLARATION }, //
				{ "LINT", BOOL, INDEX_START, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ DINT, BOOL, INDEX_START, 8, INVALID_DECLARATION }, //
				{ "INT", BOOL, INDEX_START, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ "SINT", BOOL, INDEX_START, 8, INVALID_DECLARATION }, //$NON-NLS-1$
				{ REAL, BOOL, INDEX_START, 8, INVALID_DECLARATION }, //
		});
	}

	@Test
	public void testArrayAtDeclaration() {
		testLocatedArrayDeclaration(sourceType, accessType, arrayStart, arrayStop, isValid);
	}

}
