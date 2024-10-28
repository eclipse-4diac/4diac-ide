/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.CommonLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.Messages;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class FBLaunchConfigurationDelegate extends CommonLaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
		if (resource instanceof IFile) {
			final FBType type = (FBType) TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource).getType();
			final var event = FBLaunchConfigurationAttributes.getEvent(configuration, type, getDefaultEvent(type));
			final var repeatEvent = FBLaunchConfigurationAttributes.isRepeatEvent(configuration);
			final var keepRunningWhenIdle = FBLaunchConfigurationAttributes.isKeepRunningWhenIdle(configuration);
			final var defaultArguments = getDefaultArguments(type);
			final var variables = LaunchConfigurationAttributes.getArguments(configuration, defaultArguments);
			final var evaluator = createEvaluator(type, variables);

			final FBLaunchEventQueue fBLaunchEventQueue = new FBLaunchEventQueue(event, repeatEvent,
					keepRunningWhenIdle);
			fBLaunchEventQueue.setDebugTimeValue(FBLaunchConfigurationAttributes.getClockMode(configuration),
					FBLaunchConfigurationAttributes.getClockInterval(configuration));
			evaluator.setEventQueue(fBLaunchEventQueue);
			launch(evaluator, configuration, mode, launch, resource, monitor);
		}
	}

	@SuppressWarnings("static-method") // allow subclasses to override
	protected FBEvaluator<?> createEvaluator(final FBType type, final List<Variable<?>> variables) {
		return (FBEvaluator<?>) EvaluatorFactory.createEvaluator(type,
				type.eClass().getInstanceClass().asSubclass(FBType.class), "sampling", null, variables, null); //$NON-NLS-1$
	}

	public static List<Variable<?>> getDefaultArguments(final FBType type) throws CoreException {
		try {
			return List.copyOf(new FBVariable("dummy", type).getChildren().toList()); //$NON-NLS-1$
		} catch (final Exception e) {
			throw new CoreException(Status.error(MessageFormat
					.format(Messages.FBLaunchConfigurationDelegate_InvalidDefaultArguments, type.getName()), e));
		}
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
