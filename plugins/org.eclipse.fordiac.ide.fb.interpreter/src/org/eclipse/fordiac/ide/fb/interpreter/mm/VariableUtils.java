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

import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
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

	public static void initVariable(final VarDeclaration varDeclaration) {
		// if there is no initial value, we take a default value
		if ((varDeclaration != null) && isEmptyValue(varDeclaration.getValue())) {
			setVariable(varDeclaration, InitialValueHelper.getDefaultValue(varDeclaration));
		}
	}

	public static boolean isEmptyValue(final Value value) {
		return (value == null) || (value.getValue() == null) || value.getValue().isBlank();
	}

	// Init all FB Variables
	public static void fBVariableInitialization(final BaseFBType baseFbType) {
		initInternalVars(baseFbType);
		initInternalConstVars(baseFbType);
		initializeFbType(baseFbType);
	}

	public static void fBVariableInitialization(final CompositeFBType compFBType) {
		initializeFbType(compFBType);
	}

	public static void fBVariableInitialization(final FunctionFBType functionFBType) {
		initializeFbType(functionFBType);
	}

	public static void initializeFbType(final FBType fbType) {
		initInputVars(fbType);
		initOutputVars(fbType);
		initPlugs(fbType);
		initSockets(fbType);
	}

	private static void initOutputVars(final FBType fbType) {
		fbType.getInterfaceList().getOutputVars().forEach(VariableUtils::initVariable);
	}

	private static void initInputVars(final FBType fbType) {
		fbType.getInterfaceList().getInputVars().forEach(VariableUtils::initVariable);
	}

	private static void initInternalVars(final BaseFBType baseFbType) {
		baseFbType.getInternalVars().forEach(VariableUtils::initVariable);
	}

	private static void initInternalConstVars(final BaseFBType baseFbType) {
		baseFbType.getInternalConstVars().forEach(VariableUtils::initVariable);
	}

	private static void initSockets(final FBType fbType) {
		fbType.getInterfaceList().getSockets().forEach(adp -> initializeAdapter(adp.getType()));
	}

	private static void initPlugs(final FBType fbType) {
		fbType.getInterfaceList().getPlugs().forEach(adp -> initializeAdapter(adp.getType()));
	}

	private static void initializeAdapter(final AdapterType type) {
		initInputVars(type);
		initOutputVars(type);
	}

	private VariableUtils() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

}
