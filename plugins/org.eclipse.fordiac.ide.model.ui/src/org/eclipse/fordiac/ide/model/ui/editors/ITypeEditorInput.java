/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.ui.IFileEditorInput;

public interface ITypeEditorInput extends IFileEditorInput, IContentEditorInput {
	/**
	 * Get the content of this input
	 *
	 * @return The content
	 */

	@Override
	LibraryElement getContent();

	/**
	 * Get the type entry associated with this input
	 *
	 * @return The type entry
	 */
	TypeEntry getTypeEntry();
}
