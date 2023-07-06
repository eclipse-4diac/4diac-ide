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
package org.eclipse.fordiac.ide.debug.function;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FunctionFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;

public class FunctionFBLaunchConfigurationDelegate extends FBLaunchConfigurationDelegate {

	@Override
	public FBEvaluator<? extends FBType> createEvaluator(final FBType type, final List<Variable<?>> variables)
			throws CoreException {
		return new FunctionFBEvaluator((FunctionFBType) type, null, variables, null);
	}
}
