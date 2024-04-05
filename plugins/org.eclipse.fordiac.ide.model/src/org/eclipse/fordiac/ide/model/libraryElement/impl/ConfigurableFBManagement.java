/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *      - initial implementation and documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * Helper class for methods that will be - accessed from the model
 */
public final class ConfigurableFBManagement {

	static void updateFbConfiguration(final ConfigurableFB fb) {
		if (fb instanceof ConfigurableMoveFB) {
			updateMoveFbConfiguration(fb);
		} else if ((fb instanceof final StructManipulator sm) && (fb.getDataType() instanceof StructuredType)) {
			updateStructManipulatorConfiguration(sm);
		}
	}

	private static void updateMoveFbConfiguration(final ConfigurableFB fb) {
		// if data type exists, set it as the data type of the input/output data pin
		if (fb.getDataType() != null && fb.getInterface() != null) {
			for (final VarDeclaration input : fb.getInterface().getInputVars()) {
				input.setType(fb.getDataType());
			}
			for (final VarDeclaration output : fb.getInterface().getOutputVars()) {
				output.setType(fb.getDataType());
			} // FB_MOVE has no varinouts
		}
	}

	static void loadFbConfiguration(final ConfigurableFB fb, final String attributeName, final String typeName) {
		if (fb instanceof final ConfigurableMoveFB moveFB) {
			loadFbMoveConfiguration(moveFB, attributeName, typeName);
		} else if (fb instanceof final StructManipulator structFB) {
			loadStructManipulatorConfiguration(structFB, attributeName, typeName);
		}
		if (fb instanceof final Demultiplexer demux) {
			loadVisibleChildrenDemuxConfiguration(demux, attributeName, typeName);
		}
	}

	private static void loadFbMoveConfiguration(final ConfigurableMoveFB fb, final String attributeName,
			final String typeName) {
		// attribute holds the name of the desired data type of input and output data
		if (LibraryElementTags.F_MOVE_CONFIG.equals(attributeName) && (typeName != null)) {
			// get data type from library
			final DataType dataType = fb.getTypeLibrary().getDataTypeLibrary().getType(typeName);
			fb.setDataType(dataType);
			updateFbConfiguration(fb);
		}
	}

	private static void loadStructManipulatorConfiguration(final StructManipulator fb, final String attributeName,
			final String typeName) {
		if (LibraryElementTags.STRUCT_MANIPULATOR_CONFIG.equals(attributeName) && typeName != null) {
			final DataType dataType = fb.getTypeLibrary().getDataTypeLibrary().getType(typeName);
			fb.setDataType(dataType);
			updateFbConfiguration(fb);
		}
	}

	private static void loadVisibleChildrenDemuxConfiguration(final Demultiplexer fb, final String attributeName,
			final String visibleChildren) {
		if (LibraryElementTags.DEMUX_VISIBLE_CHILDREN.equals(attributeName) && visibleChildren != null) {
			fb.setIsConfigured(true);
			updateConfiguredDemuxConfiguration(fb, visibleChildren);
		}
	}

