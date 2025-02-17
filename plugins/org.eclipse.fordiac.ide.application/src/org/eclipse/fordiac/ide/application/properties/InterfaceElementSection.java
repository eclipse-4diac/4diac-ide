/*******************************************************************************
 * Copyright (c) 2016, 2024 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
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
 *   Martin Jobst - adopt ST editor for initial values
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.editors.InitialValueEditor;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferencePage;
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.gef.widgets.ConnectionDisplayWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueRefreshJob;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractDoubleColumnSection {
	private TableViewer connectionsViewer;

	private Text typeText;
	private Text typeCommentText;
	private Text instanceCommentText;
	private Text parameterText;
	private InitialValueEditor currentParameterEditor;
	private CLabel parameterTextCLabel;
	private CLabel currentParameterTextCLabel;
	private CLabel currentVarConfigTextCLabel;
	private Button currentVarConfigCheckBox;
	private Button openEditorButton;
	private Section infoSection;
	private ConnectionDisplayWidget connectionDisplayWidget;
	private InitialValueRefreshJob refreshJob;

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

		parameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.DefaultValue + ":"); //$NON-NLS-1$
		parameterText = createGroupText(composite, false);
		parameterText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));
		refreshJob = new InitialValueRefreshJob(null, this::updateTypeInitialValue, false);

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
		instanceCommentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), instanceCommentText.getText()));
			instanceCommentText.setForeground(getForegroundColor());
			addContentAdapter();
		});

		currentParameterTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		currentParameterEditor = new InitialValueEditor(composite, SWT.SINGLE | SWT.BORDER);
		currentParameterEditor.setCommandExecutor(this::executeCommand);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false)
				.applyTo(currentParameterEditor.getControl());

		currentVarConfigTextCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.VarConfig + ":"); //$NON-NLS-1$
		currentVarConfigCheckBox = getWidgetFactory().createButton(composite, null, SWT.CHECK);
		currentVarConfigCheckBox.addListener(SWT.Selection,
				event -> executeCommand(new ChangeVarConfigurationCommand((VarDeclaration) getType(),
						currentVarConfigCheckBox.getSelection())));

		infoSection.setClient(composite);
	}

	@Override
	protected void performRefresh() {
		refreshParameterVisibility();
		final FBNetworkElement fb = getType().getFBNetworkElement();
		if (fb != null) {
			infoSection.setText(
					MessageFormat.format(Messages.InterfaceElementSection_Instance, fb.getName(), getPinName()));
		} else { // e.g., IP address of device
			infoSection.setText(Messages.InterfaceElementSection_InterfaceElement);
		}
		typeCommentText.setText(CommentHelper.getTypeComment(getType()));

		configureOpenEditorButton();

		instanceCommentText.setText(CommentHelper.getInstanceComment(getType()));
		instanceCommentText.setForeground(getForegroundColor());

		refreshTypeInitialValue();
		currentParameterEditor.setInterfaceElement(getType());
		currentParameterEditor.refresh();

		typeText.setText(getPinTypeName());

		connectionDisplayWidget.refreshConnectionsViewer(getType());

		if (getType() instanceof final VarDeclaration verDeclaration) {
			currentVarConfigCheckBox.setSelection(verDeclaration.isVarConfig());
		}

		if (fb != null) {
			setEditable(!fb.isContainedInTypedInstance());
		}
	}

	private void configureOpenEditorButton() {
		final DataType dtp = getDataType();
		if (dtp != null) {
			openEditorButton.addListener(SWT.Selection,
					ev -> OpenStructMenu.openStructEditor(dtp.getTypeEntry().getFile()));
			openEditorButton.setEnabled(((dtp instanceof StructuredType) || (dtp instanceof AdapterType))
					&& !IecTypes.GenericTypes.isAnyType(dtp));
		}
	}

	private DataType getDataType() {
		return getType().getType();
	}

	private String getPinTypeName() {
		final StringBuilder sb = new StringBuilder();
		final String typeName = getType().getFullTypeName();
		if (typeName != null) {
			sb.append(typeName);
		}
		if (getType().getType() instanceof final StructuredType structuredType) {
			appendStructTypes(sb, structuredType);
		}
		return sb.toString();
	}

	protected void refreshTypeInitialValue() {
		if (getType() instanceof final VarDeclaration varDeclaration && varDeclaration.isIsInput()
				&& varDeclaration.getFBNetworkElement() != null
				&& varDeclaration.getFBNetworkElement().getType() != null) {
			parameterText.setText(FordiacMessages.ComputingPlaceholderValue);
			refreshJob.setInterfaceElement(varDeclaration.getFBNetworkElement().getType().getInterfaceList()
					.getInterfaceElement(varDeclaration.getName()));
			refreshJob.refresh();
		} else {
			parameterText.setText("");//$NON-NLS-1$
		}
	}

	private void updateTypeInitialValue(final String value) {
		if (!parameterText.isDisposed() && FordiacMessages.ComputingPlaceholderValue.equals(parameterText.getText())) {
			if (value.length() <= DiagramPreferencePage.getMaxDefaultValueLength()) {
				parameterText.setText(value);
			} else {
				parameterText.setText(FordiacMessages.ValueTooLarge);
			}
		}
	}

	private Color getForegroundColor() {
		if (!CommentHelper.hasComment(getType())) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	private Object getPinName() {
		return getType().getName() != null ? getType().getName() : ""; //$NON-NLS-1$
	}

	private void refreshParameterVisibility() {
		final boolean isDataIO = isDataIO();
		parameterTextCLabel.setVisible(isDataIO);
		parameterText.setVisible(isDataIO);
		currentParameterTextCLabel.setVisible(isDataIO && getType().isIsInput());
		currentParameterEditor.getControl().setVisible(isDataIO && getType().isIsInput());
		currentVarConfigTextCLabel.setVisible(isDataIO && getType().isIsInput() && getType() instanceof VarDeclaration);
		currentVarConfigCheckBox.setVisible(isDataIO && getType().isIsInput() && getType() instanceof VarDeclaration);
	}

	private boolean isDataIO() {
		if (getType() instanceof ErrorMarkerInterface) {
			return !(getType().getType() instanceof EventType) && !(getType().getType() instanceof AdapterType);
		}
		return (getType() instanceof VarDeclaration);
	}

	private void setEditable(final boolean editable) {
		currentParameterEditor.setEditable(editable);
		instanceCommentText.setEditable(editable);
		instanceCommentText.setEnabled(editable);
		connectionDisplayWidget.setEditable(editable);
		currentVarConfigCheckBox.setEnabled(editable);
	}

	// this method will be removed as soon as there is a toString for StructType in
	// the model
	private static String appendStructTypes(final StringBuilder sb, final StructuredType st) {
		final EList<VarDeclaration> list = st.getMemberVariables();
		sb.append(": ("); //$NON-NLS-1$
		boolean printString = false;
		for (final VarDeclaration v : list) {
			if ((v.getType() != null)) {
				sb.append(v.getType().getName());
				printString = true;
			} else {
				sb.append("not set"); //$NON-NLS-1$
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

	@Override
	public void dispose() {
		super.dispose();
		if (refreshJob != null) {
			refreshJob.cancel();
		}
	}
}
