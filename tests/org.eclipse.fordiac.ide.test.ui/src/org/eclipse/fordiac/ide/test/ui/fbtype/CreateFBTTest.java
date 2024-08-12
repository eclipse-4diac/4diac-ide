/*******************************************************************************
 * Copyright (c) 2024 Prashantkumar Khatri, Andrea Zoitl
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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.StringNamesHelper;
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
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(StringNamesHelper.PROJECT_NAME,
				bot.textWithLabel(StringNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).setText(StringNamesHelper.FBT_TEST_PROJECT1);
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(StringNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).getText(),
				StringNamesHelper.FBT_TEST_PROJECT1);
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertTrue(bot.button(StringNamesHelper.FINISH).isEnabled());

		bot.button(StringNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(StringNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(StringNamesHelper.FBT_TEST_PROJECT1);
		assertEquals(projectItem.getText(), StringNamesHelper.FBT_TEST_PROJECT1);
		deleteFBType(StringNamesHelper.FBT_TEST_PROJECT1);
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
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		// Parent folder Name:
		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(StringNamesHelper.PROJECT_NAME,
				bot.textWithLabel(StringNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).setText(StringNamesHelper.FBT_TEST_PROJECT2);
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		// Package:
		bot.textWithLabel(StringNamesHelper.PACKAGE_NAME_LABEL).setText(StringNamesHelper.PACKAGE_NAME);
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(StringNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).getText(),
				StringNamesHelper.FBT_TEST_PROJECT2);
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertTrue(bot.button(StringNamesHelper.FINISH).isEnabled());

		bot.button(StringNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(StringNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(StringNamesHelper.FBT_TEST_PROJECT2);
		assertEquals(projectItem.getText(), StringNamesHelper.FBT_TEST_PROJECT2);
		deleteFBType(StringNamesHelper.FBT_TEST_PROJECT2);
	}

	/**
	 * Checks if a new FB Type can be created with an forbidden type name
	 *
	 * The method tries to create a New Type with an forbidden type name. This
	 * should not be possible and the Finish button should be not enabled.
	 *
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToCreateFBTypeWithForbiddenName() {
		final SWTBotMenu fileMenu = bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW)
				.menu(StringNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(StringNamesHelper.PROJECT_NAME,
				bot.textWithLabel(StringNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).setText(StringNamesHelper.FORBIDDEN_TYPE_NAME);
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(StringNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).getText(),
				StringNamesHelper.FORBIDDEN_TYPE_NAME);
		assertTrue(bot.button(StringNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());

		bot.button(StringNamesHelper.CANCEL).click();
		bot.waitUntil(shellCloses(shell));
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
		createFBType(StringNamesHelper.PROJECT_NAME, StringNamesHelper.FBT_TEST_PROJECT3, StringNamesHelper.ADAPTER);
		bot.menu(StringNamesHelper.FILE).menu(StringNamesHelper.NEW).menu(StringNamesHelper.TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(StringNamesHelper.NEW_TYPE);
		shell.activate();
		bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).setText(StringNamesHelper.FBT_TEST_PROJECT3);
		bot.textWithLabel(StringNamesHelper.PARENT_FOLDER_NAME_LABEL).setText(StringNamesHelper.PROJECT_NAME);
		assertEquals(bot.textWithLabel(StringNamesHelper.TYPE_NAME_LABEL).getText(),
				StringNamesHelper.FBT_TEST_PROJECT3);
		bot.tableWithLabel(StringNamesHelper.SELECT_TYPE_LABEL).getTableItem(StringNamesHelper.ADAPTER).select();
		assertFalse(bot.button(StringNamesHelper.FINISH).isEnabled());
		bot.button(StringNamesHelper.CANCEL).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Checks if an existing FB Type can be deleted
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFBType() {
		createFBType(StringNamesHelper.PROJECT_NAME, StringNamesHelper.FBT_TEST_PROJECT4, StringNamesHelper.ADAPTER);
		final SWTBotView systemExplorerView = bot.viewById(StringNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(StringNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(StringNamesHelper.FBT_TEST_PROJECT4);
		projectItem.select();
		bot.menu(StringNamesHelper.EDIT).menu(StringNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(StringNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.button(StringNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(StringNamesHelper.FBT_TEST_PROJECT4));
	}

}