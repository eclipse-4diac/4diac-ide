/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and documentation
 *   Alois Zoitl     - migrated mux and demux to new struct member access
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.ArrayList;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.helpers.VarDeclarationFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * Helper class for methods that will be - accessed from the model
 */
public final class ConfigurableFBManagement {

	static void updateConfiguration(final StructManipulator muxer) {
		if (!(muxer.getDataType() instanceof StructuredType)) {
			// e.g., error data type
			getMuxedVars(muxer).clear();
			getEventWithPins(muxer).getWith().clear();
		} else {
			// create member variables of struct as data input ports
			final boolean createAsInputs = muxer instanceof Multiplexer;
			createMemberVars(muxer, createAsInputs);
			// configure struct pin
			final VarDeclaration structPin = getStructuredTypePin(muxer);
			structPin.setType(muxer.getDataType());
		}
	}

	static void updateConfiguration(final ConfigurableMoveFB fb) {
		// if data type exists, set it as the data type of the input/output data pin
		if (fb.getDataType() != null && fb.getInterface() != null) {
			final boolean requiresContainerPin = isContainerPinType(fb.getDataType());
			final boolean hasContainerPin = fb.getInterface().getInputVars().get(0) instanceof ContainerVarDeclaration;
			if (requiresContainerPin != hasContainerPin) {
				// the pin type is not matching convert it
				convertMoveFBPinTypes(fb);
			} else {
				// just update the types
				fb.getInterface().getInputVars().forEach(pin -> pin.setType(fb.getDataType()));
				fb.getInterface().getOutputVars().forEach(pin -> pin.setType(fb.getDataType()));
				// FB_MOVE has no varinouts
			}
		}
	}

	private static boolean isContainerPinType(final DataType dataType) {
		return dataType instanceof StructuredType || dataType instanceof ErrorDataType;
	}

	private static void convertMoveFBPinTypes(final ConfigurableFB fb) {
		final DataType newDataType = fb.getDataType();
		fb.getInterface().getInputVars().replaceAll(pin -> createNewMovePin(newDataType, pin));
		fb.getInterface().getOutputVars().replaceAll(pin -> createNewMovePin(newDataType, pin));
	}

	private static VarDeclaration createNewMovePin(final DataType dataType, final VarDeclaration srcPin) {
		final VarDeclaration newPin = VarDeclarationFactory.createVarDecl(dataType);
		copyPinProperties(srcPin, newPin);
		newPin.setName(srcPin.getName());
		newPin.setType(dataType);
		newPin.setIsInput(srcPin.isIsInput());

		// transfer withs to new pin
		if (srcPin.getWiths() != null) {
			// we need to copy the list as setVariables changes the list because it updates
			// opposites
			for (final With with : new ArrayList<>(srcPin.getWiths())) {
				with.setVariables(newPin);
			}
		}
		return newPin;
	}

	private static void copyPinProperties(final VarDeclaration srcPin, final VarDeclaration newPin) {
		newPin.setComment(srcPin.getComment());
		newPin.setArraySize(EcoreUtil.copy(srcPin.getArraySize()));
		if (srcPin.getValue() != null) {
			newPin.setValue(EcoreUtil.copy(srcPin.getValue()));
		}
		newPin.getAttributes().addAll(EcoreUtil.copyAll(srcPin.getAttributes()));
		if (srcPin instanceof final ContainerVarDeclaration srcContainerPin
				&& newPin instanceof final ContainerVarDeclaration newContainerPin) {
			srcContainerPin.getCachedMembers()
					.forEach(cachedMember -> newContainerPin.getCachedMembers().add(copyCachedMember(cachedMember)));
		}
	}

	private static VarDeclaration copyCachedMember(final VarDeclaration cachedMember) {
		final VarDeclaration copy = VarDeclarationFactory.createVarDecl(cachedMember.getType());
		copy.setName(cachedMember.getName());
		copy.setType(cachedMember.getType());
		copy.setIsInput(cachedMember.isIsInput());
		copyPinProperties(cachedMember, copy);
		return copy;
	}

