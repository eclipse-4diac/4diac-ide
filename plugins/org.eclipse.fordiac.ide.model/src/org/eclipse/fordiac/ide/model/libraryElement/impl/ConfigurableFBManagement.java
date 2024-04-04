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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * Helper class for methods that will be - accessed from the model or - used
 * directly as static helper methods
 */
public final class ConfigurableFBManagement {

	public static final String CHILDREN_ATTRIBUTE = "VisibleChildren"; //$NON-NLS-1$
	public static final String DEMUX_NAME = "STRUCT_DEMUX"; //$NON-NLS-1$
	public static final String MUX_NAME = "STRUCT_MUX"; //$NON-NLS-1$

	static void updateFbConfiguration(final ConfigurableFB fb) {
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
		}
	}

	private static void loadFbMoveConfiguration(final ConfigurableMoveFB fb, final String attributeName,
			final String typeName) {
		// attribute holds the name of the desired data type of input and output data
		if (LibraryElementTags.F_MOVE_CONFIG.equals(attributeName) && (typeName != null)) {
			// get data type from library
			final DataType dataType = fb.getTypeLibrary().getDataTypeLibrary().getType(typeName);
			fb.setDataType(dataType);
		}
		updateFbConfiguration(fb);
	}

	static EList<Attribute> getConfigurableFbAttributes(final ConfigurableFB fb) {
		if (fb.getDataType() == null) {
			return ECollections.emptyEList();
		}
		if (fb instanceof final ConfigurableMoveFB movefb) {
			return getFbMoveAttributes(movefb);
		}
		return ECollections.emptyEList();
	}

	private static EList<Attribute> getFbMoveAttributes(final ConfigurableMoveFB fb) {

		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(LibraryElementTags.F_MOVE_CONFIG);
		attr.setValue(fb.getDataType().getName());
		return ECollections.asEList(attr);
	}

	static void setStructTypeElementsAtInterface(final StructManipulator muxer, final StructuredType newStructType) {
		muxer.setStructType(newStructType);
		setMemberVariablesAsPorts(muxer, newStructType);
	}

	static void setMemberVariablesAsPorts(final StructManipulator muxer, final StructuredType newStructType) {
		if (muxer instanceof Demultiplexer) {
			if (null == newStructType) {
				setVariablesAsOutputs(muxer, Collections.emptyList(), null);
			} else if (muxer.getAttribute(CHILDREN_ATTRIBUTE) != null) {
				setVariablesAsOutputs(muxer, collectVisibleChildren(muxer, newStructType).getMemberVariables(),
						newStructType);
			} else {
				setVariablesAsOutputs(muxer, newStructType.getMemberVariables(), newStructType);
			}
		}

		if (muxer instanceof Multiplexer) {
			setVariablesAsInputs(muxer, newStructType);
		}
	}

	private static StructuredType collectVisibleChildren(final StructManipulator muxer,
			final StructuredType newStructType) {
		final Attribute attr = muxer.getAttribute(CHILDREN_ATTRIBUTE);
		final StructuredType configuredStruct = DataFactory.eINSTANCE.createStructuredType();
		configuredStruct.setName(newStructType.getName());
		configuredStruct.setComment(newStructType.getComment());
		final List<String> visibleChildrenNames = Arrays
				.asList(attr.getValue().trim().split(LibraryElementTags.VARIABLE_SEPARATOR));
		final Collection<VarDeclaration> visibleVars = getVarDeclarations(muxer.getStructType(), visibleChildrenNames);
		configuredStruct.getMemberVariables().addAll(visibleVars);
		return configuredStruct;
	}

	private static void setVariablesAsInputs(final StructManipulator muxer, final StructuredType newStructType) {
		// create member variables of struct as data input ports
		if (null == newStructType) {
			muxer.getInterface().getInputVars().clear();
		} else {
			final Collection<VarDeclaration> list = EcoreUtil.copyAll(newStructType.getMemberVariables());
			list.forEach(varDecl -> {
				if (null == muxer.getInterfaceElement(varDecl.getName())) {
					varDecl.setIsInput(true);
					varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
			});
			final Event ev = muxer.getInterface().getEventInputs().get(0);
			// clear any previous withs
			ev.getWith().clear();

			// create with constructs
			list.forEach(varDecl -> {
				final With with = LibraryElementFactory.eINSTANCE.createWith();
				with.setVariables(varDecl);
				ev.getWith().add(with);
			});

			// add data input ports to the interface
			muxer.getInterface().getInputVars().addAll(list);
			muxer.getInterface().getOutputVars().get(0).setType(newStructType); // there should be only one output
		}
	}

	private static void setVariablesAsOutputs(final StructManipulator muxer, final Collection<VarDeclaration> vars,
			final StructuredType newStructType) {
		final Collection<VarDeclaration> list = EcoreUtil.copyAll(vars);
		list.forEach(varDecl -> {
			varDecl.setIsInput(false);
			varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		});
		final List<Event> outputEvents = muxer.getInterface().getEventOutputs();
		// clear any previous withs
		outputEvents.forEach(ev -> ev.getWith().clear());

		// create with constructs
		list.forEach(varDecl -> {
			final With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			outputEvents.forEach(ev -> ev.getWith().add(with));
		});

		// add data output ports to the interface
		muxer.getInterface().getOutputVars().clear();
		muxer.getInterface().getOutputVars().addAll(list);
		muxer.getInterface().getInputVars().get(0).setType(newStructType); // there should be only one output

	}

	private static Collection<VarDeclaration> getVarDeclarations(final StructuredType structType,
			final List<String> varDeclNames) {
		final List<VarDeclaration> vars = new ArrayList<>();
		varDeclNames.forEach(name -> {
			final VarDeclaration varDecl = EcoreUtil.copy(findVarDeclarationInStruct(structType, name));
			if (null != varDecl) {
				varDecl.setName(name);
				vars.add(varDecl);
			}
		});
		return vars;
	}

	private static VarDeclaration findVarDeclarationInStruct(final StructuredType struct, final String name) {
		// if struct is already configured, we find the variable there
		VarDeclaration found = struct.getMemberVariables().stream().filter(memVar -> memVar.getName().equals(name))
				.findFirst().orElse(null);
		if (found != null) {
			return found;
		}
		// else we have to check each element separately
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
