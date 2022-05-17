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

import java.math.BigInteger
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement

final class STCoreUtil {

	private new() {
	}

	def static boolean isArithmetic(STBinaryOperator operator) {
		switch (operator) {
			case ADD,
			case SUB,
			case MUL,
			case DIV,
			case MOD,
			case POWER,
			case AMPERSAND,
			case AND,
			case OR,
			case XOR: true
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
			case MOD: first instanceof AnyNumType && second instanceof AnyNumType
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

	def static INamedElement getExpectedType(STExpression expression) {
		switch (it : expression.eContainer) {
			STUnaryExpression,
			STBinaryExpression case op.arithmetic || op.range:
				expectedType
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
				switch (stmt : eContainer) { STCaseStatement: stmt.selector.declaredResultType }
			STInitializerExpression:
				expectedType
			STArrayInitElement:
				if (initExpressions.empty || expression !== indexOrInitExpression)
					expectedType
				else
					ElementaryTypes.INT
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
			STArrayInitializerExpression: expectedType
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
					else
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
}
