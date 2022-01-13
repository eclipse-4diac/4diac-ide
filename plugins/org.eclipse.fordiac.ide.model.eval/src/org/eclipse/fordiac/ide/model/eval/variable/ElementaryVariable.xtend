/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.variable

import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*

final class ElementaryVariable extends AbstractVariable {
	@Accessors Value value

	new(VarDeclaration declaration) {
		this(declaration, null as Value)
	}

	new(VarDeclaration declaration, String value) {
		this(declaration, value.parseValue(declaration.type))
	}

	new(VarDeclaration declaration, Value value) {
		super(declaration)
		setValue(value)
	}

	override setValue(Value value) {
		this.value = value.castValue(declaration.type) ?: declaration.type.defaultValue
	}

	override setValue(String value) {
		setValue(value.parseValue(declaration.type))
	}

	override validateValue(String value) {
		try {
			value.parseValue(declaration.type)
			true
		} catch (Exception e) {
			false
		}
	}
}
