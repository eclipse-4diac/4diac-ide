/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval

import java.util.Map
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.impl.EvaluatorFactoryRegistryImpl

interface EvaluatorFactory {
	String DEFAULT_VARIANT = "default"

	def Evaluator createEvaluator(Object source, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent)

	def static <T> Evaluator createEvaluator(T source, Class<? extends T> sourceClass, Variable<?> context,
		Iterable<Variable<?>> variables, Evaluator parent) {
		createEvaluator(source, sourceClass, DEFAULT_VARIANT, context, variables, parent)
	}

	def static <T> Evaluator createEvaluator(T source, Class<? extends T> sourceClass, String variant,
		Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		Registry.INSTANCE.getFactory(variant, sourceClass)?.createEvaluator(source, context, variables, parent)
	}

	interface Registry {
		def EvaluatorFactory getFactory(String variant, Class<?> sourceClass)

		def EvaluatorFactory registerFactory(String variant, Class<?> sourceClass, EvaluatorFactory factory)
		
		def Map<String, Map<Class<?>, EvaluatorFactory>> getVariantClassToFactoryMap()

		Registry INSTANCE = new EvaluatorFactoryRegistryImpl
	}
}
