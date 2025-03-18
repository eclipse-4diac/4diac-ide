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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.editors.FilterComposite.Filter;
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
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.ISearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.search.types.ProjectInstanceSearchChildrenProvider;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
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
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

// TODO: remove SuppressWarning and move Strings to plugin.properties
@SuppressWarnings("nls")
public class BulkEditor extends EditorPart implements CommandExecutor, CommandStackEventListener {

	private static final List<String> DEFAULT_LIST = List.of("Name", "Type", "Comment", "Initial Value");
	private static final List<String> LIST_WITHOUT_VALUE = List.of("Name", "Type", "Comment");

	private IProject project;
	private final CommandStack commandStack = new CommandStack();
	private final Map<TypeEntry, CopyElementRecord> map = new HashMap<>();
	private BulkEditorSettings settings;

	// Search For
	private Combo modeSelectionDropDown;
	private FilterComposite searchFilter;

	// Search In
	private Button fbSubappTypesButton;
	private FilterComposite fbSubappTypesFilter;

	private Button fbTypedSubappInstanceButton;
	private FilterComposite fbTypedSubappInstanceFilter;

	private Button untypedSubappButton;
	private FilterComposite untypedSubappFilter;

	private Button dataTypesButton;
	private FilterComposite dataTypesFilter;

	private Button attributeTypesButton;
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
			setPartName(getPartName() + ": " + project.getName());
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

