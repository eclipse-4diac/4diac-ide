/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends org.eclipse.fordiac.ide.gef.properties.InterfaceElementSection{
	private TreeViewer connectionsTree;
	private Button newConnection;
	private Button delConnection;
	private Group group;
	private Combo fbCombo;
	private Combo interfaceCombo;
	private CLabel fbLabel;
	private CLabel interfaceElementLabel;
	
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createConnectionDisplaySection(parent);
		createConnectionSection(parent);
	}
	
	private void createConnectionSection(Composite parent) {
		Group group = getWidgetFactory().createGroup(parent, "Create Connection");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		fbLabel = getWidgetFactory().createCLabel(group, ""); //$NON-NLS-1$
		fbCombo = new Combo(group, SWT.SINGLE | SWT.READ_ONLY);
		fbCombo.addListener( SWT.Selection, event -> fillInterfaceCombo(fbCombo.getText()));
		interfaceElementLabel = getWidgetFactory().createCLabel(group, ""); //$NON-NLS-1$
		interfaceCombo = new Combo(group, SWT.SINGLE | SWT.READ_ONLY);
		interfaceCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
//				getInterfaceelement
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		newConnection = getWidgetFactory().createButton(group, "", SWT.PUSH); //$NON-NLS-1$
		newConnection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		delConnection.setLayoutData(new  GridData(SWT.LEFT, SWT.BOTTOM, false, true));
		newConnection.setToolTipText("new Connection");	
		newConnection.addListener( SWT.Selection, event -> {
				AbstractConnectionCreateCommand cmd = null;
				if(getType() instanceof Event){
					cmd = new EventConnectionCreateCommand(getFBNetwork());
				}else if(getType() instanceof AdapterDeclaration){
					cmd = new AdapterConnectionCreateCommand(getFBNetwork());
				}else if(getType() instanceof VarDeclaration){
					cmd = new DataConnectionCreateCommand(getFBNetwork());
				}
				if(null != cmd){
					cmd.setDestination(getSelectedFB().getInterfaceElement(interfaceCombo.getText()));
					cmd.setSource(getType());
					executeCommand(cmd);
					connectionsTree.refresh();
				}
			});
	}

	private FBNetwork getFBNetwork() {
		if(null != getType().getFBNetworkElement()){
			return getType().getFBNetworkElement().getFbNetwork();
		}
		if(getType().eContainer().eContainer() instanceof CompositeFBType){
			return ((CompositeFBType)getType().eContainer().eContainer()).getFBNetwork();
		}
		return null;
	}
	
	private FBNetworkElement getSelectedFB() {
		if(null != getFBNetwork()){
			return getFBNetwork().getFBNamed(fbCombo.getText());
		}
		return null;
	}
	
	private void fillFbCombo() {
		fbCombo.removeAll();
		if(null != getFBNetwork()){
			getFBNetwork().getNetworkElements().forEach(element -> fbCombo.add(element.getName()));			
		}
//		fbCombo.remove(getType().getFBNetworkElement().getName());
	}

	private void fillInterfaceCombo(String fbName) {
		interfaceCombo.removeAll();		
		FBNetworkElement element = getSelectedFB();
		if(element.getName().equals(fbName)){
			if(getType().isIsInput() && null != getType().getFBNetworkElement()){
				if(getType() instanceof Event){
					element.getInterface().getEventOutputs().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
				if(getType() instanceof AdapterDeclaration){
					element.getInterface().getPlugs().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
				if(getType() instanceof VarDeclaration){
					element.getInterface().getOutputVars().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
			}else{
				if(getType() instanceof Event){
					element.getInterface().getEventInputs().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
				if(getType() instanceof AdapterDeclaration){
					element.getInterface().getSockets().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
				if(getType() instanceof VarDeclaration){
					element.getInterface().getInputVars().forEach(port -> interfaceCombo.add(port.getName()));
					return;
				}
			}
		}
	}
	
	private void createConnectionDisplaySection(Composite parent) {
		group = getWidgetFactory().createGroup(parent, "Connections");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		connectionsTree = new TreeViewer(group, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 100;
		gridData.widthHint = 80;
		connectionsTree.getTree().setLayoutData(gridData);
		connectionsTree.setContentProvider(new ConnectionContentProvider());
		connectionsTree.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		connectionsTree.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		new AdapterFactoryTreeEditor(connectionsTree.getTree(), adapterFactory);
				
		delConnection = getWidgetFactory().createButton(group, "", SWT.PUSH); //$NON-NLS-1$
		delConnection.setLayoutData(new  GridData(SWT.RIGHT, SWT.BOTTOM, false, true));
		delConnection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		delConnection.setToolTipText("delete Connection");
		delConnection.addListener( SWT.Selection, event -> {
				Object selection = ((TreeSelection)connectionsTree.getSelection()).getFirstElement();
				if(selection instanceof Connection){
					executeCommand(new DeleteConnectionCommand((Connection)selection));
					connectionsTree.refresh();
				}
			});	
	}

	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			if(getType().isIsInput()){
				group.setText("In-Connections");
				fbLabel.setText("Source FB:");
				interfaceElementLabel.setText("Source Interface:"); 
			}else{
				group.setText("Out-Connections");
				fbLabel.setText("Target FB:");
				interfaceElementLabel.setText("Target Interface:"); 
			}
			connectionsTree.setInput(getType());
			fillFbCombo();
		}
		commandStack = commandStackBuffer;
	}
	
	@Override
	protected void setInputCode(){
		connectionsTree.setInput(null);
	}

	public class ConnectionContentProvider implements ITreeContentProvider {
		private IInterfaceElement element;
		
		@Override
		public Object[] getElements(final Object inputElement) {
			if(inputElement instanceof IInterfaceElement){
				element = ((IInterfaceElement)inputElement);
				if(element.isIsInput() && null != element.getFBNetworkElement() 
						|| (!element.isIsInput() && null == element.getFBNetworkElement())){
					return element.getInputConnections().toArray();
				}else{
					return element.getOutputConnections().toArray();	
				}
			}
			return new Object[] {};
		}
	
		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof Connection){
				Object [] objects = new Object[2];
				if(element.isIsInput()){
					objects[0] = null != ((Connection)parentElement).getSourceElement() ? ((Connection)parentElement).getSourceElement() : element;
					objects[1] = ((Connection)parentElement).getSource();
				}else{
					objects[0] = null != ((Connection)parentElement).getDestinationElement() ? ((Connection)parentElement).getDestinationElement() : element;
					objects[1] = ((Connection)parentElement).getDestination();
				}
				return objects;
			}
			return null;
		}
	
		@Override
		public Object getParent(Object element) {
			if(element instanceof Connection){
				return this.element;
			}
			return null;
		}
	
		@Override
		public boolean hasChildren(Object element) {
			if(element instanceof Connection){
				return null != ((Connection)element).getSource() && null != ((Connection)element).getDestination();
			}
			return false;
		}
	}
}
