/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.handlers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.handlers.HandlerUtil;

public class ContributeHandler extends AbstractHandler {
	private static final String FORDIAC_CONTRIBUTION_URL = "https://eclipse.dev/4diac/en_contribute.php"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		if (null != activeWorkbenchWindow) {
			IWebBrowser browser;
			try {
				browser = activeWorkbenchWindow.getWorkbench().getBrowserSupport().createBrowser(getClass().getName());
				browser.openURL(new URI(FORDIAC_CONTRIBUTION_URL).toURL());
			} catch (PartInitException | MalformedURLException | URISyntaxException e) {
				FordiacLogHelper.logError("Error in opening the 4diac contribution web-page!", e); //$NON-NLS-1$
			}
		}

		return Status.OK_STATUS;
	}
}
