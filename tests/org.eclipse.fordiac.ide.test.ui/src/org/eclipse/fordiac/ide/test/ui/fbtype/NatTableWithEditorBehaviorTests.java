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

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class NatTableWithEditorBehaviorTests extends Abstract4diacUITests {
	protected static SWTBot4diacNatTable natTableBot;
	protected static NatTable natTable;
	protected static SWTBot4diacGefEditor editor;

	protected static String TESTVAR1 = "TESTVAR1"; //$NON-NLS-1$
	protected static String TESTVAR2 = "TESTVAR2"; //$NON-NLS-1$
	protected static String TESTVAR3 = "TESTVAR3"; //$NON-NLS-1$

	/**
	 * Initializes the environment for testing operations on the DataType Editor
	 * Table.
	 *
	 * This method is executed before each test. It creates a new FB type, confirms
	 * its visibility in the system explorer, opens the project in the editor, and
	 * prepares the NatTable for testing by creating a new variable in the table.
	 */
	@SuppressWarnings("static-method")
	@BeforeEach
	public void operationsInitialization() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT2, STRUCT);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT2);
		final Composite tableComposite = (Composite) bot.editorByTitle(FBT_TEST_PROJECT2).getWidget();
		natTable = bot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), tableComposite);
		natTableBot = new SWTBot4diacNatTable(natTable);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
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
		deleteFBType(FBT_TEST_PROJECT2);
	}

	/**
	 * Tests the creation of a new event in the DataType Editor Table.
	 *
	 * This method verifies that a new event can be created successfully by checking
	 * the presence of the new variable in the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createEvent() {
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
		assertNotNull(editor.getEditPart(TESTVAR1));
	}

	/**
	 * Tests the removal of an event from the DataType Editor Table.
	 *
	 * This method verifies that an event can be removed successfully by checking
	 * the absence of the variable in the editor after deletion.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void removeEvent() {
		NatTableHelper.deleteVariable(natTableBot, 3);
		assertNull(editor.getEditPart(TESTVAR1));
	}

	/**
	 * Tests changing the name of an event in the DataType Editor Table.
	 *
	 * This method verifies that the event name can be changed successfully and
	 * checks that the old name is no longer present in the editor, while the new
	 * name is.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeEventName() {
		assertNotNull(editor.getEditPart(TESTVAR1));
		NatTableHelper.changeCellValueInNatTbale(natTableBot, TESTVAR, 3, 1);
		assertNull(editor.getEditPart(TESTVAR1));
		assertNotNull(editor.getEditPart(TESTVAR));
	}

	/**
	 * Tests changing the name of an event using the rename button tool.
	 *
	 * This method verifies that the event name can be changed using the rename
	 * button tool and checks the presence of the new name in the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeEventNameFromRenameButtom() {
		assertNotNull(editor.getEditPart(TESTVAR1));
		NatTableHelper.changeVariableNameWithButtonTool(natTableBot, 3, TESTVAR);
		assertNull(editor.getEditPart(TESTVAR1));
		assertNotNull(editor.getEditPart(TESTVAR));
	}

	/**
	 * Tests changing the data type of a variable in the DataType Editor Table.
	 *
	 * This method checks whether the data type of a variable can be changed and
	 * verifies the successful application of the new data type.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeDataTypeOfVariable() {
		NatTableHelper.changeDataType(natTableBot, 1, INT_SMALL);
	}

	/**
	 * Tests assigning an invalid data type to a variable.
	 *
	 * This method attempts to assign an invalid data type to a variable and
	 * verifies the behavior of the editor when an invalid data type is applied.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInValidDataType() {
		NatTableHelper.setInvalidDataType(natTableBot, 1, 2, TESTVAR);
	}

	/**
	 * Tests changing the comment of an event in the DataType Editor Table.
	 *
	 * This method verifies the ability to change the comment associated with an
	 * event and checks for the expected changes in the editor.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeEventComment() {
		assertNotNull(editor.getEditPart(TESTVAR1));
		assertNull(editor.getEditPart(TESTVAR));
		NatTableHelper.changeCellValueInNatTbale(natTableBot, TEST_COMMENT, 3, 3);
	}

	/**
	 * Tests moving an event up and down in the DataType Editor Table.
	 *
	 * This method verifies the functionality of moving an event's position within
	 * the table and checks for correct ordering of events after the operation.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void moveEventUpDown() {
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
		NatTableHelper.moveElementUp(natTableBot, 5);
		NatTableHelper.moveElementDown(natTableBot, 3);
	}

	/**
	 * Tests attempting to change an event name to an existing name.
	 *
	 * This method checks whether the editor prevents changing an event name to a
	 * name that already exists in the DataType Editor Table.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToChangeEventNameWithExistingName() {
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
		assertNotNull(editor.getEditPart(TESTVAR1));
		assertNotNull(editor.getEditPart(TESTVAR2));
		NatTableHelper.changeNameWithExistingName(natTableBot, 4, TESTVAR1, TESTVAR2);
		assertNotNull(editor.getEditPart(TESTVAR1));
		assertNotNull(editor.getEditPart(TESTVAR2));
	}

	/**
	 * Tests attempting to set an invalid name for an event.
	 *
	 * This method verifies the editor's response when an invalid name is assigned
	 * to an event, ensuring that invalid names are not accepted.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void tryToSetInvalidName() {
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
		assertNotNull(editor.getEditPart(TESTVAR1));
		NatTableHelper.setInvalidName(natTableBot, 3, IF, TESTVAR1);
		assertNull(editor.getEditPart(IF));
		assertNotNull(editor.getEditPart(TESTVAR2));
	}

}
