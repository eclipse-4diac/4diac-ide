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
package org.eclipse.fordiac.ide.test.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.graphics.Point;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BasicMultipleFBNetworkEditingTests extends Abstract4diacUITests {

	/**
	 * Drag and drops a few event FBs of the same type on the editing area and
	 * checks if new FBs gets the next free running number.
	 *
	 * First multiple E_CYCLE and E_SR are dragged in mixed order onto the editing
	 * area. After that it is checked if the names were assigned correctly.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkIfNewFbGetsNextFreeRunningNumber() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(300, 100));
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(500, 100));
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 250));
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(300, 250));
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(500, 250));

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);

		assertTrue(isFBNamePresendOnEditingArea(editor, "E_CYCLE")); //$NON-NLS-1$
		assertTrue(isFBNamePresendOnEditingArea(editor, "E_CYCLE_1")); //$NON-NLS-1$
		assertTrue(isFBNamePresendOnEditingArea(editor, "E_CYCLE_2")); //$NON-NLS-1$
		assertTrue(isFBNamePresendOnEditingArea(editor, "E_CYCLE_3")); //$NON-NLS-1$
		assertFalse(isFBNamePresendOnEditingArea(editor, "E_CYCLE_0")); //$NON-NLS-1$

		assertTrue(isFBNamePresendOnEditingArea(editor, "E_SR")); //$NON-NLS-1$
		assertTrue(isFBNamePresendOnEditingArea(editor, "E_SR_1")); //$NON-NLS-1$
		assertFalse(isFBNamePresendOnEditingArea(editor, "E_SR_2")); //$NON-NLS-1$

	}

	/**
	 * Drag and drops a several (e.g. 5) FBs of the same type onto the canvas, than
	 * deletes 2 or 3 and drags again FBs of the same type onto the canvas. Checks
	 * that the new FBs get the smallest free running numbers.
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void checkIfNewFbGetsSmallestNextFreeRunningNumber() {
		// in progress
	}

	/**
	 * Create Blinky Test from Tutorial
	 */
	@Disabled("until implementation")
	@Test
	public void createBinkyTestApp() {
		// in progress
	}

}
