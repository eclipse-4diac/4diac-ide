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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.AlgorithmsLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
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

public class AlgorithmsSection extends ECCSection {
	private TableViewer algorithmViewer;
	private Button algorithmNew;
	private Button algorithmDelete;
	private AlgorithmGroup algorithmGroup;
	
	private static final String A_NAME = "Name";
	private static final String A_LANGUAGE = "Language";
	private static final String A_COMMENT = "Comment";

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createAlgorithmControls(parent);
	}
		
	public void createAlgorithmControls(final Composite parent) {	
		SashForm view = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		view.setLayout(new FillLayout());
		leftComposite = getWidgetFactory().createComposite(view);
		leftComposite.setLayout(new GridLayout(2, false));
		rightComposite = getWidgetFactory().createComposite(view);
		rightComposite.setLayout(new GridLayout());	
		view.setWeights(new int[] {1, 1});
		view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));	
		createAddDelteButtons(leftComposite);		
		createAlgorithmViewer(leftComposite);				
		algorithmGroup = new AlgorithmGroup(rightComposite, getWidgetFactory());
	}

	private void createAlgorithmViewer(final Composite parent) {
		algorithmViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL);
		GridData gridDataVersionViewer = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridDataVersionViewer.heightHint = 150;
		gridDataVersionViewer.widthHint = 80;
		algorithmViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = algorithmViewer.getTable();		
		table.setLinesVisible(true);		
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(algorithmViewer.getTable(), SWT.LEFT);
		column1.setText(A_NAME);
		TableColumn column2 = new TableColumn(algorithmViewer.getTable(), SWT.CENTER);
		column2.setText(A_LANGUAGE); 
		TableColumn column3 = new TableColumn(algorithmViewer.getTable(), SWT.LEFT);
		column3.setText(A_COMMENT);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 50));
		layout.addColumnData(new ColumnWeightData(1, 20));
		layout.addColumnData(new ColumnWeightData(3, 50));
		table.setLayout(layout);
		algorithmViewer.setCellEditors(createAlgorithmCellEditors(table));
		algorithmViewer.setColumnProperties(new String[] { A_NAME, A_LANGUAGE, A_COMMENT});
		algorithmViewer.setContentProvider(new ArrayContentProvider());		
		algorithmViewer.setLabelProvider(new AlgorithmsLabelProvider());
		algorithmViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(final Object element, final String property) {
				return true;
			}
			public Object getValue(final Object element, final String property) {
				switch (property) {
				case A_NAME:
					return ((Algorithm) element).getName();
				case A_LANGUAGE:
					return (element instanceof STAlgorithm) ? 1 : 0;	
				default:
					return ((Algorithm) element).getComment();
				}
			}
			public void modify(final Object element, final String property, final Object value) {
				TableItem tableItem = (TableItem) element;
				Algorithm data = (Algorithm) tableItem.getData();
				Command cmd = null;
				if (A_NAME.equals(property)) {
					cmd = new ChangeNameCommand(data, value.toString());
				} else if (A_LANGUAGE.equals(property)) {
					cmd = new ChangeAlgorithmTypeCommand(getType(), data, getLanguages().get((int)value));
				} else{
					cmd = new ChangeCommentCommand(data, value.toString());
				}
				if((null != cmd) && (null != commandStack)){
					commandStack.execute(cmd);
					algorithmViewer.refresh(data);
					if(cmd instanceof ChangeAlgorithmTypeCommand){
						data = ((ChangeAlgorithmTypeCommand)cmd).getNewAlgorithm();
						if(null != data){
							algorithmViewer.setSelection(new StructuredSelection(data));
						}
					}
					algorithmGroup.setAlgorithm(data);
					refresh();
				}
			}
		});
		algorithmViewer.addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) algorithmViewer.getSelection()).getFirstElement();
				setAlgorithDeleteState(null != selection);
				algorithmGroup.setAlgorithm((selection instanceof Algorithm) ? (Algorithm) selection : null);
			}
		});
	}

	private static CellEditor[] createAlgorithmCellEditors(final Table table) {
		TextCellEditor algorithmNameEditor = new TextCellEditor(table); 
		((Text)algorithmNameEditor.getControl()).addVerifyListener(new IdentifierVerifyListener());
		return new CellEditor[] { algorithmNameEditor, 
				new ComboBoxCellEditor(table, getLanguages().toArray(new String[0]), SWT.READ_ONLY), 
				new TextCellEditor(table) };
	}

	private void createAddDelteButtons(Composite composite) {
		Composite buttonComp = new Composite(composite, SWT.NONE);
		GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		buttonComp.setLayoutData(buttonCompLayoutData);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		algorithmNew = getWidgetFactory().createButton(buttonComp, "", SWT.FLAT); //$NON-NLS-1$
		algorithmNew.setToolTipText("Create new algorithm");
		algorithmNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		algorithmNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				CreateAlgorithmCommand cmd = new CreateAlgorithmCommand(getType());
				executeCommand(cmd);
				algorithmViewer.refresh();
				if(null != cmd.getNewAlgorithm()){
					algorithmViewer.setSelection(new StructuredSelection(cmd.getNewAlgorithm()), true);
				}
			}
		});
		algorithmDelete = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		setAlgorithDeleteState(false);
		algorithmDelete.setToolTipText("Delete selected algorithm");
		algorithmDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				executeCommand(new DeleteAlgorithmCommand(getType(), (Algorithm)((IStructuredSelection) algorithmViewer.getSelection()).getFirstElement()));
				algorithmViewer.refresh();
			}
		});
	}
	
	private void setAlgorithDeleteState(boolean enabled) {
		algorithmDelete.setEnabled(enabled);
		algorithmDelete.setImage((enabled) ? 
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE) :
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}

	protected void setInputCode() {
		algorithmViewer.setCellModifier(null);
	}

	protected void setInputInit() {
		algorithmGroup.initialize(getType(), commandStack);
	}	
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			algorithmViewer.setInput(getType().getAlgorithm());
		} 
		commandStack = commandStackBuffer;
	}
}
