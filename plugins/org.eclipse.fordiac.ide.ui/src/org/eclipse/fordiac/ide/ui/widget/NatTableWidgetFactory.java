/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.RowHeaderDataProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.copy.action.PasteDataAction;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.action.KeyEditAction;
import org.eclipse.nebula.widgets.nattable.edit.action.MouseEditAction;
import org.eclipse.nebula.widgets.nattable.edit.command.DeleteSelectionCommandHandler;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultColumnHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.config.DefaultGridLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.layer.config.DefaultRowStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultColumnHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultRowHeaderLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultRowHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.painter.NatTableBorderOverlayPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.painter.layer.NatGridLayerPainter;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionBindings;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.SelectionStyleLabels;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.tree.ITreeData;
import org.eclipse.nebula.widgets.nattable.tree.ITreeRowModel;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.tree.TreeRowModel;
import org.eclipse.nebula.widgets.nattable.tree.config.TreeLayerExpandCollapseKeyBindings;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.CellEditorMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.CellPainterMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.LetterOrDigitKeyEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public final class NatTableWidgetFactory {
	public static final String DEFAULT_CELL = "DEFAULT_CELL"; //$NON-NLS-1$
	public static final String ERROR_CELL = "ERROR_CELL"; //$NON-NLS-1$
	public static final String WARNING_CELL = "WARNING_CELL"; //$NON-NLS-1$
	public static final String DISABLED_CELL = "DISABLED_CELL"; //$NON-NLS-1$ $
	public static final String PROPOSAL_CELL = "PROPOSAL_CELL"; //$NON-NLS-1$
	public static final String ATTRIBUTE_PROPOSAL_CELL = "ATTRIBUTE_PROPOSAL_CELL"; //$NON-NLS-1$
	public static final String DISABLED_HEADER = "DISABLED_HEADER"; //$NON-NLS-1$
	public static final String VISIBILITY_CELL = "VISIBILITY_CELL"; //$NON-NLS-1$
	public static final String LEFT_ALIGNMENT = "LEFT_ALIGNMENT"; //$NON-NLS-1$

	public static final String CHECKBOX_CELL = "CHECKBOX_CELL"; //$NON-NLS-1$
	public static final String VAR_GONFIGURATION_CELL = "VAR_GONFIGURATION_CELL"; //$NON-NLS-1$
	public static final String RETAIN_CONFIG_CELL = "RETAIN_CONFIG_CELL"; //$NON-NLS-1$

	static final char[] ACTIVATION_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '_', '.', SWT.BS };

	public static char[] getActivationChars() {
		return ACTIVATION_CHARS;
	}

	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider) {
		return createNatTable(parent, dataLayer, headerDataProvider, IEditableRule.NEVER_EDITABLE);
	}

	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider, final IEditableRule editableRule) {
		return createNatTable(parent, dataLayer, headerDataProvider, editableRule, null);
	}

	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider, final IEditableRule editableRule,
			final ICellEditor proposalCellEditor) {

		setColumnWidths(dataLayer);

		final SelectionLayer selectionLayer = new SelectionLayer(dataLayer);
		selectionLayer.setSelectionModel(new FordiacSelectionModel(selectionLayer));
		selectionLayer.addConfiguration(new DefaultSelectionBindings() {
			@Override
			public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'v'),
						new PasteDataIntoTableAction());
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'x'), new CutDataFromTableAction());
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'c'), new CopyDataFromTableAction());
			}
		});
		selectionLayer.registerCommandHandler(new CopyDataCommandHandler(selectionLayer));
		selectionLayer.registerCommandHandler(new PasteDataFromClipboardCommandHandler(selectionLayer));
		selectionLayer.registerCommandHandler(new DeleteSelectionCommandHandler(selectionLayer));
		final ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		final DataLayer columnHeaderDataLayer = new DataLayer(headerDataProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer,
				selectionLayer);

		final CompositeLayer compositeLayer = new CompositeLayer(1, 2);
		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, columnHeaderLayer, 0, 0);
		compositeLayer.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		compositeLayer.addConfiguration(new DefaultEditConfiguration());
		compositeLayer.addConfiguration(new DefaultUiBindingConfiguration());
		compositeLayer.addConfiguration(new DefaultRegistryConfiguration(editableRule, proposalCellEditor));

		addEditDisabledLabel(dataLayer, editableRule, false);

		final NatTable table = new NatTable(parent, compositeLayer, false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		setNatTableStyle(table);

		return table;
	}

	public static NatTable createRowNatTable(final Composite parent, final DataLayer bodyDataLayer,
			final IDataProvider columnHeaderProvider, final IEditableRule editableRule,
			final ICellEditor proposalCellEditor, final I4diacNatTableUtil section, final boolean isInput) {

		setColumnWidths(bodyDataLayer);

		final SelectionLayer selectionLayer = new SelectionLayer(bodyDataLayer);
		selectionLayer.setSelectionModel(new FordiacSelectionModel(selectionLayer));
		selectionLayer.addConfiguration(new DefaultSelectionBindings() {
			@Override
			public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
				uiBindingRegistry.unregisterKeyBinding(new KeyEventMatcher(SWT.MOD1, 'c'));
				uiBindingRegistry.unregisterKeyBinding(new KeyEventMatcher(SWT.MOD1, 'x'));
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'c'), new CopyDataFromTableAction());
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'v'),
						new PasteDataIntoTableAction(section));
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'x'),
						new CutDataFromTableAction(section));
			}
		});
		selectionLayer.registerCommandHandler(new CopyDataCommandHandler(selectionLayer));
		selectionLayer.registerCommandHandler(new PasteDataFromClipboardCommandHandler(selectionLayer));
		selectionLayer.registerCommandHandler(new DeleteSelectionCommandHandler(selectionLayer));
		final ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		final DataLayer columnHeaderDataLayer = new DataLayer(columnHeaderProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer,
				selectionLayer);

		final IDataProvider rowHeaderProvider = new RowHeaderDataProvider(bodyDataLayer.getDataProvider(), isInput);
		final DataLayer rowHeaderDataLayer = new DataLayer(rowHeaderProvider, 25, DataLayer.DEFAULT_ROW_HEIGHT);
		final RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, viewportLayer, selectionLayer,
				false);
		rowHeaderLayer.addConfiguration(new DefaultRowHeaderLayerConfiguration() {
			@Override
			protected void addRowHeaderUIBindings() {
				// empty to remove resize cursor
			}
		});

		final DataLayer cornerDataLayer = new DataLayer(
				new DefaultCornerDataProvider(columnHeaderProvider, rowHeaderProvider));
		final CornerLayer cornerLayer = new CornerLayer(cornerDataLayer, rowHeaderLayer, columnHeaderLayer);

		final GridLayer gridLayer = new GridLayer(viewportLayer, columnHeaderLayer, rowHeaderLayer, cornerLayer, false);
		gridLayer.addConfiguration(new DefaultGridLayerConfiguration(gridLayer) {
			@Override
			protected void addEditingUIConfig() {
				addConfiguration(new DefaultUiBindingConfiguration());
			}

			@Override
			protected void addAlternateRowColoringConfig(final CompositeLayer gridLayer) {
				addConfiguration(new DefaultRowStyleConfiguration());
			}
		});
		gridLayer.addConfiguration(new DefaultRegistryConfiguration(editableRule, proposalCellEditor));

		addEditDisabledLabel(bodyDataLayer, editableRule, false);

		final NatTable table = new NatTable(parent, gridLayer, false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		setNatTableStyle(table);

		table.setLayerPainter(new NatGridLayerPainter(table, DataLayer.DEFAULT_ROW_HEIGHT) {
			@Override
			protected void paintBackground(final ILayer natLayer, final GC gc, final int xOffset, final int yOffset,
					final Rectangle rectangle, final IConfigRegistry configRegistry) {
				super.paintBackground(natLayer, gc, xOffset, yOffset, rectangle, configRegistry);
				gc.drawLine(0, rectangle.y, 0, rectangle.y + rectangle.height);
			}

		});

		return table;

	}

	public static <T> NatTable createTreeNatTable(final Composite parent, final DataLayer bodyDataLayer,
			final ITreeData<T> treeData, final IDataProvider columnHeaderDataProvider,
			final IEditableRule editableRule) {

		setColumnWidths(bodyDataLayer);

		final ITreeRowModel<T> treeRowModel = new TreeRowModel<>(treeData);

		final TreeLayer treeLayer = new TreeLayer(bodyDataLayer, treeRowModel);

		final SelectionLayer selectionLayer = new SelectionLayer(treeLayer, false);
		selectionLayer.addConfiguration(new DefaultTreeSelectionLayerConfiguration(selectionLayer, selectionLayer));

		final ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		final DataLayer columnHeaderDataLayer = new DefaultColumnHeaderDataLayer(columnHeaderDataProvider);
		final ILayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer, selectionLayer);

		final CompositeLayer compositeLayer = new CompositeLayer(1, 2);
		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, columnHeaderLayer, 0, 0);
		compositeLayer.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		compositeLayer.addConfiguration(new DefaultEditConfiguration());
		compositeLayer.addConfiguration(new DefaultUiBindingConfiguration());
		compositeLayer.addConfiguration(new DefaultRegistryConfiguration(editableRule, null));

		addEditDisabledLabel(bodyDataLayer, editableRule, false);

		final NatTable natTable = new NatTable(parent, compositeLayer, false);
		natTable.addConfiguration(new TreeLayerExpandCollapseKeyBindings(treeLayer, selectionLayer));

		setNatTableStyle(natTable);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
		return natTable;
	}

	public static DataLayer getDataLayer(final NatTable table) {
		final SelectionLayer selectionLayer = getSelectionLayer(table);
		if (selectionLayer != null) {
			return (DataLayer) selectionLayer.getUnderlyingLayerByPosition(0, 0);
		}
		return null;
	}

	public static SelectionLayer getSelectionLayer(final NatTable table) {
		ILayer viewportLayer = null;
		if (table.getLayer() instanceof final GridLayer gridLayer) {
			viewportLayer = gridLayer.getBodyLayer();
		} else if (table.getLayer() instanceof final CompositeLayer compLayer) {
			viewportLayer = compLayer.getUnderlyingLayersByColumnPosition(0).stream()
					.filter(ViewportLayer.class::isInstance).findFirst().orElse(null);
		}

		if (viewportLayer != null) {
			return (SelectionLayer) viewportLayer.getUnderlyingLayerByPosition(0, 0);
		}
		return null;
	}

	public static Object getLastSelectedVariable(final NatTable table) {
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
		if (selectionLayer != null) {
			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			if (rows.length > 0) {
				final DataLayer dataLayer = (DataLayer) selectionLayer.getUnderlyingLayerByPosition(0, 0);
				return ((ListDataProvider<?>) dataLayer.getDataProvider()).getRowObject(rows[rows.length - 1]);
			}
		}
		return null;
	}

	private static void setColumnWidths(final DataLayer dataLayer) {
		dataLayer.setColumnPercentageSizing(true);
		switch (dataLayer.getColumnCount()) {
		case 3:
			dataLayer.setColumnWidthPercentageByPosition(0, 33);
			dataLayer.setColumnWidthPercentageByPosition(1, 34);
			dataLayer.setColumnWidthPercentageByPosition(2, 33);
			break;
		case 4:
			dataLayer.setColumnWidthPercentageByPosition(0, 20);
			dataLayer.setColumnWidthPercentageByPosition(1, 20);
			dataLayer.setColumnWidthPercentageByPosition(2, 20);
			dataLayer.setColumnWidthPercentageByPosition(3, 40);
			break;
		case 5:
			dataLayer.setColumnWidthPercentageByPosition(0, 20);
			dataLayer.setColumnWidthPercentageByPosition(1, 20);
			dataLayer.setColumnWidthPercentageByPosition(2, 15);
			dataLayer.setColumnWidthPercentageByPosition(3, 35);
			dataLayer.setColumnWidthPercentageByPosition(4, 10);
			break;
		case 6:
			dataLayer.setColumnWidthPercentageByPosition(0, 20);
			dataLayer.setColumnWidthPercentageByPosition(1, 20);
			dataLayer.setColumnWidthPercentageByPosition(2, 15);
			dataLayer.setColumnWidthPercentageByPosition(3, 15);
			dataLayer.setColumnWidthPercentageByPosition(4, 15);
			dataLayer.setColumnWidthPercentageByPosition(5, 15);
			break;

		default:
			return; // all columns have same width
		}
	}

	private static void addEditDisabledLabel(final DataLayer dataLayer, final IEditableRule editableRule,
			final boolean isHeader) {
		final IConfigLabelAccumulator dataLayerLabelAccumulator = dataLayer.getConfigLabelAccumulator();
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (dataLayerLabelAccumulator != null) {
				dataLayerLabelAccumulator.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			if (!editableRule.isEditable(columnPosition, rowPosition)) {
				if (isHeader) {
					configLabels.addLabel(DISABLED_HEADER);
				} else {
					configLabels.addLabel(DISABLED_CELL);
				}
			}
		});
	}

	private static void setNatTableStyle(final NatTable table) {
		final DefaultNatTableStyleConfiguration tableStyle = new DefaultNatTableStyleConfiguration();
		final DefaultSelectionStyleConfiguration selectionStyle = new DefaultSelectionStyleConfiguration();
		final DefaultColumnHeaderStyleConfiguration columnHeaderStyle = new DefaultColumnHeaderStyleConfiguration();
		final DefaultRowHeaderStyleConfiguration rowHeaderStyle = new DefaultRowHeaderStyleConfiguration();

		tableStyle.bgColor = GUIHelper.COLOR_WHITE;
		tableStyle.cellPainter = new TextPainter();

		selectionStyle.fullySelectedHeaderBgColor = GUIHelper.COLOR_WHITE;
		selectionStyle.selectedHeaderBgColor = GUIHelper.COLOR_WHITE;
		selectionStyle.selectedHeaderFgColor = GUIHelper.COLOR_BLACK;
		selectionStyle.selectedHeaderFont = GUIHelper.DEFAULT_FONT;
		selectionStyle.selectionFont = GUIHelper.DEFAULT_FONT;

		columnHeaderStyle.font = GUIHelper.DEFAULT_FONT;
		columnHeaderStyle.bgColor = GUIHelper.COLOR_WHITE;
		columnHeaderStyle.renderGridLines = Boolean.TRUE;
		columnHeaderStyle.cellPainter = new TextPainter();

		rowHeaderStyle.font = GUIHelper.DEFAULT_FONT;
		rowHeaderStyle.bgColor = GUIHelper.COLOR_WHITE;
		rowHeaderStyle.cellPainter = new TextPainter();

		table.setBackground(GUIHelper.COLOR_WHITE);
		table.addOverlayPainter(new NatTableBorderOverlayPainter());

		table.addConfiguration(tableStyle);
		table.addConfiguration(selectionStyle);
		table.addConfiguration(columnHeaderStyle);
		table.addConfiguration(rowHeaderStyle);
		table.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(final IConfigRegistry configRegistry) {
				Style cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.COLOR_DARK_GRAY);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						DEFAULT_CELL);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_RED);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						ERROR_CELL);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(255, 100, 100));
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
						ERROR_CELL);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_YELLOW);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						WARNING_CELL);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(255, 255, 100));
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
						WARNING_CELL);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						LEFT_ALIGNMENT);

				cellStyle = new Style();
				final Font font = GUIHelper.getFont(new FontData(GUIHelper.DEFAULT_FONT.toString(), 10, SWT.BOLD));
				cellStyle.setAttributeValue(CellStyleAttributes.FONT, font);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						GridRegion.COLUMN_HEADER);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
						GridRegion.COLUMN_HEADER);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.EDIT,
						GridRegion.COLUMN_HEADER);

				cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WIDGET_LIGHT_SHADOW);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						DISABLED_CELL);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						DISABLED_HEADER);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
						DISABLED_HEADER);
				// Padding for the left aligned cells
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new BackgroundPainter(
						new PaddingDecorator(new TextPainter(false, true, false, true), 2, 2, 2, 2)));

				configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_STYLE, DisplayMode.SELECT,
						SelectionStyleLabels.SELECTION_ANCHOR_STYLE);

			}
		});

		table.configure();
	}

	private static class DefaultUiBindingConfiguration extends AbstractUiBindingConfiguration {
		@Override
		public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
			uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.NONE, SWT.SPACE), new KeyEditAction());
			uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.NONE, SWT.F2), new KeyEditAction());
			uiBindingRegistry.registerKeyBinding(new LetterOrDigitKeyEventMatcher(), new KeyEditAction());
			uiBindingRegistry.registerKeyBinding(new LetterOrDigitKeyEventMatcher(SWT.MOD2), new KeyEditAction());
			uiBindingRegistry.registerFirstSingleClickBinding(new CellPainterMouseEventMatcher(GridRegion.BODY,
					MouseEventMatcher.LEFT_BUTTON, CheckBoxPainter.class), new MouseEditAction());
			uiBindingRegistry.registerDoubleClickBinding(new CellEditorMouseEventMatcher(GridRegion.BODY),
					new MouseEditAction());
		}
	}

	private static class DefaultTreeSelectionLayerConfiguration extends DefaultSelectionLayerConfiguration {

		private final SelectionLayer selectionLayer;
		private final IUniqueIndexLayer pasteLayer;

		public DefaultTreeSelectionLayerConfiguration(final SelectionLayer selectionLayer,
				final IUniqueIndexLayer pasteLayer) {
			this.selectionLayer = selectionLayer;
			this.pasteLayer = pasteLayer;
			addPasteLayerConfiguration();
		}

		@Override
		protected void addSelectionUIBindings() {
			addConfiguration(new DefaultTreeSelectionBindings());
		}

		protected void addPasteLayerConfiguration() {
			addConfiguration(new DefaultTreePasteLayerConfiguration(selectionLayer, pasteLayer));
		}
	}

	private static class DefaultTreeSelectionBindings extends DefaultSelectionBindings {

		@Override
		public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
			super.configureUiBindings(uiBindingRegistry);
			uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'v'), new PasteDataAction());
		}
	}

	private static class DefaultTreePasteLayerConfiguration extends AbstractLayerConfiguration<SelectionLayer> {

		private final SelectionLayer selectionLayer;
		private final IUniqueIndexLayer pasteLayer;

		public DefaultTreePasteLayerConfiguration(final SelectionLayer selectionLayer,
				final IUniqueIndexLayer pasteLayer) {
			this.selectionLayer = selectionLayer;
			this.pasteLayer = pasteLayer;
		}

		@Override
		public void configureTypedLayer(final SelectionLayer layer) {
			layer.registerCommandHandler(new PasteFromClipboardDataCommandHandler(selectionLayer, pasteLayer));
		}
	}

	private NatTableWidgetFactory() {
		throw new UnsupportedOperationException("Helper class should not be insantiated!"); //$NON-NLS-1$
	}

}
