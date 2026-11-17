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
package org.eclipse.fordiac.ide.deployment.debug.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotFBLaunchConfigurationAttributes;

/** Tests whether an element of the debug view belongs to a snapshot launch. */
public class SnapshotLaunchPropertyTester extends PropertyTester {

	public static final String IS_SNAPSHOT_LAUNCH = "isSnapshotLaunch"; //$NON-NLS-1$

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args,
			final Object expectedValue) {
		return IS_SNAPSHOT_LAUNCH.equals(property) && getSnapshotLaunch(receiver) != null;
	}

	/**
	 * Get the snapshot launch of an element of the debug view.
	 *
	 * @param element the element
	 * @return the launch, or {@code null} if the element does not belong to a
	 *         snapshot launch
	 */
	public static ILaunch getSnapshotLaunch(final Object element) {
		final ILaunch launch = switch (element) {
		case final ILaunch elementLaunch -> elementLaunch;
		case final IDebugElement debugElement -> debugElement.getLaunch();
		case final IProcess process -> process.getLaunch();
		case null, default -> null;
		};
		final ILaunchConfiguration configuration = launch != null ? launch.getLaunchConfiguration() : null;
		try {
			if (configuration != null
					&& SnapshotFBLaunchConfigurationAttributes.ID.equals(configuration.getType().getIdentifier())) {
				return launch;
			}
		} catch (final CoreException e) {
			ILog.get().warn(e.getMessage(), e);
		}
		return null;
	}
}
