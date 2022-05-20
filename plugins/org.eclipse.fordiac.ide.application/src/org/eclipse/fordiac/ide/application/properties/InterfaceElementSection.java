/*******************************************************************************
 * Copyright (c) 2016, 2022 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - Harmonized and improved connection section
 *               - added instance comment editing
 *   Dunja Životin - extracted in/out connections table into a separate widget
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.ConnectionDisplayWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractSection {
	private TableViewer connectionsViewer;

	private Text typeText;
	private Text typeCommentText;
	private Text instanceCommentText;
	private Text parameterText;
	private Text currentParameterText;
	private CLabel parameterTextCLabel;
	private CLabel currentParameterTextCLabel;
	private Button openEditorButton;
	private Section infoSection;
	private ConnectionDisplayWidget connectionDisplayWidget;


	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		createInstanceInfoSection(getLeftComposite());
		createTypeInfoSection(getLeftComposite());
		createConnectionDisplaySection(getRightComposite());
	}

	private void createConnectionDisplaySection(final Composite parent) {
		connectionDisplayWidget = new ConnectionDisplayWidget(getWidgetFactory(), parent, this);
	}



	private void createTypeInfoSection(final Composite parent) {
		// textfields in this section without a button need to span 2 cols so that all
		// textfields are aligned

		final Section typeInfoSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		typeInfoSection.setText(FordiacMessages.TypeInfo + ":"); //$NON-NLS-1$
		typeInfoSection.setLayout(new GridLayout(1, false));
		typeInfoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = getWidgetFactory().createComposite(typeInfoSection);

		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		typeCommentText = createGroupText(composite, false);
		typeCommentText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Type + ":"); //$NON-NLS-1$

		typeText = createGroupText(composite, false);

		openEditorButton = new Button(typeText.getParent(), SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorButton.addListener(SWT.Selection, ev -> OpenStructMenu
				.openStructEditor(((VarDeclaration) getType()).getType().getTypeEntry().getFile()));

		parameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.DefaultValue + ":"); //$NON-NLS-1$
		parameterText = createGroupText(composite, false);
		parameterText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		typeInfoSection.setClient(composite);
	}

	private void createInstanceInfoSection(final Composite parent) {
		infoSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		infoSection.setLayout(new GridLayout(1, false));
		infoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = getWidgetFactory().createComposite(infoSection);

		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		instanceCommentText = createGroupText(composite, false);
		instanceCommentText.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false));
		instanceCommentText.selectAll();

		instanceCommentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), instanceCommentText.getText()));
			addContentAdapter();
		});

		currentParameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		currentParameterText = createGroupText(composite, true);
		currentParameterText.addModifyListener(e -> {
			removeContentAdapter();
			if (getType() instanceof VarDeclaration) {
				// only allow to change the parameter text if is a var declaration and not an error marker
				executeCommand(new ChangeValueCommand((VarDeclaration) getType(), currentParameterText.getText()));
			}
			addContentAdapter();
		});

		infoSection.setClient(composite);
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;

		if (null != type) {
			refreshParameterVisibility();
			final FBNetworkElement fb = getType().getFBNetworkElement();
			if (fb != null) {
				infoSection.setText(
						MessageFormat.format(Messages.InterfaceElementSection_Instance, fb.getName(), getPinName()));
			} else { // e.g., IP address of device
				infoSection.setText(Messages.InterfaceElementSection_InterfaceElement);
			}
			typeCommentText.setText(getTypeComment());

			openEditorButton.setEnabled(
					((getType().getType() instanceof StructuredType) && !"ANY_STRUCT".equals(getType().getType().getName()))
					|| (getType().getType() instanceof AdapterType));

			if (hasComment()) {
				instanceCommentText.setText(getInstanceComment());
			} else {
				instanceCommentText.setMessage(getInstanceComment());
			}

			if (getType() instanceof VarDeclaration) {
				setParameter();
			} else if (getType() instanceof ErrorMarkerInterface) {
				setErrorParam();
			}

			typeText.setText(getPinTypeName());

			connectionDisplayWidget.refreshConnectionsViewer(getType());

			if (fb != null) {
				setEditable(!fb.isContainedInTypedInstance());
			}
		}

		commandStack = commandStackBuffer;
	}

	private String getPinTypeName() {
		if (getType().getType() instanceof StructuredType) {
			return getStructTypes((StructuredType) getType().getType());
		}
		return getType().getType() != null ? getType().getType().getName() : ""; //$NON-NLS-1$
	}

	private String getTypeComment() {
		final FBNetworkElement fb = getType().getFBNetworkElement();
		if ((fb != null) && (fb.getType() != null)) {
			final IInterfaceElement interfaceElement = fb.getType().getInterfaceList()
					.getInterfaceElement(getType().getName());
			if (interfaceElement != null) {
				return interfaceElement.getComment() != null ? interfaceElement.getComment() : ""; //$NON-NLS-1$
			}
		}
		return "";   //$NON-NLS-1$
	}

	private boolean hasComment() {
		return ((getType().getComment() != null) && !getType().getComment().isBlank());
	}

	private String getInstanceComment() {
		if (hasComment()) {
			return getType().getComment();
		}
		return getTypeComment();
	}

	private Object getPinName() {
		return getType().getName() != null ? getType().getName() : ""; //$NON-NLS-1$
	}

	private void refreshParameterVisibility() {
		final boolean isDataIO = isDataIO();
		parameterTextCLabel.setVisible(isDataIO);
		parameterText.setVisible(isDataIO);
		currentParameterTextCLabel.setVisible(isDataIO && getType().isIsInput());
		currentParameterText.setVisible(isDataIO && getType().isIsInput());
	}

	private boolean isDataIO() {
		if (getType() instanceof ErrorMarkerInterface) {
			return !(getType().getType() instanceof EventType) && !(getType().getType() instanceof AdapterType);
		}
		return (getType() instanceof VarDeclaration) && !(getType() instanceof AdapterDeclaration);
	}

	private void setEditable(final boolean editable) {
		currentParameterText.setEditable(editable);
		currentParameterText.setEnabled(editable);
		instanceCommentText.setEditable(editable);
		instanceCommentText.setEnabled(editable);
		connectionDisplayWidget.setEditable(editable);
	}

	protected void setParameter() {
		final VarDeclaration varDecl = (VarDeclaration) getType();
		if (varDecl.isIsInput() && (varDecl.getFBNetworkElement() != null)) {
			final FBType fbType = varDecl.getFBNetworkElement().getType();
			if (null != fbType) {
				final IInterfaceElement ie = fbType.getInterfaceList().getInterfaceElement(varDecl.getName());
				if (ie instanceof VarDeclaration) {
					parameterText.setText(getValueFromVarDecl((VarDeclaration) ie));
				}
			}
		}
		currentParameterText.setText(getValueFromVarDecl(varDecl));
	}

	private void setErrorParam() {
		final ErrorMarkerInterface pin = (ErrorMarkerInterface) getType();
		currentParameterText.setText(getValueText(pin.getValue()));
	}

	private static String getValueFromVarDecl(final VarDeclaration varDecl) {
		return getValueText(varDecl.getValue());
	}

	private static String getValueText(final Value value) {
		return ((value != null) && (value.getValue() != null)) ? value.getValue() : ""; //$NON-NLS-1$
	}

	// this method will be removed as soon as there is a toString for StructType in
	// the model
	private static String getStructTypes(final StructuredType st) {
		final EList<VarDeclaration> list = st.getMemberVariables();
		final StringBuilder sb = new StringBuilder();
		sb.append(st.getName());
		sb.append(": ("); //$NON-NLS-1$
		boolean printString = false;
		for (final VarDeclaration v : list) {
			if ((v.getType() != null)) {
				sb.append(v.getType().getName());
				printString = true;
			} else {
				sb.append("not set");
			}
			sb.append(", "); //$NON-NLS-1$
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(')');

		return printString ? sb.toString() : ""; //$NON-NLS-1$
	}

	@Override
	protected void setInputCode() {
		connectionsViewer.setInput(null);
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		return TypedInterfacePinFilter.getInterfaceElementFromSelectedElement(input);
	}

	@Override
	protected void setInputInit() {
		// no implementation needed
	}

}
