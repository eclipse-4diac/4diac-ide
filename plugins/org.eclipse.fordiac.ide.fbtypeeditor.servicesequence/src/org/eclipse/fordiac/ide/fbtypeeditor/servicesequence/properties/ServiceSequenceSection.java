/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
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
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
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

public class ServiceSequenceSection extends AbstractServiceSection {

	private Text nameText;
	private Text commentText;
	private TreeViewer transactionViewer;
	private Button transactionNew;
	private Button transactionDelete;

	@Override
	protected ServiceSequence getType() {
		return (ServiceSequence) type;
	}

	@Override
	protected ServiceSequence getInputType(final Object input) {
		if (input instanceof ServiceSequenceEditPart) {
			return ((ServiceSequenceEditPart) input).getCastedModel();
		}
		if (input instanceof ServiceSequence) {
			return (ServiceSequence) input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTypeAndCommentSection(getLeftComposite());
		createTransactionSection(getRightComposite());
	}

	private void createTypeAndCommentSection(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, Messages.ServiceSequenceSection_Name);
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeSequenceNameCommand(nameText.getText(), getType()));
			addContentAdapter();
		});
		getWidgetFactory().createCLabel(composite, Messages.ServiceSequenceSection_Comment);
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	private void createTransactionSection(final Composite parent) {
		final Group transactionGroup = getWidgetFactory().createGroup(parent, Messages.ServiceSequenceSection_Transaction);
		transactionGroup.setLayout(new GridLayout(2, false));
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		transactionViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		transactionViewer.getTree().setLayoutData(gridData);
		transactionViewer.setContentProvider(new ServiceSequenceContentProvider());
		transactionViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		new AdapterFactoryTreeEditor(transactionViewer.getTree(), getAdapterFactory());
		transactionViewer.addSelectionChangedListener(event -> {
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
		});

		final Composite buttonComp = new Composite(transactionGroup, SWT.NONE);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));
		transactionNew = getWidgetFactory().createButton(buttonComp, Messages.ServiceSequenceSection_New, SWT.PUSH);
		transactionNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		transactionNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				executeCommand(new CreateTransactionCommand(getType()));
				transactionViewer.refresh();
			}
		});
		transactionDelete = getWidgetFactory().createButton(buttonComp, Messages.ServiceSequenceSection_Delete,
				SWT.PUSH);
		transactionDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		transactionDelete.addListener(SWT.Selection, e -> {
			final Object selection = ((TreeSelection) transactionViewer.getSelection()).getFirstElement();
			if (selection instanceof ServiceTransaction) {
				executeCommand(new DeleteTransactionCommand((ServiceTransaction) selection));
			} else if (selection instanceof InputPrimitive) {
				executeCommand(new DeleteInputPrimitiveCommand((InputPrimitive) selection));
			} else if (selection instanceof OutputPrimitive) {
				executeCommand(new DeleteOutputPrimitiveCommand((OutputPrimitive) selection));
			}
			transactionViewer.refresh();
		});
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			transactionViewer.setInput(getType());
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputCode() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
		transactionViewer.setInput(null);
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}
}
