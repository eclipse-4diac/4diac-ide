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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateTransitionCommand;
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
import org.junit.jupiter.api.Test;

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

	@Test
	public void moveECStateAdjustsOutgoingTransitionBendpoint() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 250, 150);

		final SWTBotGefEditPart statePart = editor.getEditPart(UITestNamesHelper.START);
		final SWTBotGefEditPart targetPart = editor.getEditPart(UITestNamesHelper.STATE);
		assertNotNull(statePart);
		assertNotNull(targetPart);

		final ECState state = ((ECStateEditPart) statePart.part()).getModel();
		final ECState target = ((ECStateEditPart) targetPart.part()).getModel();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();
		final ECTransition transition = createTransition(viewer, state, target);

		moveAndVerify(viewer, statePart, state, transition);
	}

	@Test
	public void moveECStateAdjustsIncomingTransitionBendpoint() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 450, 350);

		final SWTBotGefEditPart sourcePart = editor.getEditPart(UITestNamesHelper.START);
		final SWTBotGefEditPart statePart = editor.getEditPart(SECOND_STATE_NAME);
		assertNotNull(sourcePart);
		assertNotNull(statePart);

		final ECState source = ((ECStateEditPart) sourcePart.part()).getModel();
		final ECState state = ((ECStateEditPart) statePart.part()).getModel();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();
		final ECTransition transition = createTransition(viewer, source, state);

		moveAndVerify(viewer, statePart, state, transition);
	}

	@Test
	public void moveECStateWithSelfLoopAdjustsBendpointOnce() {
		editor.clickContextMenu(UITestNamesHelper.ADD_STATE, 550, 550);

		final SWTBotGefEditPart statePart = editor.getEditPart(THIRD_STATE_NAME);
		assertNotNull(statePart);

		final ECState state = ((ECStateEditPart) statePart.part()).getModel();
		final SWTBot4diacGefViewer viewer = editor.getSWTBotGefViewer();
		final ECTransition selfLoop = createTransition(viewer, state, state);

		moveAndVerify(viewer, statePart, state, selfLoop);
	}

	private static void moveAndVerify(final SWTBot4diacGefViewer viewer, final SWTBotGefEditPart statePart,
			final ECState state, final ECTransition transition) {
		final Position originalStatePosition = EcoreUtil.copy(state.getPosition());
		final Position originalTransitionPosition = EcoreUtil.copy(transition.getPosition());
		final Point statePoint = new SWTBotECC().getPoint(statePart);

		statePart.select();
		viewer.drag(statePoint.x, statePoint.y, statePoint.x + MOVE_DELTA, statePoint.y + MOVE_DELTA);

		final double dx = state.getPosition().getX() - originalStatePosition.getX();
		final double dy = state.getPosition().getY() - originalStatePosition.getY();
		assertMovedByDelta(state, transition, originalStatePosition, originalTransitionPosition, dx, dy);

		final Position movedStatePosition = EcoreUtil.copy(state.getPosition());
		final Position movedTransitionPosition = EcoreUtil.copy(transition.getPosition());
		final CommandStack commandStack = getCommandStack(viewer);

		commandStack.undo();
		assertPositionEquals(originalStatePosition, state.getPosition());
		assertPositionEquals(originalTransitionPosition, transition.getPosition());

		commandStack.redo();
		assertPositionEquals(movedStatePosition, state.getPosition());
		assertPositionEquals(movedTransitionPosition, transition.getPosition());
	}

	private static void assertMovedByDelta(final ECState state, final ECTransition transition,
			final Position originalStatePosition, final Position originalTransitionPosition, final double dx,
			final double dy) {
		assertFalse(Math.abs(dx) < POSITION_EPSILON && Math.abs(dy) < POSITION_EPSILON);
		assertEquals(originalStatePosition.getX() + dx, state.getPosition().getX(), POSITION_EPSILON);
		assertEquals(originalStatePosition.getY() + dy, state.getPosition().getY(), POSITION_EPSILON);
		assertEquals(originalTransitionPosition.getX() + dx, transition.getPosition().getX(), POSITION_EPSILON);
		assertEquals(originalTransitionPosition.getY() + dy, transition.getPosition().getY(), POSITION_EPSILON);
	}

	private static void assertPositionEquals(final Position expected, final Position actual) {
		assertEquals(expected.getX(), actual.getX(), POSITION_EPSILON);
		assertEquals(expected.getY(), actual.getY(), POSITION_EPSILON);
	}

	private static ECTransition createTransition(final SWTBot4diacGefViewer viewer, final ECState source,
			final ECState destination) {
		final CreateTransitionCommand command = new CreateTransitionCommand(source, destination, null);
		return UIThreadRunnable.syncExec((Result<ECTransition>) () -> {
			getCommandStack(viewer).execute(command);
			return (ECTransition) command.getCreatedElement();
		});
	}

	private static CommandStack getCommandStack(final SWTBot4diacGefViewer viewer) {
		return UIThreadRunnable
				.syncExec((Result<CommandStack>) () -> viewer.getGraphicalViewer().getEditDomain().getCommandStack());
	}
}
