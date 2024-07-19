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

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.jupiter.api.Test;

public class BasicFBTOperationTest extends Abstract4diacUITests {

	/**
	 * Creates new input Event pin on functional block.
	 *
	 * The method checks if you can create a new input Event pin on a functional
	 * block using editor, if the newly created event pin is visible on functional
	 * block then it's created successfully.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createNewEventInput() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT2);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT2);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT2);
		editor.clickContextMenu(CREATE_INPUT_EVENT);
		assertNotNull(editor.getEditPart(EI1));

		deletePin(editor, EI1);
		deleteFBType(FBT_TEST_PROJECT2);

	}

	/**
	 * Creates new input Data pin on functional block.
	 *
	 * The method checks if you can create a new input Data pin on a functional
	 * block using editor, if the newly created data pin is visible on functional
	 * block then it's created successfully.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createNewDataInput(final String dataType) {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT3);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT3);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT3);
		assertNotNull(editor);
		editor.clickContextMenu(CREATE_DATA_INPUT).clickContextMenu(dataType);

		assertNotNull(editor.getEditPart(DI1));
		deleteFBType(FBT_TEST_PROJECT3);
	}

	// it's creating data input, that's error
	/**
	 * Creates new output Data pin on functional block.
	 *
	 * The method checks if you can create a new output Data pin on a functional
	 * block using editor, if the newly created data pin is visible on functional
	 * block then it's created successfully.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createNewDataOutput(final String dataType) {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT3);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT3);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT3);
		assertNotNull(editor);
		editor.clickContextMenu(CREATE_DATA_OUTPUT).clickContextMenu(dataType);

		assertNotNull(editor.getEditPart(DO1));
		deleteFBType(FBT_TEST_PROJECT3);
	}

	/**
	 * Rename the existing pin on functional block.
	 *
	 * The method checks if you can rename the pin which is already existing on the
	 * functional block. It first creates the pin on FB and then try to change the
	 * name of the pin, if the previous pin is not visible and the new named pin is
	 * visible on FB, then the rename is done successfully.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void renamePin() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT4);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT4);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT4);
		assertNotNull(editor);
		editor.clickContextMenu(CREATE_INPUT_EVENT);

		assertNotNull(editor.getEditPart(EI1));

		final SWTBotGefEditPart pin = editor.getEditPart(EI1);
		pin.click();

		final SWTBot propertiesBot = selectTabFromInterfaceProperties(EVENT);
		propertiesBot.textWithLabel(NAME_LABEL).setText(EVENT);

		assertNull(editor.getEditPart(EI1));
		assertNotNull(editor.getEditPart(EVENT));

		deleteFBType(FBT_TEST_PROJECT4);
	}

	/**
	 * Change the comment for the pin on FB.
	 *
	 * The method checks if you can change the comment of the pin on FB and if the
	 * user can select the properties and tabs (tabs on property sheet) correctly.
	 * Then check if the comment is changed properly or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeEventComment() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT3);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT3);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT3);
		assertNotNull(editor);
		editor.clickContextMenu(CREATE_INPUT_EVENT);

		final SWTBotGefEditPart pin = editor.getEditPart(EI1);
		pin.click();

		final SWTBot propertiesBot = selectTabFromInterfaceProperties(EVENT);
		propertiesBot.textWithLabel(COMMENT).setText(TEST_COMMENT);

		assertEquals(propertiesBot.textWithLabel(COMMENT).getText(), TEST_COMMENT);

//		deleteFBType(FBT_TEST_PROJECT3);
	}

	/**
	 * Add New connection between two pins.
	 *
	 * The method checks whether user can create new connection between two pins on
	 * fb using table from event tab(for event pin) on property sheet, if you can
	 * select the property sheet's tabs properly, then click on checkbox to create
	 * with connection.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void addConnection() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT1);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT1);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT1);
		editor.clickContextMenu(CREATE_INPUT_EVENT);
		editor.clickContextMenu(CREATE_DATA_INPUT).clickContextMenu(INT);

		assertNotNull(editor.getEditPart(DI1));

		final SWTBotGefEditPart inputPin = editor.getEditPart(EI1);
		inputPin.click();

		final SWTBotGefEditPart outputPin = editor.getEditPart(DI1);
		outputPin.click();

		createConnectionWithinFBTypeWithPropertySheet(DI1, EI1, editor);

	}

	/**
	 * Remove connection between two pins.
	 *
	 * The method checks whether user can remove connection between two pins on fb
	 * using table from event tab(for event pin) on property sheet, if you can
	 * select the property sheet's tabs properly, then click on checkbox to remove
	 * with connection.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void removeConnection() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT1);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT1);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT1);
		editor.clickContextMenu(CREATE_INPUT_EVENT);
		editor.clickContextMenu(CREATE_DATA_INPUT).clickContextMenu(INT);

		assertNotNull(editor.getEditPart(DI1));

		final SWTBotGefEditPart inputPin = editor.getEditPart(EI1);
		inputPin.click();

		final SWTBotGefEditPart outputPin = editor.getEditPart(DI1);
		outputPin.click();

		createConnectionWithinFBTypeWithPropertySheet(DI1, EI1, editor);

		removeConnectionWithinFBTypeWithPropertySheet(DI1, EI1, editor);

	}

	/**
	 * Change data type of pin on FB
	 *
	 * The method checks whether you can change the data type of pin which is on FB,
	 * if the new data pin is created, if the you can select the tabs on property
	 * sheet, if the dot button on Type container is selected, if the "Type
	 * Selection" shell is visible, if the user can select the type, if the user can
	 * click on OK button and then is type changed successfully or not.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changePinDataType() {
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT3);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT3);

		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT3);
		assertNotNull(editor);
		editor.clickContextMenu(CREATE_DATA_INPUT).clickContextMenu(INT);

		final SWTBotGefEditPart port = editor.getEditPart(DI1);
		port.click();

		SWTBot propertiesBot = selectTabFromInterfaceProperties(DATA);
		propertiesBot.table().select(INT);
		propertiesBot.button(DOT_BUTTON).click();

		final SWTBotShell shell = bot.shell(TYPE_SELECTION);
		shell.activate();

		final SWTBotTree containerTree = bot.tree();
		final SWTBotTreeItem containerItem = containerTree.getTreeItem(ELEMENTARY_TYPE);
		containerItem.expand().select(ANY);

		bot.button(OK).click();

		propertiesBot = selectTabFromInterfaceProperties(DATA);

		assertTrue(propertiesBot.tableWithLabel(TYPE_LABEL).containsText(ANY));
	}

}
