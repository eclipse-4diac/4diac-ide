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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.InstanceNameEditPart;
import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotConnection;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFB;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.jupiter.api.Test;

public class Basic1FBNetworkEditingTests extends Abstract4diacUITests {

	/**
	 * Drags and Drops a Function Block onto the canvas.
	 *
	 * The method selects the event FB E_CYCLE from the System Explorer hierarchy
	 * tree and then drags it onto the canvas of the editing area. Afterwards it is
	 * checked if the canvas is not empty.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void dragAndDrop1FB() {
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
		final Point point = new Point(100, 100);
		eCycleNode.dragAndDrop(canvas, point);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
	}

	/**
	 * Checks if the FB is dragged onto the canvas is also visible in the hierarchy
	 * tree.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void isAddedFbInProjectAppNode() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		assertNotNull(systemExplorerView);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		assertNotNull(systemExplorerComposite);
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(treeProjectItem);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem projectNode = treeProjectItem.getNode(UITestNamesHelper.PROJECT_NAME + " []"); //$NON-NLS-1$
		assertNotNull(projectNode);
		projectNode.select();
		projectNode.expand();
		final SWTBotTreeItem appNode = projectNode.getNode(UITestNamesHelper.PROJECT_NAME + "App"); //$NON-NLS-1$
		assertNotNull(appNode);
		appNode.select();
		appNode.expand();
		assertNotNull(appNode.getNodes());
		assertTrue(appNode.getNode(UITestNamesHelper.E_CYCLE_FB).isVisible());
	}

	/**
	 * Checks if the FB is no longer on the canvas after deletion.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void deleteExistingFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(parent);
		parent.click();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();
		assertNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
	}

	/**
	 * Checks if FB can be selected by drawing a rectangle by mouse left click over
	 * the FB
	 *
	 * First, a rectangle is drawn next to the FB to check whether it is not
	 * selected as expected. Then a rectangle is drawn over the FB to check whether
	 * the FB is selected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void selectFbViaMouseLeftClickRectangleOverFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_D_FF_TREE_ITEM, new Point(200, 200));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// drag rectangle next to FB, therefore FB should not be selected
		editor.drag(40, 40, 80, 80);
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.isEmpty());
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_D_FF_FB));
		editor.drag(50, 50, 350, 350);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_D_FF_FB));
	}

	/**
	 * Checks if FB can be selected by clicking on somewhere within the FB bounds
	 * but not on a pin or FB instance name
	 *
	 * First attempts are made to click next to the FB to check whether it is not
	 * selected as expected. Then several attempts are made to click on the FB
	 * (meaning within the FB bounds) to select the FB as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void selectFbViaMouseLeftClickOnFB() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SWITCH_TREE_ITEM, new Point(150, 250));
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);

		// check bounds of FB
		editor.getEditPart(UITestNamesHelper.E_SWITCH_FB);
		editor.click(UITestNamesHelper.E_SWITCH_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(UITestNamesHelper.E_SWITCH_FB).parent();
		final IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		final Rectangle fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);

		// click next to the FB
		Point point = new Point(145, 245);
		assertFalse(fbBounds.contains(point.x, point.y));
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));

		// click next to the FB
		point = new Point(265, 350);
		assertFalse(fbBounds.contains(point.x, point.y));
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertFalse(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));

		// click exactly at the insertion point
		point = new Point(150, 250);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));

		// click within the FB bounds but not on a pin or instance name
		point = new Point(170, 300);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));

		// click within the FB bounds but not on a pin or instance name
		point = new Point(200, 340);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(fbBot.isFbSelected(selectedEditParts, UITestNamesHelper.E_SWITCH_FB));
	}

	/**
	 * Checks if the FB can be moved onto the canvas.
	 *
	 * The method drag and drops the FB E_CYCLE to a certain position onto the
	 * canvas and it is checked whether the FB is in the correct position.
	 * Afterwards the FB is moved to a new point and this position is also checked.
	 * To achieve this it is necessary to create a draw2d.geometry Point with the
	 * same coordinates of the swt.graphics Point.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void moveFB() {
		final Point pos1 = new Point(200, 200);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, pos1);
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(UITestNamesHelper.E_CYCLE_FB));
		editor.click(UITestNamesHelper.E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(UITestNamesHelper.E_CYCLE_FB).parent();
		assertNotNull(parent);

		final IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		assertNotNull(figure);
		final Rectangle fbBounds = figure.getBounds().getCopy();
		assertNotNull(fbBounds);
		figure.translateToAbsolute(fbBounds);
		assertEquals(pos1.x, fbBounds.x);
		assertEquals(pos1.y, fbBounds.y);

		final org.eclipse.draw2d.geometry.Point posToCheck1 = new org.eclipse.draw2d.geometry.Point(pos1);
		assertEquals(posToCheck1.x, fbBounds.x);
		assertEquals(posToCheck1.y, fbBounds.y);
		parent.click();

		final Point pos2 = new Point(85, 85);
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_CYCLE_FB, pos2);
	}

	/**
	 * Checks if the value of the data input pin of type time can be edited.
	 *
	 * The method sets the default value of data input pin of type time of FB
	 * E_CYCLE to a new value.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void editDTofECycle() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(200, 200));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(UITestPinHelper.DEF_VAL);
		editor.doubleClick(UITestPinHelper.DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		e.setText(UITestPinHelper.NEW_VAL);
		e.save();
		assertNotNull(editor.getEditPart(UITestPinHelper.NEW_VAL));
	}

	/**
	 * Checks if the default value of data input pin is displayed correctly.
	 *
	 * The method checks if the default value of data input pin of type time of FB
	 * E_CYCLE is displayed correctly.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void directEditorDefaultValueTest() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(UITestPinHelper.DEF_VAL);
		editor.doubleClick(UITestPinHelper.DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		assertEquals(UITestPinHelper.DEF_VAL, e.getText());
		e.save();
	}

	/**
	 * Checks if the new value of data input is displayed correctly.
	 *
	 * The method checks if the new entered value of data input pin of type time of
	 * FB E_CYCLE is displayed correctly.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void directEditorNewValueTest() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(UITestPinHelper.DEF_VAL);
		editor.doubleClick(UITestPinHelper.DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		e.setText(UITestPinHelper.NEW_VAL);
		e.save();
		editor.getEditPart(UITestPinHelper.NEW_VAL);
		editor.doubleClick(UITestPinHelper.NEW_VAL);
		assertEquals(UITestPinHelper.NEW_VAL, editor.toTextEditor().getText());
	}

	/**
	 * Checks if it is possible to edit the automatically generated name of the FB
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void editFBName() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_SR_TREE_ITEM, new Point(100, 100));
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);

		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final GraphicalViewer graphicalViewer = viewer.getGraphicalViewer();
		final Map<?, ?> editPartRegistry = graphicalViewer.getEditPartRegistry();
		final String newName = "Testee"; //$NON-NLS-1$
		InstanceNameFigure iNFigure = null;
		for (final Object obj : editPartRegistry.values()) {
			if (obj instanceof final InstanceNameEditPart iNEP) {
				final InstanceNameFigure figure = iNEP.getFigure();
				assertNotNull(figure);
				final String text = figure.getText();

				if (UITestNamesHelper.E_SR_FB.equals(text)) {
					iNFigure = figure;
					final Rectangle bounds = figure.getBounds().getCopy();
					assertNotNull(bounds);
					figure.translateToAbsolute(bounds);
					editor.setFocus();
					editor.doubleClick(bounds.x, bounds.y);
					bot.text(UITestNamesHelper.E_SR_FB).setText(newName);
					bot.activeShell().pressShortcut(Keystrokes.CR);
					assertEquals(newName, figure.getText());
					break;
				}
			}
		}
		assertNotNull(iNFigure);
		assertNotNull(editor.getEditPart(newName));
		assertDoesNotThrow(iNFigure::getText);
		assertEquals(newName, iNFigure.getText());
	}

	/**
	 * Checks if a valid connection can be created.
	 *
	 * The method checks if its possible to create a valid connection between an
	 * event input pin and a event output pin. It is also checked if the connection
	 * can be found in the
	 * {@link org.eclipse.gef.EditPartViewer#getEditPartRegistry() Map of the
	 * registered EditParts}.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void validConnectionBetweenEventInputPinAndEventOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CYCLE_TREE_ITEM, new Point(200, 200));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.START, UITestPinHelper.EO);
		assertDoesNotThrow(viewer::waitForConnection);
	}

	/**
	 * Checks if a valid connection can be created.
	 *
	 * The method checks if its possible to create a valid connection between an
	 * event output pin and a event input pin. It is also checked if the connection
	 * can be found in the
	 * {@link org.eclipse.gef.EditPartViewer#getEditPartRegistry() Map of the
	 * registered EditParts}.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void validConnectionBetweenEventOutputPinAndEventInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO1, UITestPinHelper.START);
		assertDoesNotThrow(viewer::waitForConnection);
	}

	/**
	 * Checks if a valid connection can be created.
	 *
	 * The method checks if its possible to create a valid connection between an
	 * data input pin of type unsigned integer and a data output pin of type
	 * unsigned integer. It is also checked if the connection can be found in the
	 * {@link org.eclipse.gef.EditPartViewer#getEditPartRegistry() Map of the
	 * registered EditParts}.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void validConnectionBetweenUintDataInputPinAndUintDataOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(150, 150));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.PV, UITestPinHelper.CV);
		assertDoesNotThrow(viewer::waitForConnection);
	}

	/**
	 * Checks if the connection is still there after moving the FB
	 *
	 * The method checks if its possible to create a valid connection between an
	 * data input pin of type boolean and a data output pin of type boolean. It is
	 * also checked if the connection can be found in the
	 * {@link org.eclipse.gef.EditPartViewer#getEditPartRegistry() Map of the
	 * registered EditParts}.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void validConnectionBetweenBoolInputPinAndBoolOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_D_FF_TREE_ITEM, new Point(150, 150));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.D, UITestPinHelper.Q);
		assertDoesNotThrow(viewer::waitForConnection);
	}

	/**
	 * Checks if the connection is still there after moving the FB
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void connectionCanBeFoundAfterMovingFB() {
		final Point pos1 = new Point(100, 150);
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_TABLE_CTRL_TREE_ITEM, pos1);
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		connectBot.createConnection(UITestPinHelper.INIT, UITestPinHelper.CLKO);
		connectBot.createConnection(UITestPinHelper.N, UITestPinHelper.CV);

		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		canvas.setFocus();

		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.CLKO, UITestPinHelper.INIT));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.CV, UITestPinHelper.N));

		final Point pos2 = new Point(85, 85);
		fbBot.moveSingleFB(editor, UITestNamesHelper.E_TABLE_CTRL_FB, pos2);

		// check if moving has worked correctly
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.CLKO, UITestPinHelper.INIT));
		assertTrue(connectBot.checkIfConnectionCanBeFound(UITestPinHelper.CV, UITestPinHelper.N));

		final Point translation = new Point(pos1.x - pos2.x, pos1.y - pos2.y);
		fbBot.selectFBWithFBNameInEditor((SWTBot4diacGefEditor) editor, UITestNamesHelper.E_TABLE_CTRL_FB);
		final ConnectionEditPart connection1 = connectBot.findConnection(UITestPinHelper.CLKO, UITestPinHelper.INIT);
		assertNotNull(connection1);
		final ConnectionEditPart connection2 = connectBot.findConnection(UITestPinHelper.CV, UITestPinHelper.N);

		final PolylineConnection polyLineConnection = (PolylineConnection) connection1.getFigure();
		final org.eclipse.draw2d.geometry.Point origStartPointConnection = polyLineConnection.getPoints()
				.getFirstPoint();
		final org.eclipse.draw2d.geometry.Point origEndPointConnection = polyLineConnection.getPoints().getLastPoint();
		fbBot.checkIfMovingConnectionWasCorrect((SWTBot4diacGefEditor) editor, origStartPointConnection,
				origEndPointConnection, connection1, translation);
		fbBot.checkIfMovingConnectionWasCorrect((SWTBot4diacGefEditor) editor, origStartPointConnection,
				origEndPointConnection, connection2, translation);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and an event input
	 * pin.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndEventInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.START, UITestPinHelper.STOP);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data input
	 * pin of type unsigned integer.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndUintInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(150, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.START, UITestPinHelper.N);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data input
	 * pin of type time.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndTimeInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(150, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.START, UITestPinHelper.DT);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data input
	 * pin of type boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndBoolInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_D_FF_TREE_ITEM, new Point(150, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.CLK, UITestPinHelper.D);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data output
	 * pin of type unsigned integer.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndUintOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(100, 150));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.CD, UITestPinHelper.CV);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data output
	 * pin of type time.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndTimeOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.INIT, UITestPinHelper.DTO);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event input pin and a data output
	 * pin of type boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventInputPinAndBoolOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(100, 150));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.CD, UITestPinHelper.QU);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between a data input pin of type time and a
	 * data output pin of type time.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenTimeInputPinAndTimeOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.DT, UITestPinHelper.DTO);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between a data input pin of type time and a
	 * data input pin of type unsigned integer.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenTimeInputPinAndUintInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.DT, UITestPinHelper.N);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between a data input pin of type unsigned
	 * integer and a data input pin of type boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenUintInputPinAndBoolOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.PV, UITestPinHelper.QU);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data input
	 * pin of type unsigned integer.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndUintInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO0, UITestPinHelper.N);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data input
	 * pin of type time.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndTimeInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO1, UITestPinHelper.DT);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data input
	 * pin of boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndBoolInputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_D_FF_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO, UITestPinHelper.D);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and an event
	 * output pin.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndEventOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO0, UITestPinHelper.EO2);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data output
	 * pin of type unsigned integer.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndUintOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.CLKO, UITestPinHelper.CV);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data output
	 * pin of type time.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndTimeOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.CLKO, UITestPinHelper.DTO);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between an event output pin and a data output
	 * pin of boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenEventOutputPinAndBoolOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_D_FF_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.EO, UITestPinHelper.Q);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

	/**
	 * Checks if an invalid connection can be created.
	 *
	 * Attempts to create a connection between a data output pin of type boolean and
	 * a data output pin of type boolean.
	 *
	 * @throws TimeoutException When the attempted connection cannot be found in the
	 *                          map of the {@link EditPartViewer#getEditPartRegistry
	 *                          EditPartRegistry} as expected.
	 */
	@SuppressWarnings({ "static-method", "static-access" })
	@Test
	public void invalidConnectionBetweenBoolOutputPinAndBoolOutputPin() {
		final SWTBotFB fbBot = new SWTBotFB(bot);
		fbBot.dragAndDropEventsFB(UITestNamesHelper.E_CTUD_TREE_ITEM, new Point(100, 100));
		final SWTBotConnection connectBot = new SWTBotConnection(bot);
		final SWTBot4diacGefViewer viewer = connectBot.createConnection(UITestPinHelper.QU, UITestPinHelper.QD);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

}
