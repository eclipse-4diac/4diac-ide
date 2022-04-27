/**
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
 */
package org.eclipse.fordiac.ide.model.eval.st.variable

import java.math.BigInteger
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.Subrange
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.*
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.st.ConstantExpressionEvaluator.evaluate

final class STVariableOperations {
	private new() {
	}

	def static Variable newVariable(STVarDeclaration decl) {
		if (decl.array)
			newVariable(decl.name, newArrayType(decl.type as DataType, decl.ranges.map[newSubrange])).evaluate(
				decl.defaultValue)
		else
			newVariable(decl.name, decl.type).evaluate(decl.defaultValue)
	}

	def static Variable newVariable(STVarDeclaration decl, Value value) {
		newVariable(decl.name, value)
	}

	def private static Subrange newSubrange(STExpression expr) {
		switch (expr) {
			STBinaryExpression case expr.op === STBinaryOperator.RANGE:
				newSubrange(expr.left.asConstantInt, expr.right.asConstantInt)
			default:
				newSubrange(0, expr.asConstantInt)
		}
	}

	def private static int asConstantInt(STExpression expr) {
		switch (expr) {
			STNumericLiteral: (expr.value as BigInteger).intValueExact
			default: 0
		}
	}
}
