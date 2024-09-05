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

import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VarInAndOutputsTabTests extends NatTableWithEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = UITestPinHelper.DI1;
		TESTVAR2 = UITestPinHelper.DI2;
		TESTVAR3 = UITestPinHelper.DI3;
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.ADAPTER);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.VAR_IN_AND_OUTPUTS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.VAR_IN_AND_OUTPUTS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		natTableBot = new SWTBot4diacNatTable(natTable);
		editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.FBT_TEST_PROJECT2);
		assertNotNull(editor);
		SWTBotNatTable.createNewVariableInDataTypeEditor(natTableBot);
	}

	/**
	 * Verifies the ability to change the initial value of a variable.
	 *
	 * This test checks whether the initial value of a variable can be changed, and
	 * asserts that the new value is correctly assigned.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeInitialValueOfVariable() {
		SWTBotNatTable.changeCellValueInNatTbale(natTableBot, UITestNamesHelper.TRUE, 1, 4);
		SWTBotNatTable.changeCellValueInNatTbale(natTableBot, "1", 1, 4); //$NON-NLS-1$
	}

	/**
	 * Tests the behavior when an invalid initial value is assigned to a variable.
	 *
	 * This test attempts to assign an invalid initial value to a variable and
	 * verifies whether the assignment is successful and what impact it has on the
	 * editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInValidInitialValue() {
		SWTBotNatTable.setInvalidDataType(natTableBot, 1, 4, UITestNamesHelper.TESTVAR);
	}

	/**
	 * Toggles the configuration setting of a variable.
	 *
	 * This test verifies the toggling behavior of a variable's configuration
	 * setting, ensuring that the value switches between "true" and "false".
	 */
	@SuppressWarnings("static-method")
	@Test
	public void toggleVarConfiguration() {
		assertEquals(natTableBot.getCellDataValueByPosition(2, 6), UITestNamesHelper.FALSE_SMALL);
		natTableBot.click(2, 6);
		assertEquals(natTableBot.getCellDataValueByPosition(2, 6), UITestNamesHelper.TRUE_SMALL);
	}

	/**
	 * Toggles the visibility setting of a variable.
	 *
	 * This test checks the toggling behavior of a variable's visibility setting,
	 * confirming that the value alternates between "true" and "false".
	 */
	@SuppressWarnings("static-method")
	@Test
	public void toggleVarVisibility() {
		assertEquals(natTableBot.getCellDataValueByPosition(2, 5), UITestNamesHelper.TRUE_SMALL);
		natTableBot.click(2, 5);
		assertEquals(natTableBot.getCellDataValueByPosition(2, 5), UITestNamesHelper.FALSE_SMALL);
	}

	/**
	 * Changes the retain tag of a variable.
	 *
	 * This test verifies that the retain tag of a variable can be changed
	 * successfully and checks that the new retain tag value is correctly set in the
	 * NatTable.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeRetainTag() {
		SWTBotNatTable.setRetainValue(natTableBot, 1, 7, RetainTag.RETAIN);
	}
}
