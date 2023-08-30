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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FirstTry {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void executeExit() {
		final SWTBotMenu fileMenu = bot.menu("File"); //$NON-NLS-1$
		assertNotNull(fileMenu);
	}

	@Test
	public void canCreateANewJavaProject() throws Exception {
		bot.menu("File").menu("New").menu("4diac IDE Project...").click(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		bot.textWithLabel("Project name:").setText("UiTestProject"); //$NON-NLS-1$ //$NON-NLS-2$
		bot.button("Finish").click(); //$NON-NLS-1$
	}

	@AfterEach
	public void sleep() {
		bot.sleep(2000);
	}
}
