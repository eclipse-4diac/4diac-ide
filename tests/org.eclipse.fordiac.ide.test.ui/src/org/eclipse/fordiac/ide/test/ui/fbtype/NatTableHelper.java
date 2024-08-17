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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class NatTableHelper extends Abstract4diacUITests {

	/**
	 * Creates a new variable in the Data Type Editor.
	 *
	 * @param natTableBot NatTable in which the variable needs to be created.
	 */
	protected static void createNewVariableInDataTypeEditor(final SWTBot4diacNatTable natTableBot) {
		final int rowCount = natTableBot.rowCount();
		bot.button(0).click();
		assertEquals(natTableBot.rowCount(), rowCount + 1);
	}

	/**
	 * Changes the value of a specific cell in the NatTable.
	 *
	 * @param dataTypeTableBot NatTable containing the targeted cell.
	 * @param newValue         The new value to set in the cell.
	 * @param row              The row number of the cell.
	 * @param col              The column number of the cell.
	 */
	protected static void changeCellValueInNatTbale(final SWTBot4diacNatTable dataTypeTableBot, final String newValue,
			final int row, final int col) {
		dataTypeTableBot.doubleclick(row, col);
		dataTypeTableBot.setCellDataValueByPosition(row, col, newValue);
		dataTypeTableBot.doubleclick(row, col);
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(row, col), newValue);
	}

	/**
	 * Deletes a variable from the Data Type Editor.
	 *
	 * @param natTableBot NatTable from which the variable has to be deleted.
	 * @param srNo        The serial number of the variable to be deleted.
	 */
	protected static void deleteVariable(final SWTBot4diacNatTable natTableBot, final int srNo) {
		final int rowCount = natTableBot.rowCount();
		natTableBot.click(srNo, 0);
		bot.button(1).click();
		assertEquals(natTableBot.rowCount(), rowCount - 1);
	}

	/**
	 * Changes the name of a variable using the toolbar button tool.
	 *
	 * @param natTableBot NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 * @param newName     The new name for the variable.
	 */
	protected static void changeVariableNameWithButtonTool(final SWTBot4diacNatTable natTableBot, final int srNo,
			final String newName) {
		natTableBot.click(srNo, 1);
		bot.toolbarButtonWithTooltip(UITestNamesHelper.RENAME_ELEMENT).click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.REFACTORING);
		assertNotNull(shell);
		shell.activate();
		assertFalse(bot.button(UITestNamesHelper.OK).isEnabled());

		bot.textWithLabel(UITestNamesHelper.NAME).setText(newName);
		assertTrue(bot.button(UITestNamesHelper.OK).isEnabled());

		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));

		assertEquals(natTableBot.getCellDataValueByPosition(srNo, 1), newName);
	}

	/**
	 * Attempts to change the name of a variable to an existing name, which should
	 * fail.
	 *
	 * @param natTableBot  NatTable containing the variable.
	 * @param srNo         The serial number of the variable.
	 * @param existingName The existing name to be used (which should cause a
	 *                     conflict).
	 * @param curentName   The current name of the variable, expected to remain
	 *                     unchanged.
	 */
	protected static void changeNameWithExistingName(final SWTBot4diacNatTable natTableBot, final int srNo,
			final String existingName, final String curentName) {
		createNewVariableInDataTypeEditor(natTableBot);
		natTableBot.doubleclick(srNo, 1);
		natTableBot.setCellDataValueByPosition(srNo, 1, existingName);
		natTableBot.doubleclick(srNo, 1);

		// Name should not be changed
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, 1), curentName);
	}

	/**
	 * Attempts to set an invalid name for a variable, which should not succeed.
	 *
	 * @param natTableBot NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 * @param invalidName The invalid name to be set.
	 * @param curentName  The current name of the variable, expected to remain
	 *                    unchanged.
	 */
	protected static void setInvalidName(final SWTBot4diacNatTable natTableBot, final int srNo,
			final String invalidName, final String curentName) {
		natTableBot.doubleclick(srNo, 1);
		natTableBot.setCellDataValueByPosition(srNo, 1, invalidName);
		natTableBot.doubleclick(srNo, 1);

		// Name should not be changed
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, 1), curentName);
	}

	/**
	 * Changes the data type of a variable in the NatTable.
	 *
	 * @param natTableBot NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 * @param dataType    The new data type to set for the variable.
	 */
	protected static void changeDataType(final SWTBot4diacNatTable natTableBot, final int srNo, final String dataType) {
		natTableBot.doubleclick(1, 2);

		// Assign the value, e.g., "int"
		natTableBot.setCellDataValueByPosition(srNo, 2, dataType);
		final String expectedDataType = dataType.toUpperCase();
		natTableBot.doubleclick(srNo, 2);

		// It should be converted to uppercase and the change should be valid
		assertEquals(natTableBot.getCellDataValueByPosition(1, 2), expectedDataType);
	}

	/**
	 * Attempts to set an invalid data type for a variable, which should cause an
	 * error.
	 *
	 * @param natTableBot      NatTable containing the variable.
	 * @param srNo             The serial number of the variable.
	 * @param dataTypeColumnNo The column number of the data type.
	 * @param invalidDataType  The invalid data type to set.
	 */
	protected static void setInvalidDataType(final SWTBot4diacNatTable natTableBot, final int srNo,
			final int dataTypeColumnNo, final String invalidDataType) {
		natTableBot.doubleclick(srNo, dataTypeColumnNo);
		natTableBot.setCellDataValueByPosition(srNo, dataTypeColumnNo, invalidDataType);
		natTableBot.doubleclick(srNo, dataTypeColumnNo);

		// Data Type will be changed, but with an error
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, dataTypeColumnNo), invalidDataType);
		// The cell's background color will change to red
		assertNotEquals(natTableBot.click(srNo, dataTypeColumnNo).backgroundColor(), GUIHelper.COLOR_RED);
	}

	/**
	 * Attempts to set an invalid initial value for a variable.
	 *
	 * @param natTableBot          NatTable containing the variable.
	 * @param srNo                 The serial number of the variable.
	 * @param initialValueColumnNo The column number of the initial value.
	 * @param invalidInitialValue  The invalid initial value to set.
	 */
	protected static void setInvalidInitialValue(final SWTBot4diacNatTable natTableBot, final int srNo,
			final int initialValueColumnNo, final String invalidInitialValue) {
		setInvalidDataType(natTableBot, srNo, initialValueColumnNo, invalidInitialValue);
	}

	/**
	 * Moves a variable up one row in the NatTable.
	 *
	 * @param natTableBot NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 */
	protected static void moveElementUp(final SWTBot4diacNatTable natTableBot, final int srNo) {
		final String upperRowName = natTableBot.getCellDataValueByPosition(srNo - 1, 1);
		final String currentRowName = natTableBot.getCellDataValueByPosition(srNo, 1);
		natTableBot.click(srNo, 0);

		// Move the selected variable up
		bot.arrowButtonWithTooltip(UITestNamesHelper.MOVE_ELEMENTS_UP).click();
		assertEquals(natTableBot.getCellDataValueByPosition(srNo - 1, 1), currentRowName);
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, 1), upperRowName);
	}

	/**
	 * Moves a variable down one row in the NatTable.
	 *
	 * @param natTableBot NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 */
	protected static void moveElementDown(final SWTBot4diacNatTable natTableBot, final int srNo) {
		final String lowerRowName = natTableBot.getCellDataValueByPosition(srNo + 1, 1);
		final String currentRowName = natTableBot.getCellDataValueByPosition(srNo, 1);
		natTableBot.click(srNo, 0);

		// Move the selected variable down
		bot.arrowButtonWithTooltip(UITestNamesHelper.MOVE_ELEMENTS_DOWN).click();
		assertEquals(natTableBot.getCellDataValueByPosition(srNo + 1, 1), currentRowName);
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, 1), lowerRowName);
	}

	/**
	 * Sets the retain value for a variable in the Data Type Editor.
	 *
	 * @param natTableBot NatTable containing the targeted cell for the retain
	 *                    value.
	 * @param srNo        The row number of the variable for which the retain value
	 *                    is to be set.
	 * @param retainCol   The column number where the retain value is to be set.
	 * @param retainValue The RetainTag value (RETAIN, NO_RETAIN, NOTHING) to be set
	 *                    for the variable.
	 */
	protected static void setRetainValue(final SWTBot4diacNatTable natTableBot, final int srNo, final int retainCol,
			final RetainTag retainValue) {
		natTableBot.doubleclick(srNo, retainCol);
		bot.table().select(retainValue.toString());
		assertEquals(natTableBot.getCellDataValueByPosition(srNo, retainCol), retainValue.toString());
	}
}
