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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public interface FBLaunchConfigurationAttributes extends LaunchConfigurationAttributes {
	String ID = "org.eclipse.fordiac.ide.debug.fbLaunch"; //$NON-NLS-1$
	String EVENT = "org.eclipse.fordiac.ide.debug.event"; //$NON-NLS-1$
	String REPEAT_EVENT = "org.eclipse.fordiac.ide.debug.repeatEvent"; //$NON-NLS-1$
	String KEEP_RUNNING_WHEN_IDLE = "org.eclipse.fordiac.ide.debug.keepRunningWhenIdle"; //$NON-NLS-1$

	String CLOCK_MODE = "org.eclipse.fordiac.ide.debug.clockMode"; //$NON-NLS-1$

	String CLOCK_INTERVAL = "org.eclipse.fordiac.ide.debug.debugTime"; //$NON-NLS-1$

	static Event getEvent(final ILaunchConfiguration configuration, final FBType type, final Event defaultEvent)
			throws CoreException {
		final String eventAttribute = configuration.getAttribute(EVENT, ""); //$NON-NLS-1$
		if (eventAttribute != null && !eventAttribute.isEmpty()) {
			final var event = type.getInterfaceList().getEvent(eventAttribute);
			if (event != null && event.isIsInput()) {
				return event;
			}
			return Stream
					.concat(type.getInterfaceList().getSockets().stream(), type.getInterfaceList().getPlugs().stream())
					.map(AdapterDeclaration::getAdapterFB).map(AdapterFB::getInterface)
					.map(iface -> iface.getEvent(eventAttribute)).filter(Objects::nonNull)
					.filter(Predicate.not(Event::isIsInput)).findAny().orElse(defaultEvent);
		}
		return defaultEvent;
	}

	static boolean isRepeatEvent(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(REPEAT_EVENT, false);
	}

	static boolean isKeepRunningWhenIdle(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(KEEP_RUNNING_WHEN_IDLE, true);
	}

	static FBDebugClockMode getClockMode(final ILaunchConfiguration configuration) throws CoreException {
		return FBDebugClockMode.fromString(configuration.getAttribute(CLOCK_MODE, (String) null));
	}

	static boolean isSystem(final ILaunchConfiguration configuration) throws CoreException {
		return FBDebugClockMode.SYSTEM.equals(getClockMode(configuration));
	}

	static boolean isIncrement(final ILaunchConfiguration configuration) throws CoreException {
		return FBDebugClockMode.INCREMENT.equals(getClockMode(configuration));
	}

	static boolean isManual(final ILaunchConfiguration configuration) throws CoreException {
		return FBDebugClockMode.MANUAL.equals(getClockMode(configuration));
	}

	static Duration getClockInterval(final ILaunchConfiguration configuration) throws CoreException {
		final var debugtime = getClockIntervalText(configuration);
		if (debugtime != null) {
			try {
				final long value = Long.parseLong(debugtime);
				return Duration.of(value, ChronoUnit.MILLIS);
			} catch (final NumberFormatException | ArithmeticException e) {
				throw new IllegalStateException("Debug clock interval is not accepted!"); //$NON-NLS-1$
			}
		}
		return Duration.ZERO;
	}

	static String getClockIntervalText(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(CLOCK_INTERVAL, "0");
	}
}
