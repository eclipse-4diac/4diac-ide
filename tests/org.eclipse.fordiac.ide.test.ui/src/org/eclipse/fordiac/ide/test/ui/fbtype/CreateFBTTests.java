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
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.jupiter.api.Test;

public class CreateFBTTests extends Abstract4diacUITests {

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
		final SWTBotMenu fileMenu = bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW)
				.menu(UITestNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(UITestNamesHelper.PROJECT_NAME,
				bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(UITestNamesHelper.FBT_TEST_PROJECT1);
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(UITestNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(),
				UITestNamesHelper.FBT_TEST_PROJECT1);
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertTrue(bot.button(UITestNamesHelper.FINISH).isEnabled());

		bot.button(UITestNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(UITestNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(UITestNamesHelper.FBT_TEST_PROJECT1);
		assertEquals(projectItem.getText(), UITestNamesHelper.FBT_TEST_PROJECT1);
		new SWTBotFBType(bot).deleteFBType(UITestNamesHelper.FBT_TEST_PROJECT1);
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
		final SWTBotMenu fileMenu = bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW)
				.menu(UITestNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		// Parent folder Name:
		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(UITestNamesHelper.PROJECT_NAME,
				bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(UITestNamesHelper.FBT_TEST_PROJECT2);
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		// Package:
		bot.textWithLabel(UITestNamesHelper.PACKAGE_NAME_LABEL).setText(UITestNamesHelper.PACKAGE_NAME);
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(UITestNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(),
				UITestNamesHelper.FBT_TEST_PROJECT2);
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertTrue(bot.button(UITestNamesHelper.FINISH).isEnabled());

		bot.button(UITestNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));

		final SWTBotView systemExplorerView = bot.viewByTitle(UITestNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(UITestNamesHelper.FBT_TEST_PROJECT2);
		assertEquals(projectItem.getText(), UITestNamesHelper.FBT_TEST_PROJECT2);
		new SWTBotFBType(bot).deleteFBType(UITestNamesHelper.FBT_TEST_PROJECT2);
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
		final SWTBotMenu fileMenu = bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW)
				.menu(UITestNamesHelper.TYPE_PROJECT).click();
		assertNotNull(fileMenu);
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		containerItem.select();

		assertEquals(UITestNamesHelper.PROJECT_NAME,
				bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).getText());

		// Type Name:
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(UITestNamesHelper.FORBIDDEN_TYPE_NAME);
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		// Select Type:
		bot.table().select(UITestNamesHelper.ADAPTER);

		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(),
				UITestNamesHelper.FORBIDDEN_TYPE_NAME);
		assertTrue(bot.button(UITestNamesHelper.CANCEL).isEnabled());
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());

		bot.button(UITestNamesHelper.CANCEL).click();
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
		new SWTBotFBType(bot).createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT3,
				UITestNamesHelper.ADAPTER);
		bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW).menu(UITestNamesHelper.TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(UITestNamesHelper.FBT_TEST_PROJECT3);
		bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).setText(UITestNamesHelper.PROJECT_NAME);
		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(),
				UITestNamesHelper.FBT_TEST_PROJECT3);
		bot.tableWithLabel(UITestNamesHelper.SELECT_TYPE_LABEL).getTableItem(UITestNamesHelper.ADAPTER).select();
		assertFalse(bot.button(UITestNamesHelper.FINISH).isEnabled());
		bot.button(UITestNamesHelper.CANCEL).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Checks if an existing FB Type can be deleted
	 */
	@SuppressWarnings("static-method")
	@Test
	public void deleteExistingFBType() {
		new SWTBotFBType(bot).createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT4,
				UITestNamesHelper.ADAPTER);
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(UITestNamesHelper.FBT_TEST_PROJECT4);
		projectItem.select();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(UITestNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(UITestNamesHelper.FBT_TEST_PROJECT4));
	}

}