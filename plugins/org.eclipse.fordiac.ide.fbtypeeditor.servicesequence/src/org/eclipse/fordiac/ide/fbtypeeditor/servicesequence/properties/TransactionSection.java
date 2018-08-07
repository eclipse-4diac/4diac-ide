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
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.contentprovider.TransactionContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.TransactionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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

public class TransactionSection extends AbstractServiceSection {
	
	private CLabel label;
	private Text inputPrimitive;
	private Button leftInputPrimitiveNew;
	private Button rightInputPrimitiveNew;
	private Button inputPrimitiveDelete;
	
	private TreeViewer leftOutputPrimitivesViewer;	
	private TreeViewer rightOutputPrimitivesViewer;	
	private Button leftOutputPrimitivesNew;
	private Button leftOutputPrimitivesDelete;
	private Button rightOutputPrimitivesNew;
	private Button rightOutputPrimitivesDelete;
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);	
		createInputPrimitiveSection(parent);
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createLeftOutputPrimitiveSection(composite); 	
		createRightOutputPrimitiveSection(composite);
	}

	private void createInputPrimitiveSection(Composite parent){
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Input Primitive");
		transactionGroup.setLayout(new GridLayout(1, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.UP, true, false));
		
		Composite inPrimitive = getWidgetFactory().createComposite(transactionGroup);
		inPrimitive.setLayout(new GridLayout(2, false));
		inPrimitive.setLayoutData(new GridData(SWT.FILL, SWT.UP, true, false));
		label = getWidgetFactory().createCLabel(inPrimitive, null); 
		inputPrimitive = createGroupText(inPrimitive, true);
		inputPrimitive.setEditable(false);
		inputPrimitive.setEnabled(false);
		
		Composite buttonComp = getWidgetFactory().createComposite(transactionGroup);
		buttonComp.setLayout(new GridLayout(3, false));
		buttonComp.setLayoutData(new GridData(SWT.CENTER, SWT.UP, true, false));
		leftInputPrimitiveNew = getWidgetFactory().createButton(buttonComp, "Create Left Primitive", SWT.PUSH);
		leftInputPrimitiveNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		leftInputPrimitiveNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateInputPrimitiveCommand(ServiceInterfacePaletteFactory.LEFT_INPUT_PRIMITIVE, getType()));
				refreshInputPrimitive();
			}
		});	
		inputPrimitiveDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		inputPrimitiveDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		inputPrimitiveDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {					
				executeCommand(new DeleteInputPrimitiveCommand(getType().getInputPrimitive()));
				refreshInputPrimitive();
			}
		});
		rightInputPrimitiveNew = getWidgetFactory().createButton(buttonComp, "Create Right Primitive", SWT.PUSH);
		rightInputPrimitiveNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		rightInputPrimitiveNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateInputPrimitiveCommand(ServiceInterfacePaletteFactory.RIGHT_INPUT_PRIMITIVE, getType()));
				refreshInputPrimitive();
			}
		});
	}
	
	private void createLeftOutputPrimitiveSection(Composite parent){
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Left Output Primitives");
		transactionGroup.setLayout(new GridLayout(2, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		leftOutputPrimitivesViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 60;
		gridData.widthHint = 80;
		leftOutputPrimitivesViewer.getTree().setLayoutData(gridData);
		leftOutputPrimitivesViewer.setContentProvider(new TransactionContentProvider(ServiceInterfacePaletteFactory.LEFT_OUTPUT_PRIMITIVE));
		leftOutputPrimitivesViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(leftOutputPrimitivesViewer.getTree(), adapterFactory);
		leftOutputPrimitivesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) leftOutputPrimitivesViewer.getSelection()).getFirstElement();
				if(selection instanceof OutputPrimitive){
//					selectNewSequence((ServiceSequence)((OutputPrimitive) selection).eContainer());
				}
			}
		});
		
		Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		leftOutputPrimitivesNew = getWidgetFactory().createButton(buttonComp, "New", SWT.PUSH);
		leftOutputPrimitivesNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		leftOutputPrimitivesNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateOutputPrimitiveCommand(ServiceInterfacePaletteFactory.LEFT_OUTPUT_PRIMITIVE, getType(), null));
				leftOutputPrimitivesViewer.refresh();
			}
		});
		leftOutputPrimitivesDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		leftOutputPrimitivesDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		leftOutputPrimitivesDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Object selection = ((TreeSelection)leftOutputPrimitivesViewer.getSelection()).getFirstElement();					
				if(selection instanceof OutputPrimitive){
					executeCommand(new DeleteOutputPrimitiveCommand((OutputPrimitive)selection));
				}
				leftOutputPrimitivesViewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
	}	

	private void createRightOutputPrimitiveSection(Composite parent){
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Right Output Primitives");
		transactionGroup.setLayout(new GridLayout(2, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		rightOutputPrimitivesViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 60;
		gridData.widthHint = 80;
		rightOutputPrimitivesViewer.getTree().setLayoutData(gridData);
		rightOutputPrimitivesViewer.setContentProvider(new TransactionContentProvider(ServiceInterfacePaletteFactory.RIGHT_OUTPUT_PRIMITIVE));
		rightOutputPrimitivesViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(rightOutputPrimitivesViewer.getTree(), adapterFactory);
		rightOutputPrimitivesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) rightOutputPrimitivesViewer.getSelection()).getFirstElement();
				if(selection instanceof OutputPrimitive){
//					selectNewSequence((ServiceSequence)((OutputPrimitive) selection).eContainer());
				}
			}
		});
		
		Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		rightOutputPrimitivesNew = getWidgetFactory().createButton(buttonComp, "New", SWT.PUSH);
		rightOutputPrimitivesNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		rightOutputPrimitivesNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				executeCommand(new CreateOutputPrimitiveCommand(ServiceInterfacePaletteFactory.RIGHT_OUTPUT_PRIMITIVE, getType(), null));
				rightOutputPrimitivesViewer.refresh();
			}
		});
		rightOutputPrimitivesDelete = getWidgetFactory().createButton(buttonComp, "Delete", SWT.PUSH);
		rightOutputPrimitivesDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		rightOutputPrimitivesDelete.addListener( SWT.Selection, event -> {
				Object selection = ((TreeSelection)rightOutputPrimitivesViewer.getSelection()).getFirstElement();					
				if(selection instanceof OutputPrimitive){
					executeCommand(new DeleteOutputPrimitiveCommand((OutputPrimitive)selection));
				}
				rightOutputPrimitivesViewer.refresh();
			});
	}		
	
	@Override
	protected ServiceTransaction getType(){
		return (ServiceTransaction)type;
	}

	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			leftOutputPrimitivesViewer.setInput(getType());
			rightOutputPrimitivesViewer.setInput(getType());	
			refreshInputPrimitive();
		}
		commandStack = commandStackBuffer;
	}

	private void refreshInputPrimitive(){
		if(null != getType().getInputPrimitive()){
			inputPrimitive.setText(null == getType().getInputPrimitive().getParameters() || getType().getInputPrimitive().getParameters().equals("")?  //$NON-NLS-1$
					getType().getInputPrimitive().getEvent() : getType().getInputPrimitive().getEvent() + "(" + getType().getInputPrimitive().getParameters() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			label.setImage( isLeftInterface(getType().getInputPrimitive()) ?
					FordiacImage.ICON_LeftInputPrimitive.getImage() :
						FordiacImage.ICON_RigthInputPrimitive.getImage());
		}else{
			inputPrimitive.setText(""); //$NON-NLS-1$
		}
	}
	
	@Override
	protected Object getInputType(Object input) {
		if(input instanceof TransactionEditPart){
			return ((TransactionEditPart) input).getCastedModel();	
		}
		if(input instanceof ServiceTransaction){
			return input;	
		}
		return null;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return super.getCommandStack(part);
	}

	@Override
	protected void setInputCode() {
//		leftOutputPrimitivesViewer.setInput(null);
//		rightOutputPrimitivesViewer.setInput(null);	
		leftOutputPrimitivesNew.setEnabled(false);
		leftOutputPrimitivesDelete.setEnabled(false);
		rightOutputPrimitivesNew.setEnabled(false);
		rightOutputPrimitivesDelete.setEnabled(false);
	}

	@Override
	protected void setInputInit() {	
	}
}
