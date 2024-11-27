/*******************************************************************************
 * Copyright (c) 2017, 2023 fortiss GmbH, Johannes Kepler University Linz
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

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateInterfaceElementCommand extends CreationCommand implements ScopedCommand {
	private IInterfaceElement newInterfaceElement;

	private final String name;
	private final DataType dataType;
	private final boolean isInput;
	private final boolean isInOut;
	private boolean switchOpposite;
	private final int index;
	private final String arraySize;
	private final String value;

	private AdapterFBCreateCommand adapterCreateCmd;

	private final InterfaceList targetInterfaceList;

	/** constructor for copying an interface element */
	public CreateInterfaceElementCommand(final IInterfaceElement copySrc, final boolean isInput,
			final InterfaceList targetInterfaceList, final int index) {
		this(copySrc.getType(), copySrc.getName(), targetInterfaceList, isInput, index);
		newInterfaceElement = EcoreUtil.copy(copySrc);
	}

	/**
	 * constructor for creating a new interface element based on the provided
	 * information
	 */
	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final boolean isInput, final boolean isInOut, final String arraySize, final int index) {
		this.isInput = isInput;
		this.isInOut = isInOut;
		this.switchOpposite = false;
		this.dataType = dataType;
		this.index = index;
		this.targetInterfaceList = interfaceList;
		this.name = ((null != name) && isValidName(name)) ? name : getNameProposal(dataType, isInput);
		this.arraySize = arraySize;
		this.value = ""; //$NON-NLS-1$
	}

	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final boolean isInput, final String arraySize, final int index) {
		this(dataType, name, interfaceList, isInput, false, arraySize, index);
	}

	public CreateInterfaceElementCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final boolean isInput, final int index) {
		this(dataType, name, interfaceList, isInput, null, index);
	}

	public CreateInterfaceElementCommand(final DataType dataType, final InterfaceList interfaceList,
			final boolean isInput, final int index) {
		this(dataType, getNameProposal(dataType, isInput), interfaceList, isInput, index);
	}

	private static boolean isValidName(final String name) {
		return !IdentifierVerifier.verifyIdentifier(name).isPresent();
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

	protected EList<? extends IInterfaceElement> getInterfaceListContainer() {
		if (dataType instanceof EventType) {
			return isInput ? targetInterfaceList.getEventInputs() : targetInterfaceList.getEventOutputs();
		}
		if (dataType instanceof AdapterType) {
			return isInput ? targetInterfaceList.getSockets() : targetInterfaceList.getPlugs();
		}
		return isInOut
				? switchOpposite ? targetInterfaceList.getOutMappedInOutVars() : targetInterfaceList.getInOutVars()
				: isInput ? targetInterfaceList.getInputVars() : targetInterfaceList.getOutputVars();
	}

	@Override
	public void execute() {
		if (newInterfaceElement == null) {
			createNewInterfaceElement();
		} else {
			finalizeCopyInterfaceElement();
		}
		createValue();
		createAdapterFBCreateCommand();
		insertElement();
		newInterfaceElement.setName(NameRepository.createUniqueName(newInterfaceElement, name));
		if (!isInput && isInOut) {
			switchOpposite = true;
			newInterfaceElement = ((VarDeclaration) newInterfaceElement).getInOutVarOpposite();
		}
		if (null != adapterCreateCmd) {
			adapterCreateCmd.execute();
		}
	}

	private void createValue() {
		if (newInterfaceElement instanceof final VarDeclaration varDecl && (isInput || isInOut)) {
			varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varDecl.getValue().setValue(value);
		}

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
			ArraySizeHelper.setArraySize(varDeclaration, arraySize);
			newInterfaceElement = varDeclaration;
		}

		newInterfaceElement.setIsInput(isInput || isInOut);
		newInterfaceElement.setType(dataType);
	}

	@Override
	public void redo() {
		insertElement();
		if (!isInput && isInOut) {
			switchOpposite = true;
			newInterfaceElement = ((VarDeclaration) newInterfaceElement).getInOutVarOpposite();
		}
		if (null != adapterCreateCmd) {
			adapterCreateCmd.redo();
		}
	}

	@Override
	public void undo() {
		if (switchOpposite) {
			switchOpposite = false;
			newInterfaceElement = ((VarDeclaration) newInterfaceElement).getInOutVarOpposite();
		}
		getInterfaceListContainer().remove(newInterfaceElement);
		if ((null != adapterCreateCmd) && adapterCreateCmd.canExecute()) {
			adapterCreateCmd.undo();
		}
	}

	private void insertElement() {
		@SuppressWarnings("unchecked")
		final EList<IInterfaceElement> temp = (EList<IInterfaceElement>) getInterfaceListContainer();
		final int insertionPos = index == -1 ? temp.size() : index;
		if (insertionPos > temp.size()) {
			temp.add(newInterfaceElement);
		} else {
			temp.add(insertionPos, newInterfaceElement);
		}
	}

	private void createAdapterFBCreateCommand() {
		if (dataType instanceof AdapterType) {
			final int xyPos = 10;
			adapterCreateCmd = new AdapterFBCreateCommand(xyPos, xyPos, (AdapterDeclaration) newInterfaceElement,
					(targetInterfaceList.eContainer() instanceof final FBType fbType) ? fbType : null);
		}
	}

	@Override
	public IInterfaceElement getCreatedElement() {
		return newInterfaceElement;
	}

	protected int getIndex() {
		return index;
	}

	public InterfaceList getTargetInterfaceList() {
		return targetInterfaceList;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (targetInterfaceList != null) {
			if (targetInterfaceList.eContainer() != null) {
				return Set.of(targetInterfaceList.eContainer());
			}
			return Set.of(targetInterfaceList);
		}
		return Set.of();
	}
}