		WidgetFactory.label(SWT.NONE).text("Search For").create(composite);
		modeSelectionDropDown = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems("Variable", "Attribute");
		modeSelectionDropDown.select(settings.modeSelection);
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			changeNatTable(natTable.getParent(), modeSelectionDropDown.getSelectionIndex());
			settings.modeSelection = modeSelectionDropDown.getSelectionIndex();
			natTable.getParent().layout();
		});

		final Group searchGroup = WidgetFactory.group(SWT.NONE).text("Where").create(composite);
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
		// TODO: cleanup
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		searchGroup.setText("In");

		final Composite fbSubappTypesComposite = new Composite(searchGroup, SWT.NONE);
		fbSubappTypesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fbSubappTypesComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		fbSubappTypesButton = WidgetFactory.button(SWT.CHECK).text("FB and SubApp Types")
				.create(fbSubappTypesComposite);
		fbSubappTypesFilter = new FilterComposite(fbSubappTypesComposite, SWT.NONE, LIST_WITHOUT_VALUE, settings,
				BulkEditorSettings.inFBTypesSearchList);

		fbSubappTypesButton.addListener(SWT.Selection, event -> {
			updateVisibility(fbSubappTypesButton, fbSubappTypesFilter);
			settings.fbSubappTypes = fbSubappTypesButton.getSelection();
		});
		fbSubappTypesButton.setSelection(settings.fbSubappTypes);
		updateVisibility(fbSubappTypesButton, fbSubappTypesFilter);

		final Composite fbTypedSubappInstanceComposite = new Composite(searchGroup, SWT.NONE);
		fbTypedSubappInstanceComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fbTypedSubappInstanceComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		fbTypedSubappInstanceButton = WidgetFactory.button(SWT.CHECK).text("FB and Typed Subapp Instances")
				.create(fbTypedSubappInstanceComposite);
		fbTypedSubappInstanceFilter = new FilterComposite(fbTypedSubappInstanceComposite, SWT.NONE, LIST_WITHOUT_VALUE,
				settings, BulkEditorSettings.inFBInstanceSearchList);

		fbTypedSubappInstanceButton.addListener(SWT.Selection, event -> {
			updateVisibility(fbTypedSubappInstanceButton, fbTypedSubappInstanceFilter);
			settings.fbTypedSubappInstance = fbTypedSubappInstanceButton.getSelection();
		});
		fbTypedSubappInstanceButton.setSelection(settings.fbTypedSubappInstance);
		updateVisibility(fbTypedSubappInstanceButton, fbTypedSubappInstanceFilter);

		final Composite untypedSubappComposite = new Composite(searchGroup, SWT.NONE);
		untypedSubappComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		untypedSubappComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		untypedSubappButton = WidgetFactory.button(SWT.CHECK).text("Untyped Subapps").create(untypedSubappComposite);
		untypedSubappFilter = new FilterComposite(untypedSubappComposite, SWT.NONE, LIST_WITHOUT_VALUE, settings,
				BulkEditorSettings.inUntypedSubAppSearchList);

		untypedSubappButton.addListener(SWT.Selection, event -> {
			updateVisibility(untypedSubappButton, untypedSubappFilter);
			settings.untypedSubapp = untypedSubappButton.getSelection();
		});
		untypedSubappButton.setSelection(settings.untypedSubapp);
		updateVisibility(untypedSubappButton, untypedSubappFilter);

		final Composite dataTypesComposite = new Composite(searchGroup, SWT.NONE);
		dataTypesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		dataTypesComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		dataTypesButton = WidgetFactory.button(SWT.CHECK).text("Data Types").create(dataTypesComposite);
		dataTypesFilter = new FilterComposite(dataTypesComposite, SWT.NONE, LIST_WITHOUT_VALUE, settings,
				BulkEditorSettings.inDataTypesSearchList);

		dataTypesButton.addListener(SWT.Selection, event -> {
			updateVisibility(dataTypesButton, dataTypesFilter);
			settings.dataTypes = dataTypesButton.getSelection();
		});
		dataTypesButton.setSelection(settings.dataTypes);
		updateVisibility(dataTypesButton, dataTypesFilter);

		final Composite attributeTypesComposite = new Composite(searchGroup, SWT.NONE);
		attributeTypesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		attributeTypesComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());
		attributeTypesButton = WidgetFactory.button(SWT.CHECK).text("Attribute Types").create(attributeTypesComposite);
		attributeTypesFilter = new FilterComposite(attributeTypesComposite, SWT.NONE, LIST_WITHOUT_VALUE, settings,
				BulkEditorSettings.inAttributeTypesSearchList);

		attributeTypesButton.addListener(SWT.Selection, event -> {
			updateVisibility(attributeTypesButton, attributeTypesFilter);
			settings.attributeTypes = attributeTypesButton.getSelection();
		});
		attributeTypesButton.setSelection(settings.attributeTypes);
		updateVisibility(attributeTypesButton, attributeTypesFilter);
	}

	private static void updateVisibility(final Button button, final FilterComposite filter) {
		filter.setVisible(button.getSelection());
		((GridData) filter.getLayoutData()).exclude = !button.getSelection();

		filter.layout();
		updateLayout(filter.getParent().getParent());
	}

	private static void updateLayout(final Composite composite) {
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
		scopeGroup.setText("Scope");

		projectScopeButton = WidgetFactory.button(SWT.RADIO).text("Project: " + project.getName()).create(scopeGroup);
		projectScopeButton.setSelection(settings.projectScope);
		projectScopeButton.addListener(SWT.Selection, event -> {
			settings.projectScope = projectScopeButton.getSelection();
		});
		workspaceScopeButton = WidgetFactory.button(SWT.RADIO).text("Workspace").create(scopeGroup);
		workspaceScopeButton.setSelection(!projectScopeButton.getSelection());
	}

	private void createSearchButton(final Composite parent) {
		WidgetFactory.button(SWT.PUSH).text("Search").onSelect(event -> {
			final List<ISearchContext> contexts = createSearchContextList();

			map.clear();
			final var result = contexts.stream().flatMap(context -> new IEC61499ElementSearch(context,
					createSearchFilter(), new ProjectInstanceSearchChildrenProvider()).performSearch().stream())
					.toList();

			final List<EObject> mappedList = new ArrayList<>();
			for (final EObject libE : result) {
				if (EcoreUtil.getRootContainer(libE) instanceof final LibraryElement rootLibE) {
					final TypeEntry entry = rootLibE.getTypeEntry();
					if (!map.containsKey(entry)) {
						final LibraryElement copyRoot = entry.copyType(); // dont copy if entry already has copy
						map.put(entry, new CopyElementRecord(copyRoot, new ArrayList<>()));
					}
					final EObject copyLibE = EcoreUtil.getEObject(map.get(entry).copiedElement(),
							EcoreUtil.getRelativeURIFragmentPath(rootLibE, libE));
					map.get(entry).addToList(copyLibE);
					mappedList.add(copyLibE);
				}
			}

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
		// TODO: TypeSelectionButton (use correct TypeLibrary (more Projects))
		// add AttributeTypeSelection
		// improve Location Column (Not showing where Attributes of Struct-members are)
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
					new TypeSelectionButton(() -> TypeLibraryManager.INSTANCE.getTypeLibrary(project),
							DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE),
					null, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(attributeProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
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

	private List<ISearchContext> createSearchContextList() {
		if (workspaceScopeButton.getSelection()) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return Arrays.stream(root.getProjects()).filter(IProject::isOpen).map(this::createSearchContext)
					.map(ISearchContext.class::cast).toList();
		}
		if (projectScopeButton.getSelection()) {
			return List.of(createSearchContext(project));
		}
		return List.of();
	}

	private ISearchContext createSearchContext(final IProject project) {
		return new AbstractLiveSearchContext(project) {
			@Override
			public Stream<URI> getTypes() {
				Stream<URI> s = Stream.empty();
				if (fbSubappTypesButton.getSelection()) {
					s = Stream.concat(s, Stream.concat(getTypelib().getFbTypes().stream().map(TypeEntry::getURI),
							getTypelib().getSubAppTypes().stream().map(TypeEntry::getURI)));
				}
				if (dataTypesButton.getSelection()) {
					s = Stream.concat(s,
							getTypelib().getDataTypeLibrary().getDerivedDataTypes().stream().map(TypeEntry::getURI));
				}
				if (attributeTypesButton.getSelection()) {
					s = Stream.concat(s, getTypelib().getAttributeTypes().stream().map(TypeEntry::getURI));
				}
				return s.filter(Objects::nonNull);
			}

			@Override
			public LibraryElement getLibraryElement(final URI uri) {
				final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
				return typeEntry.getType(); // use original for search
			}

			@Override
			public Collection<URI> getSubappTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getFBTypes() {
				return Collections.emptyList();
			}

			@Override
			public Collection<URI> getAllTypes() {
				return Collections.emptyList();
			}
		};
	}

	private IEC61499SearchFilter createSearchFilter() {
		return new IEC61499SearchFilter() {
			private final Pattern namePattern = createPattern(searchFilter.getFilter(DEFAULT_LIST.get(0)));
			private final Pattern typePattern = createPattern(searchFilter.getFilter(DEFAULT_LIST.get(1)));
			private final Pattern commentPattern = createPattern(searchFilter.getFilter(DEFAULT_LIST.get(2)));
			private final Pattern valuePattern = createPattern(searchFilter.getFilter(DEFAULT_LIST.get(3)));

			@Override
			public boolean apply(final EObject searchCandidate) {
				if (!isValidCandidate(searchCandidate)) {
					return false;
				}

				final ITypedElement typedElement = (ITypedElement) searchCandidate;

				return matchesName(typedElement) && matchesType(typedElement) && matchesComment(typedElement)
						&& matchesInitialValue(typedElement);
			}

			private boolean isValidCandidate(final Object searchCandidate) {
				return (searchCandidate instanceof VarDeclaration && modeSelectionDropDown.getSelectionIndex() == 0)
						|| (searchCandidate instanceof Attribute && modeSelectionDropDown.getSelectionIndex() == 1);
			}

			private boolean matchesName(final ITypedElement element) {
				final Filter nameFilter = searchFilter.getFilter(DEFAULT_LIST.get(0));
				return !nameFilter.selected.getSelection()
						|| compareStrings(nameFilter, namePattern, element.getName());
			}

			private boolean matchesType(final ITypedElement element) {
				final Filter typeFilter = searchFilter.getFilter(DEFAULT_LIST.get(1));
				return !typeFilter.selected.getSelection()
						|| compareStrings(typeFilter, typePattern, element.getTypeName());
			}

			private boolean matchesComment(final ITypedElement element) {
				final Filter commentFilter = searchFilter.getFilter(DEFAULT_LIST.get(2));
				return !commentFilter.selected.getSelection()
						|| compareStrings(commentFilter, commentPattern, element.getComment());
			}

			private boolean matchesInitialValue(final ITypedElement element) {
				final Filter valueFilter = searchFilter.getFilter(DEFAULT_LIST.get(3));
				return !valueFilter.selected.getSelection() || compareStrings(valueFilter, valuePattern,
						InitialValueHelper.getInitialOrDefaultValue(element));
			}

			private static boolean compareStrings(final FilterComposite.Filter filter, final Pattern pattern,
					String element) {
				String search = filter.textField.getText();
				if (search == null || element == null) {
					return false;
				}

				if (!filter.caseSensitive.getSelection()) {
					element = element.toLowerCase();
					search = search.toLowerCase();
				}
				if (filter.regularExpression.getSelection() && pattern != null) {
					return pattern.matcher(element).find();
				}
				if (filter.wholeWord.getSelection()) {
					return element.equals(search);
				}
				return element.contains(search);
			}

			private Pattern createPattern(final Filter filter) {
				String query = filter.textField.getText();
				if (!filter.regularExpression.getSelection()) {
					return null;
				}
				if (!filter.caseSensitive.getSelection()) {
					query = query.toLowerCase();
				}
				if (filter.wholeWord.getSelection()) {
					query = "\\b" + query + "\\b";
				}
				try {
					// TODO: handle this better
					return Pattern.compile(query);
				} catch (final PatternSyntaxException exception) {
					return null;
				}
			}
		};
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
