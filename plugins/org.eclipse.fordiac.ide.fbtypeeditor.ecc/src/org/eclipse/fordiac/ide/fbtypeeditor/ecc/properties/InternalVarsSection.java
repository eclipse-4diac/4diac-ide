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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InternalVarsSection extends ECCSection {
	private TableViewer internalVarsViewer;
	private Button internalVarsNew;
	private Button internalVarsDelete;
	private static final String IV_NAME = "Name";
	private static final String IV_TYPE = "Type";
	private static final String IV_ARRAY = "Array Size";
	private static final String IV_INIT = "Initial Value";
	private static final String IV_COMMENT = "Comment";
	private ComboBoxCellEditor typeDropDown;
	private String dataTypes[] = new String[DataTypeLibrary.getInstance().getDataTypesSorted().size()];
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalVarsControls(parent);
	}
	
	public void createInternalVarsControls(final Composite parent) {	
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));	

		createAddDelteButtons(composite);

		internalVarsViewer = new TableViewer(composite, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridDataVersionViewer.heightHint = 150;
		gridDataVersionViewer.widthHint = 80;
		internalVarsViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = internalVarsViewer.getTable();		
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column1.setText(IV_NAME);
		TableColumn column2 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column2.setText(IV_TYPE); 
		TableColumn column3 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column3.setText(IV_COMMENT);
		TableColumn column4 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column4.setText(IV_ARRAY);
		TableColumn column5 = new TableColumn(internalVarsViewer.getTable(), SWT.LEFT);
		column5.setText(IV_INIT); 
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
		internalVarsViewer.setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(final Object element, final String property) {
				return true;
			}
			@Override
			public Object getValue(final Object element, final String property) {
				if (IV_NAME.equals(property)) {
					return ((VarDeclaration) element).getName();
				} else if (IV_TYPE.equals(property)) {
					int i = 0;
					for(String item : typeDropDown.getItems()){
						if(item.equals(((VarDeclaration) element).getType().getName())){
							return i;
						}
						i++;
					}
					return 0;
				} else if (IV_COMMENT.equals(property)) {
					return ((VarDeclaration) element).getComment();
				} else if (IV_ARRAY.equals(property)) {
					return Integer.toString(((VarDeclaration) element).getArraySize());
				} else{
					return ((VarDeclaration) element).getVarInitialization().getInitialValue();
				}
			}
			@Override
			public void modify(final Object element, final String property, final Object value) {
				TableItem tableItem = (TableItem) element;
				VarDeclaration data = (VarDeclaration) tableItem.getData();
				Command cmd = null;
				if (IV_NAME.equals(property)) {
					cmd = new ChangeNameCommand(data, value.toString());
				} else if (IV_TYPE.equals(property)) {
					cmd = new ChangeTypeCommand(data, DataTypeLibrary.getInstance().getType(dataTypes[(int)value]));
				} else if (IV_COMMENT.equals(property)) {
					cmd = new ChangeCommentCommand(data, value.toString());
				} else if (IV_ARRAY.equals(property)) {
					cmd = new ChangeArraySizeCommand(data, value.toString());
				} else{
					cmd = new ChangeInitialValueCommand(data, value.toString());
				}
				if(null != cmd){
					executeCommand(cmd);
					internalVarsViewer.refresh(data);
				}			
			}
		});		

		internalVarsViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setInternalVarsDeleteState(null != event.getStructuredSelection().getFirstElement());
			}
		});

	}

	private void createAddDelteButtons(Composite composite) {
		Composite buttonComp = new Composite(composite, SWT.NONE);
		GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		buttonComp.setLayoutData(buttonCompLayoutData);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		internalVarsNew = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		internalVarsNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		internalVarsNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateInternalVariableCommand(getType()));
				internalVarsViewer.refresh();
			}
		});
		internalVarsDelete = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		setInternalVarsDeleteState(false);
		internalVarsDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {				
				executeCommand(new DeleteInternalVariableCommand(getType(), (VarDeclaration)((IStructuredSelection) internalVarsViewer.getSelection()).getFirstElement()));
				internalVarsViewer.refresh();
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
	}
}
