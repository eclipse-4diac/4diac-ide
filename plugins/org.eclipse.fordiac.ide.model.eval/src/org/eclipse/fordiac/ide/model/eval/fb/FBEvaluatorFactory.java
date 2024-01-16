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
package org.eclipse.fordiac.ide.model.eval.fb;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class FBEvaluatorFactory implements EvaluatorFactory {
	private static final String DEPLOYMENT_VARIANT = "deployment"; //$NON-NLS-1$

	@Override
	public Evaluator createEvaluator(final Object source, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		if (source instanceof final SimpleFBType simpleFBType) {
			return new SimpleFBEvaluator(simpleFBType, context, variables, parent);
		}
		if (source instanceof final BasicFBType basicFBType) {
			return new BasicFBEvaluator(basicFBType, context, variables, parent);
		}
		if (source instanceof final FunctionFBType functionFBType) {
			if (functionFBType.getBody() == null) { // try to delegate to deployment variant if no body exists
				return EvaluatorFactory.createEvaluator(functionFBType, FunctionFBType.class, DEPLOYMENT_VARIANT,
						context, variables, parent);
			}
			return new FunctionFBEvaluator(functionFBType, context, variables, parent);
		}
		return null;
	}

	public static void register() {
		final FBEvaluatorFactory factory = new FBEvaluatorFactory();
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, SimpleFBType.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, BasicFBType.class,
				factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, FunctionFBType.class,
				factory);
	}
}
