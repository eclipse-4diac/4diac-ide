/*******************************************************************************
 * Copyright (c) 2026 Cybernetic Solutions GbR and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tim Grocki - assert ECController UpdateInterval initial value matches
 *                   the 1 ms FORTE default
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * Guards the ECController UpdateInterval initial value in the IO type library.
 * The IDE skips deploying values that equal the type initial value, so this
 * string must stay aligned with FORTE's 1 ms default.
 */
class ECControllerUpdateIntervalTest {

	private static final String ECCONTROLLER_FBT = "io-3.0.0/typelib/ethercat/ECController.fbt"; //$NON-NLS-1$
	private static final Pattern UPDATE_INTERVAL_INITIAL = Pattern.compile(
			"<VarDeclaration\\s+Name=\"UpdateInterval\"\\s+Type=\"TIME\"\\s+InitialValue=\"([^\"]+)\""); //$NON-NLS-1$
	private static final String EXPECTED_INITIAL_VALUE = "T#1ms"; //$NON-NLS-1$

	@Test
	void updateIntervalInitialValueIs1ms() throws Exception {
		final Path fbt = TestRepositoryPaths.resolve().standardLibraries().resolve(ECCONTROLLER_FBT);
		assertTrue(Files.isRegularFile(fbt), () -> "missing " + fbt); //$NON-NLS-1$

		final String xml = Files.readString(fbt, StandardCharsets.UTF_8);
		final Matcher matcher = UPDATE_INTERVAL_INITIAL.matcher(xml);
		assertTrue(matcher.find(), "UpdateInterval VarDeclaration with InitialValue missing"); //$NON-NLS-1$
		assertEquals(EXPECTED_INITIAL_VALUE, matcher.group(1));
	}
}
