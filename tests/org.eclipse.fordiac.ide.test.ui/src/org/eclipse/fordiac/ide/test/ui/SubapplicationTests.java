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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.junit.jupiter.api.Test;

public class SubapplicationTests extends Abstract4diacUITests {

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
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		// drag rectangle over to FB, therefore FB should be selected
		editor.drag(50, 50, 400, 400);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SWITCH_FB.equals(fb.getModel().getName())));
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SR_FB.equals(fb.getModel().getName())));
		bot.menu(SOURCE).menu(NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> SUBAPP.equals(fb.getModel().getName())));
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
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(300, 100));
		SWTBot4diacGefViewer viewer = createConnection(EO0, S);
		assertDoesNotThrow(viewer::waitForConnection);
		viewer = createConnection(EO1, R);
//		assertDoesNotThrow(viewer::waitForConnection);
		viewer = createConnection(Q, G);
//		assertDoesNotThrow(viewer::waitForConnection);

		// drag rectangle over to FB, therefore FB should be selected
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);
		editor.drag(50, 50, 500, 300);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SWITCH_FB.equals(fb.getModel().getName())));
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SR_FB.equals(fb.getModel().getName())));
		bot.menu(SOURCE).menu(NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> SUBAPP.equals(fb.getModel().getName())));
		selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> SUBAPP.equals(fb.getModel().getName())));
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
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(300, 100));
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(500, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO, EI);
		assertDoesNotThrow(viewer::waitForConnection);

		// drag rectangle over to FB, therefore FB should be selected
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);
		editor.drag(250, 50, 600, 200);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SWITCH_FB.equals(fb.getModel().getName())));
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_SR_FB.equals(fb.getModel().getName())));
		assertFalse(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_CYCLE_TREE_ITEM.equals(fb.getModel().getName())));
		bot.menu(SOURCE).menu(NEW_SUBAPPLICATION).click();
		// renew list of selectedEditParts and then check if SubApp was created
		selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> SUBAPP.equals(fb.getModel().getName())));
	}

}
