/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NameRepositoryTest {

	public static Stream<Arguments> createUniqueNameTestCases() {
		return Stream.of(
				Arguments.of("a2147483646", "a2147483647"),//$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("a2147483647", "a2147483647_1"),//$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("a2147483648", "a2147483648_1"),//$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("a0147483648", "a0147483649"),//$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("a00147483648", "a00147483648_1"),//$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("a", "a_1"),//$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("a1", "a2"),//$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of("a00001", "a00002")//$NON-NLS-1$ //$NON-NLS-2$
				);
	}

	@ParameterizedTest(name = "{index}: {0}->{1}")
	@MethodSource("createUniqueNameTestCases")
	@SuppressWarnings({ "squid:S5803", "squid:S5960", "static-method" })
	void createUniqueName(final String input, final String output) {
		assertEquals(NameRepository.createUniqueName(input, input), output);
	}

}
