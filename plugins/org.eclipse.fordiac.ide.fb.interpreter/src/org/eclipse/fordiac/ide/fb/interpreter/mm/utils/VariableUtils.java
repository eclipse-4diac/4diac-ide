/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public final class VariableUtils {

	public static void setVariable(final VarDeclaration varDecl, final String value) {
		final Value val = varDecl.getValue();
		if (val == null) {
			varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		}
		varDecl.getValue().setValue(value);
	}

	public static void initVariable(final VarDeclaration varDeclaration, final DataTypeLibrary lib) {
		// first set type, then add value
		if (varDeclaration.getType() == null) {
			varDeclaration.setType(lib.getType(varDeclaration.getTypeName()));
		}
		// if there is no initial value, we take a default value
		if ((varDeclaration.getValue() == null) || (varDeclaration.getValue().getValue() == null)
				|| varDeclaration.getValue().getValue().isBlank()) {
			setVariable(varDeclaration, InitialValueHelper.getDefaultValue(varDeclaration));
		}
	}

	// Init all FB Variables
	public static void fBVariableInitialization(final BaseFBType baseFbType) {
		final var lib = new DataTypeLibrary();
		initInternalVars(baseFbType, lib);
		initInputVars(baseFbType, lib);
		initOutputVars(baseFbType, lib);
	}

	public static void fBVariableInitialization(final CompositeFBType compFBType) {
		final var lib = new DataTypeLibrary();
		initInputVars(compFBType, lib);
		initOutputVars(compFBType, lib);
	}

	public static void initOutputVars(final FBType fbType, final DataTypeLibrary lib) {
		fbType.getInterfaceList().getOutputVars().forEach(outputVar -> VariableUtils.initVariable(outputVar, lib));
	}

	public static void initInputVars(final FBType fbType, final DataTypeLibrary lib) {
		fbType.getInterfaceList().getInputVars().forEach(inputVar -> VariableUtils.initVariable(inputVar, lib));
	}

	public static void initInternalVars(final BaseFBType basicFbType, final DataTypeLibrary lib) {
		basicFbType.getInternalVars().forEach(interVar -> VariableUtils.initVariable(interVar, lib));
	}

	private VariableUtils() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

}
