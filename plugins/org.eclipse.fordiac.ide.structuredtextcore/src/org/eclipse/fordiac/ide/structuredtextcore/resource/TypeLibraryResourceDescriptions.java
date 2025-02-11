/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AbstractCompoundSelectable;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

public class TypeLibraryResourceDescriptions extends AbstractCompoundSelectable implements IResourceDescriptions {

	@Inject
	private IResourceServiceProvider.Registry registry;

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		return Iterables.transform(
				Iterables.concat(
						Iterables.transform(TypeLibraryManager.INSTANCE.getTypeLibraries(), TypeLibrary::getAllTypes)),
				this::getResourceDescription);
	}

	@Override
	public IResourceDescription getResourceDescription(final URI uri) {
		return getResourceDescription(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
	}

	public IResourceDescription getResourceDescription(final TypeEntry entry) {
		if (entry == null) {
			return null;
		}
		final IResourceServiceProvider resourceServiceProvider = registry.getResourceServiceProvider(entry.getURI());
		if (resourceServiceProvider == null) {
			return null;
		}
		final Manager manager = resourceServiceProvider.getResourceDescriptionManager();
		if (manager == null) {
			return null;
		}
		final LibraryElement type = entry.getType();
		if (type == null) {
			return null;
		}
		return manager.getResourceDescription(type.eResource());
	}

	@Override
	protected Iterable<? extends ISelectable> getSelectables() {
		return getAllResourceDescriptions();
	}

	@Override
	public boolean isEmpty() {
		return TypeLibraryManager.INSTANCE.getTypeLibraries().stream().map(TypeLibrary::getAllTypes)
				.allMatch(Collection::isEmpty);
	}
}
