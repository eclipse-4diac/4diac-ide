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
 *
 *   Prashantkumar Khatri - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.fbtype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

public class BasicFBTOperationTest extends Abstract4diacUITests {

	/**
	 * Sample test for interaction tabs and property sheet
	 */
	@SuppressWarnings("static-method")
	@Test
	public void interactionOprationSampleTest() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT1);

		final SWTBotView systemExplorerView = bot.viewByTitle(SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(FBT_TEST_PROJECT1);
		assertEquals(projectItem.getText(), FBT_TEST_PROJECT1);

		// Interface tab
		final SWTBotCTabItem interfaceTab = bot.cTabItem(INTERFACE);
		interfaceTab.activate();

		// Properties tab access
		final SWTBot propertiesBot = bot.viewByTitle(PROPERTIES_TITLE).bot();
		bot.viewByTitle(PROPERTIES_TITLE).setFocus();

		// Tabs access inside property sheet
		selectPropertyTabItem(VAR_INOUT, propertiesBot);
		selectPropertyTabItem("Type Info", propertiesBot); //$NON-NLS-1$

		// Classification text input
		bot.textWithLabel("Classification:").setText("Test classification");
		// Application Domain text input
		bot.textWithLabel("Application Domain:").setText("Test Domain");

		assertEquals(bot.textWithLabel("Classification:").getText(), "Test classification");
		assertEquals(bot.textWithLabel("Application Domain:").getText(), "Test Domain");

	}

}
