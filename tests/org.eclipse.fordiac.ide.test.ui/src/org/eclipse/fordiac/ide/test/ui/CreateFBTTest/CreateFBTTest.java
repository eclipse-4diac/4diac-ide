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

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.jupiter.api.Test;

public class CreateFBTTest extends Abstract4diacUITests {

	/**
	 * Checks if a New Type can be created and is visible in the System Explorer
	 * With Empty Package Name
	 *
	 * The method checks if you can create a new Type via the menu, if the buttons
	 * are enabled correctly, if you can assign a select a parent folder, enter type
	 * name, packages and can select type template and at each stage check the state
	 * of buttons and the UI responses. Then it checks if the newly created type can
	 * be found in the hierarchy tree of the system explorer.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createNewFBTypeWithoutPackage() {
		final SWTBotMenu fileMenu = bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(PROJECT_NAME);
		containerItem.select();

		assertEquals(PROJECT_NAME, bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(TYPE_NAME_LABEL).setText(FBT_TEST_PROJECT1);
		assertFalse(bot.button(FINISH).isEnabled());

		// Select Type:
		bot.table().select(TEST_TYPE_TEMPLATE_NAME);

		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), FBT_TEST_PROJECT1);
		assertTrue(bot.button(CANCEL).isEnabled());
		assertTrue(bot.button(FINISH).isEnabled());

		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(FBT_TEST_PROJECT1);
		assertEquals(projectItem.getText(), FBT_TEST_PROJECT1);
		deleteFBType(FBT_TEST_PROJECT1);
	}

	/**
	 * Checks if a New Type can be created and is visible in the System Explorer
	 * With Package Name
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
	public void createNewFBTypeWithPackage() {
		final SWTBotMenu fileMenu = bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

		// Parent folder Name:
		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(PROJECT_NAME);
		containerItem.select();

		assertEquals(PROJECT_NAME, bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(TYPE_NAME_LABEL).setText(FBT_TEST_PROJECT2);
		assertFalse(bot.button(FINISH).isEnabled());

		// Package:
		bot.textWithLabel(PACKAGE_NAME_LABEL).setText(PACKAGE_NAME);
		assertFalse(bot.button(FINISH).isEnabled());

		// Select Type:
		bot.table().select(TEST_TYPE_TEMPLATE_NAME);

		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), FBT_TEST_PROJECT2);
		assertTrue(bot.button(CANCEL).isEnabled());
		assertTrue(bot.button(FINISH).isEnabled());

		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(FBT_TEST_PROJECT2);
		assertEquals(projectItem.getText(), FBT_TEST_PROJECT2);
		deleteFBType(FBT_TEST_PROJECT2);
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
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT3);
		bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		bot.textWithLabel(TYPE_NAME_LABEL).setText(FBT_TEST_PROJECT3);
		bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).setText(PROJECT_NAME);
		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), FBT_TEST_PROJECT3);
		bot.tableWithLabel(SELECT_TYPE_LABEL).getTableItem(TEST_TYPE_TEMPLATE_NAME).select();
		assertFalse(bot.button(FINISH).isEnabled());
		bot.button(CANCEL).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Checks if an existing FB Type can be deleted
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFBType() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT4);
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(FBT_TEST_PROJECT4);
		projectItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(FBT_TEST_PROJECT4));
	}

}