/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - command now contains newly created element
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class CreateInterfaceElementCommand extends AbstractCreationCommand {
	private boolean isInput;
	private DataType dataType;
	private IInterfaceElement interfaceElement;

	private EList<? extends IInterfaceElement> interfaces;
	private int index;
	private InterfaceList interfaceList;
	private AdapterCreateCommand cmd;
	private String name;

	public CreateInterfaceElementCommand(DataType dataType, InterfaceList interfaceList, boolean isInput, int index) {
		this(dataType, getNameProposal(dataType, isInput), interfaceList, isInput, index);
	}

	public CreateInterfaceElementCommand(DataType dataType, String name, InterfaceList interfaceList, boolean isInput,
			int index) {
		this.isInput = isInput;
		this.dataType = dataType;
		this.index = index;
		this.interfaceList = interfaceList;
		this.name = (null != name) ? name : getNameProposal(dataType, isInput);
	}

	private static String getNameProposal(DataType dataType, boolean isInput) {
		if (dataType instanceof EventType) {
			return isInput ? "EI1" 	: "EO1"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (dataType instanceof AdapterType) {
			return isInput ? "SOCKET1" : "PLUG1"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (dataType instanceof DataType) {
			return isInput ? "DI1"	: "DO1"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return ""; //$NON-NLS-1$
	}

	protected boolean isInput() {
		return isInput;
	}

	protected DataType getDataType() {
		return dataType;
	}

	protected InterfaceList getInterfaceList() {
		return interfaceList;
	}

	protected int getIndex() {
		return index;
	}

	public IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}

	@Override
	public boolean canExecute() {
		return (null != dataType) && (null != interfaceList);
	}

	private void setInterfaces(InterfaceList interfaceList) {
		if (isInput) {
			if (dataType instanceof EventType) {
				this.interfaces = interfaceList.getEventInputs();
			} else {
				if (dataType instanceof AdapterType) {
					this.interfaces = interfaceList.getSockets();
				} else {
					this.interfaces = interfaceList.getInputVars();
				}
			}
		} else {
			if (dataType instanceof EventType) {
				this.interfaces = interfaceList.getEventOutputs();
			} else {
				if (dataType instanceof AdapterType) {
					this.interfaces = interfaceList.getPlugs();
				} else {
					this.interfaces = interfaceList.getOutputVars();
				}
			}
		}
	}

	@Override
	public void execute() {
		if (dataType instanceof EventType) {
			interfaceElement = LibraryElementFactory.eINSTANCE.createEvent();
		} else {
			if (dataType instanceof AdapterType) {
				interfaceElement = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
			} else {
				interfaceElement = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			}
		}
		setInterfaces(interfaceList);
		interfaceElement.setIsInput(isInput);
		interfaceElement.setType(dataType);
		interfaceElement.setTypeName(dataType.getName());
		createAdapterCreateCommand();
		insertElement();
		if (null != cmd) {
			cmd.execute();
		}
		interfaceElement.setName(NameRepository.createUniqueName(interfaceElement, name));
	}

	@Override
	public void redo() {
		insertElement();
		if (null != cmd) {
			cmd.redo();
		}
	}

	@Override
	public void undo() {
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.remove(interfaceElement);
		if ((null != cmd) && cmd.canExecute()) {
			cmd.undo();
		}
	}

	private void insertElement() {
		@SuppressWarnings("unchecked")
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;

		temp.add(index == -1 ? temp.size() : index, interfaceElement);
	}

	private void createAdapterCreateCommand() {
		if ((dataType instanceof AdapterType) && (interfaceList.eContainer() instanceof CompositeFBType)
				&& !(interfaceList.eContainer() instanceof SubAppType)) { // only show the internal adapter FBs for
			// composite and not subapp types
			cmd = new AdapterCreateCommand(10, 10, (AdapterDeclaration) interfaceElement,
					(CompositeFBType) interfaceList.eContainer());
		}
	}

	@Override
	public Object getCreatedElement() {
		return interfaceElement;
	}
}
