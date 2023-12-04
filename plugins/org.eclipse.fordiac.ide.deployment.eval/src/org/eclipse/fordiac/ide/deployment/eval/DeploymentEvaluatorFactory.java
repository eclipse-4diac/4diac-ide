/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.eval;

import org.eclipse.fordiac.ide.deployment.eval.fb.ServiceInterfaceFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;

public class DeploymentEvaluatorFactory implements EvaluatorFactory {

	@Override
	public Evaluator createEvaluator(final Object source, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		if (source instanceof final ServiceInterfaceFBType serviceInterfaceFBType) {
			return new ServiceInterfaceFBEvaluator(serviceInterfaceFBType, context, variables, parent);
		}
		return null;
	}

	public static void register() {
		final DeploymentEvaluatorFactory factory = new DeploymentEvaluatorFactory();
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT,
				ServiceInterfaceFBType.class, factory);
	}
}
