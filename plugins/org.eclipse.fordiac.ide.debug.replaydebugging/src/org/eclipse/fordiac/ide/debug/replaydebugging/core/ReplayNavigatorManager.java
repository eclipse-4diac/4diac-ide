/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @brief Manages the registration and notification of replay navigators.
 *
 *        This class is a singleton that allows for the registration of replay
 *        navigators and notifies listeners when navigators are registered or
 *        unregistered.
 */
public class ReplayNavigatorManager {

	private static final ReplayNavigatorManager INSTANCE = new ReplayNavigatorManager();
	private final CopyOnWriteArrayList<IReplayNavigatorRegistrationListener> listeners = new CopyOnWriteArrayList<>();
	private final Map<ReplayNavigator.Identifier, ReplayNavigator> registeredNavigators = new ConcurrentHashMap<>();

	private ReplayNavigatorManager() {
		// Private constructor to enforce singleton pattern
	}

	public static ReplayNavigatorManager getDefault() {
		return INSTANCE;
	}

	public void addListener(final IReplayNavigatorRegistrationListener listener) {
		listeners.add(listener);
		// Notify already registered navigators to the new listener
		for (final ReplayNavigator replayNavigator : registeredNavigators.values()) {
			listener.replayNavigatorRegistered(replayNavigator);
		}
	}

	public void removeListener(final IReplayNavigatorRegistrationListener listener) {
		listeners.remove(listener);
	}

	public void registerNavigator(final ReplayNavigator navigator) {
		registeredNavigators.put(navigator.getIdentifier(), navigator);
		for (final IReplayNavigatorRegistrationListener listener : listeners) {
			listener.replayNavigatorRegistered(navigator);
		}
	}

	public void unregisterNavigator(final ReplayNavigator navigator) {
		registeredNavigators.remove(navigator.getIdentifier());
		for (final IReplayNavigatorRegistrationListener listener : listeners) {
			listener.replayNavigatorUnregistered(navigator);
		}
	}

}