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
package org.eclipse.fordiac.ide.model.eval.fb

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType

class FBEvaluatorFactory implements EvaluatorFactory {
	override createEvaluator(Object source, Variable context, Iterable<Variable> variables, Evaluator parent) {
		if (source instanceof SimpleFBType) {
			new SimpleFBEvaluator(source, context, variables, parent)
		} else if (source instanceof BasicFBType) {
			new BasicFBEvaluator(source, context, variables, parent)
		}
	}

	def static void register() {
		val factory = new FBEvaluatorFactory
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(SimpleFBType, factory)
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(BasicFBType, factory)
	}
}
