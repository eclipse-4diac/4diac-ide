/**
 * Copyright (c) 2009-2019 THALES GLOBAL SERVICES
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - Initial API and implementation
 *      Prashantkumar Khatri -  Integrated selectPropertyTabItem and
 *      						createEvent from
 *      						"org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper"
 */
package org.eclipse.fordiac.ide.test.ui.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class SWTBotPropertySheet {

	/**
	 * SWTWorkbenchBot
	 */
	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	/**
	 * copied from
	 * org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper
	 *
	 * Select the tab with the name label in the property views.
	 *
	 * @param label         Label to find.
	 * @param propertiesBot the bot corresponding to the property view.
	 * @return true if the property tab is found, false otherwise
	 */
	@SuppressWarnings("restriction")
	public static boolean selectPropertyTabItem(final String label, final SWTBot propertiesBot) {
		final Matcher<TabbedPropertyList> matcher = Matchers
				.allOf(WidgetMatcherFactory.widgetOfType(TabbedPropertyList.class));
		final TabbedPropertyList widgets = propertiesBot.widget(matcher);
		final Boolean result = UIThreadRunnable.syncExec(SWTUtils.display(), (BoolResult) () -> {
			Boolean result1 = Boolean.FALSE;
			final Control[] children = widgets.getTabList();
			for (final Control control : children) {
				if (control.toString().equals(label)) {
					final Event mouseEvent = createEvent(control, control.getBounds().x, control.getBounds().y, 1,
							SWT.BUTTON1, 1);
					control.notifyListeners(SWT.MouseUp, mouseEvent);
					result1 = Boolean.TRUE;
					break; // quit the for
				}
			}
			return result1;
		});
		return result != null ? result.booleanValue() : false;
	}

	/**
	 * copied from
	 * org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper
	 *
	 * Create a event <br>
	 *
	 * @param x         the x coordinate of the mouse event.
	 * @param y         the y coordinate of the mouse event.
	 * @param button    the mouse button that was clicked.
	 * @param stateMask the state of the keyboard modifier keys.
	 * @param count     the number of times the mouse was clicked.
	 * @return an event that encapsulates {@link #widget} and {@link #display}
	 */
	public static Event createEvent(final Widget widget, final int x, final int y, final int button,
			final int stateMask, final int count) {
		final Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = bot.getDisplay();
		event.x = x;
		event.y = y;
		event.button = button;
		event.stateMask = stateMask;
		event.count = count;
		return event;
	}
}
