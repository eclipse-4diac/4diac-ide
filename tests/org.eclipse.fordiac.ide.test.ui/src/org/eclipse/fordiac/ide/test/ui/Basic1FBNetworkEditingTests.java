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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.InstanceNameEditPart;
import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
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
		final Point point = new Point(100, 100);
		eCycleNode.dragAndDrop(canvas, point);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
	}

	/**
	 * Checks if the FB is dragged onto the canvas is also visible in the hierarchy
	 * tree.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void isAddedFbInProjectAppNode() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		assertNotNull(systemExplorerView);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		assertNotNull(systemExplorerComposite);
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(PROJECT_NAME);
		assertNotNull(treeProjectItem);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem projectNode = treeProjectItem.getNode(PROJECT_NAME + " []"); //$NON-NLS-1$
		assertNotNull(projectNode);
		projectNode.select();
		projectNode.expand();
		final SWTBotTreeItem appNode = projectNode.getNode(PROJECT_NAME + "App"); //$NON-NLS-1$
		assertNotNull(appNode);
		appNode.select();
		appNode.expand();
		assertNotNull(appNode.getNodes());
		assertTrue(appNode.getNode(E_CYCLE_FB).isVisible());
	}

	/**
	 * Checks if the FB is no longer on the canvas after deletion.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFB() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);
		parent.click();
		bot.menu(EDIT).menu(DELETE).click();
		assertNull(editor.getEditPart(E_CYCLE_FB));
	}

	/**
	 * Checks if FB can be selected by drawing a rectangle by mouse left click over
	 * the FB
	 *
	 * First, a rectangle is drawn next to the FB to check whether it is not
	 * selected as expected. Then a rectangle is drawn over the FB to check whether
	 * the FB is selected.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void selectFbViaMouseLeftClickRectangleOverFB() {
		dragAndDropEventsFB(E_D_FF_TREE_ITEM, new Point(200, 200));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		// drag rectangle next to FB, therefore FB should not be selected
		editor.drag(40, 40, 80, 80);
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertTrue(selectedEditParts.isEmpty());
		assertFalse(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_D_FF_FB.equals(fb.getModel().getName())));

		// drag rectangle over to FB, therefore FB should be selected
		editor.drag(50, 50, 350, 350);
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart)
				.map(p -> (FBEditPart) p.part()).anyMatch(fb -> E_D_FF_FB.equals(fb.getModel().getName())));
	}

	/**
	 * Checks if FB can be selected by clicking on somewhere within the FB bounds
	 * but not on a pin or FB instance name
	 *
	 * First attempts are made to click next to the FB to check whether it is not
	 * selected as expected. Then several attempts are made to click on the FB
	 * (meaning within the FB bounds) to select the FB as expected.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void selectFbViaMouseLeftClickOnFB() {
		dragAndDropEventsFB(E_SWITCH_TREE_ITEM, new Point(150, 250));
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		// check bounds of FB
		editor.getEditPart(E_SWITCH_FB);
		editor.click(E_SWITCH_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_SWITCH_FB).parent();
		final IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		final Rectangle fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);

		// click next to the FB
		Point point = new Point(145, 245);
		assertFalse(fbBounds.contains(point.x, point.y));
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertFalse(isFbSelected(selectedEditParts, E_SWITCH_FB));

		// click next to the FB
		point = new Point(265, 350);
		assertFalse(fbBounds.contains(point.x, point.y));
		assertThrows(TimeoutException.class, () -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertFalse(isFbSelected(selectedEditParts, E_SWITCH_FB));

		// click exactly at the insertion point
		point = new Point(150, 250);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, E_SWITCH_FB));

		// click within the FB bounds but not on a pin or instance name
		point = new Point(170, 300);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, E_SWITCH_FB));

		// click within the FB bounds but not on a pin or instance name
		point = new Point(200, 340);
		editor.click(point.x, point.y);
		assertTrue(fbBounds.contains(point.x, point.y));
		assertDoesNotThrow(() -> editor.waitForSelectedFBEditPart());
		selectedEditParts = editor.selectedEditParts();
		assertFalse(selectedEditParts.isEmpty());
		assertTrue(isFbSelected(selectedEditParts, E_SWITCH_FB));
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
	@SuppressWarnings("static-method")
	@Test
	public void moveFB() {
		final Point pos1 = new Point(200, 200);
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, pos1);
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
	 * Checks if the value of the data input pin of type time can be edited.
	 *
	 * The method sets the default value of data input pin of type time of FB
	 * E_CYCLE to a new value.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void editDTofECycle() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(200, 200));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(DEF_VAL);
		editor.doubleClick(DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		e.setText(NEW_VAL);
		e.save();
		assertNotNull(editor.getEditPart(NEW_VAL));
	}

	/**
	 * Checks if the default value of data input pin is displayed correctly.
	 *
	 * The method checks if the default value of data input pin of type time of FB
	 * E_CYCLE is displayed correctly.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void directEditorDefaultValueTest() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(DEF_VAL);
		editor.doubleClick(DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		assertEquals(DEF_VAL, e.getText());
		e.save();
	}

	/**
	 * Checks if the new value of data input is displayed correctly.
	 *
	 * The method checks if the new entered value of data input pin of type time of
	 * FB E_CYCLE is displayed correctly.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void directEditorNewValueTest() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		editor.getEditPart(DEF_VAL);
		editor.doubleClick(DEF_VAL);
		final SWTBotEclipseEditor e = editor.toTextEditor();
		assertNotNull(e);
		e.setText(NEW_VAL);
		e.save();
		editor.getEditPart(NEW_VAL);
		editor.doubleClick(NEW_VAL);
		assertEquals(NEW_VAL, editor.toTextEditor().getText());
	}

	/**
	 * Checks if it is possible to edit the automatically generated name of the FB
	 */
	@SuppressWarnings("static-method")
	@Test
	public void editFBName() {
		dragAndDropEventsFB(E_SR_TREE_ITEM, new Point(100, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
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

				if (E_SR_FB.equals(text)) {
					iNFigure = figure;
					final Rectangle bounds = figure.getBounds().getCopy();
					assertNotNull(bounds);
					figure.translateToAbsolute(bounds);
					editor.setFocus();
					editor.doubleClick(bounds.x, bounds.y);
					bot.text(E_SR_FB).setText(newName);
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
	@SuppressWarnings("static-method")
	@Test
	public void validConnectionBetweenEventInputPinAndEventOutputPin() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(200, 200));
		final SWTBot4diacGefViewer viewer = createConnection(START, EO);
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
	@SuppressWarnings("static-method")
	@Test
	public void validConnectionBetweenEventOutputPinAndEventInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO1, START);
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
	@SuppressWarnings("static-method")
	@Test
	public void validConnectionBetweenUintDataInputPinAndUintDataOutputPin() {
		dragAndDropEventsFB(E_CTUD_TREE_ITEM, new Point(150, 150));
		final SWTBot4diacGefViewer viewer = createConnection(PV, CV);
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
	@SuppressWarnings("static-method")
	@Test
	public void validConnectionBetweenBoolInputPinAndBoolOutputPin() {
		dragAndDropEventsFB(E_D_FF_TREE_ITEM, new Point(150, 150));
		final SWTBot4diacGefViewer viewer = createConnection(D, Q);
		assertDoesNotThrow(viewer::waitForConnection);
	}

	/**
	 * Checks if the connection is still there after moving the FB
	 */
	@SuppressWarnings("static-method")
	@Test
	public void connectionCanBeFoundAfterMovingFB() {
		// in progress
		final Point pos1 = new Point(100, 150);
		dragAndDropEventsFB(E_TABLE_CTRL_TREE_ITEM, pos1);
		createConnection(INIT, CLKO);
		createConnection(N, CV);

		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		canvas.setFocus();

		assertTrue(checkIfConnectionCanBeFound(CLKO, INIT));
		assertTrue(checkIfConnectionCanBeFound(CV, N));

		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_TABLE_CTRL_FB));
		editor.click(E_TABLE_CTRL_FB);
		SWTBotGefEditPart parent = editor.getEditPart(E_TABLE_CTRL_FB).parent();
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

		parent = editor.getEditPart(E_TABLE_CTRL_FB).parent();
		figure = ((GraphicalEditPart) parent.part()).getFigure();
		fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);
		assertEquals(posToCheck2.x, fbBounds.x);
		assertEquals(posToCheck2.y, fbBounds.y);

		assertTrue(checkIfConnectionCanBeFound(CLKO, INIT));
		assertTrue(checkIfConnectionCanBeFound(CV, N));
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndEventInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(START, STOP);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndUintInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(150, 100));
		final SWTBot4diacGefViewer viewer = createConnection(START, N);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndTimeInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(150, 100));
		final SWTBot4diacGefViewer viewer = createConnection(START, DT);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndBoolInputPin() {
		dragAndDropEventsFB(E_D_FF_TREE_ITEM, new Point(150, 100));
		final SWTBot4diacGefViewer viewer = createConnection(CLK, D);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndUintOutputPin() {
		dragAndDropEventsFB(E_CTUD_TREE_ITEM, new Point(100, 150));
		final SWTBot4diacGefViewer viewer = createConnection(CD, CV);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndTimeOutputPin() {
		dragAndDropEventsFB(E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(INIT, DTO);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventInputPinAndBoolOutputPin() {
		dragAndDropEventsFB(E_CTUD_TREE_ITEM, new Point(100, 150));
		final SWTBot4diacGefViewer viewer = createConnection(CD, QU);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenTimeInputPinAndTimeOutputPin() {
		dragAndDropEventsFB(E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(DT, DTO);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenTimeInputPinAndUintInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(DT, N);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenUintInputPinAndBoolOutputPin() {
		dragAndDropEventsFB(E_CTUD_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(PV, QU);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndUintInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO0, N);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndTimeInputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO1, DT);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndBoolInputPin() {
		dragAndDropEventsFB(E_D_FF_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO, D);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndEventOutputPin() {
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO0, EO2);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndUintOutputPin() {
		dragAndDropEventsFB(E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(CLKO, CV);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndTimeOutputPin() {
		dragAndDropEventsFB(E_TABLE_CTRL_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(CLKO, DTO);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenEventOutputPinAndBoolOutputPin() {
		dragAndDropEventsFB(E_D_FF_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(EO, Q);
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
	@SuppressWarnings("static-method")
	@Test
	public void invalidConnectionBetweenBoolOutputPinAndBoolOutputPin() {
		dragAndDropEventsFB(E_CTUD_TREE_ITEM, new Point(100, 100));
		final SWTBot4diacGefViewer viewer = createConnection(QU, QD);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
	}

}
