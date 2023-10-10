/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeDropdown;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class TypeSelectionWidget {
	private static final String TYPE = "TYPE"; //$NON-NLS-1$

	private final TabbedPropertySheetWidgetFactory widgetFactory;
	private final Consumer<String> handleSelectionChanged;

	private ConfigurableObject configurableObject;
	private ITypeSelectionContentProvider contentProvider;
	private ITreeContentProvider treeContentProvider;

	private Composite composite;
	private TableViewer tableViewer;
	private Button openEditorButton;

	private boolean typeChangeEnabled;

	public TypeSelectionWidget(final TabbedPropertySheetWidgetFactory widgetFactory,
			final Consumer<String> handleSelectionChanged) {
		this.widgetFactory = widgetFactory;
		this.handleSelectionChanged = handleSelectionChanged;
	}

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	public ITypeSelectionContentProvider getContentProvider() {
		return contentProvider;
	}

	public void createControls(final Composite parent) {
		composite = getWidgetFactory().createComposite(parent);
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		tableViewer = createTableViewer(composite);
		tableViewer.setColumnProperties(new String[] { TYPE });
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setCellModifier(new ICellModifier() {
			@Override
			public void modify(final Object element, final String property, final Object value) {
				if (TYPE.equals(property) && (element != null) && !((TableItem) element).getData().equals(value)) {
					disableOpenEditorForAnyType();
					handleSelectionChanged.accept(value.toString());
				}
			}

			@Override
			public Object getValue(final Object element, final String property) {
				if (TYPE.equals(property)) {
					return element;
				}
				return "Could not load";
			}

			@Override
			public boolean canModify(final Object element, final String property) {
				return typeChangeEnabled;
			}
		});

		openEditorButton = new Button(composite, SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		openEditorButton.addListener(SWT.Selection, event -> {
			DataType dataType = null;
			if (configurableObject instanceof StructManipulator) {
				dataType = ((StructManipulator) configurableObject).getStructType();
			} else if (configurableObject instanceof IInterfaceElement) {
				dataType = ((IInterfaceElement) configurableObject).getType();
			}

			if ((dataType != null) && (dataType.getTypeEntry() != null)) {
				OpenStructMenu.openStructEditor(dataType.getTypeEntry().getFile());
			}
		});
	}

	public void setEditable(final boolean enabled) {
		typeChangeEnabled = enabled;
	}

	public void initialize(final ConfigurableObject type, final ITypeSelectionContentProvider contentProvider,
			final ITreeContentProvider treeContentProvider) {
		this.configurableObject = type;
		this.contentProvider = contentProvider;
		this.treeContentProvider = treeContentProvider;

		if (type instanceof StructManipulator) {
			resizeTextField();
			tableViewer.setInput(new String[] { ((StructManipulator) configurableObject).getStructType().getName() });
		} else if (type instanceof IInterfaceElement) {
			tableViewer.setInput(new String[] { ((IInterfaceElement) configurableObject).getType().getName() });
		}

		disableOpenEditorForAnyType();
		tableViewer.setCellEditors(createCellEditors());
	}

	public void refresh() {
		if (configurableObject instanceof IInterfaceElement) {
			tableViewer.setInput(new String[] { ((IInterfaceElement) configurableObject).getType().getName() });
		}
		disableOpenEditorForAnyType();
	}

	private CellEditor[] createCellEditors() {
		return new CellEditor[] {
				new DataTypeDropdown(this::getTypeLibrary, contentProvider, treeContentProvider, tableViewer) };
	}

	private TableViewer createTableViewer(final Composite parent) {
		final TableViewer viewer = new TableViewer(parent, SWT.NO_SCROLL | SWT.BORDER);

		final Table table = viewer.getTable();
		final TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(100));
		table.setLayout(tableLayout);
		table.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		table.setLinesVisible(false);
		table.setHeaderVisible(false);
		new TableColumn(table, SWT.NONE);

		return viewer;
	}

	private void resizeTextField() {
		final Table table = tableViewer.getTable();
		table.getColumn(0).dispose();
		table.setLayout(new TableLayout());
		table.setLayoutData(new GridData(SWT.FILL, 0, false, false));
		new TableColumn(table, SWT.NONE).setWidth(150);
	}

	private void disableOpenEditorForAnyType() {
		if (configurableObject instanceof StructManipulator) {
			openEditorButton.setEnabled(
					!"ANY_STRUCT".contentEquals(((StructManipulator) configurableObject).getStructType().getName())); //$NON-NLS-1$
		} else if ((configurableObject instanceof Event) || (configurableObject instanceof AdapterDeclaration)) {
			// reset parent composite and dispose button
			final Composite typeComp = tableViewer.getTable().getParent();
			final GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			typeComp.setLayout(gridLayout);
			typeComp.setLayoutData(new GridData(SWT.FILL, 0, true, false));
			openEditorButton.dispose();
		} else if (configurableObject instanceof VarDeclaration) {
			final DataType dtp = ((VarDeclaration) configurableObject).getType();
			openEditorButton.setEnabled((dtp instanceof StructuredType) && !IecTypes.GenericTypes.isAnyType(dtp)); // $NON-NLS-1$
		}
	}

	protected TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(configurableObject);
	}

	public Composite getControl() {
		return composite;
	}
}
