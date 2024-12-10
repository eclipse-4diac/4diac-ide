/*******************************************************************************
 * Copyright (c) 2024 Andrea Zoitl
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
package org.eclipse.fordiac.ide.test.ui.networkediting.subapp_compsite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSubapp;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSystemExplorer;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.junit.jupiter.api.Test;

public class SubappSystemExplorerTests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication is visible in the SystemExplorer tree.
	 *
	 * First, a the function block E_CYCLE is dragged&dropped onto the editing area,
	 * afterwards a subapplication is created with this FB and then it is checked if
	 * the FB is also visible as child of the App node of the System in the
	 * SystemExplorer tree.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void isSubappInSystemExplorerTree() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		fbBot.selectFBWithFBNameInEditor(editor, UITestNamesHelper.E_CYCLE_FB);

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));

		// checks for SystemExplorer tree
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
	}

	/**
	 * Checks if a function block of subapplication is visible in the SystemExplorer
	 * tree.
	 *
	 * First, a the function blocks E_CYCLE and E_SWITCH are dragged&dropped onto
	 * the editing area, afterwards a subapplication is created with E_CYCLE and
	 * then it is checked if the FB is also visible as child of the SubApp node of
	 * the Application in the SystemExplorer tree.
	 *
	 * It is also checked whether E_SWITCH, which does not belong to the SubApp, is
	 * displayed as Application node.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void isFbAndSubappInSystemExplorerTree() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		fbBot.selectFBWithFBNameInEditor(editor, UITestNamesHelper.E_CYCLE_FB);

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());

		// checks for App node
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.E_SWITCH_FB));

		// checks for SubApp node
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
		assertThrows(WidgetNotFoundException.class,
				() -> sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_SWITCH_FB));
	}

}
