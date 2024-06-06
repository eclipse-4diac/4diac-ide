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
package org.eclipse.fordiac.ide.test.ui.CreateFBTTest;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CreateFBTTest {

	private static final String NEW = "New"; //$NON-NLS-1$
	private static final String FILE = "File"; //$NON-NLS-1$
	private static final String CANCEL = "Cancel"; //$NON-NLS-1$
	private static final String OK = "OK"; //$NON-NLS-1$
	private static final String FINISH = "Finish"; //$NON-NLS-1$
	private static final String APP = "App"; //$NON-NLS-1$
	private static final String EDIT = "Edit"; //$NON-NLS-1$
	private static final String DELETE = "Delete"; //$NON-NLS-1$
	private static final String DELETE_PROJECT_WARNING = "Delete project contents on disk (cannot be undone)"; //$NON-NLS-1$
	private static final String DELETE_RESOURCES = "Delete Resources"; //$NON-NLS-1$
	private static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	private static final String TEMPLATE_TABLE_ID = "org.eclipse.fordiac.ide.typemanagement.templateTableViewer"; //$NON-NLS-1$
	private static final String SYSTEM_EXPLORER_LABEL = "System Explorer"; //$NON-NLS-1$
	private static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	private static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	private static final String TEST_PARENT_FOLDER = "TestParentProject"; //$NON-NLS-1$
	private static final String UI_TEST_PROJECT1 = "UiTestProject1"; //$NON-NLS-1$
	private static final String UI_TEST_PROJECT2 = "UiTestProject2"; //$NON-NLS-1$
	private static final String PARENT_FOLDER_NAME_LABEL = "Enter or select the parent folder:"; //$NON-NLS-1$
	private static final String TYPE_NAME_LABEL = "Type Name:"; //$NON-NLS-1$
	private static final String TYPE_NAVIGATOR_LABEL = "Type Navigator"; //$NON-NLS-1$
	private static final String PACKAGE_NAME_LABEL = "Package:"; //$NON-NLS-1$
	private static final String SELECT_TYPE_LABEL = "Select Type:"; //$NON-NLS-1$
	private static final String NEW_TYPE = "New Type"; //$NON-NLS-1$
	private static final String TYPE_PROJECT = "Type..."; //$NON-NLS-1$
	private static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$
	private static final String TEST_TEMPLATE_NAME = "Adapter"; //$NON-NLS-1$
	private static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	private static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();

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
		bot = new SWTWorkbenchBot();
		bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
		// increase timeout to 10 seconds
		SWTBotPreferences.TIMEOUT = 10000;
	}

	@AfterClass
	public static void afterClass() {
		bot.resetWorkbench();
	}

	// End of region for test settings
	// ----------------------------------------------------------------------------------------
	// Region of test methods

	/**
	 * Checks if the menu "Type.." exists and if the buttons are enabled correctly.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void menuNewTypeExists() {
		final SWTBotMenu fileMenu = bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());
		bot.button(CANCEL).click();
	}

	/**
	 * Checks if a New Type can be created and is visible in the System Explorer
	 *
	 * The method checks if you can create a new Type via the menu, if the buttons
	 * are enabled correctly, if you can assign a select a parent folder, enter type
	 * name, packages and can select type template and at each stage check the state
	 * of buttons and the UI responses. Then it checks if the newly created type can
	 * be found in the hierarchy tree of the system explorer. Afterwards the new FB
	 * type will be deleted.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createNewFBType() {
		createProject(TEST_PARENT_FOLDER);
		final SWTBotMenu fileMenu = bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

//		final SWTBotTable parentFolderList = bot.table();
//		if (parentFolderList.rowCount() > 0) {
//			// Parent Folder:
//			final SWTBotTableItem selectedItem = parentFolderList.getTableItem(TEST_PARENT_FOLDER);
//			selectedItem.select();
//
//			assertTrue(selectedItem.isEnabled());
//			assertEquals(selectedItem.getText(), bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).getText());
//		}

//		assertEquals(PARENT_FOLDER_NAME_LABEL, TEST_PARENT_FOLDER);

		bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).setText(TEST_PARENT_FOLDER);

		// Type Name:
		bot.textWithLabel(TYPE_NAME_LABEL).setText(UI_TEST_PROJECT1);
		assertFalse(bot.button(FINISH).isEnabled());

		// Select Type:
//		bot.tableWithLabel(SELECT_TYPE_LABEL).getTableItem("Adapter").select();
		final SWTBotTable templateTable = bot.tableWithId(TEMPLATE_TABLE_ID);
		final SWTBotTableItem item = templateTable.getTableItem(0);
		item.select();
//		final String expectedTypeNameItem = "Adapter";
//		final String expectedTypeDescriptionItem = " Adapter Interface";

//		assertEquals(bot.tableWithLabel(SELECT_TYPE_LABEL), expectedTypeNameItem);

		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), UI_TEST_PROJECT1);
//		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), UI_TEST_PROJECT1 + APP);
		assertTrue(bot.button(CANCEL).isEnabled());
		assertTrue(bot.button(FINISH).isEnabled());

		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		SWTBotTreeItem parentItem = tree.getTreeItem(TEST_PARENT_FOLDER);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(UI_TEST_PROJECT1);
		assertEquals(projectItem.getText(), UI_TEST_PROJECT1);

		final SWTBotView typeNavigatorView = bot.viewByTitle(TYPE_NAVIGATOR_LABEL);
		typeNavigatorView.show();
		final Composite typeNavigatorComposite = (Composite) typeNavigatorView.getWidget();
		swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), typeNavigatorComposite);
		final SWTBotTree typeNavigatorTree = new SWTBotTree(swtTree);
		parentItem = typeNavigatorTree.getTreeItem(TEST_PARENT_FOLDER);
		parentItem.expand();
//		projectItem = parentItem.getNode(UI_TEST_PROJECT1);

		assertEquals(projectItem.getText(), UI_TEST_PROJECT1);

		deleteProject(TEST_PARENT_FOLDER);
	}

	/**
	 * Checks if a new FB Type can be created with an existing project name
	 *
	 * The method tries to create a New Type with an already existing name. This
	 * should not be possible and the Finish button should be not enabled.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToCreateANewFBTypeWithExistingName() {
		createFBType(UI_TEST_PROJECT1);
		bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		bot.textWithLabel(TYPE_NAME_LABEL).setText(UI_TEST_PROJECT1);
		bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).setText(TEST_PARENT_FOLDER);
		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), UI_TEST_PROJECT1);
		bot.tableWithLabel(SELECT_TYPE_LABEL).getTableItem(TEST_TEMPLATE_NAME).select();
		assertFalse(bot.button(FINISH).isEnabled());
		bot.button(CANCEL).click();
		bot.waitUntil(shellCloses(shell));
		deleteProject(TEST_PARENT_FOLDER);
	}

	/**
	 * Checks if an existing FB Type can be deleted
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFBType() {
		createFBType(UI_TEST_PROJECT2);
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(TEST_PARENT_FOLDER);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(UI_TEST_PROJECT2);
		projectItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(UI_TEST_PROJECT2));
		deleteProject(TEST_PARENT_FOLDER);
	}

	// End of region for test methods
	// ----------------------------------------------------------------------------------------
	// Region of utility methods

	/**
	 * Creates a New Type with given name.
	 *
	 * @param typeName Name of the new type.
	 */
	private static void createFBType(final String typeName) {
		createProject(TEST_PARENT_FOLDER);
		bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		bot.textWithLabel(TYPE_NAME_LABEL).setText(typeName);
		bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).setText(TEST_PARENT_FOLDER);
		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), typeName);
		bot.tableWithLabel(SELECT_TYPE_LABEL).getTableItem(TEST_TEMPLATE_NAME).select();
//		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), typeName + APP);
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Creates a new 4diac IDE project with given name.
	 *
	 * @param projectName Name of the new project.
	 */
	private static void createProject(final String projectName) {
		bot.menu(FILE).menu(NEW).menu(FORDIAC_IDE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(PROJECT_NAME_LABEL).setText(projectName);
		assertEquals(bot.textWithLabel(INITIAL_SYSTEM_NAME_LABEL).getText(), projectName);
		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), projectName + APP);
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes the FB Type project with given name
	 *
	 * @param parentNodeName Name of the parent folder
	 * @param projectName    Name of the project to be deleted
	 */
	private static void deleteFBType(final String parentNodeName, final String projectName) {
		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(parentNodeName);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(projectName);
		projectItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
//		bot.checkBox(DELETE_PROJECT_WARNING).select();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes the 4diac project with given name
	 *
	 * @param projectName Name of the project to be deleted
	 */
	private static void deleteProject(final String projectName) {
		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeItem = tree.getTreeItem(projectName);
		treeItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(DELETE_PROJECT_WARNING).select();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
	}

}