/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - use library element provider
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.commands.CreateAttributeBulkEditorCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.DeleteAttributeBulkEditorCommand;
import org.eclipse.fordiac.ide.bulkeditor.nattable.BulkEditorNatTable;
import org.eclipse.fordiac.ide.bulkeditor.nattable.DelegatingSelectionProvider;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryViewer;
import org.eclipse.fordiac.ide.bulkeditor.search.EditorSearchExecutor;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.QuerySearchAdapter;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchHelper;
import org.eclipse.fordiac.ide.bulkeditor.ui.AddAttributeTreeSelectionDialog;
import org.eclipse.fordiac.ide.gef.commands.OperationHistoryCommandStack;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementStateListener;
import org.eclipse.fordiac.ide.model.ui.editors.MultiLibraryElementActivationListener;
import org.eclipse.fordiac.ide.model.ui.editors.MultiLibraryElementOperationContextUpdater;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.SaveablesLifecycleEvent;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.MultiPageEditorPart;

public class BulkEditor extends MultiPageEditorPart
		implements CommandExecutor, CommandStackEventListener, ISaveablesSource {

	private static final String CONTEXT_ID = "org.eclipse.fordiac.ide.bulkeditor"; //$NON-NLS-1$

	private static final int QUERY_PAGE_INDEX = 0;
	private static final int RESULT_PAGE_INDEX = 1;

	private IProject project;
	private final DelegatingSelectionProvider selectionProviderDelegate = new DelegatingSelectionProvider();

	private final Set<IEditorInput> editorInputs = new HashSet<>();
	private final OperationHistoryCommandStack commandStack = new OperationHistoryCommandStack();
	private final MultiLibraryElementOperationContextUpdater operationContextUpdater = new MultiLibraryElementOperationContextUpdater(
			commandStack::getUndoContext, editorInputs);
	private final EditorStateListener elementStateListener = new EditorStateListener();
	private MultiLibraryElementActivationListener activationListener;
	private ActionRegistry actionRegistry;

	private BulkEditorNatTable natTable;
	private QueryViewer queryViewer;
	private final QuerySaveable querySaveable = new QuerySaveable(() -> queryViewer, this::getTitle);

	// Latest successful search result (mapped to editable counterparts).
	private List<EObject> editableSearchResult;
	private Set<URI> searchScope;
	private PlaceConfig latestQueryConfig;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		registerActions(site);
		super.init(site, input);
		commandStack.addCommandStackEventListener(this);
		OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(operationContextUpdater);
		LibraryElementProvider.INSTANCE.addLibraryElementStateListener(elementStateListener);
		activationListener = new MultiLibraryElementActivationListener(this, editorInputs);

		if (input instanceof final BulkEditorInput bulkEditorInput) {
			this.project = bulkEditorInput.getProject();
			commandStack.setUndoContext(new ObjectUndoContext(project));
			setPartName(getPartName() + ": " + project.getName()); //$NON-NLS-1$
		}
	}

	private void registerActions(final IEditorSite site) {
		actionRegistry = new ActionRegistry();

		final IAction undoAction = new UndoAction(this);
		undoAction.setActionDefinitionId(ActionFactory.UNDO.getCommandId());
		actionRegistry.registerAction(undoAction);
		final IAction redoAction = new RedoAction(this);
		redoAction.setActionDefinitionId(ActionFactory.REDO.getCommandId());
		actionRegistry.registerAction(redoAction);

		final IActionBars bars = site.getActionBars();
		bars.setGlobalActionHandler(ActionFactory.UNDO.getId(), actionRegistry.getAction(ActionFactory.UNDO.getId()));
		bars.setGlobalActionHandler(ActionFactory.REDO.getId(), actionRegistry.getAction(ActionFactory.REDO.getId()));
		bars.updateActionBars();

		final IContextService contextService = site.getService(IContextService.class);
		contextService.activateContext(CONTEXT_ID);
	}

	@Override
	protected void createPages() {
		createQueryViewerPage();
		createResultPage();
		getSite().setSelectionProvider(selectionProviderDelegate);
	}

	private void createResultPage() {
		final Composite pageComposite = new Composite(getContainer(), SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(pageComposite);

		natTable = new BulkEditorNatTable(pageComposite, this, BulkEditorMode.VARIABLE, getSite(),
				selectionProviderDelegate);
		pageComposite.layout();

		final int index = addPage(pageComposite);
		setPageText(index, Messages.Tab_Result);
	}

	private void createQueryViewerPage() {
		final Composite pageComposite = new Composite(getContainer(), SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(pageComposite);
		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		pageComposite.setLayoutData(gd);

		queryViewer = new QueryViewer(pageComposite, this.project, this);
		queryViewer.getCommandStack().addCommandStackEventListener(this);
		if (getEditorInput() instanceof final BulkEditorInput bulkEditorInput) {
			queryViewer.restoreSnapshot(bulkEditorInput.getQuery());
			bulkEditorInput.setQuerySnapshotSupplier(queryViewer::createSnapshot);
		}
		pageComposite.layout();

		final int index = addPage(pageComposite);
		setPageText(index, Messages.Tab_Query);
	}

	public IProject getProject() {
		return project;
	}

	public void showSubappHierarchyScope(final List<String> hierarchy) {
		queryViewer.setSubappHierarchyScope(hierarchy);
		setActivePage(QUERY_PAGE_INDEX);
	}

	public boolean promptToSaveQuery() {
		return promptToSave(querySaveable);
	}

	public void onSearchRequested() {
		if (!promptToSave(getResultSaveables())) {
			return;
		}
		if (!performSearch()) {
			return;
		}

		setActivePage(RESULT_PAGE_INDEX);
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);

		switch (newPageIndex) {
		case 0 -> selectionProviderDelegate.setActiveProvider(queryViewer.getSelectionProvider());
		case 1 -> selectionProviderDelegate.setActiveProvider(natTable.getCurrentProvider());
		default -> selectionProviderDelegate.setActiveProvider(null);
		}
		updateStackActions();
		firePropertyChange(PROP_DIRTY);
	}

	private boolean performSearch() {
		final EObject queryRoot = queryViewer.getQueryRoot();
		if (queryRoot == null) {
			return false;
		}

		final var mode = QuerySearchAdapter.resolveTargetMode(queryRoot);

		final EditorSearchExecutor.Result result = EditorSearchExecutor.search(queryRoot, project);
		if (result == null) {
			return false;
		}

		changeNatTable(mode, result.attributeTypeEntry() != null ? result.attributeTypeEntry().getType() : null);

		this.latestQueryConfig = result.placeConfig();
		this.searchScope = result.searchScope();

		fireSaveablesEvent(SaveablesLifecycleEvent.POST_CLOSE, getResultSaveables());
		disconnectEditorInputs();
		connectEditorInputs(result.searchResult());

		editableSearchResult = BulkEditorHelper.findEditableResults(result.searchResult());
		natTable.updateList(editableSearchResult);

		final String infoText = result.searchResult().isEmpty() ? Messages.NoUsage : ""; //$NON-NLS-1$
		natTable.setSearchInformationText(infoText);
		commandStack.flush();
		firePropertyChange(PROP_DIRTY);
		return true;
	}

	public void reloadType() {
		performSearch();
	}

	private void changeNatTable(final BulkEditorMode modeSelection, final AttributeDeclaration simpleAttribute) {
		if (modeSelection == BulkEditorMode.SIMPLE_ATTRIBUTE && simpleAttribute != null
				&& simpleAttribute.getTypeEntry() instanceof final AttributeTypeEntry typeEntry) {
			natTable.changeNatTable(modeSelection, simpleAttribute, _ -> this.handleAddAttribute(typeEntry),
					this::handleDeleteAttribute);
		} else if (modeSelection == BulkEditorMode.ADVANCED_ATTRIBUTE) {
			natTable.changeNatTable(modeSelection, simpleAttribute, _ -> this.handleAddAttribute(null),
					this::handleDeleteAttribute);
		} else {
			natTable.changeNatTable(modeSelection, simpleAttribute);
		}
	}

	private Command handleAddAttribute(final AttributeTypeEntry attributeTypeEntry) {
		final List<EObject> searchRoots = searchScope.stream()
				.filter(uri -> TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri) instanceof final TypeEntry entry
						&& SearchHelper.linkedElementsFilter.test(entry))
				.map(SearchHelper::resolveElement).filter(Objects::nonNull).toList();

		final AddAttributeTreeSelectionDialog addAttributeDialog = new AddAttributeTreeSelectionDialog(
				getSite().getShell(), searchRoots, SearchHelper.createChildrenSearchProvider(latestQueryConfig),
				attributeTypeEntry != null ? attributeTypeEntry.getFullTypeName() : null, project, new HashSet<>());
		if (addAttributeDialog.open() != Window.OK) {
			return null;
		}

		final List<ConfigurableObject> result = Arrays.stream(addAttributeDialog.getResult())
				.filter(ConfigurableObject.class::isInstance).map(ConfigurableObject.class::cast).toList();

		connectEditorInputs(result);

		final List<ConfigurableObject> editableResults = BulkEditorHelper.findEditableResults(result);

		final DataType dataType = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getDataTypeLibrary()
				.getType(addAttributeDialog.getAttributeType());

		final CompoundCommand addAttributesCompoundCommand = new CompoundCommand();
		editableResults.stream()
				.map(configurableObject -> new CreateAttributeBulkEditorCommand(natTable, configurableObject,
						addAttributeDialog.getAttributeName(), addAttributeDialog.getAttributeComment(), dataType,
						attributeTypeEntry != null ? attributeTypeEntry.getType() : null,
						addAttributeDialog.getAttributeValue()))
				.forEach(addAttributesCompoundCommand::add);
		return addAttributesCompoundCommand;
	}

	private Command handleDeleteAttribute(final Object refElement) {
		if (refElement instanceof final Attribute attribute
				&& attribute.eContainer() instanceof final ConfigurableObject configurableObject) {
			return new DeleteAttributeBulkEditorCommand(natTable, configurableObject, attribute);
		}
		return null;
	}

	private void connectEditorInputs(final List<? extends EObject> results) {
		final MultiStatus status = new MultiStatus(getClass(), IStatus.OK,
				Messages.BulkEditor_ProblemOpeningSearchResult);
		final List<Saveable> openedSaveables = new ArrayList<>();
		for (final EObject result : results) {
			final IEditorInput editorInput = BulkEditorHelper.getEditorInput(result);
			if (editorInput != null && editorInputs.add(editorInput)) {
				try {
					LibraryElementProvider.INSTANCE.connect(editorInput);
				} catch (final CoreException e) {
					status.add(e.getStatus());
				}
				openedSaveables.add(new LibraryElementSaveable(editorInput));
			}
		}
		fireSaveablesEvent(SaveablesLifecycleEvent.POST_OPEN, openedSaveables.toArray(Saveable[]::new));
		if (!status.isOK()) {
			ErrorDialog.openError(getSite().getShell(), null, null, status);
		}
	}

	private void disconnectEditorInputs() {
		for (final IEditorInput editorInput : editorInputs) {
			LibraryElementProvider.INSTANCE.disconnect(editorInput);
		}
		editorInputs.clear();
	}

	@Override
	public boolean isDirty() {
		return Arrays.stream(getSaveables()).anyMatch(Saveable::isDirty);
	}

	@Override
	public Saveable[] getSaveables() {
		return Stream.concat(Stream.of(querySaveable), Arrays.stream(getResultSaveables())).toArray(Saveable[]::new);
	}

	@Override
	public Saveable[] getActiveSaveables() {
		// saving saves the query or the search results, whichever is shown
		if (getActivePage() == QUERY_PAGE_INDEX) {
			return new Saveable[] { querySaveable };
		}
		return getResultSaveables();
	}

	private Saveable[] getResultSaveables() {
		return editorInputs.stream().map(LibraryElementSaveable::new).toArray(Saveable[]::new);
	}

	private boolean promptToSave(final Saveable... saveables) {
		final SaveablesLifecycleEvent event = new SaveablesLifecycleEvent(this, SaveablesLifecycleEvent.PRE_CLOSE,
				saveables, false);
		getSaveablesLifecycleListener().handleLifecycleEvent(event);
		return !event.isVeto();
	}

	private void fireDirtyChanged(final Saveable saveable) {
		firePropertyChange(PROP_DIRTY);
		fireSaveablesEvent(SaveablesLifecycleEvent.DIRTY_CHANGED, saveable);
	}

	private void fireSaveablesEvent(final int eventType, final Saveable... saveables) {
		if (saveables.length > 0) {
			getSaveablesLifecycleListener()
					.handleLifecycleEvent(new SaveablesLifecycleEvent(this, eventType, saveables, false));
		}
	}

	private ISaveablesLifecycleListener getSaveablesLifecycleListener() {
		return getSite().getService(ISaveablesLifecycleListener.class);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		// nothing to be done
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// the workbench saves the saveables of the shown page, this saves all of them
		final List<Saveable> dirtySaveables = Arrays.stream(getSaveables()).filter(Saveable::isDirty).toList();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, dirtySaveables.size());
		for (final Saveable saveable : dirtySaveables) {
			try {
				saveable.doSave(subMonitor.split(1));
			} catch (final CoreException e) {
				ILog.get().error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void doSaveAs() {
		// should not be used with this editor
	}

	@Override
	public void dispose() {
		if (queryViewer != null && getEditorInput() instanceof final BulkEditorInput bulkEditorInput) {
			bulkEditorInput.setQuery(queryViewer.createSnapshot());
			bulkEditorInput.setQuerySnapshotSupplier(null);
		}

		disconnectEditorInputs();
		LibraryElementProvider.INSTANCE.removeLibraryElementStateListener(elementStateListener);
		OperationHistoryFactory.getOperationHistory().removeOperationHistoryListener(operationContextUpdater);
		commandStack.dispose();
		if (activationListener != null) {
			activationListener.dispose();
		}
		super.dispose();
	}

	@Override
	public void executeCommand(final Command cmd) {
		commandStack.execute(cmd);
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		if (event.getSource() == commandStack && ((event.getDetail() & CommandStack.POST_UNDO) != 0
				|| (event.getDetail() & CommandStack.POST_REDO) != 0)) {
			natTable.getCurrentTable().refresh();
		}
		if ((event.getDetail() & CommandStack.POST_MASK) != 0) {
			updateStackActions();
			if (event.getSource() != commandStack) {
				// the search results are dirty by their library elements, not by their stack
				fireDirtyChanged(querySaveable);
			}
		}
	}

	private CommandStack getActiveCommandStack() {
		if (getActivePage() == QUERY_PAGE_INDEX && queryViewer != null) {
			return queryViewer.getCommandStack();
		}
		return commandStack;
	}

	private void updateStackActions() {
		for (final String actionId : List.of(ActionFactory.UNDO.getId(), ActionFactory.REDO.getId())) {
			if (actionRegistry.getAction(actionId) instanceof final UpdateAction action) {
				action.update();
			}
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(getActiveCommandStack());
		}
		if (adapter == ActionRegistry.class) {
			return adapter.cast(actionRegistry);
		}
		if (adapter == ZoomManager.class && queryViewer != null) {
			// allows to zoom the query with the zoom toolbar of 4diac
			return adapter.cast(queryViewer.getZoomManager());
		}
		return super.getAdapter(adapter);
	}

	protected class EditorStateListener implements LibraryElementStateListener {
		@Override
		public void elementDirtyStateChanged(final IEditorInput input, final boolean isDirty) {
			if (editorInputs.contains(input)) {
				fireDirtyChanged(new LibraryElementSaveable(input));
				natTable.setSearchInformationText(Messages.Search_Changes);
			}
		}

		@Override
		public void elementContentReplaced(final IEditorInput input) {
			if (editorInputs.contains(input)) {
				editableSearchResult = BulkEditorHelper.findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
				natTable.setSearchInformationText(Messages.Search_Changes);
			}
		}

		@Override
		public void elementDeleted(final IEditorInput input) {
			if (editorInputs.remove(input)) {
				fireSaveablesEvent(SaveablesLifecycleEvent.POST_CLOSE, new LibraryElementSaveable(input));
				LibraryElementProvider.INSTANCE.disconnect(input);
				editableSearchResult = editableSearchResult.stream()
						.filter(result -> !input.equals(BulkEditorHelper.getEditorInput(result))).toList();
				natTable.updateList(editableSearchResult);
			}
		}

		@Override
		public void elementMoved(final IEditorInput originalInput, final IEditorInput movedInput) {
			if (editorInputs.remove(originalInput)) {
				fireSaveablesEvent(SaveablesLifecycleEvent.POST_CLOSE, new LibraryElementSaveable(originalInput));
				LibraryElementProvider.INSTANCE.disconnect(originalInput);
				connectEditorInputs(editableSearchResult);
				editableSearchResult = BulkEditorHelper.findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
			}
		}
	}
}
