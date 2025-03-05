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
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
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
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

// TODO: remove SuppressWarning and move Strings to plugin.properties
@SuppressWarnings("nls")
public class BulkEditor extends EditorPart implements CommandExecutor, CommandStackEventListener {

	private IProject project;
	private Combo modeSelectionDropDown;
	private final CommandStack commandStack = new CommandStack();
	private final Map<TypeEntry, CopyElementRecord> map = new HashMap<>();

	private Button nameButton;
	private Text nameField;
	private Button typeButton;
	private Text typeField;
	private Button commentButton;
	private Text commentField;
	private Button initialValueButton;
	private Text initialValueField;

	private Button fbSubappTypesButton;
	private Button dataTypesButton;
	private Button attributeTypesButton;

	private Button workspaceScopeButton;
	private Button projectScopeButton;

	private NatTable natTable;
	private ChangeableListDataProvider<Attribute> attributeProvider;
	private ChangeableListDataProvider<VarDeclaration> varDeclProvider;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		commandStack.addCommandStackEventListener(this);

		if (input instanceof final IFileEditorInput fileInput) {
			project = fileInput.getFile().getProject();
			setPartName(getPartName() + ": " + project.getName());
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		final Composite composite = WidgetFactory.composite(SWT.NONE).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).margins(20, 20).generateLayout(composite);

		final Composite buttonComposite = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(0, 0).generateLayout(buttonComposite);

		modeSelectionDropDown = new Combo(buttonComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		modeSelectionDropDown.setItems("Variable", "Attribute");
		modeSelectionDropDown.select(0);
		modeSelectionDropDown.addListener(SWT.Selection, event -> {
			changeNatTable(composite, modeSelectionDropDown.getSelectionIndex());
			composite.layout();
		});

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
					final EObject copyLibE = EcoreUtil.getEObject(map.get(entry).copy(),
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
		}).create(buttonComposite);

		createSearchForGroup(composite);
		createSearchInGroup(composite);
		createScopeGroup(composite);
		createTable(composite);
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
					IEditableRule.ALWAYS_EDITABLE, null, null, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new TypeDeclarationEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		} else {
			// TODO: refine Attribute Table
			attributeProvider = new ChangeableListDataProvider<>(
					new AttributeColumnAccessor(this, AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final DataLayer dataLayer = new DataLayer(attributeProvider);
			dataLayer.setConfigLabelAccumulator(new AttributeConfigLabelAccumulator(attributeProvider, () -> null,
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION));
			final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			natTable = NatTableWidgetFactory.createRowNatTable(parent, dataLayer, columnProvider,
					IEditableRule.ALWAYS_EDITABLE, null, null, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(attributeProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		}
		natTable.configure();
	}

	private void createSearchForGroup(final Composite parent) {
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());
		searchGroup.setText("Search For");

		nameButton = WidgetFactory.button(SWT.CHECK).text("Name").create(searchGroup);
		nameField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
				.create(searchGroup);

		typeButton = WidgetFactory.button(SWT.CHECK).text("Type").create(searchGroup);
		typeField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
				.create(searchGroup);

		commentButton = WidgetFactory.button(SWT.CHECK).text("Comment").create(searchGroup);
		commentField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
				.create(searchGroup);

		initialValueButton = WidgetFactory.button(SWT.CHECK).text("Initial Value").create(searchGroup);
		initialValueField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
				.create(searchGroup);
	}

	private void createSearchInGroup(final Composite parent) {
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).create());
		searchGroup.setText("Search In");

		fbSubappTypesButton = WidgetFactory.button(SWT.CHECK).text("FB and SubApp Types").create(searchGroup);
		fbSubappTypesButton.setSelection(true);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);

		dataTypesButton = WidgetFactory.button(SWT.CHECK).text("Data Types").create(searchGroup);
		dataTypesButton.setSelection(true);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);

		attributeTypesButton = WidgetFactory.button(SWT.CHECK).text("Attribute Types").create(searchGroup);
		attributeTypesButton.setSelection(true);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);
	}

	private void createScopeGroup(final Composite parent) {
		final Group scopeGroup = new Group(parent, SWT.NONE);
		scopeGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		scopeGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(4).create());
		scopeGroup.setText("Scope");

		projectScopeButton = WidgetFactory.button(SWT.RADIO).text("Project: " + project.getName()).create(scopeGroup);
		projectScopeButton.setSelection(true);
		workspaceScopeButton = WidgetFactory.button(SWT.RADIO).text("Workspace").create(scopeGroup);
	}

	private void createTable(final Composite parent) {
		changeNatTable(parent, 0);
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
					entry.save(map.get(entry).copy());
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
				return !nameButton.getSelection() || element.getName().equals(nameField.getText());
			}

			private boolean matchesType(final ITypedElement element) {
				return !typeButton.getSelection() || element.getTypeName().equals(typeField.getText());
			}

			private boolean matchesComment(final ITypedElement element) {
				return !commentButton.getSelection() || element.getComment().equals(commentField.getText());
			}

			private boolean matchesInitialValue(final ITypedElement element) {
				return !initialValueButton.getSelection()
						|| InitialValueHelper.getInitialOrDefaultValue(element).equals(initialValueField.getText());
			}
		};
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
