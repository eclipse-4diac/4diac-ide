/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Dario Romano - original implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.Set;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class FBTypeSearchResultTable extends Composite {

	protected static final int TABLE_COL_WIDTH = 150;
	private ColumnLabelProvider labelElement;
	private ColumnLabelProvider labelPath;
	private ColumnLabelProvider labelType;
	private ColumnLabelProvider labelDataType;
	final AbstractTypeEntryDataHandler<? extends TypeEntry> dataHandler;

	private static final SearchNameDictionary dictionary = new SearchNameDictionary();

	public FBTypeSearchResultTable(final Composite parent,
			final AbstractTypeEntryDataHandler<? extends TypeEntry> data) {
		this(parent, SWT.None, data);

	}

	public FBTypeSearchResultTable(final Composite parent, final int style,
			final AbstractTypeEntryDataHandler<? extends TypeEntry> data) {
		super(parent, style);
		this.dataHandler = data;
		createTreeViewer();
	}

	private Control createTreeViewer() {
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		if (dataHandler.loadInputSet()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(SWT.NONE).create(this);
			warningLabel.setText("No additional function blocks or types have been affected by this change!"); //$NON-NLS-1$
		} else {
			final Label label = new Label(getParent(), SWT.NONE);
			label.setText("Number of instances of " + dataHandler.typeEntry.getTypeName() + " : " //$NON-NLS-1$ //$NON-NLS-2$
					+ dataHandler.getInputSet().size());
			final TreeViewer treeViewer = new TreeViewer(this);
			configureTableViewer(treeViewer);
			treeViewer.setInput(dataHandler.getInputSet().keySet());
			GridLayoutFactory.fillDefaults().generateLayout(this);
		}
		return getParent();
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
				if (dataHandler.getTypeOfElement(element) != null) {
					return dataHandler.getTypeOfElement(element).getTypeEditable().getName();
				}
				return element.getClass().getSimpleName();
			}
		};
	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		return layout;
	}

	protected void configureTableViewer(final TreeViewer viewer) {
		createLabelProviders();
		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				return ArrayContentProvider.getInstance().getElements(inputElement);
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final FBType type) {
					return dataHandler.getChild(type.getName()).toArray();
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
					final Set<INamedElement> child = dataHandler.getChild(type.getName());
					return child != null && !child.isEmpty();
				}
				return false;
			}

		});

		final Tree table = viewer.getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());
		table.setSortDirection(SWT.DOWN);

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

}
