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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;

public class ConstantsTabTests extends NatTableWithoutEditorBehaviorTests {

	public ConstantsTabTests() {
		super(UITestNamesHelper.INTERNALCONSTVAR1, UITestNamesHelper.INTERNALCONSTVAR2);
	}

	@Override
	@BeforeEach
	public void operationsInitialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.TEMPLATEBASIC);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.CONSTANTS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.CONSTANTS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
	}
}
