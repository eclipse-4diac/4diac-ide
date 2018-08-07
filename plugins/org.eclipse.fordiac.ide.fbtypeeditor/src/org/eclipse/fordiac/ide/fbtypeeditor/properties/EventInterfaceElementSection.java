/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarDeclarationLabelProvider;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EventInterfaceElementSection extends AdapterInterfaceElementSection {
	
	private CheckboxTableViewer withEventsViewer;
	private Table tableWith;
	private Group eventComposite;
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createEventSection(rightComposite);
	}
	
	private void createEventSection(Composite parent) {
		eventComposite = getWidgetFactory().createGroup(parent, "With");
		eventComposite.setLayout(new GridLayout(1, false));	
		eventComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));		
		withEventsViewer = CheckboxTableViewer.newCheckList(eventComposite, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.FILL);
		GridData gridDataVersionViewer = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataVersionViewer.minimumHeight = 80;
		gridDataVersionViewer.widthHint = 400;
		withEventsViewer.getControl().setLayoutData(gridDataVersionViewer);
		tableWith = withEventsViewer.getTable();		
		tableWith.setLinesVisible(false);		
		tableWith.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column1.setText("Variable");
		TableColumn column2 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column2.setText("DataType"); 
		TableColumn column3 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column3.setText("Comment");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 100));
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(20, 100));
		tableWith.setLayout(layout);
		withEventsViewer.setContentProvider(new VarContentProvider());
		withEventsViewer.setLabelProvider(new VarDeclarationLabelProvider());
		tableWith.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final org.eclipse.swt.widgets.Event event) {
				if (event.detail == SWT.CHECK) {
					TableItem checkedItem = (TableItem) event.item;
					VarDeclaration variable = (VarDeclaration) checkedItem.getData();
					if (checkedItem.getChecked()) {
						for (With with : variable.getWiths()) {
							if (with.eContainer().equals(type)) {
								return;
							}
						}
						WithCreateCommand cmd = new WithCreateCommand();
						cmd.setEvent((Event)type);
						cmd.setVarDeclaration(variable);
						executeCommand(cmd);
					} else {
						for (With with : variable.getWiths()) {
							if (with.eContainer().equals(type)) {
								executeCommand(new DeleteWithCommand(with));
								break;
							}
						}
					}
				}

			}
		});
	}
	
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		//hide with part for sub app type events
		eventComposite.setVisible(!(getType().eContainer().eContainer() instanceof SubAppType));
		if(null == commandStack){ //disable all field
			withEventsViewer.setInput(null);
			withEventsViewer.setAllGrayed(true);
		}
	}
	
	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {			
			withEventsViewer.setAllChecked(false);
			for(Iterator<With> iterator = ((Event)getType()).getWith().iterator(); iterator.hasNext();){
				With with = iterator.next();
				if (with.getVariables() != null) {
					withEventsViewer.setChecked(with.getVariables(), true);
				}
			}
			withEventsViewer.setInput(getType());
		}
		commandStack = commandStackBuffer;
	}
	
	@Override
	public void setTypeDropdown(){
		typeCombo.removeAll();
		typeCombo.add("Event");
		typeCombo.select(0);
	}
	
	@Override
	protected DataType getTypeForSelection(String text) {
		// currently we only have one kind of data type therefore we will return null here so that it is not changed
		return null;
	}
}
