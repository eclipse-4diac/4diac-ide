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

import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeActionOrderCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeTransitionPriorityCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ActionContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.StateContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StateSection extends AbstractECSection {
	private Text nameText;
	private Text commentText;
	private TreeViewer actionViewer;	
	private Button actionUp;
	private Button actionDown;
	private Button actionNew;
	private Button actionDelete;
	private TreeViewer transitionsOutViewer;	
	private Button transitionUp;
	private Button transitionDown;
	
	@Override
	protected ECState getType() {
		return (ECState) type;
	}

	@Override
	protected Object getInputType(Object input) {
		if(input instanceof ECStateEditPart){
			return ((ECStateEditPart) input).getCastedModel();	
		}
		if(input instanceof ECState){
			return input;	
		}
		return null;
	}
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);		
		parent.setLayout(new GridLayout(2, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createNameCommentControls(parent);
		createActionTransitionControls(parent);
	}
	
	public void createNameCommentControls(final Composite parent){	
		Composite nameComposite = getWidgetFactory().createComposite(parent);
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(nameComposite, "Name:"); 
		nameText = createGroupText(nameComposite, true);
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});
		
		Composite commentComposite = getWidgetFactory().createComposite(parent);
		commentComposite.setLayout(new GridLayout(2, false));
		commentComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(commentComposite, "Comment:"); 
		commentText = createGroupText(commentComposite, true);	
		commentText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}
	
	public void createActionCreateButton(Composite actionButtonComp) {
		actionNew = getWidgetFactory().createButton(actionButtonComp, "", SWT.FLAT); //$NON-NLS-1$
		actionNew.setToolTipText("Create new action");
		actionNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));	
		actionNew.addListener(SWT.Selection, e -> {
			executeCommand(new CreateECActionCommand(LibraryElementFactory.eINSTANCE.createECAction(), getType()));
			actionViewer.refresh();
		});
	}
	
	private void createActionDeleteButton(Composite actionButtonComp) {
		actionDelete = getWidgetFactory().createButton(actionButtonComp, "", SWT.PUSH); //$NON-NLS-1$
		actionDelete.setToolTipText("Delete selected action");
		actionDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));	
		actionDelete.addListener(SWT.Selection, e -> {
			ECAction action = (ECAction)((IStructuredSelection) actionViewer.getSelection()).getFirstElement();
			executeCommand(new DeleteECActionCommand(action));
			actionViewer.refresh();
		});
	}
	
	public void createActionTransitionControls(final Composite parent){	
		Group actionGroup = getWidgetFactory().createGroup(parent, "Actions");
		actionGroup.setLayout(new GridLayout(2, false));
		actionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		createActionViewer(actionGroup, gridData);
		Composite actionButtonComp = new Composite(actionGroup, SWT.NONE);
		actionButtonComp.setLayout(new FillLayout(SWT.VERTICAL));
		createActionCreateButton(actionButtonComp);
		createActionUpButton(actionButtonComp);
		createActionDownButton(actionButtonComp);
		createActionDeleteButton(actionButtonComp);	
		Group transactionGroup = getWidgetFactory().createGroup(parent, "Outgoing Transitions");
		transactionGroup.setLayout(new GridLayout(2, false));	
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTransitionViewer(gridData, transactionGroup);
		Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		createTransitionUpButton(buttonComp);
		createTransitionDownButton(buttonComp);
	}

	private void createActionViewer(Group actionGroup, GridData gridData) {
		actionViewer = new TreeViewer(actionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		actionViewer.getTree().setLayoutData(gridData);
		actionViewer.setContentProvider(new ActionContentProvider());
		actionViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(actionViewer.getTree(), adapterFactory);
	}

	private void createTransitionViewer(GridData gridData, Group transactionGroup) {
		transitionsOutViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		transitionsOutViewer.getTree().setLayoutData(gridData);
		transitionsOutViewer.setContentProvider(new StateContentProvider());
		transitionsOutViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(transitionsOutViewer.getTree(), adapterFactory);
	}

	private void createTransitionDownButton(Composite buttonComp) {
		transitionDown = getWidgetFactory().createButton(buttonComp, "SWT.ARROW |SWT.UP", SWT.ARROW |SWT.DOWN);
		transitionDown.setToolTipText("Move transition down");
		transitionDown.addListener(SWT.Selection, e -> {
			Object selection = ((TreeSelection)transitionsOutViewer.getSelection()).getFirstElement();
			if(selection instanceof ECTransition){
				executeCommand(new ChangeTransitionPriorityCommand(getType(), (ECTransition) selection, false));
				transitionsOutViewer.refresh();
				transitionsOutViewer.setSelection(new StructuredSelection(selection));
			}
		});
	}

	private void createTransitionUpButton(Composite buttonComp) {
		transitionUp = getWidgetFactory().createButton(buttonComp, "", SWT.ARROW |SWT.UP); //$NON-NLS-1$
		transitionUp.setToolTipText("Move transition up");	
		transitionUp.addListener(SWT.Selection, e -> {
			Object selection = ((TreeSelection)transitionsOutViewer.getSelection()).getFirstElement();
			if(selection instanceof ECTransition){
				executeCommand(new ChangeTransitionPriorityCommand(getType(), (ECTransition) selection, true));
				transitionsOutViewer.refresh();
				transitionsOutViewer.setSelection(new StructuredSelection(selection));
			}
		});
	}

	private void createActionDownButton(Composite actionButtonComp) {
		actionDown = getWidgetFactory().createButton(actionButtonComp, "Down", SWT.ARROW |SWT.DOWN);
		actionDown.setToolTipText("Move action down");
		actionDown.addListener(SWT.Selection, e -> {
			Object selection = ((TreeSelection)actionViewer.getSelection()).getFirstElement();
			if(selection instanceof ECAction){
				executeCommand(new ChangeActionOrderCommand(getType(), (ECAction) selection, false));
				actionViewer.refresh();
				actionViewer.setSelection(new StructuredSelection(selection));
			}
		});
	}

	private void createActionUpButton(Composite actionButtonComp) {
		actionUp = getWidgetFactory().createButton(actionButtonComp, "", SWT.ARROW |SWT.UP); //$NON-NLS-1$
		actionUp.setToolTipText("Move action up");	
		actionUp.addListener(SWT.Selection, e -> {
			Object selection = ((TreeSelection)actionViewer.getSelection()).getFirstElement();
			if(selection instanceof ECAction){
				executeCommand(new ChangeActionOrderCommand(getType(), (ECAction) selection, true));
				actionViewer.refresh();
				actionViewer.setSelection(new StructuredSelection(selection));
			}
		});
	}

	protected void setInputCode() {
		commentText.setEnabled(false);
		nameText.setEnabled(false);
		transitionsOutViewer.setInput(null);
		actionViewer.setInput(null);
	}	

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			transitionsOutViewer.setInput(getType());
			actionViewer.setInput(getType());
		} 
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {
		//currently not needed
	}
}
