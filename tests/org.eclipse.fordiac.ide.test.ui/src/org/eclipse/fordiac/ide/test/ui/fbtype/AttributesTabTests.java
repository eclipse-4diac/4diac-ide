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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotFBType;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotNatTable;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotPropertySheet;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;

// Have to look into the table as this has so much different behavior
public class AttributesTabTests extends NatTableWithoutEditorBehaviorTests {

	public AttributesTabTests() {
		super(UITestNamesHelper.ATTRIBUTE1, UITestNamesHelper.ATTRIBUTE2);
	}

	/**
	 * Performs necessary tasks to make environment for testing the operations on
	 * DataType Editor Table.
	 *
	 * This method will run before each test and will create new FB Type each time
	 * and will confirm the visibility of the newly created type in system explorer
	 * and open the project in editor then will create a new variable in a Table.
	 */
	@Override
	@BeforeEach
	public void operationsInitialization() {
		final SWTBotFBType fbTypeBot = new SWTBotFBType(bot);
		fbTypeBot.createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.TEMPLATEBASIC);
		fbTypeBot.openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.ATTRIBUTES);
		assertNotNull(propertiesBot);
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.ATTRIBUTES, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		swt4diacNatTable = new SWTBot4diacNatTable(natTable);
		new SWTBotNatTable(bot, swt4diacNatTable).createNewVariableInDataTypeEditor();
	}

	@Override
	public void changeVariableNameWithButton() {
		// Empty method to prevent parent logic from running
	}

	@Override
	public void tryToSetInValidName() {
		// Here Name column has same behavior as Data Type column or Invalid value
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 1, UITestNamesHelper.IF);
	}

	@Override
	public void tryToChangeNameOfVariableWithExistingName() {
		// Here Name column has same behavior as Data Type column or Invalid value
		new SWTBotNatTable(bot, swt4diacNatTable).setInvalidDataType(1, 1, UITestNamesHelper.IF);
	}

}
