/*******************************************************************************
 * Copyright (c) 2016, 2025 fortiss GmbH, Johannes Kepler University Linz,
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.editors.InitialValueEditor;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
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
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractDoubleColumnSection {
	private Text typeText;
	private Text typeCommentText;
	private Text instanceCommentText;
	private Text parameterText;
	private InitialValueEditor currentParameterEditor;
	private Label parameterLabel;
	private Label currentParameterLabel;
	private Label currentVarConfigLabel;
	private Button currentVarConfigCheckBox;
	private Button openEditorButton;
	private Listener openEditorListener;
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
		final Section typeInfoSection = getWidgetFactory().createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		typeInfoSection.setText(FordiacMessages.TypeInfo + ":"); //$NON-NLS-1$
		typeInfoSection.setLayout(new GridLayout(1, false));
		typeInfoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = getWidgetFactory().createComposite(typeInfoSection);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		getWidgetFactory().createLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		typeCommentText = createGroupText(composite, false);
		typeCommentText.setLayoutData(new GridData(SWT.FILL, 0, true, false, 2, 1));

		getWidgetFactory().createLabel(composite, FordiacMessages.Type + ":"); //$NON-NLS-1$
		typeText = createGroupText(composite, false);

		openEditorButton = new Button(typeText.getParent(), SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		parameterLabel = getWidgetFactory().createLabel(composite, FordiacMessages.DefaultValue + ":"); //$NON-NLS-1$
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

		getWidgetFactory().createLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		instanceCommentText = createGroupText(composite, true);
		instanceCommentText.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false));
		instanceCommentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), instanceCommentText.getText()));
			addContentAdapter();
		});

		currentParameterLabel = getWidgetFactory().createLabel(composite, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		currentParameterEditor = new InitialValueEditor(composite, SWT.SINGLE | SWT.BORDER);
		currentParameterEditor.setCommandExecutor(this::executeCommand);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false)
				.applyTo(currentParameterEditor.getControl());
		currentParameterEditor.getControl()
				.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		currentVarConfigLabel = getWidgetFactory().createLabel(composite, FordiacMessages.VarConfig + ":"); //$NON-NLS-1$
		currentVarConfigCheckBox = getWidgetFactory().createButton(composite, null, SWT.CHECK);
		currentVarConfigCheckBox.addListener(SWT.Selection,
				event -> executeCommand(new ChangeVarConfigurationCommand((VarDeclaration) getType(),
						currentVarConfigCheckBox.getSelection())));

		infoSection.setClient(composite);
	}

	@Override
	protected void performRefresh() {
		refreshParameterVisibility();
		final FBNetworkElement fb = getType().getBlockFBNetworkElement();
		if (fb != null) {
			infoSection.setText(
					MessageFormat.format(Messages.InterfaceElementSection_Instance, fb.getName(), getPinName()));
		} else {
			infoSection.setText(Messages.InterfaceElementSection_InterfaceElement);
		}
		typeCommentText.setText(CommentHelper.getTypeComment(getType()));

		configureOpenEditorButton();

		instanceCommentText.setText(CommentHelper.getInstanceComment(getType()));

		refreshTypeInitialValue();
		currentParameterEditor.setInterfaceElement(getType());
		currentParameterEditor.refresh();

		typeText.setText(getPinTypeName());

		connectionDisplayWidget.refreshConnectionsViewer(getType());

		if (getType() instanceof final VarDeclaration varDeclaration) {
			currentVarConfigCheckBox.setSelection(varDeclaration.isVarConfig());
		}

		if (fb != null) {
			setEditable(!fb.isContainedInTypedInstance() && !containsCFB(getType().eContainer()));
		}
	}

	private static boolean containsCFB(EObject container) {
		while (container != null) {
			if (container instanceof CFBInstance || container instanceof CompositeFBType) {
				return true;
			}
			container = container.eContainer();
		}
		return false;
	}

	private void configureOpenEditorButton() {
		if (openEditorListener != null) {
			openEditorButton.removeListener(SWT.Selection, openEditorListener);
			openEditorListener = null;
		}

		final DataType dataType = getDataType();
		if (dataType != null) {
			openEditorListener = ev -> OpenStructMenu.openStructEditor(dataType.getTypeEntry().getFile());
			openEditorButton.addListener(SWT.Selection, openEditorListener);
			openEditorButton.setEnabled((dataType instanceof StructuredType || dataType instanceof AdapterType)
					&& !IecTypes.GenericTypes.isAnyType(dataType));
		} else {
			openEditorButton.setEnabled(false);
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
				&& varDeclaration.getBlockFBNetworkElement() != null) {
			final VarDeclaration typeVar = varDeclaration.findInTypeInterface();
			if (typeVar != null) {
				parameterText.setText(FordiacMessages.ComputingPlaceholderValue);
				refreshJob.setInterfaceElement(typeVar);
				refreshJob.refresh();
			}
		} else {
			parameterText.setText(""); //$NON-NLS-1$
		}
	}

	private void updateTypeInitialValue(final String value) {
		if (!parameterText.isDisposed() && FordiacMessages.ComputingPlaceholderValue.equals(parameterText.getText())) {
			if (value.length() <= PreferenceStoreProvider
					.getStore(GefPreferenceConstants.GEF_PREFERENCES_ID, getTypeLibrary().getProject())
					.getInt(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH)) {
				parameterText.setText(value);
			} else {
				parameterText.setText(FordiacMessages.ValueTooLarge);
			}
		}
	}

	private String getPinName() {
		final String pinName = getType().getRelativeName(getType().getBlockFBNetworkElement());
		return pinName != null ? pinName : ""; //$NON-NLS-1$
	}

	private void refreshParameterVisibility() {
		final boolean isDataIO = isDataIO();
		parameterLabel.setVisible(isDataIO);
		parameterText.setVisible(isDataIO);

		final boolean isDataInput = isDataIO && getType().isIsInput();
		currentParameterLabel.setVisible(isDataInput);
		currentParameterEditor.getControl().setVisible(isDataInput);

		final boolean isVarDeclInput = isDataInput && getType() instanceof VarDeclaration;
		currentVarConfigLabel.setVisible(isVarDeclInput);
		currentVarConfigCheckBox.setVisible(isVarDeclInput);
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

	private static void appendStructTypes(final StringBuilder sb, final StructuredType st) {
		final EList<VarDeclaration> list = st.getMemberVariables();
		sb.append(": ("); //$NON-NLS-1$
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(", "); //$NON-NLS-1$
			}
			final String typeName = list.get(i).getFullTypeName();
			sb.append(typeName != null ? typeName : "not set"); //$NON-NLS-1$
		}
		sb.append(')');
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
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
	public void dispose() {
		super.dispose();
		if (refreshJob != null) {
			refreshJob.cancel();
		}
	}
}