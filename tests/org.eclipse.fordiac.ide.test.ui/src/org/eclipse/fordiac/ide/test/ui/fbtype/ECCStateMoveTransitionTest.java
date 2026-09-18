/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.fbtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotECC;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ECCStateMoveTransitionTest extends Abstract4diacUITests {

	private static final double POSITION_EPSILON = 0.01d;
	private static final int MOVE_DELTA = 60;
	private static final String PROJECT_NAME = "ECCStateMoveTransitionTestType"; //$NON-NLS-1$
	private static final String SECOND_STATE_NAME = "State_1"; //$NON-NLS-1$
	private static final String THIRD_STATE_NAME = "State_2"; //$NON-NLS-1$

	protected static SWTBot4diacGefEditor editor;

	@BeforeAll
	public static void initialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, PROJECT_NAME, UITestNamesHelper.TEMPLATEBASIC);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, PROJECT_NAME);

		final SWTBotCTabItem eccTab = bot.cTabItem(UITestNamesHelper.ECC);
		eccTab.activate();
		eccTab.setFocus();

		bot.editorByTitle(PROJECT_NAME).show();
		editor = bot.gefEditor(PROJECT_NAME);
	}

	@SuppressWarnings("static-method")
	@BeforeEach
	public void beforeEach() {
		bot.editorByTitle(PROJECT_NAME).show();
	}

	/**
	 * Verifies that moving a state shifts an outgoing transition by the same delta
	 * and that undo and redo restore and reapply both positions.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(1)
	public void moveECStateAdjustsOutgoingTransitionBendpoint() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 250, 150);

		final SWTBotGefEditPart startPart = editor.getEditPart(UITestNamesHelper.START);
		assertNotNull(startPart);

		final ECState startState = ((ECStateEditPart) startPart.part()).getModel();

		final SWTBotGefEditPart targetPart = editor.getEditPart(UITestNamesHelper.STATE);
		assertNotNull(targetPart);

		final SWTBotECC eccBot = new SWTBotECC();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();

		final Point startPoint = eccBot.getPoint(startPart);
		final Point targetPoint = eccBot.getPoint(targetPart);

		viewer.drag(startPoint.x, startPoint.y, targetPoint.x, targetPoint.y);

		assertFalse(startState.getOutTransitions().isEmpty());

		final ECTransition transition = startState.getOutTransitions().get(0);
		final Position originalStatePosition = EcoreUtil.copy(startState.getPosition());
		final Position originalTransitionPosition = EcoreUtil.copy(transition.getPosition());

		startPart.select();
		viewer.drag(startPoint.x, startPoint.y, startPoint.x + MOVE_DELTA, startPoint.y + MOVE_DELTA);

		assertMovedByDelta(startState, transition, originalStatePosition, originalTransitionPosition);

		final CommandStack commandStack = getCommandStack(viewer);

		commandStack.undo();

		assertPositionEquals(originalStatePosition, startState.getPosition());
		assertPositionEquals(originalTransitionPosition, transition.getPosition());

		commandStack.redo();

		assertMovedByDelta(startState, transition, originalStatePosition, originalTransitionPosition);
	}

	/**
	 * Verifies that moving a state shifts an incoming transition by the same delta
	 * and that undo and redo restore and reapply both positions.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(2)
	public void moveECStateAdjustsIncomingTransitionBendpoint() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 450, 350);

		final SWTBotGefEditPart targetPart = editor.getEditPart(SECOND_STATE_NAME);
		assertNotNull(targetPart);

		final ECState targetState = ((ECStateEditPart) targetPart.part()).getModel();

		final SWTBotGefEditPart startPart = editor.getEditPart(UITestNamesHelper.START);
		assertNotNull(startPart);

		final SWTBotECC eccBot = new SWTBotECC();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();

		final Point startPoint = eccBot.getPoint(startPart);
		final Point targetPoint = eccBot.getPoint(targetPart);

		viewer.drag(startPoint.x, startPoint.y, targetPoint.x, targetPoint.y);

		assertFalse(targetState.getInTransitions().isEmpty());

		final ECTransition transition = targetState.getInTransitions().get(targetState.getInTransitions().size() - 1);

		final Position originalStatePosition = EcoreUtil.copy(targetState.getPosition());
		final Position originalTransitionPosition = EcoreUtil.copy(transition.getPosition());

		targetPart.select();
		viewer.drag(targetPoint.x, targetPoint.y, targetPoint.x + MOVE_DELTA, targetPoint.y + MOVE_DELTA);

		assertMovedByDelta(targetState, transition, originalStatePosition, originalTransitionPosition);

		final CommandStack commandStack = getCommandStack(viewer);

		commandStack.undo();

		assertPositionEquals(originalStatePosition, targetState.getPosition());
		assertPositionEquals(originalTransitionPosition, transition.getPosition());

		commandStack.redo();

		assertMovedByDelta(targetState, transition, originalStatePosition, originalTransitionPosition);
	}

	/**
	 * Verifies that a self-loop transition is shifted exactly once when its state
	 * is moved. Undo and redo must restore and reapply both positions.
	 */
	@SuppressWarnings("static-method")
	@Test
	@Order(3)
	public void moveECStateWithSelfLoopAdjustsBendpointOnce() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 550, 550);

		final SWTBotGefEditPart statePart = editor.getEditPart(THIRD_STATE_NAME);
		assertNotNull(statePart);

		final ECState state = ((ECStateEditPart) statePart.part()).getModel();

		final SWTBotECC eccBot = new SWTBotECC();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();
		final Point statePoint = eccBot.getPoint(statePart);

		viewer.drag(statePoint.x, statePoint.y, statePoint.x + 5, statePoint.y + 5);

		final ECTransition selfLoop = state.getOutTransitions().stream()
				.filter(transition -> transition.getSource() == state && transition.getDestination() == state)
				.findFirst().orElse(null);

		assertNotNull(selfLoop);
		assertTrue(state.getInTransitions().contains(selfLoop));

		final Position originalStatePosition = EcoreUtil.copy(state.getPosition());
		final Position originalTransitionPosition = EcoreUtil.copy(selfLoop.getPosition());

		statePart.select();
		viewer.drag(statePoint.x, statePoint.y, statePoint.x + MOVE_DELTA, statePoint.y + MOVE_DELTA);

		assertMovedByDelta(state, selfLoop, originalStatePosition, originalTransitionPosition);

		final CommandStack commandStack = getCommandStack(viewer);

		commandStack.undo();

		assertPositionEquals(originalStatePosition, state.getPosition());
		assertPositionEquals(originalTransitionPosition, selfLoop.getPosition());

		commandStack.redo();

		assertMovedByDelta(state, selfLoop, originalStatePosition, originalTransitionPosition);
	}

	private static void assertMovedByDelta(final ECState state, final ECTransition transition,
			final Position originalStatePosition, final Position originalTransitionPosition) {
		assertEquals(originalStatePosition.getX() + MOVE_DELTA, state.getPosition().getX(), POSITION_EPSILON);
		assertEquals(originalStatePosition.getY() + MOVE_DELTA, state.getPosition().getY(), POSITION_EPSILON);
		assertEquals(originalTransitionPosition.getX() + MOVE_DELTA, transition.getPosition().getX(), POSITION_EPSILON);
		assertEquals(originalTransitionPosition.getY() + MOVE_DELTA, transition.getPosition().getY(), POSITION_EPSILON);
	}

	private static void assertPositionEquals(final Position expected, final Position actual) {
		assertEquals(expected.getX(), actual.getX(), POSITION_EPSILON);
		assertEquals(expected.getY(), actual.getY(), POSITION_EPSILON);
	}

	private static CommandStack getCommandStack(final SWTBot4diacGefViewer viewer) {
		return UIThreadRunnable
				.syncExec((Result<CommandStack>) () -> viewer.getGraphicalViewer().getEditDomain().getCommandStack());
	}
}
