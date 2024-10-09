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

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NatTableWithoutEditorBehaviorTests extends Abstract4diacUITests {
	protected SWTBot4diacNatTable swt4diacNatTable;
	protected NatTable natTable;

	private final String testVar1;
	private final String testVar2;

	protected NatTableWithoutEditorBehaviorTests() {
		this("TESTVAR1", "TESTVAR2"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected NatTableWithoutEditorBehaviorTests(final String testVar1, final String testVar2) {
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
	}

	/**
	 * Performs necessary tasks to make environment for testing the operations on
	 * DataType Editor Table.
	 *
	 * This method will run before each test and will create new FB Type each time
	 * and will confirm the visibility of the newly created type in system explorer
	 * and open the project in editor then will create a new variable in a Table.
	 */
	@BeforeEach
	public void operationsInitialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.STRUCT);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final Composite inputComposite = (Composite) bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).getWidget();
		natTable = bot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), inputComposite);
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
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
		new SWTBotFBType(bot).deleteFBType(UITestNamesHelper.FBT_TEST_PROJECT2);
	}

	/**
	 * Rename the newly created variable
	 *
	 * The method will check if you can change the name of the variable which is
	 * already exist in the Table. It then checks the new name is assigned properly
	 * or not.
	 */
	@Test
	public void renameVariable() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeCellValueInNatTbale(UITestNamesHelper.TESTVAR, 1, 1);
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
	@Test
	public void changeVariableNameWithButton() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeVariableNameWithButtonTool(1, UITestNamesHelper.TESTVAR);
	}

	/**
	 * Checks whether you can give the existing name to the variable or not
	 *
	 * This method checks whether you can give existing name to the other variable
	 * or not. It first creates 2 new variables and then try to change the name of
	 * 2nd variable to same name as 1st variable has. Then check if the name is
	 * successfully changed or not.
	 */
	@Test
	public void tryToChangeNameOfVariableWithExistingName() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeNameWithExistingName(2, testVar1, testVar2);
	}

	/**
	 * Checks whether we can give Invalid name(ex: "IF") to the variable or not.
	 *
	 * This method checks whether you can give invalid name to variable or not. It
	 * will try to change the name of the variable with assigning invalid name. Then
	 * check if the name is successfully changed or not.
	 */
	@Test
	public void tryToSetInValidName() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidName(1, UITestNamesHelper.IF, testVar1);
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
	@Test
	public void reorderVariable() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.createNewVariableInDataTypeEditor();
		swtBotNatTable.createNewVariableInDataTypeEditor();
		swtBotNatTable.moveElementUp(3);
		swtBotNatTable.moveElementDown(1);
	}

	/**
	 * Checks whether you can change the data type of the variable or not.
	 *
	 * This method checks whether you can change the data type of the variable or
	 * not. It will first select the variable and then assigns data type to the
	 * selected variable. Then checks it's successfully changed or not.
	 */
	@Test
	public void changeDataTypeOfVariable() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeDataType(1, UITestNamesHelper.INT_SMALL);
	}

	/**
	 * Check whether you can assign invalid(ex: "TestVar") data type
	 *
	 * This method checks whether you can assign invalid data type to the variable
	 * or not. It will first select the variable and then assigns invalid data type
	 * to the selected variable. Then checks it's successfully changed or not or
	 * what changes is happened in the editor.
	 */
	@Test
	public void tryToSetInValidDataType() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 2, UITestNamesHelper.TESTVAR);
	}

	/**
	 * Change the comment of the variable
	 *
	 * The method will check if you can change the comment of the variable or not.
	 * It then checks the new comment is assigned properly or not.
	 */
	@Test
	public void changeCommentOfVariable() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeCellValueInNatTbale(UITestNamesHelper.TEST_COMMENT, 1, 3);
	}

	/**
	 * Checks whether you can change the initial value of the variable.
	 *
	 * The method will check if you can change the initial value of the variable or
	 * not. It then checks the new value is assigned properly or not.
	 */
	@Test
	public void changeInitialValueOfVariable() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.changeCellValueInNatTbale(UITestNamesHelper.TRUE, 1, 4);
		swtBotNatTable.changeCellValueInNatTbale("1", 1, 4); //$NON-NLS-1$
	}

	/**
	 * Checks the changes when you assign the invalid initial value.
	 *
	 * The method will check if you can assign invalid the initial value to the
	 * variable or not. It will first select the variable and then assigns invalid
	 * initial value to the selected variable. Then checks it's successfully changed
	 * or not or what changes is happened in the editor.
	 */
	@Test
	public void tryToSetInValidInitialValue() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 4, UITestNamesHelper.TESTVAR);
	}
}
