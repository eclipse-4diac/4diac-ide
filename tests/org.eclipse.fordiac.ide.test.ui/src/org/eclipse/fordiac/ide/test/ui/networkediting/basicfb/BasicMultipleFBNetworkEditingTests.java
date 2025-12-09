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
package org.eclipse.fordiac.ide.test.ui.networkediting.basicfb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.graphics.Point;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BasicMultipleFBNetworkEditingTests extends Abstract4diacUITests {

	// static FB instance names for running numbers tests
	private static final String E_CYCLE = "E_CYCLE"; //$NON-NLS-1$
	private static final String E_CYCLE_0 = "E_CYCLE_0"; //$NON-NLS-1$
	private static final String E_CYCLE_1 = "E_CYCLE_1"; //$NON-NLS-1$
	private static final String E_CYCLE_2 = "E_CYCLE_2"; //$NON-NLS-1$
	private static final String E_CYCLE_3 = "E_CYCLE_3"; //$NON-NLS-1$

	private static final String E_SWITCH = "E_SWITCH"; //$NON-NLS-1$
	private static final String E_SWITCH_0 = "E_SWITCH_0"; //$NON-NLS-1$
	private static final String E_SWITCH_1 = "E_SWITCH_1"; //$NON-NLS-1$
	private static final String E_SWITCH_2 = "E_SWITCH_2"; //$NON-NLS-1$
	private static final String E_SWITCH_3 = "E_SWITCH_3"; //$NON-NLS-1$
	private static final String E_SWITCH_4 = "E_SWITCH_4"; //$NON-NLS-1$
	private static final String E_SWITCH_5 = "E_SWITCH_5"; //$NON-NLS-1$

	private static final String E_SR = "E_SR"; //$NON-NLS-1$
	private static final String E_SR_1 = "E_SR_1"; //$NON-NLS-1$
	private static final String E_SR_2 = "E_SR_2"; //$NON-NLS-1$

	/**
	 * Drag and drops a few event FBs of the same type on the editing area and
	 * checks if new FBs gets the next free running number.
	 *
	 * First multiple E_CYCLE and E_SR are dragged in mixed order onto the editing
	 * area. After that it is checked if the names were assigned correctly.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void checkIfNewFbGetsNextFreeRunningNumber() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(500, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 250));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 250));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(500, 250));

		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);

		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_CYCLE));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_CYCLE_1));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_CYCLE_2));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_CYCLE_3));
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_CYCLE_0));

		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SR));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SR_1));
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SR_2));

	}

	/**
	 * Checks if a new FB gets the smallest next free running number for instance
	 * name.
	 *
	 * First 4 E_SWITCH FBs are dragged onto the editing area and checked if the
	 * names can be found. Afterwards a FB is deleted and inserted again and it is
	 * checked whether the new FB has got the number that has become free. Finally,
	 * 2 FBs are deleted and 4 FBs are added and it is checked whether the name
	 * allocation is correct here as well.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void checkIfNewFbGetsSmallestNextFreeRunningNumber() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(50, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(250, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(450, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(50, 250));

		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);

		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_1));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_2));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_3));
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_0));
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_4));

		fbBot.deleteFB(editor, E_SWITCH_2);
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_2));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(450, 250));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_2));

		fbBot.deleteFB(editor, E_SWITCH);
		fbBot.deleteFB(editor, E_SWITCH_1);
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH));
		assertFalse(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_1));

		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(450, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(250, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(250, 250));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(50, 100));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_1));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_4));
		assertTrue(fbBot.isFBNamePresendOnEditingArea(editor, E_SWITCH_5));
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
