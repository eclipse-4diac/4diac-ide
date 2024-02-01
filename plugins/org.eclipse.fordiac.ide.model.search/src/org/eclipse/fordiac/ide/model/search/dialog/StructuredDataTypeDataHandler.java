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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.search.types.StructManipulatorSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class StructuredDataTypeDataHandler extends AbstractTypeEntryDataHandler<DataTypeEntry> {
	public StructuredDataTypeDataHandler(final DataTypeEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	public HashMap<INamedElement, DataTypeEntry> createInputSet(final DataTypeEntry inputDataTypeEntry) {
		final HashMap<INamedElement, DataTypeEntry> inputElementsSet = new HashMap<>();

		// find SubAppTypes
		InstanceSearch search = StructDataTypeSearch
				.createStructInterfaceSearch((StructuredType) inputDataTypeEntry.getTypeEditable());
		// add types to input
		search.performTypeLibBlockSearch(inputDataTypeEntry.getTypeLibrary()).stream().filter(FBType.class::isInstance)
				.forEach(fb -> inputElementsSet.put(fb, inputDataTypeEntry));
		// initiate map with types
		inputElementsSet.keySet().stream().forEach(st -> children.put(st.getName(), new HashSet<>()));

		// add typed subapp instances as children
		final IProject project = typeEntry.getFile().getProject();
		new FBInstanceSearch(typeEntry).performProjectSearch(project).stream()
				.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast)
				.filter(fb -> fb instanceof final StructManipulator structmanipulator
						&& structmanipulator.getStructType().equals(typeEntry.getTypeEditable()))
				.forEach(s -> {
					if (s instanceof StructManipulator || (s instanceof final SubApp subApp && !subApp.isTyped())) {
						inputElementsSet.put(s, inputDataTypeEntry);
					} else if (!(s instanceof ErrorMarkerFBNElement)) {
						try {
							children.get(s.getTypeName()).add(s);
						} catch (final Exception e) {
							FordiacLogHelper.logError("FBUPDATE Dialog cant find TypeName: " + s.getTypeName()
									+ " of FBNE: " + s.getName() + " of class: " + s.getClass().getName()
									+ " in children List: " + children.toString());
							children.get(s.getTypeName()).add(s);
						}
					}
				});
		// add structmanipulators and untyped subapps to input
		InstanceSearch
				.performProjectSearch(project,
						StructDataTypeSearch
								.createStructMemberSearch((StructuredType) inputDataTypeEntry.getTypeEditable()),
						new StructManipulatorSearch(inputDataTypeEntry))
				.forEach(st -> inputElementsSet.put(st, inputDataTypeEntry));

		// add StructuredTypes
		search = StructDataTypeSearch.createStructMemberSearch((StructuredType) inputDataTypeEntry.getTypeEditable());
		final List<StructuredType> stTypes = search.searchStructuredTypes(inputDataTypeEntry.getTypeLibrary()).stream()
				.map(StructuredType.class::cast).toList();
		stTypes.forEach(st -> inputElementsSet.put(st, (DataTypeEntry) st.getTypeEntry()));
		stTypes.forEach(st -> inputElementsSet.putAll(createInputSet((DataTypeEntry) st.getTypeEntry())));

		return inputElementsSet;
	}

	public Set<INamedElement> performStructSearch() {
		final InstanceSearch structSearch = new StructManipulatorSearch(typeEntry);
		return structSearch.performCompleteSearch();
	}
}