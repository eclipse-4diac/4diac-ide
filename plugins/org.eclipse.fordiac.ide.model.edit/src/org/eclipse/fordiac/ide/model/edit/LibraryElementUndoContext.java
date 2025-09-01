/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public class LibraryElementUndoContext implements IUndoContext {

	private final LibraryElement libraryElement;

	public LibraryElementUndoContext(final LibraryElement libraryElement) {
		Assert.isNotNull(libraryElement);
		this.libraryElement = libraryElement;
	}

	@Override
	public String getLabel() {
		return libraryElement.getName();
	}

	public LibraryElement getLibraryElement() {
		return libraryElement;
	}

	@Override
	public boolean matches(final IUndoContext context) {
		if (context instanceof final LibraryElementUndoContext libElUndoContext) {
			return getLibraryElement().equals(libElUndoContext.getLibraryElement());
		}

		// check if we have a context from an according XTextDocument editing parts of
		// this library element
		if ((context instanceof final ObjectUndoContext objUndoContext)
				&& (objUndoContext.getObject() instanceof final IAdaptable adapter)) {
			return getLibraryElement() == adapter.getAdapter(LibraryElement.class);
		}
		return false;
	}

}
