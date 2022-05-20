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
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public abstract class FBLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
		if (resource instanceof IFile) {
			final FBType type = (FBType) TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource).getType();
			final var event = FBLaunchConfigurationAttributes.getEvent(configuration, type, getDefaultEvent(type));
			final var defaultArguments = getDefaultArguments(type);
			final var variables = LaunchConfigurationAttributes.getArguments(configuration, defaultArguments);
			launch(type, event, variables, configuration, mode, launch, monitor);
		}
	}

	public void launch(final FBType type, final Event event, final List<Variable<?>> variables,
			final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final Queue<Event> queue = new ArrayBlockingQueue<>(1000);
		final Evaluator evaluator = createEvaluator(type, queue, variables);
		queue.add(event);
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			final EvaluatorProcess process = new EvaluatorProcess(configuration.getName(), evaluator, launch);
			process.start();
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			final EvaluatorDebugTarget debugTarget = new EvaluatorDebugTarget(configuration.getName(), evaluator,
					launch);
			debugTarget.start();
		} else {
			throw new CoreException(Status.error("Illegal launch mode: " + mode)); //$NON-NLS-1$
		}
	}

	public abstract FBEvaluator<? extends FBType> createEvaluator(FBType type, Queue<Event> queue,
			List<Variable<?>> variables) throws CoreException;

	@SuppressWarnings("static-method")
	protected List<Variable<?>> getDefaultArguments(final FBType type) {
		return type.getInterfaceList().getInputVars().stream().map(VariableOperations::newVariable)
				.collect(Collectors.toList());
	}

	@SuppressWarnings("static-method")
	protected Event getDefaultEvent(final FBType type) {
		final var eventInputs = type.getInterfaceList().getEventInputs();
		if (!eventInputs.isEmpty()) {
			return eventInputs.get(0);
		}
		return null;
	}
}
