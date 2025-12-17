/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
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

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;

public class AdapterTypeInstanceSearch extends IEC61499ElementSearch {

	public AdapterTypeInstanceSearch(final LibraryElement typeEditable, final AdapterTypeEntry adpEntry) {
		super(new LibraryElementSearchContext(typeEditable), createSearchFilter(adpEntry),
				new DataTypeInstanceSearchChildrenProvider());
	}

	private static IEC61499SearchFilter createSearchFilter(final AdapterTypeEntry adpEntry) {
		return searchCandidate -> (searchCandidate instanceof final AdapterDeclaration adpDecl
				&& adpEntry == adpDecl.getType().getTypeEntry());
	}

	private static final class DataTypeInstanceSearchChildrenProvider implements ISearchChildrenProvider {
		@Override
		public boolean hasChildren(final EObject obj) {
			return (obj instanceof FBType) || (obj instanceof AutomationSystem) || (obj instanceof UntypedSubApp)
					|| (obj instanceof final Application) || (obj instanceof FBNetworkElement);
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			if (obj instanceof final FBType fbType) {
				return getFBTypeChildren(fbType);
			}
			if (obj instanceof final AutomationSystem system) {
				return system.getApplication().stream();
			}

			if (obj instanceof final Application application) {
				return application.getFBNetwork().getNetworkElements().stream();
			}

			if (obj instanceof final UntypedSubApp untypedSubapp) {
				return getUntypedSubappChildren(untypedSubapp);
			}

			if (obj instanceof final BlockFBNetworkElement elem) {
				return Stream.concat(elem.getAttributes().stream(),
						SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
			}

			return Stream.empty();
		}

		private static Stream<AdapterDeclaration> getInterfaceListChildren(final InterfaceList interfaceList) {
			return Stream.concat(interfaceList.getSockets().stream(), interfaceList.getPlugs().stream());
		}

		private static Stream<? extends EObject> getFBTypeChildren(final FBType fbType) {
			Stream<? extends EObject> retval = getInterfaceListChildren(fbType.getInterfaceList());
			if (fbType instanceof final SubAppType subAppType) {
				// we may have a network with untyped subapps inside
				retval = Stream.concat(retval, subAppType.getFBNetwork().getNetworkElements().stream());
			}

			return retval;
		}

		private static Stream<? extends EObject> getUntypedSubappChildren(final UntypedSubApp untypedSubapp) {
			Stream<? extends EObject> retval = getInterfaceListChildren(untypedSubapp.getInterface());
			retval = Stream.concat(retval, untypedSubapp.getSubAppNetwork().getNetworkElements().stream());
			return retval;
		}
	}

}
