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
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeEditableRule;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BulkEditorNatTable {

	private final CommandExecutor commandExecutor;
	private final Composite parent;
	private int currentMode = -1;

	private NatTable natTable;

	private SorterModel<Attribute> attributeSorterModel;
	private SorterModel<VarDeclaration> varDeclarationSorterModel;
	private ChangeableListDataProvider<Attribute> attributeProvider;
	private ChangeableListDataProvider<VarDeclaration> varDeclProvider;

	/**
	 * @param parent          Parent Composite the Table should be created in.
	 *                        Parent should be a Composite contained inside a
	 *                        ScrolledComposite
	 * @param commandExecutor for executing commands
	 * @param initialMode     Type of initial NatTable 0 = VarDeclaration, 1 =
	 *                        Attribute
	 */
	public BulkEditorNatTable(final Composite parent, final CommandExecutor commandExecutor, final int initialMode) {
		this.parent = parent;
		this.commandExecutor = commandExecutor;
		changeNatTable(initialMode);
	}

	public void updateList(final List<EObject> mappedList) {
		if (currentMode == 0 && (mappedList.isEmpty() || mappedList.getFirst() instanceof VarDeclaration)) {
			final var list = mapList(mappedList, VarDeclaration.class);
			varDeclProvider.setInput(list);
			varDeclarationSorterModel.setSortingList(list);
		} else if (currentMode == 1 && (mappedList.isEmpty() || mappedList.getFirst() instanceof Attribute)) {
			final var list = mapList(mappedList, Attribute.class);
			attributeProvider.setInput(list);
			attributeSorterModel.setSortingList(list);
		}

		natTable.getDisplay().asyncExec(() -> {
			// make sure NatTable is drawn to get Correct Cell-height
			final GridData natTableGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
			int height = (int) (24 * (double) Display.getCurrent().getDPI().x / 96);
			if (mappedList.size() > 0) {
				height = Math.max(height,
						NatTableWidgetFactory.getDataLayer(natTable).getBoundsByPosition(0, 0).height);
			}
			natTableGridData.heightHint = mappedList.size() * height + 1;
			natTable.setLayoutData(natTableGridData);

			final ViewportLayer viewportLayer = NatTableWidgetFactory.getViewportLayer(natTable);
			viewportLayer.setClientAreaProvider(() -> new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE));

			parent.layout(true, true);
			final var size = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			final var scrolledComposite = (ScrolledComposite) parent.getParent();
			scrolledComposite.setMinSize(size);
			natTable.refresh();
		});
	}

	public void changeNatTable(final int selectionIndex) {
		if (this.currentMode == selectionIndex) {
			return;
		}
		this.currentMode = selectionIndex;
		if (natTable != null) {
			natTable.dispose();
		}
		if (this.currentMode == 0) {
			final var accessor = new VarDeclarationColumnAccessor(commandExecutor,
					VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			varDeclProvider = new ChangeableListDataProvider<>(accessor);
			this.varDeclarationSorterModel = new SorterModel<>(accessor);
			final DataLayer inputDataLayer = new VarDeclarationDataLayer(varDeclProvider,
					VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			final VarDeclarationConfigLabelAccumulator configLabelProvider = new VarDeclarationConfigLabelAccumulator(
					varDeclProvider, () -> null, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION) {
				@Override
				public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition,
						final int rowPosition) {
					super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
					switch (getColumns().get(columnPosition)) {
					case NAME:
						configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
						break;
					case TYPE:
						configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
						break;
					default:
						break;
					}
				}
			};
			inputDataLayer.setConfigLabelAccumulator(configLabelProvider);
			final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
					VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			natTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
					new NatTableColumnEditableRule<>(new LinkedElementsEditableRule(varDeclProvider),
							VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_LOCATION,
							VarDeclarationTableColumn.EDITABLE_COMMENT_VALUE),
					null, null, varDeclarationSorterModel, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new TypeDeclarationEditorConfiguration(varDeclProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, commandExecutor));
		} else {
			final var accessor = new AttributeColumnAccessor(commandExecutor,
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			attributeProvider = new ChangeableListDataProvider<>(accessor);
			this.attributeSorterModel = new SorterModel<>(accessor);
			final DataLayer dataLayer = new DataLayer(attributeProvider);

			final AttributeConfigLabelAccumulator configLabelProvider = new AttributeConfigLabelAccumulator(
					attributeProvider, () -> null, AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION) {
				@Override
				public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition,
						final int rowPosition) {
					super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
					switch (getColumns().get(columnPosition)) {
					case NAME:
						configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
						break;
					case TYPE:
						configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
						break;
					default:
						break;
					}
				}
			};
			dataLayer.setConfigLabelAccumulator(configLabelProvider);
			final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(
					AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
			natTable = NatTableWidgetFactory.createRowNatTable(parent, dataLayer, columnProvider,
					new AttributeEditableRule(new LinkedElementsEditableRule(attributeProvider),
							AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION,
							AttributeTableColumn.EDITABLE_COMMENT_VALUE, attributeProvider),
					new TypeSelectionButton(() -> {
						final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
								.getLastSelectedCellPosition().getRowPosition();
						if (EcoreUtil.getRootContainer(attributeProvider
								.getRowObject(relevantRowIndex)) instanceof final LibraryElement libElement) {
							return libElement.getTypeLibrary();
						}
						return null;
					}, DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE), null,
					attributeSorterModel, false);
			natTable.addConfiguration(new InitialValueEditorConfiguration(attributeProvider));
			natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, commandExecutor));

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

		natTable.addConfiguration(new SingleClickSortConfiguration());
		natTable.configure();
		// Scroll ScrolledComposite instead of NatTable
		natTable.addListener(SWT.MouseWheel, event -> {
			final ScrolledComposite scrolledParent = ((ScrolledComposite) parent.getParent());
			final Point origin = scrolledParent.getOrigin();

			final int newY = Math.max(0, origin.y - event.count * 20);
			scrolledParent.setOrigin(origin.x, newY);
		});
	}

	public NatTable getCurrentTable() {
		return natTable;
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

	private class LinkedElementsEditableRule extends EditableRule {
		private final ChangeableListDataProvider<? extends EObject> provider;

		public LinkedElementsEditableRule(final ChangeableListDataProvider<? extends EObject> provider) {
			this.provider = provider;
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final var rootElement = EcoreUtil.getRootContainer(provider.getRowObject(rowIndex));
			if (rootElement instanceof final LibraryElement libElement) {
				return SearchHelper.linkedElementsFilter.test(libElement.getTypeEntry());
			}
			return true;
		}
	}

	private class AttributeNameCellEditor extends TextCellEditor {
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
				commandExecutor
						.executeCommand(new AddNewImportCommand(libraryElement, importProposal.getImportedNamespace()));
			}
		}
	}
}
