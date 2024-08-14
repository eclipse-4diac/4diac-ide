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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.PinNamesHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.StringNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataTypeEditorTests extends Abstract4diacUITests {

	public static SWTBot4diacNatTable dataTypeTableBot;
	public static NatTable natTable;

	/**
	 * Performs necessary tasks to make environment for testing the operations on
	 * DataType Editor Table.
	 *
	 * This method will run before each test and will create new FB Type each time
	 * and will confirm the visibility of the newly created type in system explorer
	 * and open the project in editor then will create a new variable in a Table.
	 */
	@SuppressWarnings("static-method")
	@BeforeEach
	public void operationsInitialization() {
		createFBType(StringNamesHelper.PROJECT_NAME, StringNamesHelper.FBT_TEST_PROJECT2, StringNamesHelper.STRUCT);
		openFBTypeInEditor(StringNamesHelper.PROJECT_NAME, StringNamesHelper.FBT_TEST_PROJECT2);
		final Composite inputComposite = (Composite) bot.editorByTitle(StringNamesHelper.FBT_TEST_PROJECT2).getWidget();
		natTable = bot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), inputComposite);
		dataTypeTableBot = new SWTBot4diacNatTable(natTable);
		createNewVariableInDataTypeEditor(dataTypeTableBot);
	}

	/**
	 * Clean the editor by deleting the created FB Type.
	 *
	 * This method will run after each test and will delete the FB type which was
	 * created in before start of the test so that we can perform the other
	 * operation from another test method, basically this will prevent the
	 * redundancy.
	 */
	@SuppressWarnings("static-method")
	@AfterEach
	public void resetEnvironment() {
		deleteFBType(StringNamesHelper.FBT_TEST_PROJECT2);
	}

	/**
	 * Rename the newly created variable
	 *
	 * The method will check if you can change the name of the variable which is
	 * already exist in the Table. It then checks the new name is assigned properly
	 * or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void renameVariable() {
		changeCellValueInNatTbale(dataTypeTableBot, StringNamesHelper.TESTVAR, 1, 1);
	}

	/**
	 * Change the Variable name with the button inside tool kit.
	 *
	 * The method will check if you can change the name of the variable which is
	 * already exist in the Table with the help of button on tool kit. It first
	 * click on Button with ToolTip "Rename Element" and then assure that shell ill
	 * open properly and then assigns the new name to the variable and then checks
	 * whether OK button is enabled or not, it then click on OK button and assure
	 * the shell close. Then it checks for new name is assigned properly or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeVariableNameWithButton() {
		dataTypeTableBot.click(1, 1);
		bot.toolbarButtonWithTooltip(StringNamesHelper.RENAME_ELEMENT).click();
		final SWTBotShell shell = bot.shell(StringNamesHelper.REFACTORING);
		assertNotNull(shell);
		shell.activate();
		assertFalse(bot.button(StringNamesHelper.OK).isEnabled());

		bot.textWithLabel(StringNamesHelper.NAME).setText(StringNamesHelper.TESTVAR);
		assertTrue(bot.button(StringNamesHelper.OK).isEnabled());

		bot.button(StringNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));

		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 1), StringNamesHelper.TESTVAR);

	}

	/**
	 * Checks whether you can give the existing name to the variable or not
	 *
	 * This method checks whether you can give existing name to the other variable
	 * or not. It first creates 2 new variables and then try to change the name of
	 * 2nd variable to same name as 1st variable has. Then check if the name is
	 * successfully changed or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToChangeNameOfVariableWithExistingName() {
		createNewVariableInDataTypeEditor(dataTypeTableBot);
		dataTypeTableBot.doubleclick(2, 1);
		dataTypeTableBot.setCellDataValueByPosition(2, 1, StringNamesHelper.VAR1);
		dataTypeTableBot.doubleclick(2, 1);

		// Name should not be changed
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(2, 1), StringNamesHelper.VAR2);
	}

	/**
	 * Checks whether we can give Invalid name(ex: "IF") to the variable or not.
	 *
	 * This method checks whether you can give invalid name to variable or not. It
	 * will try to change the name of the variable with assigning invalid name. Then
	 * check if the name is successfully changed or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInValidName() {
		dataTypeTableBot.doubleclick(1, 1);
		dataTypeTableBot.setCellDataValueByPosition(1, 1, StringNamesHelper.IF);
//		assertTrue(dataTypeTableBot.contextMenu().isVisible());
		dataTypeTableBot.doubleclick(1, 1);

		// Name should not be changed
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 1), StringNamesHelper.VAR1);
	}

	/**
	 * Checks the reorder operation of the variable.
	 *
	 * This method checks whether you can change the order of the variable or not.
	 * It first creates 3 new variable and then will try to change the order of the
	 * 3rd variable by bringing it it up and then will change the order of the first
	 * variable by bringing it down. Then check if the order of variables is as
	 * expected or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void reorderVariable() {
		createNewVariableInDataTypeEditor(dataTypeTableBot);
		createNewVariableInDataTypeEditor(dataTypeTableBot);
		dataTypeTableBot.click(3, 0);

		// Move the selected variable up
		bot.arrowButtonWithTooltip(StringNamesHelper.MOVE_ELEMENTS_UP).click();
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(2, 1), StringNamesHelper.VAR3);
		dataTypeTableBot.click(1, 0);

		// Move the selected variable down
		bot.arrowButtonWithTooltip(StringNamesHelper.MOVE_ELEMENTS_DOWN).click();

		// Checks the order of the Variables
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 1), StringNamesHelper.VAR3);
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(2, 1), StringNamesHelper.VAR1);
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(3, 1), StringNamesHelper.VAR2);
	}

	/**
	 * Checks whether you can change the data type of the variable or not.
	 *
	 * This method checks whether you can change the data type of the variable or
	 * not. It will first select the variable and then assigns data type to the
	 * selected variable. Then checks it's successfully changed or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeDataTypeOfVariable() {
		dataTypeTableBot.doubleclick(1, 2);

		// Here we have assigned the value "int"
		dataTypeTableBot.setCellDataValueByPosition(1, 2, StringNamesHelper.INT_SMALL);
		dataTypeTableBot.doubleclick(1, 2);

		// It should converted to Upper Case and Valid change
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 2), PinNamesHelper.INT);
	}

	/**
	 * Check whether you can assign invalid(ex: "TestVar") data type
	 *
	 * This method checks whether you can assign invalid data type to the variable
	 * or not. It will first select the variable and then assigns invalid data type
	 * to the selected variable. Then checks it's successfully changed or not or
	 * what changes is happened in the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInValidDataType() {
		dataTypeTableBot.doubleclick(1, 2);
		dataTypeTableBot.setCellDataValueByPosition(1, 2, StringNamesHelper.TESTVAR);
		dataTypeTableBot.doubleclick(1, 2);

		// Data Type will successfully changed with error
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 2), StringNamesHelper.TESTVAR);
		// The cell's background color will get changed to red
		assertNotEquals(dataTypeTableBot.click(1, 2).backgroundColor(), GUIHelper.COLOR_RED);
	}

	/**
	 * Change the comment of the variable
	 *
	 * The method will check if you can change the comment of the variable or not.
	 * It then checks the new comment is assigned properly or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeCommentOfVariable() {
		changeCellValueInNatTbale(dataTypeTableBot, StringNamesHelper.TEST_COMMENT, 1, 3);
	}

	/**
	 * Checks whether you can change the initial value of the variable.
	 *
	 * The method will check if you can change the initial value of the variable or
	 * not. It then checks the new value is assigned properly or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeInitialValueOfVariable() {
		changeCellValueInNatTbale(dataTypeTableBot, StringNamesHelper.TRUE, 1, 4);
		changeCellValueInNatTbale(dataTypeTableBot, "1", 1, 4); //$NON-NLS-1$
	}

	/**
	 * Checks the changes when you assign the invalid initial value.
	 *
	 * The method will check if you can assign invalid the initial value to the
	 * variable or not. It will first select the variable and then assigns invalid
	 * initial value to the selected variable. Then checks it's successfully changed
	 * or not or what changes is happened in the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInValidInitialValue() {
		dataTypeTableBot.doubleclick(1, 4);
		dataTypeTableBot.setCellDataValueByPosition(1, 4, StringNamesHelper.TESTVAR);
		dataTypeTableBot.doubleclick(1, 4);

		// Initial value will successfully changed with error
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(1, 4), StringNamesHelper.TESTVAR);

		// The cell's background color will get changed to red
		assertNotEquals(dataTypeTableBot.click(1, 4).backgroundColor(), GUIHelper.COLOR_RED);
	}

}
