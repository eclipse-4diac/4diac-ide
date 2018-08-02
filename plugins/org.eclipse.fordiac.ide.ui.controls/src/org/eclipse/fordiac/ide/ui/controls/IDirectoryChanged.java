/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.controls;

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
