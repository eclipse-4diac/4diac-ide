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
import org.eclipse.fordiac.ide.model.FordiacKeywords;
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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class CreateInterfaceElementCommand extends AbstractCreationCommand {
	private final boolean isInput;
	private final DataType dataType;
	private IInterfaceElement interfaceElement;
	private EList<? extends IInterfaceElement> interfaces;
	private final int index;
	private final InterfaceList interfaceList;
	private AdapterCreateCommand cmd;
	private final String name;

	public CreateInterfaceElementCommand(final DataType dataType, final InterfaceList interfaceList, final boolean isInput, final int index) {
		this(dataType, getNameProposal(dataType, isInput), interfaceList, isInput, index);
	}

	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList, final boolean isInput,
			final int index) {
		this.isInput = isInput;
		this.dataType = dataType;
		this.index = index;
		this.interfaceList = interfaceList;
		this.name = (null != name) ? name : getNameProposal(dataType, isInput);
	}

	private static String getNameProposal(final DataType dataType, final boolean isInput) {
		if (dataType instanceof EventType) {
			return isInput ? FordiacKeywords.EVENT_INPUT : FordiacKeywords.EVENT_OUTPUT;
		}
		if (dataType instanceof AdapterType) {
			return isInput ? FordiacKeywords.ADAPTER_SOCKET : FordiacKeywords.ADAPTER_PLUG;
		}
		return isInput ? FordiacKeywords.DATA_INPUT : FordiacKeywords.DATA_OUTPUT;
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

	@Override
	public boolean canExecute() {
		return (null != dataType) && (null != interfaceList);
	}

	private void setInterfaces(final InterfaceList interfaceList) {
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
				if (isInput) {
					((VarDeclaration) interfaceElement).setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
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
		final
		EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaces;
		temp.remove(interfaceElement);
		if ((null != cmd) && cmd.canExecute()) {
			cmd.undo();
		}
	}

	private void insertElement() {
		@SuppressWarnings("unchecked")
		final
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
	public IInterfaceElement getCreatedElement() {
		return interfaceElement;
	}
}
