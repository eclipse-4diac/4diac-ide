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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.util

import java.math.BigDecimal
import java.math.BigInteger
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.ByteType
import org.eclipse.fordiac.ide.model.data.CharType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DintType
import org.eclipse.fordiac.ide.model.data.DwordType
import org.eclipse.fordiac.ide.model.data.IntType
import org.eclipse.fordiac.ide.model.data.LintType
import org.eclipse.fordiac.ide.model.data.LrealType
import org.eclipse.fordiac.ide.model.data.LwordType
import org.eclipse.fordiac.ide.model.data.RealType
import org.eclipse.fordiac.ide.model.data.SintType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.Subrange
import org.eclipse.fordiac.ide.model.data.UdintType
import org.eclipse.fordiac.ide.model.data.UintType
import org.eclipse.fordiac.ide.model.data.UlintType
import org.eclipse.fordiac.ide.model.data.UsintType
import org.eclipse.fordiac.ide.model.data.WcharType
import org.eclipse.fordiac.ide.model.data.WordType
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement

final class STCoreUtil {

	private new() {
	}

	def static boolean isArithmetic(STUnaryOperator operator) {
		switch (operator) {
			case PLUS,
			case MINUS: true
			default: false
		}
	}

	def static boolean isLogical(STUnaryOperator operator) {
		switch (operator) {
			case NOT: true
			default: false
		}
	}

	def static boolean isArithmetic(STBinaryOperator operator) {
		switch (operator) {
			case ADD,
			case SUB,
			case MUL,
			case DIV,
			case MOD,
			case POWER: true
			default: false
		}
	}

	def static boolean isComparison(STBinaryOperator operator) {
		switch (operator) {
			case EQ,
			case NE,
			case GE,
			case GT,
			case LE,
			case LT: true
			default: false
		}
	}

	def static boolean isLogical(STBinaryOperator operator) {
		switch (operator) {
			case AMPERSAND,
			case AND,
			case OR,
			case XOR: true
			default: false
		}
	}

	def static boolean isRange(STBinaryOperator operator) {
		operator == STBinaryOperator.RANGE
	}

	def static boolean isApplicableTo(STUnaryOperator operator, INamedElement type) {
		switch (operator) {
			case PLUS,
			case MINUS: type instanceof AnyMagnitudeType && !(type instanceof AnyUnsignedType)
			case NOT: type instanceof AnyBitType
		}
	}

	def static boolean isApplicableTo(STBinaryOperator operator, INamedElement first, INamedElement second) {
		switch (operator) {
			case ADD,
			case SUB: first instanceof AnyMagnitudeType && second instanceof AnyMagnitudeType
			case MUL,
			case DIV: first instanceof AnyMagnitudeType && second instanceof AnyNumType
			case MOD: first instanceof AnyIntType && second instanceof AnyIntType
			case POWER: first instanceof AnyRealType && second instanceof AnyNumType
			case AMPERSAND,
			case AND,
			case OR,
			case XOR: first instanceof AnyBitType && second instanceof AnyBitType
			case EQ,
			case NE,
			case GE,
			case GT,
			case LE,
			case LT,
			case RANGE: true
		}
	}

	def static boolean isAssignable(STExpression expression) {
		switch (it : expression) {
			STMultibitPartialExpression,
			STFeatureExpression case !call,
			STArrayAccessExpression case receiver.assignable,
			STMemberAccessExpression case receiver.assignable && member.assignable: true
			default: false
		}
	}

