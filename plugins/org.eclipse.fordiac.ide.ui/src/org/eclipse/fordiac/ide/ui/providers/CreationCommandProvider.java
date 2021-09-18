/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Bianca Wiesmayr - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.providers;

/**
 * Interface for a CommandProvider that returns the created element
 */
public interface CreationCommandProvider {

	/**
	 * Provide a command for the given reference element
	 *
	 * @param refElement reference element for the command
	 * @return the command
	 */
	CreationCommand getCommand(Object refElement);

}
