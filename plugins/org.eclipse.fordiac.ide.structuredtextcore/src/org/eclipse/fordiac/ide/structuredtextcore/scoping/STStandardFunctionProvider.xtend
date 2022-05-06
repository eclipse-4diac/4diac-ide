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
package org.eclipse.fordiac.ide.structuredtextcore.scoping

import java.lang.reflect.Method
import java.util.List
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.function.Functions
import org.eclipse.fordiac.ide.model.eval.function.OnlySupportedBy
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*

class STStandardFunctionProvider {
	static final List<Class<? extends Functions>> DEFAULT_FUNCTIONS = #[StandardFunctions]
	final List<Class<? extends Functions>> functions

	/**
	 * Create a new instance using the default set of standard functions
	 */
	new() {
		this(DEFAULT_FUNCTIONS)
	}

	/**
	 * Create a new instance using the given set of standard functions
	 */
	new(List<Class<? extends Functions>> functions) {
		this.functions = functions
	}

	/**
	 * Get a list of all standard functions known to this provider
	 */
	def Iterable<STStandardFunction> get() {
		return functions.flatMap[Functions.getMethods(it)].map[toStandardFunction(emptyList)].toList
	}

	/**
	 * Get a list of all standard functions matching the given argument types
	 */
	def Iterable<STStandardFunction> get(List<DataType> argumentTypes) {
		return functions.flatMap[findMethodsFromDataTypes(argumentTypes)].map[toStandardFunction(argumentTypes)].toList
	}

	/**
	 * Convert a method to a standard function with concrete return and parameter types
	 */
	def protected create STCoreFactory.eINSTANCE.createSTStandardFunction toStandardFunction(Method method,
		List<DataType> argumentTypes) {
		name = method.name
		returnType = method.inferReturnTypeFromDataTypes(argumentTypes)
		inputParameters.addAll(method.inferParameterVariables(argumentTypes))
		onlySupportedBy.addAll(method.getAnnotationsByType(OnlySupportedBy).flatMap[value.toList])
	}

	/**
	 * Infer concrete parameter variable declarations based on a given method and argument types
	 */
	def protected Iterable<STVarDeclaration> inferParameterVariables(Method method, List<DataType> argumentTypes) {
		val ptypes = method.inferParameterTypesFromDataTypes(argumentTypes)
		(0 ..< argumentTypes.size).map [ index |
			newParameter(index, ptypes.get(index))
		]
	}

	/**
	 * Create a new parameter variable declaration
	 */
	def protected STVarDeclaration newParameter(int index, DataType parameterType) {
		STCoreFactory.eINSTANCE.createSTVarDeclaration => [
			name = '''IN«index»'''
			type = parameterType
		]
	}
}
