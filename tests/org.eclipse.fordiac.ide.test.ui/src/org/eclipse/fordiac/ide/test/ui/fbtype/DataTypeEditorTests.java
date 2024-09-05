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

import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.junit.jupiter.api.BeforeEach;

public class DataTypeEditorTests extends NatTableWithoutEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = UITestNamesHelper.VAR1;
		TESTVAR2 = UITestNamesHelper.VAR2;
		TESTVAR3 = UITestNamesHelper.VAR3;

		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.STRUCT);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);

		final Composite tableComposite = (Composite) bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).getWidget();
		natTable = bot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), tableComposite);
		natTableBot = new SWTBot4diacNatTable(natTable);
		SWTBotNatTable.createNewVariableInDataTypeEditor(natTableBot);
	}
}