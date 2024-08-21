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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.helpers.PinNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotConnection;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.jupiter.api.Test;

public class Basic2FBNetworkEditingTests extends Abstract4diacUITests {

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
		// select FB E_CYCLE
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(treeProjectItem);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(UITestNamesHelper.TYPE_LIBRARY_NODE);
		assertNotNull(typeLibraryNode);
		typeLibraryNode.select();
		typeLibraryNode.expand();
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(UITestNamesHelper.EVENTS_NODE);
		assertNotNull(eventsNode);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, UITestNamesHelper.E_CYCLE_TREE_ITEM));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(UITestNamesHelper.E_CYCLE_TREE_ITEM);
		assertNotNull(eCycleNode);
		eCycleNode.select();
		eCycleNode.click();
		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		final Point point1 = new Point(100, 100);
		eCycleNode.dragAndDrop(canvas, point1);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		final Rectangle absPos1 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertEquals(point1.x, absPos1.x);
		assertEquals(point1.y, absPos1.y);

		// select FB E_SWITCH
		final SWTBotTreeItem eSwitchNode = eventsNode.getNode(UITestNamesHelper.E_SWITCH_TREE_ITEM);
		assertNotNull(eSwitchNode);
		eSwitchNode.select();
		eSwitchNode.click();
		final Point point2 = new Point(300, 150);
		eSwitchNode.dragAndDrop(canvas, point2);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SWITCH_FB));
		final Rectangle absPos2 = getAbsolutePosition(editor, UITestNamesHelper.E_SWITCH_FB);
		assertEquals(point2.x, absPos2.x);
		assertEquals(point2.y, absPos2.y);
	}

	/**
	 * The Method drag and drops 2 FBs onto the editing area. Afterwards one FB is
	 * deleted and it is checked if the FB is no longer on the canvas after
	 * deletion.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void delete1FB() {
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(300, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
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
		dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(300, 100));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// drag rectangle next to FBs, therefore FBs should not be selected
		editor.drag(40, 40, 200, 200);
		assertThrows(TimeoutException.class, editor::waitForSelectedFBEditPart);
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.isEmpty());
		assertFalse(isFbSelected(selectedEditParts, UITestNamesHelper.E_N_TABLE_FB));
		assertFalse(isFbSelected(selectedEditParts, UITestNamesHelper.E_CTUD_FB));

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(50, 50, 500, 300);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_N_TABLE_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_CTUD_FB));
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
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		final Point pos2 = new Point(350, 100);
		dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, pos2);

		// select E_CYCLE and check position of E_CYCLE
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(fb1);
		final Rectangle fb1Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds1.contains(pos1.x, pos1.y));

		// check position of E_N_TABLE
		final Rectangle fb2Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_N_TABLE_FB);
		assertTrue(fb2Bounds1.contains(pos2.x, pos2.y));

		// move E_CYCLE and check new position
		final Point pos3 = new Point(125, 185);
		editor.drag(fb1, pos3.x, pos3.y);
		final Rectangle fb1Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds2.contains(pos3.x, pos3.y));

		// check if E_N_TABLE is still on same position
		assertTrue(fb2Bounds1.contains(pos2.x, pos2.y));
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
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		final Point pos2 = new Point(400, 200);
		dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, pos2);

		// select and move E_CYCLE
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(fb1);
		final Rectangle fb1Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds1.contains(pos1.x, pos1.y));
		final Point pos3 = new Point(85, 85);
		editor.drag(fb1, pos3.x, pos3.y);
		final Rectangle fb1Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds2.contains(pos3.x, pos3.y));

		// select and move E_SWITCH
		editor.click(UITestNamesHelper.E_SWITCH_FB);
		final SWTBotGefEditPart fb2 = editor.getEditPart(UITestNamesHelper.E_SWITCH_FB).parent();
		assertNotNull(fb2);
		final Rectangle fb2Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_SWITCH_FB);
		assertTrue(fb2Bounds1.contains(pos2.x, pos2.y));
		final Point pos4 = new Point(285, 85);
		editor.drag(fb2, pos4.x, pos4.y);
		final Rectangle fb2Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_SWITCH_FB);
		assertTrue(fb2Bounds2.contains(pos4.x, pos4.y));
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
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, absPos1Fb1);
		final Point absPos1Fb2 = new Point(100, 220);
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, absPos1Fb2);
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		final Rectangle fb1Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds1.contains(absPos1Fb1.x, absPos1Fb1.y));
		final Rectangle fb2Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_SR_FB);
		assertTrue(fb2Bounds1.contains(absPos1Fb2.x, absPos1Fb2.y));

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(50, 50, 400, 400);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_CYCLE_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// move selection by clicking on point within selection (120, 120) and drag to
		// new Point (285, 85)
		final Point pointFrom = new Point(120, 120);
		final Point pointTo = new Point(285, 85);
		editor.drag(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);

		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_CYCLE_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// Calculation of translation
		final int translationX = pointTo.x - pointFrom.x;
		final int translationY = pointTo.y - pointFrom.y;

		// Calculation of new Position of E_CYCLEs
		final int absPos2Fb1X = absPos1Fb1.x + translationX;
		final int absPos2Fb1Y = absPos1Fb1.y + translationY;
		final Rectangle fb1Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertEquals(absPos2Fb1X, fb1Bounds2.x);
		assertEquals(absPos2Fb1Y, fb1Bounds2.y);
		assertTrue(fb1Bounds2.contains(absPos2Fb1X, absPos2Fb1Y));

		// Calculation of new Position of E_SR
		final int absPos2Fb2X = absPos1Fb2.x + translationX;
		final int absPos2Fb2Y = absPos1Fb2.y + translationY;
		final Rectangle fb2Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_SR_FB);
		assertEquals(absPos2Fb2X, fb2Bounds2.x);
		assertEquals(absPos2Fb2Y, fb2Bounds2.y);
		assertTrue(fb2Bounds2.contains(absPos2Fb2X, absPos2Fb2Y));
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
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void checkIfConnectionRemainsAfterMoving1FB() {
		final Point pos1 = new Point(100, 50);
		dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		dragAndDropEventsFB(UITestNamesHelper.E_DEMUX_TREE_ITEM, new Point(300, 50));
		final SWTBotConnection connect = new SWTBotConnection(bot);
		connect.createConnection(PinNamesHelper.EO, PinNamesHelper.EI);
		final ConnectionEditPart connection = connect.findConnection(PinNamesHelper.EO, PinNamesHelper.EI);
		assertNotNull(connection);

		// select E_CYCLE
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(fb1);
		final Rectangle fb1Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds1.contains(pos1.x, pos1.y));

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point startPointConnection = polyLineConnection.getPoints().getFirstPoint();
		final org.eclipse.draw2d.geometry.Point endPointConnection = polyLineConnection.getPoints().getLastPoint();

		// calculate deltas of translation
		final Point pos2 = new Point(305, 245);
		final int deltaX = pos2.x - pos1.x;
		final int deltaY = pos2.y - pos1.y;

		// move E_CYCLE
		editor.drag(fb1, pos2.x, pos2.y);
		final Rectangle fb1Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_CYCLE_FB);
		assertTrue(fb1Bounds2.contains(pos2.x, pos2.y));
		assertNotNull(connection);
		final org.eclipse.draw2d.geometry.Point newStartPointConnection = polyLineConnection.getPoints()
				.getFirstPoint();
		final org.eclipse.draw2d.geometry.Point newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		assertEquals(startPointConnection.x + (long) deltaX, newStartPointConnection.x);
		assertEquals(startPointConnection.y + (long) deltaY, newStartPointConnection.y);
		assertEquals(endPointConnection, newEndPointConnection);
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
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void checkIfConnectionRemainsAfterMovingBothFBsOneAfterAnother() {
		final Point pos1 = new Point(375, 75);
		dragAndDropEventsFB(UITestNamesHelper.E_SELECT_TREE_ITEM, pos1);
		final Point pos2 = new Point(175, 125);
		dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, pos2);
		final SWTBotConnection connect = new SWTBotConnection(bot);
		connect.createConnection(PinNamesHelper.QU, PinNamesHelper.G);
		final ConnectionEditPart connection = connect.findConnection(PinNamesHelper.QU, PinNamesHelper.G);
		assertNotNull(connection);

		// select E_SELECT
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CTUD_FB));
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_SELECT_FB));
		editor.click(UITestNamesHelper.E_SELECT_FB);
		final SWTBotGefEditPart fb1 = editor.getEditPart(UITestNamesHelper.E_SELECT_FB).parent();
		assertNotNull(fb1);
		final Rectangle fb1Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_SELECT_FB);
		assertTrue(fb1Bounds1.contains(pos1.x, pos1.y));

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point startPointConnection = polyLineConnection.getPoints().getFirstPoint();
		final org.eclipse.draw2d.geometry.Point endPointConnection = polyLineConnection.getPoints().getLastPoint();

		// calculate deltas of translation
		final Point pos3 = new Point(45, 105);
		final int fb1DeltaX = pos3.x - pos1.x;
		final int fb1DeltaY = pos3.y - pos1.y;

		final Point pos4 = new Point(345, 145);
		final int fb2DeltaX = pos4.x - pos2.x;
		final int fb2DeltaY = pos4.y - pos2.y;

		// move E_SELECT
		editor.drag(fb1, pos3.x, pos3.y);
		final Rectangle fb1Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_SELECT_FB);
		assertTrue(fb1Bounds2.contains(pos3.x, pos3.y));
		assertNotNull(connection);
		org.eclipse.draw2d.geometry.Point newStartPointConnection = polyLineConnection.getPoints().getFirstPoint();
		org.eclipse.draw2d.geometry.Point newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		assertEquals(startPointConnection, newStartPointConnection);
		assertEquals(endPointConnection.x + (long) fb1DeltaX, newEndPointConnection.x);
		assertEquals(endPointConnection.y + (long) fb1DeltaY, newEndPointConnection.y);

		// select E_CTUD
		editor.click(UITestNamesHelper.E_CTUD_FB);
		final SWTBotGefEditPart fb2 = editor.getEditPart(UITestNamesHelper.E_CTUD_FB).parent();
		assertNotNull(fb2);
		final Rectangle fb2Bounds1 = getAbsolutePosition(editor, UITestNamesHelper.E_CTUD_FB);
		assertTrue(fb2Bounds1.contains(pos2.x, pos2.y));
		// move E_CTUD
		editor.drag(fb2, pos4.x, pos4.y);
		final Rectangle fb2Bounds2 = getAbsolutePosition(editor, UITestNamesHelper.E_CTUD_FB);
		assertTrue(fb2Bounds2.contains(pos4.x, pos4.y));
		assertNotNull(connection);
		newStartPointConnection = polyLineConnection.getPoints().getFirstPoint();
		newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		assertEquals(startPointConnection.x + (long) fb2DeltaX, newStartPointConnection.x);
		assertEquals(startPointConnection.y + (long) fb2DeltaY, newStartPointConnection.y);
		assertEquals(endPointConnection.x + (long) fb1DeltaX, newEndPointConnection.x);
		assertEquals(endPointConnection.y + (long) fb1DeltaY, newEndPointConnection.y);
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
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void checkIfConnectionRemainsAfterMovingBothFBsTogether() {
		final Point pos1 = new Point(200, 100);
		dragAndDropEventsFB(UITestNamesHelper.E_DEMUX_TREE_ITEM, pos1);
		final Point pos2 = new Point(100, 275);
		dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, pos2);
		final SWTBotConnection connect = new SWTBotConnection(bot);
		connect.createConnection(PinNamesHelper.EO1, PinNamesHelper.R);
		ConnectionEditPart connection = connect.findConnection(PinNamesHelper.EO1, PinNamesHelper.R);
		assertNotNull(connection);
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// get connection start and end point
		final PolylineConnection polyLineConnection = (PolylineConnection) connection.getFigure();
		final org.eclipse.draw2d.geometry.Point startPointConnection = polyLineConnection.getPoints().getFirstPoint();
		final org.eclipse.draw2d.geometry.Point endPointConnection = polyLineConnection.getPoints().getLastPoint();

		// drag rectangle over FBs, therefore FBs should be selected
		editor.drag(30, 30, 400, 400);
		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_DEMUX_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// move selection by clicking on one FB but not on instance name or a pin
		final Point pointFrom = new Point(125, 300);
		final Point pointTo = new Point(290, 230);
		editor.drag(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);

		assertDoesNotThrow(editor::waitForSelectedFBEditPart);
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_DEMUX_FB));
		assertTrue(isFbSelected(selectedEditParts, UITestNamesHelper.E_SR_FB));

		// calculation of translation
		final int translationX = pointTo.x - pointFrom.x;
		final int translationY = pointTo.y - pointFrom.y;

		// check if connection has been moved
		connection = connect.findConnection(PinNamesHelper.EO1, PinNamesHelper.R);
		assertNotNull(connection);
		final org.eclipse.draw2d.geometry.Point newStartPointConnection = polyLineConnection.getPoints()
				.getFirstPoint();
		final org.eclipse.draw2d.geometry.Point newEndPointConnection = polyLineConnection.getPoints().getLastPoint();

		assertEquals(startPointConnection.x + (long) translationX, newStartPointConnection.x);
		assertEquals(startPointConnection.y + (long) translationY, newStartPointConnection.y);
		assertEquals(endPointConnection.x + (long) translationX, newEndPointConnection.x);
		assertEquals(endPointConnection.y + (long) translationY, newEndPointConnection.y);

	}
}
