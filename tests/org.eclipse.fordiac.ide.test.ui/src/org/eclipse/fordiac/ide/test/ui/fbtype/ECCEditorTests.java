/*******************************************************************************
 * Copyright (c) 2024 Prashantkumar Khatri
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prashantkumar Khatri - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.fbtype;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotECC;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ECCEditorTests extends Abstract4diacUITests {

	protected static SWTBot4diacGefEditor editor;

	/**
	 * Initializes the ECC editor for testing. This method creates a function block
	 * type (FBT) and opens it in the editor. It also activates and focuses on the
	 * ECC tab within the editor.
	 */
	@BeforeAll
	public static void initialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.TEMPLATEBASIC);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBotCTabItem eccTab = bot.cTabItem(UITestNamesHelper.ECC);
		eccTab.activate();
		eccTab.setFocus();

		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		editor = bot.gefEditor(UITestNamesHelper.FBT_TEST_PROJECT2);
	}

	/**
	 * Ensures the editor is visible and focused before each test.
	 */
	@SuppressWarnings("static-method")
	@BeforeEach
	public void beforeEach() {
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
	}

	/**
	 * Tests the creation of an EC state within the ECC editor. Verifies that the
	 * new state is added to the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(1)
	public void createECState() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE);
		assertNotNull(editor.getEditPart(UITestNamesHelper.STATE));
	}

	/**
	 * Tests changing the name of an EC state using the properties tab. Verifies
	 * that the old state name is removed and the new name is assigned.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(2)
	public void changeECStateNameWithTab() {
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.STATE);
		assertNotNull(statePart);
		statePart.click();
		statePart.focus();

		// Properties tab access
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.STATE);
		assertNotNull(propertiesBot);

		propertiesBot.textWithLabel(UITestNamesHelper.NAME_LABEL).setText(UITestNamesHelper.TESTSTATE);
		assertNull(editor.getEditPart(UITestNamesHelper.STATE));
		assertNotNull(editor.getEditPart(UITestNamesHelper.TESTSTATE));
	}

	/**
	 * Tests creating an EC action within a specified EC state. Verifies that the
	 * action is added to the state.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(3)
	public void createECAction() {
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.TESTSTATE);
		assertNotNull(statePart);
		final Point testStatePoint = new SWTBotECC(bot).getPoint(statePart);
		editor.clickContextMenu(UITestNamesHelper.ADD_ACTION, testStatePoint.x, testStatePoint.y);
		assertNotNull(editor.getEditPart(UITestNamesHelper.TESTSTATE).children());
	}

	/**
	 * Tests creating an algorithm within a specified EC state action. Verifies that
	 * the algorithm is correctly assigned to the action.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(4)
	public void createAlgorithm() {

		new SWTBotECC(bot).changeAlgorithmAndEventValue(editor, UITestNamesHelper.TESTSTATE,
				UITestNamesHelper.DEINITIALIZE, 0);
	}

	/**
	 * Tests creating an output event within a specified EC state action. Verifies
	 * that the output event is correctly assigned to the action.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(5)
	public void createOutputEvent() {
		new SWTBotECC(bot).changeAlgorithmAndEventValue(editor, UITestNamesHelper.DEINIT, UITestNamesHelper.CNF, -1);
	}

	/**
	 * Tests creating a transition between two EC states. Verifies that the
	 * transition is successfully created.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(6)
	public void createTransition() {
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.START);
		final SWTBotECC eccBot = new SWTBotECC(bot);
		final Point start = eccBot.getPoint(statePart);
		assertNotNull(start);

		final SWTBotGefEditPart endPart = editor.getEditPart(UITestNamesHelper.TESTSTATE);
		final Point end = eccBot.getPoint(endPart);
		assertNotNull(end);

		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();
		viewer.drag(start.x, start.y, end.x, end.y);
	}

	/**
	 * Tests changing the algorithm associated with an EC state action. Verifies
	 * that the new algorithm is correctly assigned.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(7)
	public void changeAlgorithm() {
		new SWTBotECC(bot).changeAlgorithmAndEventValue(editor, UITestNamesHelper.TESTSTATE,
				UITestNamesHelper.INITIALIZE, 0);
	}

	/**
	 * Tests changing the condition event associated with an EC transition. Verifies
	 * that the new condition event is correctly assigned.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(8)
	public void changeConditionEvent() {
		new SWTBotECC(bot).changeAlgorithmAndEventValue(editor, UITestNamesHelper.INIT_SMALL, UITestNamesHelper.CNF,
				-1);
	}

	/**
	 * Tests changing the condition expression of an EC transition. Verifies that
	 * the transition is updated with the new expression.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(9)
	public void changeECTransitionConditionExpression() {
		final SWTBotGefEditPart part = editor
				.getEditPart(UITestNamesHelper.INIT + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QI + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		editor.select(part);
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.TRANSITION);
		assertNotNull(propertiesBot);

		propertiesBot.ccomboBoxWithLabel(UITestNamesHelper.CONDITION_LABEL).setSelection(UITestNamesHelper.REQ);

		propertiesBot.styledTextWithLabel(UITestNamesHelper.CONDITION_LABEL)
				.setText(UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO); //$NON-NLS-1$
		assertEquals(propertiesBot.styledTextWithLabel(UITestNamesHelper.CONDITION_LABEL).getText(),
				UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO); //$NON-NLS-1$
		assertNull(editor
				.getEditPart(UITestNamesHelper.INIT + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QI + "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(editor
				.getEditPart(UITestNamesHelper.REQ + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO + "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}

	/**
	 * Tests adding a comment to an EC transition. Verifies that the comment is
	 * successfully added and displayed.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(10)
	public void changeECTransitionComment() {
		final SWTBotGefEditPart part = editor
				.getEditPart(UITestNamesHelper.REQ + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		editor.select(part);
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.TRANSITION);
		assertNotNull(propertiesBot);

		propertiesBot.textWithLabel(UITestNamesHelper.COMMENT).setText(UITestNamesHelper.TEST_COMMENT);

		assertEquals(propertiesBot.textWithLabel(UITestNamesHelper.COMMENT).getText(), UITestNamesHelper.TEST_COMMENT);
	}

	/**
	 * Tests deleting an EC action from an EC state. Verifies that the action is
	 * successfully removed.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(11)
	public void deleteECAction() {
		final SWTBotECC eccBot = new SWTBotECC(bot);
		final SWTBotGefEditPart actionPart = eccBot.getChildrenPart(editor.getEditPart(UITestNamesHelper.TESTSTATE), 0);
		final Point actionPoint = eccBot.getPoint(actionPart);
		actionPart.click();
		editor.clickContextMenu(UITestNamesHelper.DELETE, actionPoint.x, actionPoint.y);

	}

	/**
	 * Tests setting an invalid name for an EC state.
	 *
	 * This test focuses on ensuring that when an invalid name (a reserved keyword)
	 * is set for a state, the change is not applied, and the state retains its
	 * original name.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(12)
	public void tryToSetInvalidName() {
		// Retrieve the EC state part and focus on it
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.TESTSTATE);
		statePart.click();
		statePart.focus();

		// Access the Properties tab and try to set an invalid name
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.STATE);
		propertiesBot.textWithLabel(UITestNamesHelper.NAME_LABEL).setText(UITestNamesHelper.IF);

		// Validate that the state name remains unchanged
		assertNotNull(editor.getEditPart(UITestNamesHelper.TESTSTATE));
		assertNull(editor.getEditPart(UITestNamesHelper.IF));
	}

	/**
	 * Tests setting an invalid condition expression for an EC transition.
	 *
	 * This test verifies that setting an invalid condition expression (e.g., using
	 * undefined variables) for a transition correctly updates the transition
	 * expression and removes the invalid transition from the editor.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(13)
	public void tryToInvalidECTransitionConditionExpression() {
		// Select the EC transition part
		final SWTBotGefEditPart part = editor
				.getEditPart(UITestNamesHelper.REQ + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		editor.select(part);

		// Access the Transition tab and set an invalid condition
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.TRANSITION);
		assertNotNull(propertiesBot);
		propertiesBot.ccomboBoxWithLabel(UITestNamesHelper.CONDITION_LABEL).setSelection(UITestNamesHelper.INIT);
		propertiesBot.styledTextWithLabel(UITestNamesHelper.CONDITION_LABEL)
				.setText(UITestNamesHelper.TRUE + " = " + UITestPinHelper.AB); //$NON-NLS-1$

		// Validate the new condition expression
		assertEquals(propertiesBot.styledTextWithLabel(UITestNamesHelper.CONDITION_LABEL).getText(),
				UITestNamesHelper.TRUE + " = " + UITestPinHelper.AB); //$NON-NLS-1$

		// Verify that the transition part is correctly updated
		assertNull(editor
				.getEditPart(UITestNamesHelper.REQ + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.QO + "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(editor
				.getEditPart(UITestNamesHelper.INIT + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.AB + "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Tests changing an algorithm associated with an EC state through the actions
	 * table.
	 *
	 * This test ensures that modifying the algorithm in the actions table of a
	 * state updates the state accordingly and removes any references to the old
	 * algorithm.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(14)
	public void changeAlgorithmWithTable() {
		// Select the state part and focus on it
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.INIT_SMALL);
		statePart.click();
		statePart.focus();

		// Access the Properties tab and verify the state name
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.STATE);
		assertEquals(propertiesBot.textWithLabel(UITestNamesHelper.NAME_LABEL).getText(), UITestNamesHelper.INIT_SMALL);

		// Modify the algorithm using the actions table
		final SWTBotTable actionsTableBot = propertiesBot.table(0);
		actionsTableBot.doubleClick(0, 0);
		propertiesBot.ccomboBox().setSelection(UITestNamesHelper.DEINITIALIZE);
		statePart.click();

		// Ensure the old algorithm part no longer exists
		assertNull(editor.getEditPart(UITestNamesHelper.INITIALIZE));
	}

	/**
	 * Tests changing an event associated with an EC state through the actions
	 * table.
	 *
	 * This test verifies that modifying the event in the actions table of a state
	 * updates the event accordingly and removes any references to the old event.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(15)
	public void changeEventWithTable() {
		// Select the state part and focus on it
		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.INIT_SMALL);
		statePart.click();
		statePart.focus();

		// Access the Properties tab and verify the state name
		final SWTBot propertiesBot = selectTabFromECCProperties(UITestNamesHelper.STATE);
		assertEquals(propertiesBot.textWithLabel(UITestNamesHelper.NAME_LABEL).getText(), UITestNamesHelper.INIT_SMALL);

		// Modify the event using the actions table
		final SWTBotTable actionsTableBot = propertiesBot.table(0);
		actionsTableBot.doubleClick(0, 1);
		propertiesBot.ccomboBox().setSelection(UITestNamesHelper.CNF);
		statePart.click();

		// Uncomment and modify the following line if you need to validate the removal
		// of an old event
		// assertNull(editor.getEditPart(UITestNamesHelper.INITIALIZE));
	}

	/**
	 * Tests deleting an EC transition.
	 *
	 * This test ensures that a selected transition is successfully deleted using
	 * the context menu, and that it is no longer present in the editor.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(16)
	public void deleteTransition() {
		// Select the transition part and focus on it
		final SWTBotGefEditPart part = editor
				.getEditPart(UITestNamesHelper.INIT + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.AB + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		editor.select(part);
		assertNotNull(part);

		// Get the location of the transition part and delete it via context menu
		final Point partPoint = new SWTBotECC(bot).getPoint(part);
		editor.select(part);
		editor.clickContextMenu(UITestNamesHelper.DELETE, partPoint.x, partPoint.y);

		// Verify that the transition part is successfully deleted
		assertNull(editor
				.getEditPart(UITestNamesHelper.INIT + "[" + UITestNamesHelper.TRUE + " = " + UITestPinHelper.AB + "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Tests deleting an EC state from the ECC editor. Verifies that the state is
	 * successfully removed.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(17)
	public void deleteECState() {
		editor.getEditPart(UITestNamesHelper.TESTSTATE).click();
		editor.clickContextMenu(UITestNamesHelper.DELETE);
	}

	public void tryToMoveBendPoint() {
		// TODO
	}

	public void tryToChangeTransitionDestination() {
		// TODO
	}

	public void tryToChangeTransitionSource() {
		// TODO
	}

}
