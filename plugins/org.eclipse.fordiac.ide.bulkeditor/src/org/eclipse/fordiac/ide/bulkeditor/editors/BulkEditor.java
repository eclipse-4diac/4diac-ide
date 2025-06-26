/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.utilities.SubAppHierarchyDialog;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings.ScopeOption;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.forms.widgets.Twistie;
import org.eclipse.ui.part.EditorPart;

public class BulkEditor extends EditorPart implements CommandExecutor, CommandStackEventListener {
	private static final List<String> DEFAULT_LIST = List.of(Messages.Name, Messages.Type, Messages.Comment,
			Messages.InitialValue);
	private static final List<String> LIST_WITHOUT_VALUE = List.of(Messages.Name, Messages.Type, Messages.Comment);

	private IProject project;
	private final CommandStack commandStack = new CommandStack();
	private final Map<TypeEntry, CopyElementRecord> map = new HashMap<>();
	private BulkEditorSettings settings;
	private List<URI> selectedSubApps = Collections.emptyList();
	private final BulkEditorTypeEntryAdapter adapter = new BulkEditorTypeEntryAdapter(this);
	private final IPartListener2 focusListener = new IPartListener2() {
		@Override
		public void partActivated(final IWorkbenchPartReference partRef) {
			if (partRef.getPart(false) == BulkEditor.this) {
				checkTypeEntriesForDirty();
			}
		}
	};

	// Search For
	private Combo modeSelectionDropDown;
	private FilterComposite searchFilter;
	private boolean changedSearchParameter = false;

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

	private Label searchInformation;
	private Label dirtyInformation;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		commandStack.addCommandStackEventListener(this);

		if (input instanceof final BulkEditorInput bulkEditorInput) {
			this.settings = bulkEditorInput.getSettings();
			project = bulkEditorInput.getProject();
			selectedSubApps = bulkEditorInput.getInitialSelectedSubApps();
			setPartName(getPartName() + ": " + project.getName()); //$NON-NLS-1$
		}
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
		final var headerLayout = new GridLayout(2, false);

		final Composite pageBodyComposite = new Composite(pageComposite, SWT.NONE);
		pageBodyComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pageBodyComposite.setLayout(new GridLayout(1, false));

		// header
		pageHeaderComposite.setLayout(headerLayout);
		WidgetFactory.label(SWT.NONE).text(Messages.SearchFor).create(pageHeaderComposite);
		final Twistie expandBodyCompositeTwistie = new Twistie(pageHeaderComposite, SWT.NONE);
		expandBodyCompositeTwistie.setExpanded(true);
		expandBodyCompositeTwistie.addListener(SWT.MouseUp, event -> {
			final boolean isVisible = expandBodyCompositeTwistie.isExpanded();
			updateVisibility(isVisible, pageBodyComposite);
		});

