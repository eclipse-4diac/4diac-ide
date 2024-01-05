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
 *   Dunja Životin, Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search2.internal.ui.SearchView;
import org.eclipse.swt.widgets.Display;

public class ModelSearchQuery implements ISearchQuery {

	private final ModelQuerySpec modelQuerySpec;
	private ModelSearchResult searchResult;

	public ModelSearchQuery(final ModelQuerySpec modelQuerySpec) {
		this.modelQuerySpec = modelQuerySpec;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) throws OperationCanceledException {
		getSearchResult().clear();
		final List<AutomationSystem> searchRootSystems = getRootSystems();
		performSearch(searchRootSystems);

		Display.getDefault()
				.asyncExec(() -> ((SearchView) NewSearchUI.getSearchResultView()).showSearchResult(getSearchResult()));

		return Status.OK_STATUS;
	}

	private List<AutomationSystem> getRootSystems() {
		final List<AutomationSystem> searchRootSystems = new ArrayList<>();

		if (modelQuerySpec.getScope() == SearchScope.PROJECT && modelQuerySpec.getProject() != null) {
			searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(modelQuerySpec.getProject()));
		} else {
			// workspace scope
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			for (final IProject proj : root.getProjects()) {
				if (proj.isOpen()) {
					searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(proj));
				}
			}
		}
		return searchRootSystems;
	}

	private void performSearch(final List<AutomationSystem> searchRootSystems) {
		for (final AutomationSystem sys : searchRootSystems) {
			searchApplications(sys);
			searchResources(sys);
			searchTypeLibrary(sys);
		}
	}

	public void searchApplications(final AutomationSystem sys) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app);
		}
	}

	public void searchApplication(final Application app) {
		if (matchEObject(app)) {
			searchResult.addResult(app);
		}
		searchFBNetwork(app.getFBNetwork(), new ArrayList<>());
	}

	private void searchFBNetwork(final FBNetwork network, final List<FBNetworkElement> path) {
		for (final FBNetworkElement fbnetworkElement : network.getNetworkElements()) {
			if (matchEObject(fbnetworkElement)) {
				if (!path.isEmpty()) {
					searchResult.getDictionary().addEntry(fbnetworkElement, path);
				}
				searchResult.addResult(fbnetworkElement);
			}
			if (fbnetworkElement instanceof final CFBInstance cfb) {
				searchFBNetwork(cfb.getType().getFBNetwork(), allocatePathList(path, cfb));
			}
			if (fbnetworkElement instanceof final SubApp subApp) {
				if (subApp.isTyped()) {
					searchFBNetwork(subApp.getType().getFBNetwork(), allocatePathList(path, subApp));
				} else if (subApp.getSubAppNetwork() != null) {
					searchFBNetwork(subApp.getSubAppNetwork(), path);
				}
			}
			if (modelQuerySpec.isCheckedPinName() && fbnetworkElement.getInterface() != null) {
				final List<EObject> matchingPins = fbnetworkElement.getInterface().getAllInterfaceElements().stream()
						.filter(pin -> pin.getName() != null && compareStrings(pin.getName()))
						.collect(Collectors.toList());
				if (!matchingPins.isEmpty()) {
					if (!path.isEmpty()) {
						searchResult.getDictionary().addEntry(fbnetworkElement, path);
					}
					searchResult.addResults(matchingPins);
				}
			}
		}
	}

	private static List<FBNetworkElement> allocatePathList(final List<FBNetworkElement> path,
			final FBNetworkElement elem) {
		final List<FBNetworkElement> list = new ArrayList<>(path);
		list.add(elem);
		return list;
	}

	private void searchResources(final AutomationSystem sys) {
		for (final Device dev : sys.getSystemConfiguration().getDevices()) {
			if (matchEObject(dev)) {
				searchResult.addResult(dev);
			}
			for (final Resource res : dev.getResource()) {
				if (matchEObject(res)) {
					searchResult.addResult(res);
				}
			}
		}
	}

	private void searchTypeLibrary(final AutomationSystem sys) {
		final TypeLibrary lib = sys.getTypeLibrary();
		searchTypeEntryList(lib.getFbTypes().values());
		searchTypeEntryList(lib.getAdapterTypes().values());
		searchTypeEntryList(lib.getDeviceTypes().values());
		searchTypeEntryList(lib.getResourceTypes().values());
		searchTypeEntryList(lib.getSegmentTypes().values());
		searchTypeEntryList(lib.getSubAppTypes().values());
	}

	private void searchTypeEntryList(final Collection<? extends TypeEntry> entries) {
		final List<EObject> foundEntries = entries.stream().map(TypeEntry::getType).filter(Objects::nonNull)
				.filter(this::matchEObject).collect(Collectors.toList());
		searchResult.addResults(foundEntries);
	}

	private boolean matchEObject(final EObject modelElement) {
		if (modelQuerySpec.isCheckedInstanceName() && modelElement instanceof INamedElement) {
			final String name = ((INamedElement) modelElement).getName();
			final boolean matchInstanceName = name != null && compareStrings(name);
			if (matchInstanceName) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedComment() && modelElement instanceof INamedElement) {
			final String comment = ((INamedElement) modelElement).getComment();
			final boolean matchComment = comment != null && compareStrings(comment);
			if (matchComment) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedType()) {
			if (modelElement instanceof final TypedConfigureableObject config) {
				return compareStrings(config.getFullTypeName());
			}
			if (modelElement instanceof final LibraryElement namElem) {
				return compareStrings(namElem.getName());
			}
		}
		return false;
	}

	private boolean compareStrings(final String toTest) {
		if (toTest == null) {
			return false;
		}
		final ModelSearchPattern pattern = new ModelSearchPattern(toTest, modelQuerySpec);
		if (pattern.matchSearchString()) {
			return true;
		}
		if (modelQuerySpec.isCheckExactMatching()) {
			return toTest.equals(modelQuerySpec.getSearchString());
		}
		if (modelQuerySpec.isCheckCaseSensitive()) {
			return toTest.contains(modelQuerySpec.getSearchString());
		}
		return toTest.toLowerCase().contains(modelQuerySpec.getSearchString().toLowerCase());
	}

	@Override
	public String getLabel() {
		return modelQuerySpec.getSearchString();
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	// Has to return true so that NewSearchUI.runQueryInBackground(searchJob); in
	// MSP can actually run
	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ModelSearchResult getSearchResult() {
		if (searchResult == null) {
			searchResult = new ModelSearchResult(this);
		}
		return searchResult;
	}

}
