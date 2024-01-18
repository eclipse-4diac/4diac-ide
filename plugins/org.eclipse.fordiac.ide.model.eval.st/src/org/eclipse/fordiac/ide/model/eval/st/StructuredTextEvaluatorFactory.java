/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.st;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;

public class StructuredTextEvaluatorFactory implements EvaluatorFactory {

	@Override
	public Evaluator createEvaluator(final Object source, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		if (source instanceof final STAlgorithm algorithm) {
			return new STAlgorithmEvaluator(algorithm, context, variables, parent);
		}
		if (source instanceof final ECTransition transition) {
			return new ECTransitionEvaluator(transition, context, variables, parent);
		}
		if (source instanceof final STMethod method) {
			return new STMethodEvaluator(method, context, variables, parent);
		}
		if (source instanceof final org.eclipse.fordiac.ide.model.libraryElement.STMethod libMethod) {
			return new STMethodEvaluator(libMethod, context, variables, parent);
		}
		if (source instanceof final STFunction function) {
			return new STFunctionEvaluator(function, context, variables, parent);
		}
		if (source instanceof final STFunctionBody functionBody) {
			return new STFunctionBodyEvaluator(functionBody, context, variables, parent);
		}
		if (source instanceof final String string) {
			return new ScopedExpressionEvaluator(string, context, variables, parent);
		}
		if (source instanceof final VarDeclaration varDeclaration) {
			return new VarDeclarationEvaluator(varDeclaration, context, variables, parent);
		}
		if (source instanceof final Attribute attribute) {
			return new AttributeEvaluator(attribute, context, variables, parent);
		}
		return null;
	}

	public static void register() {
		final StructuredTextEvaluatorFactory factory = new StructuredTextEvaluatorFactory();
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, STAlgorithm.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, ECTransition.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, STMethod.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT,
				org.eclipse.fordiac.ide.model.libraryElement.STMethod.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, STFunction.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, STFunctionBody.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, String.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, VarDeclaration.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, Attribute.class, factory);
	}
}
