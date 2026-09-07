/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - extracted attribute name editor and validator
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportContentProposal;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportTypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.fordiac.ide.util.ErrorMessenger;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.swt.SWT;

public class AttributeNameEditorConfiguration extends AbstractRegistryConfiguration {
	private final Supplier<ConfigurableObject> typeSupplier;
	private final CommandExecutor commandExecutor;
	private final Supplier<ConfigurableObject> currentSelected;

	public AttributeNameEditorConfiguration(final Supplier<ConfigurableObject> typeSupplier,
			final CommandExecutor commandExecutor) {
		this(typeSupplier, commandExecutor, typeSupplier);
	}

	public AttributeNameEditorConfiguration(final Supplier<ConfigurableObject> typeSupplier,
			final CommandExecutor commandExecutor, final Supplier<ConfigurableObject> currentSelected) {
		this.typeSupplier = Objects.requireNonNull(typeSupplier);
		this.commandExecutor = Objects.requireNonNull(commandExecutor);
		this.currentSelected = currentSelected;
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, createCellEditor(), DisplayMode.EDIT,
				NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, attributeNameValidator,
				DisplayMode.EDIT, NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
	}

	protected TextCellEditor createCellEditor() {
		final AttributeNameCellEditor cellEditor = new AttributeNameCellEditor();
		cellEditor.enableContentProposal(new TextContentAdapter(),
				new ImportTypeSelectionProposalProvider(typeSupplier, TypeLibrary::getAttributeTypeEntry,
						AttributeSelectionContentProvider.INSTANCE, this::isValidTarget),
				KeyStroke.getInstance(SWT.CTRL, SWT.SPACE), null);
		return cellEditor;
	}

	protected boolean isValidTarget(final TypeEntry entry) {
		if (currentSelected != null && entry.getType() instanceof final AttributeDeclaration decl) {
			return decl.isValidObject(currentSelected.get());
		}
		return true;
	}

	private final IDataValidator attributeNameValidator = new IDataValidator() {
		@Override
		public boolean validate(final int columnIndex, final int rowIndex, final Object newValue) {
			if (!(newValue instanceof final String name)) {
				return true;
			}

			if (InternalAttributeDeclarations.getInternalAttributeByName(name) != null) {
				ErrorMessenger
						.popUpErrorMessage(MessageFormat.format(Messages.AttributeSection_NameReservedKeyWord, name));
				return false;
			}
			return true;
		}

		@Override
		public boolean validate(final ILayerCell cell, final IConfigRegistry configRegistry, final Object newValue) {
			return validate(cell.getColumnIndex(), cell.getRowIndex(), newValue);
		}
	};

	private class AttributeNameCellEditor extends TextCellEditor {
		@Override
		protected void configureContentProposalAdapter(final ContentProposalAdapter contentProposalAdapter) {
			contentProposalAdapter.addContentProposalListener(this::proposalAccepted);
			super.configureContentProposalAdapter(contentProposalAdapter);
		}

		protected void proposalAccepted(final IContentProposal proposal) {
			final LibraryElement libraryElement = ModelHelper.getLibraryElementFromContextChecked(typeSupplier.get());
			if (proposal instanceof final ImportContentProposal importProposal
					&& !ImportHelper.matchesImports(importProposal.getImportedNamespace(), libraryElement)) {
				commandExecutor
						.executeCommand(new AddNewImportCommand(libraryElement, importProposal.getImportedNamespace()));
			}
		}
	}
}