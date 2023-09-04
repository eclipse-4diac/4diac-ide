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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Basic1FBNetworkEditingTests extends Abstract4diacUITests {

	private static final String DELETE = "Delete"; //$NON-NLS-1$
	private static final String E_CTUD_TREE_ITEM = "E_CTUD [Event-Driven Up-Down Counter]"; //$NON-NLS-1$
	private static final String E_CYCLE_FB = "E_CYCLE"; //$NON-NLS-1$
	private static final String E_CYCLE_TREE_ITEM = "E_CYCLE [Peroidic event generator]"; //$NON-NLS-1$
	private static final String E_D_FF_TREE_ITEM = "E_D_FF [Data latch (d) flip flop]"; //$NON-NLS-1$
	private static final String E_N_TABLE_TREE_ITEM = "E_N_TABLE [Generation of a finite train of sperate events]"; //$NON-NLS-1$
	private static final String E_TABLE_CTRL_TREE_ITEM = "E_TABLE_CTRL [Support function block for E_TABLE]"; //$NON-NLS-1$
	private static final String EDIT = "Edit"; //$NON-NLS-1$
	private static final String EVENTS_NODE = "events"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	private static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	private static final String TYPE_LIBRARY_NODE = "Type Library"; //$NON-NLS-1$
	// FB pins and values
	private static final String START = "START"; //$NON-NLS-1$
	private static final String STOP = "STOP"; //$NON-NLS-1$
	private static final String D = "D"; //$NON-NLS-1$
	private static final String N = "N"; //$NON-NLS-1$
	private static final String Q = "Q"; //$NON-NLS-1$
	private static final String CD = "CD"; //$NON-NLS-1$
	private static final String CV = "CV"; //$NON-NLS-1$
	private static final String DT = "DT"; //$NON-NLS-1$
	private static final String EO = "EO"; //$NON-NLS-1$
	private static final String PV = "PV"; //$NON-NLS-1$
	private static final String QD = "QD"; //$NON-NLS-1$
	private static final String QU = "QU"; //$NON-NLS-1$
	private static final String CLK = "CLK"; //$NON-NLS-1$
	private static final String EO0 = "EO0"; //$NON-NLS-1$
	private static final String EO1 = "EO1"; //$NON-NLS-1$
	private static final String EO2 = "EO2"; //$NON-NLS-1$
	private static final String DTO = "DTO"; //$NON-NLS-1$
	private static final String CLKO = "CLKO"; //$NON-NLS-1$
	private static final String INIT = "INIT"; //$NON-NLS-1$
	private static final String DEF_VAL = "T#0s"; //$NON-NLS-1$
	private static final String NEW_VAL = "T#1s"; //$NON-NLS-1$

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
	 * Checks if the FB can be moved onto the canvas.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void moveFB() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 300));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		assertNotNull(parent);
		parent.click();
		editor.drag(parent, 300, 300);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
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
	@Test
	public void editFBName() {
		// in progress
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
	@Disabled
	public void connectionCanBeFoundAfterMovingFB() {
		// in progress
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

	// End of region for test methods
	// ----------------------------------------------------------------------------------------
	// Region of utility methods

	/**
	 * Creates a connection between two pins.
	 *
	 * The method creates a connection between the two given pins, the order of the
	 * pins is not important and returns a SWTBot4diacGefViewer. Whether a
	 * connection could actually be created is not checked here.
	 *
	 * @param pin1 One of the two pins between a connection is (tried to) create.
	 * @param pin2 One of the two pins between a connection is (tried to) create.
	 * @return SWTBot4diacGefViewer
	 */
	private static SWTBot4diacGefViewer createConnection(final String pin1, final String pin2) {
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		// select input pin
		editor.click(pin1);
		final SWTBotGefEditPart ei = editor.getEditPart(pin1);
		assertNotNull(ei);
		final IFigure figure = ((GraphicalEditPart) ei.part()).getFigure();
		assertNotNull(figure);
		final Rectangle inputPinBounds1 = figure.getBounds().getCopy();
		assertNotNull(inputPinBounds1);
		figure.translateToAbsolute(inputPinBounds1);
		// select output pin
		editor.click(pin2);
		final SWTBotGefEditPart eo = editor.getEditPart(pin2);
		assertNotNull(eo);
		final Rectangle inputPinBounds2 = ((GraphicalEditPart) eo.part()).getFigure().getBounds().getCopy();
		assertNotNull(inputPinBounds2);
		figure.translateToAbsolute(inputPinBounds2);
		viewer.drag(pin2, inputPinBounds1.getCenter().x, inputPinBounds1.getCenter().y);
		return viewer;
	}

	/**
	 * Drags and drops a FB onto the canvas with given name and position.
	 *
	 * @param fbName The name of the Function Block.
	 * @param point  The Position of the FB on the canvas.
	 */
	private static void dragAndDropEventsFB(final String fbName, final Point point) {
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(PROJECT_NAME);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(TYPE_LIBRARY_NODE);
		typeLibraryNode.select();
		typeLibraryNode.expand();
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(EVENTS_NODE);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, fbName));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(fbName);
		eCycleNode.select();
		eCycleNode.click();

		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();

		assertNotNull(canvas);
		eCycleNode.dragAndDrop(canvas, point);
	}

}
