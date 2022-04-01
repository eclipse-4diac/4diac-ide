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
package org.eclipse.fordiac.ide.model.eval.st

import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression

final class ConstantExpressionEvaluator extends StructuredTextEvaluator {
	static final ConstantExpressionEvaluator INSTANCE = new ConstantExpressionEvaluator

	private new() {
		super("anonymous", null, emptyList, null)
	}
	
	def static Value evaluate(STExpression expression) {
		INSTANCE.evaluateExpression(expression)
	}
	
	def static Variable evaluate(Variable variable, STInitializerExpression expression) {
		INSTANCE.evaluateInitializerExpression(variable, expression)
	}
	
	override prepare() {}

	override evaluate() throws EvaluatorException, InterruptedException { null }

	override getSourceElement() { null }
}