	static void loadFbConfiguration(final ConfigurableFB fb, final String attributeName, final String typeName) {
		if (fb instanceof final ConfigurableMoveFB moveFB) {
			loadFbMoveConfiguration(moveFB, attributeName, typeName);
		}
		if (fb instanceof final StructManipulator structFB) {
			loadStructManipulatorConfiguration(structFB, attributeName, typeName);
		}

	}

	private static void loadFbMoveConfiguration(final ConfigurableMoveFB fb, final String attributeName,
			final String typeName) {
		// attribute holds the name of the desired data type of input and output data
		if (LibraryElementTags.F_MOVE_CONFIG.equals(attributeName) && (typeName != null)) {
			// get data type from library
			setDataType(fb, typeName);
		}
	}

	private static void loadStructManipulatorConfiguration(final StructManipulator fb, final String attributeName,
			final String typeName) {
		if (LibraryElementTags.STRUCT_MANIPULATOR_CONFIG.equals(attributeName) && typeName != null) {
			setDataType(fb, typeName);
		}
	}

	private static void setDataType(final ConfigurableFB fb, final String typeName) {
		final DataType dataType = fb.getTypeLibrary().getDataTypeLibrary().getType(typeName);
		fb.setDataType(dataType);
		fb.updateConfiguration();
	}

	static EList<Attribute> getConfigurableFbAttributes(final ConfigurableFB fb) {
		if (fb.getDataType() == null) {
			return ECollections.emptyEList();
		}
		if (fb instanceof final ConfigurableMoveFB movefb) {
			return getFbMoveAttributes(movefb);
		}
		if (fb instanceof final StructManipulator mux) {
			return getStructManipulatorAttributes(mux);
		}
		return ECollections.emptyEList();
	}

	private static EList<Attribute> getFbMoveAttributes(final ConfigurableMoveFB fb) {
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(LibraryElementTags.F_MOVE_CONFIG);
		attr.setValue(PackageNameHelper.getFullTypeName(fb.getDataType()));
		return ECollections.asEList(attr);
	}

	private static EList<Attribute> getStructManipulatorAttributes(final StructManipulator fb) {
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(LibraryElementTags.STRUCT_MANIPULATOR_CONFIG);
		attr.setType(ElementaryTypes.STRING);
		attr.setValue(PackageNameHelper.getFullTypeName(fb.getDataType()));
		return ECollections.asEList(attr);
	}

	private static Event getEventWithPins(final StructManipulator muxer) {
		if (muxer instanceof Multiplexer) {
			return muxer.getInterface().getEventInputs().get(0);
		}
		return muxer.getInterface().getEventOutputs().get(0);
	}

	private static VarDeclaration getStructuredTypePin(final StructManipulator muxer) {
		if (muxer instanceof Multiplexer) {
			return muxer.getInterface().getOutputVars().get(0);
		}
		return muxer.getInterface().getInputVars().get(0);
	}

	private static void createMemberVars(final StructManipulator muxer, final boolean isInput) {
		((StructuredType) muxer.getDataType()).getMemberVariables().forEach(memberVar -> {
			final VarDeclaration varDecl = InterfaceListCopier.copyVar(memberVar, false, false);
			varDecl.setIsInput(isInput);
			getMuxedVars(muxer).add(varDecl);
		});
		// clear any previous withs
		getEventWithPins(muxer).getWith().clear();
		// create with constructs
		getMuxedVars(muxer).forEach(varDecl -> {
			final With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			getEventWithPins(muxer).getWith().add(with);
		});
	}

	private static EList<VarDeclaration> getMuxedVars(final StructManipulator structMan) {
		return (structMan instanceof Demultiplexer) ? structMan.getInterface().getOutputVars()
				: structMan.getInterface().getInputVars();
	}

	private ConfigurableFBManagement() {
		throw new UnsupportedOperationException();
	}

}
