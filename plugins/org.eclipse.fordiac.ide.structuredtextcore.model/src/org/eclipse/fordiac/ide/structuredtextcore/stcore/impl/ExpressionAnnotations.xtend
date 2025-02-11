/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Collection
import java.util.Map
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyDateType
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

final package class ExpressionAnnotations {

	private new() {
	}

	def package static INamedElement getResultType(STBinaryExpression expr) { getResultType(expr, false) }

	def package static INamedElement getDeclaredResultType(STBinaryExpression expr) { getResultType(expr, true) }

	def package static INamedElement getResultType(STBinaryExpression expr, boolean declared) {
		if (expr.op.comparison)
			ElementaryTypes.BOOL // always return BOOL for comparison
		else {
			val left = declared ? expr.left?.declaredResultType : expr.left?.resultType
			val right = declared ? expr.right?.declaredResultType : expr.right?.resultType
			if (left instanceof DataType) {
				if (right instanceof DataType) {
					if (expr.op.arithmetic) {
						if (left instanceof AnyDurationType && right instanceof AnyNumType)
							left
						else if (left instanceof AnyDateType && right instanceof TimeType)
							left
						else if (left instanceof AnyDateType && right instanceof LtimeType)
							left.equivalentAnyLDateType
						else if (left.instanceofAnySDateType && right.instanceofAnySDateType)
							ElementaryTypes.TIME
						else if (left instanceof AnyDateType && right instanceof AnyDateType)
							ElementaryTypes.LTIME
						else if (left instanceof AnyDurationType && right instanceof AnyDurationType)
							left.commonSupertype(right)
						else
							commonSupertype(left.equivalentAnyNumType, right.equivalentAnyNumType) ?:
								commonSupertype(left.equivalentAnyUnsignedType, right.equivalentAnyUnsignedType)
					} else if (expr.op.logical) {
						commonSupertype(left.equivalentAnyBitType, right.equivalentAnyBitType)
					} else if (expr.op.range) {
						left.commonSupertype(right)
					} else
						null
				} else if (declared)
					left // if looking for declared result type and right is null/invalid, propagate left
				else
					null
			} else if (declared && right instanceof DataType)
				right // if looking for declared result type and left is null/invalid, propagate right
			else
				null
		}
	}

	def package static INamedElement getResultType(STUnaryExpression expr) { expr.expression?.resultType }

	def package static INamedElement getDeclaredResultType(STUnaryExpression expr) {
		expr.expression?.declaredResultType
	}

	def package static INamedElement getResultType(STMemberAccessExpression expr) { expr.member?.resultType }

	def package static INamedElement getDeclaredResultType(STMemberAccessExpression expr) {
		expr.member?.declaredResultType
	}

	def package static INamedElement getResultType(STArrayAccessExpression expr) { getResultType(expr, false) }

	def package static INamedElement getDeclaredResultType(STArrayAccessExpression expr) { getResultType(expr, true) }

	def package static INamedElement getResultType(STArrayAccessExpression expr, boolean declared) {
		val receiverType = declared ? expr.receiver?.declaredResultType : expr.receiver?.resultType
		switch (receiverType) {
			ArrayType:
				if (expr.index.size < receiverType.subranges.size) { // not consumed all dimensions
					receiverType.baseType.newArrayType(receiverType.subranges.drop(expr.index.size).map[copy])
				} else // consumed all dimensions
					receiverType.baseType
			StringType:
				ElementaryTypes.CHAR
			WstringType:
				ElementaryTypes.WCHAR
		}
	}

	def package static INamedElement getResultType(STFeatureExpression expr) {
		switch (feature : expr.feature) {
			STStandardFunction:
				feature.javaMethod.inferReturnTypeFromDataTypes([switch (type: expr.expectedType) { DataType: type }], [
					expr.parameters.map[resultType].filter(DataType).toList
				])
			default:
				getDeclaredResultType(expr)
		}
	}

	def package static INamedElement getDeclaredResultType(STFeatureExpression expr) {
		// mirror changes here in callaSTCoreScopeProvider.isApplicableForFeatureReference(IEObjectDescription)
		switch (feature : expr.feature) {
			VarDeclaration,
			STVarDeclaration,
			AdapterDeclaration,
			FB case !expr.call:
				feature.featureType
			STStandardFunction:
				feature.javaMethod.inferReturnTypeFromDataTypes(null) [
					val argumentTypes = expr.parameters.map[declaredResultType].filter(DataType).toList
					if (argumentTypes.size == expr.parameters.size) // all parameters have valid types
						argumentTypes
					else
						null
				]
			ICallable:
				feature.returnType
		}
	}

	def package static INamedElement getResultType(STBuiltinFeatureExpression expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS:
				if(expr.call) null else expr.eResource?.contents?.filter(FBType)?.head
		}
	}

	def package static INamedElement getResultType(STMultibitPartialExpression expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STMultibitPartialExpression expr) {
		switch (expr.specifier) {
			case null,
			case X: ElementaryTypes.BOOL
			case B: ElementaryTypes.BYTE
			case W: ElementaryTypes.WORD
			case D: ElementaryTypes.DWORD
			case L: ElementaryTypes.LWORD
		}
	}

	def package static INamedElement getResultType(STNumericLiteral expr) {
		expr.type ?: switch (result : expr.expectedType) {
			DataType case result.isNumericValueValid(expr.value):
				result
			AnyUnsignedType:
				switch (it : expr.value) {
					BigInteger case checkRangeUnsigned(0xff#bi): ElementaryTypes.USINT
					BigInteger case checkRangeUnsigned(0xffff#bi): ElementaryTypes.UINT
					BigInteger case checkRangeUnsigned(0xffffffff#bi): ElementaryTypes.UDINT
					BigInteger case checkRangeUnsigned(0xffffffffffffffff#bi): ElementaryTypes.ULINT
					default: null
				}
			AnyBitType:
				switch (it : expr.value) {
					BigInteger case checkRangeUnsigned(0xff#bi): ElementaryTypes.BYTE
					BigInteger case checkRangeUnsigned(0xffff#bi): ElementaryTypes.WORD
					BigInteger case checkRangeUnsigned(0xffffffff#bi): ElementaryTypes.DWORD
					BigInteger case checkRangeUnsigned(0xffffffffffffffff#bi): ElementaryTypes.LWORD
					default: null
				}
			default:
				null
		} ?: getValueType(expr)
	}

	def package static INamedElement getDeclaredResultType(STNumericLiteral expr) { expr.type ?: getValueType(expr) }

	def package static INamedElement getValueType(STNumericLiteral expr) {
		switch (it : expr.value) {
			Boolean: ElementaryTypes.BOOL
			BigDecimal: ElementaryTypes.LREAL
			BigInteger case checkRange(Byte.MIN_VALUE, Byte.MAX_VALUE): ElementaryTypes.SINT
			BigInteger case checkRange(Short.MIN_VALUE, Short.MAX_VALUE): ElementaryTypes.INT
			BigInteger case checkRange(Integer.MIN_VALUE, Integer.MAX_VALUE): ElementaryTypes.DINT
			BigInteger case checkRange(Long.MIN_VALUE, Long.MAX_VALUE): ElementaryTypes.LINT
			BigInteger case checkRangeUnsigned(0xffffffffffffffff#bi): ElementaryTypes.ULINT
			default: null
		}
	}

	def package static INamedElement getResultType(STDateLiteral expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STDateLiteral expr) { expr.type }

	def package static INamedElement getResultType(STTimeLiteral expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STTimeLiteral expr) { expr.type }

	def package static INamedElement getResultType(STTimeOfDayLiteral expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STTimeOfDayLiteral expr) { expr.type }

	def package static INamedElement getResultType(STDateAndTimeLiteral expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STDateAndTimeLiteral expr) { expr.type }

	def package static INamedElement getResultType(STStringLiteral expr) {
		expr.type ?: switch (result : expr.expectedType) {
			DataType case result.isStringValueValid(expr.value): result
			default: null
		} ?: getValueType(expr)
	}

	def package static INamedElement getDeclaredResultType(STStringLiteral expr) { expr.type ?: getValueType(expr) }

	def package static INamedElement getValueType(STStringLiteral expr) {
		if (expr.value.length == 1) {
			if(expr.value.wide) ElementaryTypes.WCHAR else ElementaryTypes.CHAR
		} else {
			if(expr.value.wide) ElementaryTypes.WSTRING else ElementaryTypes.STRING
		}
	}

	def package static INamedElement getResultType(STEnumLiteral expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STEnumLiteral expr) { expr.value?.type }

	def package static INamedElement getResultType(STCallUnnamedArgument arg) { arg.argument?.resultType }

	def package static INamedElement getDeclaredResultType(STCallUnnamedArgument arg) {
		arg.argument?.declaredResultType
	}

	def package static INamedElement getResultType(STCallNamedInputArgument arg) { arg.argument?.resultType }

	def package static INamedElement getDeclaredResultType(STCallNamedInputArgument arg) {
		arg.argument?.declaredResultType
	}

	def package static INamedElement getResultType(STCallNamedOutputArgument arg) { arg.argument?.resultType }

	def package static INamedElement getDeclaredResultType(STCallNamedOutputArgument arg) {
		arg.argument?.declaredResultType
	}

	def package static INamedElement getResultType(STElementaryInitializerExpression expr) { expr.value?.resultType }

	def package static INamedElement getDeclaredResultType(STElementaryInitializerExpression expr) {
		expr.value?.declaredResultType
	}

	def package static INamedElement getResultType(STArrayInitializerExpression expr) {
		expr.expectedType ?:
			expr.values.map[resultType].reduce[first, second|first.commonSupertype(second)].addDimension(expr)
	}

	def package static INamedElement getDeclaredResultType(STArrayInitializerExpression expr) {
		expr.expectedType ?:
			expr.values.map[declaredResultType].reduce[first, second|first.commonSupertype(second)].addDimension(expr)
	}

	def package static INamedElement getResultType(STSingleArrayInitElement expr) {
		expr.initExpression.resultType
	}

	def package static INamedElement getDeclaredResultType(STSingleArrayInitElement expr) {
		expr.initExpression.declaredResultType
	}

	def package static INamedElement getResultType(STRepeatArrayInitElement expr) {
		expr.initExpressions.map[resultType].reduce[first, second|first.commonSupertype(second)]
	}

	def package static INamedElement getDeclaredResultType(STRepeatArrayInitElement expr) {
		expr.initExpressions.map[declaredResultType].reduce[first, second|first.commonSupertype(second)]
	}

	def package static INamedElement getResultType(STStructInitializerExpression expr) {
		getDeclaredResultType(expr) ?: expr.expectedType
	}

	def package static INamedElement getDeclaredResultType(STStructInitializerExpression expr) { expr.type }

	def package static INamedElement getResultType(STStructInitElement expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STStructInitElement expr) { expr.variable.featureType }

	def package static Map<INamedElement, STCallArgument> getMappedInputArguments(STFeatureExpression expr) {
		expr.feature.computeMappedInputArguments(expr.parameters)
	}

	def package static Map<INamedElement, STCallArgument> getMappedOutputArguments(STFeatureExpression expr) {
		expr.feature.computeMappedOutputArguments(expr.parameters)
	}

	def package static Map<INamedElement, STCallArgument> getMappedInOutArguments(STFeatureExpression expr) {
		expr.feature.computeMappedInOutArguments(expr.parameters)
	}

	def package static Map<INamedElement, STCallArgument> getMappedInputArguments(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS:
				expr.eResource?.contents?.filter(FBType)?.head?.computeMappedInputArguments(expr.parameters)
		}
	}

	def package static Map<INamedElement, STCallArgument> getMappedOutputArguments(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS:
				expr.eResource?.contents?.filter(FBType)?.head?.computeMappedOutputArguments(expr.parameters)
		}
	}

	def package static Map<INamedElement, STCallArgument> getMappedInOutArguments(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS:
				expr.eResource?.contents?.filter(FBType)?.head?.computeMappedInOutArguments(expr.parameters)
		}
	}

	def package static Map<INamedElement, STCallArgument> computeMappedInputArguments(INamedElement feature,
		Collection<STCallArgument> arguments) {
		if (feature instanceof ICallable) {
			val parameters = feature.computeInputParameters(arguments)
			if (arguments.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				parameters.toInvertedMap [ parameter |
					val index = parameters.indexOf(parameter)
					index < arguments.size ? arguments.get(index) : null
				].unmodifiableView
			} else { // named arguments
				val namedArguments = arguments.filter(STCallNamedInputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static Map<INamedElement, STCallArgument> computeMappedOutputArguments(INamedElement feature,
		Collection<STCallArgument> arguments) {
		if (feature instanceof ICallable) {
			val parameters = feature.computeOutputParameters(arguments)
			if (arguments.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				val inputCount = feature.inputParameters.size
				val inOutCount = feature.inOutParameters.size
				parameters.toInvertedMap [ parameter |
					val index = inputCount + inOutCount + parameters.indexOf(parameter)
					index < arguments.size ? arguments.get(index) : null
				].unmodifiableView
			} else { // named arguments
				val namedArguments = arguments.filter(STCallNamedOutputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static Map<INamedElement, STCallArgument> computeMappedInOutArguments(INamedElement feature,
		Collection<STCallArgument> arguments) {
		if (feature instanceof ICallable) {
			val parameters = feature.computeInOutParameters(arguments)
			if (arguments.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				val inputCount = feature.inputParameters.size
				parameters.toInvertedMap [ parameter |
					val index = inputCount + parameters.indexOf(parameter)
					index < arguments.size ? arguments.get(index) : null
				].unmodifiableView
			} else { // named arguments
				val namedArguments = arguments.filter(STCallNamedInputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static Map<INamedElement, STInitializerExpression> getMappedStructInitElements(
		STStructInitializerExpression expr) {
		val struct = expr.resultType
		if (struct instanceof StructuredType) {
			val namedInitElements = expr.values.toMap[variable]
			struct.memberVariables.toInvertedMap [ parameter |
				namedInitElements.get(parameter)?.value
			].unmodifiableView
		} else
			emptyMap
	}

	def package static INamedElement addDimension(INamedElement type, STArrayInitializerExpression expr) {
		try {
			val size = expr.values.map [
				switch (it) {
					STRepeatArrayInitElement: repetitions.intValueExact
					default: 1
				}
			].fold(0)[a, b|a + b]
			if (type instanceof ArrayType)
				type.baseType.newArrayType(#[newSubrange(0, size - 1)] + type.subranges.map[copy])
			else if (type instanceof DataType)
				type.newArrayType(newSubrange(0, size - 1))
		} catch (ArithmeticException e) {
			null // invalid initializer expression
		}
	}

	def package static INamedElement commonSupertype(INamedElement first, INamedElement second) {
		if (first == second)
			first
		else if (first instanceof DataType) {
			if (second instanceof DataType) {
				if (first.isAssignableFrom(second))
					first
				else if (second.isAssignableFrom(first))
					second
			}
		}
	}

	def package static DataType commonSupertype(DataType first, DataType second) {
		if (first === null)
			second
		else if (second === null)
			first
		else if (first.isAssignableFrom(second))
			first
		else if (second.isAssignableFrom(first))
			second
		else
			null
	}
}
