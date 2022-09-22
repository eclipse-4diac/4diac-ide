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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.action.KeyEditAction;
import org.eclipse.nebula.widgets.nattable.edit.action.MouseEditAction;
import org.eclipse.nebula.widgets.nattable.edit.command.DeleteSelectionCommandHandler;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultColumnHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.painter.NatTableBorderOverlayPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionBindings;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.SelectionStyleLabels;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.CellEditorMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.LetterOrDigitKeyEventMatcher;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public final class NatTableWidgetFactory {
	public static final String DEFAULT_CELL = "DEFAULT_CELL"; //$NON-NLS-1$
	public static final String ERROR_CELL = "ERROR_CELL"; //$NON-NLS-1$
	public static final String DISABLED_CELL = "DISABLED_CELL"; //$NON-NLS-1$ $
	public static final String TYPE_CELL = "TYPE_CELL"; //$NON-NLS-1$
	public static final String DISABLED_HEADER = "DISABLED_HEADER"; //$NON-NLS-1$

	private static final char[] ACTIVATION_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '_', '.', SWT.BS };


	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider) {
		return createNatTable(parent, dataLayer, headerDataProvider, IEditableRule.NEVER_EDITABLE);
	}

	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider, final IEditableRule editableRule) {
		return createNatTable(parent, dataLayer, headerDataProvider, editableRule, null, -1);
	}

	public static NatTable createNatTable(final Composite parent, final DataLayer dataLayer,
			final IDataProvider headerDataProvider, final IEditableRule editableRule,
			final Map<String, List<String>> proposals, final int proposalTextFieldColumn) {

		setColumnWidths(dataLayer);
		if (proposals != null) {
			addTypeLabel(dataLayer, proposalTextFieldColumn);
		}

		final SelectionLayer selectionLayer = new SelectionLayer(dataLayer);
		selectionLayer.addConfiguration(new DefaultSelectionBindings() {
			@Override
			public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
				super.configureUiBindings(uiBindingRegistry);
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'v'),
						new PasteDataIntoTableAction());
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'x'), new CutDataFromTableAction());
			}
		});
		selectionLayer.registerCommandHandler(new DeleteSelectionCommandHandler(selectionLayer));
		final ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		final DataLayer columnHeaderDataLayer = new DataLayer(headerDataProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer,
				selectionLayer);

		final CompositeLayer gridLayer = new CompositeLayer(1, 2);
		gridLayer.setChildLayer(GridRegion.COLUMN_HEADER, columnHeaderLayer, 0, 0);
		gridLayer.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		gridLayer.addConfiguration(new DefaultEditConfiguration());
		gridLayer.addConfiguration(new AbstractUiBindingConfiguration() {
			@Override
			public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.NONE, SWT.SPACE), new KeyEditAction());
				uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.NONE, SWT.F2), new KeyEditAction());
				uiBindingRegistry.registerKeyBinding(new LetterOrDigitKeyEventMatcher(), new KeyEditAction());
				uiBindingRegistry.registerKeyBinding(new LetterOrDigitKeyEventMatcher(SWT.MOD2), new KeyEditAction());
				uiBindingRegistry.registerDoubleClickBinding(new CellEditorMouseEventMatcher(GridRegion.BODY),
						new MouseEditAction());
			}
		});
		gridLayer.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(final IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, editableRule);
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
						createButtonEditor(proposals), DisplayMode.EDIT, TYPE_CELL);
			}
		});

		addEditDisabledLabel(dataLayer, editableRule, false);
		addEditDisabledLabel(columnHeaderDataLayer, editableRule, true);

		final NatTable table = new NatTable(parent, gridLayer, false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		setNatTableStyle(table);

		return table;
	}

	private static void setColumnWidths(final DataLayer dataLayer) {
		dataLayer.setColumnPercentageSizing(true);
		switch (dataLayer.getColumnCount()) {
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
			dataLayer.setColumnWidthPercentageByPosition(3, 15);
			dataLayer.setColumnWidthPercentageByPosition(4, 30);
			break;

		default:
			return;	// all columns have same width
		}
	}

	private static void addTypeLabel(final DataLayer dataLayer, final int proposalTextFieldColumn) {
		final IConfigLabelAccumulator dataLayerLabelAccumulator = dataLayer.getConfigLabelAccumulator();
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (dataLayerLabelAccumulator != null) {
				dataLayerLabelAccumulator.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			if (columnPosition == proposalTextFieldColumn) {
				configLabels.addLabel(TYPE_CELL);
			}
		});
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
		final DefaultColumnHeaderStyleConfiguration headerStyle = new DefaultColumnHeaderStyleConfiguration();

		tableStyle.bgColor = GUIHelper.COLOR_WHITE;
		tableStyle.cellPainter = new TextPainter();

		selectionStyle.fullySelectedHeaderBgColor = GUIHelper.COLOR_WHITE;
		selectionStyle.selectedHeaderBgColor = GUIHelper.COLOR_WHITE;
		selectionStyle.selectedHeaderFgColor = GUIHelper.COLOR_BLACK;
		selectionStyle.selectedHeaderFont = GUIHelper.DEFAULT_FONT;
		selectionStyle.selectionFont = GUIHelper.DEFAULT_FONT;

		headerStyle.font = GUIHelper.DEFAULT_FONT;
		headerStyle.bgColor = GUIHelper.COLOR_WHITE;
		headerStyle.renderGridLines = Boolean.TRUE;
		headerStyle.cellPainter = new TextPainter();

		table.setBackground(GUIHelper.COLOR_WHITE);
		table.addOverlayPainter(new NatTableBorderOverlayPainter());

		table.addConfiguration(tableStyle);
		table.addConfiguration(selectionStyle);
		table.addConfiguration(headerStyle);
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
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WIDGET_LIGHT_SHADOW);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						DISABLED_CELL);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						DISABLED_HEADER);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
						DISABLED_HEADER);

				configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_STYLE, DisplayMode.SELECT,
						SelectionStyleLabels.SELECTION_ANCHOR_STYLE);
			}
		});

		table.configure();
	}

	private static ICellEditor createButtonEditor(final Map<String, List<String>> proposals) {
		if (proposals == null) {
			return new TextCellEditor();
		}

		return new ButtonTextCellEditor(proposals);
	}

	private static class ButtonTextCellEditor extends TextCellEditor {
		Map<String, List<String>> elements;
		final SimpleContentProposalProvider proposalProvider;
		private Button button;

		public ButtonTextCellEditor(final Map<String, List<String>> elements) {
			super();
			this.elements = elements;

			proposalProvider = new SimpleContentProposalProvider();
			proposalProvider.setFiltering(true);
			enableContentProposal(new TextContentAdapter(), proposalProvider, null, ACTIVATION_CHARS);
		}

		private void refreshProposal() {
			final List<String> proposals = new ArrayList<>();
			elements.forEach((k, v) -> {
				proposals.addAll(v);
			});
			proposalProvider.setProposals(proposals.toArray(new String[0]));
		}

		@Override
		protected Text createEditorControl(final Composite parent, final int style) {
			focusListener = new FocusAdapter() {
				// remove focus Listener for button popup
			};
			return super.createEditorControl(parent, style);
		}

		@Override
		protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
			refreshProposal();
			button = new Button(parent, SWT.FLAT);
			button.setText("..."); //$NON-NLS-1$
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
							Display.getCurrent().getActiveShell(), new LabelProvider() {
								@Override
								public String getText(final Object element) {
									if (element instanceof Node) {
										return ((Node) element).getValue();
									}
									return element.toString();
								}

								@Override
								public Image getImage(final Object element) {
									if (element instanceof Node) {
										final Node node = (Node) element;
										if (node.isDirectory()) {
											return PlatformUI.getWorkbench().getSharedImages()
													.getImage(ISharedImages.IMG_OBJ_ELEMENT);
										}
										return FordiacImage.ICON_DATA_TYPE.getImage();
									}
									return super.getImage(element);
								}
							}, new ITreeContentProvider() {

								@Override
								public boolean hasChildren(final Object element) {
									if (element instanceof Node) {
										return !((Node) element).getChildren().isEmpty();
									}
									return false;
								}

								@Override
								public Object getParent(final Object element) {
									if (element instanceof Node) {
										return ((Node) element).getParent();
									}
									return null;
								}

								@Override
								public Object[] getElements(final Object inputElement) {
									final List<Node> list = new ArrayList<>();
									if (inputElement instanceof HashMap<?, ?>) {
										final HashMap<String, List<String>> map = (HashMap<String, List<String>>) inputElement;

										map.forEach((k, v) -> {
											final Node root = new Node(k, true);
											v.forEach(value -> {
												final Node node = new Node(value);
												node.setParent(root);
												root.addChild(node);
											});
											list.add(root);
										});
									}
									return list.toArray();
								}

								@Override
								public Object[] getChildren(final Object parentElement) {
									if (parentElement instanceof Node) {
										return ((Node) parentElement).getChildren().toArray();
									}
									return new Object[0];
								}
							});
					dialog.setHelpAvailable(false);
					dialog.setInput(elements);

					if (dialog.open() != Window.OK) {
						if (editMode != EditModeEnum.DIALOG) {
							close();
						}
						return;
					}

					final Node selected = (Node) dialog.getFirstResult();
					if (selected != null) {
						setEditorValue(selected.getValue());

						if (editMode == EditModeEnum.INLINE) {
							commit(MoveDirectionEnum.NONE, true);
						}
					}
				}
			});

			final Text text = (Text) super.activateCell(parent, originalCanonicalValue);
			if (editMode == EditModeEnum.DIALOG) {
				final Composite composite = new Composite(parent, 0);
				composite.setLayout(new GridLayout(2, false));
				GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
				text.setParent(composite);
				button.setParent(composite);
				return parent;
			}
			return parent;
		}

		@Override
		public Rectangle calculateControlBounds(final Rectangle cellBounds) {
			button.setBounds(cellBounds.x + cellBounds.width - 25, cellBounds.y, 25, cellBounds.height);
			cellBounds.width = cellBounds.width - 25;
			return super.calculateControlBounds(cellBounds);
		}

		@Override
		public void close() {
			super.close();
			button.dispose();
		}


		private static class Node {
			private final String value;
			private final List<Node> children;
			private Node parent;
			private final boolean isDirectory;

			private Node(final String value) {
				this.value = value;
				this.isDirectory = false;
				children = new ArrayList<>();
			}

			private Node(final String value, final boolean isDirectory) {
				this.value = value;
				this.isDirectory = isDirectory;
				children = new ArrayList<>();
			}

			public boolean isDirectory() {
				return isDirectory;
			}

			private String getValue() {
				return value;
			}

			private List<Node> getChildren() {
				return children;
			}

			private void addChild(final Node child) {
				this.children.add(child);
			}

			private Node getParent() {
				return parent;
			}

			private void setParent(final Node parent) {
				this.parent = parent;
			}
		}
	}
}