	static EList<Attribute> getConfigurableFbAttributes(final ConfigurableFB fb) {
		if (fb.getDataType() == null) {
			return ECollections.emptyEList();
		}
		if (fb instanceof final ConfigurableMoveFB movefb) {
			return getFbMoveAttributes(movefb);
		}
		if (fb instanceof final Multiplexer mux) {
			return getStructManipulatorAttributes(mux);
		}
		if (fb instanceof final Demultiplexer demux) {
			return getConfigurableDemuxAttributes(demux);
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

	private static EList<Attribute> getConfigurableDemuxAttributes(final Demultiplexer fb) {
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(LibraryElementTags.DEMUX_VISIBLE_CHILDREN);
		attr.setType(ElementaryTypes.STRING);
		attr.setValue(buildVisibleChildrenString(fb));
		return ECollections.asEList(getStructManipulatorAttributes(fb).get(0), attr);
	}

	private static String buildVisibleChildrenString(final StructManipulator fb) {
		if (fb.getMemberVars().isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder sb = new StringBuilder();
		fb.getMemberVars().forEach(var -> sb.append(getMemberVarName((MemberVarDeclaration) var) + ",")); //$NON-NLS-1$
		return sb.substring(0, sb.length() - 2); // avoid adding "," in the end
	}

	static String getMemberVarName(final MemberVarDeclaration var) {
		final StringBuilder sb = new StringBuilder();
		var.getParentNames().forEach(name -> sb.append(name + ".")); //$NON-NLS-1$
		sb.append(var.getName());
		return sb.toString();
	}

	static void updateStructManipulatorConfiguration(final StructManipulator muxer) {
		if (muxer instanceof final Demultiplexer demux) {
			if (demux.isIsConfigured()) {
				// updating requires finding all elements again - struct may have changed!
				updateConfiguredDemuxConfiguration(demux, buildVisibleChildrenString(muxer));
			} else {
				updateDemuxConfiguration(muxer);
			}
		} else if (muxer instanceof Multiplexer) {
			updateMultiplexerConfiguration(muxer);
		}
	}

	private static void updateMultiplexerConfiguration(final StructManipulator muxer) {
		// create member variables of struct as data input ports
		final Event ev = muxer.getInterface().getEventInputs().get(0);
		createMemberVars(muxer, ev, true);
		// configure pin
		muxer.getInterface().getOutputVars().get(0).setType(muxer.getDataType());
	}

	private static void updateDemuxConfiguration(final StructManipulator muxer) {
		// create member variables of struct as data output ports
		if (muxer.getDataType() instanceof final StructuredType structType) {
			final Event outputEvent = muxer.getInterface().getEventOutputs().get(0);
			createMemberVars(muxer, outputEvent, false);
		} else {
			muxer.getMemberVars().clear();
		}
		// configure pin
		muxer.getInterface().getInputVars().get(0).setType(muxer.getDataType());
	}

	private static void createMemberVars(final StructManipulator muxer, final Event withedEvent,
			final boolean isInput) {
		if (muxer.getDataType() instanceof final StructuredType structType) {
			structType.getMemberVariables().forEach(memberVar -> {
				final VarDeclaration varDecl = copyVarAsMember(memberVar, isInput);
				muxer.getMemberVars().add(varDecl);
			});
			// clear any previous withs
			withedEvent.getWith().clear();
			// create with constructs
			muxer.getMemberVars().forEach(varDecl -> {
				final With with = LibraryElementFactory.eINSTANCE.createWith();
				with.setVariables(varDecl);
				withedEvent.getWith().add(with);
			});
		} else {
			muxer.getMemberVars().clear();
		}
	}

	private static MemberVarDeclaration copyVarAsMember(final VarDeclaration memberVar, final boolean isInput) {
		final MemberVarDeclaration copy = LibraryElementFactory.eINSTANCE.createMemberVarDeclaration();
		copy.setName(memberVar.getName());
		copy.setType(memberVar.getType());
		copy.setValue(LibraryElementFactory.eINSTANCE.createValue());
		copy.setIsInput(isInput);
		return copy;
	}

	private static void updateConfiguredDemuxConfiguration(final Demultiplexer demux, final String visibleChildren) {
		if (visibleChildren != null && (demux.getDataType() instanceof final StructuredType structType)) {
			final String[] memberVarNames = visibleChildren.trim().split(","); //$NON-NLS-1$
			for (final String memberVarName : memberVarNames) {
				final String[] subnames = memberVarName.split("\\."); //$NON-NLS-1$
				final MemberVarDeclaration pin = LibraryElementFactory.eINSTANCE.createMemberVarDeclaration();
				pin.setName(subnames[subnames.length - 1]);
				pin.setType(findVarDeclarationInStruct(structType, memberVarName).getType());
				pin.setValue(LibraryElementFactory.eINSTANCE.createValue());
				pin.setIsInput(false);
				demux.getMemberVars().add(pin);
			}
			// clear any previous withs
			final Event withedEvent = demux.getInterface().getEventOutputs().get(0);
			withedEvent.getWith().clear();
			// create with constructs
			demux.getMemberVars().forEach(varDecl -> {
				final With with = LibraryElementFactory.eINSTANCE.createWith();
				with.setVariables(varDecl);
				withedEvent.getWith().add(with);
			});
		} else {
			updateDemuxConfiguration(demux);
		}
	}

	private static VarDeclaration findVarDeclarationInStruct(final StructuredType struct, final String name) {
		// we have to check each element separately
		VarDeclaration found = null;
		final String[] subnames = name.split("\\."); //$NON-NLS-1$
		List<VarDeclaration> members = struct.getMemberVariables();
		for (final String subname : subnames) { //
			final Object[] findings = members.stream().filter(memVar -> memVar.getName().equals(subname)).toArray();
			if (findings.length > 0) {
				found = (VarDeclaration) findings[0];
			}
			if ((null != found) && (found.getType() instanceof final StructuredType structType)) {
				members = structType.getMemberVariables();
			}
		}
		return found;
	}

	private ConfigurableFBManagement() {
		throw new UnsupportedOperationException();
	}

}
