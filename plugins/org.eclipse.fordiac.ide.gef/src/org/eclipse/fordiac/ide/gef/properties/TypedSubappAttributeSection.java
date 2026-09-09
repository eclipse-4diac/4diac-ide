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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeNameEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.OverrideAttributeEditableRule;
import org.eclipse.fordiac.ide.model.AttributeInheritMode;
import org.eclipse.fordiac.ide.model.commands.DependentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateOverrideAttributeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteOverrideAttributeCommand;
import org.eclipse.fordiac.ide.model.data.InternalDataType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TypedSubappAttributeSection extends AbstractSection implements I4diacNatTableUtil {
	protected IChangeableRowDataProvider<Attribute> provider;
	protected NatTable table;
	protected AddDeleteWidget buttons;
	private TypedSubApp subapp;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createAttributesControls(parent);
	}

	public void createAttributesControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteWidget();
		buttons.createControls(composite, getWidgetFactory());

		final var columns = AttributeTableColumn.DEFAULT_COLUMNS;
		provider = new ChangeableListDataProvider<>(new OverrideAttributeColumnAccessor(this, columns));
		final DataLayer dataLayer = new DataLayer(provider);

		dataLayer.setConfigLabelAccumulator(
				new AttributeConfigLabelAccumulator(provider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		table = NatTableWidgetFactory.createRowNatTable(composite, dataLayer, columnProvider,
				new OverrideAttributeEditableRule(IEditableRule.ALWAYS_EDITABLE, columns, provider, () -> subapp),
				new TypeSelectionButton(this::getTypeLibrary, DataTypeSelectionContentProvider.INSTANCE,
						DataTypeSelectionTreeContentProvider.INSTANCE),
				this, false);
		table.addConfiguration(new InitialValueEditorConfiguration(provider));
		table.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		table.addConfiguration(new AttributeNameEditorConfiguration(this::getType, this));
		table.configure();

		buttons.bindToTableViewer(table, this,
				_ -> new CreateOverrideAttributeCommand(getType(), subapp, getLastSelectedAttribute()),
				_ -> new DeleteOverrideAttributeCommand(subapp,
						getLastSelectedAttribute() instanceof final OverrideAttribute att ? att : null));
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

		// inheritAttributes
		final ConfigurableObject original = getTypeElement(confObject);
		if (original != null) {
			final var copiedInheritAttributes = EcoreUtil
					.copyAll(AttributeInheritMode.getInheritAttributes(confObject, original.getAttributes()));
			if (!copiedInheritAttributes.isEmpty()) {
				filteredList = new ArrayList<>(filteredList);
				filteredList.addAll(copiedInheritAttributes);
			}
		}

		// overrideAttributes
		EObject obj = getType();
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof final TypedSubApp subApp) {
				final String relativeName = ((INamedElement) getType()).getRelativeName(subApp);
				final var overrideAttributes = subApp.getOverrideAttributes().stream()
						.filter(attribute -> attribute.getLocation().equals(relativeName)).toList();
				filteredList = merge(filteredList, overrideAttributes);

				if (!subApp.isContainedInTypedInstance()) {
					this.subapp = subApp;
					break;
				}
			}
		}

		return filteredList;
	}

	private static List<Attribute> merge(final List<? extends Attribute> base,
			final List<? extends OverrideAttribute> overrides) {
		// replace attributes if they exists, otherwise add new ones
		final List<Attribute> os = List.copyOf(overrides);
		return Stream
				.concat(base.stream()
						.map(a -> os.stream().filter(o -> o.getName().equals(a.getName())).findFirst().orElse(a)),
						os.stream().filter(o -> base.stream().noneMatch(a -> o.getName().equals(a.getName()))))
				.toList();
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
			cmd.add(new CreateOverrideAttributeCommand(getType(), subapp, attribute));
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
		return true;
	}

	@Override
	protected ConfigurableObject getInputType(final Object input) {
		return AttributeFilter.parseObject(input) instanceof final ConfigurableObject configurableObject
				? configurableObject
				: null;
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final OverrideAttribute attribute) {
			cmd.add(new DeleteOverrideAttributeCommand(subapp, attribute));
		}
	}

	public static boolean isInternalAttribute(final String name) {
		return InternalAttributeDeclarations.getInternalAttributeByName(name) != null;
	}

	@Override
	protected ConfigurableObject getType() {
		return type instanceof final ConfigurableObject configurableObject ? configurableObject : null;
	}

	private class OverrideAttributeColumnAccessor extends AttributeColumnAccessor {
		public OverrideAttributeColumnAccessor(final CommandExecutor commandExecutor,
				final List<AttributeTableColumn> columns) {
			super(commandExecutor, columns);
		}

		@Override
		public Object getDataValue(final Attribute rowObject, final AttributeTableColumn column) {
			if (column == AttributeTableColumn.LOCATION) {
				return rowObject instanceof final OverrideAttribute overrideAttribute ? overrideAttribute.getLocation()
						: ""; //$NON-NLS-1$
			}

			return super.getDataValue(rowObject, column);
		}

		@Override
		public Command createCommand(final Attribute rowObject, final AttributeTableColumn column,
				final Object newValue) {
			if (column != AttributeTableColumn.VALUE && column != AttributeTableColumn.COMMENT) {
				return super.createCommand(rowObject, column, newValue);
			}

			final String newText = Objects.toString(newValue, ""); //$NON-NLS-1$

			if (rowObject instanceof final OverrideAttribute overrideAtt && overrideAtt.eContainer() == subapp) {
				return matchesType(overrideAtt, column, newText)
						? new DeleteOverrideAttributeCommand(subapp, overrideAtt)
						: super.createCommand(rowObject, column, newValue);
			}

			final var createCmd = new CreateOverrideAttributeCommand(getType(), subapp, rowObject);
			return new DependentCommand(createCmd, () -> {
				final var attribute = createCmd.getCreatedElement();
				return column == AttributeTableColumn.VALUE ? new ChangeAttributeValueCommand(attribute, newText)
						: new ChangeCommentCommand(attribute, newText);
			});
		}

		private boolean matchesType(final OverrideAttribute overrideAtt, final AttributeTableColumn column,
				final String newText) {
			final Attribute original = getOriginal(overrideAtt.getName());

			if (original == null) {
				return false;
			}
			final String value = column == AttributeTableColumn.VALUE ? newText : overrideAtt.getValue();
			final String comment = column == AttributeTableColumn.COMMENT ? newText : overrideAtt.getComment();
			return Objects.equals(original.getValue(), value) && Objects.equals(original.getComment(), comment);
		}

		private Attribute getOriginal(final String attributeName) {
			Attribute attribute = getType().getAttribute(attributeName);
			EObject obj = getType();
			while (obj.eContainer() != null) {
				obj = obj.eContainer();
				if (obj instanceof final TypedSubApp subApp) {
					if (!subApp.isContainedInTypedInstance()) {
						break;
					}
					final String relativeName = ((INamedElement) getType()).getRelativeName(subApp);
					final var overrideAttribute = subApp.getOverrideAttributes().stream()
							.filter(att -> att.getLocation().equals(relativeName))
							.filter(att -> att.getName().equals(attributeName)).findFirst();

					if (overrideAttribute.isPresent()) {
						attribute = overrideAttribute.get();
					}
				}
			}
			return attribute;
		}
	}
}
