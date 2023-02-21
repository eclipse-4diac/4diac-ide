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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
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
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateInterfaceElementCommand extends CreationCommand {
	private IInterfaceElement newInterfaceElement;

	private final String name;
	private final DataType dataType;
	private final boolean isInput;
	private final int index;
	private int arraySize;
	private String value;

	private AdapterCreateCommand adapterCreateCmd;

	private EList<? extends IInterfaceElement> interfaceElementList;
	private final InterfaceList targetInterfaceList;

	/** constructor for copying an interface element */
	public CreateInterfaceElementCommand(final IInterfaceElement copySrc, final boolean isInput,
			final InterfaceList targetInterfaceList,
			final int index) {
		this(copySrc.getType(), copySrc.getName(), targetInterfaceList, isInput, index);
		newInterfaceElement = EcoreUtil.copy(copySrc);
	}

	/** constructor for creating a new interface element based on the provided information */
	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final boolean isInput, final int index) {
		this.isInput = isInput;
		this.dataType = dataType;
		this.index = index;
		this.targetInterfaceList = interfaceList;
		this.name = ((null != name) && isValidName(name)) ? name : getNameProposal(dataType, isInput);
		this.value = ""; //$NON-NLS-1$
	}

	private boolean isValidName(final String name) {
		return !IdentifierVerifier.verifyIdentifier(name).isPresent();
	}

	public CreateInterfaceElementCommand(final DataType dataType, final InterfaceList interfaceList,
			final boolean isInput, final int index) {
		this(dataType, getNameProposal(dataType, isInput), interfaceList, isInput, index);
	}

	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final boolean isInput, final int arraySize, final String value, final int index) {
		this(dataType, name, interfaceList, isInput, index);
		this.arraySize = arraySize;
		this.value = value != null ? value : ""; //$NON-NLS-1$
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

	protected InterfaceList getInterfaceList() {
		return targetInterfaceList;
	}

	@Override
	public boolean canExecute() {
		// we allow copying or new elements with valid datatype
		return (isValidCopySource() || canCreateValidNewElement()) && (null != targetInterfaceList);
	}

	private boolean canCreateValidNewElement() {
		return null != dataType;
	}

	private boolean isValidCopySource() {
		return (null != newInterfaceElement) && (null != newInterfaceElement.getType());
	}

	private void selectInterface(final InterfaceList interfaceList) {
		if (isInput) {
			if (dataType instanceof EventType) {
				this.interfaceElementList = interfaceList.getEventInputs();
			} else if (dataType instanceof AdapterType) {
				this.interfaceElementList = interfaceList.getSockets();
			} else {
				this.interfaceElementList = interfaceList.getInputVars();
			}
		} else if (dataType instanceof EventType) {
			this.interfaceElementList = interfaceList.getEventOutputs();
		} else if (dataType instanceof AdapterType) {
			this.interfaceElementList = interfaceList.getPlugs();
		} else {
			this.interfaceElementList = interfaceList.getOutputVars();
		}
	}

	@Override
	public void execute() {
		selectInterface(targetInterfaceList);
		if (newInterfaceElement == null) {
			createNewInterfaceElement();
		} else {
			finalizeCopyInterfaceElement();
		}
		createAdapterCreateCommand();
		insertElement();
		if (null != adapterCreateCmd) {
			adapterCreateCmd.execute();
		}
		newInterfaceElement.setName(NameRepository.createUniqueName(newInterfaceElement, name));
	}

	private void finalizeCopyInterfaceElement() {
		newInterfaceElement.setIsInput(isInput);
	}

	private void createNewInterfaceElement() {
		if (dataType instanceof EventType) {
			newInterfaceElement = LibraryElementFactory.eINSTANCE.createEvent();
		} else if (dataType instanceof AdapterType) {
			newInterfaceElement = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		} else {
			final VarDeclaration varDeclaration = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			varDeclaration.setArraySize(arraySize);
			if (isInput) {
				varDeclaration.setValue(LibraryElementFactory.eINSTANCE.createValue());
				varDeclaration.getValue().setValue(value);
			}
			newInterfaceElement = varDeclaration;
		}

		newInterfaceElement.setIsInput(isInput);
		newInterfaceElement.setType(dataType);
		newInterfaceElement.setTypeName(dataType.getName());
	}

	@Override
	public void redo() {
		insertElement();
		if (null != adapterCreateCmd) {
			adapterCreateCmd.redo();
		}
	}

	@Override
	public void undo() {
		interfaceElementList.remove(newInterfaceElement);
		if ((null != adapterCreateCmd) && adapterCreateCmd.canExecute()) {
			adapterCreateCmd.undo();
		}
	}

	private void insertElement() {
		@SuppressWarnings("unchecked")
		final EList<IInterfaceElement> temp = (EList<IInterfaceElement>) interfaceElementList;
		final int insertionPos = index == -1 ? temp.size() : index;
		if (insertionPos > temp.size()) {
			temp.add(newInterfaceElement);
		} else {
			temp.add(insertionPos, newInterfaceElement);
		}
	}

	private void createAdapterCreateCommand() {
		if (isInternalAdapterInCompositeFB()) {
			final int xyPos = 10;
			adapterCreateCmd = new AdapterCreateCommand(xyPos, xyPos, (AdapterDeclaration) newInterfaceElement,
					(CompositeFBType) targetInterfaceList.eContainer());
		}
	}

	private boolean isInternalAdapterInCompositeFB() {
		return (dataType instanceof AdapterType) && (targetInterfaceList.eContainer() instanceof CompositeFBType)
				&& !(targetInterfaceList.eContainer() instanceof SubAppType);
	}

	@Override
	public IInterfaceElement getCreatedElement() {
		return newInterfaceElement;
	}

	// for use in subclasses that can have mapped elements (i.e., untyped subapps)
	protected CreateInterfaceElementCommand createMirroredInterfaceElement() {
		// if the subapp is mapped we need to created the interface element also in the
		// opposite entry
		final CreateInterfaceElementCommand mirroredCreateCmd = new CreateInterfaceElementCommand(newInterfaceElement,
				newInterfaceElement.isIsInput(), newInterfaceElement.getFBNetworkElement().getOpposite().getInterface(),
				index);
		mirroredCreateCmd.execute();
		// Set the same name as the one we have also on the mirrored
		mirroredCreateCmd.getCreatedElement().setName(getCreatedElement().getName());
		return mirroredCreateCmd;
	}
}
