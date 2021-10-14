/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed issues in type changes for subapp interface elements
 *   Lisa Sonnleithner - new TypeAndCommentSection
 *   Alois Zoitl - Harmonized and improved connection section
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CustomTextCellEditor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractSection {
	private Section connectionSection;
	private TableViewer connectionsViewer;

	private static final String TARGET = "target"; //$NON-NLS-1$
	private static final String PIN = "pin"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private static final int TARGET_PIN_WIDTH = 100;
	private static final int COMMENT_WIDTH = 200;

	private Text typeText;
	private Text commentText;
	private Text parameterText;
	private Text currentParameterText;
	private CLabel parameterTextCLabel;
	private CLabel currentParameterTextCLabel;
	private Button openEditorButton;
	private Section infoSection;
	private AddDeleteWidget deleteButton;


	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		createInstanceInfoSection(getLeftComposite());
		createTypeInfoSection(getLeftComposite());
		createConnectionDisplaySection(getRightComposite());
	}

	private void createConnectionDisplaySection(final Composite parent) {

		connectionSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		connectionSection.setText(Messages.InterfaceElementSection_ConnectionGroup);
		connectionSection.setLayout(new GridLayout(1, false));
		connectionSection
		.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).create());

		final Composite composite = getWidgetFactory().createComposite(connectionSection);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		deleteButton = new AddDeleteWidget();
		deleteButton.createControls(composite, getWidgetFactory());
		deleteButton.setVisibleCreateButton(false);

		connectionsViewer = createConnectionsViewer(composite);

		deleteButton.bindToTableViewer(connectionsViewer, this, ref -> null,
				ref -> new DeleteConnectionCommand((Connection) ref));

		connectionSection.setClient(composite);
	}

	private TableViewer createConnectionsViewer(final Composite parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.setColumnProperties(new String[] { TARGET, PIN, COMMENT });
		viewer.setCellModifier(new ConnectionCellModifier(viewer));
		viewer.setCellEditors(new CellEditor[] { null, null, new CustomTextCellEditor(viewer.getTable()) });
		viewer.setLabelProvider(new ConnectionTableLabelProvider());
		viewer.setContentProvider(new ConnectionContentProvider());
		return viewer;
	}

	private static TableLayout createTableLayout(final Table table) {
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

	private void createTypeInfoSection(final Composite parent) {
		// textfields in this section without a button need to span 2 cols so that all
		// textfields are aligned

		final Section typeInfoSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		typeInfoSection.setText(FordiacMessages.TypeInfo + ":"); //$NON-NLS-1$
		typeInfoSection.setLayout(new GridLayout(1, false));
		typeInfoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = getWidgetFactory().createComposite(typeInfoSection);

		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, false);
		commentText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Type + ":"); //$NON-NLS-1$

		typeText = createGroupText(composite, false);

		openEditorButton = new Button(typeText.getParent(), SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorButton.addListener(SWT.Selection, ev -> OpenStructMenu
				.openStructEditor(((VarDeclaration) getType()).getType().getPaletteEntry().getFile()));

		parameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.DefaultValue + ":"); //$NON-NLS-1$
		parameterText = createGroupText(composite, false);
		parameterText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		typeInfoSection.setClient(composite);

	}

	private void createInstanceInfoSection(final Composite parent) {
		infoSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		infoSection.setLayout(new GridLayout(1, false));
		infoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = getWidgetFactory().createComposite(infoSection);

		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		currentParameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		currentParameterText = createGroupText(composite, true);
		currentParameterText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeValueCommand((VarDeclaration) getType(), currentParameterText.getText()));
			addContentAdapter();
		});

		infoSection.setClient(composite);

	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;

		if (null != type) {
			refreshParameterVisibility();
			final FBNetworkElement fb = getType().getFBNetworkElement();
			if (fb != null) {
				infoSection.setText(
						MessageFormat.format(Messages.InterfaceElementSection_Instance, fb.getName(), getPinName()));
			} else { // e.g., IP address of device
				infoSection.setText(Messages.InterfaceElementSection_InterfaceElement);
			}
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			String itype = ""; //$NON-NLS-1$

			openEditorButton.setEnabled(
					(getType().getType() instanceof StructuredType) || (getType().getType() instanceof AdapterType));

			if (getType() instanceof VarDeclaration) {
				itype = setParameterAndType();
			} else {
				itype = FordiacMessages.Event;
			}
			typeText.setText(itype);

			refreshConnectionsViewer();

			if (fb != null) {
				setEditable(!fb.isContainedInTypedInstance());
			}
		}

		commandStack = commandStackBuffer;
	}

	private Object getPinName() {
		return getType().getName() != null ? getType().getName() : ""; //$NON-NLS-1$
	}

	private void refreshParameterVisibility() {
		final boolean isDataIO = (getType() instanceof VarDeclaration) && !(getType() instanceof AdapterDeclaration);
		parameterTextCLabel.setVisible(isDataIO);
		parameterText.setVisible(isDataIO);
		currentParameterTextCLabel.setVisible(isDataIO && getType().isIsInput());
		currentParameterText.setVisible(isDataIO && getType().isIsInput());
	}

	private void refreshConnectionsViewer() {
		if (getType().isIsInput()) {
			connectionSection.setText(Messages.InterfaceElementSection_InConnections);
		} else {
			connectionSection.setText(Messages.InterfaceElementSection_OutConnections);
		}

		connectionsViewer.setInput(getType());
	}

	private void setEditable(final boolean editable) {
		currentParameterText.setEditable(editable);
		currentParameterText.setEnabled(editable);
		deleteButton.setVisibleDeleteButton(editable);
	}

	protected String setParameterAndType() {
		String itype;
		final VarDeclaration varDecl = (VarDeclaration) getType();
		itype = varDecl.getType() != null ? varDecl.getType().getName() : ""; //$NON-NLS-1$
		if (varDecl.isIsInput() && (varDecl.getFBNetworkElement() != null)) {
			final FBType fbType = varDecl.getFBNetworkElement().getType();
			if (null != fbType) {
				final IInterfaceElement ie = fbType.getInterfaceList()
						.getInterfaceElement(varDecl.getName());
				if (ie instanceof VarDeclaration) {
					parameterText.setText(
							getValueFromVarDecl((VarDeclaration) ie));
					if (varDecl.getType() instanceof StructuredType) {
						itype = getStructTypes((StructuredType) getType().getType());
					}
				}
			}
		}
		currentParameterText.setText(getValueFromVarDecl(varDecl));
		return itype;
	}

	private static String getValueFromVarDecl(final VarDeclaration varDecl) {
		return (varDecl.getValue() != null) ? varDecl.getValue().getValue() : ""; //$NON-NLS-1$
	}

	// this method will be removed as soon as there is a toString for StructType in
	// the model
	private static String getStructTypes(final StructuredType st) {
		final EList<VarDeclaration> list = st.getMemberVariables();
		final StringBuilder sb = new StringBuilder();
		sb.append(st.getName());
		sb.append(": ("); //$NON-NLS-1$
		boolean printString = false;
		for (final VarDeclaration v : list) {
			if ((v.getType() != null)) {
				sb.append(v.getType().getName());
				printString = true;
			} else {
				sb.append("not set");
			}
			sb.append(", "); //$NON-NLS-1$
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(')');

		return printString ? sb.toString() : ""; //$NON-NLS-1$
	}

	@Override
	protected void setInputCode() {
		connectionsViewer.setInput(null);
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof InterfaceEditPart) {
			return ((InterfaceEditPart) input).getModel();
		} else if (input instanceof ValueEditPart) {
			return ((ValueEditPart) input).getModel().getVarDeclaration();
		}
		return null;
	}

	@Override
	protected void setInputInit() {
		// no implementation needed
	}

	private static class ConnectionContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof IInterfaceElement) {
				final IInterfaceElement element = ((IInterfaceElement) inputElement);
				if ((element.isIsInput() && (null != element.getFBNetworkElement()))
						|| (!element.isIsInput() && (null == element.getFBNetworkElement()))) {
					return element.getInputConnections().toArray();
				}
				return element.getOutputConnections().toArray();
			}
			return new Object[] {};
		}
	}

	private class ConnectionTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public static final int TARGET_COL_INDEX = 0;
		public static final int PIN_COL_INDEX = 1;
		public static final int COMMENT_COL_INDEX = 2;

		AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(getAdapterFactory());

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			if (element instanceof Connection) {
				final Connection con = ((Connection) element);
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
			if (element instanceof Connection) {
				final Connection con = ((Connection) element);
				final IInterfaceElement ie = getInterfaceElement(con);
				if (null != ie) {
					switch (columnIndex) {
					case TARGET_COL_INDEX:
						if (null != ie.getFBNetworkElement()) {
							return ie.getFBNetworkElement().getName();
						} else if (ie.eContainer().eContainer() instanceof CompositeFBType) {
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

		private IInterfaceElement getInterfaceElement(final Connection con) {
			return (getType().equals(con.getSource())) ? con.getDestination() : con.getSource();
		}
	}

	private class ConnectionCellModifier implements ICellModifier {
		private final TableViewer viewer;

		public ConnectionCellModifier(final TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return COMMENT.equals(property);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (COMMENT.equals(property)) {
				final Connection con = (Connection) element;
				return con.getComment() != null ? con.getComment() : ""; //$NON-NLS-1$
			}
			return null;
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Object data = tableItem.getData();

			if (COMMENT.equals(property)) {
				final Connection con = (Connection) data;
				executeCommand(new ChangeCommentCommand(con, value.toString()));
				viewer.refresh(data);
			}
		}
	}

}
