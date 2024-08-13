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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.PropertySheetHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;

public class ConstantsTabTests extends NatTableWithoutEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = INTERNALCONSTVAR1;
		TESTVAR2 = INTERNALCONSTVAR2;
		TESTVAR3 = INTERNALCONSTVAR3;
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT2, TEMPLATEBASIC);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(CONSTANTS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(FBT_TEST_PROJECT2).show();
		PropertySheetHelper.selectPropertyTabItem(CONSTANTS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		natTableBot = new SWTBot4diacNatTable(natTable);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
	}
}
