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
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
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

	@Deprecated
	public static void setVariable(final FBType fb, final String name, final String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(name.strip());
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
		initInputVars(baseFbType);
		initOutputVars(baseFbType);
	}

	public static void fBVariableInitialization(final CompositeFBType compFBType) {
		initInputVars(compFBType);
		initOutputVars(compFBType);
	}

	public static void initOutputVars(final FBType fbType) {
		fbType.getInterfaceList().getOutputVars().forEach(VariableUtils::initVariable);
	}

	public static void initInputVars(final FBType fbType) {
		fbType.getInterfaceList().getInputVars().forEach(VariableUtils::initVariable);
	}

	public static void initInternalVars(final BaseFBType basicFbType) {
		basicFbType.getInternalVars().forEach(VariableUtils::initVariable);
	}

	public static void initInternalConstVars(final BaseFBType basicFbType) {
		basicFbType.getInternalConstVars().forEach(VariableUtils::initVariable);
	}

	private VariableUtils() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

}
