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
 *                - moved methods related to SystemExplorer from class
 *                  Abstract4diacUITests here due to architecture redesign and
 *                  created constructor and fields.
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class SWTBotSystemExplorer {

	private final SWT4diacGefBot bot;
	private final SWTBotView systemExplorerView;
	private final SWTBotTree tree;

	/**
	 * Creates an instance of {@code SWTBotSystemExplorer} for interacting with the
	 * System Explorer view in the 4diac IDE.
	 *
	 * This constructor initializes the System Explorer view and sets up access to
	 * the tree structure within the view. It also ensures that the System Explorer
	 * view is visible.
	 *
	 * @param bot the {@code SWT4diacGefBot} instance used for interacting with the
	 *            4diac IDEs
	 * @throws AssertionError if the tree widget within the System Explorer view
	 *                        cannot be found
	 */
	public SWTBotSystemExplorer(final SWT4diacGefBot bot) {
		this.bot = bot;
		this.systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		this.systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		this.tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
	}

	/**
	 * Checks if given element is visible in the Application of the System in the
	 * SystemExplorer Tree.
	 *
	 * @author Andrea Zoitl
	 * @param element The name of the element (function block or SubApp) searched
	 *                for in the Application of the System in the SystemExplorer
	 *                tree.
	 * @return {@code true} if node is visible in tree; {@code false} otherwise
	 */
	public boolean isElementInApplicationOfSystemInSystemExplorer(final String element) {
		final SWTBotTreeItem appNode = expandApplicationTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(appNode, element));
		final SWTBotTreeItem elementNode = appNode.getNode(element);
		assertNotNull(elementNode);
		return elementNode.isVisible();
	}

	/**
	 * Checks if given Function Block is visible in the SubApp node of the
	 * Application the System in the SystemExplorer Tree.
	 *
	 * @author Andrea Zoitl
	 * @param fbName The name of the function block searched for in the SubApp node
	 *               of the Application in the System in the SystemExplorer tree.,
	 * @return {@code true} if FB is visible in tree; {@code false} otherwise
	 */
	public boolean isFBInSubAppOfSystemInSystemExplorer(final String fbName) {
		final SWTBotTreeItem subAppNode = expandSubAppTreeItemInSystemExplorer();
		final SWTBotTreeItem fbNode = subAppNode.getNode(fbName);
		assertNotNull(fbNode);
		return fbNode.isVisible();
	}

	/**
	 * Checks if Subappliacation has no children.
	 *
	 * The method checks whether the subapplication node in the SystemExplorer has
	 * no children.
	 *
	 * @author Andrea Zoitl
	 * @return {@code true} if node has no children; {@code false} otherwise
	 */
	public boolean isSubAppNodeInSystemExplorerEmpty() {
		final SWTBotTreeItem subAppNode = expandSubAppTreeItemInSystemExplorer();
		final List<String> subAppChildren = subAppNode.getNodes();
		return subAppChildren.isEmpty();
	}

	/**
	 * Expands the 4diac IDE Project node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return treeProjectItem The expanded Project node
	 */
	private SWTBotTreeItem expandProjectTreeItemInSystemExplorer() {
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		return treeProjectItem.select().expand();

	}

	/**
	 * Expands the System node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return The expanded {@code SWTBotTreeItem} representing the system node in
	 *         the System Explorer
	 */
	private SWTBotTreeItem expandSystemTreeItemInSystemExplorer() {
		final SWTBotTreeItem treeProjectItem = expandProjectTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(treeProjectItem, UITestNamesHelper.PROJECT_NAME_TREE_ITEM));
		final SWTBotTreeItem systemNode = treeProjectItem.getNode(UITestNamesHelper.PROJECT_NAME_TREE_ITEM);
		return systemNode.select().expand();
	}

	/**
	 * Expands the Type Library node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return The expanded {@code SWTBotTreeItem} representing the Type Library
	 *         node in the System Explorer
	 */
	public SWTBotTreeItem expandTypeLibraryTreeItemInSystemExplorer() {
		final SWTBotTreeItem treeProjectItem = expandProjectTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(treeProjectItem, UITestNamesHelper.TYPE_LIBRARY_NODE));
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(UITestNamesHelper.TYPE_LIBRARY_NODE);
		return typeLibraryNode.select().expand();
	}

	/**
	 * Expands the Application node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return The expanded {@code SWTBotTreeItem} representing the Application node
	 *         in the System Explorer
	 */
	public SWTBotTreeItem expandApplicationTreeItemInSystemExplorer() {
		final SWTBotTreeItem systemNode = expandSystemTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(systemNode, UITestNamesHelper.PROJECT_NAME_APP));
		final SWTBotTreeItem appNode = systemNode.getNode(UITestNamesHelper.PROJECT_NAME_APP);
		assertNotNull(appNode);
		return appNode.select().expand();
	}

	/**
	 * Expands the Subapplication node (SWTBotTreeItem) of the 1. level of an
	 * Application in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return The expanded {@code SWTBotTreeItem} representing the Subapplication
	 *         (SubApp) node in the System Explorer
	 */
	private SWTBotTreeItem expandSubAppTreeItemInSystemExplorer() {
		final SWTBotTreeItem appNode = expandApplicationTreeItemInSystemExplorer();
		final SWTBotTreeItem subAppNode = appNode.getNode(UITestNamesHelper.SUBAPP);
		assertNotNull(subAppNode);
		return subAppNode.select().expand();
	}

}
