/*******************************************************************************
 * Copyright (c) 2022-2024 Primetals Technologies Austria GmbH
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
 *   Ernst Blecha
 *    - add cancelation of search
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.Match;
import org.eclipse.search2.internal.ui.SearchView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ModelSearchQuery implements ISearchQuery {

	private final ModelQuerySpec modelQuerySpec;
	private final ModelSearchPattern pattern;
	private ModelSearchResult searchResult;
	private boolean isIncompleteResult = false; // errors in resolved st code

	public ModelSearchQuery(final ModelQuerySpec modelQuerySpec) {
		this.modelQuerySpec = modelQuerySpec;
		pattern = new ModelSearchPattern(modelQuerySpec);
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) throws OperationCanceledException {
		getSearchResult().clear();
		final List<ISearchContext> searchRootSystems = getSearchContexts();
		try {
			performSearch(searchRootSystems, monitor);
		} catch (final SearchCanceledException e) {
			return Status.CANCEL_STATUS;
		}

		Display.getDefault()
				.asyncExec(() -> ((SearchView) NewSearchUI.getSearchResultView()).showSearchResult(getSearchResult()));

		if (isIncompleteResult) {
			Display.getDefault()
					.asyncExec(() -> MessageDialog.openWarning(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							Messages.STSearchErrorDialog_Title, Messages.STSearchErrorDialog_Body));
		}

		return Status.OK_STATUS;
	}

	private List<ISearchContext> getSearchContexts() {
		if (modelQuerySpec.scope() == SearchScope.PROJECT && modelQuerySpec.project() != null) {
			return Arrays.asList(new LiveSearchContext(modelQuerySpec.project()));
		}
		// workspace scope
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return Arrays.stream(root.getProjects()).filter(IProject::isOpen).map(LiveSearchContext::new)
				.map(ISearchContext.class::cast).toList();
	}

	private void performSearch(final List<ISearchContext> searchContexts, final IProgressMonitor monitor) {
		for (final ISearchContext context : searchContexts) {
			context.getTypes().forEach(libraryElementURI -> {
				final LibraryElement libraryElement = context.getLibraryElement(libraryElementURI);
				if (libraryElement instanceof final AutomationSystem sys) {
					searchSystem(sys, monitor);
				} else if (libraryElement != null) {
					if (matchTypeEntry(libraryElement, monitor)) {
						searchResult.addResult(libraryElement);
					}
				} else {
					FordiacLogHelper.logWarning("Could not load model for: " + libraryElementURI.toString()); ////$NON-NLS-1$
				}
			});
		}
	}

	private void searchSystem(final AutomationSystem sys, final IProgressMonitor monitor) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app, monitor);
		}
		searchResources(sys, monitor);
	}

	/**
	 * (mis)used for search in 4diac Analytics until this functionality is
	 * refactored out of this class
	 *
	 * @deprecated searchApplication can take a long time and should not be used
	 *             without considering use of a IProgressMonitor for cancellation
	 *
	 * @param app
	 */
	@Deprecated
	public void searchApplication(final Application app) {
		this.searchApplication(app, null);
	}

	private void searchApplication(final Application app, final IProgressMonitor monitor) {
		if (matchEObject(app, monitor)) {
			searchResult.addResult(app);
		}
		searchFBNetwork(app.getFBNetwork(), new ArrayList<>(), monitor);
	}

	private void searchFBNetwork(final FBNetwork network, final List<FBNetworkElement> path,
			final IProgressMonitor monitor) {
		for (final FBNetworkElement fbnetworkElement : network.getNetworkElements()) {
			if (matchEObject(fbnetworkElement, monitor)) {
				if (!path.isEmpty()) {
					searchResult.getDictionary().addEntry(fbnetworkElement, path);
				}
				searchResult.addResult(fbnetworkElement);
			}
			if (fbnetworkElement.getType() instanceof final BaseFBType type) {
				for (final FB fb : type.getInternalFbs()) {
					if (matchEObject(fb, monitor)) {
						// add the containing fb to the path in order to print the instance name
						searchResult.getDictionary().addEntry(fb, allocatePathList(path, fbnetworkElement));
						searchResult.addResult(fb);
					}
				}
			}
			if (fbnetworkElement instanceof final ConfigurableFB conf && matchEObject(conf.getDataType(), monitor)) {
				searchResult.addResult(conf);
			}
			if (fbnetworkElement instanceof final SubApp subApp && !subApp.isTyped()
					&& subApp.getSubAppNetwork() != null) {
				searchFBNetwork(subApp.getSubAppNetwork(), path, monitor);
			}
			if (fbnetworkElement.getInterface() != null) {
				if (modelQuerySpec.checkPinName()) {
					final List<IInterfaceElement> matchingPins = fbnetworkElement.getInterface()
							.getAllInterfaceElements().stream()
							.filter(pin -> pin.getName() != null && compareStrings(pin.getName())).toList();
					if (!matchingPins.isEmpty()) {
						if (!path.isEmpty()) {
							searchResult.getDictionary().addEntry(fbnetworkElement, path);
						}
						searchResult.addResults(matchingPins);
					}
				}
				if (modelQuerySpec.checkType()) {
					searchInterface(fbnetworkElement.getInterface(), monitor);
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

	private void searchResources(final AutomationSystem sys, final IProgressMonitor monitor) {
		for (final Device dev : sys.getSystemConfiguration().getDevices()) {
			if (matchEObject(dev, monitor)) {
				searchResult.addResult(dev);
			}
			for (final Resource res : dev.getResource()) {
				if (matchEObject(res, monitor)) {
					searchResult.addResult(res);
				}
			}
		}
	}

	private boolean matchTypeEntry(final LibraryElement elem, final IProgressMonitor monitor) {
		switch (elem) {
		case final CompositeFBType comp -> searchFBNetwork(comp.getFBNetwork(), new ArrayList<>(), monitor);
		case final BaseFBType type -> {
			for (final FB fb : type.getInternalFbs()) {
				if (matchEObject(fb, monitor)) {
					searchResult.addResult(fb);
				}
			}
			for (final VarDeclaration varDecl : type.getInternalVars()) {
				if (matchEObject(varDecl, monitor)) {
					searchResult.addResult(varDecl);
				}
			}
			for (final Algorithm algo : type.getAlgorithm()) {
				if (matchEObject(algo, monitor)) {
					searchResult.addResult(algo);
				}
			}
			for (final Method meth : type.getMethods()) {
				if (matchEObject(meth, monitor)) {
					searchResult.addResult(meth);
				}
			}
		}
		case final ArrayType array -> {
			final DataType base = array.getBaseType();
			if (matchEObject(base, monitor)) {
				searchResult.addResult(base);
			}
		}
		case final StructuredType struct -> matchStruct(struct, monitor);
		case final AttributeDeclaration attDecl -> {
			if (attDecl.getType() instanceof final StructuredType struct) {
				matchStruct(struct, monitor);
			} else if (matchEObject(attDecl.getType(), monitor)) {
				searchResult.addResult(attDecl.getType());
			}
		}
		default -> {
			// no default case
		}
		}

		if (elem instanceof final FBType type && type.getInterfaceList() != null) {
			searchInterface(type.getInterfaceList(), monitor);
		}
		return matchEObject(elem, monitor);
	}

	private void matchStruct(final StructuredType struct, final IProgressMonitor monitor) {
		for (final VarDeclaration varDecl : struct.getMemberVariables()) {
			if (varDecl.isArray()) {
				if (matchEObject(varDecl.getType(), monitor)) {
					searchResult.addResult(varDecl);
				}
			} else if (matchEObject(varDecl, monitor)) {
				searchResult.addResult(varDecl);
			}
		}
	}

	private void searchInterface(final InterfaceList interfaceList, final IProgressMonitor monitor) {
		Stream.concat(Stream.concat(interfaceList.getInputs(), interfaceList.getOutputVars().stream()),
				Stream.concat(interfaceList.getEventOutputs().stream(), interfaceList.getPlugs().stream()))
				.filter((final INamedElement modelElement) -> this.matchEObject(modelElement, monitor))
				.forEach(searchResult::addResult);
	}

	private boolean matchEObject(final INamedElement modelElement, final IProgressMonitor monitor) {
		SearchCanceledException.throwIfCanceled(monitor);
		if (modelQuerySpec.checkInstanceName()) {
			final String name = modelElement.getName();
			final boolean matchInstanceName = name != null && compareStrings(name);
			if (matchInstanceName) {
				if (modelElement instanceof Algorithm || modelElement instanceof Method
						|| modelElement instanceof FunctionFBType) {
					addMatchesForSTOccurance(modelElement);
				}
				return true;
			}
		}
		if (modelQuerySpec.checkComments()) {
			final String comment = modelElement.getComment();
			final boolean matchComment = comment != null && compareStrings(comment);
			if (matchComment) {
				return true;
			}
		}
		if (modelQuerySpec.checkType()) {
			if (modelElement instanceof Algorithm || modelElement instanceof Method
					|| modelElement instanceof FunctionFBType) {
				addMatchesForSTOccurance(modelElement);
				return searchResult.getMatchCount(modelElement) > 0;
			}
			if (modelElement instanceof final TypedConfigureableObject config) {
				return compareStrings(config.getTypeName())
						|| (config.getTypeEntry() != null && compareStrings(config.getTypeEntry().getFullTypeName()));
			}
			if (modelElement instanceof final LibraryElement namElem) {
				return compareStrings(namElem.getName())
						|| (namElem.getTypeEntry() != null && compareStrings(namElem.getTypeEntry().getFullTypeName()));
			}
			if (modelElement instanceof final VarDeclaration varDecl) {
				return compareStrings(varDecl.getTypeName())
						|| (varDecl.getType() != null && varDecl.getType().getTypeEntry() != null
								&& compareStrings(varDecl.getType().getTypeEntry().getFullTypeName()));
			}
		}
		return false;
	}

	private void addMatchesForSTOccurance(final INamedElement modelElement) {
		ISearchSupport searchSupport = null;
		if (modelElement instanceof final FunctionFBType function
				&& function.getBody() instanceof final STFunctionBody functionBody) {
			searchSupport = ISearchFactory.Registry.INSTANCE.getFactory(STFunctionBody.class)
					.createSearchSupport(functionBody);
		} else if (modelElement instanceof STAlgorithm) {
			searchSupport = ISearchFactory.Registry.INSTANCE.getFactory(STAlgorithm.class)
					.createSearchSupport(modelElement);
		} else if (modelElement instanceof STMethod) {
			searchSupport = ISearchFactory.Registry.INSTANCE.getFactory(STMethod.class)
					.createSearchSupport(modelElement);
		}

		if (searchSupport != null) {
			try {
				final IModelMatcher matcher = new STMatcher(this::compareStrings);
				searchSupport.search(matcher).filter(TextMatch.class::isInstance).map(TextMatch.class::cast)
						.map(match -> new Match(modelElement, Match.UNIT_LINE, match.getOffset(), match.getLine() + 1))
						.forEach(match -> searchResult.addMatch(match));
			} catch (final CoreException e) {
				isIncompleteResult = true;
			}
		}
	}

	private boolean compareStrings(final String toTest) {
		if (toTest == null) {
			return false;
		}

		if (pattern.matchSearchString(toTest)) {
			return true;
		}
		if (modelQuerySpec.checkExactMatching()) {
			return toTest.equals(modelQuerySpec.searchString());
		}
		if (modelQuerySpec.checkCaseSensitive()) {
			return toTest.contains(modelQuerySpec.searchString());
		}
		return toTest.toLowerCase().contains(modelQuerySpec.searchString().toLowerCase());
	}

	@Override
	public String getLabel() {
		return modelQuerySpec.searchString();
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

	private static class SearchCanceledException extends RuntimeException {
		// This is a RuntimeException - otherwise this cannot be used within lambdas
		private static final long serialVersionUID = 1L;

		public static void throwIfCanceled(final IProgressMonitor monitor) {
			if (monitor != null && monitor.isCanceled()) {
				throw new SearchCanceledException();
			}
		}
	}

}
