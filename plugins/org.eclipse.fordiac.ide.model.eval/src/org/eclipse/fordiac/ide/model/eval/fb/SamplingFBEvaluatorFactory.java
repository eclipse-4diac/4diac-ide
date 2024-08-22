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
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SamplingFBEvaluatorFactory implements EvaluatorFactory {
	private static final String SAMPLING_VARIANT = "sampling"; //$NON-NLS-1$

	@Override
	public Evaluator createEvaluator(final Object source, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		if (source instanceof final FBType fbType) {
			return new SamplingFBEvaluator(fbType, context, variables, parent);
		}
		return null;
	}

	public static void register() {
		final SamplingFBEvaluatorFactory factory = new SamplingFBEvaluatorFactory();
		EvaluatorFactory.Registry.INSTANCE.registerFactory(SAMPLING_VARIANT, SimpleFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(SAMPLING_VARIANT, BasicFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(SAMPLING_VARIANT, FunctionFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(SAMPLING_VARIANT, ServiceInterfaceFBType.class, factory);
	}
}
