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

package org.eclipse.fordiac.ide.test.ui.networkediting.contextmenu;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSubapp;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSystemExplorer;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacFigureCanvas;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SWTBotContextMenuUITests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication can be created via ContextMenu.
	 *
	 * SWTBot simulates a right-click to open the context menu. A new subapplication
	 * is then created. It is checked if it is empty and displayed correctly in the
	 * SystemExplorer
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void createSubapplicationViaContextMenu() {
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.clickContextMenu(UITestNamesHelper.NEW_SUBAPPLICATION, 100, 100);
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
		// check App node and SubApp TreeItem in SystemExplorer tree
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isSubAppNodeInSystemExplorerEmpty());
	}

	/**
	 * Checks if a subapplication can be created via ContextMenu.
	 *
	 * SWTBot simulates a right-click to open the context menu. A new subapplication
	 * is then created. It is checked if it is empty and displayed correctly in the
	 * SystemExplorer
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Disabled("until bug in test is found")
	@Test
	public void createFBViaContextMenu() {
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		editor.clickContextMenu(UITestNamesHelper.INSERT_FB, 100, 100);
		final SWTBot4diacFigureCanvas canvas = editor.getSWTBotGefViewer().getCanvas();
		final List<? extends Text> controls = bot.widgets(widgetOfType(Text.class), canvas.widget);

		// TODO fix test so that it is working correctly. Right now it only work exactly
		// in this order. First our overwritten method typeText (part of directEditType
		// which is modified with an Enter at the end of the written text) and den the
		// inherited method directEditType.
		final Text textControl = controls.get(0);
		canvas.typeText(textControl, UITestNamesHelper.E_CYCLE_FB);

		editor.directEditType(UITestNamesHelper.E_CYCLE_FB);
		bot.sleep(3000);

		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
	}

	/**
	 * Checks Context Menu entry "Go To Parent" of a subapplications
	 *
	 * Creates an empty subapplication via context menu and checks the
	 * SystemExplorer tree if SubApp is present in the tree. Afterwards the SubApp
	 * is entered and it is checked again, if subapplication is present in the
	 * SystemExplorer tree. Then the subapplication is exited via ContextMenu entry
	 * "Go To Parent" and SystemExplorer Tree is checked again.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void goToParentViaContextMenuEmptySubApp() {
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.clickContextMenu(UITestNamesHelper.NEW_SUBAPPLICATION, 100, 100);
		// check System Explorer tree if SubApp is present

		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isSubAppNodeInSystemExplorerEmpty());
		goToCompositeInstanceViewer(UITestNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		// check System Explorer tree again after entering subapplication
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isSubAppNodeInSystemExplorerEmpty());
		editorSubApp.clickContextMenu(UITestNamesHelper.GO_TO_PARENT, 100, 100);
		// check System Explorer tree after returning
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isSubAppNodeInSystemExplorerEmpty());
	}

	/**
	 * Checks Context Menu entry "Go To Parent" of a subapplications
	 *
	 * Creates a FB and then a subapplication with that FB via context menu and
	 * checks the SystemExplorer tree if SubApp and FB are present in the tree.
	 * Afterwards the SubApp is entered and it is checked again, if SubApp and FB
	 * are present in the SystemExplorer tree. Then the SubApp is exited via
	 * ContextMenu entry "Go To Parent" and SystemExplorer Tree is checked again.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void goToParentViaContextMenuSubAppWithFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		subappBot.createSubappWithDragRectangle(50, 50, 400, 400);
		// check System Explorer tree if SubApp is present
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
		goToCompositeInstanceViewer(UITestNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		bot.toolbarButton(UITestNamesHelper.TOOLBAR_BUTTON_ZOOM_FIT_PAGE).click();
		// check System Explorer tree again after entering subapplication
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
		editorSubApp.clickContextMenu(UITestNamesHelper.GO_TO_PARENT, 100, 100);
		// check System Explorer tree after returning
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_CYCLE_FB));
	}

	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void goToChildViaContextMenuSubAppWithACompositeFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		subappBot.createSubappWithDragRectangle(50, 50, 300, 300);
		goToCompositeInstanceViewer(UITestNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		bot.toolbarButton(UITestNamesHelper.TOOLBAR_BUTTON_ZOOM_FIT_PAGE).click();

		// check bounds of FB
		final Rectangle fbBounds = fbBot.getBoundsOfFB(editorSubApp, UITestNamesHelper.E_N_TABLE_FB);
		fbBot.selectFBWithFBNameInEditor(editorSubApp, UITestNamesHelper.E_N_TABLE_FB);
		editorSubApp.clickContextMenu(UITestNamesHelper.GO_TO_CHILD, fbBounds.x, fbBounds.y);
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		assertTrue(sysExBot.isElementInApplicationOfSystemInSystemExplorer(UITestNamesHelper.SUBAPP));
		assertTrue(sysExBot.isFBInSubAppOfSystemInSystemExplorer(UITestNamesHelper.E_N_TABLE_FB));
	}

}
