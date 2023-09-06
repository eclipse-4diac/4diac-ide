/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2021 Johannes Kepler University Linz
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
 *   Melanie Winter - buttons are created with AddDeleteWidget
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AttributeCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.AttributeDeleteCommand;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AttributeSection extends AbstractSection {
	private TableViewer attributeViewer;
	private static final String NAME_COL = "name"; //$NON-NLS-1$
	private static final String VALUE_COL = "value"; //$NON-NLS-1$
	private static final String TYPE_COL = "type"; //$NON-NLS-1$
	private static final String COMMENT_COL = "comment"; //$NON-NLS-1$

	@Override
	protected ConfigurableObject getInputType(final Object input) {
		return AttributeFilter.parseObject(input) instanceof final ConfigurableObject configurableObject
				? configurableObject
				: null;
	}

	@Override
	protected ConfigurableObject getType() {
		return type instanceof final ConfigurableObject configurableObject ? configurableObject : null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		final AddDeleteWidget buttons = createNewDeleteButton(parent);
		createInputInfoGroup(parent);
		configureButtonList(buttons, attributeViewer);
	}

	private AddDeleteWidget createNewDeleteButton(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));

		final AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(composite, getWidgetFactory());
		return buttons;
	}

	private void configureButtonList(final AddDeleteWidget buttons, final TableViewer attributeViewer) {
		buttons.bindToTableViewer(attributeViewer, this, ref -> newCreateCommand(getType(), (Attribute) ref),
				ref -> newDeleteCommand(getType(), (Attribute) ref));
	}

	private static AttributeCreateCommand newCreateCommand(final ConfigurableObject object, final Attribute ref) {
		return new AttributeCreateCommand(object, ref);
	}

	private static Command newDeleteCommand(final ConfigurableObject object, final Attribute ref) {
		return new AttributeDeleteCommand(object, ref);
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

		attributeViewer.setContentProvider(ArrayContentProvider.getInstance());
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
			attributeViewer.setInput(getType().getAttributes());
		}
	}

	@Override
	protected void setInputCode() {
		attributeViewer.setCellModifier(null);
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
			return switch (property) {
			case NAME_COL -> ((Attribute) element).getName();
			case VALUE_COL -> ((Attribute) element).getValue();
			case TYPE_COL -> Integer.valueOf(((Attribute) element).getType().getValue());
			case COMMENT_COL -> ((Attribute) element).getComment();
			default -> null;
			};
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final Attribute data = (Attribute) ((TableItem) element).getData();
			final AttributeChangeCommand cmd = switch (property) {
			case NAME_COL -> new AttributeChangeCommand(data, value.toString(), null, null, null);
			case VALUE_COL -> new AttributeChangeCommand(data, null, value.toString(), null, null);
			case TYPE_COL ->
				new AttributeChangeCommand(data, null, null, BaseType1.get(((Integer) value).intValue()), null);
			case COMMENT_COL -> new AttributeChangeCommand(data, null, null, null, value.toString());
			default -> null;
			};

			executeCommand(cmd);
			attributeViewer.refresh(data);
		}
	}

	public static class InputLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final Attribute attribute) {
				return switch (columnIndex) {
				case 0 -> attribute.getName();
				case 1 -> attribute.getType().getName();
				case 2 -> attribute.getValue();
				case 3 -> attribute.getComment() != null ? attribute.getComment() : ""; //$NON-NLS-1$
				default -> element.toString();
				};
			}
			return element.toString();
		}
	}
}
