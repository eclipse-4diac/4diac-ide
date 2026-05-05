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

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeEditableRule;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportContentProposal;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportTypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class AttributeTableBuilder {

	private AttributeTableBuilder() {
		// utility class
	}

	public static BuiltNatTable<Attribute> create(final Composite parent, final CommandExecutor commandExecutor) {
		final var accessor = new AttributeColumnAccessor(commandExecutor,
				AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);
		final ChangeableListDataProvider<Attribute> provider = new ChangeableListDataProvider<>(accessor);
		final SorterModel<Attribute> sorterModel = new SorterModel<>(accessor);
		final DataLayer dataLayer = new DataLayer(provider);

		final AttributeConfigLabelAccumulator configLabelProvider = new AttributeConfigLabelAccumulator(provider,
				() -> null, AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION) {
			@Override
			public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition,
					final int rowPosition) {
				super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
				switch (getColumns().get(columnPosition)) {
				case NAME, TYPE -> configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
				default -> {
					// no extra labels
				}
				}
			}
		};
		dataLayer.setConfigLabelAccumulator(configLabelProvider);

		final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(
				AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION);

		// The TypeSelectionButton and proposal providers need to refer to the
		// NatTable being built (for its current selection), but we only get the
		// NatTable instance back from createRowNatTable *after* passing the button
		// in. Break the cycle with a holder that the lambdas read from lazily.
		final AtomicReference<NatTable> natTableRef = new AtomicReference<>();

		final NatTable natTable = NatTableWidgetFactory.createRowNatTable(parent, dataLayer, columnProvider,
				new AttributeEditableRule(new LinkedElementsEditableRule(provider),
						AttributeTableColumn.DEFAULT_COLUMNS_WITH_LOCATION, AttributeTableColumn.EDITABLE_COMMENT_VALUE,
						provider),
				new TypeSelectionButton(() -> BulkEditorNatTable.typeLibraryForSelection(provider, natTableRef.get()),
						DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE),
				null, sorterModel, false);
		natTableRef.set(natTable);

		natTable.addConfiguration(new InitialValueEditorConfiguration(provider));
		natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, commandExecutor));

		final Predicate<TypeEntry> targetFilter = entry -> {
			if (entry.getType() instanceof final AttributeDeclaration decl) {
				final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
						.getLastSelectedCellPosition().getRowPosition();
				if (provider.getRowObject(relevantRowIndex)
						.eContainer() instanceof final ConfigurableObject configurableObject) {
					return decl.isValidObject(configurableObject);
				}
			}
			return true;
		};

		final AttributeNameCellEditor attributeNameCellEditor = new AttributeNameCellEditor(provider, commandExecutor);
		attributeNameCellEditor.enableContentProposal(new TextContentAdapter(),
				new ImportTypeSelectionProposalProvider(() -> {
					final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable)
							.getLastSelectedCellPosition().getRowPosition();
					return provider.getRowObject(relevantRowIndex).eContainer();
				}, TypeLibrary::getAttributeTypeEntry, AttributeSelectionContentProvider.INSTANCE, targetFilter),
				KeyStroke.getInstance(SWT.CTRL, SWT.SPACE), null);
		natTable.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(final IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, attributeNameCellEditor,
						DisplayMode.EDIT, NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
			}
		});

		return new BuiltNatTable<>(natTable, provider, sorterModel);
	}

	private static class AttributeNameCellEditor extends TextCellEditor {

		private final ChangeableListDataProvider<Attribute> provider;
		private final CommandExecutor commandExecutor;

		public AttributeNameCellEditor(final ChangeableListDataProvider<Attribute> provider,
				final CommandExecutor commandExecutor) {
			this.provider = provider;
			this.commandExecutor = commandExecutor;
		}

		@Override
		protected void configureContentProposalAdapter(final ContentProposalAdapter contentProposalAdapter) {
			contentProposalAdapter.addContentProposalListener(this::proposalAccepted);
			super.configureContentProposalAdapter(contentProposalAdapter);
		}

		private void proposalAccepted(final IContentProposal proposal) {
			if (proposal instanceof final ImportContentProposal importProposal
					&& EcoreUtil.getRootContainer(provider.getRowObject(this.getRowIndex())
							.eContainer()) instanceof final LibraryElement libraryElement
					&& !ImportHelper.matchesImports(importProposal.getImportedNamespace(), libraryElement)) {
				commandExecutor
						.executeCommand(new AddNewImportCommand(libraryElement, importProposal.getImportedNamespace()));
			}
		}
	}
}
