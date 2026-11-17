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
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.deployment.debug.Messages;

/** Creates and updates the launch configurations for snapshot debugging. */
public final class SnapshotFBLaunchConfigurations {

	/**
	 * Store the snapshot in the launch configuration of the target. The launch
	 * configuration is created if none exists for the instance yet.
	 *
	 * @param target   the function block instance
	 * @param snapshot the values read from the runtime
	 * @return the saved launch configuration
	 * @throws CoreException if the launch configuration cannot be saved
	 */
	public static ILaunchConfiguration createOrUpdate(final FBSnapshotTarget target, final FBSnapshot snapshot)
			throws CoreException {
		final ILaunchConfiguration existing = find(target);
		final ILaunchConfigurationWorkingCopy workingCopy;
		if (existing != null) {
			workingCopy = existing.getWorkingCopy();
		} else {
			final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(SnapshotFBLaunchConfigurationAttributes.ID);
			workingCopy = type.newInstance(null,
					manager.generateLaunchConfigurationName(MessageFormat.format(
							Messages.SnapshotFBLaunchConfigurations_Name, target.instance().getName(),
							target.device().getName(), target.resource().getName())));
			SnapshotFBLaunchConfigurationAttributes.setDefaults(workingCopy);
		}
		SnapshotFBLaunchConfigurationAttributes.setTarget(workingCopy, target);
		SnapshotFBLaunchConfigurationAttributes.setSnapshot(workingCopy, snapshot);
		return workingCopy.doSave();
	}

	/**
	 * Store a new snapshot in an existing launch configuration together with the
	 * current type of the instance.
	 *
	 * @param configuration the launch configuration
	 * @param target        the function block instance of the launch configuration
	 * @param snapshot      the values read from the runtime
	 * @return the saved launch configuration
	 * @throws CoreException if the launch configuration cannot be saved
	 */
	public static ILaunchConfiguration update(final ILaunchConfiguration configuration, final FBSnapshotTarget target,
			final FBSnapshot snapshot) throws CoreException {
		final ILaunchConfigurationWorkingCopy workingCopy = configuration.getWorkingCopy();
		// the values belong to the type the instance has now
		SnapshotFBLaunchConfigurationAttributes.setTarget(workingCopy, target);
		SnapshotFBLaunchConfigurationAttributes.setSnapshot(workingCopy, snapshot);
		return workingCopy.doSave();
	}

	/**
	 * Find the launch configuration for the instance of the target.
	 *
	 * @param target the function block instance
	 * @return the launch configuration or {@code null}
	 * @throws CoreException if the launch configurations cannot be read
	 */
	public static ILaunchConfiguration find(final FBSnapshotTarget target) throws CoreException {
		final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		final ILaunchConfigurationType type = manager
				.getLaunchConfigurationType(SnapshotFBLaunchConfigurationAttributes.ID);
		return Stream.of(manager.getLaunchConfigurations(type))
				.filter(configuration -> isConfigurationFor(configuration, target)).findFirst().orElse(null);
	}

	private static boolean isConfigurationFor(final ILaunchConfiguration configuration,
			final FBSnapshotTarget target) {
		try {
			return SnapshotFBLaunchConfigurationAttributes.isTarget(configuration, target);
		} catch (final CoreException e) {
			ILog.get().warn(e.getMessage(), e);
			return false;
		}
	}

	private SnapshotFBLaunchConfigurations() {
		throw new UnsupportedOperationException();
	}
}
