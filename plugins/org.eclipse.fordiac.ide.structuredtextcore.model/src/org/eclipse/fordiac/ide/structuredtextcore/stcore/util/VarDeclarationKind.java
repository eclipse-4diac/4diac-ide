/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.stcore.util;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;

public enum VarDeclarationKind {
	INPUT(STCorePackage.Literals.ST_VAR_INPUT_DECLARATION_BLOCK),
	INOUT(STCorePackage.Literals.ST_VAR_IN_OUT_DECLARATION_BLOCK),
	OUTPUT(STCorePackage.Literals.ST_VAR_OUTPUT_DECLARATION_BLOCK),
	TEMP(STCorePackage.Literals.ST_VAR_TEMP_DECLARATION_BLOCK),
	PLAIN(STCorePackage.Literals.ST_VAR_PLAIN_DECLARATION_BLOCK);

	private final EClass blockClass;

	VarDeclarationKind(final EClass blockClass) {
		this.blockClass = blockClass;
	}

	public EClass getBlockClass() {
		return blockClass;
	}

	public static VarDeclarationKind valueOf(final STVarDeclaration varDeclaration) {
		if (varDeclaration.eContainer() instanceof final STVarDeclarationBlock block) {
			return valueOf(block);
		}
		throw new IllegalArgumentException();
	}

	public static VarDeclarationKind valueOf(final STVarDeclarationBlock block) {
		return Stream.of(values()).filter(value -> value.getBlockClass() == block.eClass()).findAny()
				.orElseThrow(IllegalArgumentException::new);
	}
}
