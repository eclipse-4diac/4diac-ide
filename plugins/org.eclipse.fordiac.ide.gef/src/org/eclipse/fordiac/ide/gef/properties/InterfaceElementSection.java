/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Johannes Kepler University Linz
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
 *   Alois Zoitl - fixed sub-app type update, code clean-up
 *               - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InterfaceElementSection extends AbstractSection {
	private Text nameText;
	private Text commentText;
	protected CCombo typeCombo;
	private Text parameterText;
	private CLabel valueCLabel;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTypeAndCommentSection(parent);
	}

	protected void createTypeAndCommentSection(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, FordiacMessages.Type + ":"); //$NON-NLS-1$
		final Composite typeComp = getWidgetFactory().createComposite(composite);
		typeComp.setLayout(new GridLayout(2, false));
		typeComp.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		typeCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), typeComp);
		typeCombo.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		typeCombo.addListener(SWT.Selection, event -> {
			Command cmd = null;
			if (getType() instanceof AdapterDeclaration) {
				final DataType newType = getTypeLibrary().getAdapterTypeEntry(typeCombo.getText()).getType();
				cmd = newChangeTypeCommand((VarDeclaration) getType(), newType);
			} else if (getType() instanceof final VarDeclaration varDecl) {
				cmd = newChangeTypeCommand(varDecl, getDataTypeLib().getType(typeCombo.getText()));
			}
			executeCommand(cmd);
		});
		valueCLabel = getWidgetFactory().createCLabel(composite, FordiacMessages.InitialValue + ":"); //$NON-NLS-1$
		parameterText = createGroupText(composite, true);
		parameterText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeValueCommand((VarDeclaration) getType(), parameterText.getText()));
			addContentAdapter();
		});
	}

	private void fillTypeCombo(final String text) {
		typeCombo.removeAll();
		if (getType() instanceof Event) {
			EventTypeLibrary.getInstance().getEventTypes().forEach(eType -> typeCombo.add(eType.getName()));
		} else if (getType() instanceof AdapterDeclaration) {
			getTypeLibrary().getAdapterTypesSorted().forEach(adp -> typeCombo.add(adp.getType().getName()));
		} else if (getType() instanceof VarDeclaration) {
			getDataTypeLib().getDataTypesSorted().forEach(dataType -> typeCombo.add(dataType.getName()));
		}

		if (typeCombo.getItems().length > 0) {
			typeCombo.setText(text);
		}
	}

	@Override
	protected void performRefresh() {
		setEditableFields(getType().getFBNetworkElement() instanceof SubApp);
		nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
		commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
		String itype = ""; //$NON-NLS-1$
		if (getType() instanceof final VarDeclaration varDecl) {
			itype = varDecl.getType() != null ? varDecl.getTypeName() : ""; //$NON-NLS-1$
			if (getType().isIsInput()) {
				parameterText.setVisible(true);
				valueCLabel.setVisible(true);
				parameterText.setText((varDecl.getValue() != null) ? varDecl.getValue().getValue() : ""); //$NON-NLS-1$
			} else {
				valueCLabel.setVisible(false);
				parameterText.setVisible(false);
			}
		} else {
			itype = FordiacMessages.Event;
			valueCLabel.setVisible(false);
			parameterText.setVisible(false);
		}
		fillTypeCombo(itype);
	}

	/**
	 * Set the input fields edit able or not
	 *
	 * @param editAble flag indicating if the fields should be editable
	 */
	private void setEditableFields(final boolean editAble) {
		nameText.setEditable(editAble);
		nameText.setEnabled(editAble);
		commentText.setEditable(editAble);
		commentText.setEnabled(editAble);
		// if it should be editable only allow to change the type if there is no
		// connection attached
		typeCombo.setEnabled(
				editAble && getType().getInputConnections().isEmpty() && getType().getOutputConnections().isEmpty());

	}

	@SuppressWarnings("static-method") // this method allows sub-classes to provide own change type commands, e.g.,
	// subapps
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return ChangeDataTypeCommand.forDataType(data, newType);
	}

	@Override
	protected IInterfaceElement getInputType(final Object input) {
		final Object objToCheck = (input instanceof final EditPart ep) ? ep.getModel() : input;

		if (objToCheck instanceof final IInterfaceElement ie) {
			return ie;
		}
		if (input instanceof final Value value) {
			return value.getParentIE();
		}
		return null;
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected void setInputInit() {
		// currently nothing needs to be done here
	}

	@Override
	protected void setInputCode() {
		// currently nothing needs to be done here
	}
}
