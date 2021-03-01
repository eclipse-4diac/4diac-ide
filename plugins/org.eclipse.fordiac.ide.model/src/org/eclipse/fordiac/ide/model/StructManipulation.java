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

package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
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
 *
 */
public class StructManipulation {

	public static final String STRUCT_ATTRIBUTE = "StructuredType"; //$NON-NLS-1$
	public static final String CHILDREN_ATTRIBUTE = "VisibleChildren"; //$NON-NLS-1$
	public static final String DEMUX_NAME = "STRUCT_DEMUX"; //$NON-NLS-1$
	public static final String MUX_NAME = "STRUCT_MUX"; //$NON-NLS-1$


	public static void setStructTypeElementsAtInterface(StructManipulator muxer, StructuredType newStructType) {

		muxer.setStructType(newStructType);
		configureAttributes(muxer);
		setMemberVariablesAsPorts(muxer, newStructType);
	}

	private static void configureAttributes(StructManipulator muxer) {
		final StructuredType struct = muxer.getStructType();
		if (null == struct) {
			muxer.deleteAttribute(STRUCT_ATTRIBUTE);
		} else {
			muxer.setAttribute(STRUCT_ATTRIBUTE, "STRING", struct.getName(), //$NON-NLS-1$
					""); //$NON-NLS-1$
		}
	}

	public static void setMemberVariablesAsPorts(StructManipulator muxer, StructuredType newStructType) {
		if (muxer instanceof Demultiplexer) {
			if (null == newStructType) {
				setVariablesAsOutputs(muxer, Collections.emptyList());
			} else {
				setVariablesAsOutputs(muxer, newStructType.getMemberVariables());
			}
		}

		if (muxer instanceof Multiplexer) {
			if (null == newStructType) {
				setVariablesAsInputs(muxer, Collections.emptyList(), null);
			} else {
				setVariablesAsInputs(muxer, newStructType.getMemberVariables(), newStructType);
			}
		}
	}

	public static StructuredType collectVisibleChildren(StructManipulator muxer, StructuredType newStructType,
			final Attribute attr) {
		final StructuredType configuredStruct = DataFactory.eINSTANCE.createStructuredType();
		configuredStruct.setName(newStructType.getName());
		configuredStruct.setComment(newStructType.getComment());
		final List<String> visibleChildrenNames = Arrays
				.asList(attr.getValue().trim().split(LibraryElementTags.VARIABLE_SEPARATOR));
		final Collection<VarDeclaration> visibleVars = getVarDeclarations(muxer.getStructType(), visibleChildrenNames);
		configuredStruct.getMemberVariables().addAll(visibleVars);
		return configuredStruct;
	}

	public static void setVariablesAsInputs(StructManipulator muxer, Collection<VarDeclaration> vars,
			StructuredType newStructType) {
		// create member variables of struct as data input ports
		if (null == newStructType) {
			muxer.getInterface().getInputVars().clear();
		} else {
			final Collection<VarDeclaration> list = EcoreUtil.copyAll(newStructType.getMemberVariables());
			list.forEach(varDecl -> {
				if (null == muxer.getInterfaceElement(varDecl.getName())) {
					varDecl.setIsInput(true);
					if (null != varDecl.getValue()) {
						// if we have a value set it empty to get rid of default values from the struct
						// type
						varDecl.getValue().setValue(""); //$NON-NLS-1$
					}
				}
			});
			final Event ev = muxer.getInterface().getEventInputs().get(0);

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

	public static void setVariablesAsOutputs(StructManipulator muxer, Collection<VarDeclaration> vars) {
		final Collection<VarDeclaration> list = EcoreUtil.copyAll(vars);
		list.forEach(varDecl -> {
			varDecl.setIsInput(false);
			if (null != varDecl.getValue()) {
				// if we have a value set it empty to get rid of default values from the struct
				// type
				varDecl.getValue().setValue(""); //$NON-NLS-1$
			}
		});
		final List<Event> outputEvents = muxer.getInterface().getEventOutputs();

		// create with constructs
		list.forEach(varDecl -> {
			final With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			outputEvents.forEach(ev -> ev.getWith().add(with));
		});

		// add data output ports to the interface
		muxer.getInterface().getOutputVars().clear();
		muxer.getInterface().getOutputVars().addAll(list);
	}

	private static Collection<VarDeclaration> getVarDeclarations(StructuredType structType, List<String> varDeclNames) {
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

	public static VarDeclaration findVarDeclarationInStruct(StructuredType struct, String name) {
		final String[] subnames = name.split("\\."); //$NON-NLS-1$
		List<VarDeclaration> members = struct.getMemberVariables();
		VarDeclaration found = null;
		for (final String subname : subnames) { //
			final Object[] findings = members.stream().filter(var -> var.getName().equals(subname)).toArray();
			if (findings.length > 0) {
				found = (VarDeclaration) findings[0];
			}
			if ((null != found) && (found.getType() instanceof StructuredType)) {
				members = ((StructuredType) found.getType()).getMemberVariables();
			}
		}
		return found;
	}

	private StructManipulation() {
		throw new UnsupportedOperationException();
	}
}
