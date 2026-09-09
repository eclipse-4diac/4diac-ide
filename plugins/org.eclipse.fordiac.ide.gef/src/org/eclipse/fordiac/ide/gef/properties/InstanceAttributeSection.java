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
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.gef.nat.AttributeColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeNameEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.AttributeTableColumn;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.OverrideAttributeEditableRule;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteOverrideAttributeCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.DeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InstanceAttributeSection extends AbstractSection implements I4diacNatTableUtil {
	private IChangeableRowDataProvider<Attribute> provider;
	private NatTable table;
	private DeleteWidget deleteButton;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createAttributesControls(parent);
	}

	public void createAttributesControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		deleteButton = new DeleteWidget();
		deleteButton.createControls(composite, getWidgetFactory());

		final var columns = AttributeTableColumn.defaultColumnsWithPrepended(AttributeTableColumn.LOCATION);
		provider = new ChangeableListDataProvider<>(new OverrideAttributeColumnAccessor(this, columns));
		final DataLayer dataLayer = new DataLayer(provider);

		dataLayer.setConfigLabelAccumulator(
				new AttributeConfigLabelAccumulator(provider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<AttributeTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		table = NatTableWidgetFactory.createRowNatTable(composite, dataLayer, columnProvider,
				new OverrideAttributeEditableRule(new IEditableRule() {
					@Override
					public boolean isEditable(final int columnIndex, final int rowIndex) {
						return InstanceAttributeSection.this.isEditable();
					}

					@Override
					public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
						return InstanceAttributeSection.this.isEditable();
					}
				}, columns, provider, this::getType), new TypeSelectionButton(this::getTypeLibrary,
						DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE),
				this, false);
		table.addConfiguration(new InitialValueEditorConfiguration(provider));
		table.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		table.addConfiguration(new AttributeNameEditorConfiguration(this::getType, this, this::getCurrentType));
		table.configure();

		deleteButton.bindToTableViewer(table, this,
				_ -> new DeleteOverrideAttributeCommand(getType(), getLastSelectedAttribute()));
	}

	private OverrideAttribute getLastSelectedAttribute() {
		return (OverrideAttribute) NatTableWidgetFactory.getLastSelectedVariable(table);
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		// no adding possible
	}

	@Override
	protected void performRefresh() {
		provider.setInput(new ArrayList<>(getType().getOverrideAttributes()));
		table.refresh();
	}

	@Override
	protected void setInputInit() {
		deleteButton.setEnabled(isEditable());
	}

	@Override
	public void executeCommand(final Command cmd) {
		super.executeCommand(cmd);
		provider.setInput(new ArrayList<>(getType().getOverrideAttributes()));
	}

	@Override
	protected void performRefreshAnnotations() {
		table.refresh(false);
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		table.refresh();
	}

	@Override
	public boolean isEditable() {
		return getType() != null && !getType().isContainedInTypedInstance();
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
			cmd.add(new DeleteOverrideAttributeCommand(getType(), attribute));
		}
	}

	public static boolean isInternalAttribute(final String name) {
		return InternalAttributeDeclarations.getInternalAttributeByName(name) != null;
	}

	@Override
	protected TypedSubApp getType() {
		return type instanceof final TypedSubApp tsa ? tsa : null;
	}

	public ConfigurableObject getCurrentType() {
		final var selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
		final int[] rowPositions = selectionLayer.getSelectedRowPositions().stream()
				.flatMapToInt(range -> IntStream.range(range.start, range.end)).sorted().toArray();

		if (rowPositions.length != 1) {
			return null;
		}

		final var currentAttribute = provider.getRowObject(rowPositions[0]);
		final var currentElement = getType().findByQualifiedName(((OverrideAttribute) currentAttribute).getLocation())
				.filter(ConfigurableObject.class::isInstance).map(ConfigurableObject.class::cast).findFirst();
		return currentElement.orElse(null);
	}

	private class OverrideAttributeColumnAccessor extends AttributeColumnAccessor {
		public OverrideAttributeColumnAccessor(final CommandExecutor commandExecutor,
				final List<AttributeTableColumn> columns) {
			super(commandExecutor, columns);
		}

		@Override
		public Object getDataValue(final Attribute rowObject, final AttributeTableColumn column) {
			if (column == AttributeTableColumn.LOCATION
					&& rowObject instanceof final OverrideAttribute overrideAttribute) {
				return overrideAttribute.getLocation();
			}
			return super.getDataValue(rowObject, column);
		}
	}
}
