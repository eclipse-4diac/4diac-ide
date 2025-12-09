/*******************************************************************************
 * Copyright (c) 2023, 2024 Andrea Zoitl
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotConnection;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.jupiter.api.Test;

public class Basic2FBNetworkEditingTests extends Abstract4diacUITests {

	static final int TOLERANCE_SNAP_TO_GRID = 15;

	/**
	 * Drags and Drops two Function Blocks onto the canvas.
	 *
	 * The method selects the event FB E_CYCLE from the System Explorer hierarchy
	 * tree and then drags it onto the canvas of the editing area. Afterwards it is
	 * looked whether an EditPart is to be found and to conclude that FB is present.
	 * Then a second FB (E_SWITCH) is dragged onto the canvas and the check is the
	 * same as above.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void dragAndDrop2FB() {
		// drag&drop FB E_CYCLE and check if position is the given one
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		final Point posECycle = new Point(100, 100);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, posECycle);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		final Rectangle posFB = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_CYCLE_FB);
		assertEquals(posECycle.x, posFB.x);
		assertEquals(posECycle.y, posFB.y);

		// select FB E_SWITCH
		final Point posESwitch = new Point(300, 150);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, posESwitch);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SWITCH_FB));
		final Rectangle absFB2 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_SWITCH_FB);
		assertEquals(posESwitch.x, absFB2.x);
		assertEquals(posESwitch.y, absFB2.y);
	}

	/**
	 * The Method drag and drops 2 FBs onto the editing area. Afterwards one FB is
	 * deleted and it is checked if the FB is no longer on the canvas after
	 * deletion.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void delete1FB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(300, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SWITCH_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(parent);
		parent.click();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();
		assertNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SWITCH_FB));
		assertTrue(editor.selectedEditParts().isEmpty());
	}

	/**
	 *
	 * Checks if FBs can be selected together by drawing a rectangle by mouse left
	 * click over the FBs
	 *
	 * First, a rectangle is drawn next to the FBs to check whether they are not
	 * selected as expected. Then a rectangle is drawn over the FBs to check whether
	 * the FBs are selected.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void select2FBsViaMouseLeftClickOnFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// drag rectangle next to FBs, therefore FBs should not be selected
		editor.drag(40, 40, 200, 200);
		assertThrows(TimeoutException.class, editor::waitForSelectedFBEditPart);
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.isEmpty());
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_N_TABLE_FB));
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_CTUD_FB));

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(50, 50, 500, 300);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_N_TABLE_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_CTUD_FB));
	}

	/**
	 * Checks if the FB can be moved onto the canvas.
	 *
	 * The method drag and drops 2 FBs E_CYCLE and E_SWITCH to a certain position
	 * onto the canvas and it is checked whether the FBs are in the correct
	 * position. Afterwards one FB is moved to a new point and this position is also
	 * checked. Than it is checks whether the position of E_SWITCH has changed,
	 * which is not expected. To achieve this it is necessary to create a
	 * draw2d.geometry Point with the same coordinates of the swt.graphics Point.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void move1FB() {
		final Point pos1 = new Point(100, 100);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		final Point pos2 = new Point(350, 100);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, pos2);

		// select FB E_CYCLE and move it to new position of x=125, y=185
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_CYCLE_FB, new Point(125, 185));

		// check position of E_N_TABLE to ensure that it unchanged
		final Rectangle boundsENTable = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_N_TABLE_FB);
		assertTrue(boundsENTable.contains(pos2.x, pos2.y));
	}

	/**
	 * Checks if FBs can be moved on the canvas.
	 *
	 * The method drag and drops 2 FBs E_CYCLE and E_SWITCH to a certain position
	 * onto the canvas and it is checked whether the FBs are in the correct
	 * position. Afterwards the FBs are moved one after another to a new point and
	 * this position is also checked. To achieve this it is necessary to create a
	 * draw2d.geometry Point with the same coordinates of the swt.graphics Point.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void moveBothFBOneAfterAnother() {
		final Point pos1 = new Point(200, 200);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		final Point pos2 = new Point(400, 200);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, pos2);

		// select and move E_CYCLE and E_SWITCH
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_CYCLE_FB, new Point(85, 85));
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_SWITCH_FB, new Point(285, 85));
	}

	/**
	 * Checks if FBs can be selected and moved together by drawing a rectangle by
	 * mouse left click over the FBs
	 *
	 * First, a rectangle is drawn next to the FBs to check whether they are not
	 * selected as expected. Then a rectangle is drawn over the FBs to check whether
	 * the FBs are selected.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void moveBothFBTogether() {
		final Point absPos1Fb1 = new Point(100, 100);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, absPos1Fb1);
		final Point absPos1Fb2 = new Point(250, 150);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, absPos1Fb2);
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		final Rectangle fb1Bounds1 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds1.contains(absPos1Fb1.x, absPos1Fb1.y));
		final Rectangle fb2Bounds1 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_SR_FB);
		assertTrue(fb2Bounds1.contains(absPos1Fb2.x, absPos1Fb2.y));

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(50, 50, 400, 300);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_CYCLE_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// move FBs to new position by selecting them with a rectangle
		final Point translation = new Point(130, -40);
		fbBot.moveViaRectangle(editor, new Rectangle(50, 50, 400, 300), translation);

		// check if translation was correct
		fbBot.checkIfMovingFBWasCorrect(editor, UITestNamesHelper.E_CYCLE_FB, absPos1Fb1, translation);
		fbBot.checkIfMovingFBWasCorrect(editor, UITestNamesHelper.E_SR_FB, absPos1Fb2, translation);
	}

	/**
	 * Checks if Elements can be moved via selection with a rectangle
	 *
	 * First 3 FBs are dragged onto the editing area. Afterwards a rectangle is
	 * drawn up to select them and to be moved to a new location with the given
	 * translation.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void moveFBsSelectedViaRectangle() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		final Point posFB = new Point(100, 75);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, posFB);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(250, 75));
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(400, 100));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final Point translation = new Point(75, 125);
		fbBot.moveViaRectangle(editor, new Rectangle(50, 50, 600, 300), translation);
		// check first FB
		final Rectangle endPosFB = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_CYCLE_FB);
		final int expectedEndPosX = posFB.x + translation.x + TOLERANCE_SNAP_TO_GRID;
		final int expectedEndPosY = posFB.y + translation.y + TOLERANCE_SNAP_TO_GRID;
		assertTrue(endPosFB.contains(expectedEndPosX, expectedEndPosY));
	}

	/**
	 * Checks whether the connection remains and moves along with moving FB
	 *
	 * First two FB are dragged onto the editing area and a connection is created.
	 * The position of the start and end point of the connection is fetched. Then a
	 * new point is created and the delta values of the translation between the
	 * original position and the new position are calculated.
	 *
	 * Then the E_CYCLE is moved and it is checked whether the start point of the
	 * connection has also moved and whether the end point has remained unchanged.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkIfConnectionRemainsAfterMoving1FB() {
		final Point pos1 = new Point(100, 50);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_DEMUX_TREE_ITEM, new Point(300, 50));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		connectBot.createConnection(UITestPinHelper.EO, UITestPinHelper.EI);
		final ConnectionEditPart connection = connectBot.findConnection(UITestPinHelper.EO, UITestPinHelper.EI);
		assertNotNull(connection);

		// select E_CYCLE
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		fbBot.selectFBWithFBNameInEditor(editor, UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point origStartPointConnection = polyLineConnection.getPoints()
				.getFirstPoint();
		final org.eclipse.draw2d.geometry.Point origEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		// calculate deltas of translation
		final Point pos2 = new Point(305, 245);
		final Point translation = new Point(pos2.x - pos1.x, pos2.y - pos1.y);

		// move E_CYCLE
		editor.drag(fb1, pos2.x, pos2.y);
		fbBot.checkIfMovingConnectionWasCorrect(editor, origStartPointConnection, origEndPointConnection, connection,
				translation);
	}

	/**
	 * Checks whether the connection remains and moves along with moving FBs
	 *
	 * First two FB are dragged onto the editing area and a connection is created.
	 * The position of the start and end point of the connection is fetched. Then
	 * two new points are created and the delta values of the translation between
	 * the original position and the new position are calculated.
	 *
	 * Then the E_CTUD is moved and it is checked whether the end point of the
	 * connection has also moved and whether the start point has remained unchanged.
	 *
	 * Afterwards, the E_SWITCH is also moved and checked whether the start point
	 * and end point of the connection match the new positions of the FBs.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkIfConnectionRemainsAfterMovingBothFBsOneAfterAnother() {
		final Point pos1 = new Point(375, 75);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SELECT_TREE_ITEM, pos1);
		final Point pos2 = new Point(175, 125);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, pos2);
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		connectBot.createConnection(UITestPinHelper.QU, UITestPinHelper.G);
		final ConnectionEditPart connection = connectBot.findConnection(UITestPinHelper.QU, UITestPinHelper.G);
		assertNotNull(connection);

		// select E_SELECT
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CTUD_FB));
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SELECT_FB));
		editor.click(UITestNamesHelper.E_SELECT_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_SELECT_FB).parent();
		assertNotNull(fb1);
		final Rectangle fb1Bounds1 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_SELECT_FB);
		assertTrue(fb1Bounds1.contains(pos1.x, pos1.y));

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point startPointConnection = polyLineConnection.getPoints().getFirstPoint();
		org.eclipse.draw2d.geometry.Point endPointConnection = polyLineConnection.getPoints().getLastPoint();

		// calculate deltas of translation
		final Point pos3 = new Point(45, 105);
		final int fb1DeltaX = pos3.x - pos1.x;
		final int fb1DeltaY = pos3.y - pos1.y;

		final Point pos4 = new Point(345, 145);
		final int fb2DeltaX = pos4.x - pos2.x;
		final int fb2DeltaY = pos4.y - pos2.y;

		// move E_SELECT
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_SELECT_FB, pos3);

		assertNotNull(connection);
		org.eclipse.draw2d.geometry.Point newStartPointConnection = polyLineConnection.getPoints().getFirstPoint();
		org.eclipse.draw2d.geometry.Point newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		double distance = startPointConnection.getDistance(newStartPointConnection);
		assertTrue(distance <= TOLERANCE_SNAP_TO_GRID);
		distance = endPointConnection.translate(fb1DeltaX, fb1DeltaY).getDistance(newEndPointConnection);
		assertTrue(distance <= TOLERANCE_SNAP_TO_GRID);

		endPointConnection = newEndPointConnection;

		// select E_CTUD
		editor.click(UITestNamesHelper.E_CTUD_FB);
		final SWTBotGefEditPart fb2 = editor.getEditPart(UITestNamesHelper.E_CTUD_FB).parent();
		assertNotNull(fb2);
		final Rectangle fb2Bounds1 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_CTUD_FB);
		assertTrue(fb2Bounds1.contains(pos2.x, pos2.y));
		// move E_CTUD
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_CTUD_FB, pos4);
		final Rectangle fb2Bounds2 = fbBot.getBoundsOfFB(editor, UITestNamesHelper.E_CTUD_FB);
		assertTrue(fb2Bounds2.contains(pos4.x, pos4.y));
		assertNotNull(connection);
		newStartPointConnection = polyLineConnection.getPoints().getFirstPoint();
		newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		assertTrue(startPointConnection.translate(fb2DeltaX, fb2DeltaY)
				.getDistance(newStartPointConnection) <= TOLERANCE_SNAP_TO_GRID);
		assertTrue(endPointConnection.getDistance(newEndPointConnection) <= TOLERANCE_SNAP_TO_GRID);
	}

	/**
	 * Checks if connections stays in place after moving both FBs at the same time
	 *
	 * First two FB are dragged onto the editing area and a connection is created.
	 * The position of the start and end point of the connection is fetched. Then a
	 * rectangle is dragged over the 2 FBs and the two are moved by clicking on an
	 * FB and moved to a new position. The translation is calculated and compared
	 * with the new values of the connection start and end point.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkIfConnectionRemainsAfterMovingBothFBsTogether() {
		final Point pos1 = new Point(200, 100);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_DEMUX_TREE_ITEM, pos1);
		final Point pos2 = new Point(100, 275);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, pos2);
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		connectBot.createConnection(UITestPinHelper.EO1, UITestPinHelper.R);
		ConnectionEditPart connection = connectBot.findConnection(UITestPinHelper.EO1, UITestPinHelper.R);
		assertNotNull(connection);
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point origStartPointConnection = polyLineConnection.getPoints()
				.getFirstPoint();
		final org.eclipse.draw2d.geometry.Point origEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(30, 30, 400, 400);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_DEMUX_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// move selection by clicking on one FB but not on instance name or a pin
		final Point pointFrom = new Point(125, 300);
		final Point pointTo = new Point(290, 230);
		editor.drag(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);

		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_DEMUX_FB));
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// calculation of translation
		final Point translation = new Point(pointTo.x - pointFrom.x, pointTo.y - pointFrom.y);

		// check if connection has been moved
		connection = connectBot.findConnection(UITestPinHelper.EO1, UITestPinHelper.R);
		fbBot.checkIfMovingConnectionWasCorrect(editor, origStartPointConnection, origEndPointConnection, connection,
				translation);

	}
}
