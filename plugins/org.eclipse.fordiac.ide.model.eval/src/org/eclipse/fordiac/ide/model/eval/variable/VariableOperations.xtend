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

import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import org.eclipse.fordiac.ide.model.data.AnyType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser
import org.eclipse.fordiac.ide.model.eval.EvaluatorCache
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper
import org.eclipse.fordiac.ide.model.libraryElement.Attribute
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.value.TypedValueConverter

import static extension org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser.isSimpleTypeDeclaration
import static extension org.eclipse.fordiac.ide.model.eval.EvaluatorFactory.*
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType

final class VariableOperations {
	private new() {
	}

	def static Variable<?> newVariable(String name, INamedElement type) {
		switch (type) {
			ArrayType: new ArrayVariable(name, type)
			StructuredType: new StructVariable(name, type)
			AnyType: new ElementaryVariable(name, type)
			FBType: new FBVariable(name, type)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable '«name ?: "null"»' of type «type?.name ?: "null"»''')
		}
	}

	def static Variable<?> newVariable(String name, INamedElement type, String value) {
		switch (type) {
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			AnyType: new ElementaryVariable(name, type, value)
			FBType: new FBVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable '«name ?: "null"»' of type «type?.name ?: "null"»''')
		}
	}

	def static Variable<?> newVariable(String name, INamedElement type, Value value) {
		switch (type) {
			ArrayType: new ArrayVariable(name, type, value)
			StructuredType: new StructVariable(name, type, value)
			AnyType: new ElementaryVariable(name, type, value)
			FBType: new FBVariable(name, type, value)
			default: throw new UnsupportedOperationException('''Cannot instanciate variable '«name ?: "null"»' of type «type?.name ?: "null"»''')
		}
	}

	def static Variable<?> newVariable(String name, Value value) {
		newVariable(name, value.type, value)
	}

	def static Variable<?> newVariable(VarDeclaration decl) {
		try(val cache = EvaluatorCache.open()) {
			if (decl.hasDeclaredInitialValue) {
				newVariable(decl.name, decl.evaluateResultType, cache.computeInitialValueIfAbsent(decl) [
					val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
					if (evaluator instanceof VariableEvaluator) {
						evaluator.evaluate
					} else
						throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
				])
			} else if (decl.hasInheritedInitialValue) {
				// use variable from FB type since the initial value is inherited from the FB type
				val typeVariable = decl.FBNetworkElement?.type?.interfaceList?.getVariable(decl.name) ?: decl
				newVariable(decl.name, decl.evaluateResultType, cache.computeInitialValueIfAbsent(typeVariable) [
					val evaluator = typeVariable.createEvaluator(VarDeclaration, null, emptySet, null)
					if (evaluator instanceof VariableEvaluator) {
						evaluator.evaluate
					} else
						throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
				])
			} else
				newVariable(decl.name, decl.evaluateResultType)
		}
	}

	def static Variable<?> newVariable(VarDeclaration decl, String initialValue) {
		decl.withValue(initialValue).newVariable
	}

	def static Variable<?> newVariable(VarDeclaration decl, Value value) {
		newVariable(decl.name, decl.evaluateResultType, value)
	}

	def static Variable<?> newVariable(FB fb) {
		newVariable(fb.name, fb.type)
	}

	def static Variable<?> newVariable(Attribute attr) {
		if (!attr.value.nullOrEmpty) {
				val evaluator = attr.createEvaluator(Attribute, null, emptySet, null)
				if (evaluator instanceof VariableEvaluator) {
					evaluator.evaluateVariable
				} else
					throw new UnsupportedOperationException("No suitable evaluator for Attribute found")
		} else
			newVariable(attr.name, attr.type)
	}

	def static Variable<?> newVariable(DirectlyDerivedType type) {
		if (!type.initialValue.nullOrEmpty) {
				val evaluator = type.createEvaluator(DirectlyDerivedType, null, emptySet, null)
				if (evaluator instanceof VariableEvaluator) {
					evaluator.evaluateVariable
				} else
					throw new UnsupportedOperationException("No suitable evaluator for DirectlyDerivedType found")
		} else
			newVariable(type.name, type.baseType)
	}

	def static INamedElement evaluateResultType(VarDeclaration decl) {
		if (decl.array) {
			try(val cache = EvaluatorCache.open()) {
				cache.computeResultTypeIfAbsent(decl) [
					if (decl.arraySize?.value.simpleTypeDeclaration)
						TypeDeclarationParser.parseSimpleTypeDeclaration(decl.type, decl.arraySize.value)
					else {
						val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
						if (evaluator instanceof VariableEvaluator) {
							evaluator.evaluateResultType
						} else
							throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
					}
				]
			}
		} else
			decl.type
	}

	def static String validateValue(VarDeclaration decl) {
		if (!decl.simpleInitialValue) {
			val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
			try {
				evaluator?.prepare
				""
			} catch (Exception e) {
				e.message;
			}
		} else
			""
	}

	def static boolean validateValue(VarDeclaration decl, List<String> errors, List<String> warnings,
		List<String> infos) {
		if (!decl.simpleInitialValue) {
			val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
			if (evaluator instanceof VariableEvaluator) {
				try {
					evaluator.validateVariable(errors, warnings, infos)
				} catch (Exception e) {
					false
				}
			} else
				throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
		} else
			true
	}

	def static String validateType(VarDeclaration decl) {
		if (!decl.arraySize?.value.simpleTypeDeclaration) {
			val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
			if (evaluator instanceof VariableEvaluator) {
				try {
					evaluator.evaluateResultType
					""
				} catch (Exception e) {
					e.message;
				}
			} else
				throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
		} else
			""
	}

	def static boolean validateType(VarDeclaration decl, List<String> errors, List<String> warnings,
		List<String> infos) {
		if (!decl.arraySize?.value.simpleTypeDeclaration) {
			val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
			if (evaluator instanceof VariableEvaluator) {
				try {
					evaluator.validateResultType(errors, warnings, infos)
				} catch (Exception e) {
					false
				}
			} else
				throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
		} else
			true
	}

	def static String validateValue(VarDeclaration decl, String initialValue) {
		decl.withValue(initialValue).validateValue
	}

	def static String validateValue(DataType dataType, String initialValue) {
		(LibraryElementFactory.eINSTANCE.createVarDeclaration => [
			name = "dummy"
			type = dataType
			value = LibraryElementFactory.eINSTANCE.createValue => [value = initialValue]
		]).validateValue
	}

	def static Value evaluateValue(DataType dataType, String initialValue) {
		(LibraryElementFactory.eINSTANCE.createVarDeclaration => [
			name = "dummy"
			type = dataType
			value = LibraryElementFactory.eINSTANCE.createValue => [value = initialValue]
		]).newVariable.value
	}

	def static Set<String> getDependencies(VarDeclaration decl) {
		if (!decl.arraySize?.value.simpleTypeDeclaration || !decl.simpleInitialValue) {
			val evaluator = decl.createEvaluator(VarDeclaration, null, emptySet, null)
			if (evaluator instanceof VariableEvaluator) {
				evaluator.dependencies
			} else
				throw new UnsupportedOperationException("No suitable evaluator for VarDeclaration found")
		} else
			#{PackageNameHelper.getFullTypeName(decl.type)}
	}

	def static Set<String> getAllDependencies(EObject object) {
		val evaluator = object.createEvaluator(object.eClass.instanceClass, null, emptySet, null);
		if (evaluator !== null)
			evaluator.dependencies
		else
			object.eAllContents.filter(VarDeclaration).flatMap[dependencies.iterator].toSet
	}

	def static boolean hasInitialValue(VarDeclaration decl) {
		decl.hasDeclaredInitialValue || decl.hasInheritedInitialValue
	}

	def static boolean hasDeclaredInitialValue(VarDeclaration decl) {
		!decl.value?.value.nullOrEmpty
	}

	def static boolean hasInheritedInitialValue(VarDeclaration decl) {
		!decl.FBNetworkElement?.type?.interfaceList?.getVariable(decl.name)?.value?.value.nullOrEmpty
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

	def private static withValue(VarDeclaration decl, String valueString) {
		val copy = LibraryElementFactory.eINSTANCE.createVarDeclaration => [
			name = decl.name
			type = decl.type
			ArraySizeHelper.setArraySize(it, ArraySizeHelper.getArraySize(decl))
			value = LibraryElementFactory.eINSTANCE.createValue => [value = valueString]
		]
		if (decl.eResource !== null) {
			new ResourceImpl(decl.eResource.URI).contents.add(copy)
		}
		copy
	}

	def private static isSimpleInitialValue(VarDeclaration decl) {
		if (!decl.hasDeclaredInitialValue)
			true
		else if (decl.array)
			false
		else
			try {
				new TypedValueConverter(decl.type, true).toValue(decl.declaredInitialValue)
				true
			} catch (Exception e) {
				false
			}
	}
}
