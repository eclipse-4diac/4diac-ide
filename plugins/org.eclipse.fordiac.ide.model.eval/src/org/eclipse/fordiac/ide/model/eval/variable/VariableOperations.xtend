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
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.*

final class VariableOperations {
	private new() {
	}

	def static Variable newVariable(String name, DataType type) {
		newVariable(name, type, null as Value)
	}
	
	def static Variable newVariable(String name, DataType type, String value) {
		switch (type) {
			AnyElementaryType: new ElementaryVariable(name, type, value)
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable «name» of type «type.name»''')
		}
	}

	def static Variable newVariable(String name, DataType type, Value value) {
		switch (type) {
			AnyElementaryType: new ElementaryVariable(name, type, value)
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable «name» of type «type.name»''')
		}
	}

	def static Variable newVariable(VarDeclaration decl) {
		if (decl.array)
			newVariable(decl.name, decl.type.newArrayType(newSubrange(0, decl.arraySize - 1)), decl.value?.value)
		else
			newVariable(decl.name, decl.type, decl.value?.value)
	}
}
