/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public interface ITypeEntryEditor extends IEditorPart {

	/**
	 * Perform all steps to reload the type from the file and update the editor
	 *
	 */
	void reloadType();

	/**
	 * Update the editor input to be used by this editor.
	 *
	 * This can occur if directories or projects are renamed or a file is moved to a
	 * different location and the editor is open. It will not result in a reload of
	 * the content.
	 *
	 * @param typeFile the new file to be used for the Type
	 */
	void setInput(IEditorInput input);

}
