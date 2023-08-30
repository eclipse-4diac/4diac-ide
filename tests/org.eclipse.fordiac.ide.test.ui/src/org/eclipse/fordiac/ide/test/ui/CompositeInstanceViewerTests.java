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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CompositeInstanceViewerTests {

	private static final String APP = "App"; //$NON-NLS-1$
	private static final String DELETE = "Delete"; //$NON-NLS-1$
	private static final String DELETE_PROJECT_WARNING = "Delete project contents on disk (cannot be undone)"; //$NON-NLS-1$
	private static final String DELETE_RESOURCES = "Delete Resources"; //$NON-NLS-1$
	private static final String E_N_TABLE_FB = "E_N_TABLE"; //$NON-NLS-1$
	private static final String E_N_TABLE_TREE_ITEM = "E_N_TABLE [Generation of a finite train of sperate events]"; //$NON-NLS-1$
	private static final String EDIT = "Edit"; //$NON-NLS-1$
	private static final String EVENTS_NODE = "events"; //$NON-NLS-1$
	private static final String FILE = "File"; //$NON-NLS-1$
	private static final String FINISH = "Finish"; //$NON-NLS-1$
	private static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	private static final String GO_TO_PARENT = "Go To Parent"; //$NON-NLS-1$
	private static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	private static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	private static final String NAVIGATE = "Navigate"; //$NON-NLS-1$
	private static final String NEW = "New"; //$NON-NLS-1$
	private static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	private static final String OK = "OK"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	private static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$
	private static final String SELECT_ALL = "Select All"; //$NON-NLS-1$
	private static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	private static final String TYPE_LIBRARY_NODE = "Type Library"; //$NON-NLS-1$
	private static SWT4diacGefBot bot;

	/**
	 * Performs the necessary tasks to be able to perform the tests.
	 *
	 * The method creates the SWT4diacGefBot, starts 4diac IDE and closes the
	 * welcome window. The Timeout is increase from default value of 5 Seconds to 10
	 * Seconds. By calling the private method {@code createProject()} a new 4diac
	 * IDE project is created to be able to perform the tests.
	 */
	@BeforeAll
	public static void beforeAll() {
		bot = new SWT4diacGefBot();
		bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
		SWTBotPreferences.TIMEOUT = 10000;
		createProject();
	}

	/**
	 * Cleans the canvas from all objects.
	 */
	@SuppressWarnings("static-method")
	@AfterEach
	public void cleanEditorArea() {
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		viewer.getCanvas().setFocus();

		final SWTBotMenu editMenu = bot.menu(EDIT);
		editMenu.menu(SELECT_ALL).click();
		final SWTBotMenu deleteMenu = editMenu.menu(DELETE);
		if (deleteMenu.isEnabled()) {
			// not all Tests have a remaining FB
			deleteMenu.click();
		}
	}

	/**
	 * Deletes 4diac IDE project.
	 */
	@AfterAll
	public static void deleteProject() {
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);

		final SWTBotTreeItem treeItem = tree.getTreeItem(PROJECT_NAME);
		treeItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(DELETE_PROJECT_WARNING).select();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Resets the workbench after
	 */
	@AfterClass
	public static void afterClass() {
		bot.resetWorkbench();
	}

	// End of region for test settings
	// ----------------------------------------------------------------------------------------
	// Region of test methods

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
		dragAndDropEventsFB(E_N_TABLE_TREE_ITEM, new Point(200, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		assertNotNull(editor.getEditPart(E_N_TABLE_FB));
		editor.click(E_N_TABLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_N_TABLE_FB).parent();
		assertNotNull(parent);
		parent.doubleClick();

		UIThreadRunnable.syncExec(() -> {
			final IEditorPart editorPart = editor.getReference().getEditor(false);
			if (editorPart instanceof final AbstractBreadCrumbEditor breadCrumbEditor) {
				final String title = breadCrumbEditor.getBreadcrumb().getActiveItem().getText();
				assertEquals(E_N_TABLE_FB, title);
				assertTrue(breadCrumbEditor.getActiveEditor() instanceof CompositeInstanceViewer);
			}
		});
		bot.menu(NAVIGATE).menu(GO_TO_PARENT).click();
	}

	/**
	 * Checks if it is possible to move FB in CompositeInstanceViewer
	 */
	@Disabled
	public void compositeInstanceViewerMoveFB() {
		// in progress
	}

	/**
	 * Checks if it is possible to delete FB in CompositeInstanceViewer
	 */
	@Disabled
	public void compositeInstanceViewerDeleteFB() {
		// in progress
	}

	/**
	 * Checks if it is possible to edit name of FB in CompositeInstanceViewer
	 */
	@Disabled
	public void compositeInstanceViewerEditingOfFB() {
		// in progress
	}

	/**
	 * Checks if it is possible to add connections in CompositeInstanceViewer
	 */
	@Disabled
	public void compositeInstanceViewerConnectionCanBeAdded() {
		// in progress
	}

	/**
	 * Checks if it is possible to add another FB in CompositeInstanceViewer
	 */
	@Disabled
	public void compositeInstanceViewerAddAnotherFB() {
		// in progress
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
	 * Creates a new 4diac IDE project
	 *
	 * The method creates a new 4diac IDE project with the static String of
	 * PROJECT_NAME and is called from {@link #beforeAll() method beforeAll}.
	 */
	private static void createProject() {
		bot.menu(FILE).menu(NEW).menu(FORDIAC_IDE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(PROJECT_NAME_LABEL).setText(PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_SYSTEM_NAME_LABEL).getText(), PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), PROJECT_NAME + APP);
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
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

	/**
	 * Checks if there is a ConnectionEditPart in the editPartRegistry. Throws an
	 * exception if no such part can be found after 1 second.
	 *
	 * @param editPartRegistry Map with all registered editParts
	 * @throws Exception When no ConnectionEditPart can be found in the map of the
	 *                   {@link EditPartViewer#getEditPartRegistry
	 *                   EditPartRegistry}.
	 */
	private static void waitUntilCondition(final Map<?, ?> editPartRegistry) {
		bot.waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				return editPartRegistry.values().stream().filter(v -> v instanceof ConnectionEditPart).count() == 1;
			}

			@Override
			public void init(final SWTBot bot) {
				// method must be implemented but empty since not needed
			}

			@Override
			public String getFailureMessage() {
				return "no ConnectionEditPart found";
			}

		}, 1000);
	}

}
