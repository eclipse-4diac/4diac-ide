/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class CreateConnectionSection extends AbstractSection {
	private Text commentText;
	private Text sourceText;
	private Text targetText;
	private Button createConnectionButton;
	private EList<InterfaceEditPart> editParts = new BasicEList<InterfaceEditPart>();

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
		}
		return null;
	}

	@Override
	protected EList<IInterfaceElement> getInputType(Object input) {
		if(input instanceof IStructuredSelection 
				&&  ((IStructuredSelection)input).getFirstElement() instanceof InterfaceEditPart){
			EList<IInterfaceElement> list = new BasicEList<IInterfaceElement>();
			editParts.clear();
			editParts.add(((InterfaceEditPart)((IStructuredSelection) input).toList().get(0)));
			list.add(editParts.get(0).getModel());
			Object object = ((InterfaceEditPart)((IStructuredSelection) input).toList().get(1)).getModel();		
			if(((IInterfaceElement)object).isIsInput()){
				list.add((IInterfaceElement) object);
				editParts.add((InterfaceEditPart)((IStructuredSelection) input).toList().get(1));
			}else{				
				list.add(0, (IInterfaceElement) object);
				editParts.add(0, (InterfaceEditPart)((IStructuredSelection) input).toList().get(1));
			}
			return list;
		}
		return null;
	}
	
	@Override
	protected EObject getType(){
		return null;
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Source); 
		sourceText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Target); 
		targetText = createGroupText(composite, false);	
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Comment); 
		commentText = createGroupText(composite, true);
		createConnectionButton = getWidgetFactory().createButton(parent, Messages.CreateConnectionSection_CreateConnection, SWT.PUSH);
		createConnectionButton.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
		createConnectionButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				AbstractConnectionCreateCommand cmd = null;
				FBNetwork nw = editParts.get(0).getModel().getFBNetworkElement().getFbNetwork();
				if(getInterfaceElement(true) instanceof Event){
					cmd = new EventConnectionCreateCommand(nw);
				}else if(getInterfaceElement(true) instanceof AdapterDeclaration){
					cmd = new AdapterConnectionCreateCommand(nw);
				}else{
					cmd = new DataConnectionCreateCommand(nw);
				}
				if(null != cmd){
					cmd.setSource(editParts.get(0).getModel());
					cmd.setDestination(editParts.get(1).getModel());
					executeCommand(cmd);
				}
			}
		});
	}
	
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		commandStack = getCommandStack(part, selection);
		if(null == commandStack){ //disable all fields
			commentText.setEnabled(false);
			sourceText.setEnabled(false);
			targetText.setEnabled(false);
		}
		setType(selection);
	}	
	
	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			sourceText.setText(getInterfaceName(true));
			targetText.setText(getInterfaceName(false));
		} 
		commandStack = commandStackBuffer;
	}
	
	private IInterfaceElement getInterfaceElement(boolean source){
		if(source){
			return (IInterfaceElement) ((EList<?>)type).get(0);
		}
		return (IInterfaceElement) ((EList<?>)type).get(1);
	}
	
	private String getInterfaceName(boolean source){
		Object element = getInterfaceElement(source);
		return getFBName((INamedElement) element) + "." + ((INamedElement) element).getName(); //$NON-NLS-1$
	}
	
	private static String getFBName(INamedElement element){
		return ((FBNetworkElement)element.eContainer().eContainer()).getName();
	}
	
	@Override
	protected void setInputCode() {}

	@Override
	protected void setInputInit() {
	}
}
