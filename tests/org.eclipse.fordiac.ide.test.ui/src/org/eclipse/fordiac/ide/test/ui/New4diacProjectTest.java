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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.helpers.StringNamesHelper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class New4diacProjectTest {

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
	 * Checks if the menu "4diac IDE Project" exists and if the buttons are enabled
	 * correctly.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void menuNew4diacProjectExists() {
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.FORDIAC_IDE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_4DIAC_PROJECT);
		shell.activate();
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());
		bot.button(StringNamesHelper.CANCEL).click();
	}

	/**
	 * Checks if a new 4diac IDE project can be created and is visible in the System
	 * Explorer
	 *
	 * The method checks if you can create a project via the menu, if the buttons
	 * are enabled correctly, if you can assign a project name and if the initial
	 * system name and initial application name are autofilled correctly. Then it
	 * checks if the project can be found in the hierarchy tree of the system
	 * explorer. Afterwards the project will be deleted.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createANew4diacIDEProject() {
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.FORDIAC_IDE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_4DIAC_PROJECT);
		shell.activate();
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());
		bot.textWithLabel(StringNamesHelper.PROJECT_NAME_LABEL).setText(StringNamesHelper.PROJECT_NAME1);
		assertEquals(bot.textWithLabel(StringNamesHelper.INITIAL_SYSTEM_NAME_LABEL).getText(),
				StringNamesHelper.PROJECT_NAME1);
		assertEquals(bot.textWithLabel(StringNamesHelper.INITIAL_APPLICATION_NAME_LABEL).getText(),
				StringNamesHelper.PROJECT_NAME1 + StringNamesHelper.APP);
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertTrue(bot.button(StringNamesHelper.FINISH).isEnabled());
		bot.button(StringNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(StringNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeItem = tree.getTreeItem(StringNamesHelper.PROJECT_NAME1);
		assertEquals(treeItem.getText(), StringNamesHelper.PROJECT_NAME1);

		deleteProject(StringNamesHelper.PROJECT_NAME1);
	}

	/**
	 * Checks if a new project can be created with an existing project name
	 *
	 * The method tries to create a project with an already existing name. This
	 * should not be possible and the Finish button should be not enabled.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToCreateANew4diacIDEProjectMenuWithExistingName() {
		createProject(StringNamesHelper.PROJECT_NAME2);
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.FORDIAC_IDE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(StringNamesHelper.PROJECT_NAME_LABEL).setText(StringNamesHelper.PROJECT_NAME2);
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());
		bot.button(StringNamesHelper.CANCEL).click();
	}

	/**
	 * Checks if an existing project can be deleted
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExisting4diacIDEProject() {
		createProject(StringNamesHelper.PROJECT_NAME3);
		final SWTBotView systemExplorerView = bot.viewById(StringNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(StringNamesHelper.PROJECT_NAME3);
		treeProjectItem.select();
		bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(StringNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(StringNamesHelper.DELETE_PROJECT_WARNING).select();
		bot.button(StringNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Checks if the project cannot be found in the hierarchy tree after deletion.
	 *
	 * The method creates several projects. One of them is deleted and it is checked
	 * whether it is no longer to be found in the hierarchy tree.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void isProjectNotInSystemExplorerAfterDeletion() {
		createProject(StringNamesHelper.PROJECT_NAME4);
		createProject(StringNamesHelper.PROJECT_NAME5);
		deleteProject(StringNamesHelper.PROJECT_NAME4);
		final SWTBotView systemExplorerView = bot.viewById(StringNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		assertEquals(1, tree.getAllItems().length);
		assertTrue(tree.getTreeItem(StringNamesHelper.PROJECT_NAME5).isVisible());
		deleteProject(StringNamesHelper.PROJECT_NAME5);
		assertEquals(0, tree.getAllItems().length);

	}

	// End of region for test methods
	// ----------------------------------------------------------------------------------------
	// Region of utility methods

	/**
	 * Creates a new 4diac IDE project with given name.
	 *
	 * @param projectName Name of the new project.
	 */
	private static void createProject(final String projectName) {
		bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW).menu(StringNamesHelper.FORDIAC_IDE_PROJECT)
				.click();
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(StringNamesHelper.PROJECT_NAME_LABEL).setText(projectName);
		assertEquals(bot.textWithLabel(StringNamesHelper.INITIAL_SYSTEM_NAME_LABEL).getText(), projectName);
		assertEquals(bot.textWithLabel(StringNamesHelper.INITIAL_APPLICATION_NAME_LABEL).getText(),
				projectName + StringNamesHelper.APP);
		bot.button(StringNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes the 4diac IDE project with given name
	 *
	 * @param projectName Name of the project to be deleted
	 */
	private static void deleteProject(final String projectName) {
		final SWTBotView systemExplorerView = bot.viewByTitle(StringNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeItem = tree.getTreeItem(projectName);
		treeItem.select();
		bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(StringNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(StringNamesHelper.DELETE_PROJECT_WARNING).select();
		bot.button(StringNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
	}

}