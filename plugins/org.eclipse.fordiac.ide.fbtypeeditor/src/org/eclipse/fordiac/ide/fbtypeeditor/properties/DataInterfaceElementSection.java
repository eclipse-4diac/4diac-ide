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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.EventContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.EventLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DataInterfaceElementSection extends AdapterInterfaceElementSection {
	private Text arraySizeText;
	private Text initValueText;
	private CheckboxTableViewer withEventsViewer;
	private Table tableWith;
	private Group eventComposite;
	
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createDataSection(leftComposite);	
		createEventSection(rightComposite);
	}
	
	private void createDataSection(Composite parent) {
		getWidgetFactory().createCLabel(parent, "Array Size:"); 
		arraySizeText = createGroupText(parent, true);		
		arraySizeText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeArraySizeCommand((VarDeclaration)type, arraySizeText.getText()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(parent, "Initial Value:"); 
		initValueText = createGroupText(parent, true);
		initValueText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeInitialValueCommand((VarDeclaration)type, initValueText.getText()));
				addContentAdapter();
			}
		});
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
		column1.setText("Event");
		TableColumn column2 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column2.setText("DataType"); 
		TableColumn column3 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column3.setText("Comment");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 100));
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(20, 100));		
		tableWith.setLayout(layout);
		withEventsViewer.setContentProvider(new EventContentProvider());
		withEventsViewer.setLabelProvider(new EventLabelProvider());
		tableWith.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final org.eclipse.swt.widgets.Event event) {
				if (event.detail == SWT.CHECK) {
					TableItem checkedItem = (TableItem) event.item;
					Event e = (Event) checkedItem.getData();
					if (checkedItem.getChecked()) {
						for (With with : e.getWith()) {
							if (with.getVariables().equals(type)) {
								return;
							}
						}
						WithCreateCommand cmd = new WithCreateCommand();
						cmd.setEvent(e);
						cmd.setVarDeclaration((VarDeclaration)type);
						executeCommand(cmd);
					} else {
						for (With with : e.getWith()) {
							if (with.getVariables().equals(type)) {
								executeCommand(new DeleteWithCommand(with));
								break;
							}
						}
					}
				}
			}
		});
	}
	
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);	
		//hide with part for sub app type events
		eventComposite.setVisible(!(getType().eContainer().eContainer() instanceof SubAppType));
		if(null == commandStack){ //disable all field
			arraySizeText.setEnabled(false);
			initValueText.setEnabled(false);
			withEventsViewer.setInput(null);
			withEventsViewer.setAllGrayed(true);
		}
	}
	
	@Override
	protected VarDeclaration getType() {
		return (VarDeclaration)type;
	}
	
	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			arraySizeText.setText(0 >= getType().getArraySize() ? "" : (Integer.toString((getType()).getArraySize()))); //$NON-NLS-1$
			initValueText.setText(null == getType().getVarInitialization() ? "" : getType().getVarInitialization().getInitialValue()); //$NON-NLS-1$
			if(getType().eContainer().eContainer() instanceof FBType){
				eventComposite.setVisible(true);
				withEventsViewer.setAllChecked(false);
				for(Iterator<With> iterator = getType().getWiths().iterator(); iterator.hasNext();){
					With with = iterator.next();
					if (with.getVariables() != null) {
						withEventsViewer.setChecked(with.eContainer(), true);
					}
				}
				withEventsViewer.setInput(getType());		
			}else{
				eventComposite.setVisible(false);
			}
		}
		commandStack = commandStackBuffer;
	}
	
	@Override
	protected Collection<DataType> getTypes() {
		return DataTypeLibrary.getInstance().getDataTypesSorted();
	}
}
