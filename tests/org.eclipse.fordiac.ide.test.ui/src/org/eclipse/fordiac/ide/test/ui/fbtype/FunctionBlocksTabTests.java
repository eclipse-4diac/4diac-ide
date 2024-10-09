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
 *   Andrea Zoitl - Creation of a fluid API design for UI SWTBot testing
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.fbtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
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

	public FunctionBlocksTabTests() {
		super(UITestNamesHelper.INTERNALFB1, UITestNamesHelper.INTERNALFB2);
	}

	@Override
	@BeforeEach
	public void operationsInitialization() {
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
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
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
		new SWTBotNatTable(bot, swt4diacNatTable).changeDataType(1, UITestNamesHelper.INT_SMALL);
		swt4diacNatTable.doubleclick(1, 2);

		bot.button(UITestNamesHelper.DOT_BUTTON).click();

		final SWTBotShell shell = bot.shell(UITestNamesHelper.FB_TYPES);
		shell.activate();

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(UITestNamesHelper.TYPE_LIBRARY_NODE);
		containerItem.getNode(UITestNamesHelper.CONVERT).getNode(UITestNamesHelper.BOOL2BOOL).select();

		bot.button(UITestNamesHelper.OK).click();

		assertEquals(swt4diacNatTable.getCellDataValueByPosition(1, 2), UITestNamesHelper.BOOL2BOOL);
	}

	@Override
	public void tryToSetInValidDataType() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 2, UITestNamesHelper.TESTVAR);
	}

}
