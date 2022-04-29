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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
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

	def static boolean isValidLeftAssignment(STExpression expression) {
		switch (it : expression) {
			STMultibitPartialExpression,
			STFeatureExpression case !call,
			STArrayAccessExpression case receiver.validLeftAssignment,
			STMemberAccessExpression case receiver.validLeftAssignment && member.validLeftAssignment: true
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
				switch (decl : eContainer) { STVarDeclaration: decl.type }
			STArrayInitElement:
				if (initExpressions.empty || expression !== indexOrInitExpression)
					switch (decl : eContainer?.eContainer) { STVarDeclaration: decl.type }
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

	def package static getParameterNoresolve(STCallNamedInputArgument argument) {
		switch (target : argument.eGet(STCorePackage.eINSTANCE.STCallNamedInputArgument_Target, false)) {
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
}
