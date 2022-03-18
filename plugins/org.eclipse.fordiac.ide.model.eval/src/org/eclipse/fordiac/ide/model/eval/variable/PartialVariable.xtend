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

import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.ByteType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DwordType
import org.eclipse.fordiac.ide.model.data.LwordType
import org.eclipse.fordiac.ide.model.data.WordType
import org.eclipse.fordiac.ide.model.eval.value.Value

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*

class PartialVariable extends AbstractVariable {
	final Variable delegate
	final int index

	new(Variable delegate, DataType partial, int index) {
		super('''«delegate.name».%«partial.partialName»(«index»)''', partial)
		this.delegate = delegate
		this.index = index
	}

	override getValue() {
		delegate.value.partial(type, index)
	}

	override setValue(Value value) {
		delegate.value = delegate.value.partial(type, index, value)
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

	def private static getPartialName(DataType type) {
		switch (type) {
			BoolType: "X"
			ByteType: "B"
			WordType: "W"
			DwordType: "D"
			LwordType: "L"
		}
	}
}
