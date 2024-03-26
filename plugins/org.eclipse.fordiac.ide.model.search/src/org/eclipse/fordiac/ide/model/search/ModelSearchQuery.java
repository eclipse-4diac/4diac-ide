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
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
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
		final List<ISearchContext> searchRootSystems = getSearchContexts();
		performSearch(searchRootSystems);

		Display.getDefault()
				.asyncExec(() -> ((SearchView) NewSearchUI.getSearchResultView()).showSearchResult(getSearchResult()));

		return Status.OK_STATUS;
	}

	private List<ISearchContext> getSearchContexts() {
		if (modelQuerySpec.getScope() == SearchScope.PROJECT && modelQuerySpec.getProject() != null) {
			return Arrays.asList(new LiveSearchContext(modelQuerySpec.getProject()));
		}
		// workspace scope
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return Arrays.stream(root.getProjects()).filter(IProject::isOpen).map(LiveSearchContext::new)
				.map(ISearchContext.class::cast).toList();
	}

	private void performSearch(final List<ISearchContext> searchContexts) {
		for (final ISearchContext context : searchContexts) {
			for (final URI libraryElementURI : context.getAllTypes()) {
				final LibraryElement libraryElement = context.getLibraryElement(libraryElementURI);
				if (libraryElement instanceof final AutomationSystem sys) {
					searchSystem(sys);
				} else if (matchTypeEntry(libraryElement)) {
					searchResult.addResult(libraryElement);
				}
			}
		}
	}

	private void searchSystem(final AutomationSystem sys) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app);
		}
		searchResources(sys);
	}

	private void searchApplication(final Application app) {
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
			if (fbnetworkElement.getType() instanceof final BaseFBType type) {
				for (final FB fb : type.getInternalFbs()) {
					if (matchEObject(fb)) {
						// add the containing fb to the path in order to print the instance name
						searchResult.getDictionary().addEntry(fb, allocatePathList(path, fbnetworkElement));
						searchResult.addResult(fb);
					}
				}
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
				final List<IInterfaceElement> matchingPins = fbnetworkElement.getInterface().getAllInterfaceElements()
						.stream().filter(pin -> pin.getName() != null && compareStrings(pin.getName())).toList();
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

	private boolean matchTypeEntry(final LibraryElement elem) {
		if (elem instanceof final CompositeFBType comp) {
			searchFBNetwork(comp.getFBNetwork(), new ArrayList<>());
		} else if (elem instanceof final BaseFBType type) {
			for (final FB fb : type.getInternalFbs()) {
				if (matchEObject(fb)) {
					searchResult.addResult(fb);
				}
			}
		}
		return matchEObject(elem);
	}

	private boolean matchEObject(final INamedElement modelElement) {
		if (modelQuerySpec.isCheckedInstanceName()) {
			final String name = modelElement.getName();
			final boolean matchInstanceName = name != null && compareStrings(name);
			if (matchInstanceName) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedComment()) {
			final String comment = modelElement.getComment();
			final boolean matchComment = comment != null && compareStrings(comment);
			if (matchComment) {
				return true;
			}
		}
		if (modelQuerySpec.isCheckedType()) {
			if (modelElement instanceof final TypedConfigureableObject config) {
				return compareStrings(config.getTypeName());
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
			searchResult = createModelSearchResult();
		}
		return searchResult;
	}

	protected ModelSearchResult createModelSearchResult() {
		return new ModelSearchResult(this);
	}

}
