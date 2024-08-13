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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.fordiac.ide.test.ui.PropertySheetHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventInOutputsTabTests extends NatTableWithEditorBehaviorTests {

	@Override
	@BeforeEach
	public void operationsInitialization() {
		TESTVAR1 = EI1;
		TESTVAR2 = EI2;
		TESTVAR3 = EI3;
		createFBType(PROJECT_NAME, FBT_TEST_PROJECT2, ADAPTER);
		openFBTypeInEditor(PROJECT_NAME, FBT_TEST_PROJECT2);
		final SWTBot propertiesBot = selectTabFromInterfaceProperties(EVENT_IN_AND_OUTPUTS);
		assertNotNull(propertiesBot);
		bot.viewByTitle(PROPERTIES_TITLE).setFocus();
		bot.editorByTitle(FBT_TEST_PROJECT2).show();
		PropertySheetHelper.selectPropertyTabItem(EVENT_IN_AND_OUTPUTS, propertiesBot);
		natTable = propertiesBot.widget(WidgetMatcherFactory.widgetOfType(NatTable.class), 0);
		natTableBot = new SWTBot4diacNatTable(natTable);
		editor = (SWTBot4diacGefEditor) bot.gefEditor(FBT_TEST_PROJECT2);
		assertNotNull(editor);
		NatTableHelper.createNewVariableInDataTypeEditor(natTableBot);
	}

	@Override
	@Test
	public void changeDataTypeOfVariable() {
		natTableBot.doubleclick(1, 2);
		natTableBot.setCellDataValueByPosition(1, 2, TESTVAR);
		natTableBot.doubleclick(1, 2);

		// Initial value will successfully changed with error
		assertEquals(natTableBot.getCellDataValueByPosition(1, 2), EVENT);
	}

	@Override
	public void tryToSetInValidDataType() {
		// Empty method to prevent parent logic from running
	}

}
