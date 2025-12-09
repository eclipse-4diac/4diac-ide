/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013 Profactor GbmH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved to an handler
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.service.prefs.BackingStoreException;

public class HideDataConnections extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Command command = event.getCommand();
		final boolean oldValue = HandlerUtil.toggleCommandState(command);
		final IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode(UIPreferenceConstants.FORDIAC_UI_PREFERENCES_ID);
		prefs.putBoolean(UIPreferenceConstants.P_HIDE_DATA_CON, !oldValue);
		try {
			prefs.flush();
		} catch (final BackingStoreException e) {
			FordiacLogHelper.logError(Messages.HandlerPreferenceSafeError, e);
		}
		return null;
	}

}
