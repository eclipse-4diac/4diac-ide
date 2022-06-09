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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public interface FBLaunchConfigurationAttributes extends LaunchConfigurationAttributes {
	String EVENT = "org.eclipse.fordiac.ide.debug.event"; //$NON-NLS-1$
	String REPEAT_EVENT = "org.eclipse.fordiac.ide.debug.repeatEvent"; //$NON-NLS-1$

	static Event getEvent(final ILaunchConfiguration configuration, final FBType type, final Event defaultEvent) throws CoreException {
		final String eventAttribute = configuration.getAttribute(EVENT, ""); //$NON-NLS-1$
		if (eventAttribute != null && !eventAttribute.isEmpty()) {
			final var event = type.getInterfaceList().getEvent(eventAttribute);
			if (event != null && event.isIsInput()) {
				return event;
			}
		}
		return defaultEvent;
	}

	static boolean isRepeatEvent(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(REPEAT_EVENT, false);
	}
}
