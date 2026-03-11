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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class FileSearchContext implements ISearchContext {

	private final TypeEntry entry;

	protected FileSearchContext(final IFile file) {
		this.entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
	}

	@Override
	public Stream<URI> getTypes() {
		return Stream.of(entry.getURI());
	}

	@Override
	public EObject mapTypes(final URI uri) {
		if (uri.equals(entry.getURI())) {
			return entry.copyType();
		}
		return null;
	}
}
