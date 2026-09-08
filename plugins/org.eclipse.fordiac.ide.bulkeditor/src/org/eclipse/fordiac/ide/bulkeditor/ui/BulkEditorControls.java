/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.ui;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.utilities.SubAppHierarchyDialog;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditor;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings.ScopeOption;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchParameters;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Twistie;

public class BulkEditorControls {

	public static final List<String> DEFAULT_LIST = List.of(Messages.Name, Messages.Type, Messages.Comment,
			Messages.InitialValue);
	public static final List<String> LIST_WITHOUT_VALUE = List.of(Messages.Name, Messages.Type, Messages.Comment);

	private final BulkEditorSettings settings;
	private final BulkEditor editor;

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
	private List<URI> selectedSubApps = Collections.emptyList();

	public BulkEditorControls(final BulkEditorSettings settings, final BulkEditor editor,
			final List<URI> initialSelectedSubApps) {
		this.settings = settings;
		this.editor = editor;
		this.selectedSubApps = initialSelectedSubApps;
	}

	public void createControls(final Composite parent) {
		createModeSelectionComposite(parent);
		createSearchWhereGroup(parent);
		createSearchInGroup(parent);
		createScopeGroup(parent);
		WidgetFactory.button(SWT.PUSH).text(Messages.Search).onSelect(event -> editor.onSearchRequested())
				.create(parent);
	}

	private void createModeSelectionComposite(final Composite parent) {
		final Composite modeSelectionComposite = new Composite(parent, SWT.NONE);
		modeSelectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		modeSelectionComposite.setLayout(new GridLayout(2, false));

		modeSelectionDropDown = new Combo(modeSelectionComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems(Messages.Variable, Messages.Attribute);
		modeSelectionDropDown.select(BulkEditorMode.getComboBoxIndex(settings.modeSelection));
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			final int prevIndex = BulkEditorMode.getComboBoxIndex(settings.modeSelection);
			if (modeSelectionDropDown.getSelectionIndex() == prevIndex) {
				return;
			}
			if (!editor.confirmDiscardUnsavedChanges()) {
				modeSelectionDropDown.select(prevIndex);
				return;
			}

			final BulkEditorMode newMode = BulkEditorMode.resolve(modeSelectionDropDown.getSelectionIndex(),
					advancedButton.getSelection());
			settings.modeSelection = newMode;

			advancedButton.setVisible(BulkEditorMode.isAttributeMode(newMode));
			advancedButton.setSelection(BulkEditorMode.isAdvancedMode(newMode));

			changeSearchWhereGroupFilter(newMode);
			parent.getParent().layout();
			changedSearchParameter = false;
			editor.onModeChanged(newMode);
		});

