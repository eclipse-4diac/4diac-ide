/*******************************************************************************
 * Copyright (c) 2024 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.junit.jupiter.api.Test;

public class SWTBotContextMenuUITests extends Abstract4diacUITests {

	/**
	 * Checks if a subapplication can be created via ContextMenu.
	 *
	 * SWTBot simulates a right-click to open the context menu. A new subapplication
	 * is then created. It is checked if it is empty and displayed correctly in the
	 * SystemExplorer
	 */
	@SuppressWarnings("static-method")
	@Test
	public void createSubapplicationViaContextMenu() {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(PROJECT_NAME);

		editor.clickContextMenu("New subapplication", 100, 100);
		final List<SWTBotGefEditPart> selectedEditParts = editor.selectedEditParts();
		assertEquals(1, selectedEditParts.size());
		assertTrue(isSubappSelected(selectedEditParts, SUBAPP));
		// check App node and SubApp TreeItem in SystemExplorer tree
		assertTrue(isElementInApplicationOfSystemInSystemExplorer(SUBAPP));
		assertTrue(isSubAppNodeInSystemExplorerEmpty());
	}

}
