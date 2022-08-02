/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   			   - Search partially taken from ModelSearchQuery
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class StructSearch {

	private StructSearchResult searchResult;
	private final DataTypeEntry dataTypeEntry;

	public StructSearch(final DataTypeEntry dataTypeEntry) {
		this.dataTypeEntry = dataTypeEntry;
	}

	public StructSearchResult getSearchResult() {
		return searchResult;
	}

	public List<INamedElement> getAllTypesWithStruct() {
		searchResult = new StructSearchResult();

		final List<AutomationSystem> searchRootSystems = new ArrayList<>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (final IProject proj : root.getProjects()) {
			if (proj.isOpen()) {
				searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(proj).values());
			}
		}

		for (final AutomationSystem sys : searchRootSystems) {
			searchApplications(sys);
			searchTypeLibrary(sys);
		}

		return searchResult.getStructSearchResults();
	}

	private void searchApplications(final AutomationSystem sys) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app);
		}
	}

	private void searchApplication(final Application app) {
		searchFBNetwork(app.getFBNetwork());
	}

	private void searchFBNetwork(final FBNetwork network) {
		if (network != null) {
			for (final FBNetworkElement fbnetworkElement : network.getNetworkElements()) {
				matchStruct(fbnetworkElement);
				if (fbnetworkElement instanceof SubApp) {
					searchFBNetwork(((SubApp) fbnetworkElement).getSubAppNetwork());
				}
			}
		}
	}

	private void searchTypeLibrary(final AutomationSystem sys) {
		final TypeLibrary lib = sys.getTypeLibrary();
		for (final TypeEntry entry : lib.getFbTypes().values()) {
			matchStruct(entry.getType());
		}
	}

	private void matchStruct(final EObject possibleStruct) {
		if (possibleStruct instanceof StructManipulator) {
			final StructManipulator sm = ((StructManipulator) possibleStruct);
			for (final IInterfaceElement interfaceElem : sm.getInterface().getAllInterfaceElements()) {
				if (interfaceElem.getType().getName().equalsIgnoreCase(dataTypeEntry.getTypeName())) {
					searchResult.addResult(sm);
				}
			}
		}
	}
}
