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

import org.eclipse.fordiac.ide.deployment.eval.fb.DeploymentFBEvaluator;
import org.eclipse.fordiac.ide.deployment.eval.fb.DeploymentFunctionFBEvaluator;
import org.eclipse.fordiac.ide.deployment.eval.fb.DeploymentSubAppEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class DeploymentEvaluatorFactory implements EvaluatorFactory {
	public static final String DEPLOYMENT_VARIANT = "deployment"; //$NON-NLS-1$

	@Override
	public Evaluator createEvaluator(final Object source, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		return switch (source) {
		case final FunctionFBType functionFBType ->
			new DeploymentFunctionFBEvaluator(functionFBType, context, variables, parent);
		case final SubAppType subAppType -> new DeploymentSubAppEvaluator(subAppType, context, variables, parent);
		case final FBType fbType -> new DeploymentFBEvaluator<>(fbType, context, variables, parent);
		default -> null;
		};
	}

	public static void register() {
		final DeploymentEvaluatorFactory factory = new DeploymentEvaluatorFactory();
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT,
				ServiceInterfaceFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(EvaluatorFactory.DEFAULT_VARIANT, SubAppType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(DEPLOYMENT_VARIANT, SimpleFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(DEPLOYMENT_VARIANT, BasicFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(DEPLOYMENT_VARIANT, FunctionFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(DEPLOYMENT_VARIANT, ServiceInterfaceFBType.class, factory);
		EvaluatorFactory.Registry.INSTANCE.registerFactory(DEPLOYMENT_VARIANT, SubAppType.class, factory);
	}
}
