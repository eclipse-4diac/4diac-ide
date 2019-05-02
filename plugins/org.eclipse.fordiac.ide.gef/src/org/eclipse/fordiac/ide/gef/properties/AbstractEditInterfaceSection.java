/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH 
 * 				 2018, 2019 Johannes Kepler University Linz
 * 	
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Monika Wenger, Alois Zoitl - initial implementation
 * Alois Zoitl - moved group buttons to the top left, code clean-up
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceSection extends AbstractSection {
	protected TableViewer inputsViewer;	
	protected TableViewer outputsViewer;
	protected Table inputsTable;
	protected Table outputsTable;
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String TYPE = "type"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	protected enum InterfaceContentProviderType{
		EVENT, DATA, ADAPTER
	}
	
	protected abstract CreateInterfaceElementCommand newCreateCommand(boolean isInput);
	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);
	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean isInput, boolean moveUp);
	protected abstract String[] fillTypeCombo();
	protected abstract INamedElement getInputType(Object input);	

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
		}
		return null;
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createInputEdit(parent);
		createOutputEdit(parent);
	}
	
	private static void createTableLayout(Table table){
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(NAME);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(TYPE); 
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(COMMENT);
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(100));
		layout.addColumnData(new ColumnPixelData(100));
		table.setLayout(layout);
	}
	
	private void createInputEdit(Composite parent){
		Group inputsGroup = getWidgetFactory().createGroup(parent, "Inputs"); //$NON-NLS-1$
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite buttonListContainer = new Composite(inputsGroup, SWT.NONE);  // this has to be done here so that it is in the first column
		inputsViewer = new TableViewer(inputsGroup, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.FILL);
		inputsViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		inputsTable = inputsViewer.getTable();
		createTableLayout(inputsTable);
		inputsViewer.setColumnProperties(new String[] {NAME, TYPE, COMMENT});
		inputsViewer.setCellModifier(new InterfaceCellModifier(inputsViewer));
		inputsViewer.setLabelProvider(new InterfaceLabelProvider());
		createButtonList(buttonListContainer, inputsViewer, true);
	}
	
	private void createButtonList(Composite container, TableViewer viewer, boolean inputs) {		
		GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		container.setLayoutData(buttonCompLayoutData);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		
		Button createButton = getWidgetFactory().createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		createButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createButton.setToolTipText("Create interface element"); //$NON-NLS-1$
		createButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				CreateInterfaceElementCommand cmd = newCreateCommand(inputs);
				executeCommand(cmd);
				viewer.refresh();
			}
		});
		
		Button upButton = getWidgetFactory().createButton(container, "", SWT.ARROW | SWT.UP); //$NON-NLS-1$
		upButton.setToolTipText("Move interface element up"); //$NON-NLS-1$
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
				if(selection instanceof IInterfaceElement){
					ChangeInterfaceOrderCommand cmd = newOrderCommand((IInterfaceElement) selection, true, true);
					executeCommand(cmd);
					viewer.refresh();
				}
			}
		});
		
		Button downButton = getWidgetFactory().createButton(container, "", SWT.ARROW | SWT.DOWN); //$NON-NLS-1$
		downButton.setToolTipText("Move interface element down"); //$NON-NLS-1$
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
				if(selection instanceof IInterfaceElement){
					ChangeInterfaceOrderCommand cmd = newOrderCommand((IInterfaceElement) selection, true, false);
					executeCommand(cmd);
					viewer.refresh();
				}
			}
		});
		
		Button deleteButton = getWidgetFactory().createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		deleteButton.setToolTipText("Delete selected interface element"); //$NON-NLS-1$
		deleteButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
				if(selection instanceof IInterfaceElement){
					DeleteInterfaceCommand cmd = newDeleteCommand((IInterfaceElement) selection);
					executeCommand(cmd);
					viewer.refresh();
				}
			}
		});
		
		viewer.addSelectionChangedListener( ev -> 
			setButtonEnablement(upButton, downButton, deleteButton, !viewer.getSelection().isEmpty()) );
		
		//initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(upButton, downButton, deleteButton, false);
	}
	
	private static void setButtonEnablement(Button upButton, Button downButton, Button deleteButton,
			boolean somethingSelected) {
		upButton.setEnabled(somethingSelected);
		downButton.setEnabled(somethingSelected);
		deleteButton.setEnabled(somethingSelected);
		deleteButton.setImage((somethingSelected) ? 
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE) :
					PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}
	
	protected void setCellEditors(){
		inputsViewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(inputsTable), 
				new ComboBoxCellEditor(inputsTable, fillTypeCombo(), SWT.READ_ONLY), 
				new TextCellEditor(inputsTable)});
		outputsViewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(outputsTable), 
				new ComboBoxCellEditor(outputsTable, fillTypeCombo(), SWT.READ_ONLY), 
				new TextCellEditor(outputsTable)});
	}

	private void createOutputEdit(Composite parent){
		Group outputsGroup = getWidgetFactory().createGroup(parent, "Outputs"); //$NON-NLS-1$
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite buttonListContainer = new Composite(outputsGroup, SWT.NONE);  // this has to be done here so that it is in the first column
		outputsViewer = new TableViewer(outputsGroup, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.FILL);
		outputsViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		outputsTable = outputsViewer.getTable();
		createTableLayout(outputsTable);
		outputsViewer.setColumnProperties(new String[] {NAME, TYPE, COMMENT});
		outputsViewer.setCellModifier(new InterfaceCellModifier(outputsViewer));
		outputsViewer.setLabelProvider(new InterfaceLabelProvider());
		createButtonList(buttonListContainer, outputsViewer, false);
	}

	@Override
	protected void setInputCode() {}

	@Override
	protected void setInputInit() {
	}
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			inputsViewer.setInput(getType());
			outputsViewer.setInput(getType());
		} 
		commandStack = commandStackBuffer;
	}
	
	public class InterfaceContentProvider implements IStructuredContentProvider {
		private boolean inputs;
		private InterfaceContentProviderType type;
		
		public InterfaceContentProvider(boolean inputs, InterfaceContentProviderType type){
			this.inputs = inputs;
			this.type = type;
		}
		
		private Object[] getInputs(Object inputElement){
			InterfaceList interfaceList = getInterfaceListFromInput(inputElement);
			
			if(null != interfaceList) {
				switch(type){
				case EVENT:
					return interfaceList.getEventInputs().toArray();
				case ADAPTER:
					return interfaceList.getSockets().toArray();
				case DATA:
					return interfaceList.getInputVars().toArray();
				default:
					break;	
				}
			}
			return new Object[0];
		}
		
		private Object[] getOutputs(Object inputElement){
			InterfaceList interfaceList = getInterfaceListFromInput(inputElement);
			
			if(null != interfaceList) {
				switch(type){
				case EVENT:
					return interfaceList.getEventOutputs().toArray();
				case ADAPTER:
					return interfaceList.getPlugs().toArray();
				case DATA:
					return interfaceList.getOutputVars().toArray();
				default:
					break;
				}
			}
			return new Object[0];
		}
		
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof SubApp || inputElement instanceof FBType) {
				if(inputs){
					return getInputs(inputElement);
				}else{
					return getOutputs(inputElement);
				}
			}
			return new Object[0];
		}
		
		private InterfaceList getInterfaceListFromInput(Object inputElement) {
			InterfaceList interfaceList = null;
			if(inputElement instanceof SubApp) {
				interfaceList = ((SubApp)inputElement).getInterface(); 
			} else if (inputElement instanceof FBType) {
				interfaceList = ((FBType)inputElement).getInterfaceList();			
			}
			return interfaceList;
		}
	}
	
	public class InterfaceLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			String result = null;
			if (element instanceof IInterfaceElement) {
				switch (columnIndex) {
				case 0:
					result = ((IInterfaceElement) element).getName();
					break;
				case 1:
				    result = element instanceof Event ? "Event" : ((IInterfaceElement) element).getTypeName();
				    break;
				case 2:
					result = ((IInterfaceElement) element).getComment() != null ? ((IInterfaceElement) element).getComment() : ""; //$NON-NLS-1$
					break;
				}
			}else {
				result = element.toString();				
			}
			return result;
		}
	}


	protected Palette getPalette() {
		if(getType() instanceof SubApp) {
			return ((SubApp)getType()).getFbNetwork().getApplication().getAutomationSystem().getPalette();
		}
		return null;
	}
	
	private class InterfaceCellModifier implements ICellModifier{
		private TableViewer viewer;
		
		public InterfaceCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}
		
		@Override
		public boolean canModify(final Object element, final String property) {
			if(TYPE.equals(property)) {
				if(element instanceof IInterfaceElement &&
						(!((IInterfaceElement) element).getInputConnections().isEmpty() ||
								!((IInterfaceElement) element).getOutputConnections().isEmpty())) {
					return false; 
				}
			}
			return true;
		}
		
		@Override
		public Object getValue(final Object element, final String property) {
			if(NAME.equals(property)) {
				return ((INamedElement) element).getName();
			}
			if(TYPE.equals(property)) {
				String type = ((IInterfaceElement) element).getTypeName();
		        String[] items = ((ComboBoxCellEditor)viewer.getCellEditors()[1]).getItems();
		        int i = items.length - 1;
		        while (i > 0 && !type.equals(items[i])){
		        	--i;
		        }
				return i;
			}
			if(COMMENT.equals(property)) {
				return ((INamedElement) element).getComment() != null ? ((INamedElement) element).getComment() : ""; //$NON-NLS-1$
			}
			return null;
		}
		
		private DataType getTypeForSelection(String text) {
			for (AdapterTypePaletteEntry adaptertype : getAdapterTypes(getPalette())){
				if(adaptertype.getType().getName().equals(text)) {
					return adaptertype.getType();
				}
			}
			return null;
		}
		
		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			Object data =  tableItem.getData();
			Command cmd = null;
			
			switch(property) {
			case NAME:
				cmd = new ChangeSubAppIENameCommand((IInterfaceElement) data, value.toString());
				break;
			case COMMENT:
				cmd = new ChangeCommentCommand((INamedElement) data, value.toString());
				break;
			case TYPE: 
				String dataTypeName = ((ComboBoxCellEditor)viewer.getCellEditors()[1]).getItems()[(int) value];
				if(data instanceof AdapterDeclaration) {
					DataType newType = getTypeForSelection(dataTypeName);			
					cmd = new ChangeTypeCommand((VarDeclaration) data, newType);
				}else {
					if(data instanceof VarDeclaration) {
						cmd = new ChangeTypeCommand((VarDeclaration) data, 
								DataTypeLibrary.getInstance().getType(dataTypeName));
					}							
				}
				break;
			default:
				break;
			}
			
			if(null != cmd){
				executeCommand(cmd);
				viewer.refresh(data);
			}
			
		}
	}

	protected static List<AdapterTypePaletteEntry> getAdapterTypes(final Palette systemPalette){
		List<AdapterTypePaletteEntry> retVal = new ArrayList<>();		
		Palette pal = systemPalette;
		if(null == pal){
			pal = TypeLibrary.getInstance().getPalette();
		}			
		retVal.addAll(getAdapterGroup(pal.getRootGroup()));		
		return retVal;
	}
	
	protected static List<AdapterTypePaletteEntry> getAdapterGroup(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		List<AdapterTypePaletteEntry> retVal = new ArrayList<>();	
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator.hasNext();) {
			PaletteGroup paletteGroup = iterator.next();
			retVal.addAll(getAdapterGroup(paletteGroup));		
		}		
		retVal.addAll(getAdapterGroupEntries(group));		
		return retVal;
	}
	
	protected static List<AdapterTypePaletteEntry> getAdapterGroupEntries(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		List<AdapterTypePaletteEntry> retVal = new ArrayList<>();	
		for (PaletteEntry entry : group.getEntries()) {
			if(entry instanceof AdapterTypePaletteEntry){
				retVal.add((AdapterTypePaletteEntry) entry);				
			}
		}
		return retVal;
	}
}
