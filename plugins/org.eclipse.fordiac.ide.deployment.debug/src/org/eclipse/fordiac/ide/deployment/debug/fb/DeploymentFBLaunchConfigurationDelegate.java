/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.fb;

import java.util.List;

import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class DeploymentFBLaunchConfigurationDelegate extends FBLaunchConfigurationDelegate {
	public static final String DEPLOYMENT_VARIANT = "deployment"; //$NON-NLS-1$

	@Override
	protected FBEvaluator<?> createEvaluator(final FBType type, final List<Variable<?>> variables) {
		return (FBEvaluator<?>) EvaluatorFactory.createEvaluator(type,
				type.eClass().getInstanceClass().asSubclass(FBType.class), DEPLOYMENT_VARIANT, null, variables, null);
	}
}
