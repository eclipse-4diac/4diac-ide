/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.properties.ShowInterfaceEventSection.CellImmutableModifier;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceAdapterSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ShowInterfaceAdapterSection extends AbstractEditInterfaceAdapterSection {
	@Override
	protected FBNetworkElement getInputType(Object input) {
		if ((input instanceof SubAppForFBNetworkEditPart) || (input instanceof AbstractFBNElementEditPart)) {
			return (FBNetworkElement) ((EditPart) input).getModel();
		}
		if (input instanceof FBNetworkElement) {
			return (FBNetworkElement) input;
		}
		return null;
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		createButtons = false;
		super.createControls(parent, tabbedPropertySheetPage);
		final Table inputTable = (Table) getInputsViewer().getControl();
		inputTable.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		inputTable.setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		getInputsViewer().setCellModifier(new CellImmutableModifier());

		final Table outputTable = (Table) getOutputsViewer().getControl();
		outputTable.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		outputTable.setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		getOutputsViewer().setCellModifier(new CellImmutableModifier());
	}

	@Override
	protected CreateInterfaceElementCommand newCreateCommand(IInterfaceElement interfaceElement, boolean isInput) {
		return null;
	}

	@Override
	protected InsertInterfaceElementCommand newInsertCommand(IInterfaceElement interfaceElement, boolean isInput,
			int index) {
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection) {
		return null;
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp) {
		return null;
	}

	@Override
	protected ChangeTypeCommand newChangeTypeCommand(VarDeclaration data, DataType newType) {
		return null;
	}

	@Override
	protected FBNetworkElement getType() {
		return (FBNetworkElement) type;
	}

	@Override
	protected TypeLibrary getTypeLibrary() {
		return getType().getTypeLibrary();
	}
}
