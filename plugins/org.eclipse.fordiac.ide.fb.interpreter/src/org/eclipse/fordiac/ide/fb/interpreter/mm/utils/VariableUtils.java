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

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public final class VariableUtils {

	private VariableUtils() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static final Map<Class<?>, Consumer<Value>> initVariables =
			Map.of(
					AnyStringType.class, v -> v.setValue(""), //$NON-NLS-1$
					AnyIntType.class, v -> v.setValue("0"),  //$NON-NLS-1$
					BoolType.class, v -> v.setValue("false") //$NON-NLS-1$
					);

	public static void initVariable(final VarDeclaration varDeclaration, final DataTypeLibrary lib) {
		// first set type, then add value
		if (varDeclaration.getType() == null) {
			varDeclaration.setType(lib.getType(varDeclaration.getTypeName()));
		}
		//In case the value is incomplete we take a default value
		if (varDeclaration.getValue() == null || 
				varDeclaration.getValue().getValue() == null || 
					varDeclaration.getValue().getValue().isBlank()) {
			//TODO refactor to return the value...faster
			final var value = LibraryElementFactory.eINSTANCE.createValue();
			initVariables.entrySet().stream().forEach(entry -> {
				if (entry.getKey().isInstance(varDeclaration.getType()) ) {
					entry.getValue().accept(value);
				}
			});
			varDeclaration.setValue(value);
		}
	}

	//Init all FB Variables
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
}
