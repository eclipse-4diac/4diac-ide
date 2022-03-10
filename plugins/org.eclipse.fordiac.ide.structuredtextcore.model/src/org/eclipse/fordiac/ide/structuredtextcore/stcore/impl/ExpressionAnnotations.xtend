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
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.Subrange
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
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

import static extension org.eclipse.emf.common.util.ECollections.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

final package class ExpressionAnnotations {

	private new() {
	}

	def package static INamedElement getResultType(STBinaryExpression expr) {
		val left = expr.left?.resultType
		val right = expr.right?.resultType
		if (left instanceof DataType) {
			if (right instanceof DataType) {
				if (expr.op.isArithmetic) {
					if (left.isCompatibleWith(right))
						right
					else if (right.isCompatibleWith(left))
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

	def package static INamedElement getResultType(STMemberAccessExpression expr) { expr.member.resultType }

	def package static INamedElement getResultType(STArrayAccessExpression expr) {
		val arrayType = expr.receiver.resultType
		if (arrayType instanceof ArrayType) {
			if (expr.index.size < arrayType.subranges.size) { // not consumed all dimensions
				DataFactory.eINSTANCE.createArrayType => [
					baseType = arrayType.baseType
					subranges.addAll(arrayType.subranges.drop(expr.index.size))
				]
			} else // consumed all dimensions
				arrayType.baseType
		} else
			null
	}

	def package static INamedElement getResultType(STFeatureExpression expr) {
		// mirror changes here in callaSTCoreScopeProvider.isApplicableForFeatureReference(IEObjectDescription)
		switch (feature : expr.feature) {
			VarDeclaration:
				if (feature.array)
					feature.type.newArrayType(newSubrange(0, feature.arraySize))
				else
					feature.type
			STVarDeclaration:
				if (feature.array)
					(feature.type as DataType).newArrayType(feature.ranges.map[toSubrange])
				else
					feature.type
			FB:
				feature.type
			ICallable:
				feature.returnType
		}
	}

	def private static Subrange toSubrange(STExpression expr) {
		switch (expr) {
			STBinaryExpression case expr.op === STBinaryOperator.RANGE:
				newSubrange(expr.left.asConstantInt, expr.right.asConstantInt)
			default:
				newSubrange(0, expr.asConstantInt)
		}
	}

	def private static int asConstantInt(STExpression expr) {
		switch (expr) {
			STNumericLiteral: expr.value.intValue
			default: 0
		}
	}

	def private static ArrayType newArrayType(DataType arrayBaseType, Subrange... arraySubranges) {
		arrayBaseType.newArrayType(arraySubranges as Iterable<Subrange>)
	}

	def private static ArrayType newArrayType(DataType arrayBaseType, Iterable<Subrange> arraySubranges) {
		DataFactory.eINSTANCE.createArrayType => [
			baseType = arrayBaseType
			subranges.addAll(arraySubranges)
		]
	}

	def private static newSubrange(int lower, int upper) {
		DataFactory.eINSTANCE.createSubrange => [
			lowerLimit = lower
			upperLimit = upper
		]
	}

	def package static INamedElement getResultType(STMultibitPartialExpression expr) {
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
		expr.type ?: switch (it : expr.value) {
			case scale > 0: ElementaryTypes.LREAL
			case checkRange(Byte.MIN_VALUE, Byte.MAX_VALUE): ElementaryTypes.SINT
			case checkRangeUnsigned(0xff): ElementaryTypes.USINT
			case checkRange(Short.MIN_VALUE, Short.MAX_VALUE): ElementaryTypes.INT
			case checkRangeUnsigned(0xffff): ElementaryTypes.UINT
			case checkRange(Integer.MIN_VALUE, Integer.MAX_VALUE): ElementaryTypes.DINT
			case checkRangeUnsigned(0xffffffff): ElementaryTypes.UDINT
			case checkRange(Long.MIN_VALUE, Long.MAX_VALUE): ElementaryTypes.LINT
			default: ElementaryTypes.LINT
		}
	}

	def private static checkRange(BigDecimal value, long lower, long upper) {
		value.scale <= 0 && value.longValue >= lower && value.longValue <= upper
	}

	def private static checkRangeUnsigned(BigDecimal value, long upper) {
		value.scale <= 0 && Long.compareUnsigned(value.longValue, upper) < 0
	}

	def package static INamedElement getResultType(STDateLiteral expr) { expr.type }

	def package static INamedElement getResultType(STTimeLiteral expr) { expr.type }

	def package static INamedElement getResultType(STTimeOfDayLiteral expr) { expr.type }

	def package static INamedElement getResultType(STDateAndTimeLiteral expr) { expr.type }

	def package static INamedElement getResultType(STStringLiteral expr) {
		expr.type ?: if (expr.value.length == 1) {
			if(expr.value.wide) ElementaryTypes.WCHAR else ElementaryTypes.CHAR
		} else {
			if(expr.value.wide) ElementaryTypes.WSTRING else ElementaryTypes.STRING
		}
	}

	def package static EList<STCallArgument> getMappedInputArguments(STFeatureExpression expr) {
		val feature = expr.feature
		if (feature instanceof ICallable) {
			val parameters = feature.inputParameters
			val namedArguments = expr.parameters.filter(STCallNamedInputArgument).toMap[target]
			val unnamedArguments = expr.parameters.filter(STCallUnnamedArgument).iterator
			parameters.map[namedArguments.get(it) ?: if(unnamedArguments.hasNext) unnamedArguments.next else null].
				newBasicEList.unmodifiableEList
		} else
			emptyEList
	}

	def package static EList<STCallArgument> getMappedOutputArguments(STFeatureExpression expr) {
		val feature = expr.feature
		if (feature instanceof ICallable) {
			val parameters = feature.outputParameters
			val namedArguments = expr.parameters.filter(STCallNamedOutputArgument).toMap[source]
			parameters.map[namedArguments.get(it)].newBasicEList.unmodifiableEList
		} else
			emptyEList
	}
}
