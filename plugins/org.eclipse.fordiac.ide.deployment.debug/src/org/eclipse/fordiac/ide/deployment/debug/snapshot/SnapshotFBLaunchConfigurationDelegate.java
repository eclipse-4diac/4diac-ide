/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/**
 * Debugs the type of a function block in the evaluator, starting from values
 * read from a running instance.
 */
public class SnapshotFBLaunchConfigurationDelegate extends FBLaunchConfigurationDelegate {

	/** Status code of the warning about missing values. */
	public static final int INCOMPLETE_SNAPSHOT_CODE = 201;

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		SnapshotFBLaunchConfigurationAttributes.checkType(configuration);
		provideRuntime(configuration, launch, monitor);
		reportIncompleteSnapshot(configuration);
		super.launch(configuration, mode, launch, monitor);
	}

	/** Get a runtime for the internal function blocks that need one. */
	private void provideRuntime(final ILaunchConfiguration configuration, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final FBSnapshotTarget target = SnapshotFBLaunchConfigurationAttributes.getTarget(configuration);
		final List<FB> runtimeFBs = FBSnapshotValidator.getRuntimeFBs(target.type());
		if (!runtimeFBs.isEmpty()) {
			getRuntimeProvider().provide(runtimeFBs, launch, monitor);
		}
	}

	/**
	 * @return the provider of a runtime for the internal function blocks that the
	 *         evaluator cannot execute
	 */
	@SuppressWarnings("static-method") // allow subclasses to override
	protected SnapshotRuntimeProvider getRuntimeProvider() {
		return SnapshotRuntimeProvider.NONE;
	}

	@Override
	protected List<Variable<?>> getArguments(final ILaunchConfiguration configuration, final FBType type)
			throws CoreException {
		final List<Variable<?>> arguments = super.getArguments(configuration, type);
		setInternalFBValues(arguments,
				SnapshotFBLaunchConfigurationAttributes.getSnapshotInternalFBValues(configuration));
		return arguments;
	}

	/**
	 * Set the values of internal function blocks in the arguments of a launch.
	 *
	 * @param arguments the arguments, internal function blocks are FB variables
	 * @param values    the values by their path, e.g. {@code trig.MEM}
	 */
	static void setInternalFBValues(final List<Variable<?>> arguments, final Map<String, String> values) {
		values.forEach((path, value) -> {
			final Variable<?> variable = findVariable(arguments, path);
			try {
				if (variable == null) {
					throw new IllegalArgumentException(path);
				}
				variable.setValue(value);
			} catch (final Exception e) {
				ILog.get().warn(MessageFormat.format(Messages.FBSnapshotReader_InvalidValue, path, value), e);
			}
		});
	}

	private static Variable<?> findVariable(final List<Variable<?>> arguments, final String path) {
		final String[] names = path.split("\\."); //$NON-NLS-1$
		Variable<?> variable = arguments.stream().filter(argument -> argument.getName().equals(names[0]))
				.findFirst().orElse(null);
		for (int i = 1; i < names.length; i++) {
			if (!(variable instanceof final FBVariable fbVariable)) {
				return null;
			}
			variable = fbVariable.getMembers().get(names[i]);
		}
		return variable;
	}

	/** Warn about the stored snapshot, which is the one this launch uses. */
	private static void reportIncompleteSnapshot(final ILaunchConfiguration configuration) throws CoreException {
		final List<String> missing = SnapshotFBLaunchConfigurationAttributes.getSnapshotMissing(configuration);
		if (missing.isEmpty()) {
			return;
		}
		final IStatus status = new Status(IStatus.WARNING, SnapshotFBLaunchConfigurationDelegate.class,
				INCOMPLETE_SNAPSHOT_CODE, MessageFormat.format(Messages.SnapshotFBLaunchConfigurationDelegate_Missing,
						String.join(", ", missing)), //$NON-NLS-1$
				null);
		final IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(status);
		if (handler != null) {
			handler.handleStatus(status, configuration);
		} else {
			ILog.get().log(status);
		}
	}
}
