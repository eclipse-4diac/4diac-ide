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
package org.eclipse.fordiac.ide.test.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.junit.jupiter.api.Test;

public class SubappSystemExplorerTests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication is visible in the SystemExplorer tree.
	 *
	 * First, a the function block E_CYCLE is dragged&dropped onto the editing area,
	 * afterwards a subapplication is created with this FB and then it is checked if
	 * the FB is also visible in the Application of the System in the SystemExplorer
	 * tree.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void isSubappInSystemExplorerTree() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);
		editor.click(parent);

		bot.menu(SOURCE).menu(NEW_SUBAPPLICATION).click();
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, SUBAPP));
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(SUBAPP));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(E_CYCLE_FB));
	}

	/**
	 * Checks if a function block of subapplication is visible in the SystemExplorer
	 * tree.
	 *
	 * First, a the function blocks E_CYCLE and E_SWITCH are dragged&dropped onto
	 * the editing area, afterwards a subapplication is created with E_CYCLE and
	 * then it is checked if the FB is also visible in the SubApp of the Application
	 * in the SystemExplorer tree.
	 *
	 * It is also checked whether E_SWITCH, which does not belong to the SubApp, is
	 * displayed as Application node.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void isFbAndSubappInSystemExplorerTree() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);
		editor.click(parent);

		bot.menu(SOURCE).menu(NEW_SUBAPPLICATION).click();
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, SUBAPP));
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(SUBAPP));
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(E_SWITCH_FB));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(E_CYCLE_FB));
	}

}
