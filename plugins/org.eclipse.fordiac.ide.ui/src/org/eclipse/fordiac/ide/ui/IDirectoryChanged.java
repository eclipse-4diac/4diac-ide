/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

/**
 * The Interface IDirectoryChanged.
 */
public interface IDirectoryChanged {

	/**
	 * Directory changed.
	 * 
	 * @param newDirectory the new directory
	 */
	void directoryChanged(String newDirectory);

}
