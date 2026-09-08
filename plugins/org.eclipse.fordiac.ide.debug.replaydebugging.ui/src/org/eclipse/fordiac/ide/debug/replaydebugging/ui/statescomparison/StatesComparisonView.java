/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.ComparisonSorter.SortMode;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class StatesComparisonView extends ViewPart implements ComparisonService.Listener {

	public static final String VIEW_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.StatesComparisonView"; //$NON-NLS-1$

	private static final String EMPTY = ""; //$NON-NLS-1$

	private static final Color DIFF_HIGHLIGHT_COLOR = JFaceResources.getColorRegistry()
			.get("org.eclipse.fordiac.ide.debug.replaydebugging.ui.comparisonDiffColor"); //$NON-NLS-1$
	private static final Color DISABLED_TEXT_COLOR = JFaceResources.getColorRegistry()
			.get("org.eclipse.fordiac.ide.debug.replaydebugging.ui.comparisonDisabledColor"); //$NON-NLS-1$

	private static final int KEY_COLUMN_WIDTH_DEFAULT = 200;
	private static final int DATA_COLUMN_WIDTH_DEFAULT = 100;

	// ── State ────────────────────────────────────────────────────────────

	private TableViewer tableViewer;
	private Composite tableComposite;
	private TableViewerColumn fillerColumn;
	private final List<ColumnState> columnStates = new ArrayList<>();
	private final List<TableViewerColumn> viewerColumns = new ArrayList<>();
	private Listener headerMenuListener;

	private final ComparisonSorter sorter = new ComparisonSorter();
	private final UniquenessFilter uniquenessFilter = new UniquenessFilter();
	private boolean hideNonUnique = false;

	// Toolbar action references — kept to update checked/icon state
	private Action alphaAscAction;
	private Action alphaDescAction;
	private Action uniquenessAction;

	// ── Lifecycle ────────────────────────────────────────────────────────

	@Override
	public void createPartControl(final Composite parent) {
		tableComposite = new Composite(parent, SWT.NONE);
		final TableColumnLayout tableLayout = new TableColumnLayout();
		tableComposite.setLayout(tableLayout);

		tableViewer = new TableViewer(tableComposite,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setComparator(sorter);

		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		contributeToActionBars();

		ComparisonService.getInstance().addListener(this);
		columnsChanged(ComparisonService.getInstance().getColumns());
	}

	@Override
	public void dispose() {
		ComparisonService.getInstance().removeListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	// ── Service Listener ─────────────────────────────────────────────────

	@Override
	public void columnsChanged(final List<ComparisonColumn> incoming) {
		if (tableViewer == null || tableViewer.getTable().isDisposed()) {
			return;
		}

		// Merge: preserve disabled state for columns that already exist
		final Map<String, Boolean> disabledState = columnStates.stream()
				.collect(Collectors.toMap(ColumnState::getColumnId, ColumnState::isDisabled));

		columnStates.clear();
		incoming.forEach(col -> {
			final ColumnState columnState = new ColumnState(col);
			columnState.setDisabled(disabledState.getOrDefault(col.getColumnId(), false).booleanValue());
			columnStates.add(columnState);
		});

		rebuildTable();
	}

	// ── Table Rebuild ────────────────────────────────────────────────────

	private void rebuildTable() {
		final Table table = tableViewer.getTable();
		table.setRedraw(false);
		try {
			viewerColumns.forEach(vc -> vc.getColumn().dispose());
			viewerColumns.clear();

			if (fillerColumn != null && !fillerColumn.getColumn().isDisposed()) {
				fillerColumn.getColumn().dispose();
				fillerColumn = null;
			}

			if (columnStates.isEmpty()) {
				tableViewer.setInput(Collections.emptyList());
				updateSorterAndFilter();
				return;
			}

			// create the datapoints column with all unique row keys across all columns
			final Set<String> rowKeySet = new LinkedHashSet<>();
			columnStates.forEach(columnState -> rowKeySet.addAll(columnState.getColumn().getRowKeys()));
			final List<String> rowKeys = List.copyOf(rowKeySet);
			createRowKeyColumn();

			columnStates.forEach(this::createDataColumn);

			final List<ComparisonColumn> columns = columnStates.stream().map(ColumnState::getColumn).toList();
			final List<RowEntry> rows = rowKeys.stream().map(rowKey -> new RowEntry(rowKey, columns)).toList();

			updateSorterAndFilter();
			tableViewer.setInput(rows);
			tableViewer.refresh();

			viewerColumns.forEach(TableViewerColumn::getColumn);

			applyColumnWidths();

			attachHeaderMenuListener();

			addFillerColumn();

		} finally {
			table.setRedraw(true);
		}
	}

	private void addFillerColumn() {
		// Dispose previous filler if present
		if (fillerColumn != null && !fillerColumn.getColumn().isDisposed()) {
			fillerColumn.getColumn().dispose();
		}

		fillerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		fillerColumn.getColumn().setResizable(false);
		fillerColumn.getColumn().setMoveable(false);
		fillerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return EMPTY;
			}
		});

		// Give it weight=1 via ColumnWeightData so it absorbs all remaining
		// space — real columns use ColumnPixelData so they are unaffected
		final TableColumnLayout layout = (TableColumnLayout) tableComposite.getLayout();
		layout.setColumnData(fillerColumn.getColumn(), new ColumnWeightData(1, 0, false)); // weight=1, minWidth=0,
																							// resizable=false

		tableComposite.layout(true, true);
	}

	private void applyColumnWidths() {
		final TableColumnLayout layout = (TableColumnLayout) tableComposite.getLayout();

		for (int i = 0; i < viewerColumns.size(); i++) {
			final int width = (i == 0) ? KEY_COLUMN_WIDTH_DEFAULT : DATA_COLUMN_WIDTH_DEFAULT;

			layout.setColumnData(viewerColumns.get(i).getColumn(), new ColumnPixelData(width, true, false));
		}

		tableComposite.layout(true, true);
	}

	// ── Column Factory ───────────────────────────────────────────────────

	private void createRowKeyColumn() {
		final TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		col.getColumn().setText(Messages.StatesComparisonView_DatapointsHeader);
		col.getColumn().setResizable(true);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object e) {
				return e instanceof final RowEntry r ? r.rowKey() : EMPTY;
			}
		});
		viewerColumns.add(col);
	}

	private void createDataColumn(final ColumnState columnState) {
		final TableViewerColumn col = new TableViewerColumn(tableViewer, SWT.NONE);
		styleColumnHeader(col, columnState);
		col.setLabelProvider(buildLabelProvider(columnState));
		viewerColumns.add(col);
	}

	/**
	 * Sets the column header text and greys it out when disabled.
	 */
	private static void styleColumnHeader(final TableViewerColumn col, final ColumnState columnState) {
		col.getColumn().setText(columnState.getColumn().getLabel());
		col.getColumn().setResizable(true);
		col.getColumn().setMoveable(true);
	}

	private ColumnLabelProvider buildLabelProvider(final ColumnState columnState) {
		return new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				if (!(element instanceof final RowEntry r)) {
					return EMPTY;
				}
				return columnState.getColumn().getCell(r.rowKey());
			}

			@Override
			public Color getForeground(final Object element) {
				if (columnState.isDisabled()) {
					return DISABLED_TEXT_COLOR;
				}
				return null;
			}

			@Override
			public Color getBackground(final Object element) {
				// No diff highlight for disabled columns
				if (columnState.isDisabled() || !(element instanceof final RowEntry r)) {
					return null;
				}
				if (isUnique(r.rowKey(), columnState)) {
					return DIFF_HIGHLIGHT_COLOR;
				}
				if (columnState.isShowColor()) {
					return columnState.getColumn().getColor();
				}
				return null;
			}
		};
	}

	// ── Per-Column Header Context Menu ───────────────────────────────────

	private void attachHeaderMenuListener() {
		final Table table = tableViewer.getTable();

		// Remove any previously registered MenuDetect listener to avoid stacking
		// listeners across rebuilds. Use a stored reference for cleanup.
		if (headerMenuListener != null) {
			table.removeListener(SWT.MenuDetect, headerMenuListener);
		}

		headerMenuListener = event -> {
			// Convert the display-relative coordinates from the event
			// to table-relative coordinates for hit testing
			final Point displayPoint = new Point(event.x, event.y);
			final Point tablePoint = table.toControl(displayPoint);

			// getItem() only returns a TableItem (a row), not a column.
			// For header hit-testing we must check the header bounds manually.
			final int columnIndex = getColumnIndexAtX(table, tablePoint.x);

			// -1 means the click was not on any column header,
			// 0 is the "Property" label column which has no actions
			if (columnIndex <= 0) {
				return;
			}

			// columnStates index is offset by 1 because index 0 is the
			// "Property" stub column which has no corresponding ColumnState
			final ColumnState columnState = columnStates.get(columnIndex - 1);

			final Menu menu = new Menu(table.getShell(), SWT.POP_UP);
			fillColumnMenu(menu, columnState);
			menu.setLocation(displayPoint); // must use display coords for setLocation
			menu.setVisible(true);

			event.doit = false; // suppress any default menu
		};

		table.addListener(SWT.MenuDetect, headerMenuListener);
	}

	/**
	 * Returns the index of the column whose header occupies the given x coordinate,
	 * or -1 if the click is outside all headers.
	 *
	 * Must be called only when tablePoint.y is within the header band, but we check
	 * that here too via getHeaderHeight().
	 */
	private static int getColumnIndexAtX(final Table table, final int x) {
		// Is the click within the header row at all?
		if (x < 0) {
			return -1;
		}

		// Walk columns in their current visual order.
		// getColumnOrder() gives the display order, which may differ from
		// creation order if the user has dragged columns around.
		final int[] order = table.getColumnOrder();
		int pos = 0;

		for (final int visualIndex : order) {
			final TableColumn col = table.getColumn(visualIndex);
			pos += col.getWidth();
			if (x < pos) {
				return visualIndex;
			}
		}
		return -1;
	}

	private void fillColumnMenu(final Menu menu, final ColumnState columnState) {

		// Remove this column
		final MenuItem removeItem = new MenuItem(menu, SWT.PUSH);
		removeItem.setText(Messages.StatesComparisonView_RemoveColumnLabel);
		removeItem.addListener(SWT.Selection,
				e -> ComparisonService.getInstance().removeColumn(columnState.getColumnId()));

		// Disable / Enable toggle
		final MenuItem toggleItem = new MenuItem(menu, SWT.CHECK);
		toggleItem.setText(Messages.StatesComparisonView_ColumnEnabledLabel);
		toggleItem.setSelection(!columnState.isDisabled()); // checked = enabled
		toggleItem.addListener(SWT.Selection, e -> {
			columnState.setDisabled(!columnState.isDisabled());
			rebuildTable();
		});

		// Show/hide colors toggle
		final MenuItem showHideColor = new MenuItem(menu, SWT.PUSH);
		showHideColor.setText(columnState.isShowColor() ? Messages.StatesComparisonView_HideColumnColor
				: Messages.StatesComparisonView_ShowColumnColor);
		showHideColor.addListener(SWT.Selection, e -> {
			columnState.toggleShowColor();
			rebuildTable();
		});
	}

	// ── Toolbar ──────────────────────────────────────────────────────────

	private void contributeToActionBars() {
		final IActionBars bars = getViewSite().getActionBars();
		final IToolBarManager toolbar = bars.getToolBarManager();

		final Action clearAll = new Action(Messages.StatesComparisonView_RemoveAllColumnsLabel) {
			@Override
			public void run() {
				ComparisonService.getInstance().clearAll();
			}
		};
		clearAll.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
		toolbar.add(clearAll);

		// Sort A → Z
		alphaAscAction = new Action(Messages.StatesComparisonView_AlphabeticallyAscendingOrderLabel,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				applySortMode(SortMode.ALPHA_ASC);
			}
		};

		// Sort Z → A
		alphaDescAction = new Action(Messages.StatesComparisonView_AlphabeticallyDescendingOrderLabel,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				applySortMode(SortMode.ALPHA_DESC);
			}
		};

		// Sort by Uniqueness
		uniquenessAction = new Action(Messages.StatesComparisonView_SortByDiffLabel, IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				applySortMode(SortMode.UNIQUENESS);
			}
		};

		toolbar.add(alphaAscAction);
		toolbar.add(alphaDescAction);
		toolbar.add(uniquenessAction);

		final var hideNonUniqueAction = new Action(Messages.StatesComparisonView_ShowOnlyDiffLabel,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				hideNonUnique = isChecked();
				updateSorterAndFilter();
				tableViewer.refresh();
			}
		};
		toolbar.add(hideNonUniqueAction);
	}

	/**
	 * Toggles a sort mode: clicking an already-active sort button turns it off.
	 * Unchecks all other sort buttons.
	 */
	private void applySortMode(final ComparisonSorter.SortMode requested) {
		final var next = (sorter.getMode() == requested) ? ComparisonSorter.SortMode.NONE : requested;

		alphaAscAction.setChecked(next == ComparisonSorter.SortMode.ALPHA_ASC);
		alphaDescAction.setChecked(next == ComparisonSorter.SortMode.ALPHA_DESC);
		uniquenessAction.setChecked(next == ComparisonSorter.SortMode.UNIQUENESS);

		sorter.setMode(next);
		updateSorterAndFilter();
		tableViewer.refresh();
	}

	// ── Sorter / Filter Sync ─────────────────────────────────────────────

	/**
	 * Must be called whenever column disabled-state or hideNonUnique changes, so
	 * the sorter and filter always see the current enabled column list.
	 */
	private void updateSorterAndFilter() {
		final List<ComparisonColumn> enabled = columnStates.stream().filter(columnState -> !columnState.isDisabled())
				.map(ColumnState::getColumn).toList();

		sorter.setActiveColumns(enabled);
		uniquenessFilter.setActiveColumns(enabled);

		if (hideNonUnique) {
			tableViewer.setFilters(uniquenessFilter);
		} else {
			tableViewer.setFilters(); // remove all filters
		}
	}

	// ── Uniqueness Helper ────────────────────────────────────────────────

	private boolean isUnique(final String rowKey, final ColumnState target) {
		final List<ColumnState> enabledColumns = columnStates.stream().filter(cs -> !cs.isDisabled()).toList();
		if (enabledColumns.size() < 2) {
			return false;
		}

		final String val = target.getColumn().getCell(rowKey);
		return enabledColumns.stream()
				.filter(enabledColumn -> !enabledColumn.getColumnId().equals(target.getColumnId()))
				.anyMatch(enabledColumn -> !enabledColumn.getColumn().getCell(rowKey).equals(val));
	}
}