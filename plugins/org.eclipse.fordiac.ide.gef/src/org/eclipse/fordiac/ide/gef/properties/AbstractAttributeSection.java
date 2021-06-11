/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *               - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AttributeCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.AttributeDeleteCommand;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractAttributeSection extends AbstractSection {
	private TableViewer attributeViewer;
	private static final String NAME_COL = "name"; //$NON-NLS-1$
	private static final String VALUE_COL = "value"; //$NON-NLS-1$
	private static final String TYPE_COL = "type"; //$NON-NLS-1$
	private static final String COMMENT_COL = "comment"; //$NON-NLS-1$
	private Button attributeNew;
	private Button attributeDelete;

	@Override
	protected abstract ConfigurableObject getInputType(Object input);

	@Override
	protected abstract EObject getType();

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createInputInfoGroup(parent);
		createNewDeleteButton(parent);
	}

	private void createNewDeleteButton(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
		attributeNew = getWidgetFactory().createButton(composite, "", SWT.PUSH); //$NON-NLS-1$
		attributeNew.setToolTipText(Messages.AbstractAttributeSection_CreateAttribute);
		attributeNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		attributeNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				if (type instanceof ConfigurableObject) {
					executeCommand(new AttributeCreateCommand((ConfigurableObject) type));
					attributeViewer.refresh();
				}
			}
		});
		attributeDelete = getWidgetFactory().createButton(composite, "", SWT.PUSH); //$NON-NLS-1$
		attributeDelete.setToolTipText(Messages.AbstractAttributeSection_DeleteSelectedAttribute);
		attributeDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		attributeDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				executeCommand(new AttributeDeleteCommand((ConfigurableObject) type,
						(Attribute) ((IStructuredSelection) attributeViewer.getSelection()).getFirstElement()));
				attributeViewer.refresh();
			}
		});
	}

	private static String[] getDataTypes() {
		final List<BaseType1> list = Arrays.asList(BaseType1.values());
		// collects names of all base data types into a String array
		return list.stream().map(BaseType1::getName).collect(Collectors.toList()).toArray(new String[0]);
	}

	private void createInputInfoGroup(final Composite parent) {
		attributeViewer = TableWidgetFactory.createPropertyTableViewer(parent, 0);

		configureTableLayout();
		final Table table = attributeViewer.getTable();

		attributeViewer.setContentProvider(new InputContentProvider());
		attributeViewer.setLabelProvider(new InputLabelProvider());
		attributeViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table),
				ComboBoxWidgetFactory.createComboBoxCellEditor(table, getDataTypes(), SWT.READ_ONLY),
				new TextCellEditor(table, SWT.MULTI | SWT.V_SCROLL), new TextCellEditor(table) });
		attributeViewer.setColumnProperties(new String[] { NAME_COL, TYPE_COL, VALUE_COL, COMMENT_COL });
		attributeViewer.setCellModifier(new AttributeCellModifier());
	}

	private void configureTableLayout() {
		final TableColumn column1 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column1.setText(NAME_COL);
		final TableColumn column2 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column2.setText(TYPE_COL);
		final TableColumn column3 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column3.setText(VALUE_COL);
		final TableColumn column4 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column4.setText(COMMENT_COL);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		attributeViewer.getTable().setLayout(layout);
	}

	@Override
	public void refresh() {
		super.refresh();
		if (null != type) {
			attributeViewer.setInput(getType());
		}
	}

	@Override
	protected void setInputCode() {
		attributeViewer.setCellModifier(null);
		attributeDelete.setEnabled(false);
		attributeNew.setEnabled(false);
	}

	@Override
	protected void setInputInit() {
		// nothing to do
	}

	public class AttributeCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return !(element instanceof Attribute && TYPE_COL.equals(property)
					&& null != ((Attribute) element).getAttributeDeclaration());
		}

		@Override
		public Object getValue(final Object element, final String property) {
			switch (property) {
			case NAME_COL:
				return ((Attribute) element).getName();
			case VALUE_COL:
				return ((Attribute) element).getValue();
			case TYPE_COL:
				return Integer.valueOf(((Attribute) element).getType().getValue());
			case COMMENT_COL:
				return ((Attribute) element).getComment();
			default:
				return null;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final Attribute data = (Attribute) ((TableItem) element).getData();
			AttributeChangeCommand cmd = null;
			switch (property) {
			case NAME_COL:
				cmd = new AttributeChangeCommand(data, value.toString(), null, null, null);
				break;
			case VALUE_COL:
				cmd = new AttributeChangeCommand(data, null, value.toString(), null, null);
				break;
			case TYPE_COL:
				cmd = new AttributeChangeCommand(data, null, null, BaseType1.get(((Integer) value).intValue()), null);
				break;
			case COMMENT_COL:
				cmd = new AttributeChangeCommand(data, null, null, null, value.toString());
				break;
			default:
			}
			executeCommand(cmd);
			attributeViewer.refresh(data);
		}
	}

	public static class InputContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof Application) {
				return ((Application) inputElement).getAttributes().toArray();
			}
			if (inputElement instanceof Device) {
				return ((Device) inputElement).getAttributes().toArray();
			}
			if (inputElement instanceof Segment) {
				return ((Segment) inputElement).getAttributes().toArray();
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
			if (element instanceof Attribute) {
				switch (columnIndex) {
				case 0:
					return ((Attribute) element).getName();
				case 1:
					return ((Attribute) element).getType().getName();
				case 2:
					return ((Attribute) element).getValue();
				case 3:
					return ((Attribute) element).getComment() != null ? ((Attribute) element).getComment() : ""; //$NON-NLS-1$
				default:
					break;
				}
			}
			return element.toString();
		}
	}

}
