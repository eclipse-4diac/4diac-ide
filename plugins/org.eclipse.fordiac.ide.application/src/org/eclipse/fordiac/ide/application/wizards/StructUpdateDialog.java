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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.search.ModelSearchResultPage;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class StructUpdateDialog extends MessageDialog {

	protected final DataTypeEntry dataTypeEntry;

	private static final int NUMBER_OF_COLLUMNS = 1;
	private static final int TABLE_COL_WIDTH = 150;
	private static final int CHECK_BOX_COL_WIDTH = 30;
	private boolean selectAll = true;

	private Set<StructManipulator> updatedTypes;

	public StructUpdateDialog(final Shell parentShell, final String dialogTitle, final Image dialogTitleImage,
			final String dialogMessage, final int dialogImageType, final String[] dialogButtonLabels,
			final int defaultIndex, final DataTypeEntry dataTypeEntry) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.dataTypeEntry = dataTypeEntry;
	}

	public Set<StructManipulator> getUpdatedTypes() {
		return updatedTypes;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(NUMBER_OF_COLLUMNS, true));

		final Composite searchResArea = WidgetFactory.composite(NONE).create(parent);
		searchResArea.setLayout(new GridLayout(1, true));
		searchResArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final List<INamedElement> result = performStructSearch();
		if (result.isEmpty()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(NONE).create(searchResArea);
			warningLabel.setText("No additional function blocks or types have been affected by this change!"); //$NON-NLS-1$
		} else {
			final TableViewer viewer = createTableViewer(searchResArea);
			configureTableViewer(viewer);

			viewer.setInput(result.toArray());
		}
		return parent;
	}

	protected List<INamedElement> performStructSearch() {
		final StructSearch structSearch = new StructSearch(dataTypeEntry);
		return structSearch.getAllTypesWithStruct();
	}

	private static TableViewer createTableViewer(final Composite parent) {
		return TableWidgetFactory.createTableViewer(parent,
				SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	private void configureTableViewer(final TableViewer viewer) {
		updatedTypes = new HashSet<>();
		viewer.setContentProvider(new ArrayContentProvider());
		final Table table = viewer.getTable();

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());

		table.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				final TableItem tableItem = (TableItem) event.item;
				if (tableItem.getData() instanceof StructManipulator) {
					if (tableItem.getChecked()) {
						updatedTypes.add((StructManipulator) tableItem.getData());
					} else {
						updatedTypes.remove(tableItem.getData());
					}
				}

			}
		});

		// Check-box column
		final TableViewerColumn colCheckBox = new TableViewerColumn(viewer, SWT.WRAP);
		colCheckBox.getColumn()
		.setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
		colCheckBox.getColumn().addListener(SWT.Selection, event -> {
			changeSelectionState(table, selectAll);
			if (selectAll) {
				changeSelectionState(table, selectAll);
				colCheckBox.getColumn().setImage(
						PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
			} else {
				changeSelectionState(table, selectAll);
				colCheckBox.getColumn().setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
			}
			selectAll = !selectAll;
		});

		colCheckBox.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ""; //$NON-NLS-1$
			}
		});

		// Element column
		final TableViewerColumn colElement = new TableViewerColumn(viewer, SWT.LEAD);
		colElement.getColumn().setText(Messages.Element);
		colElement.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof INamedElement) {
					return ((INamedElement) element).getName();
				}
				if (element instanceof FBNetworkElement) {
					return ((FBNetworkElement) element).getName();
				}
				return super.getText(element);
			}
		});

		// Location name column
		final TableViewerColumn colPath = new TableViewerColumn(viewer, SWT.LEAD);
		colPath.getColumn().setText(Messages.Location);
		colPath.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ModelSearchResultPage.hierarchicalName(element);
			}
		});

		// Type name column
		final TableViewerColumn colType = new TableViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(Messages.Type);
		colType.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IInterfaceElement) {
					return ((IInterfaceElement) element).getTypeName();
				} else if (element instanceof StructManipulator) {
					return ((StructManipulator) element).getTypeName();
				}
				return super.getText(element);
			}
		});

	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(CHECK_BOX_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		return layout;
	}

	void changeSelectionState(final Table table, final boolean state) {
		for (int i = 0; i < table.getItemCount(); i++) {
			table.getItems()[i].setChecked(state);
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

}
