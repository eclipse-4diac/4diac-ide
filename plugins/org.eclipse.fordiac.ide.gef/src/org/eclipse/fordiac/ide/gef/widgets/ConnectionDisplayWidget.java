/*******************************************************************************
 * Copyright (c) 2016, 2021 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin
 *     - initial API and implementation and/or initial documentation
 *     - extracted code for in/out connections table from InterfaceElementSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.properties.ChangeDestinationSourceDialog;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.change.ChangeConnectionCommentCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteChangeDestinationSourceWidget;
import org.eclipse.fordiac.ide.ui.widget.CustomTextCellEditor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ConnectionDisplayWidget {

	protected Section connectionSection;
	protected TableViewer connectionsViewer;
	protected AddDeleteChangeDestinationSourceWidget inputButtons;

	private static final int TARGET_PIN_WIDTH = 100;
	private static final int COMMENT_WIDTH = 200;

	private final TabbedPropertySheetWidgetFactory widgetFactory;
	protected IInterfaceElement type;
	private ComposedAdapterFactory adapterFactory;
	private final AbstractSection parentSection;

	protected boolean isInputViewer; // true - yes; false - out-conn viewer

	public ConnectionDisplayWidget(final TabbedPropertySheetWidgetFactory widgetFactory, final Composite parent,
			final AbstractSection section) {
		this.widgetFactory = widgetFactory;
		parentSection = section;
		createConnectionDisplaySection(parent);
	}

	private void createConnectionDisplaySection(final Composite parent) {
		connectionSection = widgetFactory.createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		connectionSection.setText(Messages.InterfaceElementSection_ConnectionGroup);
		connectionSection.setLayout(new GridLayout(1, false));
		connectionSection
				.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).create());

		final Composite composite = widgetFactory.createComposite(connectionSection);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		inputButtons = new AddDeleteChangeDestinationSourceWidget();
		inputButtons.createControls(composite, widgetFactory);
		inputButtons.setVisibleCreateButton(false);

		connectionsViewer = createConnectionsViewer(composite);

		inputButtons.bindToTableViewer(connectionsViewer, parentSection, ref -> null,
				ref -> new DeleteConnectionCommand((Connection) ref),
				ref -> new ChangeDestinationSourceDialog(connectionsViewer.getControl().getShell(), (Connection) ref,
						getInterfaceElement((Connection) ref)));

		connectionSection.setClient(composite);
	}

	protected TableViewer createConnectionsViewer(final Composite parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.setColumnProperties(
				new String[] { FordiacMessages.Target, FordiacMessages.Pin, FordiacMessages.Comment });
		viewer.setCellModifier(new ConnectionCellModifier(viewer));
		viewer.setCellEditors(new CellEditor[] { null, null, new CustomTextCellEditor(viewer.getTable()) });
		viewer.setLabelProvider(new ConnectionTableLabelProvider());
		viewer.setContentProvider(new ConnectionContentProvider());
		viewer.addDoubleClickListener(ElementSelector::jumpToPinFromDoubleClickEvent);
		return viewer;
	}

	protected static TableLayout createTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Target);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Pin);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(TARGET_PIN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TARGET_PIN_WIDTH));
		layout.addColumnData(new ColumnPixelData(COMMENT_WIDTH));
		return layout;
	}

	// The default one
	public void refreshConnectionsViewer(final IInterfaceElement type) {
		this.type = type;
		isInputViewer = type.isIsInput();
		refreshConnectionsViewerManual(isInputViewer);
	}

	// For setting it manually when needed
	public void refreshConnectionsViewerManual(final boolean manualInputViewer) {
		isInputViewer = manualInputViewer;
		if (manualInputViewer) {
			connectionSection.setText(Messages.InterfaceElementSection_InConnections);
		} else {
			connectionSection.setText(Messages.InterfaceElementSection_OutConnections);
		}
		connectionsViewer.setInput(type);
		connectionsViewer.getTable().requestLayout();
	}

	public void setEditable(final boolean edit) {
		inputButtons.setVisible(edit);
		inputButtons.setEnabled(edit);
	}

	private class ConnectionCellModifier implements ICellModifier {
		private final TableViewer viewer;

		public ConnectionCellModifier(final TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return FordiacMessages.Comment.equals(property);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (FordiacMessages.Comment.equals(property)) {
				final Connection con = (Connection) element;
				return con.getComment() != null ? con.getComment() : ""; //$NON-NLS-1$
			}
			return null;
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Object data = tableItem.getData();

			if (FordiacMessages.Comment.equals(property)) {
				final Connection con = (Connection) data;
				parentSection.executeCommand(new ChangeConnectionCommentCommand(con, value.toString()));
				viewer.refresh(data);
			}
		}
	}

	private class ConnectionTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public static final int TARGET_COL_INDEX = 0;
		public static final int PIN_COL_INDEX = 1;
		public static final int COMMENT_COL_INDEX = 2;

		AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(getAdapterFactory());

		private ComposedAdapterFactory getAdapterFactory() {
			if (adapterFactory == null) {
				adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
				adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new LibraryElementItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new DataItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
				adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
			}
			return adapterFactory;
		}

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			if (element instanceof final Connection con) {
				final IInterfaceElement ie = getInterfaceElement(con);
				if (null != ie) {
					switch (columnIndex) {
					case TARGET_COL_INDEX:
						if (null != ie.getFBNetworkElement()) {
							return labelProvider.getImage(ie.getFBNetworkElement());
						}
						break;
					case PIN_COL_INDEX:
						return labelProvider.getImage(ie);
					default:
						break;
					}
				}
			}
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final Connection con) {
				final IInterfaceElement ie = getInterfaceElement(con);
				if (null != ie) {
					switch (columnIndex) {
					case TARGET_COL_INDEX:
						if (null != ie.getFBNetworkElement()) {
							return ie.getFBNetworkElement().getName();
						}
						if (ie.eContainer() == null) { // broken connection
							return ""; //$NON-NLS-1$
						}
						if (ie.eContainer().eContainer() instanceof CompositeFBType) {
							return ((CompositeFBType) ie.eContainer().eContainer()).getName();
						}
						break;
					case PIN_COL_INDEX:
						return ie.getName();
					case COMMENT_COL_INDEX:
						return con.getComment() != null ? con.getComment() : ""; //$NON-NLS-1$
					default:
						break;
					}
				}
			}
			return element.toString();
		}
	}

	private IInterfaceElement getInterfaceElement(final Connection con) {
		if (con != null) {
			return (type.equals(con.getSource())) ? con.getDestination() : con.getSource();
		}
		return null;
	}

	private static class ConnectionContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof final IInterfaceElement element) {
				if ((element.isIsInput() && (null != element.getFBNetworkElement()))
						|| (!element.isIsInput() && (null == element.getFBNetworkElement()))) {
					return element.getInputConnections().toArray();
				}
				return element.getOutputConnections().toArray();
			}
			return new Object[] {};
		}
	}
}
