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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.PinNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SubapplicationTests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication can be created via menu by selecting the
	 * Application editor breadcrumb.
	 */
	@SuppressWarnings("static-method")
	@Disabled("until bug fix")
	@Test
	public void createEmptySubappViaMenu() {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		editor.drag(10, 10, 500, 500);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
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
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// drag rectangle over to FB, therefore FB should be selected
		editor.drag(50, 50, 400, 400);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertEquals(2, selectedEditParts.size());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
	}

	/**
	 * Checks if a subapplication can be created with two FBs with an existing
	 * connection between them.
	 *
	 * First, two FBs are dragged&dropped onto the editing area, then connections
	 * are created and then a rectangle is drawn over the FBs. Then a subapplication
	 * is created via menu entry "Source" -> "New subapplication"
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createSubappViaMenuWithConnectionBetweenFBs() {
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		assertNotNull(createConnection(PinNamesHelper.EO0, PinNamesHelper.S));
		assertNotNull(createConnection(PinNamesHelper.EO1, PinNamesHelper.R));
		assertNotNull(createConnection(PinNamesHelper.Q, PinNamesHelper.G));

		// drag rectangle over to FB, therefore FB should be selected
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(50, 50, 500, 300);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());

		// expected 5 selected EditParts (2 GefEditPart for the FBs and
		// 3 GefConnectionEditPars for the connections
		assertEquals(5, selectedEditParts.size());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));
		assertTrue(checkIfConnectionCanBeFound(PinNamesHelper.EO0, PinNamesHelper.S));
		assertTrue(checkIfConnectionCanBeFound(PinNamesHelper.EO1, PinNamesHelper.R));
		assertTrue(checkIfConnectionCanBeFound(PinNamesHelper.Q, PinNamesHelper.G));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
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
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(300, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(500, 100));
		assertNotNull(createConnection(PinNamesHelper.EO, PinNamesHelper.EI));

		// drag rectangle over to FBs E_SWITCH and E_SR, therefore FBs should be
		// selected
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(250, 50, 600, 200);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertEquals(2, selectedEditParts.size());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));
		assertFalse(isFbSelected(selectedEditParts, UITestNamesHelper.E_CYCLE_TREE_ITEM));
		assertTrue(checkIfConnectionCanBeFound(PinNamesHelper.EO, PinNamesHelper.EI));

		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, UITestNamesHelper.SUBAPP));
		assertTrue(checkIfConnectionCanBeFound(PinNamesHelper.EO, PinNamesHelper.EO));
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
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

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
		assertNotNull(createConnection(PinNamesHelper.EO0, PinNamesHelper.S));
		assertNotNull(createConnection(PinNamesHelper.EO1, PinNamesHelper.R));
		assertNotNull(createConnection(PinNamesHelper.Q, PinNamesHelper.G));
	}

}
