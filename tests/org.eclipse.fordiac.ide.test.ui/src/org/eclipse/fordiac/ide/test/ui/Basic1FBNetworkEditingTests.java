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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Basic1FBNetworkEditingTests {

	private static final String SELECT_ALL = "Select All"; //$NON-NLS-1$
	private static final String NEW = "New"; //$NON-NLS-1$
	private static final String FILE = "File"; //$NON-NLS-1$
	private static final String OK = "OK"; //$NON-NLS-1$
	private static final String FINISH = "Finish"; //$NON-NLS-1$
	private static final String APP = "App"; //$NON-NLS-1$
	private static final String EDIT = "Edit"; //$NON-NLS-1$
	private static final String DELETE = "Delete"; //$NON-NLS-1$
	private static final String DELETE_PROJECT_WARNING = "Delete project contents on disk (cannot be undone)"; //$NON-NLS-1$
	private static final String DELETE_RESOURCES = "Delete Resources"; //$NON-NLS-1$
	private static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	private static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	private static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	private static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$
	private static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	private static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	private static final String EVENTS_NODE = "events"; //$NON-NLS-1$
	private static final String TYPE_LIBRARY_NODE = "Type Library"; //$NON-NLS-1$
	private static final String E_CYCLE_FB = "E_CYCLE"; //$NON-NLS-1$
	private static final String E_CYCLE_TREE_ITEM = "E_CYCLE [Peroidic event generator]"; //$NON-NLS-1$
	private static SWT4diacGefBot bot;

	@BeforeAll
	public static void beforeAll() {
		bot = new SWT4diacGefBot();
		bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
		// increase timeout to 10 seconds
		SWTBotPreferences.TIMEOUT = 10000;
		createProject();
	}

	@SuppressWarnings("static-method")
	@Test
	public void dragAndDrop1FB() {
		// select FB E_CYCLE
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
		bot.waitUntil(treeItemHasNode(eventsNode, E_CYCLE_TREE_ITEM));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(E_CYCLE_TREE_ITEM);
		eCycleNode.select();
		eCycleNode.click();

		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();

		assertNotNull(canvas);
		final Point point = new Point(100, 100);
		eCycleNode.dragAndDrop(canvas, point);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
	}

	@SuppressWarnings("static-method")
	@Test
	public void isAddedFbInProjectAppNode() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(PROJECT_NAME);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem projectNode = treeProjectItem.getNode(PROJECT_NAME + " []"); //$NON-NLS-1$
		projectNode.select();
		projectNode.expand();
		final SWTBotTreeItem appNode = projectNode.getNode(PROJECT_NAME + "App"); //$NON-NLS-1$
		appNode.select();
		appNode.expand();
		assertNotNull(appNode.getNodes());
		assertTrue(appNode.getNode(E_CYCLE_FB).isVisible());
	}

	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFB() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(300, 100));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		parent.click();
		bot.menu(EDIT).menu(DELETE).click();
		assertNull(editor.getEditPart(E_CYCLE_FB));
	}

	@SuppressWarnings("static-method")
	@Test
	public void moveFB() {
		dragAndDropEventsFB(E_CYCLE_TREE_ITEM, new Point(100, 300));
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
		editor.click(E_CYCLE_FB);
		final SWTBotGefEditPart parent = editor.getEditPart(E_CYCLE_FB).parent();
		parent.click();
		editor.drag(parent, 300, 300);
		assertNotNull(editor.getEditPart(E_CYCLE_FB));
	}

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

	@SuppressWarnings("static-method")
	@AfterEach
	public void cleanEditorArea() {
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		editor.getSWTBotGefViewer();
		editor.setFocus();
		bot.menu(EDIT).menu(SELECT_ALL);
		// not all Tests have a remaining FB
		if (bot.menu(EDIT).menu(DELETE).isEnabled()) {
			bot.menu(EDIT).menu(DELETE).click();
		}
	}

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

	@AfterClass
	public static void afterClass() {
		bot.resetWorkbench();
	}
}
