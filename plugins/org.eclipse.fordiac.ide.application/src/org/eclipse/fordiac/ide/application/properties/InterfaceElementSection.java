/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz	
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
 *   Alois Zoitl - fixed issues in type changes for subapp interface elements  
 *   Lisa Sonnleithner - new TypeAndCommentSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractSection {
	private TreeViewer connectionsTree;
	private Group group;

	private Text typeText;
	private Text commentText;
	protected CCombo typeCombo;
	private Text parameterText;
	private Text currentParameterText;
	private CLabel parameterTextCLabel;
	private CLabel currentParameterTextCLabel;
	private Button openEditorButton;
	private Form form;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		createTypeAndCommentSection(parent);
		createConnectionDisplaySection(parent);
	}

	protected void createTypeAndCommentSection(Composite parent) {

		form = getWidgetFactory().createForm(parent);
		form.getBody().setLayout(new GridLayout(1, false));
		form.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));

		createInstanceInfoSection(form.getBody());
		createTypeInfoSection(form.getBody());

	}

	private void createConnectionDisplaySection(Composite parent) {
		group = getWidgetFactory().createGroup(parent, Messages.InterfaceElementSection_ConnectionGroup);
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
		new AdapterFactoryTreeEditor(connectionsTree.getTree(), getAdapterFactory());

		Button delConnection = getWidgetFactory().createButton(group, "", SWT.PUSH); //$NON-NLS-1$
		delConnection.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true));
		delConnection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		delConnection.setToolTipText(Messages.InterfaceElementSection_DeleteConnectionToolTip);
		delConnection.addListener(SWT.Selection, event -> {
			Object selection = ((TreeSelection) connectionsTree.getSelection()).getFirstElement();
			if (selection instanceof Connection) {
				executeCommand(new DeleteConnectionCommand((Connection) selection));
				connectionsTree.refresh();
			}
		});
	}

	private void createTypeInfoSection(Composite parent) {
		// textfields in this section without a button need to span 2 cols so that all
		// textfields are aligned

		Section infoSection = getWidgetFactory().createSection(parent,
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		infoSection.setText(FordiacMessages.TypeInfo + ":"); //$NON-NLS-1$
		infoSection.setLayout(new GridLayout(1, false));
		infoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite composite = getWidgetFactory().createComposite(infoSection);

		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, false);
		commentText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Type + ":"); //$NON-NLS-1$

		typeText = createGroupText(composite, false);

		openEditorButton = new Button(typeText.getParent(), SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorButton.addListener(SWT.Selection, ev -> OpenStructMenu
				.openStructEditor(((VarDeclaration) getType()).getType().getPaletteEntry().getFile()));

		parameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.DefaultValue + ":"); //$NON-NLS-1$
		parameterText = createGroupText(composite, false);
		parameterText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		infoSection.setClient(composite);

	}

	private void createInstanceInfoSection(Composite parent) {
		Section infoSection = getWidgetFactory().createSection(parent,
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		infoSection.setText(FordiacMessages.InstanceInfo + ":"); //$NON-NLS-1$
		infoSection.setLayout(new GridLayout(1, false));
		infoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite composite = getWidgetFactory().createComposite(infoSection);

		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		currentParameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.CurrentValue + ":"); //$NON-NLS-1$
		currentParameterText = createGroupText(composite, true);
		currentParameterText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeValueCommand((VarDeclaration) getType(), currentParameterText.getText()));
			addContentAdapter();
		});

		infoSection.setClient(composite);

	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;

		Boolean b = null != type && (getType() instanceof VarDeclaration)
				&& !(getType().getType() instanceof AdapterType);
		parameterTextCLabel.setVisible(b);
		parameterText.setVisible(b);
		currentParameterTextCLabel.setVisible(b);
		currentParameterText.setVisible(b);

		if (null != type) {
			form.setText(FordiacMessages.Name + ": " + (getType().getName() != null ? getType().getName() : "")); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			String itype = ""; //$NON-NLS-1$

			openEditorButton.setEnabled(
					getType().getType() instanceof StructuredType || getType().getType() instanceof AdapterType);

			if (getType() instanceof VarDeclaration) {
				itype = setParameterAndType();

			} else {
				itype = FordiacMessages.Event;
			}
			typeText.setText(itype);

			if (getType().isIsInput()) {
				group.setText(Messages.InterfaceElementSection_InConnections);
			} else {
				group.setText(Messages.InterfaceElementSection_OutConnections);
			}
			connectionsTree.setInput(getType());

		}

		commandStack = commandStackBuffer;
	}

	protected String setParameterAndType() {
		String itype;
		VarDeclaration var = (VarDeclaration) getType();
		itype = var.getType() != null ? var.getType().getName() : ""; //$NON-NLS-1$
		if (getType().isIsInput()) {
			if (getType().getFBNetworkElement().getType() instanceof FBType) {
				IInterfaceElement ie = ((FBType) getType().getFBNetworkElement().getType())
						.getInterfaceList().getInterfaceElement(getType().getName());
				if (ie instanceof VarDeclaration) {
					parameterText.setText(
							(((VarDeclaration) ie).getValue() != null) ? ((VarDeclaration) ie).getValue().getValue()
									: ""); //$NON-NLS-1$
					if (getType().getType() instanceof StructuredType) {
						itype = getStructTypes((StructuredType) getType().getType());
					}
				}
			}
			currentParameterText.setText((var.getValue() != null) ? var.getValue().getValue() : ""); //$NON-NLS-1$
		}
		return itype;
	}

	// this method will be removed as soon as there is a toString for StructType in
	// the model
	protected String getStructTypes(StructuredType st) {

		EList<VarDeclaration> list = st.getMemberVariables();
		StringBuilder sb = new StringBuilder();
		sb.append(st.getName());
		sb.append(": (");
		boolean printString = false;
		for (VarDeclaration v : list) {
			if ((v.getType() != null)) {
				sb.append(v.getType().getName());
				printString = true;
			} else {
				sb.append("not set");
			}
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(')');

		return printString ? sb.toString() : "";
	}

	@Override
	protected void setInputCode() {
		connectionsTree.setInput(null);
	}

	private static class ConnectionContentProvider implements ITreeContentProvider {
		private IInterfaceElement element;

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof IInterfaceElement) {
				element = ((IInterfaceElement) inputElement);
				if (element.isIsInput() && null != element.getFBNetworkElement()
						|| (!element.isIsInput() && null == element.getFBNetworkElement())) {
					return element.getInputConnections().toArray();
				} else {
					return element.getOutputConnections().toArray();
				}
			}
			return new Object[] {};
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Connection) {
				Object[] objects = new Object[2];
				if (element.isIsInput()) {
					objects[0] = null != ((Connection) parentElement).getSourceElement()
							? ((Connection) parentElement).getSourceElement()
							: element;
					objects[1] = ((Connection) parentElement).getSource();
				} else {
					objects[0] = null != ((Connection) parentElement).getDestinationElement()
							? ((Connection) parentElement).getDestinationElement()
							: element;
					objects[1] = ((Connection) parentElement).getDestination();
				}
				return objects;
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof Connection) {
				return this.element;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Connection) {
				return null != ((Connection) element).getSource() && null != ((Connection) element).getDestination();
			}
			return false;
		}
	}

	protected ChangeTypeCommand newChangeTypeCommand(VarDeclaration data, DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected Object getInputType(Object input) {
		if (input instanceof InterfaceEditPart) {
			return ((InterfaceEditPart) input).getModel();
		} else if (input instanceof ValueEditPart) {
			return ((ValueEditPart) input).getModel().getVarDeclaration();
		}
		return null;
	}

	@Override
	protected void setInputInit() {
		// no implementation needed

	}

}
