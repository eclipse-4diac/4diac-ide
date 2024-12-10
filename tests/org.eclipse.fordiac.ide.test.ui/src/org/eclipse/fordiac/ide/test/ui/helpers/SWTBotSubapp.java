/*******************************************************************************
 * Copyright (c) 2023, 2024 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *                - moved methods related to Subapplications from class
 *                  Abstract4diacUITests here due to architecture redesign and
 *                  created constructor and fields.
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;

public class SWTBotSubapp {

	private final SWT4diacGefBot bot;

	public SWTBotSubapp(final SWT4diacGefBot bot) {
		this.bot = bot;
	}

	/**
	 * creates a Subapplication by first selecting all elements within the given
	 * rectangle and then creating the Subapplication.
	 *
	 * @param fromXPosition the x value of the starting point
	 * @param fromYPosition the y value of the starting point
	 * @param toXPosition   the x value of the target point
	 * @param toYPosition   the y value of the target point
	 * @return
	 */
	public void createSubappWithDragRectangle(final int fromXPosition, final int fromYPosition, final int toXPosition,
			final int toYPosition) {
		final SWTBot4diacGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(fromXPosition, fromYPosition, toXPosition, toYPosition);
		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();
	}

	/**
	 * Checks if Subapplication is selected by searching selectedEditParts list and
	 * returns the corresponding boolean value.
	 *
	 * @param selectedEditParts The List of selected EditParts.
	 * @param subAppName        The Subapplication that is searched for.
	 * @return true if Subapplication is in the List, otherwise false.
	 */
	@SuppressWarnings("static-method")
	public boolean isSubappSelected(final List<SWTBotGefEditPart> selectedEditParts, final String subAppName) {
		return selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> subAppName.equals(fb.getModel().getName()));
	}

}
