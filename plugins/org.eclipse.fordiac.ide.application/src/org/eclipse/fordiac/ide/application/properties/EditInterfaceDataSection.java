/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class EditInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(IInterfaceElement interfaceElement, boolean isInput) {
		DataType last = getLastUsedDataType(getType().getInterface(), isInput, interfaceElement);
		int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateSubAppInterfaceElementCommand(last, getCreationName(interfaceElement),
				getType().getInterface(), isInput, pos);
	}

	@Override
	protected InsertInterfaceElementCommand newInsertCommand(IInterfaceElement interfaceElement, boolean isInput,
			int index) {
		DataType last = getLastUsedDataType(getType().getInterface(), isInput, interfaceElement);
		return new InsertSubAppInterfaceElementCommand(interfaceElement, last, getType().getInterface(), isInput,
				index);
	}

	@Override
	protected InterfaceLabelProvider getLabelProvider() {
		return new DataInterfaceLabelProvider() {

			@Override
			public Color getBackground(Object element, int columnIndex) {
				if ((columnIndex == INITIALVALUE_COLUMN) && (!((VarDeclaration) element).isIsInput())) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
				}
				return null;
			}

			@Override
			public String getColumnText(Object element, int columnIndex) {
				if (columnIndex == INITIALVALUE_COLUMN && !((VarDeclaration) element).isIsInput()) {
					return "-"; //$NON-NLS-1$
				} else {
					return super.getColumnText(element, columnIndex);
				}
			}

		};
	}

	@Override
	protected InterfaceCellModifier getCellModifier(TableViewer viewer) {
		return new DataInterfaceCellModifier(viewer) {
			@Override
			public boolean canModify(Object element, String property) {
				if (INITIAL_VALUE.equals(property)) {
					return ((VarDeclaration) element).isIsInput();
				}
				return super.canModify(element, property);
			}

			@Override
			public Object getValue(Object element, String property) {
				if (property.equals(INITIAL_VALUE) && !((VarDeclaration) element).isIsInput()) {
					return "-"; //$NON-NLS-1$
				} else {
					return super.getValue(element, property);
				}
			}
		};
	}

	@Override
	protected SubApp getInputType(Object input) {
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
	protected DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected ChangeTypeCommand newChangeTypeCommand(VarDeclaration data, DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	protected TypeLibrary getTypeLibrary() {
		return getType().getFbNetwork().getAutomationSystem().getPalette().getTypeLibrary();
	}

}
