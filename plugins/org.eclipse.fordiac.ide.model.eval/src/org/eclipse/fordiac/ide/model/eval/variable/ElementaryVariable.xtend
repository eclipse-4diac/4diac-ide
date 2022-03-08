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

import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*

final class ElementaryVariable extends AbstractVariable {
	@Accessors Value value

	new(String name, AnyElementaryType type) {
		this(name, type, null as Value)
	}

	new(String name, AnyElementaryType type, String value) {
		this(name, type, value.parseValue(type))
	}

	new(String name, AnyElementaryType type, Value value) {
		super(name, type)
		setValue(value)
	}

	override setValue(Value value) {
		this.value = value.castValue(type) ?: type.defaultValue
	}

	override setValue(String value) {
		setValue(value.parseValue(type))
	}

	override validateValue(String value) {
		try {
			value.parseValue(type)
			true
		} catch (Exception e) {
			false
		}
	}
}
