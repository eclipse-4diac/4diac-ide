/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AttributeSection extends AbstractSection {
	private TableViewer attributeViewer;
	private final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	private final String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	private final String COMMENT_PROPERTY = "comment"; //$NON-NLS-1$
	
	protected Device getInputType(Object input) {
		if(input instanceof DeviceEditPart){
			return ((DeviceEditPart) input).getModel();
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);	
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createInputInfoGroup(parent);
	}
	
	private void createInputInfoGroup(Composite parent) {		
		attributeViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.FILL);
		GridData gridDataVersionViewer = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataVersionViewer.minimumHeight = 80;
		gridDataVersionViewer.widthHint = 400;
		attributeViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = attributeViewer.getTable();		
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column1.setText(NAME_PROPERTY);
		TableColumn column2 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column2.setText(VALUE_PROPERTY); 
		TableColumn column3 = new TableColumn(attributeViewer.getTable(), SWT.LEFT);
		column3.setText(COMMENT_PROPERTY);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		table.setLayout(layout);		
		attributeViewer.setContentProvider(new InputContentProvider());
		attributeViewer.setLabelProvider(new InputLabelProvider());		
		attributeViewer.setCellEditors(new CellEditor[] {new TextCellEditor(table), new TextCellEditor(table, SWT.MULTI | SWT.V_SCROLL), new TextCellEditor(table)});
		attributeViewer.setColumnProperties(new String[] {NAME_PROPERTY, VALUE_PROPERTY, COMMENT_PROPERTY});
	}
	
	@Override
	public void refresh() {
		super.refresh();
		if(null != type) {
			attributeViewer.setInput(getType());
		}
	}

	@Override
	protected EObject getType() {
		if(type instanceof Device){
			return (Device) type;
		}
		return null;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
		}
		return null;
	}

	@Override
	protected void setInputCode() {	
	}
	
	@Override
	protected void setInputInit() {
	}
	
	public class InputContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if(inputElement instanceof Device){
				return ((Device)inputElement).getAttributes().toArray();
			}
			return new Object[] {};
		}
	}
	
	public class InputLabelProvider extends LabelProvider implements ITableLabelProvider {

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
					return ((Attribute) element).getValue();
				case 2:
					return ((Attribute) element).getComment() != null ? ((Attribute) element).getComment() : ""; //$NON-NLS-1$
				default:
					break;
				}
			}
			return element.toString();
		}
	}
}
