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
package org.eclipse.fordiac.ide.debug.simple;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.fb.SimpleFBEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SimpleFBLaunchConfigurationDelegate extends FBLaunchConfigurationDelegate {

	@Override
	public void launch(FBType type, ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		Evaluator evaluator = new SimpleFBEvaluator((SimpleFBType) type, null);
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			EvaluatorProcess process = new EvaluatorProcess(configuration.getName(), evaluator, launch);
			process.start();
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			EvaluatorDebugTarget debugTarget = new EvaluatorDebugTarget(configuration.getName(), evaluator, launch);
			debugTarget.start();
		} else {
			throw new CoreException(Status.error("Illegal launch mode: " + mode));
		}
	}
}
