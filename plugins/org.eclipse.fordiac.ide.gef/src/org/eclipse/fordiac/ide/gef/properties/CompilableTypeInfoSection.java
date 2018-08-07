/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.gef.provider.CompilerContentProvider;
import org.eclipse.fordiac.ide.gef.provider.CompilerLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerInfoClassdefCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerInfoHeaderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerLanguageCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerProductCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerVendorCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCompilerVersionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewCompilerCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteCompilerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Properties tab which shows the FB type information of the selected FB
 * 
 */
public abstract class CompilableTypeInfoSection extends TypeInfoSection {
	
	private static final String LANGUAGE_OTHER = "Other";  //$NON-NLS-1$
	private static final String LANGUAGE_C = "C"; //$NON-NLS-1$
	private static final String LANGUAGE_CPP = "Cpp"; //$NON-NLS-1$
	private static final String LANGUAGE_JAVA = "Java"; //$NON-NLS-1$
	private TableViewer compilerViewer;	
	private Group compilerInfoGroup;	
	private Text headerText;
	private Text classdefText;
	private Button compilerInfoNew;
	private Button compilerInfoDelete;
	private static final String COMPILER_VERSION = "compiler_version"; //$NON-NLS-1$
	private static final String COMPILER_LANGUAGE = "language"; //$NON-NLS-1$
	private static final String COMPILER_VENDOR = "vendor"; //$NON-NLS-1$
	private static final String COMPILER_PRODUCT = "product"; //$NON-NLS-1$
	private static final String[] VALUE_SET = new String[] { LANGUAGE_JAVA, LANGUAGE_CPP, LANGUAGE_C, LANGUAGE_OTHER }; 
	
	public CompilableTypeInfoSection() {
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createCompilerInfoGroup(rightComposite);
	}

	private void createCompilerInfoGroup(Composite parent) {	
		compilerInfoGroup = getWidgetFactory().createGroup(parent, "Compiler Info");
		compilerInfoGroup.setLayout(new GridLayout(1, false));
		compilerInfoGroup.setLayoutData(new GridData(SWT.FILL, 0, false, false));	
		
		Composite composite = getWidgetFactory().createComposite(compilerInfoGroup, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		getWidgetFactory().createCLabel(composite, "Header:");	
		headerText = createGroupText(composite, true);			
		headerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeCompilerInfoHeaderCommand((FBType)type, headerText.getText()));
			}
		});
		getWidgetFactory().createCLabel(composite, "Classdef:");
		classdefText = createGroupText(composite, true);			
		classdefText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeCompilerInfoClassdefCommand((FBType)type, classdefText.getText()));
			}
		});
		Composite compositeBottom = getWidgetFactory().createComposite(compilerInfoGroup);
		compositeBottom.setLayout(new GridLayout(2, false));
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		compilerViewer = new TableViewer(compositeBottom, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);	
		GridData gridDataVersionViewer = new GridData(GridData.FILL_BOTH);
		gridDataVersionViewer.heightHint = 80;
		gridDataVersionViewer.widthHint = 400;
		compilerViewer.getControl().setLayoutData(gridDataVersionViewer);
		final Table table = compilerViewer.getTable();		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);			
		TableColumn column1 = new TableColumn(compilerViewer.getTable(), SWT.LEFT);
		column1.setText("Language"); 
		TableColumn column2 = new TableColumn(compilerViewer.getTable(), SWT.LEFT);
		column2.setText("Vendor"); 
		TableColumn column3 = new TableColumn(compilerViewer.getTable(), SWT.LEFT);
		column3.setText("Product");
		TableColumn column4 = new TableColumn(compilerViewer.getTable(), SWT.LEFT);
		column4.setText("Version");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(25, 80));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 100));
		layout.addColumnData(new ColumnWeightData(25, 80));
		table.setLayout(layout);
		compilerViewer.setContentProvider(new CompilerContentProvider());
		compilerViewer.setLabelProvider(new CompilerLabelProvider());
		compilerViewer.setCellEditors(new CellEditor[] {new ComboBoxCellEditor(table, VALUE_SET), new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		compilerViewer.setColumnProperties(new String[] { COMPILER_LANGUAGE, COMPILER_VENDOR, COMPILER_PRODUCT, COMPILER_VERSION });
		
		Composite buttonComp = new Composite(compositeBottom, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		compilerInfoNew = getWidgetFactory().createButton(buttonComp, "New", SWT.PUSH);
		compilerInfoNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		compilerInfoNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new AddNewCompilerCommand((FBType)type));
				compilerViewer.refresh();
			}
		});
		compilerInfoDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		compilerInfoDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		compilerInfoDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new DeleteCompilerCommand(((FBType)type).getCompilerInfo(), (Compiler)((IStructuredSelection) compilerViewer.getSelection()).getFirstElement()));
				compilerViewer.refresh();
			}
		});
		compilerViewer.setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(final Object element, final String property) {
				return true;
			}
			@Override
			public Object getValue(final Object element, final String property) {
				if (COMPILER_LANGUAGE.equals(property)) {
					String language = ((Compiler) element).getLanguage().getName();
					if (language.equals(LANGUAGE_JAVA)) { 
						return 0;
					} else if (language.equals(LANGUAGE_CPP)) { 
						return 1;
					} else if (language.equals(LANGUAGE_C)) {
						return 2;
					} else {
						return 3;
					}
				} else if (COMPILER_PRODUCT.equals(property)) {
					return ((Compiler) element).getProduct();
				} else if (COMPILER_VENDOR.equals(property)) {
					return ((Compiler) element).getVendor();
				} else{
					return ((Compiler) element).getVersion();
				}
			}
			@Override
			public void modify(final Object element, final String property, final Object value) {
				TableItem tableItem = (TableItem) element;
				Compiler data = (Compiler) tableItem.getData();
				Command cmd = null;
				if (COMPILER_LANGUAGE.equals(property)) {
					switch ((Integer) value) {
					case 0:
						cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_JAVA));
						break;
					case 1:
						cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_CPP));
						break;
					case 2:
						cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_C));
						break;
					case 3:
						cmd = new ChangeCompilerLanguageCommand(data, Language.get(LANGUAGE_OTHER));
						break;
					}
				} else if (COMPILER_PRODUCT.equals(property)) {
					cmd = new ChangeCompilerProductCommand(data, value.toString());
				} else if (COMPILER_VENDOR.equals(property)) {
					cmd = new ChangeCompilerVendorCommand(data, value.toString());
				} else{
					cmd = new ChangeCompilerVersionCommand(data, value.toString());
				}
				if(null != cmd){
					executeCommand(cmd);
					compilerViewer.refresh(data);
				}
			}
		});
	}
	
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		commandStack = getCommandStack(part, null);
		if(null == commandStack){ //disable all field
			headerText.setEnabled(false);
			classdefText.setEnabled(false);
			compilerInfoNew.setEnabled(false);
			compilerInfoDelete.setEnabled(false);
			compilerViewer.setCellModifier(null);
		}
	}
	
	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type && type instanceof FBType) {			
			if(null != ((FBType)type).getCompilerInfo()){
				headerText.setText(null != ((FBType)type).getCompilerInfo().getHeader() ? ((FBType)type).getCompilerInfo().getHeader() : ""); //$NON-NLS-1$
				classdefText.setText(null != ((FBType)type).getCompilerInfo().getClassdef() ? ((FBType)type).getCompilerInfo().getClassdef() : ""); //$NON-NLS-1$
				compilerViewer.setInput(type);
			}
		}	
		commandStack = commandStackBuffer;
	}
}
