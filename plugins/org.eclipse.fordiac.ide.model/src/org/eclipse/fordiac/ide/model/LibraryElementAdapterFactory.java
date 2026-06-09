/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.AdapterTypes;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

@AdapterTypes(adaptableClass = { LibraryElement.class }, adapterNames = { IFile.class })
public class LibraryElementAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (adapterType == IFile.class && adaptableObject instanceof final LibraryElement libEl
				&& libEl.getTypeEntry() != null) {
			return adapterType.cast(libEl.getTypeEntry().getFile());
		}
		return null;
	}

}
