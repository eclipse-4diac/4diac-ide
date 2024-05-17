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
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.LiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class DataTypeInstanceSearch extends IEC61499ElementSearch {

	public DataTypeInstanceSearch(final DataTypeEntry dtEntry) {
		super(new LiveSearchContext(dtEntry.getTypeLibrary()), createSearchFilter(dtEntry),
				new DataTypeInstanceSearchChildrenProivder());
	}

	/*
	 * Search inside of a LibaryElement
	 *
	 */
	public DataTypeInstanceSearch(final LibraryElement typeEditable, final DataTypeEntry dtEntry) {
		super(new LibraryElementSearchContext(typeEditable), createSearchFilter(dtEntry),
				new DataTypeInstanceSearchChildrenProivder());
	}

	private static IEC61499SearchFilter createSearchFilter(final DataTypeEntry dtEntry) {
		return searchCandidate -> (searchCandidate instanceof final VarDeclaration varDecl
				&& dtEntry == varDecl.getType().getTypeEntry())
				|| (searchCandidate instanceof final ConfigurableFB configFb
						&& isConfiguredWithDataType(configFb, dtEntry));
	}

	private static boolean isConfiguredWithDataType(final ConfigurableFB confFB, final DataTypeEntry dtEntry) {
		if (confFB.getDataType() == null) {
			return false; // unconfigured FB, corresponds to ANY_STRUCT
		}
		if (confFB.getDataType() instanceof ErrorMarkerDataType || dtEntry.getType() instanceof ErrorMarkerDataType) {
			return confFB.getDataType().getTypeEntry().getFullTypeName().equals(dtEntry.getFullTypeName());
		}
		return dtEntry == confFB.getDataType().getTypeEntry();
	}

	private static final class DataTypeInstanceSearchChildrenProivder implements ISearchChildrenProvider {
		@Override
		public boolean hasChildren(final EObject obj) {
			return (obj instanceof FBType) || (obj instanceof AutomationSystem) || (obj instanceof UntypedSubApp)
					|| (obj instanceof final StructuredType) || (obj instanceof final AttributeDeclaration);
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			if (obj instanceof final FBType fbType) {
				return getFBTypeChildren(fbType);
			}
			if (obj instanceof final AutomationSystem system) {
				return system.getApplication().stream()
						.flatMap(app -> app.getFBNetwork().getNetworkElements().stream());
			}
			if (obj instanceof final UntypedSubApp untypedSubapp) {
				return getUntypedSubappChildren(untypedSubapp);
			}
			if (obj instanceof final StructuredType structType) {
				return getStructChildren(structType);
			}
			if (obj instanceof final AttributeDeclaration attrdecl) {
				return getAttributeDeclChildren(attrdecl);
			}
			return null;
		}

		private static Stream<? extends EObject> getFBTypeChildren(final FBType fbType) {
			Stream<? extends EObject> retval = getInterfaceListChildren(fbType.getInterfaceList());
			if (fbType instanceof final BaseFBType baseFBType) {
				retval = Stream.concat(retval, baseFBType.getInternalVars().stream());
				retval = Stream.concat(retval, baseFBType.getInternalConstVars().stream());
			}
			if (fbType instanceof final SubAppType subappType) {
				// we may have untyped subapps inside
				retval = Stream.concat(retval, subappType.getFBNetwork().getNetworkElements().stream());
			}
			return retval;
		}

		private static Stream<? extends EObject> getUntypedSubappChildren(final UntypedSubApp untypedSubapp) {
			Stream<? extends EObject> retval = getInterfaceListChildren(untypedSubapp.getInterface());
			retval = Stream.concat(retval, untypedSubapp.getSubAppNetwork().getNetworkElements().stream());
			return retval;
		}

		private static Stream<? extends EObject> getInterfaceListChildren(final InterfaceList interfaceList) {
			Stream<? extends EObject> retval = Stream.concat(interfaceList.getInputVars().stream(),
					interfaceList.getInputVars().stream());
			retval = Stream.concat(retval, interfaceList.getInOutVars().stream());
			return retval;
		}

		private static Stream<? extends EObject> getAttributeDeclChildren(final AttributeDeclaration attrdecl) {
			if (attrdecl.getType() instanceof final StructuredType structType) {
				return getStructChildren(structType);
			}
			if (attrdecl.getType() instanceof final DirectlyDerivedType directType) {
				return Stream.of(directType.getBaseType());
			}
			return null;
		}

		private static Stream<VarDeclaration> getStructChildren(final StructuredType structType) {
			return structType.getMemberVariables().stream();
		}

	}
}
