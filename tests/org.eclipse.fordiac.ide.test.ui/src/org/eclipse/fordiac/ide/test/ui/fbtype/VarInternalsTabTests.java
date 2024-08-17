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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.test.ui.PropertySheetHelper;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VarInternalsTabTests extends NatTableWithoutEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = UITestNamesHelper.INTERNALVAR1;
		TESTVAR2 = UITestNamesHelper.INTERNALVAR2;
		TESTVAR3 = UITestNamesHelper.INTERNALVAR3;
		createFBType(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2,
				UITestNamesHelper.TEMPLATEBASIC);
		openFBTypeInEditor(UITestNamesHelper.PROJECT_NAME, UITestNamesHelper.FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.VAR_INTERNALS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		PropertySheetHelper.selectPropertyTabItem(UITestNamesHelper.VAR_INTERNALS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		natTableBot = new SWTBot4diacNatTable(natTable);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
	}

	/**
	 * Tests changing the retain tag of a variable in the DataType Editor Table.
	 *
	 * This method checks whether the retain tag of a variable can be modified
	 * successfully. It changes the retain tag of the specified variable and
	 * verifies the operation.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void changeRetainTag() {
		NatTableHelper.setRetainValue(natTableBot, 1, 5, RetainTag.RETAIN);
	}

}