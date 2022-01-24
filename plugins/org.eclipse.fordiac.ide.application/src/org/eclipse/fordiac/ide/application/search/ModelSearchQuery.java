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
package org.eclipse.fordiac.ide.application.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.search.internal.ui.text.SearchResultUpdater;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search2.internal.ui.SearchView;
import org.eclipse.swt.widgets.Display;

public class ModelSearchQuery implements ISearchQuery {

	private final ModelQuerySpec modelQuerySpec;
	private List<AutomationSystem> searchRoot;
	private ModelSearchResult searchResult;

	public ModelSearchQuery(final ModelQuerySpec modelQuerySpec) {
		this.modelQuerySpec = modelQuerySpec;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) throws OperationCanceledException {
		searchResult = getSearchResult();
		searchRoot = new ArrayList<>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (final IProject proj : root.getProjects()) {
			if (proj.isOpen()) {
				searchRoot.addAll(SystemManager.INSTANCE.getProjectSystems(proj).values());
			}
		}
		// TO DO: populate searchResult with something
		for (final AutomationSystem sys : searchRoot) {
			searchApplications(sys);
			searchTypeLibrary(sys);
			searchResult.addResult(LibraryElementFactory.eINSTANCE.createApplication());
			searchResult.addResult(LibraryElementFactory.eINSTANCE.createApplication());
			searchResult.addResult(LibraryElementFactory.eINSTANCE.createApplication());
		}

		Display.getDefault()
				.asyncExec(() -> ((SearchView) NewSearchUI.getSearchResultView()).showSearchResult(getSearchResult()));

		return Status.OK_STATUS;
	}

	private void searchTypeLibrary(final AutomationSystem sys) {
		final TypeLibrary lib = sys.getTypeLibrary();
		lib.getProject();
	}

	private void searchApplications(final AutomationSystem sys) {
		final EList<Application> apps = sys.getApplication();
		for (final Application app : apps) {
			searchApplication(app);
		}
	}

	private void searchApplication(final Application app) {
		for (final FBNetworkElement el : app.getFBNetwork().getNetworkElements()) {

			final String searchString = modelQuerySpec.getSearchString(); // Get the string user typed in
			String instance = "";
			final String pinName = "";
			FBType type = null;
			String comment = "";

			if (modelQuerySpec.isCheckedInstanceName() && el.getName() != null) { // [] Instance Name
				instance = el.getName();
			}
			if (modelQuerySpec.isCheckedPinName()) { // [] Pin Name
				// TO DO - find the pins
			}
			if (modelQuerySpec.isCheckedType() && el.getType() != null) { // [] Type
				type = el.getType();
			}
			if (modelQuerySpec.isCheckedComment() && el.getComment() != null) { // [] Comment
				comment = el.getComment();
			}

			if (el.getInterfaceElement(searchString) != null) { // if there is such an interface element
				el.getInterface().getAllInterfaceElements().stream().filter(pin -> searchString.equals(pin.getName()))
						.collect(Collectors.toList());
			}
		}
	}

	@Override
	public String getLabel() {
		return modelQuerySpec.toString();
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	// Has to return true so that NewSearchUI.runQueryInBackground(searchJob); in MSP can actually run
	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ModelSearchResult getSearchResult() {
		if (searchResult == null) {
			final ModelSearchResult mResult = new ModelSearchResult(this);
			new SearchResultUpdater(mResult);
			searchResult = mResult;
		}
		return searchResult;
	}

}
