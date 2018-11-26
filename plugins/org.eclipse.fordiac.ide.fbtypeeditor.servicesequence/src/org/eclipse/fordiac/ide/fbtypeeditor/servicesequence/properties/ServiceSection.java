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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeServiceInterfaceCommentCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeServiceInterfaceNameCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
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

public class ServiceSection extends AbstractServiceSection {
	
	private TreeViewer sequencesViewer;	
	private Button sequenceNew;
	private Button sequenceDelete;
	
	private Text leftNameText;
	private Text rightNameText;
	private Text leftCommentText;
	private Text rightCommentText;
	
	public ServiceSection() {}

	@Override
	protected FBType getType(){
		return (FBType) type;
	}
	
	@Override
	protected FBType getInputType(Object input) {
		if(input instanceof SequenceRootEditPart){
			return (FBType) ((SequenceRootEditPart) input).getCastedModel().eContainer();
		}
		if(input instanceof Service){
			return (FBType) ((Service)input).eContainer();
		}
		return null;
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createServiceSection(leftComposite);
		createSequencesSection(rightComposite);
	}
	
	private void createServiceSection(Composite parent){
		Group serviceGroup1 = getWidgetFactory().createGroup(parent, "Left Interface");
		serviceGroup1.setLayout(new GridLayout(2, false));	
		serviceGroup1.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));		
		getWidgetFactory().createCLabel(serviceGroup1, "Name:"); 
		leftNameText = createGroupText(serviceGroup1, true);
		leftNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeServiceInterfaceNameCommand(leftNameText.getText(), getType(), true));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(serviceGroup1, "Comment:"); 
		leftCommentText = createGroupText(serviceGroup1, true);
		leftCommentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeServiceInterfaceCommentCommand(leftCommentText.getText(), getType().getService(), true));
				addContentAdapter();
			}
		});
		
		Group serviceGroup2 = getWidgetFactory().createGroup(parent, "Right Interface");
		serviceGroup2.setLayout(new GridLayout(2, false));	
		serviceGroup2.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));	
		getWidgetFactory().createCLabel(serviceGroup2, "Name:"); 
		rightNameText = createGroupText(serviceGroup2, true);
		rightNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeServiceInterfaceNameCommand(rightNameText.getText(), getType(), false));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(serviceGroup2, "Comment:"); 	
		rightCommentText = createGroupText(serviceGroup2, true);
		rightCommentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeServiceInterfaceCommentCommand(rightCommentText.getText(), getType().getService(), false));
				addContentAdapter();
			}
		});
	}
	
	private void createSequencesSection(Composite parent){
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Service Sequences");
		transactionGroup.setLayout(new GridLayout(2, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		sequencesViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		sequencesViewer.getTree().setLayoutData(gridData);	
		sequencesViewer.setContentProvider(new AdapterFactoryContentProvider(getAdapterFactory()));
		sequencesViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(sequencesViewer.getTree(), adapterFactory);
		sequencesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
//				Object selection = ((IStructuredSelection) sequencesViewer.getSelection()).getFirstElement();
//				if(selection instanceof ServiceSequence){
//					selectNewSequence((ServiceSequence) selection);
//				}
//				else if(selection instanceof ServiceTransaction){
//					selectNewSequence((ServiceSequence) ((ServiceTransaction) selection).eContainer());
//				}
//				else if(selection instanceof InputPrimitive){
//					selectNewSequence((ServiceSequence)((InputPrimitive) selection).eContainer().eContainer());
//				}
//				else if(selection instanceof OutputPrimitive){
//					selectNewSequence((ServiceSequence)((OutputPrimitive) selection).eContainer().eContainer());
//				}
			}
		});
		
		Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		sequenceNew = getWidgetFactory().createButton(buttonComp, "New", SWT.PUSH);
		sequenceNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		sequenceNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateServiceSequenceCommand(getType()));
				sequencesViewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {}
		});
		
		sequenceDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		sequenceDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		sequenceDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Object selection = ((TreeSelection)sequencesViewer.getSelection()).getFirstElement();
				if(selection instanceof ServiceSequence){
					selectNewSequence(null);  //clear the graphical viewer of the whole service sequence is deleted
					executeCommand(new DeleteServiceSequenceCommand(getType(), (ServiceSequence)selection));						
				}else if( selection instanceof ServiceTransaction){
					executeCommand(new DeleteTransactionCommand((ServiceTransaction)selection));
				}else if(selection instanceof InputPrimitive){
					executeCommand(new DeleteInputPrimitiveCommand((InputPrimitive)selection));
				}else if(selection instanceof OutputPrimitive){
					executeCommand(new DeleteOutputPrimitiveCommand((OutputPrimitive)selection));
				}
				sequencesViewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
	}
	
	private void selectNewSequence(ServiceSequence selectedSequence) {
//		sequenceRootEditPart.setSelectedSequence(selectedSequence);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			leftNameText.setText(null != getType().getService().getLeftInterface() ? getType().getService().getLeftInterface().getName() : ""); //$NON-NLS-1$
			leftCommentText.setText(null != getType().getService().getLeftInterface() && null != getType().getService().getLeftInterface().getComment() ? getType().getService().getLeftInterface().getComment() : ""); //$NON-NLS-1$
			rightNameText.setText(null != getType().getService().getRightInterface() ? getType().getService().getRightInterface().getName() : ""); //$NON-NLS-1$
			rightCommentText.setText(null != getType().getService().getRightInterface() && null != getType().getService().getRightInterface().getComment() ? getType().getService().getRightInterface().getComment() : "");		 //$NON-NLS-1$
			sequencesViewer.setInput(getType().getService());
		} 
		commandStack = commandStackBuffer;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return super.getCommandStack(part);
	}

	@Override
	protected void setInputCode() {
		leftNameText.setEnabled(false);
		leftCommentText.setEnabled(false);
		rightNameText.setEnabled(false);
		rightCommentText.setEnabled(false);
		sequencesViewer.setInput(null);
	}

	@Override
	protected void setInputInit() {
	}
	

}
