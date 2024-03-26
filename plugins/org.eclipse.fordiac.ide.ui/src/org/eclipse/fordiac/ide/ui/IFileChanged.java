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
 * The Interface IFileChanged.
 */
public interface IFileChanged {

	/**
	 * File changed.
	 * 
	 * @param newFile the new file - not guaranted that the file exists
	 */
	void fileChanged(String newFile);

}
