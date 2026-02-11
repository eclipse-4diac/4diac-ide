/*******************************************************************************
 * Copyright (c) 2017, 2025 fortiss GmbH, Johannes Kepler University Linz,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *               - cleaned command stack handling for property sections
 *   Melanie Winter - buttons are created with AddDeleteWidget
 *   Martin Erich Jobst - convert to new attribute model and nat table
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeEditableRule;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.model.AttributeInheritMode;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateAttributeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteAttributeCommand;
import org.eclipse.fordiac.ide.model.data.InternalDataType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportContentProposal;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportTypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.event.DataUpdateEvent;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AttributeSection extends AbstractSection implements I4diacNatTableUtil {
	protected IChangeableRowDataProvider<Attribute> provider;
	protected NatTable table;
	protected AddDeleteReorderListWidget buttons;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createAttributesControls(parent);
	}

	public void createAttributesControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteReorderListWidget();
		buttons.createControls(composite, getWidgetFactory());

		provider = new ChangeableListDataProvider<>(new AttributeColumnAccessor(this));
		final DataLayer dataLayer = new DataLayer(provider);

		dataLayer.unregisterCommandHandler(UpdateDataCommand.class);
		dataLayer.registerCommandHandler(new AttributeUpdateDataCommandHandler(dataLayer));

		dataLayer.setConfigLabelAccumulator(new AttributeConfigLabelAccumulator(provider, this::getAnnotationModel));
		final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(
				AttributeTableColumn.DEFAULT_COLUMNS);
		table = NatTableWidgetFactory.createRowNatTable(composite, dataLayer, columnProvider,
				new AttributeEditableRule(new EditableRule() {
					@Override
					public boolean isEditable(final int columnIndex, final int rowIndex) {
						return isTypeEditable();
					}
				}, AttributeTableColumn.DEFAULT_COLUMNS, provider), new TypeSelectionButton(this::getTypeLibrary,
						DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE),
				this, false);
		table.addConfiguration(new InitialValueEditorConfiguration(provider));

		final Predicate<TypeEntry> targetFilter = entry -> {
			if (entry.getType() instanceof final AttributeDeclaration decl) {
				return decl.isValidObject(getType());
			}
			return true;
		};
		final AttributeNameCellEditor attributeNameCellEditor = new AttributeNameCellEditor();
		attributeNameCellEditor.enableContentProposal(new TextContentAdapter(),
				new ImportTypeSelectionProposalProvider(this::getType, TypeLibrary::getAttributeTypeEntry,
						AttributeSelectionContentProvider.INSTANCE, targetFilter),
				KeyStroke.getInstance(SWT.CTRL, SWT.SPACE), null);
		table.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(final IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, attributeNameCellEditor,
						DisplayMode.EDIT, NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
				configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, attributeNameValidator,
						DisplayMode.EDIT, NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
			}
		});

		table.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		table.configure();

		buttons.bindToTableViewer(table, this,
				ref -> CreateAttributeCommand.forTemplate(getType(), getLastSelectedAttribute(), getInsertionIndex()),
				ref -> new DeleteAttributeCommand(getType(), getLastSelectedAttribute()),
				ref -> new ChangeAttributeOrderCommand(getType(), (Attribute) ref,
						getNeighbourListItem((Attribute) ref, true)),
				ref -> new ChangeAttributeOrderCommand(getType(), (Attribute) ref,
						getNeighbourListItem((Attribute) ref, false)));
	}

	private boolean isTypeEditable() {
		final ConfigurableObject type = getType();
		return !(type instanceof final FBNetworkElement fbne && fbne.isContainedInTypedInstance()
				|| (type instanceof final IInterfaceElement ie && ie.getBlockFBNetworkElement() != null
						&& ie.getBlockFBNetworkElement().isContainedInTypedInstance())
				|| (type instanceof final Connection conn && FBNetworkElementHelper.isContainedInTypedInstance(conn)));
	}

	private Attribute getNeighbourListItem(final Attribute ref, final boolean above) {
		final List<Attribute> filtered = getType().getAttributes().stream()
				.filter(att -> !(att.getType() instanceof InternalDataType)).toList();
		int idx = filtered.indexOf(ref);
		if (above) {
			idx = idx > 0 ? idx - 1 : 0;
		} else {
			idx = idx < filtered.size() - 1 ? idx + 1 : filtered.size() - 1;
		}
		return filtered.get(idx);
	}

	private int getInsertionIndex() {
		final Attribute attribute = getLastSelectedAttribute();
		if (null == attribute) {
			return getType().getAttributes().size();
		}
		return getType().getAttributes().indexOf(attribute) + 1;
	}

	private Attribute getLastSelectedAttribute() {
		return (Attribute) NatTableWidgetFactory.getLastSelectedVariable(table);
	}

	private List<Attribute> getFilteredAttributeList() {
		final ConfigurableObject confObject = getType();
		List<Attribute> filteredList = confObject != null
				? confObject.getAttributes().stream()
						.filter(att -> !(att.getType() instanceof InternalDataType)
								&& !InternalAttributeDeclarations.isInternalAttribute(att.getAttributeDeclaration()))
						.toList()
				: Collections.emptyList();

		final ConfigurableObject original = getTypeElement(confObject);
		if (original != null) {
			final var copiedInheritAttributes = EcoreUtil
					.copyAll(AttributeInheritMode.getInheritAttributes(confObject, original.getAttributes()));
			if (!copiedInheritAttributes.isEmpty()) {
				filteredList = new ArrayList<>(filteredList);
				filteredList.addAll(copiedInheritAttributes);
			}
		}
		return filteredList;
	}

	private ConfigurableObject getTypeElement(final ConfigurableObject copy) {
		if (copy instanceof final TypedConfigureableObject typedConfigObject
				&& typedConfigObject.getTypeEntry() != null) {
			return typedConfigObject.getTypeEntry().getType();
		}
		if (copy instanceof final IInterfaceElement interfaceElement
				&& getTypeElement(interfaceElement.getBlockFBNetworkElement()) instanceof final FBType fbType) {
			return fbType.getInterfaceList().getInterfaceElement(interfaceElement);
		}

		return null;
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final Attribute attribute) {
			cmd.add(CreateAttributeCommand.forTemplate(getType(), attribute, index));
		}
	}

	@Override
	protected void performRefresh() {
		provider.setInput(getFilteredAttributeList());
		table.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		table.refresh(false);
	}

	@Override
	public void executeCommand(final Command cmd) {
		super.executeCommand(cmd);
		provider.setInput(getFilteredAttributeList());
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		table.refresh();
	}

	@Override
	public boolean isEditable() {
		return isTypeEditable();
	}

	@Override
	protected ConfigurableObject getInputType(final Object input) {
		return AttributeFilter.parseObject(input) instanceof final ConfigurableObject configurableObject
				? configurableObject
				: null;
	}

	@Override
	protected void setInputInit() {
		buttons.setEnabled(isTypeEditable());
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final Attribute attribute) {
			cmd.add(new DeleteAttributeCommand(getType(), attribute));
		}
	}

	public static boolean isInternalAttribute(final String name) {
		return InternalAttributeDeclarations.getInternalAttributeByName(name) != null;
	}

	private final IDataValidator attributeNameValidator = new IDataValidator() {
		@Override
		public boolean validate(final int columnIndex, final int rowIndex, final Object newValue) {
			if (!(newValue instanceof final String name)) {
				return true;
			}

			if (isInternalAttribute(name)) {
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

	@Override
	protected ConfigurableObject getType() {
		return type instanceof final ConfigurableObject configurableObject ? configurableObject : null;
	}

	protected class AttributeUpdateDataCommandHandler extends UpdateDataCommandHandler {
		private final DataLayer dataLayer;

		public AttributeUpdateDataCommandHandler(final DataLayer dataLayer) {
			super(dataLayer);
			this.dataLayer = dataLayer;
		}

		@Override
		protected boolean doCommand(final UpdateDataCommand command) {
			try {
				final int columnPosition = command.getColumnPosition();
				final int rowPosition = command.getRowPosition();

				final Object currentValue = dataLayer.getDataValueByPosition(columnPosition, rowPosition);
				final Object newValue = command.getNewValue();

				if ((currentValue == null && newValue != null) || (newValue == null && currentValue != null)
						|| (currentValue != null && !currentValue.equals(newValue))) {

					final Attribute attribute = provider.getRowObject(rowPosition);
					if (attribute.eContainer() == null) {
						getType().getAttributes().add(attribute);
					}

					dataLayer.setDataValueByPosition(columnPosition, rowPosition, newValue);
					dataLayer.fireLayerEvent(
							new DataUpdateEvent(dataLayer, columnPosition, rowPosition, currentValue, newValue));
				}
				return true;
			} catch (final Exception e) {
				FordiacLogHelper.logError(MessageFormat.format(Messages.NatTable_Update_Failed, command.getNewValue()),
						e);
				return false;
			}
		}
	}

	protected class AttributeNameCellEditor extends TextCellEditor {

		@Override
		protected void configureContentProposalAdapter(final ContentProposalAdapter contentProposalAdapter) {
			contentProposalAdapter.addContentProposalListener(this::proposalAccepted);
			super.configureContentProposalAdapter(contentProposalAdapter);
		}

		protected void proposalAccepted(final IContentProposal proposal) {
			final LibraryElement libraryElement = ModelHelper.getLibraryElementFromContextChecked(getType());
			if (proposal instanceof final ImportContentProposal importProposal
					&& !ImportHelper.matchesImports(importProposal.getImportedNamespace(), libraryElement)) {
				executeCommand(new AddNewImportCommand(libraryElement, importProposal.getImportedNamespace()));
			}
		}
	}
}
