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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotConnection;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSubapp;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.jupiter.api.Test;

public class SubapplicationTests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication can be created via menu by selecting the
	 * Application editor breadcrumb.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createEmptySubappViaMenu() {
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		editor.drag(50, 50, 500, 500);
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
	}

	/**
	 * Checks if two FBs can be selected by drawing a rectangle by mouse left click
	 * over the FBs to create a subapplication via menu
	 *
	 * First, two FBs are dragged&dropped onto the editing area and then a rectangle
	 * is drawn over the FBs. Then a subapplication is created via menu entry
	 * "Source" -> "New subapplication"
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createSubappViaMenu() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// drag rectangle over the FBs, therefore FBs should be selected
		editor.drag(80, 80, 400, 400);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertEquals(2, selectedEditParts.size());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
	}

	/**
	 * Checks if a subapplication can be created with two FBs with an existing
	 * connection between them.
	 *
	 * First, two FBs are dragged&dropped onto the editing area, then connections
	 * are created and then a rectangle is drawn over the FBs. Then a subapplication
	 * is created via menu entry "Source" -> "New subapplication"
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void createSubappViaMenuWithConnectionBetweenFBs() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		assertNotNull(connectBot.createConnection(UITestPinHelper.EO0, UITestPinHelper.S));
		assertNotNull(connectBot.createConnection(UITestPinHelper.EO1, UITestPinHelper.R));
		assertNotNull(connectBot.createConnection(UITestPinHelper.Q, UITestPinHelper.G));

		// drag rectangle over to FB, therefore FB should be selected
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(50, 50, 500, 300);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());

		// expected 5 selected EditParts (2 GefEditPart for the FBs and
		// 3 GefConnectionEditPars for the connections
		assertEquals(5, selectedEditParts.size());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.EO0, UITestPinHelper.S));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.EO1, UITestPinHelper.R));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.Q, UITestPinHelper.G));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
	}

	/**
	 * Checks whether a subapplication can be created when a connection to a not
	 * selected FB exists.
	 *
	 * First, three FBs are dragged&dropped onto the editing area and connections
	 * are created. Then a rectangle is drawn over two FBs where a connection is to
	 * a FB outside of the selection. Then a subapplication is created via menu
	 * entry "Source" -> "New subapplication"
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createSubappViaMenuWithExistingConnectionOutsideSubapp() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(300, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(500, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		assertNotNull(connectBot.createConnection(UITestPinHelper.EO, UITestPinHelper.EI));

		// drag rectangle over to FBs E_SWITCH and E_SR, therefore FBs should be
		// selected
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(250, 50, 600, 200);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertEquals(2, selectedEditParts.size());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_CYCLE_TREE_ITEM));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.EO, UITestPinHelper.EI));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		final SWTBotSubapp subappBot = new SWTBotSubapp(bot);
		assertTrue(subappBot.isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.EO, UITestPinHelper.EO));
	}

	/**
	 * Checks if connections can be created after a subapplication was created
	 *
	 * First, two FBs are dragged&dropped onto the editing area, then a
	 * subapplication is created with them. afterwards it is checked if connection
	 * can be created in the subapplication.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createSubappViaMenuThenCreatingConnection() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		editor.drag(50, 50, 400, 400);
		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();

		goToCompositeInstanceViewer(UITestNamesHelper.SUBAPP);
		final SWTBotGefEditor editorSubApp = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editorSubApp);

		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editorSubApp.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefEditPart editPart = editorSubApp.getEditPart(UITestNamesHelper.E_SWITCH_FB);

		// syncExec() is needed to update selection of UI and therefore it needs to run
		// in UI thread. Without this, the connections are not created correctly
		UIThreadRunnable.syncExec(() -> HandlerHelper.selectEditPart(viewer.getGraphicalViewer(), editPart.part()));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		assertNotNull(connectBot.createConnection(UITestPinHelper.EO0, UITestPinHelper.S));
		assertNotNull(connectBot.createConnection(UITestPinHelper.EO1, UITestPinHelper.R));
		assertNotNull(connectBot.createConnection(UITestPinHelper.Q, UITestPinHelper.G));
	}

}
