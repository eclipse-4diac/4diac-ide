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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.GraphicalEditPart;
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
import org.junit.jupiter.api.Disabled;
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
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(PROJECT_NAME);
		assertNotNull(treeProjectItem);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(TYPE_LIBRARY_NODE);
		assertNotNull(typeLibraryNode);
		typeLibraryNode.select();
		typeLibraryNode.expand();
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(EVENTS_NODE);
		assertNotNull(eventsNode);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, E_CYCLE_TREE_ITEM));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(E_CYCLE_TREE_ITEM);
		assertNotNull(eCycleNode);
		eCycleNode.select();
		eCycleNode.click();
		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		final Point point1 = new Point(100, 100);
		eCycleNode.dragAndDrop(canvas, point1);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));

		// select FB E_SWITCH
		final SWTBotTreeItem eSwitchNode = eventsNode.getNode(E_SWITCH_TREE_ITEM);
		assertNotNull(eSwitchNode);
		eSwitchNode.select();
		eSwitchNode.click();
		final Point point2 = new Point(300, 150);
		eSwitchNode.dragAndDrop(canvas, point2);
		assertNotNull(editor.getEditPart(E_SWITCH_FB));
	}

	/**
	 * The Method drag and drops 2 FBs onto the editing area. Afterwards one FB is
	 * deleted and it is checked if the FB is no longer on the canvas after
	 * deletion.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void delete1FB() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(300, 100));
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(100, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		assertNotNull(editor.getEditPart(E_SWITCH_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);
		parent.click();
		bot.menu(EDIT).menu(DELETE).click();
		assertNull(editor.getEditPart(E_CYCLE_FB));
		assertNotNull(editor.getEditPart(E_SWITCH_FB));
	}

	/**
	 * Checks if the FB can be moved onto the canvas.
	 *
	 * The method drag and drops 2 FBs E_CYCLE and E_SWITCH to a certain position
	 * onto the canvas and it is checked whether the FBs are in the correct
	 * position. Afterwards one FB is moved to a new point and this position is also
	 * checked. To achieve this it is necessary to create a draw2d.geometry Point
	 * with the same coordinates of the swt.graphics Point.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void move1FB() {
		final Point pos1 = new Point(200, 200);
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, pos1);
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(400, 200));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);

		IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		assertNotNull(figure);
		Rectangle fbBounds = figure.getBounds().getCopy();
		assertNotNull(fbBounds);
		figure.translateToAbsolute(fbBounds);
		assertEquals(pos1.x, fbBounds.x);
		assertEquals(pos1.y, fbBounds.y);

		final org.eclipse.draw2d.geometry.Point posToCheck1 = new org.eclipse.draw2d.geometry.Point(pos1);
		assertEquals(posToCheck1.x, fbBounds.x);
		assertEquals(posToCheck1.y, fbBounds.y);
		parent.click();

		final Point pos2 = new Point(85, 85);
		editor.drag(parent, pos2.x, pos2.y);
		final org.eclipse.draw2d.geometry.Point posToCheck2 = new org.eclipse.draw2d.geometry.Point(pos2);

		parent = editor.getEditPart(E_CYCLE_FB).parent();
		figure = ((GraphicalEditPart) parent.part()).getFigure();
		fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);
		assertEquals(posToCheck2.x, fbBounds.x);
		assertEquals(posToCheck2.y, fbBounds.y);
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
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, pos1);
		final Point pos2 = new Point(400, 200);
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, pos2);

		// E_CYCLE
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		SWTBotGefEditPart parent1 = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent1);

		IFigure figure1 = ((GraphicalEditPart) parent1.part()).getFigure();
		assertNotNull(figure1);
		Rectangle fbBounds1 = figure1.getBounds().getCopy();
		assertNotNull(fbBounds1);
		figure1.translateToAbsolute(fbBounds1);
		assertEquals(pos1.x, fbBounds1.x);
		assertEquals(pos1.y, fbBounds1.y);

		final org.eclipse.draw2d.geometry.Point posToCheck1 = new org.eclipse.draw2d.geometry.Point(pos1);
		assertEquals(posToCheck1.x, fbBounds1.x);
		assertEquals(posToCheck1.y, fbBounds1.y);
		parent1.click();

		final Point pos3 = new Point(85, 85);
		editor.drag(parent1, pos3.x, pos3.y);
		final org.eclipse.draw2d.geometry.Point posToCheck3 = new org.eclipse.draw2d.geometry.Point(pos3);

		parent1 = editor.getEditPart(E_CYCLE_FB).parent();
		figure1 = ((GraphicalEditPart) parent1.part()).getFigure();
		fbBounds1 = figure1.getBounds().getCopy();
		figure1.translateToAbsolute(fbBounds1);
		assertEquals(posToCheck3.x, fbBounds1.x);
		assertEquals(posToCheck3.y, fbBounds1.y);

		// E_SWITCH
		editor.click(E_SWITCH_FB);
		SWTBotGefEditPart parent2 = editor.getEditPart(E_SWITCH_FB).parent();
		assertNotNull(parent2);

		IFigure figure2 = ((GraphicalEditPart) parent2.part()).getFigure();
		assertNotNull(figure2);
		Rectangle fbBounds2 = figure2.getBounds().getCopy();
		assertNotNull(fbBounds2);
		figure2.translateToAbsolute(fbBounds2);
		assertEquals(pos2.x, fbBounds2.x);
		assertEquals(pos2.y, fbBounds2.y);

		final org.eclipse.draw2d.geometry.Point posToCheck2 = new org.eclipse.draw2d.geometry.Point(pos2);
		assertEquals(posToCheck2.x, fbBounds2.x);
		assertEquals(posToCheck2.y, fbBounds2.y);
		parent2.click();

		final Point pos4 = new Point(285, 85);
		editor.drag(parent2, pos4.x, pos4.y);
		final org.eclipse.draw2d.geometry.Point posToCheck4 = new org.eclipse.draw2d.geometry.Point(pos4);

		parent2 = editor.getEditPart(E_SWITCH_FB).parent();
		figure2 = ((GraphicalEditPart) parent2.part()).getFigure();
		fbBounds2 = figure2.getBounds().getCopy();
		figure2.translateToAbsolute(fbBounds2);
		assertEquals(posToCheck4.x, fbBounds2.x);
		assertEquals(posToCheck4.y, fbBounds2.y);
	}

	/**
	 * Checks if FBs can be moved together - left click draw rectangle over both FBs
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void moveBothFBTogether() {
		// in progress
	}

	/**
	 * Checks if connections stays in place after moving one FB
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void checkIfConnectionRemainsAfterMoving1FB() {
		// in progress
	}

	/**
	 * Checks if connections stays in place after moving both FBs at the same time
	 *
	 */
	@Disabled("until implementation")
	@Test
	public void checkIfConnectionRemainsAfterMovingBothFBsTogether() {
		// in progress
	}

}
