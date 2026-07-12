/*******************************************************************************
 * Copyright (c) 2021, 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmenda, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.List;
import java.util.function.Function;

import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class VariableUtils {

	public static void setVariable(final VarDeclaration varDecl, final String value) {
		final Value val = varDecl.getValue();
		if (val == null) {
			varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		}
		varDecl.getValue().setValue(value);
	}

	public static void setVariable(final FBType fb, final String name, final String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(List.of(name.strip()));
		if (!(el instanceof VarDeclaration)) {
			throw new IllegalArgumentException("variable " + name + " does not exist in FB"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		setVariable((VarDeclaration) el, ServiceSequenceUtils.removeKeyword(value));
	}

	public static void initVariable(final VarDeclaration varDeclaration,
			final Function<VarDeclaration, String> defaultCreation) {
		// if there is no initial value, we take a default value
		if ((varDeclaration != null) && isEmptyValue(varDeclaration.getValue())) {
			setVariable(varDeclaration, defaultCreation == null ? InitialValueHelper.getDefaultValue(varDeclaration)
					: defaultCreation.apply(varDeclaration));
		}
	}

	private static boolean isAny(final IInterfaceElement varDeclaration) {
		return varDeclaration.getType().getName().startsWith("ANY"); //$NON-NLS-1$
	}

	/**
	 * Looks for the a non-ANY interface in a connection, first from the source and
	 * then from the destination. If both are ANY Types, the source is returned.
	 */
	public static IInterfaceElement getNonAnyValueFromConnection(final Connection connection) {
		if (!isAny(connection.getSource())) {
			return connection.getSource();
		}
		if (!isAny(connection.getDestination())) {
			return connection.getDestination();
		}
		return connection.getSource();
	}

	/**
	 * Looks for a non-ANY interface element in the connections of the given
	 * variable declaration. If the variable is an input, it checks the source of
	 * the input connection. If it is an output, it checks the destination of the
	 * output connections. If all connected interfaces are ANY types, it returns
	 * null.
	 */
	private static IInterfaceElement getNonAnyVarFromConnections(final VarDeclaration varDeclaration) {
		if (varDeclaration.isIsInput()) {
			// only one input connection allowed, check if the source is not an ANY type
			if (!isAny(varDeclaration.getInputConnections().get(0).getSource())) {
				return varDeclaration.getInputConnections().get(0).getSource();
			}

		} else {
			// find the first output connection whose destination is not an ANY type
			for (final var connection : varDeclaration.getOutputConnections()) {
				if (!isAny(connection.getDestination())) {
					return connection.getDestination();
				}
			}
		}
		return null;
	}

	/**
	 * Checks if the given variable declaration is of an ANY type. If it is, it
	 * looks for a non-ANY type in the connections of the variable declaration. If a
	 * non-ANY type is found in the connections, a new VarDeclaration is created
	 * with the same name as the original variable declaration but with the non-ANY
	 * type.
	 */
	public static VarDeclaration getNonAnyVarDeclaration(final VarDeclaration original) {
		if (!isAny(original)) {
			return original;
		}

		final var nonAnyVar = getNonAnyVarFromConnections(original);
		if (nonAnyVar == null) {
			// all connections from original are also ANY types
			return original;
		}

		final VarDeclaration varDeclaration = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDeclaration.setType(nonAnyVar.getType());
		varDeclaration.setName(original.getName());
		return varDeclaration;
	}

	public static boolean isEmptyValue(final Value value) {
		return (value == null) || (value.getValue() == null) || value.getValue().isBlank();
	}

	// Init all FB Variables
	public static void fBVariableInitialization(final BaseFBType baseFbType,
			final Function<VarDeclaration, String> defaultCreation) {
		initInternalVars(baseFbType);
		initInternalConstVars(baseFbType);
		initializeFbType(baseFbType, defaultCreation);
	}

	public static void fBVariableInitialization(final CompositeFBType compFBType,
			final Function<VarDeclaration, String> defaultCreation) {
		initializeFbType(compFBType, defaultCreation);
	}

	public static void fBVariableInitialization(final FunctionFBType functionFBType,
			final Function<VarDeclaration, String> defaultCreation) {
		initializeFbType(functionFBType, defaultCreation);
	}

	public static void initializeFbType(final FBType fbType, final Function<VarDeclaration, String> defaultCreation) {
		initInputVars(fbType, defaultCreation);
		initOutputVars(fbType, defaultCreation);
		initPlugs(fbType, defaultCreation);
		initSockets(fbType, defaultCreation);
	}

	private static void initOutputVars(final FBType fbType, final Function<VarDeclaration, String> defaultCreation) {
		fbType.getInterfaceList().getOutputVars().forEach(outputVar -> initVariable(outputVar, defaultCreation));
	}

	private static void initInputVars(final FBType fbType, final Function<VarDeclaration, String> defaultCreation) {
		fbType.getInterfaceList().getInputVars().forEach(inputVar -> initVariable(inputVar, defaultCreation));
	}

	private static void initInternalVars(final BaseFBType baseFbType) {
		baseFbType.getInternalVars().forEach(internalVar -> initVariable(internalVar, null));
	}

	private static void initInternalConstVars(final BaseFBType baseFbType) {
		baseFbType.getInternalConstVars().forEach(internalConstVar -> initVariable(internalConstVar, null));
	}

	private static void initSockets(final FBType fbType, final Function<VarDeclaration, String> defaultCreation) {
		fbType.getInterfaceList().getSockets().forEach(adp -> initializeAdapter(adp.getType(), defaultCreation));
	}

	private static void initPlugs(final FBType fbType, final Function<VarDeclaration, String> defaultCreation) {
		fbType.getInterfaceList().getPlugs().forEach(adp -> initializeAdapter(adp.getType(), defaultCreation));
	}

	private static void initializeAdapter(final AdapterType type,
			final Function<VarDeclaration, String> defaultCreation) {
		initInputVars(type, defaultCreation);
		initOutputVars(type, defaultCreation);
	}

	private VariableUtils() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

}
