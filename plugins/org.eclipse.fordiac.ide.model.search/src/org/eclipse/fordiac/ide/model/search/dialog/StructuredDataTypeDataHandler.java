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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class StructuredDataTypeDataHandler extends AbstractTypeEntryDataHandler<DataTypeEntry> {
	public StructuredDataTypeDataHandler(final DataTypeEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	public HashMap<INamedElement, DataTypeEntry> createInputSet(final DataTypeEntry inputDataTypeEntry) {
		final HashMap<INamedElement, DataTypeEntry> inputElementsSet = new HashMap<>();
		handleStruct(inputDataTypeEntry, inputElementsSet);
		return inputElementsSet;
	}

	private static void handleVarDecl(final VarDeclaration varDecl,
			final HashMap<INamedElement, DataTypeEntry> inputElementsSet, final DataTypeEntry inputDataTypeEntry) {
		if (varDecl.eContainer() instanceof final StructuredType st) {
			final DataTypeEntry stTypeEntry = (DataTypeEntry) st.getTypeEntry();
			inputElementsSet.put(st, inputDataTypeEntry);
			if (stTypeEntry != null) {
				// for attributes the type entry is null and therefore we do not need to
				// continue our search
				handleStruct(stTypeEntry, inputElementsSet);
			}
		} else if (varDecl.eContainer() instanceof InterfaceList) {
			// we are either in an untyped subapp or in the interface of a FB or subapp type
			inputElementsSet.put((INamedElement) varDecl.eContainer().eContainer(), inputDataTypeEntry);

			if (varDecl.eContainer().eContainer() instanceof final FBType type) {
				final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(type.getTypeEntry());
				search.performSearch().stream().filter(INamedElement.class::isInstance).map(INamedElement.class::cast)
						.forEach(el -> inputElementsSet.put(el, inputDataTypeEntry));
			}
		} else if (varDecl.eContainer() instanceof final FBType type) {
			// internal variable
			inputElementsSet.put(type, inputDataTypeEntry);
		}
	}

	private static void handleStruct(final DataTypeEntry stTypeEntry,
			final HashMap<INamedElement, DataTypeEntry> inputElementsSet) {
		final DataTypeInstanceSearch dataTypeInstanceSearch = new DataTypeInstanceSearch(stTypeEntry);

		dataTypeInstanceSearch.performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				handleVarDecl(varDecl, inputElementsSet, stTypeEntry);
			} else if (obj instanceof final StructManipulator structMan) {
				inputElementsSet.put(structMan, stTypeEntry);
			}

		});
	}

	public List<? extends EObject> performStructSearch() {
		final DataTypeInstanceSearch structSearch = new DataTypeInstanceSearch(typeEntry);
		return structSearch.performSearch();
	}
}