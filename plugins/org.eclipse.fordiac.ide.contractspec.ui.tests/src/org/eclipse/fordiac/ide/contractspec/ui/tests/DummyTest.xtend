/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *               
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - fix automatic build not working
 *******************************************************************************/
package org.eclipse.fordiac.ide.contractspec.ui.tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions

/* At least one test is needed, otherwise the automatic build does not work.
 * (maven build error: No tests found)
 */
class DummyTest {

	@Test
	def void dummyTest() {
		Assertions.assertEquals(true, true);
	}
}
