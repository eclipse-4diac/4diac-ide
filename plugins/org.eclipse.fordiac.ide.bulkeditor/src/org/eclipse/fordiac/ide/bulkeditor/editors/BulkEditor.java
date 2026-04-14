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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.commands.CreateAttributeBulkEditorCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.DeleteAttributeBulkEditorCommand;
import org.eclipse.fordiac.ide.bulkeditor.nattable.BulkEditorNatTable;
import org.eclipse.fordiac.ide.bulkeditor.search.EditorSearchExecutor;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchHelper;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchParameters;
import org.eclipse.fordiac.ide.bulkeditor.ui.AddAttributeTreeSelectionDialog;
import org.eclipse.fordiac.ide.bulkeditor.ui.BulkEditorControls;
import org.eclipse.fordiac.ide.bulkeditor.ui.BulkEditorWidgetUtils;
import org.eclipse.fordiac.ide.gef.commands.OperationHistoryCommandStack;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.MultiLibraryElementOperationContextUpdater;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementStateListener;
import org.eclipse.fordiac.ide.model.ui.editors.MultiLibraryElementActivationListener;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Twistie;
import org.eclipse.ui.part.EditorPart;

public class BulkEditor extends EditorPart implements CommandExecutor, CommandStackEventListener {

	private static final String CONTEXT_ID = "org.eclipse.fordiac.ide.bulkeditor"; //$NON-NLS-1$

	private IProject project;
	private BulkEditorSettings settings;
	private List<URI> selectedSubApps = Collections.emptyList();

	private final Set<IEditorInput> editorInputs = new HashSet<>();
	private final OperationHistoryCommandStack commandStack = new OperationHistoryCommandStack();
	private final MultiLibraryElementOperationContextUpdater operationContextUpdater = new MultiLibraryElementOperationContextUpdater(
			commandStack::getUndoContext);
	private final EditorStateListener elementStateListener = new EditorStateListener();
	private MultiLibraryElementActivationListener activationListener;
	private ActionRegistry actionRegistry;

	private BulkEditorControls controls;
	private BulkEditorNatTable natTable;

	// Latest successful search result (mapped to editable counterparts).
	private List<EObject> editableSearchResult;
	private Set<URI> searchScope;
	private SearchHelper latestSearchHelper;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		registerActions(site);
		setSite(site);
		setInput(input);
		commandStack.addCommandStackEventListener(this);
		OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(operationContextUpdater);
		LibraryElementProvider.INSTANCE.addLibraryElementStateListener(elementStateListener);
		activationListener = new MultiLibraryElementActivationListener(this, editorInputs);

