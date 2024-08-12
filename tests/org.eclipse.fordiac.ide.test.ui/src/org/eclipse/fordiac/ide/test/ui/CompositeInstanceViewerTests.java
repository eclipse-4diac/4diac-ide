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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.test.ui.helpers.PinNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.StringNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.eclipse.ui.IEditorPart;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CompositeInstanceViewerTests extends Abstract4diacUITests {

	/**
	 * Checks if new breadcrumb tab appears after double click on a composite FB
	 *
	 * The method drag and drops the FB E_N_TABLE onto the canvas and then double
	 * clicks on it. It is expected that a new breadcrumb tab with the name of the
	 * FB appears and the editor is an instance of CompositeInstanceViewer.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerAppearsAfterDoubleClickOnFB() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = goToCompositeInstanceViewer(StringNamesHelper.E_N_TABLE_FB);

		UIThreadRunnable.syncExec(() -> {
			final IEditorPart editorPart = editor.getReference().getEditor(false);
			if (editorPart instanceof final AbstractBreadCrumbEditor breadCrumbEditor) {
				final String title = breadCrumbEditor.getBreadcrumb().getActiveItem().getText();
				assertEquals(StringNamesHelper.E_N_TABLE_FB, title);
				assertTrue(breadCrumbEditor.getActiveEditor() instanceof CompositeInstanceViewer);
			}
		});
		returnToEditingArea();
	}

	/**
	 * Checks if it is possible to move FB in CompositeInstanceViewer
	 *
	 * The method checks whether an FB can be moved in the CompositeinstanceViewer.
	 * First an E_N_TABLE FB is moved to the editing area and then the
	 * CompositeInstanceViewer is reached by double clicking on the FB. The
	 * coordinates of the F_SUB FB are fetched and an attempt is made to move the
	 * FB. Afterwards the position are checked again, these should be as expected
	 * the same as at the beginning. For certain checks it is also necessary to
	 * create a new draw2d.geometry Point, because draw2d.geometry Points are not
	 * compatible with the former.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerMoveFB() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(200, 200));
		goToCompositeInstanceViewer(StringNamesHelper.E_N_TABLE_FB);

		final SWTBotGefEditor editor = bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(StringNamesHelper.F_SUB));
		editor.click(StringNamesHelper.F_SUB);
		SWTBotGefEditPart parent = editor.getEditPart(StringNamesHelper.F_SUB).parent();
		assertNotNull(parent);

		IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		assertNotNull(figure);
		Rectangle fbBounds = figure.getBounds().getCopy();
		assertNotNull(fbBounds);
		figure.translateToAbsolute(fbBounds);
		final Point posFSub = new Point(fbBounds.x, fbBounds.y);

		final org.eclipse.draw2d.geometry.Point posToCheck1 = new org.eclipse.draw2d.geometry.Point(posFSub);
		assertTrue(fbBounds.contains(posToCheck1));
		parent.click();

		final Point pos2 = new Point(85, 85);
		editor.drag(parent, pos2.x, pos2.y);
		final org.eclipse.draw2d.geometry.Point posToCheck2 = new org.eclipse.draw2d.geometry.Point(pos2);

		parent = editor.getEditPart(StringNamesHelper.F_SUB).parent();
		figure = ((GraphicalEditPart) parent.part()).getFigure();
		fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);
		assertNotEquals(posToCheck2.x, fbBounds.x);
		assertEquals(posToCheck1.x, fbBounds.x);
		assertNotEquals(posToCheck2.y, fbBounds.y);
		assertEquals(posToCheck1.y, fbBounds.y);
		assertFalse(fbBounds.contains(posToCheck2));
		assertTrue(fbBounds.contains(posToCheck1));

		returnToEditingArea();
	}

	/**
	 * Checks if it is possible to delete FB in CompositeInstanceViewer
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerDeleteFB() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(200, 200));
		goToCompositeInstanceViewer(StringNamesHelper.E_N_TABLE_FB);

		final SWTBotGefEditor editor = bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(StringNamesHelper.E_DEMUX_FB));
		editor.click(StringNamesHelper.E_DEMUX_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(StringNamesHelper.E_DEMUX_FB).parent();
		assertNotNull(parent);
		parent.click();

		assertTrue(bot.menu(StringNamesHelper.EDIT).isVisible());
		bot.menu(StringNamesHelper.EDIT).click();
		assertTrue(bot.menu(StringNamesHelper.DELETE).isVisible());
		assertFalse(bot.menu(StringNamesHelper.DELETE).isEnabled());

		bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.SELECT_ALL).click();
		assertTrue(bot.menu(StringNamesHelper.DELETE).isVisible());
		assertFalse(bot.menu(StringNamesHelper.DELETE).isEnabled());

		returnToEditingArea();
	}

	/**
	 * Checks if it is possible to add another FB in CompositeInstanceViewer
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerAddAnotherFB() {
		dragAndDropEventsFB(StringNamesHelper.E_CYCLE_TREE_ITEM, new Point(200, 200));
		goToCompositeInstanceViewer(StringNamesHelper.E_CYCLE_FB);
		dragAndDropEventsFB(StringNamesHelper.E_SWITCH_TREE_ITEM, new Point(100, 100));
		final SWTBotGefEditor editor = bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(StringNamesHelper.E_DELAY_FB));
		assertNull(editor.getEditPart(StringNamesHelper.E_SWITCH_FB));
		returnToEditingArea();
	}

	/**
	 * Checks if it is possible to edit name of FB in CompositeInstanceViewer
	 */
	@Disabled("Disabled until implementation.")
	@Test
	public void compositeInstanceViewerEditingOfFB() {
		// in progress
	}

	/**
	 * Checks if it is possible to add connections in CompositeInstanceViewer
	 *
	 * The method checks if it is possible to add connections between FB pins in
	 * CompositeInstanceViewer. It is expected that this is not possible.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerConnectionCanBeAdded() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(200, 200));
		goToCompositeInstanceViewer(StringNamesHelper.E_N_TABLE_FB);
		SWTBot4diacGefViewer viewer = createConnection(PinNamesHelper.EO, PinNamesHelper.EI);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
		viewer = createConnection(PinNamesHelper.N, PinNamesHelper.CV);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
		viewer = createConnection(PinNamesHelper.START, PinNamesHelper.STOP);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
		viewer = createConnection(PinNamesHelper.START, PinNamesHelper.EO0);
		assertThrows(TimeoutException.class, viewer::waitForConnection);
		returnToEditingArea();
	}

	/**
	 * Checks if it is possible to delete a connection in CompositeInstanceViewer
	 */
	@SuppressWarnings("static-method")
	@Test
	public void compositeInstanceViewerDeleteConnection() {
		dragAndDropEventsFB(StringNamesHelper.E_N_TABLE_TREE_ITEM, new Point(100, 100));
		goToCompositeInstanceViewer(StringNamesHelper.E_N_TABLE_FB);

		final SWTBotGefEditor editor = bot.gefEditor(StringNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final ConnectionEditPart connection = findConnection(PinNamesHelper.EO, PinNamesHelper.REQ);
		final PolylineConnection figure = (PolylineConnection) connection.getFigure();
		final PointList points = figure.getPoints();
		final org.eclipse.draw2d.geometry.Point firstPoint = points.getFirstPoint();
		figure.translateToAbsolute(firstPoint);
		viewer.click(firstPoint.x, firstPoint.y);
		assertTrue(figure.containsPoint(points.getFirstPoint()));

		// select breadcrumb tab of FB label
		UIThreadRunnable.syncExec(() -> {
			final IEditorPart editorPart = editor.getReference().getEditor(false);
			if (editorPart instanceof final AbstractBreadCrumbEditor breadCrumbEditor) {
				final String title = breadCrumbEditor.getBreadcrumb().getActiveItem().getText();
				assertEquals(StringNamesHelper.E_N_TABLE_FB, title);
				assertTrue(breadCrumbEditor.getActiveEditor() instanceof CompositeInstanceViewer);
			}
		});

		assertFalse(bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.DELETE).isEnabled());
		assertTrue(bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.SELECT_ALL).isEnabled());
		bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.SELECT_ALL).click();
		assertFalse(bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.DELETE).isEnabled());
		returnToEditingArea();
	}

	/**
	 * Checks if second breadcrumb tab appears after another double on a composite
	 * FB
	 */
	@Disabled
	@Test
	public void isAnotherBreadcrumbTabAppearingAfterDoubleClickOnCompositeFB() {
		// in progress
		// for instance drag and drop E_N_TABLE and than double click on E_TABLE - is
		// another breadcrumb tab appearing (right next to E_N_TABLE)
	}

}
