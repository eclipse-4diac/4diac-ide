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
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.*

final class VariableOperations {
	private new() {
	}

	def static Variable newVariable(String name, INamedElement type) {
		newVariable(name, type, null as Value)
	}

	def static Variable newVariable(String name, INamedElement type, String value) {
		switch (type) {
			AnyElementaryType: new ElementaryVariable(name, type, value)
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable «name» of type «type.name»''')
		}
	}

	def static Variable newVariable(String name, INamedElement type, Value value) {
		switch (type) {
			AnyElementaryType: new ElementaryVariable(name, type, value)
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			FBType: new FBVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable «name» of type «type.name»''')
		}
	}

	def static Variable newVariable(VarDeclaration decl) {
		newVariable(decl, decl.initialValue)
	}

	def static Variable newVariable(VarDeclaration decl, String value) {
		if (decl.array)
			newVariable(decl.name, decl.type.newArrayType(newSubrange(0, decl.arraySize - 1)), value)
		else
			newVariable(decl.name, decl.type, value)
	}

	def static Variable newVariable(FB fb) {
		newVariable(fb.name, fb.type)
	}

	def static String getInitialValue(VarDeclaration decl) {
		decl.declaredInitialValue ?: decl.inheritedInitialValue
	}

	def static String getDeclaredInitialValue(VarDeclaration decl) {
		val result = decl.value?.value
		if(result.nullOrEmpty) null else result
	}

	def static String getInheritedInitialValue(VarDeclaration decl) {
		val result = decl.FBNetworkElement?.type?.interfaceList?.getVariable(decl.name)?.value?.value
		if(result.nullOrEmpty) null else result
	}
}
