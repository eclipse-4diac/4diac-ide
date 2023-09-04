/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
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

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;

public class Abstract4diacUITests {

	protected static SWT4diacGefBot bot;
	private static final String APP = "App"; //$NON-NLS-1$
	private static final String FILE = "File"; //$NON-NLS-1$
	private static final String FINISH = "Finish"; //$NON-NLS-1$
	private static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	private static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	private static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	private static final String NEW = "New"; //$NON-NLS-1$
	private static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	private static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$

	/**
	 * Performs the necessary tasks to be able to perform the tests.
	 *
	 * The method creates the SWT4diacGefBot, starts 4diac IDE and closes the
	 * welcome window. The Timeout is increase from default value of 5 Seconds to 10
	 * Seconds. By calling the private method {@code createProject()} a new 4diac
	 * IDE project is created to be able to perform the tests.
	 */
	@BeforeAll
	protected static void beforeAll() {
		bot = new SWT4diacGefBot();
		bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
		SWTBotPreferences.TIMEOUT = 10000;
		createProject();
	}

	/**
	 * Creates a new 4diac IDE project
	 *
	 * The method creates a new 4diac IDE project with the static String of
	 * PROJECT_NAME and is called from {@link #beforeAll() method beforeAll}.
	 */
	protected static void createProject() {
		bot.menu(FILE).menu(NEW).menu(FORDIAC_IDE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(PROJECT_NAME_LABEL).setText(PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_SYSTEM_NAME_LABEL).getText(), PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), PROJECT_NAME + APP);
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Resets the workbench after
	 */
	@AfterClass
	public static void afterClass() {
		bot.resetWorkbench();
	}

}
