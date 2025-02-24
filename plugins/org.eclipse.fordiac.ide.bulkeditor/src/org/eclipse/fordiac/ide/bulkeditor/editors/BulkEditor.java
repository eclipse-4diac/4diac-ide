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

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.LiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.IEC61499ElementSearch;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;
import org.eclipse.fordiac.ide.model.search.types.ISearchChildrenProvider;
import org.eclipse.fordiac.ide.model.search.types.SearchChildrenProviderHelper;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
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
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class BulkEditor extends EditorPart implements CommandExecutor {

	// TODO: move Strings to plugin.properties

	private IProject project;

	private Button nameButton;
	private Text nameField;
	private Button typeButton;
	private Text typeField;
	private Button commentButton;
	private Text commentField;

	private NatTable natTable;
	private ChangeableListDataProvider provider;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		if (input instanceof final IFileEditorInput fileInput) {
			project = fileInput.getFile().getProject();
			setPartName(getPartName() + ": " + project.getName());
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		final Composite composite = WidgetFactory.composite(SWT.NONE).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).margins(20, 20).generateLayout(composite);

		final Combo mode = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		mode.setItems("Variable");
		mode.select(0);

		createSearchForGroup(composite);
		createSearchInGroup(composite);
		createScopeGroup(composite);
		createTable(composite);

		final Button button = WidgetFactory.button(SWT.PUSH).text("Search").create(composite);
		button.addListener(SWT.Selection, event -> {
			final var search = new IEC61499ElementSearch(new LiveSearchContext(project), new SearchFilter(),
					new InstanceSearchChildrenProvider());

			final var result = search.performSearch();
			provider.setInput(result);
			natTable.refresh();
		});
	}

	private class SearchFilter implements IEC61499SearchFilter {
		@Override
		public boolean apply(final EObject searchCandidate) {
			boolean state = false;
			if (searchCandidate instanceof final VarDeclaration decl) {
				state = true;
				if (nameButton.getSelection() && !decl.getName().equals(nameField.getText())) {
					state = false;
				}
				if (typeButton.getSelection() && !decl.getTypeName().equals(typeField.getText())) {
					state = false;
				}
				if (commentButton.getSelection() && !decl.getComment().equals(commentField.getText())) {
					state = false;
				}
			}

			return state;
		}
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
	}

	private static void createSearchInGroup(final Composite parent) {
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).create());
		searchGroup.setText("Search In");

		WidgetFactory.button(SWT.CHECK).text("FB and SubApp Types").create(searchGroup);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);

		WidgetFactory.button(SWT.CHECK).text("FB and Typed SubApp Instances").create(searchGroup);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);

		WidgetFactory.button(SWT.CHECK).text("Untyped SubApps").create(searchGroup);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Filter...").create(searchGroup);
	}

	private static void createScopeGroup(final Composite parent) {
		final Group searchGroup = new Group(parent, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(4).create());
		searchGroup.setText("Scope");

		WidgetFactory.button(SWT.RADIO).text("Workspace").create(searchGroup);
		WidgetFactory.button(SWT.RADIO).text("Resource in active editor").create(searchGroup);
		WidgetFactory.button(SWT.RADIO).text("Enclosing project").create(searchGroup);
		WidgetFactory.button(SWT.RADIO).text("Files opened in editor").create(searchGroup);

		WidgetFactory.button(SWT.RADIO).text("Resources").create(searchGroup);
		WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false)).create(searchGroup);
		WidgetFactory.button(SWT.NONE).text("Choose...").create(searchGroup);
	}

	private void createTable(final Composite parent) {
		// TODO add path of items
		provider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(provider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(provider));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		natTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
				IEditableRule.ALWAYS_EDITABLE, null, null, false);
		natTable.addConfiguration(new InitialValueEditorConfiguration(provider));
		natTable.addConfiguration(new TypeDeclarationEditorConfiguration(provider));
		natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		natTable.configure();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		// TODO
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// TODO
	}

	@Override
	public void doSaveAs() {
		// TODO
	}

	@Override
	public void executeCommand(final Command cmd) {
		// TODO
	}

	private static final class InstanceSearchChildrenProvider implements ISearchChildrenProvider {
		// TODO: see if correct and merge with other provider
		@Override
		public boolean hasChildren(final EObject obj) {
			return (obj instanceof FBType) || (obj instanceof AutomationSystem) || (obj instanceof UntypedSubApp)
					|| (obj instanceof final StructuredType) || (obj instanceof final AttributeDeclaration)
					|| (obj instanceof final Application) || (obj instanceof Attribute)
					|| (obj instanceof FBNetworkElement) || (obj instanceof IInterfaceElement);
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			if (obj instanceof final FBType fbType) {
				return SearchChildrenProviderHelper.getFBTypeChildren(fbType);
			}
			if (obj instanceof final AutomationSystem system) {
				return Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
			}

			if (obj instanceof final Application application) {
				Stream<? extends EObject> stream = application.getFBNetwork().getNetworkElements().stream();
				stream = Stream.concat(stream, application.getFBNetwork().getAdapterConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getDataConnections().stream());
				stream = Stream.concat(stream, application.getFBNetwork().getEventConnections().stream());
				return Stream.concat(stream, application.getAttributes().stream());
			}

			if (obj instanceof final UntypedSubApp untypedSubapp) {
				return SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
			}
			if (obj instanceof final StructuredType structType) {
				return SearchChildrenProviderHelper.getStructChildren(structType);
			}
			if (obj instanceof final AttributeDeclaration attrdecl) {
				return SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
			}

			if (obj instanceof final FBNetworkElement elem) {
				return Stream.concat(elem.getAttributes().stream(),
						SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
			}

			if (obj instanceof final IInterfaceElement interfaceElement) {
				return interfaceElement.getAttributes().stream();
			}

			if (obj instanceof final Attribute atrr) {
				return Stream.of(atrr.getType());
			}

			if (obj instanceof final ConfigurableObject object) {
				return object.getAttributes().stream();
			}

			return null;
		}
	}
}