	def static boolean isNumericValueValid(DataType type, Object value) {
		switch (value) {
			Boolean:
				type instanceof BoolType
			BigDecimal:
				switch (type) {
					RealType: Float.isFinite(value.floatValue)
					LrealType: Double.isFinite(value.doubleValue)
					default: false
				}
			BigInteger:
				switch (type) {
					SintType: value.checkRange(Byte.MIN_VALUE, Byte.MAX_VALUE)
					IntType: value.checkRange(Short.MIN_VALUE, Short.MAX_VALUE)
					DintType: value.checkRange(Integer.MIN_VALUE, Integer.MAX_VALUE)
					LintType: value.checkRange(Long.MIN_VALUE, Long.MAX_VALUE)
					UsintType: value.checkRangeUnsigned(0xff#bi)
					UintType: value.checkRangeUnsigned(0xffff#bi)
					UdintType: value.checkRangeUnsigned(0xffffffff#bi)
					UlintType: value.checkRangeUnsigned(0xffffffffffffffff#bi)
					BoolType: value.checkRangeUnsigned(1bi)
					ByteType: value.checkRangeUnsigned(0xff#bi)
					WordType: value.checkRangeUnsigned(0xffff#bi)
					DwordType: value.checkRangeUnsigned(0xffffffff#bi)
					LwordType: value.checkRangeUnsigned(0xffffffffffffffff#bi)
					default: false
				}
			default:
				false
		}
	}

	def static boolean isStringValueValid(DataType type, STString value) {
		switch (type) {
			CharType: !value.wide && value.length === 1
			StringType: !value.wide
			WcharType: value.wide && value.length === 1
			WstringType: value.wide
			default: false
		}
	}

	def static checkRange(BigInteger value, long lower, long upper) {
		value >= BigInteger.valueOf(lower) && value <= BigInteger.valueOf(upper)
	}

	def static checkRangeUnsigned(BigInteger value, BigInteger upper) {
		value.signum >= 0 && value <= upper
	}

	def static INamedElement getExpectedType(STExpression expression) {
		switch (it : expression.eContainer) {
			STUnaryExpression case op.arithmetic,
			STBinaryExpression case op.arithmetic || op.range:
				expectedType.equivalentAnyNumType
			STUnaryExpression case op.logical,
			STBinaryExpression case op.logical:
				expectedType.equivalentAnyBitType
			STBinaryExpression case op.comparison:
				expression === left ? right.declaredResultType : left.declaredResultType
			STAssignmentStatement:
				expression === left ? right.declaredResultType : left.declaredResultType
			STIfStatement,
			STWhileStatement,
			STRepeatStatement:
				ElementaryTypes.BOOL
			STForStatement:
				variable.type
			STCaseCases:
				statement.selector.declaredResultType
			STInitializerExpression:
				expectedType
			STArrayInitElement:
				if (initExpressions.empty || expression !== indexOrInitExpression)
					expectedType
				else
					ElementaryTypes.INT
			STStructInitElement:
				variable.featureType
			STCallNamedInputArgument:
				// get parameter (target) but don't resolve
				switch (parameter: parameterNoresolve) {
					VarDeclaration: parameter.type
					STVarDeclaration: parameter.type
				}
			STCallUnnamedArgument:
				// get parameter (target) but don't resolve
				switch (parameter: parameterNoresolve) {
					VarDeclaration: parameter.type
					STVarDeclaration: parameter.type
				}
		}
	}

	def static INamedElement getExpectedType(STInitializerExpression expression) {
		switch (it : expression.eContainer) {
			STVarDeclaration: type
			STArrayInitElement: expectedType
			STStructInitElement: variable.featureType
			STArrayInitializerExpression: expectedType
			STStructInitializerExpression: expectedType
		}
	}

	def static INamedElement getExpectedType(STArrayInitElement initElement) {
		switch (it : initElement.eContainer) {
			STArrayInitializerExpression: expectedType
		}
	}

	def package static getParameterNoresolve(STCallNamedInputArgument argument) {
		switch (target : argument.eGet(STCorePackage.eINSTANCE.STCallNamedInputArgument_Parameter, false)) {
			INamedElement case !target.eIsProxy: target
		}
	}

	def package static getParameterNoresolve(STCallUnnamedArgument argument) {
		val featureExpression = argument.featureExpression
		if (featureExpression !== null) {
			val index = featureExpression.parameters.indexOf(argument)
			if (index >= 0) {
				val feature = featureExpression.featureNoresolve
				if (feature instanceof ICallable) {
					if (index < feature.inputParameters.size)
						feature.inputParameters.get(index)
					else if (index < feature.inputParameters.size + feature.inOutParameters.size)
						feature.inOutParameters.get(index - feature.inputParameters.size)
					else if (index <
						feature.inputParameters.size + feature.inOutParameters.size + feature.outputParameters.size)
						feature.outputParameters.get(index - feature.inputParameters.size -
							feature.inOutParameters.size)
				}
			}
		}
	}

	def package static getFeatureNoresolve(STFeatureExpression expr) {
		switch (feature : expr.eGet(STCorePackage.eINSTANCE.STFeatureExpression_Feature, false)) {
			INamedElement case !feature.eIsProxy: feature
		}
	}

	def package static getFeatureExpression(STCallUnnamedArgument argument) {
		switch (container: argument.eContainer) { STFeatureExpression: container }
	}

	def static getFeatureType(INamedElement feature) {
		switch (feature) {
			VarDeclaration:
				if (feature.array)
					feature.type.newArrayType(newSubrange(0, feature.arraySize))
				else
					feature.type
			STVarDeclaration:
				if (feature.array)
					(feature.type as DataType).newArrayType(
						if (feature.ranges.empty)
							feature.count.map[DataFactory.eINSTANCE.createSubrange]
						else
							feature.ranges.map[toSubrange]
					)
				else
					feature.type
			FB:
				feature.type
		}
	}

	def static ArrayType newArrayType(DataType arrayBaseType, Subrange... arraySubranges) {
		arrayBaseType.newArrayType(arraySubranges as Iterable<Subrange>)
	}

	def static ArrayType newArrayType(DataType arrayBaseType, Iterable<Subrange> arraySubranges) {
		DataFactory.eINSTANCE.createArrayType => [
			baseType = arrayBaseType
			subranges.addAll(arraySubranges)
		]
	}

	def static Subrange toSubrange(STExpression expr) {
		switch (expr) {
			STBinaryExpression case expr.op === STBinaryOperator.RANGE:
				newSubrange(expr.left.asConstantInt, expr.right.asConstantInt)
			default:
				newSubrange(0, expr.asConstantInt)
		}
	}

	def static int asConstantInt(STExpression expr) {
		switch (expr) {
			STNumericLiteral: (expr.value as BigInteger).intValueExact
			default: 0
		}
	}

	def static newSubrange(int lower, int upper) {
		DataFactory.eINSTANCE.createSubrange => [
			lowerLimit = lower
			upperLimit = upper
		]
	}

	def static getEquivalentAnyNumType(INamedElement type) {
		switch (type) {
			AnyNumType: type
			BoolType: ElementaryTypes.SINT
			ByteType: ElementaryTypes.USINT
			WordType: ElementaryTypes.UINT
			DwordType: ElementaryTypes.UDINT
			LwordType: ElementaryTypes.ULINT
			default: null
		}
	}

	def static getEquivalentAnyBitType(INamedElement type) {
		switch (type) {
			AnyBitType: type
			SintType,
			UsintType: ElementaryTypes.BYTE
			IntType,
			UintType: ElementaryTypes.WORD
			DintType,
			UdintType: ElementaryTypes.DWORD
			LintType,
			UlintType: ElementaryTypes.LWORD
			default: null
		}
	}

	def static boolean isAncestor(EClassifier clazz, EObject object) {
		if (object === null)
			false
		else if (clazz.isInstance(object))
			true
		else
			clazz.isAncestor(object.eContainer)
	}
}