		if (input instanceof final BulkEditorInput bulkEditorInput) {
			this.settings = bulkEditorInput.getSettings();
			this.project = bulkEditorInput.getProject();
			commandStack.setUndoContext(new ObjectUndoContext(project));
			selectedSubApps = bulkEditorInput.getInitialSelectedSubApps();
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
	public void createPartControl(final Composite parent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setBackground(parent.getBackground());
		scrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);

		final Composite pageComposite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(pageComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).margins(20, 20).generateLayout(pageComposite);

		final Composite pageHeaderComposite = new Composite(pageComposite, SWT.NONE);
		pageHeaderComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pageHeaderComposite.setLayout(new GridLayout(2, false));

		final Composite pageBodyComposite = new Composite(pageComposite, SWT.NONE);
		pageBodyComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pageBodyComposite.setLayout(new GridLayout(1, false));

		WidgetFactory.label(SWT.NONE).text(Messages.SearchFor).create(pageHeaderComposite);
		final Twistie expandBodyCompositeTwistie = new Twistie(pageHeaderComposite, SWT.NONE);
		expandBodyCompositeTwistie.setExpanded(true);
		expandBodyCompositeTwistie.addListener(SWT.MouseUp, event -> BulkEditorWidgetUtils
				.updateVisibility(expandBodyCompositeTwistie.isExpanded(), pageBodyComposite));

		controls = new BulkEditorControls(settings, this, selectedSubApps);
		controls.createControls(pageBodyComposite);
		natTable = new BulkEditorNatTable(pageComposite, this, settings.modeSelection);

		scrolledComposite.setMinSize(pageComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		pageComposite.layout();
	}

	public IProject getProject() {
		return project;
	}

	public boolean confirmDiscardUnsavedChanges() {
		if (!isDirty()) {
			return true;
		}
		final int result = MessageDialog.open(MessageDialog.QUESTION_WITH_CANCEL, getSite().getShell(), Messages.Save,
				Messages.Unsaved_Changes, 0, Messages.Save, Messages.Discard, Messages.Cancel);
		return switch (result) {
		case 0 -> {
			doSave(new NullProgressMonitor());
			yield true;
		}
		case 1 -> true;
		default -> false;
		};
	}

	public void onSearchRequested() {
		if (!confirmDiscardUnsavedChanges()) {
			return;
		}
		if (!performSearch()) {
			return;
		}
		if (controls.getModeSelection() == 1 && controls.getAddDeleteComposite().getChildren().length == 0) {
			createAddDeleteButtons();
		}
	}

	public void onModeChanged(final BulkEditorMode newMode) {
		changeNatTable(newMode, null);
		commandStack.flush();
		disconnectEditorInputs();
		firePropertyChange(PROP_DIRTY);
	}

	private boolean performSearch() {
		final SearchParameters params = controls.collectParameters();
		final EditorSearchExecutor.Result result = EditorSearchExecutor.search(params, project);
		if (result == null) {
			return false;
		}

		if (result.simpleAttributeTypeEntry() != null) {
			changeNatTable(BulkEditorMode.SIMPLE_ATTRIBUTE, result.simpleAttributeTypeEntry().getType());
		}

		this.latestSearchHelper = result.helper();
		this.searchScope = result.searchScope();

		disconnectEditorInputs();
		connectEditorInputs(result.searchResult());

		editableSearchResult = BulkEditorHelper.findEditableResults(result.searchResult());
		natTable.updateList(editableSearchResult);

		controls.resetChangedSearchParameter();
		controls.setSearchInformationText(""); //$NON-NLS-1$
		commandStack.flush();
		firePropertyChange(PROP_DIRTY);
		return true;
	}

	public void reloadType() {
		performSearch();
	}

	private void changeNatTable(final BulkEditorMode modeSelection, final AttributeDeclaration simpleAttribute) {
		Arrays.stream(controls.getAddDeleteComposite().getChildren()).forEach(Control::dispose);
		controls.getAddDeleteComposite().getParent().layout();
		natTable.changeNatTable(modeSelection, simpleAttribute);
	}

	private void createAddDeleteButtons() {
		final AttributeTypeEntry attributeTypeEntry = controls.isAdvancedMode() ? null
				: TypeLibraryManager.INSTANCE.getTypeLibrary(project)
						.getAttributeTypeEntry(controls.getSearchText().getText());

		final var addDeleteWidget = new AddDeleteWidget();
		addDeleteWidget.createControls(controls.getAddDeleteComposite(), new FormToolkit(Display.getDefault()), true);
		addDeleteWidget.bindToTableViewer(natTable.getCurrentTable(), this,
				refElement -> handleAddAttribute(attributeTypeEntry), this::handleDeleteAttribute);
		controls.getAddDeleteComposite().getParent().layout();
	}

	private Command handleAddAttribute(final AttributeTypeEntry attributeTypeEntry) {
		final List<LibraryElement> libraryElements = searchScope.stream()
				.map(TypeLibraryManager.INSTANCE::getTypeEntryForURI).filter(Objects::nonNull)
				.filter(SearchHelper.linkedElementsFilter).map(TypeEntry::getType).filter(Objects::nonNull).toList();

		final AddAttributeTreeSelectionDialog addAttributeDialog = new AddAttributeTreeSelectionDialog(
				getSite().getShell(), libraryElements, latestSearchHelper.createChildrenSearchProvider(),
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
		for (final EObject result : results) {
			final IEditorInput editorInput = BulkEditorHelper.getEditorInput(result);
			if (editorInput != null && editorInputs.add(editorInput)) {
				try {
					LibraryElementProvider.INSTANCE.connect(editorInput);
				} catch (final CoreException e) {
					status.add(e.getStatus());
				}
			}
		}
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
		return editorInputs.stream().anyMatch(LibraryElementProvider.INSTANCE::canSaveLibraryElement);
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
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(project.getParent()) {
			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				final SubMonitor subMonitor = SubMonitor.convert(monitor, editorInputs.size());
				int remaining = editorInputs.size();
				for (final IEditorInput editorInput : editorInputs) {
					if (LibraryElementProvider.INSTANCE.canSaveLibraryElement(editorInput)) {
						LibraryElementProvider.INSTANCE.saveLibraryElement(editorInput, subMonitor.split(1));
					}
					remaining--;
					subMonitor.setWorkRemaining(remaining);
				}
			}
		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		commandStack.markSaveLocation();
	}

	@Override
	public void doSaveAs() {
		// should not be used with this editor
	}

	@Override
	public void dispose() {
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
		if ((event.getDetail() & CommandStack.POST_UNDO) != 0 || (event.getDetail() & CommandStack.POST_REDO) != 0) {
			natTable.getCurrentTable().refresh();
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(commandStack);
		}
		if (adapter == ActionRegistry.class) {
			return adapter.cast(actionRegistry);
		}
		return super.getAdapter(adapter);
	}

	protected class EditorStateListener implements LibraryElementStateListener {
		@Override
		public void elementDirtyStateChanged(final IEditorInput input, final boolean isDirty) {
			if (editorInputs.contains(input)) {
				firePropertyChange(PROP_DIRTY);
				if (!controls.hasChangedSearchParameter()) {
					controls.setSearchInformationText(Messages.Search_Changes);
				}
			}
		}

		@Override
		public void elementContentReplaced(final IEditorInput input) {
			if (editorInputs.contains(input)) {
				editableSearchResult = BulkEditorHelper.findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
				if (!controls.hasChangedSearchParameter()) {
					controls.setSearchInformationText(Messages.Search_Changes);
				}
			}
		}

		@Override
		public void elementDeleted(final IEditorInput input) {
			if (editorInputs.remove(input)) {
				LibraryElementProvider.INSTANCE.disconnect(input);
				editableSearchResult = editableSearchResult.stream()
						.filter(result -> !input.equals(BulkEditorHelper.getEditorInput(result))).toList();
				natTable.updateList(editableSearchResult);
			}
		}

		@Override
		public void elementMoved(final IEditorInput originalInput, final IEditorInput movedInput) {
			if (editorInputs.remove(originalInput)) {
				LibraryElementProvider.INSTANCE.disconnect(originalInput);
				connectEditorInputs(editableSearchResult);
				editableSearchResult = BulkEditorHelper.findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
			}
		}
	}
}
