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

import org.eclipse.fordiac.ide.model.eval.value.ECStateValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.ECState

class ECStateVariable extends AbstractVariable {
	public static final String NAME = "__STATE"

	@Accessors ECStateValue value

	new(BasicFBType type) {
		this(type, new ECStateValue(type.ECC.start))
	}

	new(ECState state) {
		this(state.ECC?.basicFBType, new ECStateValue(state))
	}

	new(BasicFBType type, String value) {
		this(type)
		if(value !== null) this.value = value
	}

	new(BasicFBType type, Value value) {
		super(NAME, type)
		if(value !== null) this.value = value
	}

	override setValue(Value value) {
		if (value instanceof ECStateValue) {
			if (value.type != type) {
				throw new IllegalArgumentException('''Cannot assign EC state value with different type «value.type.name» to EC state variable of type «type.name»''')
			}
			this.value = value
		} else
			throw new ClassCastException('''Cannot assign value with incompatible type «value.type.name» as «type.name»''')
	}

	override setValue(String value) {
		val state = type.ECC.ECState.findFirst[name == value]
		if (state !== null)
			value = new ECStateValue(state)
		else
			throw new IllegalArgumentException('''No state named «value» in type «type.name»''')
	}

	override validateValue(String value) { type.ECC.ECState.findFirst[name == value] !== null }

	override BasicFBType getType() {
		return super.getType as BasicFBType
	}
}
