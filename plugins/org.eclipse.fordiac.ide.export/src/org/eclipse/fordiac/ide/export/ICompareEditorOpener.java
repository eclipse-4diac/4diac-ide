/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
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
package org.eclipse.fordiac.ide.export;

import java.io.File;

/**
 * The Interface ICompareEditorOpener.
 */
public interface ICompareEditorOpener {

	/**
	 * Checks for differences.
	 * 
	 * @return true, if successful
	 */
	boolean hasDifferences();

	/**
	 * Sets the original file.
	 * 
	 * @param original the new original file
	 */
	void setOriginalFile(File original);

	/**
	 * Sets the new file.
	 * 
	 * @param newFile the new new file
	 */
	void setNewFile(File newFile);

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	void setTitle(String title);

	/**
	 * Open compare editor.
	 */
	void openCompareEditor();

}
