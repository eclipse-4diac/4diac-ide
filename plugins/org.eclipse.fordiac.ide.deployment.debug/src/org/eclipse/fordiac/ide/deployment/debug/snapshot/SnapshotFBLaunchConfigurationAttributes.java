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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public interface SnapshotFBLaunchConfigurationAttributes extends FBLaunchConfigurationAttributes {
	String ID = "org.eclipse.fordiac.ide.deployment.debug.snapshotFBLaunch"; //$NON-NLS-1$
	String SYSTEM = DeploymentLaunchConfigurationAttributes.SYSTEM;
	String INSTANCE = "org.eclipse.fordiac.ide.deployment.debug.snapshot.instance"; //$NON-NLS-1$
	String SNAPSHOT_MISSING = "org.eclipse.fordiac.ide.deployment.debug.snapshot.missing"; //$NON-NLS-1$
	String SNAPSHOT_INTERNAL_FB_VALUES = "org.eclipse.fordiac.ide.deployment.debug.snapshot.internalFBValues"; //$NON-NLS-1$

	static String getInstanceName(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(INSTANCE, ""); //$NON-NLS-1$
	}

	static FBNetworkElement getInstance(final ILaunchConfiguration configuration) throws CoreException {
		final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
		final String instanceName = getInstanceName(configuration);
		if (system == null || instanceName.isEmpty()) {
			return null;
		}
		return system.findByQualifiedName(instanceName).filter(FBNetworkElement.class::isInstance)
				.map(FBNetworkElement.class::cast).findFirst().orElse(null);
	}

	static FBSnapshotTarget getTarget(final ILaunchConfiguration configuration) throws CoreException {
		final FBNetworkElement instance = getInstance(configuration);
		if (instance == null) {
			throw new CoreException(Status.error(MessageFormat
					.format(Messages.SnapshotFBLaunchConfigurationAttributes_InstanceNotFound, getInstanceName(configuration))));
		}
		return FBSnapshotTarget.of(instance);
	}

	/**
	 * Check that the launch configuration debugs the current type of its instance,
	 * so that the stored values are not used with another type.
	 *
	 * @throws CoreException if the instance cannot be found or has another type
	 */
	static void checkType(final ILaunchConfiguration configuration) throws CoreException {
		final String configuredType = configuration.getAttribute(RESOURCE, ""); //$NON-NLS-1$
		final String instanceType = getFullPath(getTarget(configuration).type().getTypeEntry());
		if (!instanceType.equals(configuredType)) {
			throw new CoreException(Status.error(MessageFormat.format(
					Messages.SnapshotFBLaunchConfigurationAttributes_TypeMismatch, configuredType,
					getInstanceName(configuration), instanceType)));
		}
	}

	static List<String> getSnapshotMissing(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(SNAPSHOT_MISSING, List.of());
	}

	static Map<String, String> getSnapshotInternalFBValues(final ILaunchConfiguration configuration)
			throws CoreException {
		return configuration.getAttribute(SNAPSHOT_INTERNAL_FB_VALUES, Map.of());
	}

	/**
	 * Point the launch configuration to the type, system and instance of the
	 * target.
	 */
	static void setTarget(final ILaunchConfigurationWorkingCopy configuration, final FBSnapshotTarget target) {
		configuration.setAttribute(RESOURCE, getFullPath(target.type().getTypeEntry()));
		configuration.setAttribute(SYSTEM, getFullPath(target.device().getAutomationSystem().getTypeEntry()));
		configuration.setAttribute(INSTANCE, target.instance().getQualifiedName());
	}

	/**
	 * Check whether the launch configuration points to the instance of the target.
	 * The system file and the name of the instance are compared, because editors
	 * work on their own copy of the system.
	 */
	static boolean isTarget(final ILaunchConfiguration configuration, final FBSnapshotTarget target)
			throws CoreException {
		return target.instance().getQualifiedName().equals(getInstanceName(configuration))
				&& getFullPath(target.device().getAutomationSystem().getTypeEntry())
						.equals(configuration.getAttribute(SYSTEM, "")); //$NON-NLS-1$
	}

	/**
	 * Set the defaults for snapshot debugging: stop on the first line and run the
	 * selected event only once.
	 */
	static void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(STOP_ON_FIRST_LINE, true);
		configuration.setAttribute(REPEAT_EVENT, false);
		configuration.setAttribute(KEEP_RUNNING_WHEN_IDLE, false);
	}

	/**
	 * Store the snapshot: the variables of the function block as literals in the
	 * arguments, the variables of its internal function blocks by their path,
	 * e.g. {@code trig.MEM}, and the paths of the missing values.
	 */
	static void setSnapshot(final ILaunchConfigurationWorkingCopy configuration, final FBSnapshot snapshot) {
		final Map<String, String> arguments = new HashMap<>();
		final Map<String, String> internalFBValues = new HashMap<>();
		snapshot.variable().getMembers().forEach((name, member) -> {
			if (member instanceof final FBVariable internalFB) {
				putInternalFBValues(name + '.', internalFB, internalFBValues);
			} else {
				arguments.put(name, member.toString());
			}
		});
		configuration.setAttribute(ARGUMENTS, arguments);
		configuration.setAttribute(SNAPSHOT_INTERNAL_FB_VALUES, internalFBValues);
		configuration.setAttribute(SNAPSHOT_MISSING, snapshot.missing());
	}

	private static void putInternalFBValues(final String path, final FBVariable internalFB,
			final Map<String, String> values) {
		internalFB.getMembers().forEach((name, member) -> {
			if (member instanceof final FBVariable nestedFB) {
				putInternalFBValues(path + name + '.', nestedFB, values);
			} else {
				values.put(path + name, member.toString());
			}
		});
	}

	private static String getFullPath(final TypeEntry typeEntry) {
		final IFile file = typeEntry != null ? typeEntry.getFile() : null;
		return file != null ? file.getFullPath().toString() : ""; //$NON-NLS-1$
	}
}
