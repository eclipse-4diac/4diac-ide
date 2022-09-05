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
 *   Fabio Gandolfi - added search for transfer instance comments
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
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

	public List<INamedElement> getAllTypesWithStructFromSystem(final AutomationSystem sys) {
		searchResult = new StructSearchResult();
		searchApplications(sys);

		return searchResult.getStructSearchResults();
	}

	public List<INamedElement> getAllTypesWithStructFromNetworkElements(final EList<FBNetworkElement> elements) {
		searchResult = new StructSearchResult();
		searchGroupNetwork(elements);

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
				if (fbnetworkElement instanceof SubApp && ((SubApp) fbnetworkElement).getSubAppNetwork() != null) {
					searchFBNetwork(((SubApp) fbnetworkElement).getSubAppNetwork());
				}
				if (fbnetworkElement instanceof CFBInstance
						&& ((CFBInstance) fbnetworkElement).getCfbNetwork() != null) {
					searchFBNetwork(((CFBInstance) fbnetworkElement).getCfbNetwork());
				}
				if (fbnetworkElement instanceof Group && ((Group) fbnetworkElement).getFbNetwork() != null) {
					searchGroupNetwork(((Group) fbnetworkElement).getGroupElements());
				}
			}
		}
	}

	private void searchGroupNetwork(final EList<FBNetworkElement> groupElements) {
		if (groupElements != null) {
			for (final FBNetworkElement element : groupElements) {
				matchStruct(element);
				if (element instanceof Group && ((Group) element).getFbNetwork() != null) {
					searchGroupNetwork(((Group) element).getGroupElements());
				}
				if (element instanceof SubApp && ((SubApp) element).getSubAppNetwork() != null) {
					searchFBNetwork(((SubApp) element).getSubAppNetwork());
				}
				if (element instanceof CFBInstance && ((CFBInstance) element).getCfbNetwork() != null) {
					searchFBNetwork(((CFBInstance) element).getCfbNetwork());
				}
			}
		}
	}


	private void searchTypeLibrary(final AutomationSystem sys) {
		final TypeLibrary lib = sys.getTypeLibrary();
		for (final TypeEntry entry : lib.getFbTypes().values()) {
			matchStruct(entry.getType());
		}
		for (final SubAppTypeEntry entry : lib.getSubAppTypes().values()) {
			matchStruct(entry.getType());
			for (final FBNetworkElement fbNetworkElement : entry.getType().getFBNetwork().getNetworkElements()) {
				if (fbNetworkElement.getFbNetwork() != null) {
					searchFBNetwork(fbNetworkElement.getFbNetwork());
				}
				matchStruct(fbNetworkElement);
			}
		}
	}

	private void matchStruct(final EObject possibleStruct) {
		if (possibleStruct instanceof StructManipulator) {
			final StructManipulator sm = ((StructManipulator) possibleStruct);
			if (sm.getStructType().getName().equalsIgnoreCase(dataTypeEntry.getTypeName())
					&& !searchResult.getStructSearchResults().contains(possibleStruct)) {
				searchResult.addResult(sm);
			}
		}
	}
}
