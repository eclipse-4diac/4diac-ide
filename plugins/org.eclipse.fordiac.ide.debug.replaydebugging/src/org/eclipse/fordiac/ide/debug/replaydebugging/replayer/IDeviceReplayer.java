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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer;

import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * @brief Interface for replaying the execution of a device
 *
 *        It provides methods for starting and stopping the replay
 */
public interface IDeviceReplayer {

	/**
	 * Starts the replay of the device. It returns a map with the resource replayers
	 * for each resource of the device
	 *
	 * @return a map with the resource replayers for each resource of the device
	 */
	Map<Resource, IResourceReplayer> start();

	/**
	 * Stops the replay of the device.
	 *
	 * @return true if the replay was stopped successfully, false otherwise
	 */
	boolean stop();

}
