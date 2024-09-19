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
package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class SWTBotNatTable {

	private final SWT4diacGefBot bot;
	private final SWTBot4diacNatTable natTable;

	public SWTBotNatTable(final SWT4diacGefBot bot, final SWTBot4diacNatTable natTableBot) {
		this.bot = bot;
		this.natTable = natTableBot;
	}

	/**
	 * Creates a new variable in the Data Type Editor.
	 *
	 * @param natTable NatTable in which the variable needs to be created.
	 */
	public void createNewVariableInDataTypeEditor() {
		final int rowCount = natTable.rowCount();
		bot.button(0).click();
		assertEquals(natTable.rowCount(), rowCount + 1);
	}

	/**
	 * Changes the value of a specific cell in the NatTable.
	 *
	 * @param natTable NatTable containing the targeted cell.
	 * @param newValue The new value to set in the cell.
	 * @param row      The row number of the cell.
	 * @param col      The column number of the cell.
	 */
	public void changeCellValueInNatTbale(final String newValue, final int row, final int col) {
		natTable.doubleclick(row, col);
		natTable.setCellDataValueByPosition(row, col, newValue);
		natTable.doubleclick(row, col);
		assertEquals(natTable.getCellDataValueByPosition(row, col), newValue);
	}

	/**
	 * Deletes a variable from the Data Type Editor.
	 *
	 * @param natTable NatTable from which the variable has to be deleted.
	 * @param srNo     The serial number of the variable to be deleted.
	 */
	public void deleteVariable(final int srNo) {
		final int rowCount = natTable.rowCount();
		natTable.click(srNo, 0);
		bot.button(1).click();
		assertEquals(natTable.rowCount(), rowCount - 1);
	}

	/**
	 * Changes the name of a variable using the toolbar button tool.
	 *
	 * @param natTable NatTable containing the variable.
	 * @param srNo     The serial number of the variable.
	 * @param newName  The new name for the variable.
	 */
	public void changeVariableNameWithButtonTool(final int srNo, final String newName) {
		natTable.click(srNo, 1);
		bot.toolbarButtonWithTooltip(UITestNamesHelper.RENAME_ELEMENT).click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.REFACTORING);
		assertNotNull(shell);
		shell.activate();
		assertFalse(bot.button(UITestNamesHelper.OK).isEnabled());

		bot.textWithLabel(UITestNamesHelper.NAME).setText(newName);
		assertTrue(bot.button(UITestNamesHelper.OK).isEnabled());

		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));

		assertEquals(natTable.getCellDataValueByPosition(srNo, 1), newName);
	}

	/**
	 * Attempts to change the name of a variable to an existing name, which should
	 * fail.
	 *
	 * @param natTable     NatTable containing the variable.
	 * @param srNo         The serial number of the variable.
	 * @param existingName The existing name to be used (which should cause a
	 *                     conflict).
	 * @param curentName   The current name of the variable, expected to remain
	 *                     unchanged.
	 */
	public void changeNameWithExistingName(final int srNo, final String existingName, final String curentName) {
		createNewVariableInDataTypeEditor();
		natTable.doubleclick(srNo, 1);
		natTable.setCellDataValueByPosition(srNo, 1, existingName);
		natTable.doubleclick(srNo, 1);

		// Name should not be changed
		assertEquals(natTable.getCellDataValueByPosition(srNo, 1), curentName);
	}

	/**
	 * Attempts to set an invalid name for a variable, which should not succeed.
	 *
	 * @param natTable    NatTable containing the variable.
	 * @param srNo        The serial number of the variable.
	 * @param invalidName The invalid name to be set.
	 * @param curentName  The current name of the variable, expected to remain
	 *                    unchanged.
	 */
	public void setInvalidName(final int srNo, final String invalidName, final String curentName) {
		natTable.doubleclick(srNo, 1);
		natTable.setCellDataValueByPosition(srNo, 1, invalidName);
		natTable.doubleclick(srNo, 1);

		// Name should not be changed
		assertEquals(natTable.getCellDataValueByPosition(srNo, 1), curentName);
	}

	/**
	 * Changes the data type of a variable in the NatTable.
	 *
	 * @param natTable NatTable containing the variable.
	 * @param srNo     The serial number of the variable.
	 * @param dataType The new data type to set for the variable.
	 */
	public void changeDataType(final int srNo, final String dataType) {
		natTable.doubleclick(1, 2);

		// Assign the value, e.g., "int"
		natTable.setCellDataValueByPosition(srNo, 2, dataType);
		final String expectedDataType = dataType.toUpperCase();
		natTable.doubleclick(srNo, 2);

		// It should be converted to uppercase and the change should be valid
		assertEquals(natTable.getCellDataValueByPosition(1, 2), expectedDataType);
	}

	/**
	 * Attempts to set an invalid data type for a variable, which should cause an
	 * error.
	 *
	 * @param natTable         NatTable containing the variable.
	 * @param srNo             The serial number of the variable.
	 * @param dataTypeColumnNo The column number of the data type.
	 * @param invalidDataType  The invalid data type to set.
	 */
	public void setInvalidDataType(final int srNo, final int dataTypeColumnNo, final String invalidDataType) {
		natTable.doubleclick(srNo, dataTypeColumnNo);
		natTable.setCellDataValueByPosition(srNo, dataTypeColumnNo, invalidDataType);
		natTable.doubleclick(srNo, dataTypeColumnNo);

		// Data Type will be changed, but with an error
		assertEquals(natTable.getCellDataValueByPosition(srNo, dataTypeColumnNo), invalidDataType);
		// The cell's background color will change to red
		assertNotEquals(natTable.click(srNo, dataTypeColumnNo).backgroundColor(), GUIHelper.COLOR_RED);
	}

	/**
	 * Attempts to set an invalid initial value for a variable.
	 *
	 * @param natTable             NatTable containing the variable.
	 * @param srNo                 The serial number of the variable.
	 * @param initialValueColumnNo The column number of the initial value.
	 * @param invalidInitialValue  The invalid initial value to set.
	 */
	public void setInvalidInitialValue(final int srNo, final int initialValueColumnNo,
			final String invalidInitialValue) {
		setInvalidDataType(srNo, initialValueColumnNo, invalidInitialValue);
	}

	/**
	 * Moves a variable up one row in the NatTable.
	 *
	 * @param natTable NatTable containing the variable.
	 * @param srNo     The serial number of the variable.
	 */
	public void moveElementUp(final int srNo) {
		final String upperRowName = natTable.getCellDataValueByPosition(srNo - 1, 1);
		final String currentRowName = natTable.getCellDataValueByPosition(srNo, 1);
		natTable.click(srNo, 0);

		// Move the selected variable up
		bot.arrowButtonWithTooltip(UITestNamesHelper.MOVE_ELEMENTS_UP).click();
		assertEquals(natTable.getCellDataValueByPosition(srNo - 1, 1), currentRowName);
		assertEquals(natTable.getCellDataValueByPosition(srNo, 1), upperRowName);
	}

	/**
	 * Moves a variable down one row in the NatTable.
	 *
	 * @param natTable NatTable containing the variable.
	 * @param srNo     The serial number of the variable.
	 */
	public void moveElementDown(final int srNo) {
		final String lowerRowName = natTable.getCellDataValueByPosition(srNo + 1, 1);
		final String currentRowName = natTable.getCellDataValueByPosition(srNo, 1);
		natTable.click(srNo, 0);

		// Move the selected variable down
		bot.arrowButtonWithTooltip(UITestNamesHelper.MOVE_ELEMENTS_DOWN).click();
		assertEquals(natTable.getCellDataValueByPosition(srNo + 1, 1), currentRowName);
		assertEquals(natTable.getCellDataValueByPosition(srNo, 1), lowerRowName);
	}

	/**
	 * Sets the retain value for a variable in the Data Type Editor.
	 *
	 * @param natTable    NatTable containing the targeted cell for the retain
	 *                    value.
	 * @param srNo        The row number of the variable for which the retain value
	 *                    is to be set.
	 * @param retainCol   The column number where the retain value is to be set.
	 * @param retainValue The RetainTag value (RETAIN, NO_RETAIN, NOTHING) to be set
	 *                    for the variable.
	 */
	public void setRetainValue(final int srNo, final int retainCol, final RetainTag retainValue) {
		natTable.doubleclick(srNo, retainCol);
		bot.table().select(retainValue.toString());
		assertEquals(natTable.getCellDataValueByPosition(srNo, retainCol), retainValue.toString());
	}
}
