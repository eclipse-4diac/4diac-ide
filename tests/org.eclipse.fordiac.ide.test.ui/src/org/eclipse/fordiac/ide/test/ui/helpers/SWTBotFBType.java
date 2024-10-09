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
 *   Andrea Zoitl         - moved methods that are related to Function Blocks from class
 *                          Abstract4diacUITests here due to architecture redesign and
 *                          created constructor and fields.
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class SWTBotFBType {

	private final SWT4diacGefBot bot;

	@SuppressWarnings("static-access")
	public SWTBotFBType(final SWT4diacGefBot bot) {
		this.bot = bot;
	}

	/**
	 * Creates a New Type with given name.
	 *
	 * @param parentName Name of the parent project.
	 * @param typeName   Name of the new type.
	 * @param typeLabel  Name of the Type Label.
	 */
	public void createFBType(final String parentName, final String typeName, final String typeLabel) {
		bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW).menu(UITestNamesHelper.TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(typeName);
		bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).setText(parentName);
		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(), typeName);
		bot.tableWithLabel(UITestNamesHelper.SELECT_TYPE_LABEL).getTableItem(typeLabel).select();
		bot.button(UITestNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes FB Type with give name.
	 *
	 * @param typeName Name of the new type.
	 */
	public void deleteFBType(final String typeName) {
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		parentItem.expand();
		SWTBotTreeItem projectItem = null;
		for (final SWTBotTreeItem item : parentItem.getItems()) {
			if (item.getText().contains(typeName)) {
				projectItem = item;
				break;
			}
		}
		assertNotNull(projectItem);
		projectItem.select();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(UITestNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(typeName));
	}

	/**
	 * Open FB Type in editor from explorer.
	 *
	 * @param parentName Name of the parent Project.
	 * @param typeName   Name of the new type.
	 */
	public void openFBTypeInEditor(final String parentName, final String typeName) {
		final SWTBotView systemExplorerView = bot.viewByTitle(UITestNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(parentName);
		parentItem.expand();
		SWTBotTreeItem projectItem = null;
		for (final SWTBotTreeItem item : parentItem.getItems()) {
			if (item.getText().contains(typeName)) {
				projectItem = item;
				break;
			}
		}
		assertNotNull(projectItem);
		assertTrue(projectItem.getText().contains(typeName));
	}

	/**
	 * Deletes the given pin on the FB opened the provided editor.
	 *
	 * @param editor  Selected Gef Editor
	 * @param pinName Name of pin.
	 */
	public static void deletePin(final SWTBot4diacGefEditor editor, final String pinName) {
		final SWTBotGefEditPart pin = editor.getEditPart(pinName);
		assertNotNull(pin);
		pin.click();
		editor.clickContextMenu(UITestNamesHelper.DELETE);
		assertNull(editor.getEditPart(pinName));
	}

}
