/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 * 				 2020 - 2023 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method & cell editor classes
 *   Martin Melik Merkumians - added option for setting VAR CONFIG on inputs
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationListProvider;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationWithVarConfigColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationWithVarConfigColumnProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionButton;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

public class EditUntypedSubappInterfaceSection extends AbstractEditInterfaceDataSection {

	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final DataType last = getLastUsedDataType(getType().getInterface(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(last,
				getCreationName(interfaceElement), getType().getInterface(), isInput, pos);
		if (getType().isUnfolded()) {
			// if the group is expanded we need to check if the subapp needs to be expanded
			return ResizingSubappInterfaceCreationCommand.createCommand(cmd, getType());
		}
		return cmd;
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(interfaceElement,
				isInput, getType().getInterface(), index);
		if (getType().isUnfolded()) {
			// if the group is expanded we need to check if the subapp needs to be expanded
			return ResizingSubappInterfaceCreationCommand.createCommand(cmd, getType());
		}
		return cmd;
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		inputProvider = new VarDeclarationListProvider(null, new VarDeclarationWithVarConfigColumnAccessor(this, null));
		final DataLayer inputDataLayer = setupDataLayer(inputProvider);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer,
				new VarDeclarationWithVarConfigColumnProvider(), rule, new DataTypeSelectionButton(typeSelection), this,
				Boolean.TRUE);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();
	}

	@Override
	protected DataLayer setupDataLayer(final ListDataProvider<VarDeclaration> provider) {
		final DataLayer dataLayer = new DataLayer(provider);
		final IConfigLabelAccumulator labelAcc = dataLayer.getConfigLabelAccumulator();

		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (labelAcc != null) {
				labelAcc.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			if (isEditable() && columnPosition == I4diacNatTableUtil.TYPE) {
				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			} else if (isEditable() && columnPosition == I4diacNatTableUtil.INITIAL_VALUE) {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			} else if (columnPosition == I4diacNatTableUtil.NAME || columnPosition == I4diacNatTableUtil.COMMENT) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			} else if (columnPosition == I4diacNatTableUtil.VAR_CONFIG) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			}
		});
		return dataLayer;
	}

	protected LabelProvider getLabelProvider() {
		return new DataLabelProvider() {

			@Override
			public Color getBackground(final Object element, final int columnIndex) {
				if ((columnIndex == INITIALVALUE_COL_INDEX) && (!((VarDeclaration) element).isIsInput())) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
				}
				return null;
			}

			@Override
			public String getColumnText(final Object element, final int columnIndex) {
				if ((columnIndex == INITIALVALUE_COL_INDEX) && !((VarDeclaration) element).isIsInput()) {
					return "-"; //$NON-NLS-1$
				}
				return super.getColumnText(element, columnIndex);
			}

		};
	}

	@Override
	protected SubApp getInputType(final Object input) {
		if (input instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) input).getModel();
		}
		if (input instanceof UISubAppNetworkEditPart) {
			return ((UISubAppNetworkEditPart) input).getSubApp();
		}
		if (input instanceof SubApp) {
			return (SubApp) input;
		}
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	protected void setTableInputFBType(final FBType type) {
		// TODO Auto-generated method stub

	}

}
