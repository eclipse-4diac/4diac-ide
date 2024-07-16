/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Profactor GmbH,
 *                          Johannes Kepler University Linz
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
import org.eclipse.swt.custom.CLabel;
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

public abstract class AbstractInterfaceSection extends AbstractDoubleColumnSection {
	protected Text nameText;
	protected Text commentText;
	private TableViewer inputViewer;
	private static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	private static final String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	private static final String COMMENT_PROPERTY = "comment"; //$NON-NLS-1$

	protected AbstractInterfaceSection() {
	}

	@Override
	protected INamedElement getType() {
		if (type instanceof final FBNetworkElement fbnEl) {
			return fbnEl;
		}
		if (type instanceof final Device dev) {
			return dev;
		}
		if (type instanceof final Resource res) {
			return res;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createFBInfoGroup(parent);
		createInputInfoGroup(parent);
	}

	protected void createFBInfoGroup(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.InstanceName + ":"); //$NON-NLS-1$
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
			addContentAdapter();
		});

		final CLabel commentLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.InstanceComment + ":"); //$NON-NLS-1$
		commentLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		commentText = createGroupText(composite, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		final GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.heightHint = 3 * commentText.getLineHeight();
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

	}

	private void createInputInfoGroup(final Composite parent) {
		final Group inputGroup = getWidgetFactory().createGroup(parent, FordiacMessages.Inputs);
		inputGroup.setLayout(new GridLayout(1, false));
		inputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		inputViewer = TableWidgetFactory.createPropertyTableViewer(inputGroup, 0);
		configureTableLayout(inputViewer.getTable());

		inputViewer.setContentProvider(new InputContentProvider());
		inputViewer.setLabelProvider(new InputLabelProvider());
		inputViewer.setCellModifier(new ValueCommentCellModifier());
	}

	private void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column1.setText(FordiacMessages.Name);
		final TableColumn column2 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column2.setText(FordiacMessages.InitialValue);
		final TableColumn column3 = new TableColumn(inputViewer.getTable(), SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		table.setLayout(layout);
		inputViewer.setCellEditors(
				new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		inputViewer.setColumnProperties(new String[] { NAME_PROPERTY, VALUE_PROPERTY, COMMENT_PROPERTY });
	}

	private static String getVarDeclarationValue(final VarDeclaration v) {
		return (v.getValue() != null) ? v.getValue().getValue() : ""; //$NON-NLS-1$
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		final Object input = ((IStructuredSelection) selection).getFirstElement();
		setCurrentCommandStack(part, input);
		if (null == getCurrentCommandStack()) {
			disableAllFields();
		}
		setType(input);
	}

	protected void disableAllFields() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
		inputViewer.setCellModifier(null);
	}

	@Override
	protected void performRefresh() {
		Display.getDefault().asyncExec(() -> {
			if (!nameText.isDisposed() && !nameText.getParent().isDisposed()) {
				final CommandStack commandStackBuffer = getCurrentCommandStack();
				setCurrentCommandStack(null);
				if (type instanceof AdapterFB) {
					nameText.setEnabled(false);
				}
				nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
				commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
				inputViewer.setInput(getType());
				setCurrentCommandStack(commandStackBuffer);
			}
		});
	}

	private class ValueCommentCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return (VALUE_PROPERTY.equals(property) || COMMENT_PROPERTY.equals(property));
		}

		@Override
		public Object getValue(final Object element, final String property) {
			return switch (property) {
			case VALUE_PROPERTY -> getVarDeclarationValue((VarDeclaration) element);
			case COMMENT_PROPERTY ->
				((INamedElement) element).getComment() != null ? ((INamedElement) element).getComment() : ""; //$NON-NLS-1$
			default -> null;
			};
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final Object data = tableItem.getData();
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
			if ((null != cmd) && (null != getCurrentCommandStack())) {
				executeCommand(cmd);
				inputViewer.refresh(data);
			}

		}
	}

	public static class InputContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof final FBNetworkElement fbnEl) {
				return fbnEl.getInterface().getInputVars().toArray();
			}
			if (inputElement instanceof final Device dev) {
				return dev.getVarDeclarations().toArray();
			}
			if (inputElement instanceof final Resource res) {
				return res.getVarDeclarations().toArray();
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
			if (element instanceof final VarDeclaration varDecl) {
				switch (columnIndex) {
				case 0:
					return varDecl.getName();
				case 1:
					return getVarDeclarationValue(varDecl);
				case 2:
					return varDecl.getComment() != null ? varDecl.getComment() : ""; //$NON-NLS-1$
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
