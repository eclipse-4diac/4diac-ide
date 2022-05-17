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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Map
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyCharsType
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

final package class ExpressionAnnotations {

	private new() {
	}

	def package static INamedElement getResultType(STBinaryExpression expr) { getResultType(expr, false) }

	def package static INamedElement getDeclaredResultType(STBinaryExpression expr) { getResultType(expr, true) }

	def package static INamedElement getResultType(STBinaryExpression expr, boolean declared) {
		val left = declared ? expr.left?.declaredResultType : expr.left?.resultType
		val right = declared ? expr.right?.declaredResultType : expr.right?.resultType
		if (left instanceof DataType) {
			if (right instanceof DataType) {
				if (expr.op.isArithmetic) {
					if (left.isCompatibleWith(right))
						right
					else if (right.isCompatibleWith(left))
						left
					else if (left instanceof AnyDurationType && right instanceof AnyIntType)
						left
					else
						null
				} else if (expr.op.isComparison)
					ElementaryTypes.BOOL
				else
					null
			}
		} else
			null
	}

	def package static INamedElement getResultType(STUnaryExpression expr) { expr.expression?.resultType }

	def package static INamedElement getDeclaredResultType(STUnaryExpression expr) {
		expr.expression?.declaredResultType
	}

	def package static INamedElement getResultType(STMemberAccessExpression expr) { expr.member.resultType }

	def package static INamedElement getDeclaredResultType(STMemberAccessExpression expr) {
		expr.member.declaredResultType
	}

	def package static INamedElement getResultType(STArrayAccessExpression expr) { getResultType(expr, false) }

	def package static INamedElement getDeclaredResultType(STArrayAccessExpression expr) { getResultType(expr, true) }

	def package static INamedElement getResultType(STArrayAccessExpression expr, boolean declared) {
		val arrayType = declared ? expr.receiver.declaredResultType : expr.receiver.resultType
		if (arrayType instanceof ArrayType) {
			if (expr.index.size < arrayType.subranges.size) { // not consumed all dimensions
				DataFactory.eINSTANCE.createArrayType => [
					baseType = arrayType.baseType
					subranges.addAll(arrayType.subranges.drop(expr.index.size).map[copy])
				]
			} else // consumed all dimensions
				arrayType.baseType
		} else
			null
	}

	def package static INamedElement getResultType(STFeatureExpression expr) { getDeclaredResultType(expr) }

	def package static INamedElement getDeclaredResultType(STFeatureExpression expr) {
		// mirror changes here in callaSTCoreScopeProvider.isApplicableForFeatureReference(IEObjectDescription)
		switch (feature : expr.feature) {
			VarDeclaration,
			STVarDeclaration,
			FB:
				feature.featureType
			ICallable:
				feature.returnType
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
		getDeclaredResultType(expr) ?: switch (result : expr.expectedType) {
			AnyNumType,
			AnyBitType: result
			default: null
		} ?: switch (it : expr.value) {
			Boolean: ElementaryTypes.BOOL
			BigDecimal: ElementaryTypes.LREAL
			BigInteger case checkRange(Byte.MIN_VALUE, Byte.MAX_VALUE): ElementaryTypes.SINT
			BigInteger case checkRangeUnsigned(0xff#bi): ElementaryTypes.USINT
			BigInteger case checkRange(Short.MIN_VALUE, Short.MAX_VALUE): ElementaryTypes.INT
			BigInteger case checkRangeUnsigned(0xffff#bi): ElementaryTypes.UINT
			BigInteger case checkRange(Integer.MIN_VALUE, Integer.MAX_VALUE): ElementaryTypes.DINT
			BigInteger case checkRangeUnsigned(0xffffffff#bi): ElementaryTypes.UDINT
			BigInteger case checkRange(Long.MIN_VALUE, Long.MAX_VALUE): ElementaryTypes.LINT
			BigInteger case checkRangeUnsigned(0xffffffffffffffff#bi): ElementaryTypes.ULINT
			default: null
		}
	}

	def package static INamedElement getDeclaredResultType(STNumericLiteral expr) { expr.type }

	def private static checkRange(BigInteger value, long lower, long upper) {
		value >= BigInteger.valueOf(lower) && value <= BigInteger.valueOf(upper)
	}

	def private static checkRangeUnsigned(BigInteger value, BigInteger upper) {
		value.signum >= 0 && value <= upper
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
		getDeclaredResultType(expr) ?: switch (result : expr.expectedType) {
			AnyCharsType: result
			default: null
		} ?: if (expr.value.length == 1) {
			if(expr.value.wide) ElementaryTypes.WCHAR else ElementaryTypes.CHAR
		} else {
			if(expr.value.wide) ElementaryTypes.WSTRING else ElementaryTypes.STRING
		}
	}

	def package static INamedElement getDeclaredResultType(STStringLiteral expr) { expr.type }

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
		expr.values.map[resultType].reduce[first, second|first.commonSupertype(second)].addDimension(expr)
	}

	def package static INamedElement getDeclaredResultType(STArrayInitializerExpression expr) {
		expr.values.map[declaredResultType].reduce[first, second|first.commonSupertype(second)].addDimension(expr)
	}

	def package static INamedElement getResultType(STArrayInitElement expr) {
		if (expr.initExpressions.empty)
			expr.indexOrInitExpression.resultType
		else
			expr.initExpressions.map[resultType].reduce[first, second|first.commonSupertype(second)]
	}

	def package static INamedElement getDeclaredResultType(STArrayInitElement expr) {
		if (expr.initExpressions.empty)
			expr.indexOrInitExpression.resultType
		else
			expr.initExpressions.map[declaredResultType].reduce[first, second|first.commonSupertype(second)]
	}

	def package static Map<INamedElement, STExpression> getMappedInputArguments(STFeatureExpression expr) {
		val feature = expr.feature
		if (feature instanceof ICallable) {
			val parameters = feature.inputParameters
			if (expr.parameters.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				parameters.toInvertedMap [ parameter |
					(expr.parameters.get(parameters.indexOf(parameter)) as STCallUnnamedArgument).argument
				].unmodifiableView
			} else { // named arguments
				val namedArguments = expr.parameters.filter(STCallNamedInputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)?.argument
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static Map<INamedElement, STExpression> getMappedOutputArguments(STFeatureExpression expr) {
		val feature = expr.feature
		if (feature instanceof ICallable) {
			val parameters = feature.outputParameters
			if (expr.parameters.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				val inputCount = feature.inputParameters.size
				val inOutCount = feature.inOutParameters.size
				parameters.toInvertedMap [ parameter |
					(expr.parameters.get(inputCount + inOutCount +
						parameters.indexOf(parameter)) as STCallUnnamedArgument).argument
				].unmodifiableView
			} else { // named arguments
				val namedArguments = expr.parameters.filter(STCallNamedOutputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)?.argument
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static Map<INamedElement, STExpression> getMappedInOutArguments(STFeatureExpression expr) {
		val feature = expr.feature
		if (feature instanceof ICallable) {
			val parameters = feature.inOutParameters
			if (expr.parameters.head instanceof STCallUnnamedArgument) { // first arg is unnamed -> expect remainder to be unnamed as well (mixing is illegal)
				val inputCount = feature.inputParameters.size
				parameters.toInvertedMap [ parameter |
					(expr.parameters.get(inputCount + parameters.indexOf(parameter)) as STCallUnnamedArgument).argument
				].unmodifiableView
			} else { // named arguments
				val namedArguments = expr.parameters.filter(STCallNamedInputArgument).toMap[parameter]
				parameters.toInvertedMap [ parameter |
					namedArguments.get(parameter)?.argument
				].unmodifiableView
			}
		} else
			emptyMap
	}

	def package static INamedElement addDimension(INamedElement type, STArrayInitializerExpression expr) {
		val size = expr.values.map [
			if (initExpressions.empty)
				1
			else
				(indexOrInitExpression as STElementaryInitializerExpression).value.asConstantInt
		].fold(0)[a, b|a + b]
		if (type instanceof ArrayType)
			type.baseType.newArrayType(#[newSubrange(0, size - 1)] + type.subranges.map[copy])
		else if (type instanceof DataType)
			type.newArrayType(newSubrange(0, size - 1))
	}

	def package static INamedElement commonSupertype(INamedElement first, INamedElement second) {
		if (first == second)
			first
		else if (first instanceof DataType) {
			if (second instanceof DataType) {
				if (first.isCompatibleWith(second))
					second
				else if (second.isCompatibleWith(first))
					first
			}
		}
	}
}
