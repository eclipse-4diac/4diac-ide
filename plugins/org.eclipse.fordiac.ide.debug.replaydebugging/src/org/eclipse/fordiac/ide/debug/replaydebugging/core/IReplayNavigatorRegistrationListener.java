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

public interface IReplayNavigatorRegistrationListener {

	/**
	 * Notifies the listener that a ReplayNavigator has been registered.
	 *
	 * @param navigator The ReplayNavigator instance that was registered.
	 */
	void replayNavigatorRegistered(ReplayNavigator navigator);

	/**
	 * Notifies the listener that a ReplayNavigator has been unregistered.
	 *
	 * @param navigator The ReplayNavigator instance that was unregistered.
	 */
	void replayNavigatorUnregistered(ReplayNavigator navigator);
}