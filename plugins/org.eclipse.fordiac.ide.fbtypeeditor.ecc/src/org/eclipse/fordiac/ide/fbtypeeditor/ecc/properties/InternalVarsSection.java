/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added mulitline selection and code cleanup.  
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.InternalVarsLabelProvider;
/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
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
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInitialValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InternalVarsSection extends ECCSection {
	private static final String IV_NAME = "NAME"; //$NON-NLS-1$
	private static final String IV_TYPE = "TYPE"; //$NON-NLS-1$
	private static final String IV_ARRAY = "ARRAY_SIZE"; //$NON-NLS-1$
	private static final String IV_INIT = "INITIAL_VALUE"; //$NON-NLS-1$
	private static final String IV_COMMENT = "COMMENT"; //$NON-NLS-1$

	private TableViewer internalVarsViewer;
	private Button internalVarsDelete;
	private ComboBoxCellEditor typeDropDown;
	private String[] dataTypes = new String[DataTypeLibrary.getInstance().getDataTypesSorted().size()];
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalVarsControls(parent);
	}
	
	public void createInternalVarsControls(final Composite parent) {	
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));	

		Composite buttonComp = new Composite(composite, SWT.NONE);
		internalVarsViewer = new TableViewer(composite, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridDataVersionViewer.heightHint = 150;
		gridDataVersionViewer.widthHint = 80;
		internalVarsViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = internalVarsViewer.getTable();		
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column1.setText("Name");
		TableColumn column2 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column2.setText("Type"); 
		TableColumn column3 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column3.setText("Array Size");
		TableColumn column4 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column4.setText("Initial Value");
		TableColumn column5 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column5.setText("Comment"); 
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 30));
		layout.addColumnData(new ColumnWeightData(2, 30));
		layout.addColumnData(new ColumnWeightData(3, 50));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(1, 20));
		table.setLayout(layout);
		
		for(int i = 0; i < DataTypeLibrary.getInstance().getDataTypesSorted().size(); i++){
			dataTypes[i] = ((DataType) DataTypeLibrary.getInstance().getDataTypesSorted().toArray()[i]).getName();
		}
		
		internalVarsViewer.setCellEditors(createCellEditors(table));
		internalVarsViewer.setColumnProperties(new String[] { IV_NAME, IV_TYPE, IV_COMMENT, IV_ARRAY, IV_INIT});
		internalVarsViewer.setContentProvider(new ArrayContentProvider());
		internalVarsViewer.setLabelProvider(new InternalVarsLabelProvider());
		internalVarsViewer.setCellModifier(new InternalVarsCellModifier());		

		internalVarsViewer.addSelectionChangedListener(event ->
				setInternalVarsDeleteState(null != event.getStructuredSelection().getFirstElement()));
		
		createAddDelteButtons(buttonComp);  //create the add and delete buttons here so that we have access to the internalVarsViewer
	}

	@SuppressWarnings("unchecked")
	private void createAddDelteButtons(Composite buttonComp) {
		
		GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		buttonComp.setLayoutData(buttonCompLayoutData);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		Button internalVarsNew = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		internalVarsNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		
		Listener createListener = e -> {
			executeCommand(new CreateInternalVariableCommand(getType()));
			internalVarsViewer.refresh();			
		};
		internalVarsNew.addListener( SWT.Selection, createListener);
		internalVarsDelete = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		setInternalVarsDeleteState(false);
		
		Listener deleteListener = e -> { 
			if(!internalVarsViewer.getStructuredSelection().isEmpty()) {
				CompoundCommand cmd =  new CompoundCommand();
				internalVarsViewer.getStructuredSelection().toList().
				forEach(elem -> cmd.add(new DeleteInternalVariableCommand(getType(), (VarDeclaration) elem)));				
				executeCommand(cmd);
				internalVarsViewer.refresh();
			}			
		};
		internalVarsDelete.addListener( SWT.Selection, deleteListener);
		
		internalVarsViewer.getTable().addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// Nothing to do here
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.INSERT && e.stateMask == 0) {
					createListener.handleEvent(null);
				} else  if (e.character == SWT.DEL && e.stateMask == 0) {
					deleteListener.handleEvent(null);
				}
			}
		});
	}

	private void setInternalVarsDeleteState(boolean enabled) {
		internalVarsDelete.setEnabled(enabled);
		internalVarsDelete.setImage((enabled) ?
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE) :
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}

	private CellEditor[] createCellEditors(final Table table) {
		TextCellEditor varNameEditor = new TextCellEditor(table); 
		((Text)varNameEditor.getControl()).addVerifyListener(new IdentifierVerifyListener());		
		typeDropDown = new ComboBoxCellEditor(table, dataTypes, SWT.READ_ONLY);
		return new CellEditor[] { varNameEditor, typeDropDown, new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) };
	}

	@Override
	protected void setInputCode() {
		internalVarsViewer.setCellModifier(null);
	}	
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			internalVarsViewer.setInput(getType().getInternalVars());
		} 
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {	
		// for now nothing to be done here 
	}
	
	private final class InternalVarsCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			//only allow editing if only one element is selected and if the selected is also the 
			//element to be requested for editing. This improves the usability of multi-line selection.
			return 1 == internalVarsViewer.getStructuredSelection().size() && 
					element.equals(internalVarsViewer.getStructuredSelection().getFirstElement());
		}

		@Override
		public Object getValue(final Object element, final String property) {
			VarDeclaration var = (VarDeclaration) element;
			switch(property) {
			case IV_NAME:
				return var.getName();
			case IV_TYPE:
				for(int i = 0; i < typeDropDown.getItems().length; i++) {
					if(typeDropDown.getItems()[i].equals(var.getType().getName())){
						return i;
					}
				}
				return 0;
			case IV_COMMENT:
				return var.getComment();
			case IV_ARRAY:
				return Integer.toString(var.getArraySize());
			default:
				return var.getValue().getValue();
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			VarDeclaration data = (VarDeclaration) tableItem.getData();
			Command cmd = null;
			switch(property) {
			case IV_NAME:
				cmd = new ChangeNameCommand(data, value.toString());
				break;
			case IV_TYPE:
				cmd = new ChangeTypeCommand(data, DataTypeLibrary.getInstance().getType(dataTypes[(int)value]));
				break;
			case IV_COMMENT:
				cmd = new ChangeCommentCommand(data, value.toString());
				break;
			case IV_ARRAY:
				cmd = new ChangeArraySizeCommand(data, value.toString());
				break;
			default:
				cmd = new ChangeInitialValueCommand(data, value.toString());
				break;
			}
			
			executeCommand(cmd);
			internalVarsViewer.refresh(data);
		}
	}
	
}
