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
 *   Fabio Gandolfi - added functionality to update nested DataTypes
 *   Patrick Aigner - changed to handle updates in Function Blocks
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.search.Messages;
import org.eclipse.fordiac.ide.model.search.SearchNameDictionary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class FBTypeUpdateDialog<T extends TypeEntry> extends MessageDialog {

	protected final AbstractTypeEntryDataHandler<T> data;

	private static final int NUMBER_OF_COLLUMNS = 1;
	protected static final int TABLE_COL_WIDTH = 150;
	private static final int CHECK_BOX_COL_WIDTH = 60;
	private boolean selectAll = true;

	private static TreeViewer treeViewer;
	private ColumnLabelProvider labelElement;
	private ColumnLabelProvider labelPath;
	private ColumnLabelProvider labelType;
	private ColumnLabelProvider labelDataType;

	private static final SearchNameDictionary dictionary = new SearchNameDictionary();

	public FBTypeUpdateDialog(final Shell parentShell, final String dialogTitle, final Image dialogTitleImage,
			final String dialogMessage, final int dialogImageType, final String[] dialogButtonLabels,
			final int defaultIndex, final AbstractTypeEntryDataHandler<T> dataHandler) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.data = dataHandler;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(NUMBER_OF_COLLUMNS, true));

		final Composite searchResArea = WidgetFactory.composite(NONE).create(parent);
		searchResArea.setLayout(new GridLayout(1, true));
		searchResArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final HashMap<INamedElement, T> result = data.createInputSet(data.getTypeEntry());
		if (result.isEmpty()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(NONE).create(searchResArea);
			warningLabel.setText("No additional function blocks or types have been affected by this change!"); //$NON-NLS-1$
		} else {
			data.setInputSet(result);
			createfilterButtons(parent);
			final Label label = new Label(parent, SWT.NONE);
			label.setText("Number of instances of " + data.typeEntry.getTypeName() + " : " //$NON-NLS-1$
					+ data.getInputSet().size());
			treeViewer = createTreeViewer(searchResArea);
			configureTableViewer(treeViewer);
			treeViewer.setInput(result.keySet());
			GridLayoutFactory.fillDefaults().generateLayout(searchResArea);
			changeSelectionState(treeViewer.getTree(), true); // check all elements per default
		}
		return parent;
	}

	protected void createfilterButtons(final Composite parent) {
		// Override this method to add searchFilter buttons
	}

	private void createLabelProviders() {
		this.labelElement = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final INamedElement namedElem) {
					return namedElem.getName();
				}
				return super.getText(element);
			}
		};
		this.labelPath = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return dictionary.hierarchicalName(element);
			}
		};
		this.labelType = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final SubApp subapp) {
					return subapp.isTyped() ? "Typed SubApp" : "Untyped SubApp"; //$NON-NLS-1$//$NON-NLS-2$
				}
				if (element instanceof IInterfaceElement || element instanceof StructManipulator) {
					return ((ITypedElement) element).getTypeName();
				}
				return element.getClass().getSimpleName();

			}
		};
		this.labelDataType = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (data.getTypeOfElement(element) != null) {
					return data.getTypeOfElement(element).getTypeEditable().getName();
				}
				return element.getClass().getSimpleName();
			}
		};
	}

	public void refresh() {
	}

	private static TreeViewer createTreeViewer(final Composite parent) {
		return new TreeViewer(parent, SWT.CHECK);
	}

	protected void configureTableViewer(final TreeViewer viewer) {
		data.clearElements();
		createLabelProviders();
		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				return ArrayContentProvider.getInstance().getElements(inputElement);
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final FBType type) {
					return data.getChild(type.getName()).toArray();
				}
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof final SubApp subApp && !subApp.isTyped()) {
					return null;
				}
				if (element instanceof final FBNetworkElement elem) {
					return elem.getType();
				}
				return null;
			}

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof final FBType type) {
					return !data.getChild(type.getName()).isEmpty();
				}
				return false;
			}

		});

		final Tree table = viewer.getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());
		table.setSortDirection(SWT.DOWN);

		table.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				final TreeItem tableItem = (TreeItem) event.item;
				// if it's a type just add or remove it
				if (tableItem.getData() instanceof FBType) {
					clickTypeItem(tableItem);
				} else if (tableItem.getData() instanceof FBNetworkElement) {
					clickFBNetworkItem(tableItem);
				}
			}
		});

		// Check-box column
		final TreeViewerColumn colCheckBox = new TreeViewerColumn(viewer, SWT.WRAP);
		colCheckBox.getColumn().setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
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
		colCheckBox.getColumn().addListener(SWT.Expand, event -> {
			colCheckBox.getColumn().pack();
		});

		colCheckBox.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ""; //$NON-NLS-1$
			}
		});
		final SelectionListener sortListener = new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Get the selected column

				final TreeColumn selectedColumn = (TreeColumn) e.getSource();
				if (!table.getSortColumn().equals(selectedColumn)) {
					table.setSortColumn(selectedColumn);
				} else {
					table.setSortDirection(table.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
				}
				table.redraw();
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Do nothing
			}
		};

		// Element column
		final TreeViewerColumn colElement = new TreeViewerColumn(viewer, SWT.LEAD);
		colElement.getColumn().setText(Messages.Element);
		colElement.setLabelProvider(labelElement);

		table.setSortColumn(colElement.getColumn());

		colElement.getColumn().addSelectionListener(sortListener);

		// Location name column
		final TreeViewerColumn colPath = new TreeViewerColumn(viewer, SWT.LEAD);
		colPath.getColumn().setText(Messages.Location);
		colPath.setLabelProvider(labelPath);

		colPath.getColumn().addSelectionListener(sortListener);

		// Type name column
		final TreeViewerColumn colType = new TreeViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(FordiacMessages.Type);
		colType.setLabelProvider(labelType);
		colType.getColumn().addSelectionListener(sortListener);

		// DataType name column
		final TreeViewerColumn colDataType = new TreeViewerColumn(viewer, SWT.LEAD);
		colDataType.getColumn().setText(FordiacMessages.DataType);
		colDataType.setLabelProvider(labelDataType);
		colDataType.getColumn().addSelectionListener(sortListener);

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				final TreeColumn sortCol = table.getSortColumn();
				String s1 = ""; //$NON-NLS-1$
				String s2 = ""; //$NON-NLS-1$
				if (sortCol.equals(colElement.getColumn())) {
					s1 = labelElement.getText(e1);
					s2 = labelElement.getText(e2);
				} else if (sortCol.equals(colPath.getColumn())) {
					s1 = labelPath.getText(e1);
					s2 = labelPath.getText(e2);
				} else if (sortCol.equals(colType.getColumn())) {
					s1 = labelType.getText(e1);
					s2 = labelType.getText(e2);
				} else if (sortCol.equals(colDataType.getColumn())) {
					s1 = labelDataType.getText(e1);
					s2 = labelDataType.getText(e2);
				}
				return table.getSortDirection() == SWT.DOWN ? s1.compareTo(s2) : s2.compareTo(s1);
			}
		});

	}

	private void clickTypeItem(final TreeItem item) {
		if (item.getChecked()) {
			data.addElement((INamedElement) item.getData());
			if (!item.getExpanded()) {
				item.setExpanded(true);
				treeViewer.refresh();
			}
			Arrays.asList(item.getItems()).stream().forEach(e -> {
				e.setChecked(true);
				data.addElement((INamedElement) e.getData());
			});
		} else {
			data.removeElement(item.getData());
			Arrays.asList(item.getItems()).stream().forEach(e -> {
				e.setChecked(false);
				data.removeElement(e.getData());
			});
		}
	}

	private void clickFBNetworkItem(final TreeItem item) {
		final TreeItem parent = item.getParentItem();
		if (item.getChecked()) {
			if (parent != null) {
				parent.setChecked(true);
				data.addElement((INamedElement) parent.getData());
			}
			data.addElement((INamedElement) item.getData());
		} else {
			data.removeElement(item.getData());
		}
	}

	protected TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(CHECK_BOX_COL_WIDTH, true));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		return layout;
	}

	void changeSelectionState(final Tree table, final boolean state) {
		final List<TreeItem> allItemList = new ArrayList<>();
		getAllItems(allItemList, Arrays.asList(table.getItems()));
		for (final TreeItem tableItem : allItemList) {
			tableItem.setChecked(state);
			final Object tData = tableItem.getData();
			if (tData instanceof StructuredType || tData instanceof FBNetworkElement || tData instanceof FBType) {
				if (state) {
					data.addElement((INamedElement) tData);
				} else {
					data.removeElement(tData);
				}
			}
		}
	}

	private static void getAllItems(final List<TreeItem> allItemList, final List<TreeItem> roots) {
		allItemList.addAll(roots);
		roots.stream().forEach(r -> {
			if (!r.getExpanded()) {
				r.setExpanded(true);
				treeViewer.refresh();
			}
			getAllItems(allItemList, Arrays.asList(r.getItems()));
		});
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public AbstractTypeEntryDataHandler<T> getDataHandler() {
		return data;
	}
}
