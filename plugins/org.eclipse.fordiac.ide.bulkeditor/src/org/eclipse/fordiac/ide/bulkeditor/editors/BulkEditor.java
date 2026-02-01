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
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.utilities.SubAppHierarchyDialog;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings.ScopeOption;
import org.eclipse.fordiac.ide.gef.commands.OperationHistoryCommandStack;
import org.eclipse.fordiac.ide.model.commands.ScopedOperation;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementStateListener;
import org.eclipse.fordiac.ide.model.ui.editors.MultiLibraryElementActivationListener;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Twistie;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class BulkEditor extends EditorPart implements CommandExecutor, CommandStackEventListener {
	private static final String CONTEXT_ID = "org.eclipse.fordiac.ide.bulkeditor"; //$NON-NLS-1$
	private static final List<String> DEFAULT_LIST = List.of(Messages.Name, Messages.Type, Messages.Comment,
			Messages.InitialValue);
	private static final List<String> LIST_WITHOUT_VALUE = List.of(Messages.Name, Messages.Type, Messages.Comment);

	private IProject project;
	private final Set<IEditorInput> editorInputs = new HashSet<>();
	private final OperationHistoryCommandStack commandStack = new OperationHistoryCommandStack();
	private final OperationContextUpdater operationContextUpdater = new OperationContextUpdater();
	private final LibraryElementStateListener elementStateListener = new EditorStateListener();
	private MultiLibraryElementActivationListener activationListener;
	private ActionRegistry actionRegistry;
	private SearchHelper helper;
	private BulkEditorSettings settings;
	private List<URI> selectedSubApps = Collections.emptyList();
	private Set<URI> searchScope;
	private List<EObject> editableSearchResult;

	// Search For
	private Combo modeSelectionDropDown;
	private Button advancedButton;
	private boolean changedSearchParameter = false;

	// Search Where
	private Group searchWhereGroup;
	private FilterComposite searchFilter;
	private Text searchText;

	// Search In
	private Button searchInClearButton;
	private FilterComposite fbSubappTypesFilter;
	private FilterComposite fbTypedSubappInstanceFilter;
	private FilterComposite untypedSubappFilter;
	private FilterComposite dataTypesFilter;
	private FilterComposite attributeTypesFilter;
	private Button ignoreLinkedLibrariesButton;

	// Scope
	private Button workspaceScopeButton;
	private Button projectScopeButton;
	private Button subappHierarchyScopeButton;
	private Button subappHierarchyScopeSearchButton;
	private Label subappHierarchyScopeLabel;

	BulkEditorNatTable natTable;

	private Composite addDeleteComposite;
	private Label searchInformation;
	private Label dirtyInformation;

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
			project = bulkEditorInput.getProject();
			commandStack.setUndoContext(new ObjectUndoContext(project));
			selectedSubApps = bulkEditorInput.getInitialSelectedSubApps();
			setPartName(getPartName() + ": " + project.getName()); //$NON-NLS-1$
		}
	}

	private void registerActions(final IEditorSite site) {
		final ActionRegistry registry = new ActionRegistry();

		final IAction undoAction = new UndoAction(this);
		undoAction.setActionDefinitionId(ActionFactory.UNDO.getCommandId());
		registry.registerAction(undoAction);
		final IAction redoAction = new RedoAction(this);
		redoAction.setActionDefinitionId(ActionFactory.REDO.getCommandId());
		registry.registerAction(redoAction);

		final IActionBars bars = site.getActionBars();
		bars.setGlobalActionHandler(ActionFactory.UNDO.getId(), registry.getAction(ActionFactory.UNDO.getId()));
		bars.setGlobalActionHandler(ActionFactory.REDO.getId(), registry.getAction(ActionFactory.REDO.getId()));
		bars.updateActionBars();

		final IContextService contextService = site.getService(IContextService.class);
		contextService.activateContext(CONTEXT_ID);
	}

	@Override
	public void createPartControl(final Composite parent) {
		// page layout
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

		// header
		WidgetFactory.label(SWT.NONE).text(Messages.SearchFor).create(pageHeaderComposite);
		final Twistie expandBodyCompositeTwistie = new Twistie(pageHeaderComposite, SWT.NONE);
		expandBodyCompositeTwistie.setExpanded(true);
		expandBodyCompositeTwistie.addListener(SWT.MouseUp, event -> {
			final boolean isVisible = expandBodyCompositeTwistie.isExpanded();
			updateVisibility(isVisible, pageBodyComposite);
		});

		// body
		createModeSelectionComposite(pageBodyComposite);
		createSearchWhereGroup(pageBodyComposite);
		createSearchInGroup(pageBodyComposite);
		createScopeGroup(pageBodyComposite);
		createSearchButton(pageBodyComposite);
		natTable = new BulkEditorNatTable(pageComposite, this, settings.modeSelection);

		scrolledComposite.setMinSize(pageComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		pageComposite.layout();
	}

	private void createModeSelectionComposite(final Composite parent) {
		final Composite modeSelectionComposite = new Composite(parent, SWT.NONE);
		modeSelectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		modeSelectionComposite.setLayout(new GridLayout(2, false));

		modeSelectionDropDown = new Combo(modeSelectionComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems(Messages.Variable, Messages.Attribute);
		modeSelectionDropDown.select(settings.modeSelection);
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			if (modeSelectionDropDown.getSelectionIndex() == settings.modeSelection) {
				return;
			}
			if (!openUnsavedChangesDialog()) {
				return;
			}
			advancedButton.setVisible(modeSelectionDropDown.getSelectionIndex() == 1);
			advancedButton.setSelection(settings.advancedMode);
			changeSearchWhereGroupFilter(modeSelectionDropDown.getSelectionIndex() != 1 || settings.advancedMode);
			changeNatTable(modeSelectionDropDown.getSelectionIndex(), null);
			settings.modeSelection = modeSelectionDropDown.getSelectionIndex();
			parent.getParent().layout();
			changedSearchParameter = false;
			searchInformation.setText(""); //$NON-NLS-1$
			commandStack.flush();
		});

		advancedButton = WidgetFactory.button(SWT.TOGGLE).text(Messages.Advanced).onSelect(event -> {
			changeSearchWhereGroupFilter(advancedButton.getSelection());
			settings.advancedMode = advancedButton.getSelection();
			changeNatTable(modeSelectionDropDown.getSelectionIndex(), null);
			parent.getParent().layout();
		}).create(modeSelectionComposite);
		advancedButton.setVisible(modeSelectionDropDown.getSelectionIndex() == 1);
		advancedButton.setSelection(settings.advancedMode);
	}

	private void createSearchWhereGroup(final Composite parent) {
		searchWhereGroup = createCollapsibleGroup(parent, Messages.SearchWhere,
				button -> button.addListener(SWT.Selection, event -> {
					if (searchFilter != null && !searchFilter.isDisposed()) {
						searchFilter.clear();
					}
					if (searchText != null && !searchText.isDisposed()) {
						searchText.setText(""); //$NON-NLS-1$
					}
				}));
		changeSearchWhereGroupFilter(settings.modeSelection != 1 || settings.advancedMode);
	}

	private void changeSearchWhereGroupFilter(final boolean advancedMode) {
		for (final Control child : searchWhereGroup.getChildren()) {
			child.dispose();
		}

		if (advancedMode) {
			searchFilter = new FilterComposite(searchWhereGroup, SWT.NONE, DEFAULT_LIST, settings,
					BulkEditorSettings.whereSearchList);
			searchFilter.addFilterChangedListener(() -> this.changedSearchParameter = true);
		} else {
			final Composite simpleTextComposite = new Composite(searchWhereGroup, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(2).margins(0, 0).generateLayout(simpleTextComposite);
			simpleTextComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			WidgetFactory.label(SWT.NONE).text(Messages.Name).create(simpleTextComposite);
			searchText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
					.create(simpleTextComposite);

			final IContentProposalProvider proposalProvider = new TypeSelectionProposalProvider(
					() -> TypeLibraryManager.INSTANCE.getTypeLibrary(project),
					AttributeSelectionContentProvider.INSTANCE);
			final ContentProposalAdapter proposalAdapter = new ContentProposalAdapter(searchText,
					new TextContentAdapter(), proposalProvider, KeyStroke.getInstance(SWT.CTRL, SWT.SPACE),
					NatTableWidgetFactory.getActivationChars());
			proposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		}
		searchWhereGroup.layout();
	}

	private void createSearchInGroup(final Composite parent) {
		final Group searchGroup = createCollapsibleGroup(parent, Messages.SearchIn,
				button -> this.searchInClearButton = button);
		this.searchInClearButton.addListener(SWT.Selection, event -> {
			fbSubappTypesFilter.clear();
			fbTypedSubappInstanceFilter.clear();
			untypedSubappFilter.clear();
			dataTypesFilter.clear();
			attributeTypesFilter.clear();
			ignoreLinkedLibrariesButton.setSelection(true);
			settings.ignoreLinkedLibraries = true;
		});
		fbSubappTypesFilter = createSearchFilterInGroup(searchGroup, Messages.FBandSubappTypes,
				BulkEditorSettings.inFBTypesSearchList, settings.fbSubappTypes,
				b -> settings.fbSubappTypes = b.booleanValue());
		fbTypedSubappInstanceFilter = createSearchFilterInGroup(searchGroup, Messages.FBandSubappInstances,
				BulkEditorSettings.inFBInstanceSearchList, settings.fbTypedSubappInstance,
				b -> settings.fbTypedSubappInstance = b.booleanValue());
		untypedSubappFilter = createSearchFilterInGroup(searchGroup, Messages.UntypedSubapps,
				BulkEditorSettings.inUntypedSubAppSearchList, settings.untypedSubapp,
				b -> settings.untypedSubapp = b.booleanValue());
		dataTypesFilter = createSearchFilterInGroup(searchGroup, Messages.DataTypes,
				BulkEditorSettings.inDataTypesSearchList, settings.dataTypes,
				b -> settings.dataTypes = b.booleanValue());
		attributeTypesFilter = createSearchFilterInGroup(searchGroup, Messages.AttributeTypes,
				BulkEditorSettings.inAttributeTypesSearchList, settings.attributeTypes,
				b -> settings.attributeTypes = b.booleanValue());

		ignoreLinkedLibrariesButton = WidgetFactory.button(SWT.CHECK).text(Messages.IgnoreLinkedLibraries)
				.onSelect(event -> settings.ignoreLinkedLibraries = ignoreLinkedLibrariesButton.getSelection())
				.create(searchGroup);
		ignoreLinkedLibrariesButton.setSelection(settings.ignoreLinkedLibraries);
		final GridData buttonLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		buttonLayoutData.verticalIndent = 10;
		ignoreLinkedLibrariesButton.setLayoutData(buttonLayoutData);
	}

	private void createSearchButton(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).margins(0, 0).generateLayout(composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		WidgetFactory.button(SWT.PUSH).text(Messages.Search).onSelect(this::handleSearch).create(composite);

		addDeleteComposite = new Composite(composite, 0);
		addDeleteComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addDeleteComposite.setLayout(GridLayoutFactory.fillDefaults().spacing(0, 0).create());

		dirtyInformation = WidgetFactory.label(SWT.NONE).create(composite);
		dirtyInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		searchInformation = WidgetFactory.label(SWT.NONE).create(composite);
		searchInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	private void handleSearch(final SelectionEvent selectionevent1) {
		if (!openUnsavedChangesDialog()) {
			return;
		}
		if (!performSearch()) {
			return;
		}

		if (modeSelectionDropDown.getSelectionIndex() == 1 && addDeleteComposite.getChildren().length == 0) {
			createAddDeleteButtons();
		}
	}

	private void createAddDeleteButtons() {
		final AttributeTypeEntry attributeTypeEntry = advancedButton.getSelection() ? null
				: TypeLibraryManager.INSTANCE.getTypeLibrary(project).getAttributeTypeEntry(searchText.getText());

		final var addDeleteWidget = new AddDeleteWidget();
		addDeleteWidget.createControls(addDeleteComposite, new FormToolkit(Display.getDefault()), true);
		addDeleteWidget.bindToTableViewer(natTable.getCurrentTable(), this,
				refElement -> handleAddAttribute(attributeTypeEntry), this::handleDeleteAttribute);
		addDeleteComposite.getParent().layout();
	}

	private Command handleAddAttribute(final AttributeTypeEntry attributeTypeEntry) {
		final List<LibraryElement> libraryElements = searchScope.stream()
				.map(TypeLibraryManager.INSTANCE::getTypeEntryForURI).filter(Objects::nonNull)
				.filter(SearchHelper.linkedElementsFilter).map(TypeEntry::getType).filter(Objects::nonNull).toList();

		final AddAttributeTreeSelectionDialog addAttributeDialog = new AddAttributeTreeSelectionDialog(
				this.getSite().getShell(), libraryElements, helper.createChildrenSearchProvider(),
				attributeTypeEntry != null ? attributeTypeEntry.getFullTypeName() : null, project, new HashSet<>());
		if (addAttributeDialog.open() != Window.OK) {
			return null;
		}

		final List<ConfigurableObject> result = Arrays.stream(addAttributeDialog.getResult())
				.filter(ConfigurableObject.class::isInstance).map(ConfigurableObject.class::cast).toList();

		connectEditorInputs(result);

		final List<ConfigurableObject> editableResults = findEditableResults(result);

		final DataType dataType = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getDataTypeLibrary()
				.getType(addAttributeDialog.getAttributeType());

		final CompoundCommand addAttributesCompoundCommand = new CompoundCommand();
		editableResults.stream()
				.map(configureableObject -> new CreateAttributeBulkEditorCommand(natTable, configureableObject,
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

	private static Group createCollapsibleGroup(final Composite parent, final String groupLabel,
			final Consumer<Button> clearButtonProvider) {
		// group layout
		final Composite groupComposite = new Composite(parent, SWT.NONE);
		groupComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final var groupCompositeLayout = new GridLayout(1, false);
		groupCompositeLayout.verticalSpacing = 0;
		groupCompositeLayout.marginWidth = 0;
		groupComposite.setLayout(groupCompositeLayout);

		final Composite header = new Composite(groupComposite, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(clearButtonProvider != null ? 3 : 2, false));
		final Group searchGroup = new Group(groupComposite, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		// header
		final Label titleLabel = new Label(header, SWT.NONE);
		titleLabel.setText(groupLabel);
		final Twistie expandCompositeTwistie = new Twistie(header, SWT.NONE);
		final Button clearButton;
		if (clearButtonProvider != null) {
			clearButton = WidgetFactory.button(SWT.PUSH)
					.image(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_CLEAR))
					.tooltip(Messages.ClearFilter).create(header);
			clearButtonProvider.accept(clearButton);
		} else {
			clearButton = null;
		}

		expandCompositeTwistie.setExpanded(true);
		expandCompositeTwistie.addListener(SWT.MouseUp, event -> {
			final boolean isVisible = expandCompositeTwistie.isExpanded();
			if (clearButton != null) {
				clearButton.setVisible(isVisible);
			}

			updateVisibility(isVisible, searchGroup);
		});

		return searchGroup;
	}

	private FilterComposite createSearchFilterInGroup(final Composite parent, final String name,
			final List<String> subSettingsReferencesNames, final boolean initalSelection,
			final Consumer<Boolean> buttonListener) {
		final Composite searchInCategoryComposite = new Composite(parent, SWT.NONE);
		searchInCategoryComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		searchInCategoryComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		final Composite searchInCategorySubComposite = new Composite(searchInCategoryComposite, SWT.NONE);
		searchInCategorySubComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		searchInCategorySubComposite.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0).numColumns(3).create());

		final Button categorySelectionButton = WidgetFactory.button(SWT.CHECK).text(name)
				.create(searchInCategorySubComposite);
		categorySelectionButton.addListener(SWT.Selection,
				event -> buttonListener.accept(Boolean.valueOf(categorySelectionButton.getSelection())));
		categorySelectionButton.setSelection(initalSelection);

		final FilterComposite filterComposite = new FilterComposite(searchInCategoryComposite, SWT.NONE,
				LIST_WITHOUT_VALUE, settings, subSettingsReferencesNames);

		final Twistie expandFilterCompositeTwistie = new Twistie(searchInCategorySubComposite, SWT.NONE);
		expandFilterCompositeTwistie.addListener(SWT.MouseUp,
				event -> updateVisibility(expandFilterCompositeTwistie.isExpanded(), filterComposite));
		updateVisibility(false, filterComposite);

		searchInClearButton.addListener(SWT.Selection, event -> {
			categorySelectionButton.setSelection(true);
			categorySelectionButton.notifyListeners(SWT.Selection, null);
			expandFilterCompositeTwistie.setExpanded(false);
			expandFilterCompositeTwistie.notifyListeners(SWT.MouseUp, null);
		});

		final Label stateLabel = new Label(searchInCategorySubComposite, SWT.LEAD);
		stateLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		filterComposite.addTextChangedListener(stateLabel::setText);

		return filterComposite;
	}

	private void changeNatTable(final int modeSelection, final AttributeDeclaration simpleAttribute) {
		Arrays.stream(addDeleteComposite.getChildren()).forEach(Control::dispose);
		addDeleteComposite.getParent().layout();
		if (simpleAttribute != null) {
			natTable.createDynamicNatTable(simpleAttribute);
		} else {
			natTable.changeNatTable(modeSelection);
		}
	}

	private static void updateVisibility(final boolean visible, final Composite composite) {
		composite.setVisible(visible);
		((GridData) composite.getLayoutData()).exclude = !visible;

		Composite current = composite.getParent();
		while (current != null && !(current.getParent() instanceof ScrolledComposite)) {
			current = current.getParent();
		}
		if (current != null) {
			final ScrolledComposite scrolledParentComposite = (ScrolledComposite) current.getParent();
			scrolledParentComposite.setMinSize(current.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			current.layout();
		}
		composite.getParent().layout();
	}

	private void createScopeGroup(final Composite parent) {
		final Group scopeGroup = createCollapsibleGroup(parent, Messages.Scope, null);
		final Composite groupContent = new Composite(scopeGroup, SWT.NONE);
		groupContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupContent.setLayout(GridLayoutFactory.swtDefaults().numColumns(5).create());

		projectScopeButton = WidgetFactory.button(SWT.RADIO)
				.text(MessageFormat.format(Messages.Project, project.getName())).create(groupContent);
		projectScopeButton.setSelection(settings.scope == ScopeOption.PROJECT);
		projectScopeButton.addListener(SWT.Selection, event -> {
			if (projectScopeButton.getSelection()) {
				settings.scope = ScopeOption.PROJECT;
			}
		});

		workspaceScopeButton = WidgetFactory.button(SWT.RADIO).text(Messages.Workspace).create(groupContent);
		workspaceScopeButton.setSelection(settings.scope == ScopeOption.WORKSPACE);
		workspaceScopeButton.addListener(SWT.Selection, event -> {
			if (workspaceScopeButton.getSelection()) {
				settings.scope = ScopeOption.WORKSPACE;
			}
		});

		subappHierarchyScopeButton = WidgetFactory.button(SWT.RADIO).text(Messages.SubappHierarchy)
				.create(groupContent);
		subappHierarchyScopeButton.setSelection(settings.scope == ScopeOption.SUBAPP_HIERARCHY);
		subappHierarchyScopeButton.addListener(SWT.Selection, event -> {
			if (subappHierarchyScopeButton.getSelection()) {
				settings.scope = ScopeOption.SUBAPP_HIERARCHY;
			}
			subappHierarchyScopeSearchButton.setVisible(subappHierarchyScopeButton.getSelection());
			subappHierarchyScopeLabel.setVisible(subappHierarchyScopeButton.getSelection());
		});

		subappHierarchyScopeSearchButton = WidgetFactory.button(SWT.NONE).text(Messages.SelectSubappHierarchy)
				.create(groupContent);
		subappHierarchyScopeSearchButton.addListener(SWT.Selection, event -> {
			if (subappHierarchyScopeButton.getSelection()) {
				openScopeDialog();
			}
		});
		subappHierarchyScopeSearchButton.setVisible(subappHierarchyScopeButton.getSelection());

		selectedSubApps = settings.subappHierarchies;
		subappHierarchyScopeLabel = WidgetFactory.label(SWT.NONE).create(groupContent);
		createSubappHierarchyText();
		subappHierarchyScopeLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		subappHierarchyScopeLabel.setVisible(subappHierarchyScopeButton.getSelection());
		createSubappHierarchyText();
	}

	private void openScopeDialog() {
		final var elements = selectedSubApps.stream().map(uri -> {
			final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
			return typeEntry.getType().eResource().getEObject(uri.fragment());
		}).toList();
		final var dialog = new SubAppHierarchyDialog(project, elements);
		final var result = dialog.open();
		if (result != null) {
			selectedSubApps = SubAppHierarchyDialog.mapResultToURIs(result);
			settings.subappHierarchies = selectedSubApps;
		}
		createSubappHierarchyText();
	}

	private void createSubappHierarchyText() {
		if (selectedSubApps.isEmpty()) {
			subappHierarchyScopeLabel.setForeground(new Color(255, 0, 0));
			subappHierarchyScopeLabel.setText(Messages.NoHierarchySelected);
			return;
		}

		final var elements = selectedSubApps.stream().map(uri -> {
			final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
			return typeEntry.getType().eResource().getEObject(uri.fragment());
		}).map(eObject -> {
			if (EcoreUtil.getRootContainer(eObject) instanceof final INamedElement rootElement
					&& rootElement != eObject) {
				return rootElement.getName() + "." + FordiacMarkerHelper.getLocation(eObject); //$NON-NLS-1$
			}
			return FordiacMarkerHelper.getLocation(eObject);
		});

		subappHierarchyScopeLabel.setForeground(new Color(0, 0, 0));
		subappHierarchyScopeLabel.setText(elements.collect(Collectors.joining("; "))); //$NON-NLS-1$
	}

	private boolean openUnsavedChangesDialog() {
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

	private boolean performSearch() {
		helper = new SearchHelper(
				new SearchHelper.FilterRecordClass(settings.fbSubappTypes,
						fbSubappTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(0)),
						fbSubappTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(1)),
						fbSubappTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(2))),
				new SearchHelper.FilterRecordClass(settings.fbTypedSubappInstance,
						fbTypedSubappInstanceFilter.getFilter(LIST_WITHOUT_VALUE.get(0)),
						fbTypedSubappInstanceFilter.getFilter(LIST_WITHOUT_VALUE.get(1)),
						fbTypedSubappInstanceFilter.getFilter(LIST_WITHOUT_VALUE.get(2))),
				new SearchHelper.FilterRecordClass(settings.untypedSubapp,
						untypedSubappFilter.getFilter(LIST_WITHOUT_VALUE.get(0)),
						untypedSubappFilter.getFilter(LIST_WITHOUT_VALUE.get(1)),
						untypedSubappFilter.getFilter(LIST_WITHOUT_VALUE.get(2))),
				new SearchHelper.FilterRecordClass(settings.dataTypes,
						dataTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(0)),
						dataTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(1)),
						dataTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(2))),
				new SearchHelper.FilterRecordClass(settings.attributeTypes,
						attributeTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(0)),
						attributeTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(1)),
						attributeTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(2))),
				ignoreLinkedLibrariesButton.getSelection());

		List<ISearchContext> contexts;
		if (subappHierarchyScopeButton.getSelection()) {
			contexts = SearchHelper.createSearchContextList(project, selectedSubApps);
		} else {
			contexts = helper.createSearchContextList(workspaceScopeButton.getSelection(),
					projectScopeButton.getSelection(), project);
		}

		IEC61499SearchFilter modelSearchFilter;
		if (modeSelectionDropDown.getSelectionIndex() != 1 || advancedButton.getSelection()) {
			modelSearchFilter = SearchHelper.createSearchFilter(modeSelectionDropDown.getSelectionIndex(),
					DEFAULT_LIST.stream().map(searchFilter::getFilter).toList());
		} else {
			final var attributeTypeEntry = TypeLibraryManager.INSTANCE.getTypeLibrary(project)
					.getAttributeTypeEntry(searchText.getText());
			if (attributeTypeEntry == null) {
				return false;
			}
			changeNatTable(1, attributeTypeEntry.getType());
			modelSearchFilter = SearchHelper.createAttributeDeclarationSearchFilter(attributeTypeEntry.getType());
		}

		final var result = contexts.stream().flatMap(
				context -> new IEC61499ElementSearch(context, modelSearchFilter, helper.createChildrenSearchProvider())
						.performSearch().stream())
				.toList();

		disconnectEditorInputs();
		connectEditorInputs(result);

		searchScope = contexts.stream().flatMap(ISearchContext::getTypes).collect(Collectors.toUnmodifiableSet());
		editableSearchResult = findEditableResults(result);
		natTable.updateList(editableSearchResult);
		changedSearchParameter = false;
		searchInformation.setText(""); //$NON-NLS-1$
		commandStack.flush();
		return true;
	}

	private void connectEditorInputs(final List<? extends EObject> results) {
		final MultiStatus status = new MultiStatus(getClass(), IStatus.OK,
				Messages.BulkEditor_ProblemOpeningSearchResult);
		for (final EObject result : results) {
			final IEditorInput editorInput = getEditorInput(result);
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

	protected void disconnectEditorInputs() {
		for (final IEditorInput editorInput : editorInputs) {
			LibraryElementProvider.INSTANCE.disconnect(editorInput);
		}
		editorInputs.clear();
	}

	private static <T extends EObject> List<T> findEditableResults(final List<? extends T> list) {
		return list.stream().<T>map(BulkEditor::findEditableResult).filter(Objects::nonNull).toList();
	}

	@SuppressWarnings("unchecked")
	private static <T extends EObject> T findEditableResult(final T original) {
		if (EcoreUtil.getRootContainer(original) instanceof final LibraryElement originalLibraryElement) {
			final IEditorInput editorInput = getEditorInput(originalLibraryElement);
			final String relativeFragment = EcoreUtil.getRelativeURIFragmentPath(originalLibraryElement, original);
			final LibraryElement editorLibraryElement = LibraryElementProvider.INSTANCE.getLibraryElement(editorInput);
			if (relativeFragment.isEmpty()) {
				return (T) editorLibraryElement;
			}
			return (T) EcoreUtil.getEObject(editorLibraryElement, relativeFragment);
		}
		return null;
	}

	protected static IEditorInput getEditorInput(final EObject object) {
		if (EcoreUtil.getRootContainer(object) instanceof final LibraryElement originalLibraryElement) {
			return getEditorInput(originalLibraryElement);
		}
		return null;
	}

	protected static IEditorInput getEditorInput(final LibraryElement libraryElement) {
		final TypeEntry typeEntry = libraryElement.getTypeEntry();
		if (typeEntry != null) {
			final IFile file = typeEntry.getFile();
			if (file != null) {
				return new FileEditorInput(file);
			}
		}
		return null;
	}

	protected static IEditorInput getEditorInput(final URI uri) {
		if (uri.isPlatformResource()) {
			return new FileEditorInput(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))));
		}
		return null;
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

	public void reloadType() {
		performSearch();
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
		activationListener.dispose();
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
				if (!changedSearchParameter) {
					searchInformation.setText(Messages.Search_Changes);
				}
			}
		}

		@Override
		public void elementContentReplaced(final IEditorInput input) {
			if (editorInputs.contains(input)) {
				editableSearchResult = findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
				if (!changedSearchParameter) {
					searchInformation.setText(Messages.Search_Changes);
				}
			}
		}

		@Override
		public void elementDeleted(final IEditorInput input) {
			if (editorInputs.remove(input)) {
				LibraryElementProvider.INSTANCE.disconnect(input);
				editableSearchResult = editableSearchResult.stream()
						.filter(result -> !input.equals(getEditorInput(result))).toList();
				natTable.updateList(editableSearchResult);
			}
		}

		@Override
		public void elementMoved(final IEditorInput originalInput, final IEditorInput movedInput) {
			if (editorInputs.remove(originalInput)) {
				LibraryElementProvider.INSTANCE.disconnect(originalInput);
				connectEditorInputs(editableSearchResult);
				editableSearchResult = findEditableResults(editableSearchResult);
				natTable.updateList(editableSearchResult);
			}
		}
	}

	private class OperationContextUpdater implements IOperationHistoryListener {

		@Override
		public void historyNotification(final OperationHistoryEvent event) {
			if (event.getEventType() == OperationHistoryEvent.ABOUT_TO_EXECUTE
					&& event.getOperation().hasContext(commandStack.getUndoContext())
					&& event.getOperation() instanceof final ScopedOperation scopedOperation) {
				scopedOperation.getAffectedObjects().stream().map(EcoreUtil::getRootContainer)
						.filter(LibraryElement.class::isInstance).distinct().map(ObjectUndoContext::new)
						.forEachOrdered(event.getOperation()::addContext);
			}
		}
	}
}
