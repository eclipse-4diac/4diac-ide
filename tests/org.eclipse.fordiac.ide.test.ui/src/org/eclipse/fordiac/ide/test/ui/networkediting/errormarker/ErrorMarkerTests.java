/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.networkediting.errormarker;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ErrorMarkerTests extends Abstract4diacUITests {

	/**
	 * Checks if error marker does not appears (expected).
	 *
	 * The method has 2 FBs E_SWITCH and E_SR. They have three valid connections
	 * between EO0-S, EO1-R and Q-G. Tests what happens after right click on E_SR
	 * and change type to E_SR. No error marker should appear, only the event
	 * connections are now crossed. The name remains the same.
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void changeTypeOf1FB() {
		// in progress
	}

	/**
	 * Checks if error marker appears.
	 *
	 * The method has 2 FBs E_N_TABLE and E_D_FF. They have two valid connections
	 * between CU0-CLK and between Q-D. Tests what happens after right click on
	 * E_D_FF and change type to E_SELECT. 2 red pins should now appear because of
	 * now invalid connections, the name remains the same.
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void changeTypeOf1FbErrorMarkerAppears1() {
		// in progress
	}

	/**
	 * Checks if error markers can be removed by deleting invalid connection.
	 *
	 * Test set as changeTypeOf1FbErrorMarkerAppears1(), checks if red error markers
	 * can be removed by deleting invalid connection.
	 */
	@Disabled("until implementation")
	@Test
	public void deleteConnectionToRemoveErrorMarkers1() {
		// in progress
	}

	/**
	 * Checks how changing type of FB effects neighboring FBs.
	 *
	 * 3 FBs like Blink Test from Tutorial. E_CYCLE, E_SWITCH, E_SR. Connections
	 * between EO-EI, EO0-S, EO1-R, Q-G. Change E_SWITCH to E_SPLIT. Connection
	 * EO-EI and EO1-R should remain valid. Connections EO0-S and Q-G should have
	 * error markers.
	 */
	@Disabled("until implementation")
	@Test
	public void changeTypeOf1FbErrorMarkerAppears2() {
		// in progress
	}

	/**
	 * Checks if error markers can be removed by deleting invalid connection.
	 *
	 * Test set as changeTypeOf1FbErrorMarkerAppears2(), checks if red error marker
	 * can be removed by moving source of connection from EO0 to EO2.
	 */
	@Disabled("until implementation")
	@Test
	public void deleteConnectionToRemoveErrorMarkers2() {
		// in progress
	}

}
