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
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeSequenceNameCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.contentprovider.ServiceSequenceContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ServiceSequenceSection extends AbstractServiceSection {
	
	private Text nameText;
	private Text commentText;
	private TreeViewer transactionViewer;	
	private Button transactionNew;
	private Button transactionDelete;	

	@Override
	protected ServiceSequence getType(){
		return (ServiceSequence)type;
	}

	@Override
	protected ServiceSequence getInputType(Object input) {
		if(input instanceof ServiceSequenceEditPart){
			return ((ServiceSequenceEditPart) input).getCastedModel();	
		}
		if(input instanceof ServiceSequence){
			return (ServiceSequence) input;	
		}
		return null;
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createTypeAndCommentSection(leftComposite);
		createTransactionSection(rightComposite);
	}
	
	private void createTypeAndCommentSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Name:"); 
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeSequenceNameCommand(nameText.getText(), getType()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(composite, "Comment:"); 
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
	}
	
	private void createTransactionSection(Composite parent){
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Transaction");
		transactionGroup.setLayout(new GridLayout(2, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		transactionViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		transactionViewer.getTree().setLayoutData(gridData);
		transactionViewer.setContentProvider(new ServiceSequenceContentProvider());
		transactionViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(transactionViewer.getTree(), adapterFactory);
		transactionViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
//				Object selection = ((IStructuredSelection) transactionViewer.getSelection()).getFirstElement();
//				if(selection instanceof ServiceTransaction){
//					selectNewSequence((ServiceSequence) ((ServiceTransaction) selection));
//				}
//				else if(selection instanceof InputPrimitive){
//					selectNewSequence((ServiceSequence)((InputPrimitive) selection).eContainer());
//				}
//				else if(selection instanceof OutputPrimitive){
//					selectNewSequence((ServiceSequence)((OutputPrimitive) selection).eContainer());
//				}
			}
		});
		
		Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		transactionNew = getWidgetFactory().createButton(buttonComp, "New", SWT.PUSH);
		transactionNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		transactionNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateTransactionCommand(getType()));
				transactionViewer.refresh();
			}
		});
		transactionDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		transactionDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		transactionDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Object selection = ((TreeSelection)transactionViewer.getSelection()).getFirstElement();					
				if( selection instanceof ServiceTransaction){
					executeCommand(new DeleteTransactionCommand((ServiceTransaction)selection));
				}else if(selection instanceof InputPrimitive){
					executeCommand(new DeleteInputPrimitiveCommand((InputPrimitive)selection));
				}else if(selection instanceof OutputPrimitive){
					executeCommand(new DeleteOutputPrimitiveCommand((OutputPrimitive)selection));
				}
				transactionViewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
	}	
	
	//
	//	private void selectNewSequence(ServiceSequence selectedSequence) {
	//		sequenceRootEditPart.setSelectedSequence(selectedSequence);
	//	}	

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : "");	 //$NON-NLS-1$
			transactionViewer.setInput(getType());
		} 
		commandStack = commandStackBuffer;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return super.getCommandStack(part);
	}

	@Override
	protected void setInputCode() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
		transactionViewer.setInput(null);
	}

	@Override
	protected void setInputInit() {
	}
}
