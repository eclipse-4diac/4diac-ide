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
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
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
					AnyIntType.class, v -> v.setValue("0"), // TODO check: replace with AnyNumType? //$NON-NLS-1$
					BoolType.class, v -> v.setValue("false") //$NON-NLS-1$
					);

	public static void initVariable(VarDeclaration varDeclaration, DataTypeLibrary lib) {
		// first set type, then add value
		if (varDeclaration.getType() == null) {
			varDeclaration.setType(lib.getType(varDeclaration.getTypeName()));
		}
		if (varDeclaration.getValue() == null) {
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
	public static void fBVariableInitialization(BasicFBType basicFbType) {
		final var lib = new DataTypeLibrary();
		initInternalVars(basicFbType, lib);
		initInputVars(basicFbType, lib);
		initOutputVars(basicFbType, lib);
	}

	public static void initOutputVars(BasicFBType basicFbType, DataTypeLibrary lib) {
		basicFbType.getInterfaceList().getOutputVars().forEach(outputVar -> VariableUtils.initVariable(outputVar, lib));
	}

	public static void initInputVars(BasicFBType basicFbType, DataTypeLibrary lib) {
		basicFbType.getInterfaceList().getInputVars().forEach(inputVar -> VariableUtils.initVariable(inputVar, lib));
	}

	public static void initInternalVars(BasicFBType basicFbType, DataTypeLibrary lib) {
		basicFbType.getInternalVars().forEach(interVar -> VariableUtils.initVariable(interVar, lib));
	}
}
