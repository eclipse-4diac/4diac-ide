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
package org.eclipse.fordiac.ide.test.ui.fbtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.jupiter.api.BeforeEach;

public class FunctionBlocksTabTests extends NatTableWithoutEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = UITestNamesHelper.INTERNALFB1;
		TESTVAR2 = UITestNamesHelper.INTERNALFB2;
		TESTVAR3 = UITestNamesHelper.INTERNALFB3;
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.TEMPLATEBASIC);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.FUNCTIONAL__BLOCKS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.FUNCTIONAL__BLOCKS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		natTableBot = new SWTBot4diacNatTable(natTable);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
	}

	@Override
	public void changeInitialValueOfVariable() {
		// Empty method to prevent parent logic from running
	}

	@Override
	public void tryToSetInValidInitialValue() {
		// Empty method to prevent parent logic from running
	}

	@Override
	public void changeDataTypeOfVariable() {
		NatTableHelper.changeDataType(natTableBot, 1, UITestNamesHelper.INT_SMALL);
		natTableBot.doubleclick(1, 2);

		bot.button(UITestNamesHelper.DOT_BUTTON).click();

		final SWTBotShell shell = bot.shell(UITestNamesHelper.FB_TYPES);
		shell.activate();

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(UITestNamesHelper.TYPE_LIBRARY_NODE);
		containerItem.getNode(UITestNamesHelper.CONVERT).getNode(UITestNamesHelper.BOOL2BOOL).select();

		bot.button(UITestNamesHelper.OK).click();

		assertEquals(natTableBot.getCellDataValueByPosition(1, 2), UITestNamesHelper.BOOL2BOOL);
	}

	@Override
	public void tryToSetInValidDataType() {
		NatTableHelper.setInvalidDataType(natTableBot, 1, 2, UITestNamesHelper.TESTVAR);
	}

}
