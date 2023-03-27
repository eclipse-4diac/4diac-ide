/**
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
 */
package org.eclipse.fordiac.ide.model.eval.st.variable

import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.st.ConstantExpressionEvaluator.evaluate
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getFeatureType

final class STVariableOperations {
	private new() {
	}

	def static Variable<?> newVariable(STVarDeclaration decl) {
		newVariable(decl.name, decl.featureType).evaluate(decl.defaultValue)
	}

	def static Variable<?> newVariable(STVarDeclaration decl, Value value) {
		newVariable(decl.name, decl.featureType, value)
	}
}
