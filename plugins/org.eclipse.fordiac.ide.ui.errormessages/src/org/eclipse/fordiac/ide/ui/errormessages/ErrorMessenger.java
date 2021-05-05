/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.errormessages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;

public final class ErrorMessenger {

	private static boolean messagesPaused = false;
	private static List<ErrorMessage> pausedMessages = List.of();
	private static Integer lastHash;

	public static void pauseMessages() {
		messagesPaused = true;
		pausedMessages = new ArrayList<>();
	}

	public static List<ErrorMessage> unpauseMessages() {
		messagesPaused = false;
		return pausedMessages;
	}

	public static void hashCleared(final int hash) {
		if (lastHash != null && hash == lastHash.intValue()) {
			lastHash = null;
		}
	}

	/**
	 * This method sends an error message to be displayed This can be done from any
	 * plugin, as long as the message gets put on the message bus it will be shown
	 *
	 * @param the actual error message
	 *
	 */
	public static void popUpErrorMessage(final String errorMsg) {
		popUpErrorMessage(errorMsg, USE_DEFAULT_TIMEOUT);
	}

	public static void popUpErrorMessage(final String errorMsg, final int timeout) {
		final ErrorMessage m = new ErrorMessage(errorMsg, timeout);
		if (!(lastHash != null && m.hashCode() == lastHash.intValue())) {
			lastHash = Integer.valueOf(m.hashCode());
			if (messagesPaused) {
				pausedMessages.add(m);
				return;
			}

			if (null != eventBroker) {
				eventBroker.send(TOPIC_ERRORMESSAGES, m);
			}
		}
	}

	private static final String TOPIC_ERRORMESSAGES = "ORG/ECLIPSE/FORDIAC/IDE/ERRORMESSAGES"; //$NON-NLS-1$
	public static final int USE_DEFAULT_TIMEOUT = -1;

	private static IEventBroker initEventBroker() {
		// This initialization can fail if this is run as a simple JUnit-Test instead of
		// a JUnit-Plug-in-Test
		return EclipseContextFactory.getServiceContext(Activator.getDefault().getBundle().getBundleContext())
				.get(IEventBroker.class);
	}

	private static final IEventBroker eventBroker = initEventBroker();

	private ErrorMessenger() {
		throw new UnsupportedOperationException();
	}

}
