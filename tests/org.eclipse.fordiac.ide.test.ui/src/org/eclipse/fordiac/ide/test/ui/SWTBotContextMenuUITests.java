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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.helpers.StringNamesHelper;
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
	@SuppressWarnings("static-method")
	@Test
	public void createSubapplicationViaContextMenu() {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);

		editor.clickContextMenu(StringNamesHelper.NEW_SUBAPPLICATION, 100, 100);
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, StringNamesHelper.SUBAPP));
		// check App node and SubApp TreeItem in SystemExplorer tree
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isSubAppNodeInSystemExplorerEmpty());
	}

	/**
	 * Checks if a subapplication can be created via ContextMenu.
	 *
	 * SWTBot simulates a right-click to open the context menu. A new subapplication
	 * is then created. It is checked if it is empty and displayed correctly in the
	 * SystemExplorer
	 */
	@SuppressWarnings("static-method")
	@Disabled("until bug in test is found")
	@Test
	public void createFBViaContextMenu() {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);

		editor.clickContextMenu(StringNamesHelper.INSERT_FB, 100, 100);
		final SWTBot4diacFigureCanvas canvas = editor.getSWTBotGefViewer().getCanvas();
		final List<? extends Text> controls = bot.widgets(widgetOfType(Text.class), canvas.widget);

		// TODO fix test so that it is working correctly. Right now it only work exactly
		// in this order. First our overwritten method typeText (part of directEditType
		// which is modified with an Enter at the end of the written text) and den the
		// inherited method directEditType.
		final Text textControl = controls.get(0);
		canvas.typeText(textControl, StringNamesHelper.E_CYCLE_FB);

		editor.directEditType(StringNamesHelper.E_CYCLE_FB);
		bot.sleep(3000);

		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.E_CYCLE_FB));
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
	@SuppressWarnings("static-method")
	@Test
	public void goToParentViaContextMenuEmptySubApp() {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		editor.clickContextMenu(StringNamesHelper.NEW_SUBAPPLICATION, 100, 100);
		// check System Explorer tree if SubApp is present
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isSubAppNodeInSystemExplorerEmpty());
		goToCompositeInstanceViewer(StringNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		// check System Explorer tree again after entering subapplication
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isSubAppNodeInSystemExplorerEmpty());
		editorSubApp.clickContextMenu(StringNamesHelper.GO_TO_PARENT, 100, 100);
		// check System Explorer tree after returning
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isSubAppNodeInSystemExplorerEmpty());
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
	@SuppressWarnings("static-method")
	@Test
	public void goToParentViaContextMenuSubAppWithFB() {
		dragAndDropEventsFB(StringNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		createSubappWithDragRectangle(50, 50, 400, 400);
		// check System Explorer tree if SubApp is present
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(StringNamesHelper.E_CYCLE_FB));
		goToCompositeInstanceViewer(StringNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		bot.toolbarButton(StringNamesHelper.TOOLBAR_BUTTON_ZOOM_FIT_PAGE).click();
		// check System Explorer tree again after entering subapplication
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(StringNamesHelper.E_CYCLE_FB));
		editorSubApp.clickContextMenu(StringNamesHelper.GO_TO_PARENT, 100, 100);
		// check System Explorer tree after returning
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(StringNamesHelper.E_CYCLE_FB));
	}

	@SuppressWarnings("static-method")
	@Test
	public void goToChildViaContextMenuSubAppWithACompositeFB() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		createSubappWithDragRectangle(50, 50, 300, 300);
		goToCompositeInstanceViewer(StringNamesHelper.SUBAPP);
		final SWTBot4diacGefEditor editorSubApp = (SWTBot4diacGefEditor) bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);
		bot.toolbarButton(StringNamesHelper.TOOLBAR_BUTTON_ZOOM_FIT_PAGE).click();

		// check bounds of FB
		final Rectangle fbBounds = getBoundsOfFB(editorSubApp, StringNamesHelper.E_N_TABLE_FB);
		selectFBWithFBNameInEditor(editorSubApp, StringNamesHelper.E_N_TABLE_FB);
		editorSubApp.clickContextMenu(StringNamesHelper.GO_TO_CHILD, fbBounds.x, fbBounds.y);
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(StringNamesHelper.SUBAPP));
		assertTrue(isFBInSubAppOfSystemInSystemExplorer(StringNamesHelper.E_N_TABLE_FB));
	}

}
