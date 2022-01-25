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
package org.eclipse.fordiac.ide.debug.fb;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public abstract class FBLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
		if (resource != null && resource instanceof IFile) {
			FBType type = (FBType) TypeLibrary.getPaletteEntryForFile((IFile) resource).getType();
			var event = FBLaunchConfigurationAttributes.getEvent(configuration, type, getDefaultEvent(type));
			var defaultArguments = getDefaultArguments(type);
			var variables = FBLaunchConfigurationAttributes.getArguments(configuration, defaultArguments);
			launch(type, event, variables, configuration, mode, launch, monitor);
		}
	}

	public void launch(FBType type, Event event, List<Variable> variables, ILaunchConfiguration configuration,
			String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		Queue<Event> queue = new ArrayBlockingQueue<>(1000);
		Evaluator evaluator = createEvaluator(type, queue, variables);
		queue.add(event);
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

	public abstract FBEvaluator<? extends FBType> createEvaluator(FBType type, Queue<Event> queue,
			List<Variable> variables) throws CoreException;

	protected List<Variable> getDefaultArguments(FBType type) {
		return type.getInterfaceList().getInputVars().stream().map(decl -> new ElementaryVariable(decl))
				.collect(Collectors.toList());
	}

	protected Event getDefaultEvent(FBType type) {
		final var eventInputs = type.getInterfaceList().getEventInputs();
		if (eventInputs.size() != 0) {
			return eventInputs.get(0);
		}
		return null;
	}
}