		// body
		modeSelectionDropDown = new Combo(pageBodyComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems(Messages.Variable, Messages.Attribute);
		modeSelectionDropDown.select(settings.modeSelection);
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			final int choice = openUnsavedChangesDialog();
			if (choice == 1) {
				return;
			}
			natTable.changeNatTable(modeSelectionDropDown.getSelectionIndex());
			settings.modeSelection = modeSelectionDropDown.getSelectionIndex();
			pageComposite.layout();
			changedSearchParameter = false;
			searchInformation.setText(""); //$NON-NLS-1$
			commandStack.flush();
		});

		createSearchWhereGroup(pageBodyComposite);
		createSearchInGroup(pageBodyComposite);
		createScopeGroup(pageBodyComposite);
		createSearchButton(pageBodyComposite);
		natTable = new BulkEditorNatTable(pageComposite, this, settings.modeSelection);

		scrolledComposite.setMinSize(pageComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		pageComposite.layout();
		getSite().getPage().addPartListener(focusListener);
	}

	private void createSearchWhereGroup(final Composite parent) {
		final Group searchWhereGroup = createCollapsibleGroup(parent, Messages.SearchWhere,
				button -> button.addListener(SWT.Selection, event -> {
					searchFilter.clear();
				}));

		searchFilter = new FilterComposite(searchWhereGroup, SWT.NONE, DEFAULT_LIST, settings,
				BulkEditorSettings.whereSearchList);
		searchFilter.addFilterChangedListener(() -> {
			this.changedSearchParameter = true;
		});
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
		GridLayoutFactory.fillDefaults().numColumns(3).margins(0, 0).generateLayout(composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		WidgetFactory.button(SWT.PUSH).text(Messages.Search).onSelect(event -> {
			final int choice = openUnsavedChangesDialog();
			if (choice == 1) {
				return;
			}
			performSearch();
			checkTypeEntriesForDirty();
		}).create(composite);

		dirtyInformation = WidgetFactory.label(SWT.NONE).create(composite);
		dirtyInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		searchInformation = WidgetFactory.label(SWT.NONE).create(composite);
		searchInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
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
		if (selectedSubApps.size() == 0) {
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

	private int openUnsavedChangesDialog() {
		if (commandStack.isDirty()) {
			final MessageDialog dialog = new MessageDialog(this.getSite().getShell(), "", null, //$NON-NLS-1$
					Messages.Unsaved_Changes, MessageDialog.QUESTION,
					new String[] { Messages.Continue, Messages.Cancel }, 0);
			return dialog.open();
		}
		return 0;
	}

	private IEditorPart[] getDirtyEditors() {
		return EditorUtils.findEditor(part -> {
			if (!part.isDirty()) {
				return false;
			}

			final LibraryElement libE = part.getAdapter(LibraryElement.class);
			if (libE == null) {
				return false;
			}

			return map.containsKey(libE.getTypeEntry());
		});
	}

	private void checkTypeEntriesForDirty() {
		final var editors = getDirtyEditors();

		if (editors.length <= 0) {
			return;
		}

		final StringBuilder sb = new StringBuilder();
		for (final IEditorPart editor : editors) {
			sb.append("\n"); //$NON-NLS-1$
			sb.append(editor.getAdapter(LibraryElement.class).getTypeEntry().getFile().getFullPath().toOSString());
		}
		sb.append("\n"); //$NON-NLS-1$

		final MessageDialog dialog = new MessageDialog(this.getSite().getShell(), "", null, //$NON-NLS-1$
				MessageFormat
						.format(editors.length > 1 ? Messages.Dirty_Editors : Messages.Dirty_Editor, sb.toString()),
				MessageDialog.QUESTION,
				new String[] { Messages.Dirty_Editor_IgnoreChange,
						editors.length > 1 ? Messages.Dirty_Editors_SaveAndSearch
								: Messages.Dirty_Editor_SaveAndSearch },
				0);
		final var choise = dialog.open();

		if (choise == 1) {
			for (final IEditorPart editor : editors) {
				editor.doSave(new NullProgressMonitor());
			}
			dirtyInformation.setText(""); //$NON-NLS-1$
			performSearch();
		} else {
			dirtyInformation.setText(Messages.Other_Dirty_Editor);
		}
		dirtyInformation.getParent().layout();
	}

	private void performSearch() {
		final SearchHelper helper = new SearchHelper(
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

		final var result = contexts.stream()
				.flatMap(context -> new IEC61499ElementSearch(context,
						SearchHelper.createSearchFilter(modeSelectionDropDown.getSelectionIndex(),
								DEFAULT_LIST.stream().map(searchFilter::getFilter).toList()),
						helper.createChildrenSearchProvider()).performSearch().stream())
				.toList();

		final List<EObject> mappedList = createMappedList(result);
		natTable.updateList(mappedList);
		commandStack.flush();
		changedSearchParameter = false;
		searchInformation.setText(""); //$NON-NLS-1$
	}

	private List<EObject> createMappedList(final List<? extends EObject> list) {
		final List<EObject> mappedList = new ArrayList<>();
		map.keySet().forEach(typeEntry -> typeEntry.eAdapters().remove(adapter));
		map.clear();
		for (final EObject libE : list) {
			if (EcoreUtil.getRootContainer(libE) instanceof final LibraryElement rootLibE) {
				final TypeEntry entry = rootLibE.getTypeEntry();
				map.computeIfAbsent(entry, e -> {
					entry.eAdapters().add(adapter);
					return new CopyElementRecord(e.copyType(), new ArrayList<>());
				});
				final EObject copyLibE = EcoreUtil.getEObject(map.get(entry).copiedElement(),
						EcoreUtil.getRelativeURIFragmentPath(rootLibE, libE));
				map.get(entry).addToList(copyLibE);
				mappedList.add(copyLibE);
			}
		}
		return mappedList;
	}

	@Override
	public boolean isDirty() {
		return commandStack.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void setFocus() {
		adapter.checkFileReload();
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
				final var affect = Arrays.stream(commandStack.getCommands()).filter(ScopedCommand.class::isInstance)
						.flatMap(cmd -> ((ScopedCommand) cmd).getAffectedObjects().stream()).map(eobj -> {
							if (EcoreUtil.getRootContainer(eobj) instanceof final LibraryElement rootLibE) {
								return rootLibE.getTypeEntry();
							}
							return null;
						}).filter(Objects::nonNull).distinct().toList();

				affect.forEach(entry -> {
					try {
						if (map.containsKey(entry)) {
							entry.eAdapters().remove(adapter);
							entry.save(map.get(entry).copiedElement());
							entry.eAdapters().add(adapter);
						}
					} catch (final CoreException e) {
						e.printStackTrace();
					}
				});
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
		super.dispose();
		getSite().getPage().removePartListener(focusListener);
		map.keySet().forEach(typeEntry -> typeEntry.eAdapters().remove(adapter));
	}

	@Override
	public void executeCommand(final Command cmd) {
		commandStack.execute(cmd);
		if (!changedSearchParameter) {
			searchInformation.setText(Messages.Search_Changes);
		}
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
