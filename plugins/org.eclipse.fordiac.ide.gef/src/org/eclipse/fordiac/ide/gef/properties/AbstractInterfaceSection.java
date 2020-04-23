/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH, Profactor GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - clean-up and extracting functionality
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFBNetworkElementName;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractInterfaceSection extends AbstractSection {
	protected Text nameText;
	protected Text commentText;
	private TableViewer inputViewer;
	private static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	private static final String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	private static final String COMMENT_PROPERTY = "comment"; //$NON-NLS-1$

	public AbstractInterfaceSection() {
	}

	@Override
	protected INamedElement getType() {
		if (type instanceof FBNetworkElement) {
			return (FBNetworkElement) type;
		}
		if (type instanceof Device) {
			return (Device) type;
		}
		if (type instanceof Resource) {
			return (Resource) type;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createFBInfoGroup(parent);
		createInputInfoGroup(parent);
	}

	protected void createFBInfoGroup(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.InstanceName + ":"); //$NON-NLS-1$
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(getRenameCommand(nameText.getText()));
			addContentAdapter();
		});
		getWidgetFactory().createCLabel(composite, FordiacMessages.InstanceComment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	private void createInputInfoGroup(Composite parent) {
		Group inputGroup = getWidgetFactory().createGroup(parent, FordiacMessages.Inputs);
		inputGroup.setLayout(new GridLayout(1, false));
		inputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		inputViewer = TableWidgetFactory.createPropertyTableViewer(inputGroup, 0);
		configureTableLayout(inputViewer.getTable());

		inputViewer.setContentProvider(new InputContentProvider());
		inputViewer.setLabelProvider(new InputLabelProvider());
		inputViewer.setCellModifier(new ValueCommentCellModifier());
	}

	private void configureTableLayout(final Table table) {
		TableColumn column1 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column1.setText(NAME_PROPERTY);
		TableColumn column2 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column2.setText(VALUE_PROPERTY);
		TableColumn column3 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column3.setText(COMMENT_PROPERTY);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		table.setLayout(layout);
		inputViewer.setCellEditors(
				new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		inputViewer.setColumnProperties(new String[] { NAME_PROPERTY, VALUE_PROPERTY, COMMENT_PROPERTY });
	}

	private static String getVarDeclarationValue(VarDeclaration v) {
		return (v.getValue() != null) ? v.getValue().getValue() : ""; //$NON-NLS-1$
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		commandStack = getCommandStack(part, input);
		if (null == commandStack) { // disable all fields
			nameText.setEnabled(false);
			commentText.setEnabled(false);
			inputViewer.setCellModifier(null);
		}
		setType(input);
	}

	@Override
	public void refresh() {
		if (null != type) {
			Display.getDefault().asyncExec(() -> {
				if (!nameText.isDisposed() && !nameText.getParent().isDisposed()) {
					CommandStack commandStackBuffer = commandStack;
					commandStack = null;
					if (type instanceof AdapterFB) {
						nameText.setEnabled(false);
					}
					nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
					commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
					inputViewer.setInput(getType());
					commandStack = commandStackBuffer;
				}
			});
		}
	}

	private ChangeNameCommand getRenameCommand(String newValue) {
		INamedElement element = getType();
		if (element instanceof FBNetworkElement) {
			return new ChangeFBNetworkElementName((FBNetworkElement) element, newValue);
		}
		return new ChangeNameCommand(getType(), nameText.getText());
	}

	private class ValueCommentCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return (VALUE_PROPERTY.equals(property) || COMMENT_PROPERTY.equals(property));
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case VALUE_PROPERTY:
				return getVarDeclarationValue((VarDeclaration) element);
			case COMMENT_PROPERTY:
				return ((INamedElement) element).getComment() != null ? ((INamedElement) element).getComment() : ""; //$NON-NLS-1$
			default:
				return null;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			Object data = tableItem.getData();
			Command cmd = null;
			switch (property) {
			case VALUE_PROPERTY:
				cmd = new ChangeValueCommand((VarDeclaration) data, value.toString());
				break;
			case COMMENT_PROPERTY:
				cmd = new ChangeCommentCommand((INamedElement) data, value.toString());
				break;
			default:
				break;
			}
			if ((null != cmd) && (null != commandStack)) {
				executeCommand(cmd);
				inputViewer.refresh(data);
			}

		}
	}

	public static class InputContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				return ((FBNetworkElement) inputElement).getInterface().getInputVars().toArray();
			}
			if (inputElement instanceof Device) {
				return ((Device) inputElement).getVarDeclarations().toArray();
			}
			if (inputElement instanceof Resource) {
				return ((Resource) inputElement).getVarDeclarations().toArray();
			}
			return new Object[] {};
		}
	}

	public static class InputLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof VarDeclaration) {
				switch (columnIndex) {
				case 0:
					return ((VarDeclaration) element).getName();
				case 1:
					return getVarDeclarationValue((VarDeclaration) element);
				case 2:
					return ((VarDeclaration) element).getComment() != null ? ((VarDeclaration) element).getComment()
							: ""; //$NON-NLS-1$
				default:
					break;
				}
			}
			return element.toString();
		}
	}

	@Override
	protected void setInputInit() {
		// currently nothing to do here
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}
}
