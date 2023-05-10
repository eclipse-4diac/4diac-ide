/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
 *   Michael Oberlehner - extented to a more generic version
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class InstanceSearch {

	protected SearchFilter searchFilter;
	protected List<INamedElement> searchResult;

	public InstanceSearch(final SearchFilter filter) {
		this.searchFilter = filter;
		searchResult = new ArrayList<>();

	}

	public List<INamedElement> performApplicationSearch(final AutomationSystem sys) {
		searchResult = new ArrayList<>();
		searchApplications(sys);
		return searchResult;
	}

	public List<INamedElement> performTypeLibraryNetworkSearch(final TypeLibrary library) {
		searchResult = new ArrayList<>();
		searchTypeLibraryNetworks(library);
		return searchResult;
	}

	public List<INamedElement> searchStructuredTypes(final TypeLibrary library) {
		searchResult = new ArrayList<>();
		for (final StructuredType structuredType : library.getDataTypeLibrary().getStructuredTypes()) {
			match(structuredType);
		}
		return searchResult;
	}

	public List<INamedElement> performFBNetworkSearch(final FBNetwork fbNetwork) {
		searchResult = new ArrayList<>();
		if (fbNetwork != null) {
			searchFbNetworks(fbNetwork.getNetworkElements());
		}
		return searchResult;
	}

	public List<INamedElement> performCompleteSearch() {
		searchResult = new ArrayList<>();
		final List<AutomationSystem> searchRootSystems = new ArrayList<>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (final IProject proj : root.getProjects()) {
			if (proj.isOpen()) {
				searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(proj));
			}
		}

		for (final AutomationSystem sys : searchRootSystems) {
			searchApplications(sys);
			searchTypeLibraryNetworks(sys.getTypeLibrary());
		}

		return searchResult;
	}

	
	
	public List<INamedElement> performTypeLibBlockSearch(final TypeLibrary typeLibrary) {
		searchResult = new ArrayList<>();
		List<TypeEntry> allBlockTypes = new ArrayList<>();
		allBlockTypes.addAll(typeLibrary.getSubAppTypes().values());
		allBlockTypes.addAll(typeLibrary.getFbTypes().values());
		allBlockTypes.parallelStream().forEach(f-> match(f.getTypeEditable()));
		return searchResult;
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
				match(fbnetworkElement);
				if (fbnetworkElement instanceof SubApp && ((SubApp) fbnetworkElement).getSubAppNetwork() != null) {
					searchFBNetwork(((SubApp) fbnetworkElement).getSubAppNetwork());
				}
				if (fbnetworkElement instanceof CFBInstance
						&& ((CFBInstance) fbnetworkElement).getCfbNetwork() != null) {
					searchFBNetwork(((CFBInstance) fbnetworkElement).getCfbNetwork());
				}
				if (fbnetworkElement instanceof Group && ((Group) fbnetworkElement).getFbNetwork() != null) {
					searchFbNetworks(((Group) fbnetworkElement).getGroupElements());
				}
			}
		}
	}

	private void searchFbNetworks(final EList<FBNetworkElement> groupElements) {
		if (groupElements != null) {
			for (final FBNetworkElement element : groupElements) {
				match(element);
				if (element instanceof Group && ((Group) element).getFbNetwork() != null) {
					searchFbNetworks(((Group) element).getGroupElements());
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
	
	private void searchTypeLibraryNetworks(final TypeLibrary typeLibrary) {
		searchCFBNetworks(typeLibrary);
		searchSubappTypesNetworks(typeLibrary);
	}

	public void searchSubappTypesNetworks(final TypeLibrary typeLibrary) {
		for (final SubAppTypeEntry entry : typeLibrary.getSubAppTypes().values()) {
			if (entry.getTypeEditable().getFBNetwork() != null) {
				searchFBNetwork(entry.getTypeEditable().getFBNetwork());
			}
		}
	}

	public void searchCFBNetworks(final TypeLibrary typeLibrary) {
		for (final CompositeFBType entry : typeLibrary.getCompositeFBTypes()) {
			if (entry.getFBNetwork() != null) {
				searchFBNetwork(entry.getFBNetwork());
			}
		}
	}

	private void match(final INamedElement searchCandiate) {
		if (searchFilter.apply(searchCandiate)) {
			searchResult.add(searchCandiate);
		}
	}

}
