/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.watch;

public interface ISubContainerWatch extends IContainerWatch {

	/**
	 * Get whether to watch sub-elements of this watch
	 */
	boolean isWatchSubElements();

	/**
	 * Set whether to watch sub-elements of this watch
	 *
	 * @param watchSubElements The value
	 */
	void setWatchSubElements(boolean watchSubElements);
}
