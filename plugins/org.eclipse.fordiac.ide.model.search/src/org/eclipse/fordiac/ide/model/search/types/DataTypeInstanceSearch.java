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

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.LiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DataTypeInstanceSearch extends IEC61499ElementSearch {

	public DataTypeInstanceSearch(final DataTypeEntry dtEntry) {
		super(new LiveSearchContext(dtEntry.getTypeLibrary()), createSearchFilter(dtEntry),
				new DataTypeInstanceSearchChildrenProvider());
	}

	public DataTypeInstanceSearch(final ErrorTypeEntry dtEntry, final TypeLibrary library) {
		super(new LiveSearchContext(library), createSearchFilter(dtEntry),
				new DataTypeInstanceSearchChildrenProvider());
	}

	/*
	 * Search inside of a LibaryElement
	 *
	 */
	public DataTypeInstanceSearch(final LibraryElement typeEditable, final DataTypeEntry dtEntry) {
		super(new LibraryElementSearchContext(typeEditable), createSearchFilter(dtEntry),
				new DataTypeInstanceSearchChildrenProvider());
	}

	private static IEC61499SearchFilter createSearchFilter(final TypeEntry dtEntry) {
		return searchCandidate -> (searchCandidate instanceof final VarDeclaration varDecl
				&& dtEntry == varDecl.getType().getTypeEntry())
				|| (searchCandidate instanceof final ConfigurableFB configFb
						&& isConfiguredWithDataType(configFb, dtEntry))
				|| (searchCandidate instanceof final Attribute attr && attr.getType().getTypeEntry() == dtEntry);
	}

	private static boolean isConfiguredWithDataType(final ConfigurableFB confFB, final TypeEntry dtEntry) {
		if (confFB.getDataType() == null || confFB.getDataType().getTypeEntry() == null) {
			return false; // unconfigured FB, corresponds to ANY_STRUCT
		}
		if (confFB.getDataType() instanceof ErrorMarkerDataType || dtEntry.getType() instanceof ErrorMarkerDataType) {
			return confFB.getDataType().getTypeEntry().getFullTypeName().equals(dtEntry.getFullTypeName());
		}
		return dtEntry == confFB.getDataType().getTypeEntry();
	}

	private static final class DataTypeInstanceSearchChildrenProvider implements ISearchChildrenProvider {
		@Override
		public boolean hasChildren(final EObject obj) {
			return (obj instanceof FBType) || (obj instanceof AutomationSystem) || (obj instanceof UntypedSubApp)
					|| (obj instanceof final StructuredType) || (obj instanceof final AttributeDeclaration)
					|| (obj instanceof final Application) || (obj instanceof Attribute)
					|| (obj instanceof FBNetworkElement) || (obj instanceof IInterfaceElement);
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			if (obj instanceof final FBType fbType) {
				return SearchChildrenProviderHelper.getFBTypeChildren(fbType);
			}
			if (obj instanceof final AutomationSystem system) {
				return Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
			}

			if (obj instanceof final Application application) {
				Stream<? extends EObject> stream = application.getFBNetwork().getNetworkElements().stream();
				stream = Stream.concat(stream, application.getFBNetwork().getAdapterConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getDataConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getEventConnections().stream());
				return Stream.concat(stream, application.getAttributes().stream());
			}

			if (obj instanceof final UntypedSubApp untypedSubapp) {
				return SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
			}
			if (obj instanceof final StructuredType structType) {
				return SearchChildrenProviderHelper.getStructChildren(structType);
			}
			if (obj instanceof final AttributeDeclaration attrdecl) {
				return SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
			}

			if (obj instanceof final FBNetworkElement elem) {
				return Stream.concat(elem.getAttributes().stream(),
						SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
			}

			if (obj instanceof final IInterfaceElement interfaceElement) {
				return interfaceElement.getAttributes().stream();
			}

			if (obj instanceof final Attribute atrr) {
				return Stream.of(atrr.getType());
			}

			if (obj instanceof final ConfigurableObject object) {
				return object.getAttributes().stream();
			}

			return null;
		}
	}
}
