/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.util.Map
import java.util.regex.Pattern
import org.eclipse.fordiac.ide.model.eval.value.FBValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

class FBVariable extends AbstractVariable<FBValue> {
	static final Pattern MAP_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")
	static final Pattern MAP_KV_PATTERN = Pattern.compile(":=(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")

	@Accessors final Map<String, Variable<?>> members
	@Accessors final FBValue value

	new(String name, FBType type) {
		this(name, type, emptySet)
	}

	new(String name, FBType type, String value) {
		this(name, type)
		if(!value.nullOrEmpty) this.value = value
	}

	new(String name, FBType type, Value value) {
		this(name, type)
		if(value !== null) this.value = value
	}

	new(String name, FBType type, Iterable<Variable<?>> variables) {
		super(name, type)
		val members = variables?.toMap[getName] ?: newHashMap;
		(type.interfaceList.inputVars + type.interfaceList.outputVars).forEach[initializeMember(members)]
		(type.interfaceList.sockets + type.interfaceList.plugs).forEach[adapterFB.initializeMember(members)]
		if (type instanceof BaseFBType) {
			type.internalConstVars.forEach[initializeMember(members)]
			type.internalVars.forEach[initializeMember(members)]
			type.internalFbs.forEach[initializeMember(members)]
		}
		if (type instanceof BasicFBType) {
			members.computeIfAbsent(ECStateVariable.NAME)[new ECStateVariable(type)]
		}
		this.members = members.immutableCopy
		value = new FBValue(type, this.members)
	}

	def protected void initializeMember(VarDeclaration variable, Map<String, Variable<?>> members) {
		try {
			members.computeIfAbsent(variable.name)[newVariable(variable)]
		} catch (Exception e) {
			throw new Exception('''Error initializing member "«variable.name»": «e.message»''', e)
		}
	}

	def protected void initializeMember(FB fb, Map<String, Variable<?>> members) {
		try {
			members.computeIfAbsent(fb.name)[newVariable(fb)]
		} catch (Exception e) {
			throw new Exception('''Error initializing member "«fb.name»": «e.message»''', e)
		}
	}

	override setValue(Value value) {
		if (value instanceof FBValue) {
			if (value.type != type) {
				throw new IllegalArgumentException('''Cannot assign FB value with different type «value.type.name» to FB value of type «type.name»''')
			}
			value.members.forEach[name, variable|members.get(name).value = variable.value]
		} else
			throw new ClassCastException('''Cannot assign value with incompatible type «value.type.name» as «type.name»''')
	}

	override setValue(String value) {
		val trimmed = value.trim
		if (!trimmed.startsWith("(") || !trimmed.endsWith(")")) {
			throw new IllegalArgumentException("Not a valid FB value")
		}
		val inner = trimmed.substring(1, trimmed.length - 1)
		MAP_PATTERN.split(inner).forEach [ elem |
			val split = MAP_KV_PATTERN.split(elem)
			if (split.length != 2) {
				throw new IllegalArgumentException("Not a valid FB value")
			}
			val variable = members.get(split.get(0).trim)
			if (variable === null) {
				throw new IllegalArgumentException("Not a valid FB value")
			}
			variable.value = split.get(1).trim
		]
	}

	override validateValue(String value) {
		val trimmed = value.trim
		if (!trimmed.startsWith("(") || !trimmed.endsWith(")")) {
			return false
		}
		val inner = trimmed.substring(1, trimmed.length - 1)
		MAP_PATTERN.split(inner).forall [ elem |
			val split = MAP_KV_PATTERN.split(elem)
			if (split.length != 2) {
				return false
			}
			val variable = members.get(split.get(0).trim)
			if (variable === null) {
				return false
			}
			variable.validateValue(split.get(1).trim)
		]
	}
}
