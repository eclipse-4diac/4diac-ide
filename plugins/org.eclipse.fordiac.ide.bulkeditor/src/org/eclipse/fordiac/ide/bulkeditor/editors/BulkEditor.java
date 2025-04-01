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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeEditableRule;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportContentProposal;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportTypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
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

	// Search For
	private Combo modeSelectionDropDown;
	private FilterComposite searchFilter;

	// Search In
	private FilterComposite fbSubappTypesFilter;
	private FilterComposite fbTypedSubappInstanceFilter;
	private FilterComposite untypedSubappFilter;
	private FilterComposite dataTypesFilter;
	private FilterComposite attributeTypesFilter;

	// Scope
	private Button workspaceScopeButton;
	private Button projectScopeButton;

	// NatTable
	private NatTable natTable;
	private ChangeableListDataProvider<Attribute> attributeProvider;
	private ChangeableListDataProvider<VarDeclaration> varDeclProvider;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		commandStack.addCommandStackEventListener(this);

		if (input instanceof final BulkEditorInput bulkEditorInput) {
			this.settings = bulkEditorInput.getSettings();
			project = bulkEditorInput.getProject();
			setPartName(getPartName() + ": " + project.getName()); //$NON-NLS-1$
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setBackground(parent.getBackground());
		scrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		final Composite composite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite);
		GridLayoutFactory.fillDefaults().numColumns(1).margins(20, 20).generateLayout(composite);

		WidgetFactory.label(SWT.NONE).text(Messages.SearchFor).create(composite);
		modeSelectionDropDown = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems(Messages.Variable, Messages.Attribute);
		modeSelectionDropDown.select(settings.modeSelection);
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			changeNatTable(natTable.getParent(), modeSelectionDropDown.getSelectionIndex());
			settings.modeSelection = modeSelectionDropDown.getSelectionIndex();
			natTable.getParent().layout();
		});

		final Group searchGroup = WidgetFactory.group(SWT.NONE).text(Messages.SearchWhere).create(composite);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());

		searchFilter = new FilterComposite(searchGroup, SWT.NONE, DEFAULT_LIST, settings,
				BulkEditorSettings.whereSearchList);

		createSearchInGroup(composite);
		createScopeGroup(composite);
		createSearchButton(composite);
		createNatTable(composite, settings.modeSelection);

		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.layout();
	}

	private static <T> List<T> mapList(final List<EObject> ori, final Class<T> clazz) {
		final List<T> result = new ArrayList<>();
		for (final EObject obj : ori) {
			if (clazz.isInstance(obj)) {
				result.add(clazz.cast(obj));
			}
		}
		return result;
	}

	private void createSearchInGroup(final Composite parent) {
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		searchGroup.setText(Messages.SearchIn);

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

		final Label stateLabel = new Label(searchInCategorySubComposite, SWT.LEAD);
		stateLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		filterComposite.addTextChangedListener(stateLabel::setText);

		return filterComposite;
	}

	private static void updateVisibility(final boolean visible, final FilterComposite filter) {
		filter.setVisible(visible);
		((GridData) filter.getLayoutData()).exclude = !visible;

		filter.layout();
		final Composite composite = filter.getParent().getParent();
		composite.layout();
		if (composite.getParent().getParent() instanceof final ScrolledComposite sc) {
			sc.setMinSize(composite.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
		composite.getParent().layout();
	}

	private void createScopeGroup(final Composite parent) {
		final Group scopeGroup = new Group(parent, SWT.NONE);
		scopeGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		scopeGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(4).create());
		scopeGroup.setText(Messages.Scope);

		projectScopeButton = WidgetFactory.button(SWT.RADIO)
				.text(MessageFormat.format(Messages.Project, project.getName())).create(scopeGroup);
		projectScopeButton.setSelection(settings.projectScope);
		projectScopeButton.addListener(SWT.Selection,
				event -> settings.projectScope = projectScopeButton.getSelection());
		workspaceScopeButton = WidgetFactory.button(SWT.RADIO).text(Messages.Workspace).create(scopeGroup);
		workspaceScopeButton.setSelection(!projectScopeButton.getSelection());
	}

	private void createSearchButton(final Composite parent) {
		WidgetFactory.button(SWT.PUSH).text(Messages.Search).onSelect(event -> {
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
							attributeTypesFilter.getFilter(LIST_WITHOUT_VALUE.get(2))));

			final List<ISearchContext> contexts = helper.createSearchContextList(workspaceScopeButton.getSelection(),
					projectScopeButton.getSelection(), project);

			final var result = contexts.stream()
					.flatMap(context -> new IEC61499ElementSearch(context,
							SearchHelper.createSearchFilter(modeSelectionDropDown.getSelectionIndex(),
									DEFAULT_LIST.stream().map(searchFilter::getFilter).toList()),
							helper.createChildrenSearchProvider()).performSearch().stream())
					.toList();

			final List<EObject> mappedList = createMappedList(result);

			if (modeSelectionDropDown.getSelectionIndex() == 0
					&& (mappedList.isEmpty() || mappedList.getFirst() instanceof VarDeclaration)) {
				varDeclProvider.setInput(mapList(mappedList, VarDeclaration.class));
			} else if (modeSelectionDropDown.getSelectionIndex() == 1
					&& (mappedList.isEmpty() || mappedList.getFirst() instanceof Attribute)) {
				attributeProvider.setInput(mapList(mappedList, Attribute.class));
			}
			natTable.refresh();
		}).create(parent);
	}

	private List<EObject> createMappedList(final List<? extends EObject> list) {
		final List<EObject> mappedList = new ArrayList<>();
		map.clear();
		for (final EObject libE : list) {
			if (EcoreUtil.getRootContainer(libE) instanceof final LibraryElement rootLibE) {
				final TypeEntry entry = rootLibE.getTypeEntry();
				map.computeIfAbsent(entry, e -> new CopyElementRecord(e.copyType(), new ArrayList<>()));
				final EObject copyLibE = EcoreUtil.getEObject(map.get(entry).copiedElement(),
						EcoreUtil.getRelativeURIFragmentPath(rootLibE, libE));
				map.get(entry).addToList(copyLibE);
				mappedList.add(copyLibE);
			}
		}
		return mappedList;
	}

	private void createNatTable(final Composite parent, final int selectionIndex) {
		final Composite comp = new Composite(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = (parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y / DataLayer.DEFAULT_ROW_HEIGHT + 1)
				* DataLayer.DEFAULT_ROW_HEIGHT;
		comp.setLayoutData(gridData);
		GridLayoutFactory.fillDefaults().numColumns(1).margins(0, 0).generateLayout(comp);

		changeNatTable(comp, selectionIndex);
	}

	private void changeNatTable(final Composite parent, final int selectionIndex) {
		if (natTable != null) {
			natTable.dispose();
		}
		if (selectionIndex == 0) {
			varDeclProvider = new ChangeableListDataProvider<>(
					new VarDeclarationColumnAccessor(this, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final DataLayer inputDataLayer = new VarDeclarationDataLayer(varDeclProvider,
					VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(varDeclProvider,
					() -> null, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
					VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			natTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
					new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE,
							VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION,
							VarDeclarationTableColumn.EDITABLE_NO_LOCATION),
					null, null, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new TypeDeclarationEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		} else {
			attributeProvider = new ChangeableListDataProvider<>(
					new AttributeColumnAccessor(this, AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final DataLayer dataLayer = new DataLayer(attributeProvider);
			dataLayer.setConfigLabelAccumulator(new AttributeConfigLabelAccumulator(attributeProvider, () -> null,
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			natTable = NatTableWidgetFactory.createRowNatTable(parent, dataLayer, columnProvider,
					new AttributeEditableRule(IEditableRule.ALWAYS_EDITABLE,
							AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION,
							AttributeTableColumn.EDITABLE_NO_LOCATION, attributeProvider),
					new TypeSelectionButton(() -> {
						final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
								.getLastSelectedCellPosition().getRowPosition();
						if (EcoreUtil.getRootContainer(attributeProvider
								.getRowObject(relevantRowIndex)) instanceof final LibraryElement libElement) {
							return libElement.getTypeLibrary();
						}
						return null;
					}, DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE), null,
					false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(attributeProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));

			final Predicate<TypeEntry> targetFilter = entry -> {
				if (entry.getType() instanceof final AttributeDeclaration decl) {
					final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
							.getLastSelectedCellPosition().getRowPosition();
					if (attributeProvider.getRowObject(relevantRowIndex)
							.eContainer() instanceof final ConfigurableObject configurableObject) {
						return decl.isValidObject(configurableObject);
					}
				}
				return true;
			};

			final AttributeNameCellEditor attributeNameCellEditor = new AttributeNameCellEditor();
			attributeNameCellEditor.enableContentProposal(new TextContentAdapter(),
					new ImportTypeSelectionProposalProvider(() -> {
						final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
								.getLastSelectedCellPosition().getRowPosition();
						return attributeProvider.getRowObject(relevantRowIndex).eContainer();
					}, TypeLibrary::getAttributeTypeEntry, AttributeSelectionContentProvider.INSTANCE, targetFilter),
					KeyStroke.getInstance(SWT.CTRL, SWT.SPACE), null);
			natTable.addConfiguration(new AbstractRegistryConfiguration() {
				@Override
				public void configureRegistry(final IConfigRegistry configRegistry) {
					configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, attributeNameCellEditor,
							DisplayMode.EDIT, NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
				}
			});
		}

		natTable.configure();
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
		// nothing done here
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
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
					entry.save(map.get(entry).copiedElement());
				}
			} catch (final CoreException e) {
				e.printStackTrace();
			}
		});
		commandStack.markSaveLocation();
	}

	@Override
	public void doSaveAs() {
		// should not be used with this editor
	}

	@Override
	public void executeCommand(final Command cmd) {
		commandStack.execute(cmd);
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	protected class AttributeNameCellEditor extends TextCellEditor {
		@Override
		protected void configureContentProposalAdapter(final ContentProposalAdapter contentProposalAdapter) {
			contentProposalAdapter.addContentProposalListener(this::proposalAccepted);
			super.configureContentProposalAdapter(contentProposalAdapter);
		}

		protected void proposalAccepted(final IContentProposal proposal) {
			if (proposal instanceof final ImportContentProposal importProposal
					&& EcoreUtil.getRootContainer(attributeProvider.getRowObject(this.getRowIndex())
							.eContainer()) instanceof final LibraryElement libraryElement
					&& !ImportHelper.matchesImports(importProposal.getImportedNamespace(), libraryElement)) {
				executeCommand(new AddNewImportCommand(libraryElement, importProposal.getImportedNamespace()));
			}
		}
	}
}