		advancedButton = WidgetFactory.button(SWT.TOGGLE).text(Messages.Advanced).onSelect(event -> {
			final BulkEditorMode newMode = BulkEditorMode.resolve(modeSelectionDropDown.getSelectionIndex(),
					advancedButton.getSelection());
			settings.modeSelection = newMode;

			changeSearchWhereGroupFilter(newMode);
			parent.getParent().layout();
			editor.onModeChanged(newMode);
		}).create(modeSelectionComposite);
		advancedButton.setVisible(BulkEditorMode.isAttributeMode(settings.modeSelection));
		advancedButton.setSelection(BulkEditorMode.isAdvancedMode(settings.modeSelection));
	}

	private void createSearchWhereGroup(final Composite parent) {
		searchWhereGroup = BulkEditorWidgetUtils.createCollapsibleGroup(parent, Messages.SearchWhere,
				button -> button.addListener(SWT.Selection, event -> {
					if (searchFilter != null && !searchFilter.isDisposed()) {
						searchFilter.clear();
					}
					if (searchText != null && !searchText.isDisposed()) {
						searchText.setText(""); //$NON-NLS-1$
					}
				}));
		changeSearchWhereGroupFilter(settings.modeSelection);
	}

	private void changeSearchWhereGroupFilter(final BulkEditorMode mode) {
		for (final Control child : searchWhereGroup.getChildren()) {
			child.dispose();
		}

		if (mode == BulkEditorMode.SIMPLE_ATTRIBUTE) {
			final Composite simpleTextComposite = new Composite(searchWhereGroup, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(2).margins(0, 0).generateLayout(simpleTextComposite);
			simpleTextComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			WidgetFactory.label(SWT.NONE).text(Messages.Name).create(simpleTextComposite);
			searchText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
					.create(simpleTextComposite);

			final IContentProposalProvider proposalProvider = new TypeSelectionProposalProvider(
					() -> TypeLibraryManager.INSTANCE.getTypeLibrary(editor.getProject()),
					AttributeSelectionContentProvider.INSTANCE);
			final ContentProposalAdapter proposalAdapter = new ContentProposalAdapter(searchText,
					new TextContentAdapter(), proposalProvider, KeyStroke.getInstance(SWT.CTRL, SWT.SPACE),
					NatTableWidgetFactory.getActivationChars());
			proposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		} else {
			searchFilter = new FilterComposite(searchWhereGroup, SWT.NONE, DEFAULT_LIST, settings,
					BulkEditorSettings.whereSearchList);
			searchFilter.addFilterChangedListener(() -> this.changedSearchParameter = true);
		}
		searchWhereGroup.layout();
	}

	private void createSearchInGroup(final Composite parent) {
		final Group searchGroup = BulkEditorWidgetUtils.createCollapsibleGroup(parent, Messages.SearchIn,
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

	private FilterComposite createSearchFilterInGroup(final Composite parent, final String name,
			final List<String> subSettingsReferencesNames, final boolean initialSelection,
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
		categorySelectionButton.setSelection(initialSelection);

		final FilterComposite filterComposite = new FilterComposite(searchInCategoryComposite, SWT.NONE,
				LIST_WITHOUT_VALUE, settings, subSettingsReferencesNames);

		final Twistie expandFilterCompositeTwistie = new Twistie(searchInCategorySubComposite, SWT.NONE);
		expandFilterCompositeTwistie.addListener(SWT.MouseUp, event -> BulkEditorWidgetUtils
				.updateVisibility(expandFilterCompositeTwistie.isExpanded(), filterComposite));
		BulkEditorWidgetUtils.updateVisibility(false, filterComposite);

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

	private void createScopeGroup(final Composite parent) {
		final Group scopeGroup = BulkEditorWidgetUtils.createCollapsibleGroup(parent, Messages.Scope, null);
		final Composite groupContent = new Composite(scopeGroup, SWT.NONE);
		groupContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupContent.setLayout(GridLayoutFactory.swtDefaults().numColumns(5).create());

		projectScopeButton = WidgetFactory.button(SWT.RADIO)
				.text(MessageFormat.format(Messages.Project, editor.getProject().getName())).create(groupContent);
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
		refreshSubappHierarchyText();
		subappHierarchyScopeLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		subappHierarchyScopeLabel.setVisible(subappHierarchyScopeButton.getSelection());
	}

	private void openScopeDialog() {
		final var elements = selectedSubApps.stream().map(uri -> {
			final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
			return typeEntry.getType().eResource().getEObject(uri.fragment());
		}).toList();
		final var dialog = new SubAppHierarchyDialog(editor.getProject(), elements);
		final var result = dialog.open();
		if (result != null) {
			selectedSubApps = SubAppHierarchyDialog.mapResultToURIs(result);
			settings.subappHierarchies = selectedSubApps;
		}
		refreshSubappHierarchyText();
	}

	private void refreshSubappHierarchyText() {
		if (selectedSubApps.isEmpty()) {
			subappHierarchyScopeLabel.setForeground(new Color(255, 0, 0));
			subappHierarchyScopeLabel.setText(Messages.NoHierarchySelected);
			return;
		}

		final var text = selectedSubApps.stream().map(uri -> {
			final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
			return typeEntry.getType().eResource().getEObject(uri.fragment());
		}).map(eObject -> {
			if (EcoreUtil.getRootContainer(eObject) instanceof final INamedElement rootElement
					&& rootElement != eObject) {
				return rootElement.getName() + "." + FordiacMarkerHelper.getLocation(eObject); //$NON-NLS-1$
			}
			return FordiacMarkerHelper.getLocation(eObject);
		}).collect(Collectors.joining("; ")); //$NON-NLS-1$

		subappHierarchyScopeLabel.setForeground(new Color(0, 0, 0));
		subappHierarchyScopeLabel.setText(text);
	}

	public SearchParameters collectParameters() {
		return new SearchParameters(modeSelectionDropDown.getSelectionIndex(), advancedButton.getSelection(),
				searchText, searchFilter, fbSubappTypesFilter, settings.fbSubappTypes, fbTypedSubappInstanceFilter,
				settings.fbTypedSubappInstance, untypedSubappFilter, settings.untypedSubapp, dataTypesFilter,
				settings.dataTypes, attributeTypesFilter, settings.attributeTypes,
				ignoreLinkedLibrariesButton.getSelection(), workspaceScopeButton.getSelection(),
				projectScopeButton.getSelection(), subappHierarchyScopeButton.getSelection(), selectedSubApps);
	}

	public int getModeSelection() {
		return modeSelectionDropDown.getSelectionIndex();
	}

	public boolean isAdvancedMode() {
		return advancedButton.getSelection();
	}

	public Text getSearchText() {
		return searchText;
	}

	public boolean hasChangedSearchParameter() {
		return changedSearchParameter;
	}

	public void resetChangedSearchParameter() {
		changedSearchParameter = false;
	}
}
