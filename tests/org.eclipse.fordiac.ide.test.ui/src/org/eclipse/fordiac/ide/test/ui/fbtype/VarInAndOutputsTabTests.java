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

import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestPinHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VarInAndOutputsTabTests extends NatTableWithEditorBehaviorTests {

	public VarInAndOutputsTabTests() {
		super(UITestPinHelper.DI1, UITestPinHelper.DI2);
	}

	@Override
	@BeforeEach
	public void operationsInitialization() {
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
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		editor = bot.gefEditor(UITestNamesHelper.FBT_TEST_PROJECT2);
		assertNotNull(editor);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
	}

	/**
	 * Verifies the ability to change the initial value of a variable.
	 *
	 * This test checks whether the initial value of a variable can be changed, and
	 * asserts that the new value is correctly assigned.
	 */
	@Test
	public void changeInitialValueOfVariable() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.changeCellValueInNatTbale(UITestNamesHelper.TRUE, 1, 4);
		swtBotNatTable.changeCellValueInNatTbale("1", 1, 4); //$NON-NLS-1$
	}

	/**
	 * Tests the behavior when an invalid initial value is assigned to a variable.
	 *
	 * This test attempts to assign an invalid initial value to a variable and
	 * verifies whether the assignment is successful and what impact it has on the
	 * editor.
	 */
	@Test
	public void tryToSetInValidInitialValue() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 4, UITestNamesHelper.TESTVAR);
	}

	/**
	 * Toggles the configuration setting of a variable.
	 *
	 * This test verifies the toggling behavior of a variable's configuration
	 * setting, ensuring that the value switches between "true" and "false".
	 */
	@Test
	public void toggleVarConfiguration() {
		assertEquals(swt4diacNatTable.getCellDataValueByPosition(2, 6), UITestNamesHelper.FALSE_SMALL);
		swt4diacNatTable.click(2, 6);
		assertEquals(swt4diacNatTable.getCellDataValueByPosition(2, 6), UITestNamesHelper.TRUE_SMALL);
	}

	/**
	 * Toggles the visibility setting of a variable.
	 *
	 * This test checks the toggling behavior of a variable's visibility setting,
	 * confirming that the value alternates between "true" and "false".
	 */
	@Test
	public void toggleVarVisibility() {
		assertEquals(swt4diacNatTable.getCellDataValueByPosition(2, 5), UITestNamesHelper.TRUE_SMALL);
		swt4diacNatTable.click(2, 5);
		assertEquals(swt4diacNatTable.getCellDataValueByPosition(2, 5), UITestNamesHelper.FALSE_SMALL);
	}

	/**
	 * Changes the retain tag of a variable.
	 *
	 * This test verifies that the retain tag of a variable can be changed
	 * successfully and checks that the new retain tag value is correctly set in the
	 * NatTable.
	 */
	@Test
	public void changeRetainTag() {
		new SWTBotNatTable(bot, swt4diacNatTable).setRetainValue(1, 7, RetainTag.RETAIN);
	}
}
