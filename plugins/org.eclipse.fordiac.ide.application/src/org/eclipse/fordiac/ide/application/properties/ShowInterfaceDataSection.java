/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Germany GmbH
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
 *   Daniel Lindhuber
 *     - updated label provider
 *   Fabio Gandolfi - removed special label providers
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.properties.ShowInterfaceEventSection.CellImmutableModifier;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ShowInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected FBNetworkElement getInputType(final Object input) {
		if ((input instanceof SubAppForFBNetworkEditPart) || (input instanceof AbstractFBNElementEditPart)) {
			return (FBNetworkElement) ((EditPart) input).getModel();
		}
		if (input instanceof FBNetworkElement) {
			return (FBNetworkElement) input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
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
	protected CreateInterfaceElementCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		return null;
	}

	@Override
	protected CreateInterfaceElementCommand newInsertCommand(final IInterfaceElement interfaceElement,
			final boolean isInput,
			final int index) {
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return null;
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return null;
	}

	@Override
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return null;
	}

	@Override
	protected FBNetworkElement getType() {
		return (FBNetworkElement) type;
	}

	@Override
	protected InterfaceCellModifier getCellModifier(final TableViewer viewer) {
		return new DataInterfaceCellModifier(viewer) {
			@Override
			public boolean canModify(final Object element, final String property) {
				return false;
			}
		};
	}

	@Override
	protected void setTableInput() {
		final FBNetworkElement selection = getType();
		if(selection instanceof Multiplexer) {
			getInputsViewer().setContentProvider(new ArrayContentProvider());
			getInputsViewer().setInput(((StructManipulator) selection).getStructType().getMemberVariables());
			getOutputsViewer().setInput(selection.getType());
		} else if (selection instanceof Demultiplexer) {
			getOutputsViewer().setContentProvider(new ArrayContentProvider());
			getOutputsViewer().setInput(((StructManipulator) selection).getStructType().getMemberVariables());
			getInputsViewer().setInput(selection.getType());

		} else if (selection.getType() != null) { // untyped subapp in typed subapp
			getInputsViewer().setInput(selection.getType());
			getOutputsViewer().setInput(selection.getType());
		} else {
			// untyped subapp in typed subapp
			super.setTableInput();
		}

	}
}
