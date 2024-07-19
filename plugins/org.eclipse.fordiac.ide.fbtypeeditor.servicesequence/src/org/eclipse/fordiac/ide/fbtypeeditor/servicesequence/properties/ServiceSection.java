/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH, Johannes Kepler University Linz
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
 *   Melanie Winter - modernize and cleanup section
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeServiceInterfaceCommentCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeServiceInterfaceNameCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeOutputPrimitiveOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeServiceSequenceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTransactionOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ServiceSection extends AbstractSection {

	private TreeViewer sequencesViewer;

	private Text leftNameInput;
	private Text rightNameInput;
	private Text leftCommentInput;
	private Text rightCommentInput;

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected FBType getInputType(final Object input) {
		if (input instanceof SequenceRootEditPart) {
			return (FBType) ((SequenceRootEditPart) input).getCastedModel().eContainer();
		}
		if (input instanceof Service) {
			return (FBType) ((Service) input).eContainer();
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite interfaceSection = getWidgetFactory().createComposite(parent);
		interfaceSection.setLayout(new GridLayout(2, false));
		interfaceSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createInterfaceSection(interfaceSection);

		final Composite sequencesSection = getWidgetFactory().createComposite(parent);
		sequencesSection.setLayout(new GridLayout(1, false));
		sequencesSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createSequencesSection(sequencesSection);
	}

	private void createInterfaceSection(final Composite parent) {
		// left interface
		final Group leftInterfaceGroup = getWidgetFactory().createGroup(parent, Messages.ServiceSection_LeftInterface);
		leftInterfaceGroup.setLayout(new GridLayout(1, true));
		leftInterfaceGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		final Composite leftInterfaceComposite = getWidgetFactory().createComposite(leftInterfaceGroup);
		leftInterfaceComposite.setLayout(new GridLayout(4, false));
		leftInterfaceComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		// left interface name
		getWidgetFactory().createCLabel(leftInterfaceComposite, Messages.ServiceSection_Name);
		leftNameInput = new Text(leftInterfaceComposite, SWT.BORDER);
		leftNameInput.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		leftNameInput.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeServiceInterfaceNameCommand(leftNameInput.getText(), getType(), true));
			addContentAdapter();
		});

		// left interface comment
		getWidgetFactory().createCLabel(leftInterfaceComposite, Messages.ServiceSection_Comment);
		leftCommentInput = new Text(leftInterfaceComposite, SWT.BORDER);
		leftCommentInput.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		leftCommentInput.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(
					new ChangeServiceInterfaceCommentCommand(leftCommentInput.getText(), getType().getService(), true));
			addContentAdapter();
		});

		// right interface
		final Group rightInterfaceGroup = getWidgetFactory().createGroup(parent,
				Messages.ServiceSection_RightInterface);
		rightInterfaceGroup.setLayout(new GridLayout(1, true));
		rightInterfaceGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		final Composite rightInterfaceComposite = getWidgetFactory().createComposite(rightInterfaceGroup);
		rightInterfaceComposite.setLayout(new GridLayout(4, false));
		rightInterfaceComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		// right interface name
		getWidgetFactory().createCLabel(rightInterfaceComposite, Messages.ServiceSection_Name);
		rightNameInput = new Text(rightInterfaceComposite, SWT.BORDER);
		rightNameInput.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false));
		rightNameInput.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeServiceInterfaceNameCommand(rightNameInput.getText(), getType(), false));
			addContentAdapter();
		});

		// right interface comment
		getWidgetFactory().createCLabel(rightInterfaceComposite, Messages.ServiceSection_Comment);
		rightCommentInput = new Text(rightInterfaceComposite, SWT.BORDER);
		rightCommentInput.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		rightCommentInput.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeServiceInterfaceCommentCommand(rightCommentInput.getText(), getType().getService(),
					false));
			addContentAdapter();

		});
	}

	private void createSequencesSection(final Composite parent) {
		final Group transactionGroup = getWidgetFactory().createGroup(parent, Messages.ServiceSection_ServiceSequences);
		transactionGroup.setLayout(new GridLayout(2, false));
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		createButtons(transactionGroup);

		sequencesViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		sequencesViewer.getTree().setLayoutData(gridData);
		sequencesViewer.setContentProvider(new AdapterFactoryContentProvider(getAdapterFactory()));
		sequencesViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
	}

	private void createButtons(final Group transactionGroup) {
		final Composite buttonComp = createButtonContainer(getWidgetFactory(), transactionGroup);
		buttonComp.setLayout(new FillLayout(SWT.VERTICAL));

		final Button sequenceNew = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		sequenceNew.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		sequenceNew.addListener(SWT.Selection, e -> executeCreateCommand());

		final Button sequenceReorderUp = getWidgetFactory().createButton(buttonComp, "", SWT.ARROW | SWT.UP); //$NON-NLS-1$
		sequenceReorderUp.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		sequenceReorderUp.addListener(SWT.Selection, e -> executeMoveCommand(true));

		final Button sequenceReorderDown = getWidgetFactory().createButton(buttonComp, "", SWT.ARROW | SWT.DOWN); //$NON-NLS-1$
		sequenceReorderDown.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		sequenceReorderDown.addListener(SWT.Selection, e -> executeMoveCommand(false));

		final Button sequenceDelete = getWidgetFactory().createButton(buttonComp, "", SWT.PUSH); //$NON-NLS-1$
		sequenceDelete.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		sequenceDelete.addListener(SWT.Selection, e -> executeDeleteCommand());
	}

	private void executeCreateCommand() {
		final Object selection = ((TreeSelection) sequencesViewer.getSelection()).getFirstElement();
		if (selection instanceof final ServiceSequence serSeq) {
			executeCommand(new CreateServiceSequenceCommand(getType().getService(), serSeq));
		} else if (selection instanceof final ServiceTransaction serTran) {
			executeCommand(new CreateTransactionCommand(serTran.getServiceSequence()));
		} else if (selection instanceof final OutputPrimitive op) {
			executeCommand(new CreateOutputPrimitiveCommand(op.getServiceTransaction(), null, true));
		} else if (selection == null) {
			executeCommand(new CreateServiceSequenceCommand(getType().getService()));
		}
		sequencesViewer.refresh();
	}

	private void executeMoveCommand(final boolean moveUp) {
		final Object selection = ((TreeSelection) sequencesViewer.getSelection()).getFirstElement();
		if (selection instanceof final ServiceSequence serSeq) {
			executeCommand(new ChangeServiceSequenceOrderCommand(serSeq, moveUp));
		} else if (selection instanceof final ServiceTransaction serTran) {
			executeCommand(new ChangeTransactionOrderCommand(serTran, moveUp));
		} else if (selection instanceof final OutputPrimitive op) {
			executeCommand(new ChangeOutputPrimitiveOrderCommand(op, moveUp));
		}
		sequencesViewer.refresh();
	}

	private void executeDeleteCommand() {
		final Object selection = ((TreeSelection) sequencesViewer.getSelection()).getFirstElement();
		if (selection instanceof final ServiceSequence serSeq) {
			executeCommand(new DeleteServiceSequenceCommand(getType(), serSeq));
		} else if (selection instanceof final ServiceTransaction serTran) {
			executeCommand(new DeleteTransactionCommand(serTran));
		} else if (selection instanceof final OutputPrimitive op) {
			executeCommand(new DeleteOutputPrimitiveCommand(op));
		}
		sequencesViewer.refresh();
	}

	@Override
	protected void performRefresh() {
		leftNameInput.setText(
				null != getType().getService().getLeftInterface() ? getType().getService().getLeftInterface().getName()
						: ""); //$NON-NLS-1$
		leftCommentInput.setText(null != getType().getService().getLeftInterface()
				&& null != getType().getService().getLeftInterface().getComment()
						? getType().getService().getLeftInterface().getComment()
						: ""); //$NON-NLS-1$
		rightNameInput.setText(null != getType().getService().getRightInterface()
				? getType().getService().getRightInterface().getName()
				: ""); //$NON-NLS-1$
		rightCommentInput.setText(null != getType().getService().getRightInterface()
				&& null != getType().getService().getRightInterface().getComment()
						? getType().getService().getRightInterface().getComment()
						: ""); //$NON-NLS-1$
		sequencesViewer.setInput(getType().getService());
	}

	@Override
	protected void setInputCode() {
		leftNameInput.setEnabled(false);
		leftCommentInput.setEnabled(false);
		rightNameInput.setEnabled(false);
		rightCommentInput.setEnabled(false);
		sequencesViewer.setInput(null);
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}

	private static Composite createButtonContainer(final FormToolkit widgetFactory, final Composite parent) {
		final Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		final GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, true);

		container.setLayoutData(buttonCompLayoutData);
		container.setLayout(new GridLayout(1, true));
		return container;
	}

}