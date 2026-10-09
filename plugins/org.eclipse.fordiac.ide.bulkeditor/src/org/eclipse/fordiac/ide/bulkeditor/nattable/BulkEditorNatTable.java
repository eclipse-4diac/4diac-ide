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
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.gef.nat.FilterDefaultValuesCopyDataCommandHandler;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.ContextMenuConfiguration;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.nattable.PasteFromClipboardDataCommandHandler;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.resize.command.AutoResizeColumnsCommand;
import org.eclipse.nebula.widgets.nattable.selection.RowSelectionProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class BulkEditorNatTable {
	private static final String CONTEXT_MENU_ID = "org.eclipse.fordiac.ide.bulkeditor.contextMenu"; //$NON-NLS-1$

	private final CommandExecutor commandExecutor;
	private final Composite parent;
	private ISelectionProvider currentProvider;

	private BulkEditorMode currentMode;
	private NatTable natTable;
	private Composite addDeleteComposite;
	private Label searchInformation;

	private ChangeableListDataProvider<? extends EObject> provider;
	private SorterModel<? extends EObject> sorterModel;

	private final MenuManager contextMenuManager = new MenuManager();

	private List<Attribute> currentAttributeList = Collections.emptyList();

	public Composite getAddDeleteComposite() {
		return addDeleteComposite;
	}

	public void setSearchInformationText(final String text) {
		if (searchInformation != null && !searchInformation.isDisposed()) {
			searchInformation.setText(text);
		}
	}

	public BulkEditorNatTable(final Composite parent, final CommandExecutor commandExecutor,
			final BulkEditorMode initialMode, final IWorkbenchPartSite site,
			final DelegatingSelectionProvider selectionProviderDelegate) {
		this.parent = parent;
		this.commandExecutor = commandExecutor;
		createSearchButtonRow(parent);

		this.contextMenuManager.setRemoveAllWhenShown(true);
		site.registerContextMenu(CONTEXT_MENU_ID, contextMenuManager, selectionProviderDelegate);

		changeNatTable(initialMode, null);
	}

	public NatTable getCurrentTable() {
		return natTable;
	}

	public List<Attribute> getCurrentList() {
		return currentAttributeList;
	}

	private void createSearchButtonRow(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(0, 0).generateLayout(composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		addDeleteComposite = new Composite(composite, 0);
		addDeleteComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addDeleteComposite.setLayout(GridLayoutFactory.fillDefaults().spacing(0, 0).create());

		searchInformation = WidgetFactory.label(SWT.NONE).create(composite);
		searchInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	public void changeNatTable(final BulkEditorMode mode, final AttributeDeclaration attributeDeclaration) {
		changeNatTable(mode, attributeDeclaration, null, null);
	}

	public void changeNatTable(final BulkEditorMode mode, final AttributeDeclaration attributeDeclaration,
			final CommandProvider addCommand, final CommandProvider deleteCommand) {
		if (searchInformation != null) {
			searchInformation.setText(""); //$NON-NLS-1$
		}
		this.currentMode = mode;
		disposeCurrent();

		final BuiltNatTable<? extends EObject> built = switch (this.currentMode) {
		case VARIABLE -> VarDeclarationTableBuilder.create(parent, commandExecutor);
		case ADVANCED_ATTRIBUTE -> AttributeTableBuilder.create(parent, commandExecutor);
		case SIMPLE_ATTRIBUTE -> attributeDeclaration != null
				? DynamicAttributeTableBuilder.create(parent, commandExecutor, attributeDeclaration)
				: AttributeTableBuilder.create(parent, commandExecutor);
		};

		this.natTable = built.natTable();
		this.provider = built.provider();
		this.sorterModel = built.sorterModel();

		final var addDeleteWidget = new AddDeleteWidget();
		addDeleteWidget.createControls(addDeleteComposite, new FormToolkit(Display.getDefault()), true);
		addDeleteWidget.bindToTableViewer(this.natTable, this.commandExecutor, addCommand, deleteCommand);
		if (addCommand == null || deleteCommand == null) {
			addDeleteWidget.setEnabled(false);
		}
		addDeleteComposite.layout();

		configureCommonBehavior();
	}

	private void configureCommonBehavior() {
		natTable.addConfiguration(new SingleClickSortConfiguration());
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);
		natTable.registerCommandHandler(new FilterDefaultValuesCopyDataCommandHandler(selectionLayer));
		natTable.registerCommandHandler(new PasteFromClipboardDataCommandHandler(selectionLayer));
		addContextMenu();
		natTable.configure();
		parent.layout();
	}

	public ISelectionProvider getCurrentProvider() {
		return currentProvider;
	}

	private void addContextMenu() {
		currentProvider = getSelectionProvider(this.provider);
		natTable.addConfiguration(new ContextMenuConfiguration(natTable, contextMenuManager));
	}

	private <T> RowSelectionProvider<T> getSelectionProvider(final ChangeableListDataProvider<T> prov) {
		return new RowSelectionProvider<>(NatTableWidgetFactory.getSelectionLayer(natTable), prov);
	}

	private void disposeCurrent() {
		Arrays.stream(addDeleteComposite.getChildren()).forEach(Control::dispose);
		if (natTable != null) {
			natTable.dispose();
			natTable = null;
		}
	}

	public void updateList(final List<EObject> mappedList) {
		currentAttributeList = Collections.emptyList();

		if (currentMode == BulkEditorMode.VARIABLE
				&& (mappedList.isEmpty() || mappedList.getFirst() instanceof VarDeclaration)) {
			applyInput(provider, sorterModel, filterByType(mappedList, VarDeclaration.class));
		} else if (BulkEditorMode.isAttributeMode(currentMode)
				&& (mappedList.isEmpty() || mappedList.getFirst() instanceof Attribute)) {
			final List<Attribute> list = filterByType(mappedList, Attribute.class);
			currentAttributeList = list;
			applyInput(provider, sorterModel, list);

			if (currentMode == BulkEditorMode.SIMPLE_ATTRIBUTE) {
				autoResizeDynamicColumns();
			}
		}

		natTable.refresh();
	}

	@SuppressWarnings("unchecked")
	private static <T extends EObject> void applyInput(final ChangeableListDataProvider<? extends EObject> provider,
			final SorterModel<? extends EObject> sorterModel, final List<T> list) {
		((ChangeableListDataProvider<T>) provider).setInput(list);
		((SorterModel<T>) sorterModel).setSortingList(list);
	}

	private void autoResizeDynamicColumns() {
		final DataLayer dataLayer = NatTableWidgetFactory.getDataLayer(natTable);
		if (dataLayer.getRowCount() <= 0) {
			return;
		}
		dataLayer.doCommand(
				new AutoResizeColumnsCommand(natTable, IntStream.range(0, dataLayer.getColumnCount()).toArray()));
		for (int colPos = 0; colPos < dataLayer.getColumnCount(); colPos++) {
			final int currentWidth = dataLayer.getColumnWidthByPosition(colPos);
			dataLayer.setColumnWidthByPosition(colPos, Math.max(currentWidth, 100));
		}
	}

	private static <T> List<T> filterByType(final List<EObject> source, final Class<T> clazz) {
		final List<T> result = new ArrayList<>();
		for (final EObject obj : source) {
			if (clazz.isInstance(obj)) {
				result.add(clazz.cast(obj));
			}
		}
		return result;
	}

	public static TypeLibrary typeLibraryForSelection(final ChangeableListDataProvider<? extends EObject> provider,
			final NatTable natTable) {
		if (natTable == null) {
			return null;
		}
		final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable).getLastSelectedCellPosition()
				.getRowPosition();
		if (EcoreUtil
				.getRootContainer(provider.getRowObject(relevantRowIndex)) instanceof final LibraryElement libElement) {
			return libElement.getTypeLibrary();
		}
		return null;
	}
}
