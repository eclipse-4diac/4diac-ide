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
package org.eclipse.fordiac.ide.model.search.types;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class BlockTypeInstanceSearch extends IEC61499ElementSearch {

	public BlockTypeInstanceSearch(final TypeEntry entry) {
		super(createSearchContext(entry),
				searchCanditate -> searchCanditate instanceof final TypedConfigureableObject tco
						&& entry == tco.getTypeEntry(),
				createChildrenProvider());
	}

	public BlockTypeInstanceSearch(final Collection<TypeEntry> entries) {
		super(createSearchContext(entries.iterator().next()),
				searchCanditate -> searchCanditate instanceof final TypedConfigureableObject tco
						&& entries.contains(tco.getTypeEntry()),
				createChildrenProvider());
	}

	/*
	 * Search inside of a LibaryElement
	 *
	 */
	public BlockTypeInstanceSearch(final LibraryElement typeEditable, final TypeEntry entry) {
		super(new LibraryElementSearchContext(typeEditable),
				searchCanditate -> searchCanditate instanceof final TypedConfigureableObject tco
						&& entry == tco.getTypeEntry(),
				createChildrenProvider());
	}

	private static ISearchContext createSearchContext(final TypeEntry entry) {

		return new AbstractLiveSearchContext(entry.getFile().getProject()) {

			@Override
			public Stream<URI> getTypes() {
				final Stream<SystemEntry> systems = getTypelib().getSystems().values().stream();
				final Stream<FBTypeEntry> fbs = getTypelib().getFbTypes().values().stream();
				final Stream<SubAppTypeEntry> subApps = getTypelib().getSubAppTypes().values().stream();
				return Stream.concat(systems, Stream.concat(fbs, subApps)).map(TypeEntry::getURI)
						.filter(Objects::nonNull);
			}

			@Override
			public Collection<URI> getSubappTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getFBTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getAllTypes() {
				return Collections.emptyList();
			}
		};
	}

	private static ISearchChildrenProvider createChildrenProvider() {
		return new ISearchChildrenProvider() {

			@Override
			public boolean hasChildren(final EObject obj) {
				return obj instanceof AutomationSystem || obj instanceof FBType || obj instanceof UntypedSubApp;
			}

			@Override
			public Stream<? extends EObject> getChildren(final EObject obj) {
				if (obj instanceof final AutomationSystem system) {
					return system.getApplication().stream()
							.flatMap(app -> app.getFBNetwork().getNetworkElements().stream());
				}
				if (obj instanceof FBType) {
					if (obj instanceof final BaseFBType baseFBType) {
						return baseFBType.getInternalFbs().stream();
					}
					if (obj instanceof final CompositeFBType cfbType) {
						return cfbType.getFBNetwork().getNetworkElements().stream();
					}
					if (obj instanceof final SubAppType subAppType) {
						return subAppType.getFBNetwork().getNetworkElements().stream();
					}
				}
				if (obj instanceof final UntypedSubApp utSubApp) {
					return utSubApp.getSubAppNetwork().getNetworkElements().stream();
				}

				return Stream.empty();
			}
		};
	}

}
