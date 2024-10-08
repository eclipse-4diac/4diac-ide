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
 *                - moved methods that are related to Function Blocks from class
 *                  Abstract4diacUITests here due to architecture redesign and
 *                  created constructor and fields.
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class SWTBotFB {

	private final SWT4diacGefBot bot;

	@SuppressWarnings("static-access")
	public SWTBotFB(final SWT4diacGefBot bot) {
		this.bot = bot;
	}

	/**
	 * Drags and drops a FB onto the canvas with given name and position.
	 *
	 * @param fbName The name of the Function Block.
	 * @param point  The Position of the FB on the canvas.
	 */
	@SuppressWarnings("static-access")
	public void dragAndDropEventsFB(final String fbName, final Point point) {
		final SWTBotSystemExplorer sysEx = new SWTBotSystemExplorer(bot);
		final SWTBotTreeItem typeLibraryNode = sysEx.expandTypeLibraryTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(typeLibraryNode, UITestNamesHelper.EVENTS_NODE));
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(UITestNamesHelper.EVENTS_NODE);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, fbName));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(fbName);
		eCycleNode.select();
		eCycleNode.click();

		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();

		assertNotNull(canvas);
		eCycleNode.dragAndDrop(canvas, point);
	}

	/**
	 * Selects a FunctionBlock with the given Name in the editor.
	 *
	 * The function block is searched for with the given name in the editor.
	 * However, as this only selects the name, the parent is retrieved and selected.
	 * Only then is the searched function block selected as a whole.
	 *
	 * @param editor
	 * @param name
	 */
	@SuppressWarnings("static-method")
	public SWTBot4diacGefEditor selectFBWithFBNameInEditor(final SWTBot4diacGefEditor editor, final String name) {
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(name));
		editor.click(name);
		final SWTBotGefEditPart parent = editor.getEditPart(name).parent();
		assertNotNull(parent);
		editor.click(parent);
		return editor;
	}

	/**
	 * Deletes a FB from the editing area.
	 *
	 * @param editor         The SWTBot4diacGefEditor from which a FB with given
	 *                       instance name should be deleted.
	 * @param FbInstanceName The instance name of the FB
	 */
	public void deleteFB(final SWTBot4diacGefEditor editor, final String FbInstanceName) {
		editor.setFocus();
		final SWTBotGefEditPart fb = editor.getEditPart(FbInstanceName).parent();
		fb.select().click();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();
	}

	/**
	 * Checks if FB is selected by searching selectedEditParts list and returns the
	 * corresponding boolean value.
	 *
	 * @param selectedEditParts The List of selected EditParts.
	 * @param fbName            The FB that is searched for.
	 * @return true if fbName is in the List, otherwise false.
	 */
	@SuppressWarnings("static-method")
	public boolean isFbSelected(final List<SWTBotGefEditPart> selectedEditParts, final String fbName) {
		return selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart).map(p -> (FBEditPart) p.part())
				.anyMatch(fb -> fb.getModel().getName().equals(fbName));
	}

	/**
	 * Checks if given FB instance name can be found on the editing area.
	 *
	 * @param editor The SWTBot4diacGefEditor in which the FB instance name is
	 *               searched
	 * @param fbName The String of the FB instance name searched for
	 * @return true if FB is onto editing area, false if not
	 */
	@SuppressWarnings("static-method")
	public boolean isFBNamePresendOnEditingArea(final SWTBot4diacGefEditor editor, final String fbName) {
		final List<SWTBotGefEditPart> editParts = editor.editParts(new BaseMatcher<EditPart>() {
			@Override
			public boolean matches(final Object item) {
				return item instanceof FBEditPart;
			}

			@Override
			public void describeTo(final Description description) {
				// method must be implemented but empty since not needed
			}
		});
		assertFalse(editParts.isEmpty());
		return editParts.stream().filter(p -> p.part() instanceof FBEditPart).map(p -> (FBEditPart) p.part())
				.anyMatch(fb -> fb.getModel().getName().equals(fbName));
	}

	/**
	 * Returns the bounds of the searched function block
	 *
	 * @param editor
	 * @param fBname
	 * @return The rectangle with the bounds of the function block searched for
	 */
	@SuppressWarnings("static-method")
	public Rectangle getBoundsOfFB(final SWTBotGefEditor editor, final String fBname) {
		editor.getEditPart(fBname);
		editor.click(fBname);
		final SWTBotGefEditPart parent = editor.getEditPart(fBname).parent();
		assertNotNull(parent);
		final IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		assertNotNull(figure);
		final Rectangle fbBounds = figure.getBounds().getCopy();
		assertNotNull(fbBounds);
		figure.translateToAbsolute(fbBounds);
		return fbBounds;
	}

	/**
	 * Moves a Function Block to the given position
	 *
	 * @param editor
	 * @param fBname  The FB that should be moved
	 * @param toPoint The new position of the FB
	 */
	public void moveSingleFB(SWTBotGefEditor editor, final String fBname, final Point toPoint) {
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(fBname));
		editor = selectFBWithFBNameInEditor((SWTBot4diacGefEditor) editor, fBname);
		final SWTBotGefEditPart fbEditPart = editor.getEditPart(fBname).parent();
		assertNotNull(fbEditPart);

		// move FB, get bounds of FB and expand bounds to have a tolerance for the grid
		// alignment when checking the new position
		editor.drag(fbEditPart, toPoint.x, toPoint.y);
		final Rectangle toPosition = getBoundsOfFB(editor, fBname);
		final Rectangle expandedBounds = toPosition.expand(new Insets(2));
		assertTrue(expandedBounds.contains(toPoint.x, toPoint.y));
	}

}
