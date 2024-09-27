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

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class NatTableWithEditorBehaviorTests extends Abstract4diacUITests {
	protected SWTBot4diacNatTable swt4diacNatTable;
	protected NatTable natTable;
	protected SWTBot4diacGefEditor editor;

	private final String testVar1;
	private final String testVar2;

	protected NatTableWithEditorBehaviorTests() {
		this("TESTVAR1", "TESTVAR2"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected NatTableWithEditorBehaviorTests(final String testVar1, final String testVar2) {
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
	}

	/**
	 * Initializes the environment for testing operations on the DataType Editor
	 * Table.
	 *
	 * This method is executed before each test. It creates a new FB type, confirms
	 * its visibility in the system explorer, opens the project in the editor, and
	 * prepares the NatTable for testing by creating a new variable in the table.
	 */
	@BeforeEach
	public void operationsInitialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.STRUCT);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final Composite tableComposite = (Composite) bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).getWidget();
		natTable = bot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), tableComposite);
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
	}

	/**
	 * Cleans up the environment by deleting the created FB type.
	 *
	 * This method is executed after each test. It deletes the FB type created at
	 * the beginning of the test to ensure a clean environment for the next test,
	 * thus preventing redundancy and potential conflicts.
	 */
	@SuppressWarnings("static-method")
	@AfterEach
	public void resetEnvironment() {
		new SWTBotFBType(bot).deleteFBType(UITestNamesHelper.FBT_TEST_PROJECT2);
	}

	/**
	 * Tests the creation of a new event in the DataType Editor Table.
	 *
	 * This method verifies that a new event can be created successfully by checking
	 * the presence of the new variable in the editor.
	 */
	@Test
	public void createEvent() {
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
		assertNotNull(editor.getEditPart(testVar1));
	}

	/**
	 * Tests the removal of an event from the DataType Editor Table.
	 *
	 * This method verifies that an event can be removed successfully by checking
	 * the absence of the variable in the editor after deletion.
	 */
	@Test
	public void removeEvent() {
		new SWTBotNatTable(bot, swt4diacNatTable).deleteVariable(3);
		assertNull(editor.getEditPart(testVar1));
	}

	/**
	 * Tests changing the name of an event in the DataType Editor Table.
	 *
	 * This method verifies that the event name can be changed successfully and
	 * checks that the old name is no longer present in the editor, while the new
	 * name is.
	 */
	@Test
	public void changeEventName() {
		assertNotNull(editor.getEditPart(testVar1));
		new SWTBotNatTable(bot, swt4diacNatTable).changeCellValueInNatTbale(UITestNamesHelper.TESTVAR, 3, 1);
		assertNull(editor.getEditPart(testVar1));
		assertNotNull(editor.getEditPart(UITestNamesHelper.TESTVAR));
	}

	/**
	 * Tests changing the name of an event using the rename button tool.
	 *
	 * This method verifies that the event name can be changed using the rename
	 * button tool and checks the presence of the new name in the editor.
	 */
	@Test
	public void changeEventNameFromRenameButtom() {
		assertNotNull(editor.getEditPart(testVar1));
		new SWTBotNatTable(bot, swt4diacNatTable).changeVariableNameWithButtonTool(3, UITestNamesHelper.TESTVAR);
		assertNull(editor.getEditPart(testVar1));
		assertNotNull(editor.getEditPart(UITestNamesHelper.TESTVAR));
	}

	/**
	 * Tests changing the data type of a variable in the DataType Editor Table.
	 *
	 * This method checks whether the data type of a variable can be changed and
	 * verifies the successful application of the new data type.
	 */
	@Test
	public void changeDataTypeOfVariable() {
		new SWTBotNatTable(bot, swt4diacNatTable).changeDataType(1, UITestNamesHelper.INT_SMALL);
	}

	/**
	 * Tests assigning an invalid data type to a variable.
	 *
	 * This method attempts to assign an invalid data type to a variable and
	 * verifies the behavior of the editor when an invalid data type is applied.
	 */
	@Test
	public void tryToSetInValidDataType() {
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 2, UITestNamesHelper.TESTVAR);
	}

	/**
	 * Tests changing the comment of an event in the DataType Editor Table.
	 *
	 * This method verifies the ability to change the comment associated with an
	 * event and checks for the expected changes in the editor.
	 */
	@Test
	public void changeEventComment() {
		assertNotNull(editor.getEditPart(testVar1));
		assertNull(editor.getEditPart(UITestNamesHelper.TESTVAR));
		new SWTBotNatTable(bot, swt4diacNatTable).changeCellValueInNatTbale(UITestNamesHelper.TEST_COMMENT, 3, 3);
	}

	/**
	 * Tests moving an event up and down in the DataType Editor Table.
	 *
	 * This method verifies the functionality of moving an event's position within
	 * the table and checks for correct ordering of events after the operation.
	 */
	@Test
	public void moveEventUpDown() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.createNewVariableInDataTypeEditor();
		swtBotNatTable.createNewVariableInDataTypeEditor();
		swtBotNatTable.moveElementUp(5);
		swtBotNatTable.moveElementDown(3);
	}

	/**
	 * Tests attempting to change an event name to an existing name.
	 *
	 * This method checks whether the editor prevents changing an event name to a
	 * name that already exists in the DataType Editor Table.
	 */
	@Test
	public void tryToChangeEventNameWithExistingName() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.createNewVariableInDataTypeEditor();
		assertNotNull(editor.getEditPart(testVar1));
		assertNotNull(editor.getEditPart(testVar2));
		swtBotNatTable.changeNameWithExistingName(4, testVar1, testVar2);
		assertNotNull(editor.getEditPart(testVar1));
		assertNotNull(editor.getEditPart(testVar2));
	}

	/**
	 * Tests attempting to set an invalid name for an event.
	 *
	 * This method verifies the editor's response when an invalid name is assigned
	 * to an event, ensuring that invalid names are not accepted.
	 */
	@Test
	public void tryToSetInvalidName() {
		final SWTBotNatTable swtBotNatTable = new SWTBotNatTable(bot, swt4diacNatTable);
		swtBotNatTable.createNewVariableInDataTypeEditor();
		assertNotNull(editor.getEditPart(testVar1));
		swtBotNatTable.setInvalidName(3, UITestNamesHelper.IF, testVar1);
		assertNull(editor.getEditPart(UITestNamesHelper.IF));
		assertNotNull(editor.getEditPart(testVar2));
	}

}
