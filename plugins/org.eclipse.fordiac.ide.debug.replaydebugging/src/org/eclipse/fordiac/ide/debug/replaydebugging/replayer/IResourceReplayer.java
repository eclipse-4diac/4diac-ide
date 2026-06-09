/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer;

import java.util.Optional;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;

/**
 * @brief Interface for replaying the execution of a resource
 *
 *        It provides methods for replaying the next event, getting the current
 *        state of the resource, forcing and clearing values and injecting
 *        events.
 */
public interface IResourceReplayer {

	/**
	 * @brief Replays the next event in the resource
	 *
	 * @return optional with the name of the event that was replayed, or an empty
	 *         optional if there are no more
	 */
	Optional<String> replayNextEvent();

	/**
	 * @brief Gets the current state of the resource
	 *
	 * @return the current state of the resource
	 */
	DatapointsState getCurrentState();

	/**
	 * @brief Forces a value in the resource
	 *
	 * @param name  of the value to be forced
	 * @param value to be forced
	 */
	void forceValue(String name, String value);

	/**
	 * @brief Clears a forced value in the resource
	 *
	 * @param name of the value to be cleared
	 */
	void clearForce(String name);

	/**
	 * @brief Sets the current state of the resource
	 *
	 * @param targetState to be set as the current state of the resource
	 */
	void setCurrentState(final DatapointsState targetState);

	/**
	 * @brief Injects an event into the resource
	 *
	 * @param name of the event to be injected
	 */
	void injectEvent(final String name);
}
